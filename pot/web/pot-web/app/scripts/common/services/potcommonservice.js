'use strict';

app.factory('PotCommonService', ["Restangular", function(Restangular) {
	return {

		getUsersByModulePermission : function(req) {
			
			var users = Restangular.one("user/getUsersByModulePermission").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return users;
		},
		getTimeSheetWeekCommenceDay : function(req) {
			var users = Restangular.one("common/getTimeSheetWeek").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return users;
		},
		getSystemDate : function(req) {
			var users = Restangular.one("common/getSystemDate").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return users;
		},
		savePaycycles : function(req) {
			var payCycles = Restangular.one("common/savePaycycles").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return payCycles;
		},
		getEmpUsersOnly : function(req) {
			var users = Restangular.one("common/getEmpUsersOnly").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return users;
		},
		getProjUsersOnly : function(req) {
			var users = Restangular.one("common/getProjUsersOnly").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return users;
		},
		getTangiblesOfProjects: function(req) {
			var result = Restangular.one("common/getTangiblesOfProjects").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getUsersByProjectId: function(req) {
			var result = Restangular.one("common/getAllProjUsers").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		}
	}
}]);
