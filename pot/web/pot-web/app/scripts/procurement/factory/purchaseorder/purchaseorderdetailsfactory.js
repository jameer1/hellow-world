'use strict';

app
		.factory(
				'PurchaseOrderDetailsFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PurchaseOrderService", "blockUI", "GenericAlertService","PreContractStoreFactory", function(ngDialog, $q, $filter, $timeout, $rootScope,
						PurchaseOrderService, blockUI,GenericAlertService,PreContractStoreFactory) {
					var viewBidderFactoryPopUp;
					var service = {};
					service.getPurchaseOrderDetails = function(data,purchaseOrder) {
						var deferred = $q.defer();
						viewBidderFactoryPopUp = ngDialog
								.open({
									template : 'views/procurement/purchaseorders/purchaseorderdetails.html',
									className : 'ngdialog-theme-plain ng-dialogueCustom1',
									showClose : false,
									closeByDocument : false,
									controller : [
											'$scope',
											function($scope) {
												$scope.bidderDatamoreFlag = 0;
												$scope.bidderDatamoreFlags = 0;
												$scope.manpowerFlag=true;
												$scope.plantFlag=true;
												$scope.materialFlag=true;
												$scope.serviceFlag=true;
												$scope.sowFlag=true;

												$scope.companyId=purchaseOrder.preContractCmpTO.companyId;
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
												var approvedCompanyMap = {};
                        $scope.purchaseordrePoNumer=$rootScope.selectedPurchaseOrderData;
												$scope.expandCollapseManpower=function(manpowerFlag)
												{
													$scope.manpowerFlag=!manpowerFlag;
												},
												$scope.expandCollapsePlant=function(plantFlag)
												{
													$scope.plantFlag=!plantFlag;
												},
												$scope.expandCollapseMaterial=function(materialFlag)
												{
													$scope.materialFlag=!materialFlag;
												},
												$scope.expandCollapseService=function(serviceFlag)
												{
													$scope.serviceFlag=!serviceFlag;
												},
												$scope.expandCollapseSow=function(sowFlag)
												{
													$scope.sowFlag=!sowFlag;
												},


														$scope.regeneratePurchaseOrder = function(
																apprvStatus) {
															$scope.validateBid();
															var req = {
																	"preContractTO" : $scope.preContractData,
																	"preContractCmpId" : purchaseOrder.preContractCmpTO.id,
																	"purchaseOrderId":purchaseOrder.id,
																	"projId":purchaseOrder.projId,
																	"status":1
																};
															PurchaseOrderService
															.regeneratePurchaseOrder(
																req)
															.then(
																	function(
																			data) {
																		GenericAlertService
																		.alertMessage(
																				"Purchase orders approved successfully",'Info');
																		ngDialog.close();

																	},

																	function(
																			error) {
																		GenericAlertService
																				.alertMessage(
																						"Purchase order failed while approving items",
																						"Error");
																	});

														},

                      //-- for RepeatPO Started
                      $scope.selectDeliveryPlace = function(storeLabelKeyTO, projStoreLabelKeyTO) {
                                //alert('Vzy selected selectDeliveryPlace-');
                                //alert(purchaseOrder.projId);
                      					var storeYardPopup = PreContractStoreFactory.getStocks(purchaseOrder.projId);
                      					storeYardPopup.then(function(data) {
                      					//alert(data);
                      					//alert(data.type);
                      						if (data.type == 2) {
                      							projStoreLabelKeyTO.id = data.storeStockTO.id;
                      							projStoreLabelKeyTO.code = data.storeStockTO.code;
                      						  storeLabelKeyTO.id = 0;
                      						} else if (data.type == 1){
                      							storeLabelKeyTO.id = data.storeStockTO.id;
                      							storeLabelKeyTO.code = data.storeStockTO.code;
                      							projStoreLabelKeyTO.id = 0;

                      						}
                      						console.log('projStoreLabelKeyTO.id:'+projStoreLabelKeyTO.id);
                      						console.log('projStoreLabelKeyTO.code:'+projStoreLabelKeyTO.code);

                      						console.log('storeLabelKeyTO.id:'+storeLabelKeyTO.id);
                      						console.log('storeLabelKeyTO.code:'+storeLabelKeyTO.code);

                      					}, function(error) {
                      						GenericAlertService.alertMessage("Error occured while getting Store Delivery Details", 'Error');
                      					});
                      				},
                      $scope.saveDetails=function(preContractData,isApproved){
                        //alert(' regeneratePurchaseOrder ');
                        //$scope.validateBid();
                        //console.log(" ProcurementPoRepeatpoTOs " +JSON.stringify($scope.preContractData))
                        console.log('Saving the data preContractData $scope.preContractData.preContractServiceDtlTOs' );
                        console.log($scope.preContractData);
                        //console.log($scope.preContractEmpDtlTOs);
                        //console.log($scope.preContractData.preContractServiceDtlTOs);
                        //alert(preContractData.reqUsrId);
                        //alert(isApproved);
                        //alert("PurchaseOrder Id"+JSON.stringify($rootScope.selectedPurchaseOrderData.id));
                        var aapprovedById=null;
                        if(Boolean(isApproved))
                        {
                          aapprovedById = preContractData.reqUsrId;
                           var req = {
                        "procurementPoRepeatpoTO" : $scope.preContractData,
                        "preContractTO" : $scope.preContractData,
                        "preContractCmpId" : purchaseOrder.preContractCmpTO.id,
                        "purchaseOrderId": $rootScope.selectedPurchaseOrderData.id, //purchaseOrder.id,
                        "projId":purchaseOrder.projId,
                        "approvedBy": aapprovedById,
                        "status":1
                        };
                        PurchaseOrderService.saveprocurementporepeatpo(req)
                        .then(function(data) {
                           //alert(req);
                          console.log('PurchaseOrderService.saveprocurementporepeatpo req');
                          console.log(req);

                          console.log('PurchaseOrderService.saveprocurementporepeatpo data');
                          console.log(data);

                          GenericAlertService.alertMessage("Repeat PO Approved successfully",'Info');
                          ngDialog.close();
                          
                      },function(error) {
                          GenericAlertService.alertMessage("Repeat Po  order failed while SavingS items","Error");
                        });
                        }else{
                      //$scope.preContractData.preContractServiceDtlTOs
                        var req = {
                        "procurementPoRepeatpoTO" : $scope.preContractData,
                        "preContractTO" : $scope.preContractData,
                        "preContractCmpId" : purchaseOrder.preContractCmpTO.id,
                        "purchaseOrderId": $rootScope.selectedPurchaseOrderData.id, //purchaseOrder.id,
                        "projId":purchaseOrder.projId,
                        "approvedBy": aapprovedById,
                        "status":1
                        };
                        PurchaseOrderService.saveprocurementporepeatpo(req)
                        .then(function(data) {
                           //alert(req);
                          console.log('PurchaseOrderService.saveprocurementporepeatpo req');
                          console.log(req);

                          console.log('PurchaseOrderService.saveprocurementporepeatpo data');
                          console.log(data);

                          GenericAlertService.alertMessage("Repeat PO saved successfully",'Info');
                          ngDialog.close();
                          
                      },function(error) {
                          GenericAlertService.alertMessage("Repeat Po  order failed while SavingS items","Error");
                        });}
                   },
                  //-- for Repeat PO Ended
														$scope.validateBid=function()
														{
															if ($scope.preContractData.preContractEmpDtlTOs) {
																angular
																.forEach(
																		$scope.preContractData.preContractEmpDtlTOs,
																		function(
																				empDtlTO,
																				key) {
																			var quantity = 0;
																			var apprvQuantity = 0;
																			var manpowerFalg = false;
																			var count=0;
																			angular
																					.forEach(
																							empDtlTO.preContractsEmpCmpTOs,
																							function(
																									empCmpTO,
																									key) {
																								if (empCmpTO.approveFlag) {
																									count=count+1;
																									if(count > 0)
																										{
																											if(empCmpTO.quantity <=0)
																												{
																												GenericAlertService
																												.alertMessage(
																														"Please enter manpower items approval quantity for bid",
																														"Error");
																												forEach.break();
																												apprvQuantity=0;
																												}
																										}else
																											{
																												apprvQuantity=empDtlTO.quantity;
																											}
																									quantity += parseInt(empCmpTO.quantity);
																									manpowerFalg=true;
																								}

																							});

																			if (empDtlTO.quantity < quantity) {
																				GenericAlertService
																						.alertMessage(
																								"Manpower Approval quantity cannot be greater than actual quantity",
																								"Error");
																				forEach.break();
																			}

																			if (!manpowerFalg) {
																				GenericAlertService
																				.alertMessage(
																						"Please select Manpower item for bid for approval",
																						"Warning");
																				forEach.break();

																			}
																		});


															}
															if ($scope.preContractData.preContractPlantDtlTOs) {
																angular
																		.forEach(
																				$scope.preContractData.preContractPlantDtlTOs,
																				function(
																						plantDtlTO,
																						key) {
																					var quantity = 0;
																					var apprvQuantity = 0;
																					var plantFalg = false;
																					var count=0;
																					angular
																							.forEach(
																									plantDtlTO.preContractPlantCmpTOs,
																									function(
																											plantCmpTO,
																											key) {
																										if (plantCmpTO.approveFlag) {
																											count=count+1;
																											if(count > 0)
																												{
																													if(plantCmpTO.quantity <=0)
																														{
																														GenericAlertService
																														.alertMessage(
																																"Please enter  plant items approval quantity for bid",
																																"Error");
																														forEach.break();
																														apprvQuantity=0;
																														}
																												}else
																													{
																														apprvQuantity=plantDtlTO.quantity;
																													}
																											quantity += parseInt(plantCmpTO.quantity);
																											plantFalg=true;
																										}

																									});

																					if (plantDtlTO.quantity < quantity) {
																						GenericAlertService
																								.alertMessage(
																										"Plant Approval quantity cannot be greater than actual quantity",
																										"Error");
																						forEach.break();
																					}

																					if (!plantFalg) {
																						GenericAlertService
																						.alertMessage(
																								"Please select plant item for bid for approval",
																								"Warning");
																						forEach.break();

																					}
																				});
															}

															if ($scope.preContractData.preContractMaterialDtlTOs) {
																angular
																.forEach(
																		$scope.preContractData.preContractMaterialDtlTOs,
																		function(
																				materialDtlTO,
																				key) {
																			var quantity = 0;
																			var apprvQuantity = 0;
																			var materialFalg = false;
																			var count=0;
																			angular
																					.forEach(
																							materialDtlTO.preContractMaterialCmpTOs,
																							function(
																									materialCmpTO,
																									key) {
																								if (materialCmpTO.approveFlag) {
																									count=count+1;
																									if(count > 0)
																										{
																											if(materialCmpTO.quantity <=0)
																												{
																												GenericAlertService
																												.alertMessage(
																														"Please enter material items approval quantity for bid",
																														"Error");
																												forEach.break();
																												apprvQuantity=0;
																												}
																										}else
																											{
																												apprvQuantity=materialDtlTO.quantity;
																											}
																									quantity += parseInt(materialCmpTO.quantity);
																									materialFalg=true;
																								}

																							});

																			if (materialDtlTO.quantity < quantity) {
																				GenericAlertService
																						.alertMessage(
																								"Material Approval quantity cannot be greater than actual quantity",
																								"Error");
																				forEach.break();
																			}

																			if (!materialFalg) {
																				GenericAlertService
																				.alertMessage(
																						"Please select Material item for bid for approval",
																						"Warning");
																				forEach.break();

																			}
																		});

															}
															if ($scope.preContractData.preContractServiceDtlTOs) {
																angular
																.forEach(
																		$scope.preContractData.preContractServiceDtlTOs,
																		function(
																				serviceDtlTO,
																				key) {
																			var quantity = 0;
																			var apprvQuantity = 0;
																			var serviceFalg = false;
																			var count=0;
																			angular
																					.forEach(
																							serviceDtlTO.preContractServiceCmpTOs,
																							function(
																									serviceCmpTO,
																									key) {
																								if (serviceCmpTO.approveFlag) {
																									count=count+1;
																									if(count > 0)
																										{
																											if(serviceCmpTO.quantity <=0)
																												{
																												GenericAlertService
																												.alertMessage(
																														"Please enter  service items approval quantity for bid",
																														"Error");
																												forEach.break();
																												apprvQuantity=0;
																												}
																										}else
																											{
																												apprvQuantity=serviceDtlTO.quantity;
																											}
																									quantity += parseInt(serviceCmpTO.quantity);
																									serviceFalg=true;
																									if(approvedCompanyMap==null || approvedCompanyMap[serviceCmpTO.preContractCmpId]==null)
																									{
																									  approvedCompanyMap[serviceCmpTO.preContractCmpId]=serviceCmpTO.preContractCmpId;
																									}
																								}

																							});

																			if (serviceDtlTO.quantity < quantity) {
																				GenericAlertService
																						.alertMessage(
																								"Service Approval quantity cannot be greater than actual quantity",
																								"Error");
																				forEach.break();
																			}

																			if (!serviceFalg) {
																				GenericAlertService
																				.alertMessage(
																						"Please select Service item for bid for approval",
																						"Warning");
																				forEach.break();

																			}
																		});

															}
															return true;
														}
											} ]
								});
						return deferred.promise;
					};
					return service;

				}]);
