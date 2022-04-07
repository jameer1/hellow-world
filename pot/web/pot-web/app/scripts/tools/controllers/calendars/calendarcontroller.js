'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("calendar", {
		url: '/calendar',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/tools/calendars/calendarsummary.html',
				controller: 'CalendarController'
			}
		}
	})
}]).controller("CalendarController", ["$rootScope", "$window", "$scope","$q", "$state", "ngDialog", "CalendarService", "EpsProjectSelectFactory", "GenericAlertService", "CalendarPopupFactory", "CopyCalendarFactory", "CalendarDetailsFactory","stylesService", "ngGridService", function ($rootScope, $window, $scope,$q, $state, ngDialog, CalendarService, EpsProjectSelectFactory, GenericAlertService, CalendarPopupFactory, CopyCalendarFactory, CalendarDetailsFactory, stylesService, ngGridService) {
	$scope.currentDate = new Date();
	var editCalendar = [];
	//var deleteIds = [];
	var selectedCalanders = [];
	$scope.stylesSvc = stylesService;
	$scope.isDisabled = false;
	$scope.calType = 'GCAL';
	$scope.searchProject = {};
	$scope.currentDate = null;
	var defaultCalSelected = false;
	$scope.activeFlag = 0;
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.tableData = [];
			$scope.searchProject = data.searchProject;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.selectCalType = function (calendar) {
		$scope.calendarList = [];
		editCalendar = [];
		if ($scope.calType == 'GCAL') {
			$scope.searchProject.projId = null;
			$scope.searchProject = [];
			$scope.getCalendars();
		}
				else{
			$scope.gridOptions.data =[];
		}

	}, $scope.rowSelect = function (calendar) {
		if (calendar.selected) {
			if (calendar.calDefaultValue == 1 && editCalendar.length == 0) {
				defaultCalSelected = true;
				calendar.defaultCal = true;
				editCalendar.push(calendar);
			} else if (calendar.calDefaultValue == 1 && editCalendar.length > 0) {
				calendar.selected = false;
				GenericAlertService.alertMessage("Cannot edit default calendar, when a normal calendar is selected.", 'Warning');
				return;
			}
			if (defaultCalSelected && calendar.calDefaultValue != 1) {
				calendar.selected = false;
				GenericAlertService.alertMessage("Cannot edit other calendar, when default calendar is selected.", 'Warning');
			} else if (calendar.calDefaultValue != 1)
				editCalendar.push(calendar);
		} else {
			if (calendar.calDefaultValue == 1) {
				defaultCalSelected = false;
				calendar.defaultCal = false;
			}
			editCalendar.splice(editCalendar.indexOf(editCalendar), 1)
		}
	}, $scope.getCalendars = function (click) {
		$scope.calendarList = [];
		editCalendar = [];
		if ($scope.calType == 'PCAL') {
			if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
				GenericAlertService.alertMessage("Please Select Project", 'Warning');
				return;
			}
		}
		if ($scope.calType == 'PCAL') {
			$scope.activeFlag = 1;
		}
		var req = {
			"status": 1,
			"calType": $scope.calType,
			"projId": $scope.searchProject.projId
		};
		$scope.isDisabled = true;
		CalendarService.getCalendars(req).then(function (data) {
			$scope.calendarList = data.calenderTOs;
			$scope.gridOptions.data = angular.copy($scope.calendarList);
			$scope.currentDate = data.currentDate;
			if(click=='click'){
				if ($scope.calendarList.length <= 0) {
					GenericAlertService.alertMessage("Calendars are not defined for selected search criteria", 'Warning');
				}
			}
		});
	}, $scope.copyCalendar = function () {
		if ($scope.calType == 'PCAL' && $scope.searchProject.projId <= 0) {
			GenericAlertService.alertMessage("Please Select Project", 'Warning');
			return;
		}
		var details = CopyCalendarFactory.getCopyDetails($scope.calType, $scope.searchProject.projId);
		details.then(function (data) {
			$scope.calendarList = data.calenderTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
		})
	}, $scope.addCalendar = function (actionType) {
		angular.forEach(editCalendar, function (value, key) {
			value.selected = false;
		});
		if (editCalendar.length > 0 && actionType == "Add") {
			editCalendar = [];
			GenericAlertService.alertMessage("System allow only one operation at a time", 'Warning');
			return;
		}

		if ($scope.calType == 'PCAL' && $scope.searchProject.projId <= 0) {
			GenericAlertService.alertMessage("Please Select Project", 'Warning');
			return;
		}
		if (actionType == 'Edit' && editCalendar <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var details = CalendarPopupFactory.getDetails(actionType, editCalendar, $scope.calType, $scope.searchProject.projId);
		editCalendar = [];
		details.then(function (data) {
			if (data)
				$scope.calendarList = data.calenderTOs;
			editCalendar = [];
			defaultCalSelected = false;
			$scope.getCalendars();
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
		})

	}, $scope.deleteCalendar = function () {
		if (editCalendar.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}

		var projAssignedIds = [];
		var deleteIds = [];
		$scope.calanderId = [
			{
				id: null
			}
		]
		$scope.nondeleteIds = [];
		angular.forEach(editCalendar, function (value, key) {
			if (value.id) {
				for (var i = 0; i < $scope.calendarList.length; i++) {
					if ((value.id == $scope.calendarList[i].id) && ($scope.calendarList[i].projectAssigned == false)) {
						deleteIds.push(value.id);
					}
					else if ((value.id == $scope.calendarList[i].id) && ($scope.calendarList[i].projectAssigned == true)) {
						projAssignedIds.push(value.id);
					}

				}

			}
		});
		if (projAssignedIds.length <= 0) {
			var req = {
				"id": deleteIds,
				"projId": $scope.searchProject.projId
			};
			CalendarService.deleteCalendar(req).then(function (data) {
				GenericAlertService.alertMessage('Calendar Details Deactivated successfully', 'Info');
				$scope.getCalendars();
			}, function (error) {
				GenericAlertService.alertMessage('Calendar are failed to Deactivate', "Error");
			});
		}
		else {
			var calNames = [];
			for (var j = 0; j < projAssignedIds.length; j++) {
				for (var i = 0; i < $scope.calendarList.length; i++) {
					if (projAssignedIds[j] == $scope.calendarList[i].id) {
						calNames.push($scope.calendarList[i].name)
					}
				}
			}
			if (calNames.length == 1) {
				GenericAlertService.alertMessage("Delete not possible. " + calNames + "  is assigned to project", 'Warning');
			}
			else {
				GenericAlertService.alertMessage("Delete not possible. " + calNames + "  are assigned to project", 'Warning');
			}
		}

		editCalendar = [];
    //$scope.deleteIds = [];

	}, $scope.getCalendarDetails = function () {
		angular.forEach(editCalendar, function (value) {
			value.selected = false;
			return;
		});
		if (editCalendar.length <= 0 || editCalendar.length > 1) {
			GenericAlertService.alertMessage("Please select only one calendar", 'Warning');
			editCalendar.length = 0;
			return;
		}
		var details = CalendarDetailsFactory.getCalDays(editCalendar, $scope.searchProject.projId, $scope.currentDate);
		details.then(function (data) {
			editCalendar.length = 0;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	}, $scope.reset = function () {
		$scope.searchProject = [];
		$scope.calendarList = [];
		editCalendar = [];
		$scope.activeFlag = 0;

	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)" ng-disabled="row.entity.calDefaultValue==1?true:false">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select",},
						{ field: 'name', displayName: "Calendar Name", headerTooltip: "Calendar Name"},
						{ name: 'Default', displayName: "Default", headerTooltip: "Default",
						cellTemplate: '<input type="checkbox" ng-model="row.entity.calDefaultValue" ng-true-value="1" ng-false-value="0" ng-checked="row.entity.calDefaultValue==1"  ng-disabled="grid.appScope.isDisabled">'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_Tools_Calenders");
				    $scope.getCalendars();
				}
			});
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/toolscalendarhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);
