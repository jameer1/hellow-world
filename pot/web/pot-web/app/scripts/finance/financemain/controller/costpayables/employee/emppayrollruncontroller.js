'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("generatepayroll", {

        url: '/generatepayroll',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/employee/emppayrollrun.html',
                controller: 'EmpPayRollRunController'
            }
        }
    })
}]).controller("EmpPayRollRunController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "GeneratePaySlipFactory",
    function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, GeneratePaySlipFactory) {

        $scope.getCountries = function () {

        }

        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = data.searchProject;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }

    }]);