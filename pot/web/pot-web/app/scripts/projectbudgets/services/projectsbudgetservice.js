'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ProjectBudgetService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	function sendProjectBudgetsRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/projsettings/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => {return data.data });
	}

	return {
		getCountryDetailsById: function (req) {
			var getProvisionsReq = Restangular.one('common/getCountryDetailsById').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProvisionsReq;
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
		saveProjectPlants: function (req) {
			var resultPlantsStatus = Restangular.one("projsettings/saveProjectPlants").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return resultPlantsStatus;
		},
		getEmpClasses: function (req) {
			return sendProjectBudgetsRequest(req, 'getEmpForBudgets');
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
		saveEmpTrans: function (req) {
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
		saveProjPlantTransAppr: function (req) {
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
		},
		saveProjMaterialTrans: function (req) {
			var saveProjMaterialTransReq = Restangular.one('projsettings/saveProjMaterialTrans').customPOST(req, '', {}, {
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
		getProjNoteBook: function (req) {
			var getNoteBookReq = Restangular.one('projsettings/getProjNoteBook').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getNoteBookReq;
		},
		saveProjNoteBook: function (req) {
			var saveNoteBookReq = Restangular.one('projsettings/saveProjNoteBook').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveNoteBookReq;
		},
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
		/* ================================================== */
		getProjManpowers: function (req) {
			return sendProjectBudgetsRequest(req, 'getProjManpowers');
		},
		saveProjManpowers: function (req) {
			var saveManpowerReq = Restangular.one('projsettings/saveProjManpowers').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveManpowerReq;
		},

		getProjEmpTyFpes: function (req) {
			var req = Restangular.one('projectlib/getProjEmpTyFpes').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return req;
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
		getPlantClasses: function (req) {
			return sendProjectBudgetsRequest(req, 'getPlantForBudgets');
		},
		getMaterials: function (req) {
			return sendProjectBudgetsRequest(req, 'getMaterialForBudgets');
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
		  console.log('Budget Service getProjCostStatements req');
		  console.log(req);
			return sendProjectBudgetsRequest(req, 'getProjCostStatements');
		},
		getMultiProjCostStatements: function (req) {
			return sendProjectBudgetsRequest(req, 'getMultiProjCostStatements');
		},
		/* Dashboard Cost and Performance > Date Wise Actual Cost Details */
		getActualCostDetails: function (req) {
			return sendProjectBudgetsRequest(req, 'getActualCostDetails');
		},
		/*----------------------------------------------------------------*/
		/*Dashboard Cost > Cost Work Sheet*/
		getPlanActualEarned: function (req) {
			return sendProjectBudgetsRequest(req, "getPlanActualEarned");
		},
		/*---------------------*/
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

		getProjManpowerMap: function (req) {
			var result = Restangular.one('projsettings/getProjManpowerMap').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},		
		projBudgetApproval: function(req) {
			var result = Restangular.one('projsettings/projBudgetApproval').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		projBudgetReturn: function(req) {
			var return_result = Restangular.one('projsettings/projBudgetReturnWithComments').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return return_result;
		}
	}
}]);
