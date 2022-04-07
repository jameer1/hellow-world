'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjSOE Service in the potApp.
 */
app.factory('ProjSOEService', ["Restangular","$http", "$q", "appUrl", function(Restangular, $http, $q, appUrl) {
	return {
		getSOEDetails : function(req) {
			var soeDetails = Restangular.one(
					"projectlib/getSOEItems").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return soeDetails;
		},
		
		getProjSOETrackDetails : function(req) {
			var soeTrackDetails = Restangular.one(
					"projectlib/getProjSOETrackDetails").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return soeTrackDetails;
		},
		
		
		/*
		 * getProjSOEItemsById : function(req) { var soeDetails =
		 * Restangular.one(
		 * "projectlib/getProjSOEItemsById").customPOST( req, '',
		 * {}, { ContentType : 'application/json' }); return soeDetails; }
		 */
		saveSOEDetails : function(req) {
			var soeSaveStatus = Restangular.one(
					"projectlib/saveSOEItems").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return soeSaveStatus;
		},
		
		saveSOETrackDetails : function(req) {
			var soeSaveTrack = Restangular.one(
					"projectlib/saveSOETrackDetails").customPOST(req, '',
					{}, {
						ContentType : 'application/json'
					});
			return soeSaveTrack;
		},
		
		deactivateSOEDetails : function(req) {
			var soeDeactivateStatus = Restangular.one(
					"projectlib/deleteSOEItems").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return soeDeactivateStatus;
		},
		projSoeifOnLoad : function(req) {
			var measure = Restangular.one(
					"projectlib/projSoeifOnLoad").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return measure;
		},
		projSoeApproval : function(req) {
			var soeapproval = Restangular.one(
					"projectlib/approveProjSoe").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return soeapproval;
		},
		returnWithComments : function(req) {
			var soereturn = Restangular.one(
					"projectlib/returnWithComments").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return soereturn;
		},
		viewActivityRecords : function(req) {
			var soeactivity = Restangular.one(
					"projectlib/viewActivityRecords").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return soeactivity;
		},
		viewTrackSoeRecords : function(req) {
			var soetrackrecords = Restangular.one(
					"projectlib/viewTrackSoeRecords").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return soetrackrecords;
		},
		saveSoeNotifications : function (req) {
		console.log(req);
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/app/notification/saveSoeNotifications",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;			
		},
	}
}]);
