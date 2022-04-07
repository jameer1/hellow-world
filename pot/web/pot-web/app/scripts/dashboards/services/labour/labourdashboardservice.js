

// TODO : remove this service

app.factory('LabourDashboardService', ["$q", "Restangular", function ($q, Restangular) {
	return {

		getBubbleChartList: function (req) {
			var deferred = $q.defer();
			var timeDetails =
				[{
					"epsName": 'Metro Rail Test',
					"projectName": 'Surface Works - zone1',
					"costVariance": 121,
					"scheduleVariance": 144,
				}, {
					"epsName": 'Metro Rail Test',
					"projectName": 'Surface Works - zone2',
					"costVariance": 500,
					"scheduleVariance": 20,
				}, {
					"epsName": 'Metro Rail Test',
					"projectName": 'Surface Works - zone3',
					"costVariance": 90,
					"scheduleVariance": 100,
				}, {
					"epsName": 'Metro Rail Test',
					"projectName": 'Surface Works - zone4',
					"costVariance": 4,
					"scheduleVariance": 3,
				}]

			deferred.resolve(timeDetails);
			return deferred.promise;
		},
	}
}]);
