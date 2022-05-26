'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialconsumptionrecords", {
		url : '/materialconsumptionrecords',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/datewiseconsumption/datewiseconsumptionquantity.html',
				controller : 'MaterialConsumptionRecordsController'
			}
		}
	})
}]).controller("MaterialConsumptionRecordsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "MaterialRegisterService", "GenericAlertService","stylesService", "ngGridService",  function($rootScope, $scope, $q, $state, ngDialog, MaterialRegisterService, GenericAlertService,stylesService, ngGridService) {
    $scope.stylesSvc = stylesService;
	$scope.dateWiseConsumptionDetails = [];
	$scope.companyMap = [];
	$scope.classificationMap = [];
	$scope.userProjectMap = [];
	
	$scope.getConsumptionRecords = function() {
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
		MaterialRegisterService.getMaterialProjConsumption(req).then(function(data) {
			$scope.dateWiseConsumptionDetails = data.labelKeyTOs;
			$scope.gridOptions.data = angular.copy($scope.dateWiseConsumptionDetails);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Material Consumption Details", "Error");
		});
	}
	
	$scope.$on("resetConsumptionRecords", function() {
		$scope.dateWiseConsumptionDetails = [];
		$scope.getConsumptionRecords();
	});

	$scope.$on("searchConsumptionRecords", function() {
		$scope.getConsumptionRecords();
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'displayNamesMap.consumptionDate', displayName: "WDM Date", headerTooltip : "WDM Date"},
						{ field: 'displayNamesMap.parentProjName', displayName: "EPS", headerTooltip: "EPS", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.workDairyCode', displayName: "WDM ID", headerTooltip: "WDM ID", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.reqUser', displayName: "Supervisor", headerTooltip: "Supervisor", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.SOURCE_TYPE', displayName: "Source", headerTooltip: "Source", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.classCode', displayName: "Resource", headerTooltip: "Resource", groupingShowAggregationMenu: false},
						{ field: 'code', displayName: "PO.SCH.ITM", headerTooltip: "PO.SCH.ITM", groupingShowAggregationMenu: false},
						{ field: 'name', displayName: "P.O.", headerTooltip: "P.O.", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.deliveryDocket', displayName: "Docket Type", headerTooltip: "Docket Type", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.deliveryDockNo', displayName: "Docket.No", headerTooltip: "Docket.No", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.deliveryDockDate', displayName: "Docket Date", headerTooltip: "Docket Date", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.cmpName',displayName: "Supplier", headerTooltip: "Supplier", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.classTypeUnitOfMeasure', displayName: "Unit", headerTooltip: "Unit", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.unitOfRate', displayName: "Rate", headerTooltip: "Rate", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.consumptionQty', displayName: "Used", headerTooltip: "Used", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.docketCummulativeQty', displayName: "Docket Qty ", headerTooltip: "Docket Qty", groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.schCummulativeQty',displayName: "SCH.ITM Qty", headerTooltip: "SCH.ITM Qty", groupingShowAggregationMenu: false}
						];
					let data = [];
					$scope.getConsumptionRecords();
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Materials_Consumption Records");
			        $scope.gridOptions.showColumnFooter = false;
				}
				});
}]);