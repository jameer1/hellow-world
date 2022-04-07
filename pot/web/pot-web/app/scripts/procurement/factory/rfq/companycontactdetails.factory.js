'use strict';
app.factory('CompanyContactDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "CompanyService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, CompanyService) {
	var companyDetailsPopUp;
	var service = {};
	service.getCmpContacts = function(req) {
		var deferred = $q.defer();
		var contactTOs = [];
		var companyDetailsPromise = CompanyService.getCompanyDetails(req);
		companyDetailsPromise.then(function(data) {
			angular.forEach(data.companyTOs, function(value) {
				contactTOs = value.contacts;
			});
			var companyDetailsPopUp = service.openPopup(contactTOs);
			companyDetailsPopUp.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting company contact details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting company contact details", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function(contactTOs) {
		var deferred = $q.defer();
		companyDetailsPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/companycontact.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.contactTOs = contactTOs;
				$scope.selectedContact = function(data) {
					deferred.resolve(data);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
