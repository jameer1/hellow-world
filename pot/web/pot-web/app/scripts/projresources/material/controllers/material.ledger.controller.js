'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("materialledger", {
		url: '/materialledger',
		data: {
			roles: []
		},
		views: {
			'current@': {
				templateUrl: 'views/projresources/projmaterialreg/ledger/ledger.html',
				controller: 'MaterialLedgerController'
			}
		}
	})
}]).controller("MaterialLedgerController", ["$rootScope", "$scope","uiGridGroupingConstants", "uiGridConstants",
	"MaterialRegisterService", "GenericAlertService", "stylesService", "ngGridService",
	function ($rootScope, $scope, uiGridGroupingConstants, uiGridConstants,MaterialRegisterService, GenericAlertService,
		stylesService, ngGridService) {
		$scope.stylesSvc = stylesService;

		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'date', displayName: "Date", headerTooltip: "Date", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'eps', displayName: "EPS", visible: false, headerTooltip: "EPS", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'storeLocation', displayName: "Store", visible: false, headerTooltip: "Store Location", enableFiltering: false,groupingShowAggregationMenu: false },
						{ field: 'resourceSubGroup', displayName: "Material Group", visible: false, headerTooltip: "Material Group", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'supplierName', displayName: "Supplier", visible: false, visible: false, headerTooltip: "Supplier", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'purchaseOrderCode', displayName: "P.O.", visible: false, headerTooltip: "Purchase Order", enableFiltering: false ,groupingShowAggregationMenu: false},
						{
							field: 'scheduleItemId', displayName: "Sch.Item ID", visible: false,
							headerTooltip: "Schedule Item Id", enableFiltering: true,groupingShowAggregationMenu: false
						},
						{ field: 'docketType', displayName: "DocketType", headerTooltip: "Docket Type", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'docketNumber', displayName: "DocketNumber", visible: false, headerTooltip: "Docket Number", enableFiltering: true,groupingShowAggregationMenu: false },
						{ field: 'workDairyId', displayName: "Work.Dairy.Id", visible: false, headerTooltip: "Work Dairy Id", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'currency', displayName: "Currency", headerTooltip: "Currency", enableFiltering: false,groupingShowAggregationMenu: false },
						{ field: 'rate', displayName: "Rate/Unit", headerTooltip: "Rate per Unit",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						{ field: 'openingStock', displayName: "OpeningStock", headerTooltip: "Opening Stock",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false ,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'supplied', displayName: "Supplied", headerTooltip: "Supplied",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						{ field: 'issued', displayName: "Issued", headerTooltip: "Issued",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false ,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'consumption', displayName: "Consumption", headerTooltip: "Consumption",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false ,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'stockOnTransit', displayName: "StockOnTransit", visible: false, headerTooltip: "Stock on Transit",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false ,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'externalProjTransfer', displayName: "Ext.Transfer", visible: false, headerTooltip: "External Project Transfer",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false ,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'closingStock', displayName: "ClosingStock", headerTooltip: "Closing Stock",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Resources_Material_Register");
				}
			});

		$scope.$watch(function () { return $scope.ledgerInitCompleted; },
			function (newValue, oldValue) {
				if (newValue) {
					$scope.gridOptions.data = $scope.ledgerDetails;
					$scope.ledgerInitCompleted = false;
				}
			});

		$scope.getMaterialProjLedgers = function (onLoad) {

			if ($rootScope.materialSearchProject.selectedProject != null) {
				var req = {
					"status": 1,
					"projList": [$rootScope.materialSearchProject.selectedProject.projId],
					"fromDate": $rootScope.materialSearchProject.fromDate,
					"toDate": $rootScope.materialSearchProject.toDate
				};
			}
			else {
				var req = {
					"status": 1,
					"projId": [$rootScope.materialSearchProject.selectedProject],
					"fromDate": $rootScope.materialSearchProject.fromDate,
					"toDate": $rootScope.materialSearchProject.toDate
				};
			}
			MaterialRegisterService.getMaterialProjLedgers(req).then(function (data) {
				$scope.ledgerDetails = data.ledgerRes;
				$scope.ledgerDetails.map(ledger => {
					ledger.date = moment(ledger.date).format("DD-MMM-YYYY");
				});
				if (!onLoad) {
					$scope.gridOptions.data = $scope.ledgerDetails;
					if ($scope.ledgerDetails.length <= 0) {
						GenericAlertService.alertMessage("Details are not available for the search criteria", "Warning");
					}
				}
				else
					$scope.ledgerInitCompleted = true;

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting material project ledger details", "Error");
			});
		}

		$scope.ledgerInitCompleted = false;

		$scope.getMaterialProjLedgers(true);

		$scope.empDatamoreFlag = 0;
		$scope.clickForwardledgerData = function (empDatamoreFlag1) {

			if ($scope.empDatamoreFlag < 1) {
				$scope.empDatamoreFlag = empDatamoreFlag1 + 1;
			}
		}, $scope.clickBackwardledgerData = function (empDatamoreFlag1) {
			if ($scope.empDatamoreFlag > 0) {
				$scope.empDatamoreFlag = empDatamoreFlag1 - 1;
			}
		},

			$scope.$on("searchLedger", function () {
				$scope.getMaterialProjLedgers();
			});

		$scope.$on("resetLedger", function () {
			$scope.gridOptions.data = [];
			$scope.resetLedgerData();
		});
		$scope.resetLedgerData = function () {
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		}
	}]);