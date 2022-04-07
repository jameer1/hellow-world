'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("fixedassetsmarketvalue", {

        url: '/fixedassetsmarketvalue',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/assetsvalue/fixedassetsmarketvalue.html',
                controller: 'FixedAssetsMarketvValueController'
            }
        }
    })
}]).controller("FixedAssetsMarketvValueController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory) {

    $scope.getCountries = function () {

    }
}]);