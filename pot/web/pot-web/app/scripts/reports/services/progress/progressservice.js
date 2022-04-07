app.factory('progressReportService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	function sendProgressReportRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/workdairy/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getProgressDateWiseRecords: function (req) {
			return sendProgressReportRequest(req, "getProgressDateWiseRecords");
		},

		getProgressPeriodicalRecords: function (req) {
			return sendProgressReportRequest(req, "getProgressPeriodicalRecords");
		},
		getProgressActualRecords: function (req) {
			return sendProgressReportRequest(req, "getProgressActualRecords");
		},
		getSorProgressClaimRecords: function (req) {
			return sendProgressReportRequest(req, "getSorProgressClaimRecords");
		},
		getProgressClaimRecordsReport: function (req) {
			var progressClaimProgress = Restangular.one("reports/getProgressClaimRecordsReport").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return progressClaimProgress;
		},
		getProjectsDatesForProgressSCurveReport: function (req) {
			var result = Restangular.one("reports/getProjectsDatesForProgressSCurveReport").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProgressSCurveReportData: function (req) {
			var result = Restangular.one("reports/getProgressSCurveReportData").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProgressSCurveManpowerReportData: function (req) {
			var result = Restangular.one("reports/getProgressSCurveManpowerReportData").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		}
	}
}]);
