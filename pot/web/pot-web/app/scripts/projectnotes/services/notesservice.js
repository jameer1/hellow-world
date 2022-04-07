'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ProjectNotesService', ["Restangular", "$http", function(Restangular, $http) {
	return {
		getEPSDetails : function(req) {
			var epsDetails = Restangular.one("projectlib/projEPSOnLoad").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return epsDetails;
		},
		
		// Note Book
		getProjNoteBook : function(req) {
			var getNoteBookReq = Restangular.one('projsettings/getProjNoteBook').customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return getNoteBookReq;
		},
		saveProjNoteBook : function(req) {
			var saveNoteBookReq = Restangular.one('projsettings/saveProjNoteBook').customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveNoteBookReq;
		},
		// Project Reports
	
	}
}]);
