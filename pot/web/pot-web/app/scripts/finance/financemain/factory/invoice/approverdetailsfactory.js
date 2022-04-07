'use strict';

app.factory('ApproverDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CompanyService", "PotCommonService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, CompanyService, PotCommonService, GenericAlertService) {
	var approverUserPopup = [];
	var service = {};
	service.getUsers = function(projId) {
		var deferred = $q.defer();
		approverUserPopup = ngDialog.open({
			template : 'views/finance/financemain/invoice/invoiceapprover.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.approverUserList = [];
				$scope.financeUserList = [];
				var addService = {};
				$scope.getUsersByModulePermission = function() {
					var req = {
						"moduleCode" : "INTERNALREQAPPOVAL",
						"actionCode" : "APPROVE",
						"permission" : "PROCURMT_INTRNLSTAGE1APRVL_APPROVE",
						"projId" : projId,
						"status" : 1
					};
					PotCommonService.getProjUsersOnly(req).then(function(data) {
						$scope.approverUserList = data.labelKeyTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting workflow status", 'Error');
					});
				}, $scope.addApproverToInvoice = function(approverUserTO) {
					var returnApproverUserTO = {
						"approverUserTO" : approverUserTO
					};
					deferred.resolve(returnApproverUserTO);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};
	return service;

}]);