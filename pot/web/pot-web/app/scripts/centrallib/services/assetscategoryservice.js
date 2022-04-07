'use strict';

app.factory('AssetsCategoryService', ["Restangular", function(Restangular) {

	return {
		getAssetCategory : function(req) {
			var service = Restangular.one("centrallib/getAssetCategory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		saveAssetCategory : function(req) {
			var service = Restangular.one("centrallib/saveAssetCategory").customPOST(req, '', {},
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
		deleteAssetCategory : function(req) {
			var resultStatus = Restangular.one("centrallib/deleteAssetCategory").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		}

	}

}]);