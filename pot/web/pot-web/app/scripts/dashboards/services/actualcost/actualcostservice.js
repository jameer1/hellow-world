app.factory('ActualCostService', ["$q", "Restangular", function($q,Restangular) {

	return {
		getActualCostByCountryDashboard : function(req) {
			/*var actualcost = Restangular.one("dashboards/getActualCostByCountry").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return actualcost;*/
			var deferred = $q.defer();
			var actualCostCountryDetails = {
				"actualCostCountryDetails" : [ {
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
			deferred.resolve(actualCostCountryDetails);
			return deferred.promise;
		},
	getActualCostByProvinceDetailsDashboard : function(req) {
		/*var actualcost = Restangular.one("dashboards/getActualCostByProvince").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return actualcost;*/
		var deferred = $q.defer();
		var actualCostProvinceDetails = {
			"actualCostProvinceDetails" : [ {
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
		deferred.resolve(actualCostProvinceDetails);
		return deferred.promise;
	},
	getActualCostByProjectDetailsDashboard : function(req) {
		/*var actualcost = Restangular.one("dashboards/getActualCostByProject").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return actualcost;*/
		var deferred = $q.defer();
		var actualCostProjectDetails = {
			"actualCostProjectDetails" : [ {
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
		deferred.resolve(actualCostProjectDetails);
		return deferred.promise;
	},
	getActualCostByProjectManagerDetailsDashboard : function(req) {
		/*var actualcost = Restangular.one("dashboards/getActualCostByProjectManager").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return actualcost;*/
		var deferred = $q.defer();
		var actualCostProjectManagerDetails = {
			"actualCostProjectManagerDetails" : [ {
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
		deferred.resolve(actualCostProjectManagerDetails);
		return deferred.promise;
	}
	
	}
	
	
}]);