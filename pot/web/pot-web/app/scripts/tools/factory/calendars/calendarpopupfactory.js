'use strict';
app.factory('CalendarPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CalendarService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, CalendarService, GenericAlertService) {
	var service = {};
	service.getDetails = function(actionType, editCalendar, calType, projId) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/tools/calendars/calendarpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			preCloseCallback:function(value){ 
				if(value)
				  deferred.resolve();
			},
			controller : [ '$scope', function($scope) {
				$scope.calType = calType;
				$scope.action = actionType;

				$scope.updateSelection = function(position, entities) {
					angular.forEach(entities, function(calendar, index) {
						if (position != index) {
							calendar.calDefaultValue = 0;
							GenericAlertService.alertMessage('Select Only One Default Calendar ', "Warning");
						}
					});
				}

				$scope.selectCheckTransType = function(calendar) {
					if (calendar.calDefaultValue == 1) {
						calendar.calDefaultValue = 1;
						calendar.latest = true;
					} else {
						calendar.calDefaultValue = 0;
						calendar.latest = false;
					}
				}
				
				var copyEditArray = angular.copy(editCalendar);
				var selectedCalendar = [];
				$scope.addCalendarList = [];
				if (actionType === 'Add') {
					$scope.addCalendarList.push({
						'selected' : false,
						'calName' : null,
						'calDefaultValue' : '',
						'status' : 1,
						'calType' : $scope.calType,
						"projId" : projId,
						"latest" : false
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
						'status' : 1,
						'calType' : $scope.calType,
						"projId" : projId,
						"latest" : false
					});
				},

				$scope.checkDuplicate = function(calendar) {
					calendar.duplicateFlag = false;
					//calendar.name = calendar.name.toUpperCase();
					console.log($scope.GlobalCalDuplicateMap);
					if ($scope.GlobalCalDuplicateMap[calendar.name.toUpperCase()] != null) {
						calendar.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate global calendar names are not allowed', "Warning");
						return;
					}
					calendar.duplicateFlag = false;
				},

				$scope.checkProjCalDuplicate = function(calendar) {
					calendar.duplicateFlag = false;
					//calendar.name = calendar.name.toUpperCase();
					if ($scope.ProjectCalDuplicateMap[calendar.name.toUpperCase()] != null) {
						calendar.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate project calendar names are not allowed', "Warning");
						return;
					}
					calendar.duplicateFlag = false;
				},

				$scope.getGlobalCalendarMap = function() {
					var req = {

					}
					CalendarService.getGlobalCalendarMap(req).then(function(data) {
						$scope.GlobalCalDuplicateMap = data.calendarUniqueMap;
					}, function(error) {
						GenericAlertService.alertMessage('Error Occured while getting calendar duplicate details ', "Error");
					})
				}

				$scope.getProjCalendarMap = function() {
					var req = {
						"projId" : projId,
						"status" : 1
					}

					CalendarService.getProjCalendarMap(req).then(function(data) {
						$scope.ProjectCalDuplicateMap = data.calendarUniqueMap;
					}, function(error) {
						GenericAlertService.alertMessage('Error Occured while getting calendar duplicate details ', "Error");
					})
				}
				$scope.saveCalendar = function() {
					var flag = false;
					var calendarMap = [];
					angular.forEach($scope.addCalendarList, function(value, key) {
						if (calendarMap[value.name.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							calendarMap[value.name.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.addCalendarList, function(value, key) {
							if ($scope.GlobalCalDuplicateMap[value.name.toUpperCase()] != null || $scope.ProjectCalDuplicateMap[value.name.toUpperCase()] !=null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate calendar names are not allowed', "Warning");
						return;
					}

					var saveReq = {
						"calenderTOs" : $scope.addCalendarList,
						"projId" : projId
						

					};
					CalendarService.saveCalendars(saveReq).then(function(data) {
						var result = data.calenderTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Calendar details are saved successfully', 'Info');
						var succMsg = GenericAlertService.alertMessageModal('Calendar details saved successfully', 'Info');
						succMsg.then(function(popData) {
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Calendar details  are failed to Save', 'Error');
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
						return;
					}
					if (selectedCalendar.length < $scope.addCalendarList.length) {						
						angular.forEach(selectedCalendar, function(value, key) {							
							$scope.addCalendarList.splice($scope.addCalendarList.indexOf(value), 1);
							// GenericAlertService.alertMessage('Rows are deleted successfully', "Info");
						});
						selectedCalendar = [];
						GenericAlertService.alertMessage('Row(s) deleted successfully', "Info");
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