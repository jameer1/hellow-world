'use strict';
app.factory('PayRollPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "PayRollService", "TaxCodeSelectFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, PayRollService,TaxCodeSelectFactory, GenericAlertService) {
	var payRollPopup;
	$rootScope.projEmpClassmap = [];
	var selectedPayRoll = [];
	var service = {};
	service.payRollPopupDetails = function(data, actionType, editPayRoll, codeTypeCountryProvisionId) {
		var deferred = $q.defer();
		payRollPopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/payrollpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedPayRoll = [];
				$scope.addPayRoll = [];
				$scope.payPeriodCycleList = [];
				$scope.employeeWiseCycleTO = data.employeeWiseCycleTOs;
				$scope.projEmpClassMap = data.projEmpClassMap;
				$scope.procureCategoryMap = data.procureCategoryMap;
				$scope.payPeriodCycleMap = data.payPeriodCycleMap;
				$scope.getTaxCodes = function(payRoll) {
					var taxCodesPopup = TaxCodeSelectFactory.getTaxCodes();
					taxCodesPopup.then(function(data) {
						payRoll.taxCode = data.taxCodesDetails.code;
						payRoll.taxDesc = data.taxCodesDetails.name;
					})
				}
				if (actionType === 'Add') {
					$scope.addPayRoll.push({
						'selected' : false,
						'taxCode' : null,
						'taxDesc' : null,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'procureCatgLabelKey' : {
							"id" : null,
							"code" : null,
							"name" : null
						},
						'projEmpLabelKey' : {
							"id" : null,
							"code" : null,
							"name" : null
						},
						'payRollCycleLabelKeyTO' : {
							"id" : null,
							"code" : null,
							
						},
						'comments' : null

					});
				} else {
					$scope.addPayRoll = angular.copy(editPayRoll);
					editPayRoll = [];
				}
				$scope.addRows = function() {
					$scope.addPayRoll.push({
						'selected' : false,
						'taxCode' : null,
						'taxDesc' : null,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'procureCatgLabelKey' : {
							"id" : null,
							"code" : null,
							"name" : null
						},
						'projEmpLabelKey' : {
							"id" : null,
							"code" : null,
							"name" : null
						},
						'payRollCycleLabelKeyTO' : {
							"id" : null,
							"code" : null,
						
						},
						'comments' : null

					});
				}
				$scope.savePayRoll = function() {
					var payRollReq = {
						"employeeWiseCycleTOs" : $scope.addPayRoll
					}
					blockUI.start();
					PayRollService.saveEmpPayTypeCycle(payRollReq).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('PayRoll Detail(s) is/are ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('PayRoll Detail(s) is/are failed to Save', 'Error');
					});
					$scope.closeThisDialog();
				};

				$scope.payRollPopupRowSelect = function(payRoll) {
					if (payRoll.selected) {
						selectedPayRoll.push(payRoll);
					} else {
						selectedPayRoll.pop(payRoll);
					}
				}, $scope.deleteRows = function() {
					if (selectedPayRoll.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPayRoll.length < $scope.addPayRoll.length) {
						angular.forEach(selectedPayRoll, function(value, key) {
							$scope.addPayRoll.splice($scope.addPayRoll.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedPayRoll = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);