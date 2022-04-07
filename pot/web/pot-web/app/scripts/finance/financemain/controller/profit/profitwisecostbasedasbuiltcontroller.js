'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("profitwisecostbasedasbuilt", {

        url: '/profitwisecostbasedasbuilt',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/profit/profitwisecostbasedasbuilt.html',
                controller: 'ProfitCenterWiseProfitsController'
            }
        }
    })
}]).controller("ProfitCenterWiseProfitsController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }
   
}]);