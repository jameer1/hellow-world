'use strict';

app.factory('TimeSheetService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	function sendTimesheetRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/timesheet/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		saveTimeSheetNotifications: function (req) {
			return $http({
				url: appUrl.originurl + "/app/notification/saveTimeSheetNotifications",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getTimeSheetOnload: function (req) {
			return sendTimesheetRequest(req, 'getTimeSheetOnload');
		},
		getCrewTimeSheet: function (req) {
			return sendTimesheetRequest(req, 'getCrewTimeSheet');
		},
		getIndividualTimeSheet: function (req) {
			return sendTimesheetRequest(req, 'getIndividualTimeSheet');
		},
		getCrewTimeSheets: function (req) {
			return sendTimesheetRequest(req, 'getCrewTimeSheets');
		},
		getIndividualTimeSheets: function (req) {
			return sendTimesheetRequest(req, 'getIndividualTimeSheets');
		},
		getCrewTimeSheetForApproval: function (req) {
			return sendTimesheetRequest(req, 'getCrewTimeSheetForApproval');
		},
		getIndividualTimeSheetForApproval: function (req) {
			return sendTimesheetRequest(req, 'getIndividualTimeSheetForApproval');
		},
		getIndividualsFromTimeSheet: function (req) {
			return sendTimesheetRequest(req, 'getIndividualsFromTimeSheet');
		},

		getAllIndividualsFromTimeSheet: function (req) {
			return sendTimesheetRequest(req, 'getAllIndividualsFromTimeSheet');
		},
		getCrewTimeSheetDetailsForSubmission: function (req) {
			return sendTimesheetRequest(req, 'getCrewTimeSheetDetailsForSubmission');
		},
		getCopyCrewTimeSheetDetailsForSubmission: function (req) {
			return sendTimesheetRequest(req, 'getCopyCrewTimeSheetDetailsForSubmission');
		},
		getCrewTimeSheetDetailForApproval: function (req) {
			return sendTimesheetRequest(req, 'getCrewTimeSheetDetailForApproval');
		},
		getIndividualTimeSheetDetailsForSubmission: function (req) {
			return sendTimesheetRequest(req, 'getIndividualTimeSheetDetailsForSubmission');
		},
		getIndividualTimeSheetDetailForApproval: function (req) {
			return sendTimesheetRequest(req, 'getIndividualTimeSheetDetailForApproval');
		},
		saveCrewTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'saveCrewTimeSheetDetails');
		},
		saveApproveCrewTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'saveApproveCrewTimeSheetDetails');
		},
		submitCrewTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'submitCrewTimeSheetDetails');
		},
		approveCrewTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'approveCrewTimeSheetDetails');
		},
		saveIndividualTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'saveIndividualTimeSheetDetails');
		},
		submitIndividualTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'submitIndividualTimeSheetDetails');
		},
		approveIndividualTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'approveIndividualTimeSheetDetails');
		},
		getTimeSheetEmpDetails: function (req) {
			return sendTimesheetRequest(req, 'getTimeSheetEmpDetails');
		},
		getTimeSheetEmpExpenses: function (req) {
			return sendTimesheetRequest(req, 'getTimeSheetEmpExpenses');
		},

		saveTimeSheetEmpDetails: function (req) {
			return sendTimesheetRequest(req, 'saveTimeSheetEmpDetails');
		},
		saveTimeSheetEmpExpenses: function (req) {
			return sendTimesheetRequest(req, 'saveTimeSheetEmpExpenses');
		},

		saveTimeSheeteEmpTasks: function (req) {
			return sendTimesheetRequest(req, 'saveTimeSheeteEmpTasks');
		},
		getTimeSheetById: function (req) {
			return sendTimesheetRequest(req, 'getTimeSheetById');
		},
		getEmpRegDetails: function (req) {
			return sendTimesheetRequest(req, 'getEmpRegDetails');
		},
		getProjSettingsTimeSheetDetails: function (req) {
			return sendTimesheetRequest(req, 'getProjSettingsTimeSheetDetails');
		},

		getOtherCrewEmpAttendance: function (req) {
			return sendTimesheetRequest(req, 'getOtherCrewEmpAttendance');
		},
		getCrewAttendanceForIndividuals: function (req) {
			return sendTimesheetRequest(req, 'getCrewAttendanceForIndividuals');
		},
		copyTimeSheetEmpDetails: function (req) {
			return sendTimesheetRequest(req, 'copyTimeSheetEmpDetails');
		},
		addEmpRegToTimeSheet: function (req) {
			return sendTimesheetRequest(req, 'addEmpRegToTimeSheet');
		},
		submitTimeSheetNotifications: function (req) {
			return sendTimesheetRequest(req, 'submitTimeSheetNotifications');
		},
		getProjSettingsForTimeSheet: function (req) {
			return sendTimesheetRequest(req, 'getProjSettingsForTimeSheet');
		},
		getTimeSheetDays: function (req) {
			const startDate = new Date(req.weekCommenceDay);
			startDate.setDate(startDate.getDate() + req.weekEndNo);
			return { 'weekStartDate': new Date(req.weekCommenceDay).getTime(), 'weekEndDate': startDate.getTime() };
		},
		gettimeSheetWageCodeMap: function (req) {
			return sendTimesheetRequest(req, 'timeSheetWageCodeMap');
		},
		getCreatedTimeSheets: function (req) {
			var createdTimeSheets = Restangular.one("timesheet/getCreatedTimeSheets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return createdTimeSheets;
		},
		getSubmittedTimeSheets: function (req) {
			var submittedTimeSheets = Restangular.one("timesheet/getSubmittedTimeSheets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return submittedTimeSheets;
		}
	}
}]);
