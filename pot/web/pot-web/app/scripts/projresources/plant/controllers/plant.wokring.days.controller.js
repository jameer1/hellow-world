'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantWorkingDays", {
		url : '/plantWorkingDays',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantlogbook/plantregworkingdays.html',
				controller : 'PlantWokringDayController'
			}
		}
	})
}]).controller("PlantWokringDayController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "PlantRegisterService", "GenericAlertService","uiGridConstants","uiGridGroupingConstants","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $filter, ngDialog, PlantRegisterService, GenericAlertService,uiGridConstants,uiGridGroupingConstants,stylesService, ngGridService) {
$scope.stylesSvc = stylesService;
	$scope.plantWorkingDaysTOs = [];
	$scope.getPlantWorkingDays = function() {
		if ($rootScope.selectedPlantData.projId == null || $rootScope.selectedPlantData.projId == undefined) {
			GenericAlertService.alertMessage("Selected plant is not assigned to any project", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedPlantData.projId,
			"plantId" : $rootScope.selectedPlantData.id
		};
		PlantRegisterService.getPlantAttendence(req).then(function(data) {
			$scope.plantWorkingDaysTOs = data.labelKeyTOs;
				for (let manpower of $scope.plantWorkingDaysTOs) {
				manpower.totalUsedIdle = manpower.displayNamesMap.WORKING_DAYS - manpower.displayNamesMap.NONWORKING_DAYS-manpower.displayNamesMap.IDLE_DAYS;
			    manpower.days="days";
			}
		   $scope.gridOptionss.data = angular.copy($scope.plantWorkingDaysTOs);
           console.log($scope.gridOptionss.data,'data')

			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting plant working days", "Error");
		});
	}, $scope.getPlantWorkingDays();
	$scope.calculateYearWiseDays = function(plantWorkingDaysTO) {
		var totalDays = 0;
		totalDays = totalDays + parseFloat(plantWokringDayTO.displayNamesMap.WORKING_DAYS);
		totalDays = totalDays + parseFloat(plantWokringDayTO.displayNamesMap.NONWORKING_DAYS);
		totalDays = totalDays + parseFloat(plantWokringDayTO.displayNamesMap.IDLE_DAYS);
		return totalDays;
	}, $scope.calculatetTotalDays = function() {
		var totalDays = 0;
		angular.forEach($scope.plantWorkingDaysTOs, function(value, key) {
			totalDays = totalDays + parseFloat(plantWokringDayTO.displayNamesMap.WORKING_DAYS);
			totalDays = totalDays + parseFloat(plantWokringDayTO.displayNamesMap.NONWORKING_DAYS);
			totalDays = totalDays + parseFloat(plantWokringDayTO.displayNamesMap.IDLE_DAYS);
		});
		return totalDays;
	}
	$scope.calculateVerticalTotal=function(plantWorkingDaysTOs){
	var workingDays=0;
	var nonworkingDays = 0;
	var idleDays  = 0;
	angular.forEach($scope.plantWorkingDaysTOs, function(value, key) {
	
		workingDays =workingDays + parseFloat(value.displayNamesMap.WORKING_DAYS);
		nonworkingDays = nonworkingDays + parseFloat(value.displayNamesMap.NONWORKING_DAYS);
		idleDays = idleDays + parseFloat(value.displayNamesMap.IDLE_DAYS);
		$scope.totalobj ={
				workingDays : workingDays,
				nonworkingDays : nonworkingDays,
				idleDays : idleDays
				
		}
	});
	return $scope.totalobj;
	}

	$scope.$on("resetPlantWorkingDays", function() {
		$scope.plantWorkingDaysTOs = [];
	});
	
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'displayNamesMap.PLANT_YEAR',  displayName: "Period", headerTooltip : "Period",},
						{ field: 'days', displayName: "Unit", headerTooltip: "Unit"},
						{ field: 'displayNamesMap.WORKING_DAYS', displayName: "Working", headerTooltip: "Working",treeAggregationType: uiGridGroupingConstants.aggregation.SUM,customTreeAggregationFinalizerFn: function (aggregation) {aggregation.rendered = aggregation.value;} },
						{ field: 'displayNamesMap.NONWORKING_DAYS',  displayName: "Non Working", headerTooltip : "Non Working",treeAggregationType: uiGridGroupingConstants.aggregation.SUM,customTreeAggregationFinalizerFn: function (aggregation) {aggregation.rendered = aggregation.value;}},
						{ field: 'displayNamesMap.IDLE_DAYS', displayName: "Idle", headerTooltip: "Idle",treeAggregationType: uiGridGroupingConstants.aggregation.SUM,customTreeAggregationFinalizerFn: function (aggregation) {aggregation.rendered = aggregation.value;}},
                      	{ field: 'totalUsedIdle', displayName: "total", headerTooltip: "total",treeAggregationType: uiGridGroupingConstants.aggregation.SUM,customTreeAggregationFinalizerFn: function (aggregation) {aggregation.rendered = aggregation.value;}}
						];
					let data = [];
					$scope.gridOptionss = ngGridService.initGrid($scope, columnDefs, data, "Resourse_Plant & Equipment_Log Book Records_Plant Working Days");
				}
			});
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
			
}]);