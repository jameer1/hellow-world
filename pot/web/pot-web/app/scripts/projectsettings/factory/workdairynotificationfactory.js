'use strict';
app.factory('ProjectWorkDairyNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var workDiaryNotificationPopup = [];
	var notificationService = {};
	notificationService.workDairyNotificationDetails = function(projCrewTOs,usersMap) {
		var deferred = $q.defer();
		workDiaryNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/workdairynotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = projCrewTOs;
				$scope.usersMap=usersMap;
				
				$scope.workDiaryNotificationPopup = function(notificationData) {
					var returnPopObj = {
						"projCrewTO" : notificationData,
						"usersMap":usersMap
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}, notificationService.getWorkDairyNotificationPopup = function(req) {
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getWorkDiaryNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			var usersMap =[];
			projCrewTOs = data.workDairyNotificationsTOs;
			usersMap = data.usersMap;
			var workDiaryNotificationPopup = notificationService.workDairyNotificationDetails(projCrewTOs,usersMap);
			workDiaryNotificationPopup.then(function(data) {
				var returnPopObj = {
					"projCrewTO" : data.projCrewTO,
					"usersMap":data.usersMap
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