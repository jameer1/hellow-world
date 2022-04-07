'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetsale", {
		url : '/assetsale',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/salerecords/salerecords.html',
				controller : 'AssetSaleRecordController'
			}
		}
	})
}]).controller("AssetSaleRecordController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "SaleRecordFactory", "AssetRegisterService", "GenericAlertService", "assetidservice","EmpRegisterService", "stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $location,ngDialog, SaleRecordFactory,AssetRegisterService, GenericAlertService,assetidservice,EmpRegisterService, stylesService, ngGridService) {
	
	var selectedSaleRecords = [];
	$scope.stylesSvc = stylesService;
	$scope.fixedAssetid = assetidservice.fixedAssetId;	

	$scope.getSalesRecord=function(){
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var salesRecordsGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getSalesRecord(salesRecordsGetReq).then(function(data) {
			$scope.salesRecordsDtlTOs = data.salesRecordsDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.salesRecordsDtlTOs);
			console.log($scope.salesRecordsDtlTOs);
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Asset Sale details", "Error");
		});
	},
	
	$scope.saleRecordPopUpRowSelect = function(saleRecord) {
		if (saleRecord.selected) {
			selectedSaleRecords.push(saleRecord);
		} else {
			selectedSaleRecords.splice(selectedSaleRecords.indexOf(saleRecord), 1);
		}
	},
	
	$scope.deleteSaleRecord = function() {
		if (selectedSaleRecords.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedSaleRecords = [];
		} else {
			angular.forEach(selectedSaleRecords, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateSaleRecordReq = {
					"salesRecordsIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.deleteSaleRecords(deactivateSaleRecordReq).then(function(data) {
					GenericAlertService.alertMessageModal('Asset Sale Record(s) deactivated successfully', 'Info');
					$scope.getSalesRecord();
					selectedSaleRecords = [];
				       });
				
				angular.forEach(selectedSaleRecords, function(value, key) {
					$scope.selectedSaleRecords.splice($scope.selectedSaleRecords.indexOf(value), 1);
					selectedSaleRecords = [];
					$scope.deactivateIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Asset Sale Record(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedSaleRecords, function(value) {
					value.selected = false;
				})
			})

		}
	},
	$scope.downloadRecordSaleFile = function(saleRecordId,file_name) {
		//AssetRegisterService.downloadRecordSaleFile(saleRecordId);
		let req = {
				"recordId" : saleRecordId,
				"category" : "Asset Sale Records",
				"fileName" : file_name
			}
		EmpRegisterService.downloadRegisterDocs(req);
	},
	
	$scope.addSaleRecordList = function(actionType) {
		
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		
		angular.forEach(selectedSaleRecords, function(value, key) {
			value.selected=false;
		});
		if(selectedSaleRecords.length >0 && actionType=="Add"){
			selectedSaleRecords=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		if (actionType == 'Edit' && selectedSaleRecords.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
			var salerecordpopup = SaleRecordFactory.saleRecordPopUpOnLoad(actionType, selectedSaleRecords);			
				salerecordpopup.then(function(data) {
				$scope.salesRecordsDtlTOs = data.salesRecordsDtlTOs;
				selectedSaleRecords=[];
				$scope.getSalesRecord();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Asset Sale Record(s) value details", 'Error');
			});		
	}	
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.saleRecordPopUpRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false  },
						{ field: 'dateOfSale', displayName: "Date of Sale", headerTooltip: "Date of Sale", groupingShowAggregationMenu: false },
						{ field: 'saleType', displayName: "Sale Type", headerTooltip: "Sale Type", groupingShowAggregationMenu: false },
						{ field: 'buyerName', displayName: "Buyer Name", headerTooltip: "Buyer Name", groupingShowAggregationMenu: false },
						{ field: 'buyerAddress', displayName: "Buyer Address", headerTooltip: "Buyer Address",groupingShowAggregationMenu: false },
						{ field: 'landRegisterOfficeDetails', displayName: "Land Register Office Details", headerTooltip: "Land Register Office Details",groupingShowAggregationMenu: false },
						{ field: 'landValudation', displayName: "Land Valuation", headerTooltip: "Land Valuation",groupingShowAggregationMenu: false },
						{ field: 'structureValuation', displayName: "Structure Valuation", headerTooltip: "Structure Valuation", groupingShowAggregationMenu: false },
						{ field: 'plantEquipmentValutaion', displayName: "Plant & Equipment Valuation", headerTooltip: "Plant & Equipment Valuation", groupingShowAggregationMenu: false },
						{ field: 'totalValuation', displayName: "Total Valuation", headerTooltip: "Total Valuation", groupingShowAggregationMenu: false },
						{ field: 'totalSalesAmount', displayName: "Total Sale Amount", headerTooltip: "Total Sale Amount",groupingShowAggregationMenu: false },
						{ field: 'buyerSolicitor', displayName: "Buyer Solicitor", headerTooltip: "Buyer Solicitor",groupingShowAggregationMenu: false },
						{ field: 'vendorSolicitor', displayName: "Vendor Solicitor", headerTooltip: "Vendor Solicitor",groupingShowAggregationMenu: false },
						{ name: 'Download', displayName: "Sale Records(Upload & View)", headerTooltip: "Sale Records(Upload & View)",groupingShowAggregationMenu: false , cellClass:'justify-center',headerCellClass:'justify-center',
						cellTemplate: '<div  ng-click="grid.appScope.downloadRecordSaleFile(row.entity.id, row.entity.salesRecordsDetailsFileName)" class="fa fa-download" class="btn btn-primary btn-sm" ></div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_SaleRecords");
					$scope.getSalesRecord();
				}
			});
	$scope.$on("resetSaleRecordFromAssetSales", function() {
		$scope.saleRecordDataTOs = [];
	});
	$rootScope.$on('saleRecordFromassetsales', function (event) {
		
		if($scope.fixedAssetid){
			$scope.getSaleRecord();	
		}
	});
	
}]);