'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetcostvaluestatus", {
		url : '/assetcostvaluestatus',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/assetcostandvluestatus/assetcostvaluestatus.html',
				controller : 'AssetCostValueStatusController'
			}
		}
	})
}]).controller("AssetCostValueStatusController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "AssestCostValueStatusFactory", "AssetRegisterService", "assetidservice","EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,GenericAlertService,AssestCostValueStatusFactory,AssetRegisterService,assetidservice,EmpRegisterService, stylesService, ngGridService) {
	
	var selectedAssetCostValueStatusData = [];
	$scope.stylesSvc = stylesService;
	$scope.fixedAssetid = assetidservice.fixedAssetId;
	$scope.fixedAssetAqulisitionRecordsDtlTOs = [];
	var purchaseRecords = []; 
	var assetLifeStatusRecords = []; 

	$scope.getAssetCostValueStatus = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var assetLifeStatusGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getAssetCostValueStatus(assetLifeStatusGetReq).then(function(data) {
			$scope.assetCostValueStatusDtlTOs = data.assetCostValueStatusDtlTOs;
			console.log($scope.assetCostValueStatusDtlTOs, 'Hi......');
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Asset Cost Value details", "Error");
		});
	},

	$scope.assetCostLifeStatusRowSelect = function(assetcostvaluestatus) {
		if (assetcostvaluestatus.selected) {
			selectedAssetCostValueStatusData.push(assetcostvaluestatus);
		} else {
			selectedAssetCostValueStatusData.splice(selectedAssetCostValueStatusData.indexOf(assetcostvaluestatus), 1);
		}
	},
	
	$scope.deleteAssetCostValueStatus = function() {
		if (selectedAssetCostValueStatusData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var assetCostValueStatusIds = [];
		if ($scope.selectedAll) {
			$scope.selectedAssetCostValueStatusData = [];
		} else {
			angular.forEach(selectedAssetCostValueStatusData, function(value, key) {
				if (value.id) {
					assetCostValueStatusIds.push(value.id);
				}
			});
			var deactivateAssetCostValueStatusReq = {
					"assetCostValueStatusIds" : assetCostValueStatusIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.assetCostValueStatusDeactivate(deactivateAssetCostValueStatusReq).then(function(data) {
					GenericAlertService.alertMessageModal('Asset Cost Value Status(s) deactivated successfully', 'Info');
					$scope.getAssetCostValueStatus();
					selectedAssetCostValueStatusData = [];
				       });				
				angular.forEach(selectedAssetCostValueStatusData, function(value, key) {
					$scope.selectedAssetCostValueStatusData.splice($scope.selectedAssetCostValueStatusData.indexOf(value), 1);
					selectedAssetCostValueStatusData = [];
					$scope.assetCostValueStatusIds = [];					
				}, function(error) {
					GenericAlertService.alertMessage('Asset Cost Value Status(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedAssetCostValueStatusData, function(value) {
					value.selected = false;
				})
			})

		}
	},
	
	
	$scope.downloadAssetCostValueStatusFile = function(assetCostValueStatusId,file_name) {
		//AssetRegisterService.downloadAssetCostValueStatusFile(assetCostValueStatusId); // this line of code is to download file from AWS
		let req = {
			"recordId" : assetCostValueStatusId,
			"category" : "Asset Cost and Value Status",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadAssetCostValueStatusFile");
	},
	
	$scope.purchaseRecords = function(item){
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var materialsOnLoadReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid
		};	
		AssetRegisterService.getPurchaseRecordOnLoad(materialsOnLoadReq).then(function(data) {
			$scope.fixedAssetAqulisitionRecordsDtlTOs = data.fixedAssetAqulisitionRecordsDtlTOs;		
			purchaseRecords.push(angular.copy($scope.fixedAssetAqulisitionRecordsDtlTOs[$scope.fixedAssetAqulisitionRecordsDtlTOs.length-1]));
				$scope.gridOptions.data = angular.copy($scope.assetCostValueStatusDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Purchase details", "Error");
		});
		
	},
	
	$scope.assetLifeStatusRecords = function(){
		if (!$scope.fixedAssetid) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var assetLifeStatusGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getAssetLifeStatus(assetLifeStatusGetReq).then(function(data) {
			$scope.assetLifeStatusDtlTOs = data.assetLifeStatusDtlTOs;
			assetLifeStatusRecords.push(angular.copy($scope.assetLifeStatusDtlTOs[$scope.assetLifeStatusDtlTOs.length-1]));
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Asset Life Status details", "Error");
		});
	}
	
		
   $scope.addAssetCostValueStatus = function(actionType) {
		
	   if (!$scope.fixedAssetid) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}		
		angular.forEach(selectedAssetCostValueStatusData, function(value, key) {
			value.selected=false;
		});		
		if(selectedAssetCostValueStatusData.length >0 && actionType=="Add"){
			selectedAssetCostValueStatusData=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
	
		if (actionType == 'Edit' && selectedAssetCostValueStatusData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}		
		
			var assestCostValueStatusPopUp = AssestCostValueStatusFactory.assestCostValueStatusPopUp(actionType, selectedAssetCostValueStatusData,purchaseRecords,assetLifeStatusRecords);
			assestCostValueStatusPopUp.then(function(data) {				
				$scope.assetCostValueStatusDtlTOs = data.assetCostValueStatusDtlTOs;
				selectedAssetCostValueStatusData=[];
				$scope.getAssetCostValueStatus();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Asset Cost Value details", 'Error');
			});		
	}
	
	$scope.$on("resetAssestCostValueStatus", function() {
		$scope.assetCostValueStatusTOs = [];
	});
	$rootScope.$on('resetassestcostvaluestatus', function (event) {
		
		if($rootScope.fixedAssetId){
			$scope.getAssetCostLifeStatus();	
		}
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.assetCostLifeStatusRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'effectiveDate', displayName: "Effective Date(Start date of Financial Year)", headerTooltip: "Effective Date(Start date of Financial Year)"},
						{ field: 'currency', displayName: "Currency", headerTooltip: "Currency", },
						{ field: 'landCost', displayName: "Land Cost", headerTooltip: "Land Cost"},
						{ field: 'structureCost', displayName: "Structure Cost", headerTooltip: "Structure Cost",},
						{ field: 'plantEquipmentCost', displayName: "Plant & Equipment Cost", headerTooltip: "Plant & Equipment Cost",},
						{ field: 'totalCost', displayName: "Total Cost of Asset", headerTooltip: "Total Cost of Asset",},
						{ field: 'assetMarket', displayName: "Asset Market Value", headerTooltip: "Asset Market Value"},
						{ field: 'structureScrap', displayName: "Structure Scrap Value", headerTooltip: "Structure Scrap Value",},
						{ field: 'equipmentScrapValue', displayName: "Plant & Equipment Scrap Value", headerTooltip: "Plant & Equipment Scrap Value"},
						{ field: 'cummStructure', displayName: "Cumulative Depreciation value for Structure", headerTooltip: "Cumulative Depreciation value for Structure"},
						{ field: 'cummPlant', displayName: "Cumulative Depreciation value for Plant", headerTooltip: "Cumulative Depreciation value for Plant", },
						{ field: 'cummAsset', displayName: "Cumulative Depreciation for Asset", headerTooltip: "Cumulative Depreciation for Asset"},
						{ field: 'yearlyStructure', displayName: "Yearly Depreciation for Structure", headerTooltip: "Yearly Depreciation for Structure",},
						{ field: 'yearlyPlant', displayName: "Yearly Depreciation for Plant", headerTooltip: "Yearly Depreciation for Plant",},
						{ field: 'yearlyTotalAmount', displayName: "Yearly Total Depreciation amount", headerTooltip: "Yearly Total Depreciation amount",},
						{ field: 'remainAssetCost', displayName: "Remaining - Asset Cost", headerTooltip: "Remaining - Asset Cost",},
						{ name: 'Assignment Records', displayName: "Valuation & Depreciation Records", headerTooltip: "Valuation & Depreciation Records", cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate:'<div ng-click="grid.appScope.downloadAssetCostValueStatusFile(row.entity.id, row.entity.assetCostValueDocumentFileName)" class="fa fa-download">{{assetcost.assetCostValueDocumentFileName}}</div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_AssetCost");
				}
			});
}]);