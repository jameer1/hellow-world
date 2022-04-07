'use strict';

app.factory('CalendarService', ["Restangular", "$http", function(Restangular, $http) {
	return {
		getCalendars : function(req) {
			var calendar = Restangular.one('calendar/getCalendars').customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		getCalendarById : function(req) {
			var calendar = Restangular.one('calendar/getCalendarById').customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		saveCalendars : function(req) {
			var calendar = Restangular.one("calendar/saveCalendars").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		getCalDays : function(req) {
			var calendar = Restangular.one("calendar/getCalDays").customPOST(req, '', {}, {   
				ContentType : 'application/json'
			});
			return calendar;
		},
		saveCalRegularDays : function(req) {
			var calendar = Restangular.one("calendar/saveCalRegularDays").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		saveCalSpecialDays : function(req) {
			var calendar = Restangular.one("calendar/saveCalSpecialDays").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		getCalendarByClientId : function(req) {
			var calendar = Restangular.one("calendar/getCalendarByClientId").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		copyCalendar : function(req) {
			var calendar = Restangular.one("calendar/copyCalendar").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},
		deleteCalendar : function(req) {
			var calendar = Restangular.one("calendar/deleteCalendars").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},

		getProjCalendarMap : function(req) {
			var calendar = Restangular.one("calendar/getProjCalendarMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		},

		getGlobalCalendarMap : function(req) {
			var calendar = Restangular.one("calendar/getGlobalCalendarMap").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return calendar;
		}

	}
}]);