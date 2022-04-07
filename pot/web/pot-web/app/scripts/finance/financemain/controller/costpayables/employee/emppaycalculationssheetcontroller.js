'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("emppaycalculationsheet", {

        url: '/emppaycalculationsheet',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/employee/emppaycalculationssheet.html',
                controller: 'EmpPayCalculationSheetController'
            }
        }
    })
}]).controller("EmpPayCalculationSheetController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "GeneratePaySlipFactory",
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

        $scope.generatepayslip = function () {
            var payslip = GeneratePaySlipFactory.generatepayslip();
            payslip.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error occurred while feteching Details", 'Info');
            })
        }
    }]);