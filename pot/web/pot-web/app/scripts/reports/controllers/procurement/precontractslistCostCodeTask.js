'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("precontractslistCostCodeTask", {
		url: '/precontractslistCostCodeTask',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/procurement/precontractslistCostCodeTask.html',
				controller: 'precontractslistCostCodeTaskController'
			}
		}
	})
}]).controller("precontractslistCostCodeTaskController", ["$scope", "$filter", "$q", "$state", "ngDialog", "EmpAttendanceService", "GenericAlertService", "PreContractInternalService","EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "generalservice", "stylesService", "ngGridService", "chartService", "uiGridConstants",
	function ($scope, $filter, $q, $state, ngDialog, EmpAttendanceService, GenericAlertService,PreContractInternalService,
		EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {
		
	chartService.getChartMenu($scope);
		$scope.subReportCode = "";
		$scope.subReport = "None";
		$scope.stylesSvc = stylesService;
		$scope.selectedProjIds = '';
		$scope.yearTos = [];
		$scope.moreFlag = 0;
		$scope.empAttendenceDetails = [];
		$scope.selectedCrewIds = [];
		$scope.daysList = [];
		$scope.monthTOs = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		var currDate = new Date();
		var currYear = currDate.getFullYear();
		var futureYear = currYear + 5;
		var pastYear = currYear - 5;
		for (var i = pastYear; i <= futureYear; i++) {
			$scope.yearTos.push(i);
		}
		let totalValues = {
			pCount: 0,
			abCount: 0,
			phCount: 0,
			alCount: 0,
			lslCount: 0,
			slCount: 0,
			mlCount: 0,
			cblCount: 0,
			cslCount: 0,
			uplCount: 0
		};
    		
        $scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate(($scope.toDate.getDate()-90) - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");    
		/*var d = new Date();
		var month = $scope.monthTOs[d.getMonth()];
		$scope.month = $filter('date')((month), "MMM");

		var year = currYear;
		$scope.year = parseInt(year);*/

		$scope.totalValues = angular.copy(totalValues);
		var labels = [];
		let tempInitColList = null;
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
		
		$scope.clearSubReportDetails = function () {
			$scope.empAttendenceDetails = [];
			$scope.subReportName = "";
			$scope.subReport = "None";
			$scope.type = "";
			$scope.subReportCode = "";
		};
		$scope.tempCurrentCols = [];
		$scope.getSelectedMonthDays = function (monthDetails, year) {
			var req = {
				"status": 1,
				"attendenceMonth": monthDetails + "-" + year
			}
			EmpAttendanceService.getAttendanceDays(req).then(function (data) {
				$scope.daysList = data.attendenceDays;
				if (!tempInitColList)
					tempInitColList = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.columnDefs = angular.copy(tempInitColList);
				$scope.daysList.map(day => {
					let column = {};
					column.field = day;
					column.displayName = day;
					column.headerTooltip = day;
					column.visible = false;
					$scope.gridOptions.columnDefs.push(column);
				});
				$scope.initialColumnSet = $scope.gridOptions.columnDefs;
			})
		};

		$scope.subReports = [{
			name: 'Contract Type wise-Precontracts Budget Estimate',
			code: "direct"
		}, {
			name: 'Current Stage Wise-Precontracts Budget Estimate',
			code: "indirect"
		}];
		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Id" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'code', displayName: "Pre-Contract Id", headerTooltip: "Pre-ContractId" },
				{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description" },
				{ field: 'preContractType', displayName: "Contract type", headerTooltip: "ContractType" },
				{ field: 'precontractStage', displayName: "Pre-Contract Stage", headerTooltip: "Pre-Contract Stage" },
				{ field: 'budgetEstimate', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" },
				{ field: 'budgetEstimate', displayName: "Cost Code Description", headerTooltip: "Cost Code Description" },
				{ field: 'budgetEstimate', displayName: "Budget Estimate", headerTooltip: "Budget Estimate" },
				
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
				//$scope.empAttendenceDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getCostCodeReports();
			}
		};
		
		
	
		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];

			if ($scope.subReport.code == "direct" || $scope.subReport.code == "indirect") {
				const empCategory = $scope.subReport.code == "direct" ? generalservice.employeeCategory[0] : generalservice.employeeCategory[1];
				generateSubReportData("empCategory", empCategory, true);
			} else if ($scope.subReport.code == "trade") {
				generateSubReportData("empClassId", "tradeName");
			} else if ($scope.subReport.code == "cmp") {
				generateSubReportData("companyId", "companyName");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			}
		};

		function generateSubReportData(key, value, dayWise) {
			let pCountArr = [], abCountArr = [], phCountArr = [], alCountArr = [], lslCountArr = [], slCountArr = [], mlCountArr = [], cblCountArr = [], cslCountArr = [], uplCountArr = [];
			let subReportMap = [];
			if ($scope.subReport.code == "direct") {
				let costCodeData = [
					{ field: 'day', displayName: "Date", headerTooltip: "Date" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present" },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent" },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday" },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave" },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave" },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave" },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave" },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave" },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "indirect") {
				let costCodeData = [
					{ field: 'day', displayName: "Date", headerTooltip: "Date" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present" },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent" },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday" },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave" },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave" },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave" },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave" },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave" },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "trade") {
				let costCodeData = [
					{ field: 'day', displayName: "Trade", headerTooltip: "Trade Name" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present" },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent" },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday" },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave" },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave" },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave" },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave" },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave" },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "cmp") {
				let costCodeData = [
					{ field: 'day', displayName: "Company", headerTooltip: "Company Name" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present" },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent" },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday" },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave" },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave" },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave" },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave" },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave" },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "proj") {
				let costCodeData = [
					{ field: 'day', displayName: "Project", headerTooltip: "Project Name" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present" },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent" },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday" },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave" },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave" },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave" },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave" },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave" },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "eps") {
				let costCodeData = [
					{ field: 'day', displayName: "EPS", headerTooltip: "EPS Name" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present" },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent" },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday" },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave" },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave" },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave" },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave" },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave" },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			$scope.totalValues = angular.copy(totalValues);
			if (dayWise) {
				for (const day of $scope.daysList) {
					let pCount = 0, abCount = 0, phCount = 0, alCount = 0, lslCount = 0, slCount = 0, mlCount = 0, cblCount = 0, cslCount = 0, uplCount = 0;
					for (let empDtl of $scope.empAttendenceDetails) {
						if (empDtl.displayNamesMap[key] && value && empDtl.displayNamesMap[key].toLowerCase() == value.toLowerCase()
							&& empDtl.displayNamesMap[day]) {
							switch (empDtl.displayNamesMap[day]) {
								case "P":
									pCount++;
									$scope.totalValues.pCount++;
									break;
								case "AB":
									abCount++;
									$scope.totalValues.abCount;
									break;
								case "PH":
									phCount++;
									$scope.totalValues.phCount++;
									break;
								case "AL":
									alCount++
									$scope.totalValues.alCount++;
									break;
								case "LSL":
									lslCount++
									$scope.totalValues.lslCount++;
									break;
								case "SL":
									slCount++;
									$scope.totalValues.slCount++;
									break;
								case "ML":
									mlCount++;
									$scope.totalValues.mlCount++;
									break;
								case "CBL":
									cblCount++;
									$scope.totalValues.cblCount++;
									break;
								case "CSL":
									cslCount++;
									$scope.totalValues.cslCount++;
									break;
								case "UPL":
									uplCount++;
									$scope.totalValues.uplCount++;
									break;
								default:
									break;
							}
						}
					}
					for (let empAttendence of $scope.subReportData) {
						empAttendence.totalCount = empAttendence.pCount + empAttendence.abCount + empAttendence.phCount + empAttendence.alCount + empAttendence.lslCount + empAttendence.slCount + empAttendence.mlCount + empAttendence.cblCount + empAttendence.cslCount + empAttendence.cslCount + empAttendence.uplCount;
					}
					subReportMap[day] = {
						"day": day,
						"empCategory": value,
						"pCount": pCount,
						"abCount": abCount,
						"phCount": phCount,
						"alCount": alCount,
						"lslCount": lslCount,
						"slCount": slCount,
						"mlCount": mlCount,
						"cblCount": cblCount,
						"cslCount": cslCount,
						"uplCount": uplCount
					};
					pCountArr.push(pCount);
					abCountArr.push(abCount);
					phCountArr.push(phCount);
					alCountArr.push(alCount);
					lslCountArr.push(lslCount);
					slCountArr.push(slCount);
					mlCountArr.push(mlCount);
					cblCountArr.push(cblCount);
					cslCountArr.push(cslCount);
					uplCountArr.push(uplCount);
					$scope.labels.push(day);
					$scope.subReportData.push(subReportMap[day]);
				}
			} else {
				for (let empDtl of $scope.empAttendenceDetails) {
					const mapKey = empDtl.displayNamesMap[key];
					const mapValue = empDtl.displayNamesMap[value];
					if (!subReportMap[mapKey]) {
						subReportMap[mapKey] = {
							"day": mapValue,
							"mapKey": mapKey,
							"pCount": 0,
							"abCount": 0,
							"phCount": 0,
							"alCount": 0,
							"lslCount": 0,
							"slCount": 0,
							"mlCount": 0,
							"cblCount": 0,
							"cslCount": 0,
							"uplCount": 0
						};
					}
					for (const day of $scope.daysList) {
						if (empDtl.displayNamesMap[day]) {
							switch (empDtl.displayNamesMap[day]) {
								case "P":
									subReportMap[mapKey].pCount++;
									$scope.totalValues.pCount++;
									break;
								case "AB":
									subReportMap[mapKey].abCount++;
									$scope.totalValues.abCount++;
									break;
								case "PH":
									subReportMap[mapKey].phCount++;
									$scope.totalValues.phCount++;
									break;
								case "AL":
									subReportMap[mapKey].alCount++
									$scope.totalValues.alCount++;
									break;
								case "LSL":
									subReportMap[mapKey].lslCount++
									$scope.totalValues.lslCount++;
									break;
								case "SL":
									subReportMap[mapKey].slCount++;
									$scope.totalValues.slCount++;
									break;
								case "ML":
									subReportMap[mapKey].mlCount++;
									$scope.totalValues.mlCount++;
									break;
								case "CBL":
									subReportMap[mapKey].cblCount++;
									$scope.totalValues.cblCount++;
									break;
								case "CSL":
									subReportMap[mapKey].cslCount++;
									$scope.totalValues.cslCount++;
									break;
								case "UPL":
									subReportMap[mapKey].uplCount++;
									$scope.totalValues.uplCount++;
									break;
								default:
									break;
							}
						}
					}
				}
				for (const index in subReportMap) {
					pCountArr.push(subReportMap[index].pCount);
					abCountArr.push(subReportMap[index].abCount);
					phCountArr.push(subReportMap[index].phCount);
					alCountArr.push(subReportMap[index].alCount);
					lslCountArr.push(subReportMap[index].lslCount);
					slCountArr.push(subReportMap[index].slCount);
					mlCountArr.push(subReportMap[index].mlCount);
					cblCountArr.push(subReportMap[index].cblCount);
					cslCountArr.push(subReportMap[index].cslCount);
					uplCountArr.push(subReportMap[index].uplCount);
					$scope.labels.push(subReportMap[index].day);
					$scope.subReportData.push(subReportMap[index]);
				}
				for (let empAttendence of $scope.subReportData) {
					empAttendence.totalCount = empAttendence.pCount + empAttendence.abCount + empAttendence.phCount + empAttendence.alCount + empAttendence.lslCount + empAttendence.slCount + empAttendence.mlCount + empAttendence.cblCount + empAttendence.cslCount + empAttendence.cslCount + empAttendence.uplCount;
				}
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.data.push(pCountArr);
			$scope.data.push(abCountArr);
			$scope.data.push(phCountArr);
			$scope.data.push(alCountArr);
			$scope.data.push(lslCountArr);
			$scope.data.push(slCountArr);
			$scope.data.push(mlCountArr);
			$scope.data.push(cblCountArr);
			$scope.data.push(cslCountArr);
			$scope.data.push(uplCountArr);
		};
		$scope.subReportData = [];
		$scope.data = [];
		var labels = [];
		var series = ['Present', 'Absent', 'Public Holiday', 'Annual Leave', 'Long Service Leave', 'Sick Leave', 'Maternity / Parental Leave', 'Compassionate & Bereavement Leave', 'Community Service Leave', 'Unpaid Leave'];
		$scope.initGraph = function (type) {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();

		};
		$scope.getCostCodeReports = function(){
		var req = {
			"status" : 1,
			"projIds" : $scope.selectedProjIds,
			"loginUser" : false,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		}
		if ($scope.userType == '2') {
			req.loginUser = false;
		}
		$scope.searchReq = req;
		//editPreContractList = [];
		PreContractInternalService.getPreContractListCostCodeReport(req).then(function(data) {
		console.log(data);
			$scope.userProjMap = data.userProjMap;
			$scope.contractlist = data.preContractCostCodeTOs;
			$scope.gridOptions.data = angular.copy($scope.contractlist);
			
			if(click=='click'){
				if ($scope.contractlist.length <= 0) {
					GenericAlertService.alertMessage("Pre-contracts not available for given search criteria", 'Warning');
				}
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});
		}

		/*$scope.getDailyEmpAttendenceDetails = function () {
			$scope.clearSubReportDetails();
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select Project to get details", 'Warning');
				return;
			}
			

			var attendReq = {
				"projIds": $scope.selectedProjIds,
				"crewIds": $scope.selectedCrewIds,
				"month": $scope.month,
				"year": $scope.year,
				"subReportType": $scope.subReportCode
			};

			EmpAttendanceService.getDailyEmpAttendanceReport(attendReq).then(function (data) {
				$scope.empAttendenceDetails = data;
				let tempArr = [];
				for (const d of data) {
					tempArr.push(d.displayNamesMap);
				}
				$scope.gridOptions.data = tempArr;
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.empAttendenceDetails.length <= 0) {
					GenericAlertService.alertMessage("Daily Employee Attendance Records not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Employees Attendance details", 'Error');
			});
			$scope.initGraph('bar');
		};*/
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
				{ name: 'eps', displayName: "EPS Name", headerTooltip: "EPS Id",
				cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].code}}</div>' },
				{ name: 'project', displayName: "Project Name", headerTooltip: "Project Name",
				cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].name}}</div>' },
				{ field: 'code', displayName: "Pre-Contract Id", headerTooltip: "Pre-ContractId" },
				{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description" },
				{ field: 'preContractType', displayName: "Contract type", headerTooltip: "ContractType" },
				{ field: 'contractStageStatus', displayName: "Pre-Contract Stage", headerTooltip: "Pre-Contract Stage"},
				{ field: 'costCodeId', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" },
				{ field: 'costCodeDesc', displayName: "Cost Code Description", headerTooltip: "Cost Code Description" },
				{ field: 'estimateBudget', displayName: "Budget Estimate", headerTooltip: "Budget Estimate" },
								
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
				}
			});
		$scope.resetCostCodeData = function () {
			$scope.plantDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.selectedCompanyIds = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.companyNameDisplay = null;
			$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy"); 
			$scope.subReport = "None";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};

		$scope.clickForward = function (moreFlag) {
			if ($scope.moreFlag < 32) {
				$scope.moreFlag = moreFlag + 5;
			}
		};
		$scope.clickBackward = function (moreFlag) {
			if ($scope.moreFlag > 0) {
				$scope.moreFlag = moreFlag - 5;
			}
		};
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
				template: 'views/help&tutorials/reportshelp/attendancerecordshelp/dailyemphelp.html',
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
