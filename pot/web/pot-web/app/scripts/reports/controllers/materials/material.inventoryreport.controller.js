'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matinventoryreport", {
		url: '/matinventoryreport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.inventoryreport.html',
				controller: 'MaterialInventoryReport'
			}
		}
	})
}]).controller("MaterialInventoryReport", ["$scope","uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog", "$filter", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "MaterialMultiSelectFactory", "MaterialRegisterService",
	"ProjectSettingsService", "PreContractStoreFactory", "stylesService", "ngGridService",
	function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog, $filter, GenericAlertService, EpsProjectMultiSelectFactory,
		MaterialMultiSelectFactory, MaterialRegisterService,
		ProjectSettingsService, PreContractStoreFactory, stylesService, ngGridService) {

		$scope.stylesSvc = stylesService;
		$scope.selectedProjIds = [];
		$scope.storeIds = [];
		$scope.projStoreIds = [];
		$scope.selectedMaterialIds = [];
		$scope.ledgerDetails = [];

		let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
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
		let projReportsSettings = null;

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.selectedClientIds = data.searchProject.clientIds;
				ProjectSettingsService.projReportsOnLoad({ "projIds": $scope.selectedProjIds }).then(function (data) {
					projReportsSettings = data.projectReportsTOs;
				}, function (_error) {
					GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
				});
			}, function (_error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		}

		$scope.getMaterialDetails = function () {

			var materialReq = {
				"status": 1
			};
			var materialPopUp = MaterialMultiSelectFactory.getMultiMaterials(materialReq);
			materialPopUp.then(function (data) {
				$scope.materialNameDisplay = data.selectedMaterial.materialName;
				$scope.selectedMaterialIds = data.selectedMaterial.materialIds;
			}, function (_error) {
				GenericAlertService.alertMessage("Error occured while getting Material Details", 'Error');
			})
		}

		$scope.getStoreOrStock = function () {
			if (!$scope.selectedProjIds || $scope.selectedProjIds.length == 0)
				GenericAlertService.alertMessage("Select projects, to fetch store stock details.", 'Warning');
			else {
				PreContractStoreFactory.getStocks($scope.selectedProjIds, true).then(function (data) {
					$scope.storeModelObj = data.storeModelObj;
					$scope.storeIds = data.storeItems;
					$scope.projStoreIds = data.projStoreItems;
				});
			}
		}

		$scope.reset = function () {
			$scope.selectedProjIds = [];
			$scope.storeModelObj = null;
			$scope.storeIds = [];
			$scope.projStoreIds = [];
			$scope.materialNameDisplay = null;
			$scope.selectedMaterialIds = [];
			projReportsSettings = null;
			$scope.searchProject = {};
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.subReportCode = "";
			$scope.gridOptions.data = [];

		}

		$scope.getInventoryReportDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Select projects.", 'Warning');
				return;
			}
			if ($scope.storeIds.length <= 0 && $scope.projStoreIds.length <= 0) {
				GenericAlertService.alertMessage("Select store/stock.", 'Warning');
				return;
			}
			if ($scope.selectedMaterialIds.length <= 0) {
				GenericAlertService.alertMessage("Select materials.", 'Warning');
				return;
			}
			if ($scope.fromDate == null) {
				GenericAlertService.alertMessage("From date cannot be empty.", 'Warning');
				return;
			} if ($scope.toDate == null) {
				GenericAlertService.alertMessage("To date cannot be empty.", 'Warning');
				return;
			}
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}

			var req = {
				"status": 1,
				"projList": $scope.selectedProjIds,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate,

				"storeIds": $scope.storeIds,
				"projStoreIds": $scope.projStoreIds,
				"materialIds": $scope.selectedMaterialIds
			};
			MaterialRegisterService.getInventoryReport(req).then(function (data) {
				$scope.ledgerDetails = data.ledgerRes;
				$scope.ledgerDetails.map(ledger => {
					ledger.date = moment(ledger.date).format("DD-MMM-YYYY");
				});
				$scope.gridOptions.data = $scope.ledgerDetails;
				console.log("this is error", $scope.gridOptions)
				if ($scope.gridOptions.data <= 0) {
					GenericAlertService.alertMessage("Material-Inventory Report not available for the search criteria", "Warning");
				}

			}, function (_data) {
				GenericAlertService.alertMessage("Error occured while getting material inventory report", 'Error');
				
			});

		}

		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, _oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'date', displayName: "Date", headerTooltip: "Date", enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'eps', displayName: "EPS", headerTooltip: "EPS", visible: false, enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'storeLocation', displayName: "Store", headerTooltip: "Store Location", enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'resourceSubGroup', displayName: "Material Group", visible: false, headerTooltip: "Material Group", enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material", enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure", enableFiltering: false, groupingShowAggregationMenu: false },
						{ field: 'openingStock', displayName: "OpeningStock", headerTooltip: "Opening Stock", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'supplied', displayName: "Supplied", headerTooltip: "Supplied", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'issued', displayName: "Issued", headerTooltip: "Issued", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'consumption', displayName: "Consumption", headerTooltip: "Consumption", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'stockOnTransit', displayName: "StockOnTransit", visible: false, headerTooltip: "Stock on Transit", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'externalProjTransfer', displayName: "Ext.Transfer", visible: false, headerTooltip: "External Project Transfer", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'closingStock', displayName: "ClosingStock", headerTooltip: "Closing Stock", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_MaterialInventoryReport");
				}
			});
			var HelpService = {};
			$scope.helpPage = function () {
				var help = HelpService.pageHelp();
				help.then(function(_data) {

				}, function(_error) {
					GenericAlertService.alertMessage("Error",'Info');
				})
			}
			var helppagepopup;
			HelpService.pageHelp = function () {
				var deferred = $q.defer();
				helppagepopup = ngDialog.open({
					template: 'views/help&tutorials/reportshelp/materialshelp/matinventoryhelp.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom1',
					scope: $scope,
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function (_$scope) {
			
					}]
				});
				return deferred.promise;
			}
	}]);