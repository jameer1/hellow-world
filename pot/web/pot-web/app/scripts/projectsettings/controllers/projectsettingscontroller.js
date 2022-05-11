'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("projsettings", {
		url: '/projsettings',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectsettings/projectview.html',
				controller: 'ProjectSettingsController'
			}
		}
	})

}]).controller('ProjectSettingsController', ["$rootScope", "$scope", "$state", "$q", "blockUI",
	"GeneralValuesCalendarFactory", "$filter", "ProfitCentrePopUpFactory", "FinanceCentrePopUpFactory", "ProjectSettingsService",
	"ProjMaterialTransferNotificationPopupService", "ProjPlantTransferNotificationPopupService",
	"ProjEmpTransferNotificationPopupService", "EmpRegisterService", "MaterialRegisterService", "PlantRegisterService",
	"ngDialog", "NotificationService", "GenericAlertService", "moduleservice", "ProjManPowerFactory",
	"PerformanceThresholdFactory", "CompanyListPopUpFactory", "ProjPlantFactory", "ProjectCrewPopupService", "EpsService",
	"ProjectSettingCostItemFactory", "ProjectSettingSOWItemFactory", "ProjectWorkDairyNotificationPopupService",
	"ProjectAttendencePopupService", "ProjectProcurementNotificationPopupService",
	"LeaveApprNotificationPopupService", "generalservice", "CountryService", "TreeService", "ProcureService","ProjectSoeNotificationPopupService","ProjectTimeSheetNotificationPopupService","stylesService", "ngGridService",
	function ($rootScope, $scope, $state, $q, blockUI,
	GeneralValuesCalendarFactory, $filter, ProfitCentrePopUpFactory, FinanceCentrePopUpFactory, ProjectSettingsService,
	ProjMaterialTransferNotificationPopupService, ProjPlantTransferNotificationPopupService,
	ProjEmpTransferNotificationPopupService, EmpRegisterService, MaterialRegisterService,
	PlantRegisterService, ngDialog, NotificationService, GenericAlertService, moduleservice,
	ProjManPowerFactory, PerformanceThresholdFactory, CompanyListPopUpFactory, ProjPlantFactory,
	ProjectCrewPopupService, EpsService, ProjectSettingCostItemFactory, ProjectSettingSOWItemFactory,
	ProjectWorkDairyNotificationPopupService,
	ProjectAttendencePopupService, ProjectProcurementNotificationPopupService, LeaveApprNotificationPopupService,
	generalservice, CountryService, TreeService, ProcureService, ProjectSoeNotificationPopupService,ProjectTimeSheetNotificationPopupService,stylesService,ngGridService) {

	$rootScope.projId = null;

	$scope.treeData = [];

	var deferred = $q.defer();

	$scope.moreFlag = 0;

	$scope.currentTab1 = null;

	$scope.editing = false;
	$scope.supervisors = [];
	$scope.reqNumbers = [];
  $scope.isPercentOverCostAllowed = true;

	$scope.projTabs = [{
		"name": "Settings",
		"urlValue": "views/projectsettings/settings.html",
		"childTabs": [
			{
				"name": "General Values",
				"urlValue": "views/projectsettings/generalvalues.html",
				"childTabs": [],
				"appCodeTemplateKey": 'PRJ_PRJSTG_GNRLVLU_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_GeneralValuesTab'
			}, //new requirement tab - 1
			{
				"name": "Schedule of Estimates",
				"urlValue": "views/projectsettings/estimatesTab.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_SchOFEstimatesTab',
				"SelenumLocator": 'Projects_ProjectSettings_ScheduleEstimatesTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/schEstimatescreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_SchOFEstimatesNORMALTIME',
						"SelenumLocator": 'Projects_ProjectSettings_SchOFEstimatesNORMALTIME'
					},{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/schEstimatesapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_SchOFEstimatesADDITIONALTIME',
						"SelenumLocator": 'Projects_ProjectSettings_SchOFEstimatesADDITIONALTIME'
					}
				]
			},
			{  //new requirement tab - 2
				"name": "Schedule of Rates",
				"urlValue": "views/projectsettings/schofRatesTab.html",
				"appCodeTemplateKey": "PRJ_PRJSTG_SchOFRatesTab",
				"SelenumLocator": 'Projects_ProjectSettings_ScheduleRates',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/schofRatescreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_SchOFRatesNORMALTIME',
						"SelenumLocator": 'Projects_ProjectSettings_ScheduleRatesNORMALTIME'
					},{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/schofRatesapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_SchOFRatesADDITIONALTIME',
						"SelenumLocator": 'Projects_ProjectSettings_ScheduleRatesADDITIONALTIME'
					}
				]
			},
			{ //new requirement tab - 3
				"name": "Resources Budget",
				"urlValue": "views/projectsettings/resourceBudgetTab.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_ResBudgetTab',
				"SelenumLocator": 'Projects_ProjectSettings_ResBudgetTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/resourceBudgetcreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_ResBudgetTabNORMALTIME',
						"SelenumLocator": 'Projects_ProjectSettings_ResBudgetTabNORMALTIME'
					},{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/resourceBudgetapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PTRSTG_ResBudgetTabADDITIONALTIME',
						"SelenumLocator": 'Projects_ProjectSettings_ResBudgetTabADDITIONALTIME'
					}
				]
			},
			{
				"name": "Employee Attendance",
				"urlValue": "views/projectsettings/empattendance/empattendancetabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_EMPATNDCERCDSTG_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_EmployeeAttendanceTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/empattendance/empattendancenormaltime.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_EMPNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_EmployeeAttendanceTab_NormalTimeTab'
					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/empattendance/empattendanceadditionaltime.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_EMPADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_EmployeeAttendanceTab_AdditionalTimeTab'

					}
				]
			},
			{
				"name": "Plant Attendance",
				"urlValue": "views/projectsettings/plantattendance/plantattendancetabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_PLTATNDCERCDSTG_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_PlantAttendanceTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/plantattendance/plantattendancenormaltime.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PLANTNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_PlantAttendanceTab_NormalTimeTab'
					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/plantattendance/plantattendanceadditionaltime.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PLANTADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_PlantAttendanceTab_AdditionalTimeTab'
					}
				]
			},
			{
				"name": "Work Diary",
				"urlValue": "views/projectsettings/worktabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_WRKDIRYSTG_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_WorkDiaryTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/workcreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_WRKDIRYSTGNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_WorkDiaryTab_NormalTimeTab'

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/workapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_WRKDIRYSTGADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_WorkDiaryTab_AdditionalTimeTab'

					}
				]
			},
			{
				"name": "Time Sheet",
				"urlValue": "views/projectsettings/timetabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_TIMSHETSTG_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_TimeSheetTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/timecreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_TIMESHEETSTGNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_TimeSheetTab_NormalTime'

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/timeapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_TIMESHEETSTGADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_TimeSheetTab_AdditionalTime'

					}
				]
			},
			{
				"clientId": null,
				"clientCode": null,
				"name": "Procurement",
				"appCodeTemplateKey": 'PRJ_PRJSTG_PROCURSTG_VIEW',
				"urlValue": "views/projectsettings/procurementtabs.html",
				"SelenumLocator": 'Projects_ProjectSettings_ProcurementTab',

				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/procurementcreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PROCURSTGNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ProcurementTab_NormalTime'

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/procurementapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PROCURSTGADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ProcurementTab_AdditionalTime'

					}
				]
			},
			{
				"name": "Employee Transfer",
				"urlValue": "views/projectsettings/employeetransfertabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_EMPTRSFR_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_TimeSheetTab_AdditionalTime',
				"SelenumLocator": 'Projects_ProjectSettings_EmployeeTransferTab'
				,
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/employeetransfercreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_EMPTRANSTGNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_EmployeeTransferTab_NormalTime'

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/employeetransferapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_EMPTRANSTGADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_EmployeeTransferTab_AdditionalTime'

					}
				]
			},
			{
				"name": "Plant Transfer",
				"urlValue": "views/projectsettings/planttransfertabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_PLANTTRSFR_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_PlantTransferTab'
				,
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/planttransfercreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PLANTTRANSTGNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_PlantTransferTab_NormalTime'

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/planttransferapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PLANTTRANSTGADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_PlantTransferTab_AdditionalTime'

					}
				]
			},
			{
				"name": "Material Transfer",
				"urlValue": "views/projectsettings/materialtransfertabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_MATRLTRSFR_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_MaterialTransferTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/materialtransfercreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_MATERIALTRANSTGNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_MaterialTransferTab_NormalTime'

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/materialtransferapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_MATERIALTRANSTGADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_MaterialTransferTab_AdditionalTime'

					}
				]
			},
			{
				"name": "Estimate to complete",
				"urlValue": "views/projectsettings/estimatetocomplete.html",
				"SelenumLocator": 'Projects_ProjectSettings_EstimateToCompleteTab',
				"childTabs": [],
				"appCodeTemplateKey": 'PRJ_PRJSTG_ESTTOCMPLT_VIEW',

			},
			{
				"name": "Performance Threshold",
				"urlValue": "views/projectsettings/performancetreshold.html",
				"childTabs": [],
				"appCodeTemplateKey": 'PRJ_PRJSTG_PRFMTHRSHLD_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_PerformanceThresholdTab',

			},{
				"name": "Change Orders",
				"urlValue": "views/projectsettings/changeordersTab.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_CHANGEORDER_TAB',
				"selenumLocator": 'Projects_ProjectSettings_ChangeOrderTab',
				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/changeordercreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_CHANGEORDERNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ChangeOrderTab_NormalTime'
					},{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/changeorderAdditionalTime.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_CHANGEORDERADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ChangeOrderTab_AdditionalTime'
					}
				]
			},
			{
				"name": "Reports",
				"urlValue": "views/projectsettings/reports.html",
				"childTabs": [],
				"appCodeTemplateKey": 'PRJ_PRJSTG_REPORT_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_ReportsTab',

			},
			{
				"name": "Progress Claims",
				"urlValue": "views/projectsettings/progresstabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_PROGRESCLIAM_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_ProgressClaimsTab',


				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/progressclaimscreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PROGRESCLIAMNORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ProgressClaimsTab_NormalTime',


					},
					{
						"name": "Period Cycle",
						"urlValue": "views/projectsettings/progressclaimeperiodcycle.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PROGRESCLIAMPERIODCYCLE_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ProgressClaimsTab_PeriodCycle',


					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/progressclaimsapproval.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_PROGRESCLIAMADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_ProgressClaimsTab_AdditionalTimeTab',


					}
				]
			},
			{
				"name": "Payroll Cycle",
				"urlValue": "views/projectsettings/payrollcycle.html",
				"childTabs": [],
				"appCodeTemplateKey": 'PRJ_PRJSTG_PAYROLLCYCLE_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_PayRollCycleTab',

			},
			{
				"name": "Leave Approval",
				"urlValue": "views/projectsettings/leavetabs.html",
				"appCodeTemplateKey": 'PRJ_PRJSTG_LEAVEAPPROVAL_VIEW',
				"SelenumLocator": 'Projects_ProjectSettings_LeaveApprovalTab',

				"childTabs": [
					{
						"name": "Normal Time",
						"urlValue": "views/projectsettings/leavecreate.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_LEAVEAPPROVAL_NORMALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_LeaveApprovalTab_NormalTime',

					},
					{
						"name": "Additional Time",
						"urlValue": "views/projectsettings/leaveadditional.html",
						"childTabs": [],
						"appCodeTemplateKey": 'PRJ_PRJSTG_LEAVEAPPROVAL_ADDITIONALTIME_VIEW',
						"SelenumLocator": 'Projects_ProjectSettings_LeaveApprovalTab_AdditionalTime',

					}
				]
			}
		]
	}];
	$scope.generalValues = {};
	$scope.responsibleManagers = [];
	$scope.calenders = [];
	$scope.countries = [];
	$scope.currencies = [];
	$scope.timeZones = [];
	$scope.resourceCurves = [];
	$scope.projectOwners = [];
	$scope.attendDetails = [];
	$scope.projWorkDairys = [];
	$scope.projWorkDairysAppr = [];
	$scope.projTimeSheetData = [];
	$scope.projTimeSheetAppr = [];
	$scope.projProcureDetails = [];
	$scope.projEmpDetails = [];
	$scope.plantTransDetails = [];
	$scope.MaterialTransDtls = [];
	$scope.projEstimateDetails = [];
	var editPerformanceRecords = [];
	$scope.projClaimDetails = [];
	$scope.projPayRollDtls = [];
	$scope.costDatamoreFlag = 0;
	$scope.schofEstimites = [];
	$scope.schofRates = [];
	$scope.resourceBudget = [];
	$scope.changeOrders = [];

	$scope.procureAppr = [];

	var getReq = {
		"status": 1,
		"projId": null
	};
	let defaultAttendEmpAppr = {
		"status": 1,
		"projId": null,
		"projCrewId": null,
		"notification": null,
		"cutOffDays": 0,
		"cutOffHours": 9,
		"cutOffMinutes": 0,
		"fromDate": null,
		"toDate": null,
		"type": "EMP"
	};
	$scope.attendEmpAppr = angular.copy(defaultAttendEmpAppr);

	let defaultAttendPlantAppr = {
		"status": 1,
		"projId": null,
		"projCrewId": null,
		"notification": null,
		"cutOffDays": 0,
		"cutOffHours": 9,
		"cutOffMinutes": 0,
		"fromDate": null,
		"toDate": null,
		"type": "PLANT"
	};

	$scope.attendPlantAppr = angular.copy(defaultAttendPlantAppr);
	
	$scope.schofEstimateAppr = {
		"status": 1,
		"projId": null,
		"notification": null,
		"appUserId": null,
		"notificationId": null,
		"cutofTimeinDays": 5,
		"cutofTimeinHrs": 1800,
		"externalApprover":0,
		"resubmissionType": false,
		"internalType": false,
		"externalType": false
	};
	
 	$scope.schofEstimites = {
		"status": 1,
		"soeNumber": null,
		"projName": null,
		"toUserName": null,
		"appUserId": null,
		"originalType": false,
		"externalType": false,
		"internalType": false
	};	
	
	$scope.schofRatesAppr = {
		"status": 1,
		"projId": null,
		"notification": null,
		"appUserId": null,
		"notificationId": null,
		"cutofTimeinHrs": 1800,
		"cutofTimeinDays": 5,
		"externalApprover": 0,
		"resubmissionType": false,
		"internalType": false,
		"externalType": false
	};
	
	$scope.resourceBudgetAppr = {
		"status": 1,
		"projId": null,
		"notificationNum": 0,
		"notificationId": null,
		"cutofTimeinHrs": 1800,
		"cutofTimeinDays": 5,
		"resubmissionType": false,
		"internalType": false
	};

	$scope.workDiaryAppr = {
		"status": 1,
		"projId": null,
		"notificationId": null,
		"apprUserId": null,
		"workDairyId": null,
		"fromDate": null,
		"toDate": null,
		"notification": null,
		"originalType": false,
		"externalType": false,
		"internalType": false
	};
	$scope.timeSheetAppr = {
		"status": 1,
		"projId": null,
		"timeSheetNo": null,
		"timeSheetDate": null,
		"timeSheetEndDate": null,
		"notification": null,
		"crewtype": null,
		"notificationId": null,
		"apprUserId": null,
		"timeSheetId": null,
		"grantHrs": 0,
		"forSubmission": false,
		"forApproval": false
	};
	$scope.procureAppr = {
		"status": 1,
		"projId": null,
		"procurementId": null,
		"notificationId": null,
		"requisitionDate": null,
		"procureStage1InternalApproval": false,
		"procureStage2InternalApproval": false
	};
	$scope.empTransAppr = [{
		"notificationStatus": 'APPROVED',
		"status": 1,
		"projId": null,
		"notificationId": null,
		"code": null,
		"requisitionDate": null,
		"empTransId": null
	}];
	$scope.plantTransAppr = {
		"status": 1,
		"projId": null,
		"notificationId": null,
		"code": null,
		"requisitionDate": null,
		"empTransId": null
	};
	$scope.materialTransAppr = [{
		"notificationStatus": 'APPROVED',
		"status": 1,
		"projId": null,
		"notificationId": null,
		"code": null,
		"requisitionDate": null,
		"empTransId": null
	}];
	$scope.projctClaimAppr = {
		"status": 1,
		"projId": null,
		"claimId": null,
		"projctClaimAppr": null,
		"claimPeriod": null,
		"originalType": false,
		"externalType": false,
		"internalType": false
	};
	$scope.changeOrders = {
		"status": 1,
		"projName": null,
		"coNumber": null,
		"toUserName": null,
		"appUserId": null,
		"originalType": false,
		"externalType": false,
		"internalType": false
	};

	$scope.projReportsDtls = {
		id: null,
		week: null,
		month: null,
		year: null,
		firstQuarter: null,
		firstHalf: null
	}
	$scope.earnedHoursSource = ['Schedule of Estimate','Schedule of Rate'];

	$scope.projLeaveApprovalAppr = [{
		"notificationStatus": 'APPROVED',
		"status": 1,
		"projId": null,
		"notificationId": null,
		"code": null,
		"requisitionDate": null,
		"requester": null,
		"empTransId": null
	}]
	// Highlighted Leaf
	$scope.setSelected = function (row) {
		$scope.selectedRow = row;
	}
	// end

	$scope.preventCopyPaste = function () {
		$(document).ready(function () {
			$("body").on("contextmenu cut copy paste", function (e) {
				return false;
			});
		});
	},
		/*---------Setting Tabs----------*/

		$scope.editing = false;

	$scope.onClickTab1 = function (innerTab, $index) {
		$scope.editing = true;
		if ($scope.projTabs[0].childTabs[0].urlValue == innerTab.urlValue) {
			$scope.getGeneralValuesOnLoad();
		}else if($scope.projTabs[0].childTabs[1].urlValue == innerTab.urlValue){
			$scope.currentSchofEstimates = $scope.projTabs[0].childTabs[1].childTabs[0].urlValue;
			$scope.getSchofEstimatesOnLoad();
		}else if($scope.projTabs[0].childTabs[2].urlValue == innerTab.urlValue){
			$scope.currentSchofRates = $scope.projTabs[0].childTabs[2].childTabs[0].urlValue;
			$scope.getSchofRatesOnLoad();
		}else if($scope.projTabs[0].childTabs[3].urlValue == innerTab.urlValue){
			$scope.currentResourceBudget = $scope.projTabs[0].childTabs[3].childTabs[0].urlValue;
			$scope.getResourceBudgetOnLoad();	
		} else if ($scope.projTabs[0].childTabs[1+3].urlValue === innerTab.urlValue) {
			$scope.currentEmployeeAttendanceTab = $scope.projTabs[0].childTabs[1+3].childTabs[0].urlValue;
			$scope.getEmpAttendance();
		} else if ($scope.projTabs[0].childTabs[2+3].urlValue === innerTab.urlValue) {
			$scope.currentPlantAttendanceTab = $scope.projTabs[0].childTabs[2+3].childTabs[0].urlValue;
			$scope.getPlantAttendance();
		} else if ($scope.projTabs[0].childTabs[3+3].urlValue === innerTab.urlValue) {
			$scope.currentWorkTab = $scope.projTabs[0].childTabs[3+3].childTabs[0].urlValue;
			$scope.workDairyOnLoadRecords();
		} else if ($scope.projTabs[0].childTabs[4+3].urlValue === innerTab.urlValue) {
			$scope.currentTimeTab = $scope.projTabs[0].childTabs[4+3].childTabs[0].urlValue;
			$scope.timeSheetOnLoadRecords();
		} else if ($scope.projTabs[0].childTabs[5+3].urlValue === innerTab.urlValue) {
			$scope.currentProcurementTab = $scope.projTabs[0].childTabs[5+3].childTabs[0].urlValue;
			$scope.getProcurementRecords();
		} else if ($scope.projTabs[0].childTabs[6+3].urlValue === innerTab.urlValue) {
			$scope.currentEmployeeTab = $scope.projTabs[0].childTabs[6+3].childTabs[0].urlValue;
			$scope.empTransOnLoadRecords();
		} else if ($scope.projTabs[0].childTabs[7+3].urlValue === innerTab.urlValue) {
			$scope.currentPlantTab = $scope.projTabs[0].childTabs[7+3].childTabs[0].urlValue;
			$scope.plantTransOnLoadRecords();
		} else if ($scope.projTabs[0].childTabs[8+3].urlValue === innerTab.urlValue) {
			$scope.currentMaterialTab = $scope.projTabs[0].childTabs[8+3].childTabs[0].urlValue;
			$scope.materialTransOnLoadRecords();
		} else if ($scope.projTabs[0].childTabs[9+3].urlValue === innerTab.urlValue) {
			$scope.getEstimateRecords();
		} else if ($scope.projTabs[0].childTabs[10+3].urlValue === innerTab.urlValue) {
			$scope.getProjPerformenceThresholdRecords();
		} else if($scope.projTabs[0].childTabs[11+3].urlValue === innerTab.urlValue){
			$scope.currentChangeOrderTab = $scope.projTabs[0].childTabs[11+3].childTabs[0].urlValue;
			$scope.getChangeOrderRecords();
		} else if ($scope.projTabs[0].childTabs[12+3].urlValue === innerTab.urlValue) {
			$scope.getProjReportsRecords();
		} else if ($scope.projTabs[0].childTabs[13+3].urlValue === innerTab.urlValue) {
			$scope.currentProgressTab = $scope.projTabs[0].childTabs[13+3].childTabs[0].urlValue;
			$scope.getProjClaimsRecords();
		} else if ($scope.projTabs[0].childTabs[14+3].urlValue === innerTab.urlValue) {
			$scope.getPayRollOnLoadRecords();
		} else if ($scope.projTabs[0].childTabs[15+3].urlValue === innerTab.urlValue) {
			$scope.currentLeaveTab = $scope.projTabs[0].childTabs[15+3].childTabs[0].urlValue;
			$scope.getLeaveApprovalRecords();
		}
		$scope.currentTab1 = innerTab.urlValue;
	}
	$scope.currentTab1 = 'views/projectsettings/generalvalues.html';

	$scope.isActiveTab1 = function (taburlValue) {
		return taburlValue == $scope.currentTab1;
	}, $scope.clickMore1 = function (moreFlag1) {
		if ($scope.moreFlag < 1) {
			$scope.moreFlag = moreFlag1 + 1;
			$scope.onClickTab1($scope.projTabs[0].childTabs[9]);
		}
	}, $scope.clickMore = function (moreFlag1) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag1 - 1;
			$scope.onClickTab1($scope.projTabs[0].childTabs[0]);
		}
	},
		/*---------Schedule of Estimates(new requirement)--------------*/
		$scope.onClickSchofEstimatesTab  = function (ScheduleEstimatesTab){
			$scope.currentSchofEstimates = ScheduleEstimatesTab.urlValue;
		}, $scope.isActiveScheduleEstimatesTab = function(ScheduleEstimatesTaburlValue){
			return ScheduleEstimatesTaburlValue == $scope.currentSchofEstimates;
		},
		/*---------Schedule of Rates(new requirement)------------------*/
		$scope.onClickSchofRates = function(ScheduleRatesTab){
			$scope.currentSchofRates = ScheduleRatesTab.urlValue;
		},$scope.isActiveScheduleRatesTab = function(ScheduleRatesTaburlValue){
			return ScheduleRatesTaburlValue == $scope.currentSchofRates;
		},
		/*---------Resource Budget(new requirement)--------------------*/
		$scope.onClickofResourceBudget = function(ResourceBudgetTab){
			$scope.currentResourceBudget = ResourceBudgetTab.urlValue;
		}, $scope.isActiveResourceBudgetTab = function(ResourceBudgetTaburlValue){
			return ResourceBudgetTaburlValue == $scope.currentResourceBudget
		},
		/*---------Employee Attendance Tabs----------*/
		$scope.onClickEmployeeAttendanceTab = function (employeeAattendanceTabs) {
			$scope.currentEmployeeAttendanceTab = employeeAattendanceTabs.urlValue;
		}, $scope.isActiveEmployeeAttendanceTab = function (employeeAattendanceTaburlValue) {
			return employeeAattendanceTaburlValue == $scope.currentEmployeeAttendanceTab;
		},

		/*---------Plant Attendance Tabs----------*/
		$scope.onClickPlantAttendanceTab = function (plantAattendanceTabs) {
			$scope.currentPlantAttendanceTab = plantAattendanceTabs.urlValue;
		}, $scope.isActivePlantAttendanceTab = function (plantAattendanceTaburlValue) {
			return plantAattendanceTaburlValue == $scope.currentPlantAttendanceTab;
		},

		/*---------Work Diary Tabs----------*/
		$scope.onClickWorkTab = function (workTabs) {
			$scope.currentWorkTab = workTabs.urlValue;
		}, $scope.isActiveWorkTab = function (workTaburlValue) {
			return workTaburlValue == $scope.currentWorkTab;
		},

		/*---------Time Sheet Tabs----------*/
		$scope.onClickTimeTab = function (timeTabs) {
			$scope.currentTimeTab = timeTabs.urlValue;
		}, $scope.isActiveTimeTab = function (timeTaburlValue) {
			return timeTaburlValue == $scope.currentTimeTab;
		},

		/*---------Procurement Tabs----------*/
		$scope.onClickProcurementTab = function (procurementTabs) {
			$scope.currentProcurementTab = procurementTabs.urlValue;
		}, $scope.isActiveProcurementTab = function (procurementTaburlValue) {
			return procurementTaburlValue == $scope.currentProcurementTab;
		},

		/*---------Employee Transfer Tabs----------*/
		$scope.onClickEmployeeTab = function (employeetransferTabs) {
			$scope.currentEmployeeTab = employeetransferTabs.urlValue;
		}, $scope.isActiveEmployeeTab = function (employeetransferTaburlValue) {
			return employeetransferTaburlValue == $scope.currentEmployeeTab;
		},

		/*---------Plant Transfer Tabs----------*/
		$scope.onClickPlantTab = function (planttransferTabs) {
			$scope.currentPlantTab = planttransferTabs.urlValue;
		}, $scope.isActivePlantTab = function (planttransferTaburlValue) {
			return planttransferTaburlValue == $scope.currentPlantTab;
		},

		/*---------Material Transfer Tabs----------*/
		$scope.onClickMaterialTab = function (materialtransferTabs) {
			$scope.currentMaterialTab = materialtransferTabs.urlValue;
		}, $scope.isActiveMaterialTab = function (materialtransferTaburlValue) {
			return materialtransferTaburlValue == $scope.currentMaterialTab;
		},

		/*------------Progress Tabs-----------*/
		$scope.onClickProgressTab = function (progressTabs) {
			$scope.getProjClaimsRecords();
			$scope.currentProgressTab = progressTabs.urlValue;
		}, $scope.isActiveProgressTab = function (progressTaburlValue) {
			return progressTaburlValue == $scope.currentProgressTab;
		},

		/*------------Leave Approval tabs-----------*/
		$scope.onClickLeaveTab = function (leaveTabs) {
			$scope.currentLeaveTab = leaveTabs.urlValue;
		}, $scope.isActiveLeaveTab = function (leaveTaburlValue) {
			return leaveTaburlValue == $scope.currentLeaveTab;
		},
		
		/*-----------Change Order tabs */
		$scope.onClickChangeOrderTab = function(changeTabs){
			$scope.currentChangeOrderTab = changeTabs.urlValue;
		},$scope.isActiveChangeTab = function(changeTaburlValue){
			return changeTaburlValue == $scope.currentChangeOrderTab;
		},

		/* EPS Projects */

		$scope.openSettings = function (row, projObj) {
			//console.log("===openSettings======")
			$rootScope.projId = row;
			$scope.setSelected(projObj);
			$scope.onClickTab1($scope.projTabs[0].childTabs[0]);
			//$scope.getGeneralValuesOnLoad();
			$scope.ProjGeneralMap();
			if ($scope.moreFlag > 0) {
				$scope.clickMore($scope.moreFlag);
			}
		},

		$scope.getEPSDetails = function () {
			var epsReq = {
				"status": 1
			};
			EpsService.getEPSUserProjects(epsReq).then(function (data) {
				$scope.epsData = populateData(data.epsProjs);
				$scope.epsData.map(eps => {
					$scope.itemClick(eps, false);
				});
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
			});
		};

	var populateData = function (data) {
		return TreeService.populateTreeData(data, 0, [], 'projCode', 'childProjs');
	}

	$scope.itemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childProjs');
	};

	/* Setting Tabs */

	/* General Values Tab */

	$scope.ProjGeneralMap = function () {
		var getReq = {
			"status": 1,
			"projId": $rootScope.projId
		};
		ProjectSettingsService.getProjGeneralMap(getReq).then(function (data) {
			$scope.uniqueProjGeneralMap = data.projSettingsUniqueMap;
		});
	}

	CountryService.getCountries().then(function (data) {
		$scope.countries = data.countryInfoTOs;
		//console.log("====$scope.countries====",$scope.countries)
	}, function (error) {
		GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
	});

	let setResourceCurve = function (curvesList, resourceCurveId) {
		//console.log("setResourceCurve function");
		curvesList.map(curve => {
			if (curve.id === resourceCurveId) {
				$scope.generalValues.resourceCurveTO = curve;
			}
		});
	}

	$scope.getGeneralValuesOnLoad = function () {
		//console.log("===getGeneralValuesOnLoad=====")
		if ($rootScope.projId == null || $rootScope.projId == undefined) {
			GenericAlertService.alertMessage("Please select the Project", "Info");
			return;
		}
		//console.log("===$rootScope.projId===",$rootScope.projId)
		//console.log("===$scope.countryId===",$scope.countryId)
		var getGVOnLoadReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"countryId": $scope.countryId
		};
		ProjectSettingsService.projGeneralOnLoad(getGVOnLoadReq).then(function (data) {
			$scope.generalValues = data.projGeneralMstrTO;	
			//$scope.count=$scope.generalValues.userLabelKeyTO.name.length;//used for performance threshold tab			
			$scope.count = ( $scope.generalValues.userLabelKeyTO.name != null ) ? $scope.generalValues.userLabelKeyTO.name.length : 0;
			$scope.responsibleManagers = data.users;
			if ($scope.generalValues.earnedHourSource == 'R')
				$scope.generalValues.earnedHourSource = 'Schedule of Rate';
			else if ($scope.generalValues.earnedHourSource == 'E')
				$scope.generalValues.earnedHourSource = 'Schedule of Estimate';
			if ($scope.generalValues.contractType == "SORcontract")
	        	$scope.earnedHoursSource = ['Schedule of Estimate','Schedule of Rate'];
	        else
	        	$scope.earnedHoursSource = ['Schedule of Estimate'];
       if(angular.isUndefined($scope.generalValues.contractType) || $scope.generalValues.contractType === null || $scope.generalValues.contractType == null)
       {
          $scope.generalValues.contractType = "SORcontract";
       }

       if(angular.isUndefined($scope.generalValues.percentOverCost) || $scope.generalValues.percentOverCost === null || $scope.generalValues.percentOverCost == null)
        {
           $scope.generalValues.percentOverCost=0;
        }

      $scope.isPercentOverCostAllowed = !angular.equals($scope.generalValues.contractType,"CPPTypecontract");

			$scope.empReqTransDetails = data.empReqTransTOs;
			$scope.resourceCurves = data.projresourceCurveTOs;
			setResourceCurve($scope.resourceCurves, $scope.generalValues.resourceCurveTO.id);
			$scope.projectOwners = data.companyTOs;
			$scope.calenders = data.CalenderTOs;
			//console.log("===$scope.generalValues.countryCode====",$scope.generalValues.isoAlpha3.countryCode)
			//console.log("===country.countryCode====",country.countryCode)
			angular.forEach($scope.countries, function (country, key) {
				//console.log("===/////////country//////===",country)
				//console.log("===country.isoAlpha3===",country.isoAlpha3)
				//console.log("===$scope.generalValues.isoAlpha3===",$scope.generalValues.isoAlpha3)
				//if (country.isoAlpha3 == $scope.generalValues.isoAlpha3) {
					if (country.countryCode == $scope.generalValues.isoAlpha3) {
					//console.log("********country******",country)
					$scope.generalValues.isoAlpha3 = country;
				}
			});
			$scope.getCountryDetailsById($scope.generalValues.isoAlpha3);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting General Values", "Error");
		});
	},

		$scope.companyList = function (companyTO) {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var companyListService = {};
			var onLoadReq = {
				"status": 1
			};
			var companyListPopup = CompanyListPopUpFactory.getCompanies(onLoadReq);
			companyListPopup.then(function (data) {
				companyTO.id = data.selectedRecord.id;
				companyTO.cmpName = data.selectedRecord.cmpName;
				companyTO.regNo = data.selectedRecord.regNo;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting company list details", 'Error');
			});
		},

		$scope.getProfitCentres = function (profitCentreTO) {
			var profitCentreService = {};
			var onLoadReq = {
				"status": 1
			};
			var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
			profitCentrePopup.then(function (data) {
				profitCentreTO.id = data.selectedRecord.id;
				profitCentreTO.code = data.selectedRecord.code;
				profitCentreTO.name = data.selectedRecord.name;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
			});
		},
		
		$scope.getFinanceCentre = function(generalValues){
			var req = {
				"status": 1,
       			"countryCode": generalValues.provisionName.countryCode,
       			"provinceCode":generalValues.provisionName.name
			}
			var financeCentrePopup = FinanceCentrePopUpFactory.getFinancecentre(req);
			financeCentrePopup.then(function (data) {
			$scope.generalValues.financeCentre = data.selectedSubcategory.displayFinanceCenterId;
			})
		}
		
		$scope.getCountryDetailsById = function (country) {
			console.log("==country==",country)
			if (country) {
				if (country.currencyCode) {
					$scope.generalValues.currency = country.currencyCode;
				}
				if (!$scope.generalValues.provisionName) {
					$scope.generalValues.timezone = '';
				}

				const req = { "geonameId": country.geonameId };
				//const req = { "geonameId": 2077456 };
				//console.log("***country.geonameId***",country.geonameId)
				CountryService.getProvince(req).then(function (data) {
					//console.log(data, "province");
					
					$scope.generalValues.provisionTOs = data.provisionTOs;
					//console.log("$scope.generalValues.provisionTOs", $scope.generalValues.provisionTOs);
					//console.log("***provision***",provision)
					angular.forEach($scope.generalValues.provisionTOs, function (provision, key) {
						if (provision.name == $scope.generalValues.provisionName) {
							//console.log("provision.name", provision.name);
							//console.log("$scope.generalValues.provisionName", $scope.generalValues.provisionName);
							$scope.generalValues.provisionName = provision;
							//console.log("==$scope.generalValues.provisionName==",$scope.generalValues.provisionName)
						}
					});
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Province Details", "Error");
				});
			}

		},
		$scope.getTimeZone = function (province) {
			const req = {
				"lat": province.lat,
				"lng": province.lng
			};
			CountryService.getTimeZone(req).then(function (data) {
				console.log(data, "timezone");
				$scope.generalValues.timezone = data.gmtOffset;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeZone", "Error");
			});

		},

		$scope.projId = $rootScope.projId;
	$scope.activeFlag = 0;
	$scope.selectCalendars = function (calenderTO, projCalenderTO) {
		if ($rootScope.projId == null || $rootScope.projId == undefined) {
			GenericAlertService.alertMessage("Please select the Project", "Info");
			return;
		}
		$scope.projId = $rootScope.projId;
		var calendarPopup = GeneralValuesCalendarFactory.getCalendars($scope.projId);
		calendarPopup.then(function (data) {
			$scope.activeFlag = data.type;
			$scope.generalValues.globalCalenderTO = data.CalendarTO;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Store Delivery Details", 'Error');
		});
	},

		$scope.getApproverDetails = function (userLabelKeyTO) {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var approverSerivcePopup = [];
			approverSerivcePopup = ProjectCrewPopupService.approverDetailsList();
			approverSerivcePopup.then(function (data) {
				userLabelKeyTO.id = data.projApproverTO.userId;
				userLabelKeyTO.name = data.projApproverTO.firstName;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Employee List Details", 'Error');
			});
		},


     $scope.updateContractType = function (tab,data) {
          $scope.isPercentOverCostAllowed = !angular.equals(data,"CPPTypecontract");
          if($scope.isPercentOverCostAllowed)
            tab.percentOverCost = "";
          
          if (data == "SORcontract")
        	  $scope.earnedHoursSource = ['Schedule of Estimate','Schedule of Rate'];
          else
        	  $scope.earnedHoursSource = ['Schedule of Estimate'];
     },

		$scope.saveGeneralValues = function () {
			var flag = false;
			if ($scope.uniqueProjGeneralMap[$scope.generalValues.contractNumber.toUpperCase()] != null) {
				//flag = true;
				$scope.generalValues.dupicteFlag = true;
			} else {
				$scope.generalValues.dupicteFlag = false;
				flag = false;
				$scope.uniqueProjGeneralMap[$scope.generalValues.contractNumber.toUpperCase()] = true;
			}
			if (flag) {
				GenericAlertService.alertMessage('Duplicate Contract Numbers are not allowed', "Info");
				return;
			}
			if ($scope.generalValues.defualtHrs <= $scope.generalValues.maxHrs) {
				$scope.generalValues.projId = $rootScope.projId
				if ($scope.activeFlag == 0) {
					if ($scope.generalValues.calenderTO)
						$scope.generalValues.globalCalId = $scope.generalValues.calenderTO.id;
				}
				if ($scope.generalValues.globalCalenderTO != undefined) {
					$scope.generalValues.calenderTO = $scope.generalValues.globalCalenderTO
					$scope.generalValues.globalCalId = $scope.generalValues.globalCalenderTO.id;
				}

				var values = angular.copy($scope.generalValues);
				values.countryName = values.isoAlpha3.countryName;
				console.log("==values.countryName==",values.countryName)
				values.geonameId = values.isoAlpha3.geonameId;
				console.log("==values.geonameId==",values.geonameId)
				//values.isoAlpha3 = values.isoAlpha3.isoAlpha3;
				//console.log("==values.isoAlpha3==",values.isoAlpha3)
				values.isoAlpha3 = values.isoAlpha3.countryCode;
				console.log("values.isoAlpha3.countryCode")
				values.provisionName = values.provisionName.name;
				console.log("==values.provisionName==",values.provisionName)
				values.contractType = $scope.generalValues.contractType;
				values.percentOverCost = $scope.generalValues.percentOverCost;
				/*if(values.resourceCurveTO.curveType == undefined){
				GenericAlertService.alertMessage('Please select Resource Curve', "Info");
        				return;
        		};*/
				if (values.earnedHourSource == 'Schedule of Estimate') values.earnedHourSource = 'E';
				if (values.earnedHourSource == 'Schedule of Rate') values.earnedHourSource = 'R';

				if (parseFloat(values.percentOverCost)>100) {
        				GenericAlertService.alertMessage('% Over Cost should be less than or equal to 100', "Info");
        				return;
				}
				var saveReq = {"projGeneralMstrTO": values};

				blockUI.start();
				ProjectSettingsService.saveProjGeneralValues(saveReq).then(function (data) {
					blockUI.stop();
					GenericAlertService.alertMessage('General Value(s) saved successfully', "Info");
					$scope.activeFlag = 0;
					$scope.getGeneralValuesOnLoad();
				}, function (error) {
					blockUI.stop();
					GenericAlertService.alertMessage('General Value(s) failed to save', "Error");
				});
			} else {
				blockUI.stop();
				GenericAlertService.alertMessage("Default Hours Per Day should be Less Than or Equal To Maximum Hours Per Day", "Error");
			}
		},
		/*Schedule of Estimates Tab*/
		$scope.getSchofEstimatesOnLoad = function(){
			if($rootScope.projId == null || $rootScope.projId == undefined){
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var estireq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			console.log(estireq)
			ProjectSettingsService.projScheduleEstimatesOnLoad(estireq).then(function (data){
				console.log(data.schofEstimatesApprTOs)
				$scope.schofEstimites = data.schofEstimatesApprTOs;				
				$scope.cutOffDays = $scope.schofEstimites[0].cutOffDays
				$scope.cutoffHours = $scope.schofEstimites[0].cutOffHours
				$scope.cutOffMinutes = $scope.schofEstimites[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
 
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Schedule of Estimate(s) Details", "Error");
			});
		},
		/* Schedule of Estimates save */
		$scope.saveSchEstimate = function(){
			
			var saveEstimatesReq = {
				"schofEstimatesApprTOs": $scope.schofEstimites
			};
			console.log(saveEstimatesReq)
			blockUI.start();
			ProjectSettingsService.saveEstimates(saveEstimatesReq).then(function (data){
				blockUI.stop();
				GenericAlertService.alertMessage('Schedule of Estimate(s) saved successfully', "Info");
			},function(error){
				blockUI.stop();
				GenericAlertService.alertMessage('Schedule of Estimate(s) failed to save',"Error");
			});
		}
			/*Schedule of Rates */
		$scope.getSchofRatesOnLoad = function(){
			if($rootScope.projId == null || $rootScope.projId == undefined){
				GenericAlertService.alertMessage("Please select the Project","Info");
				return;
			}
			var ratesReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			console.log(ratesReq)
			ProjectSettingsService.projScheduleofRatesOnLoad(ratesReq).then(function (data){
				$scope.schofRates = data.schofRatesApprTOs;
				console.log(data.schofRatesAppTOs)
				$scope.cutOffDays = $scope.schofRates[0].cutOffDays
				$scope.cutoffHours = $scope.schofRates[0].cutOffHours
				$scope.cutOffMinutes = $scope.schofRates[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
 
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Schedule of Rates(s) Details", "Error");
			});
		},
		/*Schedule of Rate save*/
		$scope.saveSchofRate = function(){
			var saveRate = {
				"schofRatesApprTOs": $scope.schofRates
			};
			console.log(saveRate)
			blockUI.start();
			ProjectSettingsService.saveSchRates(saveRate).then(function (data){
				console.log(data)
				blockUI.stop();
				GenericAlertService.alertMessage('Schedule of Rate(s) saved successfully', "Info");
			}, function(error){
				blockUI.stop();
				GenericAlertService.alertMessage('Schedule of Rate(s) failed to save',"Error");
			})
		},
		/*Resource Budget */
		$scope.getResourceBudgetOnLoad = function(){
			if($rootScope.projId == null || $rootScope.projId == undefined){
				GenericAlertService.alertMessage("Please select the Project","Info");
				return;
			}
			var resourceReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			console.log(resourceReq)
			ProjectSettingsService.ProjResourceBudgetOnLoad(resourceReq).then(function (data){
				$scope.resourceBudget = data.resourceBudgetTOs
				console.log(data.resourceBudgetTOs);
				$scope.cutOffDays = $scope.resourceBudget[0].cutOffDays
				$scope.cutoffHours = $scope.resourceBudget[0].cutOffHours
				$scope.cutOffMinutes = $scope.resourceBudget[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Resource Budget Details", "Error");
			});
		},
		$scope.saveResourceBudget = function(){
			var saveResource = {
				"resourceBudgetTOs": $scope.resourceBudget
			};
				console.log(saveResource) 
				blockUI.start();
				ProjectSettingsService.SaveResourceBudget(saveResource).then(function (data){
				blockUI.stop();
				GenericAlertService.alertMessage('Resource Budget(s) saved successfully', "Info");
			},function(error){
				GenericAlertService.alertMessage("Resource Budget failed to save", "Error");
			})
		},
		/* Employee Records Tab */
		/* Create Employee Records Tab */
		$scope.getEmpAttendance = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var empreq = {
				"status": 1,
				"projId": $rootScope.projId,
				"type": "EMP"
			};
			ProjectSettingsService.projAttendence(empreq).then(function (data) {
				$scope.empAttendDetails = data.projAttendenceTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Employee Attendance Details", "Error");
			});
		}, $scope.saveProjEmpAttendence = function () {
			var saveAttenReq = {
				"projAttendenceTOs": $scope.empAttendDetails,
				"type": "EMP"
			};
			blockUI.start();
			ProjectSettingsService.saveProjAttendence(saveAttenReq).then(function (data) {
				blockUI.stop();
				$scope.getEmpAttendance();
				GenericAlertService.alertMessage('Employee Attendance(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Attendance(s) is/are failed to save', "Error");
			});
		}, $scope.getAttendanceNotificDetails = function () {
			var req = {
				"projId": $scope.projId,
				"status": 1
			};
			if (req.projId == null || req.status == undefined) {
				GenericAlertService.alertMessage("Please select Project/EPS", 'Info');
				return;
			}
			NotificationService.getAttendanceNotifications(req).then(function (data) {
				$scope.attendanceNotification = data.attendenceNotificationsTOs;
				if ($scope.attendanceNotification == null || $scope.attendanceNotification.length <= 0) {
					GenericAlertService.alertMessage("There are no records avaialable for the selected project/EPS", 'Info');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
			});

		}, $scope.updatePlantCode = function (data, attendAppr) {
			attendAppr.notificationId = data.id;
		},
		/* Approval Employee Attendance Records Tab */
		$scope.saveProjEmpAttendenceAppr = function () {
			if ($scope.attendEmpAppr.notification == undefined || $scope.attendEmpAppr.notification == null) {
				GenericAlertService.alertMessage('Please Select notification for Employee Attendance', "Info");
				return;
			}
			if ($scope.attendEmpAppr.fromDate == undefined || $scope.attendEmpAppr.fromDate == null) {
				GenericAlertService.alertMessage('Please Select Employee Attendance From Date', "Info");
				return;
			}
			if ($scope.attendEmpAppr.toDate == undefined || $scope.attendEmpAppr.toDate == null) {
				GenericAlertService.alertMessage('Please Select Employee Attendance To Date', "Info");
				return;
			}
			$scope.attendEmpAppr.projAttenId = $scope.empAttendDetails[0].id;
			$scope.attendEmpAppr.projId = $rootScope.projId;
			var saveAttenReq = {
				"projAttendceApprTOs": [$scope.attendEmpAppr]

			};
			blockUI.start();
			ProjectSettingsService.saveProjAttendenceAppr(saveAttenReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Attendance Approval(s) saved successfully', "Info");
				$scope.attendEmpAppr = angular.copy(defaultAttendEmpAppr);
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Attendence Approval(s) is/are failed to save', "Error");
			});
		},

		/* Plant Records Tab */
		/* Create Attendance Records Tab */
		$scope.getPlantAttendance = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var plantreq = {
				"status": 1,
				"projId": $rootScope.projId,
				"type": "PLANT"
			};
			ProjectSettingsService.projAttendence(plantreq).then(function (data) {
				$scope.plantAttendDetails = data.projAttendenceTOs[0];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Attendance Details", "Error");
			});
		}, $scope.saveProjPlantAttendence = function () {
			var saveAttenReq = {
				"projAttendenceTOs": [$scope.plantAttendDetails],
				"type": "PLANT"
			};
			blockUI.start();
			ProjectSettingsService.saveProjAttendence(saveAttenReq).then(function (data) {
				blockUI.stop();
				$scope.getPlantAttendance();
				GenericAlertService.alertMessage('Plant Attendance(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Plant Attendance(s) is/are failed to save', "Error");
			});
		},
		/* Approval Plant Attendance Records Tab */
		$scope.saveProjPlantAttendenceAppr = function () {
			if ($scope.attendPlantAppr.notification == undefined || $scope.attendPlantAppr.notification == null) {
				GenericAlertService.alertMessage('Please Select notification for Plant Attendance', "Info");
				return;
			}
			if ($scope.attendPlantAppr.fromDate == undefined || $scope.attendPlantAppr.fromDate == null) {
				GenericAlertService.alertMessage('Please Select Plant Attendance From Date', "Info");
				return;
			}
			if ($scope.attendPlantAppr.toDate == undefined || $scope.attendPlantAppr.toDate == null) {
				GenericAlertService.alertMessage('Please Select Plant Attendance To Date', "Info");
				return;
			}
			$scope.attendPlantAppr.projAttenId = $scope.plantAttendDetails.id;
			$scope.attendPlantAppr.projId = $rootScope.projId;
			var saveAttenReq = {
				"projAttendceApprTOs": [$scope.attendPlantAppr]
			};
			blockUI.start();
			ProjectSettingsService.saveProjAttendenceAppr(saveAttenReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Attendence Approval(s) is/are saved successfully', "Info");
				$scope.attendPlantAppr = angular.copy(defaultAttendPlantAppr);
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Attendence Approval(s) is/are failed to save', "Error");
			});

		},

		/* Dairy Tab */
		$scope.workDairyOnLoadRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var workOnLoadReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.projWorkDairyOnLoad(workOnLoadReq).then(function (data) {
				$scope.projWorkDairys = data.projWorkDairyMstrTOs;
				$scope.cutOffDays = $scope.projWorkDairys[0].cutOffDays
				$scope.cutoffHours = $scope.projWorkDairys[0].cutOffHours
				$scope.cutOffMinutes = $scope.projWorkDairys[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Work Dairy Details", "Error");
			});
		},

		$scope.saveWorkDairy = function () {
			var saveAttenReq = {
				"projWorkDairyMstrTOs": $scope.projWorkDairys
			};
			blockUI.start();
			ProjectSettingsService.saveWorkDairy(saveAttenReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Work Diary(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Work Dairy(s) is/are failed to save', "Error");
			});
		}
	/* Approval Work Dairy Tab */

	$scope.saveWorkDairyApprs = function () {
		var projWorkDairysAppr = [];

		if ($scope.workDiaryAppr.fromDate == undefined || $scope.workDiaryAppr.fromDate == null) {
			GenericAlertService.alertMessage('Please Select Work Diary Date', "Info");
			return;
		}
		if ($scope.workDiaryAppr.notification == undefined || $scope.workDiaryAppr.notification == null) {
			GenericAlertService.alertMessage('Please Select notification', "Info");
			return;
		}
		var flag = false;
		if ($scope.workDiaryAppr.originalType) {
			$scope.workDiaryAppr.workDairyId = $scope.projWorkDairys[0].id;
			$scope.workDiaryAppr.projId = $rootScope.projId;
			projWorkDairysAppr.push(angular.copy($scope.workDiaryAppr));
			flag = true;

		}
		if ($scope.workDiaryAppr.internalType) {
			$scope.workDiaryAppr.workDairyId = $scope.projWorkDairys[1].id;
			$scope.workDiaryAppr.projId = $rootScope.projId;
			projWorkDairysAppr.push(angular.copy($scope.workDiaryAppr));
			flag = true;
		}
		if ($scope.workDiaryAppr.externalType) {
			$scope.workDiaryAppr.workDairyId = $scope.projWorkDairys[2].id;
			$scope.workDiaryAppr.projId = $rootScope.projId;
			projWorkDairysAppr.push(angular.copy($scope.workDiaryAppr));
			flag = true;
		}
		if (!flag) {
			GenericAlertService.alertMessage('Please  select Original Type or Internal Type or External Type', "Info");
			return;
		}

		var saveWorkDairyApprReq = {
			"projWorkDairyApprTOs": projWorkDairysAppr
		};

		blockUI.start();
		ProjectSettingsService.saveWorkDairyApprs(saveWorkDairyApprReq).then(function (data) {
			blockUI.stop();
			GenericAlertService.alertMessage('Work Dairy Approval(s) is/are saved successfully', "Info");
			$scope.workDiaryAppr = {
				"status": 1,
				"projId": null,
				"projCrewId": null,
				"apprUserId": null,
				"workDairyId": null,
				"workDairyDate": null,
				"originalType": false,
				"externalType": false,
				"internalType": false
			};
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Work Dairy Approval(s) is/are failed to save', "Error");
		});
	},
		/* Time Sheet Tab */
		/* Create Time Sheet Tab */
		$scope.timeSheetOnLoadRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var timeSheetOnLoadReq = {
				"status": 1,
				"projId": $rootScope.projId,
				"projTimeSheetWeekDtlTO.projId": $rootScope.projId
			};

			ProjectSettingsService.projTimeSheetOnLoad(timeSheetOnLoadReq).then(function (data) {
				$scope.projCommencingDays = data.projTimeSheetResp.projTimeSheetWeekDtlTO;
				$scope.projTimeSheetData = data.projTimeSheetResp.projTimeSheetTOs;
				$scope.weekDays = data.weekDays;
				$scope.cutOffDays = $scope.projTimeSheetData[0].cutOffDays
				$scope.cutoffHours = $scope.projTimeSheetData[0].cutOffHours
				$scope.cutOffMinutes = $scope.projTimeSheetData[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Time Sheet Details", "Error");
			});
		}, $scope.saveProjTimeSheet = function () {
			var saveTimeSheetReq = {
				"projTimeSheetTOs": $scope.projTimeSheetData,
				"projTimeSheetWeekDtlTO": $scope.projCommencingDays,
				"projId": $rootScope.projId,
				"projTimeSheetWeekDtlTO.projId": $rootScope.projId
			};

			blockUI.start();
			ProjectSettingsService.saveProjTimeSheet(saveTimeSheetReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Time Sheet(s) saved successfully', "Info");
				$scope.timeSheetOnLoadRecords();
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Time Sheet(s) is/are failed to save', "Error");
			});
		},
		/* Approval Time Sheet Tab */

		$scope.saveProjTimeSheetAppr = function () {
			var projTimeSheetAppr = [];

			if ($scope.timeSheetAppr.notification == undefined || $scope.timeSheetAppr.notification == null) {
				GenericAlertService.alertMessage('Please Select Crew Code', "Info");
				return;
			}
			if ($scope.timeSheetAppr.timeSheetDate == undefined || $scope.timeSheetAppr.timeSheetDate == null) {
				GenericAlertService.alertMessage('Please Select Time Sheet Date', "Info");
				return;
			}
			var flag = false;
			if ($scope.timeSheetAppr.forSubmission) {
				$scope.timeSheetAppr.timeSheetId = $scope.projTimeSheetData[0].id;
				$scope.timeSheetAppr.timeSheetEndDate = $scope.newDate;
				$scope.timeSheetAppr.projId = $rootScope.projId;
				projTimeSheetAppr.push(angular.copy($scope.timeSheetAppr));
				flag = true;

			}
			if ($scope.timeSheetAppr.forApproval) {
				$scope.timeSheetAppr.timeSheetId = $scope.projTimeSheetData[1].id;
				$scope.timeSheetAppr.timeSheetEndDate = $scope.newDate;
				$scope.timeSheetAppr.projId = $rootScope.projId;
				projTimeSheetAppr.push(angular.copy($scope.timeSheetAppr));
				flag = true;
			}

			if (!flag) {
				GenericAlertService.alertMessage('Please  select For Submission or For Approval', "Info");
				return;
			}

			var saveTimeSheetReq = {
				"projTimeSheetApprTOs": projTimeSheetAppr
			};
			blockUI.start();
			ProjectSettingsService.saveProjTimeSheetAppr(saveTimeSheetReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Time Sheet Approval(s) is/are saved successfully', "Info");
				$scope.timeSheetAppr = {
					"status": 1,
					"projId": null,
					"timeSheetNo": null,
					"timeSheetDate": null,
					"grantHrs": 0,
					"timeSheetEndDate": null,
					"projCrewId": null,
					"apprUserId": null,
					"timeSheetId": null,
					"forSubmission": false,
					"forApproval": false
				};
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Time Sheet Approval(s) is/are failed to save', "Error");
			});
		},
		
		$scope.saveSoeApprs = function(){
			var projSoeAppr = [];
			
			projSoeAppr.push(angular.copy($scope.schofEstimateAppr))
			var saveSoeApprReq = {
			"projSoeApprTOs": projSoeAppr
		    };	
          console.log(saveSoeApprReq);
		  ProjectSettingsService.saveSoeApprs(saveSoeApprReq).then(function (data) {
			 console.log(saveSoeApprReq);
		   GenericAlertService.alertMessage('SOE Approval(s) is/are saved successfully', "Info");
        $scope.schofEstimites = {
		"status": 1,
		"soeNumber": null,
		"projName": null,
		"toUserName": null,
		"appUserId": null,
		"originalType": false,
		"externalType": false,
		"internalType": false
	};	
          }, function(error) {
	       GenericAlertService.alertMessage('SOE Approval(s) is/are failed to save', "Error");
		  });  
		}	
		/* Procurement Tab */
		/* Create Procurement Tab */

		$scope.getProcurementRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var getProcureReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.getProjProcurements(getProcureReq).then(function (data) {
				$scope.projProcureDetails = data.projProcurementTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Procurement Details", "Error");
			});
		}, $scope.saveProjProcurements = function () {
			var saveProjProcureReq = {
				"projProcurementTOs": $scope.projProcureDetails
			};
			blockUI.start();
			ProjectSettingsService.saveProjProcurements(saveProjProcureReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Procurement(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Procurement(s) is/are failed to save', "Error");
			});
		},
		/* Approval Procurement Tab */
		$scope.saveProjProcurementAppr = function () {

			var projProcurementList = [];			
			if ($scope.procureAppr.code == undefined || $scope.procureAppr.code == null) {
				GenericAlertService.alertMessage('Please  select requistion number ', "Info");
				return;
			}
			if ($scope.procureAppr.requisitionDate == undefined || $scope.procureAppr.requisitionDate == null) {
				GenericAlertService.alertMessage('Please  select requistion date', "Info");
				return;
			}
			
			var flag = false;
			if ($scope.procureAppr.stage) {
				flag = true;
			}

			if (!flag) {
				GenericAlertService.alertMessage('Please  select Stage1 Internal Approval or  Stage2 Internal Approval  ', "Info");
				return;
			}
			projProcurementList.push(angular.copy($scope.procureAppr));
			var saveReq = {
				"projProcurementApprTOs": projProcurementList
			};
			console.log(saveReq);
			blockUI.start();
			ProjectSettingsService.saveProjProcurementAppr(saveReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Procurement Approval(s) is/are saved successfully', "Info");
				$scope.procureAppr = {
					"status": 1,
					"projId": null,
					"procurementId": null,
					"notificationId": null,
					"requisitionDate": null,
					"procureStage1InternalApproval": false,
					"procureStage2InternalApproval": false,
				};
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Procurement Approval(s) is/are failed to save', "Error");
			});
		},
		/* Employee Transfer Tab */
		/* Create Employee Transfer Tab */
		$scope.empTransOnLoadRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var empTransOnLoadReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.projEmpTransOnLoad(empTransOnLoadReq).then(function (data) {
				$scope.projEmpDetails = data.projEmpTransTOs;
				$scope.cutOffDays = $scope.projEmpDetails[0].cutOffDays
				$scope.cutoffHours = $scope.projEmpDetails[0].cutOffHours
				$scope.cutOffMinutes = $scope.projEmpDetails[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Employee Transfer Details", "Error");
			});
		}, $scope.saveEmpTransDetails = function () {
			var saveEmpTransReq = {
				"projEmpTransTOs": $scope.projEmpDetails
			};
			blockUI.start();
			ProjectSettingsService.saveEmpTransDetails(saveEmpTransReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Detail(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Detail(s) is/are failed to save', "Error");
			});
		},
		/* Approval Employee Transfer Tab */
		$scope.saveEmpTransAppr = function (empTransAppr) {
			Date.prototype.addDays = function (numdays) {
				this.setDate(this.getDate() + parseInt(numdays));
				return this;
			};
			var fromDate = new Date();
			var endDay = fromDate;
			endDay.addDays($scope.numdays)
			$scope.newDate = $filter('date')((endDay), "dd-MMM-yyyy");
			$scope.empTransAppr[0].requisitionDate = $scope.newDate;
			$scope.empTransAppr[0].empTransId = $scope.projEmpDetails[0].id
			$scope.empTransAppr[0].empTransId = $scope.projEmpDetails[0].projId
			$scope.empTransAppr[0].notificationId = $scope.empTransAppr.notificationId;
			var saveEmpTransApprReq = {
				"projEmpTransApprTOs": $scope.empTransAppr
			};
			blockUI.start();
			console.log(saveEmpTransApprReq);
			//return;
			ProjectSettingsService.saveEmpTransAppr(saveEmpTransApprReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Approval(s) is/are saved successfully', "Info");
				$scope.empTransAppr = [{
					"status": 1,
					"projId": null,
					"notificationId": null,
					"code": null,
					"requisitionDate": null,
					"empTransId": null
				}];
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Employee Approval(s) is/are failed to save', "Error");
			});
		},
		/* Plant Transfer Tab */
		/* Create Plant Transfer Tab */
		$scope.plantTransOnLoadRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var plantTransOnLoadReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.projPlantTransOnLoad(plantTransOnLoadReq).then(function (data) {
				$scope.plantTransDetails = data.projPlantTransTOs;
				$scope.cutOffDays = $scope.plantTransDetails[0].cutOffDays
				$scope.cutoffHours = $scope.plantTransDetails[0].cutOffHours
				$scope.cutOffMinutes = $scope.plantTransDetails[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Plant Transfer Details", "Error");
			});
		}, $scope.saveProjPlantTrans = function () {
			var savePlantDetailReq = {
				"projPlantTransTOs": $scope.plantTransDetails
			};
			blockUI.start();
			ProjectSettingsService.saveProjPlantTrans(savePlantDetailReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Plant Transfer Detail(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Plant Transfer Detail(s) is/are failed to save', "Error");
			});
		},
		/* Approval Plant Transfer Tab */
		$scope.saveProjPlantTransAppr = function () {

			// Date.prototype.addDays = function(numdays) {
			// this.setDate(this.getDate() + parseInt(numdays));
			// return this;
			// };
			// var fromDate = new Date();
			// var endDay = fromDate;
			// endDay.addDays($scope.numdays)
			// $scope.newDate = $filter('date')((endDay), "dd-MMM-yyyy");
			// $scope.plantTransAppr.requisitionDate = $scope.newDate;
			var savePlantTransApprReq = {
				"projPlantTransApprTOs": [$scope.plantTransAppr]
			};
			blockUI.start();
			ProjectSettingsService.saveProjPlantApprTrans(savePlantTransApprReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Plant Transfer Approval(s) is/are saved successfully', "Info");
				$scope.plantTransAppr = [{
					"status": 1,
					"projId": null,
					"notificationId": null,
					"code": null,
					"requisitionDate": null,
					"empTransId": null
				}];
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Plant Transfer Approval(s) is/are failed to save', "Error");
			});
		},
		/* Material Transfer Tab */
		/* Create Material Transfer Tab */
		$scope.materialTransOnLoadRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var materialsOnLoadReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.projMaterialTransOnLoad(materialsOnLoadReq).then(function (data) {
				$scope.MaterialTransDtls = data.projMaterialTransTOs;
				$scope.cutOffDays = $scope.MaterialTransDtls[0].cutOffDays
				$scope.cutoffHours = $scope.MaterialTransDtls[0].cutOffHours
				$scope.cutOffMinutes = $scope.MaterialTransDtls[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Material Details", "Error");
			});
		}, $scope.saveProjMaterialTrans = function () {
			var saveMaterialDetailsReq = {
				"projMaterialTransTOs": $scope.MaterialTransDtls
			};
			blockUI.start();
			ProjectSettingsService.saveProjMaterialTrans(saveMaterialDetailsReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Material Transfer Detail(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Material Transfer Detail(s) is/are failed to save', "Error");
			});
		},
		/* Approval Material Transfer Tab */
		$scope.saveProjMaterialTransAppr = function () {
			Date.prototype.addDays = function (numdays) {
				this.setDate(this.getDate() + parseInt(numdays));
				return this;
			};
			var fromDate = new Date();
			var endDay = fromDate;
			endDay.addDays($scope.numdays)
			$scope.newDate = $filter('date')((endDay), "dd-MMM-yyyy");
			$scope.materialTransAppr[0].requisitionDate = $scope.newDate;
			$scope.materialTransAppr[0].notificationId = $scope.materialTransAppr.notificationId;
			var saveReqObj = {
				"projMaterialTransApprTOs": $scope.materialTransAppr
			};
			console.log(saveReqObj);
			//return;
			blockUI.start();
			ProjectSettingsService.saveProjMaterialApprTrans(saveReqObj).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Material Transfer Approval(s) is/are saved successfully', "Info");
				$scope.materialTransAppr = [{
					"status": 1,
					"projId": null,
					"notificationId": null,
					"code": null,
					"requisitionDate": null,
					"requisitionStatus": null,
					"empTransId": null
				}];
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Material Transfer Approval(s) is/are failed to save', "Error");
			});
		},
		/* Estimate to Complete Tab */
		$scope.getEstimateRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var getEstimateReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.getProjEstimate(getEstimateReq).then(function (data) {
				$scope.projEstimateDetails = data.projEstimateTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Estimate Details", "Error");
			});
		},
		$scope.saveProjEstimate = function () {
			var saveProjEstimateReq = {
				"projEstimateTOs": $scope.projEstimateDetails
			};
			blockUI.start();
			ProjectSettingsService.saveProjEstimate(saveProjEstimateReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Estimate(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Estimate(s) is/are failed to save', "Error");
			});
		},

		$scope.changedEstimateToCompleteItem = function (projEstimateDetails, type) {
			projEstimateDetails.id = projEstimateDetails.id;
			projEstimateDetails.resourceType = type;
		}

	/* Project Performance Threshold */
	$scope.getProjPerformenceThresholdRecords = function () {
		if ($rootScope.projId == null || $rootScope.projId == undefined) {
			GenericAlertService.alertMessage("Please select the Project", "Info");
			return;
		}
		var getProjPerformenceThresholdReq = {
			"status": 1,
			"projId": $rootScope.projId
		};
		ProjectSettingsService.getProjPerformenceThreshold(getProjPerformenceThresholdReq).then(function (data) {
			$scope.performenceThresholdData = data.projPerformenceThresholdTOs;
			for(var performance of $scope.performenceThresholdData){
				
				performance.category1= performance.category == "Warning" ? true : false;
				performance.category2= performance.category == "Critical" ? true :false;
				performance.category3=performance.category == "Exceptional" ? true  : false; 
				performance.category4= performance.category == "Acceptable" ? true :false;
			}
			console.log($scope.performenceThresholdData,"performenceThresholdData")
			$scope.gridOptions1.data= angular.copy($scope.performenceThresholdData);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
		});
	}, $scope.selectPerformanceThreshold = function (performenceThreshold) {
		if (performenceThreshold.select) {
			editPerformanceRecords.push(performenceThreshold);
		} else {
			editPerformanceRecords.splice(editPerformanceRecords.indexOf(performenceThreshold), 1);
		}
	}, $scope.editPerformanceThreshold = function (actionType) {
		if (actionType == 'Edit' && editPerformanceRecords <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Info");
			return;
		}

		var popUpDetails = PerformanceThresholdFactory.performancePopupDetails(editPerformanceRecords);
		popUpDetails.then(function (data) {
			$scope.performenceThresholdData = data.projPerformenceThresholdTOs;
			$scope.getProjPerformenceThresholdRecords();
			editPerformanceRecords = [];
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting Project Man Power Details", 'Info');
		})
	}, $scope.reStorePerformanceThreshold = function () {
		var performanceReq = {
			"projId": $rootScope.projId
		}
		ProjectSettingsService.saveProjPerfomanceDefaultSettings(performanceReq).then(function (data) {
			$scope.getProjPerformenceThresholdRecords();
			blockUI.stop();
			GenericAlertService.alertMessage('Performance Threshold values are Restored to Default settings successfully', "Info");
		})
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.selectPerformanceThreshold(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'selected',width:65,cellTemplate: linkCellTemplate, displayName: 'Select', headerTooltip : "Select" },
						{ field: 'category', displayName: 'Threshold Category', headerTooltip: "Threshold Category",
						cellTemplate:'<span class="warning" ng-show="row.entity.category1== true"><i class="fa fa-exclamation-triangle" aria-hidden="true" ></i>{{row.entity.category}}</span><span class="critical" ng-show="row.entity.category2== true"><img src="images/critical.png" style="padding-left:5px !important;"><span style="padding-left:5px !important;">{{row.entity.category}}</span></span><span class="exceptional" ng-show="row.entity.category3== true"><i class="fa fa-star" aria-hidden="true"></i>{{row.entity.category}}</span><span class="acceptable" ng-show="row.entity.category4== true"><i class="fa fa-square" aria-hidden="true"></i>{{row.entity.category}}</span>'},
						{ field: 'svLowerLimit', displayName: "SV% Lower Limit", headerTooltip: "SV%(Schedule Variance %)Lower Limit", },
						{ field: 'svUpperLimit', displayName: "SV% Upper Limit", headerTooltip: "SV%(Schedule Variance %)Upper Limit", },
						{ name: 'cvLowerLimit', displayName: "CV% Lower Limit", headerTooltip: "CV%(Cost Variance %)Lower Limit" },
						{ name: 'cvUpperLimit', displayName: "CV% Upper Limit", headerTooltip: "CV%(Cost Variance %)Upper Limit" },
						{ name: 'spiLowerLimit', displayName: "SPI Lower Limit", headerTooltip : "SPI(Schedule Performance Index) Lower Limit" },
						{ name: 'spiUpperLimit', displayName: "SPI Upper Limit", headerTooltip : "SPI(Schedule Performance Index) Upper Limit" },
						{ field: 'cpiLowerLimit', displayName: "CPI Lower Limit", headerTooltip: "CPI(Cost Performance Index) Lower Limit"},
						{ field: 'cpiUpperLimit', displayName: "CPI Upper Limit", headerTooltip: "CPI(Cost Performance Index) Upper Limit"},
						{ field: 'tcpiLowerLimit', displayName: "TCPI Lower Limit", headerTooltip: "TCPI(To Complete Performance Index) Lower Limit" },
						{ field: 'tcpiUpperLimit', displayName: "TCPI Upper Limit", headerTooltip: "TCPI(To Complete Performance Index) Upper Limit" },
					
						];
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data, "Projects-Project Settings-Performance Threshold");
					
				}
			});
			
	/* change order  */
	$scope.getChangeOrderRecords = function(){
		if($rootScope.projId == null || $rootScope.projId == undefined){
			GenericAlertService.alertMessage("Please select the Project", "Info");
			return;
		}
		var request = {
			"status": 1,
			"projId": $rootScope.projId
		};
		console.log(request);
		ProjectSettingsService.projchangeOrderOnLoad(request).then(function (data){
			$scope.changeOrders = data.projChangeOrderDetailsTOs;
			console.log(data);
     		console.log($scope.changeOrders);
			$scope.cutOffDays = $scope.changeOrders[0].cutOffDays;
			$scope.cutOffHours = $scope.changeOrders[0].cutOffHours;
			$scope.cutOffMinutes = $scope.changeOrders[0].cutOffMinutes;
			$scope.count = (($scope.cutOffDays * 24 + $scope.cutOffHours + $scope.cutOffMinutes / 60) * 60) * 60;
			$scope.numdays = Math.floor($scope.count / 86400);
		}, function(error){
			GenericAlertService.alertMessage("Error occured while geeting the Project Change order details", "Error");
		});
	},
	//mamatha CON
	$scope.saveChangeOrder= function(){
			
			var saveChangeOrderDetailsReq = {
				"projChangeOrderDetailsTOs": $scope.changeOrders
			};
			console.log(saveChangeOrderDetailsReq)
			blockUI.start();
			ProjectSettingsService.saveChangeOrderDetails(saveChangeOrderDetailsReq).then(function (data){
				blockUI.stop();
				GenericAlertService.alertMessage('Change Order Details saved successfully', "Info");
			},function(error){
				blockUI.stop();
				GenericAlertService.alertMessage('Change Order Details failed to save',"Error");
			});
		}

	/* Project Reports */
	$scope.getProjReportsRecords = function () {
		if ($rootScope.projId == null || $rootScope.projId == undefined) {
			GenericAlertService.alertMessage("Please select the Project", "Info");
			return;
		}
		var getReportsReq = {
			"status": 1,
			"projId": $rootScope.projId
		};
		ProjectSettingsService.projReportsOnLoad(getReportsReq).then(function (data) {
			$scope.weeakDays = generalservice.weeakDays;
			$scope.monthly = generalservice.monthly;
			$scope.years = generalservice.years;
			if (data.projectReportsTOs[0])
				$scope.projReportsDtls = data.projectReportsTOs[0];
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
		});
	}, $scope.saveProjReports = function () {
		var saveProjReportsReq = {
			"projId": $rootScope.projId,
			"status": 1,
			"projectReportsTOs": [$scope.projReportsDtls]
		};
		blockUI.start();
		ProjectSettingsService.saveProjReports(saveProjReportsReq).then(function (data) {
			blockUI.stop();
			GenericAlertService.alertMessage('Project Report(s) saved successfully', "Info");
			$scope.projReportsDtls = data.projectReportsTOs[0];
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Project Report(s) is/are failed to save', "Error");
		});
	},
		/* Progress Claim Tab */
		/* Create Progress Claim Tab */
		$scope.getProjClaimsRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var getClaimsReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.getProjProgressClaims(getClaimsReq).then(function (data) {
				$scope.projClaimDetails = data.projProgressClaimTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Progress Claim Details", "Error");
			});
		},

		$scope.saveProjClaims = function () {
			var saveProjClaimReq = {
				"projProgressClaimTOs": $scope.projClaimDetails
			};
			blockUI.start();
			ProjectSettingsService.saveProjProgressClaim(saveProjClaimReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Claim(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Claim(s) is/are failed to save', "Error");
			});
		},

		$scope.projProgressClaimePeriodCycleOnload = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var req = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.ProjProgressClaimePeriodCycleOnload(req).then(function (data) {
				$scope.weeakDays = generalservice.weeakDays;
				$scope.monthly = generalservice.monthly;
				$scope.years = generalservice.years;
				$scope.projProgressClaimePeriodTOs = data.projProgressClaimePeriodTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while Period Cycle Details", "Error");
			});
		},
		/* Approval Progress Claim Tab */
		$scope.progressClaimOriginalType = function (projctClaimAppr) {
			if (projctClaimAppr.originalType) {
				projctClaimAppr.internalType = true;
				projctClaimAppr.externalType = true;
			}
		}, $scope.progressClaimInternalType = function (projctClaimAppr) {
			if (projctClaimAppr.internalType) {
				projctClaimAppr.externalType = true;
			}
		},

		$scope.projClaimePeriodOnLoad = function () {
			var onloadReq = {
				"projId": $rootScope.projId
			};
			ProjectSettingsService.projClaimePeriodOnload(onloadReq).then(function (data) {
				$scope.claimeperiod = data.claimeperiod;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Progress Claim Details", "Error");
			});
		},

		$scope.saveProjProgressClaimePeriodCycle = function () {
			var saveLeaveReq = {
				"projProgressClaimePeriodTOs": $scope.projProgressClaimePeriodTOs
			};
			blockUI.start();
			ProjectSettingsService.saveProjProgressClaimePeriodCycle(saveLeaveReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Progress Claim(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Progress Claim(s) is/are failed to save', "Error");
			});
		},

		$scope.claimeperiod = $scope.projctClaimAppr.claimPeriod;
	$scope.saveProgressClaimAppr = function () {
		var projProgressClaimAppr = [];
		if ($scope.projctClaimAppr.claimPeriod == undefined || $scope.projctClaimAppr.claimPeriod == null) {
			GenericAlertService.alertMessage('Please Enter Claim Period', "Info");
			return;
		}
		if ($scope.projctClaimAppr.projId == undefined || $scope.projctClaimAppr.projId == null) {
			GenericAlertService.alertMessage('Please Select Project', "Info");
			return;
		}
		if ($scope.projctClaimAppr.apprUserId == undefined || $scope.projctClaimAppr.apprUserId == null) {
			GenericAlertService.alertMessage('Please Select Originator', "Info");
			return;
		}
		var flag = false;
		if ($scope.projctClaimAppr.originalType) {
			$scope.projctClaimAppr.claimId = $scope.projClaimDetails[0].id;
			projProgressClaimAppr.push(angular.copy($scope.projctClaimAppr));
			flag = true;
		}
		if ($scope.workDiaryAppr.internalType) {
			$scope.workDiaryAppr.claimId = $scope.projClaimDetails[1].id;
			projProgressClaimAppr.push(angular.copy($scope.projctClaimAppr));
			flag = true;
		}
		if ($scope.workDiaryAppr.externalType) {
			$scope.workDiaryAppr.claimId = $scope.projClaimDetails[2].id;
			projProgressClaimAppr.push(angular.copy($scope.projctClaimAppr));
			flag = true;
		}
		if (!flag) {
			GenericAlertService.alertMessage('Please  select Original Type or Internal Type or External Type', "Info");
			return;
		}
		$scope.projctClaimAppr.projId = $scope.projId;
		var saveProjectClaimApprReq = {
			"projProgressClaimApprTOs": projProgressClaimAppr
		};
		blockUI.start();
		ProjectSettingsService.saveProgressClaimAppr(saveProjectClaimApprReq).then(function (data) {
			blockUI.stop();
			GenericAlertService.alertMessage('Progress Claim Approval(s) is/are saved successfully', "Info");
			$scope.projctClaimAppr = {
				"status": 1,
				"projId": null,
				"claimId": null,
				"apprUserId": null,
				"claimPeriod": null,
				"originalType": false,
				"externalType": false,
				"internalType": false
			};
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Progress Claim Approval(s) is/are failed to save', "Error");
		});
	},

		/* Pay Roll Tab */
		$scope.getPayRollOnLoadRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			$scope.categories = [];
			var getPayRollOnLoadReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			$scope.procReq = {
				"procureId" : null,
				"subProcureName" : null,
				"status" : "1",
				"procureClassName" : 'Manpower'
			};console.log($scope.procReq);
			ProcureService.getProcureCatgs(getPayRollOnLoadReq).then(function(data) {
		//		$scope.categories = data.procureMentCatgTOs;
				angular.forEach(data.procureMentCatgTOs, function(key,value){
					if(key.procureId=='Manpower'){
					console.log(key);
		  			$scope.categories.push(angular.copy(key));
					}			
				});
			});
			$scope.employeeType = generalservice.employeeCategory;
			$scope.payRollCycle = generalservice.payRollCycle;
			$scope.weeakDays = generalservice.weeakDays;
			$scope.weakly = $scope.weeakDays[0];
			$scope.fortnightly = $scope.weeakDays[0];
			$scope.monthly = generalservice.monthly;
			$scope.monthDate = $scope.monthly[0];
			$scope.years = generalservice.years;
			$scope.firstQuart = $scope.years[0];
			$scope.firstHalf = $scope.years[0];
			$scope.yearly = $scope.years[0];
			ProjectSettingsService.projPayRollCycleOnLoad(getPayRollOnLoadReq).then(function (data) {
				$scope.projPayRollDtls = data.projPayRollCycleTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Pay Roll Details", "Error");
			});
		}, $scope.saveProjPayRollCycle = function () {
			$scope.projPayRollDtls.projId = $rootScope.projId;
			var saveProjPayRollCycleReq = {
				"projProgressClaimApprTOs": $scope.projPayRollDtls
			};
			blockUI.start();
			ProjectSettingsService.saveProjPayRollCycle(saveProjPayRollCycleReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Claim Approval(s) is/are saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Claim Approval(s) is/are failed to save', "Error");
			});
		},
		/* Leave Approval Tab */
		/* Create Leave Approval Tab */
		$scope.getLeaveApprovalRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Info");
				return;
			}
			var getLeaveReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectSettingsService.getProjLeaveRequest(getLeaveReq).then(function (data) {
				$scope.projLeaveDetails = data.projLeaveRequestTOs;

				$scope.cutOffDays = $scope.projLeaveDetails[0].cutOffDays
				$scope.cutoffHours = $scope.projLeaveDetails[0].cutOffHours
				$scope.cutOffMinutes = $scope.projLeaveDetails[0].cutOffMinutes;
				$scope.count = (($scope.cutOffDays * 24 + $scope.cutoffHours + $scope.cutOffMinutes / 60) * 60) * 60;
				$scope.numdays = Math.floor($scope.count / 86400);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Leave Approval Details", "Error");
			});
		},

		$scope.saveProjLeaves = function () {
			var saveLeaveReq = {
				"projLeaveRequestTOs": $scope.projLeaveDetails
			};
			blockUI.start();
			ProjectSettingsService.saveProjLeaveRequest(saveLeaveReq).then(function (data) {
				blockUI.stop();
				$scope.projLeaveDetails = data.projLeaveRequestTOs;
				GenericAlertService.alertMessage('Leave Approval(s) saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Leave Approval(s) is/are failed to save', "Error");
			});
		},
		/* Approval Leave Approval Tab */
		$scope.saveLeaveAppr = function () {
			Date.prototype.addDays = function (numdays) {
				this.setDate(this.getDate() + parseInt(numdays));
				return this;
			};
			var fromDate = new Date();
			var endDay = fromDate;
			endDay.addDays($scope.numdays)
			$scope.newDate = $filter('date')((endDay), "dd-MMM-yyyy");
			$scope.projLeaveApprovalAppr[0].requisitionDate = $scope.newDate;
			$scope.projLeaveApprovalAppr[0].notifyRefId = $scope.notifications.notifyRefId;
			$scope.projLeaveApprovalAppr[0].notificationId = $scope.projLeaveApprovalAppr.notificationId;
			/*
			$scope.newDate = $filter('date')((endDay), "dd-MMM-yyyy");
			$scope.empTransAppr[0].requisitionDate = $scope.newDate;
			$scope.empTransAppr[0].empTransId = $scope.projEmpDetails[0].id
			$scope.empTransAppr[0].empTransId = $scope.projEmpDetails[0].projId
			$scope.empTransAppr[0].notificationId = $scope.empTransAppr.notificationId;  
			 */
			var saveProjectLeaveApprReq = {
				"projLeaveApprTOs": $scope.projLeaveApprovalAppr
			};
			
			console.log(saveProjectLeaveApprReq);
			blockUI.start();
			ProjectSettingsService.saveProjLeaveApproval(saveProjectLeaveApprReq).then(function (data) {
				blockUI.stop();
				GenericAlertService.alertMessage('Leave Approval(s) saved successfully', "Info");
				$scope.notifications = [];
				$scope.projLeaveApprovalAppr = [{
					"status": 1,
					"notificationId": null,
					"notificationNumer": null,
					"requisitionDate": null,
					"requester": null
				}]
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Leave Approval(s) is/are failed to save', "Error");
			});
		},

		// ------------------------------------------------------------------------------------------------------------------//
		$scope.calcEstimateToComplete = function (budgetObj, formulaType) {
			var BAC = 0;
			if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0) {
				BAC = budgetObj.revisedQty - budgetObj.actualQty;
			} else {
				BAC = budgetObj.originalQty - budgetObj.actualQty
			}
			if (formulaType == 1) {
				budgetObj.estimateComplete = BAC;
				return budgetObj.estimateComplete;
			} else if (formulaType == 2) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue);
				return budgetObj.estimateComplete;
			} else if (formulaType == 3) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.costPermIndex;
				return budgetObj.estimateComplete;
			} else if (formulaType == 4) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.costPermIndex * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			} else if (formulaType == 5) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.progressFactor;
				return budgetObj.estimateComplete;
			} else if (formulaType == 6) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.progressFactor * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			}

		}

	$scope.calcEstimateAtCompletion = function (budgetObj) {
		budgetObj.estimateCompletion = budgetObj.actualQty + budgetObj.estimateComplete;
		return budgetObj.estimateCompletion;
	}

	$scope.crewList = function (projectCrew) {
		var crewReq = {
			"status": 1,
			"projId": $rootScope.projId
		};
		var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			projectCrew.projCrewId = data.projCrewTO.id;
			projectCrew.projCrewCode = data.projCrewTO.code;
			projectCrew.crewName = data.projCrewTO.desc;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	$scope.attendenceList = function (projectCrew, attendEmpAppr) {
		var crewReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"type": projectCrew,
			"notifyStatus": 'Pending'

		};
		var crewSerivcePopup = ProjectAttendencePopupService.getCrewPopup(crewReq);
		crewSerivcePopup.then(function (data) {

			attendEmpAppr.notificationId = data.projCrewTO.id;
			attendEmpAppr.notification = data.projCrewTO.code;
			attendEmpAppr.crewName = data.projCrewTO.crewName;
			attendEmpAppr.fromDate = data.projCrewTO.fromDate;
			attendEmpAppr.toDate = data.projCrewTO.toDate;
			attendEmpAppr.projCrewId = data.projCrewTO.crewId;


		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}
	$scope.workDairyNotificationList = function (notification) {
		var notificationReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Additional Time For Request'
		};
		var workDiaryNotificationPopup = ProjectWorkDairyNotificationPopupService.getWorkDairyNotificationPopup(notificationReq);
		workDiaryNotificationPopup.then(function (data) {
			notification.notificationId = data.projCrewTO.id;
			notification.notification = data.projCrewTO.workDairyNumber;
			notification.crewName = data.projCrewTO.crewName;
			notification.fromDate = data.projCrewTO.fromDate;
			notification.toDate = data.projCrewTO.toDate;
			if (data.projCrewTO.type === "Original") {
				notification.originalType = true;
			} else if (data.projCrewTO.type === "Internal") {
				notification.internalType = true;
			} else {
				notification.externalType = true;
			}
			notification.supervisor = data.projCrewTO.reqUser;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

    $scope.soeNotificationList = function(notification){
	  var notificationReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Additional Time for Request'
		};
		console.log(notificationReq);
		var soeNotificationPopup = ProjectSoeNotificationPopupService.getSoeNotificationPopup(notificationReq);
		soeNotificationPopup.then(function (data) {
			console.log(data);
			$scope.schofEstimateAppr.notificationId = data.soeNotificationsTO.id;
			$scope.schofEstimateAppr.fromDate = data.soeNotificationsTO.id;
			$scope.schofEstimateAppr.toDate = new Date();
			$scope.schofEstimites.soeNumber = data.soeNotificationsTO.soeNumber;
			$scope.schofEstimites.projName = data.soeNotificationsTO.projName;
			$scope.schofEstimites.toUserName = data.soeNotificationsTO.fromUserName;
		//	$scope.schofEstimites.toUserName = data.soeNotificationsTO.id;
			if(data.soeNotificationsTO.type == "SUBMITTED_FOR_INTERNAL_APPROVAL"){
				$scope.schofEstimites.originalType = true;
			}
			if(data.soeNotificationsTO.type == "SUBMITTED_FOR_EXTERNAL_APPROVAL"){
				$scope.schofEstimites.externalType = true;
			}
			console.log(notification.soeNumber);
			}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting SOE List Details", 'Error');
		});
    }


	 $scope.timeSheetNotificationList = function (notification) {
		var notificationReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Submitted'
		};
		var timeSheetNotificationPopup = ProjectTimeSheetNotificationPopupService.getTimeSheetNotificationPopup(notificationReq);
		timeSheetNotificationPopup.then(function (data) {
			notification.notificationId = data.projCrewTO.id;
			notification.notification = data.projCrewTO.code;
			notification.crewType = data.projCrewTO.crewType;
			notification.crewName = data.projCrewTO.crewName;
			notification.timeSheetDate = data.projCrewTO.fromDate;
			notification.timeSheetEndDate = data.projCrewTO.toDate;
			notification.apprUserId = data.projCrewTO.toUserId;
			console.log(data.usersMap);
			$scope.usersMap = data.usersMap;
			if (data.projCrewTO.type = "CREATE") {
				notification.forSubmission = true;
			} else {
				notification.forApproval = true;
			}
			/*
			 * notification.apprUserCode =
			 * data.usersMap[data.projCrewTO.toUserId].code;
			 */
			notification.timeSheetNo = data.projCrewTO.timeSheetNumber;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	$scope.procurementNotificationList = function (projectCrew) {
		var crewReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Pending'
		};
		var crewSerivcePopup = ProjectProcurementNotificationPopupService.getProcurementNotificationPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			console.log(data);

			//$scope.procureAppr = data.projCrewTO;
			$scope.procureAppr = data.projCrewTO;
			$scope.procureAppr.apprUserId = data.projCrewTO.toUserId;
			console.log(data.usersMap);
			$scope.usersMap = data.usersMap;
			if (data.projCrewTO.stage == "Stage 1 Internal Approval") {
				$scope.procureAppr.procureStage1InternalApproval = true;
			}
			else {
				$scope.procureAppr.procureStage2InternalApproval = true;
			}

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	$scope.getEmpTransferNotifications = function (projectCrew) {
		var crewReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"type": 'Additional Time for Request',
			"notifyStatus": 'Pending'
		};
		var crewSerivcePopup = ProjEmpTransferNotificationPopupService.getEmpTransferNotificationPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			projectCrew.notificationId = data.projCrewTO.notifyRefId;
			projectCrew.code = data.projCrewTO.code;
			projectCrew.date = data.projCrewTO.date;
			$scope.empTransAppr[0].notificationId = data.projCrewTO.id;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	$scope.getPlantTransferNotifications = function (projectCrew) {
		var crewReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Pending',
			"type": 'Additional Time For Submit'
		};
		var crewSerivcePopup = ProjPlantTransferNotificationPopupService.getPlantTransferNotificationPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			projectCrew.notificationId = data.projCrewTO.id;
			projectCrew.code = data.projCrewTO.code;
			projectCrew.requisitionDate = data.projCrewTO.date;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	$scope.getMaterialTransferNotifications = function (projectCrew) {
		var crewReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Pending',
			"type": 'Additional Time For Submit'
		};
		var crewSerivcePopup = ProjMaterialTransferNotificationPopupService.getMaterialTransferNotificationPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			projectCrew.notificationId = data.projCrewTO.id;
			projectCrew.code = data.projCrewTO.code;
			projectCrew.requisitionDate = data.projCrewTO.date;
			$scope.materialTransAppr[0].notificationId = data.projCrewTO.id;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	$scope.leaveApprovalNotificationList = function (notification) {
		var notificationReq = {
			"status": 1,
			"projId": $rootScope.projId,
			"notifyStatus": 'Pending',
			"type": 'Additional Time for Request'
		};
		var leaveApprNotificationPopup = LeaveApprNotificationPopupService.getLeaveApprNotificationPopup(notificationReq);
		leaveApprNotificationPopup.then(function (data) {
			console.log(data);
			notification.notificationId = data.projCrewTO.id;
			notification.notificationNumber = data.projCrewTO.code;
			notification.requisitionDate = data.projCrewTO.date;
			notification.requester = data.projCrewTO.fromUserName;
			$scope.projLeaveApprovalAppr[0].notificationId = data.projCrewTO.id;
			
			$scope.notifications = data.projCrewTO;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});
	}

	var ApproverSerivcePopup = [];
	$scope.approversList = function (projectApprover) {
		ApproverSerivcePopup = ProjectCrewPopupService.approverDetailsList($rootScope.projId);
		ApproverSerivcePopup.then(function (data) {
			projectApprover.apprUserId = data.projApproverTO.userId;
			projectApprover.apprUserCode = data.projApproverTO.firstName;
			projectApprover.apprUserName = data.projApproverTO.lastName;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Approver List Details", 'Error');
		});
	}

	$scope.getProcurementNotifications = function (procurementNotification) {
		$scope.getNotificationsByModuleCode('PROCURMT', procurementNotification);
	},

		$scope.getEmpNotifications = function (empNotification) {
			$scope.getEmpNotifications('EMPTRSFR', empNotification);
		},

		$scope.getPlantNotifications = function (plantNotification) {
			$scope.getPlantNotifications('PLANTTRSFR', plantNotification);
		},

		$scope.getMaterialNotifications = function (materialNotification) {
			$scope.getMaterialNotifications('MATRLTRSFR', materialNotification);
		},

		$scope.getMaterialNotifications = function (notification) {
			var notificationPopup = [];
			notificationPopup = ProjectCrewPopupService.getMaterialNotificationDetails($scope.projId);
			notificationPopup.then(function (data) {
				notification.notificationId = data.projNotificationTO.id;
				notification.code = data.projNotificationTO.code;
				notification.date = data.projNotificationTO.date;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			});

		}, $scope.getPlantNotifications = function (notification) {
			var notificationPopup = [];
			notificationPopup = ProjectCrewPopupService.getPlantNotificationDetails($scope.projId);
			notificationPopup.then(function (data) {
				notification.notificationId = data.projNotificationTO.id;
				notification.code = data.projNotificationTO.code;
				notification.date = data.projNotificationTO.date;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			});

		}, $scope.getEmpNotifications = function (notification) {
			var notificationPopup = [];
			notificationPopup = ProjectCrewPopupService.getEmployeeNotificationDetails($scope.projId);
			notificationPopup.then(function (data) {
				notification.notificationId = data.projNotificationTO.id;
				notification.code = data.projNotificationTO.code;
				notification.date = data.projNotificationTO.date;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			});

		}

	var EPSPopUp = [];
	$scope.project = function (projClaim) {
		EPSPopUp = ProjectCrewPopupService.projctDetailList($rootScope.projId);
		EPSPopUp.then(function (data) {
			projClaim.projId = data.projEPSTO.projId;
			projClaim.code = data.projEPSTO.projCode;
			projClaim.name = data.projEPSTO.projName;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Projects List Details", 'Error');
		});
	},

		$scope.addCostItemDetails = function () {
			var costCodePopup = [];
			costCodePopup = ProjectSettingCostItemFactory.getCostItemDetails($rootScope.projId);
			costCodePopup.then(function (data) {
				$scope.projCostStmtDtls = data.projCostStmtDtlTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error osccured while gettting  cost codes", 'Error');
			});
		}

	var originalBudgetTotal = 0;
	var revisedBudgetTotal = 0;
	var actualCost = 100000;
	$scope.costPercentage = function (tab) {

		var originalBudget = tab.projCostBudgetTOs[0];
		originalBudgetTotal = originalBudget.labourCost + originalBudget.materialCost + originalBudget.plantCost + originalBudget.otherCost;

		var revisedBudget = tab.projCostBudgetTOs[1];
		revisedBudgetTotal = revisedBudget.labourCost + revisedBudget.materialCost + revisedBudget.plantCost + revisedBudget.otherCost;

		if (revisedBudgetTotal != undefined && revisedBudgetTotal > 0) {
			tab.costPercentage = (actualCost / revisedBudgetTotal) * 100;
		} else if (originalBudgetTotal != undefined) {
			tab.costPercentage = (actualCost / originalBudgetTotal) * 100;
		}
	}, $scope.percentageOfWork = function (tab) {
		var earnedValueAmount = tab.earnedValue;
		if (revisedBudgetTotal != undefined && revisedBudgetTotal > 0) {
			tab.percentageOfWork = (earnedValueAmount / revisedBudgetTotal) * 100;
		} else if (originalBudgetTotal != undefined) {
			tab.percentageOfWork = (earnedValueAmount / revisedBudgetTotal) * 100;
			tab.percentageOfWork = (earnedValueAmount / originalBudgetTotal) * 100;
		}
	}, $scope.productivityFactor = function (tab) {
		var earnedAmount = tab.earnedValue;
		// var actualCost = tab.actualCost;
		if (earnedAmount != undefined && earnedAmount > 0) {
			tab.productivityFactor = (earnedAmount / actualCost) * 100;
		}
	}, $scope.checkErr = function (startDate, endDate) {
		$scope.errMessage = '';
		var curDate = new Date();
		if (!(startDate && endDate)) {
			$scope.errMessage = 'Please Select Date';
			return false;
		}
		if (new Date(startDate) >= new Date(endDate)) {
			$scope.errMessage = 'From Date less than To Date';
			return false;
		}
	}, $scope.editItem = function () {
		$scope.editing = true;
	},
	$scope.primaveraIntegrationChanged = function(selection){
		if (selection == 'Yes') {
			if ($scope.generalValues.globalCalenderTO)
				$scope.generalValues.globalCalenderTO = null;
			if ($scope.generalValues.projCalenderTO)
				$scope.generalValues.projCalenderTO = null
			if ($scope.generalValues.calenderTO.code)
				$scope.generalValues.calenderTO = null;
			$scope.generalValues.resourceCurveTO = null;
		}
	},
	$scope.itemId = 1;
		// new code
	$scope.contractType = [{
		id: "1",
		name: "Schedule of Rates Contract",
		code: "SORcontract"
	}, {
		id: "2",
		name: "Cost Plus % Type Contract",
		code: "CPPTypecontract"
	}, {
		id: "3",
		name: "Lumpsum Contract with Milestones",
		code: "LContractMile"
	}];
	// end
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/projectshelp/projectsettingshelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
}]).filter('slice', function () {
	return function (arr, start, end) {
		return (arr || []).slice(start, end);
	}
});
