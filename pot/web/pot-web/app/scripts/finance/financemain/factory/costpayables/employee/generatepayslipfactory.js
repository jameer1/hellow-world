'use strict';
app.factory('GeneratePaySlipFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
    function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
        var service = {};
        var payslip = null;
        service.generatepayslip = function () {
            var deferred = $q.defer();
            payslip = ngDialog.open({
                template: 'views/finance/financemain/costpayables/employee/generatepayslip.html',
                className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
                scope: $rootScope,
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {

                }]
            });
            return deferred.promise;
        }
        return service;
    }]);
