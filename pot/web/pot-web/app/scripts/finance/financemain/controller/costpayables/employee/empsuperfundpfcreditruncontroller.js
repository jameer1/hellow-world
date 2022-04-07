'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("superfundcreditrun", {

        url: '/superfundcreditrun',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/employee/empsuperfundpfcreditrun.html',
                controller: 'EmpSuperFundPFCreditRunController'
            }
        }
    })
}]).controller("EmpSuperFundPFCreditRunController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "GeneratePaySlipFactory",
    function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, GeneratePaySlipFactory) {
        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = data.searchProject;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }
    }]);