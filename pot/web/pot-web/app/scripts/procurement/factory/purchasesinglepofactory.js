'use strict';

app.factory('PurchaseSinglePOFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "RFQAppendenciesFactory", "PreContractExternalService", "PurchaseOrderService", "GenericAlertService", "Principal", "ClientService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, RFQAppendenciesFactory, PreContractExternalService, PurchaseOrderService, GenericAlertService, Principal, ClientService) {
	var viewBidderFactoryPopUp;
	var service = {};

	service.getPurchaseOrderDetails = function (userProjMap, selectedData,typeOfPO,
	                                        clientId,purchaseOrderId,projId,contractId) {
		var deferred = $q.defer();
		viewBidderFactoryPopUp = ngDialog.open({
			template: 'views/procurement/pre-contracts/externalApproval/purchasesinglepo.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose: false,
			closeByDocument: false,

			controller: ['$scope', function ($scope) {
        $scope.loggedInUser=$scope.account.username;
				$scope.userProjMap = userProjMap;
				console.log($scope.userProjMap);
				if(typeOfPO == "singlepo"){
				$scope.selectedData = selectedData;
				}
				if(typeOfPO == "RepeatPO"){
				$scope.selectedData = $rootScope.selectedPurchaseOrderData;
				console.log($scope.selectedData);
				}
				$scope.preContractData = { 'vendorDetails': undefined, 'clientDetails': undefined };
				$scope.po = { 'code': undefined, 'createdOn': undefined, 'id': null };
				var preContractCmpTOs = [];
				var precontractDataWithScheduleItems = null;
				var poStartDate = null;
				var poFinishDate = null;
				var poProcureType = null;
				$scope.pageTitle = "Generation Of Single Purchase Order";
        //$scope.typeOfPO = "PO";
        $scope.clientId=clientId;
        $scope.purchaseOrderId=purchaseOrderId;
        $scope.projId=projId;
        $scope.contractId=contractId;
        //alert("at PO factory - clientId:"+JSON.stringify($scope.clientId)+
                //" : purchaseOrderId : "+JSON.stringify($scope.purchaseOrderId)+
                //" : projId : "+JSON.stringify($scope.projId)+
                //" : contractId : "+JSON.stringify($scope.contractId));

				$scope.typeOfPO = typeOfPO;
				//alert("at PO factory - typeOfPO:"+JSON.stringify($scope.typeOfPO));

				if($scope.typeOfPO == "RepeatPO")
				{
				  $scope.pageTitle = "Generation Of Repeat Purchase Order";
				}

        //alert("Updated PageTitle :"+JSON.stringify($scope.pageTitle));

				$scope.getAppendicesDetails = function () {
					console.log("getAppendicesDetails() function");
					var popupDetails = RFQAppendenciesFactory.getPurchaseOrderDetails($scope.selectedData, $scope.userProjMap, precontractDataWithScheduleItems);
					popupDetails.then(function (data) {
						$scope.userProjMap = userProjMap;
						$scope.selectedData = selectedData;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
					});
				}; $scope.singlePOData = {
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

				$scope.generateSinglePO = function () {
					blockUI.start();

					if($scope.typeOfPO == "RepeatPO")
					{
					    var savereq = {
                  //"preContractTO": preContractTO,
                  //"approvedCompanyMap": approvedCompanyMap,
                  //"projId": preContractTO.projId,
                  //"poDetailsTO": $scope.singlePOData,getPurchaseOrderSearch(searchProject.projIds)
                  //"poStartDate": poStartDate,
                  //"poFinishtDate": poFinishDate,
                  //"poProcureType": poProcureType,
                  "typeOfPO" : $scope.typeOfPO,
                  "clientId" : $scope.clientId,
                  "purchaseOrderId" : $scope.purchaseOrderId,
                  "projId" : $scope.projId,
                  "contractId" : $scope.contractId
                };
                PreContractExternalService.saveSinglePurchaseOrder(savereq).then(
                  function (data) {
                    blockUI.stop();
                      //alert("Repeat PO savereq :"+JSON.stringify(savereq));
                    var succMsg = GenericAlertService.alertMessageModal("Repeat Purchase Order generated successfully", "Info");
                    succMsg.then(function () {
                      $scope.closeThisDialog(viewBidderFactoryPopUp);
                    });
                    approvedCompanyMap = {};
                  },
                  function (error) {
                    blockUI.stop();
                    GenericAlertService.alertMessage("Failed to generated Repeat Purchase Order", "Error");
                  });
					}else
					{
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
                  "poDetailsTO": $scope.singlePOData,
                  "poStartDate": poStartDate,
                  "poFinishtDate": poFinishDate,
                  "poProcureType": poProcureType,
                  "typeOfPO" : $scope.typeOfPO
                };
                PreContractExternalService.saveSinglePurchaseOrder(savereq).then(
                  function (data) {
                    blockUI.stop();
                    var succMsg = GenericAlertService.alertMessageModal("Purchase Order generated successfully", "Info");
                    succMsg.then(function () {
                      $scope.closeThisDialog(viewBidderFactoryPopUp);
                    });
                    approvedCompanyMap = {};
                  },
                  function (error) {
                    blockUI.stop();
                    GenericAlertService.alertMessage("Failed to generated Purchase Order", "Error");
                  });
              }
					}// else for BAU
				};

				function saveGeneratePODetails() {
					var req = $scope.singlePOData;
					req.purchaseOrderId = $scope.po.id;
					PurchaseOrderService.savePurchaseOrderDetails(req).then(
						function (data) {
							blockUI.stop();
							var succMsg = GenericAlertService.alertMessageModal("Purchase order details saved successfully", 'Info');
							succMsg.then(function () {
								$scope.closeThisDialog(viewBidderFactoryPopUp);
							});
						},
						function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Purchase order details are failed to Save', 'Error');
						});
				};

				function getPurchaseOrdersByPrecontractCmpIdAndProjId() {
					const cmpIdArray = new Array();


					// Manpower
					if (precontractDataWithScheduleItems.preContractTO.preContractEmpDtlTOs) {
						for (const empDtlTO of precontractDataWithScheduleItems.preContractTO.preContractEmpDtlTOs) {
							for (const empCmpTO of empDtlTO.preContractsEmpCmpTOs) {
								if (empCmpTO.approveFlag && !cmpIdArray.includes(empCmpTO.preContractCmpId)) {
									cmpIdArray.push(empCmpTO.preContractCmpId);
								}
							}
						}
					}

					// Plant
					if (precontractDataWithScheduleItems.preContractTO.preContractPlantDtlTOs) {
						for (const plantDtlTO of precontractDataWithScheduleItems.preContractTO.preContractPlantDtlTOs) {
							for (const plantCmpTO of plantDtlTO.preContractPlantCmpTOs) {
								if (plantCmpTO.approveFlag && !cmpIdArray.includes(plantCmpTO.preContractCmpId)) {
									cmpIdArray.push(plantCmpTO.preContractCmpId);
								}
							}
						}
					}

					// Material
					if (precontractDataWithScheduleItems.preContractTO.preContractMaterialDtlTOs) {
						for (const materialDtlTO of precontractDataWithScheduleItems.preContractTO.preContractMaterialDtlTOs) {
							for (const materialCmpTO of materialDtlTO.preContractMaterialCmpTOs) {
								if (materialCmpTO.approveFlag && !cmpIdArray.includes(materialCmpTO.preContractCmpId)) {
									cmpIdArray.push(materialCmpTO.preContractCmpId);
								}
							}
						}
					}

					// Service
					if (precontractDataWithScheduleItems.preContractTO.preContractServiceDtlTOs) {
						for (const serviceDtlTO of precontractDataWithScheduleItems.preContractTO.preContractServiceDtlTOs) {
							for (const serviceCmpTO of serviceDtlTO.preContractServiceCmpTOs) {
								if (serviceCmpTO.approveFlag && !cmpIdArray.includes(serviceCmpTO.preContractCmpId)) {
									cmpIdArray.push(serviceCmpTO.preContractCmpId);
								}
							}
						}
					}

					// SOW validations
					if (precontractDataWithScheduleItems.preContractTO.precontractSowDtlTOs) {
						for (const sowDtlTO of precontractDataWithScheduleItems.preContractTO.precontractSowDtlTOs) {
							for (const sowCmpTO of sowDtlTO.precontractSowCmpTOs) {
								if (sowCmpTO.approveFlag && !cmpIdArray.includes(sowCmpTO.preContractCmpId)) {
									cmpIdArray.push(sowCmpTO.preContractCmpId);
								}
							}
						}
					}
					if(typeOfPO == "singlepo"){
					var req = {
						"precontractCmpIds": cmpIdArray,
						"projId": $scope.selectedData.projId,
					}
					}else if(typeOfPO == "RepeatPO"){
					var req = {
						"precontractCmpIds": cmpIdArray,
						"projId": $rootScope.selectedPurchaseOrderData.projId,
					}
					};
					PurchaseOrderService.getPurchaseOrdersByPrecontractCmpIdAndProjId(req).then(function (data) {
						var po = data.purchaseOrderTOs[0];
						if (po) {
						console.log(po)
							$scope.po.code = po.code;
							$scope.po.id = po.id;
							$scope.singlePOData = po.poDetailsTO;
							$scope.po.createdOn = $filter('date')(new Date(po.createdOn), "dd-MMM-yyyy");
							$scope.selectedData.date = $scope.po.createdOn;
							$scope.selectedData.poId = po.id;
						} else {
							$scope.po.createdOn = $filter('date')(new Date(), "dd-MMM-yyyy");
							$scope.po.code = undefined;
							$scope.po.id = null;
							$scope.selectedData.poId = null;
						}

					});
				}

				function getPurchaseOrderScheduleItems() {
					if(typeOfPO == "singlepo"){
					var req = {
						"contractId": $scope.selectedData.id,
						"projId": $scope.selectedData.projId,
						"status": 1
					}
					}else if(typeOfPO == "RepeatPO"){
					var req = {
						"contractId": $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
						"projId": $rootScope.selectedPurchaseOrderData.projId,
						"status": 1
					}
					};
					PreContractExternalService.getExternalPreContractPopupOnLoad(req).then(function (data) {
						precontractDataWithScheduleItems = data;
						getPurchaseOrdersByPrecontractCmpIdAndProjId();
					});

				};

				function getApprovedCompaniesMap() {
					var approvedCompanyMap = {};
					// reset dates
					poStartDate = new Date();
					poFinishDate = null;
					poProcureType = "";

					if (precontractDataWithScheduleItems.preContractTO.preContractEmpDtlTOs) {
						var manPowerProcureType = "";
						angular.forEach(precontractDataWithScheduleItems.preContractTO.preContractEmpDtlTOs, function (empDtlTO, key) {
							updatePODates(empDtlTO.startDate, empDtlTO.finishDate);
							angular.forEach(empDtlTO.preContractsEmpCmpTOs, function (empCmpTO, key) {
								if ((approvedCompanyMap == null || approvedCompanyMap[empCmpTO.preContractCmpId] == null) && empCmpTO.approveFlag) {
									approvedCompanyMap[empCmpTO.preContractCmpId] = empCmpTO.preContractCmpId;
									manPowerProcureType = "#E";
									$scope.singlePOData.issuedManPowerQuantity = empCmpTO.quantity;
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
								if ((approvedCompanyMap == null || approvedCompanyMap[plantCmpTO.preContractCmpId] == null) && plantCmpTO.approveFlag) {
									approvedCompanyMap[plantCmpTO.preContractCmpId] = plantCmpTO.preContractCmpId;
									plantProcureType = "#P";
									$scope.singlePOData.issuedPlantsQuantity = plantCmpTO.quantity;
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
								if ((approvedCompanyMap == null || approvedCompanyMap[materialCmpTO.preContractCmpId] == null) && materialCmpTO.approveFlag) {
									approvedCompanyMap[materialCmpTO.preContractCmpId] = materialCmpTO.preContractCmpId;
									materialProcureType = "#M";
									$scope.singlePOData.issuedMaterialsQuantity = materialCmpTO.quantity;
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
								if ((approvedCompanyMap == null || approvedCompanyMap[serviceCmpTO.preContractCmpId] == null) && serviceCmpTO.approveFlag) {
									approvedCompanyMap[serviceCmpTO.preContractCmpId] = serviceCmpTO.preContractCmpId;
									serviceProcureType = "#S";
									$scope.singlePOData.issuedServicesQuantity = serviceCmpTO.quantity;
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
								if ((approvedCompanyMap == null || approvedCompanyMap[sowCmpTO.preContractCmpId] == null) && sowCmpTO.approveFlag) {
									approvedCompanyMap[sowCmpTO.preContractCmpId] = sowCmpTO.preContractCmpId;
									sowProcureType = "#SOW";
									$scope.singlePOData.issuedSOWQuantity = sowCmpTO.quantity;
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
					$scope.singlePOData.vendorName = null;
					$scope.singlePOData.vendorDesignation = null;
					var cmpGetReq = {
						"status": 1,
						"preContractId": $scope.selectedData.id

					};
					PreContractExternalService.getPreContratCompanies(cmpGetReq).then(
						function (data) {
							preContractCmpTOs = data.preContractCmpTOs;
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
							var contactDetails = vendorDetails.contactsMap[data.preContractCmpTOs[0].contactId];
							if (contactDetails) {
								// $scope.singlePOData.acceptedBy = contactDetails.contactName;
								$scope.singlePOData.vendorName = contactDetails.contactName;
								$scope.singlePOData.vendorDesignation = contactDetails.designation;
							}

							$scope.preContractData.vendorDetails = groupedDetails;

						},
						function (error) {
							GenericAlertService.alertMessage("Error occured while getting Project Material classes", 'Error');
						});

				};
				function getClientDetails() {
					$scope.singlePOData.issuerName = null;
					$scope.singlePOData.issuerDesignation = null;
					Principal.identity().then(function (acc) {
						var clientReq = {
							"clientId": acc.clientId
						};
						$scope.singlePOData.issuerDesignation = acc.designation;
						$scope.singlePOData.issuerName = acc.username;
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

				getPreContractCompanies();
				getClientDetails();
				getPurchaseOrderScheduleItems();
			}]
		});
		return deferred.promise;
	};
	return service;

}]);
