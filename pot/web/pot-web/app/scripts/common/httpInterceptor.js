(function(){
    app.factory('httpInterceptor', ['localStorageService', function(localStorageService) {  
        var httpInterceptor = {
            request: function(config) {
                if (localStorageService.get('loginSuccess') 
                && config.url.indexOf('restcountries') === -1)
                    config.headers['pottoken'] = localStorageService.get('pottoken');
                return config;
            }
        };
        return httpInterceptor;
    }]);
    app.config(['$httpProvider', function($httpProvider) {  
        $httpProvider.interceptors.push('httpInterceptor');
    }]);
}());
