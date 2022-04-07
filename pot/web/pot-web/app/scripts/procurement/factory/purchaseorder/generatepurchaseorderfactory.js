'use strict';

app.factory('GeneratePurchaseOrderFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PurchaseOrderService", "blockUI", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope,
    PurchaseOrderService, blockUI, GenericAlertService) {
    var viewBidderFactoryPopUp;
    var service = {};
    service.getPurchaseOrderDetails = function () {
        var deferred = $q.defer();
        viewBidderFactoryPopUp = ngDialog.open({
                template: 'views/procurement/purchaseorders/purchaseorderdetails.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom1',
                showClose: false,
                closeByDocument: false,
                controller: ['$scope', function ($scope) {

                }]
            });
        return deferred.promise;
    };
    return service;

}]);
