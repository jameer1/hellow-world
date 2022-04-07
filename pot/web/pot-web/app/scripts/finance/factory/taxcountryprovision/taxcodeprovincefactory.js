'use strict';

app.factory('TaxCodeCountryFactService', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "TaxCodeCountryService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, TaxCodeCountryService, GenericAlertService) {
	var service = {};
	
	service.getTaxCodeProvisions = function(countryName,provisionMap) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"countryName" : countryName
		}
		blockUI.start();
		TaxCodeCountryService.getTaxCodesByCountry(req).then(function(data) {
			blockUI.stop();
			service.openTaxCodesPopup(data.taxCountryProvisionTOs,provisionMap).then(function(data) {
				var returnProvinceObj = {
					"selectedDetails" : data.selectedDetails
				};
				deferred.resolve(returnProvinceObj);
			});

		}, function (error) {
			blockUI.stop();
		});
		return deferred.promise;

	},

	service.openTaxCodesPopup = function(taxCountryProvisionTOs,provisionMap) {
		var deferred = $q.defer();
		ngDialog.open({
			template : 'views/finance/taxcountryprovision/taxcodeprovincepopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.provinceDetails = taxCountryProvisionTOs;
				$scope.provisionMap=provisionMap;
				$scope.selectCountryDetails = function(selectedDetails) {
					var returnProvinceObj = {
						"selectedDetails" : selectedDetails
					};
					deferred.resolve(returnProvinceObj);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
