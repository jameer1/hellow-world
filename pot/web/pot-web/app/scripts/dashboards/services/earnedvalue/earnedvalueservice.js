app.factory('EarnedValueService', ["Restangular", "$q", function(Restangular,$q) {

	return {
		getEarnedValueByCountryDashboard : function(req) {
			/*var earnedvalue = Restangular.one("dashboards/getEarnedValueByCountry").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return earnedvalue;*/
			var deferred = $q.defer();
			var earnedValueCountryDetails = {
				"earnedValueCountryDetails" : [ {
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
			deferred.resolve(earnedValueCountryDetails);
			return deferred.promise;
		},
	getEarnedValueByProvinceDetailsDashboard : function(req) {
		/*var earnedvalue = Restangular.one("dashboards/getEarnedValueByProvince").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return earnedvalue;*/
		var deferred = $q.defer();
		var earnedValueProvinceDetails = {
			"earnedValueProvinceDetails" : [ {
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
		deferred.resolve(earnedValueProvinceDetails);
		return deferred.promise;
	},
	getEarnedValueByProjectDetailsDashboard : function(req) {
		/*var earnedvalue = Restangular.one("dashboards/getEarnedValueByProject").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return earnedvalue;*/
		var deferred = $q.defer();
		var earnedValueProjectDetails = {
			"earnedValueProjectDetails" : [ {
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
		deferred.resolve(earnedValueProjectDetails);
		return deferred.promise;
	},
	getEarnedValueByProjectManagerDetailsDashboard : function(req) {
		/*var earnedvalue = Restangular.one("dashboards/getEarnedValueByProjectManager").customPOST(req, '', {}, {
			ContentType : 'application/json'
		});
		return earnedvalue;*/
		var deferred = $q.defer();
		var earnedValueProjectManagerDetails = {
			"earnedValueProjectManagerDetails" : [ {
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
		deferred.resolve(earnedValueProjectManagerDetails);
		return deferred.promise;
	}
	
	}
	
	
}]);