'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # EPS Service in the potApp.
 */
app.factory('EpsService', ["Restangular", "$http", "$q", "appUrl", function (Restangular, $http, $q, appUrl) {
	return {
		getEPSDetails: function (req) {
			var epsDetails = Restangular.one("projectlib/projEPSOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsDetails;
		},
		getEPSUserProjects: function (req) {
		console.debug('eps service req');
		console.debug(req);
			var epsDetails = Restangular.one("projectlib/getEPSUserProjects").customPOST(req, '',
				{}, {
					ContentType: 'application/json'
				});
				console.debug('eps service epsDetails');
        console.debug(epsDetails);
			return epsDetails;
		},
		getEPSOnly: function (req) {
			var epsDetails = Restangular.one("projectlib/getEPSOnly").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsDetails;
		},

		getEPSProjectsById: function (req) {
			var epsIdDetails = Restangular.one("projectlib/getEPSProjectsById").customPOST(req, '',
				{}, {
					ContentType: 'application/json'
				});
			return epsIdDetails;
		},

		saveProjects: function (req) {
			var epsSaveStatus = Restangular.one("projectlib/saveProject").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsSaveStatus;
		},
		saveEpsStacture: function (req) {
			var epsSaveStatus = Restangular.one("projectlib/saveEpsStacture").customPOST(req, '',
				{}, {
					ContentType: 'application/json'
				});
			return epsSaveStatus;
		},

		deactivateEPSDetails: function (req) {
			var epsDeactivateStatus = Restangular.one("projectlib/deactivateEPSProject")
				.customPOST(req, '', {}, {
					ContentType: 'application/json'
				});
			return epsDeactivateStatus;
		},
		getEpsListMap: function (req) {
			var epsMap = Restangular.one("projectlib/getEpsListMap").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsMap;
		},


		getEpsProjMap: function (req) {
			var epsMap = Restangular.one("projectlib/getEpsProjMap").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsMap;
		},

		deactivateEPS: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/projlib/app/projectlib/deactivateEPS",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},

	}
}]);
