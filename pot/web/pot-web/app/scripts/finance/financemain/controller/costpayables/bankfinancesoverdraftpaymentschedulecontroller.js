'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("bankfinanceoverdraftpaymentschedule", {

        url: '/bankfinanceoverdraftpaymentschedule',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/bankfinanceoverdraftpaymentschedule.html',
                controller: 'BankFinancesOverDraftPaymentScheduleController'
            }
        }
    })
}]).controller("BankFinancesOverDraftPaymentScheduleController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "BankFinanceOverDraftListFactory", "BankFinanceOverDraftPaymentScheduleFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, BankFinanceOverDraftListFactory, BankFinanceOverDraftPaymentScheduleFactory) {

    $scope.getCountries = function () {

    }
   
    $scope.addOverdraftPayment = function(actionType) {
		if (actionType == 'Edit') {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var bankFinanceOverDraftPaymentPopup = BankFinanceOverDraftPaymentScheduleFactory.bankFinanceOverDraftPaymentPopup(actionType);
		bankFinanceOverDraftPaymentPopup.then(function(data) {
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
		});
	}
}]);