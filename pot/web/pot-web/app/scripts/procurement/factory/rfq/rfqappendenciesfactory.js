'use strict';

app.factory('RFQAppendenciesFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "RFQService", "PurchaseOrderDetailsFactory", "PurchaseTermsConditionsFactory", "PurchaseGenerateSchItemsFactory", "PreContractExternalFactory", "PreContractExternalService", "PurchaseOrderService", "GenericAlertService", "ReferenceDocumentsPopupFactory", "DocumentService", function (ngDialog, $q, $filter, $timeout, $rootScope, RFQService, PurchaseOrderDetailsFactory, PurchaseTermsConditionsFactory,
	PurchaseGenerateSchItemsFactory, PreContractExternalFactory, PreContractExternalService, PurchaseOrderService, GenericAlertService, ReferenceDocumentsPopupFactory,
	DocumentService) {
	var poAppendicePopup;
	var service = {};
	service.getPurchaseOrderDetails = function (selectedData, userProjMap, precontractDataWithScheduleItems) {
		var deferred = $q.defer();
		poAppendicePopup = ngDialog.open({
			template: 'views/procurement/RFQ/rfqappendencies.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {

				$scope.selectedData = selectedData;
				$scope.userProjMap = userProjMap;
				$scope.getPurchaseSchduleItems = function () {
					var popupDetails = PurchaseGenerateSchItemsFactory.getPurchaseOrderSchItemDetails(precontractDataWithScheduleItems, $scope.selectedData);
					popupDetails.then(function (data) {
						$scope.companyMap = data.companyMap;
						$scope.usersMap = data.usersMap;
						$scope.purchaseOrdersList = data.purchaseOrderTOs;
						alert(JSON.stringify(data));
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
					});

				};
				/*
				$scope.getPurchaseOrderDetailsByCmpId = function () {
					var purchaseOrderDefer = $q.defer();
					var onLoadReq = {
						"preContractId": $scope.selectedData.id,
						"contractCmpId": 233,
						"projId": $scope.selectedData.projId,
						"status": 1
					};
					PurchaseOrderService.getPurchaseOrderDetails(onLoadReq).then(function (data) {
						var returnPreContractObj = {
							"preContractObj": angular.copy(data)
						};
						purchaseOrderDefer.resolve(returnPreContractObj);
					});
					return purchaseOrderDefer.promise;

				}; */

				$scope.getPurchaseOrderSearch = function () {
					var purchaseReq = {
						"projIds": [$scope.selectedData.projId],
						"status": 1
					};
					PurchaseOrderService.getPurchaseOrders(purchaseReq).then(function (data) {
						$scope.companyMap = data.companyMap;
						$scope.purchaseOrdersList = data.purchaseOrderTOs;
						if ($scope.purchaseOrdersList.length <= 0) {
							GenericAlertService.alertMessage("Purchase orders are not available for given search criteria", 'Warning');
						}
					},
						function (error) {
							GenericAlertService.alertMessage("Error occured while getting Purchase Orders", 'Error');

						});

				};
				$scope.getPreContractDocuments = function () {
					var deferred = $q.defer();
					var req = {
						"projId": $scope.selectedData.projId,
						"contractId": $scope.selectedData.id,
						"status": 1
					};
					var serviceData = RFQService.getPreContratDocs(req);
					serviceData.then(function (data) {
						$scope.preContractDocsTOs = data.preContractDocsTOs;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting pre-contract documents",
							'Error');
					});
					return deferred.promise;
				};
				$scope.downloadDocs = function (projdDocFileId) {
					DocumentService.downloadDocs(projdDocFileId);
				};
				$scope.getTermsConditions = function () {
					var popupDetails = PurchaseTermsConditionsFactory.getPurchaseOrderDetails($scope.selectedData);
					popupDetails.then(function (data) {
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while getting purchase order terms & conditions", 'Error');
					})
				};

				$scope.referenceDocumentsPopup = function () {
					$rootScope.referenceDocumentMode = "Appendices";
					var refreq = ReferenceDocumentsPopupFactory.referenceDocumentDetails($scope.selectedData, true);
					refreq.then(function (data) {
						$scope.preContractDocsTOs = data.preContractDocsTOs;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while getting pre-contract documents", 'Info');
					})
				};

				/*$scope.downloadDocs = function (fileId,file_name) {
					//DocumentService.downloadDocs(fileId);
					console.log("downloadDocs function");
					console.log(fileId);
					let req = {
						"recordId" : fileId,
						"category" : "Stage2 Approval-Generate PO-Appendices",
						"fileName" : file_name
					}
					EmpRegisterService.downloadRegisterDocs(req);					
				};*/

			}]
		});
		return deferred.promise;
	};
	return service;

}]);
