'use strict';

app.factory('TaxTypeService', ["Restangular", function(Restangular) {
	
	return {
		getCodeTypes : function(req) {
			var getStatus = Restangular.one("finance/getCodeTypes").customPOST(req,
				'', {}, {
					ContentType : 'application/json'
				});
		return getStatus;
	},
	saveCodeTypes : function(req) {
		var saveStatus = Restangular.one("finance/saveCodeTypes").customPOST(
				req, '', {}, {
					ContentType : 'application/json'
				});
		return saveStatus;
	},
	deleteTaxCodeTypes : function(req) {
		var taxCodeTypes = Restangular.one("finance/deactivateCodeTypes").customPOST(
				req, '', {}, {
					ContentType : 'application/json'
				});
		return taxCodeTypes;
	},
	}

}]);
