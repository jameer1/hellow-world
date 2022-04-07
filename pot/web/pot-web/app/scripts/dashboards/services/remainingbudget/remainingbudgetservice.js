app.factory('RemainingBudgetService', ["Restangular", "$q", function(Restangular,$q) {

	return {
		getRemainingBudgetByCountryDashboard : function(req) {
			/*var remainingbudget = Restangular.one("dashboards/getRemainingBudgetByCountry").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return remainingbudget;*/
			var deferred = $q.defer();
			var remainingBudgetCountryDetails = {
				"remainingBudgetCountryDetails" : [ {
					"id":1,
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"actualCost":'100',
					"country":'India'
				},{"id":2,
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"actualCost":'200',
					"country":'America'
				},
				{"id":3,
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 3',
					"actualCost":'300',
					"country":'Afghanisthan'
				},
				{"id":4,
					"epsName" : 'Metro ',
					"projectName" : 'Surface Works - zone 4',
					"actualCost":'400',
					"country":'Afghanin'
				},
				{"id":5,
					"epsName" : 'Metro ',
					"projectName" : 'Surface Works - zone 4',
					"actualCost":'400',
					"country":'Afghanin'
				}]
			};
			deferred.resolve(remainingBudgetCountryDetails);
			return deferred.promise;
		},
	getRemainingBudgetByProvinceDetailsDashboard : function(req) {
		/*var remainingbudget = Restangular.one("dashboards/getRemainingBudgetByProvince").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return remainingbudget;*/
		var deferred = $q.defer();
		var remainingBudgetProvinceDetails = {
			"remainingBudgetProvinceDetails" : [ {
				"id":1,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 1',
				"actualCost":'100',
				"province":'prov1'
			},{"id":2,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 2',
				"actualCost":'200',
				"province":'prov2'
			},
			{"id":3,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 3',
				"actualCost":'300',
				"province":'prov3'
			},
			{"id":4,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"province":'prov4'
			},
			{"id":5,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 5',
				"actualCost":'400',
				"province":'prov5'
			}]
		};
		deferred.resolve(remainingBudgetProvinceDetails);
		return deferred.promise;
	},
	getRemainingBudgetByProjectDetailsDashboard : function(req) {
		/*var remainingbudget = Restangular.one("dashboards/getRemainingBudgetByProject").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return remainingbudget;*/
		var deferred = $q.defer();
		var remainingBudgetProjectDetails = {
			"remainingBudgetProjectDetails" : [ {
				"id":1,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 1',
				"actualCost":'100',
				"country":'India'
			},{"id":2,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 2',
				"actualCost":'200',
				"country":'America'
			},
			{"id":3,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 3',
				"actualCost":'300',
				"country":'Afghanisthan'
			},
			{"id":4,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"country":'Afghanin'
			},
			{"id":5,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 5',
				"actualCost":'400',
				"country":'Afghanin'
			}]
		};
		deferred.resolve(remainingBudgetProjectDetails);
		return deferred.promise;
	},
	getRemainingBudgetByProjectManagerDetailsDashboard : function(req) {
		/*var remainingbudget = Restangular.one("dashboards/getRemainingBudgetByProjectManager").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return remainingbudget;*/
		var deferred = $q.defer();
		var remainingBudgetProjectManagerDetails = {
			"remainingBudgetProjectManagerDetails" : [ {
				"id":1,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 2',
				"actualCost":'100',
				"projManager":'A'
			},{"id":2,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 1',
				"actualCost":'200',
				"projManager":'B'
			},
			{"id":3,
				"epsName" : 'Metro Rail Test',
				"projectName" : 'Surface Works - zone 3',
				"actualCost":'300',
				"projManager":'C'
			},
			{"id":4,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"projManager":'D'
			},
			{"id":5,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"projManager":'E'
			}]
		};
		deferred.resolve(remainingBudgetProjectManagerDetails);
		return deferred.promise;
	}
	
	}
	
	
}]);