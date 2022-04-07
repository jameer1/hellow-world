'use strict';
app.factory('ProjectLeaveTypePopUpService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjLeaveTypeService", "GenericAlertService", "ProjCostCodeService", "generalservice", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, ProjLeaveTypeService,
	GenericAlertService, ProjCostCodeService, generalservice) {
	var projLeaveTypePopUp;
	var service = {};
	service.projLeaveTypePopUp = function (actionType, editLeaveClass, countries) {
		var deferred = $q.defer();
		projLeaveTypePopUp = ngDialog.open({
			template: 'views/centrallib/leavetype/leavetypepopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.leaveCodeData = [];
				$scope.catgData = [];
				$scope.categoryData = [];
				$scope.projLeaveTypes = [];
				$scope.emptyProjLeaveTypeObj = {};
				$scope.action = actionType;
				$scope.countries = countries;
				$scope.projLeaveTypes = angular.copy(editLeaveClass);
				$scope.catgData = generalservice.employeeCategory;
				$scope.categoryData = generalservice.leavePayType;
				$scope.leaveReq = {
					"countryName": "",
					"effectiveFrom": ""
				};
				$scope.effectiveDates = [];
				$scope.changeCountry = function () {
					if (!$scope.leaveReq.countryName) {
						GenericAlertService.alertMessage("Select the country to fetch effective dates", "Warning");
						return;
					}
					$scope.effectiveDates = [];
					ProjLeaveTypeService.getEffectiveDatesForCountry($scope.leaveReq.countryName).then(function (data) {
						$scope.effectiveDates = data;
					}, function () {
						GenericAlertService.alertMessage("Error occured while fetching leave types", 'Error');
					});
				};
				$scope.saveLeaveClass = function () {
					if (actionType == "Add") {
						if (!$scope.leaveReq.countryName) {
							GenericAlertService.alertMessage("Select the country to save leave types", "Warning");
							return;
						}
						if (!$scope.leaveReq.effectiveFrom) {
							GenericAlertService.alertMessage("Select the effective from to save leave types", "Warning");
							return;
						}
						const existingDate = $scope.effectiveDates.find(function (item) {
							return item.toLowerCase() == $scope.leaveReq.effectiveFrom.toLowerCase();
						});
						if (existingDate) {
							GenericAlertService.alertMessage("Leave Types are already created for this effective date", "Warning");
							return;
						}
						for (const projLeaveType of $scope.projLeaveTypes) {
							projLeaveType.countryCode = $scope.leaveReq.countryName;
							projLeaveType.effectiveFrom = $scope.leaveReq.effectiveFrom;
						}
					}
					var leaveProjSaveReq = {
						"projLeaveTypeTos": $scope.projLeaveTypes
					};
					blockUI.start();
					ProjLeaveTypeService.saveProjLeaveTypes(leaveProjSaveReq).then(function (data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var projectClassTOs = data.projLeaveTypeTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Leave Type(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Leave Type(s) saved successfully ',"Info");
							succMsg.then(function (data1) {
								$scope.closeThisDialog(projLeaveTypePopUp);
								var returnPopObj = {
									"projLeaveTypeTOs": projectClassTOs
								};

								deferred.resolve(returnPopObj);
							}, function (error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Leave Type(s)  is/are failed to Save', 'Error');
					});
				};
			}]
		});
		return deferred.promise;
	};
	return service;
}]);
