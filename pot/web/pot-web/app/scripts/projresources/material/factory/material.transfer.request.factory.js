'use strict';

app.factory('MaterialTransferReqFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectEmployeFactory", "EpsProjectSelectFactory", "MaterialTransferDetailsFactory", "EmployeeMasterDetailsFactory", "MaterialRegStoreFactory", "EmpReqApprUserFactory", "MaterialRegisterService", "UserService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjectEmployeFactory, EpsProjectSelectFactory, MaterialTransferDetailsFactory, EmployeeMasterDetailsFactory, MaterialRegStoreFactory, EmpReqApprUserFactory,
	MaterialRegisterService, UserService) {
	var service = {};
	service.getMaterialTransferDetails = function(actionType, editNewRequest, materialDataMap) {
		var deferred = $q.defer();
		if (actionType == 'Edit') {
			var req = {
				"id": editNewRequest.id,
				"status": 1
			};
			var resultData = MaterialRegisterService.getMaterialTranferDetails(req);
			resultData.then(function(data) {
				var popupdata = service.openPopup(materialDataMap, data.materialTransferReqApprTOs, data.transferMaterialMap);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting material request details", 'Error');
			});
		} else {
			var materialReqTransMap = [];
			var materialTransferReqApprTOs = [{
				"status": 1,
				"reqUserTO": {},
				"apprUserTO": {},
				"materialTransferReqApprDtlTOs": []
			}];
			var popupdata = service.openPopup(materialDataMap, materialTransferReqApprTOs, materialReqTransMap);
			popupdata.then(function(data) {
				deferred.resolve(data);
			});
		}
		return deferred.promise;

	}, service.openPopup = function(materialDataMap, materialTransferReqApprTOs, materialReqTransMap) {
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template: 'views/projresources/projmaterialreg/reqmaterialtransfer/materialregtransrequestpopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
			controller: ['$scope', function($scope) {

				var selectedMaterials = [];
				$scope.materialTransferReqApprTO = materialTransferReqApprTOs[0];
				$scope.userProjMap = materialDataMap.userProjMap;
				$scope.storeYardMap = materialDataMap.storeYardMap;
				$scope.materialReqTransMap = materialReqTransMap;

				$scope.projId = null;
				var newvalue1 = null;
				var oldvalue1 = null;
				$scope.originProject = {};
				$scope.destinationProject = {};
				$scope.activeFlag = false;

				$scope.materialTransferReqApprTO.reqDate = new Date();
				$scope.$watch('materialTransferReqApprTO.reqDate', function() {
					if ($scope.materialTransferReqApprTO.reqDate != null) {
						$scope.materialTransferReqApprTO.reqDate = $filter('date')(($scope.materialTransferReqApprTO.reqDate), "dd-MMM-yyyy")
					}
				});

				$scope.getUserProjects = function(type) {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function(data) {
						if (type == 1) {
							$scope.materialTransferReqApprTO.fromProjId = data.searchProject.projId;
							$scope.materialTransferReqApprTO.fromProjName = data.searchProject.projName;
						} else {
							$scope.materialTransferReqApprTO.toProjId = data.searchProject.projId;
							$scope.materialTransferReqApprTO.toProjName = data.searchProject.projName;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}

				$scope.selectStoreLocation = function(type, projId) {
					var resultData = MaterialRegStoreFactory.getStoreStocks(projId);
					resultData.then(function(data) {
						if (type == 1) {
							if (data.sourceType == 'Stock') {
								$scope.materialTransferReqApprTO.fromStoreId = data.stock.id;
								$scope.materialTransferReqApprTO.fromStoreProjectId = null;
							} else {
								$scope.materialTransferReqApprTO.fromStoreId = null;
								$scope.materialTransferReqApprTO.fromStoreProjectId = data.stock.id;
							}

							$scope.materialTransferReqApprTO.fromStoreName = data.stock.code;
						} else {
							if (data.sourceType == 'Stock') {
								$scope.materialTransferReqApprTO.toStoreId = data.stock.id;
								$scope.materialTransferReqApprTO.toStoreProjectId = null;
							} else {
								$scope.materialTransferReqApprTO.toStoreId = null;
								$scope.materialTransferReqApprTO.toStoreProjectId = data.stock.id;
							}

							$scope.materialTransferReqApprTO.toStoreName = data.stock.code;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting store location", 'Error');
					});
				}

				$scope.applyRequest = function() {

					if ($scope.materialTransferReqApprTO.fromProjId == undefined || $scope.materialTransferReqApprTO.fromProjId == null || $scope.materialTransferReqApprTO.toProjId == undefined
						|| $scope.materialTransferReqApprTO.toProjId == null || $scope.materialTransferReqApprTO.fromStoreName == null || $scope.materialTransferReqApprTO.fromStoreName == undefined
						|| $scope.materialTransferReqApprTO.toStoreName == null || $scope.materialTransferReqApprTO.toStoreName == undefined) {
						GenericAlertService.alertMessage("Please select the Origin Project or Origin Location or Destination Project or Destination Location", "Warning");
						return;
					}
					if ($scope.materialTransferReqApprTO.fromProjName == $scope.materialTransferReqApprTO.toProjName) {
						if ($scope.materialTransferReqApprTO.fromStoreName == $scope.materialTransferReqApprTO.toStoreName) {
							GenericAlertService.alertMessage('Origin & Destination Locations cannot be same for same project', "Warning");
							return;
						}
					}
					// if ($scope.materialTransferReqApprTO.fromProjId ==  $scope.materialTransferReqApprTO.toProjId) {
					// 	GenericAlertService.alertMessage(" Origin and Destination Project must be Different", "Warning");
					// 	return;

					// }
					$scope.activeFlag = true;

				}

				$scope.materialTransferReset = function() {
					$scope.activeFlag = false;
					$scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs = [];

					$scope.materialTransferReqApprTO.fromProjId = [];
					$scope.materialTransferReqApprTO.toProjId = [];
					$scope.materialTransferReqApprTO.fromProjName = [];
					$scope.materialTransferReqApprTO.fromStoreName = [];
					$scope.materialTransferReqApprTO.toProjName = [];
					$scope.materialTransferReqApprTO.toStoreName = [];
				};
				
				$scope.$watch(function() {
					return $scope.originProject.projName;
				}, function(newvalue, oldvalue) {
					newvalue1 = newvalue;
					oldvalue1 = oldvalue;
				}, true);
				$scope.getMaterialDetails = function() {
					if ($scope.activeFlag == false) {
						GenericAlertService.alertMessage("Please select apply button to add Materials", "Warning");
						return;
						
					}
					var req = {
						"status": 1,
						"projId": $scope.materialTransferReqApprTO.fromProjId,
						"toProjId": $scope.materialTransferReqApprTO.toProjId,
						"storeId": $scope.materialTransferReqApprTO.fromStoreId,
						"storeProjectId": $scope.materialTransferReqApprTO.fromStoreProjectId
					};
					var materialTransferExistingMap = [];
					angular.forEach($scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs, function(value, key) {
						materialTransferExistingMap[value.materialId] = true;
					});
					var materialTransferPopup = MaterialTransferDetailsFactory.getMaterialDetailsForTransfer(req, materialTransferExistingMap);
					materialTransferPopup.then(function(data) {
						angular.forEach(data.materialTransferReqApprDtlTOs, function(value, key) {
							$scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs.push({
								"status": 1,
								"materialId": value.id
							});
						});
						$scope.materialReqTransMap = data.materialReqTransMap;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting  registered material details", 'Error');
					});
				}

				$scope.getUserEmployees = function(type) {
					if ($scope.materialTransferReqApprTO.fromProjId == undefined || $scope.materialTransferReqApprTO.fromProjId == null || $scope.materialTransferReqApprTO.toProjId == undefined
						|| $scope.materialTransferReqApprTO.toProjId == null) {
						GenericAlertService.alertMessage("Please select the Origin and Destination Project", "Warning");
						return;
					}

					var req = {
						"status": 1,
						"moduleCode": "MATERIALTRANSFER",
						"actionCode": type,
					};
					if (type == 'SUBMIT') {
						req.permission = "RESOURCE_MATERIAL_MATERIALTRANSFER_SUBMIT";
					} else if (type == 'APPROVE') {
						req.permission = "RESOURCE_MATERIAL_MATERIALTRANSFER_APPROVE";
					}
					EmpReqApprUserFactory.getEmpUsers(req).then(function(data) {
						if (type == 'SUBMIT') {
							$scope.materialTransferReqApprTO.reqUserTO.userId = data.id;
							$scope.materialTransferReqApprTO.reqUserTO.userName = data.name;
							$scope.materialTransferReqApprTO.reqUserTO.empCode = data.code;
							$scope.materialTransferReqApprTO.reqUserTO.firstName = data.displayNamesMap['firstName'];
							$scope.materialTransferReqApprTO.reqUserTO.lastName = data.displayNamesMap['lastName'];
							$scope.materialTransferReqApprTO.reqUserTO.empDesg = data.displayNamesMap['classType'];
							$scope.materialTransferReqApprTO.reqUserTO.email = data.displayNamesMap['userEmail'];
							//	$scope.materialTransferReqApprTO.reqUserTO.projectName = data.displayNamesMap
							$scope.materialTransferReqApprTO.reqUserTO.phone = data.displayNamesMap['phone'];
							$scope.materialTransferReqApprTO.reqUserTO.projId = data.displayNamesMap['projId'];
							$scope.materialTransferReqApprTO.reqUserTO.projectName = data.displayNamesMap['projectName'];
							$scope.materialTransferReqApprTO.reqUserTO.cmpName = data.displayNamesMap['cmpName']

						}
						if (type == 'APPROVE') {
							$scope.materialTransferReqApprTO.apprUserTO.userId = data.id;
							$scope.materialTransferReqApprTO.apprUserTO.userName = data.name;
							$scope.materialTransferReqApprTO.apprUserTO.empCode = data.code;
							$scope.materialTransferReqApprTO.apprUserTO.firstName = data.displayNamesMap.firstName;
							$scope.materialTransferReqApprTO.apprUserTO.lastName = data.displayNamesMap.lastName;
							$scope.materialTransferReqApprTO.apprUserTO.empDesg = data.displayNamesMap.classType;
							$scope.materialTransferReqApprTO.apprUserTO.email = data.displayNamesMap.userEmail;
							$scope.materialTransferReqApprTO.apprUserTO.phone = data.displayNamesMap.phone;
							$scope.materialTransferReqApprTO.apprUserTO.projId = data.displayNamesMap.projId;
							$scope.materialTransferReqApprTO.apprUserTO.projectName = data.displayNamesMap.projectName;

						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting user details", "Error");
					});
				},

					$scope.materialRowSelect = function(request) {
						if (request.select || request.materialId != null) {
							console.log("record added 123 ", selectedMaterials.length + "request Id", request.materialId)
							selectedMaterials.push(request);
						} else {
							selectedMaterials.pop(request);
						}
					},

					$scope.deleteRows = function() {
						if (selectedMaterials.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						} else {
							angular.forEach(selectedMaterials, function(value, key) {
								$scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs.splice($scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs.indexOf(value), 1);
							});
							selectedMaterials = [];
							GenericAlertService.alertMessage('Row(s) is/are deleted Successfully', "Info");
						}
					}, $scope.qtyMismatch = false;
				var localqty = 0.0;
				var actualQty = 0.0;
				$scope.validateReceivedQty = function() {
					angular.forEach($scope.materialReqTransMap, function(value, key) {
						actualQty = value.displayNamesMap.qty;
					})
					angular.forEach($scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs, function(value, key) {
						if (!isNaN(value.reqQty) && value.reqQty != null) {
							localqty = value.reqQty;
						}
					});

					if (parseFloat(actualQty) >= parseFloat(localqty)) {
						$scope.qtyMismatch = false;
					} else {
						$scope.qtyMismatch = true;
						GenericAlertService.alertMessage("Required transfer quantity should be less than available quantity", "Warning");
					}

				}
				$scope.getRequesterById = function() {
					UserService.findByUserId($rootScope.account.userId).then(function(data) {						

						$scope.materialTransferReqApprTO.reqUserTO.empCode = data.empCode;
						$scope.materialTransferReqApprTO.reqUserTO.firstName = data.firstName;
						$scope.materialTransferReqApprTO.reqUserTO.lastName = data.lastName;
						$scope.materialTransferReqApprTO.reqUserTO.empDesg = data.empDesg;
						//$scope.materialTransferReqApprTO.fromProjName = data.name;
						$scope.materialTransferReqApprTO.reqUserTO.phone = data.phone;
						$scope.materialTransferReqApprTO.reqUserTO.email = data.email;
						$scope.materialTransferReqApprTO.reqUserTO.projectName=data.name;
						$scope.materialTransferReqApprTO.reqUserTO.userId = data.userId;

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting logged-in employee details", "Error");
					});
				}

				$scope.saveMaterialTransfers = function() {
					if ($scope.materialTransferReqApprTO.fromProjId == undefined || $scope.materialTransferReqApprTO.fromProjId == null || $scope.materialTransferReqApprTO.toProjId == undefined
						|| $scope.materialTransferReqApprTO.toProjId == null || $scope.materialTransferReqApprTO.fromStoreName == null || $scope.materialTransferReqApprTO.fromStoreName == undefined
						|| $scope.materialTransferReqApprTO.toStoreName == null || $scope.materialTransferReqApprTO.toStoreName == undefined) {
						GenericAlertService.alertMessage("Please select the Origin Project or Origin Location or Destination Project or Destination Location", "Warning");
						return;
					}
					if ($scope.materialTransferReqApprTO.fromProjName == $scope.materialTransferReqApprTO.toProjName) {
						if ($scope.materialTransferReqApprTO.fromStoreName == $scope.materialTransferReqApprTO.toStoreName) {
							GenericAlertService.alertMessage('Destination Location should change for same project, It can not be same', "Warning");
							return;
						}
					}
					if ($scope.activeFlag == false) {
						GenericAlertService.alertMessage("Please select apply button to add Materials", "Warning");
						return;
					}
					if ($scope.materialTransferReqApprTO.reqUserTO.empCode == null || $scope.materialTransferReqApprTO.reqUserTO.empCode == undefined || $scope.materialTransferReqApprTO.reqUserTO.empCode == '') {
						GenericAlertService.alertMessage('Select Requester Employee ID', "Warning");
						return;
					}
					if ($scope.materialTransferReqApprTO.apprUserTO.empCode == null || $scope.materialTransferReqApprTO.apprUserTO.empCode == undefined || $scope.materialTransferReqApprTO.apprUserTO.empCode == '') {
						GenericAlertService.alertMessage('Select Approver Employee ID', "Warning");
						return;
					}
					if ($scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs.length <= 0) {
						GenericAlertService.alertMessage('Please add Materials', "Warning");
						return;
					}
					for (const value of $scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs) {
						if (value.reqDate == null || value.reqDate == undefined || value.reqDate == ''
							|| value.reqQty == null || value.reqQty == undefined || value.reqQty == '') {
							GenericAlertService.alertMessage('Please select Required Transfer Date or Quantity', "Warning");
							return;
						}
					}
					if ($scope.qtyMismatch == true) {
						GenericAlertService.alertMessage('Required transfer quantity should be less than available quantity', "Warning");
						return;
					}
					var req = {
						"materialTransferReqApprTOs": [$scope.materialTransferReqApprTO],
						"status": 1,
						"materialTransReq": {
							"transType": false,
							"status": 1,
							"loginUser": true,
						},
						"projId": $scope.materialTransferReqApprTO.fromProjId
					}
					MaterialRegisterService.saveMaterialTransfers(req).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Material New Request Detail(s) saved successfully ', "Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Material New Request Detail(s) is/are Failed to Save ', "Error");
					});
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
