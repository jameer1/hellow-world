'use strict';
app.factory('PostInvoiceTrackingRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
    function (ngDialog, $q, $filter, $timeout, $rootScope,GenericAlertService) {
        var generatePopUp;
        var service = {};
        service.generatePostInvoiceTrackingRecords = function (actionType,data,userProjMap,selectedData) {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/costpayables/vendorinvoices/postinvoicetrackingrecordspopup.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom0',
                closeByDocument: false,
                showClose: true,
                controller: ['$scope', function ($scope) {
                console.log('PostInvoiceTrackingRecordsFactory   ');

             
                  $scope.loggedInUser=$scope.account.displayRole;

                
                //  $scope.po = { 'code': undefined, 'createdOn': undefined, 'id': null };
                  $scope.preContractData = { 'clientDetails': undefined };
                //  $scope.selectedData.date = $filter('date')(new Date(), "dd-MMM-yyyy");
                 $scope.selectedVendor = undefined;
    
                }]
             
               
             
            
            
            });
            return deferred.promise;
        }
        return service;
    }]);
