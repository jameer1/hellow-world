'use strict';

// to inject url for our screen
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("progressplanvsactual", {
		url: '/progressplanvsactual',
		data: {
			progress: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/progress/progressplannedvsactual.html',
				controller: 'ProgressPlannedvsActualController'
			}
		}
	})
}]).controller("ProgressPlannedvsActualController", ["$scope", "$filter", "$state", "$q", "ngDialog","EpsProjectMultiSelectFactory", "GenericAlertService", "SOWMultiSelectFactory", "progressReportService", "ResourceCurveService", "ProjectScheduleService", "SchedulePlannedValueService", "dateGroupingService", "stylesService", 'ngGridService', 'chartService', function ($scope, $filter, $state,$q, ngDialog, EpsProjectMultiSelectFactory, GenericAlertService,
	SOWMultiSelectFactory, progressReportService, ResourceCurveService, ProjectScheduleService, SchedulePlannedValueService, dateGroupingService, stylesService, ngGridService, chartService) {
	chartService.getChartMenu($scope);
	$scope.stylesSvc = stylesService;
	$scope.yAxislabels = 'Hours';
	$scope.selectedProjIds = [];
	$scope.selectedSOWIds = [];
	$scope.labels = [];
	$scope.data = [];
	var series = ['Actual Amount', 'Planned Amount'];
    var series1 = ['Actual Amount', 'Planned Amount', 'Cum Actual Amount', 'Cum Plan Amount'];
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
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Info');
			return;
		}
	};
	$scope.subReport = 'None';
	$scope.subReportCode = "";
	$scope.subReports = [{
		name: 'Overall-Progress Summary',
		code: "summary"
	}, {
		name: 'Item Name Wise-Progress Report',
		code: "name"
	}, {
		name: 'Cost Code wise-Progress Quantity',
		code: "costcode"
	}, {
		name: 'Project wise-Progress Quantity',
		code: "proj"
	}, {
		name: 'EPS wise Progress Quantity',
		code: "eps"
	}, {
		name: 'Periodical Report',
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
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	$scope.getSOWItems = function () {
		if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select project to get SOW Items", 'Info');
			return;
		}
		var sowReq = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		var SOWItemPopUp = SOWMultiSelectFactory.getMultiProjSow(sowReq);
		SOWItemPopUp.then(function (data) {
			$scope.sowNameDisplay = data.selectedSOWs.sowNameDisplay;
			$scope.selectedSOWIds = data.selectedSOWs.sowIds;
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
		});
	};
	$scope.clearSubReportDetails = function () {
		$scope.subReport = 'None';
		$scope.progressDetails = [];
		$scope.type = "";
		$scope.subReportCode = "";
		$scope.subReportName = "";
	};
	$scope.resetReport = function () {
		$scope.subReport = 'None';
		$scope.progressDetails = [];
		$scope.data = [];
		$scope.searchProject = {};
		$scope.type = '';
		$scope.sowNameDisplay = null;
		$scope.fromDate = null;
		$scope.toDate = null;
		$scope.subReportName = null;
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	function noSubReportData() {
		let columnDefs = [
			{ field: 'workDairyDate', displayName: "Date", headerTooltip: "Date" },
			{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name" },
			{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
			{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
			{ field: 'sowDesc', displayName: "SOW Desc", headerTooltip: "SOW Item Description" },
			{ field: 'parentSoeDesc', displayName: "SOE Sub Group Name", headerTooltip: "SOE Sub Group Name" },
			{ field: 'soeDesc', displayName: "SOE Desc", headerTooltip: "SOE Item Description" },
			{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
			{ field: 'currentValue', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
			{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
		$scope.gridOptions.gridMenuCustomItems = false;
	}

	$scope.changeSubReport = function (selectedSubReport) {
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
			$scope.getPlannedActualProgressDetails();
		}
	};

	function prepareSubReport() {
		$scope.labels = [];
		$scope.subReportData = [];
		$scope.data = [];
		if ($scope.subReport.code == "summary" || $scope.subReport.code == "name") {
			generateSubReportData("parentSoeId", "parentSoeDesc");
		} else if ($scope.subReport.code == "costcode") {
			generateSubReportData("parentSoeId", "parentSoeDesc");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		} else if ($scope.subReport.code == "periodical") {
			if ($scope.periodicSubReport && $scope.periodicSubReport.code) {
				$scope.changePeriodicSubReport();
			}
		}
	};

	function generateSubReportData(key, value) {
		let subReportMap = [];

		if ($scope.subReport.code == "costcode") {
			let costCodeData = [
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" },
				{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Item Name" },
				{ field: 'mapValue', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "summary" || $scope.subReport.code == "name") {
			let summaryData = [
				{ field: 'mapValue', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "proj") {
			let summaryData = [
				{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'mapValue', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" },
				{ field: 'valueCount', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "eps") {
			let summaryData = [
				{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		for (let progress of $scope.progressDetails) {
			let mapKey = progress[key];
			let mapValue = progress[value];
			if (!subReportMap[mapKey]) {
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"unitOfMeasure": progress["unitOfMeasure"],
					"valueCount": 0,
					"plannedValue": 0
				};
				if ($scope.subReport.code == "costcode") {
					subReportMap[mapKey].parentCostName = progress["parentCostName"];
					subReportMap[mapKey].costName = progress["costName"];
				}
			}
			subReportMap[mapKey].valueCount += parseFloat(progress["currentValue"]);
			subReportMap[mapKey].plannedValue += parseFloat(progress["plannedValue"]);
		}
		setGraphData(subReportMap);
	};

	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		const valueArr = new Array();
		const plannedArr = new Array();
		$scope.subReportData = new Array();
		for (const index in subReportMap) {
			valueArr.push(subReportMap[index].valueCount);
			plannedArr.push(subReportMap[index].plannedValue);
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(valueArr);
		$scope.data.push(plannedArr);
		initGraph(series);
	};

	$scope.changePeriodicSubReport = function () {
		$scope.type = 'chartTable';
		let groupByFunction = null;
		let dateFormat;
		let reportSettingProp;

		if ($scope.subReport.code == "periodical") {
			let periodicalData = [
				{ field: 'mapValue', displayName: "Period -" + $scope.periodicSubReport.name },
				{ field: 'parentSoeDesc', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'cumulativeActual', displayName: "Cum Actual", headerTooltip: "Cum Actual", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
	            { field: 'cumulativePlanned', displayName: "Cum Plan", headerTooltip: "Cum Plan", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }		
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, periodicalData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		switch ($scope.periodicSubReport.code) {
			case "daily":
				groupByFunction = dateGroupingService.groupByDate;
				dateFormat = "dd-MMM-yyyy";
				break;
			case "weekly":
				groupByFunction = dateGroupingService.groupByWeek;
				dateFormat = "dd-MMM-yyyy";
				reportSettingProp = 'Monday';
				break;
			case "monthly":
				groupByFunction = dateGroupingService.groupByMonth;
				dateFormat = "MMM-yyyy";
				reportSettingProp = '';
				break;
			case "yearly":
				groupByFunction = dateGroupingService.groupByYear;
				dateFormat = "yyyy";
				reportSettingProp = '';
				break;
			default:
				break;
		}
		// Set date property
		for (const progress of $scope.progressDetails) {
			progress.date = progress['workDairyDate'];
		}
		// groupedList contains group and respective values
		const groupedList = groupByFunction($scope.progressDetails, reportSettingProp);
		const subReportMap = new Array();
		for (const group of groupedList) {
			const mapKey = group.date;
			const mapValue = $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
			// Iterate every group and add the values as required
			for (const progress of group.values) {
				if (!subReportMap[mapKey]) {
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"unitOfMeasure": progress["unitOfMeasure"],
						"parentSoeDesc": progress["parentSoeDesc"],
						"valueCount": 0,
						"plannedValue": 0
					};
				}
				subReportMap[mapKey].valueCount += parseFloat(progress["currentValue"]);
				subReportMap[mapKey].plannedValue += parseFloat(progress["plannedValue"]);
			}
		}
		setGraphData1(subReportMap);
	};
	function setGraphData1(subReportMap) {
		series=series1;	
		$scope.labels = new Array();
		$scope.data = new Array();		
		let cumulativeActualValue = 0;	
		let cumulativePlannedValue = 0;
			for (const index in subReportMap) {
			cumulativeActualValue += subReportMap[index].valueCount;
			cumulativePlannedValue += subReportMap[index].plannedValue;
					subReportMap[index].cumulativeActual = cumulativeActualValue;
					subReportMap[index].cumulativePlanned = cumulativePlannedValue;
			}
		const valueArr = new Array();
		const plannedArr = new Array();
		const valueArrCum = new Array();
		const plannedArrCum = new Array();
		$scope.subReportData = new Array();
		for (let plan of $scope.subReportData) {
				plan.valueCount = parseFloat(plan.valueCount).toFixed(2);
				plan.plannedValue = parseFloat(plan.plannedValue).toFixed(2);
				plan.cumulativePlanned = parseFloat(plan.cumulativePlanned).toFixed(2);
				plan.cumulativeActual = parseFloat(plan.cumulativeActual).toFixed(2);
			}
		$scope.subReportData = new Array();
		for (const index in subReportMap) {
			valueArr.push(subReportMap[index].valueCount);
			plannedArr.push(subReportMap[index].plannedValue);
			valueArrCum.push(subReportMap[index].cumulativeActual);
			plannedArrCum.push(subReportMap[index].cumulativePlanned);
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(valueArr);
		$scope.data.push(plannedArr);
		$scope.data.push(valueArrCum);
		$scope.data.push(plannedArrCum);
		initGraph(series);
	};
	$scope.getPlannedActualProgressDetails = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
			return;
		}
		if ($scope.selectedSOWIds.length <= 0) {
			GenericAlertService.alertMessage("Please select SOW items to fetch report", 'Info');
			return;
		}
		
		var req = {
			"projIds": $scope.selectedProjIds,
			"sowIds": $scope.selectedSOWIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate
		};
		$scope.progressDetails = [];
		$scope.gridOptions1.data = [];
		progressReportService.getProgressActualRecords(req).then(function (data) {
		console.log(data);
			$scope.progressDetails = data.sort(function (a, b) {
														var dateA = new Date(a.workDairyDate), dateB = new Date(b.workDairyDate)
														return dateA - dateB
														});
			console.log($scope.progressDetails);
			$scope.gridOptions1.data = angular.copy($scope.progressDetails);
			if ($scope.progressDetails.length <= 0) {
				GenericAlertService.alertMessage("Progress Planned vs Actual Reports not available for the search criteria", 'Info');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  Plant details", 'Error');
		});
		initGraph();
	};

	function getDates(startDate, endDate) {
		let dates = [];
		let currentDate = startDate;
		var addDays = function (days) {
			var date = new Date(this.valueOf());
			date.setDate(date.getDate() + days);
			return date;
		};
		while (currentDate <= endDate) {
			dates.push($filter('date')(angular.copy(currentDate), "dd-MMM-yyyy"));
			currentDate = addDays.call(currentDate, 1);
		}
		return dates;
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
				{ field: 'workDairyDate', displayName: "Date", headerTooltip: "Date" },
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
				{ field: 'sowDesc', displayName: "SOW Desc", headerTooltip: "SOW Item Description" },
				{ field: 'parentSoeDesc', displayName: "SOE Sub Group Name", headerTooltip: "SOE Sub Group Name" },
				{ field: 'soeDesc', displayName: "SOE Desc", headerTooltip: "SOE Item Description" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'currentValue', displayName: "Act Qty", headerTooltip: "Actual Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'plannedValue', displayName: "Plan Qty", headerTooltip: "Planned Quantity", cellFilter: 'number: 0', cellClass: "justify-right", headerCellClass: "justify-right" }
			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data);
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
			template: 'views/help&tutorials/reportshelp/progresshelp/progressplanvsactualhelp.html',
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
