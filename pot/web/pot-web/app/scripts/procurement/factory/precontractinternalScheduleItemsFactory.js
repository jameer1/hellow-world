'use strict';

app.factory('PreContractInternalScheduleItemsFactory', ["ngDialog", "$q", "$rootScope", "PreContractInternalService", "GenericAlertService", "PrecontractDocUploadFactory", "PreContractCostPopupFactory", "PreContractCostCodeBudgetFactory", "ProcurementSubCategoryFactory", "PreContractProjEmpClassFactory", "PreContractProjPlantClassFactory", "PreContractProjServiceClassFactory", "PrecontractMaterialFactory", "PreContractStoreFactory", "PrecontractSOWFactory", "PrecontractSubmitApprRequestFactory", "generalservice" , function (ngDialog, $q, $rootScope, PreContractInternalService,
	GenericAlertService, PrecontractDocUploadFactory, PreContractCostPopupFactory, PreContractCostCodeBudgetFactory,
	ProcurementSubCategoryFactory, PreContractProjEmpClassFactory, PreContractProjPlantClassFactory, PreContractProjServiceClassFactory,
	PrecontractMaterialFactory, PreContractStoreFactory, PrecontractSOWFactory, PrecontractSubmitApprRequestFactory,
	generalservice) {

	var procInternalApprovalPopUp;
	var service = {};
	service.procInternalApprovalPopUp = function (data, stateParamsRequestPage) {
		var deferred = $q.defer();
		var contractType;
		procInternalApprovalPopUp = ngDialog.open({
			template: '/views/procurement/pre-contracts/internalApproval/precontractinternalschitemspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
//				console.log('data 62662 =   ', data);
				$scope.costcode = [];
				$scope.defaultcostcode = {};
				$scope.currentTab1 = null;
				$scope.currentTabTitle = "";
				$scope.contractTypes = generalservice.getPreContractTypes();
				$scope.currencyList = data.currencyList;
				$scope.projEmpClassmap = data.projEmpClassMap;
				$scope.projPlantClassmap = data.projPlantMap;
				$scope.projMaterialClassmap = data.projMaterialClassMap;
				$scope.projServiceClassmap = data.projServiceMap;
				$scope.projSOWMap = data.projSOWMap;
				$scope.storeClassmap = data.storeMap;
				$scope.projStoreClassmap = data.projStoreMap;
				$scope.projcostCodeMap = data.projCostItemMap;
//				console.log('$scope.procureCategoryMap =   ', $scope.projcostCodeMap);
				$scope.procureCategoryMap = data.procureCategoryMap;
//				console.log('$scope.procureCategoryMap =   ', $scope.procureCategoryMap);
				$scope.preContractObj = data.preContractTO;
				$scope.showActionButtons = true;
				if (stateParamsRequestPage == 'showActionButtons'){
					stateParamsRequestPage = true;
					$scope.showActionButtons = false;
				}
				$scope.stateParamsRequestPage = (stateParamsRequestPage === undefined) ? true : stateParamsRequestPage;
				var deleteEmpDtlIds = [];
				var deletePlantDtlIds = [];
				var deleteMaterialDtlds = [];
				var deleteServiceDtlIds = [];
				var deleteSowDtlIds = [];
				$scope.preContractCostCodeSummaryTOs = [];
				var projId = data.preContractTO.projId;
				// This temporaryStoredData variable is to store actual data while selecting a tab,
				//if user trying to change tab without saving changes, then we will show information popup to user
				//stage1 approve
				var temporaryStoredData = null;
				var currentActiveTab = null;
				//$scope.stateParamsRequestPage = ($stateParams.request === 'request');
				//console.log("afds========",$scope.stateParamsRequestPage);
				//approve false stage1 Request
				if (stateParamsRequestPage == false) {
					if($scope.preContractObj.preContractType == "Engineering Services"){
						$scope.preContrctDetailTabs = [{
						title: 'Services',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractServiceDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Services',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_REJECT',
						}]
					}
					if($scope.preContractObj.preContractType == "Labour Hire Agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Manpower',
						stateParamsRequestPage: false,
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
						nameOfVariable: 'preContractEmpDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_manpower',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_REJECT',
						}]
					}
					if($scope.preContractObj.preContractType == "Plant Hire Agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Plants',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractPlantDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Plants',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_REJECT',
						}]
					}
					if($scope.preContractObj.preContractType == "Supply Agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Materials',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractMaterialDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_materials',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_REJECT',
						}]
					}
					if($scope.preContractObj.preContractType == "Purchase  Order"){
						$scope.preContrctDetailTabs = [{
						title: 'Manpower',
						stateParamsRequestPage: false,
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
						nameOfVariable: 'preContractEmpDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_manpower',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_REJECT',
						},{
						title: 'Materials',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractMaterialDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_materials',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_REJECT',
						},{
						title: 'Plants',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractPlantDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Plants',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_REJECT',
						}]
					}
					if($scope.preContractObj.preContractType == "Sub Contract agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Project Sub Contract',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalsow.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'precontractSowDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Projectsubcontract',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_REJECT',
						}]
					}
					if($scope.preContractObj.preContractType == "Professional Services agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Services',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractServiceDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Services',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_REJECT',
						}]
					}
					/*$scope.preContrctDetailTabs = [{
						title: 'Manpower',
						stateParamsRequestPage: false,
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
						nameOfVariable: 'preContractEmpDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_manpower',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_REJECT',

					}, {
						title: 'Materials',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractMaterialDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_materials',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_REJECT',

					}, {
						title: 'Plants',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractPlantDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALPLANT_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Plants',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALPLANT_REJECT',

					}, {
						title: 'Services',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'preContractServiceDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALSERVICES_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Services',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_REJECT',

					}, {
						title: 'Project Sub Contract',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalsow.html',
						stateParamsRequestPage: false,
						nameOfVariable: 'precontractSowDtlTOs',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_VIEW',
						appCodeTemplateKeyApproval: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL',
						appCodeTemplateKeySendBackToRequestor: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_SENDBACKTOREQUESTOR',
						appCodeTemplateKeyOnHold: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ONHOLD',
						appCodeTemplateKeyReject: 'PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_REJECT',
						stage1seleniumLocator: 'Stage1Approve_viewedit_Projectsubcontract',
						approveappCodeTemplateKey: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_VIEW',
						approveappCodeTemplateKeyApproval: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL',
						approveappCodeTemplateKeySendBackToRequestor: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_SENDBACKTOREQUESTOR',
						approveappCodeTemplateKeyOnHold: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ONHOLD',
						approveappCodeTemplateKeyReject: 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_REJECT',

					}];*/
				}

				//request
				if (stateParamsRequestPage) {
					if($scope.preContractObj.preContractType == "Engineering Services"){
						$scope.preContrctDetailTabs = [{
						title: 'Services',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1SERVICES_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1SERVICES_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1SERVICES_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1SERVICES_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Service',
						nameOfVariable: 'preContractServiceDtlTOs',
						}]
					}
					if($scope.preContractObj.preContractType == "Labour Hire Agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Manpower',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1MANPOWER_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1MANPOWER_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Manpower',
						nameOfVariable: 'preContractEmpDtlTOs',
						}]
					}
					if($scope.preContractObj.preContractType == "Plant Hire Agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Plants',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1PLANTS_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1PLANTS_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1PLANTS_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1PLANTS_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Plant',
						nameOfVariable: 'preContractPlantDtlTOs',
						}]
					}
					if($scope.preContractObj.preContractType == "Supply Agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Materials',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1MATERIALS_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1MATERIALS_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_SUBMIT',
						nameOfVariable: 'preContractMaterialDtlTOs',
						stage1seleniumLocator: 'Stage1Request_viewedit_Maretials'
						}]
					}
					if($scope.preContractObj.preContractType == "Purchase  Order"){
						$scope.preContrctDetailTabs = [{
						title: 'Manpower',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1MANPOWER_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1MANPOWER_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Manpower',
						nameOfVariable: 'preContractEmpDtlTOs',
						},{
						title: 'Materials',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1MATERIALS_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1MATERIALS_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_SUBMIT',
						nameOfVariable: 'preContractMaterialDtlTOs',
						stage1seleniumLocator: 'Stage1Request_viewedit_Maretials'
						},{
						title: 'Plants',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1PLANTS_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1PLANTS_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1PLANTS_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1PLANTS_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Plant',
						nameOfVariable: 'preContractPlantDtlTOs'
						}]
					}
					if($scope.preContractObj.preContractType == "Sub Contract agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Project Sub Contract',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalsow.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1SUBCONTRACT_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1SUBCONTRACT_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_ProjecSubContract',
						nameOfVariable: 'precontractSowDtlTOs',
						}]
					}
					if($scope.preContractObj.preContractType == "Professional Services agreement"){
						$scope.preContrctDetailTabs = [{
						title: 'Services',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1SERVICES_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1SERVICES_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1SERVICES_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1SERVICES_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Service',
						nameOfVariable: 'preContractServiceDtlTOs',
						}]
					}
					
					
					
					
					/*$scope.preContrctDetailTabs = [{
						title: 'Manpower',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmanpower.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1MANPOWER_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1MANPOWER_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Manpower',
						nameOfVariable: 'preContractEmpDtlTOs',

					}, {
						title: 'Materials',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalmaterial.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1MATERIALS_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1MATERIALS_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_SUBMIT',
						nameOfVariable: 'preContractMaterialDtlTOs',
						stage1seleniumLocator: 'Stage1Request_viewedit_Maretials'

					}, {
						title: 'Plants',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalplants.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1PLANTS_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1PLANTS_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1PLANTS_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1PLANTS_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Plant',
						nameOfVariable: 'preContractPlantDtlTOs',

					}, {
						title: 'Services',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalservices.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1SERVICES_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1SERVICES_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1SERVICES_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1SERVICES_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_Service',
						nameOfVariable: 'preContractServiceDtlTOs',

					}, {
						title: 'Project Sub Contract',
						url: '/views/procurement/pre-contracts/internalApproval/precontractinternalsow.html',
						appCodeTemplateKey: 'PROCURMT_INTRNLSTAGE1SUBCONTRACT_VIEW',
						appCodeTemplateKeySubmit: 'PROCURMT_INTRNLSTAGE1SUBCONTRACT_SUBMIT',
						reqappCodeTemplateKey: 'REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_VIEW',
						reqappCodeTemplateKeySubmit: 'REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_SUBMIT',
						stage1seleniumLocator: 'Stage1Request_viewedit_ProjecSubContract',
						nameOfVariable: 'precontractSowDtlTOs',

					}];*/
				}

				$scope.onClickTab1 = function (tab) {
					if (temporaryStoredData) {
						var diff = difference($scope.preContractObj[currentActiveTab.nameOfVariable], temporaryStoredData);
						_.isEmpty(diff) ? switchTab(tab) : $scope.$broadcast('activeTabEvent', currentActiveTab.title) && GenericAlertService.alertMessage("Please save (or) remove your changes!", "Warning");
					} else {
						switchTab(tab);
					}
				};

				function switchTab(tab) {
					currentActiveTab = tab;
					$scope.currentTabTitle = tab.title;
					contractType=tab.title;
					$scope.currentTab1 = tab.url;
					storeTemporaryData(tab);
				}

				function storeTemporaryData(tab) {
					temporaryStoredData = angular.copy($scope.preContractObj[tab.nameOfVariable]);
				}
				if($scope.preContrctDetailTabs) {
          $scope.onClickTab1($scope.preContrctDetailTabs[0]);
        }

				var saveManFlag = false;
				var saveMaterialFlag = false;
				var savePlantFlag = false;
				var saveServiceFlag = false;
				var saveSOWFlag = false;

				$scope.projCostCodeDetails = function (projCostCodeLabelKeyTO) {
					console.log('projId   ', projId);
					console.log('$scope.currentTabTitle   ', $scope.currentTabTitle);
					var costCodePopup = PreContractCostPopupFactory.getProjCostDetails(projId, $scope.currentTabTitle);
					costCodePopup.then(function (data) {
						projCostCodeLabelKeyTO.id = data.id;
						projCostCodeLabelKeyTO.code = data.code;
						projCostCodeLabelKeyTO.name = data.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting cost code details", 'Error');
					});
				};

				$scope.selectProcureSubCategory = function (type, item) {
					console.log('selectProcureSubCategory  ', type);
					
					
					var req = {
						procureClassName: type,
						procureId: "Services",
						subProcureName: null,
						"status": 1
					};
					var manpowerCategoryDetailsPopup = ProcurementSubCategoryFactory.getProcureCatgs(req);
					manpowerCategoryDetailsPopup.then(function (data) {
						item.procureSubCatgId = data.selectedSubcategory.proCatgId;
					}, function (error) {
						GenericAlertService.alertMessage('Error occurred while selecting procurement sub-catagories', "Error");
					});
				};
				$scope.projEmpClassDetails = function (projEmpClassLabelKeyTO) {
					var projEmpClassDetailsPopup = PreContractProjEmpClassFactory.getProjEmpClasses();
					projEmpClassDetailsPopup.then(function (data) {
						projEmpClassLabelKeyTO.id = data.projEmpclassTO.id;
						projEmpClassLabelKeyTO.code = data.projEmpclassTO.code;
						projEmpClassLabelKeyTO.name = data.projEmpclassTO.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting employee details", 'Error');
					});
				};
				$scope.projPlantClassDetails = function (projPlantClassLabelKeyTO) {
					var projPlantClassDetailsPopup = PreContractProjPlantClassFactory.getProjPlantClasses(projId);
					projPlantClassDetailsPopup.then(function (data) {
						$scope.today1 = new Date();
						projPlantClassLabelKeyTO.id = data.projPlantClassTO.id;
						projPlantClassLabelKeyTO.code = data.projPlantClassTO.code;
						projPlantClassLabelKeyTO.name = data.projPlantClassTO.name;
						projPlantClassLabelKeyTO.unitOfMeasure = data.projPlantClassTO.measureUnitTO.name;

					}, function (error) {
						GenericAlertService.alertMessage('Error occurred while selecting plant details', "Error");
					});
				};

				$scope.addMaterialItems = function () {
					var exitingSchMaterilMap = [];
					var popupDetails = PrecontractMaterialFactory.getMaterialClasses(projId, exitingSchMaterilMap, $scope.preContractObj);
					popupDetails.then(function (data) {
						$scope.today= new Date();
						$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while adding Material Details", "Error");
					});
				};

				$scope.serviceClassDetails = function (serviceClassLabelKeyTO) {
					var serviceClassificationPopup = PreContractProjServiceClassFactory.getServiceClasses();
					serviceClassificationPopup.then(function (data) {
						serviceClassLabelKeyTO.id = data.serviceClassTO.id;
						serviceClassLabelKeyTO.code = data.serviceClassTO.code;
						serviceClassLabelKeyTO.name = data.serviceClassTO.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting service classes", 'Error');
					});
				};

				$scope.selectDeliveryPlace = function (storeLabelKeyTO, projStoreLabelKeyTO) {
					var storeYardPopup = PreContractStoreFactory.getStocks(projId);
					storeYardPopup.then(function (data) {
						if (data.type == 2) {
							projStoreLabelKeyTO.id = data.storeStockTO.id;
							projStoreLabelKeyTO.code = data.storeStockTO.code;
							storeLabelKeyTO.id = 0;
						} else if (data.type == 1) {
							storeLabelKeyTO.id = data.storeStockTO.id;
							storeLabelKeyTO.code = data.storeStockTO.code;
							projStoreLabelKeyTO.id = 0;
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Store Delivery Details", 'Error');
					});
				};
				
				$scope.selectSOWItem = function (sowItemLabelKey, projStoreLabelKeyTO) {
					
					var existedSOWMap = [];
					angular.forEach($scope.preContractObj.precontractSowDtlTOs, function (value, key) {
						existedSOWMap[value.sowId] = true;
					});
					var sowPopup = PrecontractSOWFactory.getSOWDetails(projId, existedSOWMap, 'servicesow');
					sowPopup.then(function (data) {
						angular.forEach(data.projSOWItemTOs, function (value, key) {
							sowItemLabelKey.id = value.id,
							sowItemLabelKey.code = value.code,
							sowItemLabelKey.name = value.name
						});
					});
					
				};
				
				$scope.addManPowerRows = function () {
					
					$scope.preContractObj.preContractEmpDtlTOs.push({
						"select": false,
						"itemCode": null,
						"itemDesc": null,
						"preContractId": $scope.preContractObj.id,
						"projEmpLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"storeLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projStoreLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projCostLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"quantity": null,
						"startDate": '',
						"finishDate": '',
						"apprStatus": null,
						"projId": projId,
						"estimateCost": null
					});
					console.log('addManPowerRows 966966  ');
				};

				$scope.addPlantRows = function () {
					$scope.today1 = new Date();
					
					$scope.preContractObj.preContractPlantDtlTOs.push({
						"select": false,
						"itemCode": '',
						"itemDesc": '',
						"preContractId": $scope.preContractObj.id,
						"projPlantLabelKey": {
							"id": null,
							"code": null,
							"name": null,
							"unitOfMeasure": null
						},
						"storeLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projStoreLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projCostLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"quantity": null,
						"startDate": '',
						"finishDate": '',
						"apprStatus": null,
						"projId": projId,
						"estimateCost": null
					});
				};
				$scope.addMaterialRows = function () {
					var existedMaterialMap = [];
					$scope.preContractObj.preContractMaterialDtlTOs.push({
						"select": false,
						"itemCode": '',
						"itemDesc": '',
						"preContractId": $scope.preContractObj.id,
						"projMaterialLabelKey": {
							"id": null,
							"code": null,
							"name": null,
							"unitOfMeasure": null
						},
						"storeLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projStoreLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projCostLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"quantity": null,
						"startDate": '',
						"finishDate": '',
						"apprStatus": null,
						"projId": projId,
						"estimateCost": null
					});
				};
				$scope.addServiceRows = function () {
					
					$scope.today = new Date();
					$scope.preContractObj.preContractServiceDtlTOs.push({
						"select": false,
						"itemCode": '',
						"itemDesc": '',
						"preContractId": $scope.preContractObj.id,
						"projServiceLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"storeLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projStoreLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"projCostLabelKey": {
							"id": null,
							"code": null,
							"name": null
						},
						"quantity": null,
						"startDate": '',
						"finishDate": '',
						"apprStatus": null,
						"projId": projId,
						"estimateCost": null,
						"sowItemLabelKey": {
							"id": null,
							"code": null,
							"name": null
						}
					});
				};
				$scope.addProjSOWItems = function (item) {
					var existedSOWMap = [];
					angular.forEach($scope.preContractObj.precontractSowDtlTOs, function (value, key) {
						existedSOWMap[value.sowId] = true;
					});
					var sowPopup = PrecontractSOWFactory.getSOWDetails(projId, existedSOWMap,"Project Sub Contract",$scope.preContractObj);
					sowPopup.then(function (data) {
						angular.forEach(data.projSOWItemTOs, function (value, key) {
							$scope.preContractObj.precontractSowDtlTOs.push({
								"select": false,
								"sowId": value.id,
								"itemCode": '',
								"itemDesc": '',
								"preContractId": $scope.preContractObj.id,

								"quantity": null,
								"startDate": '',
								"finishDate": '',
								"apprStatus": null,
								"estimateCost": null
							});
						});

					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting sow details", 'Error');
					});
				};
				$scope.deleteManPowerDetails = function () {
					deleteEmpDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractEmpDtlTOs, function (value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteEmpDtlIds.push(value.id);
							}
							flag = true;
							GenericAlertService.alertMessage("Stage1 manpower details deleted successfully", "Info");
						}
					});

					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractEmpDtlTOs = tempInternalRequest;
				};
				$scope.deletePlantDetails = function () {
					deletePlantDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractPlantDtlTOs, function (value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deletePlantDtlIds.push(value.id);
								GenericAlertService.alertMessage("Stage1 Plant details deleted successfully", "Info");
							}
							flag = true;
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractPlantDtlTOs = tempInternalRequest;
				};
				$scope.deleteMaterialDetails = function () {
					deleteMaterialDtlds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractMaterialDtlTOs, function (value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteMaterialDtlds.push(value.id);
							}
							flag = true;
							GenericAlertService.alertMessage("Stage1 Material details deleted successfully", "Info");
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractMaterialDtlTOs = tempInternalRequest;
				};
				$scope.deleteServiceDetails = function () {
					deleteServiceDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.preContractServiceDtlTOs, function (value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteServiceDtlIds.push(value.id);
							}
							flag = true;
							GenericAlertService.alertMessage("Stage1 Service details deleted successfully", "Info");
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.preContractServiceDtlTOs = tempInternalRequest;
				};

				$scope.deleteSowDetails = function () {
					deleteSowDtlIds = [];
					var tempInternalRequest = [];
					var flag = false;
					angular.forEach($scope.preContractObj.precontractSowDtlTOs, function (value, key) {
						if (!value.select) {
							tempInternalRequest.push(value);
						} else {
							if (value.id > 0) {
								deleteSowDtlIds.push(value.id);
							}
							flag = true;
							GenericAlertService.alertMessage("Stage1 SOW details deleted successfully", "Info");
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

					}
					$scope.preContractObj.precontractSowDtlTOs = tempInternalRequest;
				};
				$scope.saveInternalRequest = function (apprvStatus) {
					if ($scope.preContractObj.preContractEmpDtlTOs.length > 0 && $scope.preContractObj.preContractEmpDtlTOs[($scope.preContractObj.preContractEmpDtlTOs.length)-1].id == null) {
						GenericAlertService.alertMessage("Please save Manpower schedule items before approval", "Warning");
						return;
					}
					if ($scope.preContractObj.preContractPlantDtlTOs.length > 0 && $scope.preContractObj.preContractPlantDtlTOs[($scope.preContractObj.preContractPlantDtlTOs.length)-1].id == null ) {
						GenericAlertService.alertMessage("Please save plant schedule items before approval", "Warning");
						return;
					}
					if ($scope.preContractObj.preContractMaterialDtlTOs.length > 0 && $scope.preContractObj.preContractMaterialDtlTOs[($scope.preContractObj.preContractMaterialDtlTOs.length)-1].id == null) {
						GenericAlertService.alertMessage("Please save material schedule items before approval", "Warning");
						return;
					}
					if ($scope.preContractObj.preContractServiceDtlTOs.length > 0 && $scope.preContractObj.preContractServiceDtlTOs[($scope.preContractObj.preContractServiceDtlTOs.length)-1].id == null) {
						GenericAlertService.alertMessage("Please save service schedule items before approval", "Warning");
						return;
					}
					if ($scope.preContractObj.precontractSowDtlTOs.length > 0 && $scope.preContractObj.precontractSowDtlTOs[($scope.preContractObj.precontractSowDtlTOs.length)-1].id == null) {
						GenericAlertService.alertMessage("Please save scope of work schedule items before approval", "Warning");
						return;
					}
					if ($scope.preContractObj.preContractEmpDtlTOs.length > 0 ||
					 $scope.preContractObj.preContractPlantDtlTOs.length > 0 ||
					  $scope.preContractObj.preContractMaterialDtlTOs.length > 0 ||
					   $scope.preContractObj.preContractServiceDtlTOs.length > 0 ||
					    $scope.preContractObj.precontractSowDtlTOs.length > 0) {
						/*if (apprvStatus == 4) {
							var resultdata = $scope.onHoldInternalRequest(apprvStatus);
							resultdata.then(function(data) {
								deferred.resolve(data);
							});
						}*/
						var resultdata = PrecontractSubmitApprRequestFactory.submitOrApproveInternalRequest($scope.preContractObj, apprvStatus, projId);
						resultdata.then(function (data) {
							$scope.closeThisDialog(procInternalApprovalPopUp);
							deferred.resolve(data);
						});

					} else {
						GenericAlertService.alertMessage("Please add atleast one of the schedule items before approval", "Warning");
					}

				};
				$scope.releaseInternalRequest = function (apprvStatus) {
					var resultdata = PrecontractSubmitApprRequestFactory.submitOrApproveInternalRequest($scope.preContractObj, apprvStatus, projId);
					resultdata.then(function (data) {
						deferred.resolve(data);
					});
				};
				$scope.onHoldInternalRequest = function (apprvStatus) {
					var savereq = {
						"preContractTO": $scope.preContractObj,
						"apprvStatus": apprvStatus,
						"projId": projId
					};
					PreContractInternalService.saveInternalPreContracts(savereq).then(function (data) {
						var succMsg = GenericAlertService.alertMessageModal("Pre-Contract put on-hold succuessfully ", "Info");
						succMsg.then(function () {
							deferred.resolve(data);
						});

					}, function (error) {
						GenericAlertService.alertMessage("Error occured,Please try again", "Error");
					});

				};
				$scope.validateTabsData = function () {
					if ($scope.procureForm.$valid) {
						return true;
					} else {
						GenericAlertService.alertMessage("Please enter manpower details", "Warning");
						return false;
					}

				};
				$scope.saveManPowerDetails = function (item) {
					var unitmeasure = false;
					if ($scope.preContractObj.preContractEmpDtlTOs != null && $scope.preContractObj.preContractEmpDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractEmpDtlTOs, function (value, key) {
							
						if((value.unitMeasure == " " || value.unitMeasure==undefined) || value.unitMeasure==null){
							unitmeasure=true;
						}
							/*
							 * var fromDate = new Date(value.startDate); var toDate = new
							 * Date(value.finishDate); if (fromDate > toDate) {
							 * GenericAlertService.alertMessage('FinishDate must be greater
							 * than StartDate', 'Warning'); forEach.break(); return; }
							 */
						});
						if(unitmeasure){
						GenericAlertService.alertMessage("Please select unitMeasure from dropdown List", "Warning");
						}else{
						saveManFlag = false;
						var savereq = {
							"preContractEmpDtlTOs": $scope.preContractObj.preContractEmpDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"empDtlIds": deleteEmpDtlIds
						};
						
						PreContractInternalService.saveManPowerDetails(savereq).then(function (data) {
							$scope.preContractObj.preContractEmpDtlTOs = data.preContractEmpDtlTOs;
							storeTemporaryData(currentActiveTab);
							GenericAlertService.alertMessage("Stage 1 Manpower details saved successfully", "Info");
							saveManFlag = true;
						},

							function (error) {
								GenericAlertService.alertMessage("Stage1 Manpower details are  Failed To save", "Error");
							});
							}
					} else {
						GenericAlertService.alertMessage("Please enter manpower details", "Warning");

					}

				};

				$scope.savePlantDetails = function () {
				var unitmeasure = false;
					if ($scope.preContractObj.preContractPlantDtlTOs != null && $scope.preContractObj.preContractPlantDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractPlantDtlTOs, function (value, key) {
				
						if((value.unitMeasure == " " || value.unitMeasure==undefined) || value.unitMeasure==null){
							unitmeasure=true;
						}
							/*
							 * var fromDate = new Date(value.startDate); var toDate = new
							 * Date(value.finishDate); if (fromDate > toDate) {
							 * GenericAlertService.alertMessage('StartDate must be greater
							 * than FinishDate', 'Warning'); forEach.break(); return; }
							 */
						});
						if(unitmeasure){
						GenericAlertService.alertMessage("Please select unitMeasure from dropdown List", "Warning");
						}else{
						var savereq = {
							"preContractPlantDtlTOs": $scope.preContractObj.preContractPlantDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"plantDtlIds": deletePlantDtlIds,
						};
						PreContractInternalService.savePreContratPlants(savereq).then(function (data) {
							$scope.preContractObj.preContractPlantDtlTOs = data.preContractPlantDtlTOs;
							storeTemporaryData(currentActiveTab);
							GenericAlertService.alertMessage("Stage 1 Plant details saved successfully", "Info");
							savePlantFlag = true;
						},

							function (error) {
								GenericAlertService.alertMessage("Stage1 Plant details are  Failed To save", "Error");
							});
							}
					} else {
						GenericAlertService.alertMessage("Please enter plant details", "Warning");
					}
				};
				$scope.saveMaterialDetails = function () {

					if ($scope.preContractObj.preContractMaterialDtlTOs != null && $scope.preContractObj.preContractMaterialDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractMaterialDtlTOs, function (value, key) {
							/*
							 * var fromDate = new Date(value.startDate); var toDate = new
							 * Date(value.finishDate); if (fromDate > toDate) {
							 * GenericAlertService.alertMessage('StartDate must be greater
							 * than FinishDate', 'Warning'); forEach.break(); return; }
							 */
						});
						var savereq = {
							"preContractMaterialDtlTOs": $scope.preContractObj.preContractMaterialDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"materialDtlIds": deleteMaterialDtlds
						};
						PreContractInternalService.savePreContratMaterials(savereq).then(function (data) {
							$scope.preContractObj.preContractMaterialDtlTOs = data.preContractMaterialDtlTOs;
							storeTemporaryData(currentActiveTab);
							GenericAlertService.alertMessage("Stage 1 Material details saved successfully", "Info");
							saveMaterialFlag = true;
						},

							function (error) {
								GenericAlertService.alertMessage("Stage1 Material details are  Failed To save", "Error");
							});
					} else {
						GenericAlertService.alertMessage("Please enter material details", "Warning");
					}

				};
				$scope.saveServiceDetails = function () {

					if ($scope.preContractObj.preContractServiceDtlTOs != null && $scope.preContractObj.preContractServiceDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.preContractServiceDtlTOs, function (value, key) {
							/*
							 * var fromDate = new Date(value.startDate); var toDate = new
							 * Date(value.finishDate); if (fromDate > toDate) {
							 * GenericAlertService.alertMessage('StartDate must be greater
							 * than FinishDate', 'Warning'); forEach.break(); return; }
							 */
						});
						var savereq = {
							"preContractServiceDtlTOs": $scope.preContractObj.preContractServiceDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"serviceDtlIds": deleteServiceDtlIds
						};
						PreContractInternalService.savePreContratServices(savereq).then(function (data) {
							$scope.preContractObj.preContractServiceDtlTOs = data.preContractServiceDtlTOs
							storeTemporaryData(currentActiveTab);
							GenericAlertService.alertMessage("Stage 1 Services details saved successfully", "Info");
							saveServiceFlag = true;
						},

							function (error) {
								GenericAlertService.alertMessage("Stage1 Services details are  Failed To save", "Error");
							});
					} else {
						GenericAlertService.alertMessage("Please enter services details", "Warning");
					}

				};
				$scope.saveSOWDetails = function () {

					if ($scope.preContractObj.precontractSowDtlTOs != null && $scope.preContractObj.precontractSowDtlTOs.length > 0) {
						angular.forEach($scope.preContractObj.precontractSowDtlTOs, function (value, key) {
							/*
							 * var fromDate = new Date(value.startDate); var toDate = new
							 * Date(value.finishDate); if (fromDate > toDate) {
							 * GenericAlertService.alertMessage('StartDate must be greater
							 * than FinishDate', 'Warning'); forEach.break(); return; }
							 */
						});
						var savereq = {
							"precontractSowDtlTOs": $scope.preContractObj.precontractSowDtlTOs,
							"preContractId": $scope.preContractObj.id,
							"sowDtlIds": deleteSowDtlIds
						};
						PreContractInternalService.savePreContractSOWTypes(savereq).then(function (data) {
							$scope.preContractObj.precontractSowDtlTOs = data.precontractSowDtlTOs
							storeTemporaryData(currentActiveTab);
							GenericAlertService.alertMessage("Stage 1 SOW details saved successfully", "Info");
							saveSOWFlag = true;
						},

							function (error) {
								GenericAlertService.alertMessage("Stage1 SOW details are  Failed To save", "Error");
							});
					} else {
						GenericAlertService.alertMessage("Please enter sow details", "Warning");
					}

				};
				$scope.getPreContractCostCodeBudget = function () {
					var resultdata = PreContractCostCodeBudgetFactory.getPreContractCostCodeSummary($scope.preContractObj,contractType);
				};
				$scope.uploadContractDocs = function (contractId) {
					PrecontractDocUploadFactory.uplodPreContractDocs(contractId);
				}

				function customizer(baseValue, value) {
					if (Array.isArray(baseValue) && Array.isArray(value)) {
						return _.isEqual(baseValue.sort(), value.sort())
					}
				}

				function difference(object, base) {
					function changes(object, base) {
						return _.transform(object, function (result, value, key) {
							if (key !== '$$hashKey') {
								if (!_.isEqualWith(value, base[key], customizer)) {
									var diff = (_.isObject(value) && _.isObject(base[key])) ? changes(value, base[key]) : value;
									if (!_.isEmpty(diff)) {
										result[key] = diff;
									}
								}
							}

						});
					}
					return changes(object, base);
				}

			}]
		});
		return deferred.promise;
	};
	return service;

}]);

app.directive('dynamicActiveTab', function () {

	var directive = {};

	directive.restrict = 'A';

	directive.scope = {
		dynamicActiveTab: '=',
	}
	directive.link = function (scope, element, attributes) {
		scope.$on('activeTabEvent', function (event, value) {
			setTimeout(function () {
				if (value === scope.dynamicActiveTab) {
					element.addClass('active');
				} else {
					element.removeClass('active');
				}
			}, 500);
		});
	}
	return directive;
});
