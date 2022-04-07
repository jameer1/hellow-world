'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialstockpiles", {
		url : '/materialstockpiles',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/stockpiles/stockpilesitems.html',
				controller : 'MaterialStockPilesController'
			}
		}
	})
}]).controller("MaterialStockPilesController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "MaterialRegisterService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, MaterialRegisterService, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.stockPilesDetails = [];

	$scope.companyMap = [];
	$scope.classificationMap = [];
	$scope.userProjectMap = [];
	
	$scope.getStockPilesRecords = function() {
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
		MaterialRegisterService.getMaterialStockPiledConsumption(req).then(function(data) {
			$scope.stockPilesDetails = data.ledgerRes;
			$scope.gridOptions.data = angular.copy($scope.stockPilesDetails);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Stock Piles Details", "Error");
		});
	}
	
	$scope.$on("resetMaterialsStockonSiteStockPiledItems", function() {
		$scope.stockPilesDetails = [];
		$scope.getStockPilesRecords();
	});

	$scope.$on("searchMaterialsStockonSiteStockPiledItems", function() {
		$scope.getStockPilesRecords();
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    
						{ field: 'date', displayName: "Date", headerTooltip: "Date", cellFilter: 'date',},
						{ field: 'eps', displayName: "EPS Name", headerTooltip: "EPS Name", },
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name"},
						{ field: 'storeLocation', displayName: "Stock Piled Location", headerTooltip: "Stock Piled Location",},
						{ field: 'blank', displayName: "Pre Approval Notification", headerTooltip: "Pre Approval Notification",},
						
						{ field: 'docketNumber', displayName: "Project Docket", headerTooltip: "Project Docket"},
						{ field: 'supplierName', displayName: "Supplier Name", headerTooltip: "Supplier Name", },
						{ field: 'displayNamesMap.deliveryDockDate', displayName: "Supplier Docket Date", headerTooltip: "Supplier Docket Date"},
						{ field: 'resourceMaterial', displayName: "Resource Name", headerTooltip: "Resource Name",},
						{ field: 'resourceSubGroup', displayName: "Resource Sub Group Name", headerTooltip: "Resource Sub Group Name",},
						{ field: 'storeLocation', displayName: "Stock Location(Place Of Delivery)", headerTooltip: "Stock Location(Place Of Delivery)"},
						
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure"},
						{ field: 'rate', displayName: "Unit Of Rate", headerTooltip: "Unit Of Rate", },
						{ field: 'openingStock', displayName: "Opening Stock", headerTooltip: "Opening Stock"},
						{ field: 'supplied', displayName: "Date wise Supply", headerTooltip: "Date wise Supply",
						cellTemplate:'<div>{{(row.entity.issued) ? row.entity.issued : row.entity.supplied}}</div>'},
						
						{ field: 'cumulativeSupply', displayName: " Cumulative Supply", headerTooltip: " Cumulative Supply"},
						{ field: 'consumption', displayName: "Date Wise Consumption", headerTooltip: "Date Wise Consumption",},
						{ field: 'cumulativeConsumption', displayName: "Cumulative Consumption", headerTooltip: "Cumulative Consumption"},
						{ field: 'transferQty', displayName: "Transfer top Other Projects", headerTooltip: "Transfer top Other Projects",},
						{ field: 'closingStock', displayName: "Closing Stock", headerTooltip: "Closing Stock"},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_Material_StockPilesRecords");
					$scope.getStockPilesRecords();
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});
}]);