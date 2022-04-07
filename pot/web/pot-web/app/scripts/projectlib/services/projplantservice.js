'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Object Service in the potApp.
 */
app.factory('ProjPlantClassService', ["Restangular", function(Restangular) {
	return {
		getProjPlantClasses : function(req) {
			var obj = Restangular.one(
					"projectlib/getProjPlantClasses").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return obj;
		},
		getProjPlantClassMap : function(req) {
			var  response = Restangular.one("projectlib/getProjPlantClassMap").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return response;
		},
		
		
		projPlantClassifOnLoad : function(req) {
			var  response = Restangular.one("projectlib/projPlantClassifOnLoad").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return response;
		},
		saveProjPlantClasses : function(req) {
			var obj = Restangular.one("projectlib/saveProjPlantClasses").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return obj;
		},
		deleteProjPlantClasses : function(req) {
			var obj = Restangular.one("projectlib/deleteProjPlantClasses")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return obj;
		},
		getPlantClasses : function(req) {
			var obj = Restangular.one(
					"centrallib/getPlantClasses").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return obj;
		},
		getMeasurements : function(req) {
			var obj = Restangular.one("centrallib/getMeasurements")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return obj;
		}

	}
}]);
