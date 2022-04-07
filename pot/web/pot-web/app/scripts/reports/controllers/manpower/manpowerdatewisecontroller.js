'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpowerdatewise", {
		url: '/manpowerdatewise',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowerdatewiseactualhours.html',
				controller: 'ManpowerDateWiseController'
			}
		}
	})
}]).controller("ManpowerDateWiseController", ["$scope","uiGridGroupingConstants", "$q", "ngDialog", "$filter", "GenericAlertService", "ManpowerReportService", "CompanyMultiSelectFactory", "EpsProjectMultiSelectFactory", "ProjectSettingsService", "dateGroupingService", "generalservice", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($scope,uiGridGroupingConstants,$q, ngDialog,  $filter, GenericAlertService, ManpowerReportService,
	CompanyMultiSelectFactory, EpsProjectMultiSelectFactory, ProjectSettingsService, dateGroupingService, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'Hours';
	$scope.stylesSvc = stylesService;
	$scope.data = [];
	$scope.labels = [];
	$scope.flag = false;
	$scope.diplayGroup = false;
	$scope.list = [];
	$scope.selectedProjIds = [];
	$scope.selectedCmpIds = [];
	$scope.categoryName = [];
	$scope.periodicSubReportCode = null;
	$scope.date = new Date();
	$scope.toDate = new Date();
	let todayDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
	$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);
	let manpowerActualHrsData = null;
	let projReportsSettings = null;

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

	$scope.subReport = {
		name: null
	};
	$scope.category = {
		name: null
	}
	$scope.periodicSubReport = {
		name: null
	}

	$scope.selectType = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.subReport.name == null) {
			GenericAlertService.alertMessage("Please select Sub Report", 'Warning');
			return;
		}
	};

	$scope.subReports = [{
		name: 'Overall-Used Vs Idle Hours Summary',
		code: "overall"
	}, {
		name: 'Trade Wise Utilisation Hours Report',
		code: "trade"
	}, {
		name: 'Cost Code Wise-Manhour Utilisation',
		code: "costcode"
	}, {
		name: 'Project Wise-Manhour Utilisation',
		code: "proj"
	}, {
		name: 'EPS Wise-Manhour Utilisation',
		code: "eps"
	}, {
		name: 'Periodical Report-Trade Wise',
		code: "periodical"
	}];

	$scope.periodicSubReports = [{
		name: 'Daily',
		code: "daily"
	}, {
		name: 'Weekly',
		code: "weekly"
	}, {
		name: 'Monthly',
		code: "monthly"
	}, {
		name: 'Yearly',
		code: "yearly"
	}];

	$scope.changeSubReport = function () {
		$scope.type = 'chartTable';
		let compareProperty;
		let codeProperty;
		switch ($scope.subReport.code) {
			case "overall":
				compareProperty = "projId";
				codeProperty = "projName";
				$scope.tableHeading = "Project";
				if ($scope.subReport.code == "overall") {
					let overAllData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data,"Reports_Manpower_Date_Wise_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				let overallSub = new Object();
				overallSub[1] = { 'usedHrs': 0, 'idleHrs': 0 };
				for (const manPower of manpowerActualHrsData) {
					const totalHrs = calculateHrs(manPower.hrsList);
					overallSub[1].usedHrs += totalHrs.usedHrs;
					overallSub[1].idleHrs += totalHrs.idleHrs;
				}
				$scope.labels = new Array();
				$scope.data = [overallSub[1].usedHrs, overallSub[1].idleHrs];
				$scope.labels = ['Used Hrs', 'Idle Hrs'];
				initGraph('pie');
				$scope.periodicSubReport = null;
				break;
			case "trade":
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				$scope.tableHeading = "Trade";
				if ($scope.subReport.code == "trade") {
					let tradeData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, tradeData, data,"Reports_Manpower_Date_Wise_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.periodicSubReport = null;
				break;
			case "costcode":
				compareProperty = "costCodeId";
				codeProperty = "costCodeName";
				if ($scope.subReport.code == "costcode") {
					let costcodeData = [
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID" },
						{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" },
						{ field: 'costCodeName', displayName: "Cost Code Item Id", headerTooltip: "Cost Code Item Id" },
						{ field: 'costCodeDesc', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, costcodeData, data,"Reports_Manpower_Date_Wise_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.periodicSubReport = null;
				break;
			case "eps":
				compareProperty = "parentProjId";
				codeProperty = "parentProjName";
				$scope.tableHeading = "EPS";
				if ($scope.subReport.code == "eps") {
					let epsData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Date_Wise_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.periodicSubReport = null;
				break;
			case "proj":
				compareProperty = "projId";
				codeProperty = "projName";
				$scope.tableHeading = "Project";
				if ($scope.subReport.code == "proj") {
					let projectData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, projectData, data,"Reports_Manpower_Date_Wise_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.periodicSubReport = null;
				break;
			case "periodical":
				ProjectSettingsService.projReportsOnLoad({ "projIds": $scope.selectedProjIds }).then(function (data) {
					projReportsSettings = data.projectReportsTOs;
					if (!$scope.periodicSubReport || !$scope.periodicSubReport.code) {
						$scope.periodicSubReport = $scope.periodicSubReports[0];
					}
					$scope.changePeriodicSubReport();
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
				});
				return;
			default:
				$scope.manpowerDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				let columnDefs = [
					{ field: 'dateStr', displayName: "Date", headerTooltip: "Date" },
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name" },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
					{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name" },
					{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false },
					{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false },
					{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" },
					{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false },
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false },
					{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor" },
					{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false },
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false },
					{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Category", visible: false },
					{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false ,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hrs",aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hrs", visible: false ,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'totalHrs', displayName: "Total Hrs", headerTooltip: "Total Hrs", visible: false ,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Date_Wise_AH");
				$scope.getManpowerDateWiseDetails();
				$scope.gridOptions.gridMenuCustomItems = false;
		}
		const subReportMap = new Array();
		for (const manPower of manpowerActualHrsData) {
			if (!subReportMap[manPower[compareProperty]]) {
				if ($scope.subReport.code === "costcode") {
					subReportMap[manPower[compareProperty]] = {
						'code': manPower[codeProperty],
						'usedHrs': 0,
						'idleHrs': 0,
						'measureUnit': manPower.unitOfMeasure,
						'parentCostCode': manPower.parentCostCode,
						'parentCostCodeName': manPower.parentCostCodeName,
						'costCodeDesc': manPower.costCodeDesc,
						'costCodeName': manPower.costCodeName
					};
				} else {
					subReportMap[manPower[compareProperty]] = {
						'code': manPower[codeProperty],
						'usedHrs': 0,
						'idleHrs': 0,
						'measureUnit': manPower.unitOfMeasure
					};
				}
			}
			const totalHrs = calculateHrs(manPower.hrsList);
			subReportMap[manPower[compareProperty]].usedHrs += totalHrs.usedHrs;
			subReportMap[manPower[compareProperty]].idleHrs += totalHrs.idleHrs;
		}
		setGraphData(subReportMap);
	};

	function setGraphData(subReportMap) {
		$scope.series = ['Used Hrs', 'Idle Hrs'];
		if ($scope.subReport.code !== "overall")
			$scope.labels = new Array();
		let obj = null;
		const usedHrs = new Array();
		const idleHrs = new Array();
		$scope.subReportData = new Array();
		$scope.totalHrs = { 'usedHrs': 0, 'idleHrs': 0 };
		for (const key in subReportMap) {
			obj = subReportMap[key];
			if (obj.idleHrs || obj.usedHrs) {
				usedHrs.push(obj.usedHrs);
				idleHrs.push(obj.idleHrs);
				$scope.totalHrs.usedHrs += obj.usedHrs;
				$scope.totalHrs.idleHrs += obj.idleHrs;
				if ($scope.subReport.code !== "overall")
					$scope.labels.push(obj.code);
				$scope.subReportData.push(obj);
			}
		}
		for (let manpower of $scope.subReportData) {
			manpower.totalUsedIdle = manpower.usedHrs + manpower.idleHrs;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		if ($scope.subReport.code !== "overall") {
			$scope.data = new Array();
			$scope.data.push(usedHrs);
			$scope.data.push(idleHrs);
			initGraph('bar');
		}
	}

	function setPeriodicGraphData(tradePeriodicalReport, dateFormat) {
		// Group by date
		let obj = null;
		const dateGroupMap = new Object();
		$scope.totalHrs = { 'usedHrs': 0, 'idleHrs': 0 };
		let tradeNamesArray = new Array();
		$scope.subReportData = new Array();
		const reportData = new Array();
		for (const key in tradePeriodicalReport) {
			obj = tradePeriodicalReport[key];
			$scope.totalHrs.usedHrs += obj.usedHrs;
			$scope.totalHrs.idleHrs += obj.idleHrs;
			reportData.push(obj);

			if (!dateGroupMap[obj.code]) {
				dateGroupMap[obj.code] = {
					'code': obj.code,
					'trades': new Array()
				};
			}
			dateGroupMap[obj.code].trades.push(obj);
			if (!tradeNamesArray.includes(obj.trade))
				tradeNamesArray.push(obj.trade);
		}
		$scope.subReportData = _.sortBy(reportData, function (i) { return moment(i.code, dateFormat)._d });
		$scope.labels = new Array();
		const dateWiseTradeValues = new Object();

		$scope.series = tradeNamesArray;

		const sortedDays = _.sortBy(Object.keys(dateGroupMap), function (i) { return moment(i, dateFormat)._d });
		for (const key of sortedDays) {
			obj = dateGroupMap[key];
			dateWiseTradeValues[key] = new Object();
			for (const tradeName of tradeNamesArray) {
				dateWiseTradeValues[key][tradeName] = 0;
			}
			for (const trade of obj.trades) {
				dateWiseTradeValues[key][trade.trade] = trade.usedHrs + trade.idleHrs;
			}
			$scope.labels.push(obj.code);
		}
		$scope.data = new Array();
		for (const tradeName of tradeNamesArray) {
			const individualTradeValues = new Array();
			for (const key in dateWiseTradeValues) {
				individualTradeValues.push(dateWiseTradeValues[key][tradeName]);
			}
			$scope.data.push(individualTradeValues);
		}
		for (let manpower of $scope.subReportData) {
			manpower.totalUsedIdle = manpower.usedHrs + manpower.idleHrs;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		initGraph('bar');
	}

	function getProjReportSettings(projId) {
		for (const projSetting of projReportsSettings) {
			if (projSetting.projId == projId) {
				return projSetting;
			}
		}
		return null;
	}

	function calculateHrs(hrsList) {
		let usedHrs = 0;
		let idleHrs = 0;
		for (const hr of hrsList) {
			usedHrs += hr.usedHrs;
			idleHrs += hr.idleHrs;
		}
		return { 'usedHrs': usedHrs, 'idleHrs': idleHrs };
	}

	$scope.$watch('fromDate', function () {
		$scope.checkErr();
		$scope.clearSubReportDetails();
	});

	$scope.$watch('toDate', function () {
		$scope.checkErr()
		$scope.clearSubReportDetails();
	});

	$scope.changePeriodicSubReport = function () {
		$scope.type = 'chartTable';
		let groupByFunction = null;
		let dateFormat;
		let momentDateFormat;
		let reportSettingProp;
		$scope.tableHeading = null;
		if ($scope.subReport.code == "periodical") {
			let periodData = [
				{ field: 'code', displayName: "Periodical " + $scope.periodicSubReport.name, headerTooltip: "Periodical " + $scope.periodicSubReport.name + " Name" },
				{ field: 'trade', displayName: "Trade Name", headerTooltip: "Trade Name" },
				{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
				{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" },
				{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
				{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, periodData, data,"Reports_Manpower_Date_Wise_AH");
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		switch ($scope.periodicSubReport.code) {
			case "daily":
				groupByFunction = dateGroupingService.groupByDate;
				dateFormat = "dd-MMM-yyyy";
				momentDateFormat = "DD-MMM-YYYY";
				break;
			case "weekly":
				groupByFunction = dateGroupingService.groupByWeek;
				dateFormat = "dd-MMM-yyyy";
				momentDateFormat = "DD-MMM-YYYY";
				reportSettingProp = 'week';
				break;
			case "monthly":
				groupByFunction = dateGroupingService.groupByMonth;
				dateFormat = "MMM-yyyy";
				momentDateFormat = "MMM-YYYY";
				reportSettingProp = 'month';
				break;
			case "yearly":
				groupByFunction = dateGroupingService.groupByYear;
				dateFormat = "yyyy";
				momentDateFormat = "YYYY";
				reportSettingProp = 'year';
				break;
			default:
				break;
		}
		// Group by employee trade
		const tradeMap = new Array();
		for (const manPower of manpowerActualHrsData) {
			if (!tradeMap[manPower.empClassId]) {
				tradeMap[manPower.empClassId] = {
					'empClass': manPower.empClassName,
					'measureUnit': manPower.unitOfMeasure,
					'hrsList': new Array(),
					'projId': manPower.projId
				};
			}
			tradeMap[manPower.empClassId].hrsList = tradeMap[manPower.empClassId].hrsList.concat(manPower.hrsList);
		}
		const tradePeriodicalReport = new Array();

		for (const key in tradeMap) {
			const object = tradeMap[key];
			let projReportSetting = getProjReportSettings(object.projId);
			const groupedList = groupByFunction(object.hrsList, projReportSetting[reportSettingProp]);
			for (const group of groupedList) {
				if (!tradePeriodicalReport[group.date + "_" + key]) {
					tradePeriodicalReport[group.date + "_" + key] = {
						'code': null,
						'trade': object.empClass,
						'measureUnit': object.measureUnit,
						'usedHrs': 0,
						'idleHrs': 0,
					};
				}
				tradePeriodicalReport[group.date + "_" + key].code =
					$filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
				for (const val of group.values) {
					tradePeriodicalReport[group.date + "_" + key].usedHrs += val.usedHrs;
					tradePeriodicalReport[group.date + "_" + key].idleHrs += val.idleHrs;
				}

			}
		}
		setPeriodicGraphData(tradePeriodicalReport, momentDateFormat);

	};

	$scope.categories = [{
		id: 1,
		name: "Direct",
		code: generalservice.employeeCategory[0]
	}, {
		id: 2,
		name: "In-Direct",
		code: generalservice.employeeCategory[1]
	}, {
		id: 3,
		name: "All",
		code: null
	}];

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
		$scope.clearSubReportDetails();
	};

	$scope.getCompanyDetails = function () {
		var cmpReq = {
			"status": 1
		};
		var companyPopup = CompanyMultiSelectFactory.getCompanies(cmpReq);
		companyPopup.then(function (data) {
			$scope.cmpNameDisplay = data.selectedCompanies.companyName;
			$scope.selectedCmpIds = data.selectedCompanies.companyIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Company Details", 'Error');
		});
		$scope.clearSubReportDetails();
	};

	$scope.getManpowerDateWiseDetails = function () {
		if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.selectedCmpIds.length <= 0 || $scope.selectedCmpIds == undefined || $scope.selectedCmpIds == null) {
			GenericAlertService.alertMessage("Please select the Company", 'Warning');
			return;
		}
		if ($scope.categoryName == '') {
			GenericAlertService.alertMessage("Please select the Category", 'Warning');
			return;
		}
		if (new Date($scope.fromDate) > new Date($scope.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}
		const req = {
			"projIds": $scope.selectedProjIds,
			"cmpIds": $scope.selectedCmpIds,
			"category": $scope.categoryName,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
			"subReportType": $scope.subReportCode
		};

		ManpowerReportService.getManpowerDateWiseHrsReport(req).then(function (data) {
			manpowerActualHrsData = data;
			$scope.gridOptions.data = angular.copy($scope.manpowerDetails);
			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			} else {
				const empMap = new Array();
				let uniqueId = 0;
				const actualHrsList = new Array();
				for (const manHrs of manpowerActualHrsData) {
					empMap[uniqueId] = manHrs;
					for (const hr of manHrs.hrsList) {
						const newObj = { 'uniqueId': uniqueId };
						// Deep copy hr object
						for (const key in hr) {
							newObj[key] = hr[key];
						}
						actualHrsList.push(newObj);
					}
					uniqueId++;
				}
				for (let manpower of actualHrsList) {
					manpower.dateStr = $filter('date')((manpower.date), "dd-MMM-yyyy");
					manpower.parentProjName = empMap[manpower.uniqueId].parentProjName;
					manpower.projName = empMap[manpower.uniqueId].projName;
					manpower.companyName = empMap[manpower.uniqueId].companyName;
					manpower.crewName = empMap[manpower.uniqueId].crewName;
					manpower.parentCostCode = empMap[manpower.uniqueId].parentCostCode;
					manpower.parentCostCodeName = empMap[manpower.uniqueId].parentCostCodeName;
					manpower.costCodeName = empMap[manpower.uniqueId].costCodeName;
					manpower.costCodeDesc = empMap[manpower.uniqueId].costCodeDesc;
					manpower.empClassName = empMap[manpower.uniqueId].empClassName;
					manpower.wageCode = empMap[manpower.uniqueId].wageCode;
					manpower.empCode = empMap[manpower.uniqueId].empCode;
					manpower.empFirstname = empMap[manpower.uniqueId].empFirstname;
					manpower.empLastname = empMap[manpower.uniqueId].empLastname;
					manpower.empCategoryName = empMap[manpower.uniqueId].empCategoryName;
					manpower.unitOfMeasure = empMap[manpower.uniqueId].unitOfMeasure;
					manpower.totalHrs = (manpower.usedHrs + manpower.idleHrs).toFixed(2);

				}
				$scope.manpowerDetails = actualHrsList;
				$scope.gridOptions.data = angular.copy($scope.manpowerDetails);
			}
			if (manpowerActualHrsData.length <= 0) {
				GenericAlertService.alertMessage("Manpower Date Wise Actual Hours not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting  details", 'Error');
		});
	}
	$scope.clearSubReportDetails = function () {
		$scope.manpowerDetails = [];
		$scope.subReportName = "";
		$scope.type = "";
		$scope.subReportCode = "";
		$scope.subReport = null;
		$scope.periodicSubReportName = null;
		$scope.periodicSubReportCode = null;
	}
	$scope.resetManpowerDetails = function () {
		$scope.manpowerDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCmpIds = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.cmpNameDisplay = null;
		$scope.type = '';
		$scope.subReportName = null;
		$scope.subReportCode = "";
		$scope.categoryName = [];
		$scope.subReport = null;
		$scope.periodicSubReportName = null;
		$scope.periodicSubReportCode = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}
	$scope.groupBySelectedColumns = function () {
		$scope.flag = true;
		var keyValue = '';
		var manpowerGroupMap = [];
		angular.forEach($scope.manpowerDetails, function (value, key) {
			keyValue = value.displayNamesMap.trade;
			if (manpowerGroupMap[keyValue] == true) {
				$scope.list[keyValue].dataList.push(value);
			} else {
				$scope.list[keyValue] = {
					"dataList": [value]
				};
				manpowerGroupMap[keyValue] = true;
			}
		});
	};


	function initGraph(chatyType) {
		$scope.chart_type = chatyType;
		if (chatyType != 'pie') {
			chartService.defaultBarInit($scope.yAxislabels);
		}
		else
			chartService.defaultPieInit();
		
	};

	

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'dateStr', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false  },
				{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false },
				{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
				{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" , groupingShowAggregationMenu: false },
				{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false , groupingShowAggregationMenu: false },
				{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false , groupingShowAggregationMenu: false },
				{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false  , groupingShowAggregationMenu: false },
				{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false , groupingShowAggregationMenu: false },
				{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false , groupingShowAggregationMenu: false },
				{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor", groupingShowAggregationMenu: false  },
				{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" },
				{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false , groupingShowAggregationMenu: false },
				{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false, groupingShowAggregationMenu: false  },
				{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Category", visible: false , groupingShowAggregationMenu: false },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hrs" , groupingShowAggregationMenu: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{ field: 'totalHrs', displayName: "Total Hrs", headerTooltip: "Total Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Date_Wise_AH");
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
			template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowerdatewisehelp.html',
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
