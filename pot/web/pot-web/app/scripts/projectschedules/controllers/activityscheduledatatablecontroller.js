'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("activityscheduledatatable", {

		url: '/activityscheduledatatable',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectschedules/activityscheduledatatable.html',
				controller: 'ActivityScheduleDataTableController'
			}
		}
	})
}]).controller("ActivityScheduleDataTableController", ["$scope", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectSelectFactory", "ActivityScheduleAddDataSetFactory", "DatasetListFactory", "ProjectScheduleService", "stylesService", "ngGridService",
	function ($scope, $q, $state, ngDialog, GenericAlertService, EpsProjectSelectFactory, ActivityScheduleAddDataSetFactory, DatasetListFactory, ProjectScheduleService,stylesService, ngGridService) {
	$scope.searchCriteria = {};
	$scope.stylesSvc = stylesService;
		$scope.getUserProjects = function () {
			EpsProjectSelectFactory.getEPSUserProjects().then(function (data) {
				$scope.searchCriteria.searchProject = data.searchProject;
				$scope.searchCriteria.searchDataset = null;
			}, function (error) {
				cosole.log(error)
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		},
		$scope.getDatasets = function(){
			if ( $scope.searchCriteria.searchProject != undefined && !$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId ) {
				GenericAlertService.alertMessage("Please select project", 'Info');
				return;
			}
			DatasetListFactory.selectOne($scope.searchCriteria.searchProject, "A").then(function(data){
				ProjectScheduleService.getScheduleActivity({"id" : data.id}).then(function(data){
					$scope.searchCriteria.searchDataset = data;
					//$scope.searchCriteria.searchDataset1 = data.scheduleActivityDataTOs; 
				    $scope.gridOptions.data = angular.copy($scope.searchCriteria.searchDataset1);
					$scope.formatBooleans(data);
				}, function(error) {
					cosole.log(error)
					GenericAlertService.alertMessage("Error occured while selecting Dataset", 'Error');
				})
			}, function(error) {
				cosole.log(error)
				GenericAlertService.alertMessage("Error occured while selecting Dataset", 'Error');
			})
		},
		$scope.addDataSet = function () {
			if (!$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId) {
				GenericAlertService.alertMessage("Please select project", 'Info');
				return;
			}
			ActivityScheduleAddDataSetFactory.addDataset($scope.searchCriteria.searchProject).then(function (data) {
				$scope.searchCriteria.searchDataset = data;
				$scope.formatBooleans(data);
			}, function (error) {
				cosole.log(error)
				GenericAlertService.alertMessage("Error occured while fetching Details", 'Error');
			});
		},
		$scope.manageDatasets = function(){
			if (!$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId) {
				GenericAlertService.alertMessage("Please select project", 'Info');
				return;
			}
			DatasetListFactory.manageDatasets($scope.searchCriteria.searchProject, "A").then(function(data){
				$scope.searchCriteria.searchDataset = data;
				$scope.formatBooleans(data);
			}, function(error) {
				cosole.log(error)
				GenericAlertService.alertMessage("Error occured while selecting Dataset", 'Error');
			})
		},
		$scope.formatBooleans = function(data){
			if (data.current == true)
				$scope.searchCriteria.searchDataset.current = 'Yes';
			else 
				$scope.searchCriteria.searchDataset.current = 'No';
			if (data.baseline == true)
				$scope.searchCriteria.searchDataset.baseline = 'Yes';
			else 
				$scope.searchCriteria.searchDataset.baseline = 'No';
		},
    	$scope.setSelectedRow = function(scheduleActivityDataTO) {
    		$scope.selectedUniqueIdentifier = scheduleActivityDataTO.id;
    	}
    	
    	
    	/*$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'critical', cellFilter:'yesnofilter:row.entity.critical', cellTemplate:'<div>{{row.entity.critical | yesnofilter:row.entity.critical}}</div>', cellClass:"justify-center", headerCellClass:"justify-center", displayName: "Critical", headerTooltip: "Critical", groupingShowAggregationMenu: false},
					{ field: 'wbsCode', displayName: "WBS", headerTooltip: "WBS", groupingShowAggregationMenu: false },
					{ field: 'wbsName', displayName: "WBS Name", headerTooltip: "WBS Name", groupingShowAggregationMenu: false },
					{ field: 'activityCode', displayName: "Activity ID", headerTooltip: "Activity ID", groupingShowAggregationMenu: false },
					{ field: 'activityName', displayName: "Activity Name", headerTooltip: "Activity Name", groupingShowAggregationMenu: false },
					{ field: 'soeCode', displayName: "SOE ID", headerTooltip: "SOE ID", groupingShowAggregationMenu: false },
					{ field: 'originalDuration', displayName: "Original Duration", headerTooltip: "Original Duration", groupingShowAggregationMenu: false },
					{ name: 'startDate' ,cellFilter:'date',cellTemplate:'<div>{{row.entity.startDate | date}}</div>',displayName: "Start", headerTooltip: "Start", groupingShowAggregationMenu: false },
					{ name: 'finishDate' ,cellFilter:'date',cellTemplate:'<div>{{row.entity.finishDate | date}}</div>', displayName: "Finish", headerTooltip: "Finish", groupingShowAggregationMenu: false },
					{ field: 'predecessors', displayName: "Predecessors", headerTooltip: "Predecessors", groupingShowAggregationMenu: false },
                      { field: 'successors', displayName: "Successors", headerTooltip: "Successors", groupingShowAggregationMenu: false },
					 { field: 'physicalComplete', displayName: "Physical % Complete", headerTooltip: "Physical % Complete", groupingShowAggregationMenu: false },
					{ field: 'calendar', displayName: "Calendar", headerTooltip: "Calendar", groupingShowAggregationMenu: false },
				    { field: 'leadLag', displayName: "Lag or Lead", headerTooltip: "Lag or Lead", groupingShowAggregationMenu: false },
					
				]
				
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Schedules_Activity Schedule Data Table");
					$scope.getDatasets()
			}
		});*/
    	
}]);


