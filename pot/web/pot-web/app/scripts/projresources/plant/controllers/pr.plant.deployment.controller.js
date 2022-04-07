'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantdeployment", {
		url : '/plantdeployment',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantdeployment/plantregdeployment.html',
				controller : 'PlantDeployemntController'
			}
		}
	})
}]).controller("PlantDeployemntController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PlantDeploymentFactory", "PlantRegisterService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, PlantDeploymentFactory, PlantRegisterService, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantRegProjTOs = [];
	var editDeployment = [];
	$scope.userProjMap = [];
	$scope.deMobStatus = [];

	$scope.showCreateButton = false;
	$scope.showEditButton = false;
	$scope.projPlantMap = [];

	$scope.getPlantDeploymentRecords = function() {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		if ( !$rootScope.selectedPlantData.projId && $rootScope.selectedPlantData.assetType == 'New Plant') {
			GenericAlertService.alertMessage("Please Enter Procure &  Delivery Details For New Plant" , "Warning");
			return;
		}

		if (($rootScope.selectedPlantData.projId == undefined || $rootScope.selectedPlantData.projId == null) && $rootScope.selectedPlantData.assetType == 'Exsisting Plant') {
			$scope.showCreateButton = true;
		}

		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedPlantData.projId,
			"plantId" : $rootScope.selectedPlantData.id
		};
		PlantRegisterService.getPlantDeploymentOnLoad(req).then(function(data) {
			$scope.userProjMap = data.projectLibTO.userProjMap;
			$scope.projPlantMap = data.projectLibTO.projClassMap;

			$scope.deMobStatus = data.deMobStatus;
			$scope.plantRegProjTOs = data.plantRegProjTOs;
			$scope.gridOptions.data = angular.copy($scope.plantRegProjTOs);
			if ($scope.plantRegProjTOs != undefined && $scope.plantRegProjTOs != null && $scope.plantRegProjTOs.length > 0) {
				$scope.showCreateButton = false;
				$scope.showEditButton = true;
			}

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Plant Deployment Details", "Error");
		});

	}

	$scope.getPlantDeploymentRecords();

	$scope.deployRowSelect = function(deployment) {
		if (deployment.select) {
			editDeployment.push(deployment);
		} else {
			editDeployment.pop(deployment);
		}
	}
	$scope.addDeployment = function(actionType) {
		if (actionType == 'Edit' && editDeployment <= 0) {
			GenericAlertService.alertMessage('Please select active record to modify', "Warning");
			return;
		}
		var resultData = PlantDeploymentFactory.plantDeploymentOnLoad(actionType, editDeployment, $scope.userProjMap, $scope.projPlantMap,$scope.plantClassMstrMap, $scope.deMobStatus,$scope.plantRegProjTOs);
		resultData.then(function(data) {
			$scope.plantRegProjTOs = data.plantRegProjTOs;
			$scope.showCreateButton = false;
			$scope.showEditButton = true;
			editDeployment = [];
			$rootScope.$broadcast('plantRefresh', {tabIndex:2});
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant deployment details", 'Error');
		});
	}
	$scope.deployementComments = function(remarks) {
		GenericAlertService.comments(remarks);
	}

	$scope.$on("resetDeploymentHistory", function() {
		$scope.plantRegProjTOs = [];
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.deployRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', displayName: "Select", headerTooltip : "Select", cellTemplate:linkCellTemplate },
						{ field: 'deploymentId', displayName: "Project Deployment#", headerTooltip: "Project Deployment#",},
						{ field: 'parentName', displayName: "EPS Name", headerTooltip: "EPS Name",},
						{ field: 'name', displayName: "Project Name", headerTooltip : "Project Name",},
						{ field: 'Description', displayName: "Profit Center", headerTooltip : "Profit Center",},
						{ field: 'Make', displayName: "Finance Center", headerTooltip: "Finance Center",},
						{ field: 'commissionDate', displayName: "Commission Date of New Asset", headerTooltip: "Commission Date of New Asset",},
						{ field: 'assertId', displayName: "Asset ID", headerTooltip : "Asset ID",},
						{ field: 'plantMasterName', displayName: "Project Plant Id", headerTooltip : "Project Plant Id",},
						{ field: 'mobDate', displayName: "Mobilization Date", headerTooltip: "Mobilization Date",},
						{ field: 'anticipatedDeMobDate', displayName: "Expected De-Mobilization Date", headerTooltip: "Expected De-Mobilization Date",},
						{ field: 'deMobDate', displayName: "De-Mobilization Date", headerTooltip : "De-Mobilization Date", },
						{ field: 'postDeMobStatus', displayName: "Post De-Mobilization Status", headerTooltip : "Post De-Mobilization Status", },
						{ field: 'odoMeter', displayName: "Reading Mob", headerTooltip: "Reading on Mob",},
						{ field: 'deMobOdoMeter', displayName: "Meter Reading on De-Mob", headerTooltip: "Meter Reading on De-Mob",},
						{ field: 'comments', displayName: "Notes", headerTooltip : "Notes",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_PlantAndEquipments_DeployementHistory");
					$scope.getPlantDeploymentRecords();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);
