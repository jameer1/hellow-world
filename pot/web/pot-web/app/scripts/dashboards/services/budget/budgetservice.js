app.factory('BudgetDashboardService', ["$q", "Restangular", function($q, Restangular) {

	return {
		getBudgetByCountryDashboard : function(req) {
		
			var deferred = $q.defer();
			var details = {
				"actualCostCountryDetails" : [ {
					"id" : 1,
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"originalBudget" : '100',
					"country" : 'India'
				}, {
					"id" : 2,
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"originalBudget" : '200',
					"country" : 'America'
				}, {
					"id" : 3,
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 3',
					"originalBudget" : '300',
					"country" : 'Afghanisthan'
				}, {
					"id" : 4,
					"epsName" : 'Metro Train',
					"projectName" : 'Surface Works - zone 4',
					"originalBudget" : '400',
					"country" : 'Afghanin'
				}, {
					"id" : 5,
					"epsName" : 'Metro Train ',
					"projectName" : 'Surface Works - zone 5',
					"originalBudget" : '400',
					"country" : 'Afghanin'
				} ]
			};
			deferred.resolve(details);
			return deferred.promise;
		},
		getActualCostByProvinceDetailsDashboard : function(req) {
			var actualcost = Restangular.one("dashboards/getActualCostByProvince").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return actualcost;
		},
		getBudgetByProjectDetailsDashboard : function(req) {
		
			var deferred = $q.defer();
			var details = [ {
				"id" : 1,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 1',
				"originalBudget" : '100',
				"country" : 'India'
			}, {
				"id" : 2,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 2',
				"originalBudget" : '200',
				"country" : 'America'
			}, {
				"id" : 3,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 3',
				"originalBudget" : '300',
				"country" : 'Afghanisthan'
			}, {
				"id" : 4,
				"epsName" : 'Metro Train ',
				"projectName" : 'Surface Works - zone 4',
				"originalBudget" : '400',
				"country" : 'Afghanin'
			}, {
				"id" : 5,
				"epsName" : 'Metro Train',
				"projectName" : 'Surface Works - zone 5',
				"originalBudget" : '400',
				"country" : 'Afghanin'
			} ]

			deferred.resolve(details);
			return deferred.promise;
		},
		

	}

}]);