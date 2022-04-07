'use strict';
app.factory('TimeSheetIndividualUsersFactory', ["ngDialog", "$q", "$filter", "TimeSheetService", "CrewPopupTimeSheetFactory", "EmpAttendanceService", "$timeout", "$rootScope", "GenericAlertService", "EmpRegisterPopUpService", function(ngDialog, $q, $filter, TimeSheetService, CrewPopupTimeSheetFactory, EmpAttendanceService, $timeout, $rootScope, GenericAlertService, EmpRegisterPopUpService) {
	var service = {};
	service.getIndividualEmpsFromTimeSheet = function(timeSheetSearchReq, empRegMap,weekDate) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
			"weekStartDate" : $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "yyyy-MM-dd"),
			"weekEndDate" : $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "yyyy-MM-dd")
		};
		var empPopup = TimeSheetService.getAllIndividualsFromTimeSheet(req);
		empPopup.then(function(data) {
			var popup = service.openPopup(data.empRegLabelKeyTOs, empRegMap);
			popup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting empployee details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting empployee details", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function(empRegLabelKeyTOs, empRegMap) {
		var deferred = $q.defer();
		var servicePopup = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetindividualusers.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : true,
			controller : [ '$scope', function($scope) {
				$scope.empRegLabelKeyTOs = empRegLabelKeyTOs;
				$scope.empRegMap = empRegMap;
				$scope.selectIndividualUser = function(emp) {
					var returnObj = {
						"userLabelKeyTO" : {
							"id" : emp.id,
							"code" : emp.code,
							"name" : emp.displayNamesMap['firstName']
						}
					};
					deferred.resolve(returnObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
