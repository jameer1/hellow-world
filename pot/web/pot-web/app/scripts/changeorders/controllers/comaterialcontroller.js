'use strict';
app.controller( 'CoMaterialController',
	["$scope", "blockUI", "$rootScope", "$state", "$q", "ngDialog", "ChangeOrdersService", "GenericAlertService","MaterialClassService","ChangeOrdersFactory", function ($scope, blockUI, $rootScope, $state, $q, ngDialog, 
		ChangeOrdersService, GenericAlertService, MaterialClassService, ChangeOrdersFactory) {
			
	$scope.coMaterialRowData={
		materialClassificationId : null,
		materialDescription : null,
		approvedManpowerHrs : 0,
		pendingManpowerHrs : 0,
		currentManpowerHrs : 0,
		cumulativeManpowerHrs : 0,
		notes : null,
		projId : null
	};
	console.log($rootScope.selectedProject);
	$scope.coMaterialRows = [];
	//$scope.projManpowerData = [];
	var projManpowerPopupService = {};
	$scope.selectedProject = $rootScope.selectedProject.projId;
	$scope.materialItemType = [{name:'Existing Item',value:'existingitem'},{name:'New Item',value:'newitem'}];
				
	$scope.coChangeMaterialItemType = function(option,row) {
		console.log(row);
		console.log(option);
		option.itemtype = row.name;	
	},	
	$scope.coMaterialClassification = function(coMaterialRow) {
		console.log(coMaterialRow);		
		let itemType = coMaterialRow.itemType.name.indexOf('Existing Item') > -1 ? 'EXISTING' : 'NEW';
		var projMaterialPopup = ChangeOrdersFactory.getProjBudgetPopup(coMaterialRow,'MATERIAL',itemType);
		projMaterialPopup.then(function(data){
			coMaterialRow.materialClassificationId = data.code;
			coMaterialRow.materialDescription = data.name;
			console.log(data);
		});
	},	
	$scope.addMaterialRows = function() {
		console.log("addManpowerRows function from comanpowercontroller");
		$scope.coMaterialRows.push(angular.copy($scope.coMaterialRowData));
	}			
}]);

