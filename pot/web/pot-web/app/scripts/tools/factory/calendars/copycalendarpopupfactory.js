'use strict';
app.factory('CopyCalendarPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CalendarService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		CalendarService, GenericAlertService) {
	var service = {};
	service.getCalendars = function(calType, projId) {
		var deferred = $q.defer();
		var projSelection = null;
		if (calType == 'GCAL') {
			projSelection = null;
		} else {
			projSelection = projId;
		}
		var req = {
			"status" : 1,
			"calType" : calType,
			"projId" : projSelection
		};
		CalendarService.getCalendarByClientId(req).then(function(data) {
			var popupData = service.openPopup(data.calenderTOs);
			popupData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage('Error ocurred while getting calernders', 'Error');
		});
		return deferred.promise;

	}, service.openPopup = function(calendarList) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/tools/calendars/copycalendarpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedCalendar = [];
				$scope.calendarList = calendarList;
				$scope.selectCalendar = function(calendar) {
					deferred.resolve(calendar);
					$scope.closeThisDialog();
				}

			} ]

		});
		return deferred.promise;
	}
	return service;
}]);