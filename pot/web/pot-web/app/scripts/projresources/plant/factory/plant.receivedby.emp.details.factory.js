'use strict';
app.factory('PlantReceivedByEmpDetailsFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectEmployeFactory", "EpsProjectSelectFactory", "PlantTranserDetailsFactory", "EmployeeMasterDetailsFactory", "EmpReqApprUserFactory", "EmpRegisterService", "PlantRegisterService", function(ngDialog, $q, blockUI, $filter, $timeout, $rootScope, GenericAlertService, ProjectEmployeFactory, EpsProjectSelectFactory,
		PlantTranserDetailsFactory, EmployeeMasterDetailsFactory, EmpReqApprUserFactory, EmpRegisterService, PlantRegisterService) {
	var service = {};

	service.getPlantReceivedEmpDetails = function(projId) {
		var deferred = $q.defer();

		var req = {
			"status" : 1,
			"projId" : projId
		};
		blockUI.start();
		EmpRegisterService.empRegisterOnLoad(req).then(function(data) {
			blockUI.stop();
			var popupdata = service.openPopup(data.registerOnLoadTO.companyMap, data.registerOnLoadTO.classificationMap, data.empRegisterDtlTOs);
			popupdata.then(function(resultData) {
				deferred.resolve(resultData);
			});
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
		});
		return deferred.promise;

	}, service.openPopup = function(empCompanyMap, empClassMap, empDetails) {
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template : 'views/projresources/projplantreg/requestForTransfer/receivedbyempdetailspopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			controller : [ '$scope', function($scope) {
			

				$scope.empCompanyMap = empCompanyMap;
				$scope.empClassMap = empClassMap;
				$scope.empDetails = empDetails;
				$scope.empRowselect = function(emp) {

					var returnPopObj = {

						"empCode" : emp.code,

					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
