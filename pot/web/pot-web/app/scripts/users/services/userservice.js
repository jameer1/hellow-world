'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # User Service in the potApp.
 */
app.factory('UserService', ["Restangular", function(Restangular) {

	return {
		getUsers : function(req) {
			var users = Restangular.one("user/getUsers").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return users;
		},
		getVendorUsers : function(req) {
			var clientUsers = Restangular.one("user/getVendorUsers").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return clientUsers;
		},
		saveUser : function(req) {
			var data = angular.toJson(req, true);
			var saveUsers = Restangular.one("user/saveUser").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveUsers;
		},
		saveVendorUser : function(req) {
			var data = angular.toJson(req, true);
			var saveClientUser = Restangular.one("user/saveVendorUser").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveClientUser;
		},
		deleteUser : function(req) {
			var deleteStatus = Restangular.one("user/deleteUser").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deleteStatus;
		},		
		saveUserProjects : function(req) {
			var saveStatus = Restangular.one("user/saveUserProjects").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveStatus;
		},
		getUserProjects : function(req) {
			var userProjs = Restangular.one("user/getUserProjects").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return userProjs;
		},
		getUserServiceMap : function(req) {
			var userMap = Restangular.one("user/getUserServiceMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return userMap;
		},
		getUsersByClientId : function(req) {
			var userMap = Restangular.one("user/getUsersByClientId").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return userMap;
		},
		activateUser : function(req) {
			var activeStatus = Restangular.one("user/activateUser").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return activeStatus;
		},
		findByUserId : function(userId) {
			var employee = Restangular.one("user/findByUserId?userId=" + userId).customPOST('', '', {}, {
				ContentType : 'application/json'
			});
			return employee;
		},
	}
}]);
