'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantdailyresourcestatus", {
		url: '/plantdailyresourcestatus',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/plantdailyresourcestatus.html',
				controller: 'PlantDailyResourceStatusController'
			}
		}
	})
}]).controller("PlantDailyResourceStatusController", ["$scope", "uiGridGroupingConstants", "$filter", "$q", "$state", "ngDialog", "GenericAlertService", "ProjectScheduleService", "EpsProjectMultiSelectFactory", "PlantAttendanceService", "ResourceCurveService", "SchedulePlannedValueService", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($scope, uiGridGroupingConstants, $filter, $q, $state, ngDialog, GenericAlertService, ProjectScheduleService,
	EpsProjectMultiSelectFactory, PlantAttendanceService, ResourceCurveService, SchedulePlannedValueService, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	var labels = [];
	$scope.data = [];
	$scope.stylesSvc = stylesService;
	var series = ['Working', 'Planned'];
	$scope.subReport = "None";
	$scope.plantBudgets = [];
	$scope.dailyResourceDetails = [];
	$scope.type = '';
	$scope.selectedProjIds = [];
	let totalValues = {
		'plannedCount': 0,
		'workingCount': 0
	};

	$scope.date = new Date();
	var defaultDate = new Date($scope.date);
	$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");

	$scope.totalValues = angular.copy(totalValues);
	$scope.subReports = [{
		name: 'Classification Wise',
		code: "trade"
	}, {
		name: 'Project Wise',
		code: "proj"
	}, {
		name: 'EPS Wise',
		code: "eps"
	}];
	$scope.categories = [{
		id: 1,
		name: "Direct",
		value: ['DIRECT']
	}, {
		id: 2,
		name: "In-Direct",
		value: ['IN-DIRECT']
	}, {
		id: 3,
		name: "All",
		value: ['DIRECT', 'IN-DIRECT']
	}];
	$scope.category = $scope.categories[2];
	function noSubReportData() {
		let columnDefs = [
			{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
			{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
			{ field: 'displayNamesMap.plantResourceName', displayName: "Resource Name", headerTooltip: "Resource Name", groupingShowAggregationMenu: false },
			{ field: 'displayNamesMap.tradeName', displayName: "Plant Name", headerTooltip: "Plant Name", groupingShowAggregationMenu: false },
			{ field: 'displayNamesMap.plannedValue', aggregationType: uiGridConstants.aggregationTypes.sum,cellFilter: ' number: 2', displayName: "Planned Numbers", headerTooltip: "Planned Numbers", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
			customTreeAggregationFinalizerFn: function (aggregation) {
				aggregation.rendered = aggregation.value;
			} },
			{ field: 'displayNamesMap.totalWorking', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Working",cellFilter: ' number: 2', headerTooltip: "Total No's Working Condition", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
			customTreeAggregationFinalizerFn: function (aggregation) {
				aggregation.rendered = aggregation.value;
			} },
			{ field: 'displayNamesMap.variance', displayName: "Variance", headerTooltip: "Variance", cellClass: "justify-right", cellFilter: ' number: 2',headerCellClass: "justify-right", groupingShowAggregationMenu: false  }
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [],"Reports_Attendance Records_PDRS");
		$scope.gridOptions.showColumnFooter = true;
		$scope.gridOptions.gridMenuCustomItems = false;
	}
	$scope.changeSubReport = function () {
		if ($scope.subReport && $scope.subReport != "None") {
			$scope.type = 'chartTable';
			$scope.subReportName = $scope.subReport.name;
			$scope.subReportCode = $scope.subReport.code
			prepareSubReport();
		} else {
			$scope.dailyResourceDetails = [];
			$scope.type = '';
			$scope.subReportName = '';
			$scope.subReportCode = '';
			noSubReportData();
			$scope.getDailyResourceAttendence();
		}
	};
	function prepareSubReport() {
		$scope.labels = [];
		$scope.subReportData = [];
		$scope.data = [];
		if ($scope.subReport.code == "trade") {
			generateSubReportData("plantClassId", "plantResourceName");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		}
	};
	function generateSubReportData(key, value) {
		let workingCountArr = [], plannedCountArr = [];
		$scope.totalValues = angular.copy(totalValues);
		let subReportMap = [];
		if ($scope.subReport.code == "trade") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Classification Name", headerTooltip: "Classification Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.plannedCount | number: 0}}</div>',
					aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'workingCount', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.workingCount | number: 0}}</div>',
					aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'variance', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.variance | number: 0}}</div>',
					displayName: "variance", headerTooltip: "variance", groupingShowAggregationMenu: false 
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data,"Reports_Attendance Records_PDRS");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "proj") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.plannedCount | number: 0}}</div>',
					aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'workingCount', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.workingCount | number: 0}}</div>',
					aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'variance', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.variance | number: 0}}</div>',
					displayName: "variance", headerTooltip: "variance", groupingShowAggregationMenu: false 
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data,"Reports_Attendance Records_PDRS");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "eps") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.plannedCount | number: 0}}</div>',
					aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'workingCount', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.workingCount | number: 0}}</div>',
					aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'variance', cellFilter: 'number: 0', cellClass: "ui-grid-cell-contents justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{ row.entity.variance | number: 0}}</div>',
					displayName: "variance", headerTooltip: "variance", groupingShowAggregationMenu: false 
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data,"Reports_Attendance Records_PDRS");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		for (let plantDtl of $scope.dailyResourceDetails) {
			let mapKey = plantDtl.displayNamesMap[key];
			let mapValue = plantDtl.displayNamesMap[value];
			if (!subReportMap[mapKey]) {
				let workingCount = 0, plannedCount = 0;
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"workingCount": workingCount,
					"plannedCount": plannedCount
				};
			}
			if (plantDtl.displayNamesMap['plannedValue']) {
				subReportMap[mapKey].plannedCount = parseFloat(plantDtl.displayNamesMap['plannedValue']) + subReportMap[mapKey].plannedCount;
				$scope.totalValues.plannedCount += parseFloat(plantDtl.displayNamesMap['plannedValue']);
			}
			if (plantDtl.displayNamesMap['totalWorking']) {
				subReportMap[mapKey].workingCount = parseInt(plantDtl.displayNamesMap['totalWorking']) + subReportMap[mapKey].workingCount;
				$scope.totalValues.workingCount += parseFloat(plantDtl.displayNamesMap['totalWorking']);
			}
		}
		for (const index in subReportMap) {
			subReportMap[index].variance = Math.round(subReportMap[index].plannedCount) - Math.round(subReportMap[index].workingCount);
			workingCountArr.push(Math.round(subReportMap[index].workingCount));
			plannedCountArr.push(Math.round(subReportMap[index].plannedCount));
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(workingCountArr);
		$scope.data.push(plannedCountArr);
	};
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.selectedClientIds = data.searchProject.clientIds;
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	$scope.initGraph = function () {
		$scope.labels = labels;
		$scope.series = series;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit();
	};
	$scope.getDailyResourceAttendence = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select projects to fetch report", 'Info');
			return;
		}
		if (!$scope.date) {
			GenericAlertService.alertMessage("Please select the date to fetch report", 'Info');
			return;
		}
		var req = {
			"projIds": $scope.selectedProjIds,
			"date": $scope.date,
		};
		PlantAttendanceService.getPlantDailyResourceStatus(req).then(function (data) {
			$scope.dailyResourceDetails = data;
			$scope.gridOptions.data = angular.copy($scope.dailyResourceDetails);
			if ($scope.gridOptions.data.length <= 0)
				GenericAlertService.alertMessage("Plant Daily Resources Status - Plan Vs Working not available for the search criteria", 'Info');
		});
		$scope.initGraph('bar');
	};
	$scope.clearSubReportDetails = function () {
		$scope.type = '';
		$scope.subReport = "None";
		$scope.subReportName = "";
		$scope.subReportCode = "";
		$scope.dailyResourceDetails = [];
	};
	$scope.resetReportDetails = function () {
		$scope.dailyResourceDetails = [];
		$scope.selectedProjIds = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.type = '';
		$scope.subReport = "None";
		$scope.subReportCode = "";
		$scope.subReportName = null;
		$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'displayNamesMap.plantResourceName', displayName: "Resource Name", headerTooltip: "Resource Name", groupingShowAggregationMenu: false },
				{ field: 'displayNamesMap.tradeName', displayName: "Plant Name", headerTooltip: "Plant Name", groupingShowAggregationMenu: false },
				{ field: 'displayNamesMap.plannedValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Planned Numbers",cellFilter: ' number: 2', headerTooltip: "Planned Numbers", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'displayNamesMap.totalWorking', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Working",cellFilter: ' number: 2', headerTooltip: "Total No's Working Condition", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'displayNamesMap.variance', displayName: "Variance", headerTooltip: "Variance", cellClass: "justify-right", cellFilter: ' number: 2',headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false  }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Attendance Records_PDRS");
			$scope.gridOptions.showColumnFooter = true;
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
			template: 'views/help&tutorials/reportshelp/attendancerecordshelp/empplantresourcehelp.html',
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
