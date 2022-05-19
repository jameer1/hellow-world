'use strict';

app.factory('RfqScheduleItemsEditFactor', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractExternalService", "RFQService", "PreContractAddCompanyFactory", "PreContractApproverFactory", "GenericAlertService", "FileUploader", "generalservice", function (ngDialog, $q, $filter, $timeout, $rootScope, PreContractExternalService, RFQService, PreContractAddCompanyFactory, PreContractApproverFactory, GenericAlertService, FileUploader, generalservice) {
	var procExternalApprovalPopUp;
	var service = {};
	service.editPrecontractSchItems = function (data, preContractCmpTO, rfq) {
		var deferred = $q.defer();
		procExternalApprovalPopUp = ngDialog.open({
			template: 'views/procurement/RFQ/rfqschitemseditpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.date = new Date();
				$scope.status = 'Close';
				$scope.preContractCmpTO = preContractCmpTO;
				$scope.uploader = new FileUploader();
				$scope.currencyList = angular.copy(data.currencyTOs);
				$scope.contractTypes = generalservice.getPreContractTypes();
				$scope.projEmpClassmap = data.projEmpClassMap;
				$scope.projPlantClassmap = data.projPlantMap;
				$scope.projMaterialClassmap = data.projMaterialClassMap;
				$scope.projServiceClassmap = data.projServiceMap;
				$scope.storeClassmap = data.storeMap;
				$scope.projStoreClassmap = data.projStoreMap;
				$scope.projcostCodeMap = data.projCostItemMap;
				$scope.companyMap = data.companyMap;
				$scope.approverUserMap = data.usersMap;
				$scope.companyList = data.preContractTO.preContractCmpTOs;
				$scope.procureCategoryMap = data.procureCategoryMap;
				$scope.projSOWMap = data.projSOWMap;
				$scope.preContractCmpTO = preContractCmpTO;
				
				var saveManFlag = false;
				var saveMaterialFlag = false;
				var savePlantFlag = false;
				var saveServiceFlag = false;
				var saveSOWFlag = false;

				setGivenCompanyAtFirst(data.preContractTO, 'preContractEmpDtlTOs', 'preContractsEmpCmpTOs', preContractCmpTO.id);
				setGivenCompanyAtFirst(data.preContractTO, 'preContractMaterialDtlTOs', 'preContractMaterialCmpTOs', preContractCmpTO.id);
				setGivenCompanyAtFirst(data.preContractTO, 'preContractPlantDtlTOs', 'preContractPlantCmpTOs', preContractCmpTO.id);
				setGivenCompanyAtFirst(data.preContractTO, 'preContractServiceDtlTOs', 'preContractServiceCmpTOs', preContractCmpTO.id);
				setGivenCompanyAtFirst(data.preContractTO, 'precontractSowDtlTOs', 'precontractSowCmpTOs', preContractCmpTO.id);

				$scope.preContractObj = data.preContractTO;
				if($scope.preContractObj.preContractType == "Engineering Services"){
					$scope.preContrctDetailTabs = [{
					title: 'Services',
					url: 'views/procurement/RFQ/rfqeditservices.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewservice'
					}]
				}
				if($scope.preContractObj.preContractType == "Labour Hire Agreement"){
					$scope.preContrctDetailTabs = [{
					title: 'Manpower',
					url: 'views/procurement/RFQ/rfqeditmanpower.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewmanpower'
					}]
				}
				if($scope.preContractObj.preContractType == "Plant Hire Agreement"){
					$scope.preContrctDetailTabs = [{
					title: 'Plants',
					url: 'views/procurement/RFQ/rfqeditplants.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewplant'
					}]
				}
				if($scope.preContractObj.preContractType == "Supply Agreement"){
					$scope.preContrctDetailTabs = [{
					title: 'Materials',
					url: 'views/procurement/RFQ/rfqeditmaterial.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewmaterials'
					}]
				}
				if($scope.preContractObj.preContractType == "Purchase  Order"){
					$scope.preContrctDetailTabs = [{
					title: 'Manpower',
					url: 'views/procurement/RFQ/rfqeditmanpower.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewmanpower'
					},{
					title: 'Materials',
					url: 'views/procurement/RFQ/rfqeditmaterial.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewmaterials'
					},{
					title: 'Plants',
					url: 'views/procurement/RFQ/rfqeditplants.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewplant'
					}]
				}
				if($scope.preContractObj.preContractType == "Sub Contract agreement"){
					$scope.preContrctDetailTabs = [{
					title: 'Project Sub Contract',
					url: 'views/procurement/RFQ/rfqeditsow.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewprojectsubcontract'
					}]
				}
				if($scope.preContractObj.preContractType == "Professional Services agreement"){
					$scope.preContrctDetailTabs = [{
					stitle: 'Services',
					url: 'views/procurement/RFQ/rfqeditservices.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewservice'
					}]
				}

				/*$scope.preContrctDetailTabs = [{
					title: 'Manpower',
					url: 'views/procurement/RFQ/rfqeditmanpower.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewmanpower'
				}, {
					title: 'Materials',
					url: 'views/procurement/RFQ/rfqeditmaterial.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewmaterials'

				}, {
					title: 'Plants',
					url: 'views/procurement/RFQ/rfqeditplants.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewplant'

				}, {
					title: 'Services',
					url: 'views/procurement/RFQ/rfqeditservices.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewservice'

				}, {
					title: 'Project Sub Contract',
					url: 'views/procurement/RFQ/rfqeditsow.html',
					rfqscheduleSeleniumLocator: 'PurchaseOrders_RFQ_editquotesviewprojectsubcontract'

				}];*/

				$scope.onClickTab1 = function (tab) {
					$scope.currentTab1 = tab.url;
					$scope.isActiveTab1($scope.currentTab1);
				}
				$scope.isActiveTab1 = function (tabUrl) {
					return tabUrl == $scope.currentTab1;
				}

				$scope.onClickTab1($scope.preContrctDetailTabs[0]);

				$scope.getUsersByModulePermission = function () {
					var approverFactoryPopup = [];
					approverFactoryPopup = PreContractApproverFactory.getUsersByModulePermission();
					approverFactoryPopup.then(function (data) {
						$scope.preContractObj.preContractReqApprTO.apprUserLabelkeyTO = data.approverUserTO;

					}, function (error) {
						GenericAlertService.alertMessage("Error occured while gettting approver users", 'Error');
					});
				};


				$scope.submitQuotation = function () {
					if (validateScheduleItemsRate()) {
						if (!saveManFlag && $scope.preContractObj.preContractEmpDtlTOs != null && $scope.preContractObj.preContractEmpDtlTOs.length > 0) {
							GenericAlertService.alertMessage("Please save Manpower Quote Rates Before Submitting Quotes", "Warning");
							return;
						}
						if (!saveMaterialFlag && $scope.preContractObj.preContractMaterialDtlTOs != null && $scope.preContractObj.preContractMaterialDtlTOs.length > 0) {
							GenericAlertService.alertMessage("Please save material Quote Rates Before Submitting Quotes", "Warning");
							return;
						}
						if (!savePlantFlag && $scope.preContractObj.preContractPlantDtlTOs != null && $scope.preContractObj.preContractPlantDtlTOs.length > 0) {
							GenericAlertService.alertMessage("Please save plant Quote Rates Before Submitting Quotes", "Warning");
							return;
						}
						if (!saveServiceFlag && $scope.preContractObj.preContractServiceDtlTOs != null && $scope.preContractObj.preContractServiceDtlTOs.length > 0) {
							GenericAlertService.alertMessage("Please save service Quote Rates Before Submitting Quotes", "Warning");
							return;
						}
						if (!saveSOWFlag && $scope.preContractObj.precontractSowDtlTOs != null && $scope.preContractObj.precontractSowDtlTOs.length > 0) {
							GenericAlertService.alertMessage("Please save scope of work Quote Rates Before Submitting Quotes", "Warning");
							return;
						}
						if(preContractCmpTO.quoteRefCode == null){
							GenericAlertService.alertMessage("Please enter Refcode before Submitting Quotes", "Warning");
							return;
						}
						if (saveManFlag || savePlantFlag || saveMaterialFlag || saveServiceFlag || saveSOWFlag) {
							var req = {
									"id": $scope.preContractObj.id,
									"preContractCmpId": preContractCmpTO.id,
									"biddingStatus": 'Closed',
									"contarctStageStatus": 'Stage 2 Request',
									"projId": $scope.preContractObj.projId,
									"quoteRefCode": preContractCmpTO.quoteRefCode,
									"status": 1
								};
								RFQService.submitBidQuotation(req).then(function (data) {
									GenericAlertService.alertMessageModal("Quotation submitted successfully", "Info");
									deferred.resolve(data);
									$scope.closeThisDialog();

								}, function (error) {
									GenericAlertService.alertMessage("Error ocurred while sbmitting quotation", "Error");
								});
						} else {
							GenericAlertService.alertMessage("Please add atleast one Quote Rates Before Submitting Quotes", "Warning");
						}
					}
				};

				function validateScheduleItemsRate() {
					const tabProperties = [{ schItemProperty: 'preContractEmpDtlTOs', cmpProperty: 'preContractsEmpCmpTOs' },
					{ schItemProperty: 'preContractMaterialDtlTOs', cmpProperty: 'preContractMaterialCmpTOs' },
					{ schItemProperty: 'preContractPlantDtlTOs', cmpProperty: 'preContractPlantCmpTOs' },
					{ schItemProperty: 'preContractServiceDtlTOs', cmpProperty: 'preContractServiceCmpTOs' },
					{ schItemProperty: 'precontractSowDtlTOs', cmpProperty: 'precontractSowCmpTOs' }];
					// $scope.preContractObj.preContractEmpDtlTOs.
					for (const tab of tabProperties) {
						for (const schItem of $scope.preContractObj[tab.schItemProperty]) {
							for (const cmp of schItem[tab.cmpProperty]) {
								if (cmp.rate) {
									return true;
								}
							}
						}
					}
					//GenericAlertService.alertMessage("Please Enter Quote rate for atleast one Tab", "Warning");
					return true;
				}

				$scope.saveManPowerDetails = function () {

					var savereq = {
						"preContractEmpDtlTOs": $scope.preContractObj.preContractEmpDtlTOs,
						"preContractId": $scope.preContractObj.id,
						"external": true
					};
					PreContractExternalService.savePreContratEmpTypes(savereq).then(function (data) {
						setGivenCompanyAtFirst(data.preContractEmpDtlTOs, null, 'preContractsEmpCmpTOs', preContractCmpTO.id);
						$scope.preContractObj.preContractEmpDtlTOs = data.preContractEmpDtlTOs;
						saveManFlag = true;
						GenericAlertService.alertMessage("Precontract Manpower details saved successfully", "Info");
					},
					function (error) {
						GenericAlertService.alertMessage("Internal Approval(s) is/are Failed To save", "error");
					});

				},

					$scope.savePlantDetails = function () {

						var savereq = {
							"preContractPlantDtlTOs": $scope.preContractObj.preContractPlantDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratPlants(savereq).then(function (data) {
							setGivenCompanyAtFirst(data.preContractPlantDtlTOs, null, 'preContractPlantCmpTOs', preContractCmpTO.id);
							$scope.preContractObj.preContractPlantDtlTOs = data.preContractPlantDtlTOs;
							savePlantFlag = true;
							GenericAlertService.alertMessage("Precontract Plant details saved successfully", "Info");
						},

							function (error) {
								GenericAlertService.alertMessage("Internal Approval(s) is/are Failed To save", "error");
							});

					}, $scope.saveMaterialDetails = function () {
						var savereq = {
							"preContractMaterialDtlTOs": $scope.preContractObj.preContractMaterialDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratMaterials(savereq).then(function (data) {
							setGivenCompanyAtFirst(data.preContractMaterialDtlTOs, null, 'preContractMaterialCmpTOs', preContractCmpTO.id);
							$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs;
							saveMaterialFlag = true;
							GenericAlertService.alertMessage("Precontract Material details saved successfully", "Info");
						},

							function (error) {
								GenericAlertService.alertMessage("Internal Approval(s) is/are Failed To save", "error");
							});

					}, $scope.saveServiceDetails = function () {
						var savereq = {
							"preContractServiceDtlTOs": $scope.preContractObj.preContractServiceDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratServices(savereq).then(function (data) {
							setGivenCompanyAtFirst(data.preContractServiceDtlTOs, null, 'preContractServiceCmpTOs', preContractCmpTO.id);
							$scope.preContractObj.preContractServiceDtlTOs = data.preContractServiceDtlTOs;
							saveServiceFlag = true;
							GenericAlertService.alertMessage("Precontract Service details saved successfully", "Info");
						},
							function (error) {
								GenericAlertService.alertMessage("Internal Approval(s) is/are Failed To save", "error");
							});

					}, $scope.saveSOWDetails = function () {
						var savereq = {
							"precontractSowDtlTOs": $scope.preContractObj.precontractSowDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContractSOWTypes(savereq).then(function (data) {
							setGivenCompanyAtFirst(data.precontractSowDtlTOs, null, 'precontractSowCmpTOs', preContractCmpTO.id);
							$scope.preContractObj.precontractSowDtlTOs = data.precontractSowDtlTOs;
							saveSOWFlag = true;
							GenericAlertService.alertMessage("Precontract SOW details saved successfully", "Info");
						},
							function (error) {
								GenericAlertService.alertMessage("Precontract SOW details are  Failed To save", "Error");
							});

					};

				function setGivenCompanyAtFirst(precontract, schItemProp, companyArrayProp, companyId) {
					const schItemArray = schItemProp ? precontract[schItemProp] : precontract;
					if (schItemArray && schItemArray.length) {
						for (const schItem of schItemArray) {
							const array = schItem[companyArrayProp];
							for (let index = 0; index < array.length; index++) {
								if (array[index].preContractCmpId === companyId) {
									const temp = array[0];
									array[0] = array[index];
									array[index] = temp;
								}
								break;
							}
						}

					}
				}

			}]
		});
		return deferred.promise;
	};
	return service;

}]);
