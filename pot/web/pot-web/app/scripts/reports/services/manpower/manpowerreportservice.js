app.factory('ManpowerReportService', ["$http", "appUrl", function ($http, appUrl) {

	function sendManPowerReportRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/timesheet/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	function sendManPowerReportRequestToRegister(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/register/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getManpowerDateWiseHrsReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerDateWiseHrsReport');
		},
		getManpowerGenderStatisticsReport: function (req) {
			return sendManPowerReportRequestToRegister(req, 'getManpowerGenderStatisticsReport');
		},
		getManpowerIdleHrsReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerIdleHrsReport');
		},
		getManpowerPeriodicalReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerPeriodicalReport');
		},
		getManpowerCostCodeWiseReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerCostCodeWiseReport');
		},
		getManpowerActualStandardReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerActualStandardReport');
		},
		getManpowerPeriodicalMobilisationReport: function (req) {
			return sendManPowerReportRequestToRegister(req, 'getManpowerPeriodicalMobilisationReport');
		},
		getManpowerCurrentEmployeeReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerCurrentEmployeeReport');
		},
		getManpowerPlanActualEarnedReport: function (req) {
			return sendManPowerReportRequest(req, 'getManpowerPlanActualEarnedReport');
		},
		getProjEarnedValues: function (req) {
			return sendManPowerReportRequest(req, 'getProjEarnedValues');
		},
		getManpowerEmployeeDetail: function(req){
			return sendManPowerReportRequestToRegister(req, 'getManpowerEmployeeDetail');
		}
	}
}]);