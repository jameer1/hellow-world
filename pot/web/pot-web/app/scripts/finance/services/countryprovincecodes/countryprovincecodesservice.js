'use strict';
app.factory('CountryProvinceCodeService', ["Restangular", "$http", "$q", "appUrl", function(Restangular,$http, $q,appUrl) {
	
	return {
		
		saveCountryProvinceCode : function(countryProvinceCodeSaveReq) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/centrallib/saveCountryProvisionCodes",
				method : "POST",
				data: JSON.stringify(countryProvinceCodeSaveReq),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getCountryProvinceCodes : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/centrallib/getCountryProvisionCodes",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },
        deactivateCountryProvinceCodes : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/centrallib/deactivateCountryProvisionCodes",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        }
		
	}

}]);
