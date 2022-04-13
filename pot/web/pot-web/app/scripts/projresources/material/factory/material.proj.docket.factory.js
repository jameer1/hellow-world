'use strict';
app.factory('ProjDocketGenerateFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EpsProjectSelectFactory", "EmployeeMasterDetailsFactory", "PreContractStoreFactory", "GenericAlertService", "MaterialIssueDocketFactory", "ProjectEmployeFactory", "MaterialApprovalNotificationFactory", "MaterialRegisterService", function (ngDialog, $q, $filter, $timeout, $rootScope, EpsProjectSelectFactory, EmployeeMasterDetailsFactory, PreContractStoreFactory, GenericAlertService, MaterialIssueDocketFactory, ProjectEmployeFactory, MaterialApprovalNotificationFactory, MaterialRegisterService) {
	var service = {};
	service.projDocketDetails = function (actionType, itemData) {
		var deferred = $q.defer();
		var docketpopup = ngDialog.open({
			template: 'views/projresources/projmaterialreg/projdocket/materialprojdocketpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.companyMap = itemData.companyMap;
				$scope.classificationMap = itemData.classificationMap;
				$scope.userProjectMap = itemData.userProjectMap;
				$scope.empClassificationMap = itemData.empClassificationMap;
				$scope.applyFlag = false;
				$scope.action = actionType;
				$scope.materialDockReqType = 'INTERNAL';

				$scope.approvalType = 'Select';
				$scope.materialProjDocketTO = {};
				var selectedItem = [];
				var editStoreIssueDocket = [];
				$scope.submitType = itemData.materialProjDocketTO.apprStatus;

				$scope.approvalTypes = [
					{
						name: "Yes"
					}, {

						name: "No"
					}];

				if (actionType == 'Edit') {
					$scope.materialProjDocketTO = itemData.materialProjDocketTO;
					if (itemData.materialProjDocketTO.notifyLabelKeyTO != null && itemData.materialProjDocketTO.notifyLabelKeyTO.code != null) {
						$scope.approvalType = 'Yes';
					} else {
						$scope.approvalType = 'No';
					}
				} else {
					$scope.materialProjDocketTO = {
						"fromProjLabelkeyTO": {
							"id": null,
							"code": null,
							"name": null,
							"displayNamesMap": {
								"SM_ID": 0,
								"SM_CODE": null,
								"SM_CODE_CATEGORY": null,
								"SPM_ID": 0,
								"SPM_CODE": null,
								"SPM_CODE_CATEGORY": null
							}
						},
						"toProjLabelkeyTO": {
							"id": null,
							"code": null,
							"name": null,
							"displayNamesMap": {
								"SM_ID": 0,
								"SM_CODE": null,
								"SM_CODE_CATEGORY": null,
								"SPM_ID": 0,
								"SPM_CODE": null,
								"SPM_CODE_CATEGORY": null
							}
						},
						"notifyLabelKeyTO": {
							"id": null,
							"code": null,
							"name": null

						},
						"issuedByLabelkeyTO": {
							"id": null,
							"code": null,
							"name": null,
							"displayNamesMap": {
								"classType": null,
								"cmpId": null,
								"firstName": null,
								"lastName": null
							}
						},
						"receivedByLabelkeyTO": {
							"id": null,
							"code": null,
							"name": null,
							"displayNamesMap": {
								"classType": null,
								"cmpId": null,
								"firstName": null,
								"lastName": null
							}
						},
						"materialProjSchItemTOs": [],
						"projDocketReqStatus": null
					};
				}

				$scope.applyFilter = function () {
					
					if($scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE'] == $scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE']){
						GenericAlertService.alertMessage("Origin from and destination yards should not be same", 'Warning');
						return;
					}
					if ($scope.materialProjDocketTO.fromProjLabelkeyTO.id == null || $scope.materialProjDocketTO.toProjLabelkeyTO.id == null || $scope.approvalType == 'Select') {
						GenericAlertService.alertMessage("Please enter all input filter details for Schedule items", 'Warning');
						return;
					}

					if ($scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_ID'] == null && $scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_ID'] == null) {
						GenericAlertService.alertMessage("Please enter origin project store details for Schedule items", 'Warning');
						return;
					}

					if ($scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_ID'] == null && $scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_ID'] == null) {
						GenericAlertService.alertMessage("Please enter destination project store details for Schedule items", 'Warning');
						return;
					}

					if ($scope.approvalType == 'Yes' && $scope.materialProjDocketTO.notifyLabelKeyTO.id == null) {
						GenericAlertService.alertMessage("Please enter approval notification details", 'Warning');
						return;
					}
						if ($scope.approvalType == 'No' && $scope.materialProjDocketTO.notifyLabelKeyTO.id == null) {
						GenericAlertService.alertMessage("Filter applied successfully", 'Info');
						//return;
					}
					$scope.applyFlag = true;
					if ($scope.materialProjDocketTO.notifyLabelKeyTO.id) {
						let req = {
							materialNotificationId: $scope.materialProjDocketTO.notifyLabelKeyTO.id
						}
						MaterialRegisterService.getMaterialsForProjDocket(req).then(function (data) {
							$scope.materialProjDocketTO = data.materialProjDocketTOs[0];
							$scope.materialProjDocketTO.notifyCode = $scope.materialProjDocketTO.notifyLabelKeyTO.code;
						}, function (error) {
							if (error.message != null && error.status != null) {
								GenericAlertService.alertMessage(error.message, error.status);
							} else {
								GenericAlertService.alertMessage("Error occured while getting material project dockets.", "Error");
							}
						});
					}
				}, $scope.releaseApplyFilter = function () {
					$scope.applyFlag = false;
				}, $scope.reset = function () {
					$scope.applyFlag = false;
					resetOriginProject();
					resetOriginProjectData();
					resetDesinationProject();
					resetDestinationProjectData();
					resetNotificationData();
					$scope.storeIssueDocket = [];
					selectedItem = [];
					$scope.materialProjDocketTO.notifyCode = null;
					$scope.materialProjDocketTO.materialProjSchItemTOs = [];
				}

				if( actionType == 'Edit' ){
					$scope.applyFlag = true;
				}

				$scope.getUserProjects = function (type) {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function (data) {
						if (type == 1) {
							$scope.materialProjDocketTO.fromProjLabelkeyTO.id = data.searchProject.projId;
							$scope.materialProjDocketTO.fromProjLabelkeyTO.name = data.searchProject.projName;
							$scope.materialProjDocketTO.fromProjLabelkeyTO.code = data.searchProject.clientId;
						}
						if (type == 2) {
							$scope.materialProjDocketTO.toProjLabelkeyTO.id = data.searchProject.projId;
							$scope.materialProjDocketTO.toProjLabelkeyTO.name = data.searchProject.projName;
							$scope.materialProjDocketTO.toProjLabelkeyTO.code = data.searchProject.clientId;
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}

				$scope.validateProjects = function( type ){
					// Nothing Doing
				}

				function resetNotificationData() {
					$scope.materialProjDocketTO.notifyLabelKeyTO.id = null;
					$scope.materialProjDocketTO.notifyLabelKeyTO.code = null;
					$scope.materialProjDocketTO.notifyLabelKeyTO.name = null;
					$scope.approvalType = 'Select';
				}
				function resetOriginProject() {
					$scope.materialProjDocketTO.fromProjLabelkeyTO.id = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.name = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.code = null;
				}
				function resetDesinationProject() {
					$scope.materialProjDocketTO.toProjLabelkeyTO.id = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.name = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.code = null;
				}
				function resetOriginProjectData() {
					// on selection of origin project we need to reset the stock
					// and project stock data
					$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_ID'] = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE'] = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE_CATEGORY'] = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_ID'] = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_CODE'] = null;
					$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_CODE_CATEGORY'] = null;
					$scope.materialProjDocketTO.issuedByLabelkeyTO = {
						"id": null,
						"code": null,
						"name": null,
						"displayNamesMap": {
							"classType": null,
							"cmpId": null,
							"firstName": null,
							"lastName": null
						}
					};
					resetNotificationData();
					selectedItem = [];
				}
				function resetDestinationProjectData() {
					// on selection of destination project we need to reset the
					// stock and project stock data
					$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_ID'] = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE'] = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE_CATEGORY'] = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_ID'] = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_CODE'] = null;
					$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_CODE_CATEGORY'] = null;
					$scope.materialProjDocketTO.receivedByLabelkeyTO = {
						"id": null,
						"code": null,
						"name": null,
						"displayNamesMap": {
							"classType": null,
							"cmpId": null,
							"firstName": null,
							"lastName": null
						}
					};
					resetNotificationData();
				}
				var selectedProject = [];

				$scope.selectDeliveryPlace = function (type) {
					var clientId = null;
					var projId = null;

					if (type == 1) {
						projId = $scope.materialProjDocketTO.fromProjLabelkeyTO.id;
						clientId = $scope.materialProjDocketTO.fromProjLabelkeyTO.code;
					}
					if (type == 2) {
						projId = $scope.materialProjDocketTO.toProjLabelkeyTO.id;
						clientId = $scope.materialProjDocketTO.toProjLabelkeyTO.code;
					}

					if (projId == null) {
						if (type == 1) {
							GenericAlertService.alertMessage("Please select the Origin Project", 'Info');
						} else if (type == 2) {
							GenericAlertService.alertMessage("Please select the Destination Project", 'Info');
						}
						return;
					}
					var storeYardPopup = PreContractStoreFactory.getStocks(projId);
					storeYardPopup.then(function (data) {

						if (type == 1) {
							if (data.storeStockTO.clientId != undefined && data.storeStockTO.clientId != null) {
								// store Stock
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_ID'] = data.storeStockTO.id;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE'] = data.storeStockTO.desc;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE_CATEGORY'] = data.storeStockTO.category;
								// reset origin project Project store stock
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_ID'] = null;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_CODE'] = null;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_CODE_CATEGORY'] = null;

							} else {
								// Project Stock
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_ID'] = data.storeStockTO.id;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_CODE'] = data.storeStockTO.desc;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_CODE_CATEGORY'] = data.storeStockTO.category;
								// reset origin project store Stock
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_ID'] = null;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE'] = null;
								$scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_CODE_CATEGORY'] = null;
							}
						}

						if (type == 2) {

							if (data.storeStockTO.clientId != undefined && data.storeStockTO.clientId != null) {
								// store Stock
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_ID'] = data.storeStockTO.id;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE'] = data.storeStockTO.desc;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE_CATEGORY'] = data.storeStockTO.category;
								$scope.sourceType = 'SM';
								// reset destination Project project store stock
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_ID'] = null;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_CODE'] = null;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_CODE_CATEGORY'] = null;

							} else {
								// Project Stock
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_ID'] = data.storeStockTO.id;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_CODE'] = data.storeStockTO.desc;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SPM_CODE_CATEGORY'] = data.storeStockTO.category;
								$scope.sourceType = 'SPM';
								// reset destination project store stock
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_ID'] = null;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE'] = null;
								$scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap['SM_CODE_CATEGORY'] = null;
							}
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting store delivery details", 'Error');
					});
				},
					$scope.selectNotification = function () {
						$scope.materialDockReqType = 'EXTERNAL'
						if ($scope.approvalType == 'Yes') {
							var notificationReq = {
								"projId": $scope.materialProjDocketTO.fromProjLabelkeyTO.id,
								"toProjId": $scope.materialProjDocketTO.toProjLabelkeyTO.id,
								"notificationStatus": 'APPROVED',
								"fromStoreId" : $scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap.SM_ID,
								"fromStoreProjectId" : $scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap.SPM_ID,
								"toStoreId" : $scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap.SM_ID,
								"toStoreProjectId" : $scope.materialProjDocketTO.toProjLabelkeyTO.displayNamesMap.SPM_ID
							}
							var notifiactionPopup = MaterialApprovalNotificationFactory.getNotificationDetails(notificationReq);
							notifiactionPopup.then(function (data) {
								$scope.materialProjDocketTO.notifyLabelKeyTO.id = data.selectedRecord.id;
								$scope.materialProjDocketTO.notifyCode = data.selectedRecord.code;
							});
						} else {
							GenericAlertService.alertMessage("Notification ID is not applicable for non-restricted schedule items", 'Warning');
						}

					}, $scope.addStoreDocket = function () {
						if ($scope.applyFlag == false) {
							GenericAlertService.alertMessage("Please select apply button to add Materials", "Warning");
							return;

						}
						if ($scope.applyFlag) {
							var originStockLoationId = null;
							var originProjStockLoationId = null;
							var materialDockReqType = null;

							if ($scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_ID'] != null) {
								originStockLoationId = $scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SM_ID'];
							}

							if ($scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_ID'] != null) {
								originProjStockLoationId = $scope.materialProjDocketTO.fromProjLabelkeyTO.displayNamesMap['SPM_ID'];
							}

							var req = {
								"reqProjLabelKeyTO": {
									"id": $scope.materialProjDocketTO.fromProjLabelkeyTO.id,
									"displayNamesMap": {
										"stockId": originStockLoationId,
										"projStockId": originProjStockLoationId
									},

								},
								"materialDockReqType": $scope.materialDockReqType
							};
							var existSchMap = [];
							angular.forEach($scope.materialProjDocketTO.materialProjSchItemTOs, function (value, key) {
								existSchMap[value.schItemId] = true;
							});

							var storeissuePopup = MaterialIssueDocketFactory.getMaterialSchItemsByProjectAndLoc(req, existSchMap);
							storeissuePopup.then(function (data) {
								angular.forEach(data.materialProjSchItemTOs, function (value, key) {
									$scope.materialProjDocketTO.materialProjSchItemTOs.push(value);
								});
							}, function (error) {
								if (error.message != null && error.status != null) {
									GenericAlertService.alertMessage(error.message, error.status);
								} else {
									GenericAlertService.alertMessage("Error occured while gettting  material issue docket", 'Error');
								}
							});
						} else {
							GenericAlertService.alertMessage("Please click on apply filter to get schedule items", 'Warning');
						}
					}

				$scope.storeDocketRowSelect = function (storedocket) {
					if (storedocket.select) {
						selectedItem.push(storedocket);
					} else {
						selectedItem.pop(storedocket);
					}
				}, $scope.deleteRows = function () {
					if ($scope.applyFlag == false) {
						GenericAlertService.alertMessage("Please select apply button to Delete Materials", "Warning");
						return;

					}
					if (selectedItem.length > 0) {
						angular.forEach(selectedItem, function (value, key) {
							$scope.materialProjDocketTO.materialProjSchItemTOs.splice($scope.materialProjDocketTO.materialProjSchItemTOs.indexOf(value), 1);
						});
						selectedItem = [];
					} else {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}

				}, $scope.validateIssueQty = function (storedocket) {
					let issueQty = parseFloat(storedocket.issueQty);
					// parseFloat(storedocket.purchaseSchLabelKeyTO.displayNamesMap.qty)
					// 	- parseFloat(storedocket.purchaseSchLabelKeyTO.displayNamesMap.remQty)
					let actualQty = storedocket.currentAvaiableQty;
					if (issueQty > actualQty) {
						storedocket.qtyMismatch = true;
					} else {
						storedocket.qtyMismatch = false
					}
					if (storedocket.qtyMismatch) {
						qtyMismatchMsg();
					}
				}

				function qtyMismatchMsg() {
					GenericAlertService.alertMessage('Issue quantity can not be more than avaialble quantity', "Warning");
				}

				$scope.getEmployeeDetails = function (type) {
					var projId = null;
					if (type == 1) {
						projId = $scope.materialProjDocketTO.fromProjLabelkeyTO.id;
						
						//for origin project
					/*if($scope.materialProjDocketTO.fromProjLabelkeyTO.name == $scope.materialProjDocketTO.toProjLabelkeyTO.name){
						GenericAlertService.alertMessage("Origin Project and Destination Project should not be same", 'Warning');
							return;
						}*/
					}
					if (type == 2) {
						projId = $scope.materialProjDocketTO.toProjLabelkeyTO.id;
						
						//for destination project
					/*if($scope.materialProjDocketTO.fromProjLabelkeyTO.name == $scope.materialProjDocketTO.toProjLabelkeyTO.name){
						GenericAlertService.alertMessage("Origin Project and Destination Project should not be same", 'Warning');
							return;
						}*/
					}
					if (projId == null) {
						if (type == 1) {
							GenericAlertService.alertMessage("Please select the Origin Project", 'Info');
						} else if (type == 2) {
							GenericAlertService.alertMessage("Please select the Destination Project", 'Info');
						}
						return;
					}
					var getEmployeeRegisterReq = {
						"projId": projId
					};

					EmployeeMasterDetailsFactory.getEmployeeMasterDetails(getEmployeeRegisterReq).then(function (data) {
						if (type == 1) {
							$scope.materialProjDocketTO.issuedByLabelkeyTO.id = data.employeeMasterTO.id;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.code = data.employeeMasterTO.empCode;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.displayNamesMap.firstName = data.employeeMasterTO.firstName;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.displayNamesMap.lastName = data.employeeMasterTO.lastName;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.displayNamesMap.classType = data.employeeMasterTO.empClassId;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.displayNamesMap.empClassName = data.employeeMasterTO.empClassName;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.displayNamesMap.cmpId = data.employeeMasterTO.cmpId;
							$scope.materialProjDocketTO.issuedByLabelkeyTO.displayNamesMap.cmpName = data.employeeMasterTO.cmpName;

						}
						if (type == 2) {
							$scope.materialProjDocketTO.receivedByLabelkeyTO.id = data.employeeMasterTO.id;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.code = data.employeeMasterTO.empCode;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.displayNamesMap.firstName = data.employeeMasterTO.firstName;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.displayNamesMap.lastName = data.employeeMasterTO.lastName;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.displayNamesMap.classType = data.employeeMasterTO.empClassId;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.displayNamesMap.cmpName = data.employeeMasterTO.cmpName;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.displayNamesMap.empClassName = data.employeeMasterTO.empClassName;
							$scope.materialProjDocketTO.receivedByLabelkeyTO.displayNamesMap.cmpId = data.employeeMasterTO.cmpId;

						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting registered employee details", "Error");
					});
				}, $scope.generateProjectDocket = function (submitType) {
					if ($scope.applyFlag == false) {
						GenericAlertService.alertMessage("Please select apply button to Submit Materials", "Warning");
						return;

					}
					if ($scope.materialProjDocketTO.issuedByLabelkeyTO.id == null || $scope.materialProjDocketTO.issuedByLabelkeyTO.id == undefined) {
						GenericAlertService.alertMessage("Please select the Employee to whom you want to Issue", "Warning");
						return;

					}
					if ($scope.materialProjDocketTO.receivedByLabelkeyTO.id == null || $scope.materialProjDocketTO.receivedByLabelkeyTO.id == undefined) {
						GenericAlertService.alertMessage("Please select the Employee to whom you want to Receive", "Warning");
						return;

					}
					if ($scope.materialProjDocketTO.materialProjSchItemTOs.length <= 0) {
						GenericAlertService.alertMessage("Please  add Atleast one Material to submit", "Warning");
						return;

					}
					var misMatch = false;
					angular.forEach($scope.storeIssueDocket, function (value, key) {
						if (value.qtyMismatch) {
							misMatch = true;
							GenericAlertService.alertMessage('Issue quantity can not be greater than avaialble quantity', "Warning");
							return;
						}
					});
					if (misMatch) {
						return;
					}

					angular.forEach($scope.materialProjDocketTO.materialProjSchItemTOs, function (schItem) {
						schItem.openingStock = schItem.currentAvaiableQty;
						schItem.closingStock = schItem.currentAvaiableQty - schItem.issueQty;
					});

					$scope.materialProjDocketTO.apprStatus = submitType;
					$scope.materialProjDocketTO.status = 1;
					$scope.materialProjDocketTO.sourceType = $scope.sourceType;
					console.log($scope.materialProjDocketTO);
					var req = {
						"materialProjDocketTO": $scope.materialProjDocketTO,
						"apprStatus": submitType,
						"status": 1
					}
					MaterialRegisterService.saveMaterialProjDockets(req).then(function (data) {
						$scope.submitType = 'Generated';
						// var succMsg = GenericAlertService.alertMessageModal('Project docket details ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Project Docket details saved successfully',"Info");
						succMsg.then(function () {
							$scope.closeThisDialog();
							var returnPopObj = {
								"materialProjDocketTOs": data.materialProjDocketTOs
							}
							deferred.resolve(returnPopObj);
						})
					}, function (error) {
						if (error.message != null && error.status != null) {
							GenericAlertService.alertMessage(error.message, error.status);
						} else {
							GenericAlertService.alertMessage("Error occured while generating project dockets", "Error");
						}

					});
				}

			}]

		});
		return deferred.promise;
	}
	return service;
}]);
