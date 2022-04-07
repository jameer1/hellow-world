'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("progressscurvelabour", {
		url: '/progressscurvelabour',
		data: {
			dashboards: []
		},
		views: {
			'content@': {

				'templateUrl': 'views/dashboards/progress/progress.scurvelabour.html',
				controller: 'ProgressSCurveLabourController'
			}
		}
	})
}]).controller("ProgressSCurveLabourController", ["$scope", "$q", "$filter", "ngGridService", "EpsProjectSelectFactory", "progressReportService", "GenericAlertService", "ProjectSettingsService",
	function ($scope, $q, $filter, ngGridService, EpsProjectSelectFactory, progressReportService, GenericAlertService, ProjectSettingsService) {
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
    let weekDays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let weekBeginsOn = 'Monday';
    let weekEndsOn = 'Sunday';
	
    $scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Dashboards_progress_SCurve-Labour");
    $scope.getUserProjects = function () {
		EpsProjectSelectFactory.getEPSUserProjects().then(function (data) {
			$scope.reset();
			$scope.searchFilter = data.searchProject;
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
				$scope.searchFilter.fromDate = $filter('date')($scope.searchFilter.projectFromDate, "dd-MMM-yyyy");
				$scope.searchFilter.toDate = $filter('date')($scope.searchFilter.projectToDate, "dd-MMM-yyyy");
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.generateReport = function() {
		if ($scope.searchFilter == null) {
			GenericAlertService.alertMessage("Please select project to fetch report", 'Info');
			return;
		}
		progressReportService.getProgressSCurveManpowerReportData({projIds: [$scope.searchFilter.projId], fromDate: $scope.searchFilter.fromDate, toDate: $scope.searchFilter.toDate}).then(function (data){
			progressSCurveTOs = data.progressSCurveTOs;
			$scope.scheduleDate = data.scheduleDate;
			prepareReport();
			if (progressSCurveTOs.length == 0)
				GenericAlertService.alertMessage("Details are not available for the search criteria", 'Info');
		}, function(error){
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		})
	},
	$scope.periodicReportChanged = function() {
		let newColumnDefs = angular.copy(columnDefs);
		newColumnDefs[0].displayName = "Period - " + $scope.periodicReport.name;
		$scope.gridOptions = ngGridService.initGrid($scope, newColumnDefs, []);
		if ($scope.periodicReport.code == 'weekly') {
			ProjectSettingsService.projReportsOnLoad({projId: $scope.searchFilter.projId, status: 1}).then(function (data) {
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
	},
    $scope.reset = function () {
		$scope.searchFilter = {};
        $scope.type = 'chart';
        $scope.periodicReport = $scope.periodicReports[0];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
		$scope.chartLabels = [];
    	$scope.chartData = [];
    };
    
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
}])
