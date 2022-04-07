'use strict';

app.factory('POExternalFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractExternalService", "PreContractAddCompanyFactory", "PreContractApproverFactory", "GenericAlertService", "FileUploader", "PrecontractExternalApprRequestFactory", function (ngDialog, $q, $filter, $timeout, $rootScope, PreContractExternalService,
	PreContractAddCompanyFactory, PreContractApproverFactory, GenericAlertService, FileUploader,
	PrecontractExternalApprRequestFactory) {
	var procExternalApprovalPopUp;
	var service = {};
	service.procExternalApprovalPopUp = function (data) {
		var deferred = $q.defer();
		procExternalApprovalPopUp = ngDialog
			.open({
				template: 'views/procurement/purchaseorders/poexternalschitemspopup.html',
				showClose: false,
				closeByDocument: false,
				controller: ['$scope', function ($scope) {

					$scope.uploader = new FileUploader();

					$scope.currencyList = angular
						.copy(data.currencyTOs);
					$scope.contractTypes = data.preContractTypeTOs;

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
					$scope.preContrctDetailTabs = [
						{
							title: 'Man Power',
							url: 'views/procurement/purchaseorders/purchaseexternalmanpower.html'
						},
						{
							title: 'Materials',
							url: 'views/procurement/purchaseorders/purchaseexternalmaterial.html'
						},
						{
							title: 'Plants',
							url: 'views/procurement/purchaseorders/purchaseexternalplants.html'
						},
						{
							title: 'Services',
							url: 'views/procurement/purchaseorders/purchaseexternalservices.html'
						},
						{
							title: 'Sub Contract',
							url: 'views/procurement/purchaseorders/purchaseexternalsow.html'
						}];

					$scope.onClickTab1 = function (tab) {
						$scope.currentTab1 = tab.url;
						$scope.isActiveTab1($scope.currentTab1);
					};
					$scope.isActiveTab1 = function (tabUrl) {
						return tabUrl == $scope.currentTab1;
					};

					$scope.onClickTab1($scope.preContrctDetailTabs[0]);

					$scope.addCompany = function () {
						var addCompanypopup = [];
						var addCompanyService = {};
						var addCompanyServiceData = [];
						var companyData = [];
						var preContractCompanyMap = [];
						addCompanyServiceData = $scope.getPreContractCompanies();
						addCompanyServiceData.then(function (data) {
							companyData = data.precontractCmpData;
							preContractCompanyMap = data.preContractCompanyMap;
							addCompanypopup = PreContractAddCompanyFactory.getCompanies($scope.preContractObj,
								companyData, preContractCompanyMap);
							addCompanypopup.then(function (data) {
								$scope.preContractObj.preContractCmpTOs = data.preContractCmpTOs;
							}, function (error) {
								GenericAlertService.alertMessage(
									"Error occured while adding companies to precontract", 'Error');
							});

						}, function (error) {
							GenericAlertService.alertMessage(
								"Error occured while adding companies to precontract", 'Error');
						});

					};
					$scope.getPreContractCompanies = function () {
						var defferComapny = $q.defer();
						var cmpGetReq = {
							"status": 1,
							"preContractId": $scope.preContractObj.id

						};
						PreContractExternalService.getPreContratCompanies(cmpGetReq).then(
							function (data) {
								var returnCompanies = {
									"precontractCmpData": angular.copy(data.preContractCmpTOs),
									"preContractCompanyMap": angular.copy(data.preContractCompanyMap)
								};
								defferComapny.resolve(returnCompanies);
							},
							function (error) {
								GenericAlertService.alertMessage(
									"Error occured while getting Project Material classes", 'Error');
							});
						return defferComapny.promise;
					};
					$scope.getUsersByModulePermission = function () {
						var approverFactoryPopup = [];
						approverFactoryPopup = PreContractApproverFactory.getUsersByModulePermission();
						approverFactoryPopup.then(function (data) {
							$scope.preContractObj.preContractReqApprTO.apprUserLabelkeyTO = data.approverUserTO;

						}, function (error) {
							GenericAlertService.alertMessage(
								"Error occured while gettting approver users", 'Error');
						});
					};

					$scope.saveExternalRequest = function (
						apprvStatus, contarctStageStatus) {
						var validateFalg = true;
						if (apprvStatus == 4) {
							var resultdata = $scope.onHoldInternalRequest(apprvStatus, contarctStageStatus);
							resultdata.then(function (data) {
								deferred.resolve(data);
							});
						}
						if (apprvStatus == 2) {
							validateFalg = $scope.validateProcurmentDetails();
						}
						if (validateFalg) {
							PrecontractExternalApprRequestFactory.submitOrApproveInternalRequest($scope.preContractObj,
								apprvStatus, projId, $scope.approverUserMap);
						}

					};
					$scope.onHoldInternalRequest = function (apprvStatus, contarctStageStatus) {
						var savereq = {
							"preContractTO": $scope.preContractObj,
							"apprvStatus": apprvStatus,
							"contarctStageStatus": contarctStageStatus,
							"projId": projId
						};
						PreContractExternalService.saveExternalPreContracts(savereq)
							.then(function (data) {
								var succMsg = GenericAlertService.alertMessageModal(
									"Precontract put on-hold successfully ", "Info");
								succMsg.then(function (popData) {
									$scope.preContractObj = data.preContractTOs[0];
									deferred.resolve($scope.preContractObj);
								});
							}, function (error) {
								GenericAlertService.alertMessage(
									"Error occured,Please try again", "Error");
							});

					};
					$scope.validateProcurmentDetails = function () {
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
							GenericAlertService.alertMessage(
								"Please add atleast any one of procurement records", "Warning");
						}
						return false;

					};

					$scope.saveManPowerDetails = function () {

						var savereq = {
							"preContractEmpDtlTOs": $scope.preContractObj.preContractEmpDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratEmpTypes(savereq)
							.then(function (data) {
								$scope.preContractObj.preContractEmpDtlTOs = data.preContractEmpDtlTOs
								GenericAlertService.alertMessage("Success", "Info");
							}, function (error) {
								GenericAlertService.alertMessage(
									"Internal Approval(s) is/are Failed To save", "error");
							});

					};

					$scope.savePlantDetails = function () {

						var savereq = {
							"preContractPlantDtlTOs": $scope.preContractObj.preContractPlantDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratPlants(savereq)
							.then(function (data) {
								$scope.preContractObj.preContractPlantDtlTOs = data.preContractPlantDtlTOs
								GenericAlertService.alertMessage("Success", "Info");
							}, function (error) {
								GenericAlertService.alertMessage(
									"Internal Approval(s) is/are Failed To save", "error");
							});

					};
					$scope.saveMaterialDetails = function () {

						var savereq = {
							"preContractMaterialDtlTOs": $scope.preContractObj.preContractMaterialDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratMaterials(savereq)
							.then(function (data) {
								$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs
								GenericAlertService.alertMessage("Success", "Info");
							}, function (error) {
								GenericAlertService.alertMessage(
									"Internal Approval(s) is/are Failed To save", "error");
							});

					};
					$scope.saveServiceDetails = function () {

						var savereq = {
							"preContractServiceDtlTOs": $scope.preContractObj.preContractServiceDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"external": true
						};
						PreContractExternalService.savePreContratServices(savereq)
							.then(function (data) {
								$scope.preContractObj.preContractServiceDtlTOs = data.preContractServiceDtlTOs

								GenericAlertService.alertMessage("Success", "Info");
							}, function (error) {
								GenericAlertService.alertMessage(
									"Internal Approval(s) is/are Failed To save", "error");
							});

					};
					$scope.saveSOWDetails = function () {

						var savereq = {
							"precontractSowDtlTOs": $scope.preContractObj.precontractSowDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"sowDtlIds": deleteSowDtlIds
						};
						PreContractExternalService.savePreContratServices(savereq)
							.then(function (data) {
								$scope.preContractObj.precontractSowDtlTOs = data.precontractSowDtlTOs

								GenericAlertService.alertMessage(
									"Precontract SOW details are saved successfully",
									"Info");
							}, function (error) {
								GenericAlertService.alertMessage(
									"Precontract SOW details are  Failed To save", "Error");
							});

					}

				}]
			});
		return deferred.promise;
	};
	return service;

}]);
