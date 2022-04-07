'use strict';
app.controller( 'CoCostController',
	["$scope", "blockUI", "$rootScope", "$state", "$q", "ngDialog", "ChangeOrdersService", "GenericAlertService","ProjectBudgetService","PreContractProjPlantClassFactory","ChangeOrdersFactory","WorkDairyCostCodeFactory","ProjCostCodeService","PreContractCostPopupFactory", function ($scope, blockUI, $rootScope, $state, $q, ngDialog, 
		ChangeOrdersService, GenericAlertService, ProjectBudgetService, PreContractProjPlantClassFactory, ChangeOrdersFactory, WorkDairyCostCodeFactory, ProjCostCodeService, PreContractCostPopupFactory ) {
			
	$scope.coCostCodeRowData = {
		coCostCodeId : null,
		coCostCodeCode : null,
		coCostCodeDescription : null,
		approvedLabourCost : 0,
		approvedMaterialsCost : 0,
		approvedPlantCost : 0,
		approvedOtherCost : 0,
		approvedTotalCost : 0,
		pendingLabourCost : 0,
		pendingMaterialsCost : 0,
		pendingPlantCost : 0,
		pendingOtherCost : 0,
		pendingTotalCost : 0,
		currentLabourCost : 0,
		currentMaterialsCost : 0,
		currentPlantCost : 0,
		currentOtherCost : 0,
		currentTotalCost : 0,
		cumulativeLabourCost : 0,
		cumulativeMaterialsCost : 0,
		cumulativePlantCost : 0,
		cumulativeOtherCost : 0,
		cumulativeTotalCost : 0,
		notes : null,
		projId : null,
		itemType : null
	};
	console.log($rootScope.selectedProject);
	$scope.coCostCodeRows = [];
	$scope.coCostCodeSelected = null;
	
	$scope.selectedProject = $rootScope.selectedProject.projId;
	console.log($scope.selectedProject);
	$scope.costCodeItemList = [{name:'Existing Item',value:'existingitem'},{name:'New Item',value:'newitem'}];
	
	$scope.addCoCostCodeRows = function() {
		console.log("addCoCostCodeRows function");
		$scope.coCostCodeRows.push(angular.copy($scope.coCostCodeRowData));
		console.log($scope.coCostCodeRows);
	}
	$scope.coChangeCostCodeItemType = function(row,option) {
		console.log(row);
		console.log(option);
		option.itemType = row.name;	
	},	
	// function which displays the plant items from central library in case of new item option selected
	$scope.selectCoCostCodeClassification = function(coCostCodeRow) {
		console.log(coCostCodeRow);
		/*if( coCostCodeRow.itemType == "New Item" )
		{
			$scope.coProjLibraryCostClassificationList();
		}
		else
		{
			$scope.coProjBudgetCostClassificationList();
		}*/
		$scope.coProjLibraryCostClassificationList();
	},
	$scope.coProjLibraryCostClassificationList = function(coCostCodeRow) {
		var projCostCodeDetails = PreContractCostPopupFactory.getProjCostDetails($scope.selectedProject,"Plants");
		projCostCodeDetails.then(function(data){
			console.log(data);
			coCostCodeRow.coCostCodeCode = data.code;
			coCostCodeRow.coCostCodeDescription = data.name;
			coCostCodeRow.coCostCodeId = data.id;
		});
	},
	$scope.coProjBudgetCostClassificationList = function() {
		//var deferred = $q.defer();
		var costReq = {
			"status" : 1,
			"projId" : $scope.selectedProject
		};

		var populateData = function(data, level, projects) {
			return TreeService.populateTreeData(data, level, projects, 'code', 'projCostCodeItemTOs');
		}	

		ProjCostCodeService.getCostDetails(costReq).then(function(data) {
			console.log(data);
			var wokDairyCostPopup = WorkDairyCostCodeFactory.opeCostCodePopup(populateData(data.projCostItemTOs, 0, []), workDairyCostCodeList, projId, crewId, workDairyId);
			wokDairyCostPopup.then(function(data) {
				console.log(data);
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
		});
		//return deferred.promise;
	},
	$scope.saveCoCostCodeDetails = function() {
		console.log($scope.coCostCodeRows.length);
	}
}]);

