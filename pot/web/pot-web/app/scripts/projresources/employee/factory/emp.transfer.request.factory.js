'use strict';
app.factory('EmpTransferReqFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "EpsProjectSelectFactory", "EmpReqApprUserFactory", "EmployeeTransferReqDetailsFactory", "EmpRegisterService", "GenericAlertService", "UserService", function (ngDialog, $q, $filter, blockUI, $timeout, $rootScope, EpsProjectSelectFactory, EmpReqApprUserFactory, EmployeeTransferReqDetailsFactory,
	EmpRegisterService, GenericAlertService, UserService) {
	var empNewRequestPopUp;
	var deferred = $q.defer();
	var service = {};
	service.getEmpTransRequestDetails = function (actionType, editNewRequest, empReqTransMap) {
		if (actionType == 'Edit') {
			var empReq = [editNewRequest];
			var popupdata = service.openPopup(actionType, empReqTransMap, empReq);
			popupdata.then(function (data) {
				deferred.resolve(data);
			});
		} else {
			var empReqTransTOs = [{
				"status": 1,
				"reqUserTO": {},
				"apprUserTO": {}
			}];
			var popupdata = service.openPopup(actionType, empReqTransMap, empReqTransTOs);
			popupdata.then(function (data) {
				deferred.resolve(data);
			});
		}
		return deferred.promise;
	},
		service.openPopup = function (actionType, empReqTransMap, empReqTransTOs) {
			empNewRequestPopUp = ngDialog.open({
				template: 'views/projresources/projempreg/emptransfer/emptransferpopup.html',
				closeByDocument: false,
				showClose: false,
				className: 'ngdialog-theme-plain ng-dialogueCustom0-5',
				controller: [
					'$scope',
					function ($scope) {
						$scope.reqDate = new Date();
						$scope.$watch('addNewRequestData.reqDate', function () {
							if ($scope.reqDate != null) {
								$scope.reqDate = $filter('date')(($scope.reqDate), "dd-MMM-yyyy")
							}
						});
						$scope.action = actionType;
						$scope.addNewRequestData = empReqTransTOs[0];
						$scope.userProjMap = empReqTransMap.userProjMap;
						$scope.empClassMap = empReqTransMap.empClassMap;
						$scope.empCompanyMap = empReqTransMap.empCompanyMap;

						$scope.userMap = empReqTransMap.userMap;
						var selectedEmpNewRequest = [];

						if ($scope.addNewRequestData.reqDate == null) {
							$scope.date = new Date();
							$scope.addNewRequestData.reqDate = $filter('date')($scope.date, "dd-MMM-yyyy")
						}

						$scope.projId = null;
						var newvalue1 = null;
						var oldvalue1 = null;
						$scope.originProject = {};
						$scope.destinationProject = {};

						$scope.projId = null;
						var newvalue1 = null;
						var oldvalue1 = null;
						$scope.originProject = {};
						$scope.destinationProject = {};

						$scope.getUserProjects = function (type) {
							var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
							userProjectSelection.then(function (data) {
								if (type == 1) {
									$scope.addNewRequestData.fromProjId = data.searchProject.projId;
									$scope.addNewRequestData.fromProjName = data.searchProject.projName;
								} else {
									$scope.addNewRequestData.toProjId = data.searchProject.projId;
									$scope.addNewRequestData.toProjName = data.searchProject.projName;
								}
							}, function (error) {
								GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
							});
						};

						$scope.$watch(function () {
							return $scope.originProject.projName;
						}, function (newvalue, oldvalue) {
							newvalue1 = newvalue;
							oldvalue1 = oldvalue;
						}, true);

						$scope.getEmployeeByUserId = function () {
							if (!$scope.addNewRequestData.reqUserTO.userId) {
								UserService.findByUserId($rootScope.account.userId).then(function (data) {
									$scope.addNewRequestData.reqUserTO.userId = data.userId;
									$scope.addNewRequestData.reqUserTO.empCode = data.empCode;
									$scope.addNewRequestData.reqUserTO.firstName = data.firstName;
									$scope.addNewRequestData.reqUserTO.lastName = data.lastName;
									$scope.addNewRequestData.reqUserTO.empDesg = data.empDesg;
									$scope.addNewRequestData.reqUserTO.email = data.email;
									$scope.addNewRequestData.reqUserTO.phone = data.phone;
									$scope.addNewRequestData.reqUserTO.projId = data.projId;
									$scope.addNewRequestData.reqCurrentProj = data.name;
								}, function (error) {
									GenericAlertService.alertMessage("Error occured while getting logged-in employee details", "Error");
								});
							}
						};

						$scope.getUserEmployees = function (type) {

							if ($scope.addNewRequestData.fromProjId == undefined || $scope.addNewRequestData.fromProjId == null || $scope.addNewRequestData.toProjId == undefined
								|| $scope.addNewRequestData.toProjId == null) {
								GenericAlertService.alertMessage("Please select the Origin and Destination Project", "Warning");
								return;
							}
							//-------------------------------------------------------------------------------------------------
						if($scope.addNewRequestData.fromProjName == $scope.addNewRequestData.toProjName){
								 GenericAlertService.alertMessage("Origin Project and Destination Project should not be same", 'Warning');
							return;
							}
	//-------------------------------------------------------------------------------------------------
							var req = {
								"status": 1,
								"moduleCode": "EMPTRANSFER",
								"actionCode": type
							};
							if (type == 'APPROVE') {
								req.permission = "RESOURCE_EMPLOYEE_EMPTRANSFER_APPROVE";
								EmpReqApprUserFactory.getEmpUsers(req).then(function (data) {
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
								}, function (error) {
									GenericAlertService.alertMessage("Error occured while getting employee details", "Error");
								});
							}
						};

						$scope.addEmpTransferReqDetails = function () {

							if (!$scope.addNewRequestData.fromProjId) {
								GenericAlertService.alertMessage("Please select the source project", "Warning");
								return;
							}
							//-------------------------------------------------------------------------------------------------
							if($scope.addNewRequestData.fromProjName == $scope.addNewRequestData.toProjName){
								 GenericAlertService.alertMessage("Origin Project and Destination Project should not be same", 'Warning');
							return;
							}
							//-------------------------------------------------------------------------------------------------
						$scope.date = new Date();
							var req = {
								"status": 1,
								"projId": $scope.addNewRequestData.fromProjId
							};

							if ($scope.addNewRequestData.empTransReqApprDetailTOs == undefined || $scope.addNewRequestData.empTransReqApprDetailTOs == null) {
								$scope.addNewRequestData.empTransReqApprDetailTOs = [];
							}
							var empTransferExistingMap = [];
							angular.forEach($scope.addNewRequestData.empTransReqApprDetailTOs, function (value, key) {
								empTransferExistingMap[value.empRegId] = true;
							});

							var empTransferPopup = EmployeeTransferReqDetailsFactory.getEmpTransferReqDetails(req, empTransferExistingMap);
							empTransferPopup.then(function (data) {
								angular.forEach(data.empTransReqApprDetailTOs, function (value, key) {
									$scope.addNewRequestData.empTransReqApprDetailTOs.push({
										"empCode": value.code,
										"empFirstName": value.displayNamesMap.firstName,
										"empLastName": value.displayNamesMap.lastName,
										"empClassType": value.displayNamesMap.classType,
										"empCmpName": value.displayNamesMap.cmpName,
										"expectedTransDate": value.displayNamesMap.expectedTransDate,
										"empRegId": value.id
									});
								});
							}, function (error) {
								GenericAlertService.alertMessage("Error occured while gettting  registered employee details", 'Error');
							});

						};

						$scope.deleteRows = function () {
							if (selectedEmpNewRequest.length == 0) {
								GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
							} else {
								angular.forEach(selectedEmpNewRequest, function (value, key) {
									$scope.addNewRequestData.empTransReqApprDetailTOs.splice($scope.addNewRequestData.empTransReqApprDetailTOs.indexOf(value), 1);
								});
								selectedEmpNewRequest = [];
							}
						};

						$scope.empRowSelect = function (request) {
							if (request.select) {
								selectedEmpNewRequest.push(request);
							} else {
								selectedEmpNewRequest.pop(request);
							}
						};

						$scope.getApproveDetails = function (empRegisterDtlTO) {
							var empReq = {
								"status": 1,
								"projId": $scope.projId
							};
							ProjectEmployeFactory.getProjectEmployeeDetails(empReq).then(function (data) {
								empRegisterDtlTO.id = data.plantEmployeeTO.id;
								empRegisterDtlTO.code = data.plantEmployeeTO.code;
								empRegisterDtlTO.name = data.plantEmployeeTO.name;
							}, function (error) {
								GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
							});
						};

						$scope.submitEmpTransferRequest = function () {

							if (!$scope.addNewRequestData.empTransReqApprDetailTOs ||
								$scope.addNewRequestData.empTransReqApprDetailTOs.length <= 0) {
								GenericAlertService.alertMessage("Please add Atleast one Employee to submit", "Warning");
								return;
							}
							if($scope.addNewRequestData.fromProjName == $scope.addNewRequestData.toProjName){
								 GenericAlertService.alertMessage("Origin Project and Destination Project should not be same", 'Warning');
							 return;
						}
							let isTransDate = false;
							let message = "Please choose Date of Transfer to ";
							for (let empReq of $scope.addNewRequestData.empTransReqApprDetailTOs) {
								if (!empReq.transDate) {
									isTransDate = true;
									message += empReq.empCode;
									break;
								}
							}

							if (isTransDate) {
								GenericAlertService.alertMessage(message, "Warning");
								return;
							}

							$scope.addNewRequestData.transType = true;
							$scope.addNewRequestData.apprStatus = 'Pending For Approval';
							var req = {
								"empReqTransTOs": [$scope.addNewRequestData],
								"status": 1,
								"empTransReq": {
									"transType": true,
									"status": 1,
									"onload": false,
									"notificationStatus" : 'Pending' 
								}
							};
							blockUI.start();
							EmpRegisterService.saveEmpTransfers(req).then(function (data) {
								blockUI.stop();
								// var succMsg = GenericAlertService.alertMessageModal('Employee Transfer request detail(s) is/are Submitted Successfully', 'Info');
								var succMsg = GenericAlertService.alertMessageModal('Employee Transfer request detail(s) submitted successfully', 'Info');
								succMsg.then(function () {
									$scope.closeThisDialog();
									var returnPopObj = {
										"empReqTransTOs": data.empReqTransTOs,
									};
									deferred.resolve(returnPopObj);
								});
							}, function (error) {
								blockUI.stop();
								GenericAlertService.alertMessage('Employee Transfer request detail(s) is/are Failed to Save ', "Error");
							});
						};
					}]
			});
			return deferred.promise;
		}
	return service;
}]);
