'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Attendance Service in the potApp.
 */
app.factory('EmpAttendanceService', ["$q", "Restangular", "$http", "appUrl", function ($q, Restangular, $http, appUrl) {
	return {
		getAttendanceOnLoad: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/getEmpAttendance").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		getAttendanceDays: function (req) {
			var attendanceDays = Restangular.one("attendance/getAttendanceDays").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceDays;
		},
		getEmpAttendanceDetails: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getEmpAttendanceRecords",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		saveEmpAttendanceRecords: function (req) {
			var empAttendanceDetails = Restangular.one("attendance/saveEmpAttendanceRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return empAttendanceDetails;
		},
		getEmpAttendanceSheets: function (req) {
			var empAttendanceDetails = Restangular.one("attendance/getEmpAttendanceSheets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return empAttendanceDetails;
		},
		getNonAttendanceEmpRegDetails: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/getNonAttendanceEmpRegDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		copyAttendanceEmpDetails: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/copyAttendanceEmpDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		addEmpToAttendanceRecord: function (req) {
			var attendanceOnLoad = Restangular.one("attendance/addEmpToAttendanceRecord").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceOnLoad;
		},
		getProjCrewLists: function (req) {
			var projCrewData = Restangular.one("projectlib/getProjCrewLists").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return projCrewData;
		},
		submitEmpNotifications: function (req) {
			var projCrewData = Restangular.one("projectlib/submitEmpNotifications").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return projCrewData;
		},
		saveAttendanceNotifications: function (req) {
			var notifications = Restangular
				.one("notification/saveAttendanceNotifications").customPOST(req,
					'', {}, {
						ContentType: 'application/json'
					});
			return notifications;
		},
		// Services for attendance report
		getDailyEmpAttendanceReport: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getDailyEmpAttendanceReport",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getEmpAttendanceRecordsByDate: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getEmpAttendanceRecordsByDate",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getDailyEmpAttendanceReportBtwnDates: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getDailyEmpAttendanceReportBtwnDates",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getEmpDailyResourceStatus: function (req) {
			return $http({
				url: appUrl.originurl + "/app/attendance/getEmpDailyResourceStatus",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getEmployeeAttendanceRecordSheets: function (req) {
			var attendanceRecordSheets = Restangular.one("attendance/getEmployeeAttendanceRecordSheets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return attendanceRecordSheets;
		}
	}
}]);
