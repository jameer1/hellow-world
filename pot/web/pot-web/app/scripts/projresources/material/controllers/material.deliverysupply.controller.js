'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialdeliverysupply", {
		url : '/materialdeliverysupply',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/deliverysuply/materialdeliverysupplydetails.html',
				controller : 'MaterialDeliverySupplyController'
			}
		}
	})
}]).controller("MaterialDeliverySupplyController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "MaterialDeliverySupplyFactory", "MaterialRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, MaterialDeliverySupplyFactory, MaterialRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.materialProjDtlTOs = null;
	$scope.deliveryDocketDatamoreFlag = 0;

	$scope.clickForwardDeliveryDocketData = function(moreFlag) {
		$scope.setSelected($scope.row);
		if ($scope.deliveryDocketDatamoreFlag < 1) {
			$scope.deliveryDocketDatamoreFlag = moreFlag + 1;
		}
	}, $scope.clickBackwardDeliveryDocketData = function(moreFlag) {
		$scope.setSelected($scope.row);
		if ($scope.deliveryDocketDatamoreFlag > 0) {
			$scope.deliveryDocketDatamoreFlag = moreFlag - 1;
		}
	}
	$scope.setSelected = function(row) {
		$scope.row=row;
		$scope.selectedRow = row;
	}

	$scope.companyMap = [];
	$scope.classificationMap = [];
	$scope.userProjMap = [];

	$scope.showCreateButton = false;
	$scope.showEditButton = false;
		
	$scope.getDeliverySupplyDetails = function() {
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
		MaterialRegisterService.getMaterialSchItemDeliveryDockets(req).then(function(data) {
			$scope.materialProjDtlTOs = data.labelKeyTOs;
			$scope.gridOptions.data = angular.copy($scope.materialProjDtlTOs);
		});
	}
	
	$scope.getDeliverySupplyDetails1 = function() {
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
		MaterialRegisterService.getMaterialSchItemDeliveryDockets(req).then(function(data) {
			$scope.materialProjDtlTOs = data.labelKeyTOs;
		});
	}

	$scope.getDeliverySupplyDetails();
	

	$scope.materialSearch = function() {
		var projId = null;
		if ($rootScope.materialSearchProject != null && $rootScope.materialSearchProject != undefined && $rootScope.materialSearchProject.selectedProject != null && $rootScope.materialSearchProject.selectedProject != undefined) {
			projId = $rootScope.materialSearchProject.selectedProject.projId;
		}
		var req = {
			"status" : 1,
			"projId" : [ projId ],
			"fromDate" : $rootScope.materialSearchProject.fromDate,
			"toDate" : $rootScope.materialSearchProject.toDate
		};
		MaterialRegisterService.getProjMaterialSch(req).then(function(data) {
			$scope.companyMap = data.registerOnLoadTO.companyMap;
			$scope.classificationMap = data.registerOnLoadTO.classificationMap;
			$scope.userProjMap = data.userProjMap;
			$scope.materialProjDtlTOs = data.materialProjDtlTOs;
		});
	}

	$scope.$on("searchDeliverySupplyDetails", function() {
		$scope.getDeliverySupplyDetails();
	});
	
	$scope.$on("resetDeliverySupplyDetails", function() {
		$scope.materialProjDtlTOs = [];
		$scope.getDeliverySupplyDetails();
		
	});
	$scope.addDeliverySupply = function() {
		$scope.setSelected(false);
		var itemMapData = {
			"companyMap" : $scope.companyMap,
			"classificationMap" : $scope.classificationMap,
			"userProjMap" : $scope.userProjMap
		};
		var materialdailysupplypopup = MaterialDeliverySupplyFactory.materialDeliverySupplyDetails(itemMapData);
		materialdailysupplypopup.then(function(data) {
			$scope.getDeliverySupplyDetails1();
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching material daily supply details", 'Error');
		});
	}

	$scope.download = function(id) {
		MaterialRegisterService.downloadMaterialFile(id);
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    
						{ field: 'displayNamesMap.supplyDeliveryDate', width:"80", displayName: "Supply / Delivery Date", headerTooltip: "Supply / Delivery Date"},
						{ field: 'displayNamesMap.projParentName', displayName: "EPS", headerTooltip: "EPS", },
						{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project"},
						{ field: 'blank', displayName: "New or Existing Asset", headerTooltip: "New or Exg Asset",},
						{ field: 'displayNamesMap.deliveryPlace',  displayName: "Delivery Location", headerTooltip: "Delivery Location",},
						
						{ field: 'displayNamesMap.deliveryCatagory', displayName: "Location  Category", headerTooltip: "Location  Category"},
						{ field: 'displayNamesMap.purCode', displayName: "PO Number", headerTooltip: "PO Number", },
						{ field: 'code', displayName: "PO Schl. Item", headerTooltip: "PO Schl. Item"},
						{ field: 'displayNamesMap.cmpName', displayName: "Supplier", headerTooltip: "Supplier",},
						{ field: 'displayNamesMap.materialClassName', displayName: "Item Description", headerTooltip: "Item Description",},
						{ field: 'blank1', displayName: "Sub Group", headerTooltip: "Sub Group"},
						
						{ field: 'displayNamesMap.materialUnitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure"},
						{ field: 'displayNamesMap.unitOfRate', displayName: "Rate per Unit", headerTooltip: "Rate per Unit", },
						{ field: 'displayNamesMap.deliveryDockDate', width:"80", displayName: "Del Docket Date", headerTooltip: "Del Docket Date"},
						{ field: 'displayNamesMap.deliveryDockNo', displayName: "Del Docket #", headerTooltip: "Del Docket #",},
						
						{ field: 'displayNamesMap.qty', displayName: "Docket Wise Qty Received", headerTooltip: "Docket Wise Qty Received"},
						{ field: 'displayNamesMap.schCummulativeQty', displayName: "Sch Item Wise Cuml Qty Recvd.", headerTooltip: "Sch Item Wise Cuml Qty Recvd.", },
						{ field: 'displayNamesMap.receivedBy', displayName: "Received By", headerTooltip: "Received By"},
						{ field: 'displayNamesMap.defectComments', displayName: "Defects If Any", headerTooltip: "Defects If Any",},
						{ field: 'displayNamesMap.comments', displayName: "Receiver Comments", headerTooltip: "Receiver Comments"},
						{ name: 'document', displayName: "Del Docket Records", headerTooltip: "Del Docket Records",
						celltemplate:'<div ng-if="row.entity.displayNamesMap.fileKey" ng-click="grid.appScope.download(row.entity.displayNamesMap.deliveryDocketId)" class="fa fa-download"></div>'},
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_Material_DeliverySupply");
					$scope.getDeliverySupplyDetails();
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 920;
				}
			});
}]);