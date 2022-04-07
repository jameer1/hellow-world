'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("podelivery", {
		url : '/podelivery',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/podeliverymanpower.html',
			}
		}
	})
}]).controller("PurchaseDeliveryController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PurchaseOrderService", "PlantRegisterService", "PlantDocketDetailsFactory", "PurchaseDeliveryDetailsFactory", "GenericAlertService", function($rootScope, $scope, $q, $state, ngDialog,PurchaseOrderService,PlantRegisterService,PlantDocketDetailsFactory,PurchaseDeliveryDetailsFactory , GenericAlertService) {
	$scope.getPurchaseOrderDetails= function(){
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
			var req = {
				"preContractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
				"contractCmpId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.id,
				"projId" : $rootScope.selectedPurchaseOrderData.projId,
				"status" : 1
			};
		PurchaseOrderService.getPurchaseOrderDetails(req).then(function(data) {
			$scope.preContractData = data;
			$scope.bidderDatamoreFlag = 0;
			$scope.bidderDatamoreFlags = 0;
			$scope.manpowerFlag=true;
			$scope.plantFlag=true;
			$scope.materialFlag=true;
			$scope.serviceFlag=true;
			$scope.projEmpClassmap = data.projEmpClassMap;
			$scope.projPlantClassmap = data.projPlantMap;
			$scope.projMaterialClassmap = data.projMaterialClassMap;
			$scope.projServiceClassmap = data.projServiceMap;
			$scope.storeClassmap = data.storeMap;
			$scope.projStoreClassmap = data.projStoreMap;
			$scope.projcostCodeMap = data.projCostItemMap;
			$scope.preContractData = data.preContractTO;
			$scope.companyMap = data.companyMap;
			$scope.projSOWMap = data.projSOWMap;
			$scope.procureCategoryMap = data.procureCategoryMap;
			$scope.manpowerTabEnabled=false;
			$scope.materialsTabEnabled=false;
			$scope.plantsTabEnabled=false;
			$scope.servicesTabEnabled=false;
			$scope.subcontractTabEnabled=false;
			if($scope.preContractData.preContractEmpDtlTOs
				&&$scope.preContractData.preContractEmpDtlTOs.length>0){
				$rootScope.enabledTab.manpower=true;
				$scope.manpowerTabEnabled=true;
			}else{
				$rootScope.enabledTab.manpower=false;
				$scope.manpowerTabEnabled=false;
			}
			if($scope.preContractData.preContractMaterialDtlTOs
				&&$scope.preContractData.preContractMaterialDtlTOs.length>0){
				$rootScope.enabledTab.materials=true;
				$scope.materialsTabEnabled=true;
			}else{
				$rootScope.enabledTab.materials=false;
				$scope.materialsTabEnabled=false;
			}
			if($scope.preContractData.preContractPlantDtlTOs
				&&$scope.preContractData.preContractPlantDtlTOs.length>0){
				$rootScope.enabledTab.plants=true;
				$scope.plantsTabEnabled=true;
			}else{
				$rootScope.enabledTab.plants=false;
				$scope.plantsTabEnabled=false;
			}
			if($scope.preContractData.preContractServiceDtlTOs
				&&$scope.preContractData.preContractServiceDtlTOs.length>0){
				$rootScope.enabledTab.services=true;
				$scope.servicesTabEnabled=true;
			}else{
				$rootScope.enabledTab.services=false;
				$scope.servicesTabEnabled=false;
			}
			if($scope.preContractData.precontractSowDtlTOs
				&&$scope.preContractData.precontractSowDtlTOs.length>0){
				$rootScope.enabledTab.subcontract=true;
				$scope.subcontractTabEnabled=true;
			}else{
				$rootScope.enabledTab.subcontract=false;
				$scope.subcontractTabEnabled=false;
			}
			$scope.$emit('delivery', { message:$rootScope.enabledTab  });

			},
			 function(error) {
			GenericAlertService.alertMessage("Error occurred while getting purchase order details", 'Error');
		});
	},
	 $scope.totalAmount = 0;
	$scope.totalQuantity = 0;
	$scope.totalEstimated = 0;
	 $scope.getTotals = function(item){
    if (item){
        item.total = item.quantity * item.estimateCost;
        $scope.totalAmount += item.total;
        $scope.totalQuantity += item.quantity;
        $scope.totalEstimated += item.estimateCost;
    }
}
	
	$scope.getPurchaseOrderDetails();
	$scope.getPlantPoDetails = function() {
		var getPlantPoReq = {
			"purId" : $rootScope.selectedPurchaseOrderData.id,
			"procureType" : 'P',
			"status" : 1
		};
		var projPlantTypePopup = PlantRegisterService.getPOItemDetails(getPlantPoReq);
		projPlantTypePopup.then(function(data) {
			$scope.purchaseDetails = data.labelKeyTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting purchase order items details", 'Error');
		});
	},
	$scope.getManpowerPoDetails = function() {
		var req = {
			"purId" : $rootScope.selectedPurchaseOrderData.id,
			"procureType" : 'E',
			"status" : 1
		};
		var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req);
		poItemDetailsPromise.then(function(data) {
			$scope.manpowerPurchaseDetails = data.labelKeyTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Purchase Order Item Details", "Error");
		});
	},
	$scope.getMaterialPoDetails = function() {
		var req = {
			"purId" : $rootScope.selectedPurchaseOrderData.id,
			"procureType" : 'M',
			"status" : 1
		};
		var projectPODetailsPromise = PlantRegisterService.getPOItemDetails(req);
		projectPODetailsPromise.then(function(data) {
			$scope.purchaseDetails = data.labelKeyTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
		});
	},
	$scope.getServicePoDetails = function() {
		var req = {
			"purId" : $rootScope.selectedPurchaseOrderData.id,
			"procureType" : 'S',
			"status" : 1
		};
		var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req);
		poItemDetailsPromise.then(function(data) {
			$scope.servicePurchaseDetails = data.labelKeyTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Purchase Order Item Details", "Error");
		});
	},
	$scope.getSowPoDetails = function() {
		var req = {
			"purId" : $rootScope.selectedPurchaseOrderData.id,
			"procureType" : 'SOW',
			"status" : 1
		};
		var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req);
		poItemDetailsPromise.then(function(data) {
			$scope.sowPurchaseDetails = data.labelKeyTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Purchase Order Item Details", "Error");
		});
	}
	
	$scope.getDeliveryDocketsDetails = function(item) {
		var popupDetails = PurchaseDeliveryDetailsFactory.getDeliveryDetails(item,$scope.companyMap,$rootScope.selectedPurchaseOrderData);
		popupDetails.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting Delivery Docket Details", 'Info');
		})
	},
	$scope.getPlantDocketsDetails = function(item) {
		var popupDetails = PlantDocketDetailsFactory.getPlantDeliveryDetails(item,$scope.companyMap,$rootScope.selectedPurchaseOrderData);
		popupDetails.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting Delivery Docket Details", 'Info');
		})
	}
	
	$scope.$on("resetDeliveryDetails", function() {
		$scope.preContractData = [];
	});
}]);