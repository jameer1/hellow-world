'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("resourceassignment", {

		url: '/resourceassignment',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectschedules/resourcesassignmentdatatable.html',
				controller: 'ResourceAssignmentController'
			}
		}
	})
}]).controller("ResourceAssignmentController", ["$scope", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectSelectFactory", "DatasetListFactory", "ProjectScheduleService", "ResourceAssignmentAddDataSetFactory", "ProjectStatusService", function ($scope, $q, $state, ngDialog, GenericAlertService, EpsProjectSelectFactory, DatasetListFactory, ProjectScheduleService, ResourceAssignmentAddDataSetFactory, ProjectStatusService) {
	$scope.searchCriteria = {};
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
		if (!$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId) {
			GenericAlertService.alertMessage("Please select project", 'Info');
			return;
		}
		DatasetListFactory.selectOne($scope.searchCriteria.searchProject, "R").then(function(data){
			ProjectScheduleService.getScheduleActivity({"id" : data.id}).then(function(data){
				$scope.searchCriteria.searchDataset = data;
				$scope.scheduleActivityDataSet = data;
				if ($scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs.length > 0)
    				$scope.headerColumns = $scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs[0].resourceAssignmentDataValueTOs;
			}, function(error) {
				cosole.log(error)
				GenericAlertService.alertMessage("Error occured while selecting Dataset", 'Error');
			})
		}, function(error) {
			cosole.log(error)
			GenericAlertService.alertMessage("Error occured while selecting Dataset", 'Error');
		})
	},
	$scope.manageDatasets = function(){
		if (!$scope.searchCriteria.searchProject || !$scope.searchCriteria.searchProject.projId) {
			GenericAlertService.alertMessage("Please select project", 'Info');
			return;
		}
		DatasetListFactory.manageDatasets($scope.searchCriteria.searchProject, "R").then(function(data){
			$scope.searchCriteria.searchDataset = data;
			$scope.scheduleActivityDataSet = data;
			if ($scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs.length > 0)
				$scope.headerColumns = $scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs[0].resourceAssignmentDataValueTOs;
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
		ResourceAssignmentAddDataSetFactory.addDataset($scope.searchCriteria.searchProject).then(function (data) {
			$scope.searchCriteria.searchDataset = data;
			$scope.scheduleActivityDataSet = data;
			if ($scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs.length > 0)
				$scope.headerColumns = $scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs[0].resourceAssignmentDataValueTOs;
		}, function (error) {
			cosole.log(error)
			GenericAlertService.alertMessage("Error occured while fetching Details", 'Error');
		});
	},
	$scope.setSelectedRow = function(resourceAssignmentDataTableTO) {
		$scope.selectedUniqueIdentifier = resourceAssignmentDataTableTO.id;
	}
}]);