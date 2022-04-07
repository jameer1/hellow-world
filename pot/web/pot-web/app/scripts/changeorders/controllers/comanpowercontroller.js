'use strict';
app.controller( 'CoManpowerController',
	["$scope", "blockUI", "EpsProjectSelectFactory", "$rootScope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "ChangeOrdersService", "GenericAlertService","PreContractProjEmpClassFactory","ProjectBudgetService", function ($scope, blockUI, EpsProjectSelectFactory, $rootScope, $state, $q, ngDialog, EpsProjectMultiSelectFactory,
		ChangeOrdersService, GenericAlertService, PreContractProjEmpClassFactory,ProjectBudgetService) {
			
	$scope.coManpowerRowData={
		manpowerClassificationId : null,
		manpowerDescription : null,
		approvedManpowerHrs : 0,
		pendingManpowerHrs : 0,
		currentManpowerHrs : 0,
		cumulativeManpowerHrs : 0,
		notes : null,
		projId : null
	};
	console.log($rootScope.selectedProject);
	$scope.coManpowerRows = [];
	//$scope.projManpowerData = [];
	var projManpowerPopupService = {};
	$scope.selectedProject = $rootScope.selectedProject.projId;
	$scope.itemTypeData = [{name:'Existing Item',value:'existingitem'},{name:'New Item',value:'newitem'}];
				
	$scope.coChangeManpowerItemType = function(option,row) {
		console.log(row);
		console.log(option);
		option.itemType = row.name;		
	},
	$scope.coManpowerClassification = function(coManpowerData) {
		console.log(coManpowerData);
		if( coManpowerData.itemType == 'Existing Item' )
		{
			var coProjManpowerPopupDetails = $scope.getProjManpowerPopup(coManpowerData);
			coProjManpowerPopupDetails.then(function(data){
				console.log(data);
				coManpowerData.manpowerClassificationId = data.empClassTO.code;
				coManpowerData.manpowerDescription = data.empClassTO.name;				
				coManpowerData.currentManpowerHrs = data.originalQty;
				console.log(coManpowerData);
			});
		}
		else
		{
			$scope.projEmpClassDetails(coManpowerData);
		}
	},
	$scope.addManpowerRows = function() {
		console.log("addManpowerRows function from comanpowercontroller");
		$scope.coManpowerRows.push(angular.copy($scope.coManpowerRowData));
	},
	$scope.getProjManpowerDetails = function() {
		console.log($scope.coSearchCriteria);
		/*ChangeOrdersService.fetchChangeOrderDetails(co_request).then(function(data){
			console.log(data);
		});*/
	}, 
	// This is to display the manpower category list in case of new item is selected
	$scope.projEmpClassDetails = function (coManpowerRow) {
		var projEmpClassDetailsPopup = PreContractProjEmpClassFactory.getProjEmpClasses();
		projEmpClassDetailsPopup.then(function (data) {
			/*projEmpClassLabelKeyTO.id = data.projEmpclassTO.id;
			projEmpClassLabelKeyTO.code = data.projEmpclassTO.code;
			projEmpClassLabelKeyTO.name = data.projEmpclassTO.name;*/
			coManpowerRow.manpowerClassificationId = data.projEmpclassTO.code;
			coManpowerRow.manpowerDescription = data.projEmpclassTO.name;
			coManpowerRow.empClassTO = data.projEmpclassTO;
			coManpowerRow.measureId = data.projEmpclassTO.measureUnitTO.id;
			console.log(data);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting employee details", 'Error');
		});
	},
	//This function is to save the manpower details
	$scope.saveCoManpowerDetails = function() {
		console.log("saveCoManpowerDetails function");
		console.log($scope.coManpowerRows);
		var co_manpower_request = {
			"coProjManpowerTOs" : []
		}
		angular.forEach($scope.coManpowerRows,function(value,key){
			value.projId = $scope.selectedProject;
			co_manpower_request.coProjManpowerTOs.push(value);
		});
		console.log(co_manpower_request);
		// calls service to save the change order manpower details
		ChangeOrdersService.saveChangeOrderManpowerDetails(co_manpower_request).then(function(data) {
			console.log(data);
			GenericAlertService.alertMessage("Manpower details saved successfully", 'Info');
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}	
}]);

