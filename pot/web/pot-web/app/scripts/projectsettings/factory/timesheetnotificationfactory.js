'use strict';
app.factory('ProjectTimeSheetNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var timeSheetNotificationPopup = [];
	var notificationService = {};
	notificationService.timeSheetNotificationDetails = function(projCrewTOs,usersMap ) {
		var deferred = $q.defer();
		timeSheetNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/timesheetnotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = projCrewTOs;
				$scope.usersMap=usersMap;
				$scope.timeSheetNotificationPopup = function(notificationData) {
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
	}, notificationService.getTimeSheetNotificationPopup = function(req) {
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getTimeSheetNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			var usersMap=[];
			projCrewTOs = data.timeSheetNotificationsTOs;
			usersMap = data.usersMap;
			var timeSheetNotificationPopup = notificationService.timeSheetNotificationDetails(projCrewTOs,usersMap);
			timeSheetNotificationPopup.then(function(data) {
				var returnPopObj = {
					"projCrewTO" : data.projCrewTO,
					"usersMap":data.usersMap
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Crew List Details", "Error");
		});
		return deferred.promise;
	}
	return notificationService;
}]);