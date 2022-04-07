'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformanceitemwiseplantactualcost", {
		url: '/costperformanceitemwiseplantactualcost',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/itemwiseplantactualcost.html',
				controller: 'ItemWisePlantActualCostController'
			}
		}
	})
}]).controller("ItemWisePlantActualCostController", ["$scope","uiGridGroupingConstants", "$q", "ngDialog","$filter", "GenericAlertService", "EpsProjectMultiSelectFactory", "BudgetService",
	'PlannedValuesService', 'dateGroupingService', 'stylesService', 'ngGridService', "uiGridConstants", function ($scope, uiGridGroupingConstants, $q, ngDialog, $filter, GenericAlertService,
		EpsProjectMultiSelectFactory, BudgetService, PlannedValuesService, dateGroupingService, stylesService, ngGridService, uiGridConstants) {
		const colors = ['#ff0000', '#ffff00', '#800080', '#ffa500', '#ff00ff', '#800000', '#0000ff'];
		$scope.series = [];
		$scope.plannedValues = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.stylesSvc = stylesService;
		$scope.reportName = "Item Wise - Plan Vs Actual Cost";
		$scope.subReportName = "";
		$scope.subReportCode = "";
		$scope.subReport = 'None';
		$scope.type = 'chartTable';
		$scope.selectedProjIds = [];
		$scope.itemPlanActualDetails = [];
		$scope.budgetRatios = [];
		$scope.subReports = [{
			name: 'Periodical Planned Cost',
			code: "planned",
			value: "Planned"
		}, {
			name: 'Periodical Actual Cost',
			code: "actual",
			value: "Actual"
		}, {
			name: 'Periodical Labour Planned Cost',
			code: "labour",
			value: "Labour"
		}, {
			name: 'Periodical Plant Planned Cost',
			code: "plant",
			value: "Plant"
		}, {
			name: 'Periodical Material Planned Cost',
			code: "material",
			value: "Material"
		}, {
			name: 'Periodical other Planned Cost',
			code: "other",
			value: "Other"
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
		function noSubReportData() {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: 'Date' , groupingShowAggregationMenu: false},
				{ field: 'epsName', displayName: "EPS", headerTooltip: 'EPS', visible: false, groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: 'Project' , groupingShowAggregationMenu: false},
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: 'Currency', visible: false, groupingShowAggregationMenu: false },
				{ field: 'labourPlanned', displayName: "Manpower Planned", headerTooltip: 'Manpower Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'labourActual', displayName: "Manpower Actual", headerTooltip: 'Manpower Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'plantPlanned', displayName: "Plant Planned", headerTooltip: 'Plant Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'plantActual', displayName: "Plant Actual", headerTooltip: 'Plant Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'matPlanned', displayName: "Material Planned", headerTooltip: 'Material Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'matActual', displayName: "Material Actual", headerTooltip: 'Material Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'otherPlanned', displayName: "Other Planned", headerTooltip: 'Other Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'otherActual', displayName: "Other Actual", headerTooltip: 'Other Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'totalPlanned', displayName: "Total Planned", headerTooltip: 'Total Planned Cost', visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'totalActual', displayName: "Total Actual", headerTooltip: 'Total Actual Cost', visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Cost&Performance_ItemWise_PlanVsActualCost");
		}
		$scope.changePeriodicSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code;
				if ($scope.itemPlanActualDetails.length == 0) {
					$scope.getItemwisePlanActualDetails();
				} else {
					prepareSubReport();
				}
			} else {
				$scope.plannedValues = [];
				$scope.itemPlanActualDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getItemwisePlanActualDetails();
			}
		};
		function prepareSubReport() {
			initGraph();
			$scope.type = 'chartTable';
			let groupByFunction = null;
			let dateFormat, momentDateFormat;
			let reportSettingProp;
			switch ($scope.periodicSubReport.code) {
				case "daily":
					groupByFunction = dateGroupingService.groupByDate;
					dateFormat = "dd-MMM-yyyy";
					momentDateFormat = "DD-MMM-YYYY";
					break;
				case "weekly":
					groupByFunction = dateGroupingService.groupByWeek;
					dateFormat = "dd-MMM-yyyy";
					momentDateFormat = "ddd MMM DD YYYY";
					reportSettingProp = 'Monday';
					break;
				case "monthly":
					groupByFunction = dateGroupingService.groupByMonth;
					dateFormat = "MMM-yyyy";
					momentDateFormat = "ddd MMM DD YYYY";
					reportSettingProp = '';
					break;
				case "yearly":
					groupByFunction = dateGroupingService.groupByYear;
					dateFormat = "yyyy";
					momentDateFormat = "ddd MMM DD YYYY";
					reportSettingProp = '';
					break;
				default:
					break;
			}
			const groupedList = groupByFunction($scope.itemPlanActualDetails, reportSettingProp);
			if ($scope.subReportCode === "planned" || $scope.subReportCode === "actual") {
				setCostOrPlannedData(groupedList, dateFormat);
			} else {
				setPlannedActualData(groupedList, dateFormat);
			}
		}
		function setPlannedActualData(groupedList, dateFormat, momentDateFormat) {
			const periodicalMap = new Array();
			let plannedCum = 0, actualCum = 0;

			if ($scope.subReportCode != 'planned' && $scope.subReportCode != 'actual') {
				let planActualData = [
				    { field: 'mapValue', displayName: "Period-" + $scope.periodicSubReport.name, headerTooltip: "Period", groupingShowAggregationMenu: false, },
					{ field: 'planned', displayName: $scope.subReport.value + "-Planned", headerTooltip: "Plant", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativePlanned', displayName: "Cumm-" + $scope.subReport.value + "-Plan", headerTooltip: "Cummulative Planned", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actual', displayName: $scope.subReport.value + "-Actual", headerTooltip: "Actual", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativeActual', displayName: "Cumm-" + $scope.subReport.value + "-Actual", headerTooltip: "Cummulative Actual", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, planActualData, data, "Reports_Cost&Performance_ItemWise_PlanVsActualCost_Actual");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			for (const group of groupedList) {
				const mapKey = group.date;
				if (!periodicalMap[mapKey]) {
					periodicalMap[mapKey] = {
						'mapKey': mapKey,
						'mapValue': $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat),
						"planned": 0,
						"cummulativePlanned": plannedCum,
						"actual": 0,
						"cummulativeActual": actualCum,
					};
				}
				for (const val of group.values) {
					if ($scope.subReportCode == "labour") {
						periodicalMap[group.date].planned += val.labourPlanned;
						plannedCum += val.labourPlanned;
						periodicalMap[group.date].actual += val.labourActual;
						actualCum += val.labourActual;
					} else if ($scope.subReportCode == "plant") {
						periodicalMap[group.date].planned += val.plantPlanned;
						plannedCum += val.plantPlanned;
						periodicalMap[group.date].actual += val.plantActual;
						actualCum += val.plantActual;
					} else if ($scope.subReportCode == "material") {
						periodicalMap[group.date].planned += val.matPlanned;
						plannedCum += val.matPlanned;
						periodicalMap[group.date].actual += val.matActual;
						actualCum += val.matActual;
					} else if ($scope.subReportCode == "other") {
						periodicalMap[group.date].planned += val.otherPlanned;
						plannedCum += val.otherPlanned;
						periodicalMap[group.date].actual += val.otherActual;
						actualCum += val.otherActual;
					}
				}
				periodicalMap[group.date].cummulativePlanned = fixedDecimal(plannedCum);
				periodicalMap[group.date].cummulativeActual = fixedDecimal(actualCum);
			}
			let newMapArr = new Array();
			if (momentDateFormat) {
				const sortedDays = _.sortBy(Object.keys(periodicalMap), function (i) { return moment(i, momentDateFormat)._d });
				for (const key of sortedDays) {
					newMapArr[key] = periodicalMap[key];
				}
			} else {
				newMapArr = periodicalMap;
			}
			$scope.labels = new Array();
			$scope.data = new Array();
			let plannedArr = [], actualArr = [], planCumArr = [], actualCumArr = [];
			$scope.subReportData = new Array();
			for (const index in newMapArr) {
				newMapArr[index].planned = fixedDecimal(newMapArr[index].planned);
				plannedArr.push(newMapArr[index].planned);
				planCumArr.push(newMapArr[index].cummulativePlanned);
				newMapArr[index].actual = fixedDecimal(newMapArr[index].actual);
				actualArr.push(newMapArr[index].actual);
				actualCumArr.push(newMapArr[index].cummulativeActual);
				$scope.labels.push(newMapArr[index].mapValue);
				$scope.subReportData.push(newMapArr[index]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.datasetOverride = new Array();
			$scope.data.push(plannedArr);
			$scope.datasetOverride.push({
				label: 'Planned Cost',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(planCumArr);
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
			$scope.data.push(actualArr);
			$scope.datasetOverride.push({
				label: 'Actual Cost',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(actualCumArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'Cummulative Actual',
				fill: false,
				borderColor: colors[4],
				backgroundColor: colors[4],
				pointBorderColor: colors[4],
				pointBackgroundColor: colors[4],
				pointHoverBackgroundColor: colors[4],
				pointHoverBorderColor: colors[4],
				yAxisID: 'y-axis-2'
			});
			initGraph();
		}
		function setCostOrPlannedData(groupedList, dateFormat) {
			const periodicalMap = new Array();
			let labourCum = 0, plantCum = 0, matCum = 0, otherCum = 0;

			if ($scope.subReportCode == 'planned' || $scope.subReportCode == 'actual') {
				let planActualData = [
					{ field: 'mapValue', displayName: "Period-" + $scope.periodicSubReport.name, headerTooltip: "Period", groupingShowAggregationMenu: false, },
					{ field: 'labour', displayName: "Man Power", headerTooltip: "Man Power", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativeLabour', displayName: "Cumm M.P", headerTooltip: "Cummulative Man Power", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'plant', displayName: "Plant", headerTooltip: "Plant", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativePlant', displayName: "Cumm Plant", headerTooltip: "Cummulative Plant", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'material', displayName: "Material", headerTooltip: "Material", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativeMaterial', displayName: "Cumm Mat", headerTooltip: "Cummulative Material", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'other', displayName: "Other", headerTooltip: "Other", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'cummulativeOther', displayName: "Cumm Other", headerTooltip: "Cummulative Other", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, planActualData, data, "Reports_Cost&Performance_ItemWise_PlanVsActualCost_Planned");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			for (const group of groupedList) {
				const mapKey = group.date;
				if (!periodicalMap[mapKey]) {
					periodicalMap[mapKey] = {
						'mapKey': mapKey,
						'mapValue': $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat),
						"labour": 0,
						"cummulativeLabour": labourCum,
						"plant": 0,
						"cummulativePlant": plantCum,
						"material": 0,
						"cummulativeMaterial": matCum,
						"other": 0,
						"cummulativeOther": otherCum
					};
				}
				for (const val of group.values) {
					if ($scope.subReportCode === "planned") {
						periodicalMap[group.date].labour += val.labourPlanned;
						labourCum += val.labourPlanned;
						periodicalMap[group.date].plant += val.plantPlanned;
						plantCum += val.plantPlanned;
						periodicalMap[group.date].material += val.matPlanned;
						matCum += val.matPlanned;
						periodicalMap[group.date].other += val.otherPlanned;
						otherCum += val.otherPlanned;
					} else if ($scope.subReportCode === "actual") {
						periodicalMap[group.date].labour += val.labourActual;
						labourCum += val.labourActual;
						periodicalMap[group.date].plant += val.plantActual;
						plantCum += val.plantActual;
						periodicalMap[group.date].material += val.matActual;
						matCum += val.matActual;
						periodicalMap[group.date].other += val.otherActual;
						otherCum += val.otherActual;
					}
				}
				periodicalMap[group.date].cummulativeLabour = fixedDecimal(labourCum);
				periodicalMap[group.date].cummulativePlant = fixedDecimal(plantCum);
				periodicalMap[group.date].cummulativeMaterial = fixedDecimal(matCum);
				periodicalMap[group.date].cummulativeOther = fixedDecimal(otherCum);
			}
			// Roundoff Values
			$scope.labels = new Array();
			$scope.data = new Array();
			let labourArr = [], plantArr = [], matArr = [], otherArr = [];
			let labourCumArr = [], plantCumArr = [], matCumArr = [], otherCumArr = [];
			$scope.subReportData = new Array();
			for (const index in periodicalMap) {
				periodicalMap[index].labour = fixedDecimal(periodicalMap[index].labour);
				labourArr.push(fixedDecimal(periodicalMap[index].labour));
				labourCumArr.push(fixedDecimal(periodicalMap[index].cummulativeLabour));
				periodicalMap[index].plant = fixedDecimal(periodicalMap[index].plant);
				plantArr.push(fixedDecimal(periodicalMap[index].plant));
				plantCumArr.push(fixedDecimal(periodicalMap[index].cummulativePlant));
				periodicalMap[index].material = fixedDecimal(periodicalMap[index].material);
				matArr.push(fixedDecimal(periodicalMap[index].material));
				matCumArr.push(fixedDecimal(periodicalMap[index].cummulativeMaterial));
				periodicalMap[index].other = fixedDecimal(periodicalMap[index].other);
				otherArr.push(fixedDecimal(periodicalMap[index].other));
				otherCumArr.push(fixedDecimal(periodicalMap[index].cummulativeOther));
				$scope.labels.push(periodicalMap[index].mapValue);
				$scope.subReportData.push(periodicalMap[index]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.datasetOverride = new Array();
			$scope.data.push(labourArr);
			$scope.datasetOverride.push({
				label: 'Labour Planned',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(labourCumArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'Cummulative Labour',
				fill: false,
				borderColor: colors[4],
				backgroundColor: colors[4],
				pointBorderColor: colors[4],
				pointBackgroundColor: colors[4],
				pointHoverBackgroundColor: colors[4],
				pointHoverBorderColor: colors[4],
				yAxisID: 'y-axis-2'
			});
			$scope.data.push(plantArr);
			$scope.datasetOverride.push({
				label: 'Plant Planned',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(plantCumArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'Cummulative Plant',
				fill: false,
				borderColor: colors[4],
				backgroundColor: colors[4],
				pointBorderColor: colors[4],
				pointBackgroundColor: colors[4],
				pointHoverBackgroundColor: colors[4],
				pointHoverBorderColor: colors[4],
				yAxisID: 'y-axis-2'
			});
			$scope.data.push(matArr);
			$scope.datasetOverride.push({
				label: 'Material Planned',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(matCumArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'Cummulative Material',
				fill: false,
				borderColor: colors[4],
				backgroundColor: colors[4],
				pointBorderColor: colors[4],
				pointBackgroundColor: colors[4],
				pointHoverBackgroundColor: colors[4],
				pointHoverBorderColor: colors[4],
				yAxisID: 'y-axis-2'
			});
			$scope.data.push(otherArr);
			$scope.datasetOverride.push({
				label: 'Other Planned',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(otherCumArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'Cummulative Other',
				fill: false,
				borderColor: colors[4],
				backgroundColor: colors[4],
				pointBorderColor: colors[4],
				pointBackgroundColor: colors[4],
				pointHoverBackgroundColor: colors[4],
				pointHoverBorderColor: colors[4],
				yAxisID: 'y-axis-2'
			});
			initGraph();
		}
		$scope.getItemwisePlanActualDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"categories": ["direct", "in-direct"],
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			$scope.itemPlanActualDetails = [];
			$scope.budgetRatios = [];
			$scope.plannedValues = [];
			BudgetService.getDateProjWiseActualReport(req).then(function (data) {
				$scope.itemPlanActualDetails = data.costItemActualReportTOs;
				//$scope.budgetRatios = data.ratioTOs;
				if ($scope.gridOptions)
					$scope.gridOptions.data = $scope.itemPlanActualDetails;
				//if ($scope.subReport != 'None')
					//prepareSubReport();
				//processPlannedValue();
				if ($scope.itemPlanActualDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while fetching schedule and cost variance", 'Error');
			});
			initGraph();
		};
		function processPlannedValue() {
			if ($scope.itemPlanActualDetails.length > 0 && $scope.budgetRatios.length > 0 && $scope.plannedValues.length > 0) {
				for (const plannedValue of $scope.plannedValues) {
					for (let itemPlan of $scope.itemPlanActualDetails) {
						if (itemPlan.projId == plannedValue.projId) {
							for (let datePlan of plannedValue.datePlanned) {
								if (datePlan && datePlan.plannedValue) {
									const costId = datePlan.costId;
									const budgetRatio = $scope.budgetRatios.find(function (data) {
										return costId === data.costId;
									});
									if (budgetRatio) {
										if (budgetRatio.labour)
											itemPlan.labourPlanned += (budgetRatio.labour * datePlan.plannedValue);
										if (budgetRatio.plant)
											itemPlan.plantPlanned += (budgetRatio.plant * datePlan.plannedValue);
										if (budgetRatio.mat)
											itemPlan.matPlanned += (budgetRatio.mat * datePlan.plannedValue);
										if (budgetRatio.other)
											itemPlan.otherPlanned += (budgetRatio.other * datePlan.plannedValue);
									}
								}
							}
						}
					}
				}
				$scope.itemPlanActualDetails.map(v => {
					v.labourPlanned = fixedDecimal(v.labourPlanned);
					v.plantPlanned = fixedDecimal(v.plantPlanned);
					v.matPlanned = fixedDecimal(v.matPlanned);
					v.otherPlanned = fixedDecimal(v.otherPlanned);
					v.totalPlanned = fixedDecimal((v.labourPlanned + v.plantPlanned + v.matPlanned + v.otherPlanned));
				});
				if ($scope.gridOptions)
					$scope.gridOptions.data = $scope.itemPlanActualDetails;
			}
		};
		$scope.resetItemwisePlanActualDetails = function () {
			$scope.itemPlanActualDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.series = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.type = 'chartTable';
			$scope.subReportName = null;
			$scope.subReportCode = null;
			$scope.subReport = 'None';
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		function clearSubReportDetails() {
			$scope.itemPlanActualDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.series = [];
			$scope.type = 'chartTable';
			$scope.subReport = 'None';
			$scope.periodicSubReport = $scope.periodicSubReports[0];
		};
		function initGraph() {
			$scope.menuOptions = [];
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

		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'date', displayName: "Date", headerTooltip: 'Date' , groupingShowAggregationMenu: false},
					{ field: 'epsName', displayName: "EPS", headerTooltip: 'EPS', visible: false, groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: 'Project' , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: 'Currency', visible: false, groupingShowAggregationMenu: false },
					{ field: 'labourPlanned', displayName: "Manpower Planned", headerTooltip: 'Manpower Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'labourActual', displayName: "Manpower Actual", headerTooltip: 'Manpower Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'plantPlanned', displayName: "Plant Planned", headerTooltip: 'Plant Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'plantActual', displayName: "Plant Actual", headerTooltip: 'Plant Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'matPlanned', displayName: "Material Planned", headerTooltip: 'Material Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'matActual', displayName: "Material Actual", headerTooltip: 'Material Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'otherPlanned', displayName: "Other Planned", headerTooltip: 'Other Planned Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'otherActual', displayName: "Other Actual", headerTooltip: 'Other Actual Cost', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalPlanned', displayName: "Total Planned", headerTooltip: 'Total Planned Cost', visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalActual', displayName: "Total Actual", headerTooltip: 'Total Actual Cost', visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Cost&Performance_ItemWise_PlanVsActualCost");
			}
		});

		function fixedDecimal(value) {
			return parseFloat(value.toFixed(2));
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
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costitemwiseplantactualhelp.html',
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
