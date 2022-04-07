'use strict';

app
    .factory('Sessions', ["$resource", function ($resource) {
        return $resource('app/rest/account/sessions/:series', {}, {
            'getAll': { method: 'GET', isArray: true}
        });
    }]);



