'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantpayablerates", {
		url : '/plantpayablerates',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/payablerates/payablerates.html',
				controller : 'PlantPayableRatesController'
			}
		}
	})
}]).controller("PlantPayableRatesController", ["$rootScope", "$scope", "utilservice", "PlantChargeFactory", "PlantRegisterService", "GenericAlertService","PlantPayableRateFactory", function($rootScope, $scope, utilservice, PlantChargeFactory, PlantRegisterService, GenericAlertService, PlantPayableRateFactory) {
	$scope.plantPayableTable = true;
	//$scope.plantChargeOutRatesTOs = [];
	$scope.plantPayableRatesTOs = [];
	$scope.regPlantDeploymentTO = null;
	var editCharge = [];
	$scope.projCostItemMap = [];
	$scope.projGeneralLabelKeyTO = [];
	$scope.category = [];
	$scope.userProjMap = [];
	$scope.projPlantClassMap = [];
	console.log('&&&&&  payable rates controller 66666666 ');
	$scope.getPlantChargeOutRates = function() {
		console.log('payable rates selectedPlantData  ', $scope.currentTab);
		if($scope.currentTab.title == 'Payable Rates' && $rootScope.selectedPlantData.assetType == 'New Plant' && $rootScope.selectedPlantData.procurecatgCode != 'HP'){
			GenericAlertService.alertMessage("Payable Rates is not applicable for new plants with purchase category other than Hire Purchase", "Warning");
			return;
		}
		if ($rootScope.selectedPlantData.assetType == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		console.log('payable rates selectedPlantData.projId  ', $rootScope.selectedPlantData.projId);
		if (!$rootScope.selectedPlantData.projId) {
			GenericAlertService.alertMessage("Please Assign The Project And  Refresh The page To Enter Payable Rates", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedPlantData.projId,
			"plantId" : $rootScope.selectedPlantData.id
		};
		console.log('****** req >>>>  ', req);
		PlantRegisterService.getRegPlantProcureDeliveryDetails(req).then(function(data) {
                $scope.sucheduleItemId = data.plantProjPODtlTO.purchaseSchLabelKeyTO.code;
				});
		PlantRegisterService.getPlantChargeOutRates(req).then(function(data) {
			console.log('****** plantPayableTable 2222222222 ');
			console.log('****** plantPayableTable 33333 ',$scope.plantPayableTable);
			
			//$scope.projCostItemMap = data.projectLibTO.projCostItemMap;
			$scope.projGeneralLabelKeyTO = data.projGeneralLabelKeyTO;
			$scope.category = data.category;
			console.log($scope.payablecategory);
			$scope.plantProjectDtlTOs = data.plantProjectDtlTOs;
			$scope.plantPayableRatesTOs = data.plantChargeOutRatesTOs; 
			$scope.userProjMap = data.projectLibTO.userProjMap;
			$scope.projPlantClassMap = data.projectLibTO.projClassMap;
			$scope.plantPayableTable = true;
			console.log('****** plantChargeOutRatesTOs  ', $scope.plantPayableRatesTOs);
			console.log('****** plantProjectDtlTOs  ', $scope.plantProjectDtlTOs);
		}, function(error) {
			console.log('****** exception  ', error);
			GenericAlertService.alertMessage("Error occured while getting the Plant Charge Out Details", "Error");
		});
	}

	$scope.getPlantChargeOutRates();

	$scope.chargeRowSelect = function(plantChargeOut) {
		if (plantChargeOut.select) {
			utilservice.addItemToArray(editCharge, "id", plantChargeOut);
		} else {
			editCharge.pop(plantChargeOut);
		}
	}

	$scope.addPlantChargeCode = function() {
		if (editCharge <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}

		var itemData = {
			"editCharge" : editCharge,
			"projCostItemMap" : $scope.projCostItemMap,
			"projGeneralLabelKeyTO" : $scope.projGeneralLabelKeyTO,
			"plantChargeOutRatesTOs" : $scope.plantChargeOutRatesTOs,
			"category" : $scope.category,
			"userProjMap" : $scope.userProjMap,
			"projPlantClassMap" : $scope.projPlantClassMap,
			"plantClassMstrMap" : $scope.plantClassMstrMap
		};
		var plantchargedetailspopup = PlantPayableRateFactory.plantChargeOnLoad(itemData);
		plantchargedetailspopup.then(function(data) {
			$scope.plantPayableTable = true;
			$scope.plantChargeOutRatesTOs = data.plantChargeOutRatesTOs;
			$scope.getPlantChargeOutRates();
			editCharge = [];
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant charge Details", 'Error');
		});

	}, $scope.showChargeOutRates = function(remarks) {
		GenericAlertService.comments(remarks);
	}
	
	$scope.payableRowSelect  = function(payRatesTO){
		
		if (payRatesTO.select) {
			console.log('if condition ****  ');
			utilservice.addItemToArray(editCharge, "id", payRatesTO);
		} else {
			console.log('else  condition ****  ');
			editCharge.pop(payRatesTO);
		}
	}
    $scope.$on("defaultPayableRatesTab", function() {
        $scope.projCostItemMap = [];
		$scope.plantChargeOutRatesTOs = null;
		$scope.getPlantChargeOutRates();
	});
	$scope.$on("resetPayableRates", function() {
		$scope.plantPayableRatesTOs = [];
	});
}]);