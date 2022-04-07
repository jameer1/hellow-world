'use strict';
app.factory('InsurancePolicyListFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantDetailsListFactory", "ProcureCategoryFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService","ProfitCentrePopUpFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, PlantDetailsListFactory, ProcureCategoryFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService,ProfitCentrePopUpFactory) {
    var addInsurancePolicyListPopup;
    var service = {};

    service.addInsurancePolicyListPopup = function (actionType) {
        var deferred = $q.defer();
        addInsurancePolicyListPopup = ngDialog.open({
            template: 'views/finance/financemain/costpayables/insurance/insurancepolicylistpopup.html',
            closeByDocument: false,
            showClose: false,
            className: 'ngdialog-theme-plain ng-dialogueCustom0',
            controller: ['$scope', function ($scope) {
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

            }]
        });
        return deferred.promise;
    }
    return service;
}]);
