'use strict';
app.factory('PlantPayableRateFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "EmpCostCodeFactory", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, blockUI,$timeout, $rootScope, EmpCostCodeFactory, GenericAlertService, PlantRegisterService) {
	var plantChargePopUp;
	var service = {};
	service.plantChargeOnLoad = function(itemData) {
		var deferred = $q.defer();
		plantChargePopUp = ngDialog.open({
			template : 'views/projresources/projplantreg/payablerates/plantpayableratepopup.html',
			className:'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.chargeOutRatesTO = angular.copy(itemData.editCharge[0]);
				$scope.plantChargeOutRatesTOs = itemData.plantChargeOutRatesTOs;
				$scope.projCostItemMap = itemData.projCostItemMap;
				$scope.projGeneralLabelKeyTO = itemData.projGeneralLabelKeyTO;
				$scope.category = itemData.category;
				$scope.userProjMap = itemData.userProjMap;
				$scope.projPlantClassMap = itemData.projPlantClassMap;
				$scope.plantClassMstrMap = itemData.plantClassMstrMap;
				var oldEffectiveFromDate = $scope.chargeOutRatesTO.effectiveFrom;
				var req = {
			          "status" : 1,
			          "projId" : $rootScope.selectedPlantData.projId,
			          "plantId" : $rootScope.selectedPlantData.id
		        };
		        PlantRegisterService.getRegPlantProcureDeliveryDetails(req).then(function(data) {
			    console.log(data.plantProjPODtlTO.purchaseSchLabelKeyTO.code);
                $scope.sucheduleItemId = data.plantProjPODtlTO.purchaseSchLabelKeyTO.code;
			    console.log(data.plantProjPODtlTO.purchaseSchLabelKeyTO[0].code);
		        $scope.maxDate =data.plantProjPODtlTO.plantDocketDtlTOs[0].endDate;
				});
				$scope.EmptyFuel = function(category) {
					angular.forEach($scope.category, function(value) {
						if (value == "WITH FUEL") {
							$scope.chargeOutRatesTO.rateWithOutFualNRShift = null;
							$scope.chargeOutRatesTO.rateWithoutFualDBShift = null;
						} else if (value == "WITHOUT FUEL") {
							$scope.chargeOutRatesTO.rateWithFualDBShift = null;
							$scope.chargeOutRatesTO.rateWithFualNRShift = null;
						}
					})
				}
				$scope.savePlantPayableOutRates = function() {
					if ($scope.chargeOutRatesTO.id > 0 ) {
						var oldFromDate = new Date(oldEffectiveFromDate);
						var fromDate = new Date($scope.chargeOutRatesTO.effectiveFrom);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
						if (days <= 0) {
							GenericAlertService.alertMessage("New Effective Date should be future date of current Enrollment Date", 'Warning');
							return;
						}
						}
					console.log('$scope.chargeOutRatesTO   ', $scope.chargeOutRatesTO);
					var req = {
						"plantChargeOutRatesTO" : $scope.chargeOutRatesTO,
						"status" : 1,
						"plantRegProjTO" : {
							"projId" : $rootScope.selectedPlantData.projId,
							"plantId" : $rootScope.selectedPlantData.id
						}
					}
					blockUI.start();
					PlantRegisterService.savePlantPayableRates(req).then(function(data) {
						blockUI.stop();
						// var succMsg = GenericAlertService.alertMessageModal('Plant ChargeOut Detail(s) is/are  ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant Payable Rate(s) saved successfully',"Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							var returnPopObj = {
								"plantChargeOutRatesTOs" : data.plantChargeOutRatesTOs
							};
							deferred.resolve(returnPopObj);
						})
					}, function(error) {
						blockUI.stop();
						if (error.message != null && error.status != null) {
							GenericAlertService.alertMessage(error.message, error.status);
						} else {
							GenericAlertService.alertMessage('Error occured while saving the project payable details', "Error");
						}
					});
				},

				$scope.getCostCode = function(no) {
					var req = {
						"status" : 1,
						"projId" : $rootScope.selectedPlantData.projId
					};
					var popup = EmpCostCodeFactory.getCostCodeDetails(req);
					popup.then(function(data) {
						if (no == 1) {
							$scope.chargeOutRatesTO.projMobCostItemId = data.selectedRecord.id;
							$scope.chargeOutRatesTO.projMobCostItemCode = data.selectedRecord.code;
						}
						if (no == 2) {
							$scope.chargeOutRatesTO.projDeMobCostItemId = data.selectedRecord.id;
							$scope.chargeOutRatesTO.projDeMobCostItemCode = data.selectedRecord.code;
						}
					}, function(error) {
						GenericAlertService.alertMessage('Error occured while fetching the project chargeout details', "Error");
					});
				}

			} ]
		});
		return deferred.promise;
	}
	return service;

}]);
