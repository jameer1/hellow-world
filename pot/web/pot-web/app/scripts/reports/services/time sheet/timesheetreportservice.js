app.factory('TimeSheetReportService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	function sendTimesheetReportRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/timesheet/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getTimeSheetDailyReport: function (req) {
			return sendTimesheetReportRequest(req, 'getTimeSheetDailyReport');
		},
		getTimeSheetApprStatusReport: function (req) {
			return sendTimesheetReportRequest(req, 'getTimeSheetApprStatusReport');
		},
		getTimeSheetReqUser: function (req) {
			return sendTimesheetReportRequest(req, 'getTimeSheetReqUsers');
		}
	}
}]);