'use strict';

app.factory('TangibleClassService', ["Restangular", function(Restangular) {

	return {
		getTangibleGroups : function(req) {
			var service = Restangular.one("centrallib/getTangibleGroups").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		saveTangibleGroups : function(req) {
			var service = Restangular.one("centrallib/saveTangibleGroups").customPOST(req, '', {},
					{
						ContentType : 'application/json'
					});
			return service;
		},
		getTangibleClassMap : function(req) {
			var service = Restangular.one("centrallib/getTangibleClassMap").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return service;
		},
		deleteTangibleGroups : function(req) {
			var resultStatus = Restangular.one("centrallib/deleteTangibleGroups").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		}, 
		getCentralTangible : function(req) {
			var service = Restangular.one("centrallib/getCentralTangible").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		getTangibleItems : function(req) {
			var service = Restangular.one("centrallib/getTangibleItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		}

	}

}]);