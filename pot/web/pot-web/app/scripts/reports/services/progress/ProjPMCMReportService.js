'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjSOR Service in the potApp.
 */
app.factory('ProjPMCMReportService',["$q","Restangular", "$http", "appUrl", function ($q,Restangular, $http, appUrl) {
	//return {

	function sendProgressReportRequest(req) {
	  console.log("Service sendProgressReportRequest requestPayload requestEndPoint");
	  console.log(req);
	  //console.log(requestEndPoint); // timemanagement
	  //console.log("appUrl.originurl : "+appUrl.originurl);
		return $http({
			url: appUrl.originurl + "/app/projectlib/" + "getReportPMCMItems",
			method: "POST",
			data: JSON.stringify(req),
			headers: {}
		}).then(data => { return data.data });
	}

return {
		getProgressDateWiseRecords: function (req) {
		  console.log("Service sendProgressReportRequest getProgressDateWiseRecords req");
			return sendProgressReportRequest(req, "getProgressDateWiseRecords");
		},

		getProgressPeriodicalRecords: function (req) {
		  console.log("Service sendProgressReportRequest getProgressPeriodicalRecords req");
			return sendProgressReportRequest(req, "getProgressPeriodicalRecords");
		},
		getProgressActualRecords: function (req) {
		  console.log("Service sendProgressReportRequest getProgressActualRecords req");
			return sendProgressReportRequest(req, "getProgressActualRecords");
		},
		getReportPMCMDetails: function (req) {
		  console.log("Service sendProgressReportRequest getSorProgressClaimRecords req");
			return sendProgressReportRequest(req);
		},


		getProgressClaimRecordsReport: function (req) {
		console.log("Service sendProgressReportRequest getProgressClaimRecordsReport req");
			var progressClaimProgress = Restangular.one("reports/getProgressClaimRecordsReport").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return progressClaimProgress;
		}
	}
}]);
