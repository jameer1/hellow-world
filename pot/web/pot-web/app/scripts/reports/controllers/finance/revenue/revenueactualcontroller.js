'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("revenueactual", {
        url: '/revenueactual',
        data: {
            progress: []
        },
        views: {
            'content@': {
                'templateUrl': 'views/reports/finance/revenue/revenueactual.html',
                controller: 'RevenueActualController'
            }
        }
    })
}]).controller("RevenueActualController", ["$scope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "GenericAlertService",
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
