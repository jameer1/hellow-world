'use strict';
app.config(["$stateProvider", function($stateProvider) {
	/*dashboardProvider
	   .structure('6-6', {
	     rows: [{
	       columns: [{
	         styleClass: 'col-md-6'
	       }, {
	         styleClass: 'col-md-6'
	       }]
	     }]
	   });*/
	
	$stateProvider.state("masterdashboards", {
		url : '/masterdashboards',
		data : {
			dashboards : []
		},
		views : {
			'content@' : {
				'templateUrl' : 'views/dashboards/masterdashboard/masterdashboard.html',
				controller : 'MasterDashBoardController'
			}
		}
	})
/*}]).controller("MasterDashBoardController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "DashboardsSelectFactory", "CategorySelectFactory", 
	"GenericAlertService", "UserEPSProjectService", "$location", "EpsProjectMultiSelectFactory", "ProjectSettingsService", function($rootScope,$scope,$q, 
			$state, ngDialog,DashboardsSelectFactory,CategorySelectFactory, GenericAlertService, UserEPSProjectService, $location, EpsProjectMultiSelectFactory, 
			ProjectSettingsService) {*/
}]).controller("MasterDashBoardController", ["$rootScope", "$scope","$filter", "$q", "$state", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","PickTangibleFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
	"ProjectStatusService", "ProjectSettingsService","CategorySelectFactory", "DashboardsSelectFactory","uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "EpsProjectSelectFactory", "progressReportService","chartService", function($rootScope, $scope,$filter, $q, $state, ngDialog, 
			GenericAlertService, UserEPSProjectService, $location, PerformanceDashboardService, EpsProjectMultiSelectFactory,PickTangibleFactory, TreeService, 
			EstimateToCompleteService, ProjectScheduleService, ProjectStatusService, ProjectSettingsService,CategorySelectFactory, DashboardsSelectFactory,uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, EpsProjectSelectFactory, progressReportService,chartService) {
    chartService.getChartMenu($scope);

let selectedTangibleItems1 = [], projectTangibleTOs1 = [];
	let weekDays1 = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let weekBeginsOn1 = 'Monday';
    let weekEndsOn1 = 'Sunday';
    var series1 = ['Estimated Productivity','Actual Productivity','Cum Actual Productivity'];
    var series = [];
	$scope.timeScales1 = [
    	{name: 'Daily', code: "daily", filter: "dd-MMM-yyyy"}, 
    	{name: 'Weekly', code: "weekly", filter: "week"}, 
    	{name: 'Monthly', code: "monthly", filter: "MMM-yyyy"}, 
    	{name: 'Yearly', code: "yearly", filter: "yyyy"}
    ];
   
   $scope.selectedTimeScale1 = $scope.timeScales1[0];
   $scope.searchFilter2 = {};

	$scope.periodicReports = [
    	{name: 'Daily', code: "daily", filter: "dd-MMM-yyyy"}, 
    	{name: 'Weekly', code: "weekly", filter: "week"}, 
    	{name: 'Monthly', code: "monthly", filter: "MMM-yyyy"}, 
    	{name: 'Yearly', code: "yearly", filter: "yyyy"}
    ];
$scope.periodicReport = $scope.periodicReports[0];
    $scope.type = 'chart';
    $scope.scheduleDate;
    $scope.chartSeries = ['Budget', 'Earned To Date', 'Actual', 'Earned Forecast', 'Forecast To Complete'];
    $scope.chartOptions = {elements: {line: {fill: false, borderWidth: 5}}, legend: {display: true, position: 'top'}};
    $scope.datasetOverride = [
    	{pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{borderDash: [8,4], pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{borderDash: [8,4], pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    ]
    let columnDefs = [
		{ field: 'period', displayName: "Period - " + $scope.periodicReport.name, headerTooltip: 'Period' },
		{ field: 'planned', displayName: "Budget-Periodical", headerTooltip: 'Budget Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'plannedCummulative', displayName: "Budget-Cummulative", headerTooltip: 'Budget Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedActual', displayName: "Earned to date-Periodical", headerTooltip: 'Earned to date Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedActualCummulative', displayName: "Earned to date-Cummulative", headerTooltip: 'Earned to date Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'actual', displayName: "Actual to date-Periodical", headerTooltip: 'Actual to date Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'actualCummulative', displayName: "Actual to date-Cummulative", headerTooltip: 'Actual to date Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedForecast', displayName: "Earned Forecast-Periodical", headerTooltip: 'Earned Forecast Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedForecastCummulative', displayName: "Earned Forecast-Cummulative", headerTooltip: 'Earned Forecast Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'forecastToComplete', displayName: "Forecast to Complete-Periodical", headerTooltip: 'Forecast to Complete Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'forecastToCompleteCummulative', displayName: "Forecast to Complete-Cummulative", headerTooltip: 'Forecast to Complete Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
	];
	let progressSCurveTOs = [];
	let data = [];
    let weekDays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let weekBeginsOn = 'Monday';
    let weekEndsOn = 'Sunday';
	
    $scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Dashboards_Progress_SCurveCost");

	$scope.budgetTypes = [{ name: 'Manpower', code: 'projectManpower' }, { name: 'Cost', code: 'projectCost' }];
	$scope.budgetType = $scope.budgetTypes[1];	
		
		/*Projects Gantt Chart Start*/
			
		$scope.optionsGantt = {
			showGanttChart: false,
			currentDateValue: new Date(),
			currentDate: 'line',
			timeScalesGantt: [
				{header: ['month', 'day'], headerFormat: {month: 'MMMM YYYY', day: "D"}, type: "Day", selectedSize: 25, minSize: 15, maxSize: 40}, 
				{header: ['month', 'week'], headerFormat: {month: 'MMMM YYYY', week: function(column) {return column.date.format('D [-] ') + column.endDate.format('D'); }}, type: "Week", selectedSize: 20, minSize: 8, maxSize: 35}, 
				{header: ['month'], headerFormat: {month: 'MMMM YYYY'}, type: "Month", selectedSize: 15, minSize: 5, maxSize: 35},
				{header: ['year', 'quarter'], headerFormat: {'year': 'YYYY'}, type: "Quarter", selectedSize: 10, minSize: 3, maxSize: 25},
				{header: ['year'], headerFormat: {'year': 'YYYY', 'quarter': '[Q]Q YYYY'}, type: "Year", selectedSize: 10, minSize: 3, maxSize: 25},
			],
			columns: ['model.data.name', 'from', 'to', 'model.data.baselineStartDate', 'model.data.baselineFinishDate', 'model.data.progress'],
			columnsHeaders: {'model.data.name':'Project Name','from':'Start - Plan','to':'Finish - Plan','model.data.baselineStartDate':'Start - Actual', 'model.data.baselineFinishDate':'Finish - Actual', 'model.data.progress': 'Progress %'},
			columnsClasses: {'model.data.baselineStartDate':'gantt-column-baseline-start','model.data.baselineFinishDate':'gantt-column-baseline-finish', 'model.data.progress':'gantt-column-lead-lag'},
			columnsFormatters: {
				'from': function (from) {
					return from !== undefined ? from.format('ll') : undefined;
				},
			    'to': function (to) {
			        return to !== undefined ? to.format('ll') : undefined;
			    },
			    'model.data.baselineStartDate': function(baselineStartDate) {
			    	return baselineStartDate != null ? moment(baselineStartDate).format('ll') : undefined;
			    },
			    'model.data.baselineFinishDate': function(baselineFinishDate) {
			    	return baselineFinishDate != null ? moment(baselineFinishDate).format('ll') : undefined;
			    }
			}
		};
		
		$scope.optionsGantt.selectedTimeScaleGantt = $scope.optionsGantt.timeScalesGantt[1];
		$scope.searchFilterGantt = {};
		let prepareGanttChartData = function (projectGanttChartTOs) {
			let ganttChartData = [];
			for (let i=0; i<projectGanttChartTOs.length; i++) {
				ganttChartData.push({
					name: projectGanttChartTOs[i].name,
					height: "3em",
					data: {name: projectGanttChartTOs[i].name, baselineStartDate: projectGanttChartTOs[i].baselineStartDate, baselineFinishDate: projectGanttChartTOs[i].baselineFinishDate, progress: parseFloat(projectGanttChartTOs[i].progress).toFixed(2)},
					tasks: [{name: 'Current', color: '#9abb3a', classes: ['bar-margin', 'bar-height2'], from: projectGanttChartTOs[i].plannedStartDate, to: projectGanttChartTOs[i].plannedFinishDate, progress: {percent: projectGanttChartTOs[i].progress, color: '#336ab3'}},
						{name: 'Baseline', color: '#ffd1ad', classes: ['bar-margin', 'bar-height1'], from: projectGanttChartTOs[i].plannedStartDate, to: projectGanttChartTOs[i].plannedFinishDate}]
				});
			}
			$scope.data = ganttChartData;
			initiaizeGanttChartDates();
		};
		
		
		let initiaizeGanttChartDates = function() {
			   if (Array.isArray($scope.data) && $scope.data.length) {
				let minDate = null, maxDate = null;
				for (let i=0; i < $scope.data.length; i++) {
					if ($scope.data[i].hasOwnProperty('tasks')) {
						for (let j=0; j < $scope.data[i].tasks.length; j++) {
							if ($scope.data[i].tasks[j].hasOwnProperty('from')) {
								if (minDate == null)
									minDate = new Date($scope.data[i].tasks[j].from);
								else
									if (minDate > $scope.data[i].tasks[j].from)
										minDate = new Date($scope.data[i].tasks[j].from);
							}
							if ($scope.data[i].tasks[j].hasOwnProperty('to')) {
								if (maxDate == null)
									maxDate = new Date($scope.data[i].tasks[j].to);
								else
									if (maxDate < $scope.data[i].tasks[j].to)
										maxDate = new Date($scope.data[i].tasks[j].to);
							}
						}
					}
				}
				
				//Day
				let minDay = angular.copy(minDate);
				let maxDay = angular.copy(maxDate);
				minDay.setDate(minDay.getDate() - 1);
				maxDay.setDate(maxDay.getDate() + 1);
				$scope.optionsGantt.timeScalesGantt[0].fromDate = minDay;
				$scope.optionsGantt.timeScalesGantt[0].toDate = maxDay;
				//week
				let minWeek = angular.copy(minDate);
				let maxWeek = angular.copy(maxDate);
				minWeek.setDate(minWeek.getDate() - 3);
				maxWeek.setDate(maxWeek.getDate() + 3);
				$scope.optionsGantt.timeScalesGantt[1].fromDate = minWeek;
				$scope.optionsGantt.timeScalesGantt[1].toDate = maxWeek;
				//month
				let minMonth = angular.copy(minDate);
				let maxMonth = angular.copy(maxDate);
				minMonth.setDate(minMonth.getDate() - 7);
				maxMonth.setDate(maxMonth.getDate() + 7);
				$scope.optionsGantt.timeScalesGantt[2].fromDate = minMonth;
				$scope.optionsGantt.timeScalesGantt[2].toDate = maxMonth;
				//quarter
				let minQuarter = angular.copy(minDate);
				let maxQuarter = angular.copy(maxDate);
				minQuarter.setDate(minQuarter.getDate() - 10);
				maxQuarter.setDate(maxQuarter.getDate() + 10);
				$scope.optionsGantt.timeScalesGantt[3].fromDate = minQuarter;
				$scope.optionsGantt.timeScalesGantt[3].toDate = maxQuarter;
				//year
				let minYear = angular.copy(minDate);
				let maxYear = angular.copy(maxDate);
				minYear.setDate(minYear.getDate() - 15);
				maxYear.setDate(maxYear.getDate() + 15);
				$scope.optionsGantt.timeScalesGantt[4].fromDate = minYear;
				$scope.optionsGantt.timeScalesGantt[4].toDate = maxYear;
			}
		};
		
		/*Projects Gantt Chart End*/
		
		
		
	 $scope.getUserProjects = function () {
		EpsProjectSelectFactory.getEPSUserProjects().then(function (data) {			
			//console.log(data,"main");
			$scope.searchFilterGantt = {};
			$scope.searchFilterGantt.searchProject = data.searchProject;
			$scope.searchFilter1 = data.searchProject;			
			$scope.searchFilter = {};			
			$scope.selectedProj=[];
			$scope.searchFilter.searchProject = data.searchProject;
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds1= data.searchProject.projId;
			$scope.selectedProj.push($scope.selectedProjIds1);			
			$scope.selectedProjIds=$scope.selectedProj;
			$scope.selectedClientIds = data.searchProject.clientIds;	
				
				
			$scope.searchFilter2 = {};
			$scope.searchFilter2.searchProject = data.searchProject;
			progressReportService.getProjectsDatesForProgressSCurveReport({status: 1, projIds: [data.searchProject.projId]}).then(function (data) {
				for (let i=0; i < data.projStatusDatesTOs.length; i++) {
					if (data.projStatusDatesTOs[i].scheduleStartDate != null) {
						if ($scope.searchFilter2.projectFromDate == null) {
							$scope.searchFilter2.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						} else if ($scope.searchFilter2.projectFromDate > new Date(data.projStatusDatesTOs[i].scheduleStartDate)) {
							$scope.searchFilter2.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						}
					}
					if (data.projStatusDatesTOs[i].scheduleFinishDate != null) {
						if ($scope.searchFilter2.projectToDate == null) {
							$scope.searchFilter2.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						} else if ($scope.searchFilter2.projectToDate < new Date(data.projStatusDatesTOs[i].scheduleFinishDate)) {
							$scope.searchFilter2.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						}
					}
				}
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});
			ProjectSettingsService.projReportsOnLoad({projId: data.searchProject.projId, status: 1}).then(function (data) {
				weekBeginsOn1 = data.projectReportsTOs[0].week;
				if (weekDays1.indexOf(weekBeginsOn1) == 0)
					weekEndsOn1 = weekDays1[weekDays1.length - 1];
				else
					weekEndsOn1 = weekDays1[weekDays1.indexOf(weekBeginsOn1) - 1];
					//console.log(weekEndsOn1);				
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
			});
				
				
				
		   	$scope.searchFilterSk = {};
			$scope.searchFilterSk.searchProject = data.searchProject;
			progressReportService.getProjectsDatesForProgressSCurveReport({status: 1, projIds: [data.searchProject.projId]}).then(function (data) {
				for (let i=0; i < data.projStatusDatesTOs.length; i++) {
					if (data.projStatusDatesTOs[i].scheduleStartDate != null) {
						if ($scope.searchFilterSk.projectFromDate == null) {
							$scope.searchFilterSk.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						} else if ($scope.searchFilterSk.projectFromDate > new Date(data.projStatusDatesTOs[i].scheduleStartDate)) {
							$scope.searchFilterSk.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						}
					}
					if (data.projStatusDatesTOs[i].scheduleFinishDate != null) {
						if ($scope.searchFilterSk.projectToDate == null) {
							$scope.searchFilterSk.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						} else if ($scope.searchFilterSk.projectToDate < new Date(data.projStatusDatesTOs[i].scheduleFinishDate)) {
							$scope.searchFilterSk.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						}
					}
				}
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});		
				
				
			$scope.resetCurve();
			$scope.searchFilterCurve = data.searchProject;
			progressReportService.getProjectsDatesForProgressSCurveReport({status: 1, projIds: [data.searchProject.projId]}).then(function (data) {
				for (let i=0; i < data.projStatusDatesTOs.length; i++) {
					if (data.projStatusDatesTOs[i].scheduleStartDate != null) {
						if ($scope.searchFilterCurve.projectFromDate == null) {
							$scope.searchFilterCurve.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						} else if ($scope.searchFilterCurve.projectFromDate > new Date(data.projStatusDatesTOs[i].scheduleStartDate)) {
							$scope.searchFilterCurve.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						}
					}
					if (data.projStatusDatesTOs[i].scheduleFinishDate != null) {
						if ($scope.searchFilterCurve.projectToDate == null) {
							$scope.searchFilterCurve.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						} else if ($scope.searchFilterCurve.projectToDate < new Date(data.projStatusDatesTOs[i].scheduleFinishDate)) {
							$scope.searchFilterCurve.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						}
					}
				}
				$scope.searchFilterCurve.fromDate = $filter('date')($scope.searchFilterCurve.projectFromDate, "dd-MMM-yyyy");
				$scope.searchFilterCurve.toDate = $filter('date')($scope.searchFilterCurve.projectToDate, "dd-MMM-yyyy");
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});
			
			
						
			progressReportService.getProjectsDatesForProgressSCurveReport({status: 1, projIds: [data.searchProject.projId]}).then(function (data) {
				for (let i=0; i < data.projStatusDatesTOs.length; i++) {
					if (data.projStatusDatesTOs[i].scheduleStartDate != null) {
						if ($scope.searchFilter.projectFromDate == null) {
							$scope.searchFilter.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						} else if ($scope.searchFilter.projectFromDate > new Date(data.projStatusDatesTOs[i].scheduleStartDate)) {
							$scope.searchFilter.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						}
					}
					if (data.projStatusDatesTOs[i].scheduleFinishDate != null) {
						if ($scope.searchFilter.projectToDate == null) {
							$scope.searchFilter.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						} else if ($scope.searchFilter.projectToDate < new Date(data.projStatusDatesTOs[i].scheduleFinishDate)) {
							$scope.searchFilter.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						}
					}
				}
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});
						
			progressReportService.getProjectsDatesForProgressSCurveReport({status: 1, projIds: [data.searchProject.projId]}).then(function (data) {
				for (let i=0; i < data.projStatusDatesTOs.length; i++) {
					if (data.projStatusDatesTOs[i].scheduleStartDate != null) {
						if ($scope.searchFilter1.projectFromDate == null) {
							$scope.searchFilter1.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						} else if ($scope.searchFilter1.projectFromDate > new Date(data.projStatusDatesTOs[i].scheduleStartDate)) {
							$scope.searchFilter1.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						}
					}
					if (data.projStatusDatesTOs[i].scheduleFinishDate != null) {
						if ($scope.searchFilter1.projectToDate == null) {
							$scope.searchFilter1.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						} else if ($scope.searchFilter1.projectToDate < new Date(data.projStatusDatesTOs[i].scheduleFinishDate)) {
							$scope.searchFilter1.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						}
					}
				}
				$scope.searchFilter1.fromDate = $filter('date')($scope.searchFilter1.projectFromDate, "dd-MMM-yyyy");
				$scope.searchFilter1.toDate = $filter('date')($scope.searchFilter1.projectToDate, "dd-MMM-yyyy");
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}
	
	$scope.categoryDetails = [];
	$scope.getCategoryList = function() {
		var categoryPopUp = CategorySelectFactory.openPopup();
		categoryPopUp.then(function(data) {
			$scope.categoryDetails= data.selectedcategory;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  category  Details", 'Error');
		});
		
	}
	let selectedDashboards = [];
	$scope.selectedTitle = [];
	$scope.dashboardCode = [];
	$scope.getDashBoardList = function() {	
		if ($scope.categoryDetails.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one category to get the dashboard details", 'Warning');
				return;
	//	if ($scope.categoryDetails == undefined || $scope.categoryDetails== null) {
	 //  	GenericAlertService.alertMessage("Please select atleast one category to get the dashboard details", 'Warning');
		}
		var dashboardPopUp = DashboardsSelectFactory.openPopup($scope.categoryDetails);
		dashboardPopUp.then(function(data) {
			selectedDashboards = data;
			//console.log("$scope.selectedDetails", data);
			for (let i=0; i<data.length; i++) {
				//$scope.selectedDetails.selectedTitles += data[i].code + ", ";
				$scope.selectedTitle += data[i].code + ", ";
			}
			
			var lastChar = $scope.selectedTitle.slice(-1);
            if (lastChar == ',') {
            	$scope.answer = $scope.txtValue.slice(0, -1);
            }
            $scope.myVar = $scope.answer;
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while retrieving  dashboard  details", 'Error');
		});
	}
	
	
   var performanceList=[];
	var plannedVal=[];
	$scope.projCostStmtDtls = [];
	$scope.projCostValues = [];
	$scope.plannedValuesMap = [];
	
	$scope.labourCvSvTabs = new Array();
	$scope.budgetCountryTabs = new Array();
	$scope.budgetByProvinceTabs = new Array();
	$scope.budgetByProjTabs = new Array();
	$scope.budgetByProjMgrTabs = new Array();
	$scope.costCvSvBubbleTabs = new Array();
	$scope.acutlaCostByCountryTabs = new Array();
	$scope.acutlaCostByProvinceTabs = new Array();
	$scope.acutlaCostByProjectManagerTabs = new Array();
	$scope.acutlaCostByProjectTabs = new Array();
	$scope.earnedValueByCountryTabs = new Array();
	$scope.earnedValueByProvinceTabs = new Array();
	$scope.earnedValueByProjectTabs = new Array();
	$scope.earnedValueByProjectManagerTabs = new Array();
	$scope.remainingBudgetByCountryTabs = new Array();
	$scope.remainingBudgetByProvinceTabs = new Array();
	$scope.remainingBudgetByProjectTabs = new Array();
	$scope.remainingBudgetByProjectManagerTabs = new Array();
	$scope.estimateToCompleteByCountryTabs = new Array();
	$scope.estimateToCompleteByProvinceTabs = new Array();
	$scope.estimateToCompleteByProjectTabs = new Array();
	$scope.estimateToCompleteByProjMgrTabs = new Array();
	$scope.estimateCompletionByCountryTabs = new Array();
	$scope.estimateCompletionByProvinceTabs = new Array();
	$scope.estimateCompletionByProjTabs = new Array();
	$scope.estimateCompletionByProjMgrTabs = new Array();
	
			
	let task1Completed = false, task2Completed = false, taskPlannedValue = false, taskFull = false;
	
	$scope.getMasterDashboardDetails = function() {
		$scope.selectedDashboards = selectedDashboards;		
		if ($scope.searchFilterGantt.searchProject == null || $scope.selectedProjIds == 0) {
				GenericAlertService.alertMessage("Please select the Project", 'Info');
				return;
			}
			ProjectSettingsService.getProjectsGanttChartReportData({projIds: $scope.selectedProjIds}).then(function (data) {
				prepareGanttChartData(data.projectGanttChartTOs);
				$scope.optionsGantt.showGanttChart = $scope.data.length;
				if ($scope.data.length == 0) GenericAlertService.alertMessage("Resource Assignment Data Table does not exists for the selected projects", 'Info');
				if ($scope.data.length > 0 && $scope.data.length != $scope.selectedProjIds.length) GenericAlertService.alertMessage("Resource Assignment Data Table does not exists for one or more selected projects", 'Info');
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
			});
			
		task1Completed = false;
		task2Completed = false;
		taskPlannedValue = false;
		taskFull = false;
		var req = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		if (req.projIds == null) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		
		PerformanceDashboardService.getProjCostStatements(req).then(function (data) {
			//console.log(data.projCostStmtDtlTOs);
			let costData = populateCostDataCoun(data.projCostStmtDtlTOs, 0, []);
			costData.map((treeItem) => {
				$scope.costItemClick(treeItem, false);
			});
			let projCostStmtDtls = costData;
			if (projCostStmtDtls.length > 0 && projCostStmtDtls.find(x => x.item == true).estimateType &&
				projCostStmtDtls.find(x => x.item == true).estimateType.contains('SPI')) {
				ProjectScheduleService.getProjBudgetCostCodeDetails(req).then(function (data1) {
					$scope.projCostStmtDtls = projCostStmtDtls;
					calculatePlannedValues(data1, $scope.projCostStmtDtls, "costId");
					EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
					calculateCostTotal($scope.projCostStmtDtls);
					task1Completed = true;
					mergValues($scope.projCostValues, $scope.projProgressStatus);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while gettting Progress, Variance & Performance Indices details", 'Error');
				});
			} else {
				$scope.projCostStmtDtls = projCostStmtDtls;
				//console.log("$scope.projCostStmtDtls", $scope.projCostStmtDtls);
				EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
				calculateCostTotal($scope.projCostStmtDtls);
				task1Completed = true;
				mergValues($scope.projCostValues, $scope.projProgressStatus);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting Cost details", 'Error');
		});
		ProjectStatusService.getProjStatusDates(req).then(function (data) {
			$scope.projProgressStatus = data.projStatusDatesTOs;
			task2Completed = true;
			mergValues($scope.projCostValues, $scope.projProgressStatus);
			//console.log($scope.projProgressStatus);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Progress Status", "Error");
		});
		ProjectSettingsService.getProjPerformenceThreshold(req).then(function (data) {
			$scope.performenceThresholdData = data.projPerformenceThresholdTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
		});
		ProjectSettingsService.getProjPlannedValue(req).then(function (data) {
			$scope.plannedValuesMap = data.projPlannedValueTO;
			//console.log("$scope.plannedValuesMap",$scope.plannedValuesMap);
			taskPlannedValue = true;
			mergValues($scope.projCostValues, $scope.projProgressStatus);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Planned Value", "Error");
		});
		
		PerformanceDashboardService.getPerformanceIndex(req).then(function (data) {
			//console.log("data.projManpowerTOs",data.projManpowerTOs);
			if (data.projManpowerTOs.length > 0 && data.projManpowerTOs[0] && data.projManpowerTOs[0].estimateType
				&& data.projManpowerTOs[0].estimateType.contains('SPI')) {
				//console.log("if");
				ProjectScheduleService.getProjBudgetManPowerDetails(req).then(function (data1) {
					//console.log(data1);
					$scope.projManpowerDetails = data.projManpowerTOs;
					calculatePlannedValues(data1, $scope.projManpowerDetails, "empClassId");
					EstimateToCompleteService.manpower($scope.projManpowerDetails);
					calculateManhoursTotal($scope.projManpowerDetails);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
				});
			} else {
				//console.log("else");
				$scope.projManpowerDetails = data.projManpowerTOs;
				EstimateToCompleteService.manpower($scope.projManpowerDetails);
				calculateManhoursTotal($scope.projManpowerDetails);
				//console.log("$scope.projManpowerDetails", $scope.projManpowerDetails);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting manpower CostCode Wise details", 'Error');
		});
		
		ProjectStatusService.getMultiProjGenerals(req).then(function (data) {
			$scope.projGeneralValuesCoun = data.projGeneralMstrTOs;
			//console.log("$scope.projGeneralValuesCoun",$scope.projGeneralValuesCoun);
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
        });
		
		
		
		/*S Curve - Cost*/
		
		if ($scope.searchFilter1 == null) {
			GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
			return;
		}
		progressReportService.getProgressSCurveReportData({projIds: [$scope.searchFilter1.projId], fromDate: $scope.searchFilter1.fromDate, toDate: $scope.searchFilter1.toDate}).then(function (data){
			progressSCurveTOs = data.progressSCurveTOs;
			$scope.scheduleDate = data.scheduleDate;
			prepareReport();
			if (progressSCurveTOs.length == 0)
				GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
		}, function(error){
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		})
		
		
		/*S Curve - Labour*/
			
		if ($scope.searchFilterCurve == null) {
			GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
			return;
		}
		progressReportService.getProgressSCurveManpowerReportData({projIds: [$scope.searchFilterCurve.projId], fromDate: $scope.searchFilterCurve.fromDate, toDate: $scope.searchFilterCurve.toDate}).then(function (data){
			progressSCurveTOsCurve = data.progressSCurveTOs;
			$scope.scheduleDateCurve = data.scheduleDate;
			prepareReportCurve();
			if (progressSCurveTOsCurve.length == 0)
				GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
		}, function(error){
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		})
		
	}
	
	
	
	function mergValues(projCostValues, projProgressStatus) {
		var thresholdData = $scope.performenceThresholdData;
		if (task1Completed && task2Completed && taskPlannedValue) {
			$scope.progressVarienceData = new Array();
			for (const index in projCostValues) {
				for (const index1 in projProgressStatus) {
					if (projProgressStatus[index1].projId == projCostValues[index].projId) {
						projCostValues[index].currentPhase = projProgressStatus[index1].currentPhase;
						projCostValues[index].actualStartDate = projProgressStatus[index1].startDate;
						projCostValues[index].actualFinishDate = projProgressStatus[index1].finishDate;
						projCostValues[index].baselineStartDate = projProgressStatus[index1].scheduleStartDate;
						projCostValues[index].baselineFinishDate = projProgressStatus[index1].scheduleFinishDate;
						projCostValues[index].forecastStartDate = projProgressStatus[index1].forecastStartDate;
						projCostValues[index].forecastFinishDate = projProgressStatus[index1].forecastFinishDate;
					}
				}
				projCostValues[index].epsName=$scope.searchProject.parentName.split(',')[index];
				projCostValues[index].projName=$scope.searchProject.projName.split(',')[index];
				$scope.progressVarienceData.push(projCostValues[index]);
			}
			//console.log("progressVarienceData", progressVarienceData);
			var progVarienceData = $scope.progressVarienceData;
			//console.log("progVarienceData", progVarienceData);
			$scope.progVarienceDataInfo = new Array();
			for (const index2 in progVarienceData) {
				for (const index3 in thresholdData) {
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Exceptional") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].exTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].exTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].excpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].excpiUpperLimit = thresholdData[index3].cpiUpperLimit;
						progVarienceData[index2].excvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].excvUpperLimit = thresholdData[index3].cvUpperLimit;
						progVarienceData[index2].exspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].exspiUpperLimit = thresholdData[index3].spiUpperLimit;
						progVarienceData[index2].exsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].exsvUpperLimit = thresholdData[index3].svUpperLimit;
					} 
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Acceptable") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].acTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accpiUpperLimit = parseFloat((thresholdData[index3].cpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accvUpperLimit = parseFloat((thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acspiUpperLimit = parseFloat((thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acsvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
					}
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Warning") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].waTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].waTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacpiUpperLimit = parseFloat((thresholdData[index3].cpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacvUpperLimit = parseFloat((thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].waspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].waspiUpperLimit = parseFloat((thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wasvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wasvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
					}
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Critical") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].crTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcpiUpperLimit = parseFloat((thresholdData[index3].cpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcvUpperLimit = parseFloat((thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crspiUpperLimit = parseFloat((thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crsvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
					}
				}
				$scope.progVarienceDataInfo.push(progVarienceData[index2]);
			}
			
			var budgetDetails = $scope.progVarienceDataInfo;
			var projGeneral = $scope.projGeneralValuesCoun;
			
			$scope.budgetForAllDashboardDetails = new Array();
			for (const index_3 in budgetDetails){
				for (const index_4 in projGeneral) {
					if(projGeneral[index_4].projId == budgetDetails[index_3].projId) {
						budgetDetails[index_3].currency = projGeneral[index_4].currency
						budgetDetails[index_3].countryName = projGeneral[index_4].countryName
						budgetDetails[index_3].provisionName = projGeneral[index_4].provisionName
						budgetDetails[index_3].name = projGeneral[index_4].userLabelKeyTO.name;
						budgetDetails[index_3].code = projGeneral[index_4].projId;
					}
				}
				$scope.budgetForAllDashboardDetails.push(budgetDetails[index_3]);
				
			}
			
			if ($scope.selectedTitle.includes("Budget_By_Country")) {
				mergeValForCountry($scope.budgetForAllDashboardDetails);
			} 
			if($scope.selectedTitle.includes("Budget_By_Province")) {
				mergeValForProvince($scope.budgetForAllDashboardDetails);
			} 
			if($scope.selectedTitle.includes("Budget_By_Project")) {
				mergeValForProject($scope.budgetForAllDashboardDetails);
			} 
			if ($scope.selectedTitle.includes("Budget_By_Project_Manager")) {
				mergeValForProjectManager($scope.budgetForAllDashboardDetails);
			} 
			
			if ($scope.selectedTitle.includes("Actual_Cost_By_Country")) {
				mergeValForActCostCountry($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Actual_Cost_By_Province")) {
				mergeValForActCostProvince($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Actual_Cost_By_Project")) {
				mergeValForActCostProject($scope.budgetForAllDashboardDetails);
			} 
			if ($scope.selectedTitle.includes("Actual_Cost_By_Project_Manager")) {
				mergeValForActCostProjectManager($scope.budgetForAllDashboardDetails);
			}
			
			if ($scope.selectedTitle.includes("Earned_Value_By_Country")) {
				mergeValForEarnedValCountry($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Earned_Value_By_Province")) {
				mergeValForEarnedValProvince($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Earned_Value_By_Project")) {
				mergeValForEarnedValProject($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Earned_Value_By_Project_Manager")) {
				mergeValForEarnedValProjManager($scope.budgetForAllDashboardDetails);
			}
			
			if ($scope.selectedTitle.includes("Remaining_Budget_By_Country")) {
				mergeValForRemBudgetCountry($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Remaining_Budget_By_Province")) {
				mergeValForRemBudgetProvince($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Remaining_Budget_By_Project")) {
				mergeValForRemBudgetProject($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Remaining_Budget_By_Project_Manager")) {
				mergeValForRemBudgetProjManager($scope.budgetForAllDashboardDetails);
			}
			
			if ($scope.selectedTitle.includes("Estimate_To_Complete_By_Country")) {
				mergeValForEstToCompCountry($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Estimate_To_Complete_By_Province")) {
				mergeValForEstToCompProvince($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Estimate_To_Complete_By_Project")) {
				mergeValForEstToCompProject($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Estimate_To_Complete_By_Project_Manager")) {
				mergeValForEstToCompProjManager($scope.budgetForAllDashboardDetails);
			}
			
			if ($scope.selectedTitle.includes("Estimate_At_Completion_By_Country")) {
				mergeValForEstAtCompletionCountry($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Estimate_At_Completion_By_Province")) {
				mergeValForEstAtCompletionProvince($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Estimate_At_Completion_By_Project")) {
				mergeValForEstAtCompletionProject($scope.budgetForAllDashboardDetails);
			}
			if ($scope.selectedTitle.includes("Estimate_At_Completion_By_Project_Manager")) {
				mergeValForEstAtCompletionProjManager($scope.budgetForAllDashboardDetails);
			}
			
			
			
			taskFull = true;
			mergePlannedValue($scope.progVarienceDataInfo, $scope.plannedValuesMap);
		}
	}
	
	function mergeValForCountry(budgetByCountry) {
		let newData = [];
		for (let i=0; i < budgetByCountry.length; i++) {
			let index = newData.findIndex(e => e.countryName == budgetByCountry[i].countryName);
			if (index == -1) {
				newData.push({"countryName": budgetByCountry[i].countryName, "budget" : budgetByCountry[i].originalBudgetTotal});
			} else {
				newData[index].budget += budgetByCountry[i].originalBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataBudgetCountry(newData);
	}
	
	function mergeValForProvince(budgetByProvince) {
		//console.log("budgetByProvince", budgetByProvince);
		let newData = [];
		for (let i=0; i < budgetByProvince.length; i++) {
			let index = newData.findIndex(e => e.provisionName == budgetByProvince[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": budgetByProvince[i].provisionName, "budget" : budgetByProvince[i].originalBudgetTotal});
			} else {
				newData[index].budget += budgetByProvince[i].originalBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataBudgetProvince(newData);
	}
	
	function mergeValForProject(budgetByProject) {
		//console.log("budgetByProject", budgetByProject);
		let newData = [];
		for (let i=0; i < budgetByProject.length; i++) {
			let index = newData.findIndex(e => e.code == budgetByProject[i].code);
			if (index == -1) {
				newData.push({"code": budgetByProject[i].code, "budget" : budgetByProject[i].originalBudgetTotal});
			} else {
				newData[index].budget += budgetByProject[i].originalBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataBudgetProject(newData);
	}
	
	function mergeValForProjectManager(budgetByProjectManager) {
		//console.log("budgetByProjectManager", budgetByProjectManager);
		let newData = [];
		for (let i=0; i < budgetByProjectManager.length; i++) {
			let index = newData.findIndex(e => e.name == budgetByProjectManager[i].name);
			if (index == -1) {
				newData.push({"name": budgetByProjectManager[i].name, "budget" : budgetByProjectManager[i].originalBudgetTotal});
			} else {
				newData[index].budget += budgetByProjectManager[i].originalBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataBudgetProjectManager(newData);
	}
	
	function mergeValForActCostCountry(ActValByCountry) {
		let newData = [];
		for (let i=0; i < ActValByCountry.length; i++) {
			let index = newData.findIndex(e => e.countryName == ActValByCountry[i].countryName);
			if (index == -1) {
				newData.push({"countryName": ActValByCountry[i].countryName, "actualCost" : ActValByCountry[i].actualCostTotal});
			} else {
				newData[index].actualCost += ActValByCountry[i].actualCostTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataActCostCountry(newData);
	}
	
	function mergeValForActCostProvince(ActValByProvince) {
		let newData = [];
		for (let i=0; i < ActValByProvince.length; i++) {
			let index = newData.findIndex(e => e.provisionName == ActValByProvince[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": ActValByProvince[i].provisionName, "actualCost" : ActValByProvince[i].actualCostTotal});
			} else {
				newData[index].actualCost += ActValByProvince[i].actualCostTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataActCostProvince(newData);
	}
	
	function mergeValForActCostProject(ActValByPrject) {
		let newData = [];
		for (let i=0; i < ActValByPrject.length; i++) {
			let index = newData.findIndex(e => e.code == ActValByPrject[i].code);
			if (index == -1) {
				newData.push({"code": ActValByPrject[i].code, "actualCost" : ActValByPrject[i].actualCostTotal, "currency" : ActValByPrject[i].currency});
			} else {
				newData[index].actualCost += ActValByPrject[i].actualCostTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataActCostProject(newData);
		
	}
	
	function mergeValForActCostProjectManager(ActValByProManager) {
		let newData = [];
		for (let i=0; i < ActValByProManager.length; i++) {
			let index = newData.findIndex(e => e.name == ActValByProManager[i].name);
			if (index == -1) {
				newData.push({"name": ActValByProManager[i].name, "actualCost" : ActValByProManager[i].actualCostTotal});
			} else {
				newData[index].actualCost += ActValByProManager[i].actualCostTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataActCostPrjManager(newData);
	}
	
	function mergeValForEarnedValCountry(EarnedValByCountry) {
		let newData = [];
		for (let i=0; i < EarnedValByCountry.length; i++) {
			let index = newData.findIndex(e => e.countryName == EarnedValByCountry[i].countryName);
			if (index == -1) {
				newData.push({"countryName": EarnedValByCountry[i].countryName, "earnedValue" : EarnedValByCountry[i].earnedValueTotal});
			} else {
				newData[index].earnedValue += EarnedValByCountry[i].earnedValueTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEarnedValCountry(newData);
	}
	
	function mergeValForEarnedValProvince(EarnedValByProvince) {
		let newData = [];
		for (let i=0; i < EarnedValByProvince.length; i++) {
			let index = newData.findIndex(e => e.provisionName == EarnedValByProvince[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": EarnedValByProvince[i].provisionName, "earnedValue" : EarnedValByProvince[i].earnedValueTotal});
			} else {
				newData[index].earnedValue += EarnedValByProvince[i].earnedValueTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEarnedValProvince(newData);
	}
	
	function mergeValForEarnedValProject(EarnedValByProject) {
		let newData = [];
		for (let i=0; i < EarnedValByProject.length; i++) {
			let index = newData.findIndex(e => e.code == EarnedValByProject[i].code);
			if (index == -1) {
				newData.push({"code": EarnedValByProject[i].code, "earnedValue" : EarnedValByProject[i].earnedValueTotal});
			} else {
				newData[index].earnedValue += EarnedValByProject[i].earnedValueTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEarnedValProject(newData);
	}
	
	function mergeValForEarnedValProjManager(EarnedValByProjManager) {
		let newData = [];
		for (let i=0; i < EarnedValByProjManager.length; i++) {
			let index = newData.findIndex(e => e.name == EarnedValByProjManager[i].name);
			if (index == -1) {
				newData.push({"name": EarnedValByProjManager[i].name, "earnedValue" : EarnedValByProjManager[i].earnedValueTotal});
			} else {
				newData[index].earnedValue += EarnedValByProjManager[i].earnedValueTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEarnedValProjectManager(newData);
	}
	
	function mergeValForRemBudgetCountry(remBudgetByCountry) {
		let newData = [];
		for (let i=0; i < remBudgetByCountry.length; i++) {
			let index = newData.findIndex(e => e.countryName == remBudgetByCountry[i].countryName);
			if (index == -1) {
				newData.push({"countryName": remBudgetByCountry[i].countryName, "remainingBudget" : remBudgetByCountry[i].remainingBudgetTotal});
			} else {
				newData[index].remainingBudget += remBudgetByCountry[i].remainingBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataRemBudgetCountry(newData);
	}

	function mergeValForRemBudgetProvince(remBudgetByProvince) {
		let newData = [];
		for (let i=0; i < remBudgetByProvince.length; i++) {
			let index = newData.findIndex(e => e.provisionName == remBudgetByProvince[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": remBudgetByProvince[i].provisionName, "remainingBudget" : remBudgetByProvince[i].remainingBudgetTotal});
			} else {
				newData[index].remainingBudget += remBudgetByProvince[i].remainingBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataRemBudgetProvince(newData);
	}
	
	function mergeValForRemBudgetProject(remBudgetByProject) {
		let newData = [];
		for (let i=0; i < remBudgetByProject.length; i++) {
			let index = newData.findIndex(e => e.code == remBudgetByProject[i].code);
			if (index == -1) {
				newData.push({"code": remBudgetByProject[i].code, "remainingBudget" : remBudgetByProject[i].remainingBudgetTotal});
			} else {
				newData[index].remainingBudget += remBudgetByProject[i].remainingBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataRemBudgetProject(newData);
	}
	
	function mergeValForRemBudgetProjManager(remBudgetByProjManager) {
		let newData = [];
		for (let i=0; i < remBudgetByProjManager.length; i++) {
			let index = newData.findIndex(e => e.name == remBudgetByProjManager[i].name);
			if (index == -1) {
				newData.push({"name": remBudgetByProjManager[i].name, "remainingBudget" : remBudgetByProjManager[i].remainingBudgetTotal});
			} else {
				newData[index].remainingBudget += remBudgetByProjManager[i].remainingBudgetTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataRemBudgetProjManager(newData);
	}
	
	function mergeValForEstToCompCountry(estToCompByCountry) {
		let newData = [];
		for (let i=0; i < estToCompByCountry.length; i++) {
			let index = newData.findIndex(e => e.countryName == estToCompByCountry[i].countryName);
			if (index == -1) {
				newData.push({"countryName": estToCompByCountry[i].countryName, "estToComplete" : estToCompByCountry[i].etcTotal});
			} else {
				newData[index].estToComplete += estToCompByCountry[i].etcTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstToCompCountry(newData);
	}
	
	function mergeValForEstToCompProvince(estToCompByProvince) {
		let newData = [];
		for (let i=0; i < estToCompByProvince.length; i++) {
			let index = newData.findIndex(e => e.provisionName == estToCompByProvince[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": estToCompByProvince[i].provisionName, "estToComplete" : estToCompByProvince[i].etcTotal});
			} else {
				newData[index].estToComplete += estToCompByProvince[i].etcTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstToCompProvince(newData);
	}
	
	function mergeValForEstToCompProject(estToCompByProject) {
		let newData = [];
		for (let i=0; i < estToCompByProject.length; i++) {
			let index = newData.findIndex(e => e.code == estToCompByProject[i].code);
			if (index == -1) {
				newData.push({"code": estToCompByProject[i].code, "estToComplete" : estToCompByProject[i].etcTotal});
			} else {
				newData[index].estToComplete += estToCompByProject[i].etcTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstToCompProject(newData);
	}
	
	function mergeValForEstToCompProjManager(estToCompByProjManager) {
		let newData = [];
		for (let i=0; i < estToCompByProjManager.length; i++) {
			let index = newData.findIndex(e => e.name == estToCompByProjManager[i].name);
			if (index == -1) {
				newData.push({"name": estToCompByProjManager[i].name, "estToComplete" : estToCompByProjManager[i].etcTotal});
			} else {
				newData[index].estToComplete += estToCompByProjManager[i].etcTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstToCompProjManager(newData);
	}
	
	function mergeValForEstAtCompletionCountry(estAtCompletionByCountry) {
		//console.log("estAtCompletionByCountry",estAtCompletionByCountry);
		let newData = [];
		for (let i=0; i < estAtCompletionByCountry.length; i++) {
			let index = newData.findIndex(e => e.countryName == estAtCompletionByCountry[i].countryName);
			if (index == -1) {
				newData.push({"countryName": estAtCompletionByCountry[i].countryName, "estAtCompletion" : estAtCompletionByCountry[i].estAtCompletionTotal});
			} else {
				newData[index].estAtCompletion += estAtCompletionByCountry[i].estAtCompletionTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstAtCompletionCountry(newData);
	}
	
	function mergeValForEstAtCompletionProvince(estAtCompletionByProvince) {
		console.log("estAtCompletionByProvince",estAtCompletionByProvince);
		let newData = [];
		for (let i=0; i < estAtCompletionByProvince.length; i++) {
			let index = newData.findIndex(e => e.provisionName == estAtCompletionByProvince[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": estAtCompletionByProvince[i].provisionName, "estAtCompletion" : estAtCompletionByProvince[i].estAtCompletionTotal});
			} else {
				newData[index].estAtCompletion += estAtCompletionByProvince[i].estAtCompletionTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstAtCompletionProvince(newData);
	}
	
	function mergeValForEstAtCompletionProject(estAtCompletionByProject) {
		//console.log("estAtCompletionByProject",estAtCompletionByProject);
		let newData = [];
		for (let i=0; i < estAtCompletionByProject.length; i++) {
			let index = newData.findIndex(e => e.code == estAtCompletionByProject[i].code);
			if (index == -1) {
				newData.push({"code": estAtCompletionByProject[i].code, "estAtCompletion" : estAtCompletionByProject[i].estAtCompletionTotal});
			} else {
				newData[index].estAtCompletion += estAtCompletionByProject[i].estAtCompletionTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstAtCompletionProject(newData);
	}
	
	function mergeValForEstAtCompletionProjManager(estAtCompletionByProjManager) {
		//console.log("estAtCompletionByProjManager",estAtCompletionByProjManager);
		let newData = [];
		for (let i=0; i < estAtCompletionByProjManager.length; i++) {
			let index = newData.findIndex(e => e.name == estAtCompletionByProjManager[i].name);
			if (index == -1) {
				newData.push({"name": estAtCompletionByProjManager[i].name, "estAtCompletion" : estAtCompletionByProjManager[i].estAtCompletionTotal});
			} else {
				newData[index].estAtCompletion += estAtCompletionByProjManager[i].estAtCompletionTotal;
			}
		}
		//console.log("NEWDATA",newData);
		setGraphDataEstAtCompletionProjManager(newData);
	}
	
	function mergePlannedValue(progVarienceDataInfo, plannedValuesMap) { 
		//console.log("MERGING PLANNED VALUE1");
		//console.log("taskPlannedValue", taskPlannedValue);
		//console.log("taskFull", taskFull);
		if (taskPlannedValue && taskFull) {
			//console.log("MERGING PLANNED VALUE2");
			$scope.costSchedVarienceData = new Array();
			for (const index_1 in progVarienceDataInfo) {
				for (const index_2 in plannedValuesMap) {
					if(plannedValuesMap[index_2].projId == progVarienceDataInfo[index_1].projId) {
						progVarienceDataInfo[index_1].plannedCost = plannedValuesMap[index_2].cost;
						progVarienceDataInfo[index_1].plannedDirectManHours = plannedValuesMap[index_2].directManHours;
					}
				}
				progVarienceDataInfo[index_1].schedulePerformanceIndex = progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].plannedCost;
				//progVarienceDataInfo[index_1].schedulePerformanceIndexPercentage=SFI= (EV-PV)/PV*100%
				progVarienceDataInfo[index_1].scheduleVarience = progVarienceDataInfo[index_1].earnedValueTotal - progVarienceDataInfo[index_1].plannedCost;
				progVarienceDataInfo[index_1].scheduleVarienceCost = progVarienceDataInfo[index_1].earnedValueTotal - progVarienceDataInfo[index_1].plannedCost;
				progVarienceDataInfo[index_1].scheduleVarienceCostPercentage = (progVarienceDataInfo[index_1].scheduleVarienceCost / progVarienceDataInfo[index_1].plannedCost) * 100;
				progVarienceDataInfo[index_1].spiCost = progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].plannedCost;
				
				if (progVarienceDataInfo[index_1].revisedBudgetTotal > 0) {
					progVarienceDataInfo[index_1].actualProgress = (progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].revisedBudgetTotal)*100;
					progVarienceDataInfo[index_1].plannedProgress = (progVarienceDataInfo[index_1].plannedCost / progVarienceDataInfo[index_1].revisedBudgetTotal)*100;
				} else {
					progVarienceDataInfo[index_1].actualProgress = (progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].originalBudgetTotal)*100;
					progVarienceDataInfo[index_1].plannedProgress = (progVarienceDataInfo[index_1].plannedCost / progVarienceDataInfo[index_1].originalBudgetTotal)*100;
				}
				
				// Cost Varience
				if(progVarienceDataInfo[index_1].costVarience > progVarienceDataInfo[index_1].excvLowerLimit
						&& progVarienceDataInfo[index_1].excvUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].cvExceptional = true;
				}
				if(progVarienceDataInfo[index_1].costVarience > progVarienceDataInfo[index_1].accvLowerLimit
						&& progVarienceDataInfo[index_1].costVarience <= progVarienceDataInfo[index_1].accvUpperLimit) {
					progVarienceDataInfo[index_1].cvAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].costVarience > progVarienceDataInfo[index_1].wacvLowerLimit
						&& progVarienceDataInfo[index_1].costVarience <= progVarienceDataInfo[index_1].wacvUpperLimit) {
					progVarienceDataInfo[index_1].cvWarning = true;
				}
				if(progVarienceDataInfo[index_1].costVarience <= progVarienceDataInfo[index_1].crcvLowerLimit) {
					progVarienceDataInfo[index_1].cvCritical = true;
				}
				// Schedule Varience
				if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].exsvLowerLimit
						&& progVarienceDataInfo[index_1].exsvUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].svExceptional = true;
				}
				if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].acsvLowerLimit
						&& progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].acsvUpperLimit) {
					progVarienceDataInfo[index_1].svAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].wasvLowerLimit
						&& progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].wasvUpperLimit) {
					progVarienceDataInfo[index_1].svWarning = true;
				}
				if(progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].crsvLowerLimit) {
					progVarienceDataInfo[index_1].svCritical = true;
				}
				//Schedule Performance Index
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex > progVarienceDataInfo[index_1].exspiLowerLimit
						&& progVarienceDataInfo[index_1].exspiUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].spiExceptional = true;
				}
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex > progVarienceDataInfo[index_1].acspiLowerLimit
						&& progVarienceDataInfo[index_1].schedulePerformanceIndex <= progVarienceDataInfo[index_1].acspiUpperLimit) {
					progVarienceDataInfo[index_1].spiAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex > progVarienceDataInfo[index_1].waspiLowerLimit
						&& progVarienceDataInfo[index_1].schedulePerformanceIndex <= progVarienceDataInfo[index_1].waspiUpperLimit) {
					progVarienceDataInfo[index_1].spiWarning = true;
				}
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex <= progVarienceDataInfo[index_1].crspiLowerLimit) {
					progVarienceDataInfo[index_1].spiCritical = true;
				}
				// Cost Performance Index
				if(progVarienceDataInfo[index_1].costPerformanceIndex > progVarienceDataInfo[index_1].excpiLowerLimit
						&& progVarienceDataInfo[index_1].excpiUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].cpiExceptional = true;
				}
				if(progVarienceDataInfo[index_1].costPerformanceIndex > progVarienceDataInfo[index_1].accpiLowerLimit
						&& progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].accpiUpperLimit) {
					progVarienceDataInfo[index_1].cpiAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].costPerformanceIndex > progVarienceDataInfo[index_1].wacpiLowerLimit
						&& progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].wacpiUpperLimit) {
					progVarienceDataInfo[index_1].cpiWarning = true;
				}
				if(progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].crcpiLowerLimit) {
					progVarienceDataInfo[index_1].cpiCritical = true;
				}
				
				// To Complete Performance Index
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].exTcpiUpperLimit
						&& progVarienceDataInfo[index_1].exTcpiUpperLimit < progVarienceDataInfo[index_1].exTcpiLowerLimit) {
					console.log("EXCEPTIONAL");
					progVarienceDataInfo[index_1].TcpiExceptional = true;
				}
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].acTcpiUpperLimit
						&& progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex < progVarienceDataInfo[index_1].acTcpiLowerLimit) {
					progVarienceDataInfo[index_1].TcpiAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].waTcpiUpperLimit
						&& progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].waTcpiLowerLimit) {
					progVarienceDataInfo[index_1].TcpiWarning = true;
				}
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].crTcpiLowerLimit) {
					//console.log("CRITICAL");
					progVarienceDataInfo[index_1].TcpiCritical = true;
				}
				progVarienceDataInfo[index_1].schedulePerformanceIndexPercentage = (progVarienceDataInfo[index_1].schedulePerformanceIndex / progVarienceDataInfo[index_1].plannedCost)*100;
				$scope.costSchedVarienceData.push(progVarienceDataInfo[index_1]);
			}
			//console.log("FINAL", $scope.costSchedVarienceData);
		$scope.gridOptions13.data = angular.copy($scope.costSchedVarienceData);

		}
		mergeCostManHours($scope.costSchedVarienceData, $scope.projManPowerValues);
	}
	
	function mergeCostManHours(costData, manPowerData) {
		$scope.progVarPerIdxData = new Array();
		$scope.costSchedPerformanceData = new Array();
		$scope.costSchedVarienceInfo= new Array();
		$scope.costSchedVariencePercentage = new Array();
		$scope.costSchedPerformanceIndex = new Array();
		$scope.performanceIndexInfo = new Array();
		$scope.actualPlannedProgressData = new Array();
		$scope.cvSvManHoursBubbleChart = new Array();
		$scope.currDatesProgPercentageData = new Array();
		$scope.costHealthCheck = new Array();
		$scope.costCvSvBubbleChart = new Array();
		$scope.planVsActualVsEarned = new Array();
		$scope.costWorkSheet = new Array();
		$scope.labourHealthCheck = new Array();
		$scope.manPowerValues = new Array();
		$scope.planActualEarnedDirManHrs = new Array();
		$scope.masterDashboardInfo = new Array();
		for (const index_3 in costData) {
			for (const index_4 in manPowerData) {
				if(manPowerData[index_4].projId == costData[index_3].projId) {
					costData[index_3].originalHrsTotal = manPowerData[index_4].originalHrsTotal;
					if ($scope.selectedTitle.includes("Original_Vs_Estimate_At_Completion_Man_Hours")) {
						costData[index_3].etcHrsTotal = manPowerData[index_4].etcHrsTotal;
						costData[index_3].estAtCompletionHrsTotal = manPowerData[index_4].estAtCompletionHrsTotal;
					}
					costData[index_3].revisedHrsTotal = manPowerData[index_4].revisedHrsTotal;
					costData[index_3].earnedManHoursTotal = manPowerData[index_4].earnedManHoursTotal;
					costData[index_3].actualHrsTotal = manPowerData[index_4].actualHrsTotal;
					costData[index_3].scheduleVarienceDirectManHours = costData[index_3].earnedManHoursTotal - costData[index_3].plannedDirectManHours;
					costData[index_3].costVarienceManHours = costData[index_3].earnedManHoursTotal - costData[index_3].actualHrsTotal;
					costData[index_3].schedVarDirManHrsPercentage = (costData[index_3].scheduleVarienceDirectManHours / costData[index_3].plannedDirectManHours)*100;
					costData[index_3].costVarManHrsPercentage = (costData[index_3].costVarienceManHours / costData[index_3].earnedManHoursTotal)*100;
					costData[index_3].spiDirManHrs = costData[index_3].earnedManHoursTotal / costData[index_3].plannedDirectManHours;
					costData[index_3].cpiManHrs = costData[index_3].earnedManHoursTotal / costData[index_3].actualHrsTotal;
					costData[index_3].manPowerPI = (manPowerData[index_4].estAtCompletionHrsTotal - manPowerData[index_4].earnedManHoursTotal) / (manPowerData[index_4].estAtCompletionHrsTotal - manPowerData[index_4].actualHrsTotal);
					costData[index_3].tcpiManHrs = (manPowerData[index_4].estAtCompletionHrsTotal - manPowerData[index_4].earnedManHoursTotal) / (manPowerData[index_4].estAtCompletionHrsTotal - manPowerData[index_4].actualHrsTotal);
					costData[index_3].progPercentageVarienceCost = ((costData[index_3].plannedCost / costData[index_3].originalBudgetTotal)*100) - 
						((costData[index_3].earnedValueTotal / costData[index_3].originalBudgetTotal)*100);
					costData[index_3].progPercentageVarienceDirManHrs = ((costData[index_3].plannedDirectManHours / manPowerData[index_4].originalHrsTotal)*100) - 
						((manPowerData[index_4].earnedManHoursTotal / manPowerData[index_4].originalHrsTotal)*100);

					// Schedule Varience (SV)
					if(costData[index_3].scheduleVarienceDirectManHours > costData[index_3].exsvLowerLimit
							&& costData[index_3].exsvUpperLimit == 'Infinite') {
						costData[index_3].svManHoursExceptional = true;
					}
					if(costData[index_3].scheduleVarienceDirectManHours > costData[index_3].acsvLowerLimit
							&& costData[index_3].scheduleVarienceDirectManHours <= costData[index_3].acsvUpperLimit) {
						costData[index_3].svManHoursAcceptable = true;
					}
					if(costData[index_3].scheduleVarienceDirectManHours > costData[index_3].wasvLowerLimit
							&& costData[index_3].scheduleVarienceDirectManHours <= costData[index_3].wasvUpperLimit) {
						costData[index_3].svManHoursWarning = true;
					}
					if(costData[index_3].scheduleVarienceDirectManHours <= costData[index_3].crsvLowerLimit) {
						costData[index_3].svManHoursCritical = true;
					}
					
					// Cost Varience (CV)
					if(costData[index_3].costVarienceManHours > costData[index_3].excvLowerLimit
							&& costData[index_3].excvUpperLimit == 'Infinite') {
						costData[index_3].cvManHoursExceptional = true;
					}
					if(costData[index_3].costVarienceManHours > costData[index_3].accvLowerLimit
							&& costData[index_3].costVarienceManHours <= costData[index_3].accvUpperLimit) {
						costData[index_3].cvManHoursAcceptable = true;
					}
					if(costData[index_3].costVarienceManHours > costData[index_3].wacvLowerLimit
							&& costData[index_3].costVarienceManHours <= costData[index_3].wacvUpperLimit) {
						costData[index_3].cvManHoursWarning = true;
					}
					if(costData[index_3].costVarienceManHours <= costData[index_3].crcvLowerLimit) {
						costData[index_3].cvManHoursCritical = true;
					}
					
					// Schedule Performance Index (SPI)
					if(costData[index_3].spiDirManHrs > costData[index_3].exspiLowerLimit
							&& costData[index_3].exspiUpperLimit == 'Infinite') {
						costData[index_3].spiManHoursExceptional = true;
					}
					if(costData[index_3].spiDirManHrs > costData[index_3].acspiLowerLimit
							&& costData[index_3].spiDirManHrs <= costData[index_3].acspiUpperLimit) {
						costData[index_3].spiManHoursAcceptable = true;
					}
					if(costData[index_3].spiDirManHrs > costData[index_3].waspiLowerLimit
							&& costData[index_3].spiDirManHrs <= costData[index_3].waspiUpperLimit) {
						costData[index_3].spiManHoursWarning = true;
					}
					if(costData[index_3].spiDirManHrs <= costData[index_3].crspiLowerLimit) {
						costData[index_3].spiManHoursCritical = true;
					}
					
					// Cost Performance Index (CPI)
					if(costData[index_3].cpiManHrs > costData[index_3].excpiLowerLimit
							&& costData[index_3].excpiUpperLimit == 'Infinite') {
						costData[index_3].cpiManHoursExceptional = true;
					}
					if(costData[index_3].cpiManHrs > costData[index_3].accpiLowerLimit
							&& costData[index_3].cpiManHrs <= costData[index_3].accpiUpperLimit) {
						costData[index_3].cpiManHoursAcceptable = true;
					}
					if(costData[index_3].cpiManHrs > costData[index_3].wacpiLowerLimit
							&& costData[index_3].cpiManHrs <= costData[index_3].wacpiUpperLimit) {
						costData[index_3].cpiManHoursWarning = true;
					}
					if(costData[index_3].cpiManHrs <= costData[index_3].crcpiLowerLimit) {
						costData[index_3].cpiManHoursCritical = true;
					}
					
					// To Complete Performance Index (TCPI) 
					if(costData[index_3].manPowerPI >= costData[index_3].exTcpiUpperLimit
							&& costData[index_3].manPowerPI < costData[index_3].exTcpiLowerLimit) {
						costData[index_3].TcpiManPowerExceptional = true;
					}
					if(costData[index_3].manPowerPI >= costData[index_3].acTcpiUpperLimit
							&& costData[index_3].manPowerPI < costData[index_3].acTcpiLowerLimit) {
						costData[index_3].TcpiManPowerAcceptable = true;
					}
					if(costData[index_3].manPowerPI >= costData[index_3].waTcpiUpperLimit
							&& costData[index_3].costPerformanceIndex <= costData[index_3].waTcpiLowerLimit) {
						costData[index_3].TcpiManPowerWarning = true;
					}
					if(costData[index_3].manPowerPI >= costData[index_3].crTcpiLowerLimit) {
						costData[index_3].TcpiManPowerCritical = true;
					}
					
					
					if(costData[index_3].revisedHrsTotal != null && costData[index_3].revisedHrsTotal != 0) {
						costData[index_3].budget = 	costData[index_3].revisedHrsTotal;
					} else {
						costData[index_3].budget = 	costData[index_3].originalHrsTotal;
					}
					
					if (costData[index_3].budget > 100)
						costData[index_3].budget = costData[index_3].budget % 100;
					if (costData[index_3].budget > 50)
						costData[index_3].budget = costData[index_3].budget / 2;
				}
			}
			$scope.masterDashboardInfo.push(costData[index_3]);
			$scope.costSchedPerformanceData.push(costData[index_3]);
			$scope.costSchedVariencePercentage.push(costData[index_3]);
			$scope.costSchedVarienceInfo.push(costData[index_3]);
			$scope.costSchedPerformanceIndex.push(costData[index_3]);
			$scope.performanceIndexInfo.push(costData[index_3]);
			$scope.actualPlannedProgressData.push(costData[index_3]);
			$scope.cvSvManHoursBubbleChart.push(costData[index_3]);
			$scope.currDatesProgPercentageData.push(costData[index_3]);
			$scope.costHealthCheck.push(costData[index_3]);
			$scope.costCvSvBubbleChart.push(costData[index_3]);
			$scope.planVsActualVsEarned.push(costData[index_3]);
			$scope.costWorkSheet.push(costData[index_3]);
			$scope.labourHealthCheck.push(costData[index_3]);
			$scope.manPowerValues.push(costData[index_3]);
			$scope.planActualEarnedDirManHrs.push(costData[index_3]);
			$scope.progVarPerIdxData.push(costData[index_3]);
		}
		
		   //-----start of all for loops--------
      for(var lhc of $scope.labourHealthCheck){  
//SV       
        if(lhc.svManHoursExceptional == true){
        lhc.scheduleVarienceDirectManHours1=lhc.scheduleVarienceDirectManHours.toFixed(2) +" Ahead of  Schedule";     
           }
            if(lhc.svManHoursAcceptable == true){
        lhc.scheduleVarienceDirectManHours1=lhc.scheduleVarienceDirectManHours.toFixed(2) +" Ahead of  Schedule";     
           }
           if(lhc.svManHoursWarning == true){
        lhc.scheduleVarienceDirectManHours1=lhc.scheduleVarienceDirectManHours.toFixed(2) +" Behind Schedule";     
           }
           if(lhc.svManHoursCritical == true){
        lhc.scheduleVarienceDirectManHours1=lhc.scheduleVarienceDirectManHours.toFixed(2) +" Behind Schedule";     
           }
//CV
                
           if(lhc.cvManHoursExceptional == true){
        lhc.costVarienceManHours1=lhc.costVarienceManHours.toFixed(2) +" Under Budget";     
           }  
           if(lhc.cvManHoursAcceptable == true){
        lhc.costVarienceManHours1=lhc.costVarienceManHours.toFixed(2) +" Under Budget";     
           }  
           if(lhc.cvManHoursWarning == true){
        lhc.costVarienceManHours1=lhc.costVarienceManHours.toFixed(2) +" Over Budget";     
           }  
           if(lhc.cvManHoursCritical == true){
        lhc.costVarienceManHours1=lhc.costVarienceManHours.toFixed(2) +" Over Budget";     
           }            
          
//PV
          
			if(lhc.progPercentageVarienceDirManHrs >= 0){
        lhc.progPercentageVarienceDirManHrs1=lhc.progPercentageVarienceDirManHrs.toFixed(2) +" %";     
           }  			
		   if(lhc.progPercentageVarienceDirManHrs < 0){
        lhc.progPercentageVarienceDirManHrs1=lhc.progPercentageVarienceDirManHrs.toFixed(2) +" %";     
           }  
 //SPI   
		  
		  if(lhc.spiManHoursExceptional == true){
        lhc.spiDirManHrs1=lhc.spiDirManHrs.toFixed(2) +" Project in Acceleration";     
           }  
		 if(lhc.spiManHoursAcceptable == true){
        lhc.spiDirManHrs1=lhc.spiDirManHrs.toFixed(2) +" Project in Acceleration";     
           } 
		 if(lhc.spiManHoursWarning == true){
        lhc.spiDirManHrs1=lhc.spiDirManHrs.toFixed(2) +"  Project is Delayed";     
           } 
           if(lhc.spiManHoursCritical == true){
        lhc.spiDirManHrs1=lhc.spiDirManHrs.toFixed(2) +"  Project is Delayed";     
           } 
//CPI
          
           if(lhc.cpiManHoursExceptional == true){
        lhc.cpiManHrs1=lhc.cpiManHrs.toFixed(2) +"  Project in Acceleration";     
           }   
           if(lhc.cpiManHoursAcceptable == true){
        lhc.cpiManHrs1=lhc.cpiManHrs.toFixed(2) +"  Project in Acceleration";     
           }  
            if(lhc.cpiManHoursWarning == true){
        lhc.cpiManHrs1=lhc.cpiManHrs.toFixed(2) +"  Project is Delayed";     
           }      		
		 if(lhc.cpiManHoursCritical == true){
        lhc.cpiManHrs1=lhc.cpiManHrs.toFixed(2) +"  Project is Delayed";     
           }  
 //TCPI
                
              if(lhc.TcpiManPowerExceptional == true){
        lhc.tcpiManHrs1=lhc.tcpiManHrs.toFixed(2) +"  No need for  budget revision";     
           }   
         if(lhc.TcpiManPowerAcceptable == true){
        lhc.tcpiManHrs1=lhc.tcpiManHrs.toFixed(2) +"  No need for  budget revision";     
           }     
           if(lhc.TcpiManPowerWarning == true){
        lhc.tcpiManHrs1=lhc.tcpiManHrs.toFixed(2) +"  Need for  budget revision";     
           }  
           if(lhc.TcpiManPowerCritical == true){
        lhc.tcpiManHrs1=lhc.tcpiManHrs.toFixed(2) +"  Need for  budget revision";     
           }             
		 }
	  for(var chc of $scope.costHealthCheck){  
//SV      
        if(chc.svExceptional == true){
        chc.scheduleVarienceCost1=chc.scheduleVarienceCost.toFixed(2) +" Ahead of  Schedule";     
           } 
            if(chc.svAcceptable == true){
        chc.scheduleVarienceCost1=chc.scheduleVarienceCost.toFixed(2) +" Ahead of  Schedule";     
           }
           if(chc.svWarning == true){
        chc.scheduleVarienceCost1=chc.scheduleVarienceCost.toFixed(2) +" Behind Schedule";     
           } 
           if(chc.svCritical == true){
        chc.scheduleVarienceCost1=chc.scheduleVarienceCost.toFixed(2) +" Behind Schedule";     
           } 
//CV
                     	
        if(chc.cvExceptional == true){
        chc.costVarience1=chc.costVarience.toFixed(2) +" Under Budget";     
           }         
           if(chc.cvAcceptable == true){
        chc.costVarience1=chc.costVarience.toFixed(2) +" Under Budget";     
           }
           if(chc.cvWarning == true){
        chc.costVarience1=chc.costVarience.toFixed(2) +" Over Budget";     
           }   
           if(chc.cvCritical == true){
        chc.costVarience1=chc.costVarience.toFixed(2) +" Over Budget";     
           }  
//PV
           	
		if(chc.progPercentageVarienceCost >= 0){
        chc.progPercentageVarienceCost1=chc.progPercentageVarienceCost.toFixed(2) +" %";     
           } 	
           if(chc.progPercentageVarienceCost < 0){
        chc.progPercentageVarienceCost1=chc.progPercentageVarienceCost.toFixed(2) +" %";     
           } 
//SPI
       
            if(chc.spiExceptional == true){
        chc.spiCost1=chc.spiCost.toFixed(2) +" Project in Acceleration";     
           }   
        if(chc.spiAcceptable == true){
        chc.spiCost1=chc.spiCost.toFixed(2) +" Project in Acceleration";     
           }         
         if(chc.spiWarning == true){
        chc.spiCost1=chc.spiCost.toFixed(2) +" Project is Delayed";     
           }   
           if(chc.spiCritical == true){
        chc.spiCost1=chc.spiCost.toFixed(2) +" Project is Delayed";     
           }    
  //CPI
          
         if(chc.cpiExceptional == true){
        chc.cpiCost1=chc.cpiCost.toFixed(2) +" Project in Acceleration";     
           }  
            if(chc.cpiAcceptable == true){
        chc.cpiCost1=chc.cpiCost.toFixed(2) +" Project in Acceleration";     
           }   
           if(chc.cpiWarning == true){
        chc.cpiCost1=chc.cpiCost.toFixed(2) +" Project is Delayed";     
           } 
         if(chc.cpiCritical == true){
        chc.cpiCost1=chc.cpiCost.toFixed(2) +" Project is Delayed";     
           } 
 //TCPI
                
         if(chc.TcpiExceptional == true){
        chc.toCompleteCostPerformanceIndex1=chc.toCompleteCostPerformanceIndex.toFixed(2) +" No need for  budget revision";     
           }
            if(chc.TcpiAcceptable == true){
        chc.toCompleteCostPerformanceIndex1=chc.toCompleteCostPerformanceIndex.toFixed(2) +" No need for  budget revision";     
           }   
            if(chc.TcpiWarning == true){
        chc.toCompleteCostPerformanceIndex1=chc.toCompleteCostPerformanceIndex.toFixed(2) +"  Need for  budget revision";     
           }  
           if(chc.TcpiCritical == true){
        chc.toCompleteCostPerformanceIndex1=chc.toCompleteCostPerformanceIndex.toFixed(2) +"  Need for  budget revision";     
           }                                 	           	         
		}
		
		//1.PERFORMANCE_PROGRESS PERFORMANCE for loop
		
		for(var Mpvpi of $scope.progVarPerIdxData){
			//Schedule Variance
                if(Mpvpi.svExceptional == true){
	                Mpvpi.Msv=Mpvpi.scheduleVarienceCost.toFixed(2)+" "+"Ahead of  Schedule";
                }
                 if(Mpvpi.svAcceptable == true){
	                Mpvpi.Msv=Mpvpi.scheduleVarienceCost.toFixed(2) +" "+"Ahead of Schedule";
               	}
               	 if(Mpvpi.svWarning == true){
	                Mpvpi.Msv=Mpvpi.scheduleVarienceCost.toFixed(2) +" "+" Behind Schedule";
				}
				 if(Mpvpi.svCritical == true){
					Mpvpi.Msv=Mpvpi.scheduleVarienceCost.toFixed(2)+" "+" Behind Schedule";
				}
				//Cost Variance
				if(Mpvpi.costVarience > Mpvpi.excvLowerLimit && Mpvpi.excvUpperLimit == 'Infinite'){
					Mpvpi.Mcv=Mpvpi.costVarience.toFixed(2) +" "+" Under Budget";
				}
				if(Mpvpi.costVarience > Mpvpi.wacvLowerLimit && Mpvpi.costVarience <= Mpvpi.wacvUpperLimit){
					Mpvpi.Mcv=Mpvpi.Mcv == undefined ? " ": Mpvpi.Mcv;
					Mpvpi.Mcv+=Mpvpi.costVarience.toFixed(2) +" "+" Over Budget";
				}
				if(Mpvpi.costVarience < Mpvpi.accvLowerLimit && Mpvpi.costVarience <= Mpvpi.accvUpperLimit){
					Mpvpi.Mcv=Mpvpi.Mcv == undefined ? " ": Mpvpi.Mcv;
					Mpvpi.Mcv+=Mpvpi.costVarience.toFixed(2) +" "+"Under Budget";
				}
				if(Mpvpi.costVarience < Mpvpi.crcvLowerLimit && Mpvpi.costVarience <= Mpvpi.crcvUpperLimit){
					Mpvpi.Mcv=Mpvpi.Mcv == undefined ? " ": Mpvpi.Mcv;
					Mpvpi.Mcv+=Mpvpi.costVarience.toFixed(2) +" "+"Over Budget";
				}
				//Schedule perfomance Index
				if(Mpvpi.schedulePerformanceIndex > Mpvpi.exspiLowerLimit && Mpvpi.exspiUpperLimit == 'Infinite'){
					Mpvpi.Mspi=Mpvpi.schedulePerformanceIndex +" "+"Project in Acceleration";
				}
				if(Mpvpi.schedulePerformanceIndex > Mpvpi.waspiLowerLimit && Mpvpi.schedulePerformanceIndex <= Mpvpi.waspiUpperLimit){
					Mpvpi.Mspi=Mpvpi.schedulePerformanceIndex +" "+"Project is Delayed";
				}
				if(Mpvpi.schedulePerformanceIndex > Mpvpi.acspiLowerLimit && Mpvpi.schedulePerformanceIndex <= Mpvpi.acspiUpperLimit ){
					Mpvpi.Mspi=Mpvpi.schedulePerformanceIndex +" "+"On Schedule";
				}
				if(Mpvpi.schedulePerformanceIndex < Mpvpi.crspiLowerLimit || Mpvpi.schedulePerformanceIndex == Mpvpi.crspiUpperLimit){
					Mpvpi.Mspi=Mpvpi.schedulePerformanceIndex +" "+"Project is Delayed";
				}
               //Cost perfomance index
				if(Mpvpi.costPerformanceIndex > (Mpvpi.excpiLowerLimit + '').replace('>', '') && Mpvpi.excpiUpperLimit == 'Infinite'){
				Mpvpi.Mcpi1=Mpvpi.costPerformanceIndex.toFixed(2) +"Project in Acceleration";
				}
				if(Mpvpi.costPerformanceIndex > (Mpvpi.wacpiLowerLimit + '').replace('>', '') && Mpvpi.costPerformanceIndex <= Mpvpi.wacpiUpperLimit){
				Mpvpi.Mcpi1=Mpvpi.costPerformanceIndex.toFixed(2) +" Project is Delayed";
				}
				if(Mpvpi.costPerformanceIndex > (Mpvpi.accpiLowerLimit + '').replace('>', '') && Mpvpi.costPerformanceIndex <= Mpvpi.accpiUpperLimit){
				Mpvpi.Mcpi1=Mpvpi.costPerformanceIndex.toFixed(2) +" Project in Acceleration";
				}
				if(Mpvpi.costPerformanceIndex < (Mpvpi.crcpiLowerLimit + '').replace('<', '') && Mpvpi.costPerformanceIndex == Mpvpi.crcpiUpperLimit){
				Mpvpi.Mcpi1=Mpvpi.costPerformanceIndex.toFixed(2) +"Project is Delayed";
				}
			// TCPI
				if(Mpvpi.toCompleteCostPerformanceIndex >= Mpvpi.exTcpiUpperLimit && Mpvpi.toCompleteCostPerformanceIndex < Mpvpi.exTcpiLowerLimit ){
				Mpvpi.Mtcpi1=Mpvpi.toCompleteCostPerformanceIndex.toFixed(2)  +" No need for  budget revision";
				}
				if(Mpvpi.toCompleteCostPerformanceIndex >= Mpvpi.waTcpiUpperLimit && Mpvpi.toCompleteCostPerformanceIndex < Mpvpi.waTcpiLowerLimit){
				Mpvpi.Mtcpi1=Mpvpi.toCompleteCostPerformanceIndex.toFixed(2) +"Need for  budget revision";
				}
				if(Mpvpi.toCompleteCostPerformanceIndex >= Mpvpi.acTcpiUpperLimit && Mpvpi.toCompleteCostPerformanceIndex < Mpvpi.acTcpiLowerLimit){
				Mpvpi.Mtcpi1=Mpvpi.toCompleteCostPerformanceIndex.toFixed(2)+"No need for budget revision";
				}	
				if(Mpvpi.toCompleteCostPerformanceIndex >= Mpvpi.crTcpiUpperLimit && Mpvpi.toCompleteCostPerformanceIndex >= Mpvpi.crTcpiLowerLimit){
				Mpvpi.Mtcpi1=Mpvpi.toCompleteCostPerformanceIndex.toFixed(2) +"Need for  budget revision";
				}
				
			}
				
  			//2.COST SCHEDULE & PERFORMANCE for loop
		  
		    for(var Mcsp of $scope.costSchedPerformanceData){
			//cost variance
				if(Mcsp.cvExceptional == true){
				Mcsp.Mcostvariance=Mcsp.costVarience.toFixed(2) +" "+"Under Budget";
				}
				if(Mcsp.cvAcceptable == true){
				Mcsp.Mcostvariance=Mcsp.costVarience.toFixed(2)+" "+"Under Budget";
				}
				if(Mcsp.cvWarning == true){
				Mcsp.Mcostvariance=Mcsp.costVarience.toFixed(2)+" "+"Over Budget";
				}
				if(Mcsp.cvCritical == true){
				Mcsp.Mcostvariance=Mcsp.costVarience.toFixed(2)+" "+"Over Budget";
				}
			//schedule variance
				if(Mcsp.svExceptional == true){
				Mcsp.Mschedulevariance=Mcsp.scheduleVarienceCost.toFixed(2) +" "+"Ahead of Schedule";
				}
				if(Mcsp.svAcceptable == true){
				Mcsp.Mschedulevariance=Mcsp.scheduleVarienceCost.toFixed(2) +" "+"Ahead of Schedule";
				}
				if(Mcsp.svWarning == true){
				Mcsp.Mschedulevariance=Mcsp.scheduleVarienceCost.toFixed(2) +" "+"Behind Schedule";
				}
				if(Mcsp.svCritical == true){
				Mcsp.Mschedulevariance=Mcsp.scheduleVarienceCost.toFixed(2) +" "+"Behind Schedule";
				}
			//spi
				if(Mcsp.spiExceptional == true){
	            Mcsp.Mspi2=Mcsp.schedulePerformanceIndex.toFixed(2) +"Project in Acceleration";
            	}
            	if(Mcsp.spiAcceptable == true){
	            Mcsp.Mspi2=Mcsp.schedulePerformanceIndex.toFixed(2) +"On Schedule";
            	}
            	if(Mcsp.spiWarning == true){
			    Mcsp.Mspi2=Mcsp.schedulePerformanceIndex.toFixed(2)+"Project is Delayed";
            	}
            	if(Mcsp.spiCritical == true){
			    Mcsp.Mspi2=Mcsp.schedulePerformanceIndex.toFixed(2) +"Project is Delayed";
            	}
             //CPI
		   		if(Mcsp.cpiExceptional == true){
			    Mcsp.Mcpi=Mcsp.costPerformanceIndex.toFixed(2) +"Project in Acceleration";
				}
	 			if(Mcsp.cpiAcceptable == true){
				Mcsp.Mcpi=Mcsp.costPerformanceIndex.toFixed(2) +"Project in Acceleration";
				}
				if(Mcsp.cpiWarning == true){
				Mcsp.Mcpi=Mcsp.costPerformanceIndex.toFixed(2) +"Project is Delayed";
				}
				if(Mcsp.cpiCritical == true){
				Mcsp.Mcpi=Mcsp.costPerformanceIndex.toFixed(2) +"Project is Delayed";
				}
		    //TCPI 
		    	if(Mcsp.TcpiExceptional == true){
					if(Mcsp.MCStcpi==null ||Mcsp.MCStcpi==undefined ){
						Mcsp.MCStcpi="";
					}
					Mcsp.MCStcpi=Mcsp.toCompleteCostPerformanceIndex.toFixed(2) +"No need for budget revision";
				}
				if(Mcsp.TcpiAcceptable == true){
					if(Mcsp.MCStcpi==null ||Mcsp.MCStcpi==undefined  ){
						Mcsp.MCStcpi="";
					}
					Mcsp.MCStcpi+=Mcsp.toCompleteCostPerformanceIndex.toFixed(2) +"No need for budget revision";
				}
				if(Mcsp.TcpiWarning == true){
					if(Mcsp.MCStcpi==null ||Mcsp.MCStcpi==undefined ){
						Mcsp.MCStcpi="";
					}
					Mcsp.MCStcpi+=Mcsp.toCompleteCostPerformanceIndex.toFixed(2) +"Need for budget revision";
				}
				if(Mcsp.TcpiCritical == true){
					if(Mcsp.MCStcpi==null ||Mcsp.MCStcpi==undefined){
						Mcsp.MCStcpi="";
					}
					Mcsp.MCStcpi+=Mcsp.toCompleteCostPerformanceIndex.toFixed(2)+"Need for budget revision";
				}
		  }
               // -------END OF ALL FOR LOOPS--------
            $scope.gridOptions1.data = angular.copy($scope.labourHealthCheck);
		  	$scope.gridOptions2.data = angular.copy($scope.manPowerValues);
 	        $scope.gridOptions3.data = angular.copy($scope.planActualEarnedDirManHrs);
            $scope.gridOptions4.data = angular.copy($scope.costHealthCheck);
            $scope.gridOptions5.data = angular.copy($scope.costSchedPerformanceData);
            $scope.gridOptions6.data = angular.copy($scope.planVsActualVsEarned);
            $scope.gridOptions7.data = angular.copy($scope.costWorkSheet);
            $scope.gridOptions8.data = angular.copy($scope.currDatesProgPercentageData);				 
		 $scope.gridOptions11.data = angular.copy($scope.progVarPerIdxData);
		 $scope.gridOptions12.data = angular.copy($scope.costSchedPerformanceData);
		 $scope.gridOptions13.data = angular.copy($scope.costSchedVarienceInfo);
		 $scope.gridOptions14.data = angular.copy($scope.costSchedVariencePercentage);
		 $scope.gridOptions15.data = angular.copy($scope.costSchedPerformanceIndex);
		 $scope.gridOptions16.data = angular.copy($scope.performanceIndexInfo);
		 $scope.gridOptions17.data = angular.copy($scope.actualPlannedProgressData);

		if ($scope.selectedTitle.includes("Cost_And_Schedule_Variance_Bubble_Chart") || 
				$scope.selectedTitle.includes("CV_And_SV_For_Labour_Bubble_Chart")) {
		//if($scope.categoryDetails.catgName != 'Budget') {
			setGraphTimeBubbleData($scope.cvSvManHoursBubbleChart)
		} else if ($scope.selectedTitle.includes("CV_And_SV_For_Cost_Bubble_Chart")) {
			setGraphCostBubbleData($scope.costCvSvBubbleChart)
		}
	}
	$scope.costItemClick = function (item, expand) {
		TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
	}
	function populateCostData(data, level, costTOs, isChild, parent) {
		return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',isChild, parent)
	}
	
	//11111
	
	$scope.$watch(function () { return stylesService.finishedStyling;}, 
         
           function (newValue, oldValue) {
			if (newValue) {
				let columnDefs =  [
					{field: 'epsName',displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
					{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ name: 'scheduleVarienceDirectManHours1',cellTemplate:'<span ng-if="row.entity.svManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.scheduleVarienceDirectManHours | number : 2}}Ahead of  Schedule</span><span ng-if="row.entity.svManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.scheduleVarienceDirectManHours | number : 2}}Ahead of  Schedule</span><span ng-if="row.entity.svManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.scheduleVarienceDirectManHours | number : 2}}Behind Schedule</span><span ng-if="row.entity.svManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.scheduleVarienceDirectManHours | number : 2}}Behind Schedule</span>',
					displayName: "Schedule Varience", headerTooltip: "Schedule Varience", groupingShowAggregationMenu: false },
					{ name: 'costVarienceManHours1',cellTemplate:'<span ng-if="row.entity.cvManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costVarienceManHours | number: 2}}Under Budget</span><span ng-if="row.entity.cvManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costVarienceManHours | number: 2}}Under Budget</span><span ng-if="row.entity.cvManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarienceManHours | number: 2}} Over Budget</span><span ng-if="row.entity.cvManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.costVarienceManHours | number: 2}} Over Budget</span>',
					displayName: "Cost Varience", headerTooltip: "Cost Varience", groupingShowAggregationMenu: false },
					{ name: 'progPercentageVarienceDirManHrs1',cellTemplate:'<span ng-if="row.entity.progPercentageVarienceDirManHrs >= 0">{{row.entity.progPercentageVarienceDirManHrs | number : 2}}%</span><span ng-if="row.entity.progPercentageVarienceDirManHrs < 0"  style="color:red;">{{row.entity.progPercentageVarienceDirManHrs | number : 2}}%</span>',
					displayName: "Progress Variance", headerTooltip: "Progress Variance", groupingShowAggregationMenu: false },
					{ name: 'spiDirManHrs1',cellTemplate:'<span ng-if="row.entity.spiManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.spiDirManHrs | number: 2}}Project in Acceleration</span><span ng-if="row.entity.spiManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.spiDirManHrs | number: 2}}Project in Acceleration</span><span ng-if="row.entity.spiManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.spiDirManHrs | number: 2}}Project is Delayed</span><span ng-if="row.entity.spiManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.spiDirManHrs | number: 2}}Project is Delayed</span>', 
					displayName: "SPI", headerTooltip: "SPI", groupingShowAggregationMenu: false },
					{ name: 'cpiManHrs1',cellTemplate:'<span ng-if="row.entity.cpiManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.cpiManHrs | number: 2}}Project in Acceleration</span><span ng-if="row.entity.cpiManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.cpiManHrs | number: 2}}Project in Acceleration</span><span ng-if="row.entity.cpiManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.cpiManHrs | number: 2}}Project is Delayed</span><span ng-if="row.entity.cpiManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.cpiManHrs | number: 2}}Project is Delayed</span>',
					displayName: "CPI", headerTooltip: "CPI", groupingShowAggregationMenu: false },
					{ name: 'tcpiManHrs1',cellTemplate:'<span ng-if="row.entity.TcpiManPowerExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.tcpiManHrs | number: 2}}No need for  budget revision</span> <span ng-if="row.entity.TcpiManPowerAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.tcpiManHrs | number: 2}}No need for  budget revision </span> <span ng-if="row.entity.TcpiManPowerWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.tcpiManHrs | number: 2}} Need for  budget revision</span> <span ng-if="row.entity.TcpiManPowerCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.tcpiManHrs | number: 2}}Need for  budget revision</span>',
					displayName: "TCPI", headerTooltip: "TCPI", groupingShowAggregationMenu: false },
			]
				let data = [];
				$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data,"MasterDashboard_Labour_Labour Health Check");	
			}
		});
	
	//2222222
	
$scope.$watch(function () { return stylesService.finishedStyling; },
	function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'projName', displayName: "Project Name", headerTooltip: 'Project Name' },
				{
					name: 'actualHrsTotal',
					cellTemplate: '<div>{{row.entity.actualHrsTotal | number: 2 }}</div>',
					displayName: "Actual Hours", headerTooltip: 'Actual Hours'
				},
				{
					name: 'originalHrsTotal',
					cellTemplate: '<div>{{row.entity.originalHrsTotal | number: 2 }}</div>',
					displayName: "Budget Hours", headerTooltip: 'Budget Hours'
				},
				{
					name: 'etcHrsTotal',
					cellTemplate: '<div>{{row.entity.etcHrsTotal | number: 2 }}</div>',
					displayName: "Estimate To Complete", headerTooltip: 'Estimate To Complete'
				},
				{
					name: 'estAtCompletionHrsTotal',
					cellTemplate: '<div>{{row.entity.estAtCompletionHrsTotal | number: 2 }}</div>',
					displayName: "Estimate At Completion", headerTooltip: 'Estimate At Completion'
				},
			]
			let data = [];
			$scope.gridOptions2 = ngGridService.initGrid($scope, columnDefs, data, "MasterDashboard_Labour_OriginalVsEstimateAtCompletionManHours");
		}
	});
	//33333
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'projName', displayName: 'Project Name', headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedDirectManHours', displayName: 'Planned Hours', headerTooltip: "Planned Hours", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'actualHrsTotal', displayName: 'Actual Hours', headerTooltip: "Actual Hours", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'earnedManHoursTotal', displayName: 'Earned Hours', headerTooltip: "Earned Hours", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
			]
			let data = [];
			$scope.gridOptions3 = ngGridService.initGrid($scope, columnDefs, data, "MasterDashboard_Labour_planvsActualvsEarned-DirectManhours");
		}
	});
	//44444
	
	$scope.$watch(function () { return stylesService.finishedStyling; },
	function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName',  displayName: "EPS Name", headerTooltip : "EPS Name" , groupingShowAggregationMenu: false },
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" ,  groupingShowAggregationMenu: false },
						{ name: 'scheduleVarienceCost1', cellTemplate:'<span ng-if="row.entity.svExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.scheduleVarienceCost | number: 2}}Ahead of Schedule</span><span ng-if="row.entity.svAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.scheduleVarienceCost | number: 2}}Ahead of Schedule</span><span ng-if="row.entity.svWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.scheduleVarienceCost | number: 2}}Behind Schedule</span><span ng-if="row.entity.svCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.scheduleVarienceCost | number: 2}}Behind Schedule</span>',
						displayName: "Schedule Varience", headerTooltip: "Schedule Varience",  groupingShowAggregationMenu: false},
						{ name: 'costVarience1', cellTemplate:'<span ng-if="row.entity.cvExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costVarience | number: 2}}Under Budget</span><span ng-if="row.entity.cvAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costVarience | number: 2}}Under Budget</span><span ng-if="row.entity.cvWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarience | number: 2}}Over Budget</span><span ng-if="row.entity.cvCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.costVarience | number: 2}}Over Budget</span>',
                        displayName: "Cost Varience", headerTooltip: "Cost Varience", groupingShowAggregationMenu: false},
					    { name: 'progPercentageVarienceCost1',cellTemplate:'<span ng-if="row.entity.progPercentageVarienceCost >= 0">{{row.entity.progPercentageVarienceCost | number : 2}}%</span><span ng-if="row.entity.progPercentageVarienceCost < 0"  style="color:red;">{{row.entity.progPercentageVarienceCost | number : 2}}%</span>',
	                    displayName: "Progress Varience", headerTooltip: "Progress Varience",  groupingShowAggregationMenu: false},
						{ name: 'spiCost1', cellTemplate:'<span ng-if="row.entity.spiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.spiCost | number: 2}}Project in Acceleration</span><span ng-if="row.entity.spiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.spiCost | number: 2}}Project in Acceleration</span><span ng-if="row.entity.spiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.spiCost | number: 2}}Project is Delayed</span><span ng-if="row.entity.spiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.spiCost | number: 2}}Project is Delayed</span>',
						displayName: "SPI", headerTooltip: "SPI",  groupingShowAggregationMenu: false},
						{ name: 'cpiCost1',cellTemplate:'<span ng-if="row.entity.cpiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.cpiCost | number: 2}}Project in Acceleration</span> <span ng-if="row.entity.cpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.cpiCost | number: 2}}Project in Acceleration</span><span ng-if="row.entity.cpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.cpiCost | number: 2}}Project is Delayed</span><span ng-if="row.entity.cpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.cpiCost | number: 2}}Project is Delayed</span>',
						displayName: "CPI", headerTooltip: "CPI",  groupingShowAggregationMenu: false},
						{ name: 'toCompleteCostPerformanceIndex1',cellTemplate:'<span ng-if="row.entity.TcpiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.toCompleteCostPerformanceIndex | number: 2}}No need for  budget revision</span> <span ng-if="row.entity.TcpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.toCompleteCostPerformanceIndex | number: 2}} No need for budget revision</span> <span ng-if="row.entity.TcpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.toCompleteCostPerformanceIndex | number: 2}}Need for  budget revision </span> <span ng-if="row.entity.TcpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.toCompleteCostPerformanceIndex | number: 2}}Need for  budget revision </span>',
						displayName: "TCPI", headerTooltip : "TCPI" , groupingShowAggregationMenu: false }
						];
					let data = [];
					$scope.gridOptions4 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_MasterDashboard_cost_costHealthCheck");
	                 $scope.getDatasets();				
				}
			});
//555555555
      $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'epsName',displayName:'EPS',headerTooltip: "EPS", groupingShowAggregationMenu: false},				
				{ name: 'projName',displayName:'Project Name',headerTooltip: "Project Name", groupingShowAggregationMenu: false, },
				{ name: 'originalBudgetTotal',displayName:'Original Budget',headerTooltip: "Original Budget", groupingShowAggregationMenu: false, footerCellFilter:'number:2', cellFilter: 'number: 2',aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},	
				{ name: 'actualCostTotal',displayName:'Actual cost',headerTooltip: "Actual To Date", footerCellFilter:'number:2', cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},	
				{ name: 'estCompletion',displayName:'Estimate To Complete',headerTooltip: "Estimate To Complete", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'estAtCompletionTotal',displayName:'Estimate At Completion',headerTooltip: "Estimate At Completion", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				]
			let data = [];
			$scope.gridOptions5 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_MasterDashboard_cost_originavsEstimateatCompletionCost");
			
		}
	});	
//666666666
    $scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: 'EPS Name' },
						{ field: 'projName', displayName: "Project Name", headerTooltip: 'Project' },
						{
							name: 'plannedCost',
							cellTemplate: '<div>{{row.entity.plannedCost | number: 2 }}</div>',
							displayName: "Planned Cost", headerTooltip: 'Planned Cost'
						},
						{
							name: 'actualCostTotal',
							cellTemplate: '<div>{{row.entity.actualCostTotal | number: 2 }}</div>',
							displayName: "Actual Cost", headerTooltip: 'Actual Cost'
						},
						{
							name: 'earnedValueTotal',
							cellTemplate: '<div>{{row.entity.earnedValueTotal | number: 2 }}</div>',
							displayName: "Earned Value", headerTooltip: 'Earned Value'
						},	
					]
					let data = [];
					$scope.gridOptions6 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_MasterDashboard_Cost_PlanVsActualVsEarnedValueCost");
				}
			});
//7777777777	
       $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'epsName',displayName:'EPS Name',headerTooltip: "EPS Name", groupingShowAggregationMenu: false},				
				{ name: 'projName',displayName:'Project Name',headerTooltip: "Project Name", groupingShowAggregationMenu: false, },
				{ name: 'currency',displayName:'Currency',headerTooltip: "Currency", groupingShowAggregationMenu: false},	
				{ name: 'originalBudgetTotal',displayName:'Original Budget',headerTooltip: "Original Budget", footerCellFilter:'number:2', cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},	
				{ name: 'revisedBudgetTotal',displayName:'Revised Budget',headerTooltip: "Revised Budget", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'progressOfWorkPercentage',displayName:'Progress %',headerTooltip: "Progress %", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'plannedCost',displayName:'Planned Cost',headerTooltip: "Planned Cost", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'earnedValueTotal',displayName:'Earned Value',headerTooltip: "Earned Value", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'actualCostTotal',displayName:'Actual Cost',headerTooltip: "Actual Cost", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'costOccruals',displayName:'Cost Occruals',headerTooltip: "Cost Occruals", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'remainingBudgetTotal',displayName:'Remaining Budget',headerTooltip: "Remaining Budget", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'estCompletion',displayName:'Estimate To Complete',headerTooltip: "Estimate To Complete", cellFilter: 'number: 2',footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				{ name: 'estAtCompletionTotal',displayName:'Estimate At Completion',headerTooltip: "Estimate At Completion", cellFilter: 'number: 2', footerCellFilter:'number:2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}},
				]
			let data = [];
			$scope.gridOptions7 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_MasterDashboard_cost_costWorkSheet");
		}
	});
//8888888888
        $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'epsName', displayName: 'EPS Name', headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ name: 'projName', displayName: 'Project Name', headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ name: 'actualStartDate', displayName: 'Start Date', headerTooltip: "Start Date", cellTemplate:'<div><span ng-show="row.entity.actualStartDate != null">{{row.entity.actualStartDate}}</span><span ng-show="row.entity.actualStartDate == null && row.entity.forecastStartDate != null ">{{row.entity.forecastStartDate}}</span><span ng-show="row.entity.actualStartDate == null && row.entity.forecastStartDate == null && row.entity.baselineStartDate != null">{{row.entity.baselineStartDate}}</span></div>', groupingShowAggregationMenu: false },
				{ name: 'actualFinishDate', displayName: 'Finish Date', headerTooltip: "Finish Date", cellTemplate:'<div><span ng-show="row.entity.actualFinishDate != null">{{row.entity.actualFinishDate}}</span><span ng-show="row.entity.actualFinishDate == null && row.entity.forecastFinishDate != null ">{{row.entity.forecastFinishDate}}</span><span ng-show="row.entity.actualFinishDate == null && row.entity.forecastFinishDate == null && row.entity.baselineFinishDate != null">{{row.entity.baselineFinishDate}}</span></div>', groupingShowAggregationMenu: false },
				{ name: 'actualProgress', displayName: '% Complete', headerTooltip: "% Complete", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ name: 'currentPhase', displayName: 'Project Status', headerTooltip: "Project Status", groupingShowAggregationMenu: false }
				
			]
			let data = [];
			$scope.gridOptions8 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_MasterDashboard_Time_currentDates&Porgress%");
		}
	});
	//1.PROGRESS AND PERFORMANCE
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'epsName',  displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'currentPhase',  displayName: "Project Status", headerTooltip: "Project Status" },
				{field: 'progressOfWorkPercentage', cellFilter:'number:2', displayName: "Progress %", headerTooltip: "Progress %",
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:center;">{{ row.entity.progressOfWorkPercentage | number:2 }}</div>'},
				{field: 'Msv',  displayName: "Schedule Variance (Cost)", headerTooltip: "Schedule Variance (Cost)",
					cellTemplate: '<span ng-if="row.entity.svExceptional == true"><i class="fa fa-star" style="color:blue;"></i> {{row.entity.scheduleVarienceCost | number: 2}} Ahead of Schedule </span>'
						+ '<span ng-if="row.entity.svAcceptable == true"><i class="fa fa-square" style="color:green;"></i> {{row.entity.scheduleVarienceCost | number: 2}} Ahead of Schedule </span>' +
						'<span ng-if="row.entity.svWarning == true"><i class="fa fa-exclamation-triangle" style="color:blue;"></i> {{row.entity.scheduleVarienceCost | number: 2}} Behind Schedule </span>' +
						'<span ng-if="row.entity.svCritical == true"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.scheduleVarienceCost | number: 2}} Behind Schedule </span>'
				},
				{field: 'Mcv', displayName: "Cost Variance (Cost)", headerTooltip: "Cost Variance (Cost)",
					cellTemplate: '<span ng-if="row.entity.costVarience > row.entity.excvLowerLimit && excvUpperLimit == Infinite"> <i class="fa fa-star" style="color:blue;"></i> {{row.entity.costVarience | number: 2}} Under Budget</span>'
						+ '<span ng-if="row.entity.costVarience > row.entity.wacvLowerLimit && row.entity.costVarience <= row.entity.wacvUpperLimit"> <i class="fa fa-exclamation-triangle" style="color:green;"></i> {{row.entity.costVarience | number: 2}} Over Budget</span>' +
						'<span ng-if="row.entity.costVarience < row.entity.accvLowerLimit && row.entity.costVarience <= row.entity.accvUpperLimit"> <i class="fa fa-square" style="color:green;"></i> {{row.entity.costVarience | number: 2}} Under Budget</span>' +
						'<span ng-if="row.entity.costVarience < row.entity.crcvLowerLimit && row.entity.costVarience <= row.entity.crcvUpperLimit"> <img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.costVarience | number: 2}} Over Budget</span>'
				},
				{field: 'Mspi', displayName: "Schedule Performance Index (SPI)", headerTooltip: "Schedule Performance Index (SPI)",
					cellTemplate: '<span ng-if="row.entity.schedulePerformanceIndex > row.entity.exspiLowerLimit && exspiUpperLimit == Infinite"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.schedulePerformanceIndex | number: 2}} Project in Acceleration </span>'
						+ '<span ng-if="row.entity.schedulePerformanceIndex > row.entity.waspiLowerLimit && row.entity.schedulePerformanceIndex <= row.entity.waspiUpperLimit"> <i class="fa fa-exclamation-triangle" style="color:green"></i> {{row.entity.schedulePerformanceIndex | number: 2}} Project is Delayed </span>' +
						'<span ng-if="row.entity.schedulePerformanceIndex > row.entity.acspiLowerLimit && row.entity.schedulePerformanceIndex <= row.entity.accvUpperLimit"> <i class="fa fa-square" style="color:green;"></i>  {{row.entity.schedulePerformanceIndex | number: 2}} Under Budget </span>' +
						'<span ng-if="row.entity.schedulePerformanceIndex < row.entity.crspiLowerLimit || row.entity.schedulePerformanceIndex == row.entity.crspiUpperLimit"> <img src="images/critical.png" style="padding-left:5px !important;">  {{row.entity.schedulePerformanceIndex | number: 2}} Project is Delayed </span>'
				},
				{field: 'Mcpi1',  displayName: "Cost Performance Index (CPI)", headerTooltip: "Cost Performance Index (CPI)",
					cellTemplate: '<span ng-if="row.entity.costPerformanceIndex > row.entity.excpiLowerLimit && row.entity.exspiUpperLimit == Infinite"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.costPerformanceIndex | number: 2}} Project in Acceleration </span>'
						+ '<span ng-if="row.entity.costPerformanceIndex > row.entity.waspiLowerLimit && row.entity.costPerformanceIndex <= row.entity.waspiUpperLimit"> <i class="fa fa-exclamation-triangle" style="color:green"></i> {{row.entity.costPerformanceIndex | number: 2}} Project is Delayed </span>' +
						'<span ng-if="row.entity.costPerformanceIndex > row.entity.acspiLowerLimit && row.entity.costPerformanceIndex <= row.entity.accvUpperLimit"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.costPerformanceIndex | number: 2}} Project in Acceleration </span>' +
						'<span ng-if="row.entity.costPerformanceIndex < row.entity.crspiLowerLimit && row.entity.costPerformanceIndex == row.entity.crspiUpperLimit"> <img src="images/critical.png" style="padding-left:5px !important;">  {{row.entity.costPerformanceIndex | number: 2}} Project is Delayed </span>'
				},
				{field: 'Mtcpi1', displayName: "To Complete Performance Index (TCPI)", headerTooltip: "To Complete Performance Index (TCPI)",
					cellTemplate: '<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.exTcpiUpperLimit &&  row.entity.toCompleteCostPerformanceIndex <  row.entity.exTcpiLowerLimit"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.toCompleteCostPerformanceIndex | number: 2}} No need for budget revision</span>'
						+ '<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.waTcpiUpperLimit && row.entity.toCompleteCostPerformanceIndex < row.entity.waTcpiLowerLimit"> <i class="fa fa-exclamation-triangle" style="color:green"></i>  {{row.entity.toCompleteCostPerformanceIndex | number: 2}} Need for budget revision </span>' +
						'<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.acTcpiUpperLimit && row.entity.toCompleteCostPerformanceIndex < row.entity.acTcpiLowerLimit"> <i class="fa fa-square" style="color:green;"></i> {{row.entity.toCompleteCostPerformanceIndex | number: 2}} No need for budget revision </span>' +
						'<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.crTcpiUpperLimit && row.entity.toCompleteCostPerformanceIndex >= row.entity.crTcpiLowerLimit"> <img src="images/critical.png" style="padding-left:5px !important;">  {{row.entity.toCompleteCostPerformanceIndex | number: 2}} Need for budget revision </span>'
				},
			]
			let data = [];
			$scope.gridOptions11 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Cost_progressVarience & Performance Indices");
			}
		});
	// 2.COST SCHEDULE & PERFORMANCE
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { field: 'epsName', displayName: "EPS Name", headerTooltip : "EPS Name" },
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name"},
						{ field: 'actualStartDate', displayName: "Actual Start", headerTooltip: "Actual Start", },
						{ field: 'actualFinishDate', displayName: "Actual Finish", headerTooltip: "Actual Finish"},
						{ field: 'baselineStartDate', displayName: "Base Line Start", headerTooltip: "Base Line Start",},
						{ field: 'baselineFinishDate', displayName: "Base Line Finish", headerTooltip: "Base Line Finish",},
						{ field: 'progressOfWorkPercentage', displayName: "Progress %", headerTooltip: "Progress %", cellFilter: 'number: 2'},
						{ field: 'originalBudgetTotal', displayName: "Original Cost", headerTooltip: "Original Cost"},
						{ field: 'revisedBudgetTotal', displayName: "Revised Budget", headerTooltip: "Revised Budget"},
						{ field: 'estAtCompletionTotal', displayName: "Estimate At Completion Cost", headerTooltip : "Estimate At Completion Cost" },
						{ field: 'actualCostTotal', displayName: "Actual Cost", headerTooltip: "Actual Cost"},
						{ field: 'plannedCost', displayName: "Planned Cost", headerTooltip: "Planned Cost",cellFilter: 'number: 2',},
						{ field: 'earnedValueTotal', displayName: "Earned Value", headerTooltip: "Earned Value"},
						{ name: 'Mcostvariance', displayName: "Cost Variance", headerTooltip: "Cost Variance",
						cellTemplate: '<span ng-if="row.entity.cvExceptional == true" class="exceptional"> <i class="fa fa-star"></i>{{row.entity.costVarience | number: 2}}Under Budget</span><span ng-if="row.entity.cvAcceptable == true" class="acceptable"> <i class="fa fa-square"></i>{{row.entity.costVarience | number: 2}}Under Budget</span><span ng-if="row.entity.cvWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarience | number: 2}}Over Budget</span><span ng-if="row.entity.cvCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.costVarience | number: 2}}Over Budget</span>'},
						{ name: 'Mschedulevariance', displayName: "Schedule Variance", headerTooltip: "Schedule Variance",
						cellTemplate: '<span ng-if="row.entity.svExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.scheduleVarienceCost | number: 2}}Ahead of Schedule</span><span ng-if="row.entity.svAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.scheduleVarienceCost | number: 2}}Ahead of Schedule</span><span ng-if="row.entity.svWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.scheduleVarienceCost | number: 2 }}Behind Schedule</span><span ng-if="row.entity.svCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.scheduleVarienceCost | number: 2}}Behind Schedule</span>'},
						{ name: 'Mspi2', displayName: "SPI", headerTooltip: "SPI",
						cellTemplate: '<span ng-if="row.entity.spiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.schedulePerformanceIndex | number: 2}}Project in Acceleration</span><span ng-if="row.entity.spiAcceptable == true " class="acceptable"><i class="fa fa-square"></i>{{row.entity.schedulePerformanceIndex | number: 2}}On Schedule</span><span ng-if="row.entity.spiWarning == true " class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.schedulePerformanceIndex | number: 2}}Project is Delayed</span><span ng-if="row.entity.spiCritical == true " class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.schedulePerformanceIndex | number: 2}}Project is Delayed</span>'},
						{ name: 'Mcpi', displayName: "CPI", headerTooltip: "CPI",
						cellTemplate: '<span ng-if="row.entity.cpiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costPerformanceIndex | number: 2}}Project in Acceleration</span><span ng-if="row.entity.cpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costPerformanceIndex | number: 2}}Project in Acceleration</span><span ng-if="row.entity.cpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costPerformanceIndex | number: 2}}Project is Delayed</span><span ng-if="row.entity.cpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.costPerformanceIndex | number: 2}}Project is Delayed</span>'},
						{ name: 'MCStcpi', displayName: "TCPI", headerTooltip: "TCPI",
						cellTemplate: '<span ng-if="row.entity.TcpiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.toCompleteCostPerformanceIndex | number: 2}}No need for budget revision</span>'+
						'<span ng-if="row.entity.TcpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.toCompleteCostPerformanceIndex | number: 2}}No need for budget revision</span>'+
						'<span ng-if="row.entity.TcpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.toCompleteCostPerformanceIndex | number: 2}}Need for budget revision</span>'+
						'<span ng-if="row.entity.TcpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.toCompleteCostPerformanceIndex | number: 2}}Need for budget revision</span>'},
						];
					let data = [];
					$scope.gridOptions12 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_Cost Schedule & Performance");
				}
			});
			// 3.COST SCHEDULE VARIANCE UNITS
			$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'MSvlh', displayName: "Schedule Variance - Labour Hours", headerTooltip: "Schedule Variance - Labour Hours",
						cellTemplate:'<span ng-if="row.entity.svManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.scheduleVarienceDirectManHours | number: 2}} Ahead of  Schedule</span><span ng-if="row.entity.svManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.scheduleVarienceDirectManHours | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.scheduleVarienceDirectManHours | number: 2}} Behind Schedule</span><span ng-if="row.entity.svManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.scheduleVarienceDirectManHours | number: 2}} Behind Schedule</span>'},
						{ field: 'Msvc', displayName: "Schedule Variance - Cost", headerTooltip: "Schedule Variance - Cost",
						cellTemplate: '<span ng-if="row.entity.svExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.scheduleVarienceCost | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.scheduleVarienceCost | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.scheduleVarienceCost | number: 2}} Behind Schedule</span><span ng-if="row.entity.svCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.scheduleVarienceCost | number: 2}} Behind Schedule</span>'},
						{ field: 'MCScvlh', displayName: "Cost Variance - Labour Hours", headerTooltip: "Cost Variance - Labour Hours",
						cellTemplate: '<span ng-if="row.entity.cvManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costVarienceManHours | number: 2}} Under Budget</span><span ng-if="row.entity.cvManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costVarienceManHours | number: 2}} Under Budget</span><span ng-if="row.entity.cvManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarienceManHours | number: 2}} Over Budget</span><span ng-if="row.entity.cvManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.costVarienceManHours | number: 2}} Over Budget</span>'},
						{ field: 'MCScvc', displayName: "Cost Variance - Cost", headerTooltip: "Cost Variance - Cost", 
						cellTemplate: '<span ng-if="row.entity.cvExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costVarience | number: 2}} Under Budget</span><span ng-if="row.entity.cvAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costVarience | number: 2}} Under Budget</span><span ng-if="row.entity.cvWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarience | number: 2}} Over Budget</span><span ng-if="row.entity.cvCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.costVarience | number: 2}} Over Budget</span>'},
						];
					let data = [];
					$scope.gridOptions13 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_ScheduleVarianceUnit");
				}
			});
			// 4.COST SCHEDULE VARIANCE -%
			$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'Msvlh', displayName: "Schedule Variance - Labour Hours", headerTooltip: "Schedule Variance - Labour Hours",
						cellTemplate:'<span ng-if="row.entity.svManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.schedVarDirManHrsPercentage | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.schedVarDirManHrsPercentage | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.schedVarDirManHrsPercentage | number: 2}} Behind Schedule</span><span ng-if="row.entity.svManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.schedVarDirManHrsPercentage | number: 2}} Behind Schedule</span>'},
						{ field: 'Msvlc', displayName: "Schedule Variance - Cost", headerTooltip: "Schedule Variance - Cost",
						cellTemplate: '<span ng-if="row.entity.svExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.scheduleVarienceCostPercentage | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.scheduleVarienceCostPercentage | number: 2}} Ahead of Schedule</span><span ng-if="row.entity.svWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.scheduleVarienceCostPercentage | number: 2}} Behind Schedule</span><span ng-if="row.entity.svCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.scheduleVarienceCostPercentage | number: 2}} Behind Schedule</span>'},
						{ field: 'Mcvlh', displayName: "Cost Variance - Labour Hours", headerTooltip: "Cost Variance - Labour Hours",
						cellTemplate: '<span ng-if="row.entity.cvManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costVarManHrsPercentage | number: 2}} Under Budget</span><span ng-if="row.entity.cvManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costVarManHrsPercentage | number: 2}} Under Budget</span><span ng-if="row.entity.cvManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarManHrsPercentage | number: 2}} Over Budget</span><span ng-if="row.entity.cvManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.costVarManHrsPercentage | number: 2}} Over Budget</span>'},
						{ field: 'Mcvc', displayName: "Cost Variance - Cost", headerTooltip: "Cost Variance - Cost", 
						cellTemplate: '<span ng-if="row.entity.cvExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.costVarienceCostPercentage | number: 2}} Under Budget</span><span ng-if="row.entity.cvAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.costVarienceCostPercentage | number: 2}} Under Budget</span><span ng-if="row.entity.cvWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.costVarienceCostPercentage | number: 2}} Over Budget</span><span ng-if="row.entity.cvCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.costVarienceCostPercentage | number: 2}} Over Budget</span>'},
						];
					let data = [];
					$scope.gridOptions14 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_ScheduleVariancePercentage");
				}
			});
			// 5.COST SCHEDULE PERFORMANCE INDICES
			$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'MspiLabour1', displayName: "SPI - Labour Hours", headerTooltip: "SPI (Schedule Performance Index) - Labour Hours",
						cellTemplate:'<span ng-if="row.entity.spiManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.spiDirManHrs | number: 2}} Project in Acceleration</span><span ng-if="row.entity.spiManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i> {{row.entity.spiDirManHrs | number: 2}} Project in Acceleration</span><span ng-if="row.entity.spiManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.spiDirManHrs | number: 2}} Project is Delayed</span><span ng-if="row.entity.spiManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.spiDirManHrs | number: 2}} Project is Delayed</span>'},
						{ field: 'MspiCost1', displayName: "SPI - Cost", headerTooltip: "SPI (Shedule Performance Index) - Cost",
						cellTemplate: '<span ng-if="row.entity.spiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.spiCost | number: 2}} Project in Acceleration</span><span ng-if="row.entity.spiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.spiCost | number: 2}}  Project in Acceleration</span><span ng-if="row.entity.spiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.spiCost | number: 2}} Project is Delayed</span><span ng-if="row.entity.spiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.spiCost | number: 2}} Project is Delayed</span>'},
						{ field: 'MspiLabourHour1', displayName: "CPI - Labour Hours", headerTooltip: "CPI (Cost Performance Index) - Labour Hours",
						cellTemplate: '<span ng-if="row.entity.cpiManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.cpiManHrs | number: 2}} Project in Acceleration </span><span ng-if="row.entity.cpiManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.cpiManHrs | number: 2}} Project in Acceleration</span><span ng-if="row.entity.cpiManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.cpiManHrs | number: 2}} Project is Delayed</span><span ng-if="row.entity.cpiManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.cpiManHrs | number: 2}} Project is Delayed</span>'},
						{ field: 'McpiCost1', displayName: "CPI - Cost", headerTooltip: "CPI (Cost Performance Index) - Cost", 
						cellTemplate: '<span ng-if="row.entity.cpiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.cpiCost | number: 2}} Project in Acceleration</span><span ng-if="row.entity.cpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.cpiCost | number: 2}} Project in Acceleration</span><span ng-if="row.entity.cpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i> {{row.entity.cpiCost | number: 2}} Project is Delayed</span><span ng-if="row.entity.cpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.cpiCost | number: 2}} Project is Delayed</span>'},
						];
					let data = [];
					$scope.gridOptions15 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_PerformanceIndices");
				}
			});
			// 6.TO COMPLETE PERFORMANCE INDEX
			$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'MlabourHours', displayName: "TCPI - Labour Hours", headerTooltip: "TCPI (To Complete Performance Index) - Labour Hours",
						cellTemplate:'<span ng-if="row.entity.TcpiManPowerExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{ row.entity.manPowerPI | number:2 }} No need for budget revision</span><span ng-if="row.entity.TcpiManPowerAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{ row.entity.manPowerPI | number:2 }} No need for budget revision</span><span ng-if="row.entity.TcpiManPowerWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i> {{ row.entity.manPowerPI | number:2 }} Need for budget revision</span><span ng-if="row.entity.TcpiManPowerCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{ row.entity.manPowerPI | number:2 }} Need for budget revision</span>'},
						{ field: 'MTCPIcost', displayName: "TCPI - Cost", headerTooltip: "TCPI (To Complete Performance Index) - Cost",
						cellTemplate: '<span ng-if="row.entity.TcpiExceptional == true" class="exceptional"><i class="fa fa-star"></i> {{ row.entity.costPI | number:2 }} No need for budget revision	</span><span ng-if="row.entity.TcpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{ row.entity.costPI | number:2 }} No need for budget revision</span><span ng-if="row.entity.TcpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{ row.entity.costPI | number:2 }} Need for budget revision</span><span ng-if="row.entity.TcpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{ row.entity.costPI | number:2 }} Need for budget revision </span>'},
						];
					let data = [];
					$scope.gridOptions16 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_PerformanceIndex");
				}
			});
			// Progress-- Plan vs Actual Progress
			$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "eps Name", headerTooltip: 'Enterprise' },
						{ field: 'projName', displayName: "Project Name", headerTooltip: 'Project' },
						{name: 'actualProgress',cellFilter:'number:2',displayName: "Actual Progress", headerTooltip: 'Actual Progress'},
						{name: 'plannedProgress',cellFilter:'number:2',displayName: "Planned Progress", headerTooltip: 'Planned Progress'},
					]
					let data = [];
					$scope.gridOptions17 = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Progress_PlanVsActualProgress");
				}
			});	
			
	function calculateCostTotal(projCostStmtDtls) {
		let projCostValues = [];
		for (const costValue of projCostStmtDtls) {
			let costFound = false;
			for (let i=0; i < projCostValues.length; i++) {
				//projCostValues[i].estCompletion = 0;
				var actualCostTotal1 = (costValue.actualCostBudget.labourCost
						+costValue.actualCostBudget.materialCost+costValue.actualCostBudget.plantCost+costValue.actualCostBudget.otherCost);
				if (projCostValues[i].projId == costValue.projId) {
					costFound = true;
					
					projCostValues[i].projId = costValue.projId;
					projCostValues[i].epsName = $scope.searchProject.parentName;
					
					projCostValues[i].originalBudgetTotal += costValue.originalCostBudget.total;
					projCostValues[i].revisedBudgetTotal += costValue.revisedCostBudget.total;
					projCostValues[i].actualCostTotal += actualCostTotal1;
					projCostValues[i].earnedValueTotal += costValue.earnedValue;
					if(costValue.revisedQty > 0) {
						projCostValues[i].remainingBudgetTotal += costValue.revisedCostBudget.total - actualCostTotal1;
					} else {
						projCostValues[i].remainingBudgetTotal += costValue.originalCostBudget.total - actualCostTotal1;
					}
					if(costValue.estimateType == "Remaining Units") {
						if(costValue.revisedCostBudget.total > 0) {
							projCostValues[i].estCompletion += costValue.revisedCostBudget.total - actualCostTotal1;
							projCostValues[i].etcTotal += costValue.revisedCostBudget.total - actualCostTotal1;
						} else {
							projCostValues[i].estCompletion += costValue.originalCostBudget.total - actualCostTotal1;
							projCostValues[i].etcTotal += costValue.originalCostBudget.total - actualCostTotal1;
						}
					} else if(costValue.estimateType == "BAC-EV") {
						if(costValue.revisedCostBudget.total > 0) {
							projCostValues[i].estCompletion += (costValue.revisedCostBudget.total - costValue.earnedValue);
							projCostValues[i].etcTotal += (costValue.revisedCostBudget.total - costValue.earnedValue);
						} else {
							projCostValues[i].estCompletion += (costValue.originalCostBudget.total - costValue.earnedValue);
							projCostValues[i].etcTotal += (costValue.originalCostBudget.total - costValue.earnedValue);
						}
					} else if(costValue.estimateType == "(BAC-EV)/CPI") {
						var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
						var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
						var etcBAC_EV_CPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0)  ? bac_ev :
		            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/cpi;
						projCostValues[i].estCompletion += etcBAC_EV_CPI;
						projCostValues[i].etcTotal += etcBAC_EV_CPI;
					} else if(costValue.estimateType == "(BAC-EV)/(CPI*SPI)") {
						var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
						var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
						var spi=(costValue.earnedValue==0 || costValue.earnedValue==null || costValue.plannedValue==0) ? 0 : (costValue.earnedValue / costValue.plannedValue);
						var etcBAC_EV_CPI_SPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0 || costValue.plannedValue==0)  ? bac_ev :
		            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/(cpi*spi);
						projCostValues[i].estCompletion += etcBAC_EV_CPI_SPI;
						projCostValues[i].etcTotal += etcBAC_EV_CPI_SPI;
					} else if(costValue.estimateType == "New Estimate") {
						projCostValues[i].estCompletion += costValue.estimateCompleteBudget.total;
						projCostValues[i].etcTotal += costValue.estimateCompleteBudget.total;
					}
					projCostValues[i].estAtCompletionTotal = projCostValues[i].actualCostTotal + projCostValues[i].etcTotal;
					projCostValues[i].toCompleteCostPerformanceIndex = (projCostValues[i].estAtCompletionTotal - projCostValues[i].earnedValueTotal) / (projCostValues[i].estAtCompletionTotal - projCostValues[i].actualCostTotal);
					projCostValues[i].costPI = (projCostValues[i].estAtCompletionTotal - projCostValues[i].earnedValueTotal) / (projCostValues[i].estAtCompletionTotal - projCostValues[i].actualCostTotal);
					if (projCostValues[i].revisedBudgetTotal > 0 ) {
						projCostValues[i].progressOfWorkPercentage = (projCostValues[i].earnedValueTotal / projCostValues[i].revisedBudgetTotal)*100;
					} else {
						projCostValues[i].progressOfWorkPercentage = (projCostValues[i].earnedValueTotal / projCostValues[i].originalBudgetTotal)*100;
					}
					projCostValues[i].costVarience = (projCostValues[i].earnedValueTotal - projCostValues[i].actualCostTotal);
					projCostValues[i].costPerformanceIndex = (projCostValues[i].earnedValueTotal / projCostValues[i].actualCostTotal);
					projCostValues[i].costVariencePercentage = (projCostValues[i].costVarience / projCostValues[i].earnedValueTotal) * 100;
					projCostValues[i].costVarienceCostPercentage = (projCostValues[i].costVarience / projCostValues[i].earnedValueTotal) * 100;
					projCostValues[i].cpiCost = projCostValues[i].earnedValueTotal / projCostValues[i].actualCostTotal;
					
				}
			}
			if (!costFound) {
				let etctotal1 = 0;
				let estCompletion1 = 0;
				let remainingCostTotal = 0;
				
				var actualCostTotal1 = (costValue.actualCostBudget.labourCost
						+costValue.actualCostBudget.materialCost+costValue.actualCostBudget.plantCost+costValue.actualCostBudget.otherCost);
				if(costValue.revisedQty > 0) {
					remainingCostTotal += costValue.revisedCostBudget.total - actualCostTotal1;
				} else {
					remainingCostTotal += costValue.originalCostBudget.total - actualCostTotal1;
				}
				if(costValue.estimateType == "Remaining Units") {
					if(costValue.revisedCostBudget.total > 0) {
						estCompletion1 += costValue.revisedCostBudget.total - actualCostTotal1;
						etctotal1 += costValue.revisedCostBudget.total - actualCostTotal1;
					} else {
						estCompletion1 += costValue.originalCostBudget.total - actualCostTotal1;
						etctotal1 += costValue.originalCostBudget.total - actualCostTotal1;
					}
				} else if(costValue.estimateType == "BAC-EV") {
					if(costValue.revisedCostBudget.total > 0) {
						estCompletion1 += (costValue.revisedCostBudget.total - costValue.earnedValue);
						etctotal1 += (costValue.revisedCostBudget.total - costValue.earnedValue);
					} else {
						estCompletion1 += (costValue.originalCostBudget.total - costValue.earnedValue);
						etctotal1 += (costValue.originalCostBudget.total - costValue.earnedValue);
					}
				} else if(costValue.estimateType == "(BAC-EV)/CPI") {
					var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
					var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
					var etcBAC_EV_CPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0)  ? bac_ev :
	            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/cpi;
					estCompletion1 += etcBAC_EV_CPI;
					etctotal1 += etcBAC_EV_CPI;
				} else if(costValue.estimateType == "(BAC-EV)/(CPI*SPI)") {
					var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
					var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
					var spi=(costValue.earnedValue==0 || costValue.earnedValue==null || costValue.plannedValue==0) ? 0 : (costValue.earnedValue / costValue.plannedValue);
					var etcBAC_EV_CPI_SPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0 || costValue.plannedValue==0)  ? bac_ev :
	            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/(cpi*spi);
					estCompletion1 += etcBAC_EV_CPI_SPI;
					etctotal1 += etcBAC_EV_CPI_SPI;
				} else if(costValue.estimateType == "New Estimate") {
					estCompletion1 += costValue.estimateCompleteBudget.total;
					etctotal1 += costValue.estimateCompleteBudget.total;
				}
				projCostValues.push({
					"projId": costValue.projId,
					"epsName": $scope.searchProject.parentName, 
					"projName": $scope.searchProject.projName, 
					originalBudgetTotal: costValue.originalCostBudget.total,
					revisedBudgetTotal: costValue.revisedCostBudget.total,
					earnedValueTotal: costValue.earnedValue,
					actualCostTotal: actualCostTotal1,
					plannedValueTotal:0,
					estCompletion: estCompletion1,
					remainingBudgetTotal:remainingCostTotal,
					etcTotal:etctotal1,
					estAtCompletionTotal:0,
					toCompleteCostPerformanceIndex:0,
					progressOfWorkPercentage:0,
					costVarience:0,
					costPerformanceIndex:0,
					costVariencePercentage:0,
					costVarienceCostPercentage:0,
					scheduleVarience:0,
					scheduleVariencePercentage:0,
					costVarienceLabourHours:0
				});
			}
		}
		$scope.projCostValues = projCostValues;
		console.log("$scope.projCostValues",$scope.projCostValues);
	}
	
	function calculateManhoursTotal(projManpowerDetails) {
		let projManPowerValues = [];
		for (const value of projManpowerDetails) {
			if (!$scope.selectedTitle.includes("Original_Vs_Estimate_At_Completion_Man_Hours")) {
				if (value.projEmpCategory == 'DIRECT') {
					let found = false;
					for (let i=0; i < projManPowerValues.length; i++) {
						projManPowerValues[i].estCompletion = 0;
						if (projManPowerValues[i].projId == value.projId) {
							found = true;
							projManPowerValues[i].projId = value.projId;
							projManPowerValues[i].epsName = $scope.searchProject.parentName;
							
							projManPowerValues[i].originalHrsTotal += value.originalQty;
							if(value.revisedQty == null || value.revisedQty == undefined || value.revisedQty == ""){
								value.revisedQty=0;
							}
							projManPowerValues[i].revisedHrsTotal += value.revisedQty;
							projManPowerValues[i].actualHrsTotal += value.actualQty;
							projManPowerValues[i].earnedManHoursTotal += value.earnedValue;
							if(value.revisedQty > 0) {
								projManPowerValues[i].remainingHrsTotal += value.revisedQty - value.actualQty;
							} else {
								projManPowerValues[i].remainingHrsTotal += value.originalQty - value.actualQty;
							}
							if(value.estimateType == "Remaining Units") {
								if(value.revisedQty > 0) {
									projManPowerValues[i].estCompletion += (value.revisedQty - value.actualQty);
									projManPowerValues[i].etcTotal += value.revisedQty - value.actualQty;
								} else {
									projManPowerValues[i].estCompletion += (value.originalQty - value.actualQty);
									projManPowerValues[i].etcTotal += value.originalQty - value.actualQty;
								}
							} else if(value.estimateType == "BAC-EV") {
								if(value.revisedQty > 0) {
									projManPowerValues[i].estCompletion += (value.revisedQty - value.earnedValue);
									projManPowerValues[i].etcTotal += (value.revisedQty - value.earnedValue);
								} else {
									projManPowerValues[i].estCompletion += (value.originalQty - value.earnedValue);
									projManPowerValues[i].etcTotal += (value.originalQty - value.earnedValue);
								}
							} else if(value.estimateType == "(BAC-EV)/PF") {
								var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
								var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
								var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
								projManPowerValues[i].estCompletion += etcBAC_EV_PF;
								projManPowerValues[i].etcTotal += etcBAC_EV_PF;
							} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
								var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
								var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
								var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
								var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
								projManPowerValues[i].estCompletion += etcBAC_EV_PF_SPI;
								projManPowerValues[i].etcTotal += etcBAC_EV_PF_SPI;
							} else if(value.estimateType == "New Estimate") {
								projManPowerValues[i].estCompletion += value.estimateCompletion;
								projManPowerValues[i].etcTotal += value.estimateComplete;
							}
							projManPowerValues[i].estAtCompletionHrsTotal = projManPowerValues[i].actualHrsTotal + projManPowerValues[i].etcTotal;
							projManPowerValues[i].manPowerPI = (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].earnedManHoursTotal) / (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].actualHrsTotal);
						}
					}
					if (!found) {
						let etctotal1 = 0;
						let estCompletion1 = 0;
						let remainingTotal = 0;
						if(value.revisedQty > 0) {
							remainingTotal += value.revisedQty - value.actualQty;
						} else {
							remainingTotal += value.originalQty - value.actualQty;
						}
						if(value.estimateType == "Remaining Units") {
							if(value.revisedQty > 0) {
								estCompletion1 += (value.revisedQty - value.actualQty);
								etctotal1 += value.revisedQty - value.actualQty;
							} else {
								estCompletion1 += (value.originalQty - value.actualQty);
								etctotal1 += value.originalQty - value.actualQty;
							}
						} else if(value.estimateType == "BAC-EV") {
							if(value.revisedQty > 0) {
								estCompletion1 += (value.revisedQty - value.earnedValue);
								etctotal1 += (value.revisedQty - value.earnedValue);
							} else {
								estCompletion1 += (value.originalQty - value.earnedValue);
								etctotal1 += (value.originalQty - value.earnedValue);
							}
						} else if(value.estimateType == "(BAC-EV)/PF") {
							var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
							var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
							var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
							estCompletion1 += etcBAC_EV_PF;
							etctotal1 += etcBAC_EV_PF;
						} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
							var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
							var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
							var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
							var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
							//console.log("etcBAC_EV_PF_SPI " + etcBAC_EV_PF_SPI);
							estCompletion1 += etcBAC_EV_PF_SPI;
							etctotal1 += etcBAC_EV_PF_SPI;
						} else if(value.estimateType == "New Estimate") {
							estCompletion1 += value.estimateCompletion;
							etctotal1 += value.estimateComplete;
						}
						projManPowerValues.push({
							"projId": value.projId,
							"epsName": '', 
							"projName": $scope.searchProject.projName, 
							originalHrsTotal: value.originalQty,
							revisedHrsTotal: value.revisedQty,
							earnedManHoursTotal: value.earnedValue,
							actualHrsTotal: value.actualQty,
							remainingHrsTotal: remainingTotal,
							plannedValueTotal: 0,
							estCompletion: estCompletion1,
							etcTotal: etctotal1,
							estAtCompletionHrsTotal: 0,
							manPowerPI: 0,
							costPI: 0
						});
					}
				} 
			} else {
				let found = false;
				for (let i=0; i < projManPowerValues.length; i++) {
					projManPowerValues[i].estCompletion = 0;
					if (projManPowerValues[i].projId == value.projId) {
						found = true;
						projManPowerValues[i].projId = value.projId;
						projManPowerValues[i].epsName = $scope.searchProject.parentName;
						
						projManPowerValues[i].originalHrsTotal += value.originalQty;
						if(value.revisedQty == null || value.revisedQty == undefined || value.revisedQty == ""){
							value.revisedQty=0;
						}
						projManPowerValues[i].revisedHrsTotal += value.revisedQty;
						projManPowerValues[i].actualHrsTotal += value.actualQty;
						projManPowerValues[i].earnedManHoursTotal += value.earnedValue;
						if(value.revisedQty > 0) {
							projManPowerValues[i].remainingHrsTotal += value.revisedQty - value.actualQty;
						} else {
							projManPowerValues[i].remainingHrsTotal += value.originalQty - value.actualQty;
						}
						if(value.estimateType == "Remaining Units") {
							if(value.revisedQty > 0) {
								projManPowerValues[i].estCompletion += (value.revisedQty - value.actualQty);
								projManPowerValues[i].etcHrsTotal += value.revisedQty - value.actualQty;
							} else {
								projManPowerValues[i].estCompletion += (value.originalQty - value.actualQty);
								projManPowerValues[i].etcHrsTotal += value.originalQty - value.actualQty;
							}
						} else if(value.estimateType == "BAC-EV") {
							if(value.revisedQty > 0) {
								projManPowerValues[i].estCompletion += (value.revisedQty - value.earnedValue);
								projManPowerValues[i].etcHrsTotal += (value.revisedQty - value.earnedValue);
							} else {
								projManPowerValues[i].estCompletion += (value.originalQty - value.earnedValue);
								projManPowerValues[i].etcHrsTotal += (value.originalQty - value.earnedValue);
							}
						} else if(value.estimateType == "(BAC-EV)/PF") {
							var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
							var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
							var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
							projManPowerValues[i].estCompletion += etcBAC_EV_PF;
							projManPowerValues[i].etcHrsTotal += etcBAC_EV_PF;
						} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
							var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
							var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
							var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
							var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
							projManPowerValues[i].estCompletion += etcBAC_EV_PF_SPI;
							projManPowerValues[i].etcHrsTotal += etcBAC_EV_PF_SPI;
						} else if(value.estimateType == "New Estimate") {
							projManPowerValues[i].estCompletion += value.estimateCompletion;
							projManPowerValues[i].etcHrsTotal += value.estimateComplete;
						}
						projManPowerValues[i].estAtCompletionHrsTotal = projManPowerValues[i].actualHrsTotal + projManPowerValues[i].etcHrsTotal;
						projManPowerValues[i].manPowerPI = (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].earnedManHoursTotal) / (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].actualHrsTotal);
					}
				}
				if (!found) {
					let etctotal1 = 0;
					let estCompletion1 = 0;
					let remainingTotal = 0;
					if(value.revisedQty > 0) {
						remainingTotal += value.revisedQty - value.actualQty;
					} else {
						remainingTotal += value.originalQty - value.actualQty;
					}
					if(value.estimateType == "Remaining Units") {
						if(value.revisedQty > 0) {
							estCompletion1 += (value.revisedQty - value.actualQty);
							etctotal1 += value.revisedQty - value.actualQty;
						} else {
							estCompletion1 += (value.originalQty - value.actualQty);
							etctotal1 += value.originalQty - value.actualQty;
						}
					} else if(value.estimateType == "BAC-EV") {
						if(value.revisedQty > 0) {
							estCompletion1 += (value.revisedQty - value.earnedValue);
							etctotal1 += (value.revisedQty - value.earnedValue);
						} else {
							estCompletion1 += (value.originalQty - value.earnedValue);
							etctotal1 += (value.originalQty - value.earnedValue);
						}
					} else if(value.estimateType == "(BAC-EV)/PF") {
						var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
						var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
						var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
						estCompletion1 += etcBAC_EV_PF;
						etctotal1 += etcBAC_EV_PF;
					} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
						var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
						var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
						var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
						var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
						//console.log("etcBAC_EV_PF_SPI " + etcBAC_EV_PF_SPI);
						estCompletion1 += etcBAC_EV_PF_SPI;
						etctotal1 += etcBAC_EV_PF_SPI;
					} else if(value.estimateType == "New Estimate") {
						estCompletion1 += value.estimateCompletion;
						etctotal1 += value.estimateComplete;
					}
					projManPowerValues.push({
						"projId": value.projId,
						"epsName": '', 
						"projName": $scope.searchProject.projName, 
						originalHrsTotal: value.originalQty,
						revisedHrsTotal: value.revisedQty,
						earnedManHoursTotal: value.earnedValue,
						actualHrsTotal: value.actualQty,
						remainingHrsTotal: remainingTotal,
						plannedValueTotal: 0,
						estCompletion: estCompletion1,
						etcHrsTotal: etctotal1,
						estAtCompletionHrsTotal: 0,
						manPowerPI: 0,
						costPI: 0
					});
				}
			}
			
			
		}
		$scope.projManPowerValues = projManPowerValues;
		console.log("$scope.projManPowerValues",$scope.projManPowerValues);
	}
	
	function calculatePlannedValues(budgetValue, dailyResources, key) {
		const currentDate = new Date();
		for (const dailyResource of dailyResources) {
			const value = dailyResource[key];
			if (!value)
				continue;
			let budgetKey = key;
			let projTOs = budgetValue.projManpowerTOs;
			if (key == 'costId') {
				budgetKey = 'costCodeId';
				projTOs = budgetValue.projScheduleCostCodeTOs;
			}
			dailyResource.plannedValue = 0;
			const selectedRow = projTOs.find(x => x[budgetKey] == value);
			if (!selectedRow)
				continue;
			const startdate = selectedRow.startDate;
			if (!startdate)
				continue;
			let regularNonWorkingDays = [];
			if (budgetValue.regularHolidays) {
				regularNonWorkingDays.push((budgetValue.regularHolidays['sunday']) ? 0 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['monday']) ? 1 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['tuesday']) ? 2 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['wednesday']) ? 3 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['thursday']) ? 4 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['friday']) ? 5 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['saturday']) ? 6 : "");
			}
			let req = {
				"actualAndPlanned": false,
				"forReport": true,
				"reportPeriod": [startdate],
				"selectedItemRow": selectedRow,
				"selectedTimeScale": {
					value: 7,
					type: "Weekly"
				},
				"calNonWorkingDays": budgetValue.calNonWorkingDays,
				"regularNonWorkingDays": regularNonWorkingDays,
				"searchProject": {
					"projId": $rootScope.projId
				},
				"resourceCurveTypeMap": $scope.resourceCurveTypeMap,
				"reportProjectSetting": budgetValue.projReportsTo,
				"calSplWorkingDays": budgetValue.calSplWorkingDays
			};
			let resp = SchedulePlannedValueService.createPlannedCurve(req);
			if (resp && resp.labels) {
				for (let index = 0; index < resp.labels.length; index++) {
					if (new Date(resp.labels[index]) <= currentDate) {
						let plannedValue = resp.data[0][index];
						if (plannedValue) {
							dailyResource.plannedValue += parseFloat(plannedValue);
						}
					}
				}
			} else {
				dailyResource.plannedValue = 0;
			}
		}
	}
	
	function setGraphTimeBubbleData(calculatedValues) {
		//console.log("LENGTH", calculatedValues);
		const graphData = new Array();
		const series = new Array();
		calculatedValues.map(o => {
			graphData.push([{
				x: parseFloat(o.costVarienceManHours).toFixed(2),
				y: parseFloat(o.scheduleVarienceDirectManHours).toFixed(2),
				r: o.budget
			}]);
			series.push(o.projName);
		});
		//console.log("====",$scope.labourCvSvTabs);
		$scope.labourCvSvTabs.unshift({ 'data': graphData, 'series': series });
		//console.log("$scope.costVarSchedVarTabs",angular.copy($scope.labourCvSvTabs))
		initGraphTimeBubble();
	}
	
	
	
	function setGraphCostBubbleData(calculatedValues) {
		//console.log("LENGTH", calculatedValues);
		const graphData = new Array();
		const series = new Array();
		calculatedValues.map(o => {
			graphData.push([{
				x: parseFloat(o.costVarience).toFixed(2),
				y: parseFloat(o.scheduleVarience).toFixed(2),
				r: o.budget
			}]);
			series.push(o.projName);
		});
		//console.log("====",$scope.costCvSvBubbleTabs);
		$scope.costCvSvBubbleTabs.unshift({ 'data': graphData, 'series': series });
		//console.log("$scope.costVarSchedVarTabs",angular.copy($scope.costCvSvBubbleTabs))
		initGraphCostBubble();
	}
	
	function setGraphDataBudgetCountry(budgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in budgetDetails) {
			labels.push(budgetDetails[key].countryName);
			data.push(budgetDetails[key].budget);
		}
		$scope.budgetCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphBudgetCountry("pie");
	}
	
	function setGraphDataBudgetProvince(budgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in budgetDetails) {
			labels.push(budgetDetails[key].provisionName);
			data.push(budgetDetails[key].budget);
		}
		//console.log("data", data);
		//console.log("labels", labels);
		$scope.budgetByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraphBudgetProvince("bar");
	}
	
	function setGraphDataBudgetProject(budgetDetails) {
		const labels = new Array();
		const data = new Array();
		let index;
		for (const key in budgetDetails) {
			index = $scope.selectedProjIds.indexOf(budgetDetails[key].code);
			labels.push($scope.searchProject.projName.split(',')[index]);
			data.push(budgetDetails[key].budget);
		}
		$scope.budgetByProjTabs.unshift({ 'data': data, 'labels': labels });
		initGraphBudgetProject("bar");
	}
	
	function setGraphDataBudgetProjectManager(budgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in budgetDetails) {
			labels.push(budgetDetails[key].name);
			data.push(budgetDetails[key].budget);
		}
		$scope.budgetByProjMgrTabs.unshift({ 'data': data, 'labels': labels });
		initGraphBudgetProjectManager("pie");
	}
	
	function setGraphDataActCostCountry(ActCostDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in ActCostDetails) {
			labels.push(ActCostDetails[key].countryName);
			data.push(parseFloat(ActCostDetails[key].actualCost).toFixed(2));
		}
		$scope.acutlaCostByCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphActCostCountry("pie");
	}
	
	function setGraphDataActCostProvince(ActCostDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in ActCostDetails) {
			labels.push(ActCostDetails[key].provisionName);
			data.push(parseFloat(ActCostDetails[key].actualCost).toFixed(2));
		}
		$scope.acutlaCostByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraphActCostProvince("pie");
	}
	
	function setGraphDataActCostProject(ActCostDetails) {
		console.log("ActCostDetails",ActCostDetails);
		const currency = new Array();
		const labels = new Array();
		const data = new Array();
		const series = new Array();
		let index;
		for (const key in ActCostDetails) {
			index = $scope.selectedProjIds.indexOf(ActCostDetails[key].code);
			labels.push($scope.searchProject.projName.split(',')[index] + " (" + ActCostDetails[key].currency + ")");
			series.push($scope.searchProject.projName.split(',')[index] + " " + ActCostDetails[key].currency);
			currency.push(ActCostDetails[key].currency);
			data.push(parseFloat(ActCostDetails[key].actualCost).toFixed(2));
		}
		$scope.acutlaCostByProjectTabs.unshift({ 'data': data, 'labels': labels, 'series': series, 'currency': currency });
		initGraphActCostPrject("bar");
	}
	
	
	function setGraphDataActCostPrjManager(ActCostDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in ActCostDetails) {
			labels.push(ActCostDetails[key].name);
			data.push(parseFloat(ActCostDetails[key].actualCost).toFixed(2));
		}
		
		$scope.acutlaCostByProjectManagerTabs.unshift({ 'data': data, 'labels': labels });
		initGraphActCostProjManager("bar");
	}
	
	function setGraphDataEarnedValCountry(earnedValDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in earnedValDetails) {
			labels.push(earnedValDetails[key].countryName);
			data.push(parseFloat(earnedValDetails[key].earnedValue).toFixed(2));
		}
		$scope.earnedValueByCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEarnedValCountry("pie");
	}

	function setGraphDataEarnedValProvince(earnedValDetails) {
		//console.log(earnedValDetails);
		const labels = new Array();
		const data = new Array();
		for (const key in earnedValDetails) {
			labels.push(earnedValDetails[key].provisionName);
			data.push(parseFloat(earnedValDetails[key].earnedValue).toFixed(2));
		}
		$scope.earnedValueByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEarnedValProvince("pie");
	}
	
	function setGraphDataEarnedValProject(earnedValDetails) {
		const currency = new Array();
		const labels = new Array();
		const data = new Array();
		const series = new Array();
		let index;
		for (const key in earnedValDetails) {
			index = $scope.selectedProjIds.indexOf(earnedValDetails[key].code);
			labels.push($scope.searchProject.projName.split(',')[index]);
			data.push(parseFloat(earnedValDetails[key].earnedValue).toFixed(2));
		}
		$scope.earnedValueByProjectTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEarnedValProject("bar");
	}
	
	function setGraphDataEarnedValProjectManager(earnedValDetails) {
		console.log("earnedValDetails",earnedValDetails);
		const labels = new Array();
		const data = new Array();
		const series = new Array();
		let index;
		for (const key in earnedValDetails) {
			labels.push(earnedValDetails[key].name);
			data.push(parseFloat(earnedValDetails[key].earnedValue).toFixed(2));
		}
		$scope.earnedValueByProjectManagerTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEarnedValProjectManager("bar");
	}
	
	function setGraphDataRemBudgetCountry(remBudgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in remBudgetDetails) {
			labels.push(remBudgetDetails[key].countryName);
			data.push(parseFloat(remBudgetDetails[key].estToComplete).toFixed(2));
		}
		$scope.estimateToCompleteByCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphRemBudgetCountry("pie");
	}
	
	function setGraphDataRemBudgetProvince(remBudgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in remBudgetDetails) {
			labels.push(remBudgetDetails[key].provisionName);
			data.push(parseFloat(remBudgetDetails[key].remainingBudget).toFixed(2));
		}
		$scope.remainingBudgetByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraphRemBudgetProvince("pie");
	}
	
	function setGraphDataRemBudgetProject(remBudgetDetails) {
		console.log("remBudgetDetails",remBudgetDetails);
		const labels = new Array();
		const data = new Array();
		let index;
		for (const key in remBudgetDetails) {
			index = $scope.selectedProjIds.indexOf(remBudgetDetails[key].code);
			labels.push($scope.searchProject.projName.split(',')[index]);
			data.push(parseFloat(remBudgetDetails[key].remainingBudget).toFixed(2));
		}
		$scope.remainingBudgetByProjectTabs.unshift({ 'data': data, 'labels': labels });
		initGraphRemBudgetProject("bar");
	}
	
	function setGraphDataRemBudgetProjManager(remBudgetDetails) {
		console.log("remBudgetDetails",remBudgetDetails);
		const labels = new Array();
		const data = new Array();
		for (const key in remBudgetDetails) {
			labels.push(remBudgetDetails[key].name);
			data.push(parseFloat(remBudgetDetails[key].remainingBudget).toFixed(2));
		}
		$scope.remainingBudgetByProjectManagerTabs.unshift({ 'data': data, 'labels': labels });
		initGraphRemBudgetProjManager("bar");
	}
	
	function setGraphDataEstToCompCountry(estToCompDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in estToCompDetails) {
			labels.push(estToCompDetails[key].countryName);
			data.push(parseFloat(estToCompDetails[key].estToComplete).toFixed(2));
		}
		//console.log("data",data);
		//console.log("labels",labels);
		$scope.estimateToCompleteByCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstToCompCountry("pie");
	}

	function setGraphDataEstToCompCountry(estToCompDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in estToCompDetails) {
			labels.push(estToCompDetails[key].countryName);
			data.push(parseFloat(estToCompDetails[key].estToComplete).toFixed(2));
		}
		//console.log("data",data);
		//console.log("labels",labels);
		$scope.estimateToCompleteByCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstToCompCountry("pie");
	}
	
	function setGraphDataEstToCompProvince(EstToCompDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in EstToCompDetails) {
			labels.push(EstToCompDetails[key].provisionName);
			data.push(parseFloat(EstToCompDetails[key].estToComplete).toFixed(2));
		}
		$scope.estimateToCompleteByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstToCompProvince("pie");
	}
	
	function setGraphDataEstToCompProject(EstToCompDetails) {
		const labels = new Array();
		const data = new Array();
		let index;
		for (const key in EstToCompDetails) {
			index = $scope.selectedProjIds.indexOf(EstToCompDetails[key].code);
			labels.push($scope.searchProject.projName.split(',')[index]);
			data.push(parseFloat(EstToCompDetails[key].estToComplete).toFixed(2));
		}
		$scope.estimateToCompleteByProjectTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstToCompProject("bar");
	}
	
	function setGraphDataEstToCompProjManager(EstToCompDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in EstToCompDetails) {
			labels.push(EstToCompDetails[key].name);
			data.push(parseFloat(EstToCompDetails[key].estToComplete).toFixed(2));
		}
		$scope.estimateToCompleteByProjMgrTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstToCompProjManager("bar");
	}
	
	function setGraphDataEstAtCompletionCountry(estAtCompletionDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in estAtCompletionDetails) {
			labels.push(estAtCompletionDetails[key].countryName);
			data.push(parseFloat(estAtCompletionDetails[key].estAtCompletion).toFixed(2));
		}
		$scope.estimateCompletionByCountryTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstAtCompletionCountry("pie");
	}
	
	function setGraphDataEstAtCompletionProvince(estAtCompletionDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in estAtCompletionDetails) {
			labels.push(estAtCompletionDetails[key].provisionName);
			data.push(parseFloat(estAtCompletionDetails[key].estAtCompletion).toFixed(2));
		}
		$scope.estimateCompletionByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstAtCompletionProvince("pie");
	}
	
	function setGraphDataEstAtCompletionProject(estAtCompletionDetails) {
		const labels = new Array();
		const data = new Array();
		let index;
		for (const key in estAtCompletionDetails) {
			index = $scope.selectedProjIds.indexOf(estAtCompletionDetails[key].code);
			labels.push($scope.searchProject.projName.split(',')[index]);
			data.push(parseFloat(estAtCompletionDetails[key].estAtCompletion).toFixed(2));
		}
		$scope.estimateCompletionByProjTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstAtCompletionProject("pie");
	}
	
	function setGraphDataEstAtCompletionProjManager(estAtCompletionDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in estAtCompletionDetails) {
			labels.push(estAtCompletionDetails[key].name);
			data.push(parseFloat(estAtCompletionDetails[key].estAtCompletion).toFixed(2));
		}
		$scope.estimateCompletionByProjMgrTabs.unshift({ 'data': data, 'labels': labels });
		initGraphEstAtCompletionProjManager("bar");
	}
	
	function initGraphTimeBubble() {
		$scope.colors = ['#00ff00', '#ff0000', '#0000ff', '#000000', '#abcdef','#f70000','#003af7', '#f79000', '#f7eb00', '#00f780', '#00d2f7','#f70077' ];
		if ($scope.selectedTitle.includes("CV_And_SV_For_Labour_Bubble_Chart")) {
			$scope.options = {
					title: {
						display: true,
						text: 'Cost and Schedule Variance for Labour Bubble Chart'
					},
					legend: { display: true },
					scales: {
						xAxes: [{
							scaleLabel: {
								display: true,
								labelString: 'Cost Variance'
							},
							ticks: {
								beginAtZero: true
							}

						}],
						yAxes: [{
							scaleLabel: {
								display: true,
								labelString: 'Schedule Variance'
							},
							ticks: {
								beginAtZero: true
							}
						}]
					}
				}
		} else {
			$scope.options = {
					title: {
						display: true,
						text: 'Cost and Schedule Variance for Man Hours Bubble Chart'
					},
					legend: { display: true },
					scales: {
						xAxes: [{
							scaleLabel: {
								display: true,
								labelString: 'Cost Variance'
							},
							ticks: {
								beginAtZero: true
							}

						}],
						yAxes: [{
							scaleLabel: {
								display: true,
								labelString: 'Schedule Variance'
							},
							ticks: {
								beginAtZero: true
							}
						}]
					}
				}
		}
		
	}
	
	function initGraphCostBubble() {
		$scope.colors = ['#00ff00', '#ff0000', '#0000ff', '#000000', '#abcdef','#f70000','#003af7', '#f79000', '#f7eb00', '#00f780', '#00d2f7','#f70077' ];
		$scope.options = {
			title: {
				display: true,
				text: 'Cost and Schedule Variance for Cost Bubble Chart'
			},
			scales: {
				xAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Cost Variance'
					},
					ticks: {
						beginAtZero: true
					}

				}],
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Schedule Variance'
					},
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	};
	
	function initGraphBudgetCountry(type) {
		//$scope.colors = ['#00ff00', '#ff0000', '#0000ff', '#000000', '#abcdef'];
		$scope.chart_type = type;
		$scope.options = {
			legend: {
				display: false,
			},
			title: {
				display: true,
				text: 'Budget by Country'
			},
			legend: {
				display: true,
				position: "bottom"
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
					ticks: {
						reverse: false,
						beginAtZero: true
					}
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						reverse: false,
						beginAtZero: true
					}
				}]
			}
		};
		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;

		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
				$scope.options.legend.display = false;
			}
		}
	};
	
	function initGraphBudgetProvince(type) {
		//$scope.colors = ['#00ff00', '#ff0000', '#0000ff', '#000000', '#abcdef'];
		$scope.chart_type = type;
		$scope.options = {
			legend: {
				display: false,
			},
			title: {
				display: true,
				text: 'Budget by Province'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
					ticks: {
						reverse: false,
						beginAtZero: true
					}
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						reverse: false,
						beginAtZero: true
					}
				}]
			}
		};
		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;

		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
				$scope.options.legend.display = false;
			}
		}
	};
	
	
	function initGraphBudgetProject(type) {
		$scope.chart_type = type;
		$scope.options = {
			/* title: {
				display: true,
				text: 'Earned Value',
				position: 'bottom'
			}, */
			legend: {
				display: false,
			},
			title: {
				display: true,
				text: 'Budget by project'
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
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	function initGraphBudgetProjectManager(type) {
		$scope.chart_type = type;
		$scope.options = {
			/*title: {
				display: true,
				text: 'Budget'
			},*/
			legend: {
				display: true,
				position: "bottom"
			},
			title: {
				display: true,
				text: 'Budget by Project Manager'
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
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	};
	
	
	function initGraphActCostCountry(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Actual Cost by Country'

			},
			legend: {
				display: true,
				position: 'bottom'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,

				}],

			}
		};
		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	};
	
	function initGraphActCostProvince(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Actual Cost by Province'

			},
			legend: {
				display: true,
				position: 'bottom'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,

				}],

			}
		};
		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	};
	
	function initGraphActCostPrject(type) {
		$scope.chart_type = type;
		//$scope.yAxislabels = 'HOURS';
		//chartService.defaultBarInit($scope.yAxislabels);
		$scope.options = {
			title: {
				display: true,
				text: 'Actual Cost by Project',
				position: 'top'
			},
			/*
			legend: {
				display: true,
				position: 'bottom',
				maxLines: 0,
			},
			*/
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
					/*ticks: {
						display: false
					}*/
				}],
				yAxes: [{
					display: true,
					stacked: true,

				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	};
	
	function initGraphActCostProjManager(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Actual Cost by Project Manager'
			},
			/*legend: {
				display: true,
				position: 'bottom'
			},*/
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};
		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}


	};
	
	function initGraphEarnedValCountry(type) {
		$scope.chart_type = type;

		$scope.options = {
			title: {
				display: true,
				text: 'Earned Value by Country',
				position: 'top'

			},
			legend: {
				display: true,
				position: 'bottom'
			},
			/*scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			} */
		};
		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	};
	
	function initGraphEarnedValProvince(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Earned Value by Province	',
				position: 'top'
			},
			legend: {
				display: true,
				position: 'bottom'
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
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphEarnedValProject(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Earned Value by Project',
				position: 'top'
			},
			/*legend: {
				display: true,
				position: 'bottom'
			},*/
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphEarnedValProjectManager(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Earned Value by Project Manager',
				position: 'top'
			},
			/*legend: {
				display: true,
				position: 'bottom'
			},*/
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphRemBudgetCountry(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Remaining Budget by Country'

			},
			legend: {
				display: true,
				position: 'bottom'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					/*ticks: {
						beginAtZero: true
					} */
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphRemBudgetProvince(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Remaining Budget by Province'

			},
			legend: {
				display: true,
				position: 'bottom'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					/*ticks: {
						beginAtZero: true
					} */
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphRemBudgetProject(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Remaining Budget by Project',
			},
			/* legend: {
				display: true,
				position: 'bottom',
				maxLines: 0,
			}, */
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphRemBudgetProjManager(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Remaining Budget By Project Manager',
				position: 'top'
			},
			/* legend: {
				display: true,
				position: 'bottom'
			}, */
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphEstToCompCountry(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate To Complete by Country'
			},
			legend: {
				display: true,
				position: "bottom"
			},
			scales: {
				xAxes: [{
					stacked: true,
					display: true,
				}],
				yAxes: [{
					stacked: true,
					display: true,
				}],
			}
		};
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	
	function initGraphEstToCompProvince(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate To Complete by Province',
			},
			legend: {
				display: true,
				position: "bottom",
			},
		};
	}

	function initGraphEstToCompProject(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate To Complete by Project',
			},
			/* legend: {
				display: true,
				position: 'bottom',
				maxLines: 0,
			}, */
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphEstToCompProjManager(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate To Complete by Project Manager',
			},
			/* legend: {
				display: true,
				position: 'bottom',
				maxLines: 0,
			}, */
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphEstAtCompletionCountry(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate At Completion by Country'
			},
			legend: {
				display: true,
				position: "bottom"
			},
			scales: {
				xAxes: [{
					stacked: true,
					display: true,
				}],
				yAxes: [{
					stacked: true,
					display: true,
				}],
			}
		};
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}
	}
	
	function initGraphEstAtCompletionProvince(type) {
		$scope.chart_type = type;
		$scope.options = {

			title: {
				display: true,
				text: 'Estimate At Completion',
			},
			legend: {
				display: true,
				position: "bottom",
			},
		};
	}
	
	function initGraphEstAtCompletionProject(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate At Completion by Project'

			},
			/*legend: {
				display: true,
				position: "bottom"

			},*/
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			}
		};
	}
	
	function initGraphEstAtCompletionProjManager(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Estimate At Completion by Project Manager'
			},
			scales: {
				xAxes: [{
					stacked: true,
					display: true,
				}],
				yAxes: [{
					stacked: true,
					display: true,
				}],

			}
		};
	}


		$scope.periodicReportChanged = function() {
		let newColumnDefs = angular.copy(columnDefs);
		newColumnDefs[0].displayName = "Period - " + $scope.periodicReport.name;
		$scope.gridOptions = ngGridService.initGrid($scope, newColumnDefs, []);
		if ($scope.periodicReport.code == 'weekly') {
			ProjectSettingsService.projReportsOnLoad({projId: $scope.searchFilter1.projId, status: 1}).then(function (data) {
				weekBeginsOn = data.projectReportsTOs[0].week;
				if (weekDays.indexOf(weekBeginsOn) == 0)
					weekEndsOn = weekDays[weekDays.length - 1];
				else
					weekEndsOn = weekDays[weekDays.indexOf(weekBeginsOn) - 1];
				prepareReport();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
			});
		} else {
			prepareReport();
		}
	}
	
	let prepareReport = function () {
    	let reportData = [];
    	let plannedRunningTotal = 0, actualRunningTotal = 0, earnedActualRunningTotal = 0, earnedForecastRunningTotal = 0, forecastToCompleteRunningTotal = 0;
    	let actualCummulative = 0, earnedToDateCummulative = 0;
    	for (let i=0; i < progressSCurveTOs.length; i++) {
    		actualCummulative += progressSCurveTOs[i].actual;
    		earnedToDateCummulative += progressSCurveTOs[i].earnedTodate;
    	}
		for (let i=0; i < progressSCurveTOs.length; i++) {
			let progressSCurveTO = {};
			if ($scope.periodicReport.filter == "week")
				progressSCurveTO.period = weekStartEndDateFormat(progressSCurveTOs[i].date);
			else
				progressSCurveTO.period = $filter('date')(progressSCurveTOs[i].date, $scope.periodicReport.filter);
			progressSCurveTO.planned = progressSCurveTOs[i].planned;
			progressSCurveTO.actual = progressSCurveTOs[i].actual;
			progressSCurveTO.earnedActual = progressSCurveTOs[i].earnedTodate;
			progressSCurveTO.earnedForecast = progressSCurveTOs[i].earnedForecast;
			progressSCurveTO.forecastToComplete = progressSCurveTOs[i].forecastToComplete;
			plannedRunningTotal += progressSCurveTOs[i].planned;
			actualRunningTotal += progressSCurveTOs[i].actual;
			earnedActualRunningTotal += progressSCurveTOs[i].earnedTodate;
			earnedForecastRunningTotal += progressSCurveTOs[i].earnedForecast;
			forecastToCompleteRunningTotal += progressSCurveTOs[i].forecastToComplete;
			progressSCurveTO.plannedCummulative = plannedRunningTotal;
			progressSCurveTO.actualCummulative = actualRunningTotal;
			progressSCurveTO.earnedActualCummulative = earnedActualRunningTotal;
			if ($scope.scheduleDate < progressSCurveTOs[i].date) {
				progressSCurveTO.earnedForecastCummulative = earnedToDateCummulative + earnedForecastRunningTotal;
				progressSCurveTO.forecastToCompleteCummulative = actualCummulative + forecastToCompleteRunningTotal;
			} else {
				progressSCurveTO.earnedForecastCummulative = 0;
				progressSCurveTO.forecastToCompleteCummulative = 0;
			}
			
			addToReportData(reportData, progressSCurveTO);
		}
		$scope.gridOptions.data = reportData;
		prepareChart();
    };

    let addToReportData = function (reportData, progressSCurveTO) {
    	let found = false;
    	for (let i=0; i < reportData.length; i++) {
    		if (reportData[i].period == progressSCurveTO.period) {
    			reportData[i].planned += progressSCurveTO.planned;
    			reportData[i].plannedCummulative = progressSCurveTO.plannedCummulative;
    			reportData[i].earnedActual += progressSCurveTO.earnedActual;
    			reportData[i].earnedActualCummulative = progressSCurveTO.earnedActualCummulative;
    			reportData[i].actual += progressSCurveTO.actual;
    			reportData[i].actualCummulative = progressSCurveTO.actualCummulative;
    			reportData[i].earnedForecast += progressSCurveTO.earnedForecast;
    			reportData[i].earnedForecastCummulative = progressSCurveTO.earnedForecastCummulative;
    			reportData[i].forecastToComplete += progressSCurveTO.forecastToComplete;
    			reportData[i].forecastToCompleteCummulative = progressSCurveTO.forecastToCompleteCummulative;
    			found = true;
    		}
    	}
    	if (!found) reportData.push(progressSCurveTO);
    }

 let weekStartEndDateFormat = function (date) {
    	let startDate = new Date(angular.copy(date));
    	while ($filter('date')(startDate, "EEEE") != weekBeginsOn)
    		startDate.setDate(startDate.getDate() - 1);

    	let endDate = new Date(angular.copy(date));
    	while ($filter('date')(endDate, "EEEE") != weekEndsOn)
    		endDate.setDate(endDate.getDate() + 1);

    	return startDate.getDate() + "-" + endDate.getDate() + " " + $filter('date')(endDate, "MMM yyyy");
    }


    let prepareChart = function() {
    	let isScheduleDay = true;
    	let beforeScheduleDate = true;
    	let formattedScheduleDate = "";
    	if ($scope.periodicReport.filter == "week") 
    		formattedScheduleDate = weekStartEndDateFormat($scope.scheduleDate);
		else
			formattedScheduleDate = $filter('date')($scope.scheduleDate, $scope.periodicReport.filter);
    	let labels = [], planned = [], earnedActual = [], actual = [], earnedForecast = [], forecastToComplete = [];
    	for (let i=0; i < $scope.gridOptions.data.length; i++) {
    		labels.push($scope.gridOptions.data[i].period);
    		planned.push(parseFloat($scope.gridOptions.data[i].plannedCummulative).toFixed(2));
    		if (beforeScheduleDate && $scope.gridOptions.data[i].period == formattedScheduleDate) beforeScheduleDate = false;
    		if (beforeScheduleDate) {
    			earnedActual.push(parseFloat($scope.gridOptions.data[i].earnedActualCummulative).toFixed(2));
        		actual.push(parseFloat($scope.gridOptions.data[i].actualCummulative).toFixed(2));
        		earnedForecast.push(null);
        		forecastToComplete.push(null);
    		} else {
    			if (isScheduleDay) {
    				earnedActual.push(parseFloat($scope.gridOptions.data[i].earnedActualCummulative).toFixed(2));
            		actual.push(parseFloat($scope.gridOptions.data[i].actualCummulative).toFixed(2));
            		earnedForecast.push(parseFloat($scope.gridOptions.data[i].earnedActualCummulative).toFixed(2));
            		forecastToComplete.push(parseFloat($scope.gridOptions.data[i].actualCummulative).toFixed(2));
            		isScheduleDay = false;
    			} else {
    				earnedActual.push(null);
    				actual.push(null);
    				earnedForecast.push(parseFloat($scope.gridOptions.data[i].earnedForecastCummulative).toFixed(2));
            		forecastToComplete.push(parseFloat($scope.gridOptions.data[i].forecastToCompleteCummulative).toFixed(2));
    			}
    		}
    	}
    	$scope.chartLabels = labels;
    	$scope.chartData = [planned, earnedActual, actual, earnedForecast, forecastToComplete];
    }
	
	/*Periodical Productivity Rates - Major Items of Work*/
	
	
	$scope.getTangibleItems1 = function () {
		if ($scope.searchFilter2.searchProject == null || $scope.searchFilter2.searchProject.projId == 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Info');
			return;
		}
		PickTangibleFactory.selectMultiple([$scope.searchFilter2.searchProject.projId], selectedTangibleItems1, true).then(function (data) {
			selectedTangibleItems1 = data;
			$scope.searchFilter2.searchProject.selectedTangibleItemNames = "";
			for (let i=0; i < data.length; i++)
				$scope.searchFilter2.searchProject.selectedTangibleItemNames += data[i].tangibleCode + ", ";
			if ($scope.searchFilter2.searchProject.selectedTangibleItemNames.length > 0)
				$scope.searchFilter2.searchProject.selectedTangibleItemNames = $scope.searchFilter2.searchProject.selectedTangibleItemNames.substr(0, $scope.searchFilter2.searchProject.selectedTangibleItemNames.length-2);
				$scope.generateReport8($scope.selectedTimeScale1);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}
		$scope.generateReport8 = function (selectedTimeScale1) {
			//console.log(selectedTimeScale1,"sk");
		$scope.resetChartDetails8();
		if ($scope.searchFilter2.searchProject == null || $scope.searchFilter2.searchProject.projId == 0) {
			GenericAlertService.alertMessage("Please select Project", 'Info');
			return;
		}
		if (selectedTangibleItems1.length == 0) {
			GenericAlertService.alertMessage("Please select Tangible Items", 'Info');
			return;
		}
		ProjectSettingsService.getManpowerProductivityAnalysisReportData({projectIds: [$scope.searchFilter2.searchProject.projId], 
			projectTangibleTOs: selectedTangibleItems1, fromDate: $scope.searchFilter2.projectFromDate, toDate: $scope.searchFilter2.projectToDate}).then(function (data) {
				projectTangibleTOs1 = data.projectTangibleTOs;
				//console.log(projectTangibleTOs1,"genrate report");
				$scope.subReportChanged8(selectedTimeScale1);
				if (data.projectTangibleTOs.length == 0) GenericAlertService.alertMessage("Data not available for the selected criteria", 'Info');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		});
	}
	
	$scope.subReportChanged8 = function (selectedTimeScale1) {		
		$scope.resetChartDetails8();
		let periodicalData = [];
		let weekStartEndDateFormat = function (date) {			
    	let startDate = new Date(angular.copy(date));
       
    	while ($filter('date')(startDate, "EEEE") != weekBeginsOn1)
    		startDate.setDate(startDate.getDate() - 1);
      
    	let endDate = new Date(angular.copy(date));
         
    	while ($filter('date')(endDate, "EEEE") != weekEndsOn1)
    		endDate.setDate(endDate.getDate() + 1);
        
    	return startDate.getDate() + "-" + endDate.getDate() + " " + $filter('date')(endDate, "MMM yyyy");
    }
		for (let i=0; i<projectTangibleTOs1.length; i++) {
			//console.log(projectTangibleTOs1[i]);
			$scope.period = '';
			//console.log(selectedTimeScale1.filter);
			
			if (selectedTimeScale1.filter == "week" )
				$scope.period = weekStartEndDateFormat(projectTangibleTOs1[i].date);
			else
				$scope.period = $filter('date')(projectTangibleTOs1[i].date, selectedTimeScale1.filter);
			//console.log($scope.period);
			let index = periodicalData.findIndex(e => e.period == $scope.period && e.tangibleItemId == projectTangibleTOs1[i].tangibleItemId);
			if (index == -1) {
				periodicalData.push({
					period: $scope.period,
					tangibleCode: projectTangibleTOs1[i].tangibleCode,
					tangibleName: projectTangibleTOs1[i].tangibleName,
					uom: projectTangibleTOs1[i].uom,
					estimatedQuantity: projectTangibleTOs1[i].estimatedQuantity,
					estimatedHours: projectTangibleTOs1[i].estimatedHours,
					actualQuantity: projectTangibleTOs1[i].actualQuantity,
					actualHours: projectTangibleTOs1[i].actualHours});
			} else {
				periodicalData[index].estimatedQuantity += projectTangibleTOs1[i].estimatedQuantity;
				periodicalData[index].estimatedHours += projectTangibleTOs1[i].estimatedHours;
				periodicalData[index].actualQuantity += projectTangibleTOs1[i].actualQuantity;
				periodicalData[index].actualHours += projectTangibleTOs1[i].actualHours;
			}
		}
		console.log(periodicalData,"periodicalData report");
		prepareChartData8(periodicalData);
	};
	
	
	 $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
	 $scope.labels1 = [];
	 $scope.data=[];

 function prepareChartData8(periodicalData) {	
	$scope.chartData=periodicalData;
let chartData11 = [];
		let chartReportMap = [];
		let actual=[];
		for (let i=0; i<periodicalData.length; i++) {
			let index = chartData11.findIndex(e => e.period == periodicalData[i].period);
			if (index == -1) {
				
				chartData11.push({period: periodicalData[i].period,
					estimatedProductivity: periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours,
					actualProductivity: periodicalData[i].actualQuantity / periodicalData[i].actualHours});
			} else {		
				chartData11[index].estimatedProductivity += periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours;				
				chartData11[index].actualProductivity += periodicalData[i].actualQuantity / periodicalData[i].actualHours;			
			}
		}		
		$scope.chartData111=chartData11;	
 for (const catDtl of $scope.chartData111) {	
	$scope.series=['Actual Productivity','Estimated Productivity'];		
	 let mapKey;
	 let mapValue;
	 let estimatedProductivity;
	 let actualProductivity;
	 let parentName;
		 mapKey = catDtl.period;
		 mapValue = catDtl.period;
		 parentName=catDtl.parentName;
     	 estimatedProductivity=parseFloat(catDtl.estimatedProductivity).toFixed(2);
		 actualProductivity=parseFloat(catDtl.actualProductivity).toFixed(2);
		 if(actualProductivity == Infinity){
			actualProductivity = 0;
		}
	 if (!chartReportMap[mapKey]) {		
		 chartReportMap[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "parentName": parentName,
			 "estimatedProductivity": estimatedProductivity,
			 "actualProductivity": actualProductivity
		 };
	   }			
 }		
	 setGraphData8(chartReportMap);			
 };
function setGraphData8(chartReportMap) {
	
        series=series1;	
		$scope.labels = new Array();
		$scope.data = new Array();		
		let cumulativeActualValue = 0;	
			for (const index in chartReportMap) {
			cumulativeActualValue += parseFloat(chartReportMap[index].actualProductivity);
			chartReportMap[index].cumulativeActual = cumulativeActualValue;
			}
		const valueArr = new Array();
		const plannedArr = new Array();
		const valueArrCum = new Array();
		
		$scope.subReportData = new Array();
		for (let plan of $scope.subReportData) {		
				plan.cumulativeActual = parseFloat(plan.cumulativeActual).toFixed(2);
			}
		$scope.subReportData = new Array();
		for (const index in chartReportMap) {	
			valueArr.push(chartReportMap[index].estimatedProductivity);		
			plannedArr.push(chartReportMap[index].actualProductivity);			
			valueArrCum.push(chartReportMap[index].cumulativeActual);
			$scope.labels.push(chartReportMap[index].mapKey);
		}
		$scope.data.push(valueArr);
		$scope.data.push(plannedArr);	
		$scope.data.push(valueArrCum);
		initGraph8(series);
 }; 
 $scope.data= [];
 $scope.labels1= [];
 function initGraph8(graphDataPropertiesArray) {	
		  
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
							labelString: 'Cum  per Hour',
							fontSize: "15"
						}
					}]
				}
			};
			
			$scope.datasetOverride = new Array();
			for (let i=0;i<graphDataPropertiesArray.length;i++) {
				if (graphDataPropertiesArray[i]=="Cum Actual Productivity") {
					$scope.datasetOverride.push({
						label: graphDataPropertiesArray[i],
						borderWidth: 3,
						type: 'line',
						fill: false,
					});
				}				
				 else {
					$scope.datasetOverride.push({
						label: graphDataPropertiesArray[i],
						borderWidth: 1,
						type: 'bar',
					});
				}
			}	
 };
$scope.resetChartDetails8 = function () {
	 $scope.chartData=[];
	 $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
	 $scope.labels1 = [];
	 $scope.data=[];
     $scope.Labels = [];
	 $scope.series = [];			
	 $scope.datasetOverride = [];
	 $scope.chart_type = [];
	}
		
		/*Productivity Rates - Major Items of Work*/
		
	let selectedTangibleItemsSk = [];
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'Units Per Hour';
	$scope.searchFilterSk = {};
				
		
		$scope.getTangibleItemsSk = function () {
		if ($scope.searchFilterSk.searchProject == null || $scope.searchFilterSk.searchProject.projId == 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Info');
			return;
		}
		PickTangibleFactory.selectMultiple([$scope.searchFilterSk.searchProject.projId], selectedTangibleItemsSk, true).then(function (data) {
			selectedTangibleItemsSk = data;
			//console.log(selectedTangibleItemsSk,"sk123");
			$scope.searchFilterSk.searchProject.selectedTangibleItemNamesSk = "";
			for (let i=0; i < data.length; i++)
				$scope.searchFilterSk.searchProject.selectedTangibleItemNamesSk += data[i].tangibleCode + ", ";
			if ($scope.searchFilterSk.searchProject.selectedTangibleItemNamesSk.length > 0)
				$scope.searchFilterSk.searchProject.selectedTangibleItemNamesSk = $scope.searchFilterSk.searchProject.selectedTangibleItemNamesSk.substr(0, $scope.searchFilterSk.searchProject.selectedTangibleItemNamesSk.length-2);
				$scope.generateReportSk();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}
		
		
		$scope.generateReportSk = function () {
			
		if ($scope.searchFilterSk.searchProject == null || $scope.searchFilterSk.searchProject.projId == 0) {
			GenericAlertService.alertMessage("Please select Project", 'Info');
			return;
		}
		if (selectedTangibleItemsSk.length == 0) {
			GenericAlertService.alertMessage("Please select Tangible Items", 'Info');
			return;
		}
		ProjectSettingsService.getManpowerProductivityAnalysisReportData({projectIds: [$scope.searchFilterSk.searchProject.projId], 
			projectTangibleTOs: selectedTangibleItemsSk, fromDate: $scope.searchFilterSk.projectFromDate, toDate: $scope.searchFilterSk.projectToDate}).then(function (data) {
				$scope.resetChartDetailsSk();
			    prepareChartDataSk(data.projectTangibleTOs);
				$scope.chartDataSk=data.projectTangibleTOs;
				if (data.projectTangibleTOs.length == 0) GenericAlertService.alertMessage("Data not available for the selected criteria", 'Info');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		});
	};
		
	 $scope.estimatedProductivitySk1 = [];
     $scope.actualProductivitySk1 = [];
     $scope.estimatedProductivitySk11 = [];
     $scope.actualProductivitySk22 = [];
	 $scope.labelsSk1 = [];
	 $scope.dataLad=[];
 function setGraphDataSk(chartReportMapSk) {
	//console.log(chartReportMapSk);
	 for (const index in chartReportMapSk) {
	 $scope.estimatedProductivitySk1.push(chartReportMapSk[index].estimatedProductivitySk1);
     $scope.actualProductivitySk1.push(chartReportMapSk[index].actualProductivitySk1);
	 $scope.labelsSk1.push(chartReportMapSk[index].mapKey);
 }
for (var i=0;i<$scope.estimatedProductivitySk1.length;i++)
   {
	  var dataSk = [];
for (var j=0;j<$scope.estimatedProductivitySk1.length;j++)
   {
	if(i==0)
	{
	dataSk.push($scope.actualProductivitySk1[j]);
	}
		if(i==1)
	{
		  dataSk.push($scope.estimatedProductivitySk1[j]);				
	}      
}
		$scope.dataLad.push(dataSk);
}
	 initGraphSk();
 };

 $scope.dataLad= [];
 $scope.labelsSk1= [];
 function initGraphSk() {	
		    $scope.seriesSk1 = ['Actual Productivity','Estimated Productivity'];
	        $scope.labelsSk = $scope.labelsSk1;				
			$scope.datasetOverride = new Array();		
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
			$scope.options.scales.xAxes[0].stacked = false;
	 		$scope.options.scales.yAxes[0].stacked = false;
 };
		
		
	 $scope.resetChartDetailsSk = function () {
	 $scope.estimatedProductivitySk1 = [];
     $scope.actualProductivitySk1 = [];
	 $scope.labelsSk1 = [];
	 $scope.data=[];
     $scope.LabelsSk = [];
	 $scope.seriesSk = [];			
	 $scope.datasetOverride = [];
	 $scope.chart_type = [];
	}
	
	
	
	let prepareChartDataSk = function (periodicalDataSk) {
		let chartDataSk = [];
		let chartReportMapSk = [];
		let actual=[];
		for (let i=0; i<periodicalDataSk.length; i++) {
			let index = chartDataSk.findIndex(e => e.tangibleItemId == periodicalDataSk[i].tangibleItemId);
			if (index == -1) {				
				chartDataSk.push({tangibleItemId: periodicalDataSk[i].tangibleItemId, tangibleName: periodicalDataSk[i].tangibleName,
					estimatedProductivity: periodicalDataSk[i].estimatedQuantity / periodicalDataSk[i].estimatedHours,
					actualProductivity: periodicalDataSk[i].actualQuantity / periodicalDataSk[i].actualHours});
			} else {		
				chartDataSk[index].estimatedProductivity += periodicalDataSk[i].estimatedQuantity / periodicalDataSk[i].estimatedHours;				
				chartDataSk[index].actualProductivity += periodicalDataSk[i].actualQuantity / periodicalDataSk[i].actualHours;			
			}
		}		
$scope.chartDataSk1=chartDataSk;
for (const catDtl of $scope.chartDataSk1) {		
     let mapKey;
	 let mapValue;
	 let estimatedProductivitySk1;
	 let actualProductivitySk1;
	 let parentName;
		 mapKey = catDtl.tangibleName;
		 mapValue = catDtl.tangibleName;
     	 estimatedProductivitySk1 =parseFloat(catDtl.estimatedProductivity).toFixed(2);
		 actualProductivitySk1 =parseFloat(catDtl.actualProductivity).toFixed(2);
	
	   if(actualProductivitySk1 == Infinity){
			actualProductivitySk1 = 0;
		 }
		if(estimatedProductivitySk1 == Infinity){
			estimatedProductivitySk1 = 0;
		}


       if (!chartReportMapSk[mapKey]) {		
		 chartReportMapSk[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "estimatedProductivitySk1": estimatedProductivitySk1,
			 "actualProductivitySk1":actualProductivitySk1
	
		 };
	   }

            }
		setGraphDataSk(chartReportMapSk);	
		
	};
		
	/*S Curve - Labour*/	
	$scope.periodicReportsCurve = [
    	{name: 'Daily', code: "daily", filter: "dd-MMM-yyyy"}, 
    	{name: 'Weekly', code: "weekly", filter: "week"}, 
    	{name: 'Monthly', code: "monthly", filter: "MMM-yyyy"}, 
    	{name: 'Yearly', code: "yearly", filter: "yyyy"}
    ];


$scope.periodicReportCurve = $scope.periodicReportsCurve[0];
    $scope.type = 'chart';
    $scope.scheduleDateCurve;
    $scope.chartSeriesCurve = ['Budget', 'Earned To Date', 'Actual', 'Earned Forecast', 'Forecast To Complete'];
    $scope.chartOptionsCurve = {elements: {line: {fill: false, borderWidth: 5}}, legend: {display: true, position: 'top'}};
    $scope.datasetOverrideCurve = [
    	{pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{borderDash: [8,4], pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    	{borderDash: [8,4], pointRadius: 1, pointHoverRadius: 5,pointBorderWidth: 0},
    ]

 let columnDefsCurve = [
		{ field: 'period', displayName: "Period - " + $scope.periodicReportCurve.name, headerTooltip: 'Period' },
		{ field: 'planned', displayName: "Budget-Periodical", headerTooltip: 'Budget Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'plannedCummulative', displayName: "Budget-Cummulative", headerTooltip: 'Budget Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedActual', displayName: "Earned to date-Periodical", headerTooltip: 'Earned to date Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedActualCummulative', displayName: "Earned to date-Cummulative", headerTooltip: 'Earned to date Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'actual', displayName: "Actual to date-Periodical", headerTooltip: 'Actual to date Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'actualCummulative', displayName: "Actual to date-Cummulative", headerTooltip: 'Actual to date Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedForecast', displayName: "Earned Forecast-Periodical", headerTooltip: 'Earned Forecast Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'earnedForecastCummulative', displayName: "Earned Forecast-Cummulative", headerTooltip: 'Earned Forecast Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'forecastToComplete', displayName: "Forecast to Complete-Periodical", headerTooltip: 'Forecast to Complete Periodical', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
		{ field: 'forecastToCompleteCummulative', displayName: "Forecast to Complete-Cummulative", headerTooltip: 'Forecast to Complete Cummulative', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
	];
	
	let progressSCurveTOsCurve = [];
    let weekDaysCurve = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let weekBeginsOnCurve = 'Monday';
    let weekEndsOnCurve = 'Sunday';

 $scope.gridOptionsCurve = ngGridService.initGrid($scope, columnDefsCurve, [], "Dashboards_progress_SCurve-Labour");
		$scope.periodicReportChangedCurve = function() {
		let newColumnDefsCurve = angular.copy(columnDefsCurve);
		newColumnDefsCurve[0].displayName = "Period - " + $scope.periodicReportCurve.name;
		$scope.gridOptionsCurve = ngGridService.initGrid($scope, newColumnDefsCurve, []);
		if ($scope.periodicReportCurve.code == 'weekly') {
			ProjectSettingsService.projReportsOnLoad({projId: $scope.searchFilterCurve.projId, status: 1}).then(function (data) {
				weekBeginsOnCurve = data.projectReportsTOs[0].week;
				if (weekDaysCurve.indexOf(weekBeginsOnCurve) == 0)
					weekEndsOnCurve = weekDaysCurve[weekDaysCurve.length - 1];
				else
					weekEndsOnCurve = weekDaysCurve[weekDaysCurve.indexOf(weekBeginsOnCurve) - 1];
				prepareReportCurve();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
			});
		} else {
			prepareReportCurve();
		}
	}
	$scope.resetCurve = function () {
		$scope.searchFilterCurve = {};
        $scope.type = 'chart';
        $scope.periodicReportCurve = $scope.periodicReportsCurve[0];
		$scope.gridOptionsCurve = ngGridService.initGrid($scope, columnDefsCurve, []);
		$scope.chartLabelsCurve = [];
    	$scope.chartDataCurve = [];
    };
	
	  let prepareReportCurve = function () {
    	let reportDataCurve = [];
    	let plannedRunningTotal = 0, actualRunningTotal = 0, earnedActualRunningTotal = 0, earnedForecastRunningTotal = 0, forecastToCompleteRunningTotal = 0;
    	let actualCummulative = 0, earnedToDateCummulative = 0;
    	for (let i=0; i < progressSCurveTOsCurve.length; i++) {
    		actualCummulative += progressSCurveTOsCurve[i].actual;
    		earnedToDateCummulative += progressSCurveTOsCurve[i].earnedTodate;
    	}
		for (let i=0; i < progressSCurveTOsCurve.length; i++) {
			let progressSCurveTOCurve = {};
			if ($scope.periodicReportCurve.filter == "week")
				progressSCurveTOCurve.period = weekStartEndDateFormatCurve(progressSCurveTOsCurve[i].date);
			else
			progressSCurveTOCurve.period = $filter('date')(progressSCurveTOsCurve[i].date, $scope.periodicReportCurve.filter);
			progressSCurveTOCurve.planned = progressSCurveTOsCurve[i].planned;
			progressSCurveTOCurve.actual = progressSCurveTOsCurve[i].actual;
			progressSCurveTOCurve.earnedActual = progressSCurveTOsCurve[i].earnedTodate;
			progressSCurveTOCurve.earnedForecast = progressSCurveTOsCurve[i].earnedForecast;
			progressSCurveTOCurve.forecastToComplete = progressSCurveTOsCurve[i].forecastToComplete;
			plannedRunningTotal += progressSCurveTOsCurve[i].planned;
			actualRunningTotal += progressSCurveTOsCurve[i].actual;
			earnedActualRunningTotal += progressSCurveTOsCurve[i].earnedTodate;
			earnedForecastRunningTotal += progressSCurveTOsCurve[i].earnedForecast;
			forecastToCompleteRunningTotal += progressSCurveTOsCurve[i].forecastToComplete;
			progressSCurveTOCurve.plannedCummulative = plannedRunningTotal;
			progressSCurveTOCurve.actualCummulative = actualRunningTotal;
			progressSCurveTOCurve.earnedActualCummulative = earnedActualRunningTotal;
			if ($scope.scheduleDate < progressSCurveTOsCurve[i].date) {
				progressSCurveTOCurve.earnedForecastCummulative = earnedToDateCummulative + earnedForecastRunningTotal;
				progressSCurveTOCurve.forecastToCompleteCummulative = actualCummulative + forecastToCompleteRunningTotal;
			} else {
				progressSCurveTOCurve.earnedForecastCummulative = 0;
				progressSCurveTOCurve.forecastToCompleteCummulative = 0;
			}
			
			addToReportDataCurve(reportDataCurve, progressSCurveTOCurve);
		}
		$scope.gridOptionsCurve.data = reportDataCurve;
		prepareChartCurve();
    };
   let addToReportDataCurve = function (reportDataCurve, progressSCurveTOCurve) {
    	let foundCurve = false;
    	for (let i=0; i < reportDataCurve.length; i++) {
    		if (reportDataCurve[i].period == progressSCurveTOCurve.period) {
    			reportDataCurve[i].planned += progressSCurveTOCurve.planned;
    			reportDataCurve[i].plannedCummulative = progressSCurveTOCurve.plannedCummulative;
    			reportDataCurve[i].earnedActual += progressSCurveTOCurve.earnedActual;
    			reportDataCurve[i].earnedActualCummulative = progressSCurveTOCurve.earnedActualCummulative;
    			reportDataCurve[i].actual += progressSCurveTOCurve.actual;
    			reportDataCurve[i].actualCummulative = progressSCurveTOCurve.actualCummulative;
    			reportDataCurve[i].earnedForecast += progressSCurveTOCurve.earnedForecast;
    			reportDataCurve[i].earnedForecastCummulative = progressSCurveTOCurve.earnedForecastCummulative;
    			reportDataCurve[i].forecastToComplete += progressSCurveTOCurve.forecastToComplete;
    			reportDataCurve[i].forecastToCompleteCummulative = progressSCurveTOCurve.forecastToCompleteCummulative;
    			foundCurve = true;
    		}
    	}
    	if (!foundCurve) reportDataCurve.push(progressSCurveTOCurve);
    }

 let weekStartEndDateFormatCurve = function (date) {
    	let startDate = new Date(angular.copy(date));
    	while ($filter('date')(startDate, "EEEE") != weekBeginsOnCurve)
    		startDate.setDate(startDate.getDate() - 1);

    	let endDate = new Date(angular.copy(date));
    	while ($filter('date')(endDate, "EEEE") != weekEndsOnCurve)
    		endDate.setDate(endDate.getDate() + 1);

    	return startDate.getDate() + "-" + endDate.getDate() + " " + $filter('date')(endDate, "MMM yyyy");
    }


   let prepareChartCurve = function() {
    	let isScheduleDayCurve = true;
    	let beforeScheduleDateCurve = true;
    	let formattedScheduleDateCurve = "";
    	if ($scope.periodicReportCurve.filter == "week") 
    		formattedScheduleDateCurve = weekStartEndDateFormatCurve($scope.scheduleDateCurve);
		else
			formattedScheduleDateCurve = $filter('date')($scope.scheduleDateCurve, $scope.periodicReportCurve.filter);
    	let labels = [], planned = [], earnedActual = [], actual = [], earnedForecast = [], forecastToComplete = [];
    	for (let i=0; i < $scope.gridOptionsCurve.data.length; i++) {
    		labels.push($scope.gridOptionsCurve.data[i].period);
    		planned.push(parseFloat($scope.gridOptionsCurve.data[i].plannedCummulative).toFixed(2));
    		if (beforeScheduleDateCurve && $scope.gridOptionsCurve.data[i].period == formattedScheduleDateCurve) beforeScheduleDateCurve = false;
    		if (beforeScheduleDateCurve) {
    			earnedActual.push(parseFloat($scope.gridOptionsCurve.data[i].earnedActualCummulative).toFixed(2));
        		actual.push(parseFloat($scope.gridOptionsCurve.data[i].actualCummulative).toFixed(2));
        		earnedForecast.push(null);
        		forecastToComplete.push(null);
    		} else {
    			if (isScheduleDayCurve) {
    				earnedActual.push(parseFloat($scope.gridOptionsCurve.data[i].earnedActualCummulative).toFixed(2));
            		actual.push(parseFloat($scope.gridOptionsCurve.data[i].actualCummulative).toFixed(2));
            		earnedForecast.push(parseFloat($scope.gridOptionsCurve.data[i].earnedActualCummulative).toFixed(2));
            		forecastToComplete.push(parseFloat($scope.gridOptionsCurve.data[i].actualCummulative).toFixed(2));
            		isScheduleDayCurve = false;
    			} else {
    				earnedActual.push(null);
    				actual.push(null);
    				earnedForecast.push(parseFloat($scope.gridOptionsCurve.data[i].earnedForecastCummulative).toFixed(2));
            		forecastToComplete.push(parseFloat($scope.gridOptionsCurve.data[i].forecastToCompleteCummulative).toFixed(2));
    			}
    		}
    	}
    	$scope.chartLabelsCurve = labels;
    	$scope.chartDataCurve = [planned, earnedActual, actual, earnedForecast, forecastToComplete];
    }



/*Remaining Budget by Country*/

	$scope.showTableCoun=null;
	$scope.getUserProjectsCoun = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProjectCoun = {};
			$scope.searchProjectCoun = data.searchProject;
			$scope.selectedProjIdsCoun = data.searchProject.projIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}
	
	var performanceList=[];
	var plannedVal=[];
	$scope.projCostStmtDtlsCoun = [];
	$scope.projCostValuesCoun = [];
	$scope.plannedValuesMapCoun = [];
	$scope.projGeneralValuesCoun = [];

	$scope.remainingBudgetByCountryTabsCoun = new Array();
	
	let task1CompletedCoun = false, task2CompletedCoun = false, taskPlannedValueCoun = false, taskFullCoun = false;
	$scope.getRemainingBudgetByCountryDetailsCoun = function () {
		task1CompletedCoun = false;
		task2CompletedCoun = false;
		taskPlannedValueCoun = false;
		taskFullCoun = false;
		var req = {
			"status": 1,
			"projIds": $scope.selectedProjIdsCoun
		};
		if (req.projIds == null) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		PerformanceDashboardService.getProjCostStatements(req).then(function (data) {
			//console.log(data.projCostStmtDtlTOs);
			let costDataCoun = populateCostDataCoun(data.projCostStmtDtlTOs, 0, []);
			costDataCoun.map((treeItem) => {
				$scope.costItemClickCoun(treeItem, false);
			});
			let projCostStmtDtlsCoun = costDataCoun;
			if (projCostStmtDtlsCoun.length > 0 && projCostStmtDtlsCoun.find(x => x.item == true).estimateType &&
				projCostStmtDtlsCoun.find(x => x.item == true).estimateType.contains('SPI')) {
				ProjectScheduleService.getProjBudgetCostCodeDetails(req).then(function (data1) {
					$scope.projCostStmtDtlsCoun = projCostStmtDtlsCoun;
					calculatePlannedValuesCoun(data1, $scope.projCostStmtDtlsCoun, "costId");
					EstimateToCompleteService.costStatement($scope.projCostStmtDtlsCoun);
					calculateCostTotalCoun($scope.projCostStmtDtlsCoun);
					task1CompletedCoun = true;
					mergValuesCoun($scope.projCostValuesCoun, $scope.projProgressStatusCoun);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while gettting Progress, Variance & Performance Indices details", 'Error');
				});
			} else {
				$scope.projCostStmtDtlsCoun = projCostStmtDtlsCoun;
				//console.log("$scope.projCostStmtDtlsCoun", $scope.projCostStmtDtlsCoun);
				EstimateToCompleteService.costStatement($scope.projCostStmtDtlsCoun);
				calculateCostTotalCoun($scope.projCostStmtDtlsCoun);
				task1CompletedCoun = true;
				mergValuesCoun($scope.projCostValuesCoun, $scope.projProgressStatusCoun);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting Cost details", 'Error');
		});
		ProjectStatusService.getProjStatusDates(req).then(function (data) {
			$scope.projProgressStatusCoun = data.projStatusDatesTOs;
			task2CompletedCoun = true;
			mergValuesCoun($scope.projCostValuesCoun, $scope.projProgressStatusCoun);
			//console.log($scope.projProgressStatusCoun);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Progress Status", "Error");
		});
		ProjectSettingsService.getProjPerformenceThreshold(req).then(function (data) {
			$scope.performenceThresholdDataCoun = data.projPerformenceThresholdTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
		});
		ProjectSettingsService.getProjPlannedValue(req).then(function (data) {
			$scope.plannedValuesMapCoun = data.projPlannedValueTO;
			console.log("$scope.plannedValuesMapCoun",$scope.plannedValuesMapCoun);
			taskPlannedValueCoun = true;
			mergValuesCoun($scope.projCostValuesCoun, $scope.projProgressStatusCoun);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Planned Value", "Error");
		});
		ProjectStatusService.getMultiProjGenerals(req).then(function (data) {
			$scope.projGeneralValuesCoun = data.projGeneralMstrTOs;
			console.log("$scope.projGeneralValuesCoun",$scope.projGeneralValuesCoun);
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
        });
	}
	
	
	function mergValuesCoun(projCostValuesCoun, projProgressStatusCoun) {
		//console.log(projProgressStatusCoun);
		var thresholdDataCoun = $scope.performenceThresholdDataCoun;
		//console.log("task1CompletedCoun", task1CompletedCoun)
		//console.log("task2CompletedCoun", task2CompletedCoun)
		//console.log("taskPlannedValueCoun", taskPlannedValueCoun)
		if (task1CompletedCoun && task2CompletedCoun && taskPlannedValueCoun) {
			$scope.progressVarienceDataCoun = new Array();
			for (const index in projCostValuesCoun) {
				projCostValuesCoun[index].epsName=$scope.searchProjectCoun.parentName.split(',')[index];
				projCostValuesCoun[index].projName=$scope.searchProjectCoun.projName.split(',')[index];
				$scope.progressVarienceDataCoun.push(projCostValuesCoun[index]);
			}
			//console.log("progressVarienceDataCoun", progressVarienceDataCoun);
			var progVarienceDataCoun = $scope.progressVarienceDataCoun;
			console.log("progVarienceDataCoun", progVarienceDataCoun);
			$scope.progVarienceDataInfoCoun = new Array();
			for (const index2 in progVarienceDataCoun) {
				$scope.progVarienceDataInfoCoun.push(progVarienceDataCoun[index2]);
			}
			taskFullCoun = true;			
			mergePlannedValueCoun($scope.progVarienceDataInfoCoun, $scope.plannedValuesMapCoun);
		}
	}
	
	function mergePlannedValueCoun(progVarienceDataInfoCoun, plannedValuesMapCoun) { 
		
		if (taskPlannedValueCoun && taskFullCoun) {
			//console.log("MERGING PLANNED VALUE");
			$scope.costSchedPerformanceDataCoun = new Array();
			for (const index_1 in progVarienceDataInfoCoun) {
				for (const index_2 in plannedValuesMapCoun) {
					if(plannedValuesMapCoun[index_2].projId == progVarienceDataInfoCoun[index_1].projId) {
						progVarienceDataInfoCoun[index_1].plannedCost = plannedValuesMapCoun[index_2].cost;
					}
				}

				$scope.costSchedPerformanceDataCoun.push(progVarienceDataInfoCoun[index_1]);
			}
			console.log("FINAL", $scope.costSchedPerformanceDataCoun);
			var estAtCompletionDetails = $scope.costSchedPerformanceDataCoun;
			var projGeneral = $scope.projGeneralValuesCoun;
			$scope.estimateAtCountryDetailsCoun = new Array();
			for (const index_3 in estAtCompletionDetails){
				for (const index_4 in projGeneral) {
					if(projGeneral[index_4].projId == estAtCompletionDetails[index_3].projId) {
						estAtCompletionDetails[index_3].countryName = projGeneral[index_4].countryName
					}
				}
				$scope.estimateAtCountryDetailsCoun.push(estAtCompletionDetails[index_3]);
				
			}
			mergeValCoun($scope.estimateAtCountryDetailsCoun);
		}
	}
	
	function mergeValCoun(estAtCompByCountry) {
		console.log("estAtCompByCountry", estAtCompByCountry);
		let newDataCoun = [];
		for (let i=0; i < estAtCompByCountry.length; i++) {
			let index = newDataCoun.findIndex(e => e.countryName == estAtCompByCountry[i].countryName);
			if (index == -1) {
				newDataCoun.push({"countryName": estAtCompByCountry[i].countryName, "remainingBudget" : estAtCompByCountry[i].remainingBudgetTotal});
			} else {
				newDataCoun[index].remainingBudget += estAtCompByCountry[i].remainingBudgetTotal;
			}
		}
		//console.log(newDataCoun)
		setGraphDataCoun(newDataCoun);
	}
	
	$scope.costItemClickCoun = function (item, expand) {
		TreeService.dynamicTreeItemClick($scope.projCostStmtDtlsCoun, item, expand);
	}
	
	function populateCostDataCoun(data, level, costTOs, isChild, parent) {
		return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',isChild, parent)
	}
	
	function calculateCostTotalCoun(projCostStmtDtlsCoun) {
		let projCostValuesCoun = [];
		for (const costValue of projCostStmtDtlsCoun) {
			let costFoundCoun = false;
			for (let i=0; i < projCostValuesCoun.length; i++) {
				//projCostValuesCoun[i].estCompletion = 0;
				var actualCostTotal1Coun = (costValue.actualCostBudget.labourCost
						+costValue.actualCostBudget.materialCost+costValue.actualCostBudget.plantCost+costValue.actualCostBudget.otherCost);
				if (projCostValuesCoun[i].projId == costValue.projId) {
					costFoundCoun = true;
					
					projCostValuesCoun[i].projId = costValue.projId;
					projCostValuesCoun[i].epsName = $scope.searchProject.parentName;
					
					projCostValuesCoun[i].originalBudgetTotal += costValue.originalCostBudget.total;
					projCostValuesCoun[i].revisedBudgetTotal += costValue.revisedCostBudget.total;
					projCostValuesCoun[i].actualCostTotal += actualCostTotal1Coun;
					projCostValuesCoun[i].earnedValueTotal += costValue.earnedValue;
					if(costValue.revisedQty > 0) {
						projCostValuesCoun[i].remainingBudgetTotal += costValue.revisedCostBudget.total - actualCostTotal1Coun;
					} else {
						projCostValuesCoun[i].remainingBudgetTotal += costValue.originalCostBudget.total - actualCostTotal1Coun;
					}
					if(costValue.estimateType == "Remaining Units") {
						if(costValue.revisedCostBudget.total > 0) {
							projCostValuesCoun[i].estCompletion += costValue.revisedCostBudget.total - actualCostTotal1Coun;
							projCostValuesCoun[i].etcTotal += costValue.revisedCostBudget.total - actualCostTotal1Coun;
						} else {
							projCostValuesCoun[i].estCompletion += costValue.originalCostBudget.total - actualCostTotal1Coun;
							projCostValuesCoun[i].etcTotal += costValue.originalCostBudget.total - actualCostTotal1Coun;
						}
					} else if(costValue.estimateType == "BAC-EV") {
						if(costValue.revisedCostBudget.total > 0) {
							projCostValuesCoun[i].estCompletion += (costValue.revisedCostBudget.total - costValue.earnedValue);
							projCostValuesCoun[i].etcTotal += (costValue.revisedCostBudget.total - costValue.earnedValue);
						} else {
							projCostValuesCoun[i].estCompletion += (costValue.originalCostBudget.total - costValue.earnedValue);
							projCostValuesCoun[i].etcTotal += (costValue.originalCostBudget.total - costValue.earnedValue);
						}
					} else if(costValue.estimateType == "(BAC-EV)/CPI") {
						var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
						var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0) ? 0 : (costValue.earnedValue / actualCostTotal1Coun);
						var etcBAC_EV_CPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0)  ? bac_ev :
		            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/cpi;
						projCostValuesCoun[i].estCompletion += etcBAC_EV_CPI;
						projCostValuesCoun[i].etcTotal += etcBAC_EV_CPI;
					} else if(costValue.estimateType == "(BAC-EV)/(CPI*SPI)") {
						var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
						var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0) ? 0 : (costValue.earnedValue / actualCostTotal1Coun);
						var spi=(costValue.earnedValue==0 || costValue.earnedValue==null || costValue.plannedValue==0) ? 0 : (costValue.earnedValue / costValue.plannedValue);
						var etcBAC_EV_CPI_SPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0 || costValue.plannedValue==0)  ? bac_ev :
		            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/(cpi*spi);
						projCostValuesCoun[i].estCompletion += etcBAC_EV_CPI_SPI;
						projCostValuesCoun[i].etcTotal += etcBAC_EV_CPI_SPI;
					} else if(costValue.estimateType == "New Estimate") {
						projCostValuesCoun[i].estCompletion += costValue.estimateCompleteBudget.total;
						projCostValuesCoun[i].etcTotal += costValue.estimateCompleteBudget.total;
					}
					projCostValuesCoun[i].estAtCompletionTotal = projCostValuesCoun[i].actualCostTotal + projCostValuesCoun[i].etcTotal;
					projCostValuesCoun[i].toCompleteCostPerformanceIndex = (projCostValuesCoun[i].estAtCompletionTotal - projCostValuesCoun[i].earnedValueTotal) / (projCostValuesCoun[i].estAtCompletionTotal - projCostValuesCoun[i].actualCostTotal);
					if (projCostValuesCoun[i].revisedBudgetTotal > 0 ) {
						projCostValuesCoun[i].progressOfWorkPercentage = (projCostValuesCoun[i].earnedValueTotal / projCostValuesCoun[i].revisedBudgetTotal)*100;
					} else {
						projCostValuesCoun[i].progressOfWorkPercentage = (projCostValuesCoun[i].earnedValueTotal / projCostValuesCoun[i].originalBudgetTotal)*100;
					}
					projCostValuesCoun[i].costVarience = (projCostValuesCoun[i].earnedValueTotal - projCostValuesCoun[i].actualCostTotal);
					projCostValuesCoun[i].costPerformanceIndex = (projCostValuesCoun[i].earnedValueTotal / projCostValuesCoun[i].actualCostTotal);
					projCostValuesCoun[i].costVariencePercentage = (projCostValuesCoun[i].costVarience / projCostValuesCoun[i].earnedValueTotal) * 100;
					
				}
			}
			if (!costFoundCoun) {
				let etctotal1 = 0;
				let estCompletion1 = 0;
				let remainingCostTotal = 0;
				
				var actualCostTotal1Coun = (costValue.actualCostBudget.labourCost
						+costValue.actualCostBudget.materialCost+costValue.actualCostBudget.plantCost+costValue.actualCostBudget.otherCost);
				if(costValue.revisedQty > 0) {
					remainingCostTotal += costValue.revisedCostBudget.total - actualCostTotal1Coun;
				} else {
					remainingCostTotal += costValue.originalCostBudget.total - actualCostTotal1Coun;
				}
				if(costValue.estimateType == "Remaining Units") {
					if(costValue.revisedCostBudget.total > 0) {
						estCompletion1 += costValue.revisedCostBudget.total - actualCostTotal1Coun;
						etctotal1 += costValue.revisedCostBudget.total - actualCostTotal1Coun;
					} else {
						estCompletion1 += costValue.originalCostBudget.total - actualCostTotal1Coun;
						etctotal1 += costValue.originalCostBudget.total - actualCostTotal1Coun;
					}
				} else if(costValue.estimateType == "BAC-EV") {
					if(costValue.revisedCostBudget.total > 0) {
						estCompletion1 += (costValue.revisedCostBudget.total - costValue.earnedValue);
						etctotal1 += (costValue.revisedCostBudget.total - costValue.earnedValue);
					} else {
						estCompletion1 += (costValue.originalCostBudget.total - costValue.earnedValue);
						etctotal1 += (costValue.originalCostBudget.total - costValue.earnedValue);
					}
				} else if(costValue.estimateType == "(BAC-EV)/CPI") {
					var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
					var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0) ? 0 : (costValue.earnedValue / actualCostTotal1Coun);
					var etcBAC_EV_CPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0)  ? bac_ev :
	            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/cpi;
					estCompletion1 += etcBAC_EV_CPI;
					etctotal1 += etcBAC_EV_CPI;
				} else if(costValue.estimateType == "(BAC-EV)/(CPI*SPI)") {
					var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
					var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0) ? 0 : (costValue.earnedValue / actualCostTotal1Coun);
					var spi=(costValue.earnedValue==0 || costValue.earnedValue==null || costValue.plannedValue==0) ? 0 : (costValue.earnedValue / costValue.plannedValue);
					var etcBAC_EV_CPI_SPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1Coun==0 || costValue.plannedValue==0)  ? bac_ev :
	            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/(cpi*spi);
					estCompletion1 += etcBAC_EV_CPI_SPI;
					etctotal1 += etcBAC_EV_CPI_SPI;
				} else if(costValue.estimateType == "New Estimate") {
					estCompletion1 += costValue.estimateCompleteBudget.total;
					etctotal1 += costValue.estimateCompleteBudget.total;
				}
				projCostValuesCoun.push({
					"projId": costValue.projId,
					"epsName": $scope.searchProject.parentName, 
					"projName": $scope.searchProject.projName, 
					originalBudgetTotal: costValue.originalCostBudget.total,
					revisedBudgetTotal: costValue.revisedCostBudget.total,
					earnedValueTotal: costValue.earnedValue,
					actualCostTotal: actualCostTotal1Coun,
					plannedValueTotal:0,
					estCompletion: estCompletion1,
					remainingBudgetTotal:remainingCostTotal,
					etcTotal:etctotal1,
					estAtCompletionTotal:0,
					toCompleteCostPerformanceIndex:0,
					progressOfWorkPercentage:0,
					costVarience:0,
					costPerformanceIndex:0,
					costVariencePercentage:0,
					scheduleVarience:0,
					scheduleVariencePercentage:0
				});
			}
		}
		$scope.projCostValuesCoun = projCostValuesCoun;
		console.log("$scope.projCostValuesCoun",$scope.projCostValuesCoun);
	}
	
	function calculatePlannedValuesCoun(budgetValue, dailyResources, key) {
		const currentDate = new Date();
		for (const dailyResource of dailyResources) {
			const value = dailyResource[key];
			if (!value)
				continue;
			let budgetKey = key;
			let projTOs = budgetValue.projManpowerTOs;
			if (key == 'costId') {
				budgetKey = 'costCodeId';
				projTOs = budgetValue.projScheduleCostCodeTOs;
			}
			dailyResource.plannedValue = 0;
			const selectedRow = projTOs.find(x => x[budgetKey] == value);
			if (!selectedRow)
				continue;
			const startdate = selectedRow.startDate;
			if (!startdate)
				continue;
			let regularNonWorkingDays = [];
			if (budgetValue.regularHolidays) {
				regularNonWorkingDays.push((budgetValue.regularHolidays['sunday']) ? 0 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['monday']) ? 1 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['tuesday']) ? 2 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['wednesday']) ? 3 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['thursday']) ? 4 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['friday']) ? 5 : "");
				regularNonWorkingDays.push((budgetValue.regularHolidays['saturday']) ? 6 : "");
			}
			let req = {
				"actualAndPlanned": false,
				"forReport": true,
				"reportPeriod": [startdate],
				"selectedItemRow": selectedRow,
				"selectedTimeScale": {
					value: 7,
					type: "Weekly"
				},
				"calNonWorkingDays": budgetValue.calNonWorkingDays,
				"regularNonWorkingDays": regularNonWorkingDays,
				"searchProject": {
					"projId": $rootScope.projId
				},
				"resourceCurveTypeMap": $scope.resourceCurveTypeMap,
				"reportProjectSetting": budgetValue.projReportsTo,
				"calSplWorkingDays": budgetValue.calSplWorkingDays
			};
			let resp = SchedulePlannedValueService.createPlannedCurve(req);
			if (resp && resp.labels) {
				for (let index = 0; index < resp.labels.length; index++) {
					if (new Date(resp.labels[index]) <= currentDate) {
						let plannedValue = resp.data[0][index];
						if (plannedValue) {
							dailyResource.plannedValue += parseFloat(plannedValue);
						}
					}
				}
			} else {
				dailyResource.plannedValue = 0;
			}
		}
	}
	
	/*
	$scope.getRemainingBudgetByCountryDetails = function () {
		RemainingBudgetDashboardGroupingService.getRemainingBudgetDashboards($scope.selectedProjIds, "country").then(data => {
			setGraphData(data);
		});

	};
	*/
	function setGraphDataCoun(budgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in budgetDetails) {
			labels.push(budgetDetails[key].countryName);
			budgetDetails[key].remainingBudget = parseFloat(budgetDetails[key].remainingBudget).toFixed(2);
			data.push(budgetDetails[key].remainingBudget);
		}
		$scope.remainingBudgetByCountryTabsCoun.unshift({ 'data': data, 'labels': labels });
		initGraphCoun("pie");
	}

	function initGraphCoun(type) {
		$scope.chart_typeCoun = type;
		$scope.optionsCoun = {
			title: {
				display: true,
				text: 'Remaining Budget'

			},
			legend: {
				display: true,
				position: 'bottom'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					/*ticks: {
						beginAtZero: true
					} */
				}]
			}
		};

		$scope.optionsCoun.scales.xAxes[0].reverse = false;
		$scope.optionsCoun.scales.yAxes[0].reverse = false;
		if ($scope.chart_typeCoun === 'pie' || $scope.chart_typeCoun === 'polarArea' || $scope.chart_typeCoun === 'doughnut') {
			$scope.optionsCoun.scales.xAxes[0].display = false;
			$scope.optionsCoun.scales.yAxes[0].display = false;
		} else {
			if ($scope.chart_type === 'radar') {
				$scope.optionsCoun.scales.xAxes[0].display = false;
				$scope.optionsCoun.scales.yAxes[0].display = false;
			}
		}

	};

	$scope.menuOptionsCoun = [['Chart Type', [['Area', function ($itemScope) {
		$scope.chart_typeCoun = 'Area';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['Bar', function ($itemScope) {
		$scope.chart_typeCoun = 'bar';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['HorizantalBar', function ($itemScope) {
		$scope.chart_typeCoun = 'horizontalBar';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['PolarArea', function ($itemScope) {
		$scope.chart_typeCoun = 'polarArea';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['Doughnut', function ($itemScope) {
		$scope.chart_typeCoun = 'doughnut';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['Line', function ($itemScope) {
		$scope.chart_typeCoun = 'line';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['Pie', function ($itemScope) {
		$scope.chart_typeCoun = 'pie';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}], ['Radar', function ($itemScope) {
		$scope.chart_typeCoun = 'radar';
		$scope.initGraphCoun($scope.chart_typeCoun);
	}]]], null, ['Fill', [['Filled', function ($itemScope) {
	}], ['Unfilled', function ($itemScope) {
	}], ['Auto', function ($itemScope) {
	}]]], null, ['Stack', [['Stacked', function ($itemScope) {
		$scope.optionsCoun.scales.xAxes[0].stacked = true;
		$scope.optionsCoun.scales.yAxes[0].stacked = true;
	}], ['Unstacked', function ($itemScope) {
		$scope.optionsCoun.scales.xAxes[0].stacked = false;
		$scope.optionsCoun.scales.yAxes[0].stacked = false;
	}], ['Auto', function ($itemScope) {
	}]]], ['Swap Facets', function ($itemScope) {
	}], null, ['Proportional', function ($itemScope) {
	}]];


}])
