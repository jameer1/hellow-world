app.factory('PlantReportService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	function sendManPowerReportRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/workdairy/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getPlantCostCodeWiseReport: function (req) {
			return sendManPowerReportRequest(req, "getPlantCostCodeWiseReport");
		},
		getPlantDateWiseReport: function (req) {
			return sendManPowerReportRequest(req, "getPlantDateWiseReport");
		},
		getCurrentActivePlants: function (req) {
			return sendManPowerReportRequest(req, "getCurrentActivePlants");
		},
		getPlantsStandardActual: function (req) {
			return sendManPowerReportRequest(req, "getPlantsStandardActual");
		},
		getPlantsPeriodicalReport: function (req) {
			return sendManPowerReportRequest(req, 'getPlantsPeriodicalReport');
		},
		getPlantsIdlePeriodicalReport: function (req) {
			return sendManPowerReportRequest(req, 'getPlantsIdlePeriodicalReport');
		},
		getMultiProjPlantListMap: function (req) {
			var plant = Restangular.one("register/getMultiProjPlantListMap").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantsInUserProjects: function (req) {
			var plant = Restangular.one("register/getPlantsInUserProjects").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		}
	}
}]);
