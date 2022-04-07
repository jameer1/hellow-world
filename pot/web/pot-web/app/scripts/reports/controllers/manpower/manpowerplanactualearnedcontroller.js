'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpowerplanedactualearned", {
		url: '/manpowerplanedactualearned',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowerplanactualearned.html',
				controller: 'ManpowerPlanActualEarnedController'
			}
		}
	})
}]).controller("ManpowerPlanActualEarnedController", ["$scope","uiGridGroupingConstants","uiGridConstants","$q", "ngDialog","$filter", "ResourceCurveService",
	"ManpowerReportService", "GenericAlertService", "EpsProjectMultiSelectFactory", "ProjectScheduleService",
	"ProjectSettingsService", "dateGroupingService", "ngGridService", "stylesService",
	function ($scope,uiGridGroupingConstants,uiGridConstants,$q, ngDialog, $filter, ResourceCurveService, ManpowerReportService,
		GenericAlertService, EpsProjectMultiSelectFactory, ProjectScheduleService, 
		ProjectSettingsService, dateGroupingService, ngGridService, stylesService) {
		$scope.colors = ['#ff0000', '#ffff00', '#800080', '#ffa500', '#ff00ff', '#800000', '#0000ff'];
		let manpowerList = [];
		$scope.selectedProjIds = [];
		$scope.date = new Date();
		$scope.toDate = new Date();
		var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
		$scope.fromDate = new Date($scope.toDate);
		$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

		var defaultFromDate = new Date($scope.fromDate);
		var defaultToDate = new Date($scope.toDate);
		let todayDate = new Date();
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.periodicSubReportCode = "";
		$scope.subReport = {
			name: null
		}
		$scope.periodicSubReport = {
			name: null
		};
		let projReportsSettings;

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});

			$scope.clearSubReportDetails();
		};

		$scope.subReports = [{
			name: 'Planned, Actual, Earned Hours',
			code: "Trade",
		}, {
			name: 'Planned Hours',
			code: "PeriodicalPlanned"
		}, {
			name: 'Actual Hours',
			code: "PeriodicalActual"
		}, {
			name: 'Earned Hours',
			code: "Earned"
		}];

		$scope.periodicSubReports = [
			{
				name: 'Daily',
				code: "Daily"
			}, {
				name: 'Weekly',
				code: "Weekly"
			}, {
				name: 'Monthly',
				code: "Monthly"
			}, {
				name: 'Yearly',
				code: "Yearly"
			}];

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

		$scope.changeSubReport = function () {
			$scope.type = 'chartTable';
			$scope.periodicSubReport = $scope.periodicSubReports[0];
			const subReportResult = getSubReportMap($scope.subReport.code, true);
			setGraphData(subReportResult.subReportMap, subReportResult.graphDataProps);
		}

		function getSubReportMap(subReportCode, calculateCumulativeValues) {
			let graphDataProps;
			switch (subReportCode) {
				case "Trade":
					graphDataProps = ['plannedValue', 'actualHrs', 'earnedValue', 'cumulativePlanned',
						'cumulativeActual', 'cumulativeEarned'];
					if ($scope.subReport.code == "Trade") {
						let manPowerData = [
							{ field: 'code', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
							{ field: 'plannedValue', displayName: "Planned Hrs", headerTooltip: "Planned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'cumulativePlanned', displayName: "Cumm Plan Hrs", headerTooltip: "Cumulative Planned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'actualHrs', displayName: "Actual Hrs", headerTooltip: "Actual Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'cumulativeActual', displayName: 'Cumm Actual Hrs', headerTooltip: 'Cumulative Actual Hours', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'earnedValue', displayName: "Earned Hrs", headerTooltip: "Earned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'cumulativeEarned', displayName: 'Cumm Earned Hrs', headerTooltip: 'Cumm Earned Hours', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Plan_Vs_Actual_VEH");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					break;
				case "Earned":
					graphDataProps = ['earnedValue', 'cumulativeEarned'];
					if ($scope.subReport.code == "Earned") {
						let manPowerData = [
							{ field: 'code', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
							{ field: 'earnedValue', displayName: 'Period Earned Value', headerTooltip: 'Periodical Earned Value', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'cumulativeEarned', displayName: 'Cumm Earned Value', headerTooltip: 'Cumm Earned Value', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Plan_Vs_Actual_VEH");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					break;
				case "PeriodicalActual":
					graphDataProps = ['actualHrs', 'cumulativeActual'];
					if ($scope.subReport.code == "PeriodicalActual") {
						let manPowerData = [
							{ field: 'code', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
							{ field: 'actualHrs', displayName: "Periodical Actual", headerTooltip: "Periodical Actual", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'cumulativeActual', displayName: "Cumm Actual Hrs", headerTooltip: "Cumulative Actual Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Plan_Vs_Actual_VEH");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					break;
				case "PeriodicalPlanned":
					graphDataProps = ['plannedValue', 'cumulativePlanned'];
					if ($scope.subReport.code == "PeriodicalPlanned") {
						let manPowerData = [
							{ field: 'code', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
							{ field: 'plannedValue', displayName: "Period Plan Hrs", headerTooltip: "Periodical Planned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
							{ field: 'cumulativePlanned', displayName: "Cumm Plan Hrs", headerTooltip: "Cumulative Planned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, manPowerData, data,"Reports_Manpower_Plan_Vs_Actual_VEH");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					break;
				default:
					manpowerList = [];
					$scope.type = '';
					$scope.subReport = null;
					$scope.periodicSubReport = null;
					subReportCode = '';
					let columnDefs = [
						{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name" },
						{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
						{ field: 'dateStr', displayName: "Date", headerTooltip: "Date" },
						{ field: 'plannedValue', displayName: "Planned Hrs", headerTooltip: "Planned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						{ field: 'actualHrs', displayName: "Actual Hrs", headerTooltip: "Actual Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						{ field: 'earnedValue', displayName: "Earned Hrs", headerTooltip: "Earned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_Plan_Vs_Actual_VEH");
					$scope.gridOptions.gridMenuCustomItems = false;
					$scope.getManpowerManHrsDetails();
			}
			const subReportMap = new Array();
			let key;
			for (const manPower of manpowerList) {
				// let key = manPower.projId + "_" + manPower.date;
				// let cumulativeKey = manPower.projId;

				key = manPower.date;

				if (!subReportMap[key]) {
					subReportMap[key] = {
						'date': manPower.date,
						'code': $filter('date')((manPower.date), "dd-MMM-yyyy"),
						'projId': manPower.projId,
						'projName': manPower.projName,
						'plannedValue': 0,
						'actualHrs': 0,
						'earnedValue': 0,
						'cumulativePlanned': 0,
						'cumulativeActual': 0,
						'cumulativeEarned': 0
					};
				}
				subReportMap[key].plannedValue += manPower.plannedValue;
				subReportMap[key].actualHrs += manPower.actualHrs;
				subReportMap[key].earnedValue += manPower.earnedValue;
			}
			if (calculateCumulativeValues) {

				let cumulativePlannedValue = 0;
				let cumulativeActualValue = 0;
				let cumulativeEarnedValue = 0;

				const sortedKeys = _.sortBy(Object.keys(subReportMap));
				for (const prop of sortedKeys) {

					// Calculate cumulative values
					cumulativePlannedValue += subReportMap[prop].plannedValue;
					cumulativeActualValue += subReportMap[prop].actualHrs;
					cumulativeEarnedValue += subReportMap[prop].earnedValue;

					subReportMap[prop].cumulativePlanned = cumulativePlannedValue;
					subReportMap[prop].cumulativeActual = cumulativeActualValue;
					subReportMap[prop].cumulativeEarned = cumulativeEarnedValue;
				}
			}
			return { 'subReportMap': subReportMap, 'graphDataProps': graphDataProps };
		}

		function setGraphData(subReportMap, graphDataPropertiesArray) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let obj = null;
			const graphDataMap = new Array();
			for (const prop of graphDataPropertiesArray) {
				graphDataMap[prop] = new Array();
			}
			$scope.subReportData = new Array();
			for (const key in subReportMap) {
				obj = subReportMap[key];
				for (const prop of graphDataPropertiesArray) {
					graphDataMap[prop].push(obj[prop]);
				}
				$scope.labels.push(obj.code);
				$scope.subReportData.push(obj);
			}
			for (let manpower of $scope.subReportData) {
				manpower.plannedValue = parseFloat(manpower.plannedValue).toFixed(2);
				manpower.cumulativePlanned = parseFloat(manpower.cumulativePlanned).toFixed(2);
				manpower.cumulativeActual = parseFloat(manpower.cumulativeActual).toFixed(2);
				manpower.earnedValue = parseFloat(manpower.earnedValue).toFixed(2);
				manpower.cumulativeEarned = parseFloat(manpower.cumulativeEarned).toFixed(2);

			}
			for (const prop of graphDataPropertiesArray) {
				$scope.data.push(graphDataMap[prop]);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			initGraph(graphDataPropertiesArray);
		}


		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartTable';
			let groupByFunction = null;
			let dateFormat;
			let reportSettingProp;
			let subReportResult;
			switch ($scope.periodicSubReport.code) {
				case "Daily":
					subReportResult = getSubReportMap($scope.subReport.code, true);
					setGraphData(subReportResult.subReportMap, subReportResult.graphDataProps);
					return;
				case "Weekly":
					subReportResult = getSubReportMap($scope.subReport.code, false);
					groupByFunction = dateGroupingService.groupByWeek;
					dateFormat = "dd-MMM-yyyy";
					reportSettingProp = 'week';
					break;
				case "Monthly":
					subReportResult = getSubReportMap($scope.subReport.code, false);
					groupByFunction = dateGroupingService.groupByMonth;
					dateFormat = "MMM-yyyy";
					reportSettingProp = 'month';
					break;
				case "Yearly":
					subReportResult = getSubReportMap($scope.subReport.code, false);
					groupByFunction = dateGroupingService.groupByYear;
					dateFormat = "yyyy";
					reportSettingProp = 'year';
					break;
				default:
					break;
			}

			const groupedMap = new Array();
			for (const key in subReportResult.subReportMap) {
				let obj = subReportResult.subReportMap[key];
				let mapKey = obj.date;
				if (!groupedMap[mapKey]) {
					groupedMap[mapKey] = {
						'projId': obj.projId,
						'projName': obj.projName,
						'values': new Array()
					};
				}
				groupedMap[mapKey].values.push(obj);
			}
			const periodicalReport = new Array();
			for (const key in groupedMap) {
				const object = groupedMap[key];
				let projReportSetting = getProjReportSettings(object.projId);
				const groupedList = groupByFunction(object.values, projReportSetting[reportSettingProp]);
				for (const group of groupedList) {
					if (!periodicalReport[group.date]) {
						periodicalReport[group.date] = {
							'code': null,
							'projName': object.projName,
							'plannedValue': 0,
							'actualHrs': 0,
							'earnedValue': 0,
							'cumulativePlanned': 0,
							'cumulativeActual': 0,
							'cumulativeEarned': 0
						}
					}
					periodicalReport[group.date].code =
						$filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
					for (const val of group.values) {
						periodicalReport[group.date].plannedValue += val.plannedValue;
						periodicalReport[group.date].actualHrs += val.actualHrs;
						periodicalReport[group.date].earnedValue += val.earnedValue;
					}
				}
			}

			let cumulativePlanned = 0;
			let cumulativeActual = 0;
			let cumulativeEarned = 0;
			const momentDateFormat = "ddd MMM DD YYYY";
			const sortedKeys = _.sortBy(Object.keys(periodicalReport), function (i) { return moment(i, momentDateFormat)._d });

			for (const prop of sortedKeys) {
				cumulativePlanned += periodicalReport[prop].plannedValue;
				cumulativeActual += periodicalReport[prop].actualHrs;
				cumulativeEarned += periodicalReport[prop].earnedValue;

				periodicalReport[prop].cumulativePlanned = cumulativePlanned;
				periodicalReport[prop].cumulativeActual = cumulativeActual;
				periodicalReport[prop].cumulativeEarned = cumulativeEarned;
			}

			setGraphData(periodicalReport, subReportResult.graphDataProps);
		};

		$scope.getManpowerManHrsDetails = function () {
			if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select the Project", 'Info');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate,
			};
			ManpowerReportService.getManpowerPlanActualEarnedReport(req).then(function (data) {
				for (const record of data)
					record.dateStr = $filter('date')((record.date), "dd-MMM-yyyy");
				manpowerList = angular.copy(data);
				$scope.manList = [];
				let count =0;
				for(let i=0;i<manpowerList.length;i++){
				if((manpowerList[i].actualHrs != 0) || (manpowerList[i].earnedValue != 0) || (manpowerList[i].plannedValue != 0)){
				$scope.manList.push(manpowerList[i]);
				}
				}
				$scope.gridOptions1.data = $scope.manList;
				if (data.length <= 0) {
					GenericAlertService.alertMessage("Manpower Plan vs Actual vs Earned Hours not available for the selected Search Criteria", 'Info');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Planned Vs ActualVs Earned details", 'Error');
			});

			ProjectSettingsService.projReportsOnLoad({ "projIds": $scope.selectedProjIds }).then(function (data) {
				projReportsSettings = data.projectReportsTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
			});

		};

		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'dateStr', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
					{ field: 'plannedValue', displayName: "Planned Hrs", headerTooltip: "Planned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'actualHrs', displayName: "Actual Hrs", headerTooltip: "Actual Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
					{ field: 'earnedValue', displayName: "Earned Hrs", headerTooltip: "Earned Hours", cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				]
				$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, [],"Reports_Manpower_Plan_Vs_Actual_VEH");
			}
		});

		function getProjReportSettings(projId) {
			for (const projSetting of projReportsSettings) {
				if (projSetting.projId == projId) {
					return projSetting;
				}
			}
			return null;
		}

		$scope.clearSubReportDetails = function () {
			manpowerList = [];
			$scope.labels = [];
			$scope.data = [];
			$scope.series = [];
			$scope.type = '';
			$scope.subReport = null;
			$scope.periodicSubReport = null;
		};

		$scope.resetManpowerData = function () {
			manpowerList = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.type = '';
			$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
			$scope.subReport = null;
			$scope.periodicSubReport = null;
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};

		function initGraph(graphDataPropertiesArray) {
			$scope.menuOptions = [];
			$scope.chart_type = 'bar';
			$scope.options = {
				indexLabel: "low",
				legend: { display: true },
				scales: {
					xAxes: [{
						stacked: false,
					}],
					yAxes: [{
						stacked: false,
						scaleLabel: {
							display: true,
							labelString: 'HOURS',
							fontSize: "15",
							fontColor: "#0000ff",
						}
					}]
				}
			};

			$scope.datasetOverride = new Array();
			for (const property of graphDataPropertiesArray) {
				if (property.includes('cumulative')) {
					$scope.datasetOverride.push({
						label: property,
						borderWidth: 3,
						type: 'line',
						fill: false,
					});
				} else {
					$scope.datasetOverride.push({
						label: property,
						borderWidth: 1,
						type: 'bar',
					});
				}
			}
		}
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
				template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowerplanactualearnedhelp.html',
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
