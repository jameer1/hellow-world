'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformancecostscheduleperformanceindex", {
		url: '/costperformancecostscheduleperformanceindex',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/costsheduleperformanceindex.html',
				controller: 'CostshedulePerformanceIndexController'
			}
		}
	})
}]).controller("CostshedulePerformanceIndexController", ["CostCodeMultiSelectFactory", "$scope", "uiGridGroupingConstants", "$q", "ngDialog", "$filter", "GenericAlertService", "PlannedValuesService",
	"EpsProjectMultiSelectFactory", "BudgetService", "ProjectBudgetService", "EstimateToCompleteService", "TreeService", "EstimateValuesService",
	"dateGroupingService", "stylesService", "ngGridService", "uiGridConstants", function (CostCodeMultiSelectFactory, $scope, uiGridGroupingConstants, $q, ngDialog,  $filter, GenericAlertService, PlannedValuesService, EpsProjectMultiSelectFactory,
		BudgetService, ProjectBudgetService, EstimateToCompleteService, TreeService, EstimateValuesService, dateGroupingService, stylesService, ngGridService, uiGridConstants) {
		const colors = ['#ff0000', '#ffff00', '#800080', '#ffa500', '#ff00ff', '#800000', '#0000ff'];
		const series = ['Planned Cost', 'Actual Cost', 'Earned Value', 'EAC', 'SPI', 'CPI', 'TCPI'];

		$scope.reportName = "Cost & Schedule Performance Index";
		$scope.type = 'chartTable';
		$scope.stylesSvc = stylesService;
		$scope.subReport = 'None';
		$scope.selectedProjIds = [];
		$scope.selectedCostCodeIds = [];
		$scope.costData = [];
		$scope.dateWiseDetails = [];
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
			name: 'CostCodeWise Performance Index Records',
			code: "costcode"
		}, {
			name: 'ProjectWise  Performance Index Records',
			code: "proj"
		}, {
			name: 'EPSWise  Performance Index Records',
			code: "eps"
		}, {
			name: 'Periodical  Performance Index Records',
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
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCostCodes = function () {
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
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
		}

		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group ID", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costSubGroupName', displayName: "Cost Code Sub Group", headerTooltip: "Cost Code Sub Group", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costItemCode', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false },
				{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'totalReportCost', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'totalReportEarned', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'estimateAtComp', displayName: "EAC", headerTooltip: "Estimate At Completion", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'spi', displayName: "SPI", headerTooltip: "Schedule Performance Index", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'cpi', displayName: "CPI", headerTooltip: "Cost Performance Index", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'tcpi', displayName: "TCPI", headerTooltip: "To Complete Performance Index", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
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
				if ($scope.dateWiseDetails.length == 0) {
					$scope.getCostSchedulePerformanceIndexDetails();
				} else {
					prepareSubReport();
				}
			} else {
				$scope.dateWiseDetails = [];
				$scope.costData = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getCostSchedulePerformanceIndexDetails();
			}
		};
		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartTable';
			let groupByFunction = null;
			let dateFormat, momentDateFormat;
			let reportSettingProp;

			if ($scope.subReport.code == "periodical") {
				let periodicalData = [
					{ field: 'mapValue', displayName: "Period" , groupingShowAggregationMenu: false},
					{ field: 'plannedValue', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportCost', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportEarned', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Earned Value", headerTooltip: "Earned Value", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'estimateAtComp', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "EAC", headerTooltip: "Estimate At Completion", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'spi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "SPI", headerTooltip: "Schedule Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "CPI", headerTooltip: "Cost Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'tcpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "TCPI", headerTooltip: "To Complete Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, periodicalData, data, "Reports_Cost&Performance_Cost&SchedulePerformanceIndex_Periodical");
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
			const periodicalMap = new Array();

			for (const object of $scope.dateWiseDetails) {
				const groupedList = groupByFunction(object.reportValues, reportSettingProp);
				for (const group of groupedList) {
					const mapKey = group.date;
					if (!periodicalMap[mapKey]) {
						periodicalMap[mapKey] = {
							'mapKey': mapKey,
							'mapValue': $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat),
							"plannedValue": 0,
							"totalReportCost": 0,
							"totalReportEarned": 0,
							"estimateAtComp": 0,
							"spi": 0,
							"cpi": 0,
							"tcpi": 0
						};
					}
					for (const val of group.values) {
						periodicalMap[group.date].plannedValue = fixedDecimal(periodicalMap[group.date].plannedValue + val.plannedValue);
						periodicalMap[group.date].totalReportCost = fixedDecimal(periodicalMap[group.date].totalReportCost + val.costAmount);
						periodicalMap[group.date].totalReportEarned = fixedDecimal(periodicalMap[group.date].totalReportEarned + val.earnedValue);
						const eac = val.estimateValue ? val.costAmount + val.estimateValue : val.costAmount;
						periodicalMap[group.date].estimateAtComp = fixedDecimal(periodicalMap[group.date].estimateAtComp + eac);
						periodicalMap[group.date].spi = fixedDecimal(periodicalMap[group.date].spi + ifNaN(val.earnedValue / val.plannedValue));
						periodicalMap[group.date].cpi = fixedDecimal(periodicalMap[group.date].cpi + ifNaN(val.earnedValue / val.costAmount));
						let tcpi = 0;
						if ((eac - val.costAmount) != 0) {
							tcpi = ifNaN((eac - val.earnedValue) / (eac - val.costAmount));
						}
						periodicalMap[group.date].tcpi = fixedDecimal(periodicalMap[group.date].tcpi + tcpi);
					}
				}
			}
			const newMapArr = new Array();
			const sortedDays = _.sortBy(Object.keys(periodicalMap), function (i) { return moment(i, momentDateFormat)._d });
			for (const key of sortedDays) {
				newMapArr[key] = periodicalMap[key];
			}
			setGraphData(newMapArr);
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
					$scope.periodicSubReport = $scope.periodicSubReports[0];
					$scope.changePeriodicSubReport();
				}
			}
		};
		function generateSubReportData(key, value) {
			let subReportMap = [];

			if ($scope.subReport.code == "costcode") {
				let costCodeData = [
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID" , groupingShowAggregationMenu: false},
					{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" , groupingShowAggregationMenu: false},
					{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" , groupingShowAggregationMenu: false},
					{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Name" , groupingShowAggregationMenu: false},
					{ field: 'plannedValue', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportCost', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportEarned', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Earned Value", headerTooltip: "Earned Value", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'estimateAtComp', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "EAC", headerTooltip: "Estimate At Completion", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'spi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "SPI", headerTooltip: "Schedule Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "CPI", headerTooltip: "Cost Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'tcpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "TCPI", headerTooltip: "To Complete Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_Cost&SchedulePerformanceIndex_CostCode");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let costCodeData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'plannedValue', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportCost', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportEarned', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Earned Value", headerTooltip: "Earned Value", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'estimateAtComp', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "EAC", headerTooltip: "Estimate At Completion", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'spi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "SPI", headerTooltip: "Schedule Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "CPI", headerTooltip: "Cost Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'tcpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "TCPI", headerTooltip: "To Complete Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_Cost&SchedulePerformanceIndex_EPS");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let costCodeData = [
					{ field: 'mapValue', displayName: "Project", headerTooltip: "Project" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'plannedValue', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportCost', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportEarned', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Earned Value", headerTooltip: "Earned Value", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'estimateAtComp', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "EAC", headerTooltip: "Estimate At Completion", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'spi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "SPI", headerTooltip: "Schedule Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "CPI", headerTooltip: "Cost Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'tcpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "TCPI", headerTooltip: "To Complete Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_Cost&SchedulePerformanceIndex_Proj");
				$scope.gridOptions.showColumnFooter = true;
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
						"totalReportCost": 0,
						"totalReportEarned": 0,
						"periodEstimate": 0,
						"estimateAtComp": 0,
						"spi": 0,
						"cpi": 0,
						"tcpi": 0
					};
					if (key == "costItemId") {
						subReportMap[mapKey].parentCostCode = budget["costSubGroupCode"];
						subReportMap[mapKey].parentCostName = budget["costSubGroupName"];
						subReportMap[mapKey].costName = budget["costItemName"];
					}
				}
				subReportMap[mapKey].plannedValue += budget["plannedValue"];
				subReportMap[mapKey].totalReportCost += budget["totalReportCost"];
				subReportMap[mapKey].totalReportEarned += budget["totalReportEarned"];
				subReportMap[mapKey].periodEstimate += budget["periodEstimate"];
				subReportMap[mapKey].spi += budget["spi"];
				subReportMap[mapKey].cpi += budget["cpi"];
				subReportMap[mapKey].tcpi += budget["tcpi"];
				subReportMap[mapKey].estimateAtComp = fixedDecimal(subReportMap[mapKey].totalReportCost + subReportMap[mapKey].periodEstimate);
			}
			setGraphData(subReportMap);
		};
		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let planArr = [], costArr = [], earnedArr = [], estimateArr = [], spiArr = [], cpiArr = [], tcpiArr = [];
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				planArr.push(fixedDecimal(subReportMap[index].plannedValue));
				costArr.push(fixedDecimal(subReportMap[index].totalReportCost));
				earnedArr.push(fixedDecimal(subReportMap[index].totalReportEarned));
				estimateArr.push(fixedDecimal(subReportMap[index].estimateAtComp));
				spiArr.push(fixedDecimal(subReportMap[index].spi));
				cpiArr.push(fixedDecimal(subReportMap[index].cpi));
				tcpiArr.push(fixedDecimal(subReportMap[index].tcpi));
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.datasetOverride = new Array();
			$scope.data.push(planArr);
			$scope.datasetOverride.push({
				label: 'Planned Cost',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(costArr);
			$scope.datasetOverride.push({
				label: 'Actual Cost',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(earnedArr);
			$scope.datasetOverride.push({
				label: 'Earned Value',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(estimateArr);
			$scope.datasetOverride.push({
				label: 'EAC',
				type: 'bar',
				id: 'y-axis-1',
			});
			$scope.data.push(spiArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'SPI',
				fill: false,
				borderColor: colors[4],
				backgroundColor: colors[4],
				pointBorderColor: colors[4],
				pointBackgroundColor: colors[4],
				pointHoverBackgroundColor: colors[4],
				pointHoverBorderColor: colors[4],
				yAxisID: 'y-axis-2'
			});
			$scope.data.push(cpiArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'CPI',
				fill: false,
				borderColor: colors[5],
				backgroundColor: colors[5],
				pointBorderColor: colors[5],
				pointBackgroundColor: colors[5],
				pointHoverBackgroundColor: colors[5],
				pointHoverBorderColor: colors[5],
				yAxisID: 'y-axis-2'
			});
			$scope.data.push(tcpiArr);
			$scope.datasetOverride.push({
				type: 'line',
				label: 'TCPI',
				fill: false,
				borderColor: colors[6],
				backgroundColor: colors[6],
				pointBorderColor: colors[6],
				pointBackgroundColor: colors[6],
				pointHoverBackgroundColor: colors[6],
				pointHoverBorderColor: colors[6],
				yAxisID: 'y-axis-2'
			});
			initGraph();
		};
		function initGraph() {
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
							labelString: 'Cost'
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
							labelString: 'Performance Index'
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
		$scope.getCostSchedulePerformanceIndexDetails = function () {
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
			$scope.costData = [];
			// Get Report Data
			BudgetService.getCostCodeWiseReport(req).then(function (data) {
				$scope.dateWiseDetails = data;
				$scope.gridOptions.data = angular.copy(data);
				if ($scope.subReport != 'None')
					prepareSubReport();
				processPlannedResponse();
				processCostResponse();
				if ($scope.dateWiseDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while fetching schedule and cost variance", 'Error');
			});
			// Get budgets
			ProjectBudgetService.getMultiProjCostStatements({ "status": "1", "projIds": $scope.selectedProjIds }).then(function (data) {
				$scope.costData = populateCostData(data.projCostStmtDtlTOs, 0, []).filter(function (data) {
					return data.item;
				});;
				EstimateToCompleteService.costStatement($scope.costData);
				processCostResponse();
			});
			initGraph();
		};
		function populateCostData(data, level, costTOs, isChild, parent) {
			return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',
				isChild, parent)
		};
		function processPlannedResponse() {
			if ($scope.dateWiseDetails.length > 0) {
				for (let dateWise of $scope.dateWiseDetails) {
					dateWise.totalReportCost = fixedDecimal(dateWise.totalReportCost);
					dateWise.plannedValue = fixedDecimal(dateWise.reportValues[0].plannedValue);
					dateWise.spi = fixedDecimal(ifNaN(dateWise.totalReportEarned / dateWise.plannedValue));
					dateWise.cpi = fixedDecimal(ifNaN(dateWise.totalReportEarned / dateWise.totalReportCost));
				}
				$scope.gridOptions.data = angular.copy($scope.dateWiseDetails);
				if ($scope.subReport != 'None')
					prepareSubReport();
			}
		};
		function processCostResponse() {
			if ($scope.dateWiseDetails.length > 0 && $scope.costData.length > 0) {
				for (let dateWise of $scope.dateWiseDetails) {
					dateWise.estimateToComplete = 0;
					dateWise.periodEstimate = 0;
					const costByDate = $scope.costData.find(function (data) {
						return dateWise.projId === data.projId && dateWise.costItemId === data.costId;
					});
					
					if (costByDate && costByDate.estimateCompleteBudget && costByDate.revisedCostBudget && costByDate.estimateType && costByDate.originalCostBudget && costByDate.actualCostBudget){
							dateWise.estimateToComplete = (costByDate.estimateCompleteBudget.labourCost + costByDate.estimateCompleteBudget.materialCost
								+ costByDate.estimateCompleteBudget.plantCost + costByDate.estimateCompleteBudget.otherCost);
							dateWise.originalbudget = (costByDate.originalCostBudget.total);
							dateWise.estimatetype = (costByDate.estimateType);
							dateWise.revisedbudget = (costByDate.revisedCostBudget.total);
							dateWise.budget = costByDate.actualCostBudget.labourCost+costByDate.actualCostBudget.materialCost+costByDate.actualCostBudget.otherCost+costByDate.actualCostBudget.plantCost;
						}
				
				}
			//	EstimateValuesService.getCostEstimateValuesBetween($scope.selectedProjIds, $scope.fromDate, $scope.toDate, $scope.dateWiseDetails).then(function () {
					for (let dateWise of $scope.dateWiseDetails) {
						
						if(dateWise.estimatetype == "(BAC-EV)/(CPI*SPI)") {
								var bac_ev=((dateWise.revisedbudget ? dateWise.revisedbudget : dateWise.originalbudget) - dateWise.totalReportEarned);
								var cpi=(dateWise.totalReportEarned==0 || dateWise.totalReportEarned==null || dateWise.totalReportCost==0) ? 0 : (dateWise.totalReportEarned / dateWise.totalReportCost);
								var spi=(dateWise.totalReportEarned==0 || dateWise.totalReportEarned==null || dateWise.plannedValue==0) ? 0 : (dateWise.totalReportEarned / dateWise.plannedValue);
								var etcBAC_EV_CPI_SPI=(dateWise.totalReportEarned==0 || dateWise.totalReportEarned==null || dateWise.totalReportCost==0 || dateWise.plannedValue==0)  ? bac_ev :
				            		((dateWise.revisedbudget ? dateWise.revisedbudget : dateWise.originalbudget) - dateWise.totalReportEarned)/(cpi*spi);
								console.log(etcBAC_EV_CPI_SPI);
								dateWise.estimateAtCmp = etcBAC_EV_CPI_SPI;
							
							}else if(dateWise.estimatetype == "Remaining Units"){
								dateWise.estimateAtCmp = (dateWise.revisedbudget ? dateWise.revisedbudget : dateWise.originalbudget)-dateWise.totalReportCost;
							}else if(dateWise.estimatetype == "BAC-EV"){
								dateWise.estimateAtCmp = (dateWise.revisedbudget ? dateWise.revisedbudget : dateWise.originalbudget)-dateWise.totalReportEarned;
							}else if(dateWise.estimatetype == "(BAC-EV)/CPI"){
								var bac_ev=((dateWise.revisedbudget ? dateWise.originalbudget : dateWise.originalbudget) - dateWise.totalReportEarned);
								var cpi=(dateWise.totalReportEarned==0 || dateWise.totalReportEarned==null || dateWise.totalReportCost==0) ? 0 : (dateWise.totalReportEarned / dateWise.totalReportCost);
								var etcBAC_EV_CPI=(dateWise.totalReportEarned==0 || dateWise.totalReportEarned==null || dateWise.totalReportCost==0)  ? bac_ev :
				            		((dateWise.revisedbudget ? dateWise.revisedbudget : dateWise.originalbudget) - dateWise.totalReportEarned)/cpi;
								dateWise.estimateAtCmp = etcBAC_EV_CPI;
							}else if(dateWise.estimatetype == "New Estimate"){
								dateWise.estimateAtCmp = dateWise.budget+dateWise.estimateToComplete;
							}
						
						dateWise.estimateAtComp = fixedDecimal(dateWise.totalReportCost + dateWise.periodEstimate);
						
			
							dateWise.tcpi = fixedDecimal(((dateWise.estimateAtCmp - dateWise.totalReportEarned)) / ((dateWise.estimateAtCmp - dateWise.totalReportCost)));
						
					//	dateWise.tcpi = fixedDecimal(tcpi);
					}
					$scope.gridOptions.data = angular.copy($scope.dateWiseDetails);
					if ($scope.subReport != 'None')
						prepareSubReport();
		//		});
			}
		};
		$scope.resetCostSchedulePerformanceDetails = function () {
			$scope.costScheduleVarianceDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.selectedCostCodeIds = [];
			$scope.costCodeNameDisplay = null;
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.type = 'chartTable';
			$scope.subReportName = null;
			$scope.subReportCode = "";
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
					{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false },
					{ field: 'costSubGroupName', displayName: "Cost Code Sub Group", headerTooltip: "Cost Code Sub Group", visible: false, groupingShowAggregationMenu: false },
					{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" , groupingShowAggregationMenu: false},
					{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Cost", headerTooltip: "Planned Cost", cellFilter: 'number: 2',footerCellFilter : 'number : 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportCost', displayName: "Actual Cost", headerTooltip: "Actual Cost", cellFilter: 'number: 2',footerCellFilter : 'number : 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalReportEarned', displayName: "Earned Value", headerTooltip: "Earned Value", cellFilter: 'number: 2',footerCellFilter : 'number : 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'estimateAtCmp', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2',footerCellFilter : 'number : 2', displayName: "EAC", headerTooltip: "Estimate At Completion", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'spi', displayName: "SPI", headerTooltip: "Schedule Performance Index", cellFilter: 'number: 2', footerCellFilter : 'number : 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'cpi', displayName: "CPI", headerTooltip: "Cost Performance Index", cellFilter: 'number: 2', footerCellFilter : 'number : 2', cellClass: "justify-right", headerCellClass: "justify-right" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'tcpi', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', cellClass: "justify-right", displayName: "TCPI", headerTooltip: "To Complete Performance Index", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Cost&Performance_Cost&SchedulePerformanceIndex");
			}
		});
		function fixedDecimal(value) {
			return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
		}
		function ifNaN(value) {
			return isNaN(value) || !isFinite(value) ? 0 : value;
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
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costscheduleperformancehelp.html',
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
