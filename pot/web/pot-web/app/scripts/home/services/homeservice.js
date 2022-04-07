'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('HomeService', ["Restangular", function(Restangular) {


	return {
		getModulesByParent : function(req) {
			var modules = Restangular.one("auth/getModulesByParent").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return modules;
		}

	}
}]);
