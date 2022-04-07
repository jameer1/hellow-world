'use strict';
app.factory('ProjMaterialTransferNotificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "NotificationService", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, NotificationService,$rootScope, ProjectSettingsService, GenericAlertService) {
	var procurementNotificationPopup = [];
	var notificationService = {};
	notificationService.materialTransferNotificationDetails = function(projCrewTOs,usersMap) {
		var deferred = $q.defer();
		procurementNotificationPopup = ngDialog.open({									
			template : 'views/projectsettings/materialtransfernotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.notifications = projCrewTOs;
				$scope.usersMap=usersMap;
				
				$scope.materialTransferNotificationPopup = function(notificationData) {
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
	}, notificationService.getMaterialTransferNotificationPopup = function(req) {
		var deferred = $q.defer();
		var prjcrewListPromise =NotificationService.getMaterialNotificationsByProjId(req)
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			var usersMap =[];
			projCrewTOs = data.materialNotificationsTOs;
			usersMap = data.usersMap;
			var materialNotificationPopup = notificationService.materialTransferNotificationDetails(projCrewTOs,usersMap);
			materialNotificationPopup.then(function(data) {
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