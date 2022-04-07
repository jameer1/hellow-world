'use strict';
app.factory('CalendarDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CalendarService", "GenericAlertService", "$window", function(ngDialog, $q, $filter, $timeout, $rootScope, CalendarService, GenericAlertService, $window) {

	var service = {};
	service.getCalDays = function(editCalendar, projId, sysDate) {
		var deferred = $q.defer();

		var currentDate = new Date(sysDate)
//		currentDate.setYear(currentDate.getFullYear() - 5)
		var req = {
			"calendarId" : editCalendar[0].id,
			"status" : 1,
			"projId" : projId,
			"month" : (currentDate.getMonth() + 1) + "-" + currentDate.getFullYear()
		};
		CalendarService.getCalDays(req).then(function(data) {
			var poppData = service.getDetails(editCalendar, projId, sysDate, data.calenderRegularDaysTO, data.calenderDaysMap);
			poppData.then(function(resultData) {
				deferred.resolve(resultData);
			});
		}, function(error) {
			GenericAlertService.alertMessage('Calendar Details  are failed to Save', 'Error');
		});
		return deferred.promise;

	},

	service.getDetails = function(editCalendar, projId, sysDate, standardDays, calenderDaysMap) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/tools/calendars/calendardetails.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedCalendar = [];
				$scope.currentDate = new Date(sysDate);
				$scope.systemDate = new Date(sysDate);
				$scope.weekDays = [];
				var service = {};
				var editDays = [];
				$scope.disableCalendar = false;
				$scope.activeFlag = 0;
				$scope.startDay = 0;
				$scope.standardDays = standardDays;
				$scope.calName = editCalendar[0].name;
				$scope.calendarId = editCalendar[0].id;
				$scope.calenderDaysMap = calenderDaysMap;

				$scope.getMonths = function(currentDate) {
					var year = currentDate.getYear();
					var month = currentDate.getMonth() + 1;
					var startDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1, 23, 59, 59);
					$scope.startDay = startDayOfMonth.getDay();
					var days = 0;
					$scope.weekDays = [];
					if (month == 2 && year % 4 == 0) {
						days = 29;
					} else if (month == 2) {
						days = 28;
					} else if (month % 2 == 0 && month <= 7) {
						days = 30;
					} else if (month % 2 != 0 && month <= 7) {
						days = 31;
					} else if (month % 2 == 0 && month > 7) {
						days = 31;
					} else if (month % 2 != 0 && month > 7) {
						days = 30;
					}
					var week = [];
					for (var j = 1; j <= $scope.startDay; j++) {
						week.push({
							"value" : null
						});
					}
					var disableFalg = false;

					var sysDate = $scope.systemDate.getDate();
					var sysMonth = $scope.systemDate.getMonth() + 1;
					var sysYear = $scope.systemDate.getFullYear();

					var calDate = currentDate.getDate();
					var calMonth = currentDate.getMonth() + 1;
					var calYear = currentDate.getFullYear();

					var today = sysDate + "-" + sysMonth + "-" + sysYear;
					var calDay = calDate + "-" + calMonth + "-" + calYear;

					var flag = false;
					if (today == calDay) {
						flag = true;
					}
					for (var i = 1; i <= days; i++) {
						if (sysDate > i && flag) {
							disableFalg = true;
						} else {
							disableFalg = false;
						}
						week.push({
							"value" : i,
							"flag" : disableFalg
						});
						if ((i + $scope.startDay) % 7 == 0) {
							$scope.weekDays.push({
								"week" : angular.copy(week)
							});
							var week = [];
						}
					}
					var endDays = 35 - (days + $scope.startDay);
					for (var j = 1; j <= endDays; j++) {
						week.push({
							"value" : null
						});
					}
					$scope.weekDays.push({
						index : $scope.weekDays.length,
						"week" : angular.copy(week)
					});
				}
				$scope.getPreviousMonth = function(currentDate) {
					currentDate.setMonth(currentDate.getMonth() - 1);
					var req = {
						"calendarId" : editCalendar[0].id,
						"status" : 1,
						"projId" : projId,
						"month" : currentDate.getMonth() + 1 + "-" + currentDate.getFullYear()
					};
					CalendarService.getCalDays(req).then(function(data) {
						$scope.calenderDaysMap = data.calenderDaysMap;
						$scope.getMonths(currentDate);
					}, function(error) {
						GenericAlertService.alertMessage('Calendar Details  are failed to Save', 'Error');
					});
					if ($scope.systemDate > currentDate) {
						$scope.disableCalendar = true;
					} else {
						$scope.disableCalendar = false;
					}
				}
				$scope.getNextMonth = function(currentDate) {
					currentDate.setMonth(currentDate.getMonth() + 1);
					var req = {
						"calendarId" : editCalendar[0].id,
						"status" : 1,
						"projId" : projId,
						"month" : currentDate.getMonth() + 1 + "-" + currentDate.getFullYear()
					};
					CalendarService.getCalDays(req).then(function(data) {
						$scope.calenderDaysMap = data.calenderDaysMap;
						$scope.getMonths(currentDate);
					}, function(error) {
						GenericAlertService.alertMessage('Calendar Details  are failed to Save', 'Error');
					});
					if ($scope.systemDate > currentDate) {
						$scope.disableCalendar = true;
					} else {
						$scope.disableCalendar = false;
					}
				}

				$scope.rowSelect = function(day) {
					if (day.select) {
						editDays.push(day);
					} else {
						editDays.pop(day);
					}
				}
				$scope.saveNonWorkingDays = function() {
					var nonWorkingDays = [];
					var currentMonth = 0;
					if (($scope.currentDate.getMonth() + 1) < 10) {
						currentMonth = "0"+ ($scope.currentDate.getMonth() + 1) ;
					} else {
						currentMonth = $scope.currentDate.getMonth() + 1 ;
					}
					angular.forEach(editDays, function(day, key) {
						nonWorkingDays.push(angular.copy({
							"id" : null,
							"calendarId" : $scope.calendarId,
							"date" : day.value + "-" + currentMonth + "-" + $scope.currentDate.getFullYear(),
							"holiday" : true,
							"desc" : "Public Holiday"
						}));
					});
					var req = {
						"calenderSpecialDaysTOs" : nonWorkingDays,
						"calendarId" : $scope.calendarId,
						"projId" : projId,
						"month" : ($scope.currentDate.getMonth() + 1) + "-" + $scope.currentDate.getFullYear()
					};

					CalendarService.saveCalSpecialDays(req).then(function(data) {
						$scope.standardDays = data.calenderRegularDaysTO;
						$scope.calenderDaysMap = data.calenderDaysMap;
						$scope.getMonths($scope.currentDate);
						// GenericAlertService.alertMessage('Non Working Calendar Days are saved successfully', 'Info');
						GenericAlertService.alertMessage('Non Working Calendar days saved successfully', 'Info');
					}, function(error) {
						GenericAlertService.alertMessage('Non Working Calendar Days are failed to Save', 'Error');
					});

					editDays = [];
				}
				$scope.saveExceptionalDays = function() {
					var nonWorkingDays = [];
					var currentMonth = 0;
					if (($scope.currentDate.getMonth() + 1) < 10) {
						currentMonth = "0"+ ($scope.currentDate.getMonth() + 1) ;
					} else {
						currentMonth =$scope.currentDate.getMonth() + 1 ;
					}
					angular.forEach(editDays, function(day, key) {
						nonWorkingDays.push(angular.copy({
							"id" : null,
							"calendarId" : $scope.calendarId,
							"date" : day.value + "-" + currentMonth + "-" + $scope.currentDate.getFullYear(),
							"holiday" : false,
							"desc" : "Special Working Day"
						}));
					});
					var req = {
						"calenderSpecialDaysTOs" : nonWorkingDays,
						"calendarId" : $scope.calendarId,
						"projId" : projId,
						"month" : ($scope.currentDate.getMonth() + 1) + "-" + $scope.currentDate.getFullYear()
					};

					CalendarService.saveCalSpecialDays(req).then(function(data) {
						$scope.standardDays = data.calenderRegularDaysTO;
						$scope.calenderDaysMap = data.calenderDaysMap;
						$scope.getMonths($scope.currentDate);
						// GenericAlertService.alertMessage('Exceptional Working Calendar Days are saved successfully', 'Info');
						GenericAlertService.alertMessage('Exceptional Working Calendar days saved successfully', 'Info');
					}, function(error) {
						GenericAlertService.alertMessage('Exceptional Working Calendar Days are failed to Save', 'Error');
					});
					editDays = [];
				},

				$scope.saveCalRegularDays = function() {
					$scope.standardDays.latest = true;
					$scope.standardDays.projId = projId;
					$scope.standardDays.calendarId = $scope.calendarId;
					var saveReq = {
						"calenderRegularDaysTOs" : [ $scope.standardDays ],
						"calendarId" : $scope.calendarId,
						"projId" : projId,
						"month" : ($scope.currentDate.getMonth() + 1) + "-" + $scope.currentDate.getFullYear()
					};
					CalendarService.saveCalRegularDays(saveReq).then(function(data) {
						$scope.standardDays = data.calenderRegularDaysTO;
						$scope.calenderDaysMap = data.calenderDaysMap
						// GenericAlertService.alertMessage('Calendar Standard Days are saved successfully', 'Info');
						GenericAlertService.alertMessage('Calendar Standard days saved successfully', 'Info');
						$scope.closeThisDialog();
					}, function(error) {
						GenericAlertService.alertMessage('Calendar Standard Days  are failed to Save', 'Error');
					});
				}
				$scope.$on('$destroy', function() {
					editCalendar = [];
					deferred.resolve();
					return deferred.promise;
				});
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
