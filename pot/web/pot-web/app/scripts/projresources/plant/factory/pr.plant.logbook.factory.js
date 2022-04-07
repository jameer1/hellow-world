'use strict';
app.factory('PlantLogBookFactory', ["ngDialog", "$q", "blockUI", "$rootScope", "GenericAlertService", "PlantRegisterService", "EmployeeMasterDetailsFactory", function (ngDialog, $q, blockUI, $rootScope, GenericAlertService, PlantRegisterService,
	EmployeeMasterDetailsFactory) {
	var popup;	
	var service = {};
	service.getLogBookDetails = function (itemData) {
		var deferred = $q.defer();
		popup = ngDialog.open({
			template: 'views/projresources/projplantreg/plantlogbook/plantreglogbookpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				var selectedlogbook = [];
				$scope.plantLogRecordsTOs = angular.copy(itemData.editLogBook);
				$scope.userProjMap = itemData.userProjMap;
				$scope.calcRemainingUnits = function (meterObj) {
					meterObj.netUnits = 0;
					$scope.start = parseFloat(meterObj.startMeter);
					$scope.end = parseFloat(meterObj.endMeter);
					if ($scope.end > $scope.start) {
						meterObj.netUnits = meterObj.netUnits + (($scope.end) - ($scope.start));
						return meterObj.netUnits;
					}
				};
				$scope.savePlantLogBook = function () {
					if ($scope.end < $scope.start) {
						GenericAlertService.alertMessage('Start Meter Reading cannot be greater than End Meter Reading', "Warning");
						return;
					}
					var req = {
						"plantLogRecordsTO": $scope.plantLogRecordsTOs[0],
						"status": 1,
						"projId": $rootScope.selectedPlantData.projId,
						"plantId": $rootScope.selectedPlantData.id
					}
					blockUI.start();
					PlantRegisterService.savePlantLogRecords(req).then(function (data) {
						blockUI.stop();
						// var succMsg = GenericAlertService.alertMessageModal('Plant LogBook Details are  ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant Log Book Details saved successfully',"Info");
						succMsg.then(function () {
							$scope.closeThisDialog();
							var returnPopObj = {
								"plantLogRecordsTOs": data.plantLogRecordsTOs
							};
							deferred.resolve(returnPopObj);
			//				$rootScope.$broadcast('plantRefresh',{tabIndex:5});
			//				$scope.getPlantLogSearch();
							$scope.getPlantLogRecords();
						})
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occured while saving plant LogBook Details ", 'Error');

					});
				},
				$scope.getDriverDetails = function (plantLogRecordsTO) {
					if (!$rootScope.selectedPlantData.projId) {
						GenericAlertService.alertMessage("Please select the Project", "Warning");
						return;
					}
					var getEmployeeRegisterReq = {
						"projId" : $rootScope.selectedPlantData.projId
					};
					EmployeeMasterDetailsFactory.getEmployeeMasterDetails(getEmployeeRegisterReq).then(function (data) {
						if (!plantLogRecordsTO.empLabelKeyTO) {
							plantLogRecordsTO.empLabelKeyTO = {
								"id": null,
								"code": null,
								"name": null
							};
						}
						plantLogRecordsTO.empLabelKeyTO.id = data.employeeMasterTO.id;
						plantLogRecordsTO.empLabelKeyTO.code = data.employeeMasterTO.empCode;
						plantLogRecordsTO.empLabelKeyTO.name = data.employeeMasterTO.firstName + data.employeeMasterTO.lastName;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
					});
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
