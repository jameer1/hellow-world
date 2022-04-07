'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("profitwiserevenuestatus", {

        url: '/profitwiserevenuestatus',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/profit/profitcenterwiserevenuestatus.html',
                controller: 'ProfitCenterWiseRevenueStatusController'
            }
        }
    })
}]).controller("ProfitCenterWiseRevenueStatusController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }

}]);