'use strict';

app.factory('PurchaseGenerateFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "RFQAppendenciesFactory", "PreContractExternalService", "PurchaseOrderService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, RFQAppendenciesFactory,PreContractExternalService ,PurchaseOrderService, GenericAlertService) {
	var viewBidderFactoryPopUp;
	var service = {};
	service.getPurchaseOrderDetails = function(userProjMap,selectedData) {
		var deferred = $q.defer();
		viewBidderFactoryPopUp = ngDialog.open({
			template : 'views/procurement/purchaseorders/purchasegenerator.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.userProjMap = userProjMap;
				$scope.selectedData = selectedData;
				$scope.getAppendicesDetails = function() {
					var popupDetails = RFQAppendenciesFactory.getPurchaseOrderDetails($scope.selectedData,$scope.userProjMap);
					popupDetails.then(function(data) {
						$scope.userProjMap = userProjMap;
						$scope.selectedData = selectedData;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
					});
				},$scope.singlePOData =[{
					"desc":null,
					"issuedBy" : null,
					"acceptedBy" : null,
					"sign" : null,
					"name" : null,
					"designation" : null,
					"companyRep" : null,
					"vendorRep" : null,
					"vendorName" : null,
					"vendorSign" : null,
					"vendorDesignation" : null,
					"status" : 1
				}];
				$scope.saveGeneratePODetails = function() {
					var savereq = {
						"singlePurchaseOrderTOs" : $scope.singlePOData,
						"status" : 1,
						"projId":$scope.selectedData.projId
						
					};
					blockUI.start();
					PreContractExternalService
							.saveSinglePurchaseOrder(savereq)
							.then(
									function(data) {
										blockUI.stop();
										var succMsg = GenericAlertService
												.alertMessageModal(
														"Single purchase order details saved successfully",
														'Info');
										succMsg
												.then(function(
														popData) {
													var returnPopObj = {
														"singlePurchaseOrderTOs" : data
													};
													deferred
															.resolve(returnPopObj);
												});
									},
									function(error) {
										blockUI.stop();
										GenericAlertService
												.alertMessage(
														'Generate single purchase order details are failed to Save',
														'Error');
									});
				};
			} ]
		});
		return deferred.promise;
	};
	return service;

}]);
