'use strict';
app.controller( 'CoMaterialController',
	["$scope", "blockUI", "$rootScope", "$state", "$q", "ngDialog", "ChangeOrdersService", "GenericAlertService","MaterialClassService","ChangeOrdersFactory", function ($scope, blockUI, $rootScope, $state, $q, ngDialog, 
		ChangeOrdersService, GenericAlertService, MaterialClassService, ChangeOrdersFactory) {
			
	$scope.coMaterialRowData={
		coMaterialSelect:false,
		materialClassificationId : null,
		materialDescription : null,
		approvedMaterialHrs : 0,
		pendingMaterialHrs : 0,
		currentMaterialHrs : 0,
		cumulativeMaterialHrs : 0,
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
			console.log(data)
			coMaterialRow.materialClassificationId = data.code;
			coMaterialRow.materialDescription = data.name;
			coMaterialRow.approvedMaterialHrs=data.originalQty;
			coMaterialRow.pendingMaterialHrs=0;
			coMaterialRow.cumulativeMaterialHrs=data.originalQty;
			
		});
	},	
	$scope.addMaterialRows = function() {
		console.log("addManpowerRows function from comanpowercontroller");
		$scope.coMaterialRows.push(angular.copy($scope.coMaterialRowData));
	}	
		$scope.calculateCumulativeQty1 = function(currentdata) {
			currentdata.cumulativeMaterialHrs = Number(currentdata.approvedMaterialHrs) + Number(currentdata.pendingMaterialHrs) + Number(currentdata.currentMaterialHrs);
		}
		$scope.calculateCumulativeQty2 = function(currentdata) {
			currentdata.cumulativeMaterialHrs = Number(currentdata.approvedMaterialHrs) + Number(currentdata.pendingMaterialHrs) + Number(currentdata.currentMaterialHrs);
		}
		$scope.calculateCumulativeQty3 = function(currentdata) {
			currentdata.cumulativeMaterialHrs = Number(currentdata.approvedMaterialHrs) + Number(currentdata.pendingMaterialHrs) + Number(currentdata.currentMaterialHrs);

		}
	$scope.saveMaterial= function(){
		console.log($scope.coMaterialRows)
		var saveMaterial = {
			"changeOrderTOs": [
				{
					"id": $rootScope.selectedProject.projId
				}
			],
			"coMaterialTOs": $scope.coMaterialRows
		}
		console.log(saveMaterial)
		ChangeOrdersService.getCoMeterial(saveMaterial).then(function(data) {
			console.log(data)
			GenericAlertService.alertMessage("Change Order Meterial saved successfully", 'Info');
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}
		//----------Delete Material of Work rowRecord-----------
				$scope.deleteMateralRows = function() {
					//console.log(coMaterialSelect)
				var deleteMaterialDtlIds = [];
				var tempInternalRequest = [];
				var flag = false;
				angular.forEach($scope.coMaterialRows, function(value, key) {
					if (!value.coMaterialSelect) {
						tempInternalRequest.push(value);
					} else {
						if (value.coMaterialSelect) {
							deleteMaterialDtlIds.push(value.id);
						}
						flag = true;
					}
				});
				if (!flag) {
					GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

				}
				$scope.coMaterialRows = tempInternalRequest;
               }
			
}]);

