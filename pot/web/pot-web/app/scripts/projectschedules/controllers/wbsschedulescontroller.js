'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("wbsschedules", {
		url: '/wbsschedules',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectschedules/wbsscheduleprojectview.html',
				controller: 'WbsProjectScheduleController'
			}
		}
	})
}]).controller('WbsProjectScheduleController', ["$rootScope", "$scope", "$q", "$filter", "ngDialog", "GenericAlertService", "EpsProjectSelectFactory", "DatasetListFactory", "ProjSOWService", "ProjectScheduleService",
	function ($rootScope, $scope, $q, $filter, ngDialog,GenericAlertService, EpsProjectSelectFactory, DatasetListFactory, ProjSOWService, ProjectScheduleService) {
	let selectProjectsSowItemTOs = [], selectedDatasetTOs = [];
	$scope.colors = ["#7db9e8", "#41B3A3", "#659DBD", "#BC986A", "#DAAD86", "#C38D9E", "#E8A87C", "#E27D60", "#8EE4AF", "#FFD64A"];
	$scope.options = {
			showGanttChart: false,
			currentDateValue: $filter('date')((new Date()), "dd-MMM-yyyy"),
			currentDate: 'line',
			timeScales: [
				{header: ['month', 'day'], headerFormat: {month: 'MMMM YYYY', day: "D"}, type: "Day", selectedSize: 25, minSize: 15, maxSize: 40}, 
				{header: ['month', 'week'], headerFormat: {month: 'MMMM YYYY', week: function(column) {return column.date.format('D [-] ') + column.endDate.format('D'); }}, type: "Week", selectedSize: 20, minSize: 8, maxSize: 35}, 
				{header: ['month'], headerFormat: {month: 'MMMM YYYY'}, type: "Month", selectedSize: 15, minSize: 5, maxSize: 35},
				{header: ['year', 'quarter'], headerFormat: {'year': 'YYYY'}, type: "Quarter", selectedSize: 10, minSize: 3, maxSize: 25},
				{header: ['year'], headerFormat: {'year': 'YYYY', 'quarter': '[Q]Q YYYY'}, type: "Year", selectedSize: 10, minSize: 3, maxSize: 25},
			],
			columns: ['model.data.name', 'model.data.measureUnitTO.name', 'model.data.projSOEItemTO.code', 'model.data.tangibleClassTO.code', 'model.data.projSORItemTO.code', 'model.data.projCostItemTO.code', 'model.data.startDate', 'model.data.finishDate', 'model.data.minStartDateOfBaseline', 'model.data.maxFinishDateOfBaseline', 'model.data.quantity', 'model.data.revisedQty', 'model.data.actualQty', 'model.data.id', 'model.data.comments'],
			columnsHeaders: {'model.name': 'SOW Item Id', 'model.data.name': 'SOW Item Description', 'model.data.measureUnitTO.name': 'Unit Of Measure', 'model.data.projSOEItemTO.code': 'SOE Item Id', 'model.data.tangibleClassTO.code': 'Tangible Item Id', 'model.data.projSORItemTO.code': 'SOR Item Id', 'model.data.projCostItemTO.code': 'Cost Item Id', 'model.data.startDate': 'Start', 'model.data.finishDate': 'Finish', 'model.data.minStartDateOfBaseline': 'Baseline Start', 'model.data.maxFinishDateOfBaseline': 'Baseline Finish', 'model.data.quantity': 'Original Estimation', 'model.data.revisedQty': 'Revised Estimation', 'model.data.actualQty': 'Actual Quantity', 'model.data.id': 'Balance', 'model.data.comments': 'SOW Comments'},
			columnsClasses: {'model.data.name': 'gantt-column-sow-description','model.data.measureUnitTO.name':'gantt-column-unit-of-measure','model.data.projSOEItemTO.code':'gantt-column-soe-item-id','model.data.tangibleClassTO.code':'gantt-column-tangible-item-id','model.data.projSORItemTO.code':'gantt-column-sor-item-id','model.data.projCostItemTO.code':'gantt-column-cost-code-item-id','model.data.minStartDateOfBaseline':'gantt-column-baseline-start','model.data.maxFinishDateOfBaseline':'gantt-column-baseline-finish','model.data.quantity':'gantt-column-original-estimation','model.data.revisedQty':'gantt-column-revised-estimation','model.data.actualQty':'gantt-chart-actual-quantity','model.data.id':'gantt-chart-balance','model.data.comments':'gantt-column-comments'},
			columnsFormatters: {
				'model.data.startDate': function (value) {
					return value !== null ? moment(value).format('ll') : undefined;
				},
			    'model.data.finishDate': function (value) {
			        return value !== null ? moment(value).format('ll') : undefined;
			    },
			    'model.data.minStartDateOfBaseline': function(minStartDateOfBaseline) {
			    	return minStartDateOfBaseline != null ? moment(minStartDateOfBaseline).format('ll') : undefined;
			    },
			    'model.data.maxFinishDateOfBaseline': function(maxFinishDateOfBaseline) {
			    	return maxFinishDateOfBaseline != null ? moment(maxFinishDateOfBaseline).format('ll') : undefined;
			    },
			    'model.data.id': function(value, column, row) {
			    	return (row.model.data.projSOEItemTO.code == null) ? '' : (row.model.data.revisedQty ? row.model.data.revisedQty : row.model.data.quantity) - row.model.data.actualQty;
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
	$scope.onSearch = function() {
		if (!$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId) {
			GenericAlertService.alertMessage("Please select Project", 'Info');
			return;
		}
		ProjSOWService.getProjSOWDetails({"projId" : $scope.searchCriteria.searchProject.projId, "status" : "1"}).then(function(data) {
			selectProjectsSowItemTOs = data.projSOWItemTOs;
			prepareSelectedScheduleActivityDataSetsChart();
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting SOW", 'Error');
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
		DatasetListFactory.selectMultiple($scope.searchCriteria.searchProject, "A", $scope.selectedScheduleActivityDataSetTOs, false).then(function(data){
			$scope.selectedScheduleActivityDataSetTOs = data;
			prepareSelectedScheduleActivityDataSetsChart();
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
	};
	let prepareSelectedScheduleActivityDataSetsChart = function() {
		ProjectScheduleService.getActualActivityScheduleFor({"scheduleActivityDataSetTOs": $scope.selectedScheduleActivityDataSetTOs}).then(function(data) {
			selectedDatasetTOs = data.scheduleActivityDataSetTOs;
			$scope.data = ganttArray(selectProjectsSowItemTOs);
			initiaizeGanttChartDates();
			$scope.options.showGanttChart = true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting activity schedule data", 'Error');
		});
	}
	let ganttArray = function(data) {
		let returnData = [];
		for (let i=0; i < data.length; i++) {
			let rowObj = {id: data[i].id, name: data[i].code, data: data[i]};
			if (data[i].childSOWItemTOs.length > 0) {
				let childrens = [];
				for (let j=0; j < data[i].childSOWItemTOs.length; j++)
					childrens.push(data[i].childSOWItemTOs[j].id);
				rowObj.children = childrens;
				rowObj.classes = ['row-height1'];
			} else {
				if (data[i].startDate != null && data[i].finishDate != null) {
					rowObj.tasks = [];
					for (let j=0; j < selectedDatasetTOs.length; j++) {
						if (selectedDatasetTOs[j].scheduleActivityDataTOs.some(e => e.sowId == data[i].id)) {
							let scheduleActivitiesForSow = selectedDatasetTOs[j].scheduleActivityDataTOs.filter(e => e.sowId == data[i].id);
							let minDate = scheduleActivitiesForSow.reduce((min, e) => e.startDate  < min ? e.startDate : min, scheduleActivitiesForSow[0].startDate);
							let maxDate = scheduleActivitiesForSow.reduce((max, e) => e.finishDate  > max ? e.finishDate : max, scheduleActivitiesForSow[0].finishDate);
							rowObj.tasks.push({id: data[i].id + '' + j, name: data[i].code, from: minDate, to: maxDate, priority: j, classes: ['bar-margin', 'bar-height' + (selectedDatasetTOs.length - j)], color: $scope.colors[j]});
						}
					}
					rowObj.classes = ['row-height' + rowObj.tasks.length];
				}
			}
			returnData.push(rowObj);
			if (data[i].childSOWItemTOs.length > 0)
				Array.prototype.push.apply(returnData, ganttArray(data[i].childSOWItemTOs));
		}
		return returnData;
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
			template: 'views/help&tutorials/scheduleshelp/sowschedulehelp.html',
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