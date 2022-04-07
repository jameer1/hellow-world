'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("materialstoreitemscost", {

        url: '/materialstoreitemscost',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/materialstoreitemscostreport.html',
                controller: 'MaterialStoreItemsCostReportController'
            }
        }
    })
}]).controller("MaterialStoreItemsCostReportController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory) {

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

}]);