app.factory('TimeDashboardService', ["$q", "Restangular", function($q, Restangular) {
	return {
		
		getCostPercentageList : function(req) {
			var deferred = $q.defer();
			var timeDetails = {
				"timeDetails" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"startDate" : '01-feb-2017',
					"finishDate" : '28-feb-2017',
					"perceComplete" : '778.07',
					"projStatus" : 'Complete',
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"startDate" : '--',
					"finishDate" : '--',
					"perceComplete" : '--',
					"projStatus" : 'NotStarted',
				} ]
			};
			deferred.resolve(timeDetails);
			return deferred.promise;
		},
		getCostScheduleUnitsList : function(req) {
			var deferred = $q.defer();
			var timeDetails = {
				"timeDetails" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"labourHrs" : '66098.64',
					"cost" : '873000.00',
					"varianceLabourHrs" : '85049.66',
					"varianceCost" : '781786.50',
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"labourHrs" : '--',
					"cost" : '--',
					"varianceLabourHrs" : '--',
					"varianceCost" : '--',
				} ]
			};
			deferred.resolve(timeDetails);
			return deferred.promise;
		},
		getBubbleChartList : function(req) {
			var deferred = $q.defer();
			var timeDetails = 
				[ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone1',
					"costVariance" : 121,
					"scheduleVariance" : 144,
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone2',
					"costVariance" : 500,
					"scheduleVariance" : 20,
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone3',
					"costVariance" : 90,
					"scheduleVariance" : 100,
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone4',
					"costVariance" :4,
					"scheduleVariance" : 3,
				} ]
			
			deferred.resolve(timeDetails);
			return deferred.promise;
		},
	}
}]);
