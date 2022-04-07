'use strict';

app.factory('DateConvertFactory', ["$q", "GenericAlertService", function($q, GenericAlertService) {
	var service = {};
	service.getMonthDateFormat = function(month) {
		var deferred = $q.defer();
		var date = new Date(month);
		var monthName = service.getMonthNames(date);
		var returnPopObj = {
			"dateFormat" : monthName + "-" + date.getFullYear()
		};
		deferred.resolve(returnPopObj);
		return deferred.promise;

	}, service.getMonthNames = function(dt) {
		var deferred = $q.defer();
		var monthList = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
		deferred.resolve(monthList[dt.getMonth()]);
		return deferred.promise;

	}
	return service;

}]);