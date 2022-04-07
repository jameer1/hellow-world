'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ResourceCurveService', ["Restangular", "$http", function(Restangular, $http) {
	return {
		getResourceCurves : function(req) {
			var resource = Restangular.one('common/getResourceCurves').customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return resource;
		},
		saveResourceCurves : function(req) {
			var resource = Restangular.one("common/saveResourceCurves")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return resource;
		},
		deactivateResourceCurves : function(req) {
			var resource = Restangular
					.one("common/deactivateResourceCurves").customPOST(req, '',
							{}, {
								ContentType : 'application/json'
							});
			return resource;
		}

	}
}]);
