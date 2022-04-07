'use strict';

app.factory('PreContractInternalReqFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", "ProjCostCodeService", "StoreService", "PrecontractDocUploadFactory", "PreContractCostPopupFactory", "PreContractCostCodeBudgetFactory", "ProcurementSubCategoryFactory", "PreContractProjEmpClassFactory", "PreContractProjPlantClassFactory", "PreContractProjServiceClassFactory", "PrecontractMaterialFactory", "PreContractStoreFactory", "ProjBudgetMaterialFactory", "PrecontractSOWFactory", "PreContractApproverFactory", "PrecontractSubmitApprRequestFactory", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService, GenericAlertService, ProjCostCodeService, StoreService, PrecontractDocUploadFactory, PreContractCostPopupFactory, PreContractCostCodeBudgetFactory, ProcurementSubCategoryFactory, PreContractProjEmpClassFactory, PreContractProjPlantClassFactory, PreContractProjServiceClassFactory, PrecontractMaterialFactory, PreContractStoreFactory, ProjBudgetMaterialFactory, PrecontractSOWFactory, PreContractApproverFactory, PrecontractSubmitApprRequestFactory,generalservice) {
	var procInternalApprovalPopUp;
	var service = {};
	service.procInternalApprovalPopUp = function(data) {
		var deferred = $q.defer();

		var procInternalApprovalPopUp = ngDialog.open({
			template : 'views/procurement/pre-contracts/internalApproval/precontractinternalreqschitemspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {

				$scope.costcode = [];
				$scope.defaultcostcode = {};
				$scope.currentTab1 = null;
				$scope.contractTypes = generalservice.getPreContractTypes();
				$scope.currencyList = angular.copy(data.currencyTOs);
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
				$scope.stateParamsRequestPage=stateParamsRequestPage;
				$scope.preContractCostCodeSummaryTOs = [];
				$scope.stateParamsRequestPage=false;
				var projId = data.preContractTO.projId;
				//stage1 Request
				$scope.preContrctDetailTabs = [ {
					title : 'Manpower',
					url : 'views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
					reqappCodeTemplateKey :'PROCURMT_INTRNLSTAGE1MANPOWER_VIEW',
					stateParamsRequestPage:true,
					appCodeTemplateKeySubmit:'PROCURMT_INTRNLSTAGE1MANPOWER_SUBMIT'

				}, {
					title : 'Materials', 
					url : 'views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
					reqappCodeTemplateKey :'PROCURMT_INTRNLSTAGE1MATERIALS_VIEW',
					stateParamsRequestPage:true,
					appCodeTemplateKeySubmit:'PROCURMT_INTRNLSTAGE1MATERIALS_SUBMIT'

				}, {
					title : 'Plants',
					url : 'views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
					reqappCodeTemplateKey :'PROCURMT_INTRNLSTAGE1PLANTS_VIEW',
					stateParamsRequestPage:true,
					appCodeTemplateKeySubmit:'PROCURMT_INTRNLSTAGE1PLANTS_SUBMIT'

				}, {
					title : 'Services',
					url : 'views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
					reqappCodeTemplateKey :'PROCURMT_INTRNLSTAGE1SERVICES_VIEW',
					stateParamsRequestPage:true,
					appCodeTemplateKeySubmit:'PROCURMT_INTRNLSTAGE1SERVICES_SUBMIT'

				}, {
					title : 'Project Sub Contract',
					url : 'views/procurement/pre-contracts/internalApproval/precontractinternalsow.html',
					reqappCodeTemplateKey :'PROCURMT_INTRNLSTAGE1SUBCONTRACT_VIEW',
					stateParamsRequestPage:true,
					appCodeTemplateKeySubmit:'PROCURMT_INTRNLSTAGE1SUBCONTRACT_SUBMIT'

				} ];

				$scope.onClickTab1 = function(tab) {
					$scope.currentTab1 = tab.url;
					$scope.isActiveTab1($scope.currentTab1);
				}
				$scope.isActiveTab1 = function(tabUrl) {
					return tabUrl == $scope.currentTab1;
				}
				$scope.onClickTab1($scope.preContrctDetailTabs[0]);
				
				$scope.saveManFlag = false;
				$scope.saveMaterialFlag = false;
				$scope.savePlantFlag = false;
				$scope.saveServiceFlag = false;
				$scope.saveSOWFlag = false;
				
				$scope.projCostCodeDetails = function(projCostCodeLabelKeyTO) {
					console.log('projCostCodeLabelKeyTO  ',projCostCodeLabelKeyTO);
					var costCodePopup = PreContractCostPopupFactory.getProjCostDetails(projId);
					console.log('costCodePopup  ',costCodePopup);
					costCodePopup.then(function(data) {
						projCostCodeLabelKeyTO.id = data.id;
						projCostCodeLabelKeyTO.code = data.code;
						projCostCodeLabelKeyTO.name = data.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting cost code details", 'Error');
					});
				},

				$scope.selectProcureSubCategory = function(type, item) {
					var req = {
						"procureId" : type,
						"status" : 1
					};
					var manpowerCategoryDetailsPopup = ProcurementSubCategoryFactory.getProcureCatgs(req);
					manpowerCategoryDetailsPopup.then(function(data) {
						item.procureSubCatgId = data.selectedSubcategory.proCatgId;
					}, function(error) {
						GenericAlertService.alertMessage('Error occurred while selecting procurement sub-catagories', "Error");
					});
				}, $scope.projEmpClassDetails = function(projEmpClassLabelKeyTO) {
					var projEmpClassDetailsPopup = PreContractProjEmpClassFactory.getProjEmpClasses();
					projEmpClassDetailsPopup.then(function(data) {
						projEmpClassLabelKeyTO.id = data.projEmpclassTO.id;
						projEmpClassLabelKeyTO.code = data.projEmpclassTO.code;
						projEmpClassLabelKeyTO.name = data.projEmpclassTO.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting employee details", 'Error');
					});
				}, $scope.projPlantClassDetails = function(projPlantClassLabelKeyTO) {
					var projPlantClassDetailsPopup = PreContractProjPlantClassFactory.getProjPlantClasses(projId);
					projPlantClassDetailsPopup.then(function(data) {
						projPlantClassLabelKeyTO.id = data.projPlantClassTO.id;
						projPlantClassLabelKeyTO.code = data.projPlantClassTO.code;
						projPlantClassLabelKeyTO.name = data.projPlantClassTO.name;
						projPlantClassLabelKeyTO.unitOfMeasure = data.projPlantClassTO.measureUnitTO.name;

					}, function(error) {
						GenericAlertService.alertMessage('Error occurred while selecting plant details', "Error");
					});
				},

				$scope.addMaterialItems = function() {
					var exitingSchMaterilMap = [];
					var popupDetails = PrecontractMaterialFactory.getMaterialClasses(projId, exitingSchMaterilMap, $scope.preContractObj);
					console.log('popupDetails  ', popupDetails);
					popupDetails.then(function(data) {
						console.log('data   ==>  ', data);
						$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while adding Material Details", "Error");
					});
				},

				$scope.serviceClassDetails = function(serviceClassLabelKeyTO) {
					var serviceClassificationPopup = PreContractProjServiceClassFactory.getServiceClasses();
					serviceClassificationPopup.then(function(data) {
						serviceClassLabelKeyTO.id = data.serviceClassTO.id;
						serviceClassLabelKeyTO.code = data.serviceClassTO.code;
						serviceClassLabelKeyTO.name = data.serviceClassTO.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting service classes", 'Error');
					});
				},

				$scope.selectDeliveryPlace = function(storeLabelKeyTO, projStoreLabelKeyTO) {
					console.log('storeLabelKeyTO  ==>  ', storeLabelKeyTO);
					console.log('projStoreLabelKeyTO  ==>  ', projStoreLabelKeyTO);
					console.log('projId  ==>  ', projId);
					var storeYardPopup = PreContractStoreFactory.getStocks(projId);
					storeYardPopup.then(function(data) {
						console.log('data  ==>  ', data);
						if (data.type == 2) {
							projStoreLabelKeyTO.id = data.storeStockTO.id;
							projStoreLabelKeyTO.code = data.storeStockTO.code;
						  storeLabelKeyTO.id = 0;
						} else if (data.type == 1){
							storeLabelKeyTO.id = data.storeStockTO.id;
							storeLabelKeyTO.code = data.storeStockTO.code;
							projStoreLabelKeyTO.id = 0;
							
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Store Delivery Details", 'Error');
					});
				}, $scope.addManPowerRows = function() {
					console.log('addManpower records  ==??    ');
					$scope.preContractObj.preContractEmpDtlTOs.push({
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
					console.log('addManpower records  ==??    ',$scope.addManPowerRows);
				},

				$scope.addPlantRows = function() {
					$scope.preContractObj.preContractPlantDtlTOs.push({
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
				}, $scope.addMaterialRows = function() {
					var existedMaterialMap = [];
					$scope.preContractObj.preContractMaterialDtlTOs.push({
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
				}, $scope.addServiceRows = function() {
					
					$scope.preContractObj.preContractServiceDtlTOs.push({
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
				}, $scope.addProjSOWItems = function(item) {
					var existedSOWMap = [];
					angular.forEach($scope.preContractObj.precontractSowDtlTOs, function(value, key) {
						existedSOWMap[value.sowId] = true;
					});
					var sowPopup = PrecontractSOWFactory.getSOWDetails(projId, existedSOWMap);
					sowPopup.then(function(data) {
						angular.forEach(data.projSOWItemTOs, function(value, key) {
							$scope.preContractObj.precontractSowDtlTOs.push({
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

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting sow details", 'Error');
					});
				}, $scope.deleteManPowerDetails = function() {
					deleteEmpDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractEmpDtlTOs, function(value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteEmpDtlIds.push(value.id);
							}
							flag = true;
							GenericAlertService.alertMessage("Pre-contract manpower details deleted successfully", "Info");
						}
					});
					
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractEmpDtlTOs = tempInternalRequest;
				}, $scope.deletePlantDetails = function() {
					deletePlantDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractPlantDtlTOs, function(value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deletePlantDtlIds.push(value.id);
							}
							flag = true;
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractPlantDtlTOs = tempInternalRequest;
				}, $scope.deleteMaterialDetails = function() {
					deleteMaterialDtlds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractMaterialDtlTOs, function(value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteMaterialDtlds.push(value.id);
							}
							flag = true;
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractMaterialDtlTOs = tempInternalRequest;
				}, $scope.deleteServiceDetails = function() {
					deleteServiceDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractServiceDtlTOs, function(value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteServiceDtlIds.push(value.id);
							}
							flag = true;
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractServiceDtlTOs = tempInternalRequest;
				},

				$scope.deleteSowDetails = function() {
					deleteSowDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.precontractSowDtlTOs, function(value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteSowDtlIds.push(value.id);
							}
							flag = true;
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.precontractSowDtlTOs = tempInternalRequest;
				},

				$scope.saveInternalRequest = function(apprvStatus, contarctStageStatus) {
					var validateFalg = true;
					if (apprvStatus == 4) {
						var resultdata = $scope.onHoldInternalRequest(apprvStatus, contarctStageStatus);
						resultdata.then(function(data) {
							deferred.resolve(data);
						});
					}
					if (apprvStatus == 2) {
						
						if($scope.saveManFlag){
						validateFalg = $scope.validateProcurmentDetails();
						}
						if($scope.saveMaterialFlag){
						validateFalg = $scope.validateProcurmentDetails();
						}
						if($scope.savePlantFlag){
							validateFalg = $scope.validateProcurmentDetails();
							}
						if($scope.saveServiceFlag){
							validateFalg = $scope.validateProcurmentDetails();
							}
						if($scope.saveSOWFlag){
							validateFalg = $scope.validateProcurmentDetails();
							}
						else{
							GenericAlertService.alertMessage("Please Save the Details before Approval", "Warning");
							return;
						}
					}
					if (apprvStatus == 3) {
						validateFalg = $scope.validateProcurmentDetails();
					}
					if (validateFalg) {
						PrecontractSubmitApprRequestFactory.submitOrApproveInternalRequest($scope.preContractObj, apprvStatus, projId, $scope.approverUserMap);
					}
				}, $scope.onHoldInternalRequest = function(apprvStatus, contarctStageStatus) {
					var savereq = {
						"preContractTO" : $scope.preContractObj,
						"apprvStatus" : apprvStatus,
						"contarctStageStatus" : contarctStageStatus,
						"projId" : projId
					};
					PreContractInternalService.saveInternalPreContracts(savereq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal("Precontract put on-hold successfully ", "Info");
						succMsg.then(function() {
							deferred.resolve(data);
						});

					}, function(error) {
						GenericAlertService.alertMessage("Error occured,Please try again", "Error");
					});

				}, $scope.validateProcurmentDetails = function() {
					if ($scope.preContractObj.preContractEmpDtlTOs != null && $scope.preContractObj.preContractEmpDtlTOs.length>0) {
						return true;
					} else if ($scope.preContractObj.preContractPlantDtlTOs != null && $scope.preContractObj.preContractPlantDtlTOs.length > 0) {
						return true;
					} else if ($scope.preContractObj.preContractMaterialDtlTOs != null && $scope.preContractObj.preContractMaterialDtlTOs.length > 0) {
						return true;
					} else if ($scope.preContractObj.preContractServiceDtlTOs != null && $scope.preContractObj.preContractServiceDtlTOs.length > 0) {
						return true;
					} else if ($scope.preContractObj.precontractSowDtlTOs != null && $scope.preContractObj.precontractSowDtlTOs.length > 0) {
						return true;
					}
					else {
						GenericAlertService.alertMessage("Please add atleast any one of procurement records", "Warning");
					}
					return false;

				}, $scope.validateTabsData = function() {
					if ($scope.procureForm.$valid) {
						return true;
					} else {
						GenericAlertService.alertMessage("Please enter manpower details", "Warning");
						return false;
					}

				}, $scope.saveManPowerDetails = function(item) {
					console.log('saveManPowerDetails  ==> 33   ',item)
					if ($scope.preContractObj.preContractEmpDtlTOs != null && $scope.preContractObj.preContractEmpDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractEmpDtlTOs,function(value,key){
							var fromDate = new Date(value.startDate);
							var toDate = new Date(value.finishDate);
							if (fromDate > toDate) {
								GenericAlertService.alertMessage('FinishDate must be greater than StartDate', 'Warning');
								forEach.break();
								return;
							}
						});
					var savereq = {
						"preContractEmpDtlTOs" : $scope.preContractObj.preContractEmpDtlTOs,
						"preContractId" : $scope.preContractObj.id,
						"empDtlIds" : deleteEmpDtlIds
					};
					PreContractInternalService.saveManPowerDetails(savereq).then(function(data) {
						$scope.preContractObj.preContractEmpDtlTOs = data.preContractEmpDtlTOs
						GenericAlertService.alertMessage("Stage1 Manpower details are saved successfully", "Info");
						$scope.saveManFlag = true;
					},

					function(error) {
						GenericAlertService.alertMessage("Stage1 Manpower details are  Failed To save", "Error");
					});
					} else {
						GenericAlertService.alertMessage("Please enter manpower details", "Warning");
						
					}

				},

				$scope.savePlantDetails = function() {
					if ($scope.preContractObj.preContractPlantDtlTOs != null && $scope.preContractObj.preContractPlantDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractPlantDtlTOs,function(value,key){
							var fromDate = new Date(value.startDate);
							var toDate = new Date(value.finishDate);
							if (fromDate > toDate) {
								GenericAlertService.alertMessage('StartDate must be greater than FinishDate', 'Warning');
								forEach.break();
								return;
							}
						});
					var savereq = {
						"preContractPlantDtlTOs" : $scope.preContractObj.preContractPlantDtlTOs,
						"preContractId" : $scope.preContractObj.id,
						"plantDtlIds" : deletePlantDtlIds,
					};
					PreContractInternalService.savePreContratPlants(savereq).then(function(data) {
						$scope.preContractObj.preContractPlantDtlTOs = data.preContractPlantDtlTOs

						GenericAlertService.alertMessage("Stage1 Plant details are saved successfully", "Info");
						$scope.savePlantFlag = true;
					},

					function(error) {
						GenericAlertService.alertMessage("Stage1 Plant details are  Failed To save", "Error");
					});
					} else {
						GenericAlertService.alertMessage("Please enter plant details", "Warning");
					}
				}, $scope.saveMaterialDetails = function() {

					if ($scope.preContractObj.preContractMaterialDtlTOs != null && $scope.preContractObj.preContractMaterialDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractMaterialDtlTOs,function(value,key){
							var fromDate = new Date(value.startDate);
							var toDate = new Date(value.finishDate);
							if (fromDate > toDate) {
								GenericAlertService.alertMessage('StartDate must be greater than FinishDate', 'Warning');
								forEach.break();
								return;
							}
						});
					var savereq = {
						"preContractMaterialDtlTOs" : $scope.preContractObj.preContractMaterialDtlTOs,
						"preContractId" : $scope.preContractObj.id,
						"materialDtlIds" : deleteMaterialDtlds
					};
					PreContractInternalService.savePreContratMaterials(savereq).then(function(data) {
						$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs

						GenericAlertService.alertMessage("Stage1 Material details are saved successfully", "Info");
						$scope.saveMaterialFlag = true;
					},

					function(error) {
						GenericAlertService.alertMessage("Stage1 Material details are  Failed To save", "Error");
					});
					} else {
						GenericAlertService.alertMessage("Please enter material details", "Warning");
					}
					
				}, $scope.saveServiceDetails = function() {

					if ($scope.preContractObj.preContractServiceDtlTOs != null && $scope.preContractObj.preContractServiceDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractServiceDtlTOs,function(value,key){
							var fromDate = new Date(value.startDate);
							var toDate = new Date(value.finishDate);
							if (fromDate > toDate) {
								GenericAlertService.alertMessage('StartDate must be greater than FinishDate', 'Warning');
								forEach.break();
								return;
							}
						});
					var savereq = {
						"preContractServiceDtlTOs" : $scope.preContractObj.preContractServiceDtlTOs,
						"preContractId" : $scope.preContractObj.id,
						"serviceDtlIds" : deleteServiceDtlIds
					};
					PreContractInternalService.savePreContratServices(savereq).then(function(data) {
						$scope.preContractObj.preContractServiceDtlTOs = data.preContractServiceDtlTOs

						GenericAlertService.alertMessage("Stage1 Services details are saved successfully", "Info");
						$scope.saveServiceFlag = true;
					},

					function(error) {
						GenericAlertService.alertMessage("Stage1 Services details are  Failed To save", "Error");
					});
					} else {
						GenericAlertService.alertMessage("Please enter services details", "Warning");
					}
					
				}, $scope.saveSOWDetails = function() {

					if ($scope.preContractObj.precontractSowDtlTOs != null && $scope.preContractObj.precontractSowDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.precontractSowDtlTOs,function(value,key){
							var fromDate = new Date(value.startDate);
							var toDate = new Date(value.finishDate);
							if (fromDate > toDate) {
								GenericAlertService.alertMessage('StartDate must be greater than FinishDate', 'Warning');
								forEach.break();
								return;
							}
						});
					var savereq = {
						"precontractSowDtlTOs" : $scope.preContractObj.precontractSowDtlTOs,
						"preContractId" : $scope.preContractObj.id,
						"sowDtlIds" : deleteSowDtlIds
					};
					PreContractInternalService.savePreContractSOWTypes(savereq).then(function(data) {
						$scope.preContractObj.precontractSowDtlTOs = data.precontractSowDtlTOs

						GenericAlertService.alertMessage("Stage1 SOW details are saved successfully", "Info");
						$scope.saveSOWFlag = true;
					},

					function(error) {
						GenericAlertService.alertMessage("Stage1 SOW details are  Failed To save", "Error");
					});
					} else {
						GenericAlertService.alertMessage("Please enter sow details", "Warning");
					}

				}, $scope.getPreContractCostCodeBudget = function() {
					var resultdata = PreContractCostCodeBudgetFactory.getPreContractCostCodeSummary($scope.preContractObj);
				}, $scope.uploadContractDocs = function(contractId) {
					PrecontractDocUploadFactory.uplodPreContractDocs(contractId);
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);
