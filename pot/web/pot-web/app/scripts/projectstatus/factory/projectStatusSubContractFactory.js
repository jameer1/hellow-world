'use strict';
app.factory('ProjStatusSubContractFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantRegisterService) {
	var subContractPurchaseOrderPopUp;
	var service = {};
	service.subContractPurchaseOrderPopUp = function(subContractPurchaseOrderDetails) {
		var deferred = $q.defer();
		subContractPurchaseOrderPopUp = ngDialog.open({
			template : 'views/projectstatus/subcontractpurchaseorderlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {

				$scope.subContractPurchaseOrderDetails = subContractPurchaseOrderDetails;
				$scope.subContractPurchaseOrderPopUp = function(purchaseOrderTO) {
					var returnPurchaseOrderTO = {
						"purchaseOrderTO" : purchaseOrderTO
					};
					deferred.resolve(returnPurchaseOrderTO);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	},

	service.getProjectPurchaseOrders = function(req) {
		var deferred = $q.defer();
		var projectPODetailsPromise = PlantRegisterService.getProjectPurchaseOrders(req);
		projectPODetailsPromise.then(function(data) {
			if (data.purchaseOrderTOs) {
				var purchaseOrderDetails = [];
				purchaseOrderDetails = data.purchaseOrderTOs;
				var projectPODetailsPopup = service.subContractPurchaseOrderPopUp(purchaseOrderDetails);
				projectPODetailsPopup.then(function(data) {
					var returnPopObj = {
						"purchaseOrderTO" : data.purchaseOrderTO
					};
					deferred.resolve(returnPopObj);
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
				})
			} else {
				GenericAlertService.alertMessage("Purchase orders are not available for selected project", "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]);
