'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantservicehistorydetail", {
		url : '/plantservicehistorydetail',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantservicehistory/plantregservicehistory.html',
				controller : 'PlantServiceHistoryDetailController'
			}
		}
	})
}]).controller("PlantServiceHistoryDetailController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PlantServiceHistoryDetailFactory", "PlantRegisterService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, PlantServiceHistoryDetailFactory, PlantRegisterService, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantServiceDtlTable = true;
	$scope.serviceHistoryData = [];
	$scope.getPlantServiceHistory = function() {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		if ($rootScope.selectedPlantData.projId == null || $rootScope.selectedPlantData.projId == undefined) {
			GenericAlertService.alertMessage("Please select project assigned plant", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"plantId" : $rootScope.selectedPlantData.id,
			"projId" : $rootScope.selectedPlantData.projId
		};
		PlantRegisterService.plantServiceOnLoad(req).then(function(data) {
			$scope.plantServiceClassMap = data.plantServiceClassMap;
			$scope.plantServiceHistoryTOs = data.plantServiceHistoryTOs;
			$scope.gridOptions.data = angular.copy($scope.plantServiceHistoryTOs);
		}, function(error) {
			if (error.message != null && error.message != undefined) {
				GenericAlertService.alertMessage(error.message, error.status);
			} else {
				GenericAlertService.alertMessage("Error occured while getting Service History Details", "Error");
			}
		});
	}, $scope.getPlantServiceHistory();
	$scope.addServiceHistory = function(actionType) {
		if ($rootScope.selectedPlantData.id <= 0) {
			GenericAlertService.alertMessage('Please select the plant', "Warning");
			return;
		}
		var popup = PlantServiceHistoryDetailFactory.plantServiceHistoryPopUp($scope.plantServiceClassMap);
		popup.then(function(data) {
			$scope.plantServiceDtlTable = false;
			$scope.plantServiceClassMap = data.plantServiceClassMap;
			$scope.plantServiceHistoryTOs = data.plantServiceHistoryTOs;
			$scope.plantServiceDtlTable = true;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant service history details", 'Error');
		});
	}
	$scope.showServiceHistory = function(remarks) {
		GenericAlertService.comments(remarks);
	}
	
	$scope.$on("resetServiceHistory", function() {
		$scope.plantServiceHistoryTOs = [];
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { field: 'currentOdoMeter', displayName: "Last/Curnt Serv-Metr Read", headerTooltip : "Last/Curnt Serv-Metr Read" },
						{ field: 'currentPlantServiceParentName', displayName: "Last/Curnt Serv-Cetogory", headerTooltip: "Last/Curnt Serv-Cetogory"},
						{ field: 'currentPlantServiceName', displayName: "Last/Curnt Serv-Sub Catogory", headerTooltip: "Last/Curnt Serv-Sub Catogory", },
						{ field: 'prevOdoMeter', displayName: "Next Serv Due on - Mtr Read", headerTooltip: "Next Serv Due on - Mtr Read"},
						{ field: 'prevPlantServiceParentName', displayName: "Next Serv Due -  Category", headerTooltip: "Next Serv Due -  Category",},
						{ field: 'prevPlantServiceName', displayName: "Next Serv Due -   Sub Category", headerTooltip: "Next Serv Due -   Sub Category",},
						{ field: 'displayNamesMap.MOB_DATE', displayName: "Notes", headerTooltip: "Notes",
						cellTemplate:'<div ng-click="grid.appScope.showServiceHistory(row.entity.comments)">{{(row.entity.comments.length>0)?(row.entity.comments | limitTo: 10) + (row.entity.comments.length > 10 ? "..." : "") : ""}}</div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_Plant_ServiceHistory");
					$scope.getPlantServiceHistory();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);