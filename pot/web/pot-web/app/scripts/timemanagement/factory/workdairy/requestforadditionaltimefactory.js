'use strict';
app.factory('RequestForAdditionalTimeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ApproveUserFactory", "WorkDiaryService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, ApproveUserFactory, WorkDiaryService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getAdditionalTimeDetails = function (workDairySearchReq) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template: 'views/timemanagement/workdairy/createworkdairy/requestforadditionaltime.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.projId = workDairySearchReq.projectLabelKeyTO.projId;
				$scope.projName = workDairySearchReq.projectLabelKeyTO.projName;
				$scope.crewname = workDairySearchReq.crewLabelKeyTO.code;
				$scope.shiftType = workDairySearchReq.shiftLabelKeyTO.code;
				$scope.workDairyId = workDairySearchReq.code;
				$scope.type = workDairySearchReq.type;

				$scope.crewLabelKeyTO = {
					"id": null,
					"code": null,
					"name": null

				},

					$scope.approverTo = {
						"id": null,
						"code": null,
						"name": null
					};
				$scope.submitNotifications = function (projId) {
					angular.forEach($scope.tableData, function (value) {
						var fromDate =new Date($scope.tableData.fromDate);
						if(fromDate>new Date()){
							GenericAlertService.alertMessage('From Date must be grater than or equal to today date', 'Warning');
							forEach.break();
							return;
							
						}
						var toDate = new Date($scope.tableData.toDate);
						if (fromDate > toDate) {
							GenericAlertService.alertMessage('From Date must be less than To Date', 'Warning');
							forEach.break();
							return;
						}
					})
					var saveReqObj = {
						"projId": workDairySearchReq.projectLabelKeyTO.projId,
						"toUserId": $scope.approverTo.id,
						"notifyRefId": workDairySearchReq.crewLabelKeyTO.id,
						"fromDate": $scope.tableData.fromDate,
						"toDate": $scope.tableData.toDate,
						"crewName": workDairySearchReq.crewLabelKeyTO.code,
						"crewId": workDairySearchReq.crewLabelKeyTO.id,
						"shiftId": workDairySearchReq.shiftLabelKeyTO.id,
						"workDairyNumber": workDairySearchReq.code,
						"wdmId": workDairySearchReq.workDairyId,
						"status": 1,
						"notificationMsg": workDairySearchReq.notificationMsg,
						"originalType": true,
						"type": workDairySearchReq.type,
						"reqComments": $scope.comments
					};
					var saveReq = {
						"workDairyNotificationsTOs": [saveReqObj],
						"status": 1,
						"projId": workDairySearchReq.projectLabelKeyTO.projId
					};
					console.log(workDairySearchReq);
					WorkDiaryService.saveWorkDairyNotifications(saveReq).then(function (data) {
						$scope.closeThisDialog();
						GenericAlertService.alertMessage('Additional Time Details are Submitted successfully', "Info");
					}, function (error) {
						GenericAlertService.alertMessage('Additional Time Details failed to Submit', "Error");
					});
				}, $scope.getModuleUserDetails = function (approverTo) {
					var getReq = {
						"moduleCode": "WRKDIRY",
						"actionCode": "APPROVE",
						"projId": workDairySearchReq.projectLabelKeyTO.projId,
						"permission": "ASBUILTRCRD_WRKDIRY_APPROVE"
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);

					selectedUser.then(function (data) {
						approverTo.id = data.approverTo.id;
						approverTo.name = data.approverTo.name;
					});
				}

			}]
		});
		return deferred.promise;
	}
	return service;
}]);
