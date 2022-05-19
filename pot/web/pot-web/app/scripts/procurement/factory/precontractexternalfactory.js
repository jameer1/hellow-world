'use strict';

app
		.factory(
				'PreContractExternalFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractExternalService", "PreContractAddCompanyFactory", "PreContractApproverFactory", "GenericAlertService", "FileUploader", "PrecontractExternalApprRequestFactory", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, PreContractExternalService,
						PreContractAddCompanyFactory, PreContractApproverFactory,
						 GenericAlertService, FileUploader,
						PrecontractExternalApprRequestFactory,generalservice) {
					var procExternalApprovalPopUp;
					var service = {};
					service.procExternalApprovalPopUp = function(data) {
						var deferred = $q.defer();
						procExternalApprovalPopUp = ngDialog
								.open({
									template : 'views/procurement/pre-contracts/externalApproval/requestpopup.html',
									className : 'ngdialog-theme-plain ng-dialogueCustom1',
									showClose : false,
									closeByDocument : false,
									controller : [
											'$scope',
											function($scope) {

												$scope.uploader = new FileUploader();

												$scope.currencyList =  data.currencyList;
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
												$scope.projEmpCatgMap = data.projEmpCatgMap
												var projId = data.preContractTO.projId;
												$scope.preContractBidCostCodeSummaryTOs = [];
												if($scope.preContractObj.preContractType == "Engineering Services"){
													$scope.preContrctDetailTabs = [{
															title : 'Services',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalservices.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_services'
													}]
												}
												if($scope.preContractObj.preContractType == "Labour Hire Agreement"){
													$scope.preContrctDetailTabs = [{
															title : 'Manpower',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalmanpower.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_Manpower'
													}]
												}
												if($scope.preContractObj.preContractType == "Plant Hire Agreement"){
													$scope.preContrctDetailTabs = [{
															title : 'Plants',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalplants.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_plants'
													}]
												}
												if($scope.preContractObj.preContractType == "Supply Agreement"){
													$scope.preContrctDetailTabs = [{
															title : 'Materials',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalmaterial.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_materials'
													}]
												}
												if($scope.preContractObj.preContractType == "Purchase  Order"){
													$scope.preContrctDetailTabs = [{
															title : 'Manpower',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalmanpower.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_Manpower'
													},{
															title : 'Plants',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalplants.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_plants'
													},{
															title : 'Materials',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalmaterial.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_materials'
													}]
												}
												if($scope.preContractObj.preContractType == "Sub Contract agreement"){
													$scope.preContrctDetailTabs = [{
															title : 'Project Sub Contract',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalsow.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_projectsubcontract'
													}]
												}
												if($scope.preContractObj.preContractType == "Professional Services agreement"){
													$scope.preContrctDetailTabs = [{
															title : 'Services',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalservices.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_services'
													}]
												}
												/*$scope.preContrctDetailTabs = [
														{
															title : 'Manpower',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalmanpower.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_Manpower'
														},
														{
															title : 'Materials',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalmaterial.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_materials'
														},
														{
															title : 'Plants',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalplants.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_plants'
														},
														{
															title : 'Services',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalservices.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_services'
														},
														{
															title : 'Project Sub Contract',
															url : 'views/procurement/pre-contracts/externalApproval/precontractexternalsow.html',
															stage2requestSeleniumLocator: 'Stage2Approval_Scheduleofitems_view_projectsubcontract'
														} ];*/

												$scope.onClickTab1 = function(tab) {
													$scope.currentTab1 = tab.url;
													$scope.isActiveTab1($scope.currentTab1);
												}
												$scope.isActiveTab1 = function(tabUrl) {
													return tabUrl == $scope.currentTab1;
												}

												$scope.onClickTab1($scope.preContrctDetailTabs[0]);

														$scope.addCompany = function() {
															var addCompanypopup = [];
															var addCompanyService = {};
															var addCompanyServiceData = [];
															var companyData = [];
															var preContractCompanyMap = [];
															addCompanyServiceData = $scope
																	.getPreContractCompanies();

															addCompanyServiceData
																	.then(
																			function(data) {
																				companyData = data.precontractCmpData;
																				preContractCompanyMap = data.preContractCompanyMap;

																				addCompanypopup = PreContractAddCompanyFactory
																						.getCompanies(
																								$scope.preContractObj,
																								companyData,
																								preContractCompanyMap);
																				addCompanypopup
																						.then(
																								function(
																										data) {
																									$scope.preContractObj.preContractCmpTOs = data.preContractCmpTOs;
																								},
																								function(
																										error) {
																									GenericAlertService
																											.alertMessage(
																													"Error occured while adding companies to precontract",
																													'Error');
																								});

																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while adding companies to precontract",
																								'Error');
																			});

														},
														$scope.getPreContractCompanies = function() {
															var defferComapny = $q.defer();
															var cmpGetReq = {
																"status" : 1,
																"preContractId" : $scope.preContractObj.id

															};
															PreContractExternalService
																	.getPreContratCompanies(
																			cmpGetReq)
																	.then(
																			function(data) {
																				var returnCompanies = {
																					"precontractCmpData" : angular
																							.copy(data.preContractCmpTOs),
																					"preContractCompanyMap" : angular
																							.copy(data.preContractCompanyMap)
																				};
																				defferComapny
																						.resolve(returnCompanies);
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting Project Material classes",
																								'Error');
																			});
															return defferComapny.promise;
														},
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
														}

														/*$scope.saveExternalRequest = function(
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
																PrecontractExternalApprRequestFactory
																		.submitOrApproveInternalRequest(
																				$scope.preContractObj,
																				apprvStatus,
																				projId,
																				$scope.approverUserMap);
															}

														},*/
														/*$scope.onHoldInternalRequest = function(
																apprvStatus, contarctStageStatus) {
															var savereq = {
																"preContractTO" : $scope.preContractObj,
																"apprvStatus" : apprvStatus,
																"contarctStageStatus" : contarctStageStatus,
																"projId" : projId
															};
															PreContractExternalService
																	.saveExternalPreContracts(
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

														},*/
														/*$scope.validateProcurmentDetails = function() {
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

														},*/

														/*$scope.saveManPowerDetails = function() {

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
																								"Success",
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
																								"Success",
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
																								"Success",
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
																								"Success",
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
																"sowDtlIds" : deleteSowDtlIds
															};
															PreContractExternalService
																	.savePreContratServices(savereq)
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

														}*/

											} ]
								});
						return deferred.promise;
					};
					return service;

				}]);
