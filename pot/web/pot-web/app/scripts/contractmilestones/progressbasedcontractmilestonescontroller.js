'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("progressbasedcontractmilestones", {
        url: '/progressbasedcontractmilestones',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/projectstatus/progressbasedcontractmilestones.html',
                controller: 'ProgressBasedContractMilestonesController'
            }
        }
    })
}]).controller('ProgressBasedContractMilestonesController', ["$rootScope", "$scope", "ProjSOWService", "$q", "blockUI",
    "ProjectStatusService", "GenericAlertService", "ProjManPowerFactory", "ProjectCrewPopupService", "EpsService",
    "ProjectSettingCostItemFactory", "ProjectSettingSOWItemFactory", "ProjStatusMileStonesFactory",
    "ProjStatusSubContractFactory", "$filter", "TreeService", "ProjectStatusResourceStatusValueService", "EpsProjectSelectFactory",
    function ($rootScope, $scope, ProjSOWService, $q, blockUI,
        ProjectStatusService, GenericAlertService, ProjManPowerFactory, ProjectCrewPopupService, EpsService,
        ProjectSettingCostItemFactory, ProjectSettingSOWItemFactory, ProjStatusMileStonesFactory,
        ProjStatusSubContractFactory, $filter, TreeService, ProjectStatusResourceStatusValueService, EpsProjectSelectFactory) {

        $scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.tableData = [];
                $scope.searchProject = data.searchProject;
            },
                function (error) {
                    GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
                });
        }

    }]);