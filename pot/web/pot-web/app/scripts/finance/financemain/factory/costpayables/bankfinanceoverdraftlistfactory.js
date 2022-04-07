'use strict';
app.factory('BankFinanceOverDraftListFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantDetailsListFactory", "ProcureCategoryFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, PlantDetailsListFactory, ProcureCategoryFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var bankFinanceOverDraftListPopup;
	var service = {};

	service.bankFinanceOverDraftListPopup = function (actionType) {
		var deferred = $q.defer();
		bankFinanceOverDraftListPopup = ngDialog.open({
			template: 'views/finance/financemain/costpayables/bankfinanceoverdraftlistpopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			controller: ['$scope', function ($scope) {
                
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
