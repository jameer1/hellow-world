'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # CostCodeService in the potApp.
 */
app.factory('CostCodeService', ["Restangular", function(Restangular) {
	return {
		getCostCodes : function(req) {
			var costcode = Restangular.one("centrallib/getCostCodes").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return costcode;
		},
		getCostCodeClassMap : function(req) {
			var costcodeMap = Restangular.one("centrallib/getCostCodeClassMap").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return costcodeMap;
		},
		saveCostCodes : function(req) {
			var resultStatus = Restangular.one("centrallib/saveCostCodes").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		},
		deleteCostCodes : function(req) {
			var deleteStatus = Restangular.one("centrallib/deleteCostCodes").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return deleteStatus;
		}

	}

}]);
