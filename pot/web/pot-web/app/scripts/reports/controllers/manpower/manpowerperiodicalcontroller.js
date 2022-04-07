'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpowerperiodical", {
		url: '/manpowerperiodical',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowerperiodical.html',
				controller: 'ManpowerPeriodicalController'
			}
		}
	})
}]).controller("ManpowerPeriodicalController", ["$scope", "uiGridGroupingConstants","$q", "ngDialog",  "$filter", "ManpowerReportService", "GenericAlertService",
	"CompanyMultiSelectFactory", "EpsProjectMultiSelectFactory", "generalservice", "stylesService", "ngGridService", 'chartService', "uiGridConstants",
	function ($scope, uiGridGroupingConstants ,$q, ngDialog,  $filter, ManpowerReportService, GenericAlertService,
		CompanyMultiSelectFactory, EpsProjectMultiSelectFactory, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Hours';
		$scope.selectedProjIds = [];
		$scope.selectedCompanyIds = [];
		$scope.categoryName = [];
		$scope.periodicSubReportCode = null;
		$scope.manpowerList = [];
		$scope.stylesSvc = stylesService;
		$scope.data = [];
		$scope.labels = [];
		$scope.subReport = {
			name: null
		};
		$scope.periodicSubReport = {
			name: null
		};
		$scope.category = {
			name: null
		}
		$scope.date = new Date();
		$scope.toDate = new Date();
		var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
		$scope.fromDate = new Date($scope.toDate);
		$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
		let todayDate = new Date();
		var defaultFromDate = new Date($scope.fromDate);
		var defaultToDate = new Date($scope.toDate);
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		let projReportsSettings = new Array();

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.selectedClientIds = data.searchProject.clientIds;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
			$scope.clearSubReportDetails();
		};

		$scope.subReports = [{
			name: 'Overall-Used Vs Idle Hours Summary',
			code: "overall"
		}, {
			name: 'Trade Wise Utilisation Hours',
			code: "trade"
		}, {
			name: 'Cost Code Wise-Manhour Utilisation',
			code: "costcode"
		}, {
			name: 'Project Wise-Manhour Utilisation',
			code: "proj"
		}, {
			name: 'EPS Wise-Manhour Utilisation',
			code: "eps"
		}, {
			name: 'Periodical Report-Trade Wise',
			code: "periodTrade"
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

		let series;
		$scope.changeSubReport = function () {
			series = ['Used Hours', 'Idle Hours'];
			$scope.tableHeading = null;
			$scope.type = 'chartTable';
			const subReportMap = [];
			$scope.subReportData = [];
			$scope.periodicSubReport = null;
			let compareProperty = null;
			let codeProperty = null;
			if ($scope.subReport.code === "overall") {
				$scope.tableHeading = "Period";
				if ($scope.subReport.code == "overall") {
					let overAllData = [
						{ field: 'code', displayName: "Period Name", headerTooltip: "Period Name", groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data,"Reports_Manpower_Periodical_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
					
				}
				subReportMap[0] = {
					'code': 'Previous Period',
					'measureUnit': null,
					'usedHrs': 0,
					'idleHrs': 0,
				};
				subReportMap[1] = {
					'code': 'Reporting Period',
					'measureUnit': null,
					'usedHrs': 0,
					'idleHrs': 0,
				};
				subReportMap[2] = {
					'code': 'Up to Date',
					'measureUnit': null,
					'usedHrs': 0,
					'idleHrs': 0,
				};
				for (const manPower of $scope.manpowerList) {

					subReportMap[0].measureUnit = manPower.unitOfMeasure;
					subReportMap[0].usedHrs += manPower.prevUsedHrs;
					subReportMap[0].idleHrs += manPower.prevIdleHrs;

					subReportMap[1].usedHrs += manPower.currentUsedHrs;
					subReportMap[1].idleHrs += manPower.currentIdleHrs;
					subReportMap[1].measureUnit = manPower.unitOfMeasure;

					subReportMap[2].usedHrs += (manPower.prevUsedHrs + manPower.currentUsedHrs);
					subReportMap[2].idleHrs += (manPower.prevIdleHrs + manPower.currentIdleHrs);
					subReportMap[2].measureUnit = manPower.unitOfMeasure;
				}
				setGraphData(subReportMap);
				return;
			}
			else if ($scope.subReport.code === "trade") {
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				$scope.tableHeading = "Trade";
				if ($scope.subReport.code == "trade") {
					let tradeData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" , groupingShowAggregationMenu: false},
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours", groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" , groupingShowAggregationMenu: false},
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, tradeData, data,"Reports_Manpower_Periodical_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			}
			else if ($scope.subReport.code === "costcode") {
				compareProperty = "costCodeId";
				codeProperty = "costCodeName";
				if ($scope.subReport.code == "costcode") {
					let costcodeData = [
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID" , groupingShowAggregationMenu: false},
						{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
						{ field: 'costCodeName', displayName: "Cost Code Item Id", headerTooltip: "Cost Code Item Id" , groupingShowAggregationMenu: false},
						{ field: 'costCodeDesc', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours", groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" , groupingShowAggregationMenu: false},
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours", groupingShowAggregationMenu: false }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, costcodeData, data,"Reports_Manpower_Periodical_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}

			} else if ($scope.subReport.code === "proj") {
				compareProperty = "projId";
				codeProperty = "projName";
				$scope.tableHeading = "Project";
				if ($scope.subReport.code == "proj") {
					let projectData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours", groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours" , groupingShowAggregationMenu: false},
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, projectData, data,"Reports_Manpower_Periodical_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			} else if ($scope.subReport.code === "eps") {
				compareProperty = "parentProjId";
				codeProperty = "parentProjName";
				$scope.tableHeading = "EPS";
				if ($scope.subReport.code == "eps") {
					let epsData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours", groupingShowAggregationMenu: false },
						{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hrs", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_Periodical_AH");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			} else if ($scope.subReport.code === "periodTrade") {
				series = ['Reporting Period', 'Previous Period'];
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				if ($scope.subReport.code == "periodTrade") {
					let periodData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
						{ field: 'prevUsedHrs', displayName: "Previous-Used Hrs", headerTooltip: "Previous Period-Used Hours", groupingShowAggregationMenu: false ,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
						{ field: 'prevIdleHrs', displayName: "Previous-Idle Hrs", headerTooltip: "Previous Period-Idle Hours", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
						{ field: 'usedHrs', displayName: "Reporting-Used Hrs", headerTooltip: "Reporting Period-Used Hours" , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
						{ field: 'idleHrs', displayName: "Reporting-Idle Hrs", headerTooltip: "Reporting Period-Idle Hours", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
						{ field: 'usedHrs', displayName: "Up to Date-Used Hrs", headerTooltip: "Up to Date-Used Hours" , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
						{ field: 'totalUsedIdle', displayName: "Up to Date-Idle Hrs", headerTooltip: "Up to Date-Idle Hours" , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, periodData, data,"Reports_Manpower_Periodical_AH");
					$scope.gridOptions.gridMenuCustomItems = false;
				}

			} else {
				$scope.manpowerList = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				let columnDefs = [
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", visible: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
					{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name" },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group", visible: false },
					{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false },
					{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" },
					{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Desc", visible: false },
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false },
					{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false },
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false },
					{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false },
					{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor" },
					{ field: 'prevUsedHrs', displayName: "Previous-Used", headerTooltip: "Previous Period Used Hrs", visible: false },
					{ field: 'prevIdleHrs', displayName: "Previous-Idle", headerTooltip: "Previous Period Idle Hrs", visible: false },
					{ field: 'prevTotalHrs', displayName: "Previous-Total", headerTooltip: "Previous Period Total Hrs" },
					{ field: 'currentUsedHrs', displayName: "Reporting-Used", headerTooltip: "Reporting Period Used Hrs", visible: false },
					{ field: 'currentIdleHrs', displayName: "Reporting-Idle", headerTooltip: "Reporting Period Idle Hrs", visible: false },
					{ field: 'currentTotalHrs', displayName: "Reporting-Total", headerTooltip: "Reporting Period Total Hrs" },
					{ field: 'totalUsedHrs', displayName: "Up to Date-Used", headerTooltip: "Up to Date Used Hrs", visible: false },
					{ field: 'totalIdleHrs', displayName: "Up to Date-Idle", headerTooltip: "Up to Date Idle Hrs", visible: false },
					{ field: 'utdTotalHrs', displayName: "Up to Date-Total", headerTooltip: "Up to Date Total Hrs" }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Periodical_AH");
				$scope.getManpowerPeriodicalDetails();
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			for (const manPower of $scope.manpowerList) {
				if (!subReportMap[manPower[compareProperty]]) {
					if ($scope.subReport.code === "costcode") {
						subReportMap[manPower[compareProperty]] = {
							'code': manPower[codeProperty],
							'usedHrs': 0,
							'idleHrs': 0,
							'measureUnit': manPower.unitOfMeasure,
							'parentCostCode': manPower.parentCostCode,
							'parentCostCodeName': manPower.parentCostCodeName,
							'costCodeDesc': manPower.costCodeDesc,
							'costCodeName': manPower.costCodeName
						};
					} else {
						subReportMap[manPower[compareProperty]] = {
							'code': manPower[codeProperty],
							'usedHrs': 0,
							'idleHrs': 0,
							'measureUnit': manPower.unitOfMeasure,
							'prevUsedHrs': 0,
							'prevIdleHrs': 0
						};
					}
				}
				if ($scope.subReport.code === "periodTrade") {
					subReportMap[manPower[compareProperty]].prevUsedHrs += manPower.prevUsedHrs;
					subReportMap[manPower[compareProperty]].prevIdleHrs += manPower.prevIdleHrs;
				}
				subReportMap[manPower[compareProperty]].usedHrs += manPower.currentUsedHrs;
				subReportMap[manPower[compareProperty]].idleHrs += manPower.currentIdleHrs;
			}

			setGraphData(subReportMap);
		};

		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let obj = null;
			const usedHrs = new Array();
			const idleHrs = new Array();
			$scope.subReportData = new Array();
			const totalHrs = { 'usedTotal': 0, 'idleTotal': 0 };
			const idleHrsProp = ($scope.subReport.code === "periodTrade") ? 'prevUsedHrs' : 'idleHrs';
			for (const key in subReportMap) {
				obj = subReportMap[key];
				usedHrs.push(obj.usedHrs);
				totalHrs.usedTotal += obj.usedHrs;
				idleHrs.push(obj[idleHrsProp]);
				totalHrs.idleTotal += obj[idleHrsProp];
				$scope.labels.push(obj.code);
				$scope.subReportData.push(obj);
			}
			for (let manpower of $scope.subReportData) {
				manpower.totalUsedIdle = manpower.usedHrs + manpower.idleHrs;
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.totalHrs = totalHrs;
			$scope.data.push(usedHrs);
			$scope.data.push(idleHrs);
			initGraph();
		}

		$scope.categories = [{
			id: 1,
			name: "Direct",
			code: generalservice.employeeCategory[0]
		}, {
			id: 2,
			name: "In-Direct",
			code: generalservice.employeeCategory[1]
		}, {
			id: 3,
			name: "All",
			code: null
		}];

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
				GenericAlertService.alertMessage("Please select the Sub Report", 'Warning');
				return;
			}
			$scope.getManpowerPeriodicalDetails();
		};

		$scope.getManpowerPeriodicalDetails = function () {
			if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined ||
				$scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select the Project", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds.length <= 0 || $scope.selectedCompanyIds == undefined ||
				$scope.selectedCompanyIds == null) {
				GenericAlertService.alertMessage("Please select the Company", 'Warning');
				return;
			}
			if ($scope.categoryName == '') {
				GenericAlertService.alertMessage("Please select the Category", 'Warning');
				return;
			}
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
			const req = {
				"projIds": $scope.selectedProjIds,
				"cmpIds": $scope.selectedCompanyIds,
				"category": $scope.categoryName,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate,
			}

			ManpowerReportService.getManpowerPeriodicalReport(req).then(function (data) {
				$scope.manpowerList = data;
				for (let manPower of $scope.manpowerList) {
					manPower.prevTotalHrs = manPower.prevUsedHrs + manPower.prevIdleHrs;
					manPower.currentTotalHrs = manPower.currentUsedHrs + manPower.currentIdleHrs;
					manPower.utdTotalHrs = manPower.totalUsedHrs + manPower.totalIdleHrs;
				}
				$scope.gridOptions1.data = angular.copy($scope.manpowerList);
				if ($scope.subReport && $scope.subReport.code) {
					$scope.changeSubReport();
				}
				if ($scope.manpowerList.length <= 0) {
					GenericAlertService.alertMessage("Manpower - Periodical Actual Hours not available for the search criteria", 'Warning');
				}

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Periodical details", 'Error');
			});

		};

		$scope.clearSubReportDetails = function () {
			$scope.type = "";
			$scope.subReport = null;
		};

		$scope.resetManpowerPeriodicalDetails = function () {
			$scope.manpowerList = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.selectedCompanyIds = [];
			$scope.categoryName = [];
			$scope.companyNameDisplay = null;
			$scope.type = '';
			$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
			$scope.subReportName = null;
			$scope.category.name = null;
			$scope.subReport = null;
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
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
					{ field: 'parentProjName', displayName: "EPS", width:'300',headerTooltip: "EPS Name", visible: false, groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'companyName', displayName: "Company Name",  width:'300', headerTooltip: "Company Name", groupingShowAggregationMenu: false },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false },
					{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false , groupingShowAggregationMenu: false},
					{ field: 'costCodeName', displayName: "Cost Code ID", width:'180', headerTooltip: "Cost Code ID" , groupingShowAggregationMenu: false},
					{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false },
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false , groupingShowAggregationMenu: false},
					{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID", groupingShowAggregationMenu: false },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false, groupingShowAggregationMenu: false },
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Unit of Measure", width:'140', headerTooltip: "Unit of Measure", visible: false , groupingShowAggregationMenu: false},
					{ field: 'wageCode', displayName: "Wages", width:'170', headerTooltip: "Wage Factor" , groupingShowAggregationMenu: false},
					{ field: 'prevUsedHrs', displayName: "Previous-Used Hrs", width:'100', headerTooltip: "Previous Period Used Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'prevIdleHrs', width:'100', displayName: "Previous-Idle Hrs", headerTooltip: "Previous Period Idle Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'prevTotalHrs', width:'100', displayName: "Previous-Total Hrs", headerTooltip: "Previous Period Total Hrs", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'currentUsedHrs', width:'100', displayName: "Reporting-Used Hrs", headerTooltip: "Reporting Period Used Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'currentIdleHrs', width:'100', displayName: "Reporting-Idle Hrs", headerTooltip: "Reporting Period Idle Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'currentTotalHrs', width:'100', displayName: "Reporting-Total Hrs", headerTooltip: "Reporting Period Total Hrs", groupingShowAggregationMenu: false ,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'totalUsedHrs', width:'100', displayName: "Up to Date-Used Hrs", headerTooltip: "Up To Date Used Hrs", visible: false, groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
					{ field: 'totalIdleHrs', width:'100', displayName: "Up to Date-Idle Hrs", headerTooltip: "Up To Date Idle Hrs", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'utdTotalHrs', width:'100', displayName: "Up to Date-Total Hrs", headerTooltip: "Up To Date Total Hrs" , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}}
				]
				let data = [];
				$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Periodical_AH");
				$scope.gridOptions1.exporterPdfMaxGridWidth = 550;
				$scope.gridOptions1.exporterPdfOrientation = 'landscape';
				$scope.gridOptions1.exporterPdfPageSize = 'A3';
				
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
				template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowerperiodicalhelp.html',
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
