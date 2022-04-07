'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("emppayrolltax", {

        url: '/emppayrolltax',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/taxpayments/emppayrolltaxcredittun.html',
                controller: 'EmpPayRollTaxCreditRunController'
            }
        }
    })
}]).controller("EmpPayRollTaxCreditRunController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }
   
}]);