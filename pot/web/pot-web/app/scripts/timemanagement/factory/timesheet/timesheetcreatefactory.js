'use strict';
app.factory('TimeSheetCreateFactory', ["ngDialog", "$q", "$filter", "blockUI", "TimeSheetService", "TimesheetCopyFactory", "TimeSheetCostCodeFactory", "WageFactory", "TimeSheetEmpRegFactory", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, TimeSheetService, TimesheetCopyFactory, TimeSheetCostCodeFactory, WageFactory, TimeSheetEmpRegFactory, $timeout,
		$rootScope, GenericAlertService) {
	var service = {};
	service.createTimesheet = function(maxHrs, crewTypeId, additional, timeSheetEmpMap, timeSheetSearchReq, timeSheetDays, empRegMap, costCodeMap, empWageFactorMap,
			projWeekStartNo) {
		var deferred = $q.defer();
		var servicePopup = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetcreatepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.timeSheetSearchReq = timeSheetSearchReq;
				$scope.crewTypeId = crewTypeId;
				$scope.additional = additional;
				$scope.empRegLabelKeyTOs = [];
				$scope.timeSheetDetails = [];
				$scope.empRegMap = empRegMap;
				$scope.selectAll = false;
				$scope.costcodeMap = costCodeMap;
				$scope.empWageFactorMap = empWageFactorMap;
				$scope.timeSheetDays = timeSheetDays;
				$scope.projWeekStartNo = projWeekStartNo;
				var selectedEmpRegTOs = [];

				$scope.empSelect = function(emptimesheet) {
					if (emptimesheet.select) {
						selectedEmpRegTOs.push(emptimesheet);
					} else {
						selectedEmpRegTOs.splice(selectedEmpRegTOs.indexOf(emptimesheet), 1);
					}
				}, $scope.selectAllEmps = function() {
					if ($scope.selectAll) {
						angular.forEach($scope.timeSheetDetails, function(value, key) {
							value.select = true;
							selectedEmpRegTOs.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.timeSheetDetails, function(value, key) {
							value.select = false;
						});
						selectedEmpRegTOs = [];
					}
				}, $scope.getTimeSheetDetails = function(crewTypeId, timeSheetSearchReq) {
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
						$scope.getIndividualTimeSheetDetails(timeSheetSearchReq);
					}
				}, $scope.getCrewTimeSheetDetails = function(timeSheetSearchReq) {
					var timeSheetGetReq = {
						"status" : 1,
						"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
						"weekCommenceDay" :  $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
						"weekStartDate" : timeSheetSearchReq.weekStartDate,
						"weekEndDate" : timeSheetSearchReq.weekEndDate,
						"additional" : additional,
						"apprStatus" : null
					};
					var timeSheetEmpMap = [];
					TimeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq).then(function(data) {
						angular.forEach(data.timeSheetEmpDtlTOs, function(value, key) {
							if (timeSheetEmpMap[value.empRegId] == null) {
								timeSheetEmpMap[value.empRegId] = true;
								$scope.timeSheetDetails.push(value);
							}
						});
					});
				}, $scope.getIndividualTimeSheetDetails = function(timeSheetSearchReq) {
					var timeSheetGetReq = {
						"status" : 1,
						"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
						"empId" : timeSheetSearchReq.timesheetUserLabelKeyTO.id,
						"weekCommenceDay" : $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
						"weekStartDate" : timeSheetSearchReq.weekStartDate,
						"weekEndDate" : timeSheetSearchReq.weekEndDate,
						"additional" : additional,
						"apprStatus" : null
					};
					TimeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq).then(function(data) {
						angular.forEach(data.timeSheetEmpDtlTOs, function(value, key) {
							if (timeSheetEmpMap[value.empRegId] == null) {
								timeSheetEmpMap[value.empRegId] = true;
								$scope.timeSheetDetails.push(value);
							}
						});
						//$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Individual TimeSheet Details", 'Error');
					});
				}, $scope.getEmpRegDetails = function(crewId) {
					var req = {
						"status" : 1,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"crewId" : crewId,
						"fromCrewId" : timeSheetSearchReq.crewLabelKeyTO.id,
						"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
						"weekStartDate" : timeSheetSearchReq.weekStartDate,
						"weekEndDate" : timeSheetSearchReq.weekEndDate
					};
					var empPopup = TimeSheetService.getOtherCrewEmpAttendance(req);
					empPopup.then(function(data) {
						$scope.empRegLabelKeyTOs = data.empRegLabelKeyTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting empployee details", 'Error');
					});
				}, $scope.addEmpRegDetails = function(timeSheetSearchReq) {
					var empExistingMap = [];
					angular.forEach($scope.timeSheetDetails, function(value, key) {
						empExistingMap[value.empRegId] = true;
					});
					var empRegServicePopup = TimeSheetEmpRegFactory.openEmpRegPopup(timeSheetSearchReq, crewTypeId, empExistingMap);
					empRegServicePopup.then(function(data) {
						angular.forEach(data.timeSheetEmpDtlTOs, function(value, key) {
							$scope.timeSheetDetails.push(value);
						});
						timeSheetSearchReq.timeSheetId = data.timeSheetTO.id;
						timeSheetSearchReq.code = data.timeSheetTO.code;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
					});
				}, $scope.copyTimeSheetEmpRegDetails = function() {
					var empExistingMap = [];
					angular.forEach($scope.timeSheetDetails, function(value, key) {
						empExistingMap[value.empRegId] = true;
					});

					var popup = TimesheetCopyFactory.copyTimeSheetEmpRegDetails($scope.projWeekStartNo, $scope.timeSheetSearchReq, timeSheetEmpMap, empExistingMap,crewTypeId, $scope.timeSheetDetails);
					popup.then(function(data) {
						console.log(data)
						$scope.timeSheetDetails = data;
						$scope.selectAllEmps();
						/*angular.forEach(data, function(value, key) {
							$scope.timeSheetDetails = data;
					//		$scope.timeSheetDetails.push(value);
							$scope.selectAllEmps();
						});*/
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
					})
					
				}, $scope.selectCostCode = function(workDetails) {
					if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to Add Progress", 'Warning');
						return;
					}
					blockUI.start();
					var costCodepopup = TimeSheetCostCodeFactory.getCostCodeDetails($scope.timeSheetSearchReq);
					costCodepopup.then(function(data) {
						blockUI.stop();
						workDetails.costId = data.projCostItemTO.id;
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
					})

				}, $scope.getWageFactor = function(workDetails) {
					var getReq = {
						"status" : 1
					};
					var wagePopup = [];
					blockUI.start();
					wagePopup = WageFactory.wageFactorDetailsList(getReq);
					wagePopup.then(function(data) {
						blockUI.stop();
						workDetails.empWageId = data.employeeWageRateTO.wageRateId;
						workDetails.wageFactor = data.employeeWageRateTO.wageFactor;
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occured while selecting WageFactor Details", 'Error');
					});
				}, $scope.saveTimeSheetDetails = function(crewTypeId) {
					if (crewTypeId == 1) {
						$scope.saveCrewTimeSheetDetails();
					} else if (crewTypeId == 2) {
						$scope.saveIndividualTimeSheetDetails();
					}

				}, $scope.saveCrewTimeSheetDetails = function() {
					if (selectedEmpRegTOs.length <= 0) {
						GenericAlertService.alertMessage("please add atleast one employee to save  ", "Warning");
						return;
					}
					var saveEmpReq = {
						"timeSheetEmpDtlTOs" : selectedEmpRegTOs,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
							"crewId" : $scope.timeSheetSearchReq.crewLabelKeyTO.id,
							"weekCommenceDay" : $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
							"apprStatus" : null,
							"additional" : additional,
							"weekStartDate" : $scope.timeSheetSearchReq.weekStartDate,
							"weekEndDate" : $scope.timeSheetSearchReq.weekEndDate,
							"maxHrs" : maxHrs
						},
						"status" : 1
					};
					console.log(saveEmpReq)
					blockUI.start();
					TimeSheetService.saveCrewTimeSheetDetails(saveEmpReq).then(function(data) {
						blockUI.stop();
						if (data.status == "Warning") {
							GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
						} else {
							var resultData = GenericAlertService.alertMessageModal('Employee(s) saved successfully', "Info");
							$scope.closeThisDialog();
							resultData.then(function() {
								deferred.resolve(data);
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Emp(s) popup are failed to save', "Error");
					});
				}, $scope.saveIndividualTimeSheetDetails = function() {
					var saveEmpReq = {
						"timeSheetEmpDtlTOs" : $scope.timeSheetDetails,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : $scope.timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : $scope.timeSheetSearchReq.projectLabelKeyTO.projId,
							"empId" : $scope.timeSheetDetails[0].empRegId,
							"weekCommenceDay" : $filter('date')(new Date($scope.timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
							//"weekCommenceDay" : $scope.timeSheetSearchReq.weekCommenceDay,
							"apprStatus" : null,
							"additional" : additional,
							"weekStartDate" : $scope.timeSheetSearchReq.weekStartDate,
							"weekEndDate" : $scope.timeSheetSearchReq.weekEndDate,
							"maxHrs" : maxHrs
						},
						"status" : 1
					};
					console.log(saveEmpReq);
					blockUI.start();
					TimeSheetService.saveIndividualTimeSheetDetails(saveEmpReq).then(function(data) {
						blockUI.stop();
						if (data.status == "Warning") {
							GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
							return;
						} else {
							var resultData = GenericAlertService.alertMessageModal('Employee(s) saved successfully', "Info");
							$scope.closeThisDialog();
							resultData.then(function() {
								deferred.resolve(data);
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Emp(s) popup are failed to save', "Error");
					});
				}, $scope.submitTimeSheet = function() {
					if ($scope.timeSheetSearchReq.projectLabelKeyTO.projId == undefined || $scope.timeSheetSearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to submit", 'Warning');
						return;
					}
					var submitpopup = TimesheetSubmitFactory.getTimeSheetSubmitDetails($scope.crew, $scope.timeSheetSearchReq, $scope.timeSheetDetails);
					submitpopup.then(function(data) {

						$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
						console.log($scope.timeSheetDetails)
						timeSheetSearchReq.timeSheetId = data.timeSheetTO.id;
						timeSheetSearchReq.code = data.timeSheetTO.code;
						GenericAlertService.alertMessage('Employee(s) saved successfully', "Info");
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
					})

				}, $scope.deleteEmps = function() {
					if (selectedEmpRegTOs.length <= 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

					} else

					if (selectedEmpRegTOs.length < $scope.timeSheetDetails.length) {
						angular.forEach(selectedEmpRegTOs, function(value, key) {
							$scope.timeSheetDetails.splice($scope.timeSheetDetails.indexOf(value), 1);

						});
						selectedEmpRegTOs = [];

					} else if (selectedEmpRegTOs.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedEmpRegTOs.length <= 1) {
						selectedEmpRegTOs = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
