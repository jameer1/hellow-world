'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetLifeStatus", {
		url : '/assetLifeStatus',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/assetlifestatus/assetlifestatus.html',
				controller : 'AssetLifeStatusController'
			}
		}
	})
}]).controller("AssetLifeStatusController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "AssestLifeStatusFactory", "AssetRegisterService", "assetidservice","EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,GenericAlertService,AssestLifeStatusFactory,AssetRegisterService,assetidservice,EmpRegisterService, stylesService, ngGridService) {
	
	$scope.stylesSvc = stylesService;
	var selectedAssetLifeStatus = [];
	var assetLifeStatusRecords = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;
	
    	 $scope.getAssetLifeStatus = function() {
 			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
 				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
 				return;
 			}
 			var assetLifeStatusGetReq = {
 				"status" : 1,
 				"fixedAssetid" : $scope.fixedAssetid,
 			};
 	
 			AssetRegisterService.getAssetLifeStatus(assetLifeStatusGetReq).then(function(data) {
 				$scope.assetLifeStatusDtlTOs = data.assetLifeStatusDtlTOs;
 				$scope.gridOptions.data = angular.copy($scope.assetLifeStatusDtlTOs);
 			}, function(error) {
 				GenericAlertService.alertMessage("Error occured while getting Asset Life Status details", "Error");
 			});
 		},
 		
 		$scope.assetLifeStatusRecords = function() {
 			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
 				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
 				return;
 			}
 			var assetLifeStatusGetReq = {
 				"status" : 1,
 				"fixedAssetid" : $scope.fixedAssetid,
 			};
 	
 			AssetRegisterService.getAssetLifeStatus(assetLifeStatusGetReq).then(function(data) {
 				$scope.assetLifeStatusDtlTOs = data.assetLifeStatusDtlTOs;
 				$scope.gridOptions.data = angular.copy($scope.assetLifeStatusDtlTOs);
 				assetLifeStatusRecords.push(angular.copy($scope.assetLifeStatusDtlTOs[$scope.assetLifeStatusDtlTOs.length-1])); 
 				
 			}, function(error) {
 				GenericAlertService.alertMessage("Error occured while getting Asset Life Status details", "Error");
 			});
 		},

	$scope.lifestatusRowSelect = function(assetLifeStatus) {
 			if (assetLifeStatus.selected) {
 				selectedAssetLifeStatus.push(assetLifeStatus);
 			} else {
 				selectedAssetLifeStatus.splice(selectedAssetLifeStatus.indexOf(assetLifeStatus), 1);
 			}
	},
	
	$scope.deleteAssetLifeStatus = function() {
		if (selectedAssetLifeStatus.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedAssetLifeStatus = [];
		} else {
			angular.forEach(selectedAssetLifeStatus, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateAssetLifeStatusReq = {
					"assetLifeStatusIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.deleteAssetLifeStatus(deactivateAssetLifeStatusReq).then(function(data) {
					GenericAlertService.alertMessageModal('Asset Life Status(s) deactivated successfully', 'Info');
					$scope.getAssetLifeStatus();
					selectedAssetLifeStatus = [];
				       });
				angular.forEach(selectedAssetLifeStatus, function(value, key) {
					$scope.selectedAssetLifeStatus.splice($scope.selectedAssetLifeStatus.indexOf(value), 1);
					selectedAssetLifeStatus = [];
					$scope.deactivateIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Asset Life Status(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedAssetLifeStatus, function(value) {
					value.selected = false;
				})
			})

		}
	},
	
	$scope.downloadAssetLifeStatusFile = function(assetLifeStatusId,file_name) {
		//AssetRegisterService.downloadAssetLifeStatusFile(assetLifeStatusId);
		let req = {
			"recordId" : assetLifeStatusId,
			"category" : "Asset Life Status",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadRentalHistoryFile");
	},
	
	$scope.createLifeStatus = function(actionType) {			
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}			
			angular.forEach(selectedAssetLifeStatus, function(value, key) {
				value.selected=false;
			});
			if(selectedAssetLifeStatus.length >0 && actionType=="Add"){
				selectedAssetLifeStatus=[];
				GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
			return;
			}
			if (actionType == 'Edit' && selectedAssetLifeStatus.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
				return;
			}
				var assetStatusPopUp = AssestLifeStatusFactory.assetStatusPopUp(actionType, selectedAssetLifeStatus,assetLifeStatusRecords);			
				assetStatusPopUp.then(function(data) {
					$scope.assetLifeStatusDtlTOs = data.assetLifeStatusDtlTOs;
				
					selectedAssetLifeStatus=[];
					$scope.getAssetLifeStatus();
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching Asset Life Status details", 'Error');
				});		
		},
		
		$scope.$on("resetAssetLifeStatus", function() {
			$scope.assetLifeStatusDtlTOs = [];
		});
		$rootScope.$on('resetAssetLifeStatus', function (event) {
			
			if($scope.fixedAssetid){
				$scope.getAssetLifeStatus();	
			}
		});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.lifestatusRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'effectiveDate', displayName: "Effective Date", headerTooltip: "Effective Date"},
						{ field: 'buildStructure', displayName: "Year Build for Structure", headerTooltip: "Year Build for Structure", },
						{ field: 'plantCommissioningDate', displayName: "Plant Commissioning Date", headerTooltip: "Plant Commissioning Date"},
						{ field: 'structureTotal', displayName: "Structure Total Use full Life", headerTooltip: "Structure Total Use full Life",},
						{ field: 'plantEquipmentTotal', displayName: "Plant & Equipment Total use full Life", headerTooltip: "Plant & Equipment Total use full Life",},
						{ field: 'ageStructure', displayName: "Age for Structure", headerTooltip: "Age for Structure",},
						{ field: 'ageEquipment', displayName: "Age for Plant & Equipment", headerTooltip: "Age for Plant & Equipment"},
						{ field: 'remainingStruture', displayName: "Remaining Use full Life for Structure", headerTooltip: "Remaining Use full Life for Structure",},
						{ field: 'remainingEquipment', displayName: "Remaining Use full Life for Plant & Equipment", headerTooltip: "Remaining Use full Life for Plant & Equipment"},
						{ name: 'Assignment Records', displayName: "Life Assignment Records", headerTooltip: "Life Assignment Records", cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate:'<div ng-click="grid.appScope.downloadAssetLifeStatusFile(row.entity.id, row.entity.lifeAssignmentRecordsDocumentFileName)"  class="fa fa-download">{{repairs.repairsRecordsDetailsFileName}}</div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_LifeStatus");
				}
			});
}]);