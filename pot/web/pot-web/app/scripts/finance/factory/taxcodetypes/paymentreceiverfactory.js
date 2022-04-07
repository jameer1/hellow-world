'use strict';
app.factory('PaymentReceiverFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "TaxPaymentService", "PaymentReceiverPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, TaxPaymentService, PaymentReceiverPopUpFactory, GenericAlertService) {
	var payment;
	var service = {};

	service.getPaymentReceiverDetails = function(taxCountryProvisionId) {
		var deferred = $q.defer();
		var paymentReq = {
			"taxId" : taxCountryProvisionId,
			"status" : "1"
		};
		TaxPaymentService.getTaxPayment(paymentReq).then(function(data) {
			var payment = [];
			payment = service.openPaymentReceiverPopup(data.taxPaymentDetailsTOs, taxCountryProvisionId);
			payment.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Payment Receiver(s)", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Payment Receiver(s)", 'Error');
		});
		return deferred.promise;

	}, service.openPaymentReceiverPopup = function(taxPaymentDetailsTOs, taxCountryProvisionId) {
		var deferred = $q.defer();
		payment = ngDialog.open({
			template : 'views/finance/taxcodetypes/taxpaymentdetails.html',
			//className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editTaxPayment = [];
				$scope.taxPaymentDetails = taxPaymentDetailsTOs;

				$scope.rowSelect = function(taxdetails) {
					if (taxdetails.selected) {
						editTaxPayment.push(taxdetails);
					} else {
						editTaxPayment.pop(taxdetails);
					}
				},

				$scope.addTaxPayment = function(actionType) {
					if (actionType == 'Edit' && editTaxPayment <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = PaymentReceiverPopUpFactory.paymentReceiverPopupDetails(actionType, editTaxPayment, taxCountryProvisionId);
						popupDetails.then(function(data) {
							$scope.taxPaymentDetails = data.taxPaymentDetailsTOs;
							editTaxPayment = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deleteTaxPayment = function() {
					if (editTaxPayment.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.taxPaymentDetails = [];
					} else {
						angular.forEach(editTaxPayment, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						TaxPaymentService.deleteTaxPayment(req).then(function(data) {
							GenericAlertService.alertMessage('Payment Receiver(s) is/are Deactivated successfully', 'Info');
						});

						angular.forEach(editTaxPayment, function(value, key) {
							$scope.taxPaymentDetails.splice($scope.taxPaymentDetails.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('Payment Receiver(s) is/are failed to Deactivate', "Error");
						});
						editTaxPayment = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);