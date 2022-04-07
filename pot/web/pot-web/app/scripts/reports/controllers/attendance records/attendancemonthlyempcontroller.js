'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("monthlyemp", {
		url: '/monthlyemp',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/attendmonthlyemp.html',
				controller: 'AttendanceMonthlyEmpController'
			}
		}
	})
}]).controller("AttendanceMonthlyEmpController", ["$scope", "$filter", "$q", "$state", "ngDialog", "GenericAlertService", "EmpAttendanceService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "generalservice", "stylesService", "ngGridService", "chartService", "uiGridConstants", function ($scope, $filter, $q, $state, ngDialog, GenericAlertService,
	EmpAttendanceService, EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	var labels = [];
	$scope.stylesSvc = stylesService;
	$scope.empAttendenceDetails = [];
	$scope.selectedProjIds = [];
	$scope.selectedCrewIds = [];
	$scope.subReportCode = "";
	$scope.subReport = "None";
	let totalValues = {
		pCount: 0,
		abCount: 0,
		phCount: 0,
		alCount: 0,
		lslCount: 0,
		slCount: 0,
		mlCount: 0,
		cblCount: 0,
		cslCount: 0,
		uplCount: 0
	};
	$scope.totalValues = angular.copy(totalValues);

	let todayDate = new Date();
	let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
	var fromdate = $filter('date')((lastMonthDate), "MMM-yyyy");
	// $scope.fromDate = fromdate;
	// $scope.toDate = $filter('date')((todayDate), "MMM-yyyy");
	// $scope.fromDate = $filter('date')((lastMonthDate), "MMM-yyyy");
	// $scope.toDate = $filter('date')((todayDate), "MMM-yyyy");

	$scope.$watch('fromDate', function () {
		$scope.checkErr();
		// $scope.clearSubReportDetails();
	});
	$scope.$watch('toDate', function () {
		$scope.checkErr();
		// $scope.clearSubReportDetails();
	});
	$scope.checkErr = function () {
		if (new Date($scope.fromDate) > new Date($scope.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}
	};

	$scope.subReports = [{
		name: 'Periodical-Direct Men Records',
		code: "direct"
	}, {
		name: 'Periodical-Indirect Men Records',
		code: "indirect"
	}, {
		name: 'TradeWise Counting Records',
		code: "trade"
	}, {
		name: 'CompanyWise Counting Records',
		code: "cmp"
	}, {
		name: 'ProjectWise Counting Records',
		code: "proj"
	}, {
		name: 'EPSWise Counting Records',
		code: "eps"
	}];

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	$scope.getCrewList = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select project to get crews", 'Warning');
			return;
		}
		var crewReq = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		var crewSerivcePopup = MultipleCrewSelectionFactory.crewPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			$scope.crewNameDisplay = data.selectedCrews.crewName;
			$scope.selectedCrewIds = data.selectedCrews.crewIds;
			$scope.empAttendenceDetails = [];
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
		});
	};
	function noSubReportData() {
		let columnDefs = [
			{ field: 'displayNamesMap.month', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.empCode', displayName: "Emp ID", headerTooltip: "Employee ID", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.empFirstName', displayName: "First Name", headerTooltip: "First Name", visible: false, groupingShowAggregationMenu: false   },
			{ field: 'displayNamesMap.empLastName', displayName: "Last Name", headerTooltip: "Last Name", visible: false, groupingShowAggregationMenu: false   },
			{ field: 'displayNamesMap.crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.empCategory', displayName: "Category", headerTooltip: "Category", visible: false, groupingShowAggregationMenu: false   },
			{ field: 'displayNamesMap.tradeName', displayName: "Trade", headerTooltip: "Trade Name", visible: false, groupingShowAggregationMenu: false   },
			{
				name: 'present',
				cellTemplate: '<div>{{row.entity.displayNamesMap.P ? row.entity.displayNamesMap.P : 0 }}</div>',
				displayName: "Present", headerTooltip: "Present"
			},
			{
				name: 'absent',
				cellTemplate: '<div>{{row.entity.displayNamesMap.AB ? row.entity.displayNamesMap.AB : 0 }}</div>',
				displayName: "Absent", headerTooltip: "Absent"
			},
			{
				name: 'publicHoliday',
				cellTemplate: '<div>{{row.entity.displayNamesMap.PH ? row.entity.displayNamesMap.PH : 0 }}</div>',
				displayName: "Public Holiday", headerTooltip: "Public Holiday"
			},
			{
				name: 'annualLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.AL ? row.entity.displayNamesMap.AL : 0 }}</div>',
				displayName: "Annual", headerTooltip: "Annual Leave"
			},
			{
				name: 'longServiceLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.LL ? row.entity.displayNamesMap.LL : 0 }}</div>',
				displayName: "Long Sevice", headerTooltip: "Long Sevice Leave"
			},
			{
				name: 'sickLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.SL ? row.entity.displayNamesMap.SL : 0 }}</div>',
				displayName: "Sick", headerTooltip: "Sick Leave"
			},
			{
				name: 'maternityLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.ML ? row.entity.displayNamesMap.ML : 0 }}</div>',
				displayName: "Maternity", headerTooltip: "Maternity Leave"
			},
			{
				name: 'compassionateLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.CBL ? row.entity.displayNamesMap.CBL : 0 }}</div>',
				displayName: "Compassionate", headerTooltip: "Compassionate & Bereavement Leave"
			},
			{
				name: 'communityLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.CSL ? row.entity.displayNamesMap.CSL : 0 }}</div>',
				displayName: "Community Service", headerTooltip: "Community Service Leave"
			},
			{
				name: 'unPaidLeave',
				cellTemplate: '<div>{{row.entity.displayNamesMap.UL ? row.entity.displayNamesMap.UL : 0 }}</div>',
				displayName: "Un Paid", headerTooltip: "Un Paid Leave"
			}
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Report_Atten_MonthlyEmp");
		$scope.gridOptions.exporterPdfOrientation = 'landscape';
		$scope.gridOptions.exporterPdfPageSize = 'A3';
		$scope.gridOptions.exporterPdfMaxGridWidth = 860;
	}
	$scope.changeSubReport = function () {
		if ($scope.subReport && $scope.subReport != "None") {
			$scope.type = 'chartTable';
			$scope.subReportName = $scope.subReport.name;
			$scope.subReportCode = $scope.subReport.code
			prepareSubReport();
		} else {
			$scope.empAttendenceDetails = [];
			$scope.type = '';
			$scope.subReportName = '';
			$scope.subReportCode = '';
			noSubReportData();
			$scope.getMonthlyEmpAttendenceDetails();
		}
	};

	function prepareSubReport() {
		$scope.labels = [];
		$scope.subReportData = [];
		$scope.data = [];
		if ($scope.subReport.code == "direct" || $scope.subReport.code == "indirect") {
			const empCategory = $scope.subReport.code == "direct" ? generalservice.employeeCategory[0] : generalservice.employeeCategory[1];
			generateSubReportData("empCategory", empCategory, true);
		} else if ($scope.subReport.code == "trade") {
			generateSubReportData("empClassId", "tradeName");
		} else if ($scope.subReport.code == "cmp") {
			generateSubReportData("companyId", "companyName");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		}
	};

	function generateSubReportData(key, value, monthWise) {
		let subReportMap = [];
		if ($scope.subReport.code == "direct") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
				{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
				{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
				{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
				{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
				{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
				{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
				{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
				{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_MonthlyEmp_Direct");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "indirect") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
				{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
				{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
				{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
				{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
				{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
				{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
				{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
				{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_MonthlyEmp_Indirect");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "trade") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Trade", headerTooltip: "Trade Name", groupingShowAggregationMenu: false  },
				{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
				{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
				{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
				{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
				{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
				{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
				{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
				{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_MonthlyEmp_Trade");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "cmp") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Company", headerTooltip: "Company Name", groupingShowAggregationMenu: false  },
				{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
				{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
				{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
				{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
				{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
				{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
				{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
				{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_MonthlyEmp_CMP");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "proj") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
				{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
				{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
				{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
				{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
				{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
				{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
				{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
				{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_MonthlyEmp_Proj");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "eps") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
				{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
				{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
				{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
				{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
				{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
				{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
				{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
				{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
				{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_MonthlyEmp_EPS");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		$scope.totalValues = angular.copy(totalValues);
		for (const attdDtl of $scope.empAttendenceDetails) {
			let mapKey;
			let mapValue;
			if (monthWise) {
				mapKey = attdDtl.displayNamesMap.month;
				mapValue = mapKey;
				if (attdDtl.displayNamesMap[key] != value)
					continue;
			} else {
				mapKey = attdDtl.displayNamesMap[key];
				mapValue = attdDtl.displayNamesMap[value];
			}
			if (!subReportMap[mapKey]) {
				subReportMap[mapKey] = {
					"empCategory": value,
					"mapKey": mapKey,
					"mapValue": mapValue,
					"pCount": 0,
					"abCount": 0,
					"phCount": 0,
					"alCount": 0,
					"lslCount": 0,
					"slCount": 0,
					"mlCount": 0,
					"cblCount": 0,
					"cslCount": 0,
					"uplCount": 0,

				};
			}
			for (const leaveCode of generalservice.empLeaveTypes) {
				if (attdDtl.displayNamesMap[leaveCode.code]) {
					const codeCount = parseInt(attdDtl.displayNamesMap[leaveCode.code]);
					switch (leaveCode.code) {
						case generalservice.empLeaveTypes[0].code:
							subReportMap[mapKey].pCount += codeCount;
							$scope.totalValues.pCount += codeCount;
							break;
						case generalservice.empLeaveTypes[1].code:
							subReportMap[mapKey].abCount += codeCount;
							$scope.totalValues.abCount += codeCount;
							break;
						case generalservice.empLeaveTypes[2].code:
							subReportMap[mapKey].phCount += codeCount;
							$scope.totalValues.phCount += codeCount;
							break;
						case generalservice.empLeaveTypes[3].code:
							subReportMap[mapKey].alCount += codeCount;
							$scope.totalValues.alCount += codeCount;
							break;
						case generalservice.empLeaveTypes[4].code:
							subReportMap[mapKey].lslCount += codeCount;
							$scope.totalValues.lslCount += codeCount;
							break;
						case generalservice.empLeaveTypes[5].code:
							subReportMap[mapKey].slCount += codeCount;
							$scope.totalValues.slCount += codeCount;
							break;
						case generalservice.empLeaveTypes[6].code:
							subReportMap[mapKey].mlCount += codeCount;
							$scope.totalValues.mlCount += codeCount;
							break;
						case generalservice.empLeaveTypes[7].code:
							subReportMap[mapKey].cblCount += codeCount;
							$scope.totalValues.cblCount += codeCount;
							break;
						case generalservice.empLeaveTypes[8].code:
							subReportMap[mapKey].cslCount += codeCount;
							$scope.totalValues.cslCount += codeCount;
							break;
						case generalservice.empLeaveTypes[9].code:
							subReportMap[mapKey].uplCount += codeCount;
							$scope.totalValues.uplCount += codeCount;
							break;
						default:
							break;
					}
				}
			}
		}
		let pCountArr = [], abCountArr = [], phCountArr = [], alCountArr = [], lslCountArr = [], slCountArr = [], mlCountArr = [], cblCountArr = [], cslCountArr = [], uplCountArr = [];
		for (const index in subReportMap) {
			pCountArr.push(subReportMap[index].pCount);
			abCountArr.push(subReportMap[index].abCount);
			phCountArr.push(subReportMap[index].phCount);
			alCountArr.push(subReportMap[index].alCount);
			lslCountArr.push(subReportMap[index].lslCount);
			slCountArr.push(subReportMap[index].slCount);
			mlCountArr.push(subReportMap[index].mlCount);
			cblCountArr.push(subReportMap[index].cblCount);
			cslCountArr.push(subReportMap[index].cslCount);
			uplCountArr.push(subReportMap[index].uplCount);
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		for (let empAttendence of $scope.subReportData) {
			empAttendence.totalCount = empAttendence.pCount + empAttendence.abCount + empAttendence.phCount + empAttendence.alCount + empAttendence.lslCount + empAttendence.slCount + empAttendence.mlCount + empAttendence.cblCount + empAttendence.cslCount + empAttendence.cslCount + empAttendence.uplCount;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(pCountArr);
		$scope.data.push(abCountArr);
		$scope.data.push(phCountArr);
		$scope.data.push(alCountArr);
		$scope.data.push(lslCountArr);
		$scope.data.push(slCountArr);
		$scope.data.push(mlCountArr);
		$scope.data.push(cblCountArr);
		$scope.data.push(cslCountArr);
		$scope.data.push(uplCountArr);
	};

	$scope.data = [];
	$scope.labels = labels;
	var series = ['Present', 'Absent', 'Public Holiday', 'Annual Leave', 'Long Service Leave', 'Sick Leave', 'Maternity / Parental Leave', 'Compassionate & Bereavement Leave', 'Community Service Leave', 'Unpaid Leave'];
	$scope.initGraph = function () {
		$scope.series = series;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit();
	};

	$scope.getMonthlyEmpAttendenceDetails = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select Project to get details", 'Warning');
			return;
		}
		if ($scope.selectedCrewIds.length <= 0) {
			GenericAlertService.alertMessage("Please select Crew to get details", 'Warning');

			return;
		}
		if ($scope.fromDate == undefined) {
			GenericAlertService.alertMessage("Please select From Date to get details", 'Warning');

			return;
		}
		if ($scope.toDate == undefined) {
			GenericAlertService.alertMessage("Please select To Date to get details", 'Warning');

			return;
		}
		if (new Date($scope.fromDate) > new Date($scope.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}

		var attendReq = {
			"projIds": $scope.selectedProjIds,
			"crewIds": $scope.selectedCrewIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
			"subReportType": $scope.subReportCode
		};
		EmpAttendanceService.getDailyEmpAttendanceReportBtwnDates(attendReq).then(function (data) {
			$scope.empAttendenceDetails = data;
			for (var attendence of $scope.empAttendenceDetails){
	          attendence.displayNamesMap.P=(attendence.displayNamesMap.P = attendence.displayNamesMap.P ? attendence.displayNamesMap.P : 0);
	          attendence.displayNamesMap.AB=(attendence.displayNamesMap.AB = attendence.displayNamesMap.AB ? attendence.displayNamesMap.AB : 0);
	          attendence.displayNamesMap.PH=(attendence.displayNamesMap.PH = attendence.displayNamesMap.PH ? attendence.displayNamesMap.PH : 0);
	          attendence.displayNamesMap.AL=(attendence.displayNamesMap.AL = attendence.displayNamesMap.AL ? attendence.displayNamesMap.AL : 0);
	          attendence.displayNamesMap.LL=(attendence.displayNamesMap.LL = attendence.displayNamesMap.LL ? attendence.displayNamesMap.LL : 0);
	          attendence.displayNamesMap.SL=(attendence.displayNamesMap.SL = attendence.displayNamesMap.SL ? attendence.displayNamesMap.SL : 0);
	          attendence.displayNamesMap.ML=(attendence.displayNamesMap.ML = attendence.displayNamesMap.ML ? attendence.displayNamesMap.ML : 0);
	          attendence.displayNamesMap.CBL=(attendence.displayNamesMap.CBL = attendence.displayNamesMap.CBL ? attendence.displayNamesMap.CBL : 0);
	          attendence.displayNamesMap.CSL=(attendence.displayNamesMap.CSL = attendence.displayNamesMap.CSL ? attendence.displayNamesMap.CSL : 0);
	          attendence.displayNamesMap.UL=(attendence.displayNamesMap.UL = attendence.displayNamesMap.UL ? attendence.displayNamesMap.UL : 0);      
     }
			$scope.gridOptions1.data = angular.copy($scope.empAttendenceDetails);
			if ($scope.subReport && $scope.subReport != "None") {
				prepareSubReport();
			}
			if ($scope.empAttendenceDetails.length <= 0) {
				GenericAlertService.alertMessage("Monthly Employee Attendance Records not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting Monthly Employee Attendance Records", 'Error');
		});
		$scope.initGraph();
	};
	$scope.clearSubReportDetails = function () {
		$scope.empAttendenceDetails = [];
		$scope.subReportName = "";
		$scope.type = "";
		$scope.subReport = "None";
		$scope.subReportCode = "";
	};
	$scope.resetMonthlyEmpAttendenceDetails = function () {
		$scope.empAttendenceDetails = [];
		$scope.subReport = "None";
		$scope.selectedCrewIds = [];
		$scope.selectedProjIds = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.gridOptions.showColumnFooter = false;
		$scope.type = '';
		$scope.subReportName = null;
		$scope.crewNameDisplay = null;
		$scope.fromDate = $filter('date')((lastMonthDate), "MMM-yyyy");
		$scope.toDate = $filter('date')((todayDate), "MMM-yyyy");
		$scope.subReportCode = "";
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'displayNamesMap.month', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.empCode', displayName: "Emp ID", headerTooltip: "Employee ID", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.empFirstName', width:"200", displayName: "First Name", headerTooltip: "First Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'displayNamesMap.empLastName', width:"200", displayName: "Last Name", headerTooltip: "Last Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'displayNamesMap.crewName', width:"200", displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.empCategory', width:"200", displayName: "Category", headerTooltip: "Category", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'displayNamesMap.tradeName', width:"200", displayName: "Trade", headerTooltip: "Trade Name", visible: false, groupingShowAggregationMenu: false   },
				{
					name: 'displayNamesMap.P',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.P">{{row.entity.displayNamesMap.P}}</span><span ng-if="!row.entity.displayNamesMap.P">0</span></div>',
*/					displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.AB',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.AB">{{row.entity.displayNamesMap.AB}}</span><span ng-if="!row.entity.displayNamesMap.AB">0</span></div>',
*/					displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.PH',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.PH">{{row.entity.displayNamesMap.PH}}</span><span ng-if="!row.entity.displayNamesMap.PH">0</span></div>',
*/					displayName: "Public Holiday", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.AL',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.AL">{{row.entity.displayNamesMap.AL}}</span><span ng-if="!row.entity.displayNamesMap.AL">0</span></div>',
*/					displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.LL',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.LL">{{row.entity.displayNamesMap.LL}}</span><span ng-if="!row.entity.displayNamesMap.LL">0</span></div>',
*/					displayName: "Long Sevice", headerTooltip: "Long Sevice Leave", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.SL',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.SL">{{row.entity.displayNamesMap.SL}}</span><span ng-if="!row.entity.displayNamesMap.SL">0</span></div></div>',
*/					displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.ML',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.ML">{{row.entity.displayNamesMap.ML}}</span><span ng-if="!row.entity.displayNamesMap.ML">0</span></div>',
*/					displayName: "Maternity", headerTooltip: "Maternity Leave", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.CBL',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.CBL">{{row.entity.displayNamesMap.CBL}}</span><span ng-if="!row.entity.displayNamesMap.CBL">0</span></div>',
*/					displayName: "Compassionate", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false 
				},
				{
					name: 'displayNamesMap.CSL',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.CSL">{{row.entity.displayNamesMap.CSL}}</span><span ng-if="!row.entity.displayNamesMap.CSL">0</span></div>',
*/					displayName: "Community Service", headerTooltip: "Community Service Leave"
				},
				{
					name: 'displayNamesMap.UL',
/*					cellTemplate: '<div class="ui-grid-cell-contents"><span ng-if="row.entity.displayNamesMap.UL">{{row.entity.displayNamesMap.UL}}</span><span ng-if="!row.entity.displayNamesMap.UL">0</span></div>',
*/					displayName: "Un Paid", headerTooltip: "Un Paid Leave", groupingShowAggregationMenu: false 
				}
			];
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data, "Reports_Atten_MonthlyEmp");	
			$scope.gridOptions1.exporterPdfOrientation = 'landscape';
			$scope.gridOptions1.exporterPdfPageSize = 'A3';
			$scope.gridOptions1.exporterPdfMaxGridWidth = 920;
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
			template: 'views/help&tutorials/reportshelp/attendancerecordshelp/monthlyemphelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);
