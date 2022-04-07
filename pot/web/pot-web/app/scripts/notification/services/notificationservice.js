'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjCostCode Service in the potApp.
 */
app.factory('NotificationService', ["Restangular", "$http", "appUrl", function(Restangular, $http, appUrl) {
	return {
		getAttendenceAddlTimeNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getAttendenceAddlTimeNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getAttendanceNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getAttendanceNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getTimeSheetNotifications : function(req) {
			return $http({
				url: appUrl.originurl + "/app/notification/getTimeSheetNotifications",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getTimeSheetNotificationsByProjId : function(req) {
			var notifications = Restangular
					.one("notification/getTimeSheetNotificationsByProjId").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getWorkDiaryNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getWorkDiaryNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getProcureNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getProcurementNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
							
			return notifications;
		},
		getWorkDiaryNotificationsByProjId : function(req) {
			return $http({
				url: appUrl.originurl + "/app/notification/getWorkDiaryNotificationsByProjId",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		notificationStatusOnLoad : function(req) {
			var notificationStatus = Restangular.one("notification/notificationStatusOnLoad").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return notificationStatus;
		},
		getEmpNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getEmpNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getEmpNotificationsByProjId : function(req) {
			var notifications = Restangular
					.one("notification/getEmployeeNotificationsByProjId").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getLeaveApprNotificationsByProjId : function(req) {
			var notifications = Restangular
			.one("notification/getEmployeeLeaveNotificationsByProjId").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return notifications;
		},
		getPlantNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getPlantNotification").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		
		getPlantNotificationsByProjId : function(req) {
			var notifications = Restangular
					.one("notification/getPlantNotificationsByProjId").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getMaterialNotifications : function(req) {
		
			var notifications = Restangular
					.one("notification/getMaterialNotification").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		
		getMaterialNotificationsByProjId : function(req) {
			var notifications = Restangular
					.one("notification/getMaterialNotificationsByProjId").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getProcurementNotificationsByProjId : function(req) {
			var notifications = Restangular
					.one("notification/getProcurementNotificationsByProjId").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getEmpLeaveNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getEmployeeLeaveNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		getEmpLeaveNotifications : function(req) {
			var notifications = Restangular
					.one("notification/getEmployeeLeaveNotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		
		getCountNotification : function(req) {
			var notifications = Restangular
					.one("notification/getCountNotification").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		
		getSOENotifications : function(req) {
			var notifications = Restangular
					.one("notification/getSOENotifications").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		
		getSoeNotificationsByProjId : function(req) {
			var notifications = Restangular
					.one("notification/getSoeNotificationsByProjId").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return notifications;
		},
		
	}
}]);
