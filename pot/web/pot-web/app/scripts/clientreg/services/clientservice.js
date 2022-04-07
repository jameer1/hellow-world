'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # User Service in the potApp.
 */
app.factory('ClientService', ["Restangular", "$http", "appUrl", "Principal", function (Restangular, $http, appUrl, Principal) {
	return {
		getClients: function (req) {
			var clients = Restangular.one("user/getClients").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return clients;
		},
		getClientByEmail: function (req) {
			var count = Restangular.one("user/getClientByEmail").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return count;
		},

		saveClient: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'user/saveClient',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("clientRegReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},
		deactivateClient: function (req) {
			var deleteStatus = Restangular.one("user/deactivateClient").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return deleteStatus;
		},
		saveUserProjects: function (req) {
			var saveStatus = Restangular.one("user/saveUserProjects").customPOST(
				req, '', {}, {
					ContentType: 'application/json'
				});
			return saveStatus;
		},
		getUserProjects: function (req) {
			var userProjs = Restangular.one("user/getUserProjects").customPOST(req,
				'', {}, {
					ContentType: 'application/json'
				});
			return userProjs;
		},
		getCountries: function () {
		//return $http.get("https://restcountries.eu/rest/v2/all");
			return $http.get("https://restcountries.com/v2/all");
		},

		getClientById: function (req) {
			return Restangular.one("user/getClientById").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
		},

		getClientMailTemplate: function (clientId) {

			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();

			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "user/getClientMailTemplate?clientId=" + clientId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		saveDefaultClientData: function (clientId) {
			Restangular.one("user/saveDefaultClientData?clientId=" + clientId).customPOST('', '', {}, {
				ContentType: 'application/json'
			});
		}
	}
}]);
