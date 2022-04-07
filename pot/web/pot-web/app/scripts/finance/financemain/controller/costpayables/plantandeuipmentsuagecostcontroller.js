'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("plantandequipment", {

        url: '/plantandequipment',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/plantandequipmentusagecost.html',
                controller: 'PlantAndEquipmentUsageCostController'
            }
        }
    })
}]).controller("PlantAndEquipmentUsageCostController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory) {

    $scope.getCountries = function () {

    }
   
    $scope.getPlantOwners = function () {

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