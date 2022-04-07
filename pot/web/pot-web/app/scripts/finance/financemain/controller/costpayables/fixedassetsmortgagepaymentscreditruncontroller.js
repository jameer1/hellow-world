'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("mortgagepayments", {

        url: '/mortgagepayments',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/fixedassetsmortgagepaymentscreditrun.html',
                controller: 'FixedAssetMortgagePaymentsController'
            }
        }
    })
}]).controller("FixedAssetMortgagePaymentsController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory) {

    $scope.getCountries = function () {

    }
   
    $scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
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