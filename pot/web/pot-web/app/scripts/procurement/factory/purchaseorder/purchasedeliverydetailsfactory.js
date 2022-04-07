'use strict';

app.factory('PurchaseDeliveryDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "PurchaseOrderService", "PreContractInternalService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, blockUI,PurchaseOrderService,PreContractInternalService, GenericAlertService) {
	var service = {};
	service.getDeliveryDetails = function(item,companyMap,selectedPurchaseOrderData) {
		var deferred = $q.defer();
		var resourcePopup;
		var req = {
			'purId':selectedPurchaseOrderData.id,
			'projId':selectedPurchaseOrderData.projId,
			"status":1
		};
		blockUI.start();
		PurchaseOrderService.getMaterialInvoiceDocket(req).then(function(data) {
			blockUI.stop();
			var popupdata = service.openPopup(item,companyMap,selectedPurchaseOrderData,data.labelKeyTOs);
			popupdata.then(function(resultData) {
				deferred.resolve(resultData);
			});
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
		});
		return deferred.promise;
	},
	service.openPopup = function(item,companyMap,selectedPurchaseOrderData,labelKeyTOs) {
		var deferred = $q.defer();
		var resourcePopup = ngDialog.open({
			template : 'views/procurement/purchaseorders/purchasedeliverydocket.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.code = item.itemCode;
				$scope.companyMap = companyMap;
				$scope.companyId = selectedPurchaseOrderData.preContractCmpTO.companyId;
				$scope.purCode =selectedPurchaseOrderData.code;
				$scope.deliveryDetailsData =labelKeyTOs;
				
			} ]
		});
		return deferred.promise;
	};
	return service;

}]);
