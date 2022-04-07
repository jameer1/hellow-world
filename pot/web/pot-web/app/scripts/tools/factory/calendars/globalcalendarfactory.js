'use strict';
app.factory('GlobalCalendarFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CalendarService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, CalendarService, GenericAlertService) {
	var popup;
	var selectedCalendar = [];
	var service = {};
	service.getDetails = function(actionType, editCalendar,calType) {
		var deferred = $q.defer();
		popup = ngDialog.open({

			template : 'views/tools/calendars/globalcalendarpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.calType= calType;
				$scope.action = actionType;
				var selectedCalendar = [];
				$scope.addCalendarList = [];
				if (actionType === 'Add') {
					$scope.addCalendarList.push({
						'selected' : false,
						'calName' : null,
						'calDefaultValue' : '',
						'status' : 1,
						'calType' : $scope.calType
					});
				} else {
					$scope.addCalendarList = angular.copy(editCalendar);
					editCalendar = [];
				}
				$scope.addRows = function() {
					$scope.addCalendarList.push({
						'selected' : false,
						'calName' : null,
						'calDefaultValue' : '',
						'status' : 1
					});
				},

				$scope.saveCalendar = function() {
					var saveReq = {
						"calenderTOs" : $scope.addCalendarList,
					};
					CalendarService.saveCalenders(saveReq).then(function(data) {
						var result = data.calenderTOs;
						var succMsg = GenericAlertService.alertMessageModal('Calendar Details ' + data.message, data.status);
						succMsg.then(function(popData) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"calenderTOs" : result
							};
							deferred.resolve(returnPopObj);
						});
						ngDialog.close();
					}, function(error) {
						GenericAlertService.alertMessage('Calendar Details  are failed to Save', 'Error');
					});
				}

				$scope.popupRowSelect = function(calendar) {
					if (calendar.selected) {
						selectedCalendar.push(calendar);

					} else {
						selectedCalendar.pop(calendar);
					}
				},

				$scope.deleteRows = function() {
					if (selectedCalendar.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedCalendar.length < $scope.addCalendarList.length) {
						angular.forEach(selectedCalendar, function(value, key) {
							$scope.addCalendarList.splice($scope.addCalendarList.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedCalendar = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);