'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjCostCode Service in the potApp.
 */
app.factory('PlantServiceHistoryService', ["Restangular", function(Restangular) {
	return {
		getPlantServiceHistroyDetails : function(req) {
			var getPlantServiceData = Restangular.one("centrallib/getPlantClassService").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return getPlantServiceData;
		},
		getPlantServiceHistoryMap : function(req) {
			var getPlantServiceDataMap = Restangular.one("centrallib/getPlantServiceHistoryMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return getPlantServiceDataMap;
		},
		savePlantServiceHistoryRecords : function(req) {
			var savePlantServiceData = Restangular.one("centrallib/savePlantClassService").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return savePlantServiceData;
		},
		deactivatePlantServiceDetails : function(req) {
			var plantDeactivateStatus = Restangular.one("centrallib/deactivatePlantClassService").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return plantDeactivateStatus;
		},
	}
}]);
