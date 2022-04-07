'use strict';

app.factory('PreContractBidanalysisFactory', ["ngDialog", "$q", "PreContractExternalService", "PreContractApproverFactory", "GenericAlertService", "generalservice", "RequestForAdditionalTimeProcurementFactory", function(ngDialog, $q, PreContractExternalService,
	PreContractApproverFactory, GenericAlertService, generalservice, RequestForAdditionalTimeProcurementFactory) {

	var viewBidderFactoryPopUp;
	var service = {};
	service.getCompanyBidDetails = function(data, preContractTOs, stateParamsRequestPage) {
		var deferred = $q.defer();
		viewBidderFactoryPopUp = ngDialog
			.open({
				template: 'views/procurement/pre-contracts/externalApproval/precontractbidanalysis.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom0',
				showClose: false,
				closeByDocument: false,
				controller: ['$scope', function($scope) {

					$scope.bidderDatamoreFlag = 0;
					$scope.bidderDatamoreFlags = 0;
					$scope.manpowerFlag = true;
					$scope.plantFlag = true;
					$scope.materialFlag = true;
					$scope.serviceFlag = true;
					$scope.sowFlag = true;

					var approvedCompanyMap = {};
					$scope.currencyList = angular.copy(data.currencyTOs);
					$scope.contractTypes = generalservice.getPreContractTypes();
					$scope.projEmpClassmap = data.projEmpClassMap;
					$scope.projPlantClassmap = data.projPlantMap;
					$scope.projMaterialClassmap = data.projMaterialClassMap;
					$scope.projServiceClassmap = data.projServiceMap;
					$scope.storeClassmap = data.storeMap;
					$scope.projStoreClassmap = data.projStoreMap;
					$scope.projcostCodeMap = data.projCostItemMap;
					$scope.preContractData = data.preContractTO;
					$scope.companyMap = data.companyMap;
					$scope.companyList = data.preContractTO.preContractCmpTOs;
					$scope.procureCategoryMap = data.procureCategoryMap;
					$scope.projSOWMap = data.projSOWMap;
					var currentDate = $scope.preContractData.currentDate;
					$scope.stateParamsRequestPage = stateParamsRequestPage;

					$scope.expandCollapseManpower = function(manpowerFlag) {
						$scope.manpowerFlag = !manpowerFlag;
					};
					$scope.expandCollapsePlant = function(plantFlag) {
						$scope.plantFlag = !plantFlag;
					};
					$scope.expandCollapseMaterial = function(materialFlag) {
						$scope.materialFlag = !materialFlag;
					};
					$scope.expandCollapseService = function(serviceFlag) {
						$scope.serviceFlag = !serviceFlag;
					};
					$scope.expandCollapseSow = function(sowFlag) {
						$scope.sowFlag = !sowFlag;
					};

					$scope.onClickForwardBidderData = function(
						bidderDatamoreFlags1) {
						if ($scope.bidderDatamoreFlags < 3) {
							$scope.bidderDatamoreFlags = bidderDatamoreFlags1 + 1;
						}
					};

					$scope.onClickBackwardBidderData = function(
						bidderDatamoreFlags1) {
						if ($scope.bidderDatamoreFlags > 0) {
							$scope.bidderDatamoreFlags = bidderDatamoreFlags1 - 1;
						}
					};

					$scope.clickForwardBidderData = function(
						bidderDatamoreFlag1) {
						if ($scope.bidderDatamoreFlag < 2) {
							$scope.bidderDatamoreFlag = bidderDatamoreFlag1 + 1;
						}
					};

					$scope.clickBackwardBidderData = function(
						bidderDatamoreFlag1) {
						if ($scope.bidderDatamoreFlag > 0) {
							$scope.bidderDatamoreFlag = bidderDatamoreFlag1 - 1;
						}
					};

					$scope.saveProcBidderItems = function(apprvStatus) {
						var commitApprvStatus = apprvStatus == 7 ? 2 : apprvStatus;
						var savereq = {
							"preContractTO": $scope.preContractData,
							"apprvStatus": commitApprvStatus,
							"approvedCompanyMap": approvedCompanyMap
						};
						PreContractExternalService.saveExternalPreContracts(savereq).then(
							function(data) {
								var succMsg = null;
								if (apprvStatus == 2) {
									succMsg = GenericAlertService.alertMessageModal("Bid submitted for approval successfully ", "Info");
								} else if (apprvStatus == 3) {
									succMsg = GenericAlertService.alertMessageModal("Bid send back to requestor successfully ", "Info");
								} else if (apprvStatus == 4) {
									succMsg = GenericAlertService.alertMessageModal("Bid put On Hold successfully ", "Info");
								} else if (apprvStatus == 5) {
									succMsg = GenericAlertService.alertMessageModal("Bid got approved successfully ", "Info");
								} else if (apprvStatus == 6) {
									succMsg = GenericAlertService.alertMessageModal("Bid got rejected successfully ", "Info");
								} else if (apprvStatus == 7) {
									succMsg = GenericAlertService.alertMessageModal("Bid got released successfully ", "Info");
								}
								succMsg.then(function(data) {
									deferred.resolve(data);
									ngDialog.close()
								});

							}, function(error) {
								GenericAlertService.alertMessage("Approval(s) is/are Failed To save", "Error");
							});

					};
					$scope.projId = $scope.preContractData.projId
					$scope.getUsersByModulePermission = function() {
						var approverFactoryPopup = PreContractApproverFactory.getUsersByModulePermission($scope.projId);
						approverFactoryPopup.then(function(data) {
							$scope.approver = data.approverUserTO;
							$scope.approverid = $scope.approver.name;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while gettting approver users", 'Error');
						});
					};
					$scope.validateman = function(qty){for (const empDtlTO of $scope.preContractData.preContractEmpDtlTOs) {
								if(qty > empDtlTO.quantity){
									GenericAlertService.alertMessage('Manpower Approval quantity cannot be greater than actual quantity', 'Warning');
								}
								}
						};
					$scope.validateplant = function(qty){
						for (const empDtlTO of $scope.preContractData.preContractPlantDtlTOs){
						if(qty > empDtlTO.quantity){
							GenericAlertService.alertMessage("Plant Approval quantity cannot be greater than actual quantity", "Warning");
							
						}
						}
						};
					$scope.validatematerial = function(qty){
					for(const empDtlTO of $scope.preContractData.preContractMaterialDtlTOs){
					if(qty > empDtlTO.quantity)
					{	
						GenericAlertService.alertMessage("Material Approval quantity cannot be greater than actual quantity", "Warning");
					}
					}
					};
					$scope.validateservice = function(qty){
					for(const empDtlTO of preContractData.preContractServiceDtlTOs)
					{
						if(qty > empDtlTO.quantity)
						{
							GenericAlertService.alertMessage("Service Approval quantity cannot be greater than actual quantity", "Warning");
							}
						}
					};
					$scope.validatesow = function(qty)
					{
					for(const empDtlTO of preContractData.precontractSowDtlTOs)
					{
					if(qty > empDtlTO.quantity){
					GenericAlertService.alertMessage("Scope of work Approval quantity cannot be greater than actual quantity", "Warning");
						}
						}
					};
					$scope.sendForApproval = function(apprvStatus) {
						if ($scope.preContractData.preContractReqApprTO.reqComments == null) {
							GenericAlertService.alertMessage('Please enter Requestor Comments', 'Warning');
							return;
						}
						var currentDate = new Date($scope.preContractData.currentDate);
						var closeDate = null;

						if ($scope.approverid == null) {
							GenericAlertService.alertMessage('Please select approver details', 'Warning');
							return;
						}

						if ($scope.validateBid() === false) {
							return;
						}
						var notifyStatus = null;
						if (stateParamsRequestPage) {
							notifyStatus = 'Pending';
						}
						else {
							notifyStatus = 'APPROVED';
						}
						var savereq = {
							"preContractTO": $scope.preContractData,
							"apprvStatus": apprvStatus,
							"approvedCompanyMap": approvedCompanyMap,
							"apprUsrId": $scope.approver.id,
							"projId": $scope.preContractData.projId,
							"notificationStatus": notifyStatus
						};
						PreContractExternalService.saveExternalPreContracts(savereq).then(function(data) {
							var succMsg = GenericAlertService.alertMessageModal("Bid submitted for approval successfully ", "Info");
							succMsg.then(function() {
								$scope.closeThisDialog(viewBidderFactoryPopUp);
								deferred.resolve(data);
							});

						}, function(error) {
							GenericAlertService.alertMessage("Approval(s) is/are Failed To save", "Error");
						});

					};
					$scope.approveOrRejectProcBidderItems = function(apprvStatus) {
						if (apprvStatus != 6 && $scope.validateBid() === false) {
							return;
						}
						var savereq = {
							"preContractTO": $scope.preContractData,
							"apprvStatus": apprvStatus,
							"approvedCompanyMap": approvedCompanyMap,
							"projId": $scope.preContractData.projId
						};
						PreContractExternalService.saveExternalPreContracts(savereq).then(
							function(data) {
								if (apprvStatus == 5) {
									GenericAlertService.alertMessage("Bid has been Approved", "Info");

								}
								if (apprvStatus == 6) {
									GenericAlertService.alertMessageModal("Bid has been Rejected ", "Info");
								}
								deferred.resolve(data);
								ngDialog.close()
							},
							function(error) {
								GenericAlertService.alertMessage("Approval(s) is/are Failed To save", "Error");
							});

					};

					$scope.getProcurement2Notifications = function() {
						$scope.additionalStage = 'stage2';
						var popup = RequestForAdditionalTimeProcurementFactory.getAdditionalTimeDetails(preContractTOs, $scope.additionalStage);
						popup.then(function(data) {
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while getting  request additional time", 'Error');
						})
					}
					$scope.checkExternalPrecontractStatus = function(workFlowMap, status) {
						for (const key in workFlowMap) {
							if (workFlowMap[key] === status) {
								return true;
							}
						}
						return false;
					};

					$scope.onNumberOfPurchaseOrdersChanged = function() {
						let quantityEntered = 0;
						$scope.preContractData.preContractEmpDtlTOs.forEach(function(manpowerObj) {
							manpowerObj.preContractsEmpCmpTOs.forEach(function(itemCompany) {
								if (itemCompany.quantity != null && itemCompany.quantity.length > 0)
									quantityEntered++;
							})
						});

						if ($scope.preContractData.allowMultiplePurchaseOrders) {
							$scope.preContractData.preContractEmpDtlTOs.forEach(function(manpowerObj) {
								manpowerObj.preContractsEmpCmpTOs.forEach(function(itemCompany) {
									itemCompany.disabled = false;
								})
							});
						} else if (!$scope.preContractData.allowMultiplePurchaseOrders) {
							if (quantityEntered > 1)
								GenericAlertService.alertMessage("Entered data shall be cleared.", 'Info');
							$scope.preContractData.preContractEmpDtlTOs.forEach(function(manpowerObj) {
								manpowerObj.preContractsEmpCmpTOs.forEach(function(itemCompany) {
									itemCompany.quantity = null;
									itemCompany.approveFlag = false;
									itemCompany.disabled = false;
								})
							});
						}
						$scope.preContractData.preContractReqApprTO.reqComments = '';
						$scope.preContractData.preContractReqApprTO.apprComments = '';
					}

					$scope.checkQuantity = function(quantity, companies) {
						const companiesWithQuantity = companies.filter(o => (o.quantity && o.quantity > 0));
						if (!$scope.preContractData.allowMultiplePurchaseOrders) {
							validateSinglePOQuantity(companies, companiesWithQuantity);
						} else {

						}
					};

					function validateSinglePOQuantity(companies, companiesWithQuantity) {
						let occupied = false;
						for (const comp of companies) {
							if (companiesWithQuantity.length) {
								if (companiesWithQuantity.includes(comp) && !occupied) {
									comp.valid = true;
									occupied = true;
									comp.disabled = false;
								} else {
									comp.valid = false;
									comp.disabled = true;
								}
							} else {
								comp.valid = true;
							}
						}
					}

					$scope.validateBid = function() {
						let atLeastOneSelected = false;

						// Manpower validations
						if ($scope.preContractData.preContractEmpDtlTOs) {
							for (const empDtlTO of $scope.preContractData.preContractEmpDtlTOs) {
								var quantity = 0;
								var apprvQuantity = 0;
								for (const empCmpTO of empDtlTO.preContractsEmpCmpTOs) {
									if (empCmpTO.approveFlag) {
										if (empCmpTO.quantity <= 0) {
											GenericAlertService.alertMessage("Please enter manpower items approval quantity for bid", "Warning");
											return false;
										}
										apprvQuantity = empDtlTO.quantity;
										atLeastOneSelected = true;
										quantity += parseInt(empCmpTO.quantity);
										if (approvedCompanyMap == null || approvedCompanyMap[empCmpTO.preContractCmpId] == null) {
											approvedCompanyMap[empCmpTO.preContractCmpId] = empCmpTO.preContractCmpId;
										}
									}

								}

								if (empDtlTO.quantity < quantity) {
									GenericAlertService.alertMessage("Manpower Approval quantity cannot be greater than actual quantity", "Warning");
									return false;
								}
							}
						}

						// Plant validations
						if ($scope.preContractData.preContractPlantDtlTOs) {
							for (const plantDtlTO of $scope.preContractData.preContractPlantDtlTOs) {
								var quantity = 0;
								var apprvQuantity = 0;
								for (const plantCmpTO of plantDtlTO.preContractPlantCmpTOs) {
									if (plantCmpTO.approveFlag) {
										if (plantCmpTO.quantity <= 0) {
											GenericAlertService.alertMessage(
												"Please enter plant items approval quantity for bid", "Warning");
											return false;
										}
										apprvQuantity = plantDtlTO.quantity;
										atLeastOneSelected = true;
										quantity += parseInt(plantCmpTO.quantity);
										if (approvedCompanyMap == null || approvedCompanyMap[plantCmpTO.preContractCmpId] == null) {
											approvedCompanyMap[plantCmpTO.preContractCmpId] = plantCmpTO.preContractCmpId;
										}
									}

								}

								if (plantDtlTO.quantity < quantity) {
									GenericAlertService.alertMessage(
										"Plant Approval quantity cannot be greater than actual quantity", "Warning");
									return false;
								}
							}
						}

						// Material validations
						if ($scope.preContractData.preContractMaterialDtlTOs) {
							for (const materialDtlTO of $scope.preContractData.preContractMaterialDtlTOs) {
								var quantity = 0;
								var apprvQuantity = 0;
								for (const materialCmpTO of materialDtlTO.preContractMaterialCmpTOs) {
									if (materialCmpTO.approveFlag) {
										if (materialCmpTO.quantity <= 0) {
											GenericAlertService.alertMessage(
												"Please enter material items approval quantity for bid", "Warning");
											return false;
										}
										apprvQuantity = materialDtlTO.quantity;
										atLeastOneSelected = true;
										quantity += parseInt(materialCmpTO.quantity);
										if (approvedCompanyMap == null || approvedCompanyMap[materialCmpTO.preContractCmpId] == null) {
											approvedCompanyMap[materialCmpTO.preContractCmpId] = materialCmpTO.preContractCmpId;
										}
									}

								}

								if (materialDtlTO.quantity < quantity) {
									GenericAlertService.alertMessage(
										"Material Approval quantity cannot be greater than actual quantity", "Warning");
									return false;
								}
							}
						}

						// Service validations
						if ($scope.preContractData.preContractServiceDtlTOs) {
							for (const serviceDtlTO of $scope.preContractData.preContractServiceDtlTOs) {
								var quantity = 0;
								var apprvQuantity = 0;
								for (const serviceCmpTO of serviceDtlTO.preContractServiceCmpTOs) {
									if (serviceCmpTO.approveFlag) {
										if (serviceCmpTO.quantity <= 0) {
											GenericAlertService.alertMessage(
												"Please enter service items approval quantity for bid", "Warning");
											return false;
										}
										apprvQuantity = serviceDtlTO.quantity;
										atLeastOneSelected = true;
										quantity += parseInt(serviceCmpTO.quantity);
										if (approvedCompanyMap == null || approvedCompanyMap[serviceCmpTO.preContractCmpId] == null) {
											approvedCompanyMap[serviceCmpTO.preContractCmpId] = serviceCmpTO.preContractCmpId;
										}
									}

								}

								if (serviceDtlTO.quantity < quantity) {
									GenericAlertService.alertMessage(
										"Service Approval quantity cannot be greater than actual quantity", "Warning");
									return false;
								}
							}
						}

						// SOW validations
						if ($scope.preContractData.precontractSowDtlTOs) {
							for (const sowDtlTO of $scope.preContractData.precontractSowDtlTOs) {
								var quantity = 0;
								var apprvQuantity = 0;
								for (const sowCmpTO of sowDtlTO.precontractSowCmpTOs) {
									if (sowCmpTO.approveFlag) {
										if (sowCmpTO.quantity <= 0) {
											GenericAlertService.alertMessage(
												"Please enter scope of work items approval quantity for bid", "Warning");
											return false;
										}
										apprvQuantity = sowCmpTO.quantity;
										atLeastOneSelected = true;
										quantity += parseInt(sowCmpTO.quantity);
										if (approvedCompanyMap == null || approvedCompanyMap[sowCmpTO.preContractCmpId] == null) {
											approvedCompanyMap[sowCmpTO.preContractCmpId] = sowCmpTO.preContractCmpId;
										}
									}

								}

								if (sowDtlTO.quantity < quantity) {
									GenericAlertService.alertMessage(
										"Scope of work Approval quantity cannot be greater than actual quantity", "Warning");
									return false;
								}
							}
						}
						if (!atLeastOneSelected) {
							GenericAlertService.alertMessage(
								"Please select atleast one schedule item", "Warning");
							return false;
						}
						return true;
					}
				}]
			});
		return deferred.promise;
	};
	return service;

}]);
