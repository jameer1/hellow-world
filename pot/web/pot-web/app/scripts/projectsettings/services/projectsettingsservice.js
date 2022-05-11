'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ProjectSettingsService', ["Restangular", "$http", "$q", "appUrl", function (Restangular, $http, $q, appUrl) {

	function sendProjSettingsReq(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/projsettings/app/projsettings/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
			headers: {}
		}).then(data => { return data.data });
	}
	return {
		projGeneralOnLoad: function (req) {
			var onLoadGenValReq = Restangular.one('projsettings/projGeneralOnLoad').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return onLoadGenValReq;
		},
		getProjGeneralMap: function (req) {
			var genValMapReq = Restangular.one('projsettings/getProjGeneralMap').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			console.log('getProjGeneralMap genValMapReq');
			console.log(genValMapReq);
			return genValMapReq;
		},
		getCountryDetailsById: function (req) {
			var getProvisionsReq = Restangular.one('common/getCountryDetailsById').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProvisionsReq;
		},
		saveProjGeneralValues: function (req) {
			var saveProjGeneralsReq = Restangular.one('projsettings/saveProjGenerals').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjGeneralsReq;
		},
		projAttendence: function (req) {
			var projAttendenceOnLoadReq = Restangular.one('projsettings/getProjAttendence').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return projAttendenceOnLoadReq;
		},
		getProjCrewLists: function (req) {
			var crewLists = Restangular.one('projectlib/getProjCrewLists').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return crewLists;
		},
		getProjUsers: function (req) {
			var users = Restangular.one("user/getUsers").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return users;
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
		saveProjAttendence: function (req) {
			var saveProjAttendenceReq = Restangular.one('projsettings/saveProjAttendence').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjAttendenceReq;
		},
		saveProjAttendenceAppr: function (req) {
			var saveProjAttendenceApprReq = Restangular.one('projsettings/saveProjAttendenceAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjAttendenceApprReq;
		},

		projWorkDairyOnLoad: function (req) {
			var workDairyOnLoadReq = Restangular.one('projsettings/getProjWorkDairy').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyOnLoadReq;
		},
		saveWorkDairy: function (req) {
			var saveWorkDairyReq = Restangular.one('projsettings/saveWorkDairy').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveWorkDairyReq;
		},

		saveWorkDairyApprs: function (req) {
			var saveWorkDairyApprReq = Restangular.one('projsettings/saveWorkDairyApprs').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveWorkDairyApprReq;
		},

		projTimeSheetOnLoad: function (req) {
			var timeSheetOnLoadReq = Restangular.one('projsettings/ProjTimeSheetOnLoad').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return timeSheetOnLoadReq;
		},
		saveProjTimeSheet: function (req) {
			var saveTimeSheetReq = Restangular.one('projsettings/saveProjTimeSheet').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveTimeSheetReq;
		},

		saveProjTimeSheetAppr: function (req) {
			var saveProjTimeSheetApprReq = Restangular.one('projsettings/saveProjTimeSheetAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjTimeSheetApprReq;
		},

		// Procurement Create
		getProjProcurements: function (req) {
			var getProcurementReq = Restangular.one('projsettings/getProjProcure').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProcurementReq;
		},
		saveProjProcurements: function (req) {
			var saveProcureReq = Restangular.one('projsettings/saveProjProcure').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProcureReq;
		},

		// Procurement Approval
		saveProjProcurementAppr: function (req) {
			var saveProjProcureApprReq = Restangular.one('projsettings/saveProjProcureAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjProcureApprReq;
		},

		// Employee Transfer Create
		projEmpTransOnLoad: function (req) {
			var empTransOnLoadReq = Restangular.one('projsettings/getEmpTrans').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return empTransOnLoadReq;
		},
		saveEmpTransDetails: function (req) {
			var saveEmpTransReq = Restangular.one('projsettings/saveEmpTrans').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveEmpTransReq;
		},

		// Employee Transfer Approval
		saveEmpTransAppr: function (req) {
			var saveEmpTransApprReq = Restangular.one('projsettings/saveEmpTransAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveEmpTransApprReq;
		},

		// Plant Transfer Create
		projPlantTransOnLoad: function (req) {
			var plantTransOnLoadReq = Restangular.one('projsettings/getProjPlantTrans').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantTransOnLoadReq;
		},

		saveProjPlantTrans: function (req) {
			var saveProjPlantTransReq = Restangular.one('projsettings/saveProjPlantTrans').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjPlantTransReq;
		},

		// Plant Transfer Approval
		saveProjPlantApprTrans: function (req) {
			var saveProjPlantTransApprReq = Restangular.one('projsettings/saveProjPlantTransAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjPlantTransApprReq;
		},

		// Material Transfer Create
		projMaterialTransOnLoad: function (req) {

			var materialTransOnLoadReq = Restangular.one('projsettings/getProjMaterialTrans').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return materialTransOnLoadReq;
		}, saveProjMaterialTrans: function (req) {
			var saveProjMaterialTransReq = Restangular.one('projsettings/saveProjMaterialTrans').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjMaterialTransReq;
		},
		saveProjMaterialApprTrans: function (req) {
			var saveProjMaterialTransReq = Restangular.one('projsettings/saveProjMaterialTransAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjMaterialTransReq;
		},

		// Material Transfer Approval
		saveProjMaterialTransAppr: function (req) {
			var saveProjMaterialTransApprReq = Restangular.one('projsettings/saveProjMaterialTransAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjMaterialTransApprReq;
		},

		// Project Estimate
		getProjEstimate: function (req) {
			var getProjEstimateReq = Restangular.one('projsettings/getProjEstimate').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjEstimateReq;
		},
		saveProjEstimate: function (req) {
			var saveProjEstimateReq = Restangular.one('projsettings/saveProjEstimate').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjEstimateReq;
		},

		// Performance Threshold
		getProjPerformenceThreshold: function (req) {
			var getPeformanceThresholdData = Restangular.one('projsettings/getProjPerformenceThreshold').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPeformanceThresholdData;
		},
		savePerformanceThreshold: function (req) {
			var saveProjPerformenceThresholdData = Restangular.one('projsettings/SaveProjPerformenceThreshold').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjPerformenceThresholdData;
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
		// Project Status
		getProjectStatus: function (req) {
			var getProjectStatusReq = Restangular.one('projsettings/getProjectStatus').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjectStatusReq;
		},
		// Note Book

		// Project Reports
		projReportsOnLoad: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/projReportsOnLoad').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},
		saveProjReports: function (req) {
			var saveProjReportsReq = Restangular.one('projsettings/saveProjReports').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjReportsReq;
		},
		getProjProgressClaims: function (req) {
			var getProjClaimReq = Restangular.one('projsettings/getProjProgressClaim').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjClaimReq;
		},

		projClaimePeriodOnload: function (req) {
			var onloadReq = Restangular.one('projsettings/projClaimePeriodOnload').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return onloadReq;
		},

		saveProjProgressClaim: function (req) {
			var saveProjClaimReq = Restangular.one('projsettings/saveProjProgressClaim').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjClaimReq;
		},
		saveProgressClaimAppr: function (req) {
			var saveProjClaimApprReq = Restangular.one('projsettings/saveProjProgressClaimAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjClaimApprReq;
		},
		projPayRollCycleOnLoad: function (req) {
			var getPayRollReq = Restangular.one('projsettings/projPayRollCycleOnLoad').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPayRollReq;
		},
		saveProjPayRollCycle: function (req) {
			var saveProjClaimApprReq = Restangular.one('projsettings/saveProjPayRollCycle').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjClaimApprReq;
		},
		getProjLeaveRequest: function (req) {
			var getProjLeaveReq = Restangular.one('projsettings/getProjLeaveRequest').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjLeaveReq;
		},

		saveProjLeaveRequest: function (req) {
			var getProjLeaveReq = Restangular.one('projsettings/saveProjLeaveRequest').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjLeaveReq;
		},

		saveProjLeaveApproval: function (req) {
			var saveProjLeaveReq = Restangular.one('projsettings/saveProjLeaveApproval').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjLeaveReq;
		},
		saveLeaveAppr: function (req) {
			var saveProjLeaveApprReq = Restangular.one('projsettings/saveProjProgressClaimAppr').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjLeaveApprReq;
		},
		/* ================================================== */
		getProjManpowers: function (req) {
			var getManpowerReq = Restangular.one('projsettings/getProjManpowers').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getManpowerReq;
		},
		saveProjManpowers: function (req) {
			var saveManpowerReq = Restangular.one('projsettings/saveProjManpowers').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveManpowerReq;
		},
		getProjectPlants: function (req) {
			var getPlantsReq = Restangular.one('projsettings/getProjectPlants').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPlantsReq;
		},
		saveProjectPlants: function (req) {
			var savePlantsReq = Restangular.one('projsettings/saveProjectPlants').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return savePlantsReq;
		},
		getProjectMaterials: function (req) {
			var getMaterialReq = Restangular.one('projsettings/getProjectMaterials').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getMaterialReq;
		},
		saveProjectMaterials: function (req) {
			var saveMeterialsReq = Restangular.one('projsettings/saveProjectMaterials').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveMeterialsReq;
		},

		getProjCostStatements: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/getProjCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},
		getProjExitCostStatements: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/getProjExitCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},
		getProjExitManpowerCostStatements: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/getProjExitManpowerCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},

		getProjExitMaterialCostStatements: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/getProjExitMaterialCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},

		getProjExitPlantCostStatements: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/getProjExitPlantCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},

		getProjExitServicesCostStatements: function (req) {
			var getProjReportsReq = Restangular.one('projsettings/getProjExitServicesCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProjReportsReq;
		},
		saveCostStatement: function (req) {
			var saveCostStmtReq = Restangular.one('projsettings/saveProjCostStatements').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveCostStmtReq;
		},
		deactivateCostCodes: function (req) {
			var deactivateCostStmtDtls = Restangular.one('projsettings/projectCostStmtsDeactivate').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return deactivateCostStmtDtls;
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

		saveProjPerfomanceDefaultSettings: function (req) {
			var saveProjPerformanceReq = Restangular.one('projsettings/saveProjPerfomanceDefaultSettings').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjPerformanceReq;

		},

		ProjProgressClaimePeriodCycleOnload: function (req) {
			var req = Restangular.one('projsettings/ProjProgressClaimePeriodCycleOnload').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return req;
		},
		saveProjProgressClaimePeriodCycle: function (req) {
			var req = Restangular.one('projsettings/saveProjProgressClaimePeriodCycle').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return req;
		},

		getCalendars: function (req) {
			var req = Restangular.one('calendar/getCalendars').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return req;
		},

		saveProjDefaultSettings: function (req) {
			var saveProjReq = Restangular.one('projsettings/saveProjDefaultSettings').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjReq;
		},

		findEmpTransNormalTime: function (req) {
			var saveProjReq = Restangular.one('projsettings/findEmpTransNormalTime').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjReq;
		},

		getCostCodeActualQty: function (req) {
			var saveProjReq = Restangular.one('projsettings/getCostCodeActualQty').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveProjReq;
		},
		
		getProjPlannedValue: function (req) {
			var onLoadGenValReq = Restangular.one('projsettings/getProjPlannedValue').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return onLoadGenValReq;
		},

		getProjPlannedHours: function (req) {
			var onLoadGenValReq = Restangular.one('projsettings/getProjPlannedHours').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return onLoadGenValReq;
		},
		
		getManpowerProductivityAnalysisReportData: function (req) {
			var result = Restangular.one('projsettings/getManpowerProductivityAnalysisReportData').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getProjectsGanttChartReportData: function (req) {
			var result = Restangular.one('projsettings/getProjectsGanttChartReportData').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		projScheduleEstimatesOnLoad: function(req) {
			var result = Restangular.one('projsettings/getProjSchofEstimates').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveEstimates: function(req){
			var result = Restangular.one('projsettings/saveProjSchofEstimates').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		projScheduleofRatesOnLoad: function(req){
			var result = Restangular.one('projsettings/getProjSchofRates').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		saveSchRates: function(req){
			var result = Restangular.one('projsettings/saveProjSchofRates').customPOST(req, '', {
				ContentType: 'application/json'
			});
			return result;
		},
		ProjResourceBudgetOnLoad: function(req){
			var result = Restangular.one('projsettings/getProjResBudget').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		SaveResourceBudget: function(req){
			var result = Restangular.one('projsettings/saveProjResBudget').customPOST(req,'', {}, {
				ContentType: 'application/json'
			});
			return result; 
		},
		
		saveSoeApprs: function (req) {
			var saveSoeApprReq = Restangular.one('projsettings/saveSoeApprs').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveSoeApprReq;
		},
		
		projchangeOrderOnLoad: function(req){
			var result = Restangular.one('projsettings/getChangeOrderDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		
		saveChangeOrderDetails: function(req) {
			var saveChangeOrderDetailsReq = Restangular.one('projsettings/saveChangeOrderDetails').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveChangeOrderDetailsReq;
		},
	}
}]);
