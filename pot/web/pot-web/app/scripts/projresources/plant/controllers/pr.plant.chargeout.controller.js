'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantchargeoutrate", {
		url : '/plantchargeoutrate',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantcharge/plantregcharge.html',
				controller : 'PlantChargeOutController'
			}
		}
	})
}]).controller("PlantChargeOutController", ["$rootScope", "$scope", "utilservice", "PlantChargeFactory", "PlantRegisterService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, utilservice, PlantChargeFactory, PlantRegisterService, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantChargeTable = true;
	$scope.plantChargeOutRatesTOs = [];
	$scope.regPlantDeploymentTO = null;
	var editCharge = [];
	$scope.projCostItemMap = [];
	$scope.projGeneralLabelKeyTO = [];
	$scope.category = [];
	$scope.userProjMap = [];
	$scope.projPlantClassMap = [];
	$scope.getPlantChargeOutRates = function() {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		if($scope.currentTab.title == 'Charge out Rates' && $rootScope.selectedPlantData.assetType == 'New Plant' && $rootScope.selectedPlantData.procurecatgCode == 'HP'){
			GenericAlertService.alertMessage("Charge out Rates is not applicable for new plants with purchase category other than as Hire Purchase", "Warning");
			return;
		}
		if (!$rootScope.selectedPlantData.projId) {
			GenericAlertService.alertMessage("Please Assign The Project And  Refresh The page To Enter ChargeOutRates", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedPlantData.projId,
			"plantId" : $rootScope.selectedPlantData.id
		};
		PlantRegisterService.getPlantChargeOutRates(req).then(function(data) {
			console.log('plant charge of rates  ', data);
			$scope.projCostItemMap = data.projectLibTO.projCostItemMap;
			$scope.projGeneralLabelKeyTO = data.projGeneralLabelKeyTO;
			$scope.category = data.category;
			$scope.plantProjectDtlTOs = data.plantProjectDtlTOs;
			$scope.plantChargeOutRatesTOs = data.plantChargeOutRatesTOs;
			console.log($scope.plantChargeOutRatesTOs, 222222);
			$scope.gridOptions.data = angular.copy($scope.plantChargeOutRatesTOs);
			$scope.userProjMap = data.projectLibTO.userProjMap;
			$scope.projPlantClassMap = data.projectLibTO.projClassMap;
			$scope.plantChargeTable = true;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting the Plant Charge Out Details", "Error");
		});
	}

//	$scope.getPlantChargeOutRates();

	$scope.chargeRowSelect = function(plantChargeOut) {
		if (plantChargeOut.select) {
			utilservice.addItemToArray(editCharge, "id", plantChargeOut);
		} else {
			editCharge.pop(plantChargeOut);
		}
	}

	$scope.addPlantChargeCode = function() {
		if (editCharge <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}

		var itemData = {
			"editCharge" : editCharge,
			"projCostItemMap" : $scope.projCostItemMap,
			"projGeneralLabelKeyTO" : $scope.projGeneralLabelKeyTO,
			"plantChargeOutRatesTOs" : $scope.plantChargeOutRatesTOs,
			"category" : $scope.category,
			"userProjMap" : $scope.userProjMap,
			"projPlantClassMap" : $scope.projPlantClassMap,
			"plantClassMstrMap" : $scope.plantClassMstrMap
		};
		var plantchargedetailspopup = PlantChargeFactory.plantChargeOnLoad(itemData);
		plantchargedetailspopup.then(function(data) {
			$scope.plantChargeTable = false;
			$scope.plantChargeOutRatesTOs = data.plantChargeOutRatesTOs;
			$scope.getPlantChargeOutRates();
			editCharge = [];
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant charge Details", 'Error');
		});

	}, $scope.showChargeOutRates = function(remarks) {
		GenericAlertService.comments(remarks);
	}

	$scope.$on("resetChargeoutRates", function() {
		$scope.plantChargeOutRatesTOs = [];
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.chargeRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', displayName: "Select", headerTooltip : "Select", cellTemplate:linkCellTemplate },
						{ field: 'plantRegProjTO.deploymentId', displayName: "Project Deployment#", headerTooltip: "Project Deployment#",},
						{ field: 'effectiveFrom', displayName: "Effective-From", headerTooltip: "Effective-From",},
						{ field: 'plantRegProjTO.parentName', displayName: "EPS Name", headerTooltip : "EPS Name",},
						{ field: 'plantRegProjTO.name', displayName: "Project Name", headerTooltip : "Project Name",},
						{ field: 'plantRegProjTO.assertId', displayName: "Asset ID", headerTooltip: "Asset ID",},
						
						{ field: 'plantRegProjTO.plantMasterName', displayName: "Project Plant ID", headerTooltip: "Project Plant ID",},
						{ field: 'selectedPlantData.plantClassMeasureName', displayName: "Fuel Cost Category", headerTooltip : "Fuel Cost Category",},
						{ field: 'projGeneralLabelKeyTO.name', displayName: "Currency", headerTooltip : "Currency",},
						{ field: 'category', displayName: "Plant Charge Out Category", headerTooltip: "Plant Charge Out Category",},
						{ field: 'rateWithFualNRShift', displayName: "Rate-Normal Shift With Fuel", headerTooltip: "Rate-Normal Shift With Fuel",},
						{ field: 'rateWithOutFualNRShift', displayName: "Rate-Normal Shift Without Fuel", headerTooltip : "Rate-Normal Shift Without Fuel", },
						{ field: 'rateWithFualDBShift', displayName: "Rate-Double Shift With Fuel", headerTooltip : "Rate-Double Shift With Fuel", },
						{ field: 'rateWithoutFualDBShift', displayName: "Rate-Double Shift Without Fuel", headerTooltip: "Rate-Double Shift Without Fuel",},
						{ field: 'idleChargeOutRate', displayName: "Idle-Charge out Rate", headerTooltip: "Idle-Charge out Rate",},
						{ field: 'projMobCostItemCode', displayName: "Mob Cost Code", headerTooltip : "Mob Cost Code",},
						
						{ field: 'mobChargeOutRate', displayName: "Mob Rate", headerTooltip : "Mob Rate", },
						{ field: 'projDeMobCostItemCode', displayName: "De-Mob Cost Code", headerTooltip: "De-Mob Cost Code",},
						{ field: 'deMobChargeOutRate', displayName: "De-Mob Rate", headerTooltip: "De-Mob Rate",},
						{ field: 'commentes', displayName: "Notes", headerTooltip : "Notes",},
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_PlantAndEquipments_ChargeOutRates");
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
			        $scope.gridOptions.exporterPdfPageSize = 'A3';
			        $scope.gridOptions.exporterPdfMaxGridWidth = 910;
					$scope.getPlantChargeOutRates();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);