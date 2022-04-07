'use strict';

app.factory('PurchaseTermsConditionsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "PurchaseOrderService", "GenericAlertService", "DocumentService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, PurchaseOrderService, GenericAlertService, DocumentService) {
	var service = {};
	service.getPurchaseOrderDetails = function (selectedData) {
		var deferred = $q.defer();
		//service.userProjMap = userProjMap;
		var popUp = ngDialog.open({
			template: 'views/procurement/pre-contracts/externalApproval/purchasetermsconditions.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {

				$scope.vendorName = selectedData.vendorName;
				$scope.clientName = selectedData.clientName;
				$scope.projId = selectedData.projId;
				$scope.subContractId = selectedData.code;
				$scope.subContractDesc = selectedData.desc;
				$scope.termsAndConditionsObj = null;

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

				$scope.addTermsAndConditionsDocs = function (selectedFile) {
					var fileNotFound = true;
					for (var index = 0; index < $scope.termsAndConditionsObj.termsAndConditionsFiles.length; index++) {
						if (selectedFile.code === $scope.termsAndConditionsObj.termsAndConditionsFiles[index].code) {
							fileNotFound = false;
						}
					}
					if (fileNotFound) {
						selectedFile.isProjDocFile = true;
						$scope.termsAndConditionsObj.termsAndConditionsFiles.push(selectedFile);
					} else {
						GenericAlertService.alertMessage("File already exists", 'Warning');
					}
				};

				$scope.saveTermsAndConditions = function () {
					// validate data before save, if no data present then don't save
					if (!$scope.termsAndConditionsObj.id && !$scope.termsAndConditionsObj.termsAndConditionsText
						&& ($scope.termsAndConditionsObj.termsAndConditionsFiles && !$scope.termsAndConditionsObj.termsAndConditionsFiles.length)) {
						GenericAlertService.alertMessage("Please Enter text or select documents before saving", 'Warning');
						return;
					}
					var termsAndConditionsTO = {
						"id": $scope.termsAndConditionsObj.id,
						"projDocFileIds": [],
						"deletedTermsAndConditionsFileIds": [],
						"termsAndConditionsFiles": [],
						"conditionsText": $scope.termsAndConditionsObj.termsAndConditionsText,
						"preContractId": selectedData.id,
						"poId": selectedData.poId
					};

					angular.forEach($scope.termsAndConditionsObj.termsAndConditionsFiles, function (doc, key) {
						if (doc.isProjDocFile) {
							termsAndConditionsTO.projDocFileIds.push(doc.id);
						}
					});

					var req = {
						"termsAndConditionsTO": termsAndConditionsTO
					}
					PurchaseOrderService.saveTermsAndConditions(req).then(function (data) {
						extractTermsAndConditions(data);
						var succMsg = GenericAlertService.alertMessageModal("Terms and conditions are saved successfully", 'Info');
						succMsg.then(function () {
							$scope.closeThisDialog(popUp);
						});
					}, function (error) {
						GenericAlertService.alertMessage("Terms and conditions are failed to save", 'Error');

					});

				};

				$scope.downloadDocs = function (projdDocFileId) {
					DocumentService.downloadDocs(projdDocFileId);
				};

				function getTermsAndConditions() {
					var req = {
						"poId": selectedData.poId,
						"preContractId": selectedData.id,
					};
					PurchaseOrderService.getTermsAndConditions(req).then(function (data) {
						extractTermsAndConditions(data);
					}, function (error) {
						GenericAlertService.alertMessage("Error while fetching Terms and Conditions", 'Error');

					});

				};

				function extractTermsAndConditions(data) {
					if (data.termsAndConditionsTOs && data.termsAndConditionsTOs.length) {
						var termsAndCondtions = data.termsAndConditionsTOs[0];
						$scope.termsAndConditionsObj.termsAndConditionsText = termsAndCondtions.conditionsText;
						$scope.termsAndConditionsObj.termsAndConditionsFiles = termsAndCondtions.termsAndConditionsFiles;
						$scope.termsAndConditionsObj.id = termsAndCondtions.id;
					} else {
						initTermsAndConditionsObj();
					}
				}

				function initTermsAndConditionsObj() {
					$scope.termsAndConditionsObj = { "termsAndConditionsFiles": [], id: null, termsAndConditionsText: "" };
				}

				initTermsAndConditionsObj();
				getTermsAndConditions();


			}]
		});
		return deferred.promise;
	};
	return service;

}]);
