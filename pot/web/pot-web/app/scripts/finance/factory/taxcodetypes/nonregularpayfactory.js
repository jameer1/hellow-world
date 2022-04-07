'use strict';
app.factory('NonRegularPayedFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "NonRegularPayService", "NonRegularPayPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, NonRegularPayService, NonRegularPayPopUpFactory, GenericAlertService) {
	var nonRegularPay;
	var service = {};

	service.getNonRegularPayDetails = function(taxCountryProvisionId) {
		var deferred = $q.defer();
		var nonRegularPayReq = {
			"taxId" : taxCountryProvisionId,
			"status" : "1"
		};
		NonRegularPayService.getNonRegularPay(nonRegularPayReq).then(function(data) {
			var nonRegularPay = [];
			nonRegularPay = service.openNonRegularPayPopup(data.nonRegularPayAllowanceTOs, taxCountryProvisionId);
			nonRegularPay.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting NonRegular Pay Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting NonRegular Pay Details", 'Error');
		});
		return deferred.promise;

	}, service.openNonRegularPayPopup = function(nonRegularPayAllowanceTOs, taxCountryProvisionId) {
		var deferred = $q.defer();
		nonRegularPay = ngDialog.open({
			template : 'views/finance/taxcodetypes/nonregularpayallowances.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editNonRegularPay = [];
				$scope.nonRegularPayList = nonRegularPayAllowanceTOs;
				$scope.nonRegularPaySearch = {};

				$scope.nonRegularPayRowSelect = function(nonRegularPay) {
					if (nonRegularPay.selected) {
						editNonRegularPay.push(nonRegularPay);
					} else {
						editNonRegularPay.pop(nonRegularPay);
					}
				}
				$scope.addNonRegularPay = function(actionType) {
					if (actionType == 'Edit' && editNonRegularPay <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = NonRegularPayPopUpFactory.nonRegularPayPopupDetails(actionType, editNonRegularPay, taxCountryProvisionId);
						popupDetails.then(function(data) {
							$scope.nonRegularPayList = data.nonRegularPayAllowanceTOs;
							editNonRegularPay = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deleteNonRegularPay = function() {
					if (editNonRegularPay.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.nonRegularPayList = [];
					} else {
						angular.forEach(editNonRegularPay, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						NonRegularPayService.deleteNonRegularPay(req).then(function(data) {
							GenericAlertService.alertMessage('NonRegular Pay Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editNonRegularPay, function(value, key) {
							$scope.nonRegularPayList.splice($scope.nonRegularPayList.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('NonRegular Pay Details are failed to Deactivate', "Error");
						});
						editNonRegularPay = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);

