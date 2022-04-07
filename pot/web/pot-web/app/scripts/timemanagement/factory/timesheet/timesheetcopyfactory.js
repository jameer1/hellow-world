'use strict';
app.factory('TimesheetCopyFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "TimeSheetIndividualUsersFactory", "PotCommonService", "ModuleUserFactory", "ProjectCrewPopupService", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, TimeSheetIndividualUsersFactory, PotCommonService, ModuleUserFactory, ProjectCrewPopupService, TimeSheetService, GenericAlertService) {
	var service = {};
	service.copyTimeSheetEmpRegDetails = function(projWeekStartNo, timeSheetSearchReq, timeSheetEmpMap,empExistingMap,crewTypeId) {
	
		var deferred = $q.defer();
		var popUp = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/copytimesheetempdetails.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empRegLabelKeyTOs == [];
				var selectedEmpRegTOs = [];
				$scope.searchFlag = false;
				$scope.empExistingMap = empExistingMap
				$scope.timeSheetEmpMap = timeSheetEmpMap;
				$scope.projWeekStartNo = projWeekStartNo;
				$scope.crewTypeId=crewTypeId;
				$scope.selectAll = false;
				$scope.timeSheetList = [];
				$scope.maxHrs = 0;
				$scope.attendanceId = []
				$scope.weekCommenceDay = null;
				$scope.crewLabelKeyTO = {
					"id" : null,
					"code" : null,
					"name" : null

				}
				$scope.timeSheetLabelKeyTO = {
					"id" : null,
					"code" : null,
					"name" : null
				}
				var toDate = new Date(timeSheetSearchReq.weekStartDate);;
				$scope.weekCommenceDay=toDate;
				$scope.weekCommenceDay.setDate(toDate.getDate()-7);
				let resetEmp = function (empObj) {
				//	empObj.timeSheetId = null;
					empObj.id = null;
					empObj.timeSheetEmpWorkDtlTOs[0].empDtlId = null;
			//		empObj.timeSheetEmpWorkDtlTOs[0].empWageId = null;
					empObj.timeSheetEmpWorkDtlTOs[0].id = null;
				}	
				var req = {
					"status": 1,
					"projId": timeSheetSearchReq.projectLabelKeyTO.projId,
				};
				
				$scope.getEmpDetails = function() {
					var timeSheetGetReq = {
						"status" : 1,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"crewId" : $scope.crewLabelKeyTO.id,
						"fromCrewId" : null,
						"weekCommenceDay" : $filter('date')(new Date($scope.weekCommenceDay), "dd-MMM-yyyy"),
					};
					TimeSheetService.getCrewTimeSheets(timeSheetGetReq).then(function (data) {
						if (data.timeSheetTOs.length <= 0 ) {
							$scope.searchFlag = true;
						} else {
							$scope.timeSheetList = data.timeSheetTOs;
							$scope.attendanceId = $scope.timeSheetList[0].id;
							console.log($scope.timeSheetList);
							var timeSheetGetReq = {
								"status" : 1,
								"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
								"crewId" : $scope.crewLabelKeyTO.id,
								"timeSheetId" : $scope.attendanceId,
								"fromCrewId" : $scope.crewLabelKeyTO.id,
								"additional" : 0,
								"weekStartDate" : timeSheetSearchReq.weekStartDate,
								"weekEndDate" : timeSheetSearchReq.weekEndDate,
								"fromWeekStartDate" : new Date($scope.weekCommenceDay),
								"weekCommenceDay" : $filter('date')(new Date($scope.weekCommenceDay), "dd-MMM-yyyy"),
								"apprStatus": 'Under Preparation'
							};
							TimeSheetService.getCopyCrewTimeSheetDetailsForSubmission(timeSheetGetReq).then(function (data) {
								$scope.timeSheetDetails = data.timeSheetEmpDtlTOs;
								console.log($scope.timeSheetDetails);
								$scope.timeSheetSearchReq.apprStatus = data.timeSheetTO.apprStatus;
								$scope.timeSheetSearchReq.additional = data.timeSheetTO.additional;
								alert($scope.timeSheetDetails.length);
								if ($scope.timeSheetSearchReq.apprStatus == null) {
									$scope.enableTimeSheet = true;
								}
								if ($scope.timeSheetDetails.length <= 0) {
									GenericAlertService.alertMessage("Time Sheet details are not found", 'Warning');
								}
							}, function (error) {
								GenericAlertService.alertMessage("Error occured while getting TimeSheet List Details", 'Error');
							});
						}
						
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
					});
				}, $scope.selectEmp = function(empObj) {
					if(empObj.id)
					 resetEmp(empObj)
					if (empObj.select) {
						selectedEmpRegTOs.push(empObj);
					} else {
						selectedEmpRegTOs.pop(empObj);
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
				}, $scope.getIndividualTimeSheetsUsers = function(timeSheetSearchReq) {
					var selectedUser = TimeSheetIndividualUsersFactory.getIndividualEmpsFromTimeSheet(timeSheetSearchReq, $scope.empRegMap);
					selectedUser.then(function(data) {
						timeSheetSearchReq.timesheetUserLabelKeyTO.id = data.userLabelKeyTO.id;
						timeSheetSearchReq.timesheetUserLabelKeyTO.name = data.userLabelKeyTO.name;
						$scope.getTimeSheet($scope.timeSheetSearchReq);
					});
				}, 
				$scope.crewLabelKeyTO = [];
				$scope.crewLabelKeyTO = angular.copy(timeSheetSearchReq.crewLabelKeyTO);
				$scope.getCrewList = function(projId, crewLabelKeyTO) {

					var crewReq = {
						"status" : 1,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
					};

					var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
					crewSerivcePopup.then(function(data) {
						crewLabelKeyTO.id = data.projCrewTO.id;
						crewLabelKeyTO.code = data.projCrewTO.code;

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
					});
				}, $scope.addEmpRegToTimeSheet = function() {
					if ( selectedEmpRegTOs.length <= 0) {
						GenericAlertService.alertMessage("Please select  empployees to save", 'Warning');
						return;
					}
					
					if (crewTypeId == 2 && selectedEmpRegTOs.length > 1) {
						
						GenericAlertService.alertMessage("Please select only one empployee,Individual timesheet applcable for single employee", 'Warning');
						return;
					}
					console.log(selectedEmpRegTOs);
					var req = {
						"timeSheetEmpRegTOs" : selectedEmpRegTOs,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
							"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"apprStatus" : null
						}
					};
					console.log(req);
					deferred.resolve(selectedEmpRegTOs);
					$scope.closeThisDialog();
					//GenericAlertService.alertMessage("Employee Details are added", "Info");
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
