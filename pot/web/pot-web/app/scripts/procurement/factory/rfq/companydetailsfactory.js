'use strict';
app.factory('CompanyDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "CompanyService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		GenericAlertService, CompanyService) {
	var companyDetailsPopUp;
	var service = {};
	service.companyDetailsPopUp = function(companyTOs, contactsMap, addressMap,
			existingPrecontactCompanyMap) {
		var deferred = $q.defer();
		companyDetailsPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/precontractcompanypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.companyTOs = companyTOs;
				$scope.existingPrecontactCompanyMap = existingPrecontactCompanyMap;
				var selectedCompanies = [];
				var companyMap = [];
				$scope.companyRowSelect = function(companyTO) {
					if (companyTO.select) {
						selectedCompanies.push(companyTO);
						companyMap[companyTO.id] = companyTO;
					} else {
						selectedCompanies.pop(companyTO);
						companyMap[companyTO.id] = null;
					}
				}, $scope.addTOPrecontract = function() {
					var returnPopObj = {
						"companyTOs" : angular.copy(selectedCompanies),
						"companyMap" : companyMap,
						"cmpAddressMap" : addressMap,
						"cmpContactMap" : contactsMap,
					};
					if(returnPopObj.companyTOs.length > 0){
						selectedCompanies = [];
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();
					}else{
						GenericAlertService.alertMessage( "Please select a company!", 'Warning');
					}
					
				}
			} ]

		});
		return deferred.promise;
	},

	service.getCompanyDetails = function(existingPrecontactCompanyMap) {
		var deferred = $q.defer();
		var req = {
			"status" : 1
		};
		var companyDetailsPromise = CompanyService.getCompaniesWithDefaultAddressAndContact(req);
		companyDetailsPromise.then(function(data) {
			var companyDetailsPopUp = service.companyDetailsPopUp(data.companyTOs,
					data.contactsMap, data.addressMap, existingPrecontactCompanyMap);
			companyDetailsPopUp.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage(
						"Error occured while selecting Purchase Order Item Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage(
					"Error occured while getting Purchase Order Item Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]);