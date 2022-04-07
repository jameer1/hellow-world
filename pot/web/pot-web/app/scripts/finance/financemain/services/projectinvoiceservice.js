'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Role Service in the potApp.
 */
app.factory('ProjectInvoiceService', ["Restangular", function(Restangular) {
	
	return {
		getPurchaseOrderInvocies : function(req) {
			var invoices = Restangular.one("poorder/getPurchaseOrderInvocies").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		getApproveInvocie : function(req) {
			var invoices = Restangular.one("poorder/getApproveInvoce").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		getReleaseInvoiceDetails : function(req) {
			var invoices = Restangular.one("poorder/getReleasePaymentInvoice").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		savePurchaseOrderInvocies : function(req) {
			var saveStatus = Restangular.one("poorder/savePurchaseOrderInvocies").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		saveInvoiceRequestApprDetails : function(req) {
			var saveStatus = Restangular.one("poorder/saveInvoiceRequestApprDetails").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		getPurchaseOrders : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getPurchaseOrders").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		saveInvoiceDocketItems : function(req) {
			var saveStatus = Restangular.one("poorder/saveManpowerInvoiceDocket").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		savePlantInvoiceDocket : function(req) {
			var saveStatus = Restangular.one("poorder/savePlantInvoiceDocket").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		saveMaterialInvoiceDocket : function(req) {
			var saveStatus = Restangular.one("poorder/saveMaterialInvoiceDocket").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		savePostInvoiceParticulars : function(req) {
			var saveStatus = Restangular.one("poorder/saveInvoiceParticulars").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		saveInvoiceAmount : function(req) {
			var saveStatus = Restangular.one("poorder/saveInvoiceAmount").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		saveInvoiceAssignCostCodes : function(req) {
			var saveStatus = Restangular.one("poorder/saveInvoiceAssignCostCodes").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		saveInvoiceVendorBankDetails : function(req) {
			var saveStatus = Restangular.one("poorder/saveInvoiceVendorBankDetails").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		getInvoiceParticulars : function(req) {
			var invoices = Restangular.one("poorder/getInvoiceParticulars").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		getInvoiceAmount : function(req) {
			var invoices = Restangular.one("poorder/getInvoiceAmount").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		getInvoiceAssignCostCodes : function(req) {
			var invoices = Restangular.one("poorder/getInvoiceAssignCostCodes").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		getInvoiceVendorBankDetails : function(req) {
			var invoices = Restangular.one("poorder/getInvoiceVendorBankDetails").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return invoices;
		},
		saveVendorPostInvoice : function(req) {
			var service = Restangular.one("finance/saveVendorPostInvoice").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		
		getVendorPostInvoice: function(req) {
			console.log('');
			var response = 'success';
			return response;
		},
		getInvoiceMaterial : function(req) {
			var invoiceMaterialDetails = Restangular.one(
					"procurement/getInvoiceMaterial").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return invoiceMaterialDetails;
		}
	
		
	}
}]);
