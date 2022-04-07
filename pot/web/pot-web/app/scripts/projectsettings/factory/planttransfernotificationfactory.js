'use strict';
app.factory('ProjPlantTransferNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var plantNotificationPopup = [];
	var notificationService = {};
	notificationService.plantTransferNotificationDetails = function(projCrewTOs,usersMap) {
		var deferred = $q.defer();
		plantNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/planttransfernotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = projCrewTOs;
				$scope.usersMap=usersMap;
				
				$scope.plantTransferNotificationPopup = function(notificationData) {
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
	}, notificationService.getPlantTransferNotificationPopup = function(req) {
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getPlantNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			var usersMap =[];
			projCrewTOs = data.plantNotificationsTOs;
			usersMap = data.usersMap;
			var plantNotificationPopup = notificationService.plantTransferNotificationDetails(projCrewTOs,usersMap);
			plantNotificationPopup.then(function(data) {
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