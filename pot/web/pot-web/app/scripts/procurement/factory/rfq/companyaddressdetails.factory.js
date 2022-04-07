'use strict';
app.factory('CompanyAddressDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "CompanyService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, CompanyService) {
	var companyDetailsPopUp;
	var service = {};
	service.getCmpAddress = function(req) {
		var deferred = $q.defer();
		var address = [];
		var companyDetailsPromise = CompanyService.getCompanyDetails(req);
		companyDetailsPromise.then(function(data) {
			var companyData = data.companyTOs;
			angular.forEach(companyData, function(value) {
				address = value.addressList;
			});
			var companyDetailsPopUp = service.companyDetailsPopUp(address);
			companyDetailsPopUp.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting company address details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting company address details", 'Error');
		});
		return deferred.promise;
	}, service.companyDetailsPopUp = function(addressTOs) {
		var deferred = $q.defer();
		companyDetailsPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/companyaddress.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.addressTOs = addressTOs;
				$scope.selectAddress = function(addressTO) {
					deferred.resolve(addressTO);
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
