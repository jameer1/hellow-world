'use strict';

app.factory('ApproveTimeSheetMoreDetailFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "GenericAlertService", "generalservice", "TimeSheetService", "ProjectSettingsService", "TimesheetApproveFactory", "TimeSheetNotificationFactory", function(ngDialog, $q, $filter, $timeout, blockUI, GenericAlertService, generalservice, TimeSheetService, ProjectSettingsService, TimesheetApproveFactory, TimeSheetNotificationFactory) {
	var popup;
	var service = {};
	service.openPopup = function(timeSheetTO) {
		var deferred = $q.defer();
		popup = ngDialog.open({
			template : 'views/timemanagement/timesheet/approvetimesheet/timesheetmoredetail.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.timeSheetSearchReq = {
					"crewLabelKeyTO": {
						"id": timeSheetTO.crewId,
						"code": timeSheetTO.crewName,
						"name": null
					},
					"projectLabelKeyTO": {
						"projId": timeSheetTO.projId,
						"parentName": timeSheetTO.parentName,
						"projName": timeSheetTO.name
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
						"id": timeSheetTO.id,
						"code": timeSheetTO.timeSheetCode,
						"apprStatus": timeSheetTO.apprStatus
					},
					"maxHrs": null,
					"weekCommenceDay": $filter('date')(new Date(timeSheetTO.weekStartDate), "EEE dd-MMM-yyyy"),
					"weekStartDate": timeSheetTO.weekStartDate,
					"weekEndDate": timeSheetTO.weekEndDate,
					"additional": null,
					"apprStatus": timeSheetTO.apprStatus
				};
				if(timeSheetTO.crewId != null){
					$scope.crewOrIndividual = {
					id: 1,
					name: 'Team',
					desc: ''
				    };
				} else {
					$scope.crewOrIndividual = {
						id: 2,
						name: 'Individual',
						desc: ''
					};
				}	
				$scope.crew = $scope.crewOrIndividual;
				TimeSheetService.getProjSettingsForTimeSheet({"status": 1, "projId": timeSheetTO.projId}).then(function (data) {
					$scope.weekSeqId = data.weekSeqId;
					$scope.projWeekStartNo = data.weeekStartDay;
					$scope.projWeekEndNo = data.weeekEndDay;
					$scope.maxHrs = data.maxHrs;
					ProjectSettingsService.getProjCrewLists({"status": 1, "projId": timeSheetTO.projId}).then(function(data) {
						var index = data.projCrewTOs.findIndex(projCrewTO => projCrewTO.id == timeSheetTO.crewId);
						$scope.timeSheetSearchReq.crewLabelKeyTO = data.projCrewTOs[index];

						const startDate = new Date($scope.timeSheetSearchReq.weekCommenceDay);
						startDate.setDate(startDate.getDate() + ($scope.projWeekEndNo - $scope.projWeekStartNo));
						$scope.timeSheetSearchReq.weekStartDate = new Date($scope.timeSheetSearchReq.weekCommenceDay).getTime();
						$scope.timeSheetSearchReq.weekEndDate = startDate.getTime();
						TimeSheetService.getTimeSheetOnload({"status": 1, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "weekCommenceDay": $scope.timeSheetSearchReq.weekCommenceDay, "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate}).then(function (data) {
							$scope.timeSheetDays = data.timeSheetDays;
							$scope.costcodeMap = data.costCodeMap;
							$scope.empWageFactorMap = data.empWageFactorMap;
							$scope.empRegMap = data.empRegMap;
							if ($scope.crewOrIndividual.id == 1) {
								TimeSheetService.getCrewTimeSheetForApproval({"status": 1, "timeSheetId": null, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate, "additional": 0, "apprStatus": 'Submitted'}).then(function (data) {
									$scope.timeSheetList = data.timeSheetTOs;
									/*if ($scope.timeSheetList.length <= 0) 
										GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');*/
									TimeSheetService.getCrewTimeSheetDetailForApproval({"status": 1, "timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate, "additional": $scope.timeSheetSearchReq.additional, "apprStatus": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus}).then(function (data) {
										$scope.timeSheetSearchReq.apprUserId = data.timeSheetEmpDtlTOs.apprUserId;
										$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
										$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
										if ($scope.timeSheetDetails.length <= 0) 
											GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
									}, function (error) {
										GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
									});
								}, function (error) {
									GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
								});
							} else if ($scope.crewOrIndividual.id == 2) {
								TimeSheetService.getIndividualTimeSheetForApproval({"status": 1, "timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "empId": timeSheetTO.empId, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate, "additional": 0, "apprStatus": 'Submitted'}).then(function (data) {
									$scope.timeSheetList = data.timeSheetTOs;
									if ($scope.timeSheetList.length <= 0) 
										GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
	                                    var req = {"status": 1, "timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate, "additional": 0, "apprStatus": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus}
										TimeSheetService.getIndividualTimeSheetDetailForApproval(req).then(function (data) {
											$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
											$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
											if ($scope.timeSheetDetails.length <= 0) 
												GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
										}, function (error) {
											GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
										});
								}, function (error) {
									GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
								});
							}
							$scope.getProjSettingsTimeSheetDetails();
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Crew List Details", "Error");
					});
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Time Sheet Week Commence day ", 'Error');
				});
				$scope.total = 0;
				$scope.updateTotal = function (workDetails) {
					workDetails.total = $scope.total;
					if (workDetails.total != undefined || workDetails.total != NaN || workDetails.total != null) {
						workDetails.total = (parseFloat(workDetails.day1Hrs) || 0) + (parseFloat(workDetails.day2Hrs) || 0) + (parseFloat(workDetails.day3Hrs) || 0) + (parseFloat(workDetails.day4Hrs) || 0) + (parseFloat(workDetails.day5Hrs) || 0) + (parseFloat(workDetails.day6Hrs) || 0) + (parseFloat(workDetails.day7Hrs) || 0);
						return workDetails.total;
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
				$scope.getTimeSheetNotificationsForApprove = function (projId) {
			console.log(projId);
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
				$scope.selectCostCode = function (workDetails) {
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
				},
				$scope.getWageFactor = function (workDetails) {
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
				},
				$scope.deleteEmpReg = function (selectedEmp, workDetails) {
					selectedEmp.timeSheetEmpWorkDtlTOs.pop(workDetails);
				},
				$scope.approveTimeSheet = function () {
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
						$scope.getProjSettingsTimeSheetDetails();
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while approving", 'Error');
					})
				},
				$scope.saveTimeSheetDetails = function (crewTypeId) {
					$scope.buttonFlag = false;
					if (crewTypeId == 1) {
						$scope.saveCrewTimeSheetDetails();
					} else if (crewTypeId == 2) {
						$scope.saveIndividualTimeSheetDetails();
					}
				},
				$scope.saveCrewTimeSheetDetails = function () {
					var req = {
						"timeSheetEmpDtlTOs": $scope.timeSheetDetails,
						"timeSheetTO": {
							"status": 1,
							"id": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
							"crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id,
							"weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
							"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
							"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
							"maxHrs": $scope.maxHrs,
							"apprUserId": timeSheetTO.apprUserId,
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
				},
				$scope.saveIndividualTimeSheetDetails = function () {
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
				},
				$scope.seletTimeSheetEmpDetails = function () {
					if ($scope.selectedEmp == undefined || $scope.selectedEmp == null) {
						GenericAlertService.alertMessage("Please select employee to add tasks/expenses", 'Warning');
						return;
					}
					TimesheetApproveEmpPopupFactory.getTimeSheetEmpDetails($scope.empRegMap, $scope.timeSheetSearchReq, $scope.selectedEmp, $scope.costcodeMap);
				},
				$scope.rowSelectEmpReg = function (empObj, indexValue) {
					$scope.selectedEmp = empObj;
					$scope.selectedRow = indexValue;
				},
				$scope.timeFlag = false;
				$scope.getProjSettingsTimeSheetDetails = function () {
					var req = {
						"status": 1,
						"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
						"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
						"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
						"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
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
				}		
			}]
		});
		return deferred.promise;
	};
	return service;
}]);