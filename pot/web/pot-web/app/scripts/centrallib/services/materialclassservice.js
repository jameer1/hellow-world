'use strict';

app.factory('MaterialClassService', ["Restangular", function(Restangular) {

	return {
		getMaterialGroups : function(req) {
			var service = Restangular.one("centrallib/getMaterialGroups").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		saveMaterialGroups : function(req) {
			var service = Restangular.one("centrallib/saveMaterialGroups").customPOST(req, '', {},
					{
						ContentType : 'application/json'
					});
			return service;
		},
		getMaterialClassMap : function(req) {
			var service = Restangular.one("centrallib/getMaterialClassMap").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return service;
		},
		deleteMaterialGroups : function(req) {
			var resultStatus = Restangular.one("centrallib/deleteMaterialGroups").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		}, 
		getCentralMaterial : function(req) {
			var service = Restangular.one("centrallib/getCentralMaterial").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		}

	}

}]);