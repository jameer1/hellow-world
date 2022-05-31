'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state('createtimesheet', {
		url: '/createtimesheet',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/timemanagement/timesheet/createtimesheet/timesheetcreateempdetails.html',
				controller: 'CreateTimeSheetController'
			}
		}
	})
}]).controller(
	"CreateTimeSheetController",
	["$q", "$rootScope", "$scope", "$state", "$filter", "ngDialog", "blockUI", "TimeSheetNotificationFactory", "EpsProjectSelectFactory", "TimesheetCopyFactory", "TimesheetSubmitFactory", "TimeSheetCreateFactory", "WageFactory", "TimeSheetEmpRegFactory", "ModuleUserFactory", "TimeSheetCostCodeFactory", "TimesheetEmpPopupFactory", "UserService", "TimeSheetService", "ProjectCrewPopupService", "TimeSheetIndividualUsersFactory", "PotCommonService", "GenericAlertService","TimeSheetIndividualFactory", function ($q, $rootScope, $scope, $state, $filter, ngDialog, blockUI, TimeSheetNotificationFactory, EpsProjectSelectFactory, TimesheetCopyFactory, TimesheetSubmitFactory, TimeSheetCreateFactory, WageFactory, TimeSheetEmpRegFactory, ModuleUserFactory, TimeSheetCostCodeFactory,
		TimesheetEmpPopupFactory, UserService, TimeSheetService, ProjectCrewPopupService, TimeSheetIndividualUsersFactory, PotCommonService, GenericAlertService, TimeSheetIndividualFactory) {

		$scope.selectedEmp = null;
		$scope.selectedIndex = null;
		$scope.selectedRow = null;
		$scope.timeSheetDetails = [];
		$scope.costcodeMap = [];
		$scope.empWageFactorMap = [];
		$scope.errorFlag = false;
		$scope.empRegMap = [];
		$scope.timeSheetDays = [];
		$scope.timeSheetList = [];
		$scope.activeFlag = false;
		$scope.buttonFlag = false;
		$scope.resetFlag = false;
		$scope.timeFlag = false;
		$scope.currentDate = null;
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
			"approverTo": {
				"id": null,
				"code": null,
				"name": null

			},
			"timeSheetLabelKeyTo": {
				"id": null,
				"code": null,
				"apprStatus": null
			},
			"maxHrs": null,
			"weekCommenceDay": null,
			"weekStartDate": null,
			"weekEndDate": null,
			"additional": null,
			"apprStatus": null

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
		$scope.enableTimeSheet = false;
		$scope.weekSeqId = 0;
		$scope.projWeekStartNo = null;
		$scope.projWeekEndNo = null;
		$scope.maxHrs = 0;
		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.timeSheetSearchReq.projectLabelKeyTO = data.searchProject;
				$scope.getTimeSheetWeekCommenceDay($scope.timeSheetSearchReq.projectLabelKeyTO.projId);

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		}, $scope.getSystemDate = function () {
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
		}, $scope.getCrewList = function (projId) {
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
				$scope.timeSheetList = [];

				$scope.getTimeSheetWeekCommenceDay($scope.timeSheetSearchReq.projectLabelKeyTO.projId);
				$scope.getTimeSheets($scope.crew.id, $scope.timeSheetSearchReq);
				$scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			});
		}, $scope.selectTimesheet = function (timeSheetLabelKeyTo) {

			$scope.timeSheetDetails = [];

			$scope.timeSheetSearchReq.additional = timeSheetLabelKeyTo.additional;
			if ($scope.timeSheetSearchReq.additional == 1) {
				$scope.projWeekStartNo = 0;
				$scope.projWeekEndNo = 6;
			} else {

			}
			$scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
			$scope.getTimeSheetWeekCommenceDay($scope.timeSheetSearchReq.projectLabelKeyTO.projId);
		}, $scope.getIndividualTimeSheetsUsers = function (timeSheetSearchReq) {
			$scope.indiviudalWeekCommenceDay = timeSheetSearchReq.weekCommenceDay;

			var indWeekCommenceDay = $scope.indiviudalWeekCommenceDay;

			var res = indWeekCommenceDay.split(" ");

			var indWeekCommenceDay = res[1];

			$scope.weekEndDay = indWeekCommenceDay;
			var selectedUser = TimeSheetIndividualUsersFactory.getIndividualEmpsFromTimeSheet(timeSheetSearchReq, $scope.empRegMap, $scope.weekEndDay);
			selectedUser.then(function (data) {
				timeSheetSearchReq.timesheetUserLabelKeyTO.id = data.userLabelKeyTO.id;
				timeSheetSearchReq.timesheetUserLabelKeyTO.name = data.userLabelKeyTO.name;
				$scope.timeSheetDetails = [];
				$scope.timeSheetList = [];
				$scope.getTimeSheets($scope.crew.id, $scope.timeSheetSearchReq);
			});
			
			$scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
			
			var timesheetemps = $scope.getIndividualEmpsFromTimeSheet(0, timeSheetSearchReq);
			timesheetemps.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
			});
			/*
			timesheetemps.then(function (data) {
				angular.forEach(data.empRegLabelKeyTOs, function (value, key) {
					timeSheetEmpMap[value.id] = true;
				});
				var popup = TimeSheetCreateFactory.createTimesheet($scope.maxHrs, $scope.crew.id, additional, timeSheetEmpMap, timeSheetSearchReq, $scope.timeSheetDays, $scope.empRegMap, $scope.costcodeMap, $scope.empWageFactorMap, $scope.projWeekStartNo);
				popup.then(function (data) {

					$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
					$scope.timeSheetList.push(data.timeSheetTO);
					timeSheetSearchReq.timeSheetLabelKeyTo.id = data.timeSheetTO.id;
					timeSheetSearchReq.timeSheetLabelKeyTo.code = data.timeSheetTO.code;
					timeSheetSearchReq.additional = data.timeSheetTO.additional;
					timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus = data.timeSheetTO.apprStatus;
					timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
				}, function (error) {

					GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
				});
			
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
			});
			*/
		}, $scope.getIndividualEmpsFromTimeSheet = function (additional, timeSheetSearchReq) {
			var deferred = $q.defer();
			var req = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate,
				"additional": additional
			};
			var timeSheetEmps = TimeSheetService.getIndividualsFromTimeSheet(req);
			timeSheetEmps.then(function (data) {
				deferred.resolve(data);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting empployee details", 'Error');
			});
			return deferred.promise;
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

		}, $scope.selectCrewType = function (crew) {
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
		};
		$scope.selectValue = function(data,value){
			if(data.id == 2){
				$scope.selectTimesheet(value);
			}
		}
		$scope.createTimeSheet = function (timeSheetSearchReq, additional) {
			if (additional == 1) {
				if ($scope.buttonFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveTimeSheetDetails($scope.crew.id);
					}, function (error) {
						$scope.buttonFlag = false;
						$scope.createTimeSheet(timeSheetSearchReq, additional); 
					})
					return;
				}
				//$scope.projWeekStartNo = 0;
				//$scope.projWeekEndNo = 6;
				$scope.timeSheetDetails = [];
				$scope.timeSheetList = [];
	
				/*$scope.$watch('timeSheetSearchReq.weekCommenceDay', function(newValue,oldValue) {
					
					if(newValue!=oldValue){
						GenericAlertService.alertMessage("Please Select Project To Get Details", 'Warning');
						$scope.timeSheetSearchReq.crewLabelKeyTO={};
						$scope.timeSheetSearchReq.timeSheetLabelKeyTo={};
						return;
						
					}
					
				});*/
			}

			$scope.timeSheetSearchReq.timeSheetLabelKeyTo = {
				"id": null,
				"code": null,
				"apprStatus": null
			}
			if (timeSheetSearchReq.projectLabelKeyTO.projId == undefined || timeSheetSearchReq.projectLabelKeyTO.projId <= 0 || timeSheetSearchReq.weekCommenceDay == undefined || timeSheetSearchReq.weekCommenceDay == null) {
				GenericAlertService.alertMessage("Please select project and week commencing day", 'Warning');
				return;
			}
			if ($scope.crew.id == 1 && timeSheetSearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select Crew", 'Warning');
				return;
			}

			var existTimeSheet = $scope.getTimeSheet($scope.crew.id, $scope.timeSheetSearchReq, additional);
			existTimeSheet.then(function (data) {
				if (data.timeSheetTOs.length > 0) {
					if (additional == 0) {
						GenericAlertService.alertMessage("Time sheet already avaiable for the selected crew", 'Warning');
					} else {
						if (additional == 1) {
							GenericAlertService.alertMessage("Additional Time sheet already avaiable for the selected crew", 'Warning');
						}
					}
				} else {
					var timeSheetEmpMap = [];
					if ($scope.crew.id == 2) {
						var timesheetemps = $scope.getIndividualEmpsFromTimeSheet(additional, timeSheetSearchReq);
						timesheetemps.then(function (data) {
							angular.forEach(data.empRegLabelKeyTOs, function (value, key) {
								timeSheetEmpMap[value.id] = true;
							});
							var popup = TimeSheetIndividualFactory.getEmpRegDetails($scope.maxHrs,additional,timeSheetSearchReq, $scope.crew.id, timeSheetEmpMap);
					 //       var popup = TimeSheetCreateFactory.createTimesheet($scope.maxHrs, $scope.crew.id, additional, timeSheetEmpMap, timeSheetSearchReq, $scope.timeSheetDays, $scope.empRegMap, $scope.costcodeMap, $scope.empWageFactorMap, $scope.projWeekStartNo);
							popup.then(function (data) {

								$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
								$scope.timeSheetList.push(data.timeSheetTO);
								timeSheetSearchReq.timeSheetLabelKeyTo.id = data.timeSheetTO.id;
								timeSheetSearchReq.timeSheetLabelKeyTo.code = data.timeSheetTO.code;
								timeSheetSearchReq.additional = data.timeSheetTO.additional;
								timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus = data.timeSheetTO.apprStatus;
								timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
							}, function (error) {

								GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
							});
						}, function (error) {
							GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
						});
					} else {
						var popup = TimeSheetCreateFactory.createTimesheet($scope.maxHrs, $scope.crew.id, additional, timeSheetEmpMap, timeSheetSearchReq, $scope.timeSheetDays, $scope.empRegMap, $scope.costcodeMap, $scope.empWageFactorMap, $scope.projWeekStartNo);
						popup.then(function (data) {

							$scope.getTimeSheets($scope.crew.id, $scope.timeSheetSearchReq);
							$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
							$scope.timeSheetList.push(data.timeSheetTO);
							timeSheetSearchReq.timeSheetLabelKeyTo.id = data.timeSheetTO.id;
							timeSheetSearchReq.timeSheetLabelKeyTo.code = data.timeSheetTO.code;
							timeSheetSearchReq.additional = data.timeSheetTO.additional;
							timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus = data.timeSheetTO.apprStatus;
							timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;

							if (timeSheetSearchReq.additional == 1) {
								$scope.projWeekStartNo = 0;
								$scope.projWeekEndNo = 6;
								$scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
							}
						}, function (error) {
							GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
						});
					}
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
			});

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
		}, $scope.getTimeSheet = function (crewTypeId, timeSheetSearchReq, additional) {
			var deferred = $q.defer();
			if (timeSheetSearchReq.projectLabelKeyTO.projId <= 0) {
				GenericAlertService.alertMessage("Please select Project and Crew", 'Warning');
				return;
			}
			if (crewTypeId == 1) {
				if (timeSheetSearchReq.crewLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Crew", 'Warning');
					return;
				}
				var result = $scope.getCrewTimeSheet(timeSheetSearchReq, additional);
				result.then(function (data) {
					deferred.resolve(data);
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while fetching time sheet", 'Error');
				});
			} else if (crewTypeId == 2) {
				var result = $scope.getIndividualTimeSheet(timeSheetSearchReq, additional);
				result.then(function (data) {
					deferred.resolve(data);
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while fetching time sheet", 'Error');
				});
			}
			return deferred.promise;

		}, $scope.getCrewTimeSheet = function (timeSheetSearchReq, additional) {
			var deferred = $q.defer();
			var timeSheetGetReq = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay": $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate,
				"additional": additional
			};
			TimeSheetService.getCrewTimeSheet(timeSheetGetReq).then(function (data) {
				deferred.resolve(data);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
			return deferred.promise;
		}, $scope.getIndividualTimeSheet = function (timeSheetSearchReq, additional) {
			var deferred = $q.defer();
			var timeSheetGetReq = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"empId": timeSheetSearchReq.timesheetUserLabelKeyTO.id,
				"weekCommenceDay": $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate,
				"additional": additional
			};
			TimeSheetService.getIndividualTimeSheet(timeSheetGetReq).then(function (data) {
				deferred.resolve(data);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
			return deferred.promise;
		}, $scope.getTimeSheets = function (crewTypeId, timeSheetSearchReq) {
			if (timeSheetSearchReq.projectLabelKeyTO.projId <= 0) {
				GenericAlertService.alertMessage("Please select Project and Crew", 'Warning');
				return;
			}
			if (crewTypeId == 1) {
				if (timeSheetSearchReq.crewLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Crew", 'Warning');
					return;
				}
				$scope.getCrewTimeSheets(timeSheetSearchReq);
			} else if (crewTypeId == 2) {
				$scope.getIndividualTimeSheets(timeSheetSearchReq);
			}
		}, $scope.getCrewTimeSheets = function (timeSheetSearchReq) {
			var timeSheetGetReq = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay": $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate
			};
			TimeSheetService.getCrewTimeSheets(timeSheetGetReq).then(function (data) {
				$scope.timeSheetList = data.timeSheetTOs;

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		}, $scope.getIndividualTimeSheets = function (timeSheetSearchReq) {
			var timeSheetGetReq = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"empId": timeSheetSearchReq.timesheetUserLabelKeyTO.id,
				"weekCommenceDay": $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate
			};
			TimeSheetService.getIndividualTimeSheets(timeSheetGetReq).then(function (data) {
				$scope.timeSheetList = data.timeSheetTOs;

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

			$scope.getTimeSheetDetails = function (crewTypeId, timeSheetSearchReq) {
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

					if (timeSheetSearchReq.timeSheetLabelKeyTo.id <= 0) {
						GenericAlertService.alertMessage("Please select or Create TimeSheet for selected Crew", 'Warning');
						return;
					}
					$scope.getCrewTimeSheetDetails(timeSheetSearchReq);

				} else if (crewTypeId == 2) {
					if (timeSheetSearchReq.timesheetUserLabelKeyTO.id == null) {
						GenericAlertService.alertMessage("Please select Individual", 'Warning');
						return;
					}
					if (timeSheetSearchReq.timeSheetLabelKeyTo.id <= 0) {
						GenericAlertService.alertMessage("Please select or Create TimeSheet for selected Crew", 'Warning');
						return;
					}
					$scope.getIndividualTimeSheetDetails(timeSheetSearchReq);
				}
				$scope.getProjSettingsTimeSheetDetails(crewTypeId);
			}, $scope.getCrewTimeSheetDetails = function (timeSheetSearchReq) {
				var timeSheetGetReq = {
					"status": 1,
					"timeSheetId": timeSheetSearchReq.timeSheetLabelKeyTo.id,
					"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
					"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
					"weekCommenceDay": $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
					"weekStartDate": timeSheetSearchReq.weekStartDate,
					"weekEndDate": timeSheetSearchReq.weekEndDate,
					"additional": timeSheetSearchReq.additional,
					"apprStatus": timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus
				};
				TimeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq).then(function (data) {
					$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
					$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
					$scope.timeSheetSearchReq.additional = data.timeSheetTO.additional;
					if ($scope.timeSheetSearchReq.apprStatus == null) {
						$scope.enableTimeSheet = true;
					}
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
					"weekCommenceDay": $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
					"weekStartDate": timeSheetSearchReq.weekStartDate,
					"weekEndDate": timeSheetSearchReq.weekEndDate,
					"additional": timeSheetSearchReq.additional,
					"apprStatus": timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus
				};
				TimeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq).then(function (data) {
					$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
					$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
					$scope.timeSheetSearchReq.additional = data.timeSheetTO.additional;

					if ($scope.timeSheetSearchReq.apprStatus == null) {
						$scope.enableTimeSheet = true;
					}
					if ($scope.timeSheetDetails.length <= 0) {
						GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');

					}
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
				});
			}, $scope.addEmpRegDetails = function (timeSheetSearchReq) {
				/*
				 * if (timeSheetSearchReq.timesheetUserLabelKeyTO.id <= 0) {
				 * GenericAlertService .alertMessage( "Please select or Create
				 * TimeSheet for selected Crew", 'Warning'); return; }
				 */
				if ($scope.buttonFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveTimeSheetDetails($scope.crew.id);
					}, function (error) {
						$scope.buttonFlag = false;
						$scope.addEmpRegDetails(timeSheetSearchReq);
					})
					return;
				}
				if (timeSheetSearchReq.projectLabelKeyTO.projId == undefined || timeSheetSearchReq.projectLabelKeyTO.projId <= 0 || timeSheetSearchReq.weekCommenceDay == undefined || timeSheetSearchReq.weekCommenceDay == null) {
					GenericAlertService.alertMessage("Please select project and Week Commencing Day", 'Warning');
					return;
				}
				if ($scope.crew.id == 1 && timeSheetSearchReq.crewLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Crew", 'Warning');
					return;
				}
				var existingEmpMap = [];
				angular.forEach($scope.timeSheetDetails, function (value, key) {
					existingEmpMap[value.empRegId] = true;

				});
				var empRegServicePopUp = TimeSheetEmpRegFactory.getEmpRegDetails(timeSheetSearchReq, $scope.crew.id, existingEmpMap);

				empRegServicePopUp.then(function (data) {

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
				});
			},

			$scope.seletTimeSheetEmpDetails = function () {
				if ($scope.selectedEmp == undefined || $scope.selectedEmp == null) {
					GenericAlertService.alertMessage("Please select employee to add tasks/expenses", 'Warning');
					return;
				}
				TimesheetEmpPopupFactory.getTimeSheetEmpDetails($scope.empRegMap, $scope.timeSheetSearchReq, $scope.selectedEmp, $scope.costcodeMap);
			}, $scope.rowSelectEmpReg = function (empObj, indexValue) {
				$scope.selectedEmp = empObj;
				$scope.selectedRow = indexValue;
			},

			$scope.total = 0;
		$scope.updateTotal = function (workDetails) {
			workDetails.total = $scope.total;
			var day1 = 0, day1Hrs = 0, day1Mins = 0;
			var day2 = 0, day2Hrs = 0, day2Mins = 0;
			var day3 = 0, day3Hrs = 0, day3Mins = 0;
			var day4 = 0, day4Hrs = 0, day4Mins = 0;
			var day5 = 0, day5Hrs = 0, day5Mins = 0;
			var day6 = 0, day6Hrs = 0, day6Mins = 0;
			var day7 = 0, day7Hrs = 0, day7Mins = 0;
			var totMinutes = 0;
			var totHours = 0;
			if (workDetails.total != undefined || workDetails.total != NaN || workDetails.total != null) {
				day1 = parseFloat(workDetails.day1Hrs || 0);
				day1Hrs = Math.trunc(day1);
				day1Mins = (Number((day1 - day1Hrs).toFixed(2)));

				day2 = parseFloat(workDetails.day2Hrs || 0);
				day2Hrs = Math.trunc(day2);
				day2Mins = (Number((day2 - day2Hrs).toFixed(2)));

				day3 = parseFloat(workDetails.day3Hrs || 0);
				day3Hrs = Math.trunc(day3);
				day3Mins = (Number((day3 - day3Hrs).toFixed(2)));

				day4 = parseFloat(workDetails.day4Hrs || 0);
				day4Hrs = Math.trunc(day4);
				day4Mins = (Number((day4 - day4Hrs).toFixed(2)));

				day5 = parseFloat(workDetails.day5Hrs || 0);
				day5Hrs = Math.trunc(day5);
				day5Mins = (Number((day5 - day5Hrs).toFixed(2)));

				day6 = parseFloat(workDetails.day6Hrs || 0);
				day6Hrs = Math.trunc(day6);
				day6Mins = (Number((day6 - day6Hrs).toFixed(2)));

				day7 = parseFloat(workDetails.day7Hrs || 0);
				day7Hrs = Math.trunc(day7);
				day7Mins = (Number((day7 - day7Hrs).toFixed(2)));

				totMinutes = (day1Mins + day2Mins + day3Mins + day4Mins + day5Mins + day6Mins + day7Mins) * 100;
				totHours = day1Hrs + day2Hrs + day3Hrs + day4Hrs + day5Hrs + day6Hrs + day7Hrs;
				while (totMinutes >= 60) {
					totHours = totHours + Math.trunc(totMinutes / 60)
					totMinutes = totMinutes % 60;
				}
				if (totMinutes == 0) {
					totMinutes = 0;
				} else if (totMinutes <= 15) {
					totMinutes = 15;
				} else if (totMinutes <= 30) {
					totMinutes = 30;
				} else if (totMinutes <= 45) {
					totMinutes = 45;
				} else if (totMinutes <= 60) {
					totHours = totHours++;
				}
				totMinutes = totMinutes / 100
				workDetails.total = totHours + totMinutes;
				return workDetails.total;
			}
		}

		$scope.selectCostCode = function (workDetails, timeSheetEmpWorkDtlTOs) {
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
				GenericAlertService.alertMessage("Please select project to Add Progress", 'Warning');
				return;
			}
			var existingEmpCostCodeMap = [];
			angular.forEach($scope.timeSheetDetails, function (value, key) {
				existingEmpCostCodeMap[value.costId] = true;
			});
			var costCodepopup = TimeSheetCostCodeFactory.getCostCodeDetails($scope.timeSheetSearchReq, existingEmpCostCodeMap);
			costCodepopup.then(function (data) {
				if (validateWageAndCostCode(workDetails.empWageId, data.projCostItemTO.id, timeSheetEmpWorkDtlTOs)) {
					workDetails.costId = data.projCostItemTO.id;
					workDetails.costCode = data.projCostItemTO.code;
					$scope.buttonFlag = true;
				}

			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
			})

		}, $scope.getWageFactor = function (workDetails, timeSheetEmpWorkDtlTOs) {

			var getReq = {
				"status": 1
			};

			var wagePopup = [];
			var existingWageFactorMap = [];
			angular.forEach(timeSheetEmpWorkDtlTOs, function (value, key) {
				existingWageFactorMap[value.wageId] = true;
			});
			wagePopup = WageFactory.wageFactorDetailsList(getReq, existingWageFactorMap);
			wagePopup.then(function (data) {
				if (validateWageAndCostCode(data.employeeWageRateTO.wageRateId, workDetails.costId, timeSheetEmpWorkDtlTOs)) {
					workDetails.empWageId = data.employeeWageRateTO.wageRateId;
					workDetails.wageFactor = data.employeeWageRateTO.wageFactor;
					workDetails.wageCode = data.employeeWageRateTO.code;
					$scope.buttonFlag = true;
				}

			}, function (error) {

				GenericAlertService.alertMessage("Error occured while selecting WageFactor Details", 'Error');
			});
		}

		function validateWageAndCostCode(wageId, costId, timeSheetEmpWorkDtlTOs) {
			for (const empWork of timeSheetEmpWorkDtlTOs) {
				if (empWork.costId === costId && empWork.empWageId === wageId) {
					GenericAlertService.alertMessage("Selected Wage factor and Cost code already used", 'Warning');
					return false;
				}

			}
			return true;
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

		}, $scope.deleteEmpReg = function (selectedEmp, workDetails) {
			selectedEmp.timeSheetEmpWorkDtlTOs.pop(workDetails);
		}, $scope.saveTimeSheetDetails = function (crewTypeId) {
			$scope.buttonFlag = false;
			if (crewTypeId == 1) {
				$scope.saveCrewTimeSheetDetails();
			} else if (crewTypeId == 2) {
				$scope.saveIndividualTimeSheetDetails();
			}
			$state.go("listofcreatetimesheet");
		}, $scope.saveCrewTimeSheetDetails = function () {
			var flag = false;
			angular.forEach($scope.timeSheetDetails, function (value, key) {

				if (value.empRegId != null) {
					angular.forEach(value.timeSheetEmpWorkDtlTOs, function (value1, key) {

						if (value1.empWageId == null || value1.empWageId == undefined) {

							GenericAlertService.alertMessage('Please Select wage factor', "Warning");
							forEach.break();
							return;
						}
						if (value1.costId == null || value1.costId == undefined) {
							blockUI.stop();
							GenericAlertService.alertMessage('Please Select CostCode', "Warning");
							forEach.break();
							return;
						}
					});
					return;
				}
			});
			var flag = false;
			var duplicateMap = [];
			angular.forEach($scope.timeSheetDetails, function (value, key) {
				angular.forEach(value.timeSheetEmpWorkDtlTOs, function (value1, key) {
					if (duplicateMap[(value1.empDtlId) + " " + (value1.empWageId) + " " + value1.costId] != null) {
						value1.duplicateFlag = true;
						flag = true;
					} else {
						value1.duplicateFlag = false;
						duplicateMap[(value1.empDtlId) + " " + (value1.empWageId) + " " + value1.costId] = true;
					}
				})
			});
			if (flag) {
				GenericAlertService.alertMessage('Combination of Duplicate cost codes and wages  are not allowed', "Warning");
				blockUI.stop();
				return;
			}
			var req = {
				"timeSheetEmpDtlTOs": $scope.timeSheetDetails,
				"timeSheetTO": {
					"status": 1,
					"id": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
					"code": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
					"crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id,
					"weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
					"apprStatus": null,
					"additional": $scope.timeSheetSearchReq.additional,
					"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
					"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
					"maxHrs": $scope.maxHrs
				},
				"status": 1
			};

			blockUI.start();
			var empMaxHrsBookedMap = [];
			var message = '';
			TimeSheetService.saveCrewTimeSheetDetails(req).then(function (data) {
				blockUI.stop();
				empMaxHrsBookedMap = data.empMaxHrsBookedMap;
				if (Object.keys(empMaxHrsBookedMap) != undefined && Object.keys(empMaxHrsBookedMap) != null && Object.keys(empMaxHrsBookedMap).length > 0) {
					angular.forEach(Object.keys(empMaxHrsBookedMap), function (value, key) {
						for (const obj of $scope.timeSheetDetails) {
							if (obj.empRegId == value) {
								message = message + obj.empDetailLabelKeyTO.code + " , ";
							}
						}
						$scope.errorFlag = true;
						GenericAlertService.alertMessage("Following employee(s) are exceeded max hours :" + message, "Warning");
					});
				} else {
					$scope.getProjSettingsTimeSheetDetails(crewTypeId);
					$scope.errorFlag = false;
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
			angular.forEach($scope.timeSheetDetails, function (value, key) {
			});
			var req = {
				"timeSheetEmpDtlTOs": $scope.timeSheetDetails,
				"timeSheetTO": {
					"status": 1,
					"id": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
					"code": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
					"empId": $scope.timeSheetDetails[0].empRegId,
					"weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
					"apprStatus": null,
					"additional": $scope.timeSheetSearchReq.additional,
					"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
					"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
					"maxHrs": $scope.maxHrs
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
						for (const obj of $scope.timeSheetDetails) {
							if (obj.empRegId == value) {
								message = message + obj.empDetailLabelKeyTO.code + " , ";
							}
						}
					});
					GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
				} else {
					$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
					GenericAlertService.alertMessage('Emp(s)popup are saved successfully', "Info");
				}
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Emp(s)popup are failed to save', "Error");
			});
		}, $scope.submitTimeSheet = function () {
			if ($scope.buttonFlag == true) {
				GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
					$scope.saveTimeSheetDetails($scope.crew.id);
				}, function (error) {
					$scope.buttonFlag = false;
					$scope.submitTimeSheet();
				})
				return;
			}


			if ($scope.errorFlag) {
				GenericAlertService.alertMessage("Timesheet cannot be booked more than max hours,so please save again", 'Warning');
				return;
			}
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
				GenericAlertService.alertMessage("Please select project to submit", 'Warning');
				return;
			}

			var submitpopup = TimesheetSubmitFactory.getTimeSheetSubmitDetails($scope.crew, $scope.timeSheetSearchReq, $scope.timeSheetDetails, $scope.maxHrs);
			submitpopup.then(function (data) {
				$scope.timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus = "Submitted";
				$scope.getTimeSheetDetails($scope.crew.id, $scope.timeSheetSearchReq);
			}, function (error) {

				GenericAlertService.alertMessage("Error occurred while submitting time sheet", 'Error');
			})

		}, $scope.checkSettingsTimes = [];
		$scope.newDate = null;
		$scope.timeFlag = false;
		$scope.getProjSettingsTimeSheetDetails = function (crewTypeId) {
			if (crewTypeId == 1) {
				var req = {
					"status": 1,
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
					"crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id,
			//		"empId": $scope.timeSheetDetails[0].empRegId,
					"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
					"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
					"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
					"apprStatus": 'Submission'
				};
			} else if (crewTypeId == 2) {
				var req = {
					"status": 1,
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
			//		"empId": $scope.timeSheetDetails[0].empRegId,
					"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
					"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
					"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
					"apprStatus": 'Submission'
				};
			}
			TimeSheetService.getProjSettingsTimeSheetDetails(req).then(function (data) {
				$scope.projsettingsTimeSubmitDetails = data.labelKeyTOs[0];
				if ($scope.projsettingsTimeSubmitDetails.id <= 0) {
					$scope.timeFlag = true;
				}
			}, function (error) {
				//GenericAlertService.alertMessage("Error occured while getting projsettings time sheet details", "Error");
			});
		}, $scope.copyTimeSheetEmpRegDetails = function (crewTypeId, timeSheetSearchReq) {
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null || $scope.timeSheetSearchReq.crewLabelKeyTO.id == undefined || $scope.timeSheetSearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select project and crew to add employees to time sheet", 'Warning');
				return;
			}
			var additional = 0;
			var existTimeSheet = $scope.getTimeSheet($scope.crew.id, $scope.timeSheetSearchReq, additional);
			existTimeSheet.then(function (data) {
				if (data.timeSheetTOs.length > 0) {
					if (timeSheetSearchReq.timeSheetLabelKeyTo.id <= 0) {
						GenericAlertService.alertMessage("Please select or Create TimeSheet for selected Crew", 'Warning');
						return;
					}
					$scope.getCopyTimeSheetEmpRegDetails();
				} else {
					$scope.getCopyTimeSheetEmpRegDetails();
					
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
			});
			
		}, $scope.getCopyTimeSheetEmpRegDetails = function() {
			var empRegIds = [];
			angular.forEach($scope.timeSheetDetails, function(value, key) {
				empRegIds.push(value.empRegId);
			});
			var popup = TimesheetCopyFactory.copyTimeSheetEmpRegDetails($scope.projWeekStartNo, $scope.timeSheetSearchReq);
			popup.then(function (data) {
				var showIgnoringDuplicateMessage = false;
				angular.forEach(data, function (value, key) {
					if (!empRegIds.includes(value.empRegId)) { 
						$scope.timeSheetDetails.push(value);
					} else {
						showIgnoringDuplicateMessage = true;
					}
				});
				if (!showIgnoringDuplicateMessage) {
					GenericAlertService.alertMessage("Employee Details are added", "Info");
				} else {
					GenericAlertService.alertMessage("Duplicate employee(s) were ignored", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
			})
		}, $scope.getTimeSheetNotificationsForSubmit = function (projId, flag) {
			if (!flag) {
				GenericAlertService.alertMessage("Your Normal TimeSheet Hours Not Completed", 'Warning');
				return;
			}
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId.projId <= 0) {
				GenericAlertService.alertMessage("Please select Project and Crew to get Notification details", 'Warning');
				return;
			}
			console.log($scope.timeSheetSearchReq);
			console.log($scope.crew.id);
			var popup = TimeSheetNotificationFactory.getTimeSheetNotificationDetails($scope.timeSheetSearchReq, $scope.crew.id);
			popup.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
			})
		},

			$scope.getTimeSheetNotificationsForApproval = function (projId) {
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

			$scope.deactivateEmployee = function () {
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
						GenericAlertService.alertMessage('Employeedetails are Deactivated Successfully', 'Info');
					});

					angular.forEach(editemptimesheet, function (value, key) {
						$scope.emptimesheets.splice($scope.emptimesheets.indexOf(value), 1);
					}, function (error) {
						GenericAlertService.alertMessage('Employeedetails  are failed to Deactivate', "Error");
					});
					emptimesheets = [];
					$scope.deleteIds = [];

				}
			}, $scope.checkDecimal = function (costObj, dayHrs, maxHrs) {
				costObj.errorFlag = false;

				if (dayHrs != undefined && dayHrs != null) {
					var decimalInput = dayHrs.split('.');
					if (dayHrs > maxHrs) {
						costObj.errorFlag = true;
						$scope.buttonFlag = false;
						GenericAlertService.alertMessage('Timesheet cannot be booked more than max hours', "Warning");
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
			}, $scope.validateTotalDayWiseWageHrs = function (timeSheetEmpWorkDtlTOs, indexValue, maxHrs) {
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
			}, $scope.resetTimeSheet = function () {
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
				$scope.timeSheetDays = [];
				$scope.empRegMap = [];
				$scope.enableTimeSheet = false;
				$scope.weekSeqId = 0;
				$scope.projWeekStartNo = null;
				$scope.projWeekEndNo = null;
				$scope.maxHrs = 0;
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
				$scope.timeSheetList = [];
			}
			,
			$scope.workDairyCostCodeList = [{
				"id": null,
				"code": null,
				"name": null,
				"select": false
			}];
		$scope.clear1 = function () {
			$scope.timeSheetSearchReq = {
				"weekCommenceDay": null,
				"crewLabelKeyTO": {
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
				template: 'views/help&tutorials/asbuiltrecordshelp/timesheet/createtimesheethelp.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function ($scope) {
		
				}]
			});
			return deferred.promise;
		}

		var commonService = {};
		$scope.groupPage = function () {
			var group = commonService.grouping();
			group.then(function (data) {

			}, function (error) {
				GenericAlertService.alertMessage("Error", 'Info');
			})
		}
		$scope.sortPage = function () {
			var sort = commonService.sorting();
			sort.then(function (data) {

			}, function (error) {
				GenericAlertService.alertMessage("Error", 'Info');
			})
		}
		var grouppagepopup;
		var sortpagepopup;
		commonService.grouping = function () {
			var deferred = $q.defer();
			grouppagepopup = ngDialog.open({
				template: 'views/groupingsorting/asbuiltrecords/timesheet/createtimesheetgroup.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom5',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function ($scope) {

				}]
			});
			return deferred.promise;
		}
		commonService.sorting = function () {
			var deferred = $q.defer();
			sortpagepopup = ngDialog.open({
				template: 'views/groupingsorting/asbuiltrecords/timesheet/createtimesheetsort.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom5',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function ($scope) {

				}]
			});
			return deferred.promise;
		}
	}]);
