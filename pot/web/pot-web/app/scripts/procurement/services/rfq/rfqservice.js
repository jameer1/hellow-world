'use strict';

app.factory('RFQService', ["Restangular","$http","Principal","appUrl", function(Restangular,$http,Principal,appUrl){var FileSaver = require('file-saver'); {
	return {
		getPrecontractRFQsByUser : function(req) {
			var result = Restangular.one("procurement/getPreContractRFQs").customPOST(req, '', {},
					{
						ContentType : 'application/json'
					});
			return result;
		},
		savePreContratCompanies : function(req) {
			var saveDetails = Restangular.one("procurement/savePreContratCompanies").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return saveDetails;
		},
		submitBidQuotation : function(req) {
			var saveDetails = Restangular.one("procurement/submitBidQuotation").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return saveDetails;
		},
		getPreContratDocs : function(req) {

			var preContractDocs = Restangular.one("procurement/getPreContratDocs").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return preContractDocs;
		},
		getPreContratCompanies : function(req) {
			var preContarctCompanies = Restangular.one("procurement/getPreContratCompanies")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return preContarctCompanies;
		},
		getPreContratCmpDocs : function(req) {
			var result = Restangular.one("procurement/getPreContratCmpDocs").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return result;
		},

		getPreContratCmpDocsByType : function(req) {
			var result = Restangular.one("procurement/getPreContratCmpDocsByType").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		savePreContratCompnayDocs : function(filesArray,data) {
			console.log("savePreContratCompnayDocs function");
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
				url: appUrl.appurl + 'procurement/savePreContratCompnayDocs',
				transformRequest: function (data) {
				console.log(data);
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("precontractCompanyDocsSaveReqStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		getPreContractCmpQuoteDetails : function(req) {
			var result = Restangular.one("procurement/getPreContractCmpQuoteDetails").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		getPreContractDistributionDocs : function(req) {
			var result = Restangular.one("procurement/getPreContractDistributionDocs").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		savePreContractDistributionDocs : function(req) {
			var result = Restangular.one("procurement/savePreContractDistributionDocs").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		sendPreContractDocsToCompanies : function(req) {
			var result = Restangular.one("procurement/sendPreContractDocsToCompanies").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		getPreContractsForRfq : function(req) {
			var result = Restangular.one("procurement/getPreContractsForRfq").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return result;
		},
		getPreContractCmpDistributionDocs : function(req) {
			var result = Restangular.one("procurement/getPreContractCmpDistributionDocs")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		saveTransmittalMsg : function(req) {
			var result = Restangular.one("procurement/saveTransmittalMsg").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		downloadProcurementDocs : function(req) {
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
			var fileName = req.fileName;
			var url = appUrl.appurl + "procurement/downloadProcurementDocs?recordId=" + req.recordId + "&category="+req.category;			
			console.log("file name:"+fileName);
			$http({method: 'GET', url: url,responseType:"arraybuffer"}).then(function(result){
				console.log(result);
				var file = new Blob([result.data], {
					type:  'text/plain'
				});
				FileSaver.saveAs(file, fileName);
				console.log("end");
			});
		}
	}}
}]);
