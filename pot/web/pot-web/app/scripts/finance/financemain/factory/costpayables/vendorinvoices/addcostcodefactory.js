'use strict';
app.factory('AddCostCodeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
    function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
        var generatePopUp;
        var service = {};
        service.generateCostCodes = function (actionType) {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/costpayables/vendorinvoices/assigncostcodestoinvoice.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom4',
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {
                   
                }]
            });
            return deferred.promise;
        }
        return service;
    }]);