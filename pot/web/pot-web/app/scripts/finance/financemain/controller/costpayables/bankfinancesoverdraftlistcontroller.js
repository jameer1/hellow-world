'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("bankfinanceoverdraftlist", {

        url: '/bankfinanceoverdraftlist',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/bankfinancesoverdraftlist.html',
                controller: 'BankFinancesOverDraftListController'
            }
        }
    })
}]).controller("BankFinancesOverDraftListController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory) {

    $scope.getCountries = function () {

    }
   
    $scope.addOverdraftList = function(actionType) {
		if (actionType == 'Edit') {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var bankFinanceOverDraftListPopup = BankFinanceOverDraftListFactory.bankFinanceOverDraftListPopup(actionType);
		bankFinanceOverDraftListPopup.then(function(data) {
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
		});
	}
}]);