'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantutilization", {
		url : '/plantutilization',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantutilization/plantregutilization.html',
				controller : 'PlantUtilizationController'
			}
		}
	})
}]).controller("PlantUtilizationController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "PlantUtilizationFactory", "PlantRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService,PlantUtilizationFactory,PlantRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.labelKeyTOs = [];
	
	$scope.getPlantUtilisationRecords = function() {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		if ($rootScope.selectedPlantData.projId == null || $rootScope.selectedPlantData.projId == undefined) {
			GenericAlertService.alertMessage("Selected plant is not assigned to any project", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedPlantData.projId,
			"plantId" : $rootScope.selectedPlantData.id
		};
		PlantRegisterService.getPlantUtilaizationRecords(req).then(function(data) {
			$scope.labelKeyTOs = data.labelKeyTOs;
			consolee.log($scope.labelKeyTOs, 1111);
			/*$scope.gridOptions.data = angular.copy($scope.labelKeyTOs);*/
			$scope.userProjMap = data.userProjMap;
			$scope.gridOptions.data = angular.copy($scope.userProjMap);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting plant utilization Records", "Error");
		});
	}, 
	$scope.getPlantUtilisationRecords();

	$scope.showUtilization = function(remarks) {
		ngDialog.open({
			template : 'views/projresources/projplantreg/plantutilization/utilizationcommentspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			controller : [ '$scope', function($scope) {
				$scope.notes = remarks;
			} ]
		});
	}
	
	$scope.$on("resetUtilizationRecords", function() {
		$scope.labelKeyTOs = [];
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { field: 'displayNamesMap.DEPLOYMENT_ID', displayName: "Project Deployment#", headerTooltip : "Project Deployment#" },
						{ field: 'displayNamesMap.ParentName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'displayNamesMap.projectName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'assertId', displayName: "Asset ID", headerTooltip: "Asset ID"},
						{ field: 'selectedPlantData.procurecatgName', displayName: "Project plant ID", headerTooltip: "Project plant ID",},
						{ field: 'selectedPlantData.plantClassMeasureName', displayName: "Unit of Measure", headerTooltip: "Unit of Measure",},
						
						{ field: 'displayNamesMap.MOB_DATE', displayName: "Mobilization Date", headerTooltip: "Mobilization Date"},
						{ field: 'displayNamesMap.DEMOB_DATE', displayName: "De-mobilization Date", headerTooltip: "De-mobilization Date", },
						{ field: 'displayNamesMap.USED_TIME', displayName: "Proj Wise- Used Units", headerTooltip: "Proj Wise- Used Units"},
						{ field: 'displayNamesMap.IDLE_TIME', displayName: "Proj Wise-Idle Units", headerTooltip: "Proj Wise-Idle Units",},
						{ field: 'displayNamesMap.USED_TOTAL_TIME', displayName: "Cuml  Used Units", headerTooltip: "Cuml  Used Units",},
						
						{ field: 'displayNamesMap.IDLE_TOTAL_TIME', displayName: " Cuml - Idle Units", headerTooltip: " Cuml - Idle Units"},
						{ field: 'displayNamesMap.MOB_ODO_METER', displayName: " Meter Read on Mob", headerTooltip: " Meter Read on Mob", },
						{ field: 'displayNamesMap.DEMOB_ODO_METER', displayName: "Meter Read on De-Mob", headerTooltip: "Meter Read on De-Mob"},
						{ field: 'displayNamesMap.DEMOB_ODO_METER-displayNamesMap.MOB_ODO_METER', displayName: "Project wise Units of Usage-as Per Meter", headerTooltip: "Project wise Units of Usage-as Per Meter",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_Plant_UtilisationRecords");
					$scope.getPlantUtilisationRecords();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);