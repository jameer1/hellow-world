'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("periodicalprogress", {
		url: '/periodicalprogress',
		data: {
			progress: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/progress/progressperiodical.html',
				controller: 'ProgressPeriodicalController'
			}
		}
	})
}]).controller("ProgressPeriodicalController", ["$scope", "$interval", "uiGridGroupingConstants", "uiGridConstants", "$state", "$q", "ngDialog", "$filter", "GenericAlertService", "EpsProjectMultiSelectFactory", "CostCodeMultiSelectFactory", "SOWMultiSelectFactory", "progressReportService", 'stylesService', 'ngGridService', 'chartService', function ($scope, $interval, uiGridGroupingConstants, uiGridConstants, $state, $q, ngDialog, $filter, GenericAlertService, EpsProjectMultiSelectFactory, CostCodeMultiSelectFactory, SOWMultiSelectFactory, progressReportService, stylesService, ngGridService, chartService) {
	chartService.getChartMenu($scope);
	$scope.stylesSvc = stylesService;
	$scope.selectedProjIds = [];
	$scope.selectedCostCodeIds = [];
	$scope.selectedSOWIds = [];
	$scope.yAxislabels = "Unit Of Measure";
	$scope.type = '';
	$scope.subReport = 'None';
	$scope.subReportCode = "";
	$scope.labels = [];
	$scope.data = [];
	var series = ['Progress Quantity'];
	$scope.subReports = [{
		name: 'Item of Work Wise Progress',
		code: "summary"
	}, {
		name: 'Cost Code Sub Group wise Quantity',
		code: "costcode"
	}, {
		name: 'Project wise-Progress Quantity',
		code: "proj"
	}, {
		name: 'EPS wise Progress Quantity',
		code: "eps"
	}];
	let todayDate = new Date();
	let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
	$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
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
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS project name", 'Error');
		});
	};

	$scope.getCostCodes = function () {
		if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select project to get cost codes", 'Warning');
			return;
		}
		var req = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		var costCodePopUp = CostCodeMultiSelectFactory.getMultiProjCostCodes(req);
		costCodePopUp.then(function (data) {
			$scope.costCodeNameDisplay = data.selectedCostCodes.costCodesName;
			$scope.selectedCostCodeIds = data.selectedCostCodes.costCodeIds;
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting cost code details", 'Error');
		})
	};

	$scope.getSOWItems = function () {
		if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select project to get SOW items", 'Warning');
			return;
		}
		var sowReq = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		var SOWItemPopUp = SOWMultiSelectFactory.getMultiProjSow(sowReq);
		SOWItemPopUp.then(function (data) {
			$scope.sowNamedisplay = data.selectedSOWs.sowNameDisplay;
			$scope.selectedSOWIds = data.selectedSOWs.sowIds;
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting cost code details", 'Error');
		});
	};
	function noSubReportData() {
		let columnDefs = [
			{ field: 'epsCode', displayName: "EPS Code", headerTooltip: "EPS Code", groupingShowAggregationMenu: false },
			{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
			{ field: 'projCode', displayName: "Proj Code", headerTooltip: "Project Code", groupingShowAggregationMenu: false },
			{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
			{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false },
			{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },
			{ field: 'sowDesc', displayName: "SOW Desc", headerTooltip: "SOW Item Description", groupingShowAggregationMenu: false },
			{ field: 'parentSoeDesc', displayName: "SOE Sub Group Name", headerTooltip: "SOE Sub Group Name", groupingShowAggregationMenu: false },
			{ field: 'soeDesc', displayName: "SOE Desc", headerTooltip: "SOE Item Description", groupingShowAggregationMenu: false },
			{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
			{
				field: 'prevValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
			},
			{
				field: 'currentValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rep Qty", headerTooltip: "Reporting Period Quantity", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
			},
			{
				field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Qty", headerTooltip: "Quantity Up to Date", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
			}
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Progress_PP_Reports");
		$scope.gridOptions.gridMenuCustomItems = false;
	}

	$scope.changeSubReport = function () {
		if ($scope.subReport && $scope.subReport != "None") {
			$scope.type = 'chartTable';
			$scope.subReportName = $scope.subReport.name;
			$scope.subReportCode = $scope.subReport.code
			prepareSubReport();
		} else {
			$scope.progressDetails = [];
			$scope.type = '';
			$scope.subReportName = '';
			$scope.subReportCode = '';
			noSubReportData();
			$scope.getProgressPeriodicalRecords();
		}
	};
	function prepareSubReport() {
		$scope.subReportData = [];
		$scope.type = 'chartTable';
		if ($scope.subReport.code == "summary" || $scope.subReport.code == "costcode") {
			generateSubReportData("parentSoeId", "parentSoeDesc");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		}
	};
	function generateSubReportData(key, value) {
		let subReportMap = [];
		if ($scope.subReport.code == "costcode") {
			let costCodeData = [
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
				{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },
				{ field: 'costName', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name", groupingShowAggregationMenu: false },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
				{
					field: 'prevValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rep Qty", headerTooltip: "Reporting Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Qty", headerTooltip: "Quantity Up to Date", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Progress_PP_Reports_CostCode");
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "summary") {
			let summaryData = [
				{ field: 'costName', displayName: "Tangible Classification Name", headerTooltip: "Tangible Classification Name", groupingShowAggregationMenu: false },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
				{
					field: 'prevValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rep Qty", headerTooltip: "Reporting Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Qty", headerTooltip: "Quantity Up to Date", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data, "Reports_Progress_PP_Reports_Summary");
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "proj") {
			let summaryData = [
				{ field: 'costName', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name", groupingShowAggregationMenu: false },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },				
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
				{
					field: 'prevValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rep Qty", headerTooltip: "Reporting Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Qty", headerTooltip: "Quantity Up to Date", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data, "Reports_Progress_PP_Reports_Proj");
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "eps") {
			let summaryData = [
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },				
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
				{
					field: 'prevValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rep Qty", headerTooltip: "Reporting Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'valueCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Qty", headerTooltip: "Quantity Up to Date", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data, "Reports_Progress_PP_Reports_EPS");
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		for (let progress of $scope.progressDetails) {
			let mapKey = progress[key];
			let costName = progress[value];
			let epsName = progress.epsName;
			if (!subReportMap[mapKey]) {
				let valueCount = 0;
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"costName": costName,
					"epsName": epsName,
					"unitOfMeasure": progress["unitOfMeasure"],
					"valueCount": valueCount,				
					"totalValue": progress["totalValue"],
					"prevValue": progress["prevValue"],
					"parentCostName": progress["parentCostName"]
				};
				if ($scope.subReport.code == "costcode") {
					subReportMap[mapKey].parentCostName = progress["parentCostName"];
					subReportMap[mapKey].totalValue = progress["totalValue"];
					subReportMap[mapKey].prevValue = progress["prevValue"];
				}
			}
			subReportMap[mapKey].valueCount += parseFloat(progress["currentValue"]);
		}
		setGraphData(subReportMap);
	};
	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		const valueArr = new Array();
		$scope.subReportData = new Array();
		for (const index in subReportMap) {
			valueArr.push(subReportMap[index].valueCount);
			$scope.labels.push(subReportMap[index].costName+" in "+subReportMap[index].unitOfMeasure);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data = valueArr;
		initGraph();
	};
	$scope.clearSubReportDetails = function () {
		$scope.subReport = 'None';
		$scope.progressDetails = [];
		$scope.subReportCode = "";
		$scope.subReportName = "";
	};

	$scope.resetPeriodicalReport = function () {
		$scope.progressDetails = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.type = '';
		$scope.sowNamedisplay = null;
		$scope.selectedSOWIds = {};
		$scope.costCodeNameDisplay = null;
		$scope.subReportName = '';
		$scope.subReportCode = '';
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};
	$scope.getProgressPeriodicalRecords = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select project to fetch report", 'Warning');
			return;
		}
		if ($scope.selectedCostCodeIds.length <= 0) {
			GenericAlertService.alertMessage("Please select cost code to fetch report", 'Warning');
			return;
		}
		if ($scope.selectedSOWIds.length <= 0) {
			GenericAlertService.alertMessage("Please select SOW items to fetch report", 'Warning');
			return;
		}
		var req = {
			"projIds": $scope.selectedProjIds,
			"costCodeIds": $scope.selectedCostCodeIds,
			"sowIds": $scope.selectedSOWIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
			"subReportType": $scope.subReportCode
		}
		progressReportService.getProgressPeriodicalRecords(req).then(function (data) {
			$scope.progressDetails = data;
			for (let progress of $scope.progressDetails) {
				progress.totalValue = progress.prevValue + progress.currentValue;
			}
			$scope.gridOptions.data = angular.copy($scope.progressDetails);
			if ($scope.progressDetails.length <= 0) {
				GenericAlertService.alertMessage("Periodical Progress Report not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  progress details", 'Error');
		});
		initGraph();
	};

	function initGraph() {
		$scope.series = series;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit($scope.yAxislabels);
	};


	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'epsCode', displayName: "EPS Code", headerTooltip: "EPS Code", groupingShowAggregationMenu: false },
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'projCode', displayName: "Proj Code", headerTooltip: "Project Code", groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },
				{ field: 'sowDesc', displayName: "SOW Desc", headerTooltip: "SOW Item Description", groupingShowAggregationMenu: false },
				{ field: 'parentSoeDesc', displayName: "SOE Sub Group Name", headerTooltip: "SOE Sub Group Name", groupingShowAggregationMenu: false },
				{ field: 'soeDesc', displayName: "SOE Desc", headerTooltip: "SOE Item Description", groupingShowAggregationMenu: false },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
				{
					field: 'prevValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'currentValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rep Qty", headerTooltip: "Reporting Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Qty", headerTooltip: "Quantity Up to Date", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Progress_PP_Reports");
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
			template: 'views/help&tutorials/reportshelp/progresshelp/periodprogresshelp.html',
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
