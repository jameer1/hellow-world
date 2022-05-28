'use strict';
app.controller( 'CoManpowerController',
	["$scope", "blockUI", "EpsProjectSelectFactory", "$rootScope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "ChangeOrdersService", "GenericAlertService","PreContractProjEmpClassFactory","ProjectBudgetService","ChangeOrdersFactory", function ($scope, blockUI, EpsProjectSelectFactory, $rootScope, $state, $q, ngDialog, EpsProjectMultiSelectFactory,
		ChangeOrdersService, GenericAlertService, PreContractProjEmpClassFactory,ProjectBudgetService,ChangeOrdersFactory) {
			
	$scope.coManpowerRowData={
		manpowerClassificationId : null,
		manpowerDescription : null,
		approvedManpowerHrs : 0,
		pendingManpowerHrs : 0,
		currentManpowerHrs : 0,
		cumulativeManpowerHrs : 0,
		notes : null,
		projId : null,
		coManpowerSelect:false
	};
	var deletePlantDtlIds = [];
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
			
		let itemType = coManpowerData.coitemtype.name.indexOf('Existing Item') > -1 ? 'EXISTING' : 'NEW';
		var projMaterialPopup = ChangeOrdersFactory.getProjBudgetPopup(coManpowerData,'MANPOWER',itemType);
		projMaterialPopup.then(function(data){
			console.log(data)
			coManpowerData.manpowerClassificationId = data.empClassTO.code;
				coManpowerData.manpowerDescription = data.empClassTO.name;	
			   // var revisedQty1=(data.revisedQty ? data.revisedQty : data.originalQty) - data.actualQty;			
				coManpowerData.approvedManpowerHrs =data.originalQty;
				//coManpowerData.pendingManpowerHrs=is it is pending then auto fill with "manpowerClassificationId"
				coManpowerData.cumulativeManpowerHrs=data.originalQty+ data.revisedQty 
		  });
			
			/*var coProjManpowerPopupDetails = $scope.getProjManpowerPopup(coManpowerData);
			coProjManpowerPopupDetails.then(function(data){
				console.log(data);
				coManpowerData.manpowerClassificationId = data.empClassTO.code;
				coManpowerData.manpowerDescription = data.empClassTO.name;				
				coManpowerData.currentManpowerHrs = data.originalQty;
				console.log(coManpowerData);
			});*/
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
		$scope.calculateCumulativeQty1 = function(currentdata) {
				currentdata.cumulativeManpowerHrs = Number(currentdata.currentManpowerHrs) + Number(currentdata.approvedManpowerHrs) + Number(currentdata.pendingManpowerHrs);
			}
		$scope.calculateCumulativeQty2 = function(currentdata) {
				currentdata.cumulativeManpowerHrs = Number(currentdata.currentManpowerHrs) + Number(currentdata.approvedManpowerHrs) + Number(currentdata.pendingManpowerHrs);
			}
		$scope.calculateCumulativeQty3 = function(currentdata) {
				currentdata.cumulativeManpowerHrs = Number(currentdata.currentManpowerHrs) + Number(currentdata.approvedManpowerHrs) + Number(currentdata.pendingManpowerHrs);
				
			},
			
			$scope.deleteManpowerRows = function() {
				//deletePlantDtlIds = [];
				var tempInternalRequest = [];
				var flag = false;
				angular.forEach($scope.coManpowerRows, function(value, key) {
					console.log(value)
					if (!value.coManpowerSelect) {
						tempInternalRequest.push(value);
					} else {
						if (value.coManpowerSelect) {
							deletePlantDtlIds.push(value.id);
						}
						flag = true;
					}
				});
				if (!flag) {
					GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

				}
				$scope.coManpowerRows = tempInternalRequest;
               },
	//This function is to save the manpower details
	$scope.saveCoManpowerDetails = function() {
			if ($scope.coManpowerRows.length <= 0) {
				GenericAlertService.alertMessage("Please select project details to save the record", 'Warning');
				return;
			}
		console.log($rootScope.selectedEmployeeData);
		console.log("saveCoManpowerDetails function");
		console.log($scope.coManpowerRows);
		$scope.id=$rootScope.selectedEmployeeId;
		var co_manpower_request = {
			"changeOrderTOs": [
				{
					"id": $scope.id
				}
			],
			"coProjManpowerTOs" : []
		}
		console.log(co_manpower_request)
		angular.forEach($scope.coManpowerRows,function(value,key){
			//value.projId = $scope.selectedProject;
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

