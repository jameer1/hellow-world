'use strict';
app.factory('RequestForPlantAdditionalTimeFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "ApproveUserFactory", "PlantRegisterService", "GenericAlertService", function (ngDialog, $q, blockUI, $filter, $timeout, $rootScope, ApproveUserFactory, PlantRegisterService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getAdditionalTimeDetails = function (selectedValue) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template: 'views/projresources/projplantreg/approvalForTransfer/requestforadditionaltime.html',
			closeByDocument: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.selectedValue = selectedValue;
				$scope.searchProject = {};
				$scope.approverTo = {
					"id": null,
					"code": null,
					"name": null
				};

				$scope.getModuleUserDetails = function () {
					var getReq = {
						"moduleCode": "PLANTTRSFR",
						"actionCode": "APPROVE",
						"projId": $scope.selectedValue.toProjId,
						"permission": "PRJ_PRJSTG_PLANTTRSFR_APPROVE"
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
					selectedUser.then(function (data) {
						$scope.approverTo.id = data.approverTo.id;
						$scope.approverTo.name = data.approverTo.name;
					});
				};

				$scope.submitNotifications = function () {
					var saveReqObj = {
						"projId": $scope.selectedValue.fromProjId,
						"apprUserId": $scope.approverTo.id,
						"notifyRefId": 1,
						"status": 1,
						"type": 'Additional Time For Submit',
						"reqComments": $scope.comments,
						"notificationStatus": 'Pending',
						"reqId": 1,
						"forProject": $scope.selectedValue.toProjId,
						"notifyId" : $scope.selectedValue.notifyId
					};
					var saveReq = {
						"plantNotificationsTOs": [saveReqObj],
						"status": 1,
						"projId": $scope.selectedValue.fromProjId
					};
					console.log(saveReq);
					blockUI.start();
					PlantRegisterService.savePlantNotification(saveReq).then(function (data) {
						blockUI.stop();
						$scope.closeThisDialog();
						deferred.resolve('Success');
						GenericAlertService.alertMessage('Additional Time Details are Submitted successfully', "Info");
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Additional Time Details are  failed to Submit', "Error");
					});
				}

			}]
		});
		return deferred.promise;
	}
	return service;
}]);
