'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # RepeatPurchaseOrderService in the potApp.
 */
app.factory('RepeatPurchaseOrderService', ["Restangular", function(Restangular) {
	return {
		getRepeatPurchaseOrders : function(req) {
		//alert('at UI Service');
			var purchaseDetails = Restangular.one(
					"procurement/getRepeatPurchaseOrders").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return purchaseDetails;
		},
		saveRepeatPurchaseOrder : function(req) {
    			var resp = Restangular.one("procurement/saveRepeatPurchaseOrder")
    					.customPOST(req, '', {}, {
    						ContentType : 'application/json'
    					});
    			return resp;
    }

	}
}]);
