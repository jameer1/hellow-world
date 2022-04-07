'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Role Service in the potApp.
 */
app.factory('RoleService', ["Restangular", function(Restangular) {
	
	return {
		getRolePermissions : function(req) {
			var modules = Restangular.one("auth/getRolePermissions").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return modules;
		},
		getRoles : function(req) {
			var roles = Restangular.one("auth/getRoles").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return roles;
		},
		saveRoles : function(req) {
			var saveStatus = Restangular.one("auth/saveRole").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		saveRolePermission : function(req) {
			var saveRolePerStatus = Restangular.one("auth/saveRolePermissions")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return saveRolePerStatus;
		},
		deactivateRoles : function(req) {
			var deleteStatus = Restangular.one("auth/deleteRoles").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return deleteStatus;
		},
		getRoleServiceMap : function(req) {
			var roleMap = Restangular.one("auth/getRoleServiceMap").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return roleMap;
		}
	}
}]);
