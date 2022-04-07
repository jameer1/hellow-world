'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjCostCode Service in the potApp.
 */
app.factory('ProjCostCodeService', ["Restangular", function(Restangular) {
	return {
		getCostDetails : function(req) {
			var costDetails = Restangular.one("projectlib/getProjCostItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costDetails;
		},
		projCostItemsOnLoad : function(req) {
			var costCodes = Restangular.one("projectlib/projCostItemifOnLoad").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costCodes;
		},
		saveProjCostItems : function(req) {
			var costSaveStatus = Restangular.one("projectlib/saveProjCostItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costSaveStatus;
		},
		deactivateCostDetails : function(req) {
			var costDeactivateStatus = Restangular.one("projectlib/deleteProjCostItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costDeactivateStatus;
		},
		getCostCodeMap : function(req) {
			var map = Restangular.one("projectlib/getProjCostCodeMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return map;
		},
		getMultiProjCostDetails : function(req) {
			var costDetails = Restangular.one("projectlib/getMultiProjCostDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costDetails;
		},
		getProjCostItemMap : function(req) {
			var costItemMap = Restangular.one("projectlib/getProjCostItemMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costItemMap;
		},
		getMultiProjCostMap : function(req) {
			var costDetails = Restangular.one("projectlib/getMultiProjCostMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return costDetails;
		},
		getCalendarSpecialWorkingNonworkingDays : function(req) {
			var result = Restangular.one("projschedules/getCalendarSpecialWorkingNonworkingDays").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		}
	}
}]);
