'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformanceitemwisebudgetprovision", {
		url: '/costperformanceitemwisebudgetprovision',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/itemwisebugetprovision.html',
				controller: 'ItemWiseBudgetProvisionController'
			}
		}
	})
}]).controller("ItemWiseBudgetProvisionController", ["$scope","uiGridGroupingConstants", "$q", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "BudgetService", "stylesService", "ngGridService", "chartService", "uiGridConstants",
	function ($scope, uiGridGroupingConstants, $q, ngDialog,GenericAlertService, EpsProjectMultiSelectFactory, BudgetService, stylesService, ngGridService, chartService, uiGridConstants) {
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Amount';
		var totalHrs = {
			"labour": 0,
			"plant": 0,
			"material": 0, 
			"other": 0
		};
		$scope.data = [];
		$scope.labels = [];
		$scope.totalHrs = angular.copy(totalHrs);
		$scope.subReport = 'None';
		$scope.subReportData = [];
		$scope.stylesSvc = stylesService;
		$scope.type = 'chartTable';
		$scope.budgetDetails = [];
		$scope.selectedProjIds = [];
		var series = ['Labour', 'Plant', 'Material', 'Other'];
		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				clearReportData();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.subReports = [{
			name: 'CostCodeWise Itemwise Budget Cost',
			code: "costcode"
		}, {
			name: 'ProjectWise Itemwise Budget Cost',
			code: "proj"
		}, {
			name: 'EPSWise Itemwise Budget Cost',
			code: "eps"
		}];
		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id" , groupingShowAggregationMenu: false},
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false},
				{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false},
				{ field: 'costItemCode', displayName: "Cost Code Item", headerTooltip: "Cost Code Item" , groupingShowAggregationMenu: false},
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false},
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
				{ field: 'labourAmount', displayName: "Labour Budget", headerTooltip: "Labour Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'plantAmount', displayName: "Plant Budget", headerTooltip: "Plant Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'matAmount', displayName: "Mat Budget", headerTooltip: "Material Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'otherAmount', displayName: "Other Budget", headerTooltip: "Other Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ field: 'totalAmount', displayName: "Total", headerTooltip: "Total" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
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
				if ($scope.budgetDetails.length == 0) {
					$scope.getCostCodeBudgetReport();
				} else {
					prepareSubReport();
				}
			} else {
				$scope.budgetDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getCostCodeBudgetReport();
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
			}
		};
		function generateSubReportData(key, value) {
			let labourArr = [], plantArr = [], matArr = [], otherArr = [];
			let subReportMap = [];
			$scope.data = [];
			$scope.labels = [];
			if ($scope.subReport.code == "costcode") {
				let costCodeData = [
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID" , groupingShowAggregationMenu: false},
					{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" , groupingShowAggregationMenu: false},
					{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" , groupingShowAggregationMenu: false},
					{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Name" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'labourAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Labour Budg", headerTooltip: "Labour Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'plantAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Plant Budg", headerTooltip: "Plant Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'matAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Material Budg", headerTooltip: "Material Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'otherAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Other Budg", headerTooltip: "Other Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'totalAmount', displayName: "Total Budg", headerTooltip: "Total Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}}

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_ItemWiseBudgetProvision_CostCode");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let projData = [
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false},
					{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'labourAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Labour Budg", headerTooltip: "Labour Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'plantAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Plant Budg", headerTooltip: "Plant Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'matAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Material Budg", headerTooltip: "Material Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'otherAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Other Budg", headerTooltip: "Other Budget" , groupingShowAggregationMenu: false},
					{ field: 'totalAmount', displayName: "Total Budg", headerTooltip: "Total Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}}

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, projData, data, "Reports_Cost&Performance_ItemWiseBudgetProvision_Proj");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let epsData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name" , groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'labourAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Labour Budg", headerTooltip: "Labour Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'plantAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Plant Budg", headerTooltip: "Plant Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'matAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Material Budg", headerTooltip: "Material Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'otherAmount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Other Budg", headerTooltip: "Other Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'totalAmount', displayName: "Total Budg", headerTooltip: "Total Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}}

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_Cost&Performance_ItemWiseBudgetProvision_EPS");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			for (let budget of $scope.budgetDetails) {
				let mapKey = budget[key];
				let mapValue = budget[value];
				if (!subReportMap[mapKey]) {
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"currencyCode": budget["currencyCode"],
						"labourAmount": 0,
						"plantAmount": 0,
						"matAmount": 0,
						"otherAmount": 0,
						"projName": budget["projName"],
						"epsName": budget["epsName"]
					};
					if (key == "costItemId") {
						subReportMap[mapKey].parentCostCode = budget["costSubGroupCode"];
						subReportMap[mapKey].parentCostName = budget["costSubGroupName"];
						subReportMap[mapKey].costName = budget["costItemName"];
					}
				}
				subReportMap[mapKey].labourAmount += parseFloat(budget["labourAmount"]);
				subReportMap[mapKey].plantAmount += parseFloat(budget["plantAmount"]);
				subReportMap[mapKey].matAmount += parseFloat(budget["matAmount"]);
				subReportMap[mapKey].otherAmount += parseFloat(budget["otherAmount"]);
			}
			for (const index in subReportMap) {
				labourArr.push(subReportMap[index].labourAmount);
				plantArr.push(subReportMap[index].plantAmount);
				matArr.push(subReportMap[index].matAmount);
				otherArr.push(subReportMap[index].otherAmount);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.totalHrs = {
				"labour": labourArr.reduce((partial_sum, a) => partial_sum + a, 0),
				"plant": plantArr.reduce((partial_sum, a) => partial_sum + a, 0),
				"material": matArr.reduce((partial_sum, a) => partial_sum + a, 0),
				"other": otherArr.reduce((partial_sum, a) => partial_sum + a, 0)
			};
			$scope.data.push(labourArr);
			$scope.data.push(plantArr);
			$scope.data.push(matArr);
			$scope.data.push(otherArr);
			for (let subReport of $scope.subReportData) {
				subReport.totalAmount = subReport.labourAmount + subReport.plantAmount + subReport.matAmount + subReport.otherAmount;
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			initGraph();
		};
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
		$scope.getCostCodeBudgetReport = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds
			};
			BudgetService.getCostCodeBudgetReport(req).then(function (data) {
				$scope.budgetDetails = data.costReportResps;
				for (let budget of $scope.budgetDetails) {
					budget.totalAmount = budget.labourAmount + budget.plantAmount + budget.matAmount + budget.otherAmount;
				}

				$scope.gridOptions.data = angular.copy($scope.budgetDetails);
				if ($scope.subReport != 'None')
					prepareSubReport();
				if ($scope.budgetDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Warning');
				}
			}, function () {
				GenericAlertService.alertMessage("Error occured while cost code actual details", 'Error');
			});
			initGraph();
		};

		$scope.resetItemWiseBudgetDetails = function () {
			$scope.budgetDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.type = '';
			$scope.subReportCode = null;
			$scope.subReportName = null;
			$scope.subReport = 'None';
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		function clearReportData() {
			$scope.budgetDetails = [];
			$scope.subReportCode = null;
			$scope.subReportName = null;
			$scope.subReport = 'None';
		};
		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id" , groupingShowAggregationMenu: false},
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false},
					{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false},
					{ field: 'costItemCode', displayName: "Cost Code Item", headerTooltip: "Cost Code Item" , groupingShowAggregationMenu: false},
					{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false},
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" , groupingShowAggregationMenu: false},
					{ field: 'labourAmount', displayName: "Labour Budget", headerTooltip: "Labour Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'plantAmount', displayName: "Plant Budget", headerTooltip: "Plant Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'matAmount', displayName: "Mat Budget", headerTooltip: "Material Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'otherAmount', displayName: "Other Budget", headerTooltip: "Other Budget" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'totalAmount', displayName: "Total", headerTooltip: "Total" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Cost&Performance_ItemWiseBudgetProvision");
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
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costitemwisebudgethelp.html',
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
