'use strict';
app.factory('InvoicePaymentFactory', ["$rootScope", "ngDialog", "$q", "$filter", "blockUI", "ProjectInvoiceService", "$timeout", "GenericAlertService", function($rootScope, ngDialog, $q, $filter, blockUI, ProjectInvoiceService, $timeout, GenericAlertService) {
	var invoiceDetailsPopUp;
	var service = {};
	service.invoiceDetails = function(actionType, editInvoice, purchaseId) {
		var deferred = $q.defer();
		invoiceDetailsPopUp = ngDialog.open({
			template : 'views/finance/financemain/invoicewisepaymentpopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedAddress = [];
				$scope.purchaseOrderInvoiceDetails = [];
				var editInvoice = [];
				$scope.serviceType = [ {
					id : "1",
					name : "Manpower"
				}, {
					id : "2",
					name : "Plant"
				}, {
					id : "3",
					name : "Material"
				}, {
					id : "4",
					name : "Services"
				}, {
					id : "5",
					name : "Sub Contract"
				} ];
				$scope.action = actionType;
				if (actionType === 'Add') {
					$scope.purchaseOrderInvoiceDetails.push({
						'purId' : purchaseId,
						'invoiceNo' : null,
						'serviceType' : null,
						'invoicedate' : null,
						'invoiceAmount' : null,
						'taxAmout' : null,
						'invoiceTotalAmout' :null ,
						'payDueDate' : null,
						'payStatus' : 'UnPaid',
						'actualPaymentDate' : null,
						'payReleaseRefNo' : null,
						'accruedCostAmount' : null,
						'paidAmountWithTax' : null,
						'paidAmountWithoutTax' : null,
						'status' : '1',
						'selected' : false
					});
				} else {
					$scope.purchaseOrderInvoiceDetails = angular.copy(editInvoice);
					editInvoice = [];
				}
				$scope.addRows = function() {
					$scope.purchaseOrderInvoiceDetails.push({
						'purId' : purchaseId,
						'invoiceNo' : null,
						'serviceType' : null,
						'invoicedate' : null,
						'invoiceAmount' : null,
						'taxAmout' : null,
						'invoiceTotalAmout' : null,
						'payDueDate' : null,
						'payStatus' : null,
						'actualPaymentDate' : null,
						'payReleaseRefNo' : null,
						'accruedCostAmount' : null,
						'paidAmountWithTax' : null,
						'paidAmountWithoutTax' : null,
					});
				}, $scope.saveInvoiceWiseDetails = function() {
					var req = {
						"purchaseOrderInvoiceDtlTOs" : $scope.purchaseOrderInvoiceDetails,
					}
					blockUI.start();
					ProjectInvoiceService.savePurchaseOrderInvocies(req).then(function(data) {
						blockUI.stop();
						var results = data.purchaseOrderInvoiceDtlTOs;
						GenericAlertService.alertMessage("Invoice wise payment details are saved successfulley", 'Info');
						var returnPopObj = {
							"purchaseOrderInvoiceDtlTOs" : results
						};
						deferred.resolve(returnPopObj);
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Invoice Detail(s) is/are failed to save', 'Error');
					});
				}, $scope.InvoiceWisePopUpRowSelect = function(invoice) {
					if (invoice.selected) {
						selectedInvoice.push(invoice);
					} else {
						selectedInvoice.splice(selectedInvoice.indexOf(invoice), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedInvoice.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedInvoice.length < $scope.invoiceList.length) {
						angular.forEach(selectedInvoice, function(value, key) {
							$scope.invoiceList.splice($scope.invoiceList.indexOf(value), 1);
						});
						selectedInvoice = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedInvoice.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedInvoice.length == 1) {
						$scope.invoiceList[0] = {

							'selected' : false
						};
						selectedInvoice = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				}
				
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
