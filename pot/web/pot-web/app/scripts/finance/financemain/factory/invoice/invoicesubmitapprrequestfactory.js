'use strict';

app.factory('InvoiceSubmitApprRequestFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ApproverDetailsFactory", "ProjectInvoiceService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,ApproverDetailsFactory,ProjectInvoiceService , GenericAlertService) {
	var service = {};
	service.submitOrApproveInternalRequest = function(projId,selectedInvoiceData,addInvoiceData) {
		var deferred = $q.defer();
		var popupData = ngDialog.open({
			template : 'views/finance/financemain/invoice/invoicereqappruserpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
         $scope.getUsersByModulePermission = function() {
					var approverFactoryPopup = ApproverDetailsFactory.getUsers(projId);
					approverFactoryPopup.then(function(data) {
						$scope.approverUserTO = data.approverUserTO;
						$scope.addInvoiceData = addInvoiceData;
					},
					function(error) {
						GenericAlertService.alertMessage("Error occured while gettting approver users", 'Error');
					});
				},
				
				$scope.saveInvoiceRequestApprDetails = function(addInvoiceData) {
					$scope.addInvoiceData = addInvoiceData;
					$scope.addInvoiceData.purId=selectedInvoiceData.id;
					$scope.addInvoiceData.projId=selectedInvoiceData.projId;
					$scope.addInvoiceData.apprUserId = $scope.approverUserTO.id;
					$scope.addInvoiceData.currentStatus = "Pending Approval";
					$scope.addInvoiceData.comments = $scope.approverUserTO.comments
					var req = {
						"purchaseOrderInvoiceDtlTO" : addInvoiceData,
					}
					ProjectInvoiceService.savePurchaseOrderInvocies(req).then(function(data) {
						GenericAlertService.alertMessageModal("Post invoice details are saved successfully", 'Info');
					}, function(error) {
						GenericAlertService.alertMessage('Post invoice details are failed to Save', 'Error');
					});
				}
				deferred.resolve();

			} ]

		});
		return deferred.promise;
	}
	return service;
}]);