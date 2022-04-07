'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("co", {
		url: '/co',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/changeorders/co.html',
				controller: 'ChangeOrdersController'
			}
		}
	})
}]).controller(
	'ChangeOrdersController',
	["$scope", "blockUI", "EpsProjectSelectFactory", "$rootScope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "ChangeOrdersFactory", "GenericAlertService", "ChangeOrdersService", function ($scope, blockUI, EpsProjectSelectFactory, $rootScope, $state, $q, ngDialog, EpsProjectMultiSelectFactory,
		ChangeOrdersFactory, GenericAlertService, ChangeOrdersService) {
			
	$scope.coSearchCriteria = {};
	$scope.coSearchCriteria.searchProject = {};	
	$scope.coSearchCriteria.userType = '1';
	$scope.changeOrdersListCount=0;
	$scope.changeOrderData = null;
		
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.coSearchCriteria.searchProject = data.searchProject;
			$scope.invalidProjectSelection = data.searchProject.parentName ? false : true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.openCOPopup = function() {
		console.log("openCOPopup function");
		console.log($scope.coSearchCriteria);
		ChangeOrdersFactory.coCreatePopup($scope.coSearchCriteria,'CREATE');
	},
	$scope.getChangeOrderDetails = function() {
		console.log($scope.coSearchCriteria);
		/*var co_request = {
			"changeOrderTOs" : [
				{
					"projId" : 1573
				}
			]
		};*/
		var co_request = {"status":1};
		console.log(co_request);
		ChangeOrdersService.fetchChangeOrderDetails(co_request).then(function(data){
			console.log(data);
			$scope.changeOrdersListCount = data.changeOrderTOs.length;
			$scope.changeOrderData = data;
		});
	},
	$scope.coMoreDetails = function(coData) {
		//console.log(coData);
		//console.log($scope.coSearchCriteria);
		$scope.coSearchCriteria = coData;
		ChangeOrdersFactory.coCreatePopup($scope.coSearchCriteria,'EDIT');
	}
}]);

