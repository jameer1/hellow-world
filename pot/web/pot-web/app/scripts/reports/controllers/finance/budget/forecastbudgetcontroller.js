'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("forecastbudget", {
        url: '/forecastbudget',
        data: {
            progress: []
        },
        views: {
            'content@': {
                'templateUrl': 'views/reports/finance/budget/forecastbudget.html',
                controller: 'ForecastBudgetController'
            }
        }
    })
}]).controller("ForecastBudgetController", ["$scope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "GenericAlertService",
    "$filter", "progressReportService", "stylesService", 'ngGridService', 'chartService',
    function ($scope, $state, $q, ngDialog, EpsProjectMultiSelectFactory, GenericAlertService,
        $filter, progressReportService, stylesService, ngGridService, chartService) {

        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = {};
                $scope.searchProject = data.searchProject;
                $scope.selectedProjIds = data.searchProject.projIds;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        }

    }]);
