app.factory('EstimateToCompletionService', ["$q", "Restangular", function($q,Restangular) {

	return {
		getEstimateToCompleteByCountryDashboard : function(req) {
			var deferred = $q.defer();
			var estimateToCompleteCountryDetails = {
				"estimateToCompleteCountryDetails" : [ {
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
			deferred.resolve(estimateToCompleteCountryDetails);
			return deferred.promise;
		},
	getEstimateToCompleteProvinceDetails: function(req) {
		var deferred = $q.defer();
		var estimateToCompleteProvinceDetails = {
			"estimateToCompleteProvinceDetails" : [ {
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
		deferred.resolve(estimateToCompleteProvinceDetails);
		return deferred.promise;
	},
	getEstimateToCompleteProjectDetails: function(req) {
		var deferred = $q.defer();
		var estimateToCompleteProjectDetails = {
			"estimateToCompleteProjectDetails" : [ {
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
		deferred.resolve(estimateToCompleteProjectDetails);
		return deferred.promise;
	},
	getEstimateToCompleteByProjManager: function(req) {
		var deferred = $q.defer();
		var estimateToCompleteProjManagerDetails = {
			"estimateToCompleteProjManagerDetails" : [ {
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
		deferred.resolve(estimateToCompleteProjManagerDetails);
		return deferred.promise;
	}
	
	}
	
	
}]);