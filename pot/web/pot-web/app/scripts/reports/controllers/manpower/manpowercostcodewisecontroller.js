'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpowercostwise", {
		url: '/manpowercostwise',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowercostcodewisedailylist.html',
				controller: 'ManpowerCostCodeWiseController'
			}
		}
	})
}]).controller("ManpowerCostCodeWiseController", ["$scope", "uiGridGroupingConstants", "$q", "ngDialog", "$filter", "ManpowerReportService", "GenericAlertService", "EpsProjectMultiSelectFactory", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($scope, uiGridGroupingConstants, $q, ngDialog, $filter, ManpowerReportService, GenericAlertService,
	EpsProjectMultiSelectFactory, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'EMPLOYEES';
	$scope.stylesSvc = stylesService;
	$scope.manpowerList = [];
	$scope.data = [];
	$scope.subReport = {
		name: null
	}
	$scope.selectedProjIds = []
	
	$scope.date = new Date();
	var defaultDate = new Date($scope.date);
	$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");

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

	$scope.subReports = [{
		name: 'Manpower Category Wise Nos',
		code: "manpower"
	}, {
		name: 'Trade Wise - Manpower Nos',
		code: "trade"
	}, {
		name: 'Cost Code Wise-Manpower Nos',
		code: "costcode"
	}, {
		name: 'Crew Name Wise -Manpower Nos',
		code: "crew"
	}, {
		name: 'Project Wise-Manpower Nos',
		code: "proj"
	}, {
		name: 'EPS Wise-Manpower Nos',
		code: "eps"
	}];
	$scope.changeSubReport = function () {
		$scope.type = 'chartTable';
		let compareProperty;
		let codeProperty;
		switch ($scope.subReport.code) {
			case "manpower":
				compareProperty = "empCategoryName";
				codeProperty = "empCategoryName";
				$scope.tableHeading = "Category";
				if ($scope.subReport.code == "manpower") {
					let manPowerData = [
						{ field: 'code', displayName: "Category Name", headerTooltip: "Category Name" },
						{ field: 'userIds.length', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "No of Employees", headerTooltip: "Number of Employees" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_CostCodeWise_DDL_CategoryWise");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "trade":
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				$scope.tableHeading = "Trade";
				if ($scope.subReport.code == "trade") {
					let tradeData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" },
						{ field: 'userIds.length', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "No of Employees", headerTooltip: "Number of Employees" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, tradeData, data,"Reports_Manpower_CostCodeWise_DDL_TradeWise");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "costcode":
				compareProperty = "costCodeId";
				codeProperty = "costCodeName";
				if ($scope.subReport.code == "costcode") {
					let costcodeData = [
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id" },
						{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" },
						{ field: 'costCodeName', displayName: "Cost Code Item Id", headerTooltip: "Cost Code Item Id" },
						{ field: 'costCodeDesc', displayName: "Cost Code Item Desc", headerTooltip: "Cost Code Item Description" },
						{ field: 'userIds.length', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "No of Employees", headerTooltip: "Number of Employees" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, costcodeData, data,"Reports_Manpower_CostCodeWise_DDL_CostCodeWise");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "eps":
				compareProperty = "parentProjId";
				codeProperty = "parentProjName";
				$scope.tableHeading = "EPS";
				if ($scope.subReport.code == "eps") {
					let epsData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" },
						{ field: 'userIds.length', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "No of Employees", headerTooltip: "Number of Employees" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_CostCodeWise_DDL_EPSWise");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "proj":
				compareProperty = "projId";
				codeProperty = "projName";
				$scope.tableHeading = "Project";
				if ($scope.subReport.code == "proj") {
					let projData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" },
						{ field: 'userIds.length', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "No of Employees", headerTooltip: "Number of Employees" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, projData, data,"Reports_Manpower_CostCodeWise_DDL_ProjWise");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "crew":
				compareProperty = "crewId";
				codeProperty = "crewName";
				$scope.tableHeading = "Crew";
				if ($scope.subReport.code == "crew") {
					let crewData = [
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
						{ field: 'code', displayName: "Crew Name", headerTooltip: "Crew Name" },
						{ field: 'userIds.length', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "No of Employees", headerTooltip: "Number of Employees" }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, crewData, data,"Reports_Manpower_CostCodeWise_DDL_CrewWise");
					$scope.gridOptions.showColumnFooter = true;
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			default:
				$scope.manpowerList = [];
				$scope.type = '';
				let columnDefs = [
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name" },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
					{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name" },
					{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" }, { field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false },
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false },
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false },
					{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Category", visible: false },
					{ field: 'mobilizationDateStr', displayName: "Mobilisation Date", headerTooltip: "Mobilisation Date" },
					{ field: 'gender', displayName: "gender", headerTooltip: "Gender", visible: false },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group", visible: false },
					{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false },
					{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" },
					{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Desc", visible: false },
					{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor" },
					{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false },
					{ field: 'normalRate', displayName: "Normal Rate", headerTooltip: "Normal Rate" },
					{ field: 'idleRate', displayName: "Idle Rate", headerTooltip: "Idle Rate", visible: false },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_CostCodeWise_DDL");
				$scope.getManpowercostcodeDetails();
				$scope.gridOptions.gridMenuCustomItems = false;
		}
		const subReportMap = new Array();
		for (const manPower of $scope.manpowerList) {
			const key = manPower[compareProperty];
			if (!subReportMap[key]) {
				if ($scope.subReport.code === "costcode") {
					subReportMap[key] = {
						'code': manPower[codeProperty],
						'parentCostCode': manPower.parentCostCode,
						'parentCostCodeName': manPower.parentCostCodeName,
						'costCodeDesc': manPower.costCodeDesc,
						'costCodeName': manPower.costCodeName,
						'userIds': new Array(),
						'projName':manPower.projName

					};
				} else {
					subReportMap[key] = {
						'code': manPower[codeProperty],
						'value': new Array(),
						'userIds': new Array(),
						'projName':manPower.projName
					};
				}
			}
			if (subReportMap[key].userIds.indexOf(manPower.userId) === -1) {
				subReportMap[key].userIds.push(manPower.userId);
			}
		}
		setGraphData(subReportMap);
	};

	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		let obj = null;
		$scope.subReportData = new Array();
		$scope.totalEmployees = 0;
		for (const key in subReportMap) {
			obj = subReportMap[key];
			$scope.labels.push(obj.code);
			$scope.data.push(obj.userIds.length);
			$scope.totalEmployees += obj.userIds.length;
			$scope.subReportData.push(obj);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		initGraph();
	}

	$scope.clearSubReportDetails = function () {
		$scope.manpowerList = [];
		$scope.type = "";
		$scope.subReport.name = null;
	};

	function initGraph() {
		chartService.defaultPieInit();
	};

	$scope.selectType = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.subReport.name == null) {
			GenericAlertService.alertMessage("Please select subReport", 'Warning');
			return;
		}
	}

	$scope.getManpowercostcodeDetails = function () {
		if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.date == null || $scope.date == undefined) {
			GenericAlertService.alertMessage("Please select the Date", 'Warning');
			return;
		}
		var req = {
			"projIds": $scope.selectedProjIds,
			"fromDate": $scope.date,
		}
		ManpowerReportService.getManpowerCostCodeWiseReport(req).then(function (data) {
			$scope.manpowerList = data;
			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			}
			for (let manpower of $scope.manpowerList) {
				manpower.mobilizationDateStr = moment(manpower.mobilizationDate).format('DD-MMM-YYYY');
			}
			$scope.gridOptions1.data = angular.copy($scope.manpowerList);
			if ($scope.manpowerList.length <= 0) {
				GenericAlertService.alertMessage("Manpower Cost Code Wise Daily-Deployment List not available for the selected Search Criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting manpower CostCode Wise details", 'Error');
		});
	};

	$scope.resetManpowerCostCodeData = function () {
		$scope.manpowerList = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = [];
		$scope.data = [];
		$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");
		$scope.labels = [];
		$scope.type = '';
		$scope.subReport = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
				{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" }, { field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" },
				{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name", visible: false, groupingShowAggregationMenu: false },
				{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", visible: false, groupingShowAggregationMenu: false },
				{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade", visible: false, groupingShowAggregationMenu: false },
				{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Category", visible: false, groupingShowAggregationMenu: false },
				{ field: 'mobilizationDateStr', displayName: "Mobilisation Date", headerTooltip: "Mobilisation Date", groupingShowAggregationMenu: false },
				{ field: 'gender', displayName: "gender", headerTooltip: "Gender", visible: false, groupingShowAggregationMenu: false },
				{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false },
				{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false, groupingShowAggregationMenu: false },
				{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
				{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", visible: false, groupingShowAggregationMenu: false },
				{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor", groupingShowAggregationMenu: false },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false, groupingShowAggregationMenu: false },
				{ field: 'normalRate', displayName: "Normal Rate", headerTooltip: "Normal Rate", groupingShowAggregationMenu: false },
				{ field: 'idleRate', displayName: "Idle Rate", headerTooltip: "Idle Rate", visible: false, groupingShowAggregationMenu: false },

			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_CostCodeWise_DDL");
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
			template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowercostcodewisehelp.html',
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
