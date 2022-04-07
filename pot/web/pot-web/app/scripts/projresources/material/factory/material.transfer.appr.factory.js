'use strict';

app.factory('MaterialTransferApprFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectEmployeFactory", "EpsProjectSelectFactory", "MaterialTransferDetailsFactory", "EmployeeMasterDetailsFactory", "MaterialRegStoreFactory", "EmpReqApprUserFactory", "MaterialRegisterService", "RequestForMaterialAdditionalTimeFactory", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjectEmployeFactory, EpsProjectSelectFactory, MaterialTransferDetailsFactory, EmployeeMasterDetailsFactory, MaterialRegStoreFactory, EmpReqApprUserFactory, MaterialRegisterService, RequestForMaterialAdditionalTimeFactory) {
			var service = {};
			service.getMaterialTransferDetails = function(actionType, editNewRequest, materialDataMap) {
				var deferred = $q.defer();
				
				if (actionType == 'Edit') {
					var req = {
						"id" : editNewRequest.id,
						"status" : 1
					};
					
					var resultData = MaterialRegisterService.getMaterialTranferDetails(req);
					resultData.then(function(data) {
						var popupdata = service.openPopup(materialDataMap, data.materialTransferReqApprTOs, data.transferMaterialMap, data.timeFlag, editNewRequest);
						popupdata.then(function(data) {
							deferred.resolve(data);
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting material request details", 'Error');
					});
				} else {
					var materialReqTransMap = [];
					var materialTransferReqApprTOs = [ {
						"status" : 1,
						"reqUserTO" : {},
						"apprUserTO" : {},
						"materialTransferReqApprDtlTOs" : []
					} ];
					var popupdata = service.openPopup(materialDataMap, materialTransferReqApprTOs, materialReqTransMap, editNewRequest);
					popupdata.then(function(data) {
						deferred.resolve(data);
					});
				}
				return deferred.promise;

			}, service.openPopup = function(materialDataMap, materialTransferReqApprTOs, materialReqTransMap, timeFlag, editNewRequest) {
				var deferred = $q.defer();
				var popupdata = ngDialog.open({
					templateUrl : 'views/projresources/projmaterialreg/approvalmaterialtransfer/materialregtransapprovalpopup.html',
					closeByDocument : false,
					showClose : false,
					className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
					controller : [ '$scope', function($scope) {

						var selectedMaterials = [];
						$scope.editNewRequest=editNewRequest;
						$scope.materialTransferReqApprTO = materialTransferReqApprTOs[0];
						$scope.userProjMap = materialDataMap.userProjMap;
						$scope.storeYardMap = materialDataMap.storeYardMap;
						$scope.materialReqTransMap = materialReqTransMap;
						$scope.timeFlag = timeFlag;
						$scope.projId = null;
						var newvalue1 = null;
						var oldvalue1 = null;
						$scope.originProject = {};
						$scope.destinationProject = {};

						$scope.getUserProjects = function(type) {
							var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
							userProjectSelection.then(function(data) {
								if (type == 1) {
									$scope.materialTransferReqApprTO.fromProjId = data.searchProject.projId;
								} else {
									$scope.materialTransferReqApprTO.toProjId = data.searchProject.projId;
								}
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
							});
						}

						$scope.selectStoreLocation = function(type) {
							var resultData = MaterialRegStoreFactory.getStoreStocks();
							resultData.then(function(data) {
								if (type == 1) {
									$scope.materialTransferReqApprTO.fromStoreId = data.id;
								} else {
									$scope.materialTransferReqApprTO.toStoreId = data.id;
								}
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while selecting store location", 'Error');
							});
						}
						var assignProject = null;
						$scope.applyRequest = function(projId) {
							assignProject = projId;
							if (assignProject == undefined || assignProject == null) {
								GenericAlertService.alertMessage("Please select the Project", "Warning");
								return;
							}

						}
						$scope.$watch(function() {
							return $scope.originProject.projName;
						}, function(newvalue, oldvalue) {
							newvalue1 = newvalue;
							oldvalue1 = oldvalue;
						}, true);
						$scope.getMaterialDetails = function() {
							if ($scope.materialTransferReqApprTO.fromProjId == undefined || $scope.materialTransferReqApprTO.fromProjId == null || $scope.materialTransferReqApprTO.fromProjId == undefined || $scope.materialTransferReqApprTO.fromProjId == null) {
								GenericAlertService.alertMessage("Please select the Origin and Destination Project", "Warning");
								return;
							}
							var req = {
								"status" : 1,
								"projId" : $scope.materialTransferReqApprTO.fromProjId,
								"toProjId" : $scope.materialTransferReqApprTO.toProjId,
								"storeId" : $scope.materialTransferReqApprTO.fromStoreId
							};
							var materialTransferExistingMap = [];
							angular.forEach($scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs, function(value, key) {
								materialTransferExistingMap[value.materialId] = true;
							});
							var materialTransferPopup = MaterialTransferDetailsFactory.getMaterialDetailsForTransfer(req, materialTransferExistingMap);
							materialTransferPopup.then(function(data) {
								angular.forEach(data.materialTransferReqApprDtlTOs, function(value, key) {
									$scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs.push({
										"status" : 1,
										"materialId" : value.id
									});
								});
								$scope.timeFlag = data.timeFlag;
								$scope.materialReqTransMap = data.materialReqTransMap;
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while gettting  registered material details", 'Error');
							});
						}
						
						$scope.getUserEmployees = function(type) {
							var req = {
								"status" : 1,
								"moduleCode" : "MATERIALTRANSFER",
								"actionCode" : type,
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
									$scope.materialTransferReqApprTO.reqUserTO.phone = data.displayNamesMap['phone'];
									$scope.materialTransferReqApprTO.reqUserTO.projId = data.displayNamesMap['projId'];

								}
								if (type == 'APPROVE') {
									$scope.materialTransferReqApprTO.apprUserTO.userId = data.id;
									$scope.materialTransferReqApprTO.apprUserTO.userName = data.name;
									$scope.materialTransferReqApprTO.apprUserTO.empCode = data.code;
									$scope.materialTransferReqApprTO.apprUserTO.firstName = data.displayNamesMap['firstName'];
									$scope.materialTransferReqApprTO.apprUserTO.lastName = data.displayNamesMap['lastName'];
									$scope.materialTransferReqApprTO.apprUserTO.empDesg = data.displayNamesMap['classType'];
									$scope.materialTransferReqApprTO.apprUserTO.email = data.displayNamesMap['userEmail'];
									$scope.materialTransferReqApprTO.apprUserTO.phone = data.displayNamesMap['phone'];
									$scope.materialTransferReqApprTO.apprUserTO.projId = data.displayNamesMap['projId'];

								}
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while getting user details", "Error");
							});
						}
						//to get approval date
						if($scope.materialTransferReqApprTO.apprDate == null){
							$scope.date = new Date();
							$scope.materialTransferReqApprTO.apprDate = $filter('date')($scope.date, "dd-MMM-yyyy")
						}
						

						$scope.qtyMismatch = false;
						var localqty = 0.0;
						var actualQty = 0.0;
						$scope.validateReceivedQty = function() {

							angular.forEach($scope.materialTransferReqApprTO.materialTransferReqApprDtlTOs, function(value, key) {
								if (!isNaN(value.reqQty) && value.reqQty != null) {
									actualQty = value.reqQty;
								}
								if (!isNaN(value.apprQty) && value.apprQty != null) {
									localqty = value.apprQty;
								}
							});

							if (parseFloat(actualQty) >= parseFloat(localqty)) {
								$scope.qtyMismatch = false;
							} else {
								$scope.qtyMismatch = true;
								GenericAlertService.alertMessage("Approved quantity value is mismatch with actuals", "Info");
							}

						}
						 $scope.saveMaterialTransfers = function(apprStatus) {
							$scope.materialTransferReqApprTO.apprStatus = apprStatus;
							var req = {
								"materialTransferReqApprTOs" : [ $scope.materialTransferReqApprTO ],
								"status" : 1,
								"materialTransReq" : {
									"transType" : false,
									"status" : 1,
									"loginUser" : true,
									"apprStatus" : apprStatus
								},
								"projId" : $scope.materialTransferReqApprTO.fromProjId
							}
							var succMsg;
							MaterialRegisterService.saveMaterialTransfers(req).then(function(data) {
								if (apprStatus == 'Approved') {
									succMsg = GenericAlertService.alertMessageModal('Material New Request Detail(s) Approved successfully',"Info" );
								} else if (apprStatus == 'Rejected') {
									succMsg = GenericAlertService.alertMessageModal('Material New Request Detail(s) Rejected successfully',"Info" );
								}

								succMsg.then(function() {
									$scope.closeThisDialog();
									deferred.resolve(data);
								});
							}, function(error) {
								GenericAlertService.alertMessage('Material New Request Detail(s) is/are Failed to Approve/Reject ', "Error");
							});
						}
						 $scope.requestForAdditionalTime = function (apprStatus) {
							 console.log($scope.editNewRequest);
								if (apprStatus != 'Pending For Approval') {
									GenericAlertService.alertMessage("Please Select Pending For Approval", 'Warning');
									return;
								}
								var reqForAdditionalTimePopUp = RequestForMaterialAdditionalTimeFactory.getAdditionalTimeDetails($scope.editNewRequest);
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
