'use strict';

app.factory('PreContractApproverFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CompanyService", "PotCommonService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		CompanyService, PotCommonService, GenericAlertService) {
	var approverUserPopup = [];
	var service = {};
	service.getUsersByModulePermission = function(projId) {
		var deferred = $q.defer();
		approverUserPopup = ngDialog.open({
			template : 'views/procurement/pre-contracts/internalApproval/precontractapprover.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [
					'$scope',
					function($scope) {
						$scope.approverUserList = [];
						var addService = {};
						$scope.getUsersByModulePermission = function() {
							var req = {
								"moduleCode" : "INTERNALREQAPPOVAL",
								"actionCode" : "APPROVE",
								"permission" : "PROCURMT_INTRNLSTAGE1APRVL_APPROVE",
								"projId":projId,
								"status":1
							};
							PotCommonService.getProjUsersOnly(req).then(
									function(data) {
										$scope.approverUserList = data.labelKeyTOs;
									},
									function(error) {
										GenericAlertService.alertMessage(
												"Error occured while gettting workflow status",
												'Error');
									});
						}, $scope.addApproverToPrecontract = function(approverUserTO) {
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