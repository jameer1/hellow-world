'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("payrollrunrecords", {

        url: '/payrollrunrecords',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/employee/emppayrollrunrecords.html',
                controller: 'EmpPayRollRunRecordsController'
            }
        }
    })
}]).controller("EmpPayRollRunRecordsController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "EpsProjectSelectFactory", "GeneratePaySlipFactory",
    function ($scope, $q, $state, ngDialog, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService, ProfitCentrePopUpFactory, AssetCategorySelectFactory, EpsProjectSelectFactory, GeneratePaySlipFactory) {

    }]);