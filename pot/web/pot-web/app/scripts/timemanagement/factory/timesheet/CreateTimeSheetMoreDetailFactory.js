'use strict';

app.factory('CreateTimeSheetMoreDetailFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "TimeSheetNotificationFactory", "GenericAlertService", "generalservice", "TimeSheetService", "ProjectSettingsService", "WageFactory", "TimeSheetCostCodeFactory", "TimeSheetEmpRegFactory", "TimesheetSubmitFactory", "TimesheetEmpPopupFactory", function(ngDialog, $q, $filter, $timeout, blockUI, TimeSheetNotificationFactory, GenericAlertService, generalservice, TimeSheetService, ProjectSettingsService, WageFactory, TimeSheetCostCodeFactory, TimeSheetEmpRegFactory, TimesheetSubmitFactory, TimesheetEmpPopupFactory) {
	var popup;
	var service = {};
	service.openPopup = function(timeSheetTO) {
		var deferred = $q.defer();
		popup = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetmoredetail.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.flag = false;
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
					"approverTo": {
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
				if ( timeSheetTO.crewId != null) {
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
								TimeSheetService.getCrewTimeSheets({"status": 1,  "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate}).then(function (data) {
									$scope.timeSheetTOs = data.timeSheetTOs;	
									TimeSheetService.getCrewTimeSheetDetailsForSubmission({"status":  1, "timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate, "additional": $scope.timeSheetSearchReq.additional, "apprStatus": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus}).then(function (data) {
										$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
										$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
										$scope.timeSheetSearchReq.additional = data.timeSheetTO.additional;
										if ($scope.timeSheetSearchReq.apprStatus == null) 
											$scope.enableTimeSheet = true;
										if ($scope.timeSheetDetails.length <= 0) 
											GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
									}, function (error) {
										GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
									});
								}, function (error) {
									GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
								});
							} else if ($scope.crewOrIndividual.id == 2) {
								TimeSheetService.getIndividualTimeSheets({"status": 1, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "empId": timeSheetTO.empId, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate}).then(function (data) {
									$scope.timeSheetTOs = data.timeSheetTOs;
									TimeSheetService.getIndividualTimeSheetDetailsForSubmission({"status": 1, "timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id, "projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId, "empId": timeSheetTO.empId, "weekCommenceDay": $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), "weekStartDate": $scope.timeSheetSearchReq.weekStartDate, "weekEndDate": $scope.timeSheetSearchReq.weekEndDate, "additional": $scope.timeSheetSearchReq.additional, "apprStatus": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.apprStatus}).then(function (data) {
										$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
										$scope.timeSheetSearchReq.timesheetUserLabelKeyTO.id = data.timeSheetEmpDtlTOs[0].empRegId;
										$scope.timeSheetSearchReq.timesheetUserLabelKeyTO.name = data.timeSheetEmpDtlTOs[0].empDetailLabelKeyTO.displayNamesMap.firstName;
										$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
										$scope.timeSheetSearchReq.additional = data.timeSheetTO.additional;
										if ($scope.timeSheetSearchReq.apprStatus == null) {
											$scope.enableTimeSheet = true;
										}
										if ($scope.timeSheetDetails.length <= 0) {
											GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
										}
									}, function (error) {
										GenericAlertService.alertMessage("Error occured while getting Individual TimeSheet Details", 'Error');
									});
								}, function (error) {
									GenericAlertService.alertMessage("Error occured while getting Individual TimeSheet Details", 'Error');
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
				}, $scope.checkDecimal = function (costObj, dayHrs, maxHrs) {
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
				},
				$scope.getWageFactor = function (workDetails, timeSheetEmpWorkDtlTOs) {
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
				$scope.deleteEmpReg = function (selectedEmp, workDetails) {
					selectedEmp.timeSheetEmpWorkDtlTOs.pop(workDetails);
				},
				$scope.timeFlag = false;
				$scope.getProjSettingsTimeSheetDetails = function () {
					if ($scope.crewOrIndividual.id == 1) {
						var req = {
							"status": 1,
							"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
							"crewId": $scope.timeSheetSearchReq.crewLabelKeyTO.id,
							"empId": timeSheetTO.reqUserId,
							"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
							"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
							"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"apprStatus": 'Submission'
						};
					} else if ($scope.crewOrIndividual.id == 2) {
						var req = {
							"status": 1,
							"projId": $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
							"empId": timeSheetTO.empId,
							"weekStartDate": $scope.timeSheetSearchReq.weekStartDate,
							"weekEndDate": $scope.timeSheetSearchReq.weekEndDate,
							"timeSheetId": $scope.timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"apprStatus": 'Submission'
						};
					}
					
					console.log("$scope.timeSheetSearchReq");
					console.log($scope.timeSheetSearchReq);
					console.log("timeSheetTO");
					console.log(timeSheetTO);
					console.log("req");
					console.log(req);
					TimeSheetService.getProjSettingsTimeSheetDetails(req).then(function (data) {
						console.log("Project Setting Data ");
						console.log(data);
						$scope.projsettingsTimeSubmitDetails = data.labelKeyTOs[0];
						if ($scope.projsettingsTimeSubmitDetails.id <= 0) {
							$scope.timeFlag = true;
						}
					}, function (error) {
						//GenericAlertService.alertMessage("Error occured while getting projsettings time sheet details", "Error");
					});
				},
				$scope.rowSelectEmpReg = function (empObj, indexValue) {
					$scope.selectedEmp = empObj;
					$scope.selectedRow = indexValue;
				},
				$scope.getTimeSheetNotificationsForSubmit = function (projId, flag) {
					if (!flag) {
						GenericAlertService.alertMessage("Your Normal TimeSheet Hours Not Completed", 'Warning');
						return;
					}
					console.log($scope.crew.id);
					console.log($scope.timeSheetSearchReq);
					var popup = TimeSheetNotificationFactory.getTimeSheetNotificationDetails($scope.timeSheetSearchReq, $scope.crew.id);
					popup.then(function (data) {
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
					})
				},
				$scope.seletTimeSheetEmpDetails = function () {
					if ($scope.selectedEmp == undefined || $scope.selectedEmp == null) {
						GenericAlertService.alertMessage("Please select employee to add tasks/expenses", 'Warning');
						return;
					}
					TimesheetEmpPopupFactory.getTimeSheetEmpDetails($scope.empRegMap, $scope.timeSheetSearchReq, $scope.selectedEmp, $scope.costcodeMap);
				},
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
				},
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
						$scope.timeSheetDetails = [];
						$scope.timeSheetList = [];
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
				},
				 $scope.addEmpRegDetails = function (timeSheetSearchReq) {
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
				},
				$scope.saveTimeSheetDetails = function (crewTypeId) {
					$scope.buttonFlag = false;
					if ($scope.crewOrIndividual.id == 1) {
						$scope.saveCrewTimeSheetDetails();
					} else if ($scope.crewOrIndividual.id == 2) {
						$scope.saveIndividualTimeSheetDetails();
					}
					$scope.closeThisDialog();
				},
				$scope.saveCrewTimeSheetDetails = function () {
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
					//console.log(duplicateMap);
					/*if (flag) {
						GenericAlertService.alertMessage('Combination of Duplicate cost codes and wages  are not allowed', "Warning");
						blockUI.stop();
						return;
					}*/
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
							$scope.getProjSettingsTimeSheetDetails();
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
				},
				$scope.saveIndividualTimeSheetDetails = function () {
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
				},
				 $scope.submitTimeSheet = function () {
					if ($scope.buttonFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveTimeSheetDetails($scope.crew.id);
						}, function (error) {
							$scope.buttonFlag = false;
							$scope.submitTimeSheet();
							$scope.closeThisDialog();
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
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while submitting time sheet", 'Error');
					});
				},
				$scope.getTimeSheet = function (crewTypeId, timeSheetSearchReq, additional) {
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
				},
				$scope.getCrewTimeSheet = function (timeSheetSearchReq, additional) {
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
				},
				$scope.getIndividualTimeSheet = function (timeSheetSearchReq, additional) {
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
				}
			}]
		});
		return deferred.promise;
	};
	return service;
}]);