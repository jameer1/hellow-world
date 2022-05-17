'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("timesheetsearch", {
		url: '/timesheetsearch',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/time sheet/timesheet.searchrecords.html',
				controller: 'TimeSheetSearchController'
			}
		}
	})
}]).controller("TimeSheetSearchController", ["$scope","$q", "ngDialog", "GenericAlertService", "EpsProjectSelectFactory",
	"TimeSheetReportService", "ProjectCrewPopupService", "TimeSheetService", "TimeSheetIndividualUsersFactory",
	"ProjCostCodeService", "$filter", "TimesheetEmpPopupFactory", "stylesService", "ngGridService", 'chartService', function ($scope,$q, ngDialog, GenericAlertService,
		EpsProjectSelectFactory, TimeSheetReportService, ProjectCrewPopupService, TimeSheetService,
		TimeSheetIndividualUsersFactory, ProjCostCodeService, $filter, TimesheetEmpPopupFactory, stylesService, ngGridService, chartService) {
		chartService.getChartMenu($scope);
		$scope.stylesSvc = stylesService;
		$scope.yAxislabels = 'HOURS';
		$scope.projIds = [];
		let timesheetListCopy = null;
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
			"timeSheetLabelKeyTO": {
				"id": null,
				"code": null,
				"apprStatus": null
			},
			"maxHrs": null,
			"weekCommenceDay": null,
			"weekStartDate": null,
			"weekEndDate": null,
			"additional": 0,
		};
		$scope.displayStatusList = [{
			id: 1,
			name: 'Approved',
			code: "Approved"
		}, {
			id: 2,
			name: 'Submitted',
			code: "Submitted"
		}];
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

		$scope.subReports = [{
			id: 1,
			name: 'Trade Wise Hours',
			code: 'trade'
		}, {
			id: 2,
			name: 'Cost Code Wise Hours',
			code: 'costCode'
		},
		{
			id: 3,
			name: 'Company Wise Hours',
			code: 'company'
		}];
		$scope.subReport = null;
		$scope.updateReqApprDetails = function (tab) {
			$scope.reqUserId = tab.reqUserId;
			$scope.apprUserId = tab.apprUserId;
		};

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.timeSheetSearchReq.projectLabelKeyTO = data.searchProject;
				$scope.getTimeSheetWeekCommenceDay($scope.timeSheetSearchReq.projectLabelKeyTO.projId);
				$scope.projIds.push($scope.timeSheetSearchReq.projectLabelKeyTO.projId);
				$scope.getMultiProjCostMap($scope.projIds);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getMultiProjCostMap = function (projId) {
			var req = {
				"projIds": projId
			};
			ProjCostCodeService.getMultiProjCostMap(req).then(function (data) {
				$scope.projCostItemMap = data.costCodeMap;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Cost Code Item Details", 'Error');
			});
		};


		$scope.statusChanged = function () {
			$scope.timeSheetList = [];
			for (const timesheet of timesheetListCopy) {
				if ($scope.status.code === timesheet.apprStatus) {
					$scope.timeSheetList.push(timesheet);
				}

			}
		};

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
		}
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
				"apprStatus": null,
				"apprUserId": null,
				"apprUserId": null
			}
			var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				$scope.timeSheetSearchReq.crewLabelKeyTO = data.projCrewTO;
				//crewLabelKeyTO.code = data.projCrewTO.code;
				$scope.timeSheetDetails = [];
				$scope.timeSheetList = [];
				$scope.getTimeSheets($scope.crew.id, $scope.timeSheetSearchReq);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			});
		};

		$scope.selectTimesheet = function (timeSheetLabelKeyTo) {
			$scope.timeSheetDetails = [];
			$scope.timeSheetSearchReq.additional = $scope.timeSheetSearchReq.timeSheetLabelKeyTo.additional;
			if ($scope.timeSheetSearchReq.additional == 1) {
				$scope.projWeekStartNo = 0;
				$scope.projWeekEndNo = 6;
			}
			$scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
		};

		$scope.getIndividualTimeSheetsUsers = function (timeSheetSearchReq) {
			$scope.timeSheetSearchReq.crewLabelKeyTO = {};
			var selectedUser = TimeSheetIndividualUsersFactory.getIndividualEmpsFromTimeSheet(timeSheetSearchReq, $scope.empRegMap);
			selectedUser.then(function (data) {
				timeSheetSearchReq.timesheetUserLabelKeyTO.id = data.userLabelKeyTO.id;
				timeSheetSearchReq.timesheetUserLabelKeyTO.name = data.userLabelKeyTO.name;
				$scope.timeSheetDetails = [];
				$scope.timeSheetList = [];
				$scope.getTimeSheets($scope.crew.id, $scope.timeSheetSearchReq);
			});
		};

		$scope.getTimeSheets = function (crewTypeId, timeSheetSearchReq) {
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
		};
		$scope.getCrewTimeSheets = function (timeSheetSearchReq) {
			var timeSheetGetReq = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId": timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay": formatCommenceDay(timeSheetSearchReq.weekCommenceDay),
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate
			};
			TimeSheetService.getCrewTimeSheets(timeSheetGetReq).then(function (data) {
				//$scope.timeSheetList = data.timeSheetTOs;
				timesheetListCopy = data.timeSheetTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		};
		$scope.getIndividualTimeSheets = function (timeSheetSearchReq) {
			var str = timeSheetSearchReq.weekCommenceDay.split(" ");
			var weekcommence = str[1];
			//console.log(weekday);
			$scope.timeSheetSearchReq.crewLabelKeyTO = {};
			var timeSheetGetReq = {
				"status": 1,
				"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				"empId": timeSheetSearchReq.timesheetUserLabelKeyTO.id,
				"weekCommenceDay": weekcommence,
				"weekStartDate": timeSheetSearchReq.weekStartDate,
				"weekEndDate": timeSheetSearchReq.weekEndDate
			};
			TimeSheetService.getIndividualTimeSheets(timeSheetGetReq).then(function (data) {
				//$scope.timeSheetList = data.timeSheetTOs;
				timesheetListCopy = data.timeSheetTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		};

		$scope.getTimeSheetWeekCommenceDay = function (projId) {
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
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Time Sheet Week Commence day ", 'Error');
			});
		};
		$scope.$watch('timeSheetSearchReq.weekCommenceDay', function () {
			if ($scope.timeSheetSearchReq.weekCommenceDay != null) {
				// $scope.getTimeSheetWeekDays($scope.timeSheetSearchReq);
			}
		});
		$scope.getTimeSheetWeekDays = function (timeSheetSearchReq) {
			var req = {
				"weekCommenceDay": timeSheetSearchReq.weekCommenceDay,
				"weekStartNo": $scope.projWeekStartNo,
				"weekEndNo": $scope.projWeekEndNo
			}

			const weekDays = TimeSheetService.getTimeSheetDays(req);
			timeSheetSearchReq.weekStartDate = weekDays.weekStartDate;
			timeSheetSearchReq.weekEndDate = weekDays.weekEndDate;
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
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		};

		$scope.getTimeSheetDetails = function (crewTypeId, timeSheetSearchReq) {
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
		};

		$scope.getCrewTimeSheetDetails = function (timeSheetSearchReq) {
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
			TimeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq).then(function (data) {
				$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
				$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;

				if ($scope.timeSheetSearchReq.apprStatus == null) {
					$scope.enableTimeSheet = true;
				}
				if ($scope.timeSheetDetails.length <= 0) {
					GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		};

		$scope.getIndividualTimeSheetDetails = function (timeSheetSearchReq) {
			$scope.timeSheetSearchReq.crewLabelKeyTO = {
				"id": null,
				"code": null,
				"name": null
			}
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
			TimeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq).then(function (data) {
				$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
				$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
				if ($scope.timeSheetSearchReq.apprStatus == null) {
					$scope.enableTimeSheet = true;
				}
				if ($scope.timeSheetDetails.length <= 0) {
					GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');

				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		};

		$scope.updateTotal = function (workDetails) {
			workDetails.total = 0;
			if (workDetails.total != undefined || workDetails.total != NaN || workDetails.total != null) {
				workDetails.total = (parseFloat(workDetails.day1Hrs) || 0) + (parseFloat(workDetails.day2Hrs) || 0) + (parseFloat(workDetails.day3Hrs) || 0) + (parseFloat(workDetails.day4Hrs) || 0) + (parseFloat(workDetails.day5Hrs) || 0) + (parseFloat(workDetails.day6Hrs) || 0) + (parseFloat(workDetails.day7Hrs) || 0);
				return workDetails.total;
			}
		};

		$scope.changeSubReport = function () {
			const subReportMap = [];
			$scope.subReportData = [];
			if ($scope.subReport.code == 'trade') {
				$scope.tableHeading = 'Trade';
				if ($scope.subReport.code == "trade") {
					let overAllData = [
						{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" },
						{ field: 'value', displayName: "Total Used Hours", headerTooltip: "Total Used Hours" },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_TimeSheetsSearch&ItsRecords_TradeWiseHours");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				for (const timeSheet of $scope.timeSheetData) {
					if (!subReportMap[timeSheet.empRegId]) {
						subReportMap[timeSheet.empRegId] = {
							'code': timeSheet.empDetailLabelKeyTO.displayNamesMap.className,
							'value': 0
						};
					}
					for (const workDetails of timeSheet.timeSheetEmpWorkDtlTOs) {
						if (workDetails.apprStatus === $scope.status.code) {
							!workDetails.total && $scope.updateTotal(workDetails);
							subReportMap[timeSheet.empRegId].value += workDetails.total;
						}
					}
				}
			}
			else if ($scope.subReport.code == 'costCode') {
				if ($scope.subReport.code == "costCode") {
					let overAllData = [
						{ field: 'parentCode', displayName: "Sub Group ID", headerTooltip: "Sub Group ID" },
						{ field: 'parentName', displayName: "Sub Group ID Name", headerTooltip: "Sub Group ID Name" },
						{ field: 'code', displayName: "Item ID", headerTooltip: "Item ID" },
						{ field: 'name', displayName: "Item Name", headerTooltip: "Item Name" },
						{ field: 'value', displayName: "Total Used Hours", headerTooltip: "Total Used Hours" },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_TimeSheetsSearch&ItsRecords_CostCodeWiseHours");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				for (const timeSheet of $scope.timeSheetData) {
					for (const workDetails of timeSheet.timeSheetEmpWorkDtlTOs) {
						if (workDetails.apprStatus === $scope.status.code) {
							if (!subReportMap[workDetails.costId]) {
								subReportMap[workDetails.costId] = {
									'code': workDetails.costCode,
									'value': 0,
									'name': workDetails.costCodeName,
									'parentCode': workDetails.costCodeParent,
									'parentName': workDetails.costCodeParentName
								};
							}
							!workDetails.total && $scope.updateTotal(workDetails);
							subReportMap[workDetails.costId].value += workDetails.total;
						}
					}
				}
			} else if ($scope.subReport.code == 'company') {
				$scope.tableHeading = 'Company';
				if ($scope.subReport.code == "company") {
					let overAllData = [
						{ field: 'code', displayName: "Company Name", headerTooltip: "Company Name" },
						{ field: 'value', displayName: "Total Used Hours", headerTooltip: "Total Used Hours" },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_TimeSheetsSearch&ItsRecords_CompanyWiseHours");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				for (const timeSheet of $scope.timeSheetData) {
					if (!subReportMap[timeSheet.empDetailLabelKeyTO.displayNamesMap.cmpCatgName]) {
						subReportMap[timeSheet.empDetailLabelKeyTO.displayNamesMap.cmpCatgName] = {
							'code': timeSheet.empDetailLabelKeyTO.displayNamesMap.cmpCatgName,
							'value': 0
						};
					}
					for (const workDetails of timeSheet.timeSheetEmpWorkDtlTOs) {
						if (workDetails.apprStatus === $scope.status.code) {
							!workDetails.total && $scope.updateTotal(workDetails);
							subReportMap[timeSheet.empDetailLabelKeyTO.displayNamesMap.cmpCatgName].value += workDetails.total;
						}
					}
				}
			}

			$scope.labels = [];
			$scope.data = [];
			let obj = null;
			for (const key in subReportMap) {
				obj = subReportMap[key];
				$scope.labels.push(obj.code);
				$scope.data.push(obj.value);
				$scope.subReportData.push(obj);
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.type = 'chartTable';
			initGraph();
		};

		function initGraph() {
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		}

		$scope.clear = function () {
			// $scope.status = null;
			$scope.timeSheetSearchReq.crewLabelKeyTO.code = null;
			$scope.timeSheetSearchReq.timeSheetLabelKeyTo = null;
		}

		$scope.clearProject = function() {
			$scope.timeSheetSearchReq.weekCommenceDay = null;
			$scope.timeSheetSearchReq.crewLabelKeyTO.code = null;
			$scope.timeSheetSearchReq.timeSheetLabelKeyTo = null;
		}
		
		$scope.clearCrew = function () {
			$scope.status = undefined;
			$scope.timeSheetSearchReq.timeSheetLabelKeyTo = null;
			$scope.timeSheetDetails = [];
			$scope.timeSheetSearchReq = {};
		}

		$scope.getTimeSheetDailyReport = function () {
			if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
				GenericAlertService.alertMessage("Please select project", 'Warning');
				return;
			}
			if ($scope.timeSheetSearchReq.weekCommenceDay == null) {
				GenericAlertService.alertMessage("Please select Commencing Day", 'Warning');
				return;
			}
			/*if ($scope.timeSheetSearchReq.crewLabelKeyTO.code == null || $scope.timeSheetSearchReq.crewLabelKeyTO.code == undefined) {
				GenericAlertService.alertMessage("Please select crew", 'Warning');
				return;
			}*/
			if ($scope.status == null) {
				GenericAlertService.alertMessage("Please select Status", 'Warning');
				return;
			}
			if (timesheetListCopy.length <= 0) {
				GenericAlertService.alertMessage("No Time sheet available for dates & crew", 'Warning');
				return;
			}
			// if (timesheetListCopy.length > 0) {
			// 	GenericAlertService.alertMessage("Please select Time sheet", 'Warning');
			// 	return;
			// }
			if ($scope.timeSheetSearchReq.timeSheetLabelKeyTo.code == null || $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code == undefined || $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code == '') {
				GenericAlertService.alertMessage("Please select Time sheet", 'Warning');
				return;
			}
			if ($scope.timeSheetSearchReq.crewLabelKeyTO.id != null) {
				/* var getTimeSheetDailyReq = {
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
					"weekCommenceDay": formatCommenceDay($scope.timeSheetSearchReq.weekCommenceDay),
					"selectType": $scope.crew.name,
					"selectedTypeIds": [$scope.timeSheetSearchReq.crewLabelKeyTO.id],
					"apprStatus": [$scope.status.code],
					"timeSheetIds": [$scope.timeSheetList[0].code],
					"originatorIds": [$scope.timeSheetList[0].reqUserId],
					"approverIds": [$scope.timeSheetList[0].apprUserId],
					"subReportType": $scope.subReportCode
				} */

				var getTimeSheetDailyReq = {
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
					"weekCommenceDay": formatCommenceDay($scope.timeSheetSearchReq.weekCommenceDay),
					"selectType": $scope.crew.name,
					"crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id,
					'status': 1,
					"apprStatus": $scope.status.code,
					"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
					"subReportType": $scope.subReportCode,
					"additional": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.additional,
					"weekEndDate": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.weekEndDate,
					"weekStartDate": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.weekStartDate
				}

			} else {
				var getTimeSheetDailyReq = {
					"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
					"weekCommenceDay": formatCommenceDay($scope.timeSheetSearchReq.weekCommenceDay),
					"selectType": $scope.crew.name,
					"selectedTypeIds": [$scope.timeSheetSearchReq.timesheetUserLabelKeyTO.id],
					"apprStatus": $scope.status.code,
					"timeSheetIds": [$scope.timeSheetList[0].code],
					"originatorIds": [$scope.timeSheetList[0].reqUserId],
					"approverIds": [$scope.timeSheetList[0].apprUserId],
					"subReportType": $scope.subReportCode,
					"weekEndDate": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.weekEndDate,
					"weekStartDate": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.weekStartDate
				}
			}
			TimeSheetReportService.getTimeSheetDailyReport(getTimeSheetDailyReq).then(function (data) {
				$scope.timeSheetData = data.timeSheetEmpDtlTOs;
				if ($scope.subReport && $scope.subReport.code) {
					$scope.changeSubReport();
				}
				if ($scope.timeSheetData.length <= 0) {
					GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		};

		$scope.clearSubReportDetails = function () {
			$scope.timeSheetDetails = [];
			$scope.type = '';
		};

		$scope.getTimesheetEmpExpenses = function (empObj) {
			TimesheetEmpPopupFactory.getTimeSheetEmpDetails(null, $scope.timeSheetSearchReq, empObj, null);
		}

		$scope.resetTimeSheetDetails = function () {
			$scope.timeSheetData = [];
			$scope.timeSheetList = [];
			$scope.daysList = [];
			$scope.data = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.crewNameDisplay = null;
			$scope.timeSheetSearchReq = {};
			$scope.timeSheetList = [];
			$scope.status = null;
			$scope.subReport = null;
			$scope.crew = $scope.crews[0];
		}

		function formatCommenceDay(weekCommenceDay) {
			return $filter('date')(new Date(weekCommenceDay), "dd-MMM-yyyy");
		}

		$scope.crew = $scope.crews[0];
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
			template: 'views/help&tutorials/reportshelp/timesheetshelp/timesheetsearchhelp.html',
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