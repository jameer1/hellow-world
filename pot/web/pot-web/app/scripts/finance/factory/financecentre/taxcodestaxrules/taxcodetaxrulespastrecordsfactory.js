'use strict';

app.factory('TaxCodesTaxRulesPastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var taxCodesTaxRulesPastRecordsPopup;
	var service = {};
	service.taxCodesTaxRulesPastRecordsPopupDetails = function (countryName,provisionName, financeCenterRecords) {
		var deferred = $q.defer();
		taxCodesTaxRulesPastRecordsPopup = ngDialog.open({
			template: 'views/finance/taxcodetaxrules/taxcodetaxrulespastrecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				//console.log("financeCenterRecordsData===2323==",financeCenterRecords);
				$scope.unitOfTaxCodes = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
               
			}]

		});
		return deferred.promise;
	}
	return service;
}]);