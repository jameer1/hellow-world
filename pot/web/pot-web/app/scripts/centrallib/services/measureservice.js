'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Measure Service in the potApp.
 */
app.factory('MeasureService', ["Restangular", function(Restangular) {
	return {
		getMeasurements : function(req) {
			var measure = Restangular.one("centrallib/getMeasurements").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return measure;
		},	getMeasuresByProcureType : function(req) {
			var measure = Restangular.one("centrallib/getMeasuresByProcureType").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return measure;
		},
		getMeasurementMap : function(req) {
			var measure = Restangular.one("centrallib/getMeasurementMap").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return measure;
		},
		getprocures : function(req) {
			var procure = Restangular.one("centrallib/getprocures").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return procure;
		},
		saveMeasurements : function(req) {
			var resultStatus = Restangular.one("centrallib/saveMeasurements").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return resultStatus;
		},
		deleteMeasurements : function(req) {
			var deleteStatus = Restangular.one("centrallib/deleteMeasurements").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return deleteStatus;
		}

	}

}]);
