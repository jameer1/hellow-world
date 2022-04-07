'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpowermobistats", {
		url: '/manpowermobistats',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowerperiodicalmobilization.html',
				controller: 'ManpowerPeriodcalMobilizationController'
			}
		}
	})
}]).controller("ManpowerPeriodcalMobilizationController", ["$rootScope", "uiGridGroupingConstants","$scope", "$q", "$state", "$filter", "ngDialog", "ManpowerReportService", "CompanyMultiSelectFactory", "GenericAlertService", "UserEPSProjectService", "$location", "EpsProjectMultiSelectFactory", "ProjCostCodeService", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($rootScope,uiGridGroupingConstants ,$scope, $q, $state, $filter, ngDialog, ManpowerReportService, CompanyMultiSelectFactory, GenericAlertService, UserEPSProjectService, $location, EpsProjectMultiSelectFactory, ProjCostCodeService, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'NUMBER OF EMPLOYEES';
	$scope.stylesSvc = stylesService;
	$scope.manpowerList = [];
	$scope.selectedProjIds = [];
	$scope.selectedCompanyIds = [];
	$scope.series = ['Previous PeriodNet Total', 'Mobilized During Current Period',
		'DeMobilized During Current Period', 'Net Strength Remaining'];

	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);
	let todayDate = new Date();
	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
	$scope.subReport = {
		name: null
	}
	var labels = [];
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.selectedClientIds = data.searchProject.clientIds
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
		$scope.clearSubReportDetails();
	};

	$scope.subReports = [{
		name: 'Trade Wise Mobilisation Status',
		code: "trade"
	}, {
		name: 'Project Wise Mobilisation Status',
		code: "proj"
	}, {
		name: 'Eps Wise Mobilisation Status',
		code: "eps"
	}, {
		name: 'Company Wise Mobilisation Status',
		code: "company"
	}];
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
					let manPowerData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name", groupingShowAggregationMenu: false },
						{ field: 'prevCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Prev Per-Net Total', headerTooltip: 'Previous PeriodNet Total', groupingShowAggregationMenu: false },
						{ field: 'currentMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Mob During Curr Period', headerTooltip: 'Mobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'currentDeMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'DeMob During Curr Period', headerTooltip: 'DeMobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Net Strength Remaining', headerTooltip: 'Net Strength Remaining', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Periodical_MS");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "eps":
				compareProperty = "parentProjId";
				codeProperty = "parentProjName";
				if ($scope.subReport.code == "eps") {
					let manPowerData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
						{ field: 'prevCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Prev Per-Net Total', headerTooltip: 'Previous PeriodNet Total', groupingShowAggregationMenu: false },
						{ field: 'currentMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Mob During Curr Period', headerTooltip: 'Mobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'currentDeMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'DeMob During Curr Period', headerTooltip: 'DeMobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Net Strength Remaining', headerTooltip: 'Net Strength Remaining', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Periodical_MS");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "proj":
				compareProperty = "projId";
				codeProperty = "projName";
				$scope.tableHeading = "Project";
				if ($scope.subReport.code == "proj") {
					let manPowerData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'prevCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Prev Per-Net Total', headerTooltip: 'Previous PeriodNet Total', groupingShowAggregationMenu: false },
						{ field: 'currentMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Mob During Curr Period', headerTooltip: 'Mobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'currentDeMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'DeMob During Curr Period', headerTooltip: 'DeMobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Net Strength Remaining', headerTooltip: 'Net Strength Remaining', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Periodical_MS");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "company":
				compareProperty = "companyId";
				codeProperty = "companyName";
				$scope.tableHeading = "Company";
				if ($scope.subReport.code == "company") {
					let manPowerData = [
						{ field: 'code', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
						{ field: 'prevCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Prev Per-Net Total', headerTooltip: 'Previous PeriodNet Total', groupingShowAggregationMenu: false },
						{ field: 'currentMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Mob During Curr Period', headerTooltip: 'Mobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'currentDeMobilCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'DeMob During Curr Period', headerTooltip: 'DeMobilized During Current Period', groupingShowAggregationMenu: false },
						{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Net Strength Remaining', headerTooltip: 'Net Strength Remaining', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Periodical_MS");
					$scope.gridOptions.gridMenuCustomItems = false;
				
				}
				break;
			default:
				$scope.manpowerList = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				let columnDefs = [
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false, groupingShowAggregationMenu: false },
					{ field: 'classificationPerClient', displayName: "Classification As Per Client", headerTooltip: "Classification As Per Client", groupingShowAggregationMenu: false },
					{ field: 'classificationPerUnion', displayName: "Classification As Per Union", headerTooltip: "Classification As Per Union", groupingShowAggregationMenu: false },
					{ field: 'prevCount', displayName: "Strength Prev Period", headerTooltip: "Strength As Per Previous Period", groupingShowAggregationMenu: false },
					{ field: 'currentMobilCount', displayName: "Mob Rep Period", headerTooltip: "Mobilized During The Reporting Period", groupingShowAggregationMenu: false },
					{ field: 'currentDeMobilCount', displayName: "DeMob Rep Period", headerTooltip: "DeMobilized During The Reporting Period", groupingShowAggregationMenu: false },
					{ field: 'totalCount', displayName: "Net Strength", headerTooltip: "Net Strength At End Of Reporting Period", groupingShowAggregationMenu: false },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Periodical_MS");
				$scope.getmanpowerMobilizationDetails();
				$scope.gridOptions.gridMenuCustomItems = false;
		}
		const subReportMap = new Array();
		for (const manPower of $scope.manpowerList) {
			const key = manPower[compareProperty];
			if (!subReportMap[key]) {
				subReportMap[key] = {
					'code': manPower[codeProperty],
					'prevCount': 0,
					'currentMobilCount': 0,
					'currentDeMobilCount': 0,
					'totalCount': 0
				};
			}
			subReportMap[key].prevCount += manPower.prevCount;
			subReportMap[key].currentMobilCount += manPower.currentMobilCount;
			subReportMap[key].currentDeMobilCount += manPower.currentDeMobilCount;
			subReportMap[key].totalCount += manPower.totalCount;
		}
		setGraphData(subReportMap);
	};

	$scope.getCompanyList = function () {
		var companyReq = {
			"status": 1
		}
		var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
		companyPopUp.then(function (data) {
			$scope.companyNameDisplay = data.selectedCompanies.companyName;
			$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
			$scope.manpowerList = [];
		})
		$scope.clearSubReportDetails();
	};

	$scope.selectType = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.subReport.name == null) {
			GenericAlertService.alertMessage("Please select Sub Report", 'Warning');
			return;
		}
	};

	$scope.getmanpowerMobilizationDetails = function () {
		if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.selectedCompanyIds.length <= 0 || $scope.selectedCompanyIds == undefined || $scope.selectedCompanyIds == null) {
			GenericAlertService.alertMessage("Please select the Company", 'Warning');
			return;
		}
		if (new Date($scope.fromDate) > new Date($scope.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}
		const req = {
			"projIds": $scope.selectedProjIds,
			"cmpIds": $scope.selectedCompanyIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
		};

		ManpowerReportService.getManpowerPeriodicalMobilisationReport(req).then(function (data) {
			$scope.manpowerList = data;
			$scope.gridOptions1.data = angular.copy($scope.manpowerList);
			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			}
			// $scope.gridOptions.data = angular.copy($scope.manpowerList);
			if ($scope.manpowerList.length <= 0) {
				GenericAlertService.alertMessage("Manpower Periodical Mobilisation Statistics not available for the selected Search Criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting  Periodical Mobilization details", 'Error');
		});
	};
	$scope.clearSubReportDetails = function () {
		$scope.manpowerList = [];
		$scope.type = "";
		$scope.subReport = null;
	};

	$scope.resetmanpowerData = function () {
		$scope.manpowerList = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = [];
		$scope.selectedCompanyIds = []
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.companyNameDisplay = null;
		$scope.data = [];
		$scope.labels = [];
		$scope.type = '';
		$scope.subReport = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		let obj = null;
		const prevCounts = new Array();
		const currentDemobilCounts = new Array();
		const currentMobilCounts = new Array();
		const netStrength = new Array();
		$scope.subReportData = new Array();
		$scope.totalCounts = {
			'prevCount': 0,
			'currentDeMobilCount': 0,
			'currentMobilCount': 0,
			'netStrengthCount': 0
		};
		for (const key in subReportMap) {
			obj = subReportMap[key];
			prevCounts.push(obj.prevCount);
			$scope.totalCounts.prevCount += obj.prevCount;
			currentDemobilCounts.push(obj.currentDeMobilCount);
			$scope.totalCounts.currentDeMobilCount += obj.currentDeMobilCount;
			currentMobilCounts.push(obj.currentMobilCount);
			$scope.totalCounts.currentMobilCount += obj.currentMobilCount;
			netStrength.push(obj.totalCount);
			$scope.totalCounts.netStrengthCount += obj.totalCount;
			$scope.labels.push(obj.code);
			$scope.subReportData.push(obj);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(prevCounts);
		$scope.data.push(currentMobilCounts);
		$scope.data.push(currentDemobilCounts);
		$scope.data.push(netStrength);
		initGraph();
	}

	function initGraph() {


		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit($scope.yAxislabels);
	};

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
				{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
				{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false , groupingShowAggregationMenu: false},
				{ field: 'classificationPerClient', displayName: "Classification As Per Client", headerTooltip: "Classification As Per Client", groupingShowAggregationMenu: false },
				{ field: 'classificationPerUnion', displayName: "Classification As Per Union", headerTooltip: "Classification As Per Union", groupingShowAggregationMenu: false },
				{ field: 'prevCount', displayName: "Strength Prev Period", headerTooltip: "Strength As Per Previous Period", groupingShowAggregationMenu: false , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				{ field: 'currentMobilCount', displayName: "Mob Rep Period", headerTooltip: "Mobilized During The Reporting Period", groupingShowAggregationMenu: false , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				{ field: 'currentDeMobilCount', displayName: "DeMob Rep Period", headerTooltip: "DeMobilized During The Reporting Period", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{ field: 'totalCount', displayName: "Net Strength", headerTooltip: "Net Strength At End Of Reporting Period", groupingShowAggregationMenu: false , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},

			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Periodical_MS");
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
			template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowerperiodmobstathelp.html',
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
