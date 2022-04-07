'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("poregenerate", {
		url : '/poregenerate',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/poschmanpower.html',
				controller : 'PurchaseGenerateController'
			}
		}
	})
}]).controller("PurchaseGenerateController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PurchaseOrderService", "GenericAlertService", function($rootScope, $scope, $q, $state, ngDialog,PurchaseOrderService, GenericAlertService) {
	$scope.getPurchaseOrderDetails= function(){
		if (!$rootScope.selectedPurchaseOrderData) {
			return;
		}
			var req = {
				"preContractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
				"contractCmpId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.id,
				"projId" : $rootScope.selectedPurchaseOrderData.projId,
				"status" : 1
			};
		PurchaseOrderService.getPurchaseOrderDetails(req).then(function(data) {
			
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

			$scope.$emit('poschedule', { message:$rootScope.enabledTab  });
			
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
        $scope.totalAmount += (item.total);
        $scope.totalQuantity += (item.quantity);
        $scope.totalEstimated += item.estimateCost;
    }
}
  
	/*$scope.getTotals = function(){
    var total = 0;
    for(var i = 0; i < $scope.preContractData.preContractEmpDtlTOs.length; i++){
        var product = $scope.preContractData.preContractEmpDtlTOs[i];
        $scope.totalAmount += (product.quantity*product.estimateCost);
    }
    return total;
}*/
	$scope.$on("defaultPurchaseOrderDetailsTab", function(evt) {
		$scope.getPurchaseOrderDetails();
	});
	$scope.$on("resetScheduleItems", function(evt) {
		$scope.preContractData=[];
	});

	$scope.getPurchaseOrderDetails();
		
}]);