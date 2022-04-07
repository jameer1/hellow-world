'use strict';

app.factory('PlantAttendanceService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {
	return {
		getAttendanceOnLoad: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/getPlantAttendance").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		getPlantAttendanceDetails: function (req) {
			var plantAttendanceDetails = Restangular.one("attendance/getPlantAttendanceRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantAttendanceDetails;
		},
		savePlantAttendanceRecords: function (req) {
			var plantAttendanceDetails = Restangular.one("attendance/savePlantAttendanceRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantAttendanceDetails;
		},
		getPlantAttendanceSheets: function (req) {
			var plantAttendanceDetails = Restangular.one("attendance/getPlantAttendanceSheets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantAttendanceDetails;
		},
		addPlantToAttendanceRecord: function (req) {
			var plantAttendanceDetails = Restangular.one("attendance/addPlantToAttendanceRecord").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantAttendanceDetails;
		},

		getPlantRegDetails: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/getNonAttendancePlantRegDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		copyAttendancePlantDetails: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/copyAttendancePlantDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		getProjLeaveCodes: function (req) {
			var projCrewData = Restangular.one("projectlib/getProjLeaveCodes").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return projCrewData;
		}, saveAttendanceNotifications: function (req) {
			var notifications = Restangular
				.one("notification/saveAttendanceNotifications").customPOST(req,
					'', {}, {
						ContentType: 'application/json'
					});
			return notifications;
		},
		getDailyPlantAttendanceReport: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getDailyPlantAttendanceReport",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getDailyPlantAttendanceReportBtwnDates: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getDailyPlantAttendanceReportBtwnDates",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getPlantDailyResourceStatus: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getPlantDailyResourceStatus",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getPlantAttendanceRecordSheets: function (req) {
			var attendanceRecordSheets = Restangular.one("attendance/getPlantAttendanceRecordSheets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceRecordSheets;
		}
	}
}]);
