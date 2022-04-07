'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformanceperiodical", {
		url: '/costperformanceperiodical',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/periodicalwiseactualcost.html',
				controller: 'PeriodicalWiseActualCostController'
			}
		}
	})
}]).controller("PeriodicalWiseActualCostController", ["$scope", "uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog", "$filter", "BudgetService", "GenericAlertService", "EpsProjectMultiSelectFactory",
	"CostCodeMultiSelectFactory", "PlannedValuesService", "dateGroupingService", "stylesService", "ngGridService", "chartService", function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog, $filter, BudgetService, GenericAlertService,
		EpsProjectMultiSelectFactory, CostCodeMultiSelectFactory, PlannedValuesService, dateGroupingService, stylesService, ngGridService, chartService) {
		const series = ['Planned Cost', 'Actual Cost', 'Earned Value'];
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Amount';
		$scope.data = [];
		$scope.labels = [];
		$scope.type = 'chartTable';
		$scope.reportName = "Periodical - Plan Vs Actual Vs Earned"
		$scope.periodicWiseDetails = [];
		$scope.plannedValues = [];
		$scope.subReportCode = "";
		$scope.subReportName = "";
		$scope.selectedProjIds = [];
		$scope.subReport = 'None';
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
			code: "overall"
		}];
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
				clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCostCodes = function () {
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select project to get CostCodes", 'Warning');
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
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
		function noSubReportData() {
			let columnDefs = [

				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costItemCode', displayName: "Cost Code Item", headerTooltip: "Cost Code Item", groupingShowAggregationMenu: false   },
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false   },

				{ field: 'totalPrevPlanned', displayName: "Prev-BCWS", headerTooltip: "Previous Period Planned value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'totalPrevCost', displayName: "Prev-ACWP", headerTooltip: "Previous Period Actual Cost", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'totalPrevEarned', displayName: "Prev-BCWP", headerTooltip: "Previous Period Earned Value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'prevScheduleVariance', displayName: "Prev-SV", headerTooltip: "Previous Period Schedule variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'prevCostVariance', displayName: "Prev-CV", headerTooltip: "Previous Period Cost Variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'prevShedVar', displayName: "Prev-SV%", headerTooltip: "Previous Period Schedule variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'prevCostVar', displayName: "Prev-CV%", headerTooltip: "Previous Period Cost Variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },

				{ field: 'totalReportPlanned', displayName: "Rep-BCWS", headerTooltip: "Reporting Period Planned value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'totalReportCost', displayName: "Rep-ACWP", headerTooltip: "Reporting Period Actual Cost", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'totalReportEarned', displayName: "Rep-BCWP", headerTooltip: "Reporting Period Earned Value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'currentScheduleVariance', displayName: "Rep-SV", headerTooltip: "Reporting Period Schedule variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'currentCostVariance', displayName: "Rep-CV", headerTooltip: "Reporting Period Cost Variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'repShedVar', displayName: "Rep-SV%", headerTooltip: "Reporting Period Schedule variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'repCostVar', displayName: "Rep-CV%", headerTooltip: "Reporting Period Cost Variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },

				{ field: 'utdBcws', displayName: "U.T.D-BCWS", headerTooltip: "Up To Date Planned value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'utdAcwp', displayName: "U.T.D-ACWP", headerTooltip: "Up To Date Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'utdBcwp', displayName: "U.T.D-BCWP", headerTooltip: "Up To Date Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'utdCv', displayName: "U.T.D-SV", headerTooltip: "Up To Date Schedule variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'utdSv', displayName: "U.T.D-CV", headerTooltip: "Up To Date Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'utdSvPer', displayName: "U.T.D-SV%", headerTooltip: "Up To Date Schedule variance%", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'utdCvPer', displayName: "U.T.D-CV%", headerTooltip: "Up To Date Cost Variance%", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Cost&Performance_PeriodicalPlanVsActualVsEarned");
		}
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				if ($scope.periodicWiseDetails.length == 0) {
					$scope.getPeriodicalWiseReport();
				} else {
					prepareSubReport();
				}
			} else {
				$scope.periodicWiseDetails = [];
				$scope.plannedValues = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getPeriodicalWiseReport();
			}
		};
		function prepareSubReport() {
			$scope.subReportData = [];
			if ($scope.subReport.code == "costcode") {
				generateSubReportData("costItemId", "costItemCode");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			} else if ($scope.subReport.code == "overall") {
				generateOverAllReport();
			}
		};
		function generateOverAllReport() {
			let subReportMap = [];
			if ($scope.subReport.code == "overall") {
				let overAllData = [
					{ field: 'mapValue', displayName: "", groupingShowAggregationMenu: false },
					{ field: 'plannedCost', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualCost', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_Cost&Performance_PeriodicalPlanVsActualVsEarned_Overall");
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			const prevKey = "Previous Period", reportKey = "Reporting Period", uptoDateKey = "Up to Date";
			for (let periodicDtl of $scope.periodicWiseDetails) {
				if (!subReportMap[prevKey]) {
					subReportMap[prevKey] = {
						"mapKey": prevKey,
						"mapValue": prevKey,
						"plannedCost": 0,
						"actualCost": 0,
						"earnedValue": 0
					};
				}
				if (!subReportMap[reportKey]) {
					subReportMap[reportKey] = {
						"mapKey": reportKey,
						"mapValue": reportKey,
						"plannedCost": 0,
						"actualCost": 0,
						"earnedValue": 0
					};
				}
				if (!subReportMap[uptoDateKey]) {
					subReportMap[uptoDateKey] = {
						"mapKey": uptoDateKey,
						"mapValue": uptoDateKey,
						"plannedCost": 0,
						"actualCost": 0,
						"earnedValue": 0
					};
				}
				subReportMap[prevKey].plannedCost = fixedDecimal(subReportMap[prevKey].plannedCost + periodicDtl["totalPrevPlanned"]);
				subReportMap[prevKey].actualCost = fixedDecimal(subReportMap[prevKey].actualCost + periodicDtl["totalPrevCost"]);
				subReportMap[prevKey].earnedValue = fixedDecimal(subReportMap[prevKey].earnedValue + periodicDtl["totalPrevEarned"]);

				subReportMap[reportKey].plannedCost = fixedDecimal(subReportMap[reportKey].plannedCost + periodicDtl["totalReportPlanned"]);
				subReportMap[reportKey].actualCost = fixedDecimal(subReportMap[reportKey].actualCost + periodicDtl["totalReportCost"]);
				subReportMap[reportKey].earnedValue = fixedDecimal(subReportMap[reportKey].earnedValue + periodicDtl["totalReportEarned"]);

				subReportMap[uptoDateKey].plannedCost = fixedDecimal(subReportMap[uptoDateKey].plannedCost + periodicDtl["totalPrevPlanned"] + periodicDtl["totalReportPlanned"]);
				subReportMap[uptoDateKey].actualCost = fixedDecimal(subReportMap[uptoDateKey].actualCost + periodicDtl["totalPrevCost"] + periodicDtl["totalReportCost"]);
				subReportMap[uptoDateKey].earnedValue = fixedDecimal(subReportMap[uptoDateKey].earnedValue + periodicDtl["totalPrevEarned"] + periodicDtl["totalReportEarned"]);
			}
			setGraphData(subReportMap);
		}
		function generateSubReportData(key, value) {
			let subReportMap = [];
			if ($scope.subReport.code == "costcode") {
				let costCodeData = [
					{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID", groupingShowAggregationMenu: false },
					{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
					{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
					{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Name", groupingShowAggregationMenu: false },
					{ field: 'plannedCost', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualCost', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_PeriodicalPlanVsActualVsEarned_CostCode");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let projData = [
					{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", groupingShowAggregationMenu: false },
					{ field: 'plannedCost', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualCost', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, projData, data, "Reports_Cost&Performance_PeriodicalPlanVsActualVsEarned_Proj");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let epsData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency", groupingShowAggregationMenu: false },
					{ field: 'plannedCost', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'actualCost', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'earnedValue', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_Cost&Performance_PeriodicalPlanVsActualVsEarned_EPS");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			for (let budget of $scope.periodicWiseDetails) {
				let mapKey = budget[key];
				let mapValue = budget[value];
				if (!subReportMap[mapKey]) {
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"currencyCode": budget["currencyCode"],
						"projName": budget["projName"],
						"epsName": budget["epsName"],
						"plannedCost": 0,
						"actualCost": 0,
						"earnedValue": 0
					};
					if (key == "costItemId") {
						subReportMap[mapKey].parentCostCode = budget["costSubGroupCode"];
						subReportMap[mapKey].parentCostName = budget["costSubGroupName"];
						subReportMap[mapKey].costName = budget["costItemName"];
					}
				}
				subReportMap[mapKey].plannedCost = fixedDecimal(subReportMap[mapKey].plannedCost + budget["totalReportPlanned"]);
				subReportMap[mapKey].actualCost = fixedDecimal(subReportMap[mapKey].actualCost + budget["totalReportCost"]);
				subReportMap[mapKey].earnedValue = fixedDecimal(subReportMap[mapKey].earnedValue + budget["totalReportEarned"]);
			}
			setGraphData(subReportMap);
		};
		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let plannedArr = [], actualArr = [], earnedArr = [];
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				plannedArr.push(subReportMap[index].plannedCost);
				actualArr.push(subReportMap[index].actualCost);
				earnedArr.push(subReportMap[index].earnedValue);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.data.push(plannedArr);
			$scope.data.push(actualArr);
			$scope.data.push(earnedArr);
			initGraph();
		};
		$scope.getPeriodicalWiseReport = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select project to fetch report", 'Warning');
				return;
			}
			if ($scope.selectedCostCodeIds == undefined || $scope.selectedCostCodeIds == null) {
				GenericAlertService.alertMessage("Please select Cost Code to fetch report", 'Warning');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"costcodeIds": $scope.selectedCostCodeIds,
				"categories": ["direct", "in-direct"],
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			BudgetService.getPeriodicalWiseReport(req).then(function (data) {
				$scope.periodicWiseDetails = data;
				$scope.gridOptions.data = angular.copy(data);
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.subReport != 'None')
					prepareSubReport();
				processPlannedValue();
				if ($scope.periodicWiseDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Warning');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while fetching schedule and cost variance", 'Error');
			});
			PlannedValuesService.getCostPlannedValuesBetween($scope.selectedProjIds, undefined, $scope.toDate, true).then(function (data) {
				$scope.plannedValues = data;
				processPlannedValue();
				if ($scope.plannedValues.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Warning');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while fetching planned values", 'Error');
			});
			initGraph();
		};
		function processPlannedValue() {
			if ($scope.plannedValues.length > 0 && $scope.periodicWiseDetails.length > 0) {
				for (let periodicWise of $scope.periodicWiseDetails) {
					const valuesByDate = function() {return periodicWise;}
					if (valuesByDate) {
						let prevPlanned = 0, reportPlanned = 0, totalPrevCV = 0, totalPrevSV = 0, totalReportCV = 0, totalReportSV = 0;
						for (let prevValue of periodicWise.prevValues) {
							prevValue.costVariance = prevValue.earnedValue - prevValue.costAmount;
							totalPrevCV += Math.abs(prevValue.costVariance);
							let value = function() {return valuesByDate;}
							if (value && value.plannedValue) {
								prevValue.plannedValue = value.plannedValue;
								prevPlanned += value.plannedValue;
								prevValue.scheduleVariance = prevValue.earnedValue - prevValue.plannedValue;
								totalPrevSV += prevValue.scheduleVariance;
							}
						}
						for (let reportValue of periodicWise.reportValues) {
							reportValue.costVariance = reportValue.earnedValue - reportValue.costAmount;						
							totalReportCV += Math.abs(reportValue.costVariance);
							let value = function() {return valuesByDate;}
							if (value && value.plannedValue) {
								reportValue.plannedValue = value.plannedValue;
								reportPlanned += value.plannedValue;
								reportValue.scheduleVariance = reportValue.earnedValue - reportValue.plannedValue;
								totalReportSV += reportValue.scheduleVariance;
							}
						}
						periodicWise.totalPrevPlanned = fixedDecimal(prevPlanned);
						periodicWise.totalReportPlanned = fixedDecimal(reportPlanned);
						periodicWise.prevScheduleVariance = fixedDecimal(totalPrevSV);
						periodicWise.prevCostVariance = fixedDecimal(totalPrevCV);
						periodicWise.currentScheduleVariance = fixedDecimal(totalReportSV);
						periodicWise.currentCostVariance = fixedDecimal(totalReportCV);
						periodicWise.prevShedVar = fixedDecimal(ifNaN((periodicWise.prevScheduleVariance / periodicWise.totalPrevPlanned) * 100));
						periodicWise.prevCostVar = fixedDecimal(ifNaN((periodicWise.prevCostVariance / periodicWise.totalPrevEarned) * 100));
						periodicWise.repShedVar = fixedDecimal(ifNaN((periodicWise.currentScheduleVariance / periodicWise.totalReportPlanned) * 100));
						periodicWise.repCostVar = fixedDecimal(ifNaN((periodicWise.currentCostVariance / periodicWise.totalReportEarned) * 100));
						periodicWise.utdBcws = fixedDecimal(periodicWise.totalPrevPlanned + periodicWise.totalReportPlanned);
						periodicWise.utdAcwp = fixedDecimal(periodicWise.totalPrevCost + periodicWise.totalReportCost);
						periodicWise.utdBcwp = fixedDecimal(periodicWise.totalPrevEarned + periodicWise.totalReportEarned);
						periodicWise.utdSv = fixedDecimal(periodicWise.prevScheduleVariance + periodicWise.currentScheduleVariance);
						periodicWise.utdCv = fixedDecimal(periodicWise.prevCostVariance + periodicWise.currentCostVariance);
						periodicWise.utdSvPer = fixedDecimal(ifNaN(((periodicWise.prevScheduleVariance + periodicWise.currentScheduleVariance) / (periodicWise.totalPrevPlanned + periodicWise.totalReportPlanned)) * 100));
						periodicWise.utdCvPer = fixedDecimal(ifNaN(((periodicWise.prevCostVariance + periodicWise.currentCostVariance) / (periodicWise.totalPrevEarned + periodicWise.totalReportEarned)) * 100));
					}
				}
				$scope.gridOptions.data = angular.copy($scope.periodicWiseDetails);
				if ($scope.subReport != 'None')
					prepareSubReport();
			}
		};
		$scope.resetPeriodicWiseDetails = function () {
			$scope.periodicWiseDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.selectedCostCodeIds = [];
			$scope.companyNameDisplay = null;
			$scope.costCodeNameDisplay = null;
			$scope.type = 'chartTable';
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.subReportName = null;
			$scope.subReport = 'None';
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		function clearSubReportDetails() {
			$scope.periodicWiseDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.type = 'chartTable';
			$scope.subReport = 'None';
		};
		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [

					{ field: 'epsName', width:"150",displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'currencyCode',width:"100", displayName: "Currency", headerTooltip: "Currency", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costSubGroupCode',width:"100", displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costSubGroupName', width:"155",displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costItemCode', displayName: "Cost Code Item", headerTooltip: "Cost Code Item", groupingShowAggregationMenu: false },
					{ field: 'costItemName', width:"250",displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false   },

					{ field: 'totalPrevPlanned', width:"70", displayName: "Prev-BCWS", headerTooltip: "Previous Period Planned value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalPrevCost', width:"70", displayName: "Prev-ACWP", headerTooltip: "Previous Period Actual Cost", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalPrevEarned', width:"70", displayName: "Prev-BCWP", headerTooltip: "Previous Period Earned Value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'prevScheduleVariance', width:"70", displayName: "Prev-SV", headerTooltip: "Previous Period Schedule variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'prevCostVariance', width:"70", displayName: "Prev-CV", headerTooltip: "Previous Period Cost Variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'prevShedVar',  width:"70",displayName: "Prev-SV%", headerTooltip: "Previous Period Schedule variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'prevCostVar', width:"70", displayName: "Prev-CV%", headerTooltip: "Previous Period Cost Variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },

					{ field: 'totalReportPlanned', width:"70", displayName: "Rep-BCWS", headerTooltip: "Reporting Period Planned value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalReportCost',  width:"160", displayName: "Rep-ACWP", headerTooltip: "Reporting Period Actual Cost", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalReportEarned',  width:"160", displayName: "Rep-BCWP", headerTooltip: "Reporting Period Earned Value", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'currentScheduleVariance',  width:"70", displayName: "Rep-SV", headerTooltip: "Reporting Period Schedule variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'currentCostVariance',  width:"160", displayName: "Rep-CV", headerTooltip: "Reporting Period Cost Variance", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'repShedVar',  width:"70", displayName: "Rep-SV%", headerTooltip: "Reporting Period Schedule variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'repCostVar', width:"100",  displayName: "Rep-CV%", headerTooltip: "Reporting Period Cost Variance%", visible: false, cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },

					{ field: 'utdBcws', displayName: "U.T.D-BCWS", headerTooltip: "Up To Date Planned value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'utdAcwp',  displayName: "U.T.D-ACWP", headerTooltip: "Up To Date Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'utdBcwp', displayName: "U.T.D-BCWP", headerTooltip: "Up To Date Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'utdSv', displayName: "U.T.D-SV", headerTooltip: "Up To Date Schedule variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'utdCv',  width:"160", displayName: "U.T.D-CV", headerTooltip: "Up To Date Cost Variance", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'utdSvPer',  width:"100", displayName: "U.T.D-SV%", headerTooltip: "Up To Date Schedule variance%", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'utdCvPer', displayName: "U.T.D-CV%", headerTooltip: "Up To Date Cost Variance%", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
				];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Cost&Performance_PeriodicalPlanVsActualVsEarned");
				$scope.gridOptions.exporterPdfOrientation = 'landscape';
				$scope.gridOptions.exporterPdfPageSize = 'A3';
				$scope.gridOptions.exporterPdfMaxGridWidth = 860;
			}
		});
		function fixedDecimal(value) {
			return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
		}
		function ifNaN(value) {
			return isNaN(value) ? 0 : value;
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
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costperiodplantactualhelp.html',
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
