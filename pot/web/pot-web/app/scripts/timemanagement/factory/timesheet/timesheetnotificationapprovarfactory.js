'use strict';
app.factory('TimeSheetNotificationFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "ProjectSettingsService", "$rootScope", "ApproveUserFactory", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, ProjectSettingsService,$rootScope,ApproveUserFactory ,TimeSheetService,GenericAlertService) {
	var dateWisePopUp;
	var notificationservice = {};

	notificationservice.getTimeSheetNotificationDetails = function(timeSheetSearchReq,crewTypeId) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetnotification.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
					$scope.projId = timeSheetSearchReq.projectLabelKeyTO.projId;
					$scope.projName = timeSheetSearchReq.projectLabelKeyTO.projName;
					$scope.crewName = timeSheetSearchReq.crewLabelKeyTO.code;
					$scope.userName =timeSheetSearchReq.timesheetUserLabelKeyTO.name ;
					$scope.weekCommenceDay =timeSheetSearchReq.weekCommenceDay;
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
			
			var saveReqObj={
				"projId":timeSheetSearchReq.projectLabelKeyTO.projId ,
				"notifyRefId":crewTypeId,
				"toUserId":$scope.approverTo.id,
				"fromDate":$scope.tableData.weekStartDate,
				"toDate":$scope.tableData.weekEndDate,
				"weeakCommencingDay":$scope.weekCommenceDay,
				"type":'Approval',
				"timeSheetNumber":timeSheetSearchReq.timeSheetLabelKeyTo.code,
				"status" : 1,
				"reqComments":$scope.comments,
				"notificationStatus" : 'Pending',
				"notificationMsg":'Request for Additional Time'
			};
			
			var saveReq = {
				"timeSheetNotificationsTOs":[saveReqObj],
				"status" : 1,
				"projId" : timeSheetSearchReq.projectLabelKeyTO.projId
			};
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
					
					
			} ]
		});
		return deferred.promise;
	}
	return notificationservice;
}]);
