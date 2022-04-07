'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("statusonprogressclaim", {

        url: '/statusonprogressclaim',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/receivables/statusonprogressclaims.html',
                controller: 'StatusOnProgressClaimController'
            }
        }
    })
}]).controller("StatusOnProgressClaimController", ["$scope", "$q", "$state", "ngDialog", "EmpRegisterService", "UserEPSProjectService", "GenerateInvoiceFactory", "ProjectCrewPopupService", "GenericAlertService", "EpsProjectSelectFactory", "AllClaimsSelectFactory","blockUI",
    function ($scope, $q, $state, ngDialog, EmpRegisterService, UserEPSProjectService, GenerateInvoiceFactory, ProjectCrewPopupService, GenericAlertService, EpsProjectSelectFactory, AllClaimsSelectFactory, blockUI) {

        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = data.searchProject;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }

        $scope.newClaim = function () {
            var newClaimPopup = AllClaimsSelectFactory.getClaimDetails();
            newClaimPopup.then(function (data) {
                
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while fetching Details", 'Error');
            });
        }
    }]);