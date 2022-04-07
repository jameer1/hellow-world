'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialissuerecords", {
		url : '/materialissuerecords',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/dailyissue/dailyissuerecords.html',
				controller : 'MaterialIssueRecordsController'
			}
		}
	})
}]).controller("MaterialIssueRecordsController", ["$rootScope", "$scope", "uiGridGroupingConstants", "uiGridConstants","$q", "$state", "ngDialog", "MaterialRegisterService", 'stylesService', 'ngGridService', "GenericAlertService", function($rootScope, $scope,uiGridGroupingConstants, uiGridConstants , $q, $state, ngDialog, MaterialRegisterService,stylesService, ngGridService, GenericAlertService) {
$scope.stylesSvc = stylesService;
	$scope.dailyIssueRecords = [];
	var projId = null;
	if ($rootScope.materialSearchProject != null && $rootScope.materialSearchProject != undefined && $rootScope.materialSearchProject.selectedProject != null && $rootScope.materialSearchProject.selectedProject != undefined) {
		projId = $rootScope.materialSearchProject.selectedProject.projId;
	}
	$scope.getDailyIssueRecords = function() {
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
		MaterialRegisterService.getMaterialDailyIssueSchItems(req).then(function(data) {
			$scope.dailyIssueRecords = data.labelKeyTOs;
			$scope.gridOptions.data = angular.copy($scope.dailyIssueRecords);
			if ($scope.dailyIssueRecords.length <= 0) {
				GenericAlertService.alertMessage("Material Daily Issue Record Details are not available for the selected criteria", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Material Daily Issue Record Details", "Error");
		});
	}
	var linkCellTemplate = '<span class="btn btn-primary btn-sm" ng-click="grid.appScope.showMore()" class="btn bottomaligned btn-brown btn-sm">More Details</span>';
    $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
						{ field: 'displayNamesMap.consumptionDate', displayName: "Date Of Issue", headerTooltip: "Date Of Issue", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.notificationCode', displayName: "Pre Approval Notification ID", visible: false, headerTooltip: "Pre Approval Notification ID", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.deliveryDockNo', displayName: "Project Docket", headerTooltip: "Project Docket", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.className', displayName: "Resource Name", visible: false, headerTooltip: "Resource Name", enableFiltering: false,groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.classCode', displayName: "Resource Sub Group Name", visible: false, headerTooltip: "Resource Sub Group Name", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.fromProjName', displayName: "Origin Project", headerTooltip: "Origin Project", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.fromLocation', displayName: "Origin Store Stock Yard", visible: false, visible: false, headerTooltip: "Origin Store Stock Yard", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.toProjName', displayName: "Destination Project", visible: false, headerTooltip: "Destination Project", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.toLocation', displayName: "Destination Store Stock Yard", headerTooltip: "Destination Store Stock Yard", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.cmpName', displayName: "Supplier Name", visible: false, headerTooltip: "Supplier Name", enableFiltering: true,groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.issueEmpName', displayName: "WIssuer Employee Name", visible: false, headerTooltip: "Issuer Employee Name", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.receiveEmpName', displayName: "Receiver Employee Name", headerTooltip: "Receiver Employee Name", enableFiltering: false ,groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", enableFiltering: false,groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.issuedQty', displayName: "Quantity", headerTooltip: "Quantity",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
						{ field: 'displayNamesMap.unitOfRate', displayName: "Rate", headerTooltip: "Rate",aggregationType: uiGridConstants.aggregationTypes.sum, enableFiltering: false ,groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'displayNamesMap.comments', displayName: "Comments", headerTooltip: "Comments", enableFiltering: false,groupingShowAggregationMenu: false }
						]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Material_Register");
			$scope.gridOptions.exporterPdfOrientation = 'landscape';
			$scope.gridOptions.exporterPdfPageSize = 'A3';
			$scope.gridOptions.exporterPdfMaxGridWidth = 700;
		}
	});

	$scope.$on("resetStoreOrMaterialsDailyIssueRecords", function() {
		$scope.materialProjDtlTOs = [];
		$scope.getDailyIssueRecords();
	});

	$scope.$on("searchStoreOrMaterialsDailyIssueRecords", function() {
		$scope.getDailyIssueRecords();
	});
}]);