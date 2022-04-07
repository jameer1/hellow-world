'use strict';

app.factory('GeneralValuesCalendarFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjectSettingsService, GenericAlertService) {
	var calendarsPopup = [];
	var calendarService = {};
	calendarService.getCalendars = function(projId) {
		var deferred = $q.defer();
		calendarsPopup = ngDialog.open({
			template : 'views/projectsettings/generalvaluesdefaultcalendartabs.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.CalendarTabs = [ {
					title : 'Global Calendar ',
					url : 'views/projectsettings/generaldefaultcalendar.html',
					SelenumLocator:'Projects_ProjectSettings_GeneralValuesTab_DefaultCalendar_Global_Calendar'
				}, {
					title : 'Project Calendar',
					url : 'views/projectsettings/generalprojcalendar.html',
					SelenumLocator:'Projects_ProjectSettings_GeneralValuesTab_DefaultCalendar_Project_Calendar'
				} ];
				$scope.calendarCurrentTab = 'views/projectsettings/generaldefaultcalendar.html';
				$scope.calendarOnClickTab = function(tab) {
					$scope.calendarCurrentTab = tab.url;
				}
				$scope.isCalendarActiveTab = function(tabUrl) {
					return tabUrl == $scope.calendarCurrentTab;
				}, $scope.globalCalDetails = [];
				$scope.projCalDetails = [];
				$scope.getGlobalCalenders = function() {
					var calReq = {
						"status" : 1,
					};

					ProjectSettingsService.getCalendars(calReq).then(function(data) {
						$scope.globalCalDetails = data.calenderTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Global Calendar Details", 'Error');
					});
				}, $scope.getProjCalendars = function() {
					var storeYardReq = {
						"status" : 1,
						"projId" : projId
					};
					ProjectSettingsService.getCalendars(storeYardReq).then(function(data) {
						$scope.projCalDetails = data.calenderTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project  Calendar Details", 'Error');
					});
				}, $scope.deliveryPlacePopup = function(calData, type) {

					var projStoreReturnObj = {
						"CalendarTO" : calData,
						"type" : type
					};
					deferred.resolve(projStoreReturnObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};
	return calendarService;

}]);
