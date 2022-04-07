'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("empattendence", {
		url: '/empattendence',
		parent: 'site',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/timemanagement/attendance/empattendencedetails.html',
				controller: 'EmpAttendenceController'
			}
		}
	})
}]).controller('EmpAttendenceController', ["$filter", "$scope", "$q", "$state", "blockUI", "ngDialog", "AttendanceEmp1CreateFactory", "EmpNotificationFactory", "EmpCopyPopupFactory", "EmpAttendanceService", "EpsProjectSelectFactory", "AttendanceEmpRegFactory", "generalservice", "EmpAttendSheetsPopUpFactory", "EmpAttendenaceLeaveFactory", "ProjectCrewPopupService", "PotCommonService", "GenericAlertService", function ($filter, $scope, $q, $state, blockUI, ngDialog, AttendanceEmp1CreateFactory,
	EmpNotificationFactory, EmpCopyPopupFactory, EmpAttendanceService, EpsProjectSelectFactory, AttendanceEmpRegFactory, generalservice,
	EmpAttendSheetsPopUpFactory, EmpAttendenaceLeaveFactory, ProjectCrewPopupService, PotCommonService, GenericAlertService) {
	$scope.empFlag = false;
	$scope.crewListData = [];
	$scope.empAttendenceDetails = [];
	$scope.searchProject = {};
	$scope.empAttendenceSheets = [];
	$scope.empAttendanceDays = [];
	$scope.moreFlag = 0;
	$scope.lessFlag = 0;
	$scope.attendenceDayMap = [];
	$scope.empLeaveTypeMap = [];
	$scope.empDetailsMap = [];
	$scope.companyMap = [];
	$scope.procureCatgMap = [];
	$scope.currentDate = null;
	$scope.empClassMap = [];
	$scope.empLeaveTypes = generalservice.empLeaveTypes;
	$scope.empAttendReq = {
		"status": 1,
		"crewLabelKeyTO": {
			"id": null,
			"code": null,
			"name": null
		},
		"attendenceId": null,
		"attendenceCode": null,
		"attendenceMonth": null

	};
	$scope.currentMonth = false;
	$scope.empDemobilizationDateMap = [];
	$scope.$watch('empAttendReq.attendenceMonth', function (newValue, oldValue) {

		if (newValue != oldValue && oldValue != null) {

			$scope.empAttendReq.crewLabelKeyTO = {};
			$scope.empAttendReq.attendenceCode = null;
			$scope.empAttendanceDays = [];
			return;
		}

	});
	$scope.getUserProjects = function () {
		$scope.empAttendenceDetails = [];
		$scope.empAttendReq.crewLabelKeyTO = [];
		$scope.empAttendReq.attendenceCode = [];
		$scope.empAttendReq.attendenceMonth = [];
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
		},
			function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
	},


		$scope.resetEmpAttendData = function () {
			if ($scope.empFlag) {
				GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
					$scope.saveEmpAttendanceRecords();
				}, function (error) {
					$scope.empFlag = false;
					$scope.resetEmpAttendData();
				})
				return;
			}
			$scope.searchProject = {};
			$scope.empAttendReq = {
				"status": 1,
				"crewLabelKeyTO": {
					"id": null,
					"code": null,
					"name": null
				},
				"attendenceId": null,
				"attendenceCode": null,
				"attendenceMonth": null
			};
			$scope.empAttendenceDetails = [];
			$scope.empLeaveTypeMap = [];
			$scope.empDetailsMap = [];
			$scope.empAttendanceDays = [];
			$scope.currentMonth = false;
			$scope.empDemobilizationDateMap = [];
		}, $scope.checkAttendance = function (attendanceTypeTO) {
			attendanceTypeTO.colorFlag = false;
			$scope.empFlag = true;
			var inputvar = attendanceTypeTO.code.toUpperCase();
			var keyId = 0;
			var mapCode = null;
			angular.forEach($scope.empLeaveTypeMap, function (value, key) {
				mapCode = value.code.toUpperCase();
				if (mapCode == inputvar) {
					keyId = value.id;
				}
			});
			if (keyId > 0) {
				attendanceTypeTO.id = keyId;
				attendanceTypeTO.colorFlag = true;
				//$scope.empFlag= false;
			} else {
				attendanceTypeTO.id = null;
			}
			attendanceTypeTO.code = inputvar;


			$scope.empAttendanceDays
			var i = 0;
			var count = 0;
			while (i < $scope.empAttendanceDays.length) {
				if (array[i]['status'] == true) count += 1;
				i += 1;
			}

		}
	$scope.getEmpCopyTemplate = function (projId, empAttendReq) {
		if (projId !== undefined && projId != null && empAttendReq.crewLabelKeyTO !== undefined && empAttendReq.crewLabelKeyTO.id <= 0) {
			GenericAlertService.alertMessage("Please select project and crew ", 'Warning');
			return;
		}
		var popup = EmpCopyPopupFactory.copyAttendanceEmpDetails(projId, empAttendReq);
		popup.then(function (data) {
			$scope.empAttendenceDetails = data.empAttendenceTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
		})
	},
		$scope.getEmpNotifications = function (projId) {
			if (!$scope.searchProject.projId) {
				GenericAlertService.alertMessage("Please select project to get Notification details", 'Warning');
				return;
			}

			if (!$scope.empAttendReq.crewLabelKeyTO.id) {
				GenericAlertService.alertMessage("Please select Crew to get Notification details", 'Warning');
				return;
			}

			var popup = EmpNotificationFactory.getEmpNotificationDetails($scope.searchProject, $scope.empAttendReq);
			popup.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
			})
		},
		$scope.getSystemDate = function () {
			var deferred = $q.defer();
			$scope.currentMonth = true;
			var req = {
				"status": 1
			};
			PotCommonService.getSystemDate(req).then(function (data) {
				var currentDate = {
					"currentDate": data.labelKeyTO.displayNamesMap['mmm-yyyy']
				};
				deferred.resolve(currentDate);
			});
			return deferred.promise;
		},
		$scope.getAttendanceOnLoad = function (projId, crewId) {
			var getEmpAttendReq = {
				"status": 1,
				"projId": projId,
				"crewId": crewId,
				"attendenceMonth": $scope.empAttendReq.attendenceMonth,
			};
			EmpAttendanceService.getAttendanceOnLoad(getEmpAttendReq).then(function (data) {
				$scope.empAttendReq.attendenceCode = data.empAttendanceMstrTO.attendenceName;
				$scope.empAttendReq.attendenceId = data.empAttendanceMstrTO.id;
				$scope.empAttendReq.month = data.empAttendanceMstrTO.attendenceMonth;
				$scope.empAttendanceDays = data.attendenceDays;
				$scope.attendenceDayMap = data.attendenceDayMap;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting Attendance Month", 'Error');
			});
		},
		// TODO remove this, if not required
		$scope.getEmpAttendanceSheets = function (projId, crewId) {
			if (projId == null || crewId == null) {
				GenericAlertService.alertMessage("Please select project and crew to get Attendance ID(s)", 'Warning');
			} else {
				var empAttendPopUp = [];
				empAttendPopUp = EmpAttendSheetsPopUpFactory.getEmpAttendenceSheets(projId, crewId);
				empAttendPopUp.then(function (data) {
					$scope.empAttendReq.attendenceCode = data.attendanceObj.code;
					$scope.empAttendReq.attendenceId = data.attendanceObj.id;
					$scope.empAttendReq.attendenceMonth = data.attendanceObj.name;
					$scope.empAttendanceDays = data.attendenceDays;

				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting Attendance ID", 'Error');
				});
			}
		}, $scope.getProjLeaveTypes = function (projId) {
			if (projId == undefined || projId == null) {
				GenericAlertService.alertMessage("Please select project to get Attendance Codes", 'Warning');
				return;
			}
			var projEmpLeaveTypePopup = [];
			projEmpLeaveTypePopup = EmpAttendenaceLeaveFactory.getProjLeaveCodes();
			projEmpLeaveTypePopup.then(function (data) {
			},
				function (error) {
					GenericAlertService.alertMessage("Error occured while getting Attendance Codes", 'Error');
				});
		},
		$scope.getEmpAttendenceDetails = function () {
			if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0 || $scope.empAttendReq.crewLabelKeyTO.id == undefined || $scope.empAttendReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select all the search criteria to get Attendance details", 'Warning');
				return
			}
			if ($scope.empAttendReq.attendenceId == undefined || $scope.empAttendReq.attendenceId == null) {
				GenericAlertService.alertMessage("No data is available, Click on Create to add Employee Attendance ", 'Warning');
				return;
			}
			if ($scope.empFlag) {
				GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
					$scope.saveEmpAttendanceRecords();
				}, function (error) {
					$scope.empFlag = false;
					$scope.getEmpAttendenceDetails();
				});
				return;
			}
			var attendReq = {
				"status": 1,
				"projId": $scope.searchProject.projId,
				"crewId": $scope.empAttendReq.crewLabelKeyTO.id,
				"attendenceMonth": $scope.empAttendReq.attendenceMonth,
				"attendenceId": $scope.empAttendReq.attendenceId
			};
			EmpAttendanceService.getEmpAttendanceDetails(attendReq).then(function (data) {
				$scope.empAttendenceDetails = data.empAttendenceTOs;
				$scope.empDemobilizationDateMap = data.empDemobilizationDateMap;

				angular.forEach($scope.empAttendenceDetails, function (value, key) {
					var totalCount = 0;
					angular.forEach(value.empAttendenceDtlMap, function (value1, key1) {
						if (value1.attendanceTypeTO.code) {
							totalCount += 1;
						}
					});
					value.totalCount = totalCount;
				});
				if ($scope.empAttendenceDetails.length <= 0) {
					GenericAlertService.alertMessage("Employees are not available for the selected crew", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Employees Attendance details", 'Error');
			});
		}, $scope.saveEmpAttendanceRecords = function () {
			$scope.empFlag = false;
			var attendReq = {
				"status": 1,
				"projId": $scope.searchProject.projId,
				"crewId": $scope.empAttendReq.crewLabelKeyTO.id,
				"attendenceId": $scope.empAttendReq.attendenceId,
				"attendenceMonth": $scope.empAttendReq.attendenceMonth,
				"empAttendenceTOs": $scope.empAttendenceDetails
			};
			blockUI.start();
			EmpAttendanceService.saveEmpAttendanceRecords(attendReq).then(function (data) {
				blockUI.stop();
				$scope.empAttendenceDetails = [];
				$scope.empAttendenceDetails = data.empAttendenceTOs;
				angular.forEach($scope.empAttendenceDetails, function (value, key) {
					var totalCount = 0;
					angular.forEach(value.empAttendenceDtlMap, function (value1, key1) {
						if ($scope.empLeaveTypeMap[value1.attendanceTypeTO.id] != null) {
							value1.attendanceTypeTO.code = $scope.empLeaveTypeMap[value1.attendanceTypeTO.id].code;
							value1.attendanceTypeTO.colorFlag = value1.attendenceFlag;
						}
						if (value1.attendanceTypeTO.code) {
							totalCount += 1;
						}

					});
					value.totalCount = totalCount;
				});
				GenericAlertService.alertMessage("Attendance saved successfully", 'Info');
				$state.go("listofempattendence");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while gettting Employees Attendance  details", 'Error');
			});

		},

		$scope.getCrewList = function (projId, crewLabelKeyTO) {
			if (projId == undefined || projId == null) {
				GenericAlertService.alertMessage("Please select project to get crews", 'Warning');
				return;
			}
			if ($scope.empAttendReq.attendenceMonth == null || $scope.empAttendReq.attendenceMonth == undefined || $scope.empAttendReq.attendenceMonth == '') {
				GenericAlertService.alertMessage("Please choose Attendence Month", 'Warning');
				return;
			}
			var crewReq = {
				"status": 1,
				"projId": projId
			};

			var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				crewLabelKeyTO.id = data.projCrewTO.id;
				crewLabelKeyTO.code = data.projCrewTO.code;
				crewLabelKeyTO.name = data.projCrewTO.desc;
				$scope.empAttendenceDetails = [];
				$scope.getAttendanceOnLoad($scope.searchProject.projId, crewLabelKeyTO.id);

			}, function (error) {

				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
			});

		},

		$scope.addEmployeeDetails = function (empAttendReq, projId) {
			if (projId == undefined || projId == null) {
				GenericAlertService.alertMessage("Please select project to get employees", 'Warning');
				return;
			}
			if (empAttendReq.crewLabelKeyTO == undefined || empAttendReq.crewLabelKeyTO == null || empAttendReq.crewLabelKeyTO.id <= 0) {
				GenericAlertService.alertMessage("Please select crew to get employees", 'Warning');
				return;
			}
			if ($scope.empFlag) {
				GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
					$scope.saveEmpAttendanceRecords();
				}, function (error) {
					$scope.empFlag = false;
					$scope.addEmployeeDetails(empAttendReq, projId);
				})
				return;
			}
			var empPopup = [];
			var empExistingMap = [];
			angular.forEach($scope.empAttendenceDetails, function (value, key) {
				empExistingMap[value.empId] = true;
			});
			empPopup = AttendanceEmpRegFactory.getEmpRegDetails(empExistingMap, $scope.empAttendReq, projId);
			empPopup.then(function (data) {
				$scope.empFlag = true;
				angular.forEach(data.empAttendenceTOs, function (value, key) {
					$scope.empAttendenceDetails.push(value);
				});
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Employee Data", 'Error');
			});
		},

		$scope.createAttendanceRecord = function (empAttendReq, projId) {

			if (projId == undefined || projId == null || empAttendReq.crewLabelKeyTO.id == undefined || empAttendReq.crewLabelKeyTO.id <= 0) {
				GenericAlertService.alertMessage("Please select project  and  crew to create Attendance record", 'Warning');
				return;
			}
			if (empAttendReq.attendenceId != undefined || empAttendReq.attendenceId > 0) {
				GenericAlertService.alertMessage("Attendance Record Already existed for selected Crew", 'Warning');
				return;
			}
			var empPopup = [];
			empPopup = AttendanceEmp1CreateFactory.createAttendanceRecords($scope.searchProject,
				$scope.empAttendReq,
				$scope.empAttendanceDays,
				$scope.attendenceDayMap,
				$scope.empLeaveTypeMap,
				$scope.empDetailsMap);
			empPopup.then(function (data) {
				$scope.empAttendenceDetails = data.empAttendenceTOs;
				empAttendReq.attendenceId = data.labelKeyTO.id;
				empAttendReq.attendenceCode = data.labelKeyTO.code;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while opening Create  popup ", 'Error');
			});
		},


		$scope.clickForward = function (moreFlag) {
			if ($scope.moreFlag <= 31) {
				$scope.moreFlag = moreFlag + 7;
			}
		}, $scope.clickBackward = function (moreFlag) {
			if ($scope.moreFlag > 0) {
				$scope.moreFlag = moreFlag - 7;
			}
		}
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
				template: 'views/help&tutorials/asbuiltrecordshelp/empattendancehelp.html',
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