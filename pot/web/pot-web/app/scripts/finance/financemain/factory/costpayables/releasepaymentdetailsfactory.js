'use strict';
app.factory('ReleasePaymentDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
    function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
        var generatePopUp;
        var service = {};
        service.generateReleasePaymentDetails = function (actionType) {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/costpayables/vendorinvoices/releasepaymentdetailspopup.html',
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