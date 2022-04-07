'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("paycalsheetrecords", {

        url: '/paycalsheetrecords',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/employee/emppaycalsheetrecords.html',
                controller: 'EmpPayCalculationSheetRecordsController'
            }
        }
    })
}]).controller("EmpPayCalculationSheetRecordsController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "GeneratePaySlipFactory",
    function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, GeneratePaySlipFactory) {

    }]);