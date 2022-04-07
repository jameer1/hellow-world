'use strict';

app.factory('PickTangibleFactory', ["ngDialog", "$q", "$filter", "$timeout", "GenericAlertService", "PotCommonService",
	function (ngDialog, $q, $filter, $timeout, GenericAlertService, PotCommonService) {
    var Popup;
    var service = {};
    service.selectMultiple = function (projectIds, resetTOs, showOnlyActuals) {
        var deferred = $q.defer();
        Popup = ngDialog.open({
            template: 'views/common/tangiblelistmultiple.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom4',
            showClose: false,
            closeByDocument: false,
            controller: ['$scope', function ($scope) {
            	$scope.selectedTangibleTOs = [];
            	$scope.availableTangibleTOs = [];
            	PotCommonService.getTangiblesOfProjects({projectIds: projectIds, showOnlyActuals: showOnlyActuals}).then(function (data) {
            		$scope.availableTangibleTOs = $filter('unique')(data.projectTangibleTOs,'tangibleCode');
            		$scope.reset();
            	}, function (error) {
        			GenericAlertService.alertMessage("Error occured while getting tangibles of project", 'Error');
        		}),
            	$scope.isSelected = function(projectTangibleTO) {
            		for (let i=0; i < $scope.selectedTangibleTOs.length; i++) {
            			if ($scope.selectedTangibleTOs[i].tangibleItemId == projectTangibleTO.tangibleItemId) {
            				return true;
            			}
            		}
            	    return false;
            	},
            	
            	$scope.selectAll = function(tab){
            	if(tab == true){
            	$scope.selectedTangibleTOs = $scope.availableTangibleTOs;
            	for(let i=0;i<$scope.availableTangibleTOs.length;i++){
            	$scope.isSelected($scope.availableTangibleTOs[i]);
            	}
            	}
            	if(tab == false){
            	$scope.selectedTangibleTOs = [];
            	for(let i=0;i<$scope.availableTangibleTOs.length;i++){
            	$scope.isSelected($scope.availableTangibleTOs[i]);
            	}
            	}
            	}
            	
            	
            	$scope.toggleSelection = function($event, projectTangibleTO) {
            		$event.stopPropagation();
            		let index = -1;
            		for (let i=0; i < $scope.selectedTangibleTOs.length; i++) {
            			if ($scope.selectedTangibleTOs[i].tangibleItemId == projectTangibleTO.tangibleItemId) {
            				index = i;
            				break;
            			}
            		}
            	    if (index > -1)
            	    	$scope.selectedTangibleTOs.splice(index, 1);
              	    else
              	    	$scope.selectedTangibleTOs.push(projectTangibleTO);
            	},
            	$scope.OK = function() {
            		deferred.resolve($scope.selectedTangibleTOs);
            		$scope.closeThisDialog();
            	},
            	$scope.reset = function() {
            		$scope.selectedTangibleTOs = angular.copy(resetTOs);
            	};
            }]
        });
        return deferred.promise;
    }
    return service;
}]);