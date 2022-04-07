'use strict';
app.factory('ApproveAnInvoiceFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
    function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
        var generatePopUp;
        var service = {};
        service.generateApproveInvoiceDetails = function (actionType) {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/costpayables/vendorinvoices/approveinvoicepopup.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom0',
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {

                }]
            });
            return deferred.promise;
        }
        return service;
    }]);