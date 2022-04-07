'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjSOW Service  in the potApp.
 */
app.factory('ProjSOWService', ["Restangular", "$q", "$http", "appUrl", function(Restangular, $q, $http, appUrl) {
	return {
		getProjSOWDetails : function(req) {
			var sowDetails = Restangular
					.one("projectlib/getSOWItems").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return sowDetails;
		},
		getSowDetailsByCostCode : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/getSOWItemsByCostCodes",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getSowDetailsExceptCostCode : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/getSOWItemsExceptCostCodes",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getProjSOWItemsById : function(req) {
			var sowDetails = Restangular
					.one("projectlib/getProjSOWItemsById").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return sowDetails;
		},
		saveSOWItems : function(req) {
			var sowSaveStatus = Restangular
					.one("projectlib/saveSOWItems").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return sowSaveStatus;
		},
		deleteSOWItems : function(req) {
			var sowDeactivateStatus = Restangular
					.one("projectlib/deleteSOWItems").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return sowDeactivateStatus;
		},getMeasurements : function(req) {
			var measure = Restangular.one("centrallib/getMeasurements").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return measure;
		},
		getMultiProjSOWDetails : function(req) {
			var sowDetails = Restangular
					.one("projectlib/getMultiProjSOWDetails").customPOST(req,
							'', {}, {
								ContentType : 'application/json'
							});
			return sowDetails;
		},
		
		getSOWActualRevisedQuantities : function(req) {
			var actualRevisedQuantities = Restangular.one("projectlib/getSOWTotalActualQuantities").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return actualRevisedQuantities;
		}

	}
}]);
