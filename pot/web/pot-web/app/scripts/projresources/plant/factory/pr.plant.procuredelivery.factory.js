'use strict';
app.factory('PlantProcureDeliveryFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "RegisterPurchaseOrderFactory", "PreContractStoreFactory", "PreContractExternalService", "GenericAlertService", "RegisterPurchaseOrderItemsFactory", "EmployeeMasterDetailsFactory", "ProjectEmployeFactory", "EpsProjectSelectFactory", "PlantRegisterService", function (ngDialog, $q, $filter, blockUI, $timeout, $rootScope, RegisterPurchaseOrderFactory, PreContractStoreFactory, PreContractExternalService, GenericAlertService,
	RegisterPurchaseOrderItemsFactory, EmployeeMasterDetailsFactory, ProjectEmployeFactory, EpsProjectSelectFactory, PlantRegisterService) {
	var poDetailsPopUp;
	var service = {};
	service.poDetailsPopUp = function (actionType, plantProjPODtlTOs, plantProjPurchaseType, userProjMap) {
		var deferred = $q.defer();
		poDetailsPopUp = ngDialog.open({
			template: 'views/projresources/projplantreg/plantprocrdelivery/plantregprocrdeliverypopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			controller: ['$scope', function ($scope) {
				$scope.plantProjPurchaseType = plantProjPurchaseType;
				$scope.userProjMap = userProjMap;
				$scope.action = actionType;
				$scope.inputProject = null;
				$scope.projStoreClassmap = [];
				$scope.actionType = actionType;
				$scope.activeFlag = false;
				$scope.searchProject = null;
				$scope.typeFlag = false;
				$scope.inputDisable = false;
				var defualtPlantQty = 1;
				$scope.total = 0;
				$scope.flag1 = false;
				$scope.selectedFileMap = [];
				$scope.purchaseOrders = {};
				$scope.mode = actionType;
				
				var selectedPoData = [];
				var plantDocketDataObj = {
					"select": false,
					"id": null,
					"startDate": null,
					"docketNum": null,
					"endDate": null,
					"receivedBy": null,
					"receiverComments": null,
					"deliveryLocation": null,
					"odoMeter": null,
					"deliveryType": "",
					"quantity": null,
					"fileObjectIndex" : null,
					"fileName": null
				};
				$scope.addRows = function () {

					$scope.plantProjPODtlTO.plantDocketDtlTOs.push(angular.copy(plantDocketDataObj));
				}
				$scope.validateAddDeleteRows = function (data) {
					var totqty = 0;
					if (data.length >= 1) {
						angular.forEach(data, function (value, key) {
							totqty = totqty + value.quantity;
							if (totqty == 1) {
								$scope.flag1 = true;
							}
						})
					}
				}

				$scope.getDeliveryType = function (docket, index) {
                    if(docket.deliveryType == 'Part'){
	                   docket.quantity = "";
                       docket.cumulative = "";
                    }
					if (angular.equals(docket.deliveryType, 'Full')) {
						$scope.typeFlag = true;
						docket.quantity = 1;
						$scope.plantProjPODtlTO.plantDeliveryStatus = 'C';
						$scope.validateDeliveryQuantity(docket.quantity);
						$scope.validateCommissionQty(index, docket);
					} else {
						$scope.typeFlag = false;
						$scope.plantProjPODtlTO.plantDeliveryStatus = 'P';
					}
				}
				if ('Add' === actionType) {
					console.log("add block");
					$scope.plantProjPODtlTO = {
						"id": null,
						"selected": false,
						"projId": null,
						"plantPOId": null,
						"plantId": null,
						"purchaseLabelKeyTO": {
							id: null,
							code: null,
							name: null
						},
						"purchaseSchLabelKeyTO": {
							id: null,
							code: null,
							name: null,
							displayNamesMap: {}
						},
						"commissionDate": null,
						"plantDeliveryStatus": null,
						"clientId": null,
						"plantDocketDtlTOs": []

					};
					$scope.addRows();
				} else {
					console.log("else block"+actionType);
					$scope.plantProjPODtlTO = angular.copy(plantProjPODtlTOs[0]);
					$scope.plantProjPODtlTO.plantDocketDtlTOs = plantProjPODtlTOs[0].plantDocketDtlTOs;
					$scope.cumulative = 0;
					angular.forEach($scope.plantProjPODtlTO.plantDocketDtlTOs, function (value) {
						$scope.cumulative = $scope.cumulative + parseFloat(value.quantity);
						value.cumulative = angular.copy($scope.cumulative);
					})

					$scope.inputDisable = true;
				}
				
				/*$scope.clear = function (plant,id) {
					if(actionType == 'Edit'){
						$scope.plantProjPODtlTO.purchaseLabelKeyTO.code = $scope.plantProjPODtlTO.purchaseLabelKeyTO.code;
						$scope.plantProjPODtlTO.purchaseSchLabelKeyTO.code = $scope.plantProjPODtlTOs.purchaseSchLabelKeyTO.code;
					}else
					$scope.plantProjPODtlTO.purchaseLabelKeyTO.code = "";
			        $scope.plantProjPODtlTO.purchaseSchLabelKeyTO.code ="";
			        $scope.plantProjPODtlTO.purchaseSchLabelKeyTO.name ="";
		        }*/
                var getPlantPoReq = {
							"purId": $scope.plantProjPODtlTO.purchaseLabelKeyTO.id,
							"procureType": 'P',
							"status": 1
						};
						PlantRegisterService.getPOItemDetails(getPlantPoReq).then(function(data) {
							console.log(data.labelKeyTOs);
							for (var i = 0; i < data.labelKeyTOs.length; i++) {
								if (data.labelKeyTOs[i].id == plantProjPODtlTOs[0].purchaseSchLabelKeyTO.id) {
									$scope.purchaseOrders = angular.copy(data.labelKeyTOs[i]);
								}
							}
						});
				$scope.getProjectPoDetails = function () {
					console.log("getProjectPoDetails from plant procuredeliveryfactory js file");
					if (!$scope.plantProjPODtlTO.projId) {
						GenericAlertService.alertMessage("Please select the Project", "Warning");
						return;
					}

					var req = {
						"status": 1,
						"projId": $scope.plantProjPODtlTO.projId,
						"procureType": '#P,#S,#P#S,#S#P'
					};

					$scope.purchaseOrderTO = null;
					RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function (data) {
						$scope.plantProjPODtlTO.purchaseLabelKeyTO.id = data.purchaseOrderTO.id;
						$scope.plantProjPODtlTO.purchaseLabelKeyTO.code = data.purchaseOrderTO.code;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
					});

				}

				$scope.getUserProjects = function () {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function (userEPSProjData) {
						if ($scope.plantProjPODtlTO.projId != userEPSProjData.searchProject.projId) {
							$scope.plantProjPODtlTO.projectChanged = true;
						}
						$scope.plantProjPODtlTO.clientId = userEPSProjData.searchProject.clientId;
						$scope.plantProjPODtlTO.projId = userEPSProjData.searchProject.projId;
						$scope.plantProjPODtlTO.plantRegProjTO = {
							"parentName": userEPSProjData.searchProject.parentName,
							"name": userEPSProjData.searchProject.projName
						};
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}

				$scope.validateDeliveryQuantity = function (qty) {


					if (qty > 1) {
						GenericAlertService.alertMessage('Delivery quantity should be less than are equal to One', "Warning");
						return;
					}



				};
				$scope.selectDeliveryPlace = function(docket) {
					var storeYardPopup = PreContractStoreFactory.getStocks($scope.plantProjPODtlTO.projId);
					storeYardPopup.then(function(data) {
						$scope.deliveryLocation =[];
					for(var i=0;i<$scope.plantProjPODtlTO.plantDocketDtlTOs.length;i++){
						$scope.deliveryLocation = data.storeStockTO.desc;
						docket.deliveryLocation = angular.copy($scope.deliveryLocation);
					}	
										
					console.log($scope.docket.deliveryLocation);
                        console.log(data);
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
				}	
				$scope.validateCommissionQty = function (index, docket) {

					$scope.cumulative = 0;
					$scope.qtyMismatch = false;
					$scope.localCumulate = 0;

					for (var i = 0; i <= index; i++) {

						$scope.cumulative = $scope.cumulative + parseFloat($scope.plantProjPODtlTO.plantDocketDtlTOs[i].quantity);
						docket.cumulative = angular.copy($scope.cumulative);
					}

					if (parseFloat($scope.cumulative || 0) > parseFloat(defualtPlantQty)) {
						$scope.qtyMismatch = true;
					}

					if ($scope.qtyMismatch) {
						GenericAlertService.alertMessage("Delivery quantity should be less than are equal to One", "Warning");
					} else {
						$scope.qtyMismatch = false;
					}

				},

					$scope.getPlantTypes = function () {

						if (!$scope.plantProjPODtlTO.projId) {
							GenericAlertService.alertMessage("Please select the Project", "Warning");
							return;
						}

						if (!$scope.plantProjPODtlTO.purchaseLabelKeyTO.id) {
							GenericAlertService.alertMessage("Please select the Purchase Order", "Warning");
							return;
						}

						var getPlantPoReq = {
							"purId": $scope.plantProjPODtlTO.purchaseLabelKeyTO.id,
							"procureType": 'P',
							"status": 1
						};

						var projPlantTypePopup = RegisterPurchaseOrderItemsFactory.getPOItemDetails(getPlantPoReq);
						projPlantTypePopup.then(function (data) {
							console.log(data);
							$scope.plantProjPODtlTO.purchaseSchLabelKeyTO = data.selectedRecord;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while selecting purchase order items details", 'Error');
						});
					},

					$scope.employeeDetails = function (docket) {
						if (!$scope.plantProjPODtlTO.projId) {
							GenericAlertService.alertMessage("Please select the Project", "Warning");
							return;
						}
						var getEmployeeRegisterReq = {
							"projId": $scope.plantProjPODtlTO.projId
						};
						EmployeeMasterDetailsFactory.getEmployeeMasterDetails(getEmployeeRegisterReq).then(function (data) {
							docket.receivedCode = data.employeeMasterTO.empCode;
							docket.receivedBy = data.employeeMasterTO.id;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
						});
					},

					$scope.plantDetailsPopUpRowSelect = function (plant) {
						if (plant.select) {
							selectedPoData.push(plant);
						} else {
							selectedPoData.pop(plant);
						}
					},

					$scope.deleteRows = function () {

						if (selectedPoData.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
							return;
						}

						if ($scope.plantProjPODtlTO.plantDocketDtlTOs.length == 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
							return;
						}

						if (selectedPoData.length < $scope.plantProjPODtlTO.plantDocketDtlTOs.length) {
							angular.forEach(selectedPoData, function (value, key) {
								$scope.plantProjPODtlTO.plantDocketDtlTOs.splice($scope.plantProjPODtlTO.plantDocketDtlTOs.indexOf(value), 1);
							});
							selectedPoData = [];
						} else {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					};

				$scope.checkFile = function (file, docket, index) {
					var allowedFiles = [".doc", ".docx", ".png", ".jpg", "jpeg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!file) {
						docket.isValidDoc = false;
					} if (!regex.test(file.name.toLowerCase().replace(/\s/g, ""))) {
						docket.docErrorMessage = "Supported formats PNG,JPEG,DOC,DOCX ";
						docket.isValidDoc = false;
					} else if ((file.size > 1000000)) {
						docket.docErrorMessage = "Supported Max. File size 1MB";
						docket.isValidDoc = false;
					} else {
						docket.isValidDoc = true;
						docket.fileName = file.name;						
						$scope.selectedFileMap[index] = file;
					}
				};

				$scope.saveProcureDeliveryDetails = function () {

					$scope.activeFlag = true;
					if (!$scope.qtyMismatch) {
						$scope.plantProjPODtlTO.cumulative = $scope.localCumulate;

						$scope.plantProjPODtlTO.quantity = $scope.quantity;
						$scope.plantProjPODtlTO.plantId = $rootScope.selectedPlantData.id;
						if (defualtPlantQty == $scope.localCumulate) {
							$scope.plantProjPODtlTO.plantDeliveryStatus = 'C';
						} else {
							$scope.plantProjPODtlTO.plantDeliveryStatus = 'P';
						}
						angular.forEach($scope.plantProjPODtlTO.plantDocketDtlTOs, function (value, key) {
							$scope.odoMeter = value.odoMeter;

						});
						if ($scope.plantProjPODtlTO.plantDeliveryStatus == 'P' && $scope.plantProjPODtlTO.commissionDate == null && $scope.cumulative == 1) {
							GenericAlertService.alertMessage('Commission Date should not be empty value for Partial delivery if Quantity is one', "Warning");
							return; 
						}
						if ($scope.plantProjPODtlTO.plantDeliveryStatus == 'C' || $scope.odoMeter == null && $scope.cumulative == 1) {
						
							GenericAlertService.alertMessage('Odo meter Reading is mandatory', "Warning");
							return;
						}
						/*if ($scope.plantProjPODtlTO.plantDeliveryStatus == 'P' && $scope.odoMeter == null && $scope.quantity == 1) {
							GenericAlertService.alertMessage('Odo meter Reading is mandatory', "Warning");
							return;
						}*/
						

						if ($scope.plantProjPODtlTO.plantDeliveryStatus == 'P' && $scope.plantProjPODtlTO.commissionDate != null && $scope.quantity < 1) {
							GenericAlertService.alertMessage('Commission Date should be empty value when Quantity is less than one', "Warning");
							return;
						}
						if ($scope.plantProjPODtlTO.plantDeliveryStatus == 'C' && $scope.plantProjPODtlTO.commissionDate == null) {
							GenericAlertService.alertMessage('Commission Date should not be empty value for Full delivery', "Warning");
							return;
						}
													
						if($scope.plantProjPODtlTO.purchaseSchLabelKeyTO.displayNamesMap.schItemCmpId == undefined){
							$scope.plantProjPODtlTO.purchaseSchLabelKeyTO = $scope.purchaseOrders;
						}       
						var req = {
							"plantProjPODtlTO": $scope.plantProjPODtlTO,
							"plantId": $rootScope.selectedPlantData.id,
							"projId": $rootScope.selectedPlantData.projId,
							"folderCategory" : "Procurement and Delivery Details",
							"mode" : $scope.mode,
							"status": 1
						};
						// TODO revisit this, not index 0 always
						const file = $scope.plantProjPODtlTO.plantDocketDtlTOs[0].deliverDocument;
						$scope.plantProjPODtlTO.plantDocketDtlTOs[0].deliverDocument = null;
						
						var files = [];
						for (let index = 0; index < $scope.selectedFileMap.length; index++) {
							const value = $scope.selectedFileMap[index];
							if (value) {
								$scope.plantProjPODtlTO.plantDocketDtlTOs[index].fileObjectIndex = files.length;
								files.push(value);
							}
						}
						blockUI.start();
						console.log(req);
						console.log(file);
						console.log(files);
						
						
						//$scope.refdocumentslist[index].fileObjectIndex = files.length;
						PlantRegisterService.saveRegPlantProcureDelivery(req, files).then(function (data) {
							blockUI.stop();
							let message = 'Plant Procurement & Delivery Details are saved successfully';
							if ($scope.plantProjPODtlTO.projectChanged) {
								message = $rootScope.selectedPlantData.assertId + " is registered to " + $scope.plantProjPODtlTO.plantRegProjTO.name + " Successfully";
							}
							var succMsg = GenericAlertService.alertMessageModal(message, "Info");
							succMsg.then(function () {
								$scope.closeThisDialog();
								$rootScope.selectedPlantData.projId = $scope.plantProjPODtlTO.projId;
								deferred.resolve(data);
							});
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Plant Procurement & Delivery Details are failed to  ', "Error");
						});
					} else {
						GenericAlertService.alertMessage("Delivery quantity value must be  one or below one", "Warning");
					}
				}
			}]
		});
		return deferred.promise;
	}
	service.poDetailsPopUpOnLoad = function (actionType, plantProjectPODtlTO, plantProjPurchaseType, userProjectMap, plantProjDtlTOs) {
		var deferred = $q.defer();
console.log("poDetailsPopUpOnLoad function plant procuredelivery factory js file");
console.log(userProjectMap);
		var poDetailsPopUp = service.poDetailsPopUp(actionType, plantProjectPODtlTO, plantProjPurchaseType, userProjectMap, plantProjDtlTOs, actionType)
		poDetailsPopUp.then(function (data) {
			deferred.resolve(data);
		});

		return deferred.promise;
	}

	return service;
}]);
