'use strict';
app.factory('RevenueAssetSalesFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantDetailsListFactory", "ProcureCategoryFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, PlantDetailsListFactory, ProcureCategoryFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var addAssetSalesPopup;
	var service = {};

	service.addAssetSalesPopup = function (actionType) {
		var deferred = $q.defer();
		addAssetSalesPopup = ngDialog.open({
			template: 'views/finance/financemain/immovableassets/revenuethroughassetsalespopup.html',
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
