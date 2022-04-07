'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("periodicalprogressclaim", {
		url: '/periodicalprogressclaim',
		data: {
			progress: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/progress/progressclaimreport.html',
				controller: 'ProgressClaimReportController'
			}
		}
	})
}]).controller("ProgressClaimReportController", ["$scope", "$state","$q", "ngDialog", "EpsProjectMultiSelectFactory","EpsProjectSelectFactory", "GenericAlertService", "$filter", "progressReportService", "stylesService", 'ngGridService', 'chartService', function ($scope, $state,$q, ngDialog, EpsProjectMultiSelectFactory,EpsProjectSelectFactory, GenericAlertService,
	$filter, progressReportService, stylesService, ngGridService, chartService) {
	chartService.getChartMenu($scope);
	$scope.stylesSvc = stylesService;
	$scope.progressDetails = [];
	$scope.selectedProjIds = [];
	$scope.subReport = "None";
	$scope.labels = [];
	$scope.data = [];
	var series = ["Previous Period Amount", "Reporting Period Amount"];
	$scope.subReports = [{
		name: 'Cost code wise',
		code: "costcode"
	}, {
		name: 'Project Wise',
		code: "proj"
	}, {
		name: 'EPS Wise',
		code: "eps"
	}];
	$scope.subContractList = [{
		id: "1",
		code: "hc",
		name: "Head Company",
		value: "Head-Company"
	}, {
		id: "2",
		code: "sc",
		name: "Sub Company",
		value: "Sub-Contract"
	}];
	// Removing Report column - separation of pages individually
	/*$scope.reportTypes = [{
		code: "SOR",
		name: "Schedule Of Rates",
	}, {
		code: "Cost",
		name: "Actual Cost",
	}, {
		code: "Milestones",
		name: "Lumpsum Contract with Milestones",
	}];
	$scope.reportType = $scope.reportTypes[0];*/
	$scope.contract = $scope.subContractList[0];
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
	$scope.contractType = "SORcontract";
	
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjectsByContactType($scope.contractType);
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = [data.searchProject.projId];
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	$scope.getProgressClaimDetails = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select project to fetch report", 'Warning');
			return;
		}
		var req = {
			"projIds": $scope.selectedProjIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
			"contractType": $scope.contract.value
		}
		// if ($scope.reportType.code == $scope.reportTypes[0].code) {
			progressReportService.getSorProgressClaimRecords(req).then(function (data) {
				$scope.progressDetails = data;
				
				
				 for(var sample of $scope.progressDetails){
				sample.progAmt=(sample.prevValue * sample.sorTotal) + (sample.currentValue * sample.sorTotal);
			}
				 for(var sample of $scope.progressDetails){
				sample.progQty=sample.prevValue + sample.currentValue;
			}
			for(var sample of $scope.progressDetails){
				sample.repAmt=sample.currentValue * sample.sorTotal ;
			}	
				for(var sample of $scope.progressDetails){
				sample.prevAmt=sample.prevValue * sample.sorTotal ;
			}
				for(var sample of $scope.progressDetails){
				sample.estAmt=sample.sorQuantity * sample.sorTotal ;
			}
				
				
				$scope.gridOptions1.data = angular.copy($scope.progressDetails);
				if ($scope.progressDetails.length <= 0) {
					GenericAlertService.alertMessage("Progress Claim Report not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting  progress details", 'Error');
			});
		// }
		initGraph();
	};
	$scope.changeReport = function () {
		$scope.clearSubReportDetails();
		$scope.getProgressClaimDetails();
	};

	function noSubReportData() {
		let columnDefs = [
			{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name" },
			{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", visible: false },
			{ field: 'parentSorDesc', displayName: "SOR Sub Group Name", headerTooltip: "SOR Sub Group Name" },
			{ field: 'sorDesc', displayName: "SOR Desc", headerTooltip: "SOR Item Description", visible: false },
			{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false },
			{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
			{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false },
			{ field: 'sorQuantity', displayName: "Cont Qty", headerTooltip: "Contract Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
			{ field: 'sorTotal', displayName: "Unit Rate", headerTooltip: "Unit Rate", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
			{
				name: 'estAmt',
				cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{row.entity.sorQuantity * row.entity.sorTotal | number:2}}</div>',
				displayName: "Est Amt", headerTooltip: "Estimated Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
			},
			{ field: 'currency', displayName: "Curr", headerTooltip: "Currency" },
			{ field: 'prevValue', displayName: "Prev-Qty", headerTooltip: "Progress to Previous Period/ Month-Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
			{
				name: 'prevAmt',
				cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{row.entity.prevValue * row.entity.sorTotal | number:2}}</div>',
				displayName: "Prev-Amt", headerTooltip: "Progress to Previous Period/ Month-Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
			},
			{ field: 'currentValue', displayName: "Rep-Qty", headerTooltip: "Progress to Reporting Period-Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
			{
				name: 'repAmt',
				cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{row.entity.currentValue * row.entity.sorTotal | number: 2}}</div>',
				displayName: "Rep-Amt", headerTooltip: "Progress to Reporting Period-Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
			},
			{
				name: 'progQty',
				cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{row.entity.prevValue + row.entity.currentValue | number:2}}</div>',
				displayName: "Dt-Qty", headerTooltip: "Progress to Date-Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
			},
			{
				name: 'progAmt',
				cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{(row.entity.prevValue * row.entity.sorTotal) + (row.entity.currentValue * row.entity.sorTotal) | number: 2}}</div>',
				displayName: "Dt-Amt", headerTooltip: "Progress to Date-Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
			}
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
			$scope.getProgressClaimDetails();
		}
	};
	function prepareSubReport() {
		$scope.labels = [];
		$scope.subReportData = [];
		$scope.data = [];
		if ($scope.subReport.code == "costcode") {
			generateSubReportData("costId", "costName");
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
				{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id" },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" },
				{ field: 'costCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" },
				{ field: 'mapValue', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
				{ field: 'amount', displayName: "Est Amt", headerTooltip: "Estimated Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'currency', displayName: "Curr", headerTooltip: "Currency" },
				{ field: 'prevTotal', displayName: "Prev Amt", headerTooltip: "Previous period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'currentTotal', displayName: "Rep Amt", headerTooltip: "Reporting period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{
					field: 'total',
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.prevTotal + row.entity.currentTotal | number:2}}</div>',
					displayName: "Total Amt", headerTooltip: "Up to Date Period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "proj") {
			let summaryData = [
				{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'amount', displayName: "Est Amt", headerTooltip: "Estimated Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'currency', displayName: "Curr", headerTooltip: "Currency" },
				{ field: 'prevTotal', displayName: "Prev Amt", headerTooltip: "Previous period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'currentTotal', displayName: "Rep Amt", headerTooltip: "Reporting period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{
					field: 'total',
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.prevTotal + row.entity.currentTotal | number:2}}</div>',
					displayName: "Total Amt", headerTooltip: "Up to Date Period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "eps") {
			let summaryData = [
				{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'amount', displayName: "Est Amt", headerTooltip: "Estimated Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'currency', displayName: "Curr", headerTooltip: "Currency" },
				{ field: 'prevTotal', displayName: "Prev Amt", headerTooltip: "Previous period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'currentTotal', displayName: "Rep Amt", headerTooltip: "Reporting period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{
					field: 'total',
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{ row.entity.prevTotal + row.entity.currentTotal | number:2}}</div>',
					displayName: "Total Amt", headerTooltip: "Up to Date Period Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				}
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		for (let progress of $scope.progressDetails) {
			let mapKey = progress[key];
			let mapValue = progress[value];
			if (!subReportMap[mapKey]) {
				let valueCount = 0;
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"currency": progress["currency"],
					"amount": 0,
					"prevTotal": 0,
					"currentTotal": 0
				};
				if ($scope.subReport.code == "costcode") {
					subReportMap[mapKey].parentCostName = progress["parentCostName"];
					subReportMap[mapKey].parentCostCode = progress["parentCostCode"];
					subReportMap[mapKey].costCode = progress["costCode"];
				}
			}
			subReportMap[mapKey].amount = (progress["sorQuantity"] * progress["sorTotal"]);
			subReportMap[mapKey].prevTotal += parseFloat(progress["prevValue"]);
			subReportMap[mapKey].currentTotal += parseFloat(progress["currentValue"]);
		}
		setGraphData(subReportMap);
	};
	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		const prevArr = new Array();
		const currentArr = new Array();
		$scope.subReportData = new Array();
		for (const index in subReportMap) {
			prevArr.push(subReportMap[index].prevTotal);
			currentArr.push(subReportMap[index].currentTotal);
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(prevArr);
		$scope.data.push(currentArr);
		initGraph();
	};
	$scope.clearSubReportDetails = function () {
		$scope.subReport = 'None';
		$scope.progressDetails = [];
		$scope.type = "";
		$scope.subReportCode = "";
		$scope.subReportName = "";
	};
	$scope.resetReport = function () {
		$scope.progressDetails = [];
		$scope.data = [];
		$scope.searchProject = {};
		$scope.type = '';
		$scope.subReportName = '';
		$scope.subReportCode = '';
		$scope.selectedProjIds = [];
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};
	function initGraph() {
		$scope.series = series;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
	};
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", visible: false },
				{ field: 'parentSorDesc', displayName: "SOR Sub Group Name", headerTooltip: "SOR Sub Group Name" },
				{ field: 'sorDesc', displayName: "SOR Desc", headerTooltip: "SOR Item Description", visible: false },
				{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", visible: false },
				{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name" },
				{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", visible: false },
				{ field: 'sorQuantity', displayName: "Cont Qty", headerTooltip: "Contract Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{ field: 'sorTotal', displayName: "Unit Rate", headerTooltip: "Unit Rate", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{
					name: 'estAmt',
					displayName: "Est Amt", headerTooltip: "Estimated Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				},
				{ field: 'currency', displayName: "Curr", headerTooltip: "Currency" },
				{ field: 'prevValue', displayName: "Prev-Qty", headerTooltip: "Progress to Previous Period/ Month-Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{
					name: 'prevAmt',
					displayName: "Prev-Amt", headerTooltip: "Progress to Previous Period/ Month-Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				},
				{ field: 'currentValue', displayName: "Rep-Qty", headerTooltip: "Progress to Reporting Period-Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2' },
				{
					name: 'repAmt',
					displayName: "Rep-Amt", headerTooltip: "Progress to Reporting Period-Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				},
				{
					name: 'progQty',
					displayName: "Dt-Qty", headerTooltip: "Progress to Date-Quantity", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				},
				{
					name: 'progAmt',
					displayName: "Dt-Amt", headerTooltip: "Progress to Date-Amount", headerCellClass: "justify-right", cellClass: "justify-right", cellFilter: 'number: 2'
				}
			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data);
			$scope.gridOptions1.exporterPdfOrientation = 'landscape';
			$scope.gridOptions1.exporterPdfPageSize = 'A3';
			$scope.gridOptions1.exporterPdfMaxGridWidth = 820;
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
			template: 'views/help&tutorials/reportshelp/progresshelp/progressclaimhelp.html',
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
