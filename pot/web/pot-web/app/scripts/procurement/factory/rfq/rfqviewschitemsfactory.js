'use strict';

app
		.factory(
				'RfqSchItemsViewFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", "ProjCostCodeService", "StoreService", "PrecontractDocUploadFactory", "PreContractCostPopupFactory", "ProcurementSubCategoryFactory", "PreContractProjEmpClassFactory", "PreContractProjPlantClassFactory", "PreContractProjServiceClassFactory", "PreContractMaterialClassFactory", "PreContractStoreFactory", "PrecontractSOWFactory", "PreContractApproverFactory", "PrecontractSubmitApprRequestFactory", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService,
						GenericAlertService, ProjCostCodeService, StoreService,
						PrecontractDocUploadFactory, PreContractCostPopupFactory,
						ProcurementSubCategoryFactory, PreContractProjEmpClassFactory,
						PreContractProjPlantClassFactory, PreContractProjServiceClassFactory,
						PreContractMaterialClassFactory, PreContractStoreFactory,
						PrecontractSOWFactory, PreContractApproverFactory,
						PrecontractSubmitApprRequestFactory,generalservice) {
					var procInternalApprovalPopUp;
					var service = {};
					service.viewScheduleItemsDetails = function(data) {
						var deferred = $q.defer();

						var procInternalApprovalPopUp = ngDialog
								.open({
									template : 'views/procurement/RFQ/rfqschitemsviewpopup.html',
									className : 'ngdialog-theme-plain ng-dialogueCustom0',
									showClose : false,
									closeByDocument : false,
									controller : [
											'$scope',
											function($scope) {

												$scope.costcode = [];
												$scope.defaultcostcode = {};
												$scope.currentTab1 = null;
												$scope.contractTypes = generalservice.getPreContractTypes();
												$scope.currencyList = angular
														.copy(data.currencyTOs);
												$scope.projEmpClassmap = data.projEmpClassMap;
												$scope.projPlantClassmap = data.projPlantMap;
												$scope.projMaterialClassmap = data.projMaterialClassMap;
												$scope.projServiceClassmap = data.projServiceMap;
												$scope.projSOWMap = data.projSOWMap;
												$scope.storeClassmap = data.storeMap;
												$scope.projStoreClassmap = data.projStoreMap;
												$scope.projcostCodeMap = data.projCostItemMap;
												$scope.approverUserMap = data.usersMap;
												$scope.procureCategoryMap = data.procureCategoryMap;
												$scope.preContractObj = data.preContractTO;
												var deleteEmpDtlIds = [];
												var deletePlantDtlIds = [];
												var deleteMaterialDtlds = [];
												var deleteServiceDtlIds = [];
												var deleteSowDtlIds = [];
												var projId = data.preContractTO.projId;
												$scope.preContrctDetailTabs = [
														{
															title : 'Manpower',
															url : 'views/procurement/RFQ/rfqviewmanpower.html',
															rfqscheduleSeleniumLocator:'PurchaseOrders_RFQ_scheduleofitemsviewmanpower'
										
														},
														{
															title : 'Materials',
															url : 'views/procurement/RFQ/rfqviewmaterial.html',
															rfqscheduleSeleniumLocator:'PurchaseOrders_RFQ_scheduleofitemsviewmaterials'
														},
														{
															title : 'Plants',
															url : 'views/procurement/RFQ/rfqviewplants.html',
															rfqscheduleSeleniumLocator:'PurchaseOrders_RFQ_scheduleofitemsviewplant'

														},
														{
															title : 'Services',
															url : 'views/procurement/RFQ/rfqviewservices.html',
															rfqscheduleSeleniumLocator:'PurchaseOrders_RFQ_scheduleofitemsviewservices'
														},
														{
															title : 'Project Sub Contract',
															url : 'views/procurement/RFQ/rfqviewsow.html',
															rfqscheduleSeleniumLocator:'PurchaseOrders_RFQ_scheduleofitemsviewprojectsubcontract'
														} ];

												$scope.onClickTab1 = function(tab) {
													$scope.currentTab1 = tab.url;
													$scope.isActiveTab1($scope.currentTab1);
												}
												$scope.isActiveTab1 = function(tabUrl) {
													return tabUrl == $scope.currentTab1;
												}
												$scope.onClickTab1($scope.preContrctDetailTabs[0]);

														$scope.projCostCodeDetails = function(
																projCostCodeLabelKeyTO) {
															var projCostCodePopup = PreContractCostPopupFactory
																	.getProjCostDetails(projId);
															projCostCodePopup
																	.then(
																			function(data) {
																				projCostCodeLabelKeyTO.id = data.projCostStmtDtlTO.id;
																				projCostCodeLabelKeyTO.referenceId = data.projCostStmtDtlTO.costId;
																				projCostCodeLabelKeyTO.code = data.projCostStmtDtlTO.code;
																				projCostCodeLabelKeyTO.name = data.projCostStmtDtlTO.name;

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting project cost codes",
																								'Error');
																			});
														},

														$scope.selectProcureSubCategory = function(
																type, item) {
															var req = {
																"procureId" : type,
																"status" : 1
															};
															var manpowerCategoryDetailsPopup = ProcurementSubCategoryFactory
																	.getProcureCatgs(req);
															manpowerCategoryDetailsPopup
																	.then(
																			function(data) {
																				item.procureSubCatgId = data.selectedSubcategory.proCatgId;
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								'Error occurred while selecting procurement sub-catagories',
																								"Error");
																			});
														},
														$scope.projEmpClassDetails = function(
																projEmpClassLabelKeyTO) {
															var projEmpClassDetailsPopup = PreContractProjEmpClassFactory
																	.getProjEmpClasses();
															projEmpClassDetailsPopup
																	.then(
																			function(data) {
																				projEmpClassLabelKeyTO.id = data.projEmpclassTO.id;
																				projEmpClassLabelKeyTO.code = data.projEmpclassTO.code;
																				projEmpClassLabelKeyTO.name = data.projEmpclassTO.name;
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting project employee classes",
																								'Error');
																			});
														},
														$scope.projPlantClassDetails = function(
																projPlantClassLabelKeyTO) {
															var projPlantClassDetailsPopup = PreContractProjPlantClassFactory
																	.getProjPlantClasses(projId);
															projPlantClassDetailsPopup
																	.then(
																			function(data) {
																				projPlantClassLabelKeyTO.id = data.projPlantClassTO.id;
																				projPlantClassLabelKeyTO.code = data.projPlantClassTO.code;
																				projPlantClassLabelKeyTO.name = data.projPlantClassTO.name;
																				projPlantClassLabelKeyTO.unitOfMeasure = data.projPlantClassTO.measureUnitTO.name;

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								'Error occurred while selecting procurement sub-catagories',
																								"Error");
																			});
														},

														$scope.projmaterialClassDetails = function(
																MaterialClassLabelKeyTO) {
															var projMaterialClassDetailsPopup = PreContractMaterialClassFactory
																	.getMaterialSubGroups();
															projMaterialClassDetailsPopup
																	.then(
																			function(data) {
																				MaterialClassLabelKeyTO.id = data.materialSubGroupTO.id;
																				MaterialClassLabelKeyTO.code = data.materialSubGroupTO.code;
																				MaterialClassLabelKeyTO.name = data.materialSubGroupTO.name;
																				MaterialClassLabelKeyTO.unitOfMeasure = data.materialSubGroupTO.measureUnitTO.name;

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting project material classes",
																								'Error');
																			});
														},

														$scope.serviceClassDetails = function(
																serviceClassLabelKeyTO) {
															var serviceClassificationPopup = PreContractProjServiceClassFactory
																	.getServiceClasses();
															serviceClassificationPopup
																	.then(
																			function(data) {
																				serviceClassLabelKeyTO.id = data.serviceClassTO.id;
																				serviceClassLabelKeyTO.code = data.serviceClassTO.code;
																				serviceClassLabelKeyTO.name = data.serviceClassTO.name;
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting service classes",
																								'Error');
																			});
														},

														$scope.selectDeliveryPlace = function(
																storeLabelKeyTO,
																projStoreLabelKeyTO) {
															var storeYardPopup = PreContractStoreFactory
																	.getStocks(projId);
															storeYardPopup
																	.then(
																			function(data) {

																				if (data.type === 2) {
																					projStoreLabelKeyTO.id = data.storeStockTO.id;
																					projStoreLabelKeyTO.code = data.storeStockTO.code;
																				} else {
																					storeLabelKeyTO.id = data.storeStockTO.id;
																					storeLabelKeyTO.code = data.storeStockTO.code;
																				}
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting Store Delivery Details",
																								'Error');
																			});
														},
														$scope.addManPowerRows = function() {
															$scope.finishDate = new Date();
															var toDate = new Date(
																	$scope.finishDate.getFullYear(),
																	$scope.finishDate.getMonth() + 1,
																	0);
															$scope.startDate = new Date(
																	$scope.finishDate);
															$scope.startDate
																	.setDate($scope.finishDate
																			.getDate()
																			- toDate.getDate());
															$scope.preContractObj.preContractEmpDtlTOs
																	.push({
																		"select" : false,
																		"itemCode" : null,
																		"itemDesc" : null,
																		"preContractId" : $scope.preContractObj.id,
																		"projEmpLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"storeLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projStoreLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projCostLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"quantity" : null,
																		"startDate" : '',
																		"finishDate" : '',
																		"apprStatus" : null,
																		"projId" : projId
																	});
														},

														$scope.addPlantRows = function() {
															$scope.finishDate = new Date();
															var toDate = new Date(
																	$scope.finishDate.getFullYear(),
																	$scope.finishDate.getMonth() + 1,
																	0);
															$scope.startDate = new Date(
																	$scope.finishDate);
															$scope.startDate
																	.setDate($scope.finishDate
																			.getDate()
																			- toDate.getDate());
															$scope.preContractObj.preContractPlantDtlTOs
																	.push({
																		"select" : false,
																		"itemCode" : '',
																		"itemDesc" : '',
																		"preContractId" : $scope.preContractObj.id,
																		"projPlantLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null,
																			"unitOfMeasure" : null
																		},
																		"storeLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projStoreLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projCostLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"quantity" : null,
																		"startDate" : '',
																		"finishDate" : '',
																		"apprStatus" : null,
																		"projId" : projId
																	});
														},
														$scope.addMaterialRows = function() {
															$scope.finishDate = new Date();
															var toDate = new Date(
																	$scope.finishDate.getFullYear(),
																	$scope.finishDate.getMonth() + 1,
																	0);
															$scope.startDate = new Date(
																	$scope.finishDate);
															$scope.startDate
																	.setDate($scope.finishDate
																			.getDate()
																			- toDate.getDate());
															$scope.preContractObj.preContractMaterialDtlTOs
																	.push({
																		"select" : false,
																		"itemCode" : '',
																		"itemDesc" : '',
																		"preContractId" : $scope.preContractObj.id,
																		"projMaterialLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null,
																			"unitOfMeasure" : null
																		},
																		"storeLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projStoreLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projCostLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"quantity" : null,
																		"startDate" : '',
																		"finishDate" : '',
																		"apprStatus" : null,
																		"projId" : projId
																	});
														},
														$scope.addServiceRows = function() {
															$scope.finishDate = new Date();
															var toDate = new Date(
																	$scope.finishDate.getFullYear(),
																	$scope.finishDate.getMonth() + 1,
																	0);
															$scope.startDate = new Date(
																	$scope.finishDate);
															$scope.startDate
																	.setDate($scope.finishDate
																			.getDate()
																			- toDate.getDate());
															$scope.preContractObj.preContractServiceDtlTOs
																	.push({
																		"select" : false,
																		"itemCode" : '',
																		"itemDesc" : '',
																		"preContractId" : $scope.preContractObj.id,
																		"projServiceLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"storeLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projStoreLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"projCostLabelKey" : {
																			"id" : null,
																			"code" : null,
																			"name" : null
																		},
																		"quantity" : null,
																		"startDate" : '',
																		"finishDate" : '',
																		"apprStatus" : null,
																		"projId" : projId
																	});
														},
														$scope.addProjSOWItems = function(item) {
															var existedSOWMap = [];
															angular
																	.forEach(
																			$scope.preContractObj.precontractSowDtlTOs,
																			function(value, key) {
																				existedSOWMap[value.sowId] = true;
																			});
															var sowPopup = PrecontractSOWFactory
																	.getSOWDetails(projId,
																			existedSOWMap);
															sowPopup
																	.then(
																			function(data) {
																				angular
																						.forEach(
																								data.projSOWItemTOs,
																								function(
																										value,
																										key) {
																									$scope.preContractObj.precontractSowDtlTOs
																											.push({
																												"select" : false,
																												"sowId" : value.id,
																												"itemCode" : '',
																												"itemDesc" : '',
																												"preContractId" : $scope.preContractObj.id,
																												
																												"quantity" : null,
																												"startDate" : '',
																												"finishDate" : '',
																												"apprStatus" : null
																											});
																								});

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting sow details",
																								'Error');
																			});
														},
														$scope.deleteManPowerDetails = function() {
															deleteEmpDtlIds = [];
															var tempInternalRequest = [];
															var flag = false;
															angular
																	.forEach(
																			$scope.preContractObj.preContractEmpDtlTOs,
																			function(value, key) {
																				if (!value.select) {
																					tempInternalRequest
																							.push(value);
																				} else {
																					if (value.id > 0) {
																						deleteEmpDtlIds
																								.push(value.id);
																					}
																					flag = true;
																				}
																			});
															if (!flag) {
																GenericAlertService
																		.alertMessage(
																				"Please select atleast one row to delete",
																				"Warning");

															}
															$scope.preContractObj.preContractEmpDtlTOs = tempInternalRequest;
														},
														$scope.deletePlantDetails = function() {
															deletePlantDtlIds = [];
															var tempInternalRequest = [];
															var flag = false;
															angular
																	.forEach(
																			$scope.preContractObj.preContractPlantDtlTOs,
																			function(value, key) {
																				if (!value.select) {
																					tempInternalRequest
																							.push(value);
																				} else {
																					if (value.id > 0) {
																						deletePlantDtlIds
																								.push(value.id);
																					}
																					flag = true;
																				}
																			});
															if (!flag) {
																GenericAlertService
																		.alertMessage(
																				"Please select atleast one row to delete",
																				"Warning");

															}
															$scope.preContractObj.preContractPlantDtlTOs = tempInternalRequest;
														},
														$scope.deleteMaterialDetails = function() {
															deleteMaterialDtlds = [];
															var tempInternalRequest = [];
															var flag = false;
															angular
																	.forEach(
																			$scope.preContractObj.preContractMaterialDtlTOs,
																			function(value, key) {
																				if (!value.select) {
																					tempInternalRequest
																							.push(value);
																				} else {
																					if (value.id > 0) {
																						deleteMaterialDtlds
																								.push(value.id);
																					}
																					flag = true;
																				}
																			});
															if (!flag) {
																GenericAlertService
																		.alertMessage(
																				"Please select atleast one row to delete",
																				"Warning");

															}
															$scope.preContractObj.preContractMaterialDtlTOs = tempInternalRequest;
														},
														$scope.deleteServiceDetails = function() {
															deleteServiceDtlIds = [];
															var tempInternalRequest = [];
															var flag = false;
															angular
																	.forEach(
																			$scope.preContractObj.preContractServiceDtlTOs,
																			function(value, key) {
																				if (!value.select) {
																					tempInternalRequest
																							.push(value);
																				} else {
																					if (value.id > 0) {
																						deleteServiceDtlIds
																								.push(value.id);
																					}
																					flag = true;
																				}
																			});
															if (!flag) {
																GenericAlertService
																		.alertMessage(
																				"Please select atleast one row to delete",
																				"Warning");

															}
															$scope.preContractObj.preContractServiceDtlTOs = tempInternalRequest;
														},

														$scope.deleteSowDetails = function() {
															deleteSowDtlIds = [];
															var tempInternalRequest = [];
															var flag = false;
															angular
																	.forEach(
																			$scope.preContractObj.precontractSowDtlTOs,
																			function(value, key) {
																				if (!value.select) {
																					tempInternalRequest
																							.push(value);
																				} else {
																					if (value.id > 0) {
																						deleteSowDtlIds
																								.push(value.id);
																					}
																					flag = true;
																				}
																			});
															if (!flag) {
																GenericAlertService
																		.alertMessage(
																				"Please select atleast one row to delete",
																				"Warning");

															}
															$scope.preContractObj.precontractSowDtlTOs = tempInternalRequest;
														},

														$scope.saveInternalRequest = function(
																apprvStatus, contarctStageStatus) {
															var validateFalg = true;
															if (apprvStatus == 4) {
																var resultdata = $scope
																		.onHoldInternalRequest(
																				apprvStatus,
																				contarctStageStatus);
																resultdata.then(function(data) {
																	deferred.resolve(data);
																});
															}
															if (apprvStatus == 2) {
																validateFalg = $scope
																		.validateProcurmentDetails();
															}
															if (validateFalg) {
																PrecontractSubmitApprRequestFactory
																		.submitOrApproveInternalRequest(
																				$scope.preContractObj,
																				apprvStatus,
																				projId,
																				$scope.approverUserMap);
															}
														},
														$scope.onHoldInternalRequest = function(
																apprvStatus, contarctStageStatus) {
															var savereq = {
																"preContractTO" : $scope.preContractObj,
																"apprvStatus" : apprvStatus,
																"contarctStageStatus" : contarctStageStatus,
																"projId" : projId
															};
															PreContractInternalService
																	.saveInternalPreContracts(
																			savereq)
																	.then(
																			function(data) {
																				var succMsg = GenericAlertService
																						.alertMessageModal(
																								"Precontract put on-hold successfully ",
																								"Info");
																				succMsg
																						.then(function(
																								popData) {
																							$scope.preContractObj = data.preContractTOs[0];
																							deferred
																									.resolve($scope.preContractObj);
																						});

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured,Please try again",
																								"Error");
																			});

														},
														$scope.validateProcurmentDetails = function() {
															if ($scope.preContractObj.preContractEmpDtlTOs != null
																	&& $scope.preContractObj.preContractEmpDtlTOs.length > 0) {
																return true;
															} else if ($scope.preContractObj.preContractPlantDtlTOs != null
																	&& $scope.preContractObj.preContractPlantDtlTOs.length > 0) {
																return true;
															} else if ($scope.preContractObj.preContractMaterialDtlTOs != null
																	&& $scope.preContractObj.preContractMaterialDtlTOs.length > 0) {
																return true;
															} else if ($scope.preContractObj.preContractServiceDtlTOs != null
																	&& $scope.preContractObj.preContractServiceDtlTOs.length > 0) {
																return true;
															} else {
																GenericAlertService
																		.alertMessage(
																				"Please add atleast any one of procurement records",
																				"Warning");
															}
															return false;

														},
														$scope.validateTabsData = function() {
															if ($scope.procureForm.$valid) {
																return true;
															} else {
																GenericAlertService
																		.alertMessage(
																				"Please enter manpower details",
																				"Warning");
																return false;
															}

														},
														$scope.saveManPowerDetails = function() {
															var savereq = {
																"preContractEmpDtlTOs" : $scope.preContractObj.preContractEmpDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"empDtlIds" : deleteEmpDtlIds
															};
															PreContractInternalService
																	.saveManPowerDetails(savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.preContractEmpDtlTOs = data.preContractEmpDtlTOs
																				GenericAlertService
																						.alertMessage(
																								"Precontract Manpower details are saved successfully",
																								"Info");
																			},

																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Precontract Manpower details are  Failed To save",
																								"Error");
																			});

														},

														$scope.savePlantDetails = function() {

															var savereq = {
																"preContractPlantDtlTOs" : $scope.preContractObj.preContractPlantDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"plantDtlIds" : deletePlantDtlIds,
															};
															PreContractInternalService
																	.savePreContratPlants(savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.preContractPlantDtlTOs = data.preContractPlantDtlTOs

																				GenericAlertService
																						.alertMessage(
																								"Precontract Plant details are saved successfully",
																								"Info");
																			},

																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Precontract Plant details are  Failed To save",
																								"Error");
																			});

														},
														$scope.saveMaterialDetails = function() {

															var savereq = {
																"preContractMaterialDtlTOs" : $scope.preContractObj.preContractMaterialDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"materialDtlIds" : deleteMaterialDtlds
															};
															PreContractInternalService
																	.savePreContratMaterials(
																			savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs

																				GenericAlertService
																						.alertMessage(
																								"Precontract Material details are saved successfully",
																								"Info");
																			},

																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Precontract Material details are  Failed To save",
																								"Error");
																			});

														},
														$scope.saveServiceDetails = function() {

															var savereq = {
																"preContractServiceDtlTOs" : $scope.preContractObj.preContractServiceDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"serviceDtlIds" : deleteServiceDtlIds
															};
															PreContractInternalService
																	.savePreContratServices(savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.preContractServiceDtlTOs = data.preContractServiceDtlTOs

																				GenericAlertService
																						.alertMessage(
																								"Precontract Services details are saved successfully",
																								"Info");
																			},

																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Precontract Services details are  Failed To save",
																								"Error");
																			});

														},
														$scope.saveSOWDetails = function() {

															var savereq = {
																"precontractSowDtlTOs" : $scope.preContractObj.precontractSowDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"sowDtlIds" : deleteSowDtlIds
															};
															PreContractInternalService
																	.savePreContractSOWTypes(savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.precontractSowDtlTOs = data.precontractSowDtlTOs

																				GenericAlertService
																						.alertMessage(
																								"Precontract SOW details are saved successfully",
																								"Info");
																			},

																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Precontract SOW details are  Failed To save",
																								"Error");
																			});

														},
														$scope.uploadContractDocs = function(
																contractId) {
															PrecontractDocUploadFactory
																	.uplodPreContractDocs(contractId);
														}

											} ]
								});
						return deferred.promise;
					};
					return service;

				}]);
