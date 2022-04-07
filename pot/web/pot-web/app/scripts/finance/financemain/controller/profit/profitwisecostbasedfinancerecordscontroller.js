'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("profitwisecostbasedfinance", {

        url: '/profitwisecostbasedfinance',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/profit/profitwisecostbasedfinancerecords.html',
                controller: 'ProfitWiseCostBasedFinanceController'
            }
        }
    })
}]).controller("ProfitWiseCostBasedFinanceController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }

}]);