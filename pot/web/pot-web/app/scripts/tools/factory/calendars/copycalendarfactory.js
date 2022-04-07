'use strict';
app.factory('CopyCalendarFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CalendarService", "CopyCalendarPopupFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		CalendarService, CopyCalendarPopupFactory, GenericAlertService) {
	var service = {};
	service.getCopyDetails = function(calType, projId) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/tools/calendars/copycalendar.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [
					'$scope',
					function($scope) {
						$scope.calType = calType;
						var selectedCalendar = [];
						$scope.calendarList = [];
						$scope.calenderLabelKeyTO = {
							"id" : null,
							"code" : null,
							"name" : null
						}
						$scope.calendarList.push({
							'selected' : false,
							'calName' : null,
							'calDefaultValue' : '',
							'status' : 1,
							"projId" : projId,
							"latest" : true
						});
						$scope.getGlobalCalendarMap = function() {
					var req = {

					}
					CalendarService.getGlobalCalendarMap(req).then(function(data) {
						$scope.GlobalCalDuplicateMap = data.calendarUniqueMap;
					}, function(error) {
						GenericAlertService.alertMessage('Error Occured while getting calendar duplicate details ', "Error");
					})
				}
				var isDuplicate = false;
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
						$scope.selectCalType = function() {
							$scope.calenderLabelKeyTO = {
								"id" : null,
								"code" : null,
								"name" : null
							}
						}, $scope.selectCalendar = function() {
							var popupData = CopyCalendarPopupFactory.getCalendars($scope.calType, projId);
							popupData.then(function(data) {
								$scope.calenderLabelKeyTO.id = data.id;
								$scope.calenderLabelKeyTO.code = data.code;
								$scope.calenderLabelKeyTO.name = data.name;
							});
						}, $scope.checkCalenderNameDuplicates = function(calname) {
							if ($scope.ProjectCalDuplicateMap[calname.toUpperCase()] != null) {
							isDuplicate = true;
								GenericAlertService.alertMessage('Duplicate project calendar names are not allowed', "Warning");
								return;
							}
							 isDuplicate = false;
						}, 
						
						$scope.copyCalendar = function() {
							if (isDuplicate) {
									GenericAlertService.alertMessage('Duplicate project calendar names are not allowed', "Warning");
									return;
								}
							if ($scope.calenderLabelKeyTO.id <= 0) {
								GenericAlertService.alertMessage('Please select copy calendar',
										'Warning');
								return;
							}
							var req = {
								"calenderTOs" : $scope.calendarList,
								"projId" : projId,
								"calType" : $scope.calType,
								"id" : $scope.calenderLabelKeyTO.id
							};
							GenericAlertService.confirmMessageModal('Do you really want to copy calendar details ', 'Warning', 'YES', 'NO').then(function() {
							CalendarService.copyCalendar(req).then(
									function(data) {
										var result = data.calenderTOs;
										var succMsg = GenericAlertService.alertMessageModal(
												'Calendar Details saved successfully', 'Info');
										succMsg.then(function(popData) {
											$scope.closeThisDialog();
											deferred.resolve(data);
										});
									},
									function(error) {
										GenericAlertService.alertMessage(
												'Calendar Details  are failed to Save', 'Error');
									});
							}, function(data) {
								deferred.resolve(data);
								ngDialog.close();
								})
						}
					} ]

		});
		return deferred.promise;
	}
	return service;
}]);