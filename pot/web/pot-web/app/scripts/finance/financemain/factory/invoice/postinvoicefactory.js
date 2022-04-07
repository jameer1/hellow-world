'use strict';
app.factory('PostInvoiceFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "CostCodesFactory", "ViewInvoiceDetailsFactory", "InvoiceSubmitApprRequestFactory", "ProjectInvoiceService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, CostCodesFactory, ViewInvoiceDetailsFactory, InvoiceSubmitApprRequestFactory, ProjectInvoiceService) {
	var service = {};
	service.addPostInvoiceDetails = function(actionType, editInvoiceDetails, selectedInvoiceData, userProjMap, companyMap, userMap) {
		var deferred = $q.defer();
		var popUp = ngDialog.open({
			template : 'views/finance/financemain/invoice/postinvoicepopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.addInvoiceData = {};
				$scope.code = selectedInvoiceData.code;
				$scope.projId = selectedInvoiceData.projId;
				$scope.desc = selectedInvoiceData.preContractCmpTO.preContractTO.desc;
				$scope.paymentInDays = selectedInvoiceData.paymentInDays;
				$scope.reqUserId = selectedInvoiceData.preContractCmpTO.preContractTO.preContractReqApprTO.reqUserLabelkeyTO.id;
				$scope.apprUserId = selectedInvoiceData.apprUserId;
				$scope.companyId = selectedInvoiceData.preContractCmpTO.companyId;
				$scope.procureType = selectedInvoiceData.procureType;
				$scope.userMap = userMap;
				$scope.userProjMap = userProjMap;
				$scope.companyMap = companyMap;

				$scope.viewDeliveryDocketsDetails = function(invoice) {
					var popupDetails = ViewInvoiceDetailsFactory.viewInvoiceDocketDetails($scope.procureType, selectedInvoiceData);
					popupDetails.then(function(data) {
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting invoice dockets", 'Info');
					});
				}, $scope.addCostCodes = function() {
					var popup = CostCodesFactory.getCostDetails($scope.projId);
					popup.then(function(data) {
						$scope.addInvoiceData.costId = data.id;
						$scope.costCode = data.code;
						$scope.name = data.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while creating post invoice details", 'Info');
					})
				}, $scope.addInvoiceData = {
					"id" : null,
					"invoiceDate" : null,
					"receivedDate" : null,
					"paymentInDays" : $scope.paymentInDays,
					"payDueDate" : null,
					"proposedDueDate" : null,
					"netAmount" : null,
					"taxAmount" : null,
					"retainedNetAmount" : null,
					"retainedTaxAmount" : null,
					"costId" : null,
					"split" : null,
					"costAmount" : null,
					"bankName" : null,
					"accName" : null,
					"bankCode" : null,
					"accNum" : null,
					"accDtlsVerified" : null,
					"purId" : selectedInvoiceData.id,
					"projId" : selectedInvoiceData.projId,
					"status" : 1
				}
				if (actionType === 'Edit') {
					$scope.addInvoiceData = angular.copy(editInvoiceDetails);
				}
				$scope.saveInvoiceDetails = function(addInvoiceData) {
					var req = {
						"purchaseOrderInvoiceDtlTO" : addInvoiceData
					}
					ProjectInvoiceService.savePurchaseOrderInvocies(req).then(function(data) {
						deferred.resolve(data);
						ngDialog.close();
						GenericAlertService.alertMessage("Project invoice details are saved successfully", 'Info');
					}, function(error) {
						GenericAlertService.alertMessage('Post invoice details are failed to Save', 'Error');
					});
				}, $scope.submitInvoiceDetails = function(addInvoiceData) {
					var popUp = InvoiceSubmitApprRequestFactory.submitOrApproveInternalRequest($scope.projId, selectedInvoiceData, addInvoiceData);
					popUp.then(function(data) {

					});
				}

			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
