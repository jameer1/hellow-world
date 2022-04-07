'use strict';
app.factory('PayCycleMasterFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PayRollService", "PayCycleFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,PayRollService, PayCycleFactory, GenericAlertService) {
	var payCycle;
	var service = {};

	service.getPayCycleDetails = function(taxCountryProvisionId) {
		var deferred = $q.defer();
		var req = {
			"taxId" : taxCountryProvisionId,
			"status" : "1"
		};
		PayRollService.getPayRollCycle(req).then(function(data) {
			var payCycle = [];
			payCycle = service.openPayCyclePopup(data.periodCycleTOs, data.yearsTOs, taxCountryProvisionId);
			payCycle.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting PayCycle Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PayCycle Details", 'Error');
		});
		return deferred.promise;

	}, service.openPayCyclePopup = function(periodCycleTOs, yearsTOs,taxCountryProvisionId) {
		var deferred = $q.defer();
		payCycle = ngDialog.open({
			template : 'views/finance/taxcodetypes/paycyclemaster.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editPayCycle = [];
				$scope.payCycleList = periodCycleTOs;

				$scope.payCycleRowSelect = function(payCycle) {
					if (payCycle.selected) {
						editPayCycle.push(payCycle);
					} else {
						editPayCycle.pop(payCycle);
					}
				}
				$scope.addData = function(actionType) {
					if (actionType == 'Edit' && editPayCycle <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = PayCycleFactory.payCyclePopupDetails(actionType, editPayCycle, taxCountryProvisionId);
						popupDetails.then(function(data) {
							$scope.payCycleList = data.periodCycleTOs;
							editPayCycle = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
			/*$scope.deleteData = function() {
					if (editPayCycle.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.payCycleList = [];
					} else {
						angular.forEach(editPayCycle, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						RegularPayservice.deleteRegularPay(req).then(function(data) {
							GenericAlertService.alertMessage('Regular Pay Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editPayCycle, function(value, key) {
							$scope.regularPayList.splice($scope.regularPayList.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('Regular Pay Details are failed to Deactivate', "Error");
						});
						editPayCycle = [];
						$scope.deleteIds = [];

					}
				}*/
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);