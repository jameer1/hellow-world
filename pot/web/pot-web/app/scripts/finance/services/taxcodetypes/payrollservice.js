'use strict';

app.factory('PayRollService', ["$q", "Restangular", function($q, Restangular) {

	return {
		getPayRoll : function(req) {
			var getStatus = Restangular.one("finance/getEmpPayTypeCycle").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return getStatus;
		},
		saveEmpPayTypeCycle : function(req) {
			var saveStatus = Restangular.one("finance/saveEmpPayTypeCycle").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveStatus;
		},
		deletePayRoll : function(req) {
			var deleteStatus = Restangular.one("finance/deleteEmpPayTypeCycle").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deleteStatus;
		},
		getPayRollCycle : function(req) {
			var getStatus = Restangular.one("finance/getPayRollCycle").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return getStatus;
		},
		savePayPeriodCycle : function(req) {
			var saveStatus = Restangular.one("common/savePaycycles").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveStatus;
		},
		getPaycycles : function(req) {
			//console.log("====getPaycycles=======")
			var payCycles = Restangular.one("common/getPaycycles").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			//console.log("====getPaycycles=====close",payCycles);
			return payCycles;
			
		},

	}

}]);
