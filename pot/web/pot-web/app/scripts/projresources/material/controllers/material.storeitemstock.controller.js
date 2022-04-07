'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialstoreitemstock",{
		url : '/materialstoreitemstock',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/storeitemstockbal/storesitemstockbalance.html',
				controller : 'MaterialStoreItemStockController'
			}
		}
	})
}]).controller("MaterialStoreItemStockController",["$rootScope", "$scope", "$q", "$state", "ngDialog", "MaterialRegisterService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, MaterialRegisterService,GenericAlertService, stylesService, ngGridService ) {
	$scope.stylesSvc = stylesService;
	$scope.storeStockBalanceDetails = [];
	$scope.dateWiseConsumptionDetails = [];
	$scope.companyMap = [];
	$scope.classificationMap = [];
	$scope.userProjectMap = [];

	$scope.getStoreItemStockBlncRecords = function() {
		if($rootScope.materialSearchProject.selectedProject!=null){
			var req = {
				"status" : 1,
				"projList" : [ $rootScope.materialSearchProject.selectedProject.projId ],
				"fromDate" : $rootScope.materialSearchProject.fromDate,
				"toDate" : $rootScope.materialSearchProject.toDate
			};
			}
			else{
				var req = {
						"status" : 1,
						"projId" : [ $rootScope.materialSearchProject.selectedProject ],
						"fromDate" : $rootScope.materialSearchProject.fromDate,
						"toDate" : $rootScope.materialSearchProject.toDate
					};
			}
		MaterialRegisterService.getMaterialStoreTransitConsumption(req).then(function(data) {
			$scope.storeStockBalanceDetails = data.ledgerRes;
			$scope.gridOptions.data = angular.copy($scope.storeStockBalanceDetails);
		},function(error) {
			GenericAlertService.alertMessage("Error occured while getting Store Item Stock balance Details","Error");
		});
	}
	
	$scope.$on("resetStoreItemsStockonSiteIntransit", function() {
		$scope.storeStockBalanceDetails = [];
		$scope.getStoreItemStockBlncRecords();
	});

	$scope.$on("searchStoreItemsStockonSiteIntransit", function() {
		$scope.getStoreItemStockBlncRecords();
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    
						{ field: 'date', displayName: "Date", headerTooltip: "Date", cellFilter: 'date'},
						{ field: 'eps', displayName: "EPS", headerTooltip: "EPS", },
						{ field: 'projName', displayName: "Project", headerTooltip: "Project"},
						{ field: 'docketNumber', displayName: "Project Docket", headerTooltip: "Project Docket",},
						{ field: 'resourceMaterial', displayName: "Resource Name", headerTooltip: "Resource Name",},
						
						{ field: 'resourceSubGroup', displayName: "Resource Sub Group Name", headerTooltip: "Resource Sub Group Name"},
						{ field: 'originProject', displayName: "Origin Project", headerTooltip: "Origin Project", },
						{ field: 'originLocation', displayName: "Origin Store Stock Yard", headerTooltip: "Origin Store Stock Yard"},
						{ field: 'destinationProject', displayName: "Destination Project", headerTooltip: "Destination Project",},
						{ field: 'destinationLocation', displayName: "Destination Store Stock Yard", headerTooltip: "Destination Store Stock Yard",},
						{ field: 'supplierName', displayName: "Supplier Name", headerTooltip: "Supplier Name"},
						
						{ field: 'issuerEmpName', displayName: "Issuer Employee Name", headerTooltip: "Issuer Employee Name"},
						{ field: 'receiverEmpName', displayName: "Receiver Employee Name", headerTooltip: "Receiver Employee Name", },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure"},
						{ field: 'rate', displayName: "Rate", headerTooltip: "Rate",},
						
						{ field: 'openingStock', displayName: "Opening Stock", headerTooltip: "Opening Stock"},
						{ name: 'blank', displayName: "Issued Qty", headerTooltip: "Issued Qty", 
						cellTemplate:'<div>{{(row.entity.issued) ? row.entity.issued : row.entity.supplied}}</div>'},
						{ field: 'consumption', displayName: "Date wise Consumption", headerTooltip: "Date wise Consumption"},
						{ field: 'cumulativeConsumption', displayName: "Cumulative Consumption", headerTooltip: "Cumulative Consumption",},
						{ field: 'closingStock', displayName: "Closing Stock", headerTooltip: "Closing Stock"},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_Material_StockBalanceRecords");
					$scope.getStoreItemStockBlncRecords();
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});
}]);