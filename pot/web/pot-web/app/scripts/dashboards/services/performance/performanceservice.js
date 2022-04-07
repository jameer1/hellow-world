'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */

app.factory('PerformanceDashboardService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {
	
	function sendProjectBudgetsRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/projsettings/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => {return data.data });
	}
	
	return {
		getPerformanceIndex: function (req) {
			return sendProjectBudgetsRequest(req, 'getProjManpowers');
		},
		getProjCostStatements: function (req) {
			return sendProjectBudgetsRequest(req, 'getProjCostStatements');
		},
		getPerformanceList : function(req) {
			var deferred = $q.defer();
			var performanceList = {
				"performanceList" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"projStatus" : 'Started',
					"progress" : '778.07',
					"scheduleVariance" : '45',
					"costVariance" : '80',
			        "scheduleIndex" : '40',
					"costIndex":'9.57',
					"toCompleteIndex":'36.25'
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"projStatus" : 'Not Started',
					"progress" : '450',
					"scheduleVariance" : '70',
					"costVariance" : '60',
					"scheduleIndex" : '60',
					"costIndex":'40',
					"toCompleteIndex":'52'
				} ]
			};
			deferred.resolve(performanceList);
			return deferred.promise;
		},
		getCostScheduleUnitsList : function(req) {
			var deferred = $q.defer();
			var performanceList = {
				"performanceList" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"labourHrs" : '29.64',
					"cost" : '69.00',
					"varianceLabourHrs" : '78.66',
					"varianceCost" : '58.50',
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"labourHrs" : '56',
					"cost" : '46',
					"varianceLabourHrs" : '45',
					"varianceCost" : '30',
				} ]
			};
			deferred.resolve(performanceList);
			return deferred.promise;
		},
		getCostScheduleList : function(req) {
			var deferred = $q.defer();
			var performanceList = {
				"performanceList" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"actualStart" : '',
					"actualFinish" : '',
					"baseLineStart" : '',
					"baseLineFinish" : '',
					"progress" : '778.07',
					"originalCost" : '112200.00',
					"revisedBudget" : '66098.64',
					"estimateCompletionCost" : '0.00',
					"actualCost" : '-669586.50',
					"plannedCost" : '91213.50',
					"earnedCost" : '0.00',
					"costVariance" : '0.00',
					"scheduleVariance" : '0.00',
					"spi" : '0.00',
					"cpi" : '0.00',
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"actualStart" : '',
					"actualFinish" : '',
					"baseLineStart" : '',
					"baseLineFinish" : '',
					"progress" : '--',
					"originalCost" : '--',
					"revisedBudget" : '--',
					"estimateCompletionCost" : '--',
					"actualCost" : '--',
					"plannedCost" : '--',
					"earnedCost" : '--',
					"costVariance" : '--',
					"scheduleVariance" : '--',
					"spi" : '--',
					"cpi" : '--',
				} ]
			};
			deferred.resolve(performanceList);
			return deferred.promise;
		}
		,
		getVariancePercentageList : function(req) {
			var deferred = $q.defer();
			var performanceList = {
				"performanceList" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"labourHrs" : '338.10',
					"cost" : '347',
					"varianceLabourHrs" : '99.30',
					"varianceCost" : '89.55',
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"labourHrs" : '300',
					"cost" : '500',
					"varianceLabourHrs" : '40',
					"varianceCost" : '66',
				} ]
			};
			deferred.resolve(performanceList);
			return deferred.promise;
		},
		getindicesList : function(req) {
			var deferred = $q.defer();
			var performanceList = {
				"performanceList" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"labourHrs" : '4.38',
					"cost" : '575',
					"indexLabourHrs" : '142.99',
					"indexCost" : '9.57',
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"labourHrs" : '8.57',
					"cost" : '695',
					"indexLabourHrs" : '130',
					"indexCost" : '7.45',
				} ]
			};
			deferred.resolve(performanceList);
			return deferred.promise;
		},
		getToCompleteList : function(req) {
			var deferred = $q.defer();
			var performanceList = {
				"performanceList" : [ {
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 2',
					"labourHrs" : '3.49',
					"cost" : '36.25',
					
				},{
					"epsName" : 'Metro Rail Test',
					"projectName" : 'Surface Works - zone 1',
					"labourHrs" : '--',
					"cost" : '--',
					
				} ]
			};
			deferred.resolve(performanceList);
			return deferred.promise;
		}
	
	}
}]);
