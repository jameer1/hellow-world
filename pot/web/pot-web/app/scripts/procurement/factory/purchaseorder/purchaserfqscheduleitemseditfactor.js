'use strict';

app
		.factory(
				'PurchaseRfqScheduleItemsEditFactor',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractExternalService", "RFQService", "PreContractAddCompanyFactory", "PreContractApproverFactory", "GenericAlertService", "FileUploader", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, PreContractExternalService,
						RFQService, PreContractAddCompanyFactory, PreContractApproverFactory,
						GenericAlertService, FileUploader,generalservice) {
					var procExternalApprovalPopUp;
					var service = {};
					service.editPrecontractSchItems = function(data, preContractCmpTO,rfq) {
						var deferred = $q.defer();
						procExternalApprovalPopUp = ngDialog
								.open({
									template : 'views/procurement/purchaseorders/porfqschitemseditpopup.html',
									showClose : false,
									closeByDocument : false,
									controller : [
											'$scope',
											function($scope) {
												$scope.uploader = new FileUploader();
												$scope.currencyList = angular
														.copy(data.currencyTOs);
												$scope.contractTypes = generalservice.getPreContractTypes();
												$scope.projEmpClassmap = data.projEmpClassMap;
												$scope.projPlantClassmap = data.projPlantMap;
												$scope.projMaterialClassmap = data.projMaterialClassMap;
												$scope.projServiceClassmap = data.projServiceMap;
												$scope.storeClassmap = data.storeMap;
												$scope.projStoreClassmap = data.projStoreMap;
												$scope.projcostCodeMap = data.projCostItemMap;
												$scope.preContractObj = data.preContractTO;
												$scope.companyMap = data.companyMap;
												$scope.approverUserMap = data.usersMap;
												$scope.companyList = data.preContractTO.preContractCmpTOs;
												$scope.procureCategoryMap = data.procureCategoryMap;
												$scope.projSOWMap = data.projSOWMap;

												$scope.preContrctDetailTabs = [
														{
															title : 'Manpower',
															url : 'views/procurement/RFQ/rfqeditmanpower.html'
														},
														{
															title : 'Materials',
															url : 'views/procurement/RFQ/rfqeditmaterial.html'
														},
														{
															title : 'Plants',
															url : 'views/procurement/RFQ/rfqeditplants.html'
														},
														{
															title : 'Services',
															url : 'views/procurement/RFQ/rfqeditservices.html'
														},
														{
															title : 'Sub Contract',
															url : 'views/procurement/RFQ/rfqeditsow.html'
														} ];

												$scope.onClickTab1 = function(tab) {
													$scope.currentTab1 = tab.url;
													$scope.isActiveTab1($scope.currentTab1);
												}
												$scope.isActiveTab1 = function(tabUrl) {
													return tabUrl == $scope.currentTab1;
												}

												$scope.onClickTab1($scope.preContrctDetailTabs[0]);

														$scope.getUsersByModulePermission = function() {
															var approverFactoryPopup = [];
															approverFactoryPopup = PreContractApproverFactory
																	.getUsersByModulePermission();
															approverFactoryPopup
																	.then(
																			function(data) {
																				$scope.preContractObj.preContractReqApprTO.apprUserLabelkeyTO = data.approverUserTO;

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while gettting approver users",
																								'Error');
																			});
														},

														$scope.submitQuotation = function() {
															var savereq = {
																"id" : preContractCmpTO.id,
																"biddingStatus" : 'Closed',
																"projId" : $scope.preContractObj.projId

															};
															RFQService
																	.submitBidQuotation(savereq)
																	.then(
																			function(data) {
																				GenericAlertService
																						.alertMessageModal(
																								"Quotation submitted successfully",
																								"Info");
																				deferred
																				.resolve(returnObj);
																		$scope
																				.closeThisDialog();

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error ocurred while sbmitting quotation",
																								"Error");
																			});
														},
														$scope.saveManPowerDetails = function() {

															var savereq = {
																"preContractEmpDtlTOs" : $scope.preContractObj.preContractEmpDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"external" : true
															};
															PreContractExternalService
																	.savePreContratEmpTypes(savereq)
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
																								"Internal Approval(s) is/are Failed To save",
																								"error");
																			});

														},

														$scope.savePlantDetails = function() {

															var savereq = {
																"preContractPlantDtlTOs" : $scope.preContractObj.preContractPlantDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"external" : true
															};
															PreContractExternalService
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
																								"Internal Approval(s) is/are Failed To save",
																								"error");
																			});

														},
														$scope.saveMaterialDetails = function() {

															var savereq = {
																"preContractMaterialDtlTOs" : $scope.preContractObj.preContractMaterialDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"external" : true
															};
															PreContractExternalService
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
																								"Internal Approval(s) is/are Failed To save",
																								"error");
																			});

														},
														$scope.saveServiceDetails = function() {

															var savereq = {
																"preContractServiceDtlTOs" : $scope.preContractObj.preContractServiceDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"external" : true
															};
															PreContractExternalService
																	.savePreContratServices(savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.preContractServiceDtlTOs = data.preContractServiceDtlTOs

																				GenericAlertService
																						.alertMessage(
																								"Precontract Service details are saved successfully",
																								"Info");
																			},

																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Internal Approval(s) is/are Failed To save",
																								"error");
																			});

														},
														$scope.saveSOWDetails = function() {
															var savereq = {
																"precontractSowDtlTOs" : $scope.preContractObj.precontractSowDtlTOs,
																"preContractId" : $scope.preContractObj.id,
																"external" : true
															};
															PreContractExternalService
																	.savePreContractSOWTypes(savereq)
																	.then(
																			function(data) {
																				$scope.preContractObj.precontractSowDtlTOs = data.precontractSowDtlTOs;
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

														}

											} ]
								});
						return deferred.promise;
					};
					return service;

				}]);
