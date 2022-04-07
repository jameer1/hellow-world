'use strict';

app.factory('ProjPMCPReportService',["$q","Restangular", "$http", "appUrl", function ($q,Restangular, $http, appUrl) {

	function getProjReportPMCPDetails(req) {
	  console.log("Service sendProgressReportRequest requestPayload requestEndPoint");
	  console.log(req);

	  var resultdata = $http({
                     			url: appUrl.originurl + "/app/projectlib/" + "getReportPMCPItems",
                     			method: "POST",
                     			data: JSON.stringify(req),
                     			headers: {}
                     		}).then(data => { return data.data });
    console.log('PMCP Report Service : resultdata');
    console.log(resultdata);
		return resultdata;
	}

return {
		getReportPMCPDetails: function (req) {
		  console.log("Service sendProgressReportRequest getSorProgressClaimRecords req");
			return getProjReportPMCPDetails(req);
		}
	}
}]);
