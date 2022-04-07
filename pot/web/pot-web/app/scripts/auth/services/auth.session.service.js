'use strict';

app.factory('AuthServerProvider', ["$http", "$q", "Restangular", "localStorageService", "appUrl", "GenericAlertService", function($http, $q, Restangular, localStorageService, appUrl, GenericAlertService) {
	return {
		login : function(credentials) {
			var deferred = $q.defer();
			var data = {
				"username" : credentials.username,
				"password" : credentials.password,
				"clientcode" : credentials.clientCode
			};
			$http({
				url : appUrl.appurl + "account/authentication",
				method : "POST",
				params : data,
				headers : {
					"Content-Type" : "application/json"
				}

			}).then(function(response) {
				console.log('after success response' + response.data.token);
				localStorageService.set('pottoken', response.data.token);
				localStorageService.set('registeredUsers', response.data.registeredUsers);
				deferred.resolve(response.data);
			},function(error) {
				GenericAlertService.alertMessage("Please enter valid credentials", 'Warning');
			});
			return deferred.promise;
		},
		potlogin : function(credentials) {
			var deferred = $q.defer();
			var data = {
				"username" : credentials.username,
				"password" : credentials.password
			};
			$http({
				url : appUrl.appurl + "account/internalauth",
				method : "POST",
				params : data,
				headers : {
					"Content-Type" : "application/json"
				}

			}).then(function(response) {
				console.log('after success response' + response.data.token);
				localStorageService.set('pottoken', response.data.token);
				localStorageService.set('registeredUsers', null);
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},

		logout : function() {
			// logout from the server
			return $http({
                url: appUrl.appurl + 'logout',
                method: "POST",
                headers: {
                    "pottoken": localStorageService.get('pottoken')
                }
			});
		},
		getToken : function() {
			var token = localStorageService.get('pottoken');
			return token;
		},
		hasValidToken : function() {
			var token = this.getToken();
			return !!token;
		}
	};
}]);
