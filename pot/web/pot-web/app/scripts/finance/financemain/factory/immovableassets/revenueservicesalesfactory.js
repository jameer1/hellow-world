'use strict';
app.factory('RevenueServiceSalesFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantDetailsListFactory", "ProcureCategoryFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, PlantDetailsListFactory, ProcureCategoryFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var addServiceSalesPopup;
	var service = {};

	service.addServiceSalesPopup = function (actionType) {
		var deferred = $q.defer();
		addServiceSalesPopup = ngDialog.open({
			template: 'views/finance/financemain/immovableassets/revenuethroughservicesalespopup.html',
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
