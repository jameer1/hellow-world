'use strict';

app.factory('PersonalTaxService', ["Restangular", "$http", function(Restangular, $http) {
	return {
		getPersonalTaxRates : function(req) {
			var personalTax = Restangular.one('finance/getPersonalTaxRates')
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return personalTax;
		},
		
		savePersonalTaxRates : function(req) {
			var personalTaxStatus = Restangular.one("finance/savePersonalTaxRates").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return personalTaxStatus;
		},
		deletePersonalTaxRates: function(req) {
			var deletePersonalTax = Restangular.one("finance/deletePersonalTaxRates").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return deletePersonalTax;
		}
	}
}]);
