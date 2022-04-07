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
		saveChangeOrderDetails : function(req) {
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
		}
	}
}]);
