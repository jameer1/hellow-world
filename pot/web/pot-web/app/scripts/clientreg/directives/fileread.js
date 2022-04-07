'use strict';

/**
 * @ngdoc directive
 * @name potApp.module
 * @description # Directive to store imported file into a model object.
 */
app.directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                scope.$apply(function () {
                    scope.fileread = changeEvent.target.files[0];
                });
            });
        }
    }
}]);