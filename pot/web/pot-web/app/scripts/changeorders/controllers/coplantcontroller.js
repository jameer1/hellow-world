'use strict';
app.controller( 'CoPlantController',
	["$scope", "blockUI", "$rootScope", "$state", "$q", "ngDialog", "ChangeOrdersService", "GenericAlertService","ProjectBudgetService","PreContractProjPlantClassFactory","ChangeOrdersFactory", function ($scope, blockUI, $rootScope, $state, $q, ngDialog, 
		ChangeOrdersService, GenericAlertService, ProjectBudgetService, PreContractProjPlantClassFactory, ChangeOrdersFactory ) {
			
	$scope.coPlantRowData={
		coPlantSelect:false,
		plantlClassificationId : null,
		plantDescription : null,
		approvedPlantHrs : 0,
		pendingPlantHrs : 0,
		currentPlantHrs : 0,
		cumulativePlantHrs : 0,
		notes : null,
		projId : null
	};
	console.log($rootScope.selectedProject);
	$scope.coPlantRows = [];
	$scope.coPlantItemSelected = null;
	//$scope.projManpowerData = [];
	var projPlantPopupService = {};
	$scope.selectedProject = $rootScope.selectedProject.projId;
	$scope.plantItemList = [{name:'Existing Item',value:'existingitem'},{name:'New Item',value:'newitem'}];
	
	$scope.coChangePlantItemType = function(option,row) {
		console.log(row);
		console.log(option);
		option.itemType = row.name;		
	},
	$scope.addCoPlantRows = function() {		
		$scope.coPlantRows.push(angular.copy($scope.coPlantRowData));
	},
	// function which displays the plant items from central library in case of new item option selected
	$scope.selectCoPlantClassification = function(coPlantRow) {
		let projId = $scope.selectedProject;
		var projPlantClassDetailsPopup = PreContractProjPlantClassFactory.getProjPlantClasses(projId);
		projPlantClassDetailsPopup.then(function (data) {
			coPlantRow.plantlClassificationId = data.projPlantClassTO.code; 
			coPlantRow.plantDescription = data.projPlantClassTO.name;						
			console.log(data);
		}, function (error) {
			GenericAlertService.alertMessage('Error occurred while selecting plant details', "Error");
		});
	},
	$scope.coProjPlantClassification = function(coPlantRow) {
		console.log(coPlantRow);
		var projPlantPopup = ChangeOrdersFactory.getProjBudgetPopup(coPlantRow,'PLANT','EXISTING');
		projPlantPopup.then(function(data){
			console.log(data)
			coPlantRow.plantlClassificationId = data.plantClassTO.code;
			coPlantRow.plantDescription = data.plantClassTO.name;
			coPlantRow.approvedPlantHrs=data.originalQty;
			coPlantRow.pendingPlantHrs=0;
			coPlantRow.cumulativePlantHrs=data.originalQty;
		});
	}
	
		$scope.calculateCumulativeQty1 = function(currentdata) {
			currentdata.cumulativePlantHrs = Number(currentdata.currentPlantHrs) + Number(currentdata.approvedPlantHrs) + Number(currentdata.pendingPlantHrs);
		}
		$scope.calculateCumulativeQty2 = function(currentdata) {
			currentdata.cumulativePlantHrs = Number(currentdata.currentPlantHrs) + Number(currentdata.approvedPlantHrs) + Number(currentdata.pendingPlantHrs);
		}
		$scope.calculateCumulativeQty3 = function(currentdata) {
			currentdata.cumulativePlantHrs = Number(currentdata.currentPlantHrs) + Number(currentdata.approvedPlantHrs) + Number(currentdata.pendingPlantHrs);
		}
			
	$scope.saveCoPlantDetails = function() {
		var co_plant_request = {
			"changeOrderTOs": [
				{
					"id": $scope.selectedProject
				}
			],
			"coProjPlantsTOs" : []
		}
		angular.forEach($scope.coPlantRows,function(value,key){
			value.projId = $scope.selectedProject;
			co_plant_request.coProjPlantsTOs.push(value);
		});
		console.log(co_plant_request);
		// calls service to save the change order manpower details
		ChangeOrdersService.saveChangeOrderPlantDetails(co_plant_request).then(function(data) {
			console.log(data);
			GenericAlertService.alertMessage("Plant details saved successfully", 'Info');
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}
		//-----------Delete Scope of Work rowRecord------------------
		$scope.deletePlantRows = function() {
			var deletePlantDtlIds = [];
			var tempInternalRequest = [];
			var flag = false;
			angular.forEach($scope.coPlantRows, function(value, key) {
				if (!value.coPlantSelect) {
					tempInternalRequest.push(value);
				} else {
					if (value.coPlantSelect) {
						deletePlantDtlIds.push(value.id);
					}
					flag = true;
				}
			});
			if (!flag) {
				GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

			}
			$scope.coPlantRows = tempInternalRequest;
		}
	}]);

