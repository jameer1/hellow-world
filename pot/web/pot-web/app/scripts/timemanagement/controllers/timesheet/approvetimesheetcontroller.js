'use strict';

import { format } from "url";

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state('approvetimesheet', {
		url: '/approvetimesheet',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/timemanagement/timesheet/approvetimesheet/timesheetapproveempdetails.html',
				controller: 'ApproveTimeSheetController'
			}
		}
	})
}]).controller("ApproveTimeSheetController", [ "$rootScope", "$scope", "blockUI", "$state", "$q", "ngDialog",
	"TimeSheetNotificationFactory", "EpsProjectSelectFactory", "TimesheetCopyFactory", "TimesheetApproveFactory",
	"WageFactory", "TimeSheetEmpRegFactory", "ModuleUserFactory", "TimeSheetCostCodeFactory",
	"TimesheetApproveEmpPopupFactory", "UserService", "TimeSheetService", "ProjectCrewPopupService",
	"TimeSheetIndividualUsersFactory", "PotCommonService", "GenericAlertService", "$filter" ,function ($rootScope, $scope, blockUI, $state,$q, ngDialog,
	TimeSheetNotificationFactory, EpsProjectSelectFactory, TimesheetCopyFactory, TimesheetApproveFactory,
	WageFactory, TimeSheetEmpRegFactory, ModuleUserFactory, TimeSheetCostCodeFactory,
	TimesheetApproveEmpPopupFactory, UserService, TimeSheetService, ProjectCrewPopupService,
	TimeSheetIndividualUsersFactory, PotCommonService, GenericAlertService, $filter) {
	$scope.selectedEmp = null;
	$scope.selectedRow = null;
	$scope.timeSheetDetails = [];
	$scope.costcodeMap = [];
	$scope.empWageFactorMap = [];
	$scope.empRegMap = [];
	$scope.timeSheetDays = [];
	$scope.timeSheetList = [];
	$scope.timeFlag = false;
	$scope.buttonFlag = false;
	$scope.resetFlag = false;
	$scope.timeSheetSearchReq = {
		"crewLabelKeyTO": {
			"id": null,
			"code": null,
			"name": null

		},
		"projectLabelKeyTO": {
			"projId": null,
			"parentName": null,
			"projName": null

		},
		"userLabelKeyTO": {
			"id": null,
			"code": null,
			"name": null

		},
		"timesheetUserLabelKeyTO": {
			"id": null,
			"code": null,
			"name": null

		},
		"costLabelKeyTO": {
			"id": null,
			"code": null,
			"name": null

		},
		"timeSheetLabelKeyTo": {
			"id": null,
			"code": null,
			"apprStatus": null
		},
		"weekCommenceDay": null,
		"weekStartDate": null,
		"weekEndDate": null,
		"additional": 0,
		/*"apprStatus" : null,*/
		"maxHrs": null

	};
	$scope.crew = {
		id: 1,
		name: 'Team',
		desc: ''
	};
	$scope.crews = [{
		id: 1,
		name: 'Team',
		desc: ''
	}, {
		id: 2,
		name: 'Individual',
		desc: ''
	}];
	$scope.enableTimeSheet = true;
	$scope.projWeekStartNo = null;
	$scope.projWeekEndNo = null;

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.timeSheetSearchReq.projectLabelKeyTO = data.searchProject;
			$scope.getTimeSheetWeekCommenceDay($scope.timeSheetSearchReq.projectLabelKeyTO.projId);
		},
			function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
	},

		$scope.getTimeSheetNotificationsForApprove = function (projId) {
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId.projId <= 0) {
				GenericAlertService.alertMessage("Please select Project and Crew to get Notification details", 'Warning');
				return;
			}
			var popup = TimeSheetNotificationFactory.getTimeSheetNotificationDetails($scope.timeSheetSearchReq, $scope.crew.id);
			popup.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
			})
		},

		$scope.getCrewList = function (projId) {
			if (projId == undefined || projId == null || $scope.timeSheetSearchReq.weekCommenceDay == null) {
				GenericAlertService.alertMessage("Please select project and week commencing day to get crews", 'Warning');
				return;
			}
			var crewReq = {
				"status": 1,
				"projId": projId
			};
			$scope.timeSheetSearchReq.timeSheetLabelKeyTo = {
				"id": null,
				"code": null,
				"apprStatus": null
			}
			var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				$scope.timeSheetSearchReq.crewLabelKeyTO = data.projCrewTO;
				//crewLabelKeyTO.code = data.projCrewTO.code;
				$scope.timeSheetDetails = [];
				$scope.getTimeSheet($scope.crew.id, $scope.timeSheetSearchReq);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			});
		}, $scope.getIndividualTimeSheetsUsers = function (timeSheetSearchReq) {
			var selectedUser = TimeSheetIndividualUsersFactory.getIndividualEmpsFromTimeSheet(timeSheetSearchReq, $scope.empRegMap);
			selectedUser.then(function (data) {
				timeSheetSearchReq.timesheetUserLabelKeyTO.id = data.userLabelKeyTO.id;
				timeSheetSearchReq.timesheetUserLabelKeyTO.name = data.userLabelKeyTO.name;
				$scope.timeSheetDetails = [];
				$scope.getTimeSheet($scope.crew.id, $scope.timeSheetSearchReq);

			});
		}, $scope.getTimeSheetWeekDays = function (timeSheetSearchReq) {
			var req = {
				"weekCommenceDay": timeSheetSearchReq.weekCommenceDay,
				"weekStartNo": $scope.projWeekStartNo,
				"weekEndNo": ($scope.projWeekEndNo - $scope.projWeekStartNo)
			}
			const weekDays = TimeSheetService.getTimeSheetDays(req);
			timeSheetSearchReq.weekStartDate = weekDays.weekStartDate;
			timeSheetSearchReq.weekEndDate = weekDays.weekEndDate;
			$scope.getTimeSheetOnload($scope.timeSheetSearchReq);
		},

		$scope.$watch('timeSheetSearchReq.weekCommenceDay', function () {
			if ($scope.timeSheetSearchReq.weekCommenceDay != null) {
				$scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
			}
		});

	$scope.selectCrewType = function (crew) {
		if (crew.id == 1) {
			$scope.timeSheetSearchReq.userLabelKeyTO = {
				"id": null,
				"code": null,
				"name": null
			};
			$scope.timeSheetSearchReq.timesheetUserLabelKeyTO = {
				"id": null,
				"code": null,
				"name": null
			};
		} else {
			$scope.timeSheetSearchReq.crewLabelKeyTO = {
				"id": null,
				"code": null,
				"name": null
			};
		}
		$scope.timeSheetSearchReq.timeSheetLabelKeyTo = {
			"id": null,
			"code": null,
			"apprStatus": null
		}
		$scope.timeSheetDetails = [];
	}, $scope.getTimeSheetOnload = function (timeSheetSearchReq) {
		if (timeSheetSearchReq.projectLabelKeyTO.projId <= 0) {
			GenericAlertService.alertMessage("Please select Project", 'Warning');
			return;
		}
		var timeSheetGetReq = {
			"status": 1,
			"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
			"weekCommenceDay": timeSheetSearchReq.weekCommenceDay,
			"weekStartDate": timeSheetSearchReq.weekStartDate,
			"weekEndDate": timeSheetSearchReq.weekEndDate
		};
		TimeSheetService.getTimeSheetOnload(timeSheetGetReq).then(function (data) {
			$scope.timeSheetDays = data.timeSheetDays;
			$scope.costcodeMap = data.costCodeMap;
			$scope.empWageFactorMap = data.empWageFactorMap;
			$scope.empRegMap = data.empRegMap;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
		});
	}, $scope.getTimeSheet = function (crewTypeId, timeSheetSearchReq) {
		if (timeSheetSearchReq.projectLabelKeyTO.projId <= 0) {
			GenericAlertService.alertMessage("Please select Project and Crew", 'Warning');
			return;
		}
		if (crewTypeId == 1) {
			if (timeSheetSearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select Crew", 'Warning');
				return;
			}
			$scope.getCrewTimeSheetForApproval(timeSheetSearchReq);
		} else if (crewTypeId == 2) {
			$scope.getIndividualTimeSheetForApproval(timeSheetSearchReq);
		}
	}, $scope.getCrewTimeSheetForApproval = function (timeSheetSearchReq) {
		var timeSheetGetReq = {
			"status": 1,
			"timeSheetId": timeSheetSearchReq.timeSheetLabelKeyTo.id,
			"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
			"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
			"weekCommenceDay": formateDay(timeSheetSearchReq.weekCommenceDay),
			"weekStartDate": timeSheetSearchReq.weekStartDate,
			"weekEndDate": timeSheetSearchReq.weekEndDate,
			"additional": timeSheetSearchReq.additional,
			"apprStatus": 'Submitted'

		};
		TimeSheetService.getCrewTimeSheetForApproval(timeSheetGetReq).then(function (data) {
			$scope.timeSheetList = data.timeSheetTOs;

			if ($scope.timeSheetList.length <= 0) {
				GenericAlertService.alertMessage("Time Sheetdetails are not found", 'Warning');

			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
		});
	}, $scope.getIndividualTimeSheetForApproval = function (timeSheetSearchReq) {
		var timeSheetGetReq = {
			"status": 1,
			"timeSheetId": timeSheetSearchReq.timeSheetLabelKeyTo.id,
			"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
			"empId": timeSheetSearchReq.timesheetUserLabelKeyTO.id,
			"weekCommenceDay": timeSheetSearchReq.weekCommenceDay,
			"weekStartDate": timeSheetSearchReq.weekStartDate,
			"weekEndDate": timeSheetSearchReq.weekEndDate,
			"additional": timeSheetSearchReq.additional,
			"apprStatus": 'Submitted'

		};
		TimeSheetService.getIndividualTimeSheetForApproval(timeSheetGetReq).then(function (data) {
			$scope.timeSheetList = data.timeSheetTOs;
			if ($scope.timeSheetList.length <= 0) {
				GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');

			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
		});
	}, $scope.getTimeSheetWeekCommenceDay = function (projId) {
		var req = {
			"status": 1,
			"projId": projId,
		};
		if (projId <= 0) {
			GenericAlertService.alertMessage("Please select Project ID to get Time Sheet Week Commence Day", 'Warning');
			return;
		}
		TimeSheetService.getProjSettingsForTimeSheet(req).then(function (data) {
			$scope.weekSeqId = data.weekSeqId;
			$scope.projWeekStartNo = data.weeekStartDay;
			$scope.projWeekEndNo = data.weeekEndDay;
			$scope.maxHrs = data.maxHrs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Time Sheet Week Commence day ", 'Error');
		});
	},

	/*$scope.getTimeSheetDetails = function(crewTypeId, timeSheetSearchReq) {
			alert("hi"+$scope.buttonFlag);
			alert(JSON.stringify(crewTypeId));
			if ($scope.buttonFlag == true) {
				GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function() {
					$scope.saveTimeSheetDetails($scope.crew.id);
				}, function(error) {
					$scope.buttonFlag = false;
					$scope.getTimeSheetDetails(crewTypeId, timeSheetSearchReq);
				})
				return;
			} 
		if (timeSheetSearchReq.projectLabelKeyTO.projId == null) {
			GenericAlertService.alertMessage("Please select Project ID", 'Warning');
			return;
		}
		if (timeSheetSearchReq.weekCommenceDay == null) {
			GenericAlertService.alertMessage("Please select Week Commence Day", 'Warning');
			return;
		}
		if (crewTypeId == 1) {
			if (timeSheetSearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select Crew", 'Warning');
				return;
			}
			$scope.getTimeSheetForApproval(timeSheetSearchReq);
		} else if (crewTypeId == 2) {
			$scope.getTimeSheetForApproval(timeSheetSearchReq);
		}
	},*/ $scope.getTimeSheetDetails = function (crewTypeId, timeSheetSearchReq) {
			if ($scope.buttonFlag == true) {
				GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
					$scope.saveTimeSheetDetails($scope.crew.id);
				}, function (error) {
					$scope.buttonFlag = false;
					$scope.getTimeSheetDetails(crewTypeId, timeSheetSearchReq);
				})
				return;
			}
			if (timeSheetSearchReq.projectLabelKeyTO.projId == null) {
				GenericAlertService.alertMessage("Please select Project ID", 'Warning');
				return;
			}
			if (timeSheetSearchReq.weekCommenceDay == null) {
				GenericAlertService.alertMessage("Please select Week Commence Day", 'Warning');
				return;
			}
			if (crewTypeId == 1) {
				if (timeSheetSearchReq.crewLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Crew", 'Warning');
					return;
				}
				$scope.getCrewTimeSheetDetails(timeSheetSearchReq);
			} else if (crewTypeId == 2) {
				if (timeSheetSearchReq.timesheetUserLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Individual",
						'Warning');
					return;
				}
				$scope.getIndividualTimeSheetDetails(timeSheetSearchReq);
			}
		}, $scope.getCrewTimeSheetDetails = function (timeSheetSearchReq) {
			var timeSheetGetReq = {
				"status": 1,
				"timeSheetId": timeSheetSearchReq.timeSheetLabelKeyTo.id,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay": formateDay(timeSheetSearchReq.weekCommenceDay),
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate,
				"additional": timeSheetSearchReq.additional,
				"apprStatus": timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus
			};
			TimeSheetService.getCrewTimeSheetDetailForApproval(timeSheetGetReq).then(function (data) {
				$scope.timeSheetSearchReq.apprUserId = data.timeSheetEmpDtlTOs.apprUserId;

				$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
				$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;

				if ($scope.timeSheetDetails.length <= 0) {
					GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');

				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		}, $scope.getIndividualTimeSheetDetails = function (timeSheetSearchReq) {
			var timeSheetGetReq = {
				"status": 1,
				"timeSheetId": timeSheetSearchReq.timeSheetLabelKeyTo.id,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay": timeSheetSearchReq.weekCommenceDay,
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate,
				"additional": timeSheetSearchReq.additional,
				"apprStatus": timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus
			};
			TimeSheetService.getIndividualTimeSheetDetailForApproval(timeSheetGetReq).then(function (data) {
				$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
				$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
				if ($scope.timeSheetDetails.length <= 0) {
					GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');

				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		}, $scope.addEmpRegDetails = function (timeSheetSearchReq) {
			if (timeSheetSearchReq.projectLabelKeyTO.projId == undefined || timeSheetSearchReq.projectLabelKeyTO.projId <= 0 || timeSheetSearchReq.weekCommenceDay == undefined || timeSheetSearchReq.weekCommenceDay == null) {
				GenericAlertService.alertMessage("Please select project and Week Commencing Day", 'Warning');
				return;
			}
			if ($scope.crew.id == 1 && timeSheetSearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select Crew", 'Warning');
				return;
			}
			var empRegServicePopup = TimeSheetEmpRegFactory.openEmpRegPopup(timeSheetSearchReq);
			empRegServicePopup.then(function (data) {
				angular.forEach(data.timeSheetEmpDtlTOs, function (value, key) {
					$scope.timeSheetDetails.push(value);
				});
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
			});
		}, $scope.getModuleUserDetails = function (userLabelKeyTO) {
			var getReq = {
				"moduleCode": "CREATTIMESHET",
				"actionCode": "APPROVE",
				"permission": "ASBUILTRCRD_TIMSHET_CREATTIMESHET_APPROVE"
			};
			var selectedUser = ModuleUserFactory.getUsersByModulePermission(getReq);

			selectedUser.then(function (data) {
				userLabelKeyTO.id = data.userLabelKeyTO.id;
				userLabelKeyTO.name = data.userLabelKeyTO.name;
				$scope.userLabelKeyTO = data.userLabelKeyTO

			});
		},

		$scope.seletTimeSheetEmpDetails = function () {
			if ($scope.selectedEmp == undefined || $scope.selectedEmp == null) {
				GenericAlertService.alertMessage("Please select employee to add tasks/expenses", 'Warning');
				return;
			}
			TimesheetApproveEmpPopupFactory.getTimeSheetEmpDetails($scope.empRegMap, $scope.timeSheetSearchReq, $scope.selectedEmp, $scope.costcodeMap);
		}, $scope.rowSelectEmpReg = function (empObj, indexValue) {
			$scope.selectedEmp = empObj;
			$scope.selectedRow = indexValue;
		}, $scope.selectCostCode = function (workDetails) {

			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
				GenericAlertService.alertMessage("Please select project to Add Progress", 'Warning');
				return;
			}
			var costCodepopup = TimeSheetCostCodeFactory.getCostCodeDetails($scope.timeSheetSearchReq);
			costCodepopup.then(function (data) {

				workDetails.costId = data.projCostItemTO.id;
				workDetails.costCode = data.projCostItemTO.code;
				$scope.buttonFlag = true;
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
			})

		}, $scope.getWageFactor = function (workDetails) {
			var getReq = {
				"status": 1
			};
			var wagePopup = [];
			wagePopup = WageFactory.wageFactorDetailsList(getReq);
			wagePopup.then(function (data) {
				workDetails.empWageId = data.employeeWageRateTO.wageRateId;
				workDetails.wageFactor = data.employeeWageRateTO.wageFactor;
				workDetails.wageCode = data.employeeWageRateTO.code;
				$scope.buttonFlag = true;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting WageFactor Details", 'Error');
			});
		}

	$scope.addEmpTimeSheet = function (timeSheetDetails) {
		if ($scope.selectedEmp == undefined || $scope.selectedEmp == null) {
			GenericAlertService.alertMessage("Please Select Employee To Add Additional Costcode/Wagefactor", 'Warning');
			return;
		}
		var val = $scope.timeSheetDetails.indexOf($scope.selectedEmp);
		var ohterCostHrsWorkHrs = $scope.selectedEmp.timeSheetEmpWorkDtlTOs[0];
		$scope.timeSheetDetails[val].timeSheetEmpWorkDtlTOs.push({
			"empDtlId": $scope.selectedEmp.id,
			"empWageId": null,
			"costId": null,
			"day1Hrs": 0,
			"day2Hrs": 0,
			"day3Hrs": 0,
			"day4Hrs": 0,
			"day5Hrs": 0,
			"day6Hrs": 0,
			"day7Hrs": 0,
			"day1Flag": ohterCostHrsWorkHrs.day1Flag,
			"day2Flag": ohterCostHrsWorkHrs.day2Flag,
			"day3Flag": ohterCostHrsWorkHrs.day3Flag,
			"day4Flag": ohterCostHrsWorkHrs.day4Flag,
			"day5Flag": ohterCostHrsWorkHrs.day5Flag,
			"day6Flag": ohterCostHrsWorkHrs.day6Flag,
			"day7Flag": ohterCostHrsWorkHrs.day7Flag
		});

	}, $scope.saveTimeSheetDetails = function (crewTypeId) {
		$scope.buttonFlag = false;
		if (crewTypeId == 1) {
			$scope.saveCrewTimeSheetDetails();
		} else if (crewTypeId == 2) {
			$scope.saveIndividualTimeSheetDetails();
		}
		$scope.closeThisDialog();
	}, $scope.saveCrewTimeSheetDetails = function () {
		var req = {
			"timeSheetEmpDtlTOs": $scope.timeSheetDetails,
			"timeSheetTO": {
				"status": 1,
				"id": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
				"code": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
				"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay": formateDay($scope.timeSheetSearchReq.weekCommenceDay),
				"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
				"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
				"maxHrs": $scope.maxHrs,
				"apprUserId": $scope.timeSheetDetails[0].apprUserId,
				"apprStatus": 'Submitted'

			},
			"status": 1
		};

		blockUI.start();
		var empMaxHrsBookedMap = [];
		var message = '';
		TimeSheetService.saveApproveCrewTimeSheetDetails(req).then(function (data) {
			blockUI.stop();
			empMaxHrsBookedMap = data.empMaxHrsBookedMap;
			if (Object.keys(empMaxHrsBookedMap) != undefined && Object.keys(empMaxHrsBookedMap) != null && Object.keys(empMaxHrsBookedMap).length > 0) {
				angular.forEach(Object.keys(empMaxHrsBookedMap), function (value, key) {
					for (const obj of $scope.timeSheetDetails) {
						if (obj.empRegId == value) {
							message = message + obj.empDetailLabelKeyTO.code + " , ";
						}
					}
					GenericAlertService.alertMessage("Following employee(s) are exceeded max hours :" + message, "Warning");
				});

			} else {
				$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
				GenericAlertService.alertMessage('Employee(s) saved successfully', "Info");
			}
			if ($scope.resetFlag) {
				$scope.timeSheetDetails = [];
				$scope.timeSheetSearchReq = {};
				$scope.timeSheetList = [];
				$scope.timeSheetDays = [];
			}
			$scope.resetFlag = false;
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Emp(s)popup are failed to save', "Error");
		});
	}, $scope.saveIndividualTimeSheetDetails = function () {
		var req = {
			"timeSheetEmpDtlTOs": $scope.timeSheetDetails,
			"timeSheetTO": {
				"status": 1,
				"id": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
				"code": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
				"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
				"empId": $scope.timeSheetSearchReq.timesheetUserLabelKeyTO.id,
				"weekCommenceDay": $scope.timeSheetSearchReq.weekCommenceDay,
				"apprStatus": 'Submitted',
				"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
				"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
				"maxHrs": $scope.maxHrs,
			},
			"status": 1
		};
		blockUI.start();
		var empMaxHrsBookedMap = [];
		var message = '';
		TimeSheetService.saveIndividualTimeSheetDetails(req).then(function (data) {
			blockUI.stop();
			empMaxHrsBookedMap = data.empMaxHrsBookedMap;
			if (Object.keys(empMaxHrsBookedMap) != undefined && Object.keys(empMaxHrsBookedMap) != null && Object.keys(empMaxHrsBookedMap).length > 0) {
				angular.forEach(Object.keys(empMaxHrsBookedMap), function (value, key) {
					message = message + $scope.empRegMap[value].code + " , ";
				});
				GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
			} else {
				$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
				GenericAlertService.alertMessage('Employee(s) saved successfully', "Info");
			}
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Emp(s)popup are failed to save', "Error");
		});
	}, $scope.approveTimeSheet = function () {
		$scope.timeSheetSearchReq.apprStatus = true;
		if ($scope.buttonFlag == true) {
			GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
				$scope.saveTimeSheetDetails($scope.crew.id);
			}, function (error) {
				$scope.buttonFlag = false;
				$scope.approveTimeSheet();
			})
			return;
		}

		if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
			GenericAlertService.alertMessage("Please select project to approve", 'Warning');
			return;
		}
		var approvepopup = TimesheetApproveFactory.getTimeSheetAproveDetails($scope.crew, $scope.timeSheetSearchReq, $scope.timeSheetDetails);
		approvepopup.then(function (data) {
			$scope.timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus = 'Approved';

		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while approving", 'Error');
		})
		$scope.getProjSettingsTimeSheetDetails();
	},

		$scope.timeFlag = false;
	$scope.getProjSettingsTimeSheetDetails = function () {
		var req = {
			"status": 1,
			"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
			"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
			"apprStatus": 'Approval'
		};
		TimeSheetService.getProjSettingsTimeSheetDetails(req).then(function (data) {
			$scope.projsettingsTimeSubmitDetails = data.labelKeyTOs[0];
			if ($scope.projsettingsTimeSubmitDetails.id <= 0) {
				$scope.timeFlag = true;
			}
		}, function (error) {
			//GenericAlertService.alertMessage("Error occured while getting projsettings time sheet details", "Error");
		});
	},

		$scope.copyTimeSheetEmpRegDetails = function () {
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null || $scope.timeSheetSearchReq.crewLabelKeyTO.id == undefined || $scope.timeSheetSearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select project and crew to add employees to time sheet", 'Warning');
				return;
			}
			var popup = TimesheetCopyFactory.copyTimeSheetEmpRegDetails($scope.projWeekStartNo, $scope.timeSheetSearchReq);
			popup.then(function (data) {
				angular.forEach(data.timeSheetEmpDtlTOs, function (value, key) {
					$scope.timeSheetDetails.push(value);
				});
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
			})

		}, $scope.timesheetRowSelect = function (emptimesheet) {
			if (emptimesheet.select) {
				editemptimesheet.push(emptimesheet);
			} else {
				editemptimesheet.pop(emptimesheet);
			}
		}, $scope.getTimeSheetNotifications = function (projId) {
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId.projId <= 0) {
				GenericAlertService.alertMessage("Please select Project and Crew to get Notification details", 'Warning');
				return;
			}
			var popup = TimeSheetNotificationFactory.getTimeSheetNotificationDetails($scope.timeSheetSearchReq, $scope.crew.id);
			popup.then(function (data) {
				// $scope.empAttendenceDetails = data.empAttendenceTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
			})
		}, $scope.rowSelect = function (time) {
			if (time.selected) {
				editTimeSheet.push(time);
			} else {
				editTimeSheet.pop(time);
			}
		}, $scope.deleteEmpReg = function (selectedEmp, workDetails) {
			selectedEmp.timeSheetEmpWorkDtlTOs.pop(workDetails);
		}, $scope.deactivateEmployee = function () {
			if (editemptimesheet.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
				return;
			}
			var deleteIds = [];
			$scope.nondeleteIds = [];
			if ($scope.selectedAll) {
				$scope.emptimesheets = [];
			} else {
				angular.forEach(editemptimesheet, function (value, key) {
					if (value.id) {
						deleteIds.push(value.id);
					}
				});
				var req = {
					"empTimeIds": deleteIds,
					"status": 2
				};
				TimeSheetService.deleteEmployeeTimeSheetDetails(req).then(function (data) {
					GenericAlertService.alertMessage('Employee Details are Deactivated Successfully', 'Info');
				});

				angular.forEach(editemptimesheet, function (value, key) {
					$scope.emptimesheets.splice($scope.emptimesheets.indexOf(value), 1);
				}, function (error) {
					GenericAlertService.alertMessage('Employee Details  are failed to Deactivate', "Error");
				});
				emptimesheets = [];
				$scope.deleteIds = [];

			}
		},
		$scope.checkDecimal = function (costObj, dayHrs, maxHrs) {
			costObj.errorFlag = false;

			if (dayHrs != undefined && dayHrs != null) {
				var decimalInput = dayHrs.split('.');
				if (dayHrs > maxHrs) {
					costObj.errorFlag = true;
					$scope.buttonFlag = false;
					//GenericAlertService.alertMessage('Timesheet cannot be booked more than max hours', "Warning");
					return;
				}
				if (decimalInput.length > 2) {
					costObj.errorFlag = true;
					$scope.buttonFlag = false;
					GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
					return;
				}
				if ((decimalInput[0] != undefined && decimalInput[0].length > 2)) {
					costObj.errorFlag = true;
					$scope.buttonFlag = false;
					GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
					return;
				}
				if (decimalInput[1] != undefined) {
					if (decimalInput[1].length == 1) {
						if (decimalInput[1] > 5.99) {
							costObj.errorFlag = true;
							$scope.buttonFlag = false;
							GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
							return;
						}

					} else if (decimalInput[1].length == 2 && decimalInput[1] > 59) {
						userCostObj.errorFlag = true;
						$scope.buttonFlag = false;
						GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
						return;
					} else if (decimalInput[1].length > 2) {
						var secondPartData = decimalInput[1].slice(0, 2);
					} else {
						secondPartData = decimalInput[1];
					}
				}
			}

			costObj.errorFlag = false;
		},
		$scope.validateTotalDayWiseWageHrs = function (timeSheetEmpWorkDtlTOs, indexValue, maxHrs) {
			$scope.buttonFlag = true;
			var workDetails = {};
			var totalWageHrs = 0;
			angular.forEach(timeSheetEmpWorkDtlTOs, function (value, key) {
				if (indexValue == 1) {
					totalWageHrs = totalWageHrs + parseFloat(value.day1Hrs);
				} else if (indexValue == 2) {
					totalWageHrs = totalWageHrs + parseFloat(value.day2Hrs);
				} else if (indexValue == 3) {
					totalWageHrs = totalWageHrs + parseFloat(value.day3Hrs);
				} else if (indexValue == 4) {
					totalWageHrs = totalWageHrs + parseFloat(value.day4Hrs);
				} else if (indexValue == 5) {
					totalWageHrs = totalWageHrs + parseFloat(value.day5Hrs);
				} else if (indexValue == 6) {
					totalWageHrs = totalWageHrs + parseFloat(value.day6Hrs);
				} else if (indexValue == 7) {
					totalWageHrs = totalWageHrs + parseFloat(value.day7Hrs);
				}
				workDetails = value;
			});
			if (totalWageHrs > maxHrs) {
				workDetails.errorFlag = true;
				$scope.buttonFlag = false;
				GenericAlertService.alertMessage('Employee cannot be booked more than max hours per Day', "Warning");
				return;
			}
			workDetails.errorFlag = false;
		},
		$scope.total = 0;
	$scope.updateTotal = function (workDetails) {
		workDetails.total = $scope.total;
		if (workDetails.total != undefined || workDetails.total != NaN || workDetails.total != null) {
			workDetails.total = (parseFloat(workDetails.day1Hrs) || 0) + (parseFloat(workDetails.day2Hrs) || 0) + (parseFloat(workDetails.day3Hrs) || 0) + (parseFloat(workDetails.day4Hrs) || 0) + (parseFloat(workDetails.day5Hrs) || 0) + (parseFloat(workDetails.day6Hrs) || 0) + (parseFloat(workDetails.day7Hrs) || 0);

			return workDetails.total;
		}
	}


	$scope.resetTimeSheet = function () {
		$scope.resetFlag = true;
		if ($scope.buttonFlag == true) {
			GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
				$scope.saveTimeSheetDetails($scope.crew.id);
			}, function (error) {
				$scope.timeSheetDetails = [];
				$scope.buttonFlag = false;
				$scope.resetTimeSheet();
			})
			return;
		}
		$scope.timeFlag = false;
		$scope.timeSheetDetails = [];
		$scope.timeSheetSearchReq = {
			"crewLabelKeyTO": {
				"id": null,
				"code": null,
				"name": null
			},
			"projectLabelKeyTO": {
				"projId": null,
				"parentName": null,
				"projName": null

			},
			"userLabelKeyTO": {
				"id": null,
				"code": null,
				"name": null

			},
			"timesheetUserLabelKeyTO": {
				"id": null,
				"code": null,
				"name": null

			},
			"timeSheetLabelKeyTo": {
				"id": null,
				"code": null,
			},
			"weekCommenceDay": null,
			"weekStartDate": null,
			"weekEndDate": null,
			"additional": 0
		}
		$scope.crew = {
			id: 1,
			name: 'Team',
			desc: ''
		};
		$scope.projWeekStartNo = null;
	}
	$scope.workDairyCostCodeList = [{
		"id": null,
		"code": null,
		"name": null,
		"select": false
	}];

	function formateDay(weekCommenceDay) {
		return $filter('date')(new Date(weekCommenceDay), "dd-MMM-yyyy")
	}
	$scope.clear1 = function() {
		$scope.timeSheetSearchReq.weekCommenceDay = null;
		$scope.timeSheetSearchReq.crewLabelKeyTO.code = null;
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
			template: 'views/help&tutorials/requestapprovalhelp/approvalshelp/approvetimesheethelp.html',
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
