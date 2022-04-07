'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("empdailyresourcestatus", {
		url: '/empdailyresourcestatus',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/empdailyresourcestatus.html',
				controller: 'EmpDailyResourceStatusController'
			}
		}
	})
}]).controller("EmpDailyResourceStatusController", ["$scope", "$filter", "uiGridGroupingConstants", "$q", "$state", "ngDialog", "GenericAlertService", "ProjectScheduleService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "EmpAttendanceService", "ResourceCurveService", "SchedulePlannedValueService", "stylesService", "ngGridService", "chartService", "uiGridConstants", function ($scope, $filter, uiGridGroupingConstants, $q, $state, ngDialog, GenericAlertService, ProjectScheduleService,
	EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, EmpAttendanceService, ResourceCurveService, SchedulePlannedValueService, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	var labels = [];
	$scope.data = [];
	$scope.stylesSvc = stylesService;
	var series = ['Present', 'Planned'];
	$scope.subReport = "None";
	$scope.manpowerBudgets = [];
	$scope.dailyResourceDetails = [];
	$scope.type = '';
	$scope.selectedProjIds = [];
	let totalValues = {
		'plannedCount': 0,
		'presentCount': 0
	};

	$scope.date = new Date();
	var defaultDate = new Date($scope.date);
	$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");

	$scope.totalValues = angular.copy(totalValues);
	$scope.subReports = [{
		name: 'Resource Category Wise',
		code: "category"
	}, {
		name: 'Trade Wise',
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
			{ field: 'displayNamesMap.empCategory', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false },
			{ field: 'displayNamesMap.tradeName', displayName: "Trade Name", headerTooltip: "Trade Name", groupingShowAggregationMenu: false },
			{ field: 'displayNamesMap.plannedValue', displayName: "Planned Numbers", headerTooltip: "Planned Numbers", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
			customTreeAggregationFinalizerFn: function (aggregation) {
				aggregation.rendered = aggregation.value;
			} },
			{ field: 'displayNamesMap.totalPresent', displayName: "Total Present", headerTooltip: "Total No's Present Condition", cellClass: "justify-right", headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
			customTreeAggregationFinalizerFn: function (aggregation) {
				aggregation.rendered = aggregation.value;
			} },
			{ field: 'displayNamesMap.variance', displayName: "Variance", headerTooltip: "Variance", cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
			customTreeAggregationFinalizerFn: function (aggregation) {
				aggregation.rendered = aggregation.value;
			} }
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Reports_Attendance_EmployeeDailyResourcesStatus");
		// $scope.gridOptions.showColumnFooter = true;
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
		if ($scope.subReport.code == "category") {
			generateSubReportData("empCategory", "empCategory");
		} else if ($scope.subReport.code == "trade") {
			generateSubReportData("empClassId", "tradeName");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		}
	};
	function generateSubReportData(key, value) {
		let presentCountArr = [], plannedCountArr = [];
		let subReportMap = [];
		if ($scope.subReport.code == "category") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.plannedCount | number: 0}}</div>',
					displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'presentCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.presentCount | number: 0}}</div>',
					displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{ field: 'variance', displayName: "Variance", headerTooltip: "Variance" , cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Attendance_EmployeeDailyResourcesStatus_Category");
			// $scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "trade") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Trade Name", headerTooltip: "Trade Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.plannedCount | number: 0}}</div>',
					displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'presentCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.presentCount | number: 0}}</div>',
					displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{ field: 'variance', displayName: "Variance", headerTooltip: "Variance", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Attendance_EmployeeDailyResourcesStatus_Trade");
			// $scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "proj") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.plannedCount | number: 0}}</div>',
					displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} 
				},
				{
					name: 'presentCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.presentCount | number: 0}}</div>',
					displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{ field: 'variance', displayName: "Variance", headerTooltip: "Variance", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Attendance_EmployeeDailyResourcesStatus_Proj");
			// $scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "eps") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.plannedCount | number: 0}}</div>',
					displayName: "Planned Numbers", headerTooltip: "Planned Numbers", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'presentCount', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.presentCount | number: 0}}</div>',
					displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{ field: 'variance', displayName: "Variance", headerTooltip: "Variance", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Attendance_EmployeeDailyResourcesStatus_EPS");
			// $scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		$scope.totalValues = angular.copy(totalValues);
		for (let plantDtl of $scope.dailyResourceDetails) {
			let mapKey = plantDtl.displayNamesMap[key];
			let mapValue = plantDtl.displayNamesMap[value];
			if (!subReportMap[mapKey]) {
				let presentCount = 0, plannedCount = 0;
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"presentCount": presentCount,
					"plannedCount": plannedCount
				};
			}
			if (plantDtl.displayNamesMap['plannedValue']) {
				subReportMap[mapKey].plannedCount = parseFloat(plantDtl.displayNamesMap['plannedValue']) + subReportMap[mapKey].plannedCount;
				$scope.totalValues.plannedCount += parseFloat(plantDtl.displayNamesMap['plannedValue']);
			}
			if (plantDtl.displayNamesMap['totalPresent']) {
				subReportMap[mapKey].presentCount = parseInt(plantDtl.displayNamesMap['totalPresent']) + subReportMap[mapKey].presentCount;
				$scope.totalValues.presentCount += parseInt(plantDtl.displayNamesMap['totalPresent']);
			}
		}
		for (const index in subReportMap) {
			subReportMap[index].variance = Math.round(subReportMap[index].plannedCount) - Math.round(subReportMap[index].presentCount);
			presentCountArr.push(Math.round(subReportMap[index].presentCount));
			plannedCountArr.push(Math.round(subReportMap[index].plannedCount));
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(presentCountArr);
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
			"empCats": $scope.category.value,
			"date": $scope.date,
		};
		EmpAttendanceService.getEmpDailyResourceStatus(req).then(function (data) {
			$scope.dailyResourceDetails = data;
			$scope.gridOptions.data = angular.copy($scope.dailyResourceDetails);
			if ($scope.dailyResourceDetails.length <= 0) {
				GenericAlertService.alertMessage("Employee Daily Resources Status - Plan Vs Present not available for the search criteria", 'Info');
			}
		});
		$scope.getScheduleManpower();
		$scope.initGraph('bar');
	};
	$scope.getScheduleManpower = function () {
		ProjectScheduleService.getMultiProjBudgetManPowerDetails($scope.selectedProjIds).then(function (data) {
			$scope.manpowerBudgets = data;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Manpower budget details", "Error");
		});
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
		$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");
		$scope.subReportCode = "";
		$scope.subReportName = null;
		$scope.category = $scope.categories[2];
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
				{ field: 'displayNamesMap.empCategory', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false },
				{ field: 'displayNamesMap.tradeName', displayName: "Trade Name", headerTooltip: "Trade Name", groupingShowAggregationMenu: false },
				{ field: 'displayNamesMap.plannedValue', displayName: "Planned Numbers", headerTooltip: "Planned Numbers", cellClass: "justify-right",cellFilter: ' number: 2', headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'displayNamesMap.totalPresent', displayName: "Total Present", headerTooltip: "Total No's Present Condition", cellClass: "justify-right",cellFilter: ' number: 2', headerCellClass: "justify-right", footerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ field: 'displayNamesMap.variance', displayName: "Variance", headerTooltip: "Variance", cellClass: "justify-right", headerCellClass: "justify-right",cellFilter: ' number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Attendance_EmployeeDailyResourcesStatus");
			// $scope.gridOptions.showColumnFooter = true;
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
			template: 'views/help&tutorials/reportshelp/attendancerecordshelp/empdailyresourcehelp.html',
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
