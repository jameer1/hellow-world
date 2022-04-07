'use strict';
app.factory('PaymentReceiverPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "TaxCodeCountryService", "TaxPaymentService", "TaxCountryMappingFactory", "TaxCodeSelectFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, TaxCodeCountryService, TaxPaymentService, TaxCountryMappingFactory, TaxCodeSelectFactory, GenericAlertService) {
	var paymentPopup;
	var selectedTaxPayment = [];
	var service = {};
	service.paymentReceiverPopupDetails = function(actionType, editTaxPayment, codeTypeCountryProvisionId) {
		var deferred = $q.defer();
		paymentPopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/taxpaymentpopup.html',
			// className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedTaxPayment = [];
				$scope.addTaxdetails = [];
				$scope.getTaxCodes = function(taxdetails) {
					var taxCodesPopup = TaxCodeSelectFactory.getTaxCodes();
					taxCodesPopup.then(function(data) {
						taxdetails.taxCode = data.taxCodesDetails.code;
						taxdetails.taxDesc = data.taxCodesDetails.name;
					})
				}

				if (actionType === 'Add') {
					$scope.addTaxdetails.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'taxCode' : '',
						'taxDesc' : '',
						'deptName' : null,
						'regAddr' : null,
						'accNumber' : null,
						'bankName' : null,
						'bankCode' : null,
					});
				} else {
					$scope.addTaxdetails = angular.copy(editTaxPayment);
					editTaxPayment = [];
				}
				$scope.addRows = function() {

					$scope.addTaxdetails.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'taxCode' : '',
						'taxDesc' : '',
						'deptName' : null,
						'regAddr' : null,
						'accNumber' : null,
						'bankName' : null,
						'bankCode' : null,

					});
				}, $scope.saveTaxPayment = function() {
					var req = {
						"taxPaymentDetailsTOs" : $scope.addTaxdetails,
					}
					blockUI.start();
					TaxPaymentService.saveTaxPayment(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('TaxPayment Detail(s) is/are ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('TaxPayment Detail(s) is/are failed to Save', 'Error');
					});
					$scope.closeThisDialog();
				};

				$scope.popupRowSelect = function(taxdetails) {
					if (taxdetails.selected) {
						selectedTaxPayment.push(taxdetails);
					} else {
						selectedTaxPayment.pop(taxdetails);
					}
				}, $scope.deleteRows = function() {
					if (selectedTaxPayment.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedTaxPayment.length < $scope.addTaxdetails.length) {
						angular.forEach(selectedTaxPayment, function(value, key) {
							$scope.addTaxdetails.splice($scope.addTaxdetails.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedTaxPayment = [];
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