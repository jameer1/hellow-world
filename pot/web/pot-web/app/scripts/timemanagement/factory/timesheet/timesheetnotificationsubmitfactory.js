'use strict';
app.factory('TimeSheetNotificationFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "ProjectSettingsService", "$rootScope", "ApproveUserFactory", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, ProjectSettingsService,$rootScope,ApproveUserFactory ,TimeSheetService,GenericAlertService) {
	var dateWisePopUp;
	var notificationservice = {};

	notificationservice.getTimeSheetNotificationDetails = function(timeSheetSearchReq,crewTypeId) {
		console.log(timeSheetSearchReq);
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetnotification.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				
				$scope.projId = timeSheetSearchReq.projectLabelKeyTO.projId;
				$scope.projName = timeSheetSearchReq.projectLabelKeyTO.projName;
				if(crewTypeId == 1) {
					$scope.crewName = timeSheetSearchReq.crewLabelKeyTO.code;
				} else if (crewTypeId == 2) {
					$scope.userName = timeSheetSearchReq.timesheetUserLabelKeyTO.name ;
				}
				$scope.weekCommenceDay =timeSheetSearchReq.weekCommenceDay;
				$scope.commencingDay = $scope.weekCommenceDay.substring(4,15);
				$scope.crewTypeId = crewTypeId;
				$scope.timeSheetCode = timeSheetSearchReq.timeSheetLabelKeyTo.code;
				$scope.approverTo={
						"id":null,
						"code":null,
						"name":null
				};
				
				$scope.submitNotifications = function(projId) {
					angular.forEach($scope.tableData,function(value) {
						var fromDate = new Date($scope.tableData.weekStartDate);
						var toDate = new Date($scope.tableData.weekEndDate);
						if (fromDate > toDate) {
							GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
							forEach.break();
							return;
						}
					})
					if (crewTypeId == 1) {
						var saveReqObj={
							"projId":timeSheetSearchReq.projectLabelKeyTO.projId ,
							"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
							"notifyRefId":crewTypeId,
							"toUserId":$scope.approverTo.id,
							"fromDate":$scope.tableData.weekStartDate,
							"toDate":$scope.tableData.weekEndDate,
							"weeakCommencingDay":$scope.commencingDay,
							"type":'Submission',
							"timeSheetNumber":timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"status" : 1,
							"reqComments":$scope.comments,
							"notificationStatus" : 'Pending',
							"notificationMsg":'Request for Additional Time'
						};
					} else if (crewTypeId == 2) {
						var saveReqObj={
							"projId":timeSheetSearchReq.projectLabelKeyTO.projId ,
							"id" : timeSheetSearchReq.timesheetUserLabelKeyTO.id,
							"notifyRefId":crewTypeId,
							"toUserId":$scope.approverTo.id,
							"fromDate":$scope.tableData.weekStartDate,
							"toDate":$scope.tableData.weekEndDate,
							"weeakCommencingDay":$scope.commencingDay,
							"type":'Submission',
							"timeSheetNumber":timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"status" : 1,
							"reqComments":$scope.comments,
							"notificationStatus" : 'Pending',
							"notificationMsg":'Request for Additional Time'
						};
					}
					
					console.log(saveReqObj);
					var saveReq = {
						"timeSheetNotificationsTOs":[saveReqObj],
						"status" : 1,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId
					};
					console.log(saveReq);
					blockUI.start();
					TimeSheetService.saveTimeSheetNotifications(saveReq).then(function(data) {
						blockUI.stop();
						$scope.closeThisDialog();
						GenericAlertService.alertMessage('Notification Details are Submitted successfully', "Info");
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Notification Details are  failed to Submit', "Error");
					});
				},$scope.getModuleUserDetails = function(approverTo) {
					var getReq = {
						"moduleCode" : "CREATTIMESHET",
						"actionCode" : "APPROVE",
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"permission" : "ASBUILTRCRD_TIMSHET_CREATTIMESHET_APPROVE"
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
	
					selectedUser.then(function(data) {
						approverTo.id = data.approverTo.id;
						approverTo.name = data.approverTo.name;
					});
				}
			}]
		});
		return deferred.promise;
	}
	return notificationservice;
}]);
