'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("dailyplant", {
		url: '/dailyplant',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/attenddailyplant.html',
				controller: 'AttendanceDailyPlantController'
			}
		}
	})
}]).controller("AttendanceDailyPlantController", ["$scope", "$filter", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "EmpAttendanceService", "PlantAttendanceService", "stylesService", "ngGridService", "chartService",
	function ($scope, $filter, $q, $state, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory,
		MultipleCrewSelectionFactory, EmpAttendanceService, PlantAttendanceService, stylesService, ngGridService, chartService) {
		chartService.getChartMenu($scope);
		$scope.subReportCode = "";
		var labels = [];
		$scope.stylesSvc = stylesService;
		$scope.subReport = "None";
		$scope.yearTos = [];
		$scope.moreFlag = 0;
		$scope.plantAttendenceDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCrewIds = [];
		$scope.daysList = [];
		$scope.dayList =[];
		$scope.monthTOs = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		var currDate = new Date();
		var currYear = currDate.getFullYear();
		var futureYear = currYear + 1;
		var pastYear = currYear - 15;
		for (var i = pastYear; i <= futureYear; i++) {
			$scope.yearTos.push(i);
		}
		var d = new Date();
		var month = $scope.monthTOs[d.getMonth()];
		$scope.month = $filter('date')((month), "MMM");

		var year = currYear;
		$scope.year = parseInt(year);

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
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
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
				$scope.plantAttendenceDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
			});
		};

		$scope.tempCurrentCols = [];

		$scope.getSelectedMonthDays = function (monthDetails, year) {
			var req = {
				"status": 1,
				"attendenceMonth": monthDetails + "-" + year
			};
			EmpAttendanceService.getAttendanceDays(req).then(function (data) {
				$scope.daysList = data.attendenceDays;
			/*	if (!tempInitColList)
					tempInitColList = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.columnDefs = angular.copy(tempInitColList);
				$scope.daysList.map(day => {
					let column = {};
					column.field = day;
					column.displayName = day;
					column.headerTooltip = day;
					column.visible = false;
					$scope.gridOptions.columnDefs.splice(($scope.gridOptions.data.length - 1), 0, column);
				});
				if ($scope.gridOptions)
					$scope.gridOptions.data.attendenceDays = $scope.daysList;
				$scope.initialColumnSet = $scope.gridOptions.columnDefs; */
			})
		};

		$scope.subReports = [{
			name: 'Periodical-Attendance Records',
			code: "periodical"
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
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", groupingShowAggregationMenu: false },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ field: 'plantCode', displayName: "Asset ID", headerTooltip: "Asset ID", groupingShowAggregationMenu: false},
				{ field: 'plantDesc', displayName: "Plant Description", headerTooltip: "Plant Description", groupingShowAggregationMenu: false},
				{ field: 'plantMake', displayName: "Plant Make", headerTooltip: "Plant Make", groupingShowAggregationMenu: false},
				{ field: 'plantModel', displayName: "Plant Model", headerTooltip: "Plant Model", groupingShowAggregationMenu: false},
				{ field: 'crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false},
				{ field: 'companyName', displayName: "Parent Company", headerTooltip: "Parent Company", groupingShowAggregationMenu: false},
				{ field: 'totalWorking', displayName: "Total", headerTooltip: "Total Days" }
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [] );
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.plantAttendenceDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getDailyPlantAttendenceDetails();
			}
		};

		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "periodical") {
				generateSubReportData("", "", true);
			} else if ($scope.subReport.code == "cmp") {
				generateSubReportData("companyId", "companyName");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			}
		};

		function generateSubReportData(key, value, dayWise) {
			let wCountArr = [], nwCountArr = [], idleCountArr = [], urCountArr = [];
			let subReportMap = [];
			if ($scope.subReport.code == "periodical") {
				let epsData = [
					{ field: 'mapValue', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
					{ field: 'wCount', displayName: "Work Days", headerTooltip: "Working Days", groupingShowAggregationMenu: false},
					{ field: 'nwCount', displayName: "Non Work Days", headerTooltip: "Non Working Days", groupingShowAggregationMenu: false},
					{ field: 'idleCount', displayName: "Idle Days", headerTooltip: "Idle Days", groupingShowAggregationMenu: false},
					{ field: 'urCount', displayName: "Under Repair Days", headerTooltip: "Under Repair Days", groupingShowAggregationMenu: false}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_AttendanceDailyPlant_Periodical");
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "cmp") {
				let epsData = [
					{ field: 'mapValue', displayName: "Company", headerTooltip: "Company Name", groupingShowAggregationMenu: false},
					{ field: 'wCount', displayName: "Work Days", headerTooltip: "Working Days", groupingShowAggregationMenu: false},
					{ field: 'nwCount', displayName: "Non Work Days", headerTooltip: "Non Working Days", groupingShowAggregationMenu: false},
					{ field: 'idleCount', displayName: "Idle Days", headerTooltip: "Idle Days", groupingShowAggregationMenu: false},
					{ field: 'urCount', displayName: "Under Repair Days", headerTooltip: "Under Repair Days", groupingShowAggregationMenu: false}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_AttendanceDailyPlant_CMP");
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "proj") {
				let epsData = [
					{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
					{ field: 'wCount', displayName: "Work Days", headerTooltip: "Working Days", groupingShowAggregationMenu: false},
					{ field: 'nwCount', displayName: "Non Work Days", headerTooltip: "Non Working Days", groupingShowAggregationMenu: false},
					{ field: 'idleCount', displayName: "Idle Days", headerTooltip: "Idle Days", groupingShowAggregationMenu: false},
					{ field: 'urCount', displayName: "Under Repair Days", headerTooltip: "Under Repair Days", groupingShowAggregationMenu: false}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_AttendanceDailyPlant_Proj");
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			if ($scope.subReport.code == "eps") {
				let epsData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
					{ field: 'wCount', displayName: "Work Days", headerTooltip: "Working Days", groupingShowAggregationMenu: false},
					{ field: 'nwCount', displayName: "Non Work Days", headerTooltip: "Non Working Days", groupingShowAggregationMenu: false},
					{ field: 'idleCount', displayName: "Idle Days", headerTooltip: "Idle Days", groupingShowAggregationMenu: false},
					{ field: 'urCount', displayName: "Under Repair Days", headerTooltip: "Under Repair Days", groupingShowAggregationMenu: false}
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_AttendanceDailyPlant_EPS");
				$scope.gridOptions.gridMenuCustomItems = false;
			}
			for (let plantDtl of $scope.plantAttendenceDetails) {
				for (const day of $scope.daysList) {
					let mapKey;
					let mapValue;
					if (dayWise) {
						mapKey = day;
						mapValue = day;
					} else {
						mapKey = plantDtl.displayNamesMap[key];
						mapValue = plantDtl.displayNamesMap[value];
					}
					if (!subReportMap[mapKey]) {
						let wCount = 0, nwCount = 0, idleCount = 0, urCount=0;
						subReportMap[mapKey] = {
							"mapKey": mapKey,
							"mapValue": mapValue,
							"wCount": wCount,
							"nwCount": nwCount,
							"idleCount": idleCount,
							"urCount": urCount
							
						};
					}
					if (plantDtl.displayNamesMap[day]) {
						switch (plantDtl.displayNamesMap[day]) {
							case "NW":
								subReportMap[mapKey].nwCount++;
								break;
							case "W":
								subReportMap[mapKey].wCount++;
								break;
							case "I":
								subReportMap[mapKey].idleCount++;
								break;
							case "UR":
								subReportMap[mapKey].urCount++;
								break;
							default:
								break;
						}
					}
				}
			}
			for (const index in subReportMap) {
				if (subReportMap[index].wCount || subReportMap[index].nwCount || subReportMap[index].idleCount || subReportMap[index].urCount) {
					wCountArr.push(subReportMap[index].wCount);
					nwCountArr.push(subReportMap[index].nwCount);
					idleCountArr.push(subReportMap[index].idleCount);
					urCountArr.push(subReportMap[index].urCount);
					$scope.labels.push(subReportMap[index].mapValue);
					$scope.subReportData.push(subReportMap[index]);
				}
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.data.push(wCountArr);
			$scope.data.push(nwCountArr);
			$scope.data.push(idleCountArr);
			$scope.data.push(urCountArr);
		};

		$scope.getDailyPlantAttendenceDetails = function () {
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

			PlantAttendanceService.getDailyPlantAttendanceReport(attendReq).then(function (data) {
				$scope.plantAttendenceDetails = data;
				for(var i=0;i<data.length;i++){
				for(var j=0;j<$scope.daysList.length;j++){
				if(data[i].displayNamesMap[$scope.daysList[j]] != undefined){$scope.dayList.push($scope.daysList[j]);}
				}
				}
				console.log($scope.dayList);
				$scope.dayList = $filter('unique')($scope.dayList,'$scope.dayList');
				if (!tempInitColList)
					tempInitColList = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.columnDefs = angular.copy(tempInitColList);
				$scope.dayList.map(day => {
					let column = {};
					column.field = day;
					column.displayName = day;
					column.headerTooltip = day;
					column.visible = false;
					$scope.gridOptions.columnDefs.splice(($scope.gridOptions.data.length - 1), 0, column);
				});
				if ($scope.gridOptions)
					$scope.gridOptions.data.attendenceDays = $scope.daysList;
				$scope.initialColumnSet = $scope.gridOptions.columnDefs; 
			
				
				
				let tempArr = [];
				for (const d of data) {
					tempArr.push(d.displayNamesMap);
				}
				$scope.gridOptions.data = tempArr;
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.plantAttendenceDetails.length <= 0) {
					GenericAlertService.alertMessage("Daily Plant Attendance Records not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  plants Attendance details", 'Error');
			});
			$scope.initGraph();
		};
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", groupingShowAggregationMenu: false},
						{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
						{ field: 'plantCode', displayName: "Asset ID", headerTooltip: "Asset ID", groupingShowAggregationMenu: false},
						{ field: 'plantDesc', displayName: "Plant Description", headerTooltip: "Plant Description", groupingShowAggregationMenu: false},
						{ field: 'plantMake', displayName: "Plant Make", headerTooltip: "Plant Make", groupingShowAggregationMenu: false},
						{ field: 'plantModel', displayName: "Plant Model", headerTooltip: "Plant Model", groupingShowAggregationMenu: false},
						{ field: 'crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false},
						{ field: 'companyName', displayName: "Parent Company", headerTooltip: "Parent Company", groupingShowAggregationMenu: false},
						{ field: 'totalWorking', displayName: "Total", headerTooltip: "Total Days", groupingShowAggregationMenu: false},
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Attendance_AttendanceDailyPlant");
				}
			});

		$scope.data = [];
		$scope.labels = [];
		$scope.initGraph = function () {
			var series = ['Working', 'Non Working', 'Idle','Under Repair'];
			$scope.labels = labels;
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();
		};
		$scope.clearSubReportDetails = function () {
			$scope.plantAttendenceDetails = [];
			$scope.type = "";
			$scope.subReport = "None";
			$scope.subReportName = "";
			$scope.subReportCode = "";
		};
		$scope.resetDailyPlantAttendData = function () {
			$scope.plantAttendenceDetails = [];
			$scope.subReport = "None";
			$scope.daysList = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.month = $filter('date')((month), "MMM");
			$scope.year = parseInt(year);
			$scope.type = '';
			$scope.selectedProjIds = '';
			$scope.selectedCrewIds = '';
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.subReportCode = "";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);;
				$scope.gridOptions.data = [];
			}
		};
		$scope.clickForward = function (moreFlag) {
			if ($scope.moreFlag <= 31) {
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
				template: 'views/help&tutorials/reportshelp/attendancerecordshelp/dailyplanthelp.html',
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
