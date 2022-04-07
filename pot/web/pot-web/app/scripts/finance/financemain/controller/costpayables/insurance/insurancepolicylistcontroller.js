'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("insurancepolicylist", {

        url: '/insurancepolicylist',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/insurance/insurancepolicylist.html',
                controller: 'InsurancePolicyListController'
            }
        }
    })
}]).controller("InsurancePolicyListController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "InsurancePolicyListFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, InsurancePolicyListFactory) {

    $scope.getCountries = function () {

    }
    $scope.addInsurancePolicyList = function(actionType) {
		if (actionType == 'Edit') {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var addInsurancePolicyListPopup = InsurancePolicyListFactory.addInsurancePolicyListPopup(actionType);
		addInsurancePolicyListPopup.then(function(data) {
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
		});
	}
    
}]);