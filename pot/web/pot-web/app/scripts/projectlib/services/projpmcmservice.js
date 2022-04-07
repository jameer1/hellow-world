'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjSOR Service in the potApp.
 */
app.factory('ProjPMCMService', ["Restangular", function(Restangular) {
	return {
/*
    getProjPMCPCostStatements : function(getCostStatReq) {
		 console.log('getPMCMDetails service getCostStatReq');
          console.log(getCostStatReq);
     			var sorDetails = Restangular.one(
     					"projectlib/getPMCPItems").customPOST(getCostStatReq, '',
     					{}, {
     						ContentType : 'application/json'
     					});
     			console.log('sorDetails');
           console.log(sorDetails);
     			return sorDetails;
		},
		*/
		getPMCMDetails : function(req) {
		 console.log('getPMCMDetails service req');
     console.log(req);
			var sorDetails = Restangular.one(
					"projectlib/getPMCMItems").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			console.log('sorDetails');
      console.log(sorDetails);
			return sorDetails;
		},
		/*getProjSORItemsById : function(req) {
			var sorDetails = Restangular.one(
					"projectlib/getProjSORItemsById").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return sorDetails;
		},*/
		saveSORDetails : function(req) {
		console.log('saveSORDetails service req');
         console.log(req);
			var sorSaveStatus = Restangular.one(
					"projectlib/savePMCMItems").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});

					console.log('response sorSaveStatus');
          console.log(sorSaveStatus);
			return sorSaveStatus;
		},
		deactivateSORDetails : function(req) {
			var sorDeactivateStatus = Restangular.one(
					"projectlib/deleteSORItems").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return sorDeactivateStatus;
		},
		projSorifOnLoad : function(req) {
		console.log('projSorifOnLoad req');
		console.log(req);
			var pmcmLoad = Restangular.one(
					"projectlib/projPMCMifOnLoad").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			console.log('projSorifOnLoad pmcmLoad');
      console.log(pmcmLoad);
			return pmcmLoad;
		}

	}
}]);
