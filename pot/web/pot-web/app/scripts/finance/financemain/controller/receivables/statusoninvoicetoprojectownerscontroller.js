'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("statusoninvoicetoprojectowner", {

        url: '/statusoninvoicetoprojectowner',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/receivables/statusoninvoicetoprojectowners.html',
                controller: 'StatusOnInvoiceToProjectOwnersController'
            }
        }
    })
}]).controller("StatusOnInvoiceToProjectOwnersController", ["$scope", "$q", "$state", "ngDialog", "EmpRegisterService", "UserEPSProjectService", "GenerateInvoiceFactory", "ProjectCrewPopupService", "GenericAlertService", "EpsProjectSelectFactory",
    function ($scope, $q, $state, ngDialog, EmpRegisterService, UserEPSProjectService, GenerateInvoiceFactory, ProjectCrewPopupService, GenericAlertService, EpsProjectSelectFactory) {

        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = data.searchProject;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }
    }]);