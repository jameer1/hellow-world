'use strict';

app.factory('ActivityScheduleAddDataSetFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectScheduleService", function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjectScheduleService) {
    var Popup;
    var service = {};
    service.addDataset = function (searchProject) {
        var deferred = $q.defer();
        Popup = ngDialog.open({
            template: 'views/projectschedules/activityscheduledatasetpopup.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
            showClose: false,
            closeByDocument: false,
            controller: ['$scope', function ($scope) {
            	$scope.searchProject = searchProject;
            		ProjectScheduleService.getDatasetList({"projId": $scope.searchProject.projId, "type": "A"}).then(function(data){
            		$scope.scheduleActivityDataSetTOs = data.scheduleActivityDataSetTOs;
            	})
            	$scope.parseScheduleFile = function (file) {
            		if ((file.size > 1000000)){
            			GenericAlertService.alertMessage("Maximum Allowed File size is 1MB", 'Info');
            			return;
            		}
            		ProjectScheduleService.parseScheduleActivityData(file, {"projId": $scope.searchProject.projId, "type": "A"}).then(function (data) {
            			$scope.scheduleActivityDataSet = data.data;
            			if (data.data.scheduleActivityDataTOs.length == 0)
            				GenericAlertService.alertMessage("This file is not in the expeccted format.</br></br>Expected Format: Critical, WBS ID, WBS Name, WBS Path, Activity ID, Activity Name, SOE ID, Original Duration, Start, Finish, Predecessors, Successors, Physical % Complete, Calendar, Lag or Lead", 'Info');
            		}, function (error) {
        				GenericAlertService.alertMessage("Error occured while importing", 'Error');
        			});
            	},
            	
            	$scope.duplicatename = function(name){
           
            		for(let i=0;i<$scope.scheduleActivityDataSetTOs.length;i++){
            			if(($scope.scheduleActivityDataSetTOs[i].datasetName).toUpperCase() == name.toUpperCase()){
            			GenericAlertService.alertMessage("Dataset name already exists", 'Warning');
            			return;
            		}
            			
            		}
            	}
            	
            	
            	$scope.saveDataset = function(){
            	$scope.duplicatename();
            		if ($scope.scheduleActivityDataSet.datasetName == null){
            			GenericAlertService.alertMessage("Please enter dataset name", 'Info');
            			return;
            		}
            		if ($scope.scheduleActivityDataSet.scheduleDate == null){
            			GenericAlertService.alertMessage("Please enter schedule date", 'Info');
            			return;
            		}
            		$scope.scheduleActivityDataSet.projId = $scope.searchProject.projId;
            		$scope.scheduleActivityDataSet.type = "A";
            		$scope.scheduleActivityDataSet.scheduleDate = new Date($scope.scheduleActivityDataSet.scheduleDate);
            		ProjectScheduleService.saveDataset($scope.scheduleActivityDataSet).then(function(data){
            			GenericAlertService.alertMessage("Dataset saved successfully", 'Info');
            			deferred.resolve(data);
    					$scope.closeThisDialog();
            		}, function (error) {
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
            	$scope.setSelectedRow = function(scheduleActivityDataTO) {
            		$scope.selectedUniqueIdentifier = scheduleActivityDataTO.wbsName + scheduleActivityDataTO.wbsPath + scheduleActivityDataTO.activityCode;
            	}
            }]
        });
        return deferred.promise;
    }
    return service;
}]);