'use strict';
app.factory('ProjectSoeNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var soeNotificationPopup = [];
	var notificationService = {};
	notificationService.soeNotificationDetails = function(soeNotificationsTOs) {
		var deferred = $q.defer();
		soeNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/soenotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = soeNotificationsTOs;
				console.log($scope.notifications);
				$scope.soeNotificationPopup = function(notificationData) {
				console.log(notificationData);
					var returnPopObj = {
						"soeNotificationsTO" : notificationData
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}, notificationService.getSoeNotificationPopup = function(req) {
	    console.log(req);
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getSoeNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
		console.log(data);
			var soeNotificationsTOs = [];
			soeNotificationsTOs = data.soeNotificationsTOs;
			var soeNotificationPopup = notificationService.soeNotificationDetails(soeNotificationsTOs);
			soeNotificationPopup.then(function(data) {
				var returnPopObj = {
					"soeNotificationsTO" : data.soeNotificationsTO,
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