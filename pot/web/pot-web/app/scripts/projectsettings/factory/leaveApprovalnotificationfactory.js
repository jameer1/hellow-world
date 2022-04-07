'use strict';
app.factory('LeaveApprNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var procurementNotificationPopup = [];
	var notificationService = {};
	notificationService.leaveApprNotificationDetails = function(projCrewTOs) {
		var deferred = $q.defer();
		procurementNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/leaveApprnotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = projCrewTOs;
				
				$scope.getLeaveNotificationPopup = function(notificationData) {
					var returnPopObj = {
						"projCrewTO" : notificationData,
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}, notificationService.getLeaveApprNotificationPopup = function(req) {
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getLeaveApprNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			projCrewTOs = data.employeeLeaveNotificationsTOs;
			var procurementNotificationPopup = notificationService.leaveApprNotificationDetails(projCrewTOs);
			procurementNotificationPopup.then(function(data) {
				var returnPopObj = {
					"projCrewTO" : data.projCrewTO
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Notification Details", "Error");
		});
		return deferred.promise;
	}
	return notificationService;
}]);