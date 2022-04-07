'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("vendorpaystatus", {
        url: '/vendorpaystatus',
        data: {
            progress: []
        },
        views: {
            'content@': {
                'templateUrl': 'views/reports/finance/costs/vendorpaymentstatuscosts.html',
                controller: 'VendorPaymentStatusCostController'
            }
        }
    })
}]).controller("VendorPaymentStatusCostController", ["$scope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "GenericAlertService",
    "$filter", "progressReportService", "stylesService", 'ngGridService', 'chartService', 'ProfitCentrePopUpFactory',
    function ($scope, $state, $q, ngDialog, EpsProjectMultiSelectFactory, GenericAlertService,
        $filter, progressReportService, stylesService, ngGridService, chartService, ProfitCentrePopUpFactory) {

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

        $scope.getProfitCentres = function () {
            var onLoadReq = {
                "status": 1
            };
            var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
            profitCentrePopup.then(function (data) {
                $scope.profitCentreName = data.selectedRecord.name;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
            });
        }
    }]);
