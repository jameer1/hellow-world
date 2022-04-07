'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("profitstatusfinance", {
        url: '/profitstatusfinance',
        data: {
            progress: []
        },
        views: {
            'content@': {
                'templateUrl': 'views/reports/finance/profitstatus/profitstatusbasedonfinancerecords.html',
                controller: 'ProfitStatusBasedFinanceRecordsController'
            }
        }
    })
}]).controller("ProfitStatusBasedFinanceRecordsController", ["$scope", "$state", "$q", "ngDialog", "EpsProjectMultiSelectFactory", "GenericAlertService",
    "$filter", "progressReportService", "stylesService", 'ngGridService', 'chartService', 'CompanyMultiSelectFactory', "CountryService", "ProfitCentrePopUpFactory",
    function ($scope, $state, $q, ngDialog, EpsProjectMultiSelectFactory, GenericAlertService,
        $filter, progressReportService, stylesService, ngGridService, chartService, CompanyMultiSelectFactory, CountryService, ProfitCentrePopUpFactory) {

        $scope.getCompanyList = function () {
            var companyReq = {
                "status": 1
            }
            var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
            companyPopUp.then(function (data) {
                $scope.companyNameDisplay = data.selectedCompanies.companyName;

            })
        };

        $scope.countries = [];
        CountryService.getCountries().then(function (data) {
            $scope.countries = data.countryInfoTOs;
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
        });

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
