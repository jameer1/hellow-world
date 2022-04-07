'use strict';
app.factory('PayCycleFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PayRollService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PayRollService, GenericAlertService) {
	var payCyclePopup;
	var service = {};
	service.payCyclePopupDetails = function() {
		var deferred = $q.defer();
		payCyclePopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/paycycle.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.periodCycleTOs = [];
				var selectedPayCycle = [];
				$scope.payCycleList = [];
				$scope.payYearList = [];
				$scope.payPeriodCycleList = [];
				$scope.payCycleList.push({
					'selected' : false,
					'status' : 1,
					'payRollCycleLabelKeyTO' : {
						"value" : null
					},
					'payRollCycle' : null,
					'selectYearLabelKeyTO' : {
						"name" : null,
						"value" : null
					},
					"selectYear" : null
				});
				$scope.payCycleRowSelect = function(payCycle) {
					if (payCycle.selected) {
						selectedPayCycle.push(payCycle);
					} else {
						selectedPayCycle.pop(payCycle);
					}
				}
				$scope.addRows = function() {
					$scope.payCycleList.push({
						'selected' : false,
						'status' : 1,
						'payRollCycleLabelKeyTO' : {
							"value" : null
						},
						'payRollCycle' : null,
						'selectYearLabelKeyTO' : {
							"name" : null,
							"value" : null
						},
						"selectYear" : null
					});
				}, $scope.upDateData = function(selectedCreditCycle) {
					// TODO use values in general service, remove the below service
					/*$scope.selectedCreditCycle = selectedCreditCycle.value;
					var dropDownReq = {
						"status" : "1",
						"paycycleId" : $scope.selectedCreditCycle
					}
					PayRollService.getPaycycles(dropDownReq).then(function(data) {
						$scope.payPeriodCycleList = data.financePeriodPayCyclesTOs;
						$scope.cycleTypes = data.cycleTypes;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting pay period cycles", 'Error');
					});*/
				}
				$scope.savePayTypeCycle = function() {
					
					var payCyclereq = {
						"financePeriodPayCyclesTOs" : $scope.payCycleList,
					}
					// blockUI.start();
					PayRollService.savePayPeriodCycle(payCyclereq).then(function(data) {
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
                        GenericAlertService.alertMessageModal('PayCycle Detail(s) is/are ' + data.message, data.status);
						}

					}, function(error) {
						GenericAlertService.alertMessage(' PayCycle Detail(s) is/are failed to Save', 'Error');
					});
					// ngDialog.close();
					$scope.closeThisDialog();
				},

				$scope.deleteRows = function() {
					if (selectedPayCycle.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPayCycle.length < $scope.payRollList.length) {
						angular.forEach(selectedPayCycle, function(value, key) {
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedPayCycle = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.getData = function(payCycle) {
					var req = {
						"status" : 1
					}
					PayRollService.getPayRollCycle(req).then(function(data) {
						$scope.periodCycleTOs = data.periodCycleTOs;
						/*
						 * angular.forEach(data.periodCycleTOs, function(value,
						 * key) { $scope.periodCycleTOs.push(value.value); });
						 */
					});
				}, $scope.updateFinancialYear = function(payRollCycle) {
					payRollCycle.selectYear = payRollCycle.selectYearLabelKeyTO.name;
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);