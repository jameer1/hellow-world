'use strict';

app.factory('Account', ["appUrl", "localStorageService", "$http", "$q", function (appUrl, localStorageService, $http, $q) {
    return {
        /* return $resource(appUrl.appurl+'account', {}, {
            'get': { method: 'GET', headers: { 'pottoken': localStorageService.get('pottoken') },params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                    
                        return response;
                    }
                }
            }
        }); */

        get: function () {
            return $http({
                url: appUrl.appurl + "account",
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "pottoken": localStorageService.get('pottoken')
                }
            });

        }

    }

}]);



