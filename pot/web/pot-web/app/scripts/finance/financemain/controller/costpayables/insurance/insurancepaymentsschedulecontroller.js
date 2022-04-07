'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("insurancepaymentsschedule", {

        url: '/insurancepaymentsschedule',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/insurance/insurancepaymentsschedule.html',
                controller: 'InsurancePaymentsScheduleController'
            }
        }
    })
}]).controller("InsurancePaymentsScheduleController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory) {

    $scope.getCountries = function () {

    }
    $scope.getProfitCentres = function (profitCentreTO) {
        var profitCentreService = {};
        var onLoadReq = {
            "status": 1
        };
        var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
        profitCentrePopup.then(function (data) {
            profitCentreTO.id = data.selectedRecord.id;
            profitCentreTO.code = data.selectedRecord.code;
            profitCentreTO.name = data.selectedRecord.name;
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
        });
    }
}]);