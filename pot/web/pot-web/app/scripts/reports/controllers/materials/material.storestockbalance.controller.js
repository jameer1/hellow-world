'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matstorestockreport", {
		url: '/matstorestockreport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.storestockbalance.html',
				controller: 'MaterialStoreStockReport'
			}
		}
	})
}]).controller("MaterialStoreStockReport", ["$scope", "uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "MaterialRegisterService", "stylesService", "ngGridService", "$filter",
	function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog, GenericAlertService,
		EpsProjectMultiSelectFactory, MaterialRegisterService, stylesService, ngGridService, $filter) {
		$scope.stylesSvc = stylesService;
		$scope.storeStockBalanceDetails = [];
		$scope.storeStockBalance = [];

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

		$scope.resetDeliverySupplyDetails = function () {
			$scope.searchProject = null;
			$scope.selectedProjIds = null;
			$scope.storeStockBalanceDetails = null;
			setDate();
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		}

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

		$scope.getStoreStockBalanceDetails = function () {
			if (!$scope.selectedProjIds) {
				GenericAlertService.alertMessage("Please select a project to generate report.", 'Warning');
			} else if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			}
			else {
				let req = {
					"status": 1,
					projList: $scope.selectedProjIds,
					fromDate: $scope.fromDate,
					toDate: $scope.toDate
				}
				MaterialRegisterService.getMaterialStoreTransitConsumption(req).then(function (data) {
				console.log(data);
					$scope.storeStockBalanceDetails = data.ledgerRes;
					for(let i=0;i<$scope.storeStockBalanceDetails.length;i++){
					if($scope.storeStockBalanceDetails[i].issued != null)
					$scope.storeStockBalance.push($scope.storeStockBalanceDetails[i]);
					}
					for (let storestock of $scope.storeStockBalance) {
						storestock.dateStr = $filter('date')((storestock.date), "dd-MMM-yyyy");
						storestock.issuedResult = (storestock.issued) ? storestock.issued : storestock.supplied;
					}
					$scope.gridOptions.data = $scope.storeStockBalance;
					if ($scope.storeStockBalanceDetails.length <= 0) {
						GenericAlertService.alertMessage("Material Store Items-Stock Balance not available for the search criteria", 'Warning');
					}
				}, function (data) {
					GenericAlertService.alertMessage("Error occured while fetching store stock balance reports.", 'Error');
				});
			}
		}
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'dateStr', displayName: "Date", headerTooltip: 'Date', visible: false, groupingShowAggregationMenu: false },
						{ field: 'eps', displayName: "EPS", headerTooltip: 'EPS', visible: false, groupingShowAggregationMenu: false },
						{ field: 'projName', displayName: "Project", headerTooltip: 'Project Name' , groupingShowAggregationMenu: false},
						{ field: 'docketNumber', displayName: "Docket", headerTooltip: 'Project Docket' , groupingShowAggregationMenu: false},
						{ field: 'resourceMaterial', displayName: "Material", headerTooltip: 'Resource Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'resourceSubGroup', displayName: "Material S.G", headerTooltip: 'Resource Sub Group Name' , groupingShowAggregationMenu: false},
						{ field: 'originProject', displayName: "Origin Proj", headerTooltip: 'Origin Project', visible: false, groupingShowAggregationMenu: false },
						{ field: 'originLocation', displayName: "Origin Store", headerTooltip: 'Origin Store Stock Yard' , groupingShowAggregationMenu: false},
						{ field: 'destinationProject', displayName: "Dest Proj", headerTooltip: 'Destination Project', visible: false, groupingShowAggregationMenu: false },
						{ field: 'destinationLocation', displayName: "Dest Store", headerTooltip: 'Destination Store Stock Yard' , groupingShowAggregationMenu: false},
						{ field: 'supplierName', displayName: "Supplier", headerTooltip: 'Supplier Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'issuerEmpName', displayName: "Issuer", headerTooltip: 'Issuer Employee Name' , groupingShowAggregationMenu: false},
						{ field: 'receiverEmpName', displayName: "Receiver", headerTooltip: 'Receiver Employee Name' , groupingShowAggregationMenu: false},
						{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: 'Unit Of Measure' , groupingShowAggregationMenu: false},
						{ field: 'rate', displayName: "Rate", headerTooltip: 'Rate' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'openingStock', displayName: "Q-O.S", headerTooltip: 'Quantity-Opening Stock' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'issuedResult', displayName: "Q-Issued", headerTooltip: 'Quantity-Issued' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'consumption', displayName: "Q-Consum", headerTooltip: 'Quantity-Date wise Consumption' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'cumulativeConsumption', displayName: "Q-Cum.Consum", headerTooltip: 'Quantity-Cumulative Consumption' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'closingStock', displayName: "Q-C.S", headerTooltip: 'Quantity-Closing Stock' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},

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
					template: 'views/help&tutorials/reportshelp/materialshelp/matstorestockbalancehelp.html',
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