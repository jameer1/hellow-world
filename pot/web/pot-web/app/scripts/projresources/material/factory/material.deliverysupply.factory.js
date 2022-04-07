'use strict';
app.factory('MaterialDeliverySupplyFactory', ["blockUI", "ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialRegisterService", "EpsProjectSelectFactory", "MaterialPurchaseOrderSchItemFactory", "MaterialRegMultipleSchItemFactory", "PreContractStoreFactory", "MaterialDeliveryDocketFactory", "ProjectEmployeFactory", "RegisterPurchaseOrderFactory", "GenericAlertService", function (blockUI, ngDialog, $q, $filter, $timeout, $rootScope, MaterialRegisterService, EpsProjectSelectFactory, MaterialPurchaseOrderSchItemFactory, MaterialRegMultipleSchItemFactory, PreContractStoreFactory, MaterialDeliveryDocketFactory,
	ProjectEmployeFactory, RegisterPurchaseOrderFactory, GenericAlertService) {
	var materialDailySupplyPopUp;
	var service = {};
	service.materialDeliverySupplyDetails = function (itemMapData) {
		var deferred = $q.defer();
		materialDailySupplyPopUp = ngDialog.open({
			template: 'views/projresources/projmaterialreg/deliverysuply/materialdeliverysupplypopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
			controller: ['$scope', function ($scope) {
				$scope.selectedMaterialProjSchItemTO = {};
				$scope.materialProjSchItemTOs = [];
				var selectedMaterialDockets = [];
				$scope.selectedFileMap = [];
				$scope.classificationMap = itemMapData.classificationMap;
				$scope.scheduleItemList = [];
				$scope.addMaterialData = {
					"status": null,
					"projId": null,
					"purId": null,
					"purCode": null,
					"cmpName": null,
					"assetType" : null
				}
				$scope.scheduleItemList = [{
					code: '',
					name: '',
					qty: '',
					unitOfRate: ''
				}, {
					code: '',
					name: '',
					qty: '',
					unitOfRate: ''
				}]

				$scope.addMaterialData = {};
				$scope.companyMap = itemMapData.companyMap;
				$scope.classificationMap = itemMapData.classificationMap;
				$scope.userProjMap = itemMapData.userProjMap;
				$scope.searchProject = {};

				$scope.getUserProjects = function () {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function (data) {
						$scope.addMaterialData.projId = data.searchProject.projId;
						$scope.addMaterialData.projParentName = data.searchProject.parentName;
						$scope.addMaterialData.projName = data.searchProject.projName;
						$scope.searchProject = data.searchProject
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}

				// Purchase Order
				$scope.getProjectPoDetails = function () {
					if($scope.addMaterialData.projId == '' || $scope.addMaterialData.projId == null) {
							GenericAlertService.alertMessage("Please select Project Name to get Purchase Order details", "Warning");
							return;
						}
					var req = {
						"status": 1,
						"projId": $scope.addMaterialData.projId,
						"procureType": '#M,#M#S,#S#M,#S'
					};
					$scope.purchaseOrderTO = null;
					RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function (data) {						
						$scope.addMaterialData.purId = data.purchaseOrderTO.id;
						$scope.addMaterialData.purCode = data.purchaseOrderTO.code;
						$scope.addMaterialData.cmpName = data.purchaseOrderTO.displayNamesMap.cmpName;
						$scope.poStartDate=data.purchaseOrderTO.displayNamesMap.purStartDate;
						$scope.materialProjSchItemTOs = [];
						$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs = [];
						console.log($scope.addMaterialData.purId);
						var schItemReq = {
							"status": 1,
							"purId": $scope.addMaterialData.purId
						};
						var exitsingSchItems = MaterialRegisterService.getMaterialSchItemsByPurchaseOrder(schItemReq);
						exitsingSchItems.then(function (data) {
							angular.forEach(data.materialProjDtlTOs, function (value, key) {
								$scope.selectedMaterialProjSchItemTO.stockId = value.stockId;
								$scope.selectedMaterialProjSchItemTO.projStockId = value.projStockId;
								$scope.materialProjSchItemTOs.push(value);
							});
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting purchase existing schedule items", "Error");
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting purchase orders", "Error");
					});
				}

				$scope.getPOItemsByProject = function () {
					if (selectedSceduleItemsList.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one schedule item", "Warning");
						return;
					}
					var poQty = $scope.addMaterialData.purchaseSchLabelKeyTO.displayNamesMap['qty'];
					var localqty = 0.0;
					angular.forEach($scope.addMaterialData.materialPODeliveryDocketTOs, function (value, key) {
						if (!isNaN(value.receivedQty) && value.receivedQty != null) {
							localqty = parseFloat(value.receivedQty) + parseFloat(localqty);
						}
					});
					if (parseFloat(poQty) == parseFloat(localqty)) {
						GenericAlertService.alertMessage("Schedule item received quantity greater than atual quantity, No further delivery dockets are allowed for the selcted schedule item", "Info");
						return;
					}

				}

				$scope.getPOItemsByPurchaseOrder = function () {
					if ($scope.addMaterialData.projId == null || $scope.addMaterialData.projId == undefined) {
						GenericAlertService.alertMessage("Please select the project for material schedule items", "Warning");
						return;
					}
					if ($scope.addMaterialData.purId == null || $scope.addMaterialData.purId == undefined) {
						GenericAlertService.alertMessage("Please select the purchase order to get material schedule items", "Warning");
						return;
					}
					var req = {
						"status": 1,
						"purId": $scope.addMaterialData.purId,
						"procureType": 'M'
					};
					var existingSchItemsMap = [];
					angular.forEach($scope.materialProjSchItemTOs, function (value, key) {
						existingSchItemsMap[value.purchaseSchLabelKeyTO.id] = value;
					});
					var schItemData = MaterialRegMultipleSchItemFactory.getPOItemsByPurchaseOrder(req, $scope.addMaterialData.purId, $scope.addMaterialData.purCode, $scope.addMaterialData.projId, existingSchItemsMap);
					schItemData.then(function (data) {
						$scope.materialProjSchItemTOs = data.materialProjDtlTOs;
						console.log($scope.materialProjSchItemTOs);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order schedule items", 'Error');
					});
				}
				var deliveryDocketObj = {
					"select": false,
					"id": null,
					"docketDate": null,
					"docketNumber": null,
					"receivedQty": null,
					"receivedBy": null,
					"defectComments": null,
					"comments": null
				};
				$scope.selectMaterialSchItem = function (materialProjSchItemTO) {
					$scope.selectedMaterialProjSchItemTO = materialProjSchItemTO;
					if ($scope.selectedMaterialProjSchItemTO.id > 0) {
						var req = {
							"status": 1,
							"materialId": $scope.selectedMaterialProjSchItemTO.id
						};
						var materialSchDockets = MaterialRegisterService.getMaterialDeliveryDockets(req);
						materialSchDockets.then(function (data) {
							$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs = [];
							$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs = data.materialPODeliveryDocketTOs;

						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting schedule item delivery dockets", 'Error');
						});
					}
				}, $scope.selectMaterialDocket = function (materialDocket) {
					if (materialDocket.select) {
						selectedMaterialDockets.push(materialDocket);
					} else {
						selectedMaterialDockets.pop(materialDocket);
					}
				}, $scope.addRows = function () {
					if ($scope.selectedMaterialProjSchItemTO.id == null || $scope.selectedMaterialProjSchItemTO.id == undefined) {
						GenericAlertService.alertMessage("Please Select atleast one Schedule Item", 'Warning');
						return;
					}

					var docketObj = angular.copy(deliveryDocketObj);
					docketObj.stockLabelKeyTO = {
						"id": null,
						"code": null
					};
					docketObj.projStockLabelKeyTO = {
						"id": null,
						"code": null
					};
					docketObj.stockLabelKeyTO.id = $scope.selectedMaterialProjSchItemTO.stockId;
					if ($scope.selectedMaterialProjSchItemTO.stockId != null && $scope.selectedMaterialProjSchItemTO.stockId > 0) {
						docketObj.stockLabelKeyTO.code = $scope.selectedMaterialProjSchItemTO.deliveryPlace;
					} else {
						docketObj.projStockLabelKeyTO.id = $scope.selectedMaterialProjSchItemTO.projStockId;
						docketObj.projStockLabelKeyTO.code = $scope.selectedMaterialProjSchItemTO.deliveryPlace;
					}
					$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs.push(docketObj);

				}, $scope.deleteRows = function () {
					if (selectedMaterialDockets.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedMaterialDockets.length < $scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs.length) {
						angular.forEach(selectedMaterialDockets, function (value, key) {
							$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs.splice($scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs.indexOf(value), 1);
						});
						selectedMaterialDockets = [];
					selectedMaterialDockets.length>0 ? GenericAlertService.alertMessage('Row(s) is/are deleted Successfully', "Info") : "";
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.getEmployeeDetails = function (docket) {
					var req = {
						"status": 1,
						"projId": $scope.addMaterialData.projId
					}
					var projPlantEmployeePopup = ProjectEmployeFactory.getProjectEmployeeDetails(req);
					projPlantEmployeePopup.then(function (data) {
						docket.receivedBy = data.plantEmployeeTO.empCode;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order items details", 'Error');
					});
				}, $scope.getMaterialTypes = function () {
					var req = {
						"purId": $scope.addMaterialData.purchaseLabelKeyTO.id,
						"procureType": 'M',
						"status": 1
					};
					var projMaterialTypePopup = RegisterPurchaseOrderItemsFactory.getPOItemDetails(req);
					projMaterialTypePopup.then(function (data) {
						$scope.addMaterialData.purchaseMaterialTypeLabelKeyTO = data.selectedRecord;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order items details", 'Error');
					});
				}, $scope.materialStockDetails = function (supply) {
					var projMaterialStockPopup = PreContractStoreFactory.getStocks($scope.searchProject.clientId, $scope.addMaterialData.projId);
					projMaterialStockPopup.then(function (data) {
						if (data.type === 2) {
							supply.projStockLabelKeyTO.id = data.storeStockTO.id;
							supply.projStockLabelKeyTO.code = data.storeStockTO.code;
							supply.stockLabelKeyTO.id = null;
							supply.stockLabelKeyTO.code = null;
							supply.locType = data.storeStockTO.category;
						} else {
							supply.stockLabelKeyTO.id = data.storeStockTO.id;
							supply.stockLabelKeyTO.code = data.storeStockTO.code;
							supply.projStockLabelKeyTO.id = null;
							supply.projStockLabelKeyTO.code = null;
							supply.locType = data.storeStockTO.category;
						}

					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting purchase order items details", 'Error');
					});

				}
				$scope.receivedBy = function (supply) {
					var req = {
						"status": 1,
						"projId": $scope.addMaterialData.projId
					};
					ProjectEmployeFactory.getProjectEmployeeDetails(req).then(function (data) {
						supply.empRegisterDtlTO = data.plantEmployeeTO;
						supply.empRegId = data.plantEmployeeTO.id;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
					});
				}, $scope.qtyMismatch = false;
				var localqty = 0.0;
				var localCumulate = 0;
				$scope.validateReceivedQty = function () {
					var poQty = $scope.selectedMaterialProjSchItemTO.purchaseSchLabelKeyTO.displayNamesMap['qty'];
					localqty = 0.0;
					angular.forEach($scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs, function (value, key) {
						if (!isNaN(value.receivedQty) && value.receivedQty != null) {
							localqty = parseFloat(value.receivedQty) + parseFloat(localqty);
						}
					});

					if (parseFloat(poQty) >= parseFloat(localqty)) {
						$scope.qtyMismatch = false;
					} else {
						$scope.qtyMismatch = true;
						GenericAlertService.alertMessage("Received quantity value is mismatch with actuals", "Info");
					}
					localCumulate = localqty;
				},
				$scope.dateValidation= function(docketDate){
					if($scope.poStartDate>docketDate){
						GenericAlertService.alertMessage("Docket date should be greater than PO date", "Warning");
						return;						
					}
					
				}

				$scope.saveDeliveryDocketDetails = function () {
					var invalidRecordExist = false;
					if ($scope.addMaterialData.projId == null || $scope.addMaterialData.purId == null) {
						GenericAlertService.alertMessage("Please enter mterial schedule items", "Warning");
						return;
					}

					angular.forEach($scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs, function (value, key) {
						if (value.docketDate == null || value.docketNumber == null || value.receivedQty == null || value.receivedBy == null) {
							invalidRecordExist = true;
							return;
						}
					});

					if (invalidRecordExist) {
						GenericAlertService.alertMessage("Please Enter Delivery Docket Details", "Warning");
						return;
					}

					if ($scope.qtyMismatch) {
						GenericAlertService.alertMessage("Received quantity value is mismatch with actuals", "Warning");
						return;
					}
					console.log($scope.addMaterialData.purId+"->"+$scope.selectedMaterialProjSchItemTO.purchaseSchLabelKeyTO.id);
					var req = {
						"materialProjDtlTOs": [$scope.selectedMaterialProjSchItemTO],
						"status": 1,
						"materialId": $scope.selectedMaterialProjSchItemTO.id,
						"projId": $scope.selectedMaterialProjSchItemTO.projId,
						"folderCategory" : "Delivery/Supply Details",
						"purOrderId" : $scope.addMaterialData.purId,
						"schItemId" : $scope.selectedMaterialProjSchItemTO.purchaseSchLabelKeyTO.id,
						"assetType" : $scope.addMaterialData.assetType
					}
					console.log("saveDeliveryDocketDetails function");
					console.log(req);
					console.log($scope.selectedFileMap);
					blockUI.start();
					MaterialRegisterService.saveProjMaterialSchDocketDetails($scope.selectedFileMap, req).then(function (data) {
						blockUI.stop();
						$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs = data.data.materialPODeliveryDocketTOs;
						// GenericAlertService.alertMessageModal('Material Delivery/Supply Details ' + data.data.message, data.data.status);
						GenericAlertService.alertMessageModal('Material Delivery / Supply Details saved successfully',"Info");
						$scope.closeThisDialog();
						deferred.resolve(data.data);
					}, function (error) {
						blockUI.stop();
						if (error.message != null && error.message != undefined) {
							GenericAlertService.alertMessage(error.message, error.status);
						} else {
							GenericAlertService.alertMessage('Please add Supply/Delivery Details  ', "Warning");
						}
					});

				},
				$scope.upload = function (fileObject, refdocument, index) {

					if (fileObject) {
						// Max file size should be 5 MB
						if (fileObject.size > 5 * 1024 * 1024) {
							GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
							return;
						}
						refdocument.name = fileObject.name;
						refdocument.fileType = fileObject.type;
						refdocument.fileSize = fileObject.size;
					} else {
						refdocument.name = null;
						refdocument.fileType = null;
						refdocument.fileSize = null;
					}
					$scope.selectedFileMap[index] = fileObject;
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
