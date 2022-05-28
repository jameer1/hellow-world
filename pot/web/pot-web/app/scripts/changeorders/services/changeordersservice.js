'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjSOW Service  in the potApp.
 */
app.factory('ChangeOrdersService', ["Restangular", "$q", "$http", "appUrl", function(Restangular, $q, $http, appUrl) {
	return {		
		getCoSowDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/getSOWItemsByProjId",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		saveOrderDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/saveChangeOrderDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		saveChangeOrderManpowerDetails : function(req) {
			var deferred = $q.defer();
			$http({                                           
					url : appUrl.originurl + "/app/projectlib/saveCoManpowerDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		saveChangeOrderPlantDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/saveCoPlantDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		
		// pass ayyi frent end lo data kuda vastundi kaani req lo em em accept chestundio teliyatledu
		fetchChangeOrderDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/getChangeOrderDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		fetchCoManpowerDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/getCoManpowerDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		updateApproverDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/updateCoApproverDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		//-------------------------
	/*	getCoMainDetails : function(req) {alert("4")

			var contractDetails = Restangular.one("procurement/getPOByPreContranctType")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return contractDetails;
		}*/
		
		
		//------Get po Id ---------
		getCoMainDetails : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/procurement/getPOByPreContranctType",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		
		//--------Save Sow----------
			saveCoScopeOfWorks : function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/saveCoScopeOfWork",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		//-----Save Material----------- 
		getCoMeterial: function(req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/projectlib/saveCoMaterialDetails",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		}
		
		
	}
}]);
