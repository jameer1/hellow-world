'use strict';

app.factory('ProjectScheduleService', ["Restangular", "$http", "appUrl", "Principal", function (Restangular, $http, appUrl, Principal) {

	function sendRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/projschedules/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getProjBudgetManPowerDetails: function (req) {
			var result = Restangular.one('projschedules/getProjBudgetManPowerDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjBudgetPlantDetails: function (req) {
			return sendRequest(req, 'getProjBudgetPlantDetails');
		},
		getProjBudgetMaterialDetails: function (req) {
			return sendRequest(req, 'getProjBudgetMaterialDetails');
		},
		getProjBudgetCostCodeDetails: function (req) {
			return sendRequest(req, 'getProjBudgetCostCodeDetails');
		},
		getProjBudgetSOWDetails: function (req) {
			return sendRequest(req, 'getProjBudgetSOWDetails');
		},
		getMultiProjBudgetSOWDetails: function (req) {
			return sendRequest(req, 'getMultiProjBudgetSOWDetails');
		},
		getProjScheduleBaseLines: function (req) {
			var result = Restangular.one('projschedules/getProjScheduleBaseLines').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveProjScheduleBaseLines: function (req) {
			var result = Restangular.one('projschedules/saveProjScheduleBaseLines').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjScheduleManPowerDetails: function (req) {
			var result = Restangular.one('projschedules/getProjScheduleManPowerDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveProjScheduleManPowerDetails: function (req) {
			var result = Restangular.one('projschedules/saveProjScheduleManPowerDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjSchedulePlantDetails: function (req) {
			var result = Restangular.one('projschedules/getProjSchedulePlantDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveProjSchedulePlantDetails: function (req) {
			var result = Restangular.one('projschedules/saveProjSchedulePlantDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjScheduleMaterialDetails: function (req) {
			var result = Restangular.one('projschedules/getProjScheduleMaterialDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveProjScheduleMaterialDetails: function (req) {
			var result = Restangular.one('projschedules/saveProjScheduleMaterialDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjScheduleCostCodeDetails: function (req) {
			var result = Restangular.one('projschedules/getProjScheduleCostCodeDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveProjScheduleCostCodeDetails: function (req) {
			var result = Restangular.one('projschedules/saveProjScheduleCostCodeDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjScheduleSOWDetails: function (req) {
			var result = Restangular.one('projschedules/getProjScheduleSOWDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveProjScheduleSOWDetails: function (req) {
			var result = Restangular.one('projschedules/saveProjScheduleSOWDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		}, deleteProjScheduleBaseLines: function (req) {
			var result = Restangular.one('projschedules/deleteProjScheduleBaseLines').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		}, saveAssignedBaseLine: function (req) {
			var result = Restangular.one('projschedules/saveAssignedBaseLine').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getMultiProjBudgetManPowerDetails: function (req) {
			return sendRequest(req, 'getMultiProjBudgetManPowerDetails');
		},
		getMultiProjBudgetPlantDetails: function (req) {
			return sendRequest(req, 'getMultiProjBudgetPlantDetails');
		},
		getMultiProjMultiBudgetTypeDetails: function (req) {
			return sendRequest(req, 'getMultiProjMultiBudgetTypeDetails');
		},
		parseScheduleActivityData: function (file, req) {
			return $http({
				headers: {'Content-Type': undefined, 'pottoken' : Principal.potToken()},
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'projschedules/parseScheduleActivityData',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("scheduleActivityDataSetReq", angular.toJson(data.model));
					return formData;
				},
				data: {model:req,file : file}
			});
		},
		parseResourceAssignmentData: function (file, req) {
			return $http({
				headers: {'Content-Type': undefined, 'pottoken' : Principal.potToken()},
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'projschedules/parseResourceAssignmentData',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("scheduleActivityDataSetReq", angular.toJson(data.model));
					return formData;
				},
				data: {model:req,file : file}
			});
		},
		saveDataset: function(req) {
			var result = Restangular.one('projschedules/saveScheduleActivity').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getDatasetList: function(req) {
			var result = Restangular.one('projschedules/getScheduleActivityDatasetList').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getScheduleActivity: function(req) {
			var result = Restangular.one('projschedules/getScheduleActivity').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveScheduleActivityDatasets: function(req) {
			var result = Restangular.one('projschedules/saveScheduleActivityDatasets').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getDateWiseForecastActualForResource: function(req) {
			var result = Restangular.one('projschedules/getDateWiseForecastActualForResource').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getTangibles: function(req) {
			var result = Restangular.one('projschedules/getTangibles').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getScheduleOfRates: function(req) {
			var result = Restangular.one('projschedules/getScheduleOfRates').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getActualActivityScheduleFor: function(req) {
			var result = Restangular.one('projschedules/getActualActivityScheduleFor').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getActualActivityScheduleForGanttChart: function(req) {
			var result = Restangular.one('projschedules/getActualActivityScheduleForGanttChart').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		prepareResourceAssignmentData: function(req) {
			var result = Restangular.one('projschedules/prepareResourceAssignmentData').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveResourceCurveMapping: function(req) {
			var result = Restangular.one('projschedules/saveResourceCurveMapping').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
	};
}]);
