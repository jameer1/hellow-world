'use strict';
app.factory('MaterialApprovalNotificationFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "MaterialRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, 
														GenericAlertService, MaterialRegisterService) {
	var materialApprovalNotificationPopUp;
	var service = {};
	service.materialApprovalNotificationPopUp = function(materialNotificationsTOs) {
		var deferred = $q.defer();
		materialApprovalNotificationPopUp = ngDialog.open({
			template : 'views/projresources/common/materialapprovalnotificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.materialNotificationList = materialNotificationsTOs;
				$scope.selectRecord = function(record) {
					var returnRecord = {
						"selectedRecord" : record
					};
					deferred.resolve(returnRecord);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	},

	service.getNotificationDetails = function(req) {
		var deferred = $q.defer();
		var notificationDetailsPromise = MaterialRegisterService.getMaterialNotification(req);
		notificationDetailsPromise.then(function(data) {
			
			var materialNotificationPopUp = service.materialApprovalNotificationPopUp(data.materialNotificationsTOs);
			materialNotificationPopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Approval Notifiaction Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Approval Notification Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]);
