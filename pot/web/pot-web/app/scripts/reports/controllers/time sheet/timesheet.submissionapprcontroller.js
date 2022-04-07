'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("timesheetsubmitappr", {
		url: '/timesheetsubmitappr',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/time sheet/timesheet.submissionappr.html',
				controller: 'TimeSheetSubmitApprController'
			}
		}
	})
}]).controller("TimeSheetSubmitApprController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "ModuleUserMultiSelectFactory", "TimeSheetReportService", "ManpowerReportService", "TimeSheetSupervisorFactory", "NotificationService", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($rootScope, $scope, $q, $state, $filter, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory, ModuleUserMultiSelectFactory, TimeSheetReportService, ManpowerReportService, TimeSheetSupervisorFactory, NotificationService, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'Axis';
	$scope.selectedProjIds = '';
	$scope.selectedApprStatus = new Array();
	$scope.userIds = '';
	$scope.stylesSvc = stylesService;
	$scope.userLabelKeyTO = new Array();
	var labels = [];
	$scope.labels = [];
	$scope.data = [];
	$scope.series = ["Under Preparation", "Pending Approval", "Approved"];
	$scope.subReport = {
		name: null
	};
	$scope.apprStatusList = [{
		"name": "Under Preparation",
		"code": "Under Preparation",
	}, {
		"name": "Pending Approval",
		"code": "Submitted",
	}, {
		"name": "Approved",
		"code": "Approved",
	}, {
		"name": "All",
		"code": null
	}];

	$scope.subReports = [{
		"name": "Supervisor Id Wise TimeSheet Approval Status",
		"code": "supervisor"
	}, {
		"name": "Crew Wise TimeSheet Approval Status",
		"code": "crew"
	}, {
		"name": "Project Wise TimeSheet Approval Status",
		"code": "proj"
	}, {
		"name": "EPS Wise TimeSheet Approval Status",
		"code": "eps"
	}];
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);
	let todayDate = new Date();
	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
	$scope.$watch('fromDate', function () {
		$scope.checkErr();
		$scope.clearSubReportDetails();
	});
	$scope.$watch('toDate', function () {
		$scope.checkErr();
		$scope.clearSubReportDetails();
	});
	$scope.checkErr = function () {
		if (new Date($scope.fromDate) > new Date($scope.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}
	};
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};

	$scope.getSupervisors = function (userLabelKeyTO) {
		var selectedUser = TimeSheetSupervisorFactory.getTimeSheetSupervisorData($scope.selectedProjIds)
		selectedUser.then(function (data) {
			$scope.userLabelKeyTO = data;
			$scope.selectedUsers = "";
			for (const user of data) {
				$scope.selectedUsers += user.name;
			}
		});
	};

	$scope.changeSubReport = function () {
		const subReportMap = [];
		$scope.subReportData = [];
		$scope.type = 'chartTable';
		let compareProperty = null;
		let codeProperty = null;
		if ($scope.subReport.code === "supervisor") {
			compareProperty = "reqUserId";
			codeProperty = "reqUserName";
			$scope.tableHeading = "Supervisor";
			if ($scope.subReport.code == "supervisor") {
				let overAllData = [
					{ field: 'code', displayName: "Supervisor Name", headerTooltip: "Supervisor Name" },
					{ field: 'notSubmittedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Under Preparation", headerTooltip: "Under Preparation" },
					{ field: 'inProgressCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Pending Approval", headerTooltip: "Pending Approval" },
					{ field: 'approvedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Approved", headerTooltip: "Approved" },
					{ field: 'total', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data,"Reports_Time_sheets_S&AS_Supervisor");
				$scope.gridOptions.showGridFooter = false;
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
		}
		else if ($scope.subReport.code === "crew") {
			compareProperty = "crewId";
			codeProperty = "crewName";
			$scope.tableHeading = "Crew";
			if ($scope.subReport.code == "crew") {
				let overAllData = [
					{ field: 'code', displayName: "Crew Name", headerTooltip: "Crew Name" },
					{ field: 'notSubmittedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Under Preparation", headerTooltip: "Under Preparation" },
					{ field: 'inProgressCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Pending Approval", headerTooltip: "Pending Approval" },
					{ field: 'approvedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Approved", headerTooltip: "Approved" },
					{ field: 'total', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data,"Reports_Time_sheets_S&AS_Crew_Wise");
				$scope.gridOptions.showGridFooter = false;
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
		} else if ($scope.subReport.code === "proj") {
			compareProperty = "projId";
			codeProperty = "projectTO.name";
			$scope.tableHeading = "Project";
			if ($scope.subReport.code == "proj") {
				let overAllData = [
					{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" },
					{ field: 'notSubmittedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Under Preparation", headerTooltip: "Under Preparation" },
					{ field: 'inProgressCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Pending Approval", headerTooltip: "Pending Approval" },
					{ field: 'approvedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Approved", headerTooltip: "Approved" },
					{ field: 'total', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data,"Reports_Time_sheets_S&AS_Project_Wise");
				$scope.gridOptions.showGridFooter = false;
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
		} else if ($scope.subReport.code === "eps") {
			compareProperty = "parentProjId";
			codeProperty = "projectTO.parentName";
			$scope.tableHeading = "EPS";
			if ($scope.subReport.code == "eps") {
				let overAllData = [
					{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" },
					{ field: 'notSubmittedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Under Preparation", headerTooltip: "Under Preparation" },
					{ field: 'inProgressCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Pending Approval", headerTooltip: "Pending Approval" },
					{ field: 'approvedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Approved", headerTooltip: "Approved" },
					{ field: 'total', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data,"Reports_Time_sheets_S&AS_EPS_Wise");
				$scope.gridOptions.showGridFooter = false;
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
		} else {
			$scope.timeSheetDetails = [];
			$scope.type = '';
			$scope.subReportName = '';
			$scope.subReportCode = '';
			let columnDefs = [
				{ field: 'weekCommenceDay', displayName: "Week Commencing Date", headerTooltip: "Week Commencing Date" },
				{
					name: 'epsIdName',
					cellTemplate: '<div>{{row.entity.projectTO.parentName}} ({{row.entity.projectTO.parentCode}})</div>',
					displayName: "EPS ID & Name", headerTooltip: "EPS ID & Name"
				},
				{
					name: 'projIdName',
					cellTemplate: '<div>{{row.entity.projectTO.name}} ({{row.entity.projectTO.code}})</div>',
					displayName: "Project ID & Name", headerTooltip: "Project ID & Name"
				},
				{
					name: 'crewName',
					cellTemplate: '<div>{{row.entity.crewName}} ({{row.entity.crewCode}})</div>',
					displayName: "Crew ID & Name", headerTooltip: "Crew ID & Name"
				},
				{ field: 'code', displayName: "TimeSheet ID", headerTooltip: "TimeSheet ID" },
				{
					name: 'superVisorName',
					cellTemplate: '<div>{{row.entity.reqUserName}} ({{row.entity.reqUserCode}})</div>',
					displayName: "Supervisor Name", headerTooltip: "Supervisor Name"
				},
				{
					name: 'apprIdName',
					cellTemplate: '<div>{{row.entity.apprStatus === "Under Preparation" ? "N/A" : row.entity.apprUserName}}{{row.entity.apprStatus === "Under Preparation" ? "" : "("+row.entity.apprUserCode+")"}}</div>',
					displayName: "Approval ID & Name", headerTooltip: "Approver ID & Name"
				},
				{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status" }
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [],"Reports_Time_sheets_S&AS");
			$scope.gridOptions.gridMenuCustomItems = false;
			$scope.getTimeSheetApprStatusReport();
		}
		for (const timeSheet of $scope.timeSheetDetails) {
			if (!subReportMap[timeSheet[compareProperty]]) {
				subReportMap[timeSheet[compareProperty]] = {
					'code': null,
					'approvedCount': 0,
					'inProgressCount': 0,
					'notSubmittedCount': 0
				};
				if (codeProperty.includes(".")) {
					const propArray = codeProperty.split(".");
					subReportMap[timeSheet[compareProperty]].code = timeSheet[propArray[0]][propArray[1]];
				} else {
					subReportMap[timeSheet[compareProperty]].code = timeSheet[codeProperty];
				}
			}

			switch (timeSheet.apprStatus) {
				// Under Preparation
				case $scope.apprStatusList[0].code:
					subReportMap[timeSheet[compareProperty]].notSubmittedCount += 1;
					break;

				// Pending Approval
				case $scope.apprStatusList[1].code:
					subReportMap[timeSheet[compareProperty]].inProgressCount += 1;
					break;

				// Approved
				case $scope.apprStatusList[2].code:
					subReportMap[timeSheet[compareProperty]].approvedCount += 1;
					break;
				default:
					break;
			}
		}

		$scope.labels = [];
		$scope.data = [];
		let obj = null;
		const approved = [];
		const inProgress = [];
		const notSubitted = [];
		$scope.totalValues = { 'notSubmitted': 0, 'inProgress': 0, 'approved': 0, 'allCount': 0 };
		for (const key in subReportMap) {
			obj = subReportMap[key];
			approved.push(obj.approvedCount);
			$scope.totalValues.approved += obj.approvedCount;

			inProgress.push(obj.inProgressCount);
			$scope.totalValues.inProgress += obj.inProgressCount;

			notSubitted.push(obj.notSubmittedCount);
			$scope.totalValues.notSubmitted += obj.notSubmittedCount;
			$scope.labels.push(obj.code);
			$scope.subReportData.push(obj);
		}
		for (let timeSheet of $scope.subReportData) {
			timeSheet.total = timeSheet.approvedCount + timeSheet.inProgressCount + timeSheet.notSubmittedCount;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(notSubitted);
		$scope.data.push(inProgress);
		$scope.data.push(approved);
	};

	$scope.initGraph = function () {
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit($scope.yAxislabels);
	};

	$scope.total = function (timeSheet) {
		return (timeSheet.approvedCount + timeSheet.inProgressCount + timeSheet.notSubmittedCount)
	};
	
	$scope.getTimeSheetApprStatusReport = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
			return;
		}
		if ($scope.apprStatus == undefined) {
			GenericAlertService.alertMessage("Please select Approval Status to fetch report", 'Warning');
			return;
		}
		if ($scope.apprStatus.name == "All") {
			for (const status of $scope.apprStatusList) {
				status.code && $scope.selectedApprStatus.push(status.code);
			}
		} else {
			$scope.selectedApprStatus.push($scope.apprStatus.code);
		}
		const userIds = [];
		for (const obj of $scope.userLabelKeyTO) {
			userIds.push(obj.id);
		}
		if (userIds.length <= 0) {
			GenericAlertService.alertMessage("Please select Supervisor to fetch report", 'Warning');
			return;
		}
		var req = {
			"projIds": $scope.selectedProjIds,
			"apprStatus": $scope.selectedApprStatus,
			"supervisorIds": userIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
		}
		TimeSheetReportService.getTimeSheetApprStatusReport(req).then(function (data) {
			$scope.timeSheetDetails = data;
			$scope.gridOptions.data = angular.copy($scope.timeSheetDetails);
			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			}
			if ($scope.timeSheetDetails.length <= 0) {
				GenericAlertService.alertMessage("Time Sheets-Submission & Approval Status not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting  TimeSheet Details", 'Error');
		});
		$scope.initGraph();
	}
	$scope.clearSubReportDetails = function () {
		$scope.type = '';
		$scope.subReport = null;
	}
	$scope.resetTimeSheetDetails = function () {
		$scope.timeSheetDetails = [];
		$scope.selectedProjIds = '';
		$scope.userIds = '';
		$scope.searchProject = {};
		$scope.selectedUsers = "";
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.userLabelKeyTO = new Array();
		$scope.data = [];
		$scope.labels = [];
		$scope.type = '';
		$scope.apprStatus = null;
		$scope.subReport = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}


	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'weekCommenceDay', displayName: "Week Commencing Date", headerTooltip: "Week Commencing Date" },
				{
					name: 'projectTO.parentName',
					cellTemplate: '<div>{{row.entity.projectTO.parentName}} ({{row.entity.projectTO.parentCode}})</div>',
					displayName: "EPS ID & Name", headerTooltip: "EPS ID & Name"
				},
				{
					name: 'projectTO.name',
					cellTemplate: '<div>{{row.entity.projectTO.name}} ({{row.entity.projectTO.code}})</div>',
					displayName: "Project ID & Name", headerTooltip: "Project ID & Name"
				},
				{
					name: 'crewName',
					cellTemplate: '<div>{{row.entity.crewName}} ({{row.entity.crewCode}})</div>',
					displayName: "Crew ID & Name", headerTooltip: "Crew ID & Name"
				},
				{ field: 'code', displayName: "TimeSheet ID", headerTooltip: "TimeSheet ID" },
				{
					name: 'reqUserName',
					cellTemplate: '<div>{{row.entity.reqUserName}} ({{row.entity.reqUserCode}})</div>',
					displayName: "Supervisor Name", headerTooltip: "Supervisor Name"
				},
				{
					name: 'apprUserName',
					cellTemplate: '<div>{{row.entity.apprStatus === "Under Preparation" ? "N/A" : row.entity.apprUserName}}{{row.entity.apprStatus === "Under Preparation" ? "" : "("+row.entity.apprUserCode+")"}}</div>',
					displayName: "Approval ID & Name", headerTooltip: "Approver ID & Name"
				},
				{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status" }

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Time_sheets_S&AS");
		}
	});
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
			template: 'views/help&tutorials/reportshelp/timesheetshelp/timesheetsubbmissionapprhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}])
