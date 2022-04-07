'use strict';
app.factory('BankFinanceOverDraftPaymentScheduleFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantDetailsListFactory", "ProcureCategoryFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, PlantDetailsListFactory, ProcureCategoryFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var bankFinanceOverDraftPaymentPopup;
	var service = {};

	service.bankFinanceOverDraftPaymentPopup = function (actionType) {
		var deferred = $q.defer();
		bankFinanceOverDraftPaymentPopup = ngDialog.open({
			template: 'views/finance/financemain/costpayables/bankfinanceoverdraftpaymentpopup.html',
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
