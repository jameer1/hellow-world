'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpoweridlehrs", {
		url: '/manpoweridlehrs',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpoweridlehours.html',
				controller: 'ManpowerIdleHoursController'
			}
		}
	})
}]).controller("ManpowerIdleHoursController", ["$scope","uiGridGroupingConstants","$q", "ngDialog", "$filter", "GenericAlertService", "ProjectSettingsService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "ManpowerReportService", "generalservice", "dateGroupingService", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($scope,uiGridGroupingConstants,$q, ngDialog,$filter, GenericAlertService, ProjectSettingsService,
	EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, ManpowerReportService, generalservice, dateGroupingService, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);

	$scope.stylesSvc = stylesService;
	$scope.manpowerDetails = [];
	let manpowerActualHrsData;
	let projReportsSettings;
	$scope.date = new Date();
	$scope.toDate = new Date();
	let todayDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
	$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	$scope.selectedProjIds = [];
	$scope.selectedCrewIds = [];
	$scope.categoryName = [];
	$scope.type = '';
	$scope.category = {
		name: null
	}

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.selectedClientIds = data.searchProject.clientIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};

	$scope.subReports = [{
		name: 'Trade Wise Idle Hours',
		code: "trade"
	}, {
		name: 'Cost Code Wise Idle Hours',
		code: "costcode"
	}, {
		name: 'Project Wise Idle Hours',
		code: "proj"
	}, {
		name: 'EPS Wise Idle Hours',
		code: "eps"
	}, {
		name: 'Periodical Trade Wise Idle Hours',
		code: "tradewise"
	}, {
		name: 'Periodical Trade Wise Idle Amount',
		code: "amountwise"
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
			case "trade":
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				$scope.tableHeading = "Trade";
				if ($scope.subReport.code == "trade") {
					let epsData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name", groupingShowAggregationMenu: false  },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours" , groupingShowAggregationMenu: false },
						{ field: 'idleAmount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount" , groupingShowAggregationMenu: false }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Idle_HR");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.periodicSubReport = null;
				break;
			case "costcode":
				compareProperty = "costCodeId";
				codeProperty = "costCodeName";
				if ($scope.subReport.code == "costcode") {
					let epsData = [
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group ID", groupingShowAggregationMenu: false  },
						{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false  },
						{ field: 'costCodeName', displayName: "Cost Code Item Id", headerTooltip: "Cost Code Item ID", groupingShowAggregationMenu: false  },
						{ field: 'costCodeDesc', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" , groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false  },
						{ field: 'idleAmount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount" , groupingShowAggregationMenu: false }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Idle_HR");
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
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours" , groupingShowAggregationMenu: false },
						{ field: 'idleAmount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount" , groupingShowAggregationMenu: false }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Idle_HR");
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
					let epsData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" , groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false  },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false  },
						{ field: 'idleAmount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount", groupingShowAggregationMenu: false  }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Idle_HR");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.periodicSubReport = null;
				break;
			case "amountwise":
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
			case "tradewise":
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
					{ field: 'dateStr', displayName: "Date", headerTooltip: "Date" , groupingShowAggregationMenu: false },
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false },
					{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" , groupingShowAggregationMenu: false },
					{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" , groupingShowAggregationMenu: false },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false , groupingShowAggregationMenu: false },
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false , groupingShowAggregationMenu: false },
					{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name" , groupingShowAggregationMenu: false },
					{ field: 'empClassname', displayName: "Trade", headerTooltip: "Trade", visible: false, groupingShowAggregationMenu: false  },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false  },
					{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false  },
					{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" , groupingShowAggregationMenu: false },
					{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false  },
					{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor" , groupingShowAggregationMenu: false },
					{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hrs", groupingShowAggregationMenu: false , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					{ field: 'idleRate', displayName: "Idle Rate", headerTooltip: "Idle Rate" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					{ field: 'idleAmount', displayName: "Idle Amt", headerTooltip: "Idle Amount", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Idle_HR");
				$scope.getManpowerDetails();
				$scope.gridOptions.gridMenuCustomItems = false;
		}
		const subReportMap = new Array();
		for (const manPower of manpowerActualHrsData) {
			if (!subReportMap[manPower[compareProperty]]) {
				if ($scope.subReport.code === "costcode") {
					subReportMap[manPower[compareProperty]] = {
						'code': manPower[codeProperty],
						'idleAmount': 0,
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
						'idleAmount': 0,
						'idleHrs': 0,
						'measureUnit': manPower.unitOfMeasure
					};
				}
			}
			const totalHrs = calculateHrs(manPower.hrsList, manPower.idleRate, manPower.wageFactorValue);
			subReportMap[manPower[compareProperty]].idleAmount += totalHrs.idleAmount;
			subReportMap[manPower[compareProperty]].idleHrs += totalHrs.idleHrs;
		}
		setGraphData(subReportMap);
	};

	function calculateHrs(hrsList, idleRate, wageFactorValue) {
		let idleAmount = 0;
		let idleHrs = 0;
		for (const hr of hrsList) {
			idleAmount += idleRate * wageFactorValue * hr.idleHrs;
			idleHrs += hr.idleHrs;
		}
		return { 'idleHrs': idleHrs, 'idleAmount': idleAmount };
	}

	$scope.changePeriodicSubReport = function () {
		$scope.type = 'chartTable';
		let groupByFunction = null;
		let dateFormat;
		let momentDateFormat;
		let reportSettingProp;
		$scope.tableHeading = null;
		if ($scope.subReport.code == "tradewise") {
			let epsData = [
				{ field: 'code', displayName: 'Periodical ' + $scope.periodicSubReport.code, headerTooltip: 'Periodical ' + $scope.periodicSubReport.code , groupingShowAggregationMenu: false },
				{ field: 'trade', displayName: 'Trade Name', headerTooltip: 'Trade Name' , groupingShowAggregationMenu: false },
				{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false  },
				{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false  },
				// { field: 'idleAmount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount", groupingShowAggregationMenu: false  }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Idle_HR");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "amountwise") {
			let epsData = [
				{ field: 'code', displayName: 'Periodical ' + $scope.periodicSubReport.code, headerTooltip: 'Periodical ' + $scope.periodicSubReport.code , groupingShowAggregationMenu: false },
				{ field: 'trade', displayName: 'Trade Name', headerTooltip: 'Trade Name' , groupingShowAggregationMenu: false },
				{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false  },
				// { field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false  },
				{ field: 'idleAmount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount", groupingShowAggregationMenu: false  }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Idle_HR");
			$scope.gridOptions.showColumnFooter = true;
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
		let tradeKey = null;
		for (const manPower of $scope.manpowerDetails) {
			tradeKey = $scope.employeeDetailsMap[manPower.uniqueId].empClassId;
			if (!tradeMap[tradeKey]) {
				tradeMap[tradeKey] = {
					'empClass': $scope.employeeDetailsMap[manPower.uniqueId].empClassName,
					'measureUnit': $scope.employeeDetailsMap[manPower.uniqueId].unitOfMeasure,
					'hrsList': new Array(),
					'projId': $scope.employeeDetailsMap[manPower.uniqueId].projId
				};
			}
			tradeMap[tradeKey].hrsList.push(manPower);
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
						'idleHrs': 0,
						'idleAmount': 0,
					};
				}
				tradePeriodicalReport[group.date + "_" + key].code =
					$filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
				for (const val of group.values) {
					tradePeriodicalReport[group.date + "_" + key].idleHrs += val.idleHrs;
					tradePeriodicalReport[group.date + "_" + key].idleAmount += val.idleAmount;
				}

			}
		}
		setPeriodicalGraphData(tradePeriodicalReport, momentDateFormat);
	};

	function getProjReportSettings(projId) {
		for (const projSetting of projReportsSettings) {
			if (projSetting.projId == projId) {
				return projSetting;
			}
		}
		return null;
	}

	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		let obj = null;
		const idleHrs = new Array();
		$scope.subReportData = new Array();
		$scope.totalHrs = { 'idleHrs': 0, 'idleAmount': 0 };
		for (const key in subReportMap) {
			obj = subReportMap[key];
			$scope.totalHrs.idleHrs += obj.idleHrs;
			$scope.totalHrs.idleAmount += obj.idleAmount;
			idleHrs.push(obj.idleHrs);
			$scope.labels.push(obj.code);
			$scope.subReportData.push(obj);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data = idleHrs;
		initGraph('pie');
	}

	function setPeriodicalGraphData(tradePeriodicalReport, momentDateFormat) {
		// Group by date
		let obj = null;
		const dateGroupMap = new Object();
		$scope.totalHrs = { 'idleHrs': 0, 'idleAmount': 0 };
		let tradeNamesArray = new Array();
		$scope.subReportData = new Array();
		const reportData = new Array();
		for (const key in tradePeriodicalReport) {
			obj = tradePeriodicalReport[key];
			$scope.totalHrs.idleHrs += obj.idleHrs;
			$scope.totalHrs.idleAmount += obj.idleAmount;
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
		$scope.subReportData = _.sortBy(reportData, function (i) { return moment(i.code, momentDateFormat)._d });
		$scope.labels = new Array();
		const dateWiseTradeValues = new Object();

		$scope.series = tradeNamesArray;

		const sortedDays = _.sortBy(Object.keys(dateGroupMap), function (i) { return moment(i, momentDateFormat)._d });
		let valueProperty = 'idleHrs';
		if ($scope.subReport.code === "amountwise") {
			valueProperty = 'idleAmount';
		}
		for (const key of sortedDays) {
			obj = dateGroupMap[key];
			dateWiseTradeValues[key] = new Object();
			for (const tradeName of tradeNamesArray) {
				dateWiseTradeValues[key][tradeName] = 0;
			}
			for (const trade of obj.trades) {
				dateWiseTradeValues[key][trade.trade] = trade[valueProperty];
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
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		/////////////////////////
		/* $scope.labels = new Array();
		$scope.data = new Array();
		let obj = null;
		$scope.subReportData = new Array();
		$scope.totalHrs = { 'idleHrs': 0, 'idleAmount': 0 };
		for (const key of _.sortBy(Object.keys(subReportMap), function (i) { return moment(i.split("_")[0], momentDateFormat)._d })) {
			obj = subReportMap[key];
			$scope.labels.push(obj.code);
			$scope.data.push(obj.value);
			$scope.totalHrs.idleAmount += obj.value;
			$scope.subReportData.push(obj);
		} */
		initGraph('bar');
	}

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

	$scope.getCrewList = function () {
		if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
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
			$scope.manpowerDetails = [];
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
		});
	};
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
	$scope.selectChartType = function () {
		if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select Project", 'Warning');
			return;
		}
		if ($scope.subReport.name == null) {
			GenericAlertService.alertMessage("Please select Sub Report", 'Warning');
			return;
		}
	};

	$scope.getManpowerDetails = function () {
		if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.selectedCrewIds.length <= 0) {
			GenericAlertService.alertMessage("Please select the Crew", 'Warning');
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
			"crewIds": $scope.selectedCrewIds,
			"categories": $scope.categoryName,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
		}
		ManpowerReportService.getManpowerIdleHrsReport(req).then(function (data) {
			manpowerActualHrsData = data;
			$scope.gridOptions.data = angular.copy($scope.manpowerDetails);
			const empMap = new Array();
			$scope.employeeDetailsMap = new Array();
			let uniqueId = 0;
			const actualHrsList = new Array();
			for (const manHrs of manpowerActualHrsData) {
				empMap[uniqueId] = manHrs;
				for (const hr of manHrs.hrsList) {
					const newObj = {
						'uniqueId': uniqueId,
						'idleAmount': manHrs.idleRate * manHrs.wageFactorValue * hr.idleHrs
					};

					// Deep copy hr object
					for (const key in hr) {
						newObj[key] = hr[key];
					}
					actualHrsList.push(newObj);
				}
				uniqueId++;

			}
			$scope.manpowerDetails = actualHrsList;
			$scope.employeeDetailsMap = empMap;
			for (let manpower of actualHrsList) {
				manpower.dateStr = $filter('date')((manpower.date), "dd-MMM-yyyy");
				manpower.parentProjName = empMap[manpower.uniqueId].parentProjName;
				manpower.projName = empMap[manpower.uniqueId].projName;
				manpower.crewName = empMap[manpower.uniqueId].crewName;
				manpower.empCode = empMap[manpower.uniqueId].empCode;
				manpower.empFirstname = empMap[manpower.uniqueId].empFirstname;
				manpower.empLastname = empMap[manpower.uniqueId].empLastname;
				manpower.companyName = empMap[manpower.uniqueId].companyName;
				manpower.empClassname = empMap[manpower.uniqueId].empClassname;
				manpower.parentCostCode = empMap[manpower.uniqueId].parentCostCode;
				manpower.parentCostCodeName = empMap[manpower.uniqueId].parentCostCodeName;
				manpower.costCodeName = empMap[manpower.uniqueId].costCodeName;
				manpower.costCodeDesc = empMap[manpower.uniqueId].costCodeDesc;
				manpower.wageCode = empMap[manpower.uniqueId].wageCode;
				manpower.idleRate = empMap[manpower.uniqueId].idleRate;
			}
			$scope.gridOptions.data = angular.copy($scope.manpowerDetails);
			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			}
			if ($scope.manpowerDetails.length <= 0) {
				GenericAlertService.alertMessage("Manpower Idle Hours Records not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting Manpower details", 'Error');
		});
	};

	$scope.clearSubReportDetails = function () {
		$scope.labels = [];
		$scope.data = [];
		$scope.series = [];
		$scope.type = "";
		$scope.subReport = null;
		$scope.periodicSubReport = null;
	};

	$scope.resetManpowerDetails = function () {
		$scope.manpowerDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCrewIds = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.categoryName = null;
		$scope.crewNameDisplay = null;
		$scope.type = '';
		$scope.subReport = null;
		$scope.periodicSubReport = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	function initGraph(chartType) {
		if ($scope.subReport.code === "amountwise") {
			$scope.yAxislabels = 'Idle Amount';
		} else if ($scope.subReport.code === 'tradewise') {
			$scope.yAxislabels = 'Idle Hours';
		}
		$scope.chart_type = chartType;
		if (chartType != 'pie') {
			chartService.defaultBarInit($scope.yAxislabels);
		}
		else
			chartService.defaultPieInit();

	}

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'dateStr', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false  },
				{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false },
				{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" , groupingShowAggregationMenu: false },
				{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" , groupingShowAggregationMenu: false },
				{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false , groupingShowAggregationMenu: false },
				{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false , groupingShowAggregationMenu: false },
				{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name" , groupingShowAggregationMenu: false },
				{ field: 'empClassname', displayName: "Trade", headerTooltip: "Trade", visible: false, groupingShowAggregationMenu: false  },
				{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false , groupingShowAggregationMenu: false },
				{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" , groupingShowAggregationMenu: false },
				{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false , groupingShowAggregationMenu: false },
				{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor" , groupingShowAggregationMenu: false },
				{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hrs" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{ field: 'idleRate', displayName: "Idle Rate", headerTooltip: "Idle Rate", groupingShowAggregationMenu: false , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{ field: 'idleAmount', displayName: "Idle Amt", headerTooltip: "Idle Amount" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Idle_HR");
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
			template: 'views/help&tutorials/reportshelp/manpowerhelp/manpoweridlehrshelp.html',
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
