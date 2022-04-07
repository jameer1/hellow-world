'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ProjectStatusService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	function sendProjectStatusRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/projsettings/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getProjGenerals: function (req) {
			var projGenerals = Restangular.one('projsettings/getProjGenerals').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return projGenerals;
		},

		getMultiProjGenerals: function (req) {
			return sendProjectStatusRequest(req, 'getMultiProjGenerals');
		},

		getEPSDetails: function (req) {
			var epsDetails = Restangular.one("projectlib/projEPSOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsDetails;
		},
		getCostDetails: function (req) {
			var costDetails = Restangular.one("projectlib/getProjCostItems").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return costDetails;
		},
		getProjSOWDetails: function (req) {
			var sowDetails = Restangular.one("projectlib/getSOWItems").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowDetails;
		},

		saveProjTimeSheetAppr: function (req) {
			var saveProjTimeSheetApprReq = Restangular.one('projsettings/saveProjTimeSheetAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjTimeSheetApprReq;
		},
		// Project Summary

		getMeasureUnits: function (req) {
			var getMeasureUnitsReq = Restangular.one('projsettings/getProjManPowerStatus').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getMeasureUnitsReq;
		},

		getPlantUnits: function (req) {
			var getPlantUnitsReq = Restangular.one('projsettings/getProjectPlantsStatus').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPlantUnitsReq;
		},

		getCostUnits: function (req) {
			var getCostUnitsReq = Restangular.one('projsettings/getProjectCostStatementsSummary').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getCostUnitsReq;
		},
		getMultiProjectCostStatementsSummary: function (req) {
			return sendProjectStatusRequest(req, 'getMultiProjectCostStatementsSummary');
		},
		// Project Status
		getProjectStatus: function (req) {
			var getProjectStatusReq = Restangular.one('projsettings/getProjectStatus').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjectStatusReq;
		},

		saveProjCostCodes: function (req) {
			var saveCostStmtReq = Restangular.one('projsettings/saveProjCostCodes').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveCostStmtReq;
		},
		getProjProgMeasure: function (req) {
			var saveCostStmtReq = Restangular.one('projsettings/getProjProgress').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveCostStmtReq;
		},
		saveProjSOWItems: function (req) {
			var saveProjSOWItemReq = Restangular.one('projsettings/saveProjSows').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjSOWItemReq;
		},
		saveProjProgMeasure: function (req) {
			var saveProjProgMeasureReq = Restangular.one('projsettings/saveProjProgress').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjProgMeasureReq;
		},
		getProjStatusDates: function (req) {
			var getReq = Restangular.one('projsettings/getProjStatusDates').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getReq;
		},
		getMultiProjStatusDates: function (req) {
			return sendProjectStatusRequest(req, 'getMultiProjStatusDates');
		},
		saveProjStatusDates: function (req) {
			var saveReq = Restangular.one('projsettings/saveProjStatusDates').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveReq;
		},
		getProjStatusActualQty: function (req) {
			var getProjActualQtyResp = Restangular.one('projsettings/getProjStatusActualQty').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjActualQtyResp;
		},
		saveProjDurationStatus: function (req) {
			var saveReq = Restangular.one('projsettings/saveProjDurationStatus').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveReq;
		},
		saveProjStatusMileStones: function (req) {
			var saveReq = Restangular.one('projsettings/saveProjStatusMileStones').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveReq;
		},
		getProjStatusMileStones: function (req) {
			var saveReq = Restangular.one('projsettings/getProjStatusMileStones').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveReq;
		},
		deleteProjStatusMileStones: function (req) {
			var saveReq = Restangular.one('projsettings/deleteProjStatusMileStones').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveReq;
		}
	}
}]);
