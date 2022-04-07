'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("crewwiseemp", {
		url: '/crewwiseemp',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/crewwisedeployment.html',
				controller: 'CrewWiseDeploymentController'
			}
		}
	})
}]).controller("CrewWiseDeploymentController", ["$scope","$interval", "uiGridGroupingConstants",  "uiGridConstants","$filter", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "ProjectBudgetService",
	"MultipleCrewSelectionFactory", "EmpAttendanceService", "ManpowerReportService", "ProjCostCodeService", "stylesService", "ngGridService", "generalservice", "chartService",
	function ($scope, $interval, uiGridGroupingConstants, uiGridConstants, $filter, $q, $state, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory, ProjectBudgetService,
		MultipleCrewSelectionFactory, EmpAttendanceService, ManpowerReportService, ProjCostCodeService,
		stylesService, ngGridService, generalservice, chartService) {
		chartService.getChartMenu($scope);
		var labels = [];
		var series = ['Present', 'Absent', 'Public Holiday', 'Annual Leave', 'Long Service Leave', 'Sick Leave', 'Maternity / Parental Leave', 'Compassionate & Bereavement Leave', 'Community Service Leave', 'Unpaid Leave'];
		$scope.stylesSvc = stylesService;
		$scope.subReport = 'None';
		$scope.selectedProjIds = [];
		$scope.subReportCode = "";
		$scope.selectedCrewIds = [];
		$scope.crewWiseAttendenceDetails = [];
		$scope.unitofmeasure = "";

		$scope.date = new Date();
		var defaultDate = new Date($scope.date);
		$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");

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
		$scope.totalValues = angular.copy(totalValues);
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
				$scope.crewWiseAttendenceDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
			});
		};
		$scope.subReports = [{
			name: 'TradeWise Counting Records',
			code: "trade"
		}, {
			name: 'CrewWise Counting Records',
			code: "crew"
		}, {
			name: 'ProjectWise Counting Records',
			code: "proj"
		}, {
			name: 'EPSWise Counting Records',
			code: "eps"
		}];
		function noSubReportData() {
			let columnDefs = [
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", groupingShowAggregationMenu: false  },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false },
				{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
				{ field: 'empFirstName', displayName: "First Name", headerTooltip: "First Name" , groupingShowAggregationMenu: false },
				{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Last Name", groupingShowAggregationMenu: false  },
				{ field: 'unitofmeasure', displayName: "Unit Of Measurement", headerTooltip: "Unit Of Measurement", groupingShowAggregationMenu: false  },
				{ field: 'tradeName', displayName: "Trade", headerTooltip: "Trade Name", groupingShowAggregationMenu: false  },
				{ field: 'empCategory', displayName: "Category", headerTooltip: "Category Name", groupingShowAggregationMenu: false  },
				{ field: 'empMobDate', displayName: "Date of Mobilisation", headerTooltip: "Mobilisation Date", groupingShowAggregationMenu: false  },
				{ field: 'empGender', displayName: "Gender", headerTooltip: "Gender", groupingShowAggregationMenu: false  },
				{ field: 'empLocType', displayName: "Local/NonLocal", headerTooltip: "Local / Non Local", groupingShowAggregationMenu: false  },
				{ field: 'attdRecord', displayName: "Attendance", headerTooltip: "Attendance Record", groupingShowAggregationMenu: false  },
				{ field: 'empChargeRate', displayName: "Rate", headerTooltip: "Chargeable Rate", groupingShowAggregationMenu: false  }
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
				$scope.crewWiseAttendenceDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getCrewWiseAttendenceDetails();
			}
		};
		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "trade") {
				generateSubReportData("empClassId", "tradeName");
			} else if ($scope.subReport.code == "crew") {
				generateSubReportData("crewId", "crewName");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			}
		};
		function generateSubReportData(key, value) {
			let subReportMap = [];
			$scope.data = [];
			if ($scope.subReport.code == "trade") {
				let costCodeData = [
					{ field: 'mapValue', displayName: "Trade", headerTooltip: "Trade Name" },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave", groupingShowAggregationMenu: false  },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave" , groupingShowAggregationMenu: false },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_CrewiseDailyDeployment_Trade");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "crew") {
				let costCodeData = [
					{ field: 'mapValue', displayName: "Crew", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
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
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_CrewiseDailyDeployment_Crew");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "proj") {
				let costCodeData = [
					{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
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
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_CrewiseDailyDeployment_Proj");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "eps") {
				let costCodeData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
					{ field: 'pCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Present", headerTooltip: "Present", groupingShowAggregationMenu: false  },
					{ field: 'abCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Absent", headerTooltip: "Absent", groupingShowAggregationMenu: false  },
					{ field: 'phCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Public", headerTooltip: "Public Holiday", groupingShowAggregationMenu: false  },
					{ field: 'alCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Annual", headerTooltip: "Annual Leave", groupingShowAggregationMenu: false  },
					{ field: 'lslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Long Service", headerTooltip: "Long Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'slCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Sick", headerTooltip: "Sick Leave" },
					{ field: 'mlCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Maternity/Parental", headerTooltip: "Maternity/Parental Leave", groupingShowAggregationMenu: false  },
					{ field: 'cblCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Compassionate & Bereavement", headerTooltip: "Compassionate & Bereavement Leave", groupingShowAggregationMenu: false  },
					{ field: 'cslCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Community Service", headerTooltip: "Community Service Leave", groupingShowAggregationMenu: false  },
					{ field: 'uplCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Unpaid", headerTooltip: "Unpaid Leave", groupingShowAggregationMenu: false  },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_CrewiseDailyDeployment_EPS");
				$scope.gridOptions.showColumnFooter = true;
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			$scope.totalValues = angular.copy(totalValues);
			for (const attdDtl of $scope.crewWiseAttendenceDetails) {
				const mapKey = attdDtl.displayNamesMap[key];
				const mapValue = attdDtl.displayNamesMap[value];
				if (!subReportMap[mapKey]) {
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
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
				if (attdDtl.displayNamesMap[$scope.date]) {
					switch (attdDtl.displayNamesMap[$scope.date]) {
						case generalservice.empLeaveTypes[0].code:
							subReportMap[mapKey].pCount++;
							$scope.totalValues.pCount++;
							break;
						case generalservice.empLeaveTypes[1].code:
							subReportMap[mapKey].abCount++;
							$scope.totalValues.abCount++;
							break;
						case generalservice.empLeaveTypes[2].code:
							subReportMap[mapKey].phCount++;
							$scope.totalValues.phCount++;
							break;
						case generalservice.empLeaveTypes[3].code:
							subReportMap[mapKey].alCount++;
							$scope.totalValues.alCount++;
							break;
						case generalservice.empLeaveTypes[4].code:
							subReportMap[mapKey].lslCount++;
							$scope.totalValues.lslCount++;
							break;
						case generalservice.empLeaveTypes[5].code:
							subReportMap[mapKey].slCount++;
							$scope.totalValues.slCount++;
							break;
						case generalservice.empLeaveTypes[6].code:
							subReportMap[mapKey].mlCount++;
							$scope.totalValues.mlCount++;
							break;
						case generalservice.empLeaveTypes[7].code:
							subReportMap[mapKey].cblCount++;
							$scope.totalValues.cblCount++;
							break;
						case generalservice.empLeaveTypes[8].code:
							subReportMap[mapKey].cslCount++;
							$scope.totalValues.cslCount++;
							break;
						case generalservice.empLeaveTypes[9].code:
							subReportMap[mapKey].uplCount++;
							$scope.totalValues.uplCount++;
							break;
						default:
							break;
					}
				}
			}
			let pCountArr = [], abCountArr = [], phCountArr = [], alCountArr = [], lslCountArr = [], slCountArr = [], mlCountArr = [], cblCountArr = [], cslCountArr = [], uplCountArr = [];
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
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			for (let crewWiseAttendence of $scope.subReportData) {
				crewWiseAttendence.totalCount = crewWiseAttendence.pCount + crewWiseAttendence.abCount + crewWiseAttendence.phCount + crewWiseAttendence.alCount + crewWiseAttendence.lslCount + crewWiseAttendence.slCount + crewWiseAttendence.mlCount + crewWiseAttendence.cblCount + crewWiseAttendence.cslCount + crewWiseAttendence.cslCount + crewWiseAttendence.uplCount;
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
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", groupingShowAggregationMenu: false  },
						{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
						{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
						{ field: 'empFirstName', displayName: "First Name", headerTooltip: "First Name", groupingShowAggregationMenu: false  },
						{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Last Name", groupingShowAggregationMenu: false  },
						{ field: 'unitofmeasure', displayName: "Unit Of Measurement", headerTooltip: "Unit of Measurement", groupingShowAggregationMenu: false  },
						{ field: 'tradeName', displayName: "Trade", headerTooltip: "Trade Name", groupingShowAggregationMenu: false  },
						{ field: 'empCategory', displayName: "Category", headerTooltip: "Category Name", groupingShowAggregationMenu: false  },
						{ field: 'empMobDate', displayName: "Date of Mobilisation", headerTooltip: "Mobilisation Date", groupingShowAggregationMenu: false  },
						{ field: 'empGender', displayName: "Gender", headerTooltip: "Gender", groupingShowAggregationMenu: false  },
						{ field: 'empLocType', displayName: "Local/NonLocal", headerTooltip: "Local / Non Local", groupingShowAggregationMenu: false  },
						{ field: 'attdRecord', displayName: "Attendance", headerTooltip: "Attendance Record", groupingShowAggregationMenu: false  },
						{ field: 'empChargeRate', displayName: "Rate", headerTooltip: "Chargeable Rate", groupingShowAggregationMenu: false  }
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Atten_CrewiseDailyDeployment");
				}
			});
		$scope.data = [];
		$scope.labels = [];
		$scope.initGraph = function () {
			$scope.labels = labels;
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();
		};
		$scope.measure = function(){
		var getManpowerReq = {
				"status": "1",
				"projId": 185
			};
			console.log(getManpowerReq);
			ProjectBudgetService.getProjManpowers(getManpowerReq).then(function (data1) {
			$scope.unitofmeasure = data1.projManpowerTOs[0].measureUnitTO.name;
				console.log($scope.unitofmeasure);
					});
		};
		$scope.getCrewWiseAttendenceDetails = function () {
		$scope.measure();
		console.log($scope.unitofmeasure);
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select Project to get details", 'Warning');
				return;
			}
			if ($scope.selectedCrewIds.length <= 0) {
				GenericAlertService.alertMessage("Please select Crew to get details", 'Warning');

				return;
			}
			if ($scope.date == undefined) {
				GenericAlertService.alertMessage("Please select Date to get details", 'Warning');

				return;
			}
			
			var req = {
				"projIds": $scope.selectedProjIds,
				"crewIds": $scope.selectedCrewIds,
				"date": $scope.date,
				"subReportType": $scope.subReportCode
			}
			EmpAttendanceService.getEmpAttendanceRecordsByDate(req).then(function (data) {
				$scope.crewWiseAttendenceDetails = data;
				let tempArr = [];
				for (const d of data) {
					let map = d.displayNamesMap;
					const leaveType = generalservice.empLeaveTypes.find(function (type) {
						return type.code === map[$scope.date];
					});
					if (leaveType) {
						map.attdRecord = leaveType.desc;
					}
					map.unitofmeasure = "Hours";
					tempArr.push(map);
				}
				$scope.gridOptions.data = tempArr;
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.crewWiseAttendenceDetails.length <= 0) {
					GenericAlertService.alertMessage("Crew wise Daily Deployment List not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting Crew Wise Attendance Records", 'Error');
			});
			$scope.initGraph();
		};
		$scope.clearSubReportDetails = function () {
			$scope.crewWiseAttendenceDetails = [];
			$scope.subReportName = "";
			$scope.type = "";
			$scope.subReportCode = "";
			$scope.subReport = "None";
		};
		$scope.resetCrewWiseAttendenceDetails = function () {
			$scope.crewWiseAttendenceDetails = [];
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.date = null;
			$scope.subReport = "None";
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.gridOptions.showColumnFooter = false;
			$scope.type = '';
			$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.fromDate = null;
			$scope.toDate = null;
			$scope.subReportCode = "";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
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
				template: 'views/help&tutorials/reportshelp/attendancerecordshelp/crewwisehelp.html',
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
