'use strict';
app.factory('PayDeductionFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PayDeductionService", "PayDeductionPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PayDeductionService, PayDeductionPopUpFactory, GenericAlertService) {
	var payDeduction;
	var service = {};

	service.getPayDeductionDetails = function(taxCountryProvisionId) {
		var deferred = $q.defer();
		var payDeductionReq = {
			"taxId" : taxCountryProvisionId,
			"status" : "1"
		};
		PayDeductionService.getPayDeductions(payDeductionReq).then(function(data) {
			var payDeduction = [];
			payDeduction = service.openPayDeductionPopup(data.payDeductionTOs, taxCountryProvisionId);
			payDeduction.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting PayDeduction Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PayDeduction Details", 'Error');
		});
		return deferred.promise;

	}, service.openPayDeductionPopup = function(payDeductionTOs, taxCountryProvisionId) {
		var deferred = $q.defer();
		payDeduction = ngDialog.open({
			template : 'views/finance/taxcodetypes/paydeductioncodes.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editpayDeduction = [];
				$scope.payDeductionCodes = payDeductionTOs;

				$scope.payDeductionRowSelect = function(payDeduction) {
					if (payDeduction.selected) {
						editpayDeduction.push(payDeduction);
					} else {
						editpayDeduction.pop(payDeduction);
					}
				}
				$scope.addPayDeduction = function(actionType) {
					if (actionType == 'Edit' && editpayDeduction <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = PayDeductionPopUpFactory.payDeductionPopupDetails(actionType, editpayDeduction, taxCountryProvisionId);
						popupDetails.then(function(data) {
							$scope.payDeductionCodes = data.payDeductionTOs;
							editpayDeduction = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deletePayDeduction = function() {
					if (editpayDeduction.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.payDeductionCodes = [];
					} else {
						angular.forEach(editpayDeduction, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						PayDeductionService.deletePayDeductions(req).then(function(data) {
							GenericAlertService.alertMessage(' PayDeduction Detail(s) is/are Deactivated successfully', 'Info');
						});

						angular.forEach(editpayDeduction, function(value, key) {
							$scope.payDeductionCodes.splice($scope.payDeductionCodes.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('PayDeduction Detail(s) is/are failed to Deactivate', "Error");
						});
						editpayDeduction = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
