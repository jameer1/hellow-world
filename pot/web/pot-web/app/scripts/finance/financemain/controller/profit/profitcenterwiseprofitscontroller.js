'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("profitcenterwiseprofits", {

        url: '/profitcenterwiseprofits',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/profit/profitcenterwiseprofitsstatus.html',
                controller: 'ProfitCenterWiseProfitsController'
            }
        }
    })
}]).controller("ProfitCenterWiseProfitsController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }
   
}]);