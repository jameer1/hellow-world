'use strict';

app.factory('AssetsMaintenanceCategoryService', ["Restangular", function(Restangular) {

	return {
		getAssetMaintenanceCategory : function(req) {
			var service = Restangular.one("centrallib/getAssetMaintenanceCategory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		saveAssetMaintenanceCategory : function(req) {
			var service = Restangular.one("centrallib/saveAssetMaintenanceCategory").customPOST(req, '', {},
					{
						ContentType : 'application/json'
					});
			return service;
		},
		deleteAssetMaintenanceCategory : function(req) {
			var resultStatus = Restangular.one("centrallib/deleteAssetMaintenanceCategory").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		}

	}

}]);