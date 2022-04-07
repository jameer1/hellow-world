'use strict';

app.factory('FinanceCountryFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "TaxCodeCountryService", "RegularPayservice", "NonRegularPayService", "GenericAlertService", "CountryService", "CountryProvinceCodeService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, 
	TaxCodeCountryService, RegularPayservice, NonRegularPayService, GenericAlertService, CountryService,CountryProvinceCodeService ) {
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
					var countryProvinceCodeGetReq = {
							"status": 1,
						};
						CountryProvinceCodeService.getCountryProvinceCodes(countryProvinceCodeGetReq).then(function (data) {
							$scope.countryDetails = data.countryProvinceCodeToTOs;

						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting Material Details", "Error");
						});
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
