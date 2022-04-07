'use strict';
app.factory('ReleasePaymentFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ApproverDetailsFactory", "FinanceApproverDetailsFactory", "ProjectInvoiceService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ApproverDetailsFactory,FinanceApproverDetailsFactory ,ProjectInvoiceService , GenericAlertService) {
	var service = {};
	service.paymentDetails = function(actionType,editInvoiceDetails, selectedInvoiceData, userProjMap, companyMap, userMap) {
		var deferred = $q.defer();
		var refPopup = ngDialog.open({
			template : 'views/finance/financemain/invoice/releasepaymentpopup.html',
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
				$scope.apprUserId = $scope.addInvoiceData.apprUserId;
				$scope.companyId = selectedInvoiceData.preContractCmpTO.companyId;
				$scope.procureType = selectedInvoiceData.procureType;
				$scope.userMap = userMap;
				$scope.userProjMap = userProjMap;
				$scope.companyMap = companyMap;

				$scope.addInvoiceData = {
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
				$scope.getFinanceUserDetails = function() {
					var approverFactoryPopup = FinanceApproverDetailsFactory.getUsers($scope.projId);
					approverFactoryPopup.then(function(data) {
						$scope.financeApproverUserTO = data.financeApproverUserTO;
						$scope.addInvoiceData.financeUserId = data.financeApproverUserTO.id;
					},
					function(error) {
						GenericAlertService.alertMessage("Error occured while gettting approver users", 'Error');
					});
				}
				
				$scope.applyReleasePayment = function(addInvoiceData) {
					$scope.addInvoiceData.paymentStatus = 'Released';
					var req = {
						"purchaseOrderInvoiceDtlTO" : addInvoiceData
					}
					ProjectInvoiceService.savePurchaseOrderInvocies(req).then(function(data) {
						GenericAlertService.alertMessage("Project invoice details are released successfully", 'Info');
							deferred.resolve(data);
							ngDialog.close();
					}, function(error) {
						GenericAlertService.alertMessage('Post invoice details are failed to release', 'Error');
					});
				},
				$scope.releasePaymentPaid = function(addInvoiceData) {
					$scope.addInvoiceData.paymentStatus = 'Paid';
					$scope.addInvoiceData.currentStatus = 'Paid';
					var req = {
						"purchaseOrderInvoiceDtlTO" : addInvoiceData
					}
					ProjectInvoiceService.savePurchaseOrderInvocies(req).then(function(data) {
						GenericAlertService.alertMessage("Project invoice payment details are saved successfully", 'Info');
							deferred.resolve(data);
							ngDialog.close();
					}, function(error) {
						GenericAlertService.alertMessage('Post invoice payment details are failed to Save', 'Error');
					});
				}

			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
