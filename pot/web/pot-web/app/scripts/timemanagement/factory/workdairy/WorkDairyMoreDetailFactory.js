'use strict';

app.factory('WorkDairyMoreDetailFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "GenericAlertService", "generalservice", "WorkDiaryService", "WorkDairyAddProgressFactory", "WorkDairyCopyFactory", "RequestForAdditionalTimeFactory", "WorkDairyProgressFactory", "WorkDairyEmpFactory", "WorkDairyPlantFactory", "WorkDairyMaterialProjDocketFactory", "WorkDairyCostCodeFactory", "WageFactory", "RegisterPurchaseOrderFactory", "RegisterPurchaseOrderItemsFactory", "WorkDairyMaterialDeliveryDocketFactory", "WorkDairySubmitFactory","PlantRegisterService","$rootScope", function(ngDialog, $q, $filter, $timeout, blockUI, GenericAlertService, generalservice, WorkDiaryService, WorkDairyAddProgressFactory, WorkDairyCopyFactory, RequestForAdditionalTimeFactory, WorkDairyProgressFactory, WorkDairyEmpFactory, WorkDairyPlantFactory, WorkDairyMaterialProjDocketFactory, WorkDairyCostCodeFactory, WageFactory, RegisterPurchaseOrderFactory, RegisterPurchaseOrderItemsFactory, WorkDairyMaterialDeliveryDocketFactory, WorkDairySubmitFactory,PlantRegisterService,$rootScope) {
	var attendancePopup;
	var service = {};
	service.openPopup = function(workDairyTO) {
		var deferred = $q.defer();
		attendancePopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairymoredetail.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.workDairyTO = workDairyTO;
				$scope.currentTab = 'views/timemanagement/workdairy/createworkdairy/workdairyprogress.html';
				$scope.selectedEmp = null;
				$scope.selectedIndex = null;
				$scope.selectedRow = null;
				$scope.timeFlag = false;
				var editMans = [];
				var editPlants = [];
				var editMatestores = [];
				var editProgress = [];
				$scope.workDairyCostCodeMap = [];
				$scope.empWageFactorMap = [];
				$scope.empRegMap = [];
				$scope.weatherMap = [];
				$scope.projShiftMap = [];
				$scope.total = 0;
				$scope.companyMap = [];
				$scope.plantRegMap = [];
				$scope.materialRegMap = [];
				$scope.sowMap = [];
				$scope.workDairyEmpDtlTOs = [];
				$scope.workDairyPlantDtlTOs = [];
				$scope.workDairyMaterialDtlTOs = [];
				$scope.workDairyProgressDtlTOs = [];
				$scope.workDairyCostCodeList = [];
				$scope.activeFlag = false;
				$scope.errorEmpFlag = false;
				$scope.errorPlantFlag = false;
				$scope.errorProgressFlag = false;
				$scope.errorMaterialFlag = false;
				$scope.companyMap = [];
				$scope.classificationMap = [];
				$scope.userProjectMap = [];
				$scope.materialProjDtlMap = [];
				$scope.disableButton = false;
				$scope.getProjSettingsWorkDairyDetails = [];
				$scope.empFlag = false;
				$scope.manpowerForm = false;
				$scope.plantFlag = false;
				$scope.materialFlag = false;
				$scope.progressFlag = false;
				$scope.flag = 0;
				$scope.selectedFileMap = [];
				$scope.selectedWorkdairyIds = [];
				$scope.selectedWorkdairyManpowerIds = [];
				$scope.selectedWorkdairyPlantIds = [];
				$scope.workDairySearchReq = {
					"workDairyId": workDairyTO.id,
					"code": workDairyTO.workDiaryCode,
					"workDairyDate": workDairyTO.workDairyDate,
					"projectLabelKeyTO": {
						"projId": workDairyTO.projId,
						"parentName": workDairyTO.parentName,
						"projName": workDairyTO.name
					},
					"crewLabelKeyTO": {
						"id": workDairyTO.crewId,
						"code": workDairyTO.crewCode,
						"name": null
					},
					"weatherLabelKeyTO": {
						"id": workDairyTO.weatherId,
						"code": workDairyTO.weatherCode,
						"name": null
					},
					"shiftLabelKeyTO": {
						"id": workDairyTO.shiftId,
						"code": workDairyTO.shiftCode,
						"name": null
					},
					"costLabelKeyTO": {
						"id": null,
						"code": null,
						"name": null
					},
					"userLabelKeyTO": {
						"id": null,
						"code": null,
						"name": null
					},
					"approverTo": {
						"id": null,
						"code": null,
						"name": null
					},
					"workDairyDate": workDairyTO.workDairyDate,
					"empMaxHrs": null,
					"plantMaxHrs": null,
					"contractType": 'Head-Company',
					"purId": workDairyTO.purId,
					"contractNo": workDairyTO.contractNo,
					"type": 'Original',
					"quantity": null,
					"notificationMsg": 'Request for Additional Time'
				};
				var schItemData = {
						"id": null,
						"selected": false,
						"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"sourceType": 'Suppler Delivery',
						"docketType": 'Delivery Docket',
						"deliveryDocketId": null,
						"deliveryPlace": '',
						"docketNum": '',
						"docketDate": '',
						"purchaseLabelKeyTO": {
							id: null,
							code: null,
							name: null
						},
						"purchaseSchLabelKeyTO": {
							id: null,
							code: null,
							name: null,
							displayNamesMap: {}
						},
						"stockLabelKeyTO": {
							id: null,
							code: null,
							name: null,
							type: null
						},
						"receivedQty": '',
						"defectComments": '',
						"consumptionQty": '',
						"comments": '',
						"workDairyMaterialStatusTOs": []
					};
				$scope.isActiveTab = function (tabUrl){
					return tabUrl == $scope.currentTab;
				}
				$scope.createWorkDiaryTabs = [{
					title: 'Progress',
					url: 'views/timemanagement/workdairy/createworkdairy/workdairyprogress.html'
				}, {
					title: 'Manpower Utilisation',
					url: 'views/timemanagement/workdairy/createworkdairy/workdairymanpower.html'
				}, {
					title: 'Plant Utilisation',
					url: 'views/timemanagement/workdairy/createworkdairy/workdairyplant.html'
				}, {
					title: 'Material and Store Consumption',
					url: 'views/timemanagement/workdairy/createworkdairy/workdairymaterial.html'
				}];
				$scope.getWorkDairyDetails = function () {
					$scope.activeFlag = true;
					$scope.timeFlag = false;
					var workDairyGetReq = {
						'workDairyId': $scope.workDairySearchReq.workDairyId,
						"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
						"status": 1,
						"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"apprStatus": 'Original Submission'
					};
					WorkDiaryService.getWorkDairyDetails(workDairyGetReq).then(function (data) {
						$scope.workDairyEmpDtlTOs = $filter('unique')(data.workDairyEmpDtlTOs, 'code');
						console.log($scope.workDairyEmpDtlTOs);
						$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
						$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						$scope.timeFlag = (data.workDairyTO.apprStatus) ? false : data.timeFlag;
						$scope.workDairyMaterialDtlTOs = [];
						$scope.populateMaterialMaps(data.materialProjResp);
						angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
							$scope.deliveryPlace = value.deliveryPlace;
							$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Work Diary Details", 'Error');
					});
				},
				$scope.getWorkDairy = function () {
					$scope.disableButton = false;
					$scope.workDairyEmpDtlTOs = [];
					$scope.workDairyPlantDtlTOs = [];
					$scope.workDairyMaterialDtlTOs = [];
					$scope.workDairyProgressDtlTOs = [];
					if ($scope.workDairySearchReq.workDairyDate == null) {
						GenericAlertService.alertMessage("Please select Workdairy date", 'Warning');
					}
					var workDairyReq = {
						"status": 1,
						"workDairyDate": $scope.workDairySearchReq.workDairyDate,
						"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id
					};
					WorkDiaryService.getWorkDairy(workDairyReq).then(function (data) {
						$scope.workDairySearchReq.workDairyId = data.workDairyTO.id;
						$scope.workDairySearchReq.code = data.workDairyTO.code;
						if (data.workDairyTO.contractType != undefined && data.workDairyTO.contractType != null) {
							$scope.workDairySearchReq.contractType = data.workDairyTO.contractType;
							$scope.workDairySearchReq.contractNo = data.workDairyTO.contractNo;
							$scope.workDairySearchReq.purId = data.workDairyTO.purId;
						}
						$scope.workDairySearchReq.weatherLabelKeyTO.id = data.workDairyTO.weatherId;
						$scope.workDairySearchReq.weatherLabelKeyTO.code = data.workDairyTO.weatherCode;
						$scope.workDairySearchReq.shiftLabelKeyTO.id = data.workDairyTO.shiftId;
						$scope.workDairySearchReq.shiftLabelKeyTO.code = data.workDairyTO.shiftCode;
						$scope.workDairySearchReq.apprStatus = data.workDairyTO.apprStatus;
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						$scope.workDairyCostCodeMap = data.costCodeMap;
						$scope.empWageFactorMap = data.empWageFactorMap;
						$scope.empRegMap = data.empRegMap;
						$scope.plantRegMap = data.plantRegMap;
						$scope.materialRegMap = data.materialRegMap;
						$scope.sowMap = data.sowMap;
						$scope.weatherMap = data.weatherMap;
						$scope.projShiftMap = data.projShiftMap;

						if ($scope.workDairySearchReq.apprStatus != undefined && $scope.workDairySearchReq.apprStatus != null) {
							$scope.disableButton = true;
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Work Diary Details", 'Error');
					});
				},
				$scope.onClickTab = function (tab) {
					$scope.currentTab = tab.url;
					if ($scope.empFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
						}, function (error) {
							$scope.workDairyEmpDtlTOs = [];
							$scope.getWorkDairyDetails();
							$scope.workDairyEmpDtlTOs = $scope.workDairyEmpDtlTOs;
							$scope.empFlag = false;
							$scope.onClickTab(tab);
						})
						return;
					}
					else if ($scope.plantFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyPlantDetails();
						}, function (error) {
							//$scope.workDairyPlantDtlTOs = [];
							$scope.getWorkDairyDetails();
							$scope.workDairyPlantDtlTOs = $scope.workDairyPlantDtlTOs;
							$scope.plantFlag = false;
							$scope.onClickTab(tab);
						})
						return;
					} else if ($scope.materialFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyMaterialDetails();
						}, function (error) {
							$scope.getWorkDairyDetails();
							$scope.materialFlag = false;
							$scope.onClickTab(tab);
						})
						return;
					} else if ($scope.progressFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyProgressDetails();
						}, function (error) {
							$scope.workDairyProgressDtlTOs = [];
							$scope.getWorkDairyDetails();
							$scope.workDairyProgressDtlTOs = $scope.workDairyProgressDtlTOs;
							$scope.progressFlag = false;
							$scope.onClickTab(tab);
						})
						return;
					}
				},
				$scope.saveWorkDairyPlantDetails = function (plantForm) {
					$scope.plantFlag = false;
					angular.forEach($scope.workDairyPlantDtlTOs, function (value, key) {
						if (value.shiftName == null || value.shiftName == undefined) {
							blockUI.stop();
							GenericAlertService.alertMessage('Please select the Shift', "Warning");
							forEach.break();
							return;
						}
					});
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId <= 0) {
						GenericAlertService.alertMessage("Please select project ", 'Warning');
						return;
					}
					var savePlantReq = {
						"status": 1,
						"workDairyPlantDtlTOs": $scope.workDairyPlantDtlTOs,
						"workDairyTO": {
							"status": 1,
							"id": $scope.workDairySearchReq.workDairyId,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
							"clientApproval": $scope.workDairySearchReq.clientApproval,
							"apprStatus": $scope.workDairySearchReq.apprStatus,
							"plantMaxHrs": $scope.workDairySearchReq.plantMaxHrs,
							"newRequired": false,
							"workDairyDate": $scope.workDairySearchReq.workDairyDate
						}
					};
					blockUI.start();
					WorkDiaryService.saveWorkDairyPlantDetails(savePlantReq).then(function (data) {
						blockUI.stop();
						if (data.status == "Warning") {
							$scope.errorPlantFlag = true;
							GenericAlertService.alertMessage("WorkDairy Plant Hours cannot be booked more than max hours ", "Warning");
							$scope.plantFlag = true;
						} else {
							$scope.errorPlantFlag = false;
							$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
							GenericAlertService.alertMessage('Plant Details saved successfully', "Info");
						}
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant Details are failed to save', "Error");
					});
				},
				$scope.totalUsedIdleCount= function(usedTotal,idleTotal){
				var tothours = 0;
				var totminutes = 0;
				var usedTotal = parseFloat(usedTotal)
				var idleTotal = parseFloat(idleTotal)

				var usedTotalHours = Math.trunc(usedTotal);
				var idleTotalHours = Math.trunc(idleTotal);
				var usedTotalMinutes = Number((usedTotal - usedTotalHours).toFixed(2));
				var idleTotalMinutes = Number((idleTotal - idleTotalHours).toFixed(2));
				tothours = parseFloat(usedTotalHours || 0) + parseFloat(idleTotalHours || 0) + parseFloat(tothours || 0);
				totminutes = parseFloat(usedTotalMinutes || 0) + parseFloat(idleTotalMinutes || 0) + parseFloat(totminutes || 0);
				if (totminutes >= 0.60) {
					tothours++;
					totminutes = totminutes % 0.60;
				}
				return tothours + totminutes;
			}
				$scope.uploadFile = function (file, docket, index) {
					$scope.selectedFileMap[index] = file;					
				},
				$scope.saveWorkDairyProgressDetails = function () {
					$scope.progressFlag = false;
					var errorFlag = false;					
					angular.forEach($scope.workDairyProgressDtlTOs, function (progressDtlTO, key) {
						if ($scope.validateQuantity(progressDtlTO)) {
							errorFlag = true;
						}
					});
					if (errorFlag) {
						GenericAlertService.alertMessage('WorkDairy cannot be booked more than SOW Item Quantity', "Warning");
						return;
					}
					var files = [];
					console.log($scope.selectedFileMap);
					for (let index = 0; index < $scope.selectedFileMap.length; index++) {
						const value = $scope.selectedFileMap[index];
						if (value) {
							$scope.workDairyProgressDtlTOs[index].fileObjectIndex = files.length;
							$scope.workDairyProgressDtlTOs[index].fileName = value.name;
							files.push(value);
						}
					}
					var req = {
						"status": 1,
						"workDairyProgressDtlTOs": $scope.workDairyProgressDtlTOs,
						"workDairyTO": {
							"status": 1,
							"id": $scope.workDairySearchReq.workDairyId,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
							"clientApproval": $scope.workDairySearchReq.clientApproval,
							"apprStatus": $scope.workDairySearchReq.apprStatus,
							"newRequired": false
						},
						"folderCategory" : "Progress - Upload Site Photos",
						"userId" : $rootScope.account.userId
					};
					angular.forEach($scope.workDairyEmpDtlTOs, function (value, key) {

						angular.forEach(value.workDairyEmpWageTOs, function (value1, key) {
							$scope.calculateUsedTotal(value1, $scope.workDairySearchReq.empMaxHrs);
							$scope.calculateIdleTotal(value1, $scope.workDairySearchReq.empMaxHrs);

						});
					});
					blockUI.start();
					console.log("This is from saveWorkDairyProgressDetails function");
					console.log(req);
					console.log(files);
					WorkDiaryService.saveWorkDairyProgressDetails(req,files).then(function (data) {
						blockUI.stop();
						$scope.workDairyProgressDtlTOs = data.data.workDairyProgressDtlTOs;
						GenericAlertService.alertMessage('Progress Details saved successfully', "Info");
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Progress Details are failed to save', "Error");
					});			
				},
				$scope.submitWorkDairyForapproval = function () {
					var validateFalg = true;
					if ($scope.workDairySearchReq.apprStatus == null) {
						validateFalg = $scope.validateWorkdairyDetails();
					}
					if (validateFalg) {
						if ($scope.empFlag == true) {
							GenericAlertService.confirmMessageModal('Do you want to Save The Details ', 'Warning', 'YES', 'NO').then(function () {
								$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
							}, function (error) {
								$scope.workDairyEmpDtlTOs = [];
								$scope.empFlag = false;
							})
							return;
						} else if ($scope.plantFlag == true) {
							GenericAlertService.confirmMessageModal('Do you want to Save The Details ', 'Warning', 'YES', 'NO').then(function () {
								$scope.saveWorkDairyPlantDetails();
							}, function (error) {
								$scope.workDairyPlantDtlTOs = [];
								$scope.plantFlag = false;
							})
							return;
						} else if ($scope.materialFlag == true) {
							GenericAlertService.confirmMessageModal('Do you want to Save The Details ', 'Warning', 'YES', 'NO').then(function () {
								$scope.saveWorkDairyMaterialDetails();
							}, function (error) {
								$scope.materialFlag = false;
								$scope.onClickTab(tab);
							})
							return;
						} else if ($scope.progressFlag == true) {
							GenericAlertService.confirmMessageModal('Do you want to Save The Details', 'Warning', 'YES', 'NO').then(function () {
								$scope.saveWorkDairyProgressDetails();
							}, function (error) {
								$scope.workDairyProgressDtlTOs = [];
								$scope.progressFlag = false;
							})
							return;
						}
						if ($scope.errorEmpFlag) {
							GenericAlertService.alertMessage("WorkDairy Man Hours cannot be booked more than max hours, Save again", 'Warning');
							return;
						} else if ($scope.errorPlantFlag) {
							GenericAlertService.alertMessage("WorkDairy Plant Hours cannot be booked more than max hours, Save again", 'Warning');
							$scope.plantFlag = true;
							return;
						}
						tweakMaterialDtl();
						var submitpopup = WorkDairySubmitFactory.getWorkDairySubmitDetails($scope.workDairySearchReq, $scope.workDairyEmpDtlTOs, $scope.workDairyPlantDtlTOs, $scope.workDairyMaterialDtlTOs, $scope.workDairyProgressDtlTOs);
						submitpopup.then(function (data) {
							$scope.workDairySearchReq.apprStatus = data.workDairyTO.apprStatus;
							if ($scope.workDairySearchReq.apprStatus != undefined && $scope.workDairySearchReq.apprStatus != null)
								$scope.disableButton = true;
							$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
							$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
							$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
							$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
							$scope.workDairyMaterialDtlTOs = [];
							angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
								$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
							});
							deferred.resolve(data);
							$scope.closeThisDialog();
						}, function (error) {
							GenericAlertService.alertMessage("Error occurred while submitting WorkDairy", 'Error');
						});
					}
				},
				$scope.validateWorkdairyDetails = function () {
					if ($scope.workDairyEmpDtlTOs != null && $scope.workDairyEmpDtlTOs.length > 0) {
						return true;
					} else if ($scope.workDairyPlantDtlTOs != null && $scope.workDairyPlantDtlTOs.length > 0) {
						return true;
					} else if ($scope.workDairyMaterialDtlTOs != null && $scope.workDairyMaterialDtlTOs.length > 0) {
						return true;
					} else if ($scope.workDairyProgressDtlTOs != null && $scope.workDairyProgressDtlTOs.length > 0) {
						return true;
					} else {
						GenericAlertService.alertMessage("Please add atleast any one of workdairy records for submit", "Warning");
					}
					return false;
				},
				$scope.validateMaterialQuantity = function (materialObj) {
					if (materialObj.availableQty < materialObj.workDairyMaterialStatusTOs[0].total)
						return true;
					return false;
				},
				$scope.validateQuantity = function (progress) {
					if (progress.value > progress.revisedquantity)
						return true;
					return false;
				},
				$scope.getWorkDairyNotifications = function (flag) {
					if (flag) {
						GenericAlertService.alertMessage("Normal hours not completed", 'Warning');
						return;
					}
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId <= 0 || $scope.workDairySearchReq.crewLabelKeyTO.id == undefined || $scope.workDairySearchReq.crewLabelKeyTO.id <= 0) {
						GenericAlertService.alertMessage("Please select Project and Crew to get request additional time", 'Warning');
						return;
					}
					var popup = RequestForAdditionalTimeFactory.getAdditionalTimeDetails($scope.workDairySearchReq);
					popup.then(function (data) {
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while getting  request additional time", 'Error');
					})
				},
				$scope.getCopyTemplate = function () {
					if ($scope.workDairySearchReq.workDairyId == undefined || $scope.workDairySearchReq.workDairyId == null) {
						GenericAlertService.alertMessage("Please create Work Diary before copying template.", 'Warning');
						return;
					}
					if ($scope.disableButton) {
						GenericAlertService.alertMessage("Work Diary is already submitted", 'Warning');
						return;
					}
					if ($scope.empFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
						}, function (error) {
							$scope.empFlag = false;
							$scope.getCopyTemplate();
						})
						return;
					} else if ($scope.plantFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyPlantDetails();
						}, function (error) {
							$scope.plantFlag = false;
							$scope.getCopyTemplate();
						})
						return;
					} else if ($scope.materialFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyMaterialDetails();
						}, function (error) {
							$scope.materialFlag = false;
							$scope.getCopyTemplate();
						})
						return;
					} else if ($scope.progressFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyProgressDetails();
						}, function (error) {
							$scope.progressFlag = false;
							$scope.getCopyTemplate();
						})
						return;
					}
					if (!$scope.workDairySearchReq.projectLabelKeyTO.projId ||
						!$scope.workDairySearchReq.crewLabelKeyTO.id) {
						GenericAlertService.alertMessage("Please select project and crew to copy Workdairy", 'Warning');
						return;
					}
					var empExistingMap = [];
					angular.forEach($scope.workDairyEmpDtlTOs, function (value, key) {
						empExistingMap[value.empRegId] = true;
					});

					var existingPlantMap = [];
					angular.forEach($scope.workDairyPlantDtlTOs, function (value, key) {
						existingPlantMap[value.plantRegId] = true;
					});

					var copypopup = WorkDairyCopyFactory.getWorkDairyDetails($scope.workDairySearchReq, $scope.empRegMap, $scope.plantRegMap, $scope.materialRegMap, $scope.sowMap, $scope.workDairyCostCodeList, empExistingMap, existingPlantMap);
					copypopup.then(function (data) {
						angular.forEach(data.workDairyEmpDtlTOs, function (value, key) {
							$scope.workDairyEmpDtlTOs.push(value);
						});
						angular.forEach(data.workDairyPlantDtlTOs, function (value, key) {
							$scope.workDairyPlantDtlTOs.push(value);
						});
						angular.forEach(data.workDairyProgressDtlTOs, function (value, key) {
							$scope.workDairyProgressDtlTOs.push(value);
						});
						angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
							let data = {
								"id": null,
								"selected": false,
								"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
								"sourceType": value.sourceType,
								"deliveryDocketId": value.deliveryDocketId,
								"projDocketSchId": value.projDocketSchId,
								"docketNum": value.docketNum,
								"docketDate": value.docketDate,
								"supplierDocket": value.supplierDocket,
								"docketType": value.docketType,
								"onLoadDocket": false,
								"deliveryPlace": value.deliveryPlace,
								"scheduleItemId": value.scheduleItemId,
								"consumpitonUpdated": value.consumpitonUpdated,
								"purchaseLabelKeyTO": {
									id: null,
									code: value.materialProjDtlTO[0].purchaseSchLabelKeyTO.displayNamesMap.purCode,
									name: null
								},
								"purchaseSchLabelKeyTO": {
									id: value.materialProjDtlTO[0].id,
									code: value.materialProjDtlTO[0].purchaseSchLabelKeyTO.code,
									name: value.materialProjDtlTO[0].purchaseSchLabelKeyTO.name,
									displayNamesMap: value.materialProjDtlTO[0].purchaseSchLabelKeyTO.displayNamesMap
								},
								"stockLabelKeyTO": {
									id: null,
									code: null,
									name: null,
									type: null
								},
								"receivedQty": value.receivedQty,
								"defectComments": value.defectComments,
								"consumptionQty": null,
								"comments": value.apprComments,
								"workDairyMaterialStatusTOs": value.workDairyMaterialStatusTOs,
								"transitQty": value.transitQty,
								"openingStock": value.openingStock,
								"closingStock": value.closingStock
							}
							$scope.workDairyMaterialDtlTOs.push(data);
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
					})
				},
				$scope.populateMaterialObj = function (materialTOs, value) {
					for (var i = 0; i < value.materialProjDtlTO.length; i++)
						$scope.addMaterial(materialTOs, value, value.materialProjDtlTO[i]);
				},
				$scope.addMaterial = function (materialTOs, value, scheduleItemId) {
					var dataObj = {
						"id": value.id,
						"selected": false,
						"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"sourceType": value.sourceType,
						"deliveryDocketId": value.deliveryDocketId,
						"projDocketSchId": value.projDocketSchId,
						"docketNum": value.docketNum,
						"docketDate": value.docketDate,
						"supplierDocket": value.supplierDocket,
						"docketType": (value.supplierDocket) ? 'existing' : value.docketType,
						"onLoadDocket": (value.supplierDocket) ? true : false,
						"deliveryPlace": $scope.deliveryPlace,
						"scheduleItemId": scheduleItemId,
						"consumpitonUpdated": value.consumpitonUpdated,
						"purchaseLabelKeyTO": {
							id: null,
							code: scheduleItemId.purchaseSchLabelKeyTO.displayNamesMap.purCode,
							name: null
						},
						"purchaseSchLabelKeyTO": {
							id: scheduleItemId.id,
							code: scheduleItemId.purchaseSchLabelKeyTO.code,
							name: scheduleItemId.purchaseSchLabelKeyTO.name,
							displayNamesMap: scheduleItemId.purchaseSchLabelKeyTO.displayNamesMap
						},
						"stockLabelKeyTO": {
							id: null,
							code: null,
							name: null,
							type: null
						},
						"deliveryPlace": scheduleItemId.purchaseSchLabelKeyTO.displayNamesMap.deliveryPlace,
						"receivedQty": value.receivedQty,
						"defectComments": value.defectComments,
						"consumptionQty": '',
						"comments": value.apprComments,
						"workDairyMaterialStatusTOs": value.workDairyMaterialStatusTOs,
						"transitQty": value.transitQty,
						"openingStock": value.openingStock,
						"closingStock": value.closingStock
					};
					materialTOs.push(dataObj);
				},
				$scope.saveWorkDairyMaterialDetails = function () {
					$scope.materialFlag = false;
					var errorFlag = false;
					angular.forEach($scope.workDairyMaterialDtlTOs, function (materialDtlTO, key) {
						if ($scope.validateMaterialQuantity(materialDtlTO)) {
							errorFlag = true;
						}
					});
					if (errorFlag) {
						GenericAlertService.alertMessage('WorkDairy cannot be booked more tham Received Quantity', "Warning");
						return;
					}
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId <= 0) {
						GenericAlertService.alertMessage("Please select project ", 'Warning');
						return;
					}
					var saveMaterialreq = [];
					angular.forEach($scope.workDairyMaterialDtlTOs, function (value, key) {
						var purchaseSchLabelKeyTO = {};
						purchaseSchLabelKeyTO = value.purchaseSchLabelKeyTO;
						purchaseSchLabelKeyTO.id = (Array.isArray(value.purchaseSchLabelKeyTO.id)) ? value.purchaseSchLabelKeyTO.id[0] : value.purchaseSchLabelKeyTO.id;
						if (!value.purchaseSchLabelKeyTO.displayNamesMap.purId) {
							purchaseSchLabelKeyTO.displayNamesMap.purId = value.purchaseLabelKeyTO.id;
							purchaseSchLabelKeyTO.displayNamesMap.purCode = value.purchaseLabelKeyTO.code;
						}

						var maretailSchReq = {
							"mapId": (value.scheduleItemId) ? (Array.isArray((value.scheduleItemId)) ? value.scheduleItemId[0] : value.scheduleItemId.id) : null,
							"purchaseSchLabelKeyTO": purchaseSchLabelKeyTO,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"schItemCmpId": (value.schItemCmpId) ? value.schItemCmpId : purchaseSchLabelKeyTO.displayNamesMap.schItemCmpId,
							"issueQty": value.purchaseSchLabelKeyTO.displayNamesMap['qty'],
							"workDairyCunsumptionQty": (value.workDairyMaterialStatusTOs) ? value.workDairyMaterialStatusTOs[0].total : null,
							"materialPODeliveryDocketTOs": [angular.copy({
								"status": 1,
								"sourceType": value.sourceType,
								"id": (value.deliveryDocketId) ? value.deliveryDocketId[0] : null,
								"receivedQty": value.receivedQty,
								"docketNumber": value.docketNum,
								"deliveryPlace": value.deliveryPlace,
								"regMaterialId": (value.scheduleItemId) ? (Array.isArray((value.scheduleItemId)) ? value.scheduleItemId[0] : value.scheduleItemId.id) : null,
								"projDocketSchId": value.projDocketSchId,
								"docketDate": value.docketDate,
								"defectComments": value.defectComments,
								"comments": value.comments,
								"receivedBy": value.receivedBy,
								"supplierDocket": value.supplierDocket,
								"transitQty": value.transitQty,
								"stockLabelKeyTO": {
									id: purchaseSchLabelKeyTO.displayNamesMap.stockId
								},
								"projStockLabelKeyTO": {
									id: purchaseSchLabelKeyTO.displayNamesMap.projStockId
								}
							})]
						};

						if (typeof value.scheduleItemId == 'number')
							maretailSchReq.mapId = value.scheduleItemId;
						if (!value.transitQty)
							value.transitQty = 0;
						if (!value.workDairyMaterialStatusTOs[0].total)
							value.workDairyMaterialStatusTOs[0].total = 0;
						var material = {
							"id": value.id,
							"status": 1,
							"workDairyId": $scope.workDairySearchReq.workDairyId,
							"projDocketSchId": value.projDocketSchId,
							"consumpitonUpdated": false,
							"sourceType": value.sourceType,
							"deliveryDocketId": [],
							"docketType": (value.docketType === "new" || value.docketType === "existing") ? "Supplier Docket" : "Delivery Docket",
							"scheduleItemId": [],
							"projDocketId": value.projDocketId,
							"docketNum": value.docketNum,
							"docketDate": value.docketDate,
							"deliveryPlace": value.deliveryPlace,
							"defectComments": value.defectComments,
							"receivedQuantity": value.receivedQty,
							"supplierDocket": value.supplierDocket,
							"apprStatus": null,
							"receivedQty": value.receivedQty,
							"apprComments": value.comments,
							"workDairyMaterialStatusTOs": value.workDairyMaterialStatusTOs,
							"materialProjDtlTO": [maretailSchReq],
							"openingStock": (value.openingStock) ? value.openingStock : ((value.availableQty) ? value.availableQty : value.receivedQty),
							"closingStock": (value.openingStock) ? (value.openingStock - value.workDairyMaterialStatusTOs[0].total) :
								((value.availableQty) ? value.availableQty - value.workDairyMaterialStatusTOs[0].total : (value.receivedQty - value.workDairyMaterialStatusTOs[0].total))
						}

						if (value.deliveryDocketId && Array.isArray(value.deliveryDocketId))
							for (var i = 0; i < value.deliveryDocketId.length; i++) {
								material.deliveryDocketId.push(value.deliveryDocketId[i]);
							}
						else if (value.deliveryDocketId)
							material.deliveryDocketId.push(value.deliveryDocketId);

						if (value.scheduleItemId && Array.isArray(value.scheduleItemId))
							for (var i = 0; i < value.scheduleItemId.length; i++) {
								material.scheduleItemId.push(value.scheduleItemId[i].id);
							}
						else if (value.scheduleItemId && !angular.isNumber(value.scheduleItemId))
							material.scheduleItemId.push(value.scheduleItemId.id);
						else
							material.scheduleItemId.push(value.scheduleItemId);
						saveMaterialreq.push(material);
					});
					var req = {
						"status": 1,
						"workDairyMaterialDtlTOs": saveMaterialreq,
						"workDairyTO": {
							"status": 1,
							"id": $scope.workDairySearchReq.workDairyId,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
							"clientApproval": $scope.workDairySearchReq.clientApproval,
							"apprStatus": $scope.workDairySearchReq.apprStatus,
							"newRequired": false
						}
					};
					blockUI.start();
					WorkDiaryService.saveWorkDairyMaterialDetails(req).then(function (data) {
						blockUI.stop();
						$scope.workDairyMaterialDtlTOs = [];
						$scope.populateMaterialMaps(data.materialProjResp);
						angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
							$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
						});
						GenericAlertService.alertMessage('Material Details saved successfully', "Info");
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Material Details are failed to save', "Error");
					});
				},
				$scope.populateMaterialMaps = function (materialProjResp) {
					if (materialProjResp.registerOnLoadTO != null) {
						$scope.companyMap = materialProjResp.registerOnLoadTO.companyMap;
						$scope.classificationMap = materialProjResp.registerOnLoadTO.classificationMap;
					}
					$scope.userProjectMap = materialProjResp.userProjMap;
					angular.forEach(materialProjResp.materialProjDtlTOs, function (value, key) {
						$scope.materialProjDtlMap[value.id] = value;
					});
				},
				$scope.dockettype = function () {
					var materialPopup = WorkDairyDocketFactory.docTypeDetails();
					materialPopup.then(function (data) {
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching dockettype details", 'Error');
					});
				},
				$scope.calculatePlantIdleTotal = function (plantStatusTO, plantMaxHrs) {
					$scope.plantFlag = true;
					plantStatusTO.idleTotal = 0;
					var idleTime = 0;
					var idleTimeHours = 0;
					var idleTimeMinutes = 0;
					var totHours = 0;
					var totMinutes = 0;
					angular.forEach(plantStatusTO.workDairyPlantCostDtlTOs, function (value, key) {
						if (value.idleTime == undefined) {
							value.idleTime = 0;
						}
						idleTime = parseFloat(value.idleTime);
						idleTimeHours = Math.trunc(idleTime);
						idleTimeMinutes = (Number((idleTime - idleTimeHours).toFixed(2)));
						totHours = parseFloat(idleTimeHours || 0) + parseFloat(totHours || 0);
						totMinutes = parseFloat(idleTimeMinutes || 0) + parseFloat(totMinutes || 0);
						if (totMinutes >= 0.60) {
							totHours++;
							totMinutes = totMinutes % 0.60;
						}
						plantStatusTO.idleTotal = totHours + totMinutes;
					});
					if (plantStatusTO.usedTotal > plantMaxHrs && plantStatusTO.idleTotal > plantMaxHrs) {
						$scope.plantFlag = false;
						GenericAlertService.alertMessage('WorkDairy Cannot booked more than max hours', "Warning");
						$scope.plantFlag = true;
						return;
					}
					var tota = plantStatusTO.usedTotal + plantStatusTO.idleTotal;
					if (tota > plantMaxHrs) {
						$scope.plantFlag = false;
						GenericAlertService.alertMessage('Work Diary Total Hours cannot be more than Max Hours', "Warning");
						$scope.plantFlag = true;
						return;
					}
					return plantStatusTO.idleTotal;
				},
				$scope.calculatePlantUsedTotal = function (plantStatusTO, plantMaxHrs) {
					$scope.plantFlag = true;
					plantStatusTO.usedTotal = 0;
					var usedTime = 0;
					var usedTimeHours = 0;
					var usedTimeMinutes = 0;
					var totHours = 0;
					var totMinutes = 0;
					angular.forEach(plantStatusTO.workDairyPlantCostDtlTOs, function (value, key) {
						if (value.usedTime == undefined) {
							value.usedTime = 0;
						}
						usedTime = parseFloat(value.usedTime);
						usedTimeHours = Math.trunc(usedTime);
						usedTimeMinutes = (Number((usedTime - usedTimeHours).toFixed(2)));
						totHours = parseFloat(usedTimeHours || 0) + parseFloat(totHours || 0);
						totMinutes = parseFloat(usedTimeMinutes || 0) + parseFloat(totMinutes || 0);
						if (totMinutes >= 0.60) {
							totHours++;
							totMinutes = totMinutes % 0.60;
						}
						plantStatusTO.usedTotal = totHours + totMinutes;
					});
					if (plantStatusTO.usedTotal > plantMaxHrs && plantStatusTO.idleTotal > plantMaxHrs) {
						$scope.plantFlag = false;
						GenericAlertService.alertMessage('WorkDairy Cannot booked more than max hours', "Warning");
						$scope.plantFlag = true;
						return;
					}
					var tota = plantStatusTO.usedTotal + plantStatusTO.idleTotal;
					if (tota > plantMaxHrs) {
						$scope.plantFlag = false;
						GenericAlertService.alertMessage('Work Diary Total Hours cannot be more than Max Hours', "Warning");
						$scope.plantFlag = true;
						return;
					}
					return plantStatusTO.usedTotal;
				},
				$scope.calculateIdleTotal = function (workDetails, empMaxHrs) {
					$scope.empFlag = true;
					var idleTime = 0;
					var idleTimeHours = 0;
					var idleTimeMinutes = 0;
					var totHours = 0;
					var totMinutes = 0;
					workDetails.idleTotal = 0;
					angular.forEach(workDetails.workDairyEmpCostDtlTOs, function (value, key) {
						if (value.idleTime == null && value.idleTime == undefined)
							value.idleTime = 0;
						idleTime = parseFloat(value.idleTime);
						idleTimeHours = Math.trunc(idleTime);
						idleTimeMinutes = (Number((idleTime - idleTimeHours).toFixed(2)));
						totHours = parseFloat(idleTimeHours || 0) + parseFloat(totHours || 0);
						totMinutes = parseFloat(idleTimeMinutes || 0) + parseFloat(totMinutes || 0);
						if (totMinutes >= 0.60) {
							totHours++;
							totMinutes = totMinutes % 0.60;
						}
						workDetails.idleTotal = totHours + totMinutes;
					});
					if (workDetails.usedTotal > empMaxHrs && workDetails.idleTotal > empMaxHrs) {
						$scope.empFlag = false;
						GenericAlertService.alertMessage('WorkDairy Cannot booked more than max hours', "Warning");
						return;
					}
					var tota = workDetails.usedTotal + workDetails.idleTotal;
					if (tota > empMaxHrs) {
						$scope.empFlag = false;
						GenericAlertService.alertMessage('Work Diary Total Hours cannot be more than Max Hours', "Warning");
						$scope.empFlag = true;
						return;
					}
					return (workDetails.idleTotal).toFixed(2);

				},
				$scope.calculateUsedTotal = function (workDetails, empMaxHrs) {
					$scope.empFlag = true;
					workDetails.usedTotal = 0;
					var usedTime = 0;
					var usedTimeHours = 0;
					var usedTimeMinutes = 0;
					var totHours = 0;
					var totMinutes = 0;
					angular.forEach(workDetails.workDairyEmpCostDtlTOs, function (value, key) {
						if (value.usedTime == null && value.usedTime == undefined)
							value.usedTime = 0;
						usedTime = parseFloat(value.usedTime);
						usedTimeHours = Math.trunc(usedTime);
						usedTimeMinutes = (Number((usedTime - usedTimeHours).toFixed(2)));
						totHours = parseFloat(usedTimeHours || 0) + parseFloat(totHours || 0);
						totMinutes = parseFloat(usedTimeMinutes || 0) + parseFloat(totMinutes || 0);
						if (totMinutes >= 0.60) {
							totHours++;
							totMinutes = totMinutes % 0.60;
						}
						workDetails.usedTotal = totHours + totMinutes;
					});
					if (workDetails.usedTotal > empMaxHrs && workDetails.idleTotal > empMaxHrs) {
						$scope.empFlag = false;
						GenericAlertService.alertMessage('WorkDairy Cannot booked more than max hours', "Warning");
						$scope.empFlag = true;
						return;
					}
					var tota = workDetails.usedTotal + workDetails.idleTotal;
					if (tota > empMaxHrs) {
						$scope.empFlag = false;
						GenericAlertService.alertMessage('Work Diary Total Hours cannot be more than Max Hours', "Warning");
						$scope.empFlag = true;
						return;
					}
					return (workDetails.usedTotal).toFixed(2);
				},
				$scope.saveWorkDairyEmpDetails = function (workDairySearchReq) {
					$scope.empFlag = false;
					angular.forEach($scope.workDairyEmpDtlTOs, function (value, key) {
						if (value.empRegId != null) {
							angular.forEach(value.workDairyEmpWageTOs, function (value1, key1) {
								if (value1.wageId == null || value1.wageId == undefined) {
									blockUI.stop();
									GenericAlertService.alertMessage('Please select the Wage Factor', "Warning");
									forEach.break();
									return;
								}
							})
							return;
						}
					});
					var saveEmpReq = {
						"status": 1,
						"workDairyEmpDtlTOs": $scope.workDairyEmpDtlTOs,
						"workDairyTO": {
							"status": 1,
							"id": $scope.workDairySearchReq.workDairyId,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
							"clientApproval": $scope.workDairySearchReq.clientApproval,
							"apprStatus": $scope.workDairySearchReq.apprStatus,
							"empMaxHrs": $scope.workDairySearchReq.empMaxHrs,
							"newRequired": false,
							"workDairyDate": $scope.workDairySearchReq.workDairyDate
						}
					};
					blockUI.start();
					WorkDiaryService.saveWorkDairyEmpDetails(saveEmpReq).then(function (data) {
						blockUI.stop();
						if (data.status == "Warning") {
							$scope.errorEmpFlag = true;
							GenericAlertService.alertMessage("Work Diary Employee Hours cannot be booked more than Max Hours ", "Warning");
							$scope.empFlag = true;
						} else {
							$scope.errorEmpFlag = false;
							$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
							GenericAlertService.alertMessage('Employee(s) details saved successfully', "Info");
						}
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Employees Details are failed to save', "Error");
					});
				},
				$scope.addProgressDetails = function () {
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to Add Progress Details", 'Warning');
						return;
					}
					var workProgressMap = [];
					angular.forEach($scope.workDairyProgressDtlTOs, function (value, key) {
						workProgressMap[value.sowId] = true;
					});
					var progressPopup = WorkDairyAddProgressFactory.getSOWDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, workProgressMap);
					progressPopup.then(function (data) {
						angular.forEach(data.workDairyProgressDtlTOs, function (value, key) {
							$scope.workDairyProgressDtlTOs.push(value);
							angular.forEach($scope.workDairyProgressDtlTOs, function (value, key) {
								$scope.workDairySearchReq.revisedquantity = value.revisedquantity;
							})
						});
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						$scope.progressFlag = true;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
					});
				},
				$scope.addMoreSowItems = function () {
					if ($scope.progressFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Sow costCodes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyProgressDetails();
						}, function (error) {
							$scope.progressFlag = false;
							$scope.getWorkDairyDetails($scope.workDairySearchReq.projectLabelKeyTO.projId);
							$scope.addMoreSowItems();
						})
						return;
					}
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to Add Progress Details", 'Warning');
						return;
					}
					var workProgressMap = [];
					angular.forEach($scope.workDairyProgressDtlTOs, function (value, key) {
						workProgressMap[value.sowId] = true;
					});
					var progressPopup = WorkDairyProgressFactory.getSOWDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, workProgressMap);
					progressPopup.then(function (data) {
						$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
						$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
						$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
						$scope.workDairyMaterialDtlTOs = [];
						angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
							$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
						});
						$scope.workDairyCostCodeList = data.workDairyProgressDtlTOs;
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						$scope.progressFlag = true;

					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
					});
				},
				$scope.addProgressPOScheduleItems = function () {
					$scope.progressFlag = false;
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to Add Progress Details", 'Warning');
						return;
					}
					var workProgressMap = [];
					angular.forEach($scope.workDairyProgressDtlTOs, function (value, key) {
						workProgressMap[value.sowId] = true;
					});
					var req = {
						"status": 1,
						"purId": $scope.workDairySearchReq.purId,
						"procureType": 'SOW'
					};
					var procureSowMap = [];
					var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req);
					poItemDetailsPromise.then(function (data) {
						angular.forEach(data.labelKeyTOs, function (value, key) {
							procureSowMap[value.displayNamesMap['purClassId']] = value;
						});
						angular.forEach(data.projSOWItemTOs, function (value, key) {
							$scope.workDairyProgressDtlTOs.push(value);
						});
						var workProgressMap = [];
						angular.forEach($scope.workDairyProgressDtlTOs, function (value, key) {
							workProgressMap[value.sowId] = true;
						});
						var progressPopup = POScheduleItemsFactory.getSOWDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, workProgressMap, procureSowMap);
						progressPopup.then(function (data) {
							angular.forEach(data.projSOWItemTOs, function (value, key) {
								$scope.workDairyProgressDtlTOs.push(value);
							});
							$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						}, function (error) {
							GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
						});

					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
					});
				},
				$scope.deleteProgressRecords = function () {
					let canWorkDairyProgressInsert = false;
					if (editProgress.length <= 0) {
						GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
						return;
					}
					if (editProgress.length < $scope.workDairyProgressDtlTOs.length) {
						canWorkDairyProgressInsert = true;
						angular.forEach(editProgress, function (value, key) {
							$scope.workDairyProgressDtlTOs.splice($scope.workDairyProgressDtlTOs.indexOf(value), 1);
							console.log(value.sowId)
						$scope.selectedWorkdairyIds.push(value.sowId);		//	$scope.selectedWorkdairyIds.push(value.id); <- previous one
							editProgress = [];
						});
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
					console.log(canWorkDairyProgressInsert);
					console.log($scope.selectedWorkdairyIds,"selectedWorkDairyIds");
					if( canWorkDairyProgressInsert )
					{
						console.log($scope.selectedWorkdairyIds);
						var saveProgressReq = {
							"workDairyProgressIds" : $scope.selectedWorkdairyIds,
							"workDairyDeleteType" : "WORKDAIRY_PROGRESS"
						};
						WorkDiaryService.deleteWorkDairyProgressRecords(saveProgressReq).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage("Progress deleted Successfully", 'Info');
							
						});											
					}					
				},
				$scope.rowSelectEmpReg = function (empObj, indexValue) {
					$scope.selectedEmp = empObj;
					$scope.selectedRow = indexValue;
				},
				$scope.deleteEmpReg = function (selectedEmp, workDetails) {
					selectedEmp.workDairyEmpWageTOs.pop(workDetails);
				},
				$scope.getWageFactor = function (workDetails, workDairyEmpWageTOs) {
					var getReq = {
						"status": 1
					};
					var existingWageFactorMap = [];
					angular.forEach(workDairyEmpWageTOs, function (value, key) {
						existingWageFactorMap[value.wageId] = true
					});
					var wagePopup = [];
					wagePopup = WageFactory.wageFactorDetailsList(getReq, existingWageFactorMap);
					wagePopup.then(function (data) {
						workDetails.wageId = data.employeeWageRateTO.wageRateId;
						workDetails.wageFactor = data.employeeWageRateTO.wageFactor;
						workDetails.code = data.employeeWageRateTO.code;
						$scope.empFlag = true;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting WageFactor Details", 'Error');
					});
				},
				$scope.addEmpRegDetails = function () {
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to Add Employees", 'Warning');
						return;
					}
					var existingMap = [];
					angular.forEach($scope.workDairyEmpDtlTOs, function (value, key) {

						existingMap[value.empRegId] = true;
					});
					var popup = WorkDairyEmpFactory.getEmpRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, existingMap);
					popup.then(function (data) {
						angular.forEach(data.workDairyEmpDtlTOs, function (value, key) {
							$scope.workDairyEmpDtlTOs.push(angular.copy(value));
						});
						$scope.empFlag = true;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
					});
				},
				$scope.addEmpWages = function (workDairyEmpDtlTOs) {
					if ($scope.selectedEmp == undefined || $scope.selectedEmp == null) {
						GenericAlertService.alertMessage("Please select employee To add additional Cost Code/Wage factor", 'Warning');
						return;
					}
					var workDairyEmpCostDtlTOs = [];
					angular.forEach($scope.workDairyCostCodeList, function (value, key) {
						workDairyEmpCostDtlTOs.push(angular.copy({
							"costId": value.costId,
							"workDairyId": $scope.workDairySearchReq.workDairyId,
							"usedTime": null,
							"idleTime": null,
							"status": 1,
							"indexValue": $scope.selectedRow
						}))
					});
					$scope.workDairyEmpDtlTOs[$scope.selectedRow].workDairyEmpWageTOs.push(angular.copy({
						"empDtlId": $scope.selectedEmp.id,
						"workDairyId": $scope.workDairySearchReq.workDairyId,
						"wageId": null,
						"status": 1,
						"workDairyEmpCostDtlTOs": workDairyEmpCostDtlTOs
					}));
				},
				$scope.deleteEmpRecords = function () {
					let canWorkDairyEmpInsert = false;
					if (editMans.length <= 0) {
						GenericAlertService.alertMessage(
							'Please Select Atleast One Row to Delete', "Warning");
						return;
					}
					if (editMans.length < $scope.workDairyEmpDtlTOs.length) {
						canWorkDairyEmpInsert = true;
						angular.forEach(editMans, function (value, key) {
							$scope.workDairyEmpDtlTOs.splice($scope.workDairyEmpDtlTOs
								.indexOf(value), 1);
							editMans = [];
							$scope.selectedWorkdairyManpowerIds.push(value.id);
						});
					} else {
						GenericAlertService.alertMessage(
							'Please leave atleast one row', "Warning");
					}
					if( canWorkDairyEmpInsert )
					{
						console.log($scope.selectedWorkdairyIds);
						var deleteWorkDairyManpowerReq = {
							"workDairyManpowerIds" : $scope.selectedWorkdairyManpowerIds,
							"workDairyDeleteType" : "WORKDAIRY_MANPOWER"
						};
						WorkDiaryService.deleteWorkDairyManpowerRecords(deleteWorkDairyManpowerReq).then(function(data){
							console.log(data);
						});								
					}
				},
				$scope.manpowerRowSelect = function (man) {
					if (man.select) {
						editMans.push(man);
					} else {
						editMans.pop(man);
					}
				},
				$scope.progressRowSelect = function (prog) {
					if (prog.select) {
						editProgress.push(prog);
					} else {
						editProgress.pop(prog);
					}
				},
				
				$scope.checkDecimal = function (costObj, indexValue, empMaxHrs) {
					var inputValue = null;
					costObj.errorFlag = false;
					if (indexValue == 1) {
						inputValue = costObj.usedTime;
					}
					if (indexValue == 2) {
						inputValue = costObj.idleTime;
					}
					if (inputValue != undefined && inputValue != null) {
						var decimalInput = inputValue.split('.');
						if (inputValue > empMaxHrs) {
							costObj.errorFlag = true;
							$scope.empFlag = false;
							GenericAlertService.alertMessage('Work Diary cannot be booked more than maximum hours', "Warning");
							$scope.empFlag = true;
							return;
						}
						if (decimalInput[1] != undefined) {
							if (decimalInput[1].length == 1) {
								if (decimalInput[1] > 5.99) {
									costObj.errorFlag = true;
									$scope.empFlag = false;
									GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
									return;
								}

							} else if (decimalInput[1].length == 2 && decimalInput[1] > 59) {
								costObj.errorFlag = true;
								$scope.empFlag = false;
								GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
								return;
							}
						}
					}
					costObj.errorFlag = false;
				},
				$scope.plantRowSelect = function (plant) {
					if (plant.select) {
						editPlants.push(plant);
					} else {
						editPlants.pop(plant);
					}
				},
				$scope.checkDecimalPlant = function (costObj, indexValue, plantMaxHrs) {
					var inputValue = null;
					costObj.errorFlag = false;
					if (indexValue == 1) {
						inputValue = costObj.usedTime;
					}
					if (indexValue == 2) {
						inputValue = costObj.idleTime;
					}
					if (inputValue != undefined && inputValue != null) {
						var decimalInput = inputValue.split('.');
						if (inputValue > plantMaxHrs) {
							costObj.errorFlag = true;
							$scope.plantFlag = false;
							GenericAlertService.alertMessage('Work Diary cannot be booked more than maximum hours', "Warning");
							$scope.plantFlag = true;
							return;
						}
						if (decimalInput[1] != undefined) {
							if (decimalInput[1].length == 1) {
								if (decimalInput[1] > 5.99) {
									costObj.errorFlag = true;
									$scope.plantFlag = false;
									GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
									return;
								}

							} else if (decimalInput[1].length == 2 && decimalInput[1] > 59) {
								costObj.errorFlag = true;
								$scope.plantFlag = false;
								GenericAlertService.alertMessage('Please enter Only digits with decimals', "Warning");
								return;
							}
						}
					}
					costObj.errorFlag = false;
				},
				$scope.getTotalCount = function (usedTotal, idleTotal, obj) {
					var tothours = 0;
					var totminutes = 0;
					var usedTotal = parseFloat(usedTotal)
					var idleTotal = parseFloat(idleTotal)

					var usedTotalHours = Math.trunc(usedTotal);
					var idleTotalHours = Math.trunc(idleTotal);
					var usedTotalMinutes = Number((usedTotal - usedTotalHours).toFixed(2));
					var idleTotalMinutes = Number((idleTotal - idleTotalHours).toFixed(2));
					tothours = parseFloat(usedTotalHours || 0) + parseFloat(idleTotalHours || 0) + parseFloat(tothours || 0);
					totminutes = parseFloat(usedTotalMinutes || 0) + parseFloat(idleTotalMinutes || 0) + parseFloat(totminutes || 0);
					if (totminutes >= 0.60) {
						tothours++;
						totminutes = totminutes % 0.60;
					}
					let total = (tothours + totminutes).toFixed(2);
					obj.total = total;
					return total;
				},
				$scope.clickForwardData = function (moreFlag) {
					if ($scope.flag < 1) {
						$scope.flag = moreFlag + 1;
					}
				}, $scope.clickBackwardData = function (moreFlag) {
					if ($scope.flag > 0) {
						$scope.flag = moreFlag - 1;
					}
				},
				$scope.addPlantRegDetails = function () {
					console.log("RESULT",resultData);
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to Add Plants", 'Warning');
						return;
					}
					var plantMap = [];
					angular.forEach($scope.workDairyPlantDtlTOs, function (value, key) {

						plantMap[value.plantRegId] = true;
					});
					var resultData = WorkDairyPlantFactory.getPlantRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, plantMap);
					
					resultData.then(function (data) {
						console.log("data.workDairyPlantDtlTOs", data.workDairyPlantDtlTOs);
						angular.forEach(data.workDairyPlantDtlTOs, function (value, key) {
							$scope.workDairyPlantDtlTOs.push(angular.copy(value));
							console.log("$scope.workDairyPlantDtlTOs ", $scope.workDairyPlantDtlTOs);
						});
						$scope.plantFlag = true;
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching Plants", 'Error');
					});
				},
				$scope.deletePlantRecords = function () {
					let canWorkDairyPlantInsert = false;
					if (editPlants.length <= 0) {
						GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
						return;
					}
					console.log($scope.workDairyPlantDtlTOs)
					if (editPlants.length < $scope.workDairyPlantDtlTOs.length) {
						canWorkDairyPlantInsert = true;
						angular.forEach(editPlants, function (value, key) {
							$scope.workDairyPlantDtlTOs.splice($scope.workDairyPlantDtlTOs.indexOf(value), 1);
							editPlants = [];
							console.log(value.workDairyId);
							$scope.selectedWorkdairyPlantIds.push(value.workDairyId);	//	$scope.selectedWorkdairyPlantIds.push(value.id); <- previous one
						});
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
					if( canWorkDairyPlantInsert )
					{
						var deletePlantRecordsReq = {
							"workDairyPlantIds" : $scope.selectedWorkdairyPlantIds,
							"workDairyDeleteType" : "WORKDAIRY_PLANT"
						};
						console.log(deletePlantRecordsReq);
						WorkDiaryService.deleteWorkDairyPlantRecords(deletePlantRecordsReq).then(function(data){
							console.log(data);
						GenericAlertService.alertMessage('Plant deleted Successfully', 'Info');
						
						});			
					}
				},
				$scope.materialStoreRowSelect = function (supplier) {
					if (supplier.select) {
						editMatestores.push(supplier);
					} else {
						editMatestores.pop(supplier);
					}
				},
				$scope.supplierDocketType = function (materialObj) {
					if (materialObj.docketType === 'new') {
						materialObj.scheduleItemId = null;
						materialObj.receivedQty = null;
						materialObj.deliveryPlace = null;
						materialObj.docketNum = null;
						materialObj.docketDate = null;
						materialObj.deliveryDocketId = null;
						materialObj.defectComments = null;
						materialObj.purchaseSchLabelKeyTO.code = null;
						materialObj.purchaseSchLabelKeyTO.name = null;
						materialObj.purchaseSchLabelKeyTO.displayNamesMap.cmpName = null;
						materialObj.purchaseSchLabelKeyTO.displayNamesMap.purClassUnitOfMeasure = null;
						materialObj.purchaseSchLabelKeyTO.displayNamesMap.unitOfRate = null;
						materialObj.purchaseLabelKeyTO.id = null;
						materialObj.purchaseLabelKeyTO.code = null;
						materialObj.maxReceivedQty = null;
						materialObj.deliveryDocketSelected = false;
					}
				},
				$scope.supplierReceivedVal = function (material) {
					if (material.supplierDocket) {
						if (material.receivedQty > material.maxReceivedQty) {
							GenericAlertService.alertMessage("Received cannot exceed available quantity of " + material.maxReceivedQty, "Warning");
						}
					}
				},
				$scope.calculateMaterialTotal = function (materialObjStatusTO) {
					materialObjStatusTO.total = 0;
					angular.forEach(materialObjStatusTO.workDairyMaterialCostTOs, function (value, key) {
						if (value.quantity == undefined) {
							value.quantity = 0;
						}
						materialObjStatusTO.total = parseFloat(materialObjStatusTO.total || 0) + parseFloat(value.quantity || 0);
					});
					return materialObjStatusTO.total;
				},
				$scope.getPurchaseOrdersForMaterial = function (materialObj) {
					var req = {
						"status": 1,
						"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"procureType": 'M',
						"workDairyDate": $scope.workDairySearchReq.workDairyDate
					};
					RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function (data) {
						materialObj.purchaseLabelKeyTO.id = data.purchaseOrderTO.id;
						materialObj.purchaseLabelKeyTO.code = data.purchaseOrderTO.code;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
					});

				},
				$scope.getScheduleItems = function (materialObj) {
					var req = {
						"status": 1,
						"purId": materialObj.purchaseLabelKeyTO.id,
						"procureType": 'M'
					};
					RegisterPurchaseOrderItemsFactory.getPOItemDetails(req).then(function (data) {
						materialObj.purchaseSchLabelKeyTO.id = data.selectedRecord.id;
						materialObj.purchaseSchLabelKeyTO.code = data.selectedRecord.code;
						materialObj.purchaseSchLabelKeyTO.name = data.selectedRecord.name;
						materialObj.purchaseSchLabelKeyTO.displayNamesMap = data.selectedRecord.displayNamesMap;
						materialObj.deliveryPlace = data.selectedRecord.displayNamesMap.deliveryPlace;
						materialObj.maxReceivedQty = (data.selectedRecord.displayNamesMap['recievedQty']) ? parseInt(data.selectedRecord.displayNamesMap['qty']) - parseInt(data.selectedRecord.displayNamesMap['recievedQty']) : parseInt(data.selectedRecord.displayNamesMap['qty']);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
					});
				},
				$scope.getMaterialProjDeliveryDockets = function () {
					if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
						GenericAlertService.alertMessage("Please select project to add  material dockets", 'Warning');
						return;
					}
					var workMaterialProjectDocketMap = [];
					angular.forEach($scope.workDairyMaterialDtlTOs, function (value, key) {
						if (value.deliveryDocketId && value.deliveryDocketId[0])
							workMaterialProjectDocketMap[value.deliveryDocketId[0]] = true;
						else if (value.projDocketSchId)
							workMaterialProjectDocketMap[value.projDocketSchId] = true;
					});
					var resultData = WorkDairyMaterialProjDocketFactory.getMaterialProjDockets($scope.workDairySearchReq, $scope.workDairyCostCodeList, $scope.companyMap, $scope.classificationMap, $scope.toProjLabelkeyTO, $scope.userProjectMap, workMaterialProjectDocketMap);
					resultData.then(function (data) {
						$scope.companyMap = data.companyMap;
						$scope.classificationMap = data.classificationMap;
						angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
							$scope.workDairyMaterialDtlTOs.push(value);
						});
						$scope.materialFlag = true;

					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while fetching Material details", 'Error');
					});
				},
				$scope.addSupplierDockets = function () {
					var workDairyMaterialCostTOs = [];
					schItemData.workDairyMaterialStatusTOs = [];
					angular.forEach($scope.workDairyCostCodeList, function (value, key) {
						workDairyMaterialCostTOs.push(angular.copy({
							"costId": value.costId,
							"quantity": null,
							"workDairyId": $scope.workDairySearchReq.workDairyId,
							"status": 1
						}))
					});
					schItemData.workDairyMaterialStatusTOs.push({
						"apprStatus": null,
						"materialDtlId": null,
						"status": 1,
						"comments": null,
						"apprStatus": null,
						"workDairyMaterialCostTOs": angular.copy(workDairyMaterialCostTOs)
					});
					schItemData.supplierDocket = true;
					schItemData.docketType = "new";
					$scope.workDairyMaterialDtlTOs.push(angular.copy(schItemData));
				},
				$scope.deleteMatestoreRecords = function () {
					if (editMatestores.length <= 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

					} else

						if (editMatestores.length < $scope.workDairyMaterialDtlTOs.length) {
							angular.forEach(editMatestores, function (value, key) {
								$scope.workDairyMaterialDtlTOs.splice($scope.workDairyMaterialDtlTOs.indexOf(value), 1);

							});
							editMatestores = [];
						} else if (editMatestores.length > 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						} else if (editMatestores.length <= 1) {
							editMatestores = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
				},
				$scope.addCostCodeItemsToWorkDairy = function (projId, crewId) {
					if ($scope.disableButton) {
						GenericAlertService.alertMessage("Work Diary is already submitted", 'Warning');
						return;
					}
					if ($scope.empFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
						}, function (error) {
							$scope.empFlag = false;
							$scope.addCostCodeItemsToWorkDairy(projId, crewId);
						})
						return;
					} else if ($scope.plantFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyPlantDetails();
						}, function (error) {
							$scope.plantFlag = false;
							$scope.addCostCodeItemsToWorkDairy(projId, crewId);
						})
						return;
					} else if ($scope.materialFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyMaterialDetails();
						}, function (error) {
							$scope.materialFlag = false;
							$scope.addCostCodeItemsToWorkDairy(projId, crewId);
						})
						return;
					} else if ($scope.progressFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyProgressDetails();
						}, function (error) {
							$scope.progressFlag = false;
							$scope.addCostCodeItemsToWorkDairy(projId, crewId);
						})
						return;
					}
					if (projId !== null && crewId != null) {
						var costCodepopup = WorkDairyCostCodeFactory.getCostCodeDetails(projId, crewId, $scope.workDairyCostCodeList, $scope.workDairySearchReq.workDairyId);
						costCodepopup.then(function (data) {
							$scope.workDairyCostCodeList = data;
							if ($scope.workDairySearchReq.workDairyId != undefined && $scope.workDairySearchReq.workDairyId != null) {
								angular.forEach($scope.workDairyEmpDtlTOs, function (value, key) {

									angular.forEach(value.workDairyEmpWageTOs, function (value1, key) {
										$scope.calculateUsedTotal(value1, $scope.workDairySearchReq.empMaxHrs);
										$scope.calculateIdleTotal(value1, $scope.workDairySearchReq.empMaxHrs);

									});
								});
								angular.forEach($scope.workDairyPlantDtlTOs, function (value, key) {

									angular.forEach(value.workDairyPlantStatusTOs, function (value1, key) {
										$scope.calculatePlantUsedTotal(value1, $scope.workDairySearchReq.plantMaxHrs);
										$scope.calculatePlantIdleTotal(value1, $scope.workDairySearchReq.plantMaxHrs);

									});
								});
							}
						}, function (error) {
							GenericAlertService.alertMessage("Error occurred while fetching cost code details", 'Error');
						})
					} else {
						GenericAlertService.alertMessage("Please select project  and crew To add cost codes", 'Warning');
					}
				},
				$scope.getProjSettingsForWorkDairy = function (projId) {
					if (projId == undefined || projId == null) {
						GenericAlertService.alertMessage("Please select project to get contract number", 'Warning');
						return;
					}
					var req = {
						"status": 1,
						"projId": projId

					};
					var resultData = WorkDiaryService.getProjSettingsForWorkDairy(req);
					resultData.then(function (data) {
						$scope.workDairySearchReq.empMaxHrs = data.labelKeyTO.id;
						$scope.workDairySearchReq.plantMaxHrs = 24;
						$scope.workDairySearchReq.contractNo = data.labelKeyTO.code;
						$scope.contractNo = data.labelKeyTO.code;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while gettting project contract number", 'Error');
					});
				},
				$scope.getMaterialDeliveryDockets = function (materialObj) {
					if (materialObj.docketType !== "new") {
						var req = {
							"status": 1,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId
						};
						if (materialObj.docketType === "existing") {
							req.supplierDocket = true;
						}

						let existingDocketIds = [];
						$scope.workDairyMaterialDtlTOs.map(material => {
							if (material.deliveryDocketId)
								existingDocketIds.push(material.deliveryDocketId[0]);
						});

						var materialPopup = WorkDairyMaterialDeliveryDocketFactory.getMaterialDeliveryDocketDetails(req, existingDocketIds);
						materialPopup.then(function (data) {
							materialObj.scheduleItemId = { id: data.id };
							materialObj.receivedQty = data.displayNamesMap['calculatedReceivedQty'];
							materialObj.deliveryPlace = data.displayNamesMap['location'];
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.stockId = data.displayNamesMap['stockId'],
								materialObj.purchaseSchLabelKeyTO.displayNamesMap.projStockId = data.displayNamesMap['projStockId'],
								materialObj.docketNum = data.displayNamesMap['deliveryDockNo'];
							var docketDate = data.displayNamesMap['deliveryDockDate'];
							materialObj.docketDate = moment(docketDate).format("DD-MMM-YYYY");
							materialObj.deliveryDocketId = [];
							materialObj.deliveryDocketId.push(data.displayNamesMap['deliveryDocketId']);
							materialObj.defectComments = data.displayNamesMap['defectComments'];
							materialObj.schItemCmpId = data.displayNamesMap['schItemCmpId'];
							materialObj.purchaseSchLabelKeyTO.code = data.code;
							materialObj.purchaseSchLabelKeyTO.name = data.displayNamesMap['classType'];
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.qty = materialObj.receivedQty;
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.cmpId = data.displayNamesMap['cmpId'];
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.cmpName = data.displayNamesMap['cmpCatgName'];
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.schItemCmpId = data.displayNamesMap['schItemCmpId'];
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.purClassUnitOfMeasure = data.unitOfMeasure;
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.purClassId = data.displayNamesMap['purClassId'];
							materialObj.purchaseSchLabelKeyTO.displayNamesMap.unitOfRate = data.displayNamesMap['unitOfRate'];
							materialObj.purchaseLabelKeyTO.id = data.displayNamesMap['purId'];
							materialObj.purchaseLabelKeyTO.code = data.displayNamesMap['purCode'];
							materialObj.transitQty = data.displayNamesMap['transsitQty'];
							materialObj.projDocketSchId = data.displayNamesMap['projDocketSchId'];
							if (materialObj.docketType === "existing") {
								materialObj.receivedQty = data.displayNamesMap['calculatedReceivedQty'];
								materialObj.maxReceivedQty = parseInt(data.displayNamesMap['calculatedReceivedQty']) - parseInt(data.displayNamesMap['transsitQty']);
							}
							materialObj.deliveryDocketSelected = true;
						}, function (error) {
							GenericAlertService.alertMessage("Error occurred while getting material delivery dockets", 'Error');
						});
					}
				},
				$scope.checkProgressQuantity = function (revisedquantity, progress) {
					$scope.progressFlag = true;
					progress.errorFlag = false;
					var inputvalue = progress.value;
					var balance = progress.quantity - progress.actualQty;
					if (inputvalue > (progress.quantity - progress.actualQty)) {
						$scope.progressFlag = false;
						progress.errorFlag = true;
						GenericAlertService.alertMessage('Progress Quantity cannot exceed SOW item Quantity: ' + balance, "Warning");
						return;
					}
					return false;

				},
				$scope.calculateProgressTotal = function (progress) {
					progress.total = 0;
					progress.total = progress.total + parseFloat(progress.value || 0);
					return progress.total;
				}
				let tweakMaterialDtl = function () {
					let scheduleItemId = [];
					angular.forEach($scope.workDairyMaterialDtlTOs, function (value) {
						if (value.scheduleItemId && Array.isArray(value.scheduleItemId))
							for (var i = 0; i < value.scheduleItemId.length; i++) {
								scheduleItemId.push(value.scheduleItemId[i].id);
							}
						else if (value.scheduleItemId)
							scheduleItemId.push(value.scheduleItemId.id);
						value.scheduleItemId = scheduleItemId;
					});
				}
				
				$scope.getWorkDairy();
				$scope.getWorkDairyDetails();
				$scope.getProjSettingsForWorkDairy($scope.workDairySearchReq.projectLabelKeyTO.projId);
				
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);