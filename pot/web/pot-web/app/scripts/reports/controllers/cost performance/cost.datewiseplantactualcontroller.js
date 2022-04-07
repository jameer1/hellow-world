'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformancedatewiseplantandactual", {
		url: '/costperformancedatewiseplantandactual',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/datewisewiseplantactualcost.html',
				controller: 'DateWisePlantActualCostController'
			}
		}
	})
}]).controller("DateWisePlantActualCostController", ["$scope", "uiGridGroupingConstants", "uiGridConstants",  "$q", "ngDialog","$filter", "BudgetService", "CostCodeMultiSelectFactory", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "PlannedValuesService", "dateGroupingService", "stylesService", "ngGridService", "chartService", function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog, $filter, BudgetService, CostCodeMultiSelectFactory,
		GenericAlertService, EpsProjectMultiSelectFactory, PlannedValuesService, dateGroupingService, stylesService, ngGridService, chartService) {
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Variance';
		let labels = [];
		const colors = ['#ff0000', '#ffff00', '#800080', '#ffa500', '#ff00ff', '#800000', '#0000ff'];
		const series = ['Planned Cost', 'Actual Cost', 'Earned Value'];
		$scope.reportName = "Date Wise - Plan Vs Actual Vs Earned";
		$scope.stylesSvc = stylesService;
		$scope.subReport = 'None';
		$scope.subReportData = [];
		$scope.type = 'chartTable';
		$scope.selectedProjIds = [];
		$scope.selectedCostCodeIds = [];
		$scope.dateWiseDetails = [];
		$scope.plannedValues = [];
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
			name: 'CostCodeWise Cost and Earned Value Report',
			code: "costcode"
		}, {
			name: 'ProjectWise Cost and Earned Value Report',
			code: "proj"
		}, {
			name: 'EPSWise Cost and Earned Value Report',
			code: "eps"
		}, {
			name: 'Perodical Cost and Earned Value Report',
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
		$scope.periodicSubReport = $scope.periodicSubReports[0];
		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				clearSubReportDetails();
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
				clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
			})
		};

		function noSubReportData() {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", groupingShowAggregationMenu: false },
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group ID", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costSubGroupName', displayName: "Cost Code Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costItemCode', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false },
				{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'costAmount', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Cost&Performance_DateWisePlanVsActualVsEarned");
		}

		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				if ($scope.dateWiseDetails.length == 0) {
					$scope.getDatewisePlantActualDetails();
				} else {
					prepareSubReport();
				}
			} else {
				$scope.gridOptions.data = [];
				$scope.dateWiseDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getDatewisePlantActualDetails();
			}
		};
		function prepareSubReport() {
			initGraph();
			$scope.subReportData = [];
			if ($scope.subReport.code == "costcode") {
				generateSubReportData("costItemId", "costItemCode");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			} else if ($scope.subReport.code == "periodical") {
				if ($scope.periodicSubReport && $scope.periodicSubReport.code) {
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
					{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID", groupingShowAggregationMenu: false },
					{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
					{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
					{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Name", groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualValue', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_DateWisePlanVsActualVsEarned_CostCode");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let projData = [
					{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualValue', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, projData, data, "Reports_Cost&Performance_DateWisePlanVsActualVsEarned_Proj");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let epsData = [
					{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualValue', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_Cost&Performance_DateWisePlanVsActualVsEarned_EPS");
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
						"plannedValue": 0,
						"actualValue": 0,
						"earnedValue": 0,
						"projName": budget["projName"],
						"epsName": budget["epsName"]
					};
					if (key == "costItemId") {
						subReportMap[mapKey].parentCostCode = budget["costSubGroupCode"];
						subReportMap[mapKey].parentCostName = budget["costSubGroupName"];
						subReportMap[mapKey].costName = budget["costItemName"];
					}
				}
				subReportMap[mapKey].plannedValue = fixedDecimal(subReportMap[mapKey].plannedValue + parseFloat(budget["plannedValue"]));
				subReportMap[mapKey].actualValue = fixedDecimal(subReportMap[mapKey].actualValue + parseFloat(budget["costAmount"]));
				subReportMap[mapKey].earnedValue = fixedDecimal(subReportMap[mapKey].earnedValue + parseFloat(budget["earnedValue"]));
			}
			setGraphData(subReportMap);
		};
		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartTable';
			let groupByFunction = null;
			let dateFormat;
			let reportSettingProp;

			if ($scope.subReport.code == "periodical") {
				let periodicalData = [
					{ field: 'mapValue', displayName: "Period", groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativePlanned', displayName: "Cumm Plan Cost", headerTooltip: "Cummulative Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualValue', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativeActual', displayName: "Cumm Actual Cost", headerTooltip: "Cummulative Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativeEarned', displayName: "Cumm Earn Value", headerTooltip: "Cummulative Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, periodicalData, data, "Reports_Cost&Performance_DateWisePlanVsActualVsEarned_Periodical");
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
			let cummulativePlanned = 0, cummulativeActual = 0, cummulativeEarned = 0;
			for (const group of groupedList) {
				const mapKey = group.date;
				if (!periodicalMap[mapKey]) {
					periodicalMap[mapKey] = {
						'mapKey': mapKey,
						'mapValue': $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat),
						"plannedValue": 0,
						"cummulativePlanned": cummulativePlanned,
						"actualValue": 0,
						"cummulativeActual": cummulativeActual,
						"earnedValue": 0,
						"cummulativeEarned": cummulativeEarned
					};
				}
				for (const val of group.values) {
					periodicalMap[group.date].plannedValue = fixedDecimal(periodicalMap[group.date].plannedValue + val.plannedValue);
					cummulativePlanned += val.plannedValue;
					periodicalMap[group.date].actualValue = fixedDecimal(periodicalMap[group.date].actualValue + val.costAmount);
					cummulativeActual += val.costAmount;
					periodicalMap[group.date].earnedValue = fixedDecimal(periodicalMap[group.date].earnedValue + val.earnedValue);
					cummulativeEarned += val.earnedValue;
				}
				periodicalMap[group.date].cummulativePlanned = fixedDecimal(cummulativePlanned);
				periodicalMap[group.date].cummulativeActual = fixedDecimal(cummulativeActual);
				periodicalMap[group.date].cummulativeEarned = fixedDecimal(cummulativeEarned);
			}
			setGraphData(periodicalMap);
		};

		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let plannedArr = [], actualArr = [], earnedArr = [];
			let cumPlannedArr = [], cumActualArr = [], cumEarnedArr = [];
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				if (subReportMap[index].plannedValue > 0 || subReportMap[index].actualValue > 0 || subReportMap[index].earnedValue > 0) {
					plannedArr.push(fixedDecimal(subReportMap[index].plannedValue));
					cumPlannedArr.push(fixedDecimal(subReportMap[index].cummulativePlanned));
					actualArr.push(fixedDecimal(subReportMap[index].actualValue));
					cumActualArr.push(fixedDecimal(subReportMap[index].cummulativeActual));
					earnedArr.push(fixedDecimal(subReportMap[index].earnedValue));
					cumEarnedArr.push(fixedDecimal(subReportMap[index].cummulativeEarned));
					$scope.labels.push(subReportMap[index].mapValue);
					$scope.subReportData.push(subReportMap[index]);
				}
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.datasetOverride = new Array();
			$scope.data.push(plannedArr);
			$scope.datasetOverride.push({
				label: 'Planned Cost',
				type: 'bar',
				id: 'y-axis-1',
			});
			if ($scope.subReport.code == "periodical") {
				$scope.data.push(cumPlannedArr);
				$scope.datasetOverride.push({
					type: 'line',
					label: 'Cummulative Planned',
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
			$scope.data.push(actualArr);
			$scope.datasetOverride.push({
				label: 'Actual Cost',
				type: 'bar',
				id: 'y-axis-1',
			});
			if ($scope.subReport.code == "periodical") {
				$scope.data.push(cumActualArr);
				$scope.datasetOverride.push({
					type: 'line',
					label: 'Cummulative Actual',
					fill: false,
					borderColor: colors[5],
					backgroundColor: colors[5],
					pointBorderColor: colors[5],
					pointBackgroundColor: colors[5],
					pointHoverBackgroundColor: colors[5],
					pointHoverBorderColor: colors[5],
					yAxisID: 'y-axis-2'
				});
			}
			$scope.data.push(earnedArr);
			$scope.datasetOverride.push({
				label: 'Earned Value',
				type: 'bar',
				id: 'y-axis-1',
			});
			if ($scope.subReport.code == "periodical") {
				$scope.data.push(cumEarnedArr);
				$scope.datasetOverride.push({
					type: 'line',
					label: 'Cummulative Earned',
					fill: false,
					borderColor: colors[6],
					backgroundColor: colors[6],
					pointBorderColor: colors[6],
					pointBackgroundColor: colors[6],
					pointHoverBackgroundColor: colors[6],
					pointHoverBorderColor: colors[6],
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
		$scope.getDatewisePlantActualDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
				return;
			}
			if ($scope.selectedCostCodeIds.length <= 0) {
				GenericAlertService.alertMessage("Please select cost code to fetch report", 'Info');
				return;
			}
			$scope.plannedValues = [];
			$scope.dateWiseDetails = [];
			var req = {
				"projIds": $scope.selectedProjIds,
				"costcodeIds": $scope.selectedCostCodeIds,
				"categories": ["direct", "in-direct"],
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			BudgetService.getDateWisePlanActualEarned(req).then(function (data) {
				$scope.dateWiseDetails = data.costReportResps;
				$scope.gridOptions.data = angular.copy($scope.dateWiseDetails);
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.dateWiseDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while cost code actual details", 'Error');
			});
			
			initGraph();
		};
		$scope.resetDateWisePlannedActualDetails = function () {
			$scope.dateWiseDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.selectedCostCodeIds = [];
			$scope.costCodeNameDisplay = null;
			$scope.type = 'chartTable';
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.subReportName = null;
			$scope.subReportCode = null;
			$scope.subReport = 'None';
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		function clearSubReportDetails() {
			$scope.dateWiseDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.type = 'chartTable';
			$scope.subReport = 'None';
		};


		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
					{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", groupingShowAggregationMenu: false },
					{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group ID", visible: false, groupingShowAggregationMenu: false },
					{ field: 'costSubGroupName', displayName: "Cost Code Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false },
					{ field: 'costItemCode', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
					{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'costAmount', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Cost&Performance_DateWisePlanVsActualVsEarned");
				
			}
		});
		function fixedDecimal(value) {
			return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
		}
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
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costdatewiseplantactualhelp.html',
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
