'use strict';

app.factory('PurchaseMultiPOFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "RFQAppendenciesFactory", "RFQService", "PreContractExternalService", "PurchaseOrderService", "GenericAlertService", "Principal", "ClientService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, RFQAppendenciesFactory, RFQService, PreContractExternalService,
	PurchaseOrderService, GenericAlertService, Principal, ClientService) {
	var viewBidderFactoryPopUp;
	var service = {};
	service.getPurchaseOrderDetails = function (userProjMap, selectedData) {
		var deferred = $q.defer();
		viewBidderFactoryPopUp = ngDialog.open({
			template: 'views/procurement/pre-contracts/externalApproval/purchasemultiplepo.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.userProjMap = userProjMap;
				$scope.selectedData = selectedData;
				$scope.vendors = [];
        $scope.loggedInUser=$scope.account.username;
				$scope.po = { 'code': undefined, 'createdOn': undefined, 'id': null };
				$scope.preContractData = { 'clientDetails': undefined };
				$scope.selectedData.date = $filter('date')(new Date(), "dd-MMM-yyyy");
				$scope.selectedVendor = undefined;



				var preContractCmpTOs = [];
				var precontractDataWithScheduleItems;
				var purchaseOrders = undefined;
				var poStartDate = null;
				var poFinishDate = null;
				var poProcureType = null;
				$scope.getAppendicesDetails = function () {
					var popupDetails = RFQAppendenciesFactory.getPurchaseOrderDetails($scope.selectedData, $scope.userProjMap, precontractDataWithScheduleItems);
					popupDetails.then(function (data) {
						$scope.userProjMap = userProjMap;
						$scope.selectedData = selectedData;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
					});
				};
				$scope.multiPOData = {
					"desc": null,
					"issuedBy": null,
					"issuerSign": null,
					"issuerName": null,
					"issuerDesignation": null,
					"acceptedBy": null,
					"vendorSign": null,
					"vendorName": null,
					"vendorDesignation": null,
					"issuedManPowerQuantity": 0,
					"issuedPlantsQuantity": 0,
					"issuedMaterialsQuantity": 0,
					"issuedServicesQuantity": 0,
					"issuedSOWQuantity": 0
				};
				$scope.saveMultiGeneratePODetails = function () {
					var savereq = {
						"multiPurchaseOrderTOs": $scope.multiPOData,
						"status": 1,
						"projId": $scope.selectedData.projId

					};
					blockUI.start();
					PreContractExternalService.saveMultiPurchaseOrder(savereq).then(
						function (data) {
							blockUI.stop();
							var succMsg = GenericAlertService.alertMessageModal(
								"Multiple purchase order details saved successfully", 'Info');
							succMsg.then(function (popData) {
								var returnPopObj = {
									"multiPurchaseOrderTOs": data
								};
								deferred.resolve(returnPopObj);
							});
						},
						function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage(
								'Generate multiple purchase order details are failed to Save', 'Error');
						});
				};
				$scope.getMultiPODetails = function () {
					var popupDetails = RFQAppendenciesFactory.getPurchaseOrderDetails($scope.selectedData, $scope.userProjMap);
					popupDetails.then(function (data) {
						$scope.userProjMap = userProjMap;
						$scope.selectedData = selectedData;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
					});
				};
				/*
				$scope.getMultiPOCmpDetails = function () {
					var req = {
						"preContractId": $scope.selectedData.id,
						"status": 1,
					};
					var results = RFQService.getPreContratCompanies(req).then(function (data) {
						$scope.preContractCmpTOs = data.preContractCmpTOs;
						$scope.companyMap = data.companyMap;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting PreContract companies", 'Error');

					});
				}; */

				$scope.vendorChanged = function () {
					$scope.selectedData.vendorName = $scope.selectedVendor.vendorName;
					initMultiPOData();
					$scope.multiPOData.vendorName = $scope.selectedVendor.contactDetails.contactName;
					$scope.multiPOData.vendorDesignation = $scope.selectedVendor.contactDetails.designation;
					$scope.multiPOData.issuerName = $scope.preContractData.issuerName;
					$scope.multiPOData.issuerDesignation = $scope.preContractData.issuerDesignation;

					if (purchaseOrders.length) {
						var poFound = false
						angular.forEach(purchaseOrders, function (po, key) {
							if (po.preContractCmpTO.id === $scope.selectedVendor.preContractCmpId) {
								poFound = true;
								$scope.po.code = po.code;
								$scope.po.id = po.id;
								$scope.multiPOData = po.poDetailsTO;
								$scope.po.createdOn = $filter('date')(new Date(po.createdOn), "dd-MMM-yyyy");
								$scope.selectedData.date = $scope.po.createdOn;
								$scope.selectedData.poId = po.id;
							}
						});
						if (!poFound) {
							$scope.po.createdOn = $filter('date')(new Date(), "dd-MMM-yyyy");
							$scope.po.code = undefined;
							$scope.po.id = null;
							$scope.selectedData.poId = null;
						}

					}
				}

				$scope.generateMultiplePO = function () {
					if ($scope.po.code) {
						saveGeneratePODetails();
					} else {
						var approvedCompanyMap = getApprovedCompaniesMap();
						var preContractTO = selectedData;
						preContractTO.preContractCmpTOs = preContractCmpTOs;
						var savereq = {
							"preContractTO": preContractTO,
							"approvedCompanyMap": approvedCompanyMap,
							"projId": preContractTO.projId,
							"poDetailsTO": $scope.multiPOData,
							"poStartDate": poStartDate,
							"poFinishtDate": poFinishDate,
							"poProcureType": poProcureType
						};
						PreContractExternalService.saveSinglePurchaseOrder(savereq).then(
							function (data) {
								GenericAlertService.alertMessage("Purchase Order generated successfully", "Info");
								approvedCompanyMap = {};
								purchaseOrders = data.purchaseOrderTOs;
								$scope.vendorChanged();
							},
							function (error) {
								GenericAlertService.alertMessage("Failed to generated Purchase Order", "Error");
							});
					}
				};

				function initMultiPOData() {
					$scope.multiPOData = {
						"desc": null,
						"issuedBy": null,
						"issuerSign": null,
						"issuerName": null,
						"issuerDesignation": null,
						"acceptedBy": null,
						"vendorSign": null,
						"vendorName": null,
						"vendorDesignation": null,
						"issuedManPowerQuantity": 0,
						"issuedPlantsQuantity": 0,
						"issuedMaterialsQuantity": 0,
						"issuedServicesQuantity": 0,
						"issuedSOWQuantity": 0
					};
				}

				function saveGeneratePODetails() {
					var req = $scope.multiPOData;
					req.purchaseOrderId = $scope.po.id;
					blockUI.start();
					PurchaseOrderService.savePurchaseOrderDetails(req).then(
						function (data) {
							blockUI.stop();
							GenericAlertService.alertMessageModal("Purchase order details saved successfully", 'Info');
						},
						function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Purchase order details are failed to Save', 'Error');
						});
				};

				function getApprovedCompaniesMap() {
					var approvedCompanyMap = {};
					// reset dates
					poStartDate = new Date();
					poFinishDate = null;
					poProcureType = "";

					$scope.multiPOData.issuedManPowerQuantity = 0;
					$scope.multiPOData.issuedPlantsQuantity = 0;
					$scope.multiPOData.issuedMaterialsQuantity = 0;
					$scope.multiPOData.issuedServicesQuantity = 0;
					$scope.multiPOData.issuedSOWQuantity = 0;

					if (precontractDataWithScheduleItems.preContractTO.preContractEmpDtlTOs) {
						var manPowerProcureType = "";
						angular.forEach(precontractDataWithScheduleItems.preContractTO.preContractEmpDtlTOs, function (empDtlTO, key) {
							updatePODates(empDtlTO.startDate, empDtlTO.finishDate);
							angular.forEach(empDtlTO.preContractsEmpCmpTOs, function (empCmpTO, key) {
								if (empCmpTO.quantity && empCmpTO.preContractCmpId === $scope.selectedVendor.preContractCmpId) {
									approvedCompanyMap[empCmpTO.preContractCmpId] = empCmpTO.preContractCmpId;
									manPowerProcureType = "#E";
									$scope.multiPOData.issuedManPowerQuantity += empCmpTO.quantity;
								}
							});
						});
						poProcureType += manPowerProcureType;
					}
					if (precontractDataWithScheduleItems.preContractTO.preContractPlantDtlTOs) {
						var plantProcureType = "";
						angular.forEach(precontractDataWithScheduleItems.preContractTO.preContractPlantDtlTOs, function (plantDtlTO, key) {
							updatePODates(plantDtlTO.startDate, plantDtlTO.finishDate);
							angular.forEach(plantDtlTO.preContractPlantCmpTOs, function (plantCmpTO, key) {
								if (plantCmpTO.quantity && plantCmpTO.preContractCmpId === $scope.selectedVendor.preContractCmpId) {
									approvedCompanyMap[plantCmpTO.preContractCmpId] = plantCmpTO.preContractCmpId;
									plantProcureType = "#P";
									$scope.multiPOData.issuedPlantsQuantity += plantCmpTO.quantity;
								}
							});
						});
						poProcureType += plantProcureType;
					}
					if (precontractDataWithScheduleItems.preContractTO.preContractMaterialDtlTOs) {
						var materialProcureType = "";
						angular.forEach(precontractDataWithScheduleItems.preContractTO.preContractMaterialDtlTOs, function (materialDtlTO, key) {
							updatePODates(materialDtlTO.startDate, materialDtlTO.finishDate);
							angular.forEach(materialDtlTO.preContractMaterialCmpTOs, function (materialCmpTO, key) {
								if (materialCmpTO.quantity && materialCmpTO.preContractCmpId === $scope.selectedVendor.preContractCmpId) {
									approvedCompanyMap[materialCmpTO.preContractCmpId] = materialCmpTO.preContractCmpId;
									materialProcureType = "#M";
									$scope.multiPOData.issuedMaterialsQuantity += materialCmpTO.quantity;
								}
							});
						});
						poProcureType += materialProcureType;
					}
					if (precontractDataWithScheduleItems.preContractTO.preContractServiceDtlTOs) {
						var serviceProcureType = "";
						angular.forEach(precontractDataWithScheduleItems.preContractTO.preContractServiceDtlTOs, function (serviceDtlTO, key) {
							updatePODates(serviceDtlTO.startDate, serviceDtlTO.finishDate);
							angular.forEach(serviceDtlTO.preContractServiceCmpTOs, function (serviceCmpTO, key) {
								if (serviceCmpTO.quantity && serviceCmpTO.preContractCmpId === $scope.selectedVendor.preContractCmpId) {
									approvedCompanyMap[serviceCmpTO.preContractCmpId] = serviceCmpTO.preContractCmpId;
									serviceProcureType = "#S";
									$scope.multiPOData.issuedServicesQuantity += serviceCmpTO.quantity;
								}
							});
						});
						poProcureType += serviceProcureType;
					}
					if (precontractDataWithScheduleItems.preContractTO.precontractSowDtlTOs) {
						var sowProcureType = "";
						angular.forEach(precontractDataWithScheduleItems.preContractTO.precontractSowDtlTOs, function (sowDtlTO, key) {
							updatePODates(sowDtlTO.startDate, sowDtlTO.finishDate);
							angular.forEach(sowDtlTO.precontractSowCmpTOs, function (sowCmpTO, key) {
								if (sowCmpTO.quantity && sowCmpTO.preContractCmpId === $scope.selectedVendor.preContractCmpId) {
									approvedCompanyMap[sowCmpTO.preContractCmpId] = sowCmpTO.preContractCmpId;
									sowProcureType = "#SOW";
									$scope.multiPOData.issuedSOWQuantity += sowCmpTO.quantity;
								}
							});
						});
						poProcureType += sowProcureType;
					}
					return approvedCompanyMap;
				};

				function updatePODates(startDate, finishDate) {
					var schItemStartDate = new Date(startDate);
					if (poStartDate > schItemStartDate) {
						poStartDate = schItemStartDate;
					}

					var schItemFinishDate = new Date(finishDate);
					if (poFinishDate < schItemFinishDate) {
						poFinishDate = schItemFinishDate;
					}
				}

				function getPreContractCompanies() {
					var cmpGetReq = {
						"status": 1,
						"preContractId": $scope.selectedData.id

					};
					PreContractExternalService.getPreContratCompanies(cmpGetReq).then(
						function (data) {
							var companyIds = [];
							preContractCmpTOs = data.preContractCmpTOs;
							angular.forEach(data.preContractCmpTOs, function (company, key) {
								companyIds.push(company.id);
								var vendorDetails = data.preContractCompanyMap[company.companyId];
								vendorDetails.preContractCmpId = company.id;
								var vendorAddress = vendorDetails.addressMap[company.addressId];
								var groupedDetails = vendorDetails.cmpName + " (" + vendorDetails.cmpCode + ")";
								vendorDetails.vendorName = groupedDetails;
								if (vendorAddress) {
									groupedDetails += "\n" + vendorAddress.address + ",";
									groupedDetails += "\n" + vendorAddress.city + ",";
									groupedDetails += "\n" + vendorAddress.pincode + ",";
									groupedDetails += "\n" + vendorAddress.state + ".";
								}
								vendorDetails.addressDetails = groupedDetails;
								vendorDetails.contactDetails = vendorDetails.contactsMap[company.contactId];
								$scope.vendors.push(vendorDetails);
							});
							getPurchaseOrdersByPrecontractCmpIdAndProjId(companyIds);
							/*
							var vendorDetails = data.preContractCompanyMap[data.preContractCmpTOs[0].companyId];
							var vendorAddress = vendorDetails.addressMap[data.preContractCmpTOs[0].addressId];
							var groupedDetails = vendorDetails.cmpName + " (" + vendorDetails.cmpCode + ")";
							$scope.selectedData.vendorName = groupedDetails;
							if (vendorAddress) {
								groupedDetails += "\n" + vendorAddress.address + ",";
								groupedDetails += "\n" + vendorAddress.city + ",";
								groupedDetails += "\n" + vendorAddress.pincode + ",";
								groupedDetails += "\n" + vendorAddress.state + ".";
							}
							$scope.preContractData.vendorDetails = groupedDetails; */

						},
						function (error) {
							GenericAlertService.alertMessage("Error occured while getting Project Material classes", 'Error');
						});

				};

				function getClientDetails() {

					Principal.identity().then(function (acc) {
						var clientReq = {
							"clientId": acc.clientId
						};
						$scope.preContractData.issuerDesignation = acc.designation;
						$scope.preContractData.issuerName = acc.username;
						ClientService.getClientById(clientReq).then(
							function (data) {
								$scope.selectedData.clientName = data.name;
								$scope.preContractData.clientDetails = data.name + ",\n" + data.address + ",\n" + data.country;
							},
							function (error) {
								GenericAlertService.alertMessage("Error occured while getting Client details", 'Error');
							});
					});


				};

				function getPurchaseOrdersByPrecontractCmpIdAndProjId(companyIds) {
					var req = {
						"precontractCmpIds": companyIds,
						"projId": $scope.selectedData.projId,
					};
					PurchaseOrderService.getPurchaseOrdersByPrecontractCmpIdAndProjId(req).then(function (data) {
						purchaseOrders = data.purchaseOrderTOs;
						// select first vendor by default
						$scope.selectedVendor = $scope.vendors[0];
						// update PO details by calling vendor chnage function
						$scope.vendorChanged();
						if (!data.purchaseOrderTOs.length) {
							$scope.po.createdOn = $filter('date')(new Date(), "dd-MMM-yyyy");
						}
					});
				}

				function getPurchaseOrderScheduleItems() {
					var req = {
						"contractId": $scope.selectedData.id,
						"projId": $scope.selectedData.projId,
						"status": 1
					};
					PreContractExternalService.getExternalPreContractPopupOnLoad(req).then(function (data) {
						precontractDataWithScheduleItems = data;
					});

				};


				getPreContractCompanies();
				getClientDetails();
				getPurchaseOrderScheduleItems();
				initMultiPOData();

			}]
		});
		return deferred.promise;
	};
	return service;

}]);
