'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matdeliverysupplyreport", {
		url: '/matdeliverysupplyreport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.deliverysupplydetails.html',
				controller: 'MaterialDeliverySupplyReport'
			}
		}
	})
}]).controller("MaterialDeliverySupplyReport", ["$scope","$filter", "uiGridGroupingConstants", "$q", "ngDialog", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "MaterialRegisterService",
	"stylesService", "ngGridService","uiGridConstants",
	function ($scope, $filter, uiGridGroupingConstants, $q, ngDialog,  GenericAlertService, EpsProjectMultiSelectFactory, MaterialRegisterService,
		stylesService, ngGridService,  uiGridConstants) {

		$scope.stylesSvc = stylesService;

		$scope.download = function (id) {
			MaterialRegisterService.downloadMaterialFile(id);
		}

		$scope.deliveryDocketDatamoreFlag = 0;

		$scope.loadHiddenData = function (deliveryDocketDatamoreFlag) {
			if (deliveryDocketDatamoreFlag == 0)
				$scope.deliveryDocketDatamoreFlag = 1;
			else
				$scope.deliveryDocketDatamoreFlag = 0;
		}

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
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		}

		$scope.getDeliverySupplyDetails = function () {
			if (!$scope.selectedProjIds) {
				GenericAlertService.alertMessage("Please select a project to generate report.", 'Warning');
			} else if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			}
			else {
				let req = {
					projList: $scope.selectedProjIds,
					fromDate: $scope.fromDate,
					toDate: $scope.toDate
				}
				MaterialRegisterService.getMaterialSchItemDeliveryDockets(req).then(function (data) {
					$scope.gridOptions.data = data.labelKeyTOs;
					if (data.labelKeyTOs.length <= 0) {
						GenericAlertService.alertMessage("Material Delivery & Supply Details not available for the search criteria", 'Warning');
					}
				}, function (data) {
					GenericAlertService.alertMessage("Error occured while fetching delivery/supply reports.", 'Error');
				});
			}
		}

		$scope.resetDeliverySupplyDetails = function () {
			$scope.searchProject = null;
			$scope.selectedProjIds = null;
			$scope.gridOptions.data = [];
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
						{
							field: 'displayNamesMap.supplyDeliveryDate', displayName: "Supply Date", headerTooltip: "Supply Delivery Date", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.projParentName', displayName: "Eps", headerTooltip: "EPS Name", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.deliveryPlace', displayName: "Location", headerTooltip: "Location of Delivery", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.deliveryCatagory', displayName: "Loc.Category", headerTooltip: "Location Category", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.purCode', displayName: "P.O.", headerTooltip: "Purchase Order Number", groupingShowAggregationMenu: false
						},
						{
							field: 'name', displayName: "Sch.Item", headerTooltip: "Schedule Item", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.cmpName', displayName: "Supplier", headerTooltip: "Supplier", groupingShowAggregationMenu: false	
						},
						{
							field: 'displayNamesMap.materialClassName', displayName: "Material.Desc", headerTooltip: "Material Description", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.materialUnitOfMeasure', displayName: "Units", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.unitOfRate', displayName: "Rate", cellFilter: 'number: 0', headerTooltip: "Rate per Unit", groupingShowAggregationMenu: false,
							aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
							customTreeAggregationFinalizerFn: function (aggregation) {
								aggregation.rendered = aggregation.value;
							}
						},
						{
							field: 'displayNamesMap.deliveryDockDate', displayName: "Delivery.Doc.Date", headerTooltip: "Delivery Docket Date", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.deliveryDockNo', displayName: "Delivery.Doc.Id", headerTooltip: "Delivery Docket Id", groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.qty', displayName: "Received.Qty",
							headerTooltip: "Docket wise- Quantity Received", enableFiltering: false, cellFilter: 'number: 2',
							 groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
							customTreeAggregationFinalizerFn: function (aggregation) {
								aggregation.rendered = aggregation.value;
							}
						},
						{
							field: 'displayNamesMap.schCummulativeQty', displayName: "Received.Cumu.Qty",
							headerTooltip: "Schedule Item wise Cumulative Qty received", enableFiltering: false, cellFilter: 'number: 2', groupingShowAggregationMenu: false,
							 aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
							customTreeAggregationFinalizerFn: function (aggregation) {
								aggregation.rendered = aggregation.value;
							}
						},
						{
							field: 'displayNamesMap.receivedBy', displayName: "Received By", headerTooltip: "Received By", enableFiltering: false, groupingShowAggregationMenu: false
						},
						{
							field: 'displayNamesMap.defectComments', displayName: "Defects", headerTooltip: "Defects If Any", enableFiltering: false, visible: false,
						},
						{
							field: 'displayNamesMap.comments', displayName: "Comments", headerTooltip: "Other Comments from Receiver", enableFiltering: false, groupingShowAggregationMenu: false, visible: false,
						},
						{
							field: 'record', displayName: "Record", headerTooltip: "Record of Delivery Dockets", enableFiltering: false, 
							cellTemplate: '<button class="justify-right ui-grid-cell-contents class="fa fa-download" ng-if="row.entity.displayNamesMap.fileKey" ng-click="grid.appScope.download(row.entity.displayNamesMap.deliveryDocketId)" title="Click To Download">download</button>',
							visible: false, groupingShowAggregationMenu: false
						},
					]

					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_Delivery&SupplyDetails");
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
				template: 'views/help&tutorials/reportshelp/materialshelp/matdeliverysupplyhelp.html',
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