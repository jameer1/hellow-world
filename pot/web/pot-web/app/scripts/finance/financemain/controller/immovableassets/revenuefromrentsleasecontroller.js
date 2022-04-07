'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("revenuefromrentslease", {

        url: '/revenuefromrentslease',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/immovableassets/revenuefromrentslease.html',
                controller: 'RevenueFromRentsAndLease'
            }
        }
    })
}]).controller("RevenueFromRentsAndLease", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory) {

    $scope.getCountries = function () {

    }
   
    $scope.getProfitCentres = function (profitCentreTO) {
        var profitCentreService = {};
        var onLoadReq = {
            "status": 1
        };
        var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
        profitCentrePopup.then(function (data) {
            profitCentreTO.id = data.selectedRecord.id;
            profitCentreTO.code = data.selectedRecord.code;
            profitCentreTO.name = data.selectedRecord.name;
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
        });
    }

    $scope.getAssetCategory = function(assetCategoryTO, item) {
        var assetCategoryService = {};
        var onLoadReq = {
            "status" : 1
        };
        var categoryPopup = AssetCategorySelectFactory.getAssetCategory(onLoadReq);
        categoryPopup.then(function(data) {
            $scope.assetCategoryTOId = data.selectedRecord.id;
            $scope.assetCategoryTOCode = data.selectedRecord.code;
            item.assetCategoryName = data.selectedRecord.name;
        
        }, function(error) {
            GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
        });
    }

}]);