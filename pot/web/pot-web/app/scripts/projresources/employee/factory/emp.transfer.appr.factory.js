'use strict';
app.factory('EmpTransferApprFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EpsProjectSelectFactory", "EmpReqApprUserFactory", "EmployeeMasterDetailsFactory", "EmpRegisterService", "GenericAlertService", "ProjectSettingsService", "RequestForEmployeeAdditionalTimeFactory", function(ngDialog, $q, $filter, $timeout, $rootScope, EpsProjectSelectFactory,
	EmpReqApprUserFactory, EmployeeMasterDetailsFactory, EmpRegisterService, GenericAlertService,
	ProjectSettingsService, RequestForEmployeeAdditionalTimeFactory) {
	var empNewRequestPopUp;
	var service = {};
	service.getEmpTransRequestDetails = function(editNewRequest, empReqTransMap) {
		var deferred = $q.defer();
		const empReq = [editNewRequest];
		var popupdata = service.openPopup(editNewRequest, empReqTransMap, empReq);
		popupdata.then(function(data) {
			deferred.resolve(data);
		});
		return deferred.promise;
	}, service.openPopup = function(editNewRequest, empReqTransMap, empReqTransTOs) {
		var deferred = $q.defer();
		empNewRequestPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/approvaltransfer/emptransferapprovalpopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.addNewRequestData = empReqTransTOs[0];
				console.log($scope.addNewRequestData);
				console.log($scope.addNewRequestData.addlTimeFlag);
				console.log($scope.addNewRequestData.notifyDate);
				$scope.userProjMap = empReqTransMap.userProjMap;
				$scope.empClassMap = empReqTransMap.empClassMap;
				$scope.empCompanyMap = empReqTransMap.empCompanyMap;
				$scope.userMap = empReqTransMap.userMap;
				$scope.editNewRequest=editNewRequest;
				$scope.apprStatus=$scope.addNewRequestData.apprStatus;
				$scope.timeFlag = false;
				$scope.reqForAdtlTime = false;
				$scope.getProjSettingsEmpDetailsCheck=function() {

					// Disable button when approver is not logged in user
					/*
					if ($rootScope.account.userId != $scope.addNewRequestData.apprUserTO.userId) {
						$scope.timeFlag = true;
						return;
					}
					*/
					var req={
							"projId" : $scope.editNewRequest.fromProjId,
							"id" : $scope.editNewRequest.id,
							"emptransType" : 'Approval Time'
					};
					ProjectSettingsService.findEmpTransNormalTime(req).then(function(data) {
						console.log(data)
						const empTrans = data.projEmpTransTOs[0];
						console.log(empTrans);
						/*
						const requestedDate = $scope.addNewRequestData.reqDate;
			
						if ($scope.addNewRequestData.addlTimeFlag) {
							const requestedDate = $scope.addNewRequestData.notifyDate;
						} else {
							const requestedDate = $scope.addNewRequestData.reqDate;
						}
						*/
						//const foo = condition ? valueA : valueB
						const requestedDate = $scope.addNewRequestData.addlTimeFlag ? $scope.addNewRequestData.notifyDate : $scope.addNewRequestData.reqDate;
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
						GenericAlertService.alertMessage("Error occured while getting settings normal time", 'Error');
					});
				}
				if ($scope.addNewRequestData.apprDate == null) {
					$scope.date = new Date();
					$scope.addNewRequestData.apprDate = $filter('date')($scope.date, "dd-MMM-yyyy")
				}
				$scope.getEmpMasterDetails = function() {
					var req = {
						"status" : 1
					};
					EmployeeMasterDetailsFactory.getEmployeeMasterDetails(req).then(function(data) {
						$scope.employeeData = data.employeeMasterTO;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting employee request details", 'Error');
					});
				},
				$scope.getUserEmployees = function(type) {
					var req = {
						"status" : 1,
						"moduleCode" : "EMPTRANSFER",
						"actionCode" : 'SUBMIT'
					};
					if (type == 'SUBMIT') {
						req.permission = "RESOURCE_EMPLOYEE_EMPTRANSFER_SUBMIT";
					} else if (type == 'APPROVE') {
						req.permission = "RESOURCE_EMPLOYEE_EMPTRANSFER_APPROVE";
					}
					EmpReqApprUserFactory.getEmpUsers(req).then(function(data) {
						if (type == 'SUBMIT') {
							$scope.addNewRequestData.reqUserTO.userId = data.id;
							$scope.addNewRequestData.reqUserTO.userName = data.name;
							$scope.addNewRequestData.reqUserTO.empCode = data.code;
							$scope.addNewRequestData.reqUserTO.firstName = data.displayNamesMap['firstName'];
							$scope.addNewRequestData.reqUserTO.lastName = data.displayNamesMap['lastName'];
							$scope.addNewRequestData.reqUserTO.empDesg = data.displayNamesMap['classType'];
							$scope.addNewRequestData.reqUserTO.email = data.displayNamesMap['userEmail'];
							$scope.addNewRequestData.reqUserTO.phone = data.displayNamesMap['phone'];
							$scope.addNewRequestData.reqUserTO.projId = data.displayNamesMap['projId'];
							$scope.addNewRequestData.reqCurrentProj = data.displayNamesMap['projectName'];
						}
						if (type == 'APPROVE') {
							$scope.addNewRequestData.apprUserTO.userId = data.id;
							$scope.addNewRequestData.apprUserTO.userName = data.name;
							$scope.addNewRequestData.apprUserTO.empCode = data.code;
							$scope.addNewRequestData.apprUserTO.firstName = data.displayNamesMap['firstName'];
							$scope.addNewRequestData.apprUserTO.lastName = data.displayNamesMap['lastName'];
							$scope.addNewRequestData.apprUserTO.empDesg = data.displayNamesMap['classType'];
							$scope.addNewRequestData.apprUserTO.email = data.displayNamesMap['userEmail'];
							$scope.addNewRequestData.apprUserTO.phone = data.displayNamesMap['phone'];
							$scope.addNewRequestData.apprUserTO.projId = data.displayNamesMap['projId'];
							$scope.addNewRequestData.apprCurrentProj = data.displayNamesMap['projectName'];
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting employee details", "Error");
					});
				},
				$scope.getEmpTransferList = function(emp) {
					var req = {
						"status" : 1
					};
					EmployeeMasterDetailsFactory.getEmployeeMasterDetails(req).then(function(data) {
						emp.empRegisterDtlTO = data.employeeMasterTO;
						$scope.empCompanyMap = data.empCompanyMap;
						$scope.empClassMap = data.classificationMap;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Registered Employee Details", "Error");
					});
				},
				$scope.addEmpRows = function() {
					if ($scope.addNewRequestData.empTransReqApprDetailTOs == undefined) {
						$scope.addNewRequestData.empTransReqApprDetailTOs = [];
					}
					$scope.addNewRequestData.empTransReqApprDetailTOs.push({
						"status" : 1
					});
				},
				$scope.deleteEmpRows = function(request) {
					if (request.selected) {
						selectedPlantNewRequest.push(request);
					} else {
						selectedPlantNewRequest.pop(request);
					}
				},
				$scope.empRowSelect = function(request) {
					if (request.selected) {
						selectedPlantNewRequest.push(request);
					} else {
						selectedPlantNewRequest.pop(request);
					}
				},
				$scope.getUserProjects = function(type) {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function(data) {
						if (type == 1) {
							$scope.addNewRequestData.fromProjId = data.searchProject.projId;
						} else {
							$scope.addNewRequestData.toProjId = data.searchProject.projId;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				},
				$scope.getApproveDetails = function(empRegisterDtlTO) {
					var empReq = {
						"status" : 1,
						"projId" : $scope.projId
					};
					ProjectEmployeFactory.getProjectEmployeeDetails(empReq).then(function(data) {
						empRegisterDtlTO.id = data.plantEmployeeTO.id;
						empRegisterDtlTO.code = data.plantEmployeeTO.code;
						empRegisterDtlTO.name = data.plantEmployeeTO.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
					});
				},
				$scope.saveEmptrasnferRequest = function(apprStatus) {
					$scope.addNewRequestData.transType = false;
					$scope.addNewRequestData.apprStatus = apprStatus;
					var req = {
						"empReqTransTOs" : [ $scope.addNewRequestData ],
						"empTransReq" : {
							"transType" : false,
							"status" : 1,
							"notificationStatus" : 'APPROVED'
						},
						"apprStatus" : apprStatus

					};
					EmpRegisterService.saveEmpTransfers(req).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Employee New Request Detail(s) successfully '+  req.apprStatus ,"Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							var returnPopObj = {
								"empReqTransTOs" : data.empReqTransTOs,
							};
							$scope.apprStatus=apprStatus;
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Employee New Request Detail(s) successfully '+  req.apprStatus  , "Error");
					});
				},
				$scope.addAdditionalForEmployee = function() {
					console.log($scope.editNewRequest);
					var reqForAdditionalTimePopUp = RequestForEmployeeAdditionalTimeFactory
						.getAdditionalTimeDetails($scope.editNewRequest);
				   	reqForAdditionalTimePopUp.then(function (data) {
						$scope.closeThisDialog();
				   	}, function (error) {
						GenericAlertService.alertMessage("Error occurred while getting additional transfer details", 'Error');
				   	});
			   };
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
