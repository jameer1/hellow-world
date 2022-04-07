'use strict';

app.factory('PrecontractSubmitApprRequestFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "PreContractApproverFactory", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService, PreContractApproverFactory, GenericAlertService) {
	var service = {};
	service.submitOrApproveInternalRequest = function (preContractObj, apprvStatus, projId, notificationStatus) {
		var deferred = $q.defer();
		var popupData = ngDialog.open({
			template: 'views/procurement/pre-contracts/internalApproval/precontractinternalrequestapprpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.preContractObj = preContractObj;
				$scope.apprStatus = apprvStatus;
				$scope.saveInternalRequest = function () {
					var validateFalg = true;
					var notifyStatus = null;
					if (apprvStatus == 2) {
						validateFalg = $scope.validateApproverId();
					}
					if (notificationStatus) {
						notifyStatus = 'Pending'
					}
					if (validateFalg) {
						var savereq = {
							"preContractTO": preContractObj,
							"apprvStatus": apprvStatus,
							"projId": preContractObj.projId,
							"preContractId": preContractObj.id,
							"notificationStatus": notifyStatus
						};
						console.log(savereq);
						PreContractInternalService.saveInternalPreContracts(savereq).then(function (data) {
							if (apprvStatus == 2) {
								GenericAlertService.alertMessage("Pre-contract released  for approval successfully", "Info");
							} else if (apprvStatus == 3) {
								GenericAlertService.alertMessage("Precontract send back to requestor successfully ", "Info");
							} else if (apprvStatus == 4) {
								GenericAlertService.alertMessage("Precontract put on-hold successfully ", "Info");
							} else if (apprvStatus == 5) {
								GenericAlertService.alertMessage("Precontract approved successfully ", "Info");
							} else if (apprvStatus == 6) {
								GenericAlertService.alertMessage("Pre-contract rejected  for approval successfully", "Info");
							}
							deferred.resolve(data);
							ngDialog.close()
						},
							function (error) {
								GenericAlertService.alertMessage("Error occured,Please try again", "Error");
							});

					}
				}, $scope.validateApproverId = function () {
					if ($scope.preContractObj.preContractReqApprTO.apprUserLabelkeyTO.name != null && $scope.preContractObj.preContractReqApprTO.apprUserLabelkeyTO.id > 0) {
						return true;
					} else {
						GenericAlertService.alertMessage("Please select approver  user", "Warning");
						return false;
					}
				}, $scope.getUsersByModulePermission = function () {
					var approverFactoryPopup = PreContractApproverFactory.getUsersByModulePermission(projId);
					approverFactoryPopup.then(function (data) {
						$scope.preContractObj.preContractReqApprTO.apprUserLabelkeyTO = data.approverUserTO;
					},

						function (error) {
							GenericAlertService.alertMessage("Error occured while gettting approver users", 'Error');
						});
				}

			}]

		});
		return deferred.promise;
	}
	return service;
}]);