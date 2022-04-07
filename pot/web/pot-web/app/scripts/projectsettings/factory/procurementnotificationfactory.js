'use strict';
app.factory('ProjectProcurementNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var procurementNotificationPopup = [];
	var notificationService = {};
	notificationService.procurementNotificationDetails = function(projCrewTOs,usersMap) {
		var deferred = $q.defer();
		procurementNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/procurementnotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = projCrewTOs;
				$scope.usersMap=usersMap;
				
				$scope.procurementNotificationPopup = function(notificationData) {
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
	}, notificationService.getProcurementNotificationPopup = function(req) {
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getProcurementNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			var usersMap =[];
			projCrewTOs = data.procurementNotificationsTOs;
			usersMap = data.usersMap;
			var procurementNotificationPopup = notificationService.procurementNotificationDetails(projCrewTOs,usersMap);
			procurementNotificationPopup.then(function(data) {
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