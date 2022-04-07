'use strict';
app.factory('EmpLeaveApprovalFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "EmpLeaveTypeFactory", "EmpReqApprUserFactory", "GenericAlertService", "ProjectSettingsService", "RequestForEmployeeLeaveAdditionalTimeFactory", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, 
		EmpLeaveTypeFactory, EmpReqApprUserFactory, GenericAlertService, ProjectSettingsService, RequestForEmployeeLeaveAdditionalTimeFactory) {
	var leavereqPopUp;
	var selectedLeaveReq = [];
	var service = {};
	service.getLeaveRequestDetails = function(editleavereq, empDropDown,leaveCodeMap,projId) {
		var deferred = $q.defer();
			var req = {
				"id" : editleavereq.id
			};
			EmpRegisterService.getEmpLeaveReqApprovalDetails(req).then(function(data) {
				var req = service.openPopup(data.empLeaveReqApprTOs[0], empDropDown,leaveCodeMap,projId);
				req.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage('Error occured while getting leave request details', "Error");
			});
		return deferred.promise;
	}, service.openPopup = function(empLeaveReq, empDropDown,leaveCodeMap,projId ) {
		var deferred = $q.defer();
		leavereqPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/leaveapprovalform/empleaveapprovalpopup.html',
			scope : $rootScope,
			closeByDocument : false,
			className:'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {
			
				$scope.empDropDown = empDropDown;
				console.log($scope.empDropDown);
				var selectedLeaveReqs = [];
				var selectedPublicHolidays = [];
				var selectedRosterDays = [];
				$scope.leaveCodeMap=leaveCodeMap;
				$scope.empLeaveRequestTO = empLeaveReq;
				console.log($scope.empLeaveRequestTO);
				$scope.leaveCount = 0;
				$scope.projId=projId;
				$scope.reqForAdtlTime = false;
				$scope.publicHolidaysCount = $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length;
				if ($scope.empLeaveRequestTO.empRosterDays != undefined && $scope.empLeaveRequestTO.empRosterDays != null) {
					$scope.rosterDayscount = $scope.empLeaveRequestTO.empRosterDays.length;
				} else {
					$scope.rosterDayscount = 0;
				}
				$scope.addRows = function() {
					$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.push({
						"select" : false,
						"fromDate" : null,
						"toDate" : null,
						"totalDays" : null,
						"reqComments" : null,
						"status" : 1
					});
				}
				$scope.leavereqRowSelect = function(leaveReqTO) {
					if (leaveReqTO.select) {
						selectedLeaveReqs.push(leaveReqTO);
					} else {
						selectedLeaveReqs.pop(leaveReqTO);
					}
				}, $scope.deleteRows = function() {
					if (selectedLeaveReqs.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}
					
					
					if ($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length == 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						return;
					}

					if (selectedLeaveReqs.length <= $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length) {
						angular.forEach(selectedLeaveReqs, function(value, key) {
							$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.splice($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.indexOf(value), 1);
						});
						selectedLeaveReqs = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				},

				$scope.addHolidaysRows = function() {
					$scope.publicHolidaysCount = $scope.publicHolidaysCount + 1;
					$scope.empLeaveRequestTO.empPublicHolidayTOs.push({
						"select" : false,
						"desc" : null,
						"type" : 'PH',
						"date" : null,
						"status" : 1
					});
				}, $scope.holidayDayRowSelect = function(holidayTO) {
					if (holidayTO.select) {
						selectedPublicHolidays.push(holidayTO);
					} else {
						selectedPublicHolidays.pop(holidayTO);
					}
				}, $scope.deleteHolidaysRows = function() {
					if (selectedPublicHolidays.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}

				

					if (selectedPublicHolidays.length <= $scope.empLeaveRequestTO.empPublicHolidayTOs.length) {
						$scope.publicHolidaysCount = $scope.publicHolidaysCount - selectedPublicHolidays.length;
						angular.forEach(selectedPublicHolidays, function(value, key) {
							$scope.empLeaveRequestTO.empPublicHolidayTOs.splice($scope.empLeaveRequestTO.empPublicHolidayTOs.indexOf(value), 1);
						});
						selectedPublicHolidays = [];
					} 
				},

				$scope.addRDOsRows = function() {
					$scope.rosterDayscount = $scope.rosterDayscount + 1;
					$scope.empLeaveRequestTO.empRosterDays.push({
						"select" : false,
						"desc" : null,
						"type" : 'RD',
						"date" : null,
						"status" : 1
					});
				}, $scope.rosterDaysRowSelect = function(rosterDay) {
					if (rosterDay.select) {
						selectedRosterDays.push(rosterDay);
					} else {
						selectedRosterDays.pop(rosterDay);
					}
				}, $scope.deleteRDOsRows = function() {
					if (selectedRosterDays.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}
					
					if (selectedRosterDays.length <= $scope.empLeaveRequestTO.empRosterDays.length) {
						$scope.rosterDayscount = $scope.rosterDayscount - selectedRosterDays.length;
						angular.forEach(selectedRosterDays, function(value, key) {
							$scope.empLeaveRequestTO.empRosterDays.splice($scope.empLeaveRequestTO.empRosterDays.indexOf(value), 1);
						});
						selectedRosterDays = [];
					} 
				},
				$scope.timeFlag=false;
				$scope.getProjSettingsEmpLeaveCheck = function() {

					// Disable button when approver is not logged in user
					/*
					if ($rootScope.account.userId != $scope.empLeaveRequestTO.apprUserTO.userId) {
						$scope.timeFlag = true;
						return;
					}
					*/
					var req = {
						"status" : 1,
						"projId" : $scope.empLeaveRequestTO.empProjId
					};
					ProjectSettingsService.getProjLeaveRequest(req).then(function(data) {
						console.log(data);
						const empTrans = data.projLeaveRequestTOs[0];
						console.log(empTrans);
						console.log($scope.empLeaveRequestTO.notifyDate);
						console.log($scope.empLeaveRequestTO.reqDate);
						//const requestedDate = $scope.empLeaveRequestTO.reqDate;
						const requestedDate = $scope.empLeaveRequestTO.addlTimeFlag ? $scope.empLeaveRequestTO.notifyDate : $scope.empLeaveRequestTO.reqDate;
						let reqDate = new Date(requestedDate);
						if (empTrans.cutOffDays) {
							// Adding Cut Off days + 1 (1 day is to set midnight from requested date)
							reqDate.setDate(reqDate.getDate() + 1 + empTrans.cutOffDays);
						}
						if (empTrans.cutOffHours) {
							// Adding hours
							reqDate.setHours(reqDate.getHours() + empTrans.cutOffHours);
						}
						if (empTrans.cutOffMinutes) {
							// Adding hours
							reqDate.setMinutes(reqDate.getMinutes() + empTrans.cutOffMinutes);
						}
						//reqDate.setHours(0,0,0,0);
						//const today = new Date().setHours(0,0,0);
						const today = new Date();
						$scope.timeFlag = (reqDate.getTime() <= today);
						if ($scope.timeFlag) {
							$scope.reqForAdtlTime = true;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Leave Approval Details", "Error");
					});
				},
				$scope.getUserEmployees = function() {
					var req = {
						"status" : 1,
						"moduleCode" : "EMPTRANSFER",
						"actionCode" : 'APPROVE',
						"permission" : "RESOURCE_EMPLOYEE_EMPTRANSFER_APPROVE"
					};
					EmpReqApprUserFactory.getEmpUsers(req).then(function(data) {
						$scope.empLeaveRequestTO.apprUserTO.empCode = data.code;
						$scope.empLeaveRequestTO.apprUserTO.userName = data.name;
				
						$scope.empLeaveRequestTO.apprUserTO.firstName = data.displayNamesMap['firstName'] + data.displayNamesMap['lastName'];
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting employee details", "Error");
					});
				},
				$scope.saveEmpLeaveReq = function(apprStatus) {
					$scope.empLeaveRequestTO.apprStatus=apprStatus;
					var req = {
						"empLeaveReqApprTOs" : [ $scope.empLeaveRequestTO ],
						"empLeaveReq" : {
							"status" : 1,
							"fromDate" : null,
							"toDate" : null,
							"onload" : false,
							"reqType" : false,
							"apprStatus":apprStatus
						}
					}
					console.log(req);
					//return;
					EmpRegisterService.saveEmpLeaveReqApprovals(req).then(function(data) {
						console.log(data);
						var succMsg = GenericAlertService.alertMessageModal('Employee Leave request got '+  empLeaveReq.apprStatus ,"Info");
						succMsg.then(function () {
							$scope.closeThisDialog();
							var returnPopObj = {
									"empLeaveReqApprTOs": empLeaveReqApprTOs
							};
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Error occured while taking approver decision', "Error");
					});
				}, $scope.caluclauteTotalWeekDays = function(leaveReqTO) {
					var start = new Date(leaveReqTO.fromDate);
					var end = new Date(leaveReqTO.toDate);
					var totalDays = new Date(end - start);
					var days = totalDays/1000/60/60/24;
					if (days > 0) {
						leaveReqTO.totalDays = days;						
					}
				},  $scope.caluclauteNetTotalDays = function() {
					$scope.leaveCount=0;
					angular.forEach($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs,function(data){
						$scope.leaveCount = $scope.leaveCount+data.totalDays;
					});
					return $scope.leaveCount;
				}, $scope.getLeaveTypes = function(empLeaveRequestTO) {
					EmpLeaveTypeFactory.getLeaveTypes().then(function(data) {
						empLeaveRequestTO.leaveTypeTO = data;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting leave types", "Error");
					});
				},	
				$scope.getEmpContactDetails = function() {
					var req = {
						"status" : 1,
						"empId" : $rootScope.selectedEmployeeData.id,
					};
					EmpRegisterService.getEmpContactDetails(req).then(function(data) {
						$scope.empContactDtlTOs = data.empContactDtlTOs;
						angular.forEach($scope.empContactDtlTOs,function(value,key)
								{
									$scope.phoneNumber=value.phoneNumber;
									$scope.email=value.email;
								});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting employee contact details", 'Error');
					});
				},
				$scope.addAdditionalForEmployeeLeave = function() {
					if ( $scope.empLeaveRequestTO.apprStatus != 'Pending For Approval' ) {
						 GenericAlertService.alertMessage("Please Select Pending For Approval", 'Warning');
						 return;
					}
					var reqForAdditionalTimePopUp = RequestForEmployeeLeaveAdditionalTimeFactory
						.getAdditionalTimeDetails($scope.empLeaveRequestTO,$scope.leaveCodeMap);
				   	reqForAdditionalTimePopUp.then(function(data) {
						$scope.closeThisDialog();
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting additional transfer details", 'Error');
					});
			   }
			} ]
		});
		return deferred.promise;
	}

	return service;
}]);
