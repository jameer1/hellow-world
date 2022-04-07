'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("actualrevenuereceipts", {

        url: '/actualrevenuereceipts',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/receivables/actualrevenuereceipts.html',
                controller: 'ActualRevenueReceiptsController'
            }
        }
    })
}]).controller("ActualRevenueReceiptsController", ["$scope", "$q", "$state", "ngDialog", "EmpRegisterService", "UserEPSProjectService", "GenerateInvoiceToProjOwnerFactory", "ProjectCrewPopupService", "GenericAlertService", "EpsProjectSelectFactory",
    function ($scope, $q, $state, ngDialog, EmpRegisterService, UserEPSProjectService, GenerateInvoiceToProjOwnerFactory, ProjectCrewPopupService, GenericAlertService, EpsProjectSelectFactory) {

        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = data.searchProject;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }

        $scope.generateInvoice = function () {
            var addGenerateInvoicePopUp = GenerateInvoiceToProjOwnerFactory.generatePopUpDetails();
            addGenerateInvoicePopUp.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
            });
        }
    }]);