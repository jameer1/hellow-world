'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # StoreStock Class Service in the potApp.
 */
app.factory('ProjStoreStockService', ["Restangular", function(Restangular) {
	return {
		getUserProjects : function(req) {
			var projectEps = Restangular.one("projectlib/getUserProjects")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return projectEps;
		},

		getProjStoreStocks : function(req) {
			var result = Restangular.one("projectlib/getProjStoreStocks")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return result;
		},
		projStoreStockifOnLoad : function(req) {
			var response = Restangular.one("projectlib/projStoreStockifOnLoad")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return response;
		},
		saveProjStoreStocks : function(req) {
			var saveStatus = Restangular.one("projectlib/saveProjStoreStocks")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		deleteProjStoreStocks : function(req) {
			var deleteStatus = Restangular.one(
					"projectlib/deleteProjStoreStocks").customPOST(req, '', {},
					{
						ContentType : 'application/json'
					});
			return deleteStatus;
		},
		getProjStockPileMap : function(req) {
			var map = Restangular.one("projectlib/getProjStockPileMap")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return map;
		},
		getMultipleProjsStoreList : function(req) {
			var multistore = Restangular.one("projectlib/getMultipleProjsStoreList")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return multistore;
		}

	}
}]);
