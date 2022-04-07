'use strict';
app.factory('CompanyFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "UserEPSProjectService", "PreContractDetailsFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, UserEPSProjectService, PreContractDetailsFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var companyDetailsPopUp;
	var service = {};
	service.companyDetailsPopUp = function(actionType, editBidder) {
		var deferred = $q.defer();
		companyDetailsPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/rfqdetailspopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				
				$scope.actionType = actionType;
				$scope.searchProject = {};
				$scope.contractData = {};
				var editComapny = [];
				
				$scope.addDocketData = [];
				var selectedPoData = [];
				$scope.addRows = function() {
					$scope.addDocketData.push({
						"selected" : false,
						"id":null,
						"docketStartDate" : null,
						"docketNum" : null,
						"docketEndDate" : null,
						"receivedBy" : null,
						"receiverComments" : null,
						"stockLabelKeyTO" : {
							id : null,
							code : null,
							name : null
						},
						"projStockLabelKeyTO" : {
							id : null,
							code : null,
							name : null
						},

						"odoMeterReading" : null,
						"deliveryType":"Full",
						"quantity":null
					});
				}
				
				if ('Add' === actionType){
					
					$scope.addPoData = {
						
						"id":null,
						"selected" : false,
						"projId" : null,
						"plantPOId" : null,
						"plantId":null,
						"purchaseLabelKeyTO" : {
							id : null,
							code : null,
							name : null
						},
						"purchaseSchLabelKeyTO" : {
							id : null,
							code : null,
							name : null,
							displayNamesMap:{}
						},
						"commissionDate" : null,
						"plantDeliveryStatus":null,
						"clientId":null
						
					};
					 $scope.addRows();
				}else  {
					$scope.addPoData = angular.copy(plantProjectPODtlTO);
					$scope.addDocketData = angular.copy(plantProjDtlTOs);
					$scope.inputDisable =  true;
				}
				$scope.getProjectPoDetails = function() {
					var getPlantPoReq = {
						"status" : 1,
						"projId" : $scope.addPoData.projId,
						"procureType" : 'P'
					};
					$scope.purchaseOrderTO = null;
					RegisterPurchaseOrderFactory.getProjectPODetails(getPlantPoReq).then(function(data) {
							$scope.addPoData.purchaseLabelKeyTO.id = data.purchaseOrderTO.id;
							$scope.addPoData.purchaseLabelKeyTO.code = data.purchaseOrderTO.code;
						}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order details", "Error");
					});

				}
				
				$scope.getUserProjects = function() {
					var userProjectSelection = UserEPSProjectService.epsProjectPopup();
					userProjectSelection.then(function(userEPSProjData) {
							$scope.searchProject = userEPSProjData.selectedProject;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}
				$scope.getPreContractDetails = function() {
					var getContractReq = {
							"projId" : $scope.searchProject.projId,
							"approveStatus" : 5,
							"status" : 1
					};
					PreContractDetailsFactory.getPreContractDetails(getContractReq).then(function(data) {
							$scope.contractData = data.selectedRecord;
						}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Pre Contract Stage1 Approved details", "Error");
					});

				}
				$scope.addComapny = function(actionType) {
					if (actionType == 'Edit' && editComapny.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
						return;
					}
						var companydetailspopup = CompanyDetailsFactory.comapnyDetailsPopUpOnLoad(actionType, editComapny);
						companydetailspopup.then(function(data) {
							editComapny = [];
							$scope.rfqDetails =	data;
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while fetching Bidder details", 'Error');
						});
				}
				$scope.qtyMismatch = false;
				var localqty = 0.0;
				var localCumulate  = 0;
				$scope.validateCommissionQty = function(){
				localqty = 0.0;
				angular.forEach($scope.addDocketData, function(value, key) {
							if (!isNaN(value.quantity) && value.quantity != null){
								localqty = parseFloat(value.quantity)+parseFloat(localqty);
							}
					});

					
					if ( parseFloat(defualtPlantQty)  >= parseFloat(localqty)){
						  $scope.qtyMismatch = false;
					}else {
						$scope.qtyMismatch = true;
						GenericAlertService.alertMessage("Received quantity value is mismatch with actuals", "Info");
					}
					localCumulate =  parseInt(localqty);
					
				}

				$scope.getPlantTypes = function() {
					var getPlantPoReq = {
							"purId" : $scope.addPoData.purchaseLabelKeyTO.id,
							"procureType" : 'P',
							"status" : 1
					};
					
					var projPlantTypePopup = RegisterPurchaseOrderItemsFactory.getPOItemDetails(getPlantPoReq);
					projPlantTypePopup.then(function(data) {
						$scope.addPoData.purchaseSchLabelKeyTO = data.selectedRecord;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order item details", 'Error');
					});
				}, 
				$scope.plantStockDetails = function(docket) {						
					
					var projPlantStockPopup = PreContractStoreFactory.getStocks($scope.addPoData.clientId,$scope.addPoData.projId);
					projPlantStockPopup.then(function(data) {
											

						if (data.type === 2) {
							docket.projStockLabelKeyTO.id = data.storeStockTO.id;
							docket.projStockLabelKeyTO.code = data.storeStockTO.code;
							docket.stockLabelKeyTO.id = null;
							docket.stockLabelKeyTO.code = null;
						} else {
							docket.stockLabelKeyTO.id = data.storeStockTO.id;
							docket.stockLabelKeyTO.code = data.storeStockTO.code;
							docket.projStockLabelKeyTO.id =  null;
							docket.projStockLabelKeyTO.code =  null;
							
						}
						
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order item details", 'Error');
					});
				}, 
				$scope.getEmployeeDetails = function(docket) {				
					
					if ($scope.addPoData.projId == null || $scope.addPoData.projId == undefined){
						GenericAlertService.alertMessage("Please select the project for Employee details", 'Error');
					}
					var req={
							"status":1,
							"projId":$scope.addPoData.projId
					}
					
					var projPlantEmployeePopup = ProjectEmployeFactory.getProjectEmployeeDetails(req);
					projPlantEmployeePopup.then(function(data) {
						docket.receivedBy = data.plantEmployeeTO.firstName;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order item details", 'Error');
					});
				}, 
				
				$scope.plantDetailsPopUpRowSelect = function(plant) {
					if (plant.selected) {
						selectedPoData.push(plant);
					} else {
						selectedPoData.pop(plant);
					}
				}, $scope.selectDeliveryPlace = function(storeLabelKeyTO, projStoreLabelKeyTO) {
					var storeYardPopup = PreContractStoreFactory.getStocks(selectedProject.clientId, selectedProject.projId);
					storeYardPopup.then(function(data) {

						if (data.type === 2) {
							projStoreLabelKeyTO.id = data.storeStockTO.id;
							projStoreLabelKeyTO.code = data.storeStockTO.code;
						} else {
							storeLabelKeyTO.id = data.storeStockTO.id;
							storeLabelKeyTO.code = data.storeStockTO.code;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Store Delivery Details", 'Error');
					});
				}, $scope.deleteRows = function() {
					if (selectedPoData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}

					if ($scope.addDocketData.length == 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						return;
					}

					if (selectedPoData.length < $scope.addDocketData.length) {
						angular.forEach(selectedPoData, function(value, key) {
							$scope.addDocketData.splice($scope.addDocketData.indexOf(value), 1);
						});

						selectedPoData = [];

					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				$scope.save = function() {
					
					if (!$scope.qtyMismatch){
					$scope.addPoData.cumulative = localCumulate;
					$scope.addPoData.quantity = localqty;
					$scope.addPoData.plantId = $rootScope.selectedPlantData.id;
					
					
					if ( defualtPlantQty == localqty){
						$scope.addPoData.plantDeliveryStatus = 'C';
					}else {
						$scope.addPoData.plantDeliveryStatus = 'P';
					}
					
					
					if ($scope.addPoData.plantDeliveryStatus == 'P' && $scope.addPoData.commissionDate != undefined 
														 && $scope.addPoData.commissionDate != null){
						GenericAlertService.alertMessage('Commission date should be empty value for Partially delivery', "Warning");
						return ;
					}
					
					
					if ($scope.addPoData.plantDeliveryStatus == 'C' && $scope.addPoData.commissionDate == undefined 
														 && $scope.addPoData.commissionDate == null){
							GenericAlertService.alertMessage('Commission date should not be empty value for Full delivery', "Warning");
							return ;
					}
					var req = {
						"plantProjDtlTOs" : $scope.addDocketData,
						"plantProjectPODtlTO":$scope.addPoData
					}
					
					PlantRegisterService.saveRegPlantProcureDelivery(req).then(function(data) {
						  
						var succMsg = GenericAlertService.alertMessageModal('Plant Procurement & Delivery Details '+ data.message,data.status);
						   succMsg.then(function() {	
								 var returnPopObj = {
	                                 "plantProjDtlTOs":  data.plantProjDtlTOs,
	                                 "userProjectMap":	data.projectLibTO.userProjMap,
	                                 "plantProjectPODtlTO":  data.plantProjectPODtlTO
	                             }
								 $rootScope.selectedPlantData.projId = data.plantProjectPODtlTO.projId;
								  deferred.resolve(returnPopObj); 
								
						   })
						},function (error){
								GenericAlertService.alertMessage('Project purchase scheduled item(s) is/are failed to save',"Error");
					   });
					
					}else {
						GenericAlertService.alertMessage("Received quantity value is mismatch with actuals", "Info");
					}
				}
			} ]
		});
		return deferred.promise;
	}
	
	service.comapnyDetailsPopUpOnLoad = function(actionType, editComapny){
		var deferred = $q.defer();
			var companyDetailsPopUp =service.companyDetailsPopUp(actionType, editBidder);
			companyDetailsPopUp.then(function(data){
							
					 var returnPopObj = {								                 
		                
			         }
					
			  deferred.resolve(returnPopObj); 
			},function(error) {
			if (error.status != undefined && error.status != null){
				GenericAlertService.alertMessage(message, error.status);
			}else {
			GenericAlertService.alertMessage("Error occured while selecting User Project details", 'Error');
			}
		});
		
		return deferred.promise;
	}
	
	return service;
}]);
