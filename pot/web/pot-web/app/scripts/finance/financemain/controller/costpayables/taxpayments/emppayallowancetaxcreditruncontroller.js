'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("emppayallowtax", {

        url: '/emppayallowtax',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/taxpayments/emppayallowancetaxcreditrun.html',
                controller: 'EmpPayAllowanceTaxCreditRunController'
            }
        }
    })
}]).controller("EmpPayAllowanceTaxCreditRunController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }
   
}]);