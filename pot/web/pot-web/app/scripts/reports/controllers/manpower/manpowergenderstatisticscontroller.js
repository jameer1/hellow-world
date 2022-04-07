'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("genderstats", {
		url: '/genderstats',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowergenderstatistics.html',
				controller: 'ManpowerGenderStatisticsController'
			}
		}
	})
}]).controller("ManpowerGenderStatisticsController", ["$scope", "uiGridGroupingConstants","$q", "ngDialog","$filter", "generalservice", "ManpowerReportService", "CompanyMultiSelectFactory", "GenericAlertService", "EpsProjectMultiSelectFactory", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($scope, uiGridGroupingConstants,$q, ngDialog, $filter, generalservice,
	ManpowerReportService, CompanyMultiSelectFactory, GenericAlertService, EpsProjectMultiSelectFactory, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'NUMBER OF EMPLOYEES';
	$scope.stylesSvc = stylesService;
	$scope.manpowerList = [];
	$scope.selectedProjIds = [];
	$scope.selectedCompanyIds = [];
	$scope.categoryName = [];
	$scope.subReport = {
		name: null
	}
	$scope.category = {
		name: null
	}

	$scope.date = new Date();
	var defaultDate = new Date($scope.date);
	$scope.reportDate = $filter('date')((defaultDate), "dd-MMM-yyyy");

	$scope.$watch('reportDate', function () {
		$scope.clearSubReportDetails();
	})
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

	$scope.percentage = function (gender, type) {
		if (type === 'male') {
			var total = parseInt(gender.male) + parseInt(gender.female);
			return (parseInt(gender.male) / total) * 100;
		}
		if (type === 'female') {
			var total = parseInt(gender.male) + parseInt(gender.female);
			return (parseInt(gender.female) / total) * 100;
		}
		if (type === 'local') {
			var total = parseInt(gender.local) + parseInt(gender.nonLocal);
			return (parseInt(gender.local) / total) * 100;
		}
		if (type === 'nonlocal') {
			var total = parseInt(gender.local) + parseInt(gender.nonLocal);
			return (parseInt(gender.nonLocal) / total) * 100;
		}
	};

	$scope.total = function (gender, type) {
		var total = 0;
		total = parseInt(gender.male) + parseInt(gender.female);
		if (type === 'totalPercntage') {
			var totalTrade = 0;
			angular.forEach($scope.manpowerList, function (value, key) {
				totalTrade = totalTrade + (parseInt(value.displayNamesMap.male)
					+ parseInt(value.displayNamesMap.female));
			})
			return (total / totalTrade) * 100;
		}
		return total;
	};
	$scope.gendrSubReports = [{
		name: 'Local & NonLocal Ratio',
		code: "statistics"
	}, {
		name: 'Trade Wise Male/Female Count',
		code: "tradegender"
	}, {
		name: 'EPS Wise Male/Female Count',
		code: "epsgender"
	}, {
		name: 'Project Wise Male/Female Count',
		code: "projgender"
	}, {
		name: 'Trade Wise Local-NonLocal Count',
		code: "tradelocal"
	}, {
		name: 'Project Wise Local-NonLocal Count',
		code: "projlocal"
	}, {
		name: 'EPS Wise Local-NonLocal Count',
		code: "epslocal"
	}];

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
	function genderSeries() {
		$scope.series = ['Female', 'Male'];
	}

	function localSeries() {
		$scope.series = ['Local', 'Non-Local'];
	}

	$scope.changeSubReport = function (selectedSubReport) {
		$scope.type = 'chartTable';
		let compareProperty;
		let codeProperty;
		let valuePropertyOne;
		let valuePropertyTwo;
		switch ($scope.subReport.code) {
			case "tradelocal":
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				valuePropertyOne = 'localEmp';
				valuePropertyTwo = 'nonLocalEmp';
				localSeries();
				$scope.tableHeading = "Trade";
				if ($scope.subReport.code == "tradelocal") {
					let manPowerData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name", groupingShowAggregationMenu: false },
						{ field: 'valueOne', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', groupingShowAggregationMenu: false },
						{ field: 'valueTwo', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local' , groupingShowAggregationMenu: false},
						{ field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Total', headerTooltip: 'Total' , groupingShowAggregationMenu: false},
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "tradegender":
				compareProperty = "empClassId";
				codeProperty = "empClassName";
				valuePropertyOne = 'femaleCount';
				valuePropertyTwo = 'maleCount';
				$scope.tableHeading = "Trade";
				genderSeries();
				if ($scope.subReport.code == "tradegender") {
					let manPowerData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" },
						{ field: 'valueOne', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', groupingShowAggregationMenu: false },
						{ field: 'valueTwo', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local' , groupingShowAggregationMenu: false},
						{ field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Total', headerTooltip: 'Total', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "projlocal":
				compareProperty = "projId";
				codeProperty = "projName";
				valuePropertyOne = 'localEmp';
				valuePropertyTwo = 'nonLocalEmp';
				$scope.tableHeading = "Project";
				localSeries();
				if ($scope.subReport.code == "projlocal") {
					let manPowerData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
						{ field: 'valueOne', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Female' : 'Local' , groupingShowAggregationMenu: false},
						{ field: 'valueTwo', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local' , groupingShowAggregationMenu: false},
						{ field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Total', headerTooltip: 'Total', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "projgender":
				compareProperty = "projId";
				codeProperty = "projName";
				valuePropertyOne = 'femaleCount';
				valuePropertyTwo = 'maleCount';
				$scope.tableHeading = "Project";
				genderSeries();
				if ($scope.subReport.code == "projgender") {
					let manPowerData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
						{ field: 'valueOne', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Female' : 'Local' , groupingShowAggregationMenu: false},
						{ field: 'valueTwo', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', groupingShowAggregationMenu: false },
						{ field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Total', headerTooltip: 'Total' , groupingShowAggregationMenu: false},
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "epsgender":
				compareProperty = "parentProjId";
				codeProperty = "parentProjName";
				valuePropertyOne = 'femaleCount';
				valuePropertyTwo = 'maleCount';
				$scope.tableHeading = "EPS";
				genderSeries();
				if ($scope.subReport.code == "epsgender") {
					let manPowerData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" },
						{ field: 'valueOne', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Female' : 'Local' , groupingShowAggregationMenu: false},
						{ field: 'valueTwo', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local' , groupingShowAggregationMenu: false},
						{ field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Total', headerTooltip: 'Total', groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "epslocal":
				compareProperty = "parentProjId";
				codeProperty = "parentProjName";
				valuePropertyOne = 'localEmp';
				valuePropertyTwo = 'nonLocalEmp';
				$scope.tableHeading = "EPS";
				localSeries();
				if ($scope.subReport.code == "epslocal") {
					let manPowerData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
						{ field: 'valueOne', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Female' : 'Local', groupingShowAggregationMenu: false },
						{ field: 'valueTwo', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local', headerTooltip: $scope.subReport.code.includes('gender') ? 'Male' : 'Non-Local' , groupingShowAggregationMenu: false},
						{ field: 'totalValue', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Total', headerTooltip: 'Total' , groupingShowAggregationMenu: false},
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case "statistics":
				const subReportMap = new Object();
				subReportMap.local = {
					'code': 'Local',
					'value': 0,
					'percentage': 0
				};
				subReportMap.nonLocal = {
					'code': 'Non-Local',
					'value': 0,
					'percentage': 0
				};
				$scope.data = new Array();
				$scope.subReportData = new Array();
				$scope.labels = new Array();
				for (const detail of $scope.manpowerList) {
					subReportMap.local.value += detail.countsObj.localEmp;
					subReportMap.nonLocal.value += detail.countsObj.nonLocalEmp;
				}
				subReportMap.local.percentage = (subReportMap.local.value /
					(subReportMap.local.value + subReportMap.nonLocal.value)) * 100;
				subReportMap.nonLocal.percentage = (subReportMap.nonLocal.value /
					(subReportMap.local.value + subReportMap.nonLocal.value)) * 100;
				let obj;
				localSeries();

				$scope.total = { 'totalEmployee': 0 };
				for (const key in subReportMap) {
					obj = subReportMap[key];
					$scope.labels.push(obj.code);
					$scope.data.push(obj.value);
					$scope.total.totalEmployee += obj.value;
					$scope.subReportData.push(obj);
				}

				initGraph('pie');
				if ($scope.subReport.code == "statistics") {
					let manPowerData = [
						{ field: 'code', displayName: "Local or NonLocal Category", headerTooltip: "Local or NonLocal Category" , groupingShowAggregationMenu: false},
						{ field: 'value', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: 'Number of Employees', headerTooltip: 'Number of Employees' , groupingShowAggregationMenu: false},
						{ field: 'percentage', displayName: 'Percentage(%)', headerTooltip: 'Percentage(%)', cellFilter: 'number:2' , groupingShowAggregationMenu: false},
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Gender_Statistics");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				$scope.gridOptions.data = angular.copy($scope.subReportData);
				return;
			default:
				$scope.manpowerDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				let columnDefs = [
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade ID" , groupingShowAggregationMenu: false},
					{ field: 'classificationPerClient', displayName: "Classification As Per Client", headerTooltip: "Classification As Per Client", groupingShowAggregationMenu: false },
					{ field: 'classificationPerUnion', displayName: "Classification As Per Union", headerTooltip: "Classification As Per Union" , groupingShowAggregationMenu: false},
					{
						name: 'totalNo',
						cellTemplate: '<div>{{ row.entity.countsObj.femaleCount + row.entity.countsObj.maleCount }}</div>',
						aggregationType: uiGridConstants.aggregationTypes.sum,
						displayName: "Total No's", headerTooltip: "Total No", visible: false, groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
					},
					{
						name: 'totalNoPer',
						cellTemplate: '<div>{{((row.entity.countsObj.femaleCount + row.entity.countsObj.maleCount) /'
							+ ' grid.appScope.totalEmployees ) * 100 | number:2}}</div>',
						aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', 
						displayName: "Total %", headerTooltip: "Total No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
					},

					{ field: 'countsObj.maleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Male No's", headerTooltip: "Male No", visible: false , groupingShowAggregationMenu: false},
					{
						name: 'maleNoPer',
						cellTemplate: '<div>{{(row.entity.countsObj.maleCount / ' +
							'(row.entity.countsObj.femaleCount + row.entity.countsObj.maleCount)) * 100 | number:2}}</div>',
						aggregationType: uiGridConstants.aggregationTypes.sum,
						displayName: "Male %", headerTooltip: "Male No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
					},

					{ field: 'countsObj.femaleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Female No's", headerTooltip: "Female No", visible: false , groupingShowAggregationMenu: false},
					{
						name: 'femaleNoPer',
						cellTemplate: '<div>{{(row.entity.countsObj.femaleCount /'
							+ '(row.entity.countsObj.femaleCount + row.entity.countsObj.maleCount)) * 100 | number:2}}</div>',
						aggregationType: uiGridConstants.aggregationTypes.sum,
						displayName: "Female %", headerTooltip: "Female No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
					},
					{ field: 'countsObj.localEmp', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Local No's", headerTooltip: "Local No", visible: false, groupingShowAggregationMenu: false },
					{
						name: 'localNoPer',
						cellTemplate: '<div>{{(row.entity.countsObj.localEmp / '
							+ '(row.entity.countsObj.localEmp + row.entity.countsObj.nonLocalEmp)) * 100 | number:2}}</div>',
						aggregationType: uiGridConstants.aggregationTypes.sum,
						displayName: "Local %", headerTooltip: "Local No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
					},
					{ field: 'countsObj.nonLocalEmp', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Local No's", headerTooltip: "Non Local No", visible: false, groupingShowAggregationMenu: false },
					{
						name: 'nonLocalNoPer',
						cellTemplate: '<div>{{(row.entity.countsObj.nonLocalEmp / '
							+ '(row.entity.countsObj.localEmp + row.entity.countsObj.nonLocalEmp)) * 100  | number:2}}</div>',
						aggregationType: uiGridConstants.aggregationTypes.sum,
						displayName: "Non Local %", headerTooltip: "Non Local No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prev Qty", headerTooltip: "Previous Period Quantity", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
					},
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Gender_Statistics");
				$scope.getManpowerDetails();
				$scope.gridOptions.gridMenuCustomItems = false;
		}
		const subReportMap = new Array();
		for (const manPower of $scope.manpowerList) {
			if (!subReportMap[manPower[compareProperty]]) {
				subReportMap[manPower[compareProperty]] = {
					'code': manPower[codeProperty],
					'valueOne': 0,
					'valueTwo': 0,
				};
			}
			subReportMap[manPower[compareProperty]].valueOne += manPower.countsObj[valuePropertyOne];
			subReportMap[manPower[compareProperty]].valueTwo += manPower.countsObj[valuePropertyTwo];
		}
		setGraphData(subReportMap);
	}

	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		let obj = null;
		const valueOne = new Array();
		const valueTwo = new Array();
		$scope.subReportData = new Array();
		$scope.total = { 'valueOneTotal': 0, 'valueTwoTotal': 0 };
		for (const key in subReportMap) {
			obj = subReportMap[key];
			valueOne.push(obj.valueOne);
			$scope.total.valueOneTotal += obj.valueOne;
			valueTwo.push(obj.valueTwo);
			$scope.total.valueTwoTotal += obj.valueTwo;
			$scope.labels.push(obj.code);
			$scope.subReportData.push(obj);
		}
		for (let manpower of $scope.subReportData) {
			manpower.totalValue = manpower.valueOne + manpower.valueTwo;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(valueOne);
		$scope.data.push(valueTwo);
		initGraph('bar');
	}

	function initGraph(chartType) {
		$scope.chart_type = chartType;
		if (chartType != 'pie') {
			chartService.defaultBarInit($scope.yAxislabels);
		}
		else
			chartService.defaultPieInit();
	}
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
	$scope.getManpowerDetails = function () {
		if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select the Project", 'Warning');
			return;
		}
		if ($scope.selectedCompanyIds.length <= 0 || $scope.selectedCompanyIds == undefined || $scope.selectedCompanyIds == null) {
			GenericAlertService.alertMessage("Please select the Company", 'Warning');
			return;
		}
		if ($scope.categoryName == '') {
			GenericAlertService.alertMessage("Please select the Category", 'Warning');
			return;
		}
		if ($scope.reportDate == null || $scope.reportDate == undefined) {
			GenericAlertService.alertMessage("Please select the Date", 'Warning');
			return;
		}
		const req = {
			"projIds": $scope.selectedProjIds,
			"cmpIds": $scope.selectedCompanyIds,
			"categories": $scope.categoryName,
			"date": $scope.reportDate,
		}
		ManpowerReportService.getManpowerGenderStatisticsReport(req).then(function (data) {
			$scope.totalEmployees = 0;
			$scope.manpowerList = data;
			$scope.gridOptions1.data = angular.copy($scope.manpowerList);
			for (const emp of data) {
				let countsObj = {};
				countsObj.maleCount = 0;
				countsObj.femaleCount = 0;
				countsObj.localEmp = 0;
				countsObj.nonLocalEmp = 0;
				for (const detail of emp.empDetails) {
					if (detail.gender === 'Male') {
						countsObj.maleCount++;
					} else {
						countsObj.femaleCount++
					}

					if (detail.localEmployee) {
						countsObj.localEmp++;
					} else {
						countsObj.nonLocalEmp++;
					}
				}
				$scope.totalEmployees += emp.empDetails.length;
				emp.countsObj = countsObj;
			}

			for (let manpower of $scope.manpowerList) {
				manpower.totalNo = manpower.countsObj.femaleCount + manpower.countsObj.maleCount;
				manpower.totalNoPer = (manpower.totalNo / $scope.totalEmployees) * 100;
				manpower.totalNoPer = parseFloat(manpower.totalNoPer).toFixed(2);
				manpower.maleNoPer = (manpower.countsObj.maleCount / manpower.totalNo) * 100;
				manpower.maleNoPer = parseFloat(manpower.maleNoPer).toFixed(2);
				manpower.femaleNoPer = (manpower.countsObj.femaleCount / manpower.totalNo) * 100;
				manpower.femaleNoPer = parseFloat(manpower.femaleNoPer).toFixed(2);
				manpower.localNoPer = (manpower.countsObj.localEmp / (manpower.countsObj.localEmp + manpower.countsObj.nonLocalEmp)) * 100;
				manpower.localNoPer = parseFloat(manpower.localNoPer).toFixed(2);
				manpower.nonLocalNoPer = (manpower.countsObj.nonLocalEmp / (manpower.countsObj.localEmp + manpower.countsObj.nonLocalEmp)) * 100;
				manpower.nonLocalNoPer = parseFloat(manpower.nonLocalNoPer).toFixed(2);
			}

			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			}
			$scope.gridOptions1.data = angular.copy($scope.manpowerList);
			if ($scope.manpowerList.length <= 0) {
				GenericAlertService.alertMessage("Manpower Gender Statistics not available for the selected Search Criteria", 'Warning');
			}

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting manpower gender details", 'Error');
		});
	};

	$scope.clearSubReportDetails = function () {
		$scope.manpowerList = [];
		$scope.labels = [];
		$scope.data = [];
		$scope.series = [];
		$scope.type = '';
		$scope.subReport = null;
	};

	$scope.resetGenderStatistics = function () {
		$scope.manpowerList = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = [];
		$scope.selectedCompanyIds = [];
		$scope.companyNameDisplay = null;
		$scope.reportDate = $filter('date')((defaultDate), "dd-MMM-yyyy");
		$scope.categoryName = null;
		$scope.type = '';
		$scope.subReport = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
				{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade ID" , groupingShowAggregationMenu: false},
				{ field: 'classificationPerClient', displayName: "Classification As Per Client", headerTooltip: "Classification As Per Client" , groupingShowAggregationMenu: false},
				{ field: 'classificationPerUnion', displayName: "Classification As Per Union", headerTooltip: "Classification As Per Union" , groupingShowAggregationMenu: false},
				{
					field: 'totalNo', aggregationType: uiGridConstants.aggregationTypes.sum,
					displayName: "Total No's", headerTooltip: "Total No", visible: false, groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					field: 'totalNoPer', aggregationType: uiGridConstants.aggregationTypes.sum,
					displayName: "Total %", headerTooltip: "Total No %", footerCellFilter: 'number:2' , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},

				{ field: 'countsObj.maleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Male No's", headerTooltip: "Male No", visible: false, groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
				{
					field: 'maleNoPer', aggregationType: uiGridConstants.aggregationTypes.sum,
					displayName: "Male %", headerTooltip: "Male No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},

				{ field: 'countsObj.femaleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Female No's", headerTooltip: "Female No", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				{
					field: 'femaleNoPer', aggregationType: uiGridConstants.aggregationTypes.sum,
					displayName: "Female %", headerTooltip: "Female No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{ field: 'countsObj.localEmp', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Local No's", headerTooltip: "Local No", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum,treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				{
					field: 'localNoPer', aggregationType: uiGridConstants.aggregationTypes.sum,
					displayName: "Local %", headerTooltip: "Local No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{ field: 'countsObj.nonLocalEmp', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Local No's", headerTooltip: "Non Local No", visible: false , groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				{
					field: 'nonLocalNoPer', aggregationType: uiGridConstants.aggregationTypes.sum,
					displayName: "Non Local %", headerTooltip: "Non Local No %", groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Gender_Statistics");
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
			template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowergenderstathelp.html',
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
