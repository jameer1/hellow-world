'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # PreContract Service in the potApp.
 */
app.factory('PurchaseOrderService', ["Restangular", function(Restangular) {
	return {
		getPurchaseOrders : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getPurchaseOrders").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		getManpowerInvoiceDocketItems : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getManpowerInvoiceDocket").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		getPlantInvoiceDocket : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getPlantInvoiceDocket").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		getMaterialInvoiceDocket : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getMaterialInvoiceDocket").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		getServiceInvoiceDocket : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getServiceInvoiceDocketItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		getSowInvoiceDocket : function(req) {
			var purchaseDetails = Restangular.one(
					"procurement/getSowInvoiceDocketItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		getInternalPreContracts : function(req) {
			var contractDetails = Restangular.one(
					"procurement/getInternalPreContracts").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return contractDetails;
		},
		getExternalPreContracts : function(req) {

			var externalDetails = Restangular.one(
					"procurement/getExternalPreContracts").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return externalDetails;
		},
		getPurchaseOrderDetails : function(req) {
			var purchaseRegenerate = Restangular.one(
					"procurement/getPurchaseOrderDetails").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return purchaseRegenerate;
		},
		savePurchaseOrders : function(req) {
			var purchaseSaveStatus = Restangular.one(
					"procurement/savePurchaseOrders").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseSaveStatus;
		},
		saveprocurementporepeatpo : function(req) {
			var procurementpoRepeatpo = Restangular.one(
					"procurement/saveprocurementporepeatpo").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return procurementpoRepeatpo;
		},
		regeneratePurchaseOrder : function(req) {
			var purchaseRegenerate = Restangular.one(
					"procurement/regeneratePurchaseOrder").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return purchaseRegenerate;
		},

		getPurchaseOrdersByPrecontractCmpIdAndProjId: function (req) {
			return Restangular.one(
				"procurement/getPurchaseOrdersByPrecontractCmpIdAndProjId").customPOST(req, '',
					{}, {
						ContentType: 'application/json'
					});;
		},

		savePurchaseOrderDetails: function (req) {
			return Restangular.one(
				"procurement/savePurchaseOrderDetails").customPOST(req, '',
					{}, {
						ContentType: 'application/json'
					});;
		},

		saveTermsAndConditions: function (req) {
			return Restangular.one(
				"procurement/saveTermsAndConditions").customPOST(req, '',
					{}, {
						ContentType: 'application/json'
					});;
		},
		// for Repeat Purchase Order
		saveTermsAndConditions: function (req) {
    			return Restangular.one(
    				"procurement/saveTermsAndConditions").customPOST(req, '',
    					{}, {
    						ContentType: 'application/json'
    					});;
    		},

		getTermsAndConditions: function (req) {
			return Restangular.one(
				"procurement/getTermsAndConditions").customPOST(req, '',
					{}, {
						ContentType: 'application/json'
					});;
		},
    		
    	getVendorPostInvoice: function(req) {
    			console.log('function calling');
    			return Restangular.one(
				"finance/getVendorPostInvoice").customPOST(req, '',
					{}, {
						ContentType: 'application/json'
					});;
    	}


	}
}]);
