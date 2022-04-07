'use strict';
app.factory('EmpLeaveRequestFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "EmpLeaveTypeFactory", "EmpReqApprUserFactory", "GenericAlertService", "UserService","EmpRegisterDataShareFactory", function (ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, EmpLeaveTypeFactory,
	EmpReqApprUserFactory, GenericAlertService, UserService,EmpRegisterDataShareFactory) {
	var leavereqPopUp;
	var selectedLeaveReq = [];
	var service = {};
	service.getLeaveRequestDetails = function (actionType, editleavereq, empDropDown, leaveCodeMap) {

		var deferred = $q.defer();
		if (actionType == 'Add') {
			var empLeaveReq = {
				"status": 1,
				"empProjId": $rootScope.selectedEmployeeData.projId,
				"empRegId": $rootScope.selectedEmployeeData.id
			};
			empLeaveReq.empLeaveReqApprDetailTOs = [];
			empLeaveReq.empPublicHolidayTOs = [];
			empLeaveReq.empRosterDays = [];
			empLeaveReq.apprUserTO = {};
			empLeaveReq.reqUserTO = {};

			var req = service.openPopup(actionType, empLeaveReq, empDropDown, leaveCodeMap);
			req.then(function (data) {
				var returnPopObj = {
					"empLeaveReqApprTOs": data.empLeaveReqApprTOs
				};
				deferred.resolve(returnPopObj);
			});
		} else {
			var req = {
				"id": editleavereq.id
			};
			EmpRegisterService.getEmpLeaveReqApprovalDetails(req).then(function (data) {

				var req = service.openPopup(actionType, data.empLeaveReqApprTOs[0], empDropDown, leaveCodeMap);
				req.then(function (data) {
					var returnPopObj = {
						"empLeaveReqApprTOs": data.empLeaveReqApprTOs
					};
					deferred.resolve(returnPopObj);
				});
			}, function (error) {
				GenericAlertService.alertMessage('Error occured while getting leave request details', "Error");
			});
		}
		return deferred.promise;
	}, service.openPopup = function (actionType, empLeaveReq, empDropDown, leaveCodeMap) {
		var deferred = $q.defer();
		leavereqPopUp = ngDialog.open({

			template: 'views/projresources/projempreg/leaverequestform/empleaverequestpopup.html',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			controller: ['$scope', function ($scope) {
				$scope.todaysDate = new Date();
				$scope.action = actionType;
				$scope.empDropDown = empDropDown;
				var selectedLeaveReqs = [];
				var selectedPublicHolidays = [];
				var selectedRosterDays = [];
				$scope.leaveCodeMap = leaveCodeMap;
				$scope.empLeaveRequestTO = empLeaveReq;
				var fName = empLeaveReq.apprUserTO.firstName
				var lName = empLeaveReq.apprUserTO.lastName
				             
				$scope.leaveCount = 0;
				$scope.publicHolidaysCount = $scope.empLeaveRequestTO.empPublicHolidayTOs.length;
				if ($scope.empLeaveRequestTO.empRosterDays != undefined && $scope.empLeaveRequestTO.empRosterDays != null) {
					$scope.rosterDayscount = $scope.empLeaveRequestTO.empRosterDays.length;
				} else {
					$scope.rosterDayscount = 0;
				}

				let addNewRow = {
					"select": false,
					"fromDate": $scope.todaysDate,
					"toDate": null,
					"totalDays": null,
					"reqComments": null,
					"status": 1
				};
				if (actionType == 'Add') {
					var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
					];
					var date = new Date()
					date.setDate(date.getDate())
					date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear();
					$scope.empLeaveRequestTO.reqDate = date;
					$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.push(angular.copy(addNewRow));
				}
				$scope.addRows = function () {
					console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs);
				    if($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length>1){
					  for(var i=1;i<$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length;i++){
						if($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i-1].leaveType == $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveType){
							GenericAlertService.alertMessage('Please Select Different Leave Type', "Warning");
							return;
						}
						
					}
				    }

					for(var i=0;i<$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length;i++)
					{	
						if( $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].toDate == null || $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].toDate == "" || $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].fromDate == null || $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].fromDate == "" )
						{
							GenericAlertService.alertMessage('Please pass fromDate and toDate', "Warning");
							return;
						}
						
						/*if( $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode == null || $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode == "" || $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode == null || $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode == "" )
						{
							GenericAlertService.alertMessage('Please pass leave Code', "Info");
							return;
						}*/
					}
					$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.push(angular.copy(addNewRow));
					
					for(var i=0;$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length>i;i++){
						console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode);
						
					}	
					console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length);				
					for(var i=$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length-1;i>0;i--)
					{						
						console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i]);
						let templ_cnt = i;
						console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[templ_cnt-1]);
						if( $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].toDate == null && $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[templ_cnt-1].toDate != null )
						{
							//let temp_cnt = i+1;
							console.log("if condition");
							$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].fromDate = $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[templ_cnt-1].toDate;
						}						
					}					
					console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs);
				};
				$scope.leavereqRowSelect = function (leaveReqTO) {
					if (leaveReqTO.select) {
						selectedLeaveReqs.push(leaveReqTO);
					} else {
						selectedLeaveReqs.pop(leaveReqTO);
					}
				};
				$scope.deleteRows = function () {
					if (selectedLeaveReqs.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}

					if ($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length == 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						return;
					}

					if (selectedLeaveReqs.length < $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length) {
						angular.forEach(selectedLeaveReqs, function (value, key) {
							$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.splice($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.indexOf(value), 1);
						});
						selectedLeaveReqs = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				};
				//------------------------------------------------------------------------------------------------------
	$scope.getRegEmployeeProcureDeliveryDetails = function() {
		
		var getEmployeePoReq = {
			"status" : 1,
			"projId" : $rootScope.selectedEmployeeData.projId,
			"empId" : $rootScope.selectedEmployeeData.id
		};
		var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
		resultMap.then(function(data) {
			
			$scope.empDropDown = data.empMapsData;
			EmpRegisterService.getEmpEnrollments(getEmployeePoReq).then(function(data) {

				$scope.empLeaveRequestTO.apprUserTO.userId = data.empEnrollmentDtlTOs[0].reportingManagerLabelKeyTO.id
			//	$scope.empLeaveRequestTO.apprUserTO.empCode = data.empEnrollmentDtlTOs[0].reportingManagerLabelKeyTO.code;
			//	$scope.empLeaveRequestTO.apprUserTO.firstName = data.empEnrollmentDtlTOs[0].reportingManagerLabelKeyTO.name;
				console.log($scope.reportingMgnrCode+"..."+$scope.reportingMgnrName);
			//	console.log(data.empEnrollmentDtlTOs.reportingManagerLabelKeyTO)
				$scope.employeeProjDtlTOs = data.empEnrollmentDtlTOs;
				$scope.empRegisterDtlTO = data.empRegisterDtlTO;
				$scope.empRegisterDropDownTO = data.empRegisterDropDownTO;

				
			}, function(error) {
				if (error.message != null && error.message != undefined) {
					GenericAlertService.alertMessage(error.message, error.status);
				} else {
					GenericAlertService.alertMessage("Error occured while getting the Employee Procuremet Details", "Error");
				}
			});
		});

	},
//-------------------------------------------------------------------------------------------------------
				$scope.getEmpContactDetails = function () {

					var req = {
						"status": 1,
						"empId": $rootScope.selectedEmployeeData.id,
						"projId": $rootScope.selectedEmployeeData.id
					};			
	//-------------------------------------------------------------------------------------				
		/*		EmpRegisterService.getEmpContactDetails(req).then(function (data) {
					
						$scope.empContactDtlTOs = data.empContactDtlTOs;
						angular.forEach($scope.empContactDtlTOs, function (value, key) {
							$scope.phoneNumber = value.phoneNumber;
							$scope.email = value.email;  */
  //--------------------------------------------------------------------------------------
					EmpRegisterService.getEmpEnrollments(req).then(function(data) {
				angular.forEach(data.empEnrollmentDtlTOs, function(value, key){
					//	$scope.phoneNumber = value.contractNumber;
					//	$scope.email = value.email;	
						console.log("phone number: "+$scope.phoneNumber)
							console.log("email number: "+$scope.email)
	
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting employee contact details", 'Error');
					});
				};
				var req = {
		     	"status" : 1,
			     "empId" : $rootScope.selectedEmployeeData.id,
		       };
		     EmpRegisterService.getEmpContactDetails(req).then(function(data) {
			 angular.forEach(data.empContactDtlTOs,function(value,key){
				 $scope.email = value.email;
				 $scope.phoneNumber = value.phoneNumber;
                 console.log(value);
			})
             });
				$scope.addHolidaysRows = function () {
					$scope.publicHolidaysCount = $scope.publicHolidaysCount + 1;
					$scope.empLeaveRequestTO.empPublicHolidayTOs.push({
						"select": false,
						"desc": null,
						"type": 'PH',
						"date": null,
						"status": 1
					});
				};
				$scope.holidayDayRowSelect = function (holidayTO) {
					if (holidayTO.select) {
						selectedPublicHolidays.push(holidayTO);
					} else {
						selectedPublicHolidays.pop(holidayTO);
					}
				};
				$scope.deleteHolidaysRows = function () {
					if (selectedPublicHolidays.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}

					if (selectedPublicHolidays.length <= $scope.empLeaveRequestTO.empPublicHolidayTOs.length) {
						$scope.publicHolidaysCount = $scope.publicHolidaysCount - selectedPublicHolidays.length;
						angular.forEach(selectedPublicHolidays, function (value, key) {
							$scope.empLeaveRequestTO.empPublicHolidayTOs.splice($scope.empLeaveRequestTO.empPublicHolidayTOs.indexOf(value), 1);
						});
						selectedPublicHolidays = [];
					}
				};
				$scope.addRDOsRows = function () {
					$scope.rosterDayscount = $scope.rosterDayscount + 1;
					$scope.empLeaveRequestTO.empRosterDays.push({
						"select": false,
						"desc": null,
						"type": 'RD',
						"date": null,
						"status": 1
					});
				};
				$scope.rosterDaysRowSelect = function (rosterDay) {
					if (rosterDay.select) {
						selectedRosterDays.push(rosterDay);
					} else {
						selectedRosterDays.pop(rosterDay);
					}
				};
				$scope.deleteRDOsRows = function () {
					if (selectedRosterDays.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}
					if (selectedRosterDays.length <= $scope.empLeaveRequestTO.empRosterDays.length) {
						$scope.rosterDayscount = $scope.rosterDayscount - selectedRosterDays.length;
						angular.forEach(selectedRosterDays, function (value, key) {
							$scope.empLeaveRequestTO.empRosterDays.splice($scope.empLeaveRequestTO.empRosterDays.indexOf(value), 1);
						});
						selectedRosterDays = [];
					}
				};
				$scope.getUserEmployees = function () {
					var req = {
						"status": 1,
						"moduleCode": "EMPTRANSFER",
						"actionCode": 'APPROVE',
						"permission": "RESOURCE_EMPLOYEE_EMPTRANSFER_APPROVE"
					};
					EmpReqApprUserFactory.getEmpUsers(req).then(function (data) {

						$scope.empLeaveRequestTO.apprUserTO.userId = data.id;
						$scope.empLeaveRequestTO.apprUserTO.userName = data.name;
						$scope.empLeaveRequestTO.apprUserTO.empCode = data.code;

						$scope.empLeaveRequestTO.apprUserTO.firstName = data.displayNamesMap['firstName'] +"  "+ data.displayNamesMap['lastName'];
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting employee details", "Error");
					});
				};
				$scope.getEmployeeByUserId = function () {
					if (!$scope.empLeaveRequestTO.apprUserTO || !$scope.empLeaveRequestTO.apprUserTO.empCode) {
						UserService.findByUserId($rootScope.account.userId).then(function (data) {
						//	$scope.empLeaveRequestTO.apprUserTO.firstName = data.displayNamesMap['firstName'] +" "+ data.displayNamesMap['lastName'];
							$scope.empLeaveRequestTO.reqUserTO = {
								"userId": data.userId,
								"empCode": data.empCode,
								"firstName": data.firstName,
								"lastName": data.lastName
							}
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting logged-in employee details", "Error");
						});
					}
				};
				$scope.submitEmpLeaveReq = function () {
					
					 if($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length>1){
					  for(var i=1;i<$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length;i++){
						if($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i-1].leaveType == $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveType){
							GenericAlertService.alertMessage('Please Select Different Leave Type', "Warning");
							return;
						}
						
					}
				    }
						if( $scope.empLeaveRequestTO.apprUserTO.empCode == null || $scope.empLeaveRequestTO.apprUserTO.firstName == null){
						GenericAlertService.alertMessage("Your request cannot be processed as the Reporting Manager is not assigned", 'Info');
					return;
				}
					
					if (!$scope.empLeaveRequestTO.apprUserTO || !$scope.empLeaveRequestTO.apprUserTO.empCode) {
						GenericAlertService.alertMessage("Please Select Approver !! ", "Warning");
						return;
					}
					if (isLeaveAfterDemob() || holidaysInBetween() || roasterInBetween() || $scope.validateLeaveDates() || $scope.validateLeaveOverLapDates() || $scope.validateDiffDays()) {
						return;
					}
					var size = $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length;
					$scope.empLeaveRequestTO.startDate = $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[0].fromDate;
					$scope.empLeaveRequestTO.endDate = $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[size - 1].toDate;
					$scope.empLeaveRequestTO.totalDays = $scope.leaveCount - ($scope.publicHolidaysCount + $scope.rosterDayscount);
					var req = {
						"empLeaveReqApprTOs": [$scope.empLeaveRequestTO],
						"empLeaveReq": {
							"status": 1,
							"id": $rootScope.selectedEmployeeData.id,
							"fromDate": null,
							"toDate": null,
							"onload": false,
							"reqType": true
						}
					}
					EmpRegisterService.saveEmpLeaveReqApprovals(req).then(function (data) {
						var succMsg = GenericAlertService.alertMessageModal('Employee leave request has been send for approval', "Info");
						succMsg.then(function () {
							$scope.closeThisDialog();
							var returnPopObj = {
								"empLeaveReqApprTOs": data.empLeaveReqApprTOs
							};
							deferred.resolve(returnPopObj);
						});
					}, function (error) {
						GenericAlertService.alertMessage('Error occured while sending leave request', "Error");
					});

				};
				function isLeaveAfterDemob() {
					var flag = false;
					for (const value of $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs) {
						const demob = ($rootScope.selectedEmployeeData.projEmpRegisterTO.deMobilaizationDate != null )?  new Date($rootScope.selectedEmployeeData.projEmpRegisterTO.deMobilaizationDate): ''
						const fromDate = new Date(value.fromDate);
						const toDate = new Date(value.toDate);
						if(demob !=''){
						if (fromDate > demob || toDate > demob) {
							flag = true;
							break;
						}
						}
					}
					if (flag) {
						GenericAlertService.alertMessage("Leave date should be less than demobilization date", "Warning");
					}
					return flag;
				}
				// Validating if holidays and roaster days are in between leave dates
				function holidaysInBetween() {
					let inRange = false;
					if ($scope.empLeaveRequestTO.empPublicHolidayTOs.length <= 0)
						return inRange;
					for (const publicHoliday of $scope.empLeaveRequestTO.empPublicHolidayTOs) {
						if (publicHoliday.date) {
							for (const leaveDateObj of $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs) {
								inRange = (moment(publicHoliday.date, "DD-MMM-YYYY").isBetween(moment(leaveDateObj.fromDate, "DD-MMM-YYYY"), moment(leaveDateObj.toDate, "DD-MMM-YYYY"))
									|| moment(publicHoliday.date, "DD-MMM-YYYY").isSame(moment(leaveDateObj.fromDate, "DD-MMM-YYYY"))
									|| moment(publicHoliday.date, "DD-MMM-YYYY").isSame(moment(leaveDateObj.toDate, "DD-MMM-YYYY")));
							}
							if (!inRange) {
								GenericAlertService.alertMessage("Public Holiday " + publicHoliday.date + " is not in the range of leave dates", "Warning");
								break;
							}
						}
					}
					return !inRange;
				};
				function roasterInBetween() {
					let inRange = false;
					if ($scope.empLeaveRequestTO.empRosterDays.length <= 0)
						return inRange;
					for (const roaster of $scope.empLeaveRequestTO.empRosterDays) {
						if (roaster.date) {
							for (const leaveDateObj of $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs) {
								inRange = (moment(roaster.date, "DD-MMM-YYYY").isBetween(moment(leaveDateObj.fromDate, "DD-MMM-YYYY"), moment(leaveDateObj.toDate, "DD-MMM-YYYY"))
									|| moment(roaster.date, "DD-MMM-YYYY").isSame(moment(leaveDateObj.fromDate, "DD-MMM-YYYY"))
									|| moment(roaster.date, "DD-MMM-YYYY").isSame(moment(leaveDateObj.toDate, "DD-MMM-YYYY")));
							}
							if (!inRange) {
								GenericAlertService.alertMessage("Roaster Day " + roaster.date + " is not in the range of leave dates", "Warning");
								break;
							}
						}
					}
					return !inRange;
				};
				$scope.validateLeaveDates = function () {
					var totalDays = null;
					var days = null;
					var flag = false;

					angular.forEach($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs, function (value, key) {
						value.errorFlag = false;
						totalDays = Math.abs(new Date(value.toDate).getTime() - new Date(value.fromDate).getTime());
						days = Math.ceil(totalDays / (1000 * 3600 * 24));
						if (days < 0) {
							flag = true;
							value.errorFlag = true;
						}
					});
					if (flag) {
						GenericAlertService.alertMessage("Leave start date cannot be greater then end date", "Warning");
						return true;
					} else {
						return false;
					}
				};
				$scope.validateLeaveOverLapDates = function () {
					var fromDate = null
					var toDate = null
					var totalDays = null;
					var days = null;
					var flag = false;
					var count = 0;
					angular.forEach($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs, function (value, key) {
						value.errorFlag = false;
						if (count > 0) {
							totalDays = Math.abs(new Date(value.toDate).getTime() - new Date(value.fromDate).getTime());
							days = Math.ceil(totalDays / (1000 * 3600 * 24));
							if (days <= 0) {
								flag = true;
								value.errorFlag = true;
							} else {
								fromDate = new Date(value.toDate);
							}
						} else {
							fromDate = new Date(value.toDate);
						}
						count++;
					});
					if (flag) {
						GenericAlertService.alertMessage("Applied Leaves dates cannot be over lap", "Warning");
						return true;
					} else {
						return false;
					}
				};
				$scope.validateDiffDays = function () {
					var totalDays = null;
					var leaveDays = null;
					var flag1 = false;
					angular.forEach($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs, function (value, key) {
						totalDays = Math.abs(new Date(value.toDate).getTime() - new Date(value.fromDate).getTime());
						leaveDays = Math.ceil(totalDays / (1000 * 3600 * 24));
						if (value.totalDays != leaveDays) {
							flag1 = true;
							return;
						}
					});
					if (flag1) {
						GenericAlertService.alertMessage("Leave count Cannot be more than Difference of end date and start date", "Warning");
						return true;
					} else {
						return false;
					}
				};
				$scope.caluclauteTotalWeekDays = function (leaveReqTO) {
					var start = new Date(leaveReqTO.fromDate);
					var end = new Date(leaveReqTO.toDate);
					var totalDays = new Date(end - start);
					var days = totalDays / 1000 / 60 / 60 / 24;
					if (days > 0) {
						leaveReqTO.totalDays = days;
						return days;
					}
					return null;
				};
				$scope.caluclauteNetTotalDays = function () {
					$scope.leaveCount = 0;
					angular.forEach($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs, function (value, key) {
						if (value.totalDays != undefined && value.totalDays > 0) {
							$scope.leaveCount = $scope.leaveCount + parseFloat(value.totalDays);
						}
					});
					return $scope.leaveCount;
				};	
				$scope.leavereason= function(leave){
						if(leave != null || leave !="" || leave!= undefined){
							$scope.leavecode = false;
						}
					}			
				$scope.getLeaveTypes = function (empLeaveRequestTO) {
					console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length);
					if($scope.empLeaveRequestTO.reasonForLeave == undefined || $scope.empLeaveRequestTO.reasonForLeave == null){
						$scope.leavecode = true;
						return;
					}					
							for(var i=0;$scope.empLeaveRequestTO.empLeaveReqApprDetailTOs.length<i;i++){
								if($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode == $scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i+1].leaveCode){
							
								GenericAlertService.alertMessage("please","Warning");
								return;
								}
						console.log($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs[i].leaveCode);
					} 
					
					var leaveExistingMap = [];
					angular.forEach($scope.empLeaveRequestTO.empLeaveReqApprDetailTOs, function (value, key) {
						if (value.leaveTypeTO && value.leaveTypeTO.id) {
							leaveExistingMap[value.leaveTypeTO.id] = true;
						}
					});
					EmpLeaveTypeFactory.getLeaveTypes(leaveExistingMap).then(function (data) {
						empLeaveRequestTO.leaveTypeTO = data.leaveTypeTO;
						empLeaveRequestTO.leaveType = data.leaveTypeTO.code;
						empLeaveRequestTO.leaveCode = data.leaveTypeTO.desc;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting leave types", "Error");
					});
				};

				$scope.calculateLeaveDays = function (leaveReqObj) {
					if (leaveReqObj.toDate && leaveReqObj.fromDate) {
						var millsDifference = Math.abs(new Date(leaveReqObj.toDate).getTime() - new Date(leaveReqObj.fromDate).getTime());
						leaveReqObj.totalDays = Math.ceil(millsDifference / (1000 * 3600 * 24));

					}

				};


			}]
		});
		return deferred.promise;
	}

	return service;
}]);
