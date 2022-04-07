'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matstockpilesreport", {
		url: '/matstockpilesreport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.stockpiles.html',
				controller: 'MaterialStockPilesReport'
			}
		}
	})
}]).controller("MaterialStockPilesReport", ["$scope", "uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog","GenericAlertService", "EpsProjectMultiSelectFactory",
	"MaterialRegisterService", "stylesService", "ngGridService", "$filter",
	function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory, MaterialRegisterService, stylesService, ngGridService, $filter) {
		$scope.stylesSvc = stylesService;
		$scope.selectedProjIds = '';
		$scope.stockPileDetails = [];
		function setDate() {
			$scope.toDate = moment(new Date()).format('DD-MMM-YYYY');
			let fromDateObj = new Date();
			fromDateObj.setMonth(fromDateObj.getMonth() - 1);
			$scope.fromDate = moment(fromDateObj).format('DD-MMM-YYYY');
		}

		setDate();
		let todayDate = new Date();
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.$watch('fromDate', function () {
			$scope.checkErr();
		});
		$scope.$watch('toDate', function () {
			$scope.checkErr();
		});
		$scope.checkErr = function () {
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
		};

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.selectedClientIds = data.searchProject.clientIds
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		}

		$scope.getStockPilesDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select a project to generate report.", 'Warning');
				return;
			}
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return true;
			}
			let req = {
				"status": 1,
				projList: $scope.selectedProjIds,
				fromDate: $scope.fromDate,
				toDate: $scope.toDate
			}
			$scope.stockPileDetails = [];
			MaterialRegisterService.getMaterialStockPiledConsumption(req).then(function (data) {
				$scope.stockPileDetails = data.ledgerRes;
				for (let stock of $scope.stockPileDetails) {
					stock.dateStr = $filter('date')((stock.date), "dd-MMM-yyyy");
					stock.issuedResult = (stock.issued) ? stock.issued : stock.supplied;
				}
				$scope.gridOptions.data = $scope.stockPileDetails;
				if ($scope.stockPileDetails.length <= 0) {
					GenericAlertService.alertMessage("Stock Piles-Stock Balance Report not available for the search criteria", 'Warning');
				}
			}, function (data) {
				GenericAlertService.alertMessage("Error occured while fetching daily issues reports.", 'Error');
			});
		}

		$scope.resetDeliverySupplyDetails = function () {
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.stockPileDetails = [];
			$scope.data = [];
			setDate();
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		}

		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'dateStr', displayName: "Date", headerTooltip: 'Date', visible: false, groupingShowAggregationMenu: false },
						{ field: 'eps', displayName: "EPS", headerTooltip: 'EPS Name' , groupingShowAggregationMenu: false},
						{ field: 'projName', displayName: "Project", headerTooltip: 'Project Name' , groupingShowAggregationMenu: false},
						{ field: 'storeLocation', displayName: "Location", headerTooltip: 'Stock Piled Location' , groupingShowAggregationMenu: false},
						// { field:'' ,displayName: "Approv Notif", headerTooltip: 'Pre Approval Notification', visible: false, groupingShowAggregationMenu: false },
						{ field: 'docketNumber', displayName: "Docket", headerTooltip: 'Project Docket' , groupingShowAggregationMenu: false},
						{ field: 'supplierName', displayName: "Supp Name", headerTooltip: 'Supplier Name' , groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.deliveryDockDate', displayName: "Supp Dock Dt", headerTooltip: 'Supplier Docket Date', visible: false, groupingShowAggregationMenu: false },
						{ field: 'resourceMaterial', displayName: "Resource", headerTooltip: 'Resource Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'resourceSubGroup', displayName: "Resource S.G", headerTooltip: 'Resource Sub Group Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'storeLocation', displayName: "Location-Delivery", headerTooltip: 'Stock Location(Place Of Delivery)' , groupingShowAggregationMenu: false},
						{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: 'Unit of Measure' , groupingShowAggregationMenu: false},
						{ field: 'rate', displayName: "Rate", headerTooltip: 'Unit of Rate' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'openingStock', displayName: "Open.S", headerTooltip: 'Quantity-Opening Stock' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'issuedResult', displayName: "Dt.Supply", headerTooltip: 'Quantity-Date wise Supply' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'cumulativeSupply', displayName: "Cum.Supply", headerTooltip: 'Quantity-Cumulative Supply' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'consumption', displayName: "Dt.Consum", headerTooltip: 'Quantity-Date wise Consumption' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'cumulativeConsumption', displayName: "Cum.Consum", headerTooltip: 'Quantity-Cumulative Consumption' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'transferQty', displayName: "Transfer", headerTooltip: 'Quantity-Transfer to other Projects' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'closingStock', displayName: "Close.Stock", headerTooltip: 'Quantity-Closing Stock', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} }



					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_StoreStockBalanceInTransit");
				}
			});
			var HelpService = {};
			$scope.helpPage = function () {
				var help = HelpService.pageHelp();
				help.then(function(data) {

				}, function(error) {
					GenericAlertService.alertMessage("Error",'Info');
				})
			}
			var helppagepopup;
			HelpService.pageHelp = function () {
				var deferred = $q.defer();
				helppagepopup = ngDialog.open({
					template: 'views/help&tutorials/reportshelp/materialshelp/matstockpileshelp.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom1',
					scope: $scope,
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function ($scope) {
			
					}]
				});
				return deferred.promise;
			}
	}]);