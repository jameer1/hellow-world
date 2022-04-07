'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjCostCode Service in the potApp.
 */
app.factory('MaterialReportService', ["Restangular", "$q", "$http", "appUrl", 
	function(Restangular, $q, $http, appUrl) {
	return {
		getDailyIssueRecordsReport : function(req) {
			var daily = Restangular.one("reports/getDailyIssueRecordsReport").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return daily;
		},
		getStoreStockRecordsReport : function(req) {
			var store = Restangular.one("reports/getStoreStockRecordsReport").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return store;
		},
		getStockPiledRecordsReport : function(req) {
			var piled = Restangular.one("reports/getStockPiledRecordsReport").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return piled;
		},
		getPeriodicalConsumptionRecordsReport : function(req) {
			var periodical = Restangular.one("reports/getMaterialPeriodicalConsuReport").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return periodical;
		},
		getDateWiseConsumptionRecordsReport : function(req) {
			var date = Restangular.one("reports/getMaterialDateWiseConsuReport").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return date;
		},
		getInventoryRecordsReport : function(req) {
			var inventory = Restangular.one("reports/getMaterialInventoryReport").customPOST(req,'', {}, {
								ContentType : 'application/json'
							});
			return inventory;
		}
	}
}]);
