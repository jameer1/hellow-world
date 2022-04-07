'use strict';
app.factory('PlantNotificationFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "ApproveUserFactory", "EmpAttendanceService", "GenericAlertService", "NotificationService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, ApproveUserFactory, EmpAttendanceService,GenericAlertService, NotificationService) {
	var notificationPopUp;
	var notificationservice = {};

	notificationservice.getPlantNotificationDetails = function(searchProject,plantAttendReq) {
		var deferred = $q.defer();
		notificationPopUp = ngDialog.open({
			template : 'views/timemanagement/attendance/plantnotification.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.projId  = 	searchProject.projId;
				$scope.projName = searchProject.projName;
				$scope.crewName = plantAttendReq.crewLabelKeyTO.code;
				$scope.approverTo={
						"id":null,
						"code":null,
						"name":null
				};
				var currMonth = new Date();
				var dayFirst = new Date(plantAttendReq.attendenceMonth);
				var months=["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
				var firstDay = dayFirst.getDate()+"-"+months[dayFirst.getMonth()]+"-"+dayFirst.getFullYear();
				
				var date = new Date(dayFirst), y = date.getFullYear(), m = date.getMonth();
				var dayLast = new Date(y, m + 1, 0);
				var lastDay = dayLast.getDate()+"-"+months[dayLast.getMonth()]+"-"+dayLast.getFullYear();
				
				var monthCurrent = months[currMonth.getMonth()]+"-"+currMonth.getFullYear();
				if(plantAttendReq.attendenceMonth.toLowerCase() == monthCurrent.toLowerCase()) {
					lastDay = currMonth.getDate()+"-"+months[currMonth.getMonth()]+"-"+currMonth.getFullYear();
				}
				
				var alreadyRequestedDates = [];
				NotificationService.getAttendenceAddlTimeNotifications({"projId" : searchProject.projId, "crewId" : plantAttendReq.crewLabelKeyTO.id, "type": "PLANT", "fromDate" : firstDay, "toDate" : lastDay}).then(function(data) {
					data.attendenceNotificationsTOs.forEach(function(attendenceNotificationsTO){
						alreadyRequestedDates.push([attendenceNotificationsTO.fromDate, attendenceNotificationsTO.toDate])
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
				});
				$scope.tableData= {
					"fromDate":null,
					"toDate":null,
					"fDate":firstDay,
					"tDate":lastDay,
					"disableDates": alreadyRequestedDates
				};
		$scope.submitNotifications = function(projId) {
			if ($scope.fromDate == null || $scope.fromDate == undefined || $scope.fromDate == '') {
				GenericAlertService.alertMessage('From Date required', "Warning");
				return;
			}
			if ($scope.toDate == null || $scope.toDate == undefined || $scope.toDate == '') {
				GenericAlertService.alertMessage('To Date required', "Warning");
				return;
			}
			if ($scope.approverTo.name == null || $scope.approverTo.name == undefined || $scope.approverTo.name == '' || $scope.approverTo.id == null || $scope.approverTo.id == undefined || $scope.approverTo.id == '') {
				GenericAlertService.alertMessage('Approver required', "Warning");
				return;
			}
			var saveReqObj={
				"projId":$scope.projId ,
				"notifyRefId":plantAttendReq.crewLabelKeyTO.id,
				"toUserId":$scope.approverTo.id,
				"fromDate":$scope.fromDate,
				"toDate":$scope.toDate,
				"crewName":plantAttendReq.crewLabelKeyTO.code,		
				"type":'PLANT',
				"status" : 1,
				"reqComments":$scope.comments,
				"crewId" : plantAttendReq.crewLabelKeyTO.id,
				"notificationMsg" : 'Request for Additional Time',
				"notificationStatus" : 'Pending'
			};
			var saveReq = {
				"attendenceNotificationsTOs":[saveReqObj],
				"status" : 1,
				"projId" : projId
			};
			blockUI.start();
			EmpAttendanceService.saveAttendanceNotifications(saveReq).then(function(data) {
				blockUI.stop();
				$scope.closeThisDialog();
				GenericAlertService.alertMessage('Additional Time details submitted successfully', "Info");
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage('notification Details are  failed to Submit', "Error");
			});
		},$scope.getModuleUserDetails = function(approverTo) {
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
		}
					
			} ]
		});
		return deferred.promise;
	}
	return notificationservice;
}]);
