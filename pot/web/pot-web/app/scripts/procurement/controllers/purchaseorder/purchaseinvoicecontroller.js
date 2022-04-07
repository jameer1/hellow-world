'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("poinvoice", {
		url : '/poinvoice',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/purchaseinvoicedetails.html',
			}
		}
	})
}]).controller("PurchaseInvoiceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "ProjectInvoiceService", "GenericAlertService", function($rootScope, $scope, $q, $state, ngDialog,ProjectInvoiceService, GenericAlertService) {
	
	$scope.getInvoiceDetails = function() {
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
		var req = {
			"purchaseId" : $rootScope.selectedPurchaseOrderData.id,
			"status" : 1
		};
		ProjectInvoiceService.getInvoiceWisePaymentDetails(req).then(function(data) {
			$scope.purchaseOrderInvoiceDtlTOs = data.purchaseOrderInvoiceDtlTOs;
			$scope.companyMap = data.companyMap;
			$scope.code = $rootScope.selectedPurchaseOrderData.code;
			$scope.desc = $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.desc;
			$scope.companyId = $rootScope.selectedPurchaseOrderData.preContractCmpTO.companyId;
		});
	}
	
}]);