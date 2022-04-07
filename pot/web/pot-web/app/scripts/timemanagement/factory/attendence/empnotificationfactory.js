'use strict';
app.factory('EmpNotificationFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "EmpAttendanceService", "ApproveUserFactory", "GenericAlertService", "NotificationService", function(ngDialog, $q, $filter, blockUI,$timeout, $rootScope, EmpAttendanceService,ApproveUserFactory,GenericAlertService, NotificationService) {
	var dateWisePopUp;
	var notificationservice = {};

	notificationservice.getEmpNotificationDetails = function(searchProject,empAttendReq) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/attendance/empnotification.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				
				$scope.projId  = 	searchProject.projId;
				$scope.projName = searchProject.projName;
				$scope.crewName = empAttendReq.crewLabelKeyTO.code;
				$scope.approverTo={
						"id":null,
						"code":null,
						"name":null
				};
				
				var currMonth = new Date();
				var dayFirst = new Date(empAttendReq.attendenceMonth);
				var months=["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
				var firstDay = dayFirst.getDate()+"-"+months[dayFirst.getMonth()]+"-"+dayFirst.getFullYear();
				
				var date = new Date(dayFirst), y = date.getFullYear(), m = date.getMonth();
				var dayLast = new Date(y, m + 1, 0);
				var lastDay = dayLast.getDate()+"-"+months[dayLast.getMonth()]+"-"+dayLast.getFullYear();
				
				var monthCurrent = months[currMonth.getMonth()]+"-"+currMonth.getFullYear();
				if(empAttendReq.attendenceMonth.toLowerCase() == monthCurrent.toLowerCase()) {
					lastDay = currMonth.getDate()+"-"+months[currMonth.getMonth()]+"-"+currMonth.getFullYear();
				}
				var alreadyRequestedDates = [];
				NotificationService.getAttendenceAddlTimeNotifications({"projId" : searchProject.projId, "crewId" : empAttendReq.crewLabelKeyTO.id, "type": "EMP", "fromDate" : firstDay, "toDate" : lastDay}).then(function(data) {
					
					data.attendenceNotificationsTOs.forEach(function(attendenceNotificationsTO){
						alreadyRequestedDates.push([attendenceNotificationsTO.fromDate, attendenceNotificationsTO.toDate])
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
				});
				console.log("Additional Time Request 1111111111");
				$scope.tableData= {
					"fromDate":null,
					"toDate":null,
					"fDate":firstDay,
					"tDate":lastDay,
					"disableDates": alreadyRequestedDates
				};
				
		$scope.submitNotifications = function(projId) {
			/*angular.forEach($scope.tableData,function(value) {
				var fromDate = new Date($scope.tableData.fromDate);
				var toDate = new Date($scope.tableData.toDate);
				if (fromDate > toDate) {
					GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
					forEach.break();
					return;
				}
			})*/
			if ($scope.tableData.fromDate == null || $scope.tableData.fromDate == undefined || $scope.tableData.fromDate == '') {
				GenericAlertService.alertMessage('From Date required', "Warning");
				return;
			}
			if ($scope.tableData.toDate == null || $scope.tableData.toDate == undefined || $scope.tableData.toDate == '') {
				GenericAlertService.alertMessage('To Date required', "Warning");
				return;
			}
			if ($scope.approverTo.name == null || $scope.approverTo.name == undefined || $scope.approverTo.name == '' || $scope.approverTo.id == null || $scope.approverTo.id == undefined || $scope.approverTo.id == '') {
				GenericAlertService.alertMessage('Approver required', "Warning");
				return;
			}
			var saveReqObj={
				"projId":$scope.projId ,
				"notifyRefId":empAttendReq.crewLabelKeyTO.id,
				"toUserId":$scope.approverTo.id,
				"fromDate":$scope.tableData.fromDate,
				"toDate":$scope.tableData.toDate,
				"crewName":empAttendReq.crewLabelKeyTO.code,
				"crewId" : empAttendReq.crewLabelKeyTO.id,
				"type":'EMP',
				"status" : 1,
				"crewName":empAttendReq.crewLabelKeyTO.code,
				"reqComments":$scope.comments,
				"notificationMsg" : 'Request for Additional Time',
				"notificationStatus" : 'Pending'
				
			};

			var saveReq = {
				"attendenceNotificationsTOs":[saveReqObj],
				"status" : 1,
				"projId" : $scope.projId
			};
        blockUI.start();
			EmpAttendanceService.saveAttendanceNotifications(saveReq).then(function(data) {
				 blockUI.stop();
				$scope.closeThisDialog();
				GenericAlertService.alertMessage('Additional Time details submitted successfully', "Info");
			}, function(error) {
				 blockUI.stop();
				GenericAlertService.alertMessage('Notification request failed to Submit', "Error");
			});
		},
		$scope.getProjUsersOnly = function(approverTo) {
			var getReq = {
				"moduleCode" : "ATNDNCERCRDS",
				"actionCode" : "APPROVE",
				"projId" : $scope.projId,
				"permission" : "ASBUILTRCRD_ATNDNCERCRDS_APPROVE"
			};
			var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
			selectedUser.then(function(data) {
				approverTo.id = data.approverTo.id;
				approverTo.name = data.approverTo.name;
				
			});
		}, $scope.submitTimeSheetDetails = function() {
			if (crewTypeTO.id == 1) {
				$scope.submitCrewTimeSheetDetails();
			} else if (crewTypeTO.id == 2) {
				$scope.submitIndividualTimeSheetDetails();
			}
		}
					
			} ]
		});
		return deferred.promise;
	}
	return notificationservice;
}]);
