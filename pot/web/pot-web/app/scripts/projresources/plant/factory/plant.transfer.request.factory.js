'use strict';
app.factory('PlantTransferRequestFactory', ["ngDialog", "$q", "blockUI", "$filter", "$rootScope", "GenericAlertService", "EpsProjectSelectFactory", "PlantTranserDetailsFactory", "EmpReqApprUserFactory", "PlantRegisterService", "UserService", function (ngDialog, $q, blockUI, $filter, $rootScope, GenericAlertService,
	EpsProjectSelectFactory, PlantTranserDetailsFactory, EmpReqApprUserFactory, PlantRegisterService, UserService) {
	var service = {};
	service.getPlantTransRequestDetails = function (actionType, editNewRequest) {
		var deferred = $q.defer();
		if (actionType == 'Edit') {
			var plantReq = [editNewRequest];
			var popupdata = service.openPopup(plantReq);
			popupdata.then(function (data) {
				deferred.resolve(data);
			});
		} else {
			var plantTransferReqApprTOs = [{
				"notificationStatus" : 'Pending',
				"status": 1,
				"reqUserTO": {},
				"apprUserTO": {},
				"plantTransReqApprDtlTOs": []
			}];
			var popupdata = service.openPopup(plantTransferReqApprTOs);
			popupdata.then(function (data) {
				deferred.resolve(data);
			});
		}
		return deferred.promise;

	}, service.openPopup = function (plantTransferReqApprTOs, transferPlantMap) {
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template: 'views/projresources/projplantreg/requestForTransfer/plantregtransferreqpopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0-5',
			controller: [
				'$scope',
				function ($scope) {
					var selectedPlantNewRequest = [];
					$scope.plantTransReqData = plantTransferReqApprTOs[0];
					$scope.activeFlag = false;
					$scope.projId = null;
					var newvalue1 = null;
					var oldvalue1 = null;
					$scope.originProject = {};
					$scope.destinationProject = {};
					if ($scope.plantTransReqData.reqDate == null) {
						$scope.date = new Date();
						$scope.plantTransReqData.reqDate = $filter('date')($scope.date, "dd-MMM-yyyy")
					}

					$scope.getUserProjects = function (type) {
						var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
						userProjectSelection.then(function (data) {
							if (type == 1) {
								$scope.plantTransReqData.fromProjId = data.searchProject.projId;
								$scope.plantTransReqData.fromProjName = data.searchProject.projName;
							} else {
								$scope.plantTransReqData.toProjId = data.searchProject.projId;
								$scope.plantTransReqData.toProjName = data.searchProject.projName;
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

					$scope.addPlantDetails = function () {
						if (!$scope.plantTransReqData.fromProjId) {
							GenericAlertService.alertMessage("Please select the source project", "Warning");
							return;
						}
						var req = {
							"status": 1,
							"projId": $scope.plantTransReqData.fromProjId
						};

						if (!$scope.plantTransReqData.plantTransReqApprDtlTOs) {
							$scope.plantTransReqData.plantTransReqApprDtlTOs = [];
						}

						var plantTransferExistingMap = [];
						angular.forEach($scope.plantTransReqData.plantTransReqApprDtlTOs, function (value, key) {
							plantTransferExistingMap[value.plantId] = true;
						});
						var plantTransferPopup = PlantTranserDetailsFactory.getPlantTransferReqDetails(req, plantTransferExistingMap);
						plantTransferPopup.then(function (data) {
							angular.forEach(data.plantRegisterDtlTOs, function (value, key) {
								$scope.plantTransReqData.plantTransReqApprDtlTOs.push({
									"status": 1,
									"plantId": value.id,
									"plantAssetId": value.code,
									"plantDesc": value.name,
									"plantRegNo": value.displayNamesMap.plntRegNo,
									"plantManfature": value.displayNamesMap.plntManfature,
									"plantModel": value.displayNamesMap.plntModel,
									"expectedTransDate": value.displayNamesMap.expectedDate,
									"deMob": $filter('date')(value.demobDate,"dd-MMM-yyyy")
								});
							});
							console.log($scope.plantTransReqData.plantTransReqApprDtlTOs)
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while gettting  registered plant details", 'Error');
						});
					};

					$scope.getEmployeeByUserId = function () {
						if (!$scope.plantTransReqData.reqUserTO.userId) {
							UserService.findByUserId($rootScope.account.userId).then(function (data) {
								$scope.plantTransReqData.reqUserTO.userId = data.userId;
								$scope.plantTransReqData.reqUserTO.empCode = data.empCode;
								$scope.plantTransReqData.reqUserTO.firstName = data.firstName;
								$scope.plantTransReqData.reqUserTO.lastName = data.lastName;
								$scope.plantTransReqData.reqUserTO.empDesg = data.empDesg;
								$scope.plantTransReqData.reqUserTO.email = data.email;
								$scope.plantTransReqData.reqUserTO.phone = data.phone;
								$scope.plantTransReqData.reqUserTO.projId = data.projId;
								$scope.plantTransReqData.reqCurrentProj = data.name;
							}, function (error) {
								GenericAlertService.alertMessage("Error occured while getting logged-in employee details", "Error");
							});
						}
					};

					$scope.getUserEmployees = function (type) {
						if ($scope.plantTransReqData.fromProjId == undefined || $scope.plantTransReqData.fromProjId == null || $scope.plantTransReqData.toProjId == undefined
							|| $scope.plantTransReqData.toProjId == null) {
							GenericAlertService.alertMessage("Please select the Origin and Destination Project", "Warning");
							return;
						}
						var req = {
							"status": 1,
							"moduleCode": "PLANTTRANSFER",
							"actionCode": type,
						};
						if (type == 'APPROVE') {
							req.permission = "RESOURCE_PLANT_PLANTTRANSFER_APPROVE";
							EmpReqApprUserFactory.getEmpUsers(req).then(function (data) {
								$scope.plantTransReqData.apprUserTO.userId = data.id;
								$scope.plantTransReqData.apprUserTO.userName = data.name;
								$scope.plantTransReqData.apprUserTO.empCode = data.code;
								$scope.plantTransReqData.apprUserTO.firstName = data.displayNamesMap['firstName'];
								$scope.plantTransReqData.apprUserTO.lastName = data.displayNamesMap['lastName'];
								$scope.plantTransReqData.apprUserTO.empDesg = data.displayNamesMap['classType'];
								$scope.plantTransReqData.apprUserTO.email = data.displayNamesMap['userEmail'];
								$scope.plantTransReqData.apprUserTO.phone = data.displayNamesMap['phone'];
								$scope.plantTransReqData.apprUserTO.projId = data.displayNamesMap['projId'];
								$scope.plantTransReqData.apprCurrentProj = data.displayNamesMap['projectName'];
							}, function (error) {
								GenericAlertService.alertMessage("Error occured while getting user details", "Error");
							});
						}
					};

					$scope.plantRowSelect = function (request) {
						if (request.select) {
							selectedPlantNewRequest.push(request);
						} else {
							selectedPlantNewRequest.pop(request);
						}
					};

					$scope.deleteRows = function () {
						if (selectedPlantNewRequest.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to Delete', "Warning");
						} else {
							angular.forEach(selectedPlantNewRequest, function (value, key) {
								$scope.plantTransReqData.plantTransReqApprDtlTOs.splice($scope.plantTransReqData.plantTransReqApprDtlTOs.indexOf(value), 1);
							});
							selectedPlantNewRequest = [];
							GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
						}
					};

					$scope.submitReqTransferDetails = function () {
					
					if($scope.plantTransReqData.fromProjName == $scope.plantTransReqData.toProjName){
						GenericAlertService.alertMessage("Origin project and Destination project should not be same", "Warning");
							return;
					}
						if ($scope.plantTransReqData.plantTransReqApprDtlTOs.length <= 0) {
							GenericAlertService.alertMessage("Please  add Atleast one Plant to submit", "Warning");
							return;
						}
						
						let validateTrans = false;
						for (const value of $scope.plantTransReqData.plantTransReqApprDtlTOs) {
							if (!value.transDate) {
								validateTrans = true;
								break
							}
						}
						if (validateTrans) {
							GenericAlertService.alertMessage("Please add required date of transfer", "Warning");
							return;
						}
						var req = {
							"plantTransferReqApprTOs": [$scope.plantTransReqData],
							"status": 1,
							"transType": true,
							"loginUser": true,
							"projId": $scope.plantTransReqData.fromProjId
						}
						blockUI.start();
						PlantRegisterService.savePlantTransfers(req).then(function (data) {
							blockUI.stop();
							// var succMsg = GenericAlertService.alertMessageModal('Plant New Request Detail(s) is/are  ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Plant New Request Detail(s) saved successfully',"Info");
							succMsg.then(function () {
								$scope.closeThisDialog();
								deferred.resolve(data);
							});
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Plant New Request Detail(s) is/are Failed to Save ', "Error");
						});
					};
				}]
		});
		return deferred.promise;
	}
	return service;
}]);
