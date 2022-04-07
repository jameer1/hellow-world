'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("periodicalprogressclaimactualcost", {
		url: '/periodicalprogressclaimactualcost',
		data: {
			progress: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/progress/progressclaimactualcostreport.html',
				controller: 'ProgressClaimActualCostReportController'
			}
		}
	})
}]).controller("ProgressClaimActualCostReportController", ["$scope", "$state", "$q", "ngDialog", "EpsProjectSelectFactory", "GenericAlertService", "$filter", "ProjPMCPReportService", "stylesService", 'ngGridService', 'chartService', function ($scope, $state, $q, ngDialog, EpsProjectSelectFactory, GenericAlertService,
	$filter, ProjPMCPReportService, stylesService, ngGridService, chartService) {
	/*
	chartService.getChartMenu($scope);
	$scope.stylesSvc = stylesService;
	$scope.progressDetails = [];
	$scope.selectedProjIds = [];
	$scope.subReport = "None";
	$scope.maxAmount=0;
	$scope.labels = [];
	$scope.data = [];

	$scope.claimedAmountGrandTotal = 0;
	$scope.prevGrandTotal = 0;
	$scope.currentGrandTotal = 0;

	$scope.yAxislabels = 'Claimed Amount';
	var series = ['PrevAmnt','ReprtAmnt'];
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
	*/
	//----
	//$scope.maxAmount=10000000;
	$scope.yAxislabels = 'Cost';
	$scope.stylesSvc = stylesService;
	chartService.getChartMenu($scope);
	//$scope.yAxislabels = 'Amount';
	//var series = ['PrevPrdAmt', 'RptPrdAmt', 'UptoDateAmt'];
	var series = ['PrevAmnt', 'ReprtAmnt'];
	$scope.data = [];
	$scope.labels = [];
	$scope.type = 'chartTable';
	$scope.subReports = [{
		name: 'CostCodeWise Actual Cost Report',
		code: "costcode"
	}, {
		name: 'ProjectWise Actual Cost Report',
		code: "proj"
	}, {
		name: 'EPSWise Actual Cost Report',
		code: "eps"
	}];
	$scope.subReport = "None";
	$scope.subReportCode = "";
	$scope.dateActualDetails = [];
	//$scope.dateActualDetailsSub = [];

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
	$scope.contractType = "CPPTypecontract";

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
	function noSubReportData() {

		let columnDefs = [
			{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id" },
			{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
			{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id" },
			{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Desc", headerTooltip: "Cost Code Sub Group Description" },
			{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Item Id" },
			{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
			{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" },
			{ field: 'budget', displayName: "Budget", headerTooltip: "Budget" },
			{ field: 'prevManpowerCost', displayName: "Prev Manpower Cost", headerTooltip: "prevManpowerCost" },
			{ field: 'prevPlantCost', displayName: "PrevPlantCost", headerTooltip: "PrevPlantCost" },
			{ field: 'prevMaterialCost', displayName: "PrevMaterialCost", headerTooltip: "PrevMaterialCost" },
			{ field: 'reportingManpowerCost', displayName: "ReportingManpowerCost", headerTooltip: "ReportingManpowerCost" },
			{ field: 'reportingPlantCost', displayName: "ReportingPlantCost", headerTooltip: "ReportingPlantCost" },
			{ field: 'reportingMaterialCost', displayName: "ReportingMaterialCost", headerTooltip: "ReportingMaterialCost" },
			{ field: 'uptoDateManpowerCost', displayName: "UptoDateManpowerCost", headerTooltip: "UptoDateManpowerCost" },
			{ field: 'uptoDatePlantCost', displayName: "UptoDatePlantCostCost", headerTooltip: "UptoDatePlantCostCost" },
			{ field: 'uptoDateMaterialCost', displayName: "UptoDateMaterialCostCost", headerTooltip: "UptoDateMaterialCostCost" }
		]
		let data = [];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
		$scope.gridOptions.gridMenuCustomItems = false;
	}
	$scope.changeSubReport = function () {
		if ($scope.subReport && $scope.subReport != "None") {
			$scope.type = 'chartTable';
			$scope.subReportName = $scope.subReport.name;
			$scope.subReportCode = $scope.subReport.code
			prepareSubReport();
		} else {
			$scope.plantPeriodicalDetails = [];
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
			generateSubReportData("costItemId", "costItemCode");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		}
	};
	function generateSubReportData(key, value) {
		//let manpowerArr = [], plantArr = [], materialArr = [], serviceArr = [];
		/*let totalPrevCostArr = [],totalReportCostArr = [], totalPrevEarnedArr = [],totalPrevPlannedArr = [],
								totalReportEarnedArr = [],totalReportPlannedArr = []; */
		let budgetArr = [], totalPreviousAmountArr = [], totalReportingAmountArr = [], totalUptoDateAmountArr = [];
		let subReportMap = [];
		if ($scope.subReport.code == "costcode") {
			let costCodeData = [
				//{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Name" },
				//{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				//{ field: 'currentDate', displayName: "currentDate", headerTooltip: "currentDate" },
				{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" },
				{ field: 'budget', displayName: "Budget Cost", headerTooltip: "Budget Cost" },
				{ field: 'totalPreviousAmount', displayName: "Total Previous Amount", headerTooltip: "Total Previous Amount" },
				{ field: 'totalReportingAmount', displayName: "Total Reporting Amount", headerTooltip: "Total Reporting Amount" },
				{ field: 'totalUptoDateAmount', displayName: "Total UptoDate Amount", headerTooltip: "Total UptoDate Amount" }
				//{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" },
				//{ field: 'totalPrevCost', displayName: "TotalPrevCost", headerTooltip: "TotalPrevCost" },
				//{ field: 'totalPrevEarned', displayName: "TotalPrevEarned", headerTooltip: "TotalPrevEarned" },
				//{ field: 'totalPrevPlanned', displayName: "TotalPrevPlanned", headerTooltip: "TotalPrevPlanned" },
				//{ field: 'totalReportCost', displayName: "TotalReportCost", headerTooltip: "TotalReportCost" }
				//{ field: 'totalReportEarned', displayName: "TotalReportEarned", headerTooltip: "TotalReportEarned" },
				//{ field: 'totalReportPlanned', displayName: "TotalReportPlanned", headerTooltip: "TotalReportPlanned" }
				/*
				{ field: 'costName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
				{ field: 'currency', displayName: "Currency", headerTooltip: "Currency" },
				{
					name: 'manpowerCost',
					cellTemplate: '<div>{{row.entity.manpowerCost | number : 2}}</div>',
					displayName: "Manpower Cost", headerTooltip: "Manpower Cost"
				},
				{
					name: 'plantCost',
					cellTemplate: '<div>{{row.entity.plantCost | number : 2}}</div>',
					displayName: "Plant Cost", headerTooltip: "Plant Cost"
				},
				{
					name: 'materialCost',
					cellTemplate: '<div>{{row.entity.materialCost | number : 2}}</div>',
					displayName: "Material Cost", headerTooltip: "Material Cost"
				},
				{
					name: 'serviceCost',
					cellTemplate: '<div>{{row.entity.serviceCost | number : 2}}</div>',
					displayName: "Service Cost", headerTooltip: "Service Cost"
				},
				{
					name: 'totalCost',
					cellTemplate: '<div>{{row.entity.totalCost | number : 2}}</div>',
					displayName: "Total Cost", headerTooltip: "Total Cost"
				}
				*/
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "proj") {
			let projData = [
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Name" },
				{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'budget', displayName: "Budget Cost", headerTooltip: "Budget Cost" },
				{ field: 'totalPreviousAmount', displayName: "Total Previous Amount", headerTooltip: "Total Previous Amount" },
				{ field: 'totalReportingAmount', displayName: "Total Reporting Amount", headerTooltip: "Total Reporting Amount" },
				{ field: 'totalUptoDateAmount', displayName: "Total UptoDate Amount", headerTooltip: "Total UptoDate Amount" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, projData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}

		if ($scope.subReport.code == "eps") {
			let epsData = [
				{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name" },
				{ field: 'budget', displayName: "Budget Cost", headerTooltip: "Budget Cost" },
				{ field: 'totalPreviousAmount', displayName: "Total Previous Amount", headerTooltip: "Total Previous Amount" },
				{ field: 'totalReportingAmount', displayName: "Total Reporting Amount", headerTooltip: "Total Reporting Amount" },
				{ field: 'totalUptoDateAmount', displayName: "Total UptoDate Amount", headerTooltip: "Total UptoDate Amount" }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, epsData, data);
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		for (let plantDtl of $scope.dateActualDetails) {
			let mapKey = plantDtl[key];
			let mapValue = plantDtl[value];
			if (!subReportMap[mapKey]) {
				subReportMap[mapKey] = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"currentDate": plantDtl["currentDate"],
					"budget": 0,
					"totalPreviousAmount": 0,
					"totalReportingAmount": 0,
					"totalUptoDateAmount": 0
				};
				if (key == "costItemId") {
					subReportMap[mapKey].epsName = plantDtl["epsName"];
					subReportMap[mapKey].projName = plantDtl["projName"];
					subReportMap[mapKey].costName = plantDtl["costItemName"];
				}
				if (key == "projId") {
					subReportMap[mapKey].epsName = plantDtl["epsName"];
				}
			}
			subReportMap[mapKey].budget += plantDtl.budget;
			subReportMap[mapKey].totalPreviousAmount += plantDtl.prevManpowerCost + plantDtl.prevPlantCost + plantDtl.prevMaterialCost;
			subReportMap[mapKey].totalReportingAmount += plantDtl.reportingManpowerCost + plantDtl.reportingPlantCost + plantDtl.reportingMaterialCost;
			subReportMap[mapKey].totalUptoDateAmount += plantDtl.prevManpowerCost + plantDtl.prevPlantCost + plantDtl.prevMaterialCost;
		}
		for (const index in subReportMap) {

			budgetArr.push(fixedDecimal(subReportMap[index].budget));
			totalPreviousAmountArr.push(fixedDecimal(subReportMap[index].totalPreviousAmount));
			totalReportingAmountArr.push(fixedDecimal(subReportMap[index].totalReportingAmount));
			totalUptoDateAmountArr.push(fixedDecimal(subReportMap[index].totalUptoDateAmount));

			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
			//}
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		//$scope.data.push(budgetArr);
		$scope.data.push(totalPreviousAmountArr);
		$scope.data.push(totalReportingAmountArr);
		//$scope.data.push(totalUptoDateAmountArr);
		console.log('totalUptoDateAmountArr');
		console.log(totalUptoDateAmountArr);
		//var maxValue = Math.max(totalUptoDateAmountArr);
		//$scope.maxAmount = maxValue;
		//console.log('Max :'+maxValue);
		var maxValue = Math.max.apply(null, totalUptoDateAmountArr);
		$scope.maxAmount = maxValue;
		console.log('Max :' + maxValue);

		initGraph();
	};
	function fixedDecimal(value) {
		return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
	}
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
	function initGraph1() {
		console.log("initGraph")
		$scope.series = series;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit($scope.yAxislabels);
		$scope.options = {
			colors: [{
				backgroundColor: '#FF6384',
				pointBackgroundColor: '#FF6384'
			}, {
				backgroundColor: '#36A2EB',
				pointBackgroundColor: '#36A2EB'
			}],
			showTooltips: true,
			tooltipFillColor: '#EEE',
			tooltipFontColor: '#000',
			tooltipFontSize: 12,
			maintainAspectRatio: false,
			responsive: true,
			responsiveAnimationDuration: 1000,
			scaleFontColor: 'red',
			tooltips: {
				//mode: 'single',
				enabled: true,
				//displayColors: false,
				xPadding: 5,
				yPadding: 5
			},
			/*layout: {
							padding: {
								left: 50,
								right: 0,
								top: 0,
								bottom: 0
							}
						},*/
			legend: {
				display: true,
				position: 'top',
				labels: {
					//boxWidth: 50,
					fontColor: 'black',
					padding: 60
				}
			},

			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						precision: 0,
						fontSize: 10,
						beginAtZero: true,
						steps: 10,
						stepValue: Math.ceil($scope.maxAmount / 10),
						max: $scope.maxAmount //{{maxAmount}}
						//padding: 5,
						//backdropPaddingY: 20
						//	/*beginAtZero: true*/
						//	//backdropPaddingY: 20
					},
					scaleLabel: {
						display: true,
						labelString: 'Cost',
						fontColor: "red"
					}
				}]

			}
			};
			/*
			title: {
				display: false,
				text: 'Cost Code Wise Progress Claim Report'
			} */
			//,
			//barThickness: 20
		}
		/*$scope.options = {
					title: {
						display: false,
						text: 'Progress Measure Based on Contract Milestones - '+$scope.subReportName

					},
					legend: {
						display: true,
						position: 'top'
					},
					scales: {
						xAxes: [{
							display: true,
							stacked: true,
						}],
						yAxes: [{
							display: false,
							stacked: false,

						}],

					}
				};
				*/
	

	//$scope.getDatewiseactualDetails = function () {
	$scope.getProgressClaimDetails = function () {
		//$scope.resetReport();
		// $scope.clearSubReportDetails();
		if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select Project", 'Warning');
			return;
		}

		$scope.selectedCostCodeIds = [];
		$scope.category = [];
		$scope.selectedCompanyIds = [];
		// $scope.fromDate
		var req = {
			"projIds": $scope.selectedProjIds,
			"costcodeIds": $scope.selectedCostCodeIds,
			"categories": $scope.category,
			"cmpIds": $scope.selectedCompanyIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate
		};

		//BudgetService.getCostCodeActualDetailsReport(req).then(function (data) {
		ProjPMCPReportService.getReportPMCPDetails(req).then(function (data) {

			console.log('getReportPMCPDetails data');
			console.log(data);


			//$scope.dateActualDetails = data.costReportResps;
			$scope.dateActualDetails = data.periodCostTOs;
			console.log('$scope.dateActualDetails');
			console.log($scope.dateActualDetails);

			//$scope.dateActualDetailsSub = data.periodCostTOs;

			$scope.gridOptions1.data = angular.copy(data.periodCostTOs);
			if ($scope.subReport && $scope.subReport != "None") {
				prepareSubReport();
			}
			if ($scope.dateActualDetails.length <= 0) {
				GenericAlertService.alertMessage("Details are not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting Date Wise Actual details", 'Error');
		});
		initGraph();
	};
	$scope.clearSubReportDetails = function () {
		$scope.dateActualDetails = [];
		//$scope.dateActualDetailsSub = [];
		$scope.subReportName = "";
		$scope.subReport = "None";
		$scope.type = "";
		$scope.subReportCode = "";


		$scope.data = [];
		//$scope.gridOptions= [];
		noSubReportData();
		//$scope.searchProject = {};
		//$scope.type = '';
		//$scope.subReportName = '';
		// $scope.subReportCode = '';
		//$scope.selectedProjIds = [];

		// $scope.subReportName = null;
		//$scope.subReport = "None";
		//$scope.type = '';

	};
	$scope.resetDateWiseActualDetails = function () {
		$scope.dateActualDetails = [];
		//$scope.dateActualDetailsSub = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = [];
		$scope.companyNameDisplay = null;
		$scope.costCodeNameDisplay = null;
		$scope.selectedCostCodeIds = [];
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.subReportName = null;
		$scope.subReport = "None";
		$scope.type = '';
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions1.data = [];
		}
	};
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id" },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false },
				{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Desc", headerTooltip: "Cost Code Sub Group Description", visible: false },
				{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Item Id" },
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
				{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" },
				{ field: 'budget', displayName: "Budget Cost", headerTooltip: "Budget Cost" },
				{ field: 'prevManpowerCost', displayName: "Prev Manpower Cost", headerTooltip: "prevManpowerCost" },
				{ field: 'prevPlantCost', displayName: "PrevPlantCost", headerTooltip: "PrevPlantCost" },
				{ field: 'prevMaterialCost', displayName: "PrevMaterialCost", headerTooltip: "PrevMaterialCost" },
				{ field: 'reportingManpowerCost', displayName: "ReportingManpowerCost", headerTooltip: "ReportingManpowerCost" },
				{ field: 'reportingPlantCost', displayName: "ReportingPlantCost", headerTooltip: "ReportingPlantCost" },
				{ field: 'reportingMaterialCost', displayName: "ReportingMaterialCost", headerTooltip: "ReportingMaterialCost" },
				{ field: 'uptoDateManpowerCost', displayName: "UptoDateManpowerCost", headerTooltip: "UptoDateManpowerCost" },
				{ field: 'uptoDatePlantCost', displayName: "UptoDatePlantCostCost", headerTooltip: "UptoDatePlantCostCost" },
				{ field: 'uptoDateMaterialCost', displayName: "UptoDateMaterialCostCost", headerTooltip: "UptoDateMaterialCostCost" }

				/*
					{ field: 'date', displayName: "Date", headerTooltip: "Date" },
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
					{ field: 'cmpName', displayName: "Company", headerTooltip: "Parent Company Name", visible: false },
					{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false },
					{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Desc", headerTooltip: "Cost Code Sub Group Description", visible: false },
					{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Item Id", visible: false },
					{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
					{ field: 'resourceId', displayName: "Resource", headerTooltip: "Resource Id" },
					{ field: 'empFirstName', displayName: "First Name", headerTooltip: "Employee First Name", visible: false },
					{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Employee Last Name", visible: false },
					{ field: 'category', displayName: "Category", headerTooltip: "Category", visible: false },
					{ field: 'type', displayName: "Type", headerTooltip: "Actual Hours Type" },
					{ field: 'plantDesc', displayName: "Plant Desc", headerTooltip: "Plant Description AsPerCompany", visible: false },
					{ field: 'plantRegNumber', displayName: "Reg.Num", headerTooltip: "Plant Registration Number", visible: false },
					{ field: 'plantMake', displayName: "Make", headerTooltip: "Plant Make", visible: false },
					{ field: 'plantModel', displayName: "Model", headerTooltip: "Plant Model", visible: false },
					{ field: 'plantRateType', displayName: "Rate Type", headerTooltip: "Plant Rate Type", visible: false },
					{ field: 'materialSubGroupCode', displayName: "Material Sub Group Id", headerTooltip: "Material Sub Group Id", visible: false },
					{ field: 'materialSubGroupName', displayName: "Material Sub Group Name", headerTooltip: "Material Sub Group Name", visible: false },
					{ field: 'invoiceNumber', displayName: "Invoice Num", headerTooltip: "Invoice Number", visible: false },
					{ field: 'invoiceDate', displayName: "Invoice Date", headerTooltip: "Invoice Date", visible: false },
					{ field: 'ratePerUnit', displayName: "Rate/Unit", headerTooltip: "Rate PerUnit" },
					{ field: 'unitOfMesure', displayName: "Units", headerTooltip: "Unit OfMeasure", visible: false },
					{ field: 'quantity', displayName: "Qty", headerTooltip: "Quantity" },
					{ field: 'costAmount', displayName: "Amt", headerTooltip: "Amount" },
					{ field: 'currencyCode', displayName: "Currency", headerTooltip: "Currency" }
					*/
			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data);
		}
	});

	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function (data) {

		}, function (error) {
			GenericAlertService.alertMessage("Error", 'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/reportshelp/costperformancehelp/costdatewiseactualhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
	$scope.resetReport = function () {
		$scope.dateActualDetails = [];
		$scope.data = [];
		$scope.searchProject = {};
		//$scope.type = '';
		$scope.subReportName = '';
		$scope.subReportCode = '';
		$scope.selectedProjIds = [];

		// $scope.subReportName = null;
		$scope.subReport = "None";
		$scope.type = '';
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}

		$scope.pmFromStatusDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.$watch('pmFromStatusDate', function () {
			$scope.clearSubReportDetails();
		});
		$scope.pmStatusDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.$watch('pmStatusDate', function () {
			$scope.clearSubReportDetails();
		});
	};
}]);
