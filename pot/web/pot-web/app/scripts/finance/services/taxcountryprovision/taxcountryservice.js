'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('TaxCodeCountryService', ["Restangular", "$http", function(Restangular, $http) {
	return {
		getTaxCodesByCountry : function(req) {
			var taxCode = Restangular.one('finance/getTaxCodesByCountry')
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return taxCode;
		},
		getTaxCountryProvision : function(req) {
			var taxStatus = Restangular.one("finance/getTaxCountryProvision")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return taxStatus;
		},
		getTaxCodeCountryProvisionsOnload : function(req) {
			var taxStatus = Restangular.one(
					"finance/getTaxCodeCountryProvisionsOnload").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return taxStatus;
		},
		getTaxCodeCountryProvisions : function(req) {
			var taxStatus = Restangular.one(
					"finance/getTaxCodeCountryProvisions").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return taxStatus;
			
		},
		saveTaxCountryProvision : function(req) {
			var taxStatus = Restangular.one("finance/saveTaxCountryProvision")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return taxStatus;
			
		},
		saveTaxCodeCountryProvisions : function(req) {
			var taxStatus = Restangular.one(
					"finance/saveTaxCodeCountryProvisions").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return taxStatus;
		},
		deactivateTaxCodeCountryProvisions : function(req) {
			var taxCodeStatus = Restangular.one(
					"finance/deactivateTaxCodeCountryProvisions").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return taxCodeStatus;
		},getTaxCodeCountryProvisions : function(req) {
			var taxStatus = Restangular.one(
					"finance/getTaxCodeCountryProvisions").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return taxStatus;
		},
		getTaxCountryProvisionMap : function(req) {
			var taxMap = Restangular.one(
					"finance/getTaxCountryProvisionMap").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return taxMap;
		},
	}
}]);
