'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("periodicalprogressclaimcontmilestone", {
		url: '/periodicalprogressclaimcontmilestone',
		data: {
			progress: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/progress/progressclaimcontractmilestonereport.html',
				controller: 'ProgressClaimContractMilestoneReportController'
			}
		}
	})
}])

	.controller("ProgressClaimContractMilestoneReportController", ["$scope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory","EpsProjectSelectFactory", "GenericAlertService", "$filter", "ProjPMCMReportService", "stylesService", 'ngGridService', 'chartService', function ($scope, $state, $q, ngDialog, EpsProjectMultiSelectFactory, EpsProjectSelectFactory,GenericAlertService,
		$filter, ProjPMCMReportService, stylesService, ngGridService, chartService) {

		chartService.getChartMenu($scope);
		$scope.stylesSvc = stylesService;
		$scope.progressDetails = [];
		$scope.selectedProjIds = [];
		$scope.subReport = "None";
		$scope.labels = [];
		$scope.data = [];
		var series = ['PrevAmnt', 'ReprtAmnt'];
		$scope.type = 'chartTable';
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
		$scope.subReport = "None";
		$scope.subReportCode = "";
		$scope.dateActualDetails = [];

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
		$scope.contractType = "LContractMile";
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
				"pmFromStatusDate": $scope.fromDate,
				"projStatusDate": $scope.toDate
			}
			ProjPMCMReportService.getReportPMCMDetails(req).then(function (data) {
				console.log("getReportPMCMDetails data");
				console.log(data); // projPMCMItemTOs
				console.log('projPMCMItemTOs');
				console.log(data.projPMCMItemTOs);
				$scope.progressDetails = data.projPMCMReportTOList;
				console.log("$scope.progressDetails");
				console.log($scope.progressDetails);
				$scope.gridOptions1.data = angular.copy(data.projPMCMReportTOList);
				console.log("$scope.gridOptions.data");
				console.log($scope.gridOptions.data);
				console.log(data);
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
			//   $scope.series = series;
			$scope.clearSubReportDetails();
			$scope.getProgressClaimDetails();
		};
		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", visible: true },
				{ field: 'projId', displayName: "Project Id", headerTooltip: "Project Id", visible: true },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", visible: true },
				{ field: 'code', displayName: "Milestone GroupID", headerTooltip: "Milestone GroupID" },
				{ field: 'name', displayName: "Milestone GroupName", headerTooltip: "Milestone GroupName", visible: true },
				{ field: 'pmStatusDate', displayName: "Status Date", headerTooltip: "Status Date", visible: true },
				{ field: 'pmCostCodeId', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", visible: true },
				{ field: 'pmCostCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", visible: true },
				{ field: 'pmCurrency', displayName: "Currency", headerTooltip: "Currency", visible: true },
				{ field: 'pmContractAmount', displayName: "Contract Amount", headerTooltip: "Contract Amount", visible: true },
				{ field: 'pmSchedFinishDate', displayName: "Schedule Finish Date", headerTooltip: "Schedule Finish Date", visible: true },
				{ field: 'pmProgressStatus', displayName: "Progress Status", headerTooltip: "Progress Status", visible: true },
				{ field: 'pmActualFinishDate', displayName: "Actual Finish Date", headerTooltip: "Actual Finish Date", visible: true },
				{ field: 'pmPrevProgClaim', displayName: "Included in Previous Progress Claim", headerTooltip: "Included in Previous Progress Claim", visible: true },
				{ field: 'pmClaimedAmount', displayName: "Claimed Amount", headerTooltip: "Claimed Amount", visible: true },
				{ field: 'prevValue', displayName: "Progress-Previous Period", headerTooltip: "Progress-Previous Period" },
				{ field: 'currentValue', displayName: "Progress-Reporting Period", headerTooltip: "Progress-Reporting Period" },
				{ field: 'comments', displayName: "Comments", headerTooltip: "Comments", visible: true }
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
			$scope.gridOptions.gridMenuCustomItems = false;
			console.log("this is data", data)
		}
		$scope.changeSubReport = function () {
			$scope.series = series;
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
			// $scope.series = series;
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "costcode") {
				generateSubReportData("pmCostCodeId", "pmCostCodeName");
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
					{ field: 'mapValue', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
					{ field: 'pmContractAmount', displayName: "Contract Amt", headerTooltip: "Contract Amt" },
					{ field: 'pmCurrency', displayName: "Currency", headerTooltip: "Currency" },
					{ field: 'prevTotal', displayName: "Prev Amt", headerTooltip: "Previous period Amount" },
					{ field: 'currentTotal', displayName: "Rep Amt", headerTooltip: "Reporting period Amount" },
					{
						field: 'total',
						cellTemplate: '<div>{{ row.entity.prevTotal + row.entity.currentTotal }}</div>',
						displayName: "Total Amt", headerTooltip: "Up to Date Period Amount"
					}

				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let summaryData = [
					{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name" },
					{ field: 'pmContractAmount', displayName: "Contract Amt", headerTooltip: "Contract Amt" },
					{ field: 'pmCurrency', displayName: "Currency", headerTooltip: "Currency" },
					{ field: 'prevTotal', displayName: "Prev Amt", headerTooltip: "Previous period Amount" },
					{ field: 'currentTotal', displayName: "Rep Amt", headerTooltip: "Reporting period Amount" },
					{
						field: 'total',
						cellTemplate: '<div>{{ row.entity.prevTotal + row.entity.currentTotal }}</div>',
						displayName: "Total Amt", headerTooltip: "Up to Date Period Amount"
					}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let summaryData = [
					{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name" },
					{ field: 'pmContractAmount', displayName: "Contract Amt", headerTooltip: "Contract Amt" },
					{ field: 'pmCurrency', displayName: "Currency", headerTooltip: "Currency" },
					{ field: 'prevTotal', displayName: "Prev Amt", headerTooltip: "Previous period Amount" },
					{ field: 'currentTotal', displayName: "Rep Amt", headerTooltip: "Reporting period Amount" },
					{
						field: 'total',
						cellTemplate: '<div>{{ row.entity.prevTotal + row.entity.currentTotal }}</div>',
						displayName: "Total Amt", headerTooltip: "Up to Date Period Amount"
					}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, summaryData, data);
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			console.log('$scope.progressDetails');
			console.log($scope.progressDetails);
			for (let progress of $scope.progressDetails) {
				let mapKey = progress[key];
				let mapValue = progress[value];
				if (!subReportMap[mapKey]) {
					let valueCount = 0;
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"pmCurrency": progress["pmCurrency"],
						"pmContractAmount": 0,
						"prevTotal": 0,
						"currentTotal": 0
					};
					if ($scope.subReport.code == "costcode") {
						subReportMap[mapKey].pmCostCodeName = progress["pmCostCodeName"];
						subReportMap[mapKey].pmCostCode = progress["pmCostCode"];
						subReportMap[mapKey].costCode = progress["costCode"];
					}
				}
				subReportMap[mapKey].pmContractAmount += parseFloat(progress["pmContractAmount"]);
				subReportMap[mapKey].prevTotal += parseFloat(progress["prevValue"]);
				subReportMap[mapKey].currentTotal += parseFloat(progress["currentValue"]);
			}
			console.log('$scope.claimedAmountGrandTotal :' + $scope.claimedAmountGrandTotal);
			console.log('$scope.prevGrandTotal :' + $scope.prevGrandTotal);
			console.log('$scope.currentGrandTotal :' + $scope.currentGrandTotal);

			setGraphData(subReportMap);
		};
		function setGraphData(subReportMap) {
			console.log("subReportMap")
			console.log(subReportMap)

			$scope.labels = new Array();
			$scope.data = new Array();
			const prevArr = new Array();
			const currentArr = new Array();
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				prevArr.push(fixedDecimal(subReportMap[index].prevTotal));
				currentArr.push(fixedDecimal(subReportMap[index].currentTotal));
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			console.log("$scope.gridOptions.data")
			console.log($scope.gridOptions.data)
			$scope.data.push(prevArr);
			$scope.data.push(currentArr);

			// var maxValue = Math.max.apply(null, pmContractAmountArr);
			// $scope.maxAmount = maxValue;
			// console.log('Max :' + maxValue);

			initGraph();
		};
		function fixedDecimal(value) {
			return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
		}
		$scope.clearSubReportDetails = function () {
			$scope.subReport = 'None';
			$scope.progressDetails = [];
			$scope.type = "";
			$scope.subReportCode = "";
			$scope.subReportName = "";
		};
		$scope.resetReport = function () {
			$scope.dateActualDetails = [];
			$scope.data = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.subReportName = '';
			$scope.subReportCode = '';
			$scope.selectedProjIds = [];
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
		function initGraph() {
			console.log("initGraph")
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			// chartService.defaultBarInit($scope.yAxislabels);

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
					enabled: true,
					xPadding: 5,
					yPadding: 5
				},
				legend: {
					display: true,
					position: 'top',
					labels: {
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
						stacked: false,
						ticks: {
							precision: 0,
							fontSize: 10,
							beginAtZero: true,
							steps: 10,
							stepValue: Math.ceil($scope.maxAmount / 10),
							max: $scope.maxAmount //{{maxAmount}}
						},
						scaleLabel: {
							display: true,
							labelString: 'Claimed Amount',
							fontColor: "red"
						}
					}]

				}
			}

		};

		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'epsCode', displayName: "EPS Code", headerTooltip: "EPS Code", visible: true },
					{ field: 'epsName', width:"150", displayName: "EPS Name", headerTooltip: "EPS Name", visible: true },
					{ field: 'projCode', displayName: "Project Code", headerTooltip: "Project Code", visible: true },
					{ field: 'projName', width:"150", displayName: "Project Name", headerTooltip: "Project Name", visible: true },
					{ field: 'parentName', displayName: "Milestone SubGroupName", headerTooltip: "Milestone SubGroupName", visible: true },
					{ field: 'code', displayName: "Milestone ID", headerTooltip: "Milestone ID", visible: true },
					{ field: 'name', displayName: "Milestone Description", headerTooltip: "Milestone Description", visible: true },
					{ field: 'pmStatusDate', width:"120", displayName: "Status Date", headerTooltip: "Status Date", visible: true },
					{ field: 'pmCostCodeId', width:"120",displayName: "Cost Code ID", headerTooltip: "Cost Code ID", visible: true },
					{ field: 'pmCostCodeName', width:"200", displayName: "Cost Code Description", headerTooltip: "Cost Code Description", visible: true },
					{ field: 'pmCurrency', displayName: "Currency", headerTooltip: "Currency", visible: true },
					{ field: 'pmContractAmount', displayName: "Contract Amount", headerTooltip: "Contract Amount", visible: true },
					{ field: 'pmSchedFinishDate', width:"120",displayName: "Schedule Finish Date", headerTooltip: "Schedule Finish Date", visible: false },
					{ field: 'pmProgressStatus', displayName: "Progress Status", headerTooltip: "Progress Status", visible: false },
					{ field: 'pmActualFinishDate',width:"120", displayName: "Actual Finish Date", headerTooltip: "Actual Finish Date", visible: false },
					{ field: 'pmPrevProgClaim', displayName: "Included in Previous Progress Claim", headerTooltip: "Included in Previous Progress Claim", visible: false },
					{ field: 'pmClaimedAmount', displayName: "Claimed Amount", headerTooltip: "Claimed Amount", visible: false },
					{ field: 'prevValue', displayName: "Progress-Previous Period", headerTooltip: "Progress-Previous Period" },
					{ field: 'currentValue', displayName: "Progress-Reporting Period", headerTooltip: "Progress-Reporting Period" },
					{
						name: 'upToDate', cellTemplate: '<div>{{row.entity.prevValue + row.entity.currentValue}}</div>',
						displayName: "Progress - To Date", headerTooltip: "Progress - To Date"
					},
					{ field: 'comments', width:"130", displayName: "Comments", headerTooltip: "Comments", visible: true }

				]
				let data = [];
				$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data);
				$scope.gridOptions1.exporterPdfOrientation = 'landscape';
				$scope.gridOptions1.exporterPdfPageSize = 'A3';
				$scope.gridOptions1.exporterPdfMaxGridWidth = 860;
			}
		});
		// class="chart-base"
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

