'use strict';

app.factory('ProfitCentreService', ["Restangular", function(Restangular) {

	return {
		getProfitCentres : function(req) {
			var service = Restangular.one("finance/getProfitCentres").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return service;
		},
		saveProfitCentres : function(req) {
			var service = Restangular.one("finance/saveProfitCentres").customPOST(req, '', {},
					{
						ContentType : 'application/json'
					});
			return service;
		},
		deleteProfitCentres : function(req) {
			var resultStatus = Restangular.one("finance/deleteProfitCentres").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		}

	}

}]);