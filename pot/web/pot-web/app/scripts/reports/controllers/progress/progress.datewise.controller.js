'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("datewiseprogress", {
		url: '/datewiseprogress',
		data: {
			progress: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/progress/progressdatewise.html',
				controller: 'ProgressDateWiseController'
			}
		}
	})
}]).controller("ProgressDateWiseController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "CostCodeMultiSelectFactory", "SOWMultiSelectFactory", "$filter", "progressReportService", "dateGroupingService", "stylesService", 'ngGridService', 'chartService', function ($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory,
	CostCodeMultiSelectFactory, SOWMultiSelectFactory, $filter, progressReportService, dateGroupingService, stylesService, ngGridService, chartService) {
	chartService.getChartMenu($scope);
	$scope.stylesSvc = stylesService;
	$scope.selectedProjIds = [];
	$scope.selectedCostCodeIds = [];
	$scope.selectedSOWIds = [];
	$scope.yAxislabels = "Hours";
	$scope.subReport = 'None';
	$scope.subReportCode = "";
	$scope.labels = [];
	$scope.data = [];
	$scope.subReports = [{
		name: 'Item of Work Wise Progress',
		code: "summary"
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
		name: 'Periodical Progress',
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
		var costCodeReq = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		var costCodePopUp = CostCodeMultiSelectFactory.getMultiProjCostCodes(costCodeReq);
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
			GenericAlertService.alertMessage("Please select project to get SOW Items", 'Warning');
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
			GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
		});
	};

	function noSubReportData() {
		let columnDefs = [
			{ field: 'workDairyDate', displayName: "Date", headerTooltip: "Date" },
			{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name" },
			{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
			{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "CostCode Sub Group Name", visible: false },
			{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
			{ field: 'sowDesc', displayName: "SOW Desc", headerTooltip: "SOW Item Description" },
			{ field: 'parentSoeDesc', displayName: "SOE Sub Group Name", headerTooltip: "SOE Sub Group Name" },
			{ field: 'soeDesc', displayName: "SOE Desc", headerTooltip: "SOE Item Description" },
			{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
			{ field: 'currentValue', displayName: "Qty", headerTooltip: "Quantity" }
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
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
			$scope.getDateWiseProgressDetails();
		}
	};
	function prepareSubReport() {
		$scope.subReportData = [];
		if ($scope.subReport.code == "summary" || $scope.subReport.code == "costcode") {
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
				{ field: 'valueCount', displayName: "Prog Qty", headerTooltip: "Progress Quantity" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "summary") {
			let summaryData = [
				{ field: 'mapValue', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Prog Qty", headerTooltip: "Progress Quantity" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "proj") {
			let summaryData = [
				{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Item Name" },
				{ field: 'mapValue', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Prog Qty", headerTooltip: "Progress Quantity" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "eps") {
			let summaryData = [
				{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'costName', displayName: "Cost Code Name", headerTooltip: "Cost Code Item Name" },
				{ field: 'mapValue', displayName: "SOW Sub Group Name", headerTooltip: "SOW Sub Group Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'valueCount', displayName: "Prog Qty", headerTooltip: "Progress Quantity" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		for (let progress of $scope.progressDetails) {
			let mapKey = progress[key];
			let mapValue = progress[value];
			let costName=progress.costName;
			if (!subReportMap[mapKey]) {
				let valueCount = 0;
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"unitOfMeasure": progress["unitOfMeasure"],
					"valueCount": valueCount,
					"costName":progress["costName"]
				};
				if ($scope.subReport.code == "costcode") {
					subReportMap[mapKey].parentCostName = progress["parentCostName"];
					subReportMap[mapKey].costName = progress["costName"];
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
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data = valueArr;
		initGraph();
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
				{ field: 'valueCount', displayName: "Prog Qty", headerTooltip: "Progress Quantity" }
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
		const periodicalMap = new Array();
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
					let valueCount = 0;
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"unitOfMeasure": progress["unitOfMeasure"],
						"parentSoeDesc": progress["parentSoeDesc"],
						"valueCount": valueCount
					};
				}
				subReportMap[mapKey].valueCount += parseFloat(progress["currentValue"]);
			}
		}
		setGraphData(subReportMap);
	};
	$scope.getDateWiseProgressDetails = function () {
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
			"toDate": $scope.toDate
		};
		progressReportService.getProgressDateWiseRecords(req).then(function (data) {
			$scope.progressDetails = data;
			$scope.gridOptions.data = angular.copy($scope.progressDetails);
			if ($scope.progressDetails.length <= 0) {
				GenericAlertService.alertMessage("Datewise Progress Report not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  progress details", 'Error');
		});
		initGraph();
	};
	var series = ['Progress Quantity'];
	function initGraph() {
		$scope.series = series;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit($scope.yAxislabels);
	};
	$scope.clearSubReportDetails = function () {
		$scope.subReport = 'None';
		$scope.progressDetails = [];
		$scope.type = "";
		$scope.subReportCode = "";
		$scope.subReportName = "";
	};
	$scope.resetProgressDetails = function () {
		$scope.subReport = 'None';
		$scope.progressDetails = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = null;
		$scope.type = '';
		$scope.sowNamedisplay = null;
		$scope.costCodeNameDisplay = null;
		$scope.periodicSubReportCode = "";
		$scope.subReportName = null;
		$scope.subReportCode = null;
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};

	$scope.exportData = function () {
		alasql('SELECT * INTO XLSX("john.xlsx",{headers:true}) FROM ?', [$scope.progressDetails]);
	};

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'workDairyDate', displayName: "Date", headerTooltip: "Date" },
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false },
				{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
				{ field: 'sowDesc', displayName: "SOW Desc", headerTooltip: "SOW Item Description" },
				{ field: 'parentSoeDesc', displayName: "SOE Sub Group Name", headerTooltip: "SOE Sub Group Name" },
				{ field: 'soeDesc', displayName: "SOE Desc", headerTooltip: "SOE Item Description" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
				{ field: 'currentValue', displayName: "Qty", headerTooltip: "Quantity" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
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
			template: 'views/help&tutorials/reportshelp/progresshelp/datewiseprogresshelp.html',
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
