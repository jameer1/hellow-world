'use strict';
app.factory('ModifyCalendarFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {

	var popup;
	var selectedCalendar = [];
	var service = {};
	service.getDetails = function() {
		var deferred = $q.defer();
		popup = ngDialog.open({
			template : 'views/tools/calendars/modifycalendar.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedCalendar = [];
				$scope.currentDate = new Date();
				$scope.weekDays = [];
				var service = {};
				var editDays = [];
				$scope.activeFlag = 0;
				$scope.startDay = 0;
				$scope.standardDaysList = [];

				$scope.standardDaysList = [ {
					hours : null,
					index : 0,
					selected : false
				}, {
					hours : null,
					index : 1,
					selected : false
				}, {
					hours : null,
					index : 2,
					selected : false
				}, {
					hours : null,
					index : 3,
					selected : false
				}, {
					hours : null,
					index : 4,
					selected : false
				}, {
					hours : null,
					index : 5,
					selected : false
				}, {
					hours : null,
					index : 6,
					selected : false
				} ];

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
							"value" : null,
						});
					}
					for (var i = 1; i <= days; i++) {
						week.push({
							"value" : i,
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
							"value" : null,
						});
					}
					$scope.weekDays.push({
						index : $scope.weekDays.length,
						"week" : angular.copy(week)
					});
				}
				$scope.getPreviousMonth = function(currentDate) {
					currentDate.setMonth(currentDate.getMonth() - 1);
					$scope.getMonths(currentDate);
				}
				$scope.getNextMonth = function(currentDate) {
					currentDate.setMonth(currentDate.getMonth() + 1);
					$scope.getMonths(currentDate);
				}

				$scope.rowSelect = function(day, index) {
					if (day.select) {
						editDays.push(day);
					} else {
						editDays.pop(day);
					}
				}
				$scope.nonWorkingDays = function() {
					var nonWorkingDays = [];
					nonWorkingDays = editDays;
					angular.forEach(nonWorkingDays, function(nonwork) {
						(nonwork.select) ? $("#day_id_" + nonwork.value).css({
							background : "#ec8610",
							color : "white"
						}) : "";
					})
					editDays = [];
				}
				$scope.workingDays = function() {
					var workingDays = [];
					workingDays = editDays;
					angular.forEach(workingDays, function(work) {
						(work.select) ? $("#day_id_" + work.value).css({
							background : "#f2f7fa",
							color : "black"
						}) : "";
					})
					editDays = [];
				}
				$scope.exceptionalDays = function() {
					var exceptionalDays = [];
					exceptionalDays = editDays;
					angular.forEach(exceptionalDays, function(exceptional) {
						(exceptional.select) ? $("#day_id_" + exceptional.value).css({
							background : "blue",
							color : "white"
						}) : "";
					})
					editDays = [];
				}

				$scope.saveCalendar = function() {
					var standarWorkingDays = [];
					var standardNonWorkingDays = [];
					angular.forEach($scope.standardDaysList, function(totData, i) {
						if (totData.hours == undefined) {
							standardNonWorkingDays.push(totData);
						} else {
							standarWorkingDays.push(totData);
						}
					})
					angular.forEach(standardNonWorkingDays, function(standardNonWork, i) {
						if (standardNonWork.index == 0) {
								var sunday =[];
								sunday.push($scope.weekDays[0].week[0].value,$scope.weekDays[1].week[0].value,$scope.weekDays[2].week[0].value,$scope.weekDays[3].week[0].value,$scope.weekDays[4].week[0].value)
								angular.forEach(sunday, function(sunday) {
									$("#day_id_" + sunday).css({
										background : "#ec8610",
										color : "white"
									}) 
								})
						}
						if (standardNonWork.index == 1) {
							var monday =[];
							monday.push($scope.weekDays[0].week[1].value,$scope.weekDays[1].week[1].value,$scope.weekDays[2].week[1].value,$scope.weekDays[3].week[1].value,$scope.weekDays[4].week[1].value);
							angular.forEach(monday, function(monday) {
								$("#day_id_" + monday).css({
									background : "#ec8610",
									color : "white"
								}) 
							})
						}
						if (standardNonWork.index == 2) {
							var tuesday =[];
							tuesday.push($scope.weekDays[0].week[2].value,$scope.weekDays[1].week[2].value,$scope.weekDays[2].week[2].value,$scope.weekDays[3].week[2].value,$scope.weekDays[4].week[2].value);
							angular.forEach(tuesday, function(tuesday) {
								$("#day_id_" + tuesday).css({
									background : "#ec8610",
									color : "white"
								}) 
							})
						}
						if (standardNonWork.index == 3) {
							var wednesday =[];
							wednesday.push($scope.weekDays[0].week[3].value,$scope.weekDays[1].week[3].value,$scope.weekDays[2].week[3].value,$scope.weekDays[3].week[3].value,$scope.weekDays[4].week[3].value);
							angular.forEach(wednesday, function(wednesday) {
								$("#day_id_" + wednesday).css({
									background : "#ec8610",
									color : "white"
								}) 
							})
						}
						if (standardNonWork.index == 4) {
							var thursday =[];
							thursday.push($scope.weekDays[0].week[4].value,$scope.weekDays[1].week[4].value,$scope.weekDays[2].week[4].value,$scope.weekDays[3].week[4].value,$scope.weekDays[4].week[4].value);
							angular.forEach(thursday, function(thursday) {
								$("#day_id_" + thursday).css({
									background : "#ec8610",
									color : "white"
								}) 
							})
						}
						if (standardNonWork.index == 5) {
							var friday =[];
							friday.push($scope.weekDays[0].week[5].value,$scope.weekDays[1].week[5].value,$scope.weekDays[2].week[5].value,$scope.weekDays[3].week[5].value,$scope.weekDays[4].week[5].value);
							angular.forEach(friday, function(friday) {
								$("#day_id_" + friday).css({
									background : "#ec8610",
									color : "white"
								}) 
							})
						}
						if (standardNonWork.index == 6) {
							var satday =[];
							satday.push($scope.weekDays[0].week[6].value,$scope.weekDays[1].week[6].value,$scope.weekDays[2].week[6].value,$scope.weekDays[3].week[6].value,$scope.weekDays[4].week[6].value);
							angular.forEach(satday, function(satday) {
								$("#day_id_" + satday).css({
									background : "#ec8610",
									color : "white"
								}) 
							})
						}
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);