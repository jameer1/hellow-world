'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("dailyemprecords", {
		url: '/dailyemprecords',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/attenddailyemp.html',
				controller: 'AttendanceDailyEmpController'
			}
		}
	})
}]).controller("AttendanceDailyEmpController", ["$scope", "$interval", "uiGridGroupingConstants", "$filter", "$q", "$state", "ngDialog", "EmpAttendanceService", "GenericAlertService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "generalservice", "stylesService", "ngGridService", "chartService", "uiGridConstants",
	function ($scope, $interval, uiGridGroupingConstants, $filter, $q, $state, ngDialog, EmpAttendanceService, GenericAlertService,
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
		$scope.dayList = [];
		$scope.monthTOs = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		var currDate = new Date();
		var currYear = currDate.getFullYear();
		var futureYear = currYear + 1;
		var pastYear = currYear - 15;
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

		var d = new Date();
		var month = $scope.monthTOs[d.getMonth()];
		$scope.month = $filter('date')((month), "MMM");

		var year = currYear;
		$scope.year = parseInt(year);

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
		$scope.getCrewList = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select project to get crews", 'Warning');
				return;
			}
			var crewReq = {
				"status": 1,
				"projIds": $scope.selectedProjIds
			};
			var crewSerivcePopup = MultipleCrewSelectionFactory.crewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				$scope.crewNameDisplay = data.selectedCrews.crewName;
				$scope.selectedCrewIds = data.selectedCrews.crewIds;
				$scope.empAttendenceDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
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
/*				if (!tempInitColList)
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
				$scope.initialColumnSet = $scope.gridOptions.columnDefs; */
			})
		};

		$scope.subReports = [{
			name: 'Periodical-Direct Men Records',
			code: "direct"
		}, {
			name: 'Periodical-Indirect Men Records',
			code: "indirect"
		}, {
			name: 'TradeWise Counting Records',
			code: "trade"
		}, {
			name: 'CompanyWise Counting Records',
			code: "cmp"
		}, {
			name: 'ProjectWise Counting Records',
			code: "proj"
		}, {
			name: 'EPSWise Counting Records',
			code: "eps"
		}];
		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Id", groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'empCode', displayName: "Employee Id", headerTooltip: "Employee ID", groupingShowAggregationMenu: false },
				{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
				{ field: 'empFirstName', displayName: "First Name", headerTooltip: "First Name", groupingShowAggregationMenu: false },
				{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Last Name", groupingShowAggregationMenu: false },
				{ field: 'crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false },
				{ field: 'empCategory', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false },
				{ field: 'tradeName', displayName: "Trade", headerTooltip: "Trade", groupingShowAggregationMenu: false }
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
		}
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.empAttendenceDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getDailyEmpAttendenceDetails();
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
					{ field: 'day', displayName: "Date", headerTooltip: "Date",groupingShowAggregationMenu: false  },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
					{ field: 'totalCount',visible:"{{row.entity.totalCount}}>0",aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_AttRec_DailyEmp_Proj_Dir");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "indirect") {
				let costCodeData = [
					{ field: 'day', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false  },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_AttRec_DailyEmp_Ind");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "trade") {
				let costCodeData = [
					{ field: 'day', displayName: "Trade", headerTooltip: "Trade Name", groupingShowAggregationMenu: false  },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_AttRec_DailyEmp_Trade");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "cmp") {
				let costCodeData = [
					{ field: 'day', displayName: "Company", headerTooltip: "Company Name", groupingShowAggregationMenu: false  },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_AttRec_DailyEmp_Cmp");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "proj") {
				let costCodeData = [
					{ field: 'day', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_AttRec_DailyEmp_Proj");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "eps") {
				let costCodeData = [
					{ field: 'day', displayName: "EPS", headerTooltip: "EPS Name",  groupingShowAggregationMenu: false },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_AttRec_DailyEmp_EPS");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			$scope.totalValues = angular.copy(totalValues);
			if (dayWise) {
				for (const day of $scope.dayList) {
					let pCount = "", abCount = "", phCount = "", alCount = "", lslCount = "", slCount = "", mlCount = "", cblCount = "", cslCount = "", uplCount = "";
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
					for (const day of $scope.dayList) {
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

		$scope.getDailyEmpAttendenceDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select Project to get details", 'Warning');
				return;
			}
			if ($scope.selectedCrewIds.length <= 0) {
				GenericAlertService.alertMessage("Please select Crew to get details", 'Warning');

				return;
			}
			if ($scope.month == undefined) {
				GenericAlertService.alertMessage("Please select Month to get details", 'Warning');

				return;
			}
			if ($scope.year == undefined) {
				GenericAlertService.alertMessage("Please select Year to get details", 'Warning');

				return;
			}

			$scope.getSelectedMonthDays($scope.month, $scope.year);

			var attendReq = {
				"projIds": $scope.selectedProjIds,
				"crewIds": $scope.selectedCrewIds,
				"month": $scope.month,
				"year": $scope.year,
				"subReportType": $scope.subReportCode
			};

			EmpAttendanceService.getDailyEmpAttendanceReport(attendReq).then(function (data) {
				$scope.empAttendenceDetails = data;
				$scope.dayList1=[];
				//var set = new set();
				for(var i=0;i<data.length;i++){
				for(var j=0;j<$scope.daysList.length;j++){
				if(data[i].displayNamesMap[$scope.daysList[j]] != undefined){$scope.dayList1.push($scope.daysList[j]);}
				}
				}
				$scope.dayList = $filter('unique')($scope.dayList1,'$scope.dayList');
				console.log($scope.dayList);
				$scope.dayList = $filter('unique')($scope.dayList,'$scope.dayList');
				console.log($scope.dayList);
				if (!tempInitColList)
				tempInitColList = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.columnDefs = angular.copy(tempInitColList);
				$scope.dayList.map(day => {
					let column = {};
					column.field = day;
					column.width=50;
					column.displayName = day;
					column.width=50;
					column.headerTooltip = day;
					column.width=50;
					column.visible = false;
					$scope.gridOptions.columnDefs.push(column);
				});
				$scope.initialColumnSet = $scope.gridOptions.columnDefs;
			
				
				
				
				let tempArr = [];
				for (const d of $scope.empAttendenceDetails) {
					tempArr.push(d.displayNamesMap);
				}
				$scope.gridOptions.data = angular.copy(tempArr);
				// $scope.gridOptions.data = tempArr;
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
		};
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Id", groupingShowAggregationMenu: false },
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'empCode', displayName: "Employee Id", headerTooltip: "Employee ID", groupingShowAggregationMenu: false },
						{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
						{ field: 'empFirstName', displayName: "First Name", headerTooltip: "First Name", groupingShowAggregationMenu: false },
						{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Last Name", groupingShowAggregationMenu: false },
						{ field: 'crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false },
						{ field: 'empCategory', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false },
						{ field: 'tradeName', displayName: "Trade", headerTooltip: "Trade", groupingShowAggregationMenu: false }
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_AttRec_DailyEmp");
					$scope.gridOptions.showColumnFooter = false;
					$scope.gridOptions.exporterPdfMaxGridWidth = 760;
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
				}
			});
		$scope.resetDailyEmpAttendData = function () {
			$scope.empAttendenceDetails = [];
			$scope.subReport = "None";
			$scope.daysList = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = '';
			$scope.selectedCrewIds = '';
			$scope.gridOptions.showColumnFooter = false;
			$scope.type = '';
			$scope.month = $filter('date')((month), "MMM");
			$scope.year = parseInt(year);
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.subReportCode = "";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = tempInitColList;
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
