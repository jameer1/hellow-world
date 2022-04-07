'use strict';

app.factory('EmpChargeOutRatesFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpCostCodeFactory", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpCostCodeFactory, EmpRegisterService, GenericAlertService) {
	var employeeChargePopUp;
	var service = {};
	service.getEmpChargeOutRateDetails = function(itemData) {
	        console.log('Vij itemData');
  				console.log(itemData);
		var deferred = $q.defer();
		employeeChargePopUp = ngDialog.open({
			template : 'views/projresources/projempreg/chargerate/empchargeoutratespopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {

				$scope.chargeOutRate = angular.copy(itemData.chargeOutRate);
				// if (!$scope.chargeOutRate.id) {
				// 	$scope.chargeOutRate.fromDate = $filter('date')(new Date(), "dd-MMM-yyyy");
				// }
				$scope.chargeOutRate.projGenId=itemData.projGeneralLabelTO.id
				$scope.projCostItemMap = itemData.projCostItemMap;
				$scope.projGeneralLabelTO = itemData.projGeneralLabelTO;
				console.log('Vij projGeneralLabelTO');
				console.log($scope.projGeneralLabelTO);
				$scope.projEmployeeClassMap = itemData.projEmployeeClassMap;
				$scope.empDropDown = itemData.empDropDown;
				$scope.chargeOutRate.unit = 'Hours';
				var oldEffectiveFromDate = $scope.chargeOutRate.fromDate;
               // console.log($rootScope.selectedEmployeeData.projEmpRegisterTO,'project');
                $scope.maxDate = new Date($rootScope.selectedEmployeeData.projEmpRegisterTO.mobilaizationDate);
				$scope.saveEmpChargeOutRates = function() {

					if ($scope.chargeOutRate.id > 0 ) {
						var oldFromDate = new Date(oldEffectiveFromDate);
						var fromDate = new Date($scope.chargeOutRate.fromDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
						var deMobilaizationDate = ($scope.chargeOutRate.projEmpRegisterTO.deMobilaizationDate != null) ? new Date($scope.chargeOutRate.projEmpRegisterTO.deMobilaizationDate) : null
						if (days <= 0) {
							GenericAlertService.alertMessage("Please select  the new effective from date", 'Warning');
							return;
						} 
						console.log("old from date: "+fromDate)
						console.log("deMobilaizationDate : "+deMobilaizationDate)
						if(deMobilaizationDate != null){
							if(fromDate > deMobilaizationDate){
								GenericAlertService.alertMessage("Effective Date should be less than deMobilaizationDate", 'Warning');
							return;
							}
						}
					}
					var req = {
						"empChargeOutRateTOs" : [ $scope.chargeOutRate],
						"status" : 1,
						"empId" : $rootScope.selectedEmployeeData.id,
						"projId" : $rootScope.selectedEmployeeData.projId
					};

					EmpRegisterService.saveEmpChargeOutRates(req).then(function(data) {
						// var succMsg = GenericAlertService.alertMessageModal('Employee Charge Out Rate(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Employee Charge out Rate(s) saved successfully',"Info");
						succMsg.then(function() {
							$rootScope.$broadcast('employeeRefresh',{tabIndex:3});
							$scope.closeThisDialog();
							deferred.resolve(data);
						});

					}, function(error) {
						GenericAlertService.alertMessage('Error occured while saving the Employee chargeout details', "Error");
					});
				}

				$scope.getCostCode = function(chargeOutRate, no) {
					var onLoadReq = {
						"status" : 1,
						"projId" : $rootScope.selectedEmployeeData.projId
					};
					console.log('Vij fact onLoadReq');
          console.log(onLoadReq);
					var costCodetPopUp = EmpCostCodeFactory.getCostCodeDetails(onLoadReq);
					console.log('Vij fact costCodetPopUp');
          console.log(costCodetPopUp);
					costCodetPopUp.then(function(data) {
						if (no == 1) {
							chargeOutRate.leaveCostItemId = data.selectedRecord.id;
							chargeOutRate.leaveCostItemCode = data.selectedRecord.code;
						}
						if (no == 2) {
							chargeOutRate.mobCostItemId = data.selectedRecord.id;
							chargeOutRate.mobCostItemCode = data.selectedRecord.code;
						}
						if (no == 3) {
							chargeOutRate.deMobCostItemId = data.selectedRecord.id;
							chargeOutRate.deMobCostItemCode = data.selectedRecord.code;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting cost code details", 'Error');
					});
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
