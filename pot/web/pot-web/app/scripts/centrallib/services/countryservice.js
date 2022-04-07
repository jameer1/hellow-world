'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # CountryService in the potApp.
 */
app.factory('CountryService', ["Restangular", "$http", function(Restangular,$http) {
	return {
		saveCountries : function(req) {
			var saveReq = Restangular.one("common/saveCountries").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return saveReq;
		},
		saveCountryProvisions  : function(req) {
			var saveReq = Restangular.one("common/saveCountryProvisions").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return saveReq;
		},
		getCountries: function() {
			var saveReq = Restangular.one("common/countryInfoJSON").customPOST('', '', {}, {
						ContentType : 'application/json'
					});
			return saveReq;
			// return $http.get("http://api.geonames.org/countryInfoJSON?username=amit11patel6");
		},
		getProvince: function(req) {
			return Restangular.one("common/getCountryProvisionsJSON").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			// return $http.get("http://api.geonames.org/childrenJSON?username=amit11patel6&geonameId="+id);
		},
		getTimeZone: function(req) {
			return Restangular.one("common/getTimezoneJSON").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			// return $http.get("http://api.geonames.org/timezoneJSON?username=amit11patel6&lat="+lat+"&lng="+lng);
		},
		getCountryProvisions  : function(req) {
			var saveReq = Restangular.one("common/getCountryProvisions").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return saveReq;
		}

	}

}]);
