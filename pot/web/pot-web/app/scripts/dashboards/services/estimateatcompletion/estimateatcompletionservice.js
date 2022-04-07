app.factory('EstimateAtCompletionService', ["$q", "Restangular", function($q,Restangular) {

	return {
		getEstimateAtCompletionByCountryDashboard : function(req) {
			var deferred = $q.defer();
			var estimateAtCompletionCountryDetails = {
				"estimateAtCompletionCountryDetails" : [ {
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
					"country":'Australia'
				},
				{"id":5,
					"epsName" : 'Metro ',
					"projectName" : 'Surface Works - zone 4',
					"actualCost":'400',
					"country":'Chaina'
				}]
			};
			deferred.resolve(estimateAtCompletionCountryDetails);
			return deferred.promise;
		},
	getEstimateAtCompletionProvinceDetails: function(req) {
		var deferred = $q.defer();
		var estimateAtCompletionProvinceDetails = {
			"estimateAtCompletionProvinceDetails" : [ {
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
				"country":'Australia'
			},
			{"id":5,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"country":'Chaina'
			}]
		};
		deferred.resolve(estimateAtCompletionProvinceDetails);
		return deferred.promise;
	},
	getEstimateAtCompletionProjectDetails: function(req) {
		var deferred = $q.defer();
		var estimateAtCompletionProjectDetails = {
			"estimateAtCompletionProjectDetails" : [ {
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
				"country":'Australia'
			},
			{"id":5,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"country":'Chaina'
			}]
		};
		deferred.resolve(estimateAtCompletionProjectDetails);
		return deferred.promise;
	},
	getEstimateAtCompletionByProjManager: function(req) {
		var deferred = $q.defer();
		var estimateAtCompletionProjManagerDetails = {
			"estimateAtCompletionProjManagerDetails" : [ {
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
				"country":'Australia'
			},
			{"id":5,
				"epsName" : 'Metro ',
				"projectName" : 'Surface Works - zone 4',
				"actualCost":'400',
				"country":'Chaina'
			}]
		};
		deferred.resolve(estimateAtCompletionProjManagerDetails);
		return deferred.promise;
	}
	
	}
	
	
}]);