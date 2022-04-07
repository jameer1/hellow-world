'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("actschedules", {
		url: '/actschedules',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectschedules/actscheduleprojectview.html',
				controller: 'ActProjectScheduleController'
			}
		}
	})
}]).controller('ActProjectScheduleController', ["$scope", "$state", "$q", "$timeout", "$filter", "EpsProjectSelectFactory", "GenericAlertService", "ngDialog", "ngGridService", "ProjectScheduleService", "uiGridGroupingConstants", "DatasetListFactory",
	function ($scope, $state, $q, $timeout, $filter, EpsProjectSelectFactory, GenericAlertService, ngDialog, ngGridService, ProjectScheduleService, uiGridGroupingConstants, DatasetListFactory) {
	$scope.colors = ["#7db9e8", "#41B3A3", "#659DBD", "#BC986A", "#DAAD86", "#C38D9E", "#E8A87C", "#E27D60", "#8EE4AF", "#FFD64A"];
	$scope.options = {
			showGanttChart: false,
			currentDateValue: $filter('date')((new Date()), "dd-MMM-yyyy"),
			currentDate: 'line',
			showDependencies: false,
			timeScales: [
				{header: ['month', 'day'], headerFormat: {month: 'MMMM YYYY', day: "D"}, type: "Day", selectedSize: 25, minSize: 15, maxSize: 40}, 
				{header: ['month', 'week'], headerFormat: {month: 'MMMM YYYY', week: function(column) {return column.date.format('D [-] ') + column.endDate.format('D'); }}, type: "Week", selectedSize: 20, minSize: 8, maxSize: 35}, 
				{header: ['month'], headerFormat: {month: 'MMMM YYYY'}, type: "Month", selectedSize: 15, minSize: 5, maxSize: 35},
				{header: ['year', 'quarter'], headerFormat: {'year': 'YYYY'}, type: "Quarter", selectedSize: 10, minSize: 3, maxSize: 25},
				{header: ['year'], headerFormat: {'year': 'YYYY', 'quarter': '[Q]Q YYYY'}, type: "Year", selectedSize: 10, minSize: 3, maxSize: 25},
			],
			columns: ['model.data.wbsCode', 'model.data.critical', 'model.data.activityCode', 'model.data.activityName', 'model.data.soeCode', 'model.data.originalDuration', 'from', 'to', 'model.data.minStartDateOfBaseline', 'model.data.maxFinishDateOfBaseline', 'model.data.predecessors', 'model.data.successors', 'model.data.physicalComplete', 'model.data.calendar', 'model.data.leadLag'],
			columnsHeaders: {'model.data.wbsCode':'WBS ID', 'model.data.critical':'Critical','model.data.activityCode':'Activity ID','model.data.activityName':'Activity Name','model.data.soeCode':'SOE ID','model.data.originalDuration':'Original Duration','from':'Start','to':'Finish','model.data.minStartDateOfBaseline':'Baseline Start', 'model.data.maxFinishDateOfBaseline':'Baseline Finish','model.data.predecessors':'Predecessors','model.data.successors':'Successors','model.data.physicalComplete':'Physical % Complete','model.data.calendar':'Calendar','model.data.leadLag':'Lead / Lag'},
			columnsClasses: {'model.data.critical': 'gantt-column-critical', 'model.data.activityCode':'gantt-column-activity-code', 'model.data.originalDuration':'gantt-column-original-duration','model.data.minStartDateOfBaseline':'gantt-column-baseline-start','model.data.maxFinishDateOfBaseline':'gantt-column-baseline-finish','model.data.predecessors':'gantt-column-predecessors','model.data.successors':'gantt-column-predecessors','model.data.physicalComplete':'gantt-column-physical-complete','model.data.leadLag':'gantt-column-lead-lag'},
			columnsFormatters: {
				'model.data.critical': function(value) {
					return value == null ? '' : value == 0 ? 'No' : 'Yes';
				},
				'from': function (from) {
					return from !== undefined ? from.format('ll') : undefined;
				},
			    'to': function (to) {
			        return to !== undefined ? to.format('ll') : undefined;
			    },
			    'model.data.minStartDateOfBaseline': function(minStartDateOfBaseline) {
			    	return minStartDateOfBaseline != null ? moment(minStartDateOfBaseline).format('ll') : undefined;
			    },
			    'model.data.maxFinishDateOfBaseline': function(maxFinishDateOfBaseline) {
			    	return maxFinishDateOfBaseline != null ? moment(maxFinishDateOfBaseline).format('ll') : undefined;
			    }
			}
	};
	$scope.options.selectedTimeScale = $scope.options.timeScales[1];
	$scope.selectedScheduleActivityDataSetTOs = [];
	$scope.searchCriteria = {};
	$scope.getUserProjects = function () {
		EpsProjectSelectFactory.getEPSUserProjects().then(function (data) {
			$scope.searchCriteria.searchProject = data.searchProject;
			ProjectScheduleService.getDatasetList({"projId": $scope.searchCriteria.searchProject.projId, "type": "A"}).then(function(data){
				$scope.selectedScheduleActivityDataSetTOs = [];
        		for (let i=0; i < data.scheduleActivityDataSetTOs.length; i++) {
        			if (data.scheduleActivityDataSetTOs[i].current) {
        				data.scheduleActivityDataSetTOs[i].readOnly = true;
        				$scope.selectedScheduleActivityDataSetTOs.push(data.scheduleActivityDataSetTOs[i]);
        				break;
        			}
        		}
        	}, function (error) {
        		cosole.log(error)
				GenericAlertService.alertMessage("Error occured while getting dataset", 'Error');
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.onReset = function() {
		$scope.searchCriteria = {};
		$scope.selectedScheduleActivityDataSetTOs = $scope.data = [];
		$scope.options.showGanttChart = false;
	},
	$scope.selectMultipleScheduleActivityDataSetTOs = function() {
		if ($scope.searchCriteria.searchProject == null || $scope.searchCriteria.searchProject == undefined) {
			GenericAlertService.alertMessage("Please select the project", "Info");
			return;
		}
		DatasetListFactory.selectMultiple($scope.searchCriteria.searchProject, "A", $scope.selectedScheduleActivityDataSetTOs).then(function(data){
			$scope.selectedScheduleActivityDataSetTOs = data;
			prepareSelectedScheduleActivityDataSetTOsDataForChart();
		}, function (error) {
    		cosole.log(error)
			GenericAlertService.alertMessage("Error occured while selecting datasets", 'Error');
		});
	},
	$scope.getFullDatasetname = function(scheduleActivityDataSetTO) {
		if (scheduleActivityDataSetTO.current)
			return scheduleActivityDataSetTO.datasetName + " (Current)";
		if (scheduleActivityDataSetTO.baseline)
			return scheduleActivityDataSetTO.datasetName + " (Baseline)";
		else
			return scheduleActivityDataSetTO.datasetName;
	},
	$scope.onSearch = function() {
		if (!$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId) {
			GenericAlertService.alertMessage("Please select Project", 'Info');
			return;
		}
		prepareSelectedScheduleActivityDataSetTOsDataForChart();
	};
	let prepareSelectedScheduleActivityDataSetTOsDataForChart = function() {
		ProjectScheduleService.getActualActivityScheduleForGanttChart({"scheduleActivityDataSetTOs": $scope.selectedScheduleActivityDataSetTOs}).then(function(data) {
			for (let i=0; i < data.ganttChartRow.length; i++) {
				if (data.ganttChartRow[i].tasks.length == 0) {
					delete data.ganttChartRow[i].tasks;
				} else if (data.ganttChartRow[i].tasks.length > 0) {
					for (let j=0; j < data.ganttChartRow[i].tasks[0].dependencies.length; j++) {
						if (data.ganttChartRow[i].tasks[0].dependencies[j].from == null)
							delete data.ganttChartRow[i].tasks[0].dependencies[j].from;
						if (data.ganttChartRow[i].tasks[0].dependencies[j].to == null)
							delete data.ganttChartRow[i].tasks[0].dependencies[j].to;
					}
					for (let j=0; j < data.ganttChartRow[i].tasks.length; j++) {
						if (data.ganttChartRow[i].tasks[j].progress.percent == 0)
							delete data.ganttChartRow[i].tasks[j].progress;
						else
							delete data.ganttChartRow[i].tasks[j].progress.classes;
					}
				}
				if (data.ganttChartRow[i].children.length == 0) {
					delete data.ganttChartRow[i].children;
				}
			}
			$scope.data = data.ganttChartRow;
			initiaizeGanttChartDates();
			$scope.options.showGanttChart = true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting activity schedule data", 'Error');
		});
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
			template: 'views/help&tutorials/Enterprisehelp.html',
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
