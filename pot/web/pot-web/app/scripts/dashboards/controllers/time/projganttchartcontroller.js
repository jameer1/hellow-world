'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("projectsganttchart", {
		url : '/projectsganttchart',
		data : {
			dashboards : []
		},
		views : {
			'content@' : {
				'templateUrl' : 'views/dashboards/time/projganttchart.html',
				controller : 'ProjGanttChartController'
			}
		}
	})
}]).controller("ProjGanttChartController", ["$scope", "$q", "$filter", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "ProjectSettingsService",
	function($scope, $q, $filter, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory, ProjectSettingsService) {
		$scope.options = {
			showGanttChart: false,
			currentDateValue: new Date(),
			currentDate: 'line',
			timeScales: [
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
		$scope.options.selectedTimeScale = $scope.options.timeScales[1];
		$scope.searchFilter = {};
		
		$scope.getUserProjects = function () {
			EpsProjectMultiSelectFactory.getEPSUserProjects().then(function (data) {
				$scope.searchFilter = {};
				$scope.searchFilter.searchProject = data.searchProject;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.generateReport = function () {
			if ($scope.searchFilter.searchProject == null || $scope.searchFilter.searchProject.projIds == 0) {
				GenericAlertService.alertMessage("Please select the Project", 'Info');
				return;
			}
			ProjectSettingsService.getProjectsGanttChartReportData({projIds: $scope.searchFilter.searchProject.projIds}).then(function (data) {
				prepareGanttChartData(data.projectGanttChartTOs);
				$scope.options.showGanttChart = $scope.data.length;
				if ($scope.data.length == 0) GenericAlertService.alertMessage("Resource Assignment Data Table does not exists for the selected projects", 'Info');
				if ($scope.data.length > 0 && $scope.data.length != $scope.searchFilter.searchProject.projIds.length) GenericAlertService.alertMessage("Resource Assignment Data Table does not exists for one or more selected projects", 'Info');
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
			});
		};
		
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
				$scope.options.timeScales[0].fromDate = minDay;
				$scope.options.timeScales[0].toDate = maxDay;
				//week
				let minWeek = angular.copy(minDate);
				let maxWeek = angular.copy(maxDate);
				minWeek.setDate(minWeek.getDate() - 3);
				maxWeek.setDate(maxWeek.getDate() + 3);
				$scope.options.timeScales[1].fromDate = minWeek;
				$scope.options.timeScales[1].toDate = maxWeek;
				//month
				let minMonth = angular.copy(minDate);
				let maxMonth = angular.copy(maxDate);
				minMonth.setDate(minMonth.getDate() - 7);
				maxMonth.setDate(maxMonth.getDate() + 7);
				$scope.options.timeScales[2].fromDate = minMonth;
				$scope.options.timeScales[2].toDate = maxMonth;
				//quarter
				let minQuarter = angular.copy(minDate);
				let maxQuarter = angular.copy(maxDate);
				minQuarter.setDate(minQuarter.getDate() - 10);
				maxQuarter.setDate(maxQuarter.getDate() + 10);
				$scope.options.timeScales[3].fromDate = minQuarter;
				$scope.options.timeScales[3].toDate = maxQuarter;
				//year
				let minYear = angular.copy(minDate);
				let maxYear = angular.copy(maxDate);
				minYear.setDate(minYear.getDate() - 15);
				maxYear.setDate(maxYear.getDate() + 15);
				$scope.options.timeScales[4].fromDate = minYear;
				$scope.options.timeScales[4].toDate = maxYear;
			}
		};
}]);
