'use strict';

app.factory('DatasetListFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectScheduleService", function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjectScheduleService) {
    var Popup;
    var service = {};
    service.selectOne = function (searchProject, type) {
        var deferred = $q.defer();
        Popup = ngDialog.open({
            template: 'views/projectschedules/datasetlist.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom4',
            showClose: false,
            closeByDocument: false,
            controller: ['$scope', function ($scope) {
            	$scope.searchProject = searchProject;
            	ProjectScheduleService.getDatasetList({"projId": $scope.searchProject.projId, "type": type}).then(function(data){
            		$scope.scheduleActivityDataSetTOs = data.scheduleActivityDataSetTOs
            	}, function (error) {
            		cosole.log(error)
    				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
    			});
            	$scope.selectDataset = function(scheduleActivityDataSetTO) {
            		deferred.resolve(scheduleActivityDataSetTO);
					$scope.closeThisDialog();
            	}
            }]
        });
        return deferred.promise;
    },
    service.selectMultiple = function (searchProject, type, resetScheduleActivityDataSetTOs, includeActual, includeCurrentPlan) {
        var deferred = $q.defer();
        Popup = ngDialog.open({
            template: 'views/projectschedules/datasetlistmultiple.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom4',
            showClose: false,
            closeByDocument: false,
            controller: ['$scope', function ($scope) {
            	$scope.selectedScheduleActivityDataSetTOs = [];
            	ProjectScheduleService.getDatasetList({"projId": searchProject.projId, "type": type}).then(function(data){
            		$scope.scheduleActivityDataSetTOs = data.scheduleActivityDataSetTOs;
            		if (includeCurrentPlan || resetScheduleActivityDataSetTOs.some(el => el.datasetName == 'Current Plan'))
            			$scope.scheduleActivityDataSetTOs.unshift({"id": -1, "datasetName":"Current Plan", "current": false, "baseline": false});
            		if (includeActual || resetScheduleActivityDataSetTOs.some(el => el.datasetName == 'Actual'))
            			$scope.scheduleActivityDataSetTOs.unshift({"id": 0, "datasetName":"Actual", "current": false, "baseline": false});
            		$scope.reset();
            	}, function (error) {
            		cosole.log(error)
    				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
    			}),
    			$scope.isSelected = function(scheduleActivityDataSetTO) {
            		for (let i=0; i < $scope.selectedScheduleActivityDataSetTOs.length; i++) {
            			if ($scope.selectedScheduleActivityDataSetTOs[i].id == scheduleActivityDataSetTO.id) {
            				return true;
            			}
            		}
            	    return false;
            	},
            	$scope.isReadOnly = function(scheduleActivityDataSetTO) {
            		const datasetTO = resetScheduleActivityDataSetTOs.find(e => e.id == scheduleActivityDataSetTO.id);
            		if (datasetTO === undefined) {
            			return false;
            		} else {
            			if (datasetTO.hasOwnProperty('readOnly'))
            				return datasetTO.readOnly;
            			else
            				return false;
            		}
            	},
            	$scope.toggleSelection = function($event, scheduleActivityDataSetTO) {
            		$event.stopPropagation();
            		if ($scope.isReadOnly(scheduleActivityDataSetTO)) return;
            		let index = -1;
            		for (let i=0; i < $scope.selectedScheduleActivityDataSetTOs.length; i++) {
            			if ($scope.selectedScheduleActivityDataSetTOs[i].id == scheduleActivityDataSetTO.id) {
            				index = i;
            				break;
            			}
            		}
            	    if (index > -1)
            	    	$scope.selectedScheduleActivityDataSetTOs.splice(index, 1);
              	    else
              	    	$scope.selectedScheduleActivityDataSetTOs.push(scheduleActivityDataSetTO);
            	  },
            	  $scope.OK = function() {
            		  deferred.resolve($scope.selectedScheduleActivityDataSetTOs);
            		  $scope.closeThisDialog();
            	  },
            	  $scope.reset = function() {
            		  $scope.selectedScheduleActivityDataSetTOs = angular.copy(resetScheduleActivityDataSetTOs);
            	  }
            }]
        });
        return deferred.promise;
    },
    service.manageDatasets = function (searchProject, type) {
    	var deferred = $q.defer();
        Popup = ngDialog.open({
            template: 'views/projectschedules/dataseteditlist.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom4',
            showClose: false,
            closeByDocument: false,
            controller: ['$scope', function ($scope) {
            	$scope.searchProject = searchProject;
            	$scope.selectedCurrent = 0;
            	$scope.selectedBaseline = 0;
            	$scope.resetCurrent = 0;
            	$scope.resetBaseline = 0;
            	ProjectScheduleService.getDatasetList({"projId": $scope.searchProject.projId, "type": type}).then(function(data){
            		$scope.scheduleActivityDataSetTOs = data.scheduleActivityDataSetTOs;
            		for (let i=0; i < $scope.scheduleActivityDataSetTOs.length; i++){
            			if ($scope.scheduleActivityDataSetTOs[i].current)
            				$scope.selectedCurrent = $scope.scheduleActivityDataSetTOs[i].id;
            			if ($scope.scheduleActivityDataSetTOs[i].baseline)
            				$scope.selectedBaseline = $scope.scheduleActivityDataSetTOs[i].id;
            		}
            		$scope.resetCurrent = $scope.selectedCurrent;
            		$scope.resetBaseline = $scope.selectedBaseline;
            	}, function (error) {
            		cosole.log(error)
    				GenericAlertService.alertMessage("Error occured while getting Datasets", 'Error');
    			});
            	$scope.setCurrent = function(id){
            		$scope.selectedCurrent = id;
            	},
            	$scope.setBaseline = function(id){
            		$scope.selectedBaseline = id;
            	},
            	$scope.reset = function(){
            		$scope.selectedCurrent = $scope.resetCurrent;
            		$scope.selectedBaseline = $scope.resetBaseline;
            	}
            	$scope.save = function(){
            		let newScheduleActivityDataSetTOs = [];
            		for (let i=0; i < $scope.scheduleActivityDataSetTOs.length; i++){
            			if ($scope.scheduleActivityDataSetTOs[i].id == $scope.selectedCurrent){
            				$scope.scheduleActivityDataSetTOs[i].current = true;
            				$scope.scheduleActivityDataSetTOs[i].baseline = false;
            				newScheduleActivityDataSetTOs.push($scope.scheduleActivityDataSetTOs[i])
            			}
            			if ($scope.scheduleActivityDataSetTOs[i].id == $scope.selectedBaseline){
            				$scope.scheduleActivityDataSetTOs[i].baseline = true;
            				$scope.scheduleActivityDataSetTOs[i].current = false;
            				newScheduleActivityDataSetTOs.push($scope.scheduleActivityDataSetTOs[i])
            			}
            		}
            		if(newScheduleActivityDataSetTOs[0].datasetName == newScheduleActivityDataSetTOs[1].datasetName){
						newScheduleActivityDataSetTOs[0].baseline = true;
						newScheduleActivityDataSetTOs[0].current = true;
						newScheduleActivityDataSetTOs[1].baseline = true;
						newScheduleActivityDataSetTOs[1].current = true;
					}
            		ProjectScheduleService.saveScheduleActivityDatasets({"scheduleActivityDataSetTOs": newScheduleActivityDataSetTOs}).then(function(data){
            			GenericAlertService.alertMessage("Dataset saved successfully", 'Info');
            			deferred.resolve(data);
    					$scope.closeThisDialog();
            		}, function (error) {
            			cosole.log(error)
            			GenericAlertService.alertMessage("Error occured while saving", 'Error');
            		})
            	}
            }]
        });
        return deferred.promise;
    }
    return service;
}]);