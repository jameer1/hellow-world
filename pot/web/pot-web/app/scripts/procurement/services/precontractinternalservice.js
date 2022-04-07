'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # PreContract Service in the potApp.
 */
app.factory('PreContractInternalService', ["Restangular", "$http", "appUrl", "Principal", function(Restangular,$http, appUrl, Principal) {
	
	
	return {
		saveProcurementNotifications: function (req) {
			return $http({
				url: appUrl.originurl + "/app/notification/saveProcurementNotifications",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getInternalPreContracts : function(req) {

			var contractDetails = Restangular.one("procurement/getInternalPreContracts")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return contractDetails;
		},
		getProjSettingsInternalPreContracts : function(req) {
			var projSettingsInternalPreContracts = Restangular.one("procurement/getProjSettingsInternalPreContracts")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
							
					});
			return projSettingsInternalPreContracts;
		},
		getWorkFlowstatus : function(isRequestPage) {
			var workFlowstatus = Restangular.one("procurement/getWorkFlowStatus?requestPage="+isRequestPage).customPOST(null,
					'', {}, {
						ContentType : 'application/json'
					});
			return workFlowstatus;
		},
		getProjSettingsForProcurement : function(req) {
			var result = Restangular.one("procurement/getProjSettingsForProcurement").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		getInternalPreContractPopupOnLoad : function(req) {
			var costCodes = Restangular.one("procurement/getInternalPreContractPopupOnLoad")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costCodes;
		},
		getPreContractListReport : function(req) {
			var costCodes = Restangular.one("procurement/getInternalPreContractPopupOnLoad")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costCodes;
		},
		getPreContractReqApprs : function(req) {
			var reqApprovals = Restangular.one("procurement/getPreContractReqApprs").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return reqApprovals;
		},
		getProjStoreStocks : function(req) {
			var result = Restangular.one("projectlib/getProjStoreStocks").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveInternalPreContracts : function(req) {
			var costSaveStatus = Restangular.one("procurement/saveInternalPreContracts")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costSaveStatus;
		},
		savePreContractsList : function(req) {
			var costSaveStatus = Restangular.one("procurement/savePreContractsList").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return costSaveStatus;
		},
		deactivatePreContracts : function(req) {
			var costDeactivateStatus = Restangular.one("procurement/deactivatePreContracts")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costDeactivateStatus;
		},
		deactivatePreContractDetails : function(req) {
			var costDeactivateStatus = Restangular.one("procurement/deactivatePreContractDetails")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costDeactivateStatus;
		},
		getPreContratEmpDetails : function(req) {
			var costDeactivateStatus = Restangular.one("procurement/getPreContratEmpTypes")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costDeactivateStatus;
		},
		getPreContratPlantDetails : function(req) {
			var costDeactivateStatus = Restangular.one("procurement/getPreContratPlants")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costDeactivateStatus;
		},
		getPreContratMaterialDetails : function(req) {
			var costDeactivateStatus = Restangular.one("procurement/getPreContratMaterials")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return costDeactivateStatus;
		},
		getPreContratServiceDetails : function(req) {
			var costDeactivateStatus = Restangular.one("procurement/getPreContratServices")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return serDetails;
		},
		saveManPowerDetails : function(req) {

			var saveStatus = Restangular.one("procurement/savePreContratEmpTypes").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});

			return saveStatus;

		},
		savePreContratMaterials : function(req) {

			var saveStatus = Restangular.one("procurement/savePreContratMaterials").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		savePreContratPlants : function(req) {

			var saveStatus = Restangular.one("procurement/savePreContratPlants").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		savePreContratServices : function(req) {

			var saveStatus = Restangular.one("procurement/savePreContratServices").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		savePreContratDocs : function(filesArray, data) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();               

			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;                        
					}
				});
			}
			return $http({
				headers: {'Content-Type': undefined,
				'pottoken': token },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'procurement/savePreContratDocs',
				transformRequest: function (data) {
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("precontractDocSaveReqStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},

		getPreContractList : function(req) {
			var contractList = Restangular.one("procurement/getLatestPreContracts").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return contractList;
		},
		getPreContractListReport : function(req) {
			var contractList = Restangular.one("procurement/getExternalPreContractPopupOnLoadReport").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return contractList;
		},
		getPreContractListEmpTaskReport : function(req) {
			var contractList = Restangular.one("procurement/getPreContractEmpTaskReport").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return contractList;
		},
		getPreContractListCostCodeReport : function(req) {
			var contractList = Restangular.one("procurement/getPreContractCostCodeReport").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return contractList;
		},
		getPreContratDocs : function(req) {
			var externalDetails = Restangular.one("procurement/getPreContratDocs").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return externalDetails;
		},
		savePreContractList : function(req) {

			var contractList = Restangular.one("procurement/savePreContractList").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return contractList;
		},
		saveReferenceDocument : function(req) {

			var saveReferenceDoc = Restangular.one("procurement/saveReferenceDocument").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return saveReferenceDoc;
		},
		savePreContractSOWTypes : function(req) {
			var saveReferenceDoc = Restangular.one("procurement/savePreContractSOWTypes")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return saveReferenceDoc;
		},
		deactivatePreContract : function(req) {
			var deletePreContract = Restangular.one("procurement/deactivatePreContract")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return deletePreContract;
		},
		getPreContractCostCodeSummary : function(req) {
			var deletePreContract = Restangular.one("procurement/getPreContractCostcodeSummary")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return deletePreContract;
		}

	}
}]);
