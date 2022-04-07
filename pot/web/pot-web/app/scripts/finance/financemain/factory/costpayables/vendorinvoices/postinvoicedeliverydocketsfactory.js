'use strict';
app.factory('PostInvoiceDeliveryDocketsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
    function (ngDialog, $q, $filter, $timeout, $rootScope,GenericAlertService) {
        var generatePopUp;
        var service = {};
        service.openResourceDeliveryDockets = function (actionType,data,userProjMap,selectedData) {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/costpayables/vendorinvoices/postinvoicedeliverydocketspopup.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom0',
                closeByDocument: false,
                showClose: true,
                controller: ['$scope', function ($scope) {
                console.log('postinvoicedeliverydocketspopup.html   ');



               /* $scope.preContractCmpTOs = data.preContractTO.preContractCmpTOs;
                  $scope.preContractDtoTOs = data;

                  $scope.storeMap = data.storeMap;
                  $scope.projCostItemMap = data.projCostItemMap;
                  $scope.usersMap = data.usersMap;
                  $scope.companyMap = data.companyMap;
                  $scope.projSOWMap = data.projSOWMap;
                  $scope.procureCategoryMap = data.procureCategoryMap;
                  $scope.userProjMap = userProjMap;*/
                  $scope.selectedData = selectedData;
                  $scope.vendors = [];
                  $scope.loggedInUser=$scope.account.displayRole;

                
                //  $scope.po = { 'code': undefined, 'createdOn': undefined, 'id': null };
                  $scope.preContractData = { 'clientDetails': undefined };
                //  $scope.selectedData.date = $filter('date')(new Date(), "dd-MMM-yyyy");
                 $scope.selectedVendor = undefined;
                //  console.log(" preContractCmpTOs  ":+ JSON.stringify( $scope.preContractCmpTOs));
                 
               
                }]
             
               
             
            
            
            });
            return deferred.promise;
        }
        return service;
    }]);