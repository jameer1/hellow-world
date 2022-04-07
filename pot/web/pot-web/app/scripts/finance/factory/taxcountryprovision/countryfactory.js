'use strict';

app.factory('TaxCountryFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "TaxCodeCountryService", "RegularPayservice", "NonRegularPayService", "GenericAlertService", "CountryService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, 
	TaxCodeCountryService, RegularPayservice, NonRegularPayService, GenericAlertService, CountryService) {
	var getCountry;
	var service = {};
	service.getCountryDetails = function() {
		var deferred = $q.defer();
		ngDialog.open({
			template : 'views/finance/taxcountryprovision/countrypopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.countryDetails = [];
				$scope.getCountryDetails = function() {
					var req = {
						"status" : 1
					}
					CountryService.getCountries().then(function(data) {
						$scope.countryDetails = data.countryInfoTOs;
					});

					// TaxCodeCountryService.getCountryDetails(req).then(function(data) {
					// 	$scope.countryDetails = data.countryTOs;
					// });
				}
				$scope.projcountryPopUp = function(selectedCountry) {
					const returnCountryObj = {
						"selectedCountry" : selectedCountry 
					};
					deferred.resolve(returnCountryObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
