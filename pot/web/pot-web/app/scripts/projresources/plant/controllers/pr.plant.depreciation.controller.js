'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantdepreciation", {
		url : '/plantdepreciation',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantdepreciation/plantregdepreciation.html',
				controller : 'PlantDepreciationController'
			}
		}
	})
}]).controller("PlantDepreciationController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PlantDepreciationFactory", "PlantRegisterService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, PlantDepreciationFactory,PlantRegisterService,GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantDepreciationTable = true;
	$scope.depreciationData = [];
	var editDepreciation = [];
	$scope.showEditButton = false;
	$scope.userProjMap = [];
	$scope.projId = null;
	$scope.projGenCurrencyLabelKeyTO =[];
	$scope.projPlantClassMap =[];
	$scope.getPlantDeprisiationRecords = function() {
		
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		
		if (!$rootScope.selectedPlantData.projId) {
			GenericAlertService.alertMessage("Please Assign The Project ", "Warning");
			return;
		} else {
			 $scope.projId = $rootScope.selectedPlantData.projId;
		}
		var getPlantDeprisiationReq = {
			"status" : 1,
			"projId" : $scope.projId,
			"plantId": $rootScope.selectedPlantData.id
		};
		PlantRegisterService.getPlantDeprisiationRecords(getPlantDeprisiationReq).then(function(data) {
			$scope.depreciationData = data.plantDepriciationSalvageTOs;
			$scope.gridOptions.data = angular.copy($scope.depreciationData);
			$scope.userProjMap = data.projectLibTO.userProjMap;
			$scope.projGenCurrencyLabelKeyTO = data.projGenCurrencyLabelKeyTO;
			$scope.projPlantClassMap = data.projectLibTO.projClassMap;
		}, function(error) {
			if (error.message != null && error.message != undefined ){
				GenericAlertService.alertMessage(error.message, error.status);
			}else {
				GenericAlertService.alertMessage("Error occured while getting Plant Depriciation Salvage Details", "Error");
			}
		});
		$scope.showEditButton = true;
	}

	$scope.depreciationRowSelect = function(depreciation) {
		if (depreciation.selected) {
			editDepreciation.push(depreciation);
		} else {
			editDepreciation.pop(depreciation);
		}
	}
	$scope.addDepreciation = function(actionType) {
		if (actionType == 'Edit' && editDepreciation <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		var plantDepreciationPopup = PlantDepreciationFactory.plantDepreciationOnLoad(actionType, {"editDepreciation":editDepreciation,
																									"userProjMap":$scope.userProjMap,
																									"projPlantClassMap":$scope.projPlantClassMap,
																									"plantClassMstrMap" : $scope.plantClassMstrMap,
																									"projGenCurrencyLabelKeyTO":$scope.projGenCurrencyLabelKeyTO});
		
		plantDepreciationPopup.then(function(data) {
			$scope.plantDepreciationTable = false;
			$scope.depreciationData = data.plantDepriciationSalvageTOs;
			$scope.userProjMap = data.userProjMap;
			$scope.projPlantClassMap = data.projPlantClassMap;
			$scope.projGenCurrencyLabelKeyTO = data.projGenCurrencyLabelKeyTO;
			editDepreciation = [];
			$scope.plantDepreciationTable = true;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching Plant Depreciation and Salvage Details", 'Error');
		});
	}

	$scope.showDepreciation = function(remarks) {
		GenericAlertService.comments(remarks);
	}

	$scope.$on("resetDepandSalValue", function() {
		$scope.depreciationData = [];
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.depreciationRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', displayName: "Select", headerTooltip : "Select", cellTemplate:linkCellTemplate },
						{ field: 'plantRegProjTO.deploymentId', displayName: "Project Deployment#", headerTooltip: "Project Deployment#",},
						{ field: 'effectiveFrom', displayName: "Effective From", headerTooltip: "Effective From",},
						{ field: 'plantRegProjTO.parentName', displayName: "EPS Name", headerTooltip : "EPS Name",},
						{ field: 'plantRegProjTO.name', displayName: "Project Name", headerTooltip : "Project Name",},
						{ field: 'plantRegProjTO.assertId', displayName: "Asset ID", headerTooltip: "Asset ID",},
						{ field: 'plantRegProjTO.commissionDate', displayName: "Commissioning Date", headerTooltip: "Commissioning Date",},
						{ field: 'plantRegProjTO.mobDate', displayName: "Mobilization Date", headerTooltip : "Mobilization Date",},
						{ field: 'plantRegProjTO.deMobDate', displayName: "De-mobilization Date", headerTooltip : "De-mobilization Date",},
						{ field: 'plantClassMeasureName', displayName: "U.O.M of Life", headerTooltip: "U.O.M of Life",},
						{ field: 'estimateLife', displayName: "Estemated Full Life", headerTooltip: "Estemated Full Life",},
						{ field: 'endMeterReading', displayName: "Upto Date Usage", headerTooltip : "Upto Date Usage", },
						{ field: 'remainingLife', displayName: "Remaining Life", headerTooltip : "Remaining Life", },
						{ field: 'code', displayName: "Currency For Asst Value", headerTooltip: "Currency For Asst Value",},
						{ field: 'orginalValue', displayName: "Original Value", headerTooltip: "Original value",},
						{ field: 'salvageValue', displayName: "Salvage Value", headerTooltip : "Salvage Value",},
						{ field: 'depriciationValue', displayName: "Depreciation Value", headerTooltip: "Depreciation Value",},
						{ field: 'remaining', displayName: "Remaining Value", headerTooltip: "Remaining value",},
						{ field: 'comments', displayName: "Salvage Value", headerTooltip : "Salvage Value",
						cellTemplate:'<div ng-click="grid.appScope.showDepreciation(row.entity.comments)"></div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_PlantAndEquipments_DeployementHistory");
					$scope.getPlantDeprisiationRecords();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);