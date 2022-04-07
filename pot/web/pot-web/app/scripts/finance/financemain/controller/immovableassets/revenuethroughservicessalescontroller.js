'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("revenuethroughservicessales", {

        url: '/revenuethroughservicessales',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/immovableassets/revenuethroughservicessales.html',
                controller: 'RevenueThroughServicesSalesController'
            }
        }
    })
}]).controller("RevenueThroughServicesSalesController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "RevenueServiceSalesFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, RevenueServiceSalesFactory) {

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

    $scope.addServiceSales = function(actionType) {
		if (actionType == 'Edit') {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var addServiceSalesPopup = RevenueServiceSalesFactory.addServiceSalesPopup(actionType);
		addServiceSalesPopup.then(function(data) {
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
		});
	}
    
}]);