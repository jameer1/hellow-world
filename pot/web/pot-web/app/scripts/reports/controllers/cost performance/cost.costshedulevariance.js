'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformancecostschedulevariance", {
		url: '/costperformancecostschedulevariance',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/costshedulevariance.html',
				controller: 'CostSheduleVarianceController'
			}
		}
	})
}]).controller("CostSheduleVarianceController", ["$scope", "uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog",  "$filter", "BudgetService", "GenericAlertService", "EpsProjectMultiSelectFactory",
	"CostCodeMultiSelectFactory", "PlannedValuesService", "dateGroupingService", "stylesService", "ngGridService", "chartService", function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog,  $filter, BudgetService, GenericAlertService,
		EpsProjectMultiSelectFactory, CostCodeMultiSelectFactory, PlannedValuesService, dateGroupingService, stylesService, ngGridService, chartService) {
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Amount';
		const colors = ['#ff0000', '#ffff00', '#800080', '#ffa500', '#ff00ff', '#800000', '#0000ff'];
		const series = ["Cost Variance", "Schedule Variance"];
		$scope.reportName = "Cost & Schedule Variance";
		$scope.stylesSvc = stylesService;
		$scope.subReportCode = "";
		$scope.subReportName = "";
		$scope.costScheduleVarianceDetails = [];
		$scope.dateWiseDetails = [];
		$scope.plannedValues = [];
		$scope.subReport = 'None';
		$scope.type = 'chartTable';
		$scope.selectedProjIds = [];
		$scope.selectedCostCodeIds = [];
		let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.$watch('fromDate', function () {
			$scope.checkErr();
			clearSubReportDetails();
		});
		$scope.$watch('toDate', function () {
			$scope.checkErr();
			clearSubReportDetails();
		});
		$scope.checkErr = function () {
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Info');
				return;
			}
		};
		$scope.subReports = [{
			name: 'CostCodeWise Cost Variance and Schedule Variance Report',
			code: "costcode"
		}, {
			name: 'ProjectWise Cost Variance and Schedule Variance Report',
			code: "proj"
		}, {
			name: 'EPSWise Cost and Schedule Variance Report',
			code: "eps"
		}, {
			name: 'Periodical Cost and Schedule Variance Report',
			code: "periodical"
		}];
		$scope.periodicSubReports = [{
			name: 'Daily',
			code: "daily"
		}, {
			name: 'Weekly',
			code: "weekly"
		}, {
			name: 'Montly',
			code: "monthly"
		}, {
			name: 'Yearly',
			code: "yearly"
		}];
		$scope.periodicSubReport = $scope.periodicSubReports[0];
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
		$scope.getCostCodes = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select project to get CostCodes", 'Info');
				return;
			}
			var costCodeReq = {
				"status": 1,
				"projIds": $scope.selectedProjIds
			};
			var costCodePopUp = CostCodeMultiSelectFactory.getMultiProjCostCodes(costCodeReq);
			costCodePopUp.then(function (data) {
				$scope.costCodeNameDisplay = data.selectedCostCodes.costCodesName;
				$scope.selectedCostCodeIds = data.selectedCostCodes.costCodeIds;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
			})
		};
		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group" , groupingShowAggregationMenu: false},
				{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" , groupingShowAggregationMenu: false},
				{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" , groupingShowAggregationMenu: false},
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" , groupingShowAggregationMenu: false},
				{ field: 'costVariance', displayName: "Cost Variance", headerTooltip: "Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'scheduleVariance', displayName: "Schedule Variance", headerTooltip: "Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
		}
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				if ($scope.costScheduleVarianceDetails.length == 0) {
					$scope.getCostScheduleVarianceDetails();
				} else {
					prepareSubReport();
				}
			} else {
				$scope.dateWiseDetails = [];
				$scope.plannedValues = [];
				$scope.costScheduleVarianceDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getCostScheduleVarianceDetails();
			}
		};
		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartTable';
			let groupByFunction = null;
			let dateFormat;
			let reportSettingProp;

			if ($scope.subReport.code == "periodical") {
				let periodicalData = [
					{ field: 'mapValue', displayName: "Period" , groupingShowAggregationMenu: false},
					{ field: 'costVariance', displayName: "Cost Variance", headerTooltip: "Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cummulativeCost', displayName: "Cumm Cost Variance", headerTooltip: "Cummulative Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'scheduleVariance', displayName: "Schedule Variance", headerTooltip: "Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cummulativeSchedule', displayName: "Cumm Sched Variance", headerTooltip: "Cummulative Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, periodicalData, data, "Reports_Cost&Performance_Cost&ScheduleVariance_Periodical");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			switch ($scope.periodicSubReport.code) {
				case "daily":
					groupByFunction = dateGroupingService.groupByDate;
					dateFormat = "dd-MMM-yyyy";
					break;
				case "weekly":
					groupByFunction = dateGroupingService.groupByWeek;
					dateFormat = "dd-MMM-yyyy";
					reportSettingProp = 'Monday';
					break;
				case "monthly":
					groupByFunction = dateGroupingService.groupByMonth;
					dateFormat = "MMM-yyyy";
					reportSettingProp = '';
					break;
				case "yearly":
					groupByFunction = dateGroupingService.groupByYear;
					dateFormat = "yyyy";
					reportSettingProp = '';
					break;
				default:
					break;
			}
			const periodicalMap = new Array();

			const groupedList = groupByFunction($scope.dateWiseDetails, reportSettingProp);
			let cummulativeCost = 0, cummulativeSchedule = 0;
			for (const group of groupedList) {
				const mapKey = group.date;
				if (!periodicalMap[mapKey]) {
					periodicalMap[mapKey] = {
						'mapKey': mapKey,
						'mapValue': $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat),
						"costVariance": 0,
						"cummulativeCost": cummulativeCost,
						"scheduleVariance": 0,
						"cummulativeSchedule": cummulativeSchedule
					};
				}
				for (const val of group.values) {
					periodicalMap[group.date].costVariance = fixedDecimal(periodicalMap[group.date].costVariance + val.costVariance);
					cummulativeCost += val.costVariance;
					periodicalMap[group.date].scheduleVariance = fixedDecimal(periodicalMap[group.date].scheduleVariance + val.scheduleVariance);
					cummulativeSchedule += val.scheduleVariance;
				}
				periodicalMap[group.date].cummulativeCost = fixedDecimal(cummulativeCost);
				periodicalMap[group.date].cummulativeSchedule = fixedDecimal(cummulativeSchedule);
			}
			setGraphData(periodicalMap);
		};
		function prepareSubReport() {
			$scope.subReportData = [];
			if ($scope.subReport.code == "costcode") {
				generateSubReportData("costItemId", "costItemCode");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			} else if ($scope.subReport.code == "periodical") {
				if ($scope.periodicSubReport && $scope.periodicSubReport.code) {
					$scope.periodicSubReport = $scope.periodicSubReports[0];
					$scope.changePeriodicSubReport();
				}
			}
		};
		function generateSubReportData(key, value) {
			let subReportMap = [];
			chartService.getChartMenu($scope);
			chartService.defaultBarInit($scope.yAxislabels);
			if ($scope.subReport.code == "costcode") {
				let costCodeData = [
					{ field: 'projectName', displayName: "Project Name", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID" , groupingShowAggregationMenu: false},
					{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" , groupingShowAggregationMenu: false},
					{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" , groupingShowAggregationMenu: false},
					{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Name" , groupingShowAggregationMenu: false},
					{ field: 'costVariance', displayName: "Cost Variance", headerTooltip: "Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'scheduleVariance', displayName: "Sched Variance", headerTooltip: "Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_Cost&ScheduleVariance_CostCode");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let projData = [
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false},
					{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'costVariance', displayName: "Cost Variance", headerTooltip: "Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'scheduleVariance', displayName: "Sched Variance", headerTooltip: "Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, projData, data, "Reports_Cost&Performance_Cost&ScheduleVariance_Proj");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let epsData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'costVariance', displayName: "Cost Variance", headerTooltip: "Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'scheduleVariance', displayName: "Sched Variance", headerTooltip: "Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_Cost&Performance_Cost&ScheduleVariance_EPS");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			for (let budget of $scope.dateWiseDetails) {
				let mapKey = budget[key];
				let mapValue = budget[value];
				if (!subReportMap[mapKey]) {
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"currencyCode": budget["currencyCode"],
						"epsName": budget["epsName"],
						"projectName": budget["projName"],
						"costVariance": 0,
						"scheduleVariance": 0
					};
					if (key == "costItemId") {
						subReportMap[mapKey].parentCostCode = budget["costSubGroupCode"];
						subReportMap[mapKey].parentCostName = budget["costSubGroupName"];
						subReportMap[mapKey].costName = budget["costItemName"];
					}
				}
				subReportMap[mapKey].costVariance = fixedDecimal(subReportMap[mapKey].costVariance + parseFloat(budget["costVariance"]));
				subReportMap[mapKey].scheduleVariance = fixedDecimal(subReportMap[mapKey].scheduleVariance + parseFloat(budget["scheduleVariance"]));
			}
			setGraphData(subReportMap);
		};
		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let costArr = [], scheduleArr = [], cumCostArr = [], cumScheduleArr = [];
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				costArr.push(subReportMap[index].costVariance);
				cumCostArr.push(subReportMap[index].cummulativeCost);
				scheduleArr.push(subReportMap[index].scheduleVariance);
				cumScheduleArr.push(subReportMap[index].cummulativeSchedule);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.datasetOverride = new Array();
			$scope.data.push(costArr);
			$scope.datasetOverride.push({
				label: 'Cost Variance',
				type: 'bar',
				id: 'y-axis-1',
			});
			if ($scope.subReport.code == "periodical") {
				$scope.data.push(cumCostArr);
				$scope.datasetOverride.push({
					type: 'line',
					label: 'Cummulative Cost Variance',
					fill: false,
					borderColor: colors[4],
					backgroundColor: colors[4],
					pointBorderColor: colors[4],
					pointBackgroundColor: colors[4],
					pointHoverBackgroundColor: colors[4],
					pointHoverBorderColor: colors[4],
					yAxisID: 'y-axis-2'
				});
			}
			$scope.data.push(scheduleArr);
			$scope.datasetOverride.push({
				label: 'Schedule Variance',
				type: 'bar',
				id: 'y-axis-1',
			});
			if ($scope.subReport.code == "periodical") {
				$scope.data.push(cumScheduleArr);
				$scope.datasetOverride.push({
					type: 'line',
					label: 'Cummulative Schedule Variance',
					fill: false,
					borderColor: colors[4],
					backgroundColor: colors[4],
					pointBorderColor: colors[4],
					pointBackgroundColor: colors[4],
					pointHoverBackgroundColor: colors[4],
					pointHoverBorderColor: colors[4],
					yAxisID: 'y-axis-2'
				});
			}
			if ($scope.subReport.code == "periodical") {
				initPeriodicalGraph();
			} else {
				initGraph();
			}
		};
		function initPeriodicalGraph() {
			$scope.menuOptions = [];
			$scope.series = series;
			$scope.options = {
				elements: {
					rectangle: {
						borderWidth: 2,
						borderColor: '#98c4f9',
						borderSkipped: 'bottom'
					}
				},
				responsive: true,
				legend: {
					display: true,
				},
				scales: {
					yAxes: [{
						display: true,
						id: "y-axis-1",
						ticks: {
							beginAtZero: true,
							min: 0
						},
						min: 0,
						beginAtZero: true,
						position: "left",
						scaleLabel: {
							display: true,
							labelString: 'Periodical Amount'
						}
					}, {
						type: "linear",
						display: true,
						id: "y-axis-2",
						labels: {
							show: true,
						},
						beginAtZero: true,
						position: "right",
						scaleLabel: {
							display: true,
							labelString: 'Cummulative Amount'
						}
					}],
					xAxes: [{
						display: true,
						ticks: {
							fontSize: 8
						}
					}],
				}
			};
		};
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);

		};
		$scope.getCostScheduleVarianceDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
				return;
			}
			if ($scope.selectedCostCodeIds.length <= 0) {
				GenericAlertService.alertMessage("Please select Cost Code to fetch report", 'Info');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"costcodeIds": $scope.selectedCostCodeIds,
				"categories": ["direct", "in-direct"],
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			$scope.dateWiseDetails = [];
			$scope.plannedValues = [];
			BudgetService.getDateWisePlanActualEarned(req).then(function (data) {
				$scope.dateWiseDetails = data.costReportResps;
				$scope.gridOptions.data = angular.copy($scope.dateWiseDetails);
				if ($scope.subReport != 'None')
					prepareSubReport();
				processPlannedValue();
				if ($scope.dateWiseDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while fetching schedule and cost variance", 'Error');
			});
			PlannedValuesService.getCostPlannedValuesBetween($scope.selectedProjIds, $scope.fromDate, $scope.toDate, false).then(function (data) {
				$scope.plannedValues = data;
				processPlannedValue();
			}, function () {
				GenericAlertService.alertMessage("Error occured while fetching planned values", 'Error');
			});
		};
		function processPlannedValue() {
			if ($scope.plannedValues.length > 0 && $scope.dateWiseDetails.length > 0) {
				processToCostValue();
			}
		};
		function processToCostValue() {
			for (const dateWise of $scope.dateWiseDetails) {
				let existing = $scope.costScheduleVarianceDetails.find(function (data) {
					return dateWise.projId === data.projId && dateWise.costItemId == data.costItemId;
				});
				calculatePVSV(dateWise);
				if (existing) {
					existing.costVariance = fixedDecimal(existing.costVariance + dateWise.costVariance);
					existing.scheduleVariance = fixedDecimal(existing.scheduleVariance + dateWise.scheduleVariance);
				} else {
					$scope.costScheduleVarianceDetails.push(dateWise);
				}
			}
			$scope.gridOptions.data = angular.copy($scope.costScheduleVarianceDetails);
			if ($scope.subReport != 'None')
				prepareSubReport();
		};
		function calculatePVSV(element) {
			element.costVariance = fixedDecimal(element.earnedValue - element.costAmount);
			element.scheduleVariance = fixedDecimal(element.earnedValue - element.plannedValue);
		};
		function fixedDecimal(value) {
			return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
		}
		$scope.resetCostScheduleDetails = function () {
			$scope.dateWiseDetails = [];
			$scope.costScheduleVarianceDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.companyNameDisplay = null;
			$scope.costCodeNameDisplay = null;
			$scope.type = '';
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.subReportName = null;
			$scope.subReport = 'None';
			$scope.periodicSubReport = $scope.periodicSubReports[0];
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		function clearSubReportDetails() {
			$scope.costScheduleVarianceDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.type = 'chartTable';
			$scope.subReport = 'None';
			$scope.periodicSubReport = $scope.periodicSubReports[0];
		};

		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id" , groupingShowAggregationMenu: false},
					{ field: 'costSubGroupName', displayName: "Cost Code Sub Group", headerTooltip: "Cost Code Sub Group" , groupingShowAggregationMenu: false},
					{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" , groupingShowAggregationMenu: false},
					{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" , groupingShowAggregationMenu: false},
					{ field: 'costVariance', displayName: "Cost Variance", headerTooltip: "Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'scheduleVariance', displayName: "Schedule Variance", headerTooltip: "Schedule Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Cost&Performance_Cost&ScheduleVariance");
			}
		});
		var HelpService = {};
		$scope.helpPage = function () {
			var help = HelpService.pageHelp();
			help.then(function(data) {

			}, function(error) {
				GenericAlertService.alertMessage("Error",'Error');
			})
		}
		var helppagepopup;
		HelpService.pageHelp = function () {
			var deferred = $q.defer();
			helppagepopup = ngDialog.open({
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costschedulevariancehelp.html',
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
