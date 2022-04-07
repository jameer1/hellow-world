'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("plantsbookvalue", {

        url: '/plantsbookvalue',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/assetsvalue/plantsbookvalue.html',
                controller: 'PlantsBookValueController'
            }
        }
    })
}]).controller("PlantsBookValueController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory",
    function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory) {

        $scope.getCountries = function () {

        }
        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.timeSheetSearchReq.projectLabelKeyTO = data.searchProject;
                $scope.getTimeSheetWeekCommenceDay($scope.timeSheetSearchReq.projectLabelKeyTO.projId);

            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }
    }]);