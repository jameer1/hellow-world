'use strict';

app.factory('ResourceAssignmentAddDataSetFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectScheduleService", "ProjectStatusService", function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjectScheduleService, ProjectStatusService) {
    var Popup;
    var service = {};
    service.addDataset = function (searchProject) {
        var deferred = $q.defer();
        Popup = ngDialog.open({
            template: 'views/projectschedules/resourceassignmentdatasetpopup.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
            showClose: false,
            closeByDocument: false,
            controller: ['$scope', function ($scope) {
            	$scope.searchProject = searchProject;
            	$scope.name = "";
            	$scope.isPrimaveraIntegrationEnabled = 'Yes';
            	ProjectScheduleService.getDatasetList({"projId": $scope.searchProject.projId, "type": "R"}).then(function(data){
            		$scope.scheduleActivityDataSetTOs = data.scheduleActivityDataSetTOs
            	})
            	ProjectStatusService.getProjGenerals({"projId": $scope.searchProject.projId, "status": 1}).then(function(data){
    				$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
    			}, function (error) {    				
    				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
    			});
            	$scope.parseScheduleFile = function (file) {
            		if ((file.size > 2000000)){
            			GenericAlertService.alertMessage("Supported Max. File size 1MB", 'Error');
            			return;
            		}
            		ProjectScheduleService.parseResourceAssignmentData(file, {"projId": $scope.searchProject.projId, "type": "R", "scheduleDate": new Date($scope.scheduleActivityDataSet.scheduleDate), "datasetName" : $scope.scheduleActivityDataSet.datasetName}).then(function (data) {
            			$scope.scheduleActivityDataSet = data.data;
            			$scope.scheduleActivityDataSet.scheduleDate = $filter('date')(data.data.scheduleDate, "dd-MMM-yyyy");
            			$scope.scheduleActivityDataSet.datasetName = data.data.datasetName;
            			if ($scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs.length > 0)
            				$scope.headerColumns = $scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs[0].resourceAssignmentDataValueTOs;
            		}, function (error) {
        				GenericAlertService.alertMessage("Error occured while importing", 'Error');
        			});
            	},
            	$scope.prepareDataset = function() {
            		if ($scope.scheduleActivityDataSet.scheduleDate == null){
            			GenericAlertService.alertMessage("Please enter schedule date", 'Info');
            			return;
            		}
            		ProjectScheduleService.prepareResourceAssignmentData({"projId": $scope.searchProject.projId, "type": "R", "scheduleDate": new Date($scope.scheduleActivityDataSet.scheduleDate), "datasetName" : $scope.scheduleActivityDataSet.datasetName}).then(function (data) {
            			$scope.scheduleActivityDataSet = data;
            			console.log(data)
            			$scope.scheduleActivityDataSet.scheduleDate = $filter('date')(data.scheduleDate, "dd-MMM-yyyy");
            			$scope.scheduleActivityDataSet.datasetName = data.datasetName;
            			if ($scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs.length > 0)
            				$scope.headerColumns = $scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs[0].resourceAssignmentDataValueTOs;
            		}, function (error) {
            			cosole.log(error)
        				GenericAlertService.alertMessage("Error occured while preparing dataset", 'Error');
        			});
            	},
            	$scope.duplicate = function(name){
					$scope.name = name;
            		for(let i=0;i<$scope.scheduleActivityDataSetTOs.length;i++){
            			if(($scope.scheduleActivityDataSetTOs[i].datasetName).toUpperCase() == name.toUpperCase()){
            			    GenericAlertService.alertMessage("Dataset name already exists", 'Warning');
            			    return;
            		}
            			
            		}
            	}
            	$scope.saveDataset = function(){
            	$scope.duplicate($scope.name);
            		if ($scope.scheduleActivityDataSet.datasetName == null){
            			GenericAlertService.alertMessage("Please enter dataset name", 'Info');
            			return;
            		}
            		if ($scope.scheduleActivityDataSet.scheduleDate == null){
            			GenericAlertService.alertMessage("Please enter schedule date", 'Info');
            			return;
            		}
            		$scope.scheduleActivityDataSet.projId = $scope.searchProject.projId;
            		$scope.scheduleActivityDataSet.type = "R";
            		$scope.scheduleActivityDataSet.scheduleDate = new Date($scope.scheduleActivityDataSet.scheduleDate);
            		if ($scope.isPrimaveraIntegrationEnabled == 'No') $scope.scheduleActivityDataSet.resourceAssignmentDataTableTOs = [];
            		ProjectScheduleService.saveDataset($scope.scheduleActivityDataSet).then(function(data){
            			GenericAlertService.alertMessage("Dataset saved successfully", 'Info');
            			deferred.resolve(data);
    					$scope.closeThisDialog();
            		}, function (error) {
            			cosole.log(error)
        				GenericAlertService.alertMessage("Error occured while saving", 'Error');
        			})
            	},
            	$scope.showComments = function(messages) {
            		ngDialog.open({
            			template : 'views/projectschedules/viewpopup.html',
            			className : 'ngdialog-theme-plain ng-dialogueCustom6',
            			showClose : false,
            			controller : [ '$scope', function($scope) {
            				$scope.messages = messages;
            			} ]
            		});
            	},
            	$scope.setSelectedRow = function(resourceAssignmentDataTableTO) {
            		$scope.selectedUniqueIdentifier = resourceAssignmentDataTableTO.code;
            	}
            }]
        });
        return deferred.promise;
    }
    return service;
}]);