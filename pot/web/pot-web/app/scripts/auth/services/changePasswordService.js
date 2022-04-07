'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # CostCodeService in the potApp.
 */
app.factory('changePasswordService', ["Restangular", function(Restangular) {
	return {
		changeUserPassword : function(req) {
			var costcode = Restangular.one("user/changeUserPassword").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return costcode;
		}
	}

}]);
