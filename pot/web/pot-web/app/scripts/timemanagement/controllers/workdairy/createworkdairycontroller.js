'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("createworkdairy", {
		url: '/createworkdairy',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/timemanagement/workdairy/createworkdairy/createworkdairy.html',
				controller: 'CreateWorkDairyController'
			}
		}
	})

}]).controller(
	'CreateWorkDairyController',
	["$scope", "$state","$q", "ngDialog", "blockUI", "WageFactory", "POScheduleItemsFactory", "PlantRegisterService",
		"WorkDairyDocketFactory", "WorkDairyProgressFactory", "EpsProjectSelectFactory", "WorkDairyCopyFactory",
		"UserService", "$rootScope", "WorkDairySubmitFactory", "WorkDairyEmpFactory", "WorkDairyPlantFactory",
		"MaterialFactory", "ProjectCrewPopupService", "CreateWorkShiftPopupFactory", "WorkDiaryService",
		"ModuleUserFactory", "WeatherService", "CreateWeatherPopupFactory", "WorkDairyCostCodeFactory",
		"RegisterPurchaseOrderFactory", "WorkDairyMaterialProjDocketFactory", "GenericAlertService",
		"RequestForAdditionalTimeFactory", "MaterialRegisterService", "WorkDairyAddProgressFactory", "$filter",
		"RegisterPurchaseOrderItemsFactory", "WorkDairyMaterialDeliveryDocketFactory",
		function ($scope, $state,$q, ngDialog, blockUI, WageFactory, POScheduleItemsFactory, PlantRegisterService, WorkDairyDocketFactory, WorkDairyProgressFactory, EpsProjectSelectFactory, WorkDairyCopyFactory, UserService, $rootScope, WorkDairySubmitFactory, WorkDairyEmpFactory,
			WorkDairyPlantFactory, MaterialFactory, ProjectCrewPopupService, CreateWorkShiftPopupFactory, WorkDiaryService, ModuleUserFactory, WeatherService, CreateWeatherPopupFactory, WorkDairyCostCodeFactory, RegisterPurchaseOrderFactory, WorkDairyMaterialProjDocketFactory,
			GenericAlertService, RequestForAdditionalTimeFactory, MaterialRegisterService, WorkDairyAddProgressFactory,
			$filter, RegisterPurchaseOrderItemsFactory, WorkDairyMaterialDeliveryDocketFactory) {
			$scope.selectedEmp = null;
			$scope.selectedIndex = null;
			$scope.selectedRow = null;
			$scope.timeFlag = false;

			$scope.currentTab = null;
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
			$scope.selectedFileMap = [];
			$scope.workDairySearchReq = {
				"workDairyId": null,
				"code": null,
				"workDairyDate": null,
				"projectLabelKeyTO": {
					"projId": null,
					"parentName": null,
					"projName": null

				},
				"crewLabelKeyTO": {
					"id": null,
					"code": null,
					"name": null

				},
				"weatherLabelKeyTO": {
					"id": null,
					"code": null,
					"name": null

				},
				"shiftLabelKeyTO": {
					"id": null,
					"code": null,
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
				"workDairyDate": null,
				"empMaxHrs": null,
				"plantMaxHrs": null,
				"contractType": 'Head-Company',
				"purId": null,
				"contractNo": null,
				"type": 'Original',
				"quantity": null,
				"notificationMsg": 'Request for Additional Time'

			};

			$scope.contractNo = null;

			$scope.flag = 0;
			$scope.$watch('workDairySearchReq.workDairyDate', function (newValue, oldValue) {

				if (newValue != oldValue && oldValue != null) {

					$scope.workDairySearchReq.crewLabelKeyTO = {};
					$scope.workDairySearchReq.code = null;
					$scope.projShiftMap = [];
					$scope.weatherMap = [];
					$scope.workDairyEmpDtlTOs = [];
					$scope.workDairyPlantDtlTOs = [];
					$scope.workDairyMaterialDtlTOs = [];
					$scope.workDairyProgressDtlTOs = [];
					return;
				}

			});
			$scope.clickForwardData = function (moreFlag) {
				if ($scope.flag < 1) {
					$scope.flag = moreFlag + 1;
				}
			}, $scope.clickBackwardData = function (moreFlag) {
				if ($scope.flag > 0) {
					$scope.flag = moreFlag - 1;
				}
			}

			$scope.isExistingDocket = function (materialObj) {
				return (materialObj.docketType == 'existing');
			}

			$scope.getUserProjects = function (projectLabelKeyTO) {
				var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
				userProjectSelection.then(function (data) {
					$scope.searchProject = data.searchProject;
					projectLabelKeyTO.projId = data.searchProject.projId;
					projectLabelKeyTO.parentName = data.searchProject.parentName;
					projectLabelKeyTO.projName = data.searchProject.projName;
					$scope.workDairySearchReq.contractNo = null;
					$scope.workDairySearchReq.contractType = 'Head-Company';
					$scope.getProjSettingsForWorkDairy(projectLabelKeyTO.projId);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			},
				$scope.conformFlag = function () {


					$scope.manpowerForm = true;

				}

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
			}, $scope.selectContractType = function () {
				if ($scope.workDairySearchReq.contractType == 'Head-Company') {
					$scope.workDairySearchReq.contractNo = $scope.contractNo;
				} else {
					$scope.workDairySearchReq.contractNo = null;
				}

			}, $scope.selectSubContract = function (projId) {
				if (projId == undefined || projId == null) {
					GenericAlertService.alertMessage("Please select project  to get sub-contracts", 'Warning');
					return;
				}
				var req = {
					"status": 1,
					"projId": projId,
					"procureType": 'SOW'
				};
				RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function (data) {
					$scope.workDairySearchReq.purId = data.purchaseOrderTO.id;
					$scope.workDairySearchReq.contractNo = data.purchaseOrderTO.code;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
				});

			}, $scope.getCrewList = function (projId, crewLabelKeyTO) {
				if (projId == null || $scope.workDairySearchReq.workDairyDate == null) {
					GenericAlertService.alertMessage("Please select project and work diary date to get crews", 'Warning');
					return;
				}
				var crewReq = {
					"status": 1,
					"projId": projId

				};
				var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
				crewSerivcePopup.then(function (data) {
					crewLabelKeyTO.id = data.projCrewTO.id;
					crewLabelKeyTO.code = data.projCrewTO.code;
					$scope.getWorkDairy();
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
				});
			}, $scope.createWorkDairy = function (projId, crewId) {
				if (projId == null) {
					GenericAlertService.alertMessage("Please select project to create Work Diary", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.workDairyDate == null) {
					GenericAlertService.alertMessage("Please select Date to create Work Diary", 'Warning');
					return;
				}
				if (crewId == null) {
					GenericAlertService.alertMessage("Please select Crew to create Work Diary", 'Warning');
					return;
				}
				if ($scope.workDairyCostCodeList.length <= 0) {
					GenericAlertService.alertMessage("Please select Cost Codes to create Work Diary", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.contractNo <= 0) {
					GenericAlertService.alertMessage("Please select Contract Number", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.weatherLabelKeyTO.id == undefined || $scope.workDairySearchReq.weatherLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Weather to create Work Diary", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.shiftLabelKeyTO.id == undefined || $scope.workDairySearchReq.shiftLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Working Shift to create Work Diary", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.workDairyId > 0) {
					GenericAlertService.alertMessage("Work Diary already available, Please search to get the details", 'Warning');
					return;
				}
				// Confirmation Flag
				if ($scope.empFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
					}, function (error) {
						$scope.empFlag = false;
						$scope.createWorkDairy(projId, crewId);
					})
					return;
				} else if ($scope.plantFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyPlantDetails();
					}, function (error) {
						$scope.plantFlag = false;
						$scope.createWorkDairy(projId, crewId);
					})
					return;
				} else if ($scope.materialFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyMaterialDetails();
					}, function (error) {
						$scope.materialFlag = false;
						$scope.createWorkDairy(projId, crewId);
					})
					return;
				} else if ($scope.progressFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyProgressDetails();
					}, function (error) {
						$scope.progressFlag = false;
						$scope.createWorkDairy(projId, crewId);
					})
					return;
				}

				//remove ids from workDairyCostCodeList
				angular.forEach($scope.workDairyCostCodeList, function (value, key) {
					value.id = null;
				});
				var req = {
					"status": 1,
					'workDairyDate': $scope.workDairySearchReq.workDairyDate,
					"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
					"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
					"workDairyTO": {
						"status": 1,
						'workDairyDate': $scope.workDairySearchReq.workDairyDate,
						"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
						"weatherId": $scope.workDairySearchReq.weatherLabelKeyTO.id,
						"shiftId": $scope.workDairySearchReq.shiftLabelKeyTO.id,
						"workDairyCostCodeTOs": $scope.workDairyCostCodeList,
						"contractType": $scope.workDairySearchReq.contractType,
						"contractNo": $scope.workDairySearchReq.contractNo,
						"purId": $scope.workDairySearchReq.purId
					},
				};

				blockUI.start();
				WorkDiaryService.createWorkDairy(req).then(function (data) {
					blockUI.stop();
					$scope.workDairySearchReq.workDairyId = data.workDairyTO.id;
					$scope.workDairySearchReq.code = data.workDairyTO.code;
					$scope.populateEmpDetails(data.empRegDetails);
					$scope.populatePlantDetails(data.plantRegDetails);
					$scope.populateMaterialMaps(data.materialProjResp);
					$scope.getWorkDairyDetails($scope.workDairySearchReq.projectLabelKeyTO.projId);

				}, function (error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while creating Workdairy", 'Error');
				});
			}, $scope.populateEmpDetails = function (empRegDetails) {
				var workDairyEmpCostDtlTOs = [];
				angular.forEach($scope.workDairyCostCodeList, function (value, key) {
					workDairyEmpCostDtlTOs.push(angular.copy({
						"costId": value.costId,
						"usedTime": null,
						"idleTime": null,
						"workDairyId": $scope.workDairySearchReq.workDairyId,
						"status": 1
					}))
				});
				var workDairyEmpWageTOs = []
				workDairyEmpWageTOs.push({
					"wageId": null,
					"status": 1,
					"workDairyEmpCostDtlTOs": workDairyEmpCostDtlTOs
				});
				angular.forEach(empRegDetails, function (value, key) {
					$scope.workDairyEmpDtlTOs.push(angular.copy({
						"workDairyId": $scope.workDairySearchReq.workDairyId,
						"empRegId": value.id,
						"status": 1,
						"workDairyEmpWageTOs": workDairyEmpWageTOs
					}));
				});
			}, $scope.populatePlantDetails = function (plantRegDetails) {
				var workDairyPlantCostDtlTOs = [];
				angular.forEach($scope.workDairyCostCodeList, function (value, key) {
					workDairyPlantCostDtlTOs.push(angular.copy({
						"costId": value.costId,
						"usedTime": null,
						"idleTime": null,
						"workDairyId": $scope.workDairySearchReq.workDairyId,
						"status": 1
					}))
				});
				angular.forEach(plantRegDetails, function (value, key) {
					$scope.workDairyPlantDtlTOs.push(angular.copy({
						"workDairyId": $scope.workDairySearchReq.workDairyId,
						"plantRegId": value.id,
						"shiftName": null,
						"status": 1,
						"workDairyPlantStatusTOs": [{
							"workDairyPlantCostDtlTOs": workDairyPlantCostDtlTOs
						}]
					}));
				});
			}, $scope.getWorkDairy = function () {

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
			}, $scope.getWorkDairyDetails = function (projId) {
				// Confirmation Flag

				if ($scope.empFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
					}, function (error) {
						$scope.empFlag = false;
						$scope.getWorkDairyDetails(projId);
					})
					return;
				} else if ($scope.plantFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyPlantDetails();
					}, function (error) {
						$scope.plantFlag = false;
						$scope.getWorkDairyDetails(projId);
					})
					return;
				} else if ($scope.materialFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyMaterialDetails();
					}, function (error) {
						$scope.materialFlag = false;
						$scope.getWorkDairyDetails(projId);
					})
					return;
				} else if ($scope.progressFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyProgressDetails();
					}, function (error) {
						$scope.progressFlag = false;
						$scope.getWorkDairyDetails(projId);
					})
					return;
				}

				$scope.activeFlag = true;
				$scope.timeFlag = false;
				var workDairyGetReq = {
					'workDairyId': $scope.workDairySearchReq.workDairyId,
					"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id,
					"status": 1,
					"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
					"apprStatus": 'Original Submission'
				};
				// if ($scope.workDairySearchReq == undefined || projId <= 0) {
				// 	GenericAlertService.alertMessage("Please select project", 'Warning');
				// 	return;
				// }
				// if ($scope.workDairySearchReq.workDairyDate == null) {
				// 	GenericAlertService.alertMessage("Please select Date", 'Warning');
				// 	return;
				// }
				// if ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) {
				// 	GenericAlertService.alertMessage("Please select Crew", 'Warning');
				// 	return;
				// }
				
				if (($scope.workDairySearchReq == undefined || projId <= 0) && $scope.workDairySearchReq.workDairyDate == null
				 && ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) ) {
					GenericAlertService.alertMessage("Please select Project, Date, Crew", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.workDairyDate == null
					&& ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) ) {
						GenericAlertService.alertMessage("Please select Date, Crew", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Crew", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.workDairyId == undefined || $scope.workDairySearchReq.workDairyId == null) {
					GenericAlertService.alertMessage("Please Create Work Diary Id before search", 'Warning');
					return;
				}
				WorkDiaryService.getWorkDairyDetails(workDairyGetReq).then(function (data) {
				//	$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
					$scope.workDairyEmpDtlTOs = $filter('unique')(data.workDairyEmpDtlTOs, 'code');
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
			$scope.submitWorkDairyForapproval = function () {
				var validateFalg = true;

				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to submit", 'Warning');
					return;
				}
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
						return;
					}
					tweakMaterialDtl();
					var submitpopup = WorkDairySubmitFactory.getWorkDairySubmitDetails($scope.workDairySearchReq, $scope.workDairyEmpDtlTOs, $scope.workDairyPlantDtlTOs, $scope.workDairyMaterialDtlTOs, $scope.workDairyProgressDtlTOs);
					submitpopup.then(function (data) {
						$scope.workDairySearchReq.apprStatus = data.workDairyTO.apprStatus;
						if ($scope.workDairySearchReq.apprStatus != undefined && $scope.workDairySearchReq.apprStatus != null) {
							$scope.disableButton = true;
						}
						$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
						$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
						$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						$scope.workDairyMaterialDtlTOs = [];
						angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
							$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
						});
						$state.go("listofcreatedworkdairy");
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while submitting WorkDairy", 'Error');
					});


				}

			}, $scope.getWorkingShiftId = function (projId, shiftLabelKeyTO) {
				if (projId == undefined || projId == null) {
					GenericAlertService.alertMessage("Please select project to get WorkingShift", 'Warning');
					return;
				}
				var workshiftPopup = [];
				workshiftPopup = CreateWorkShiftPopupFactory.workShiftDetailsList(projId);
				workshiftPopup.then(function (data) {
					shiftLabelKeyTO.id = data.projWorkShiftTO.id;
					$scope.workDairySearchReq.shiftLabelKeyTO.code = data.projWorkShiftTO.code;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting Working Shift Details", 'Error');
				});
			},

				$scope.getWeatherClassId = function (projId, weatherLabelKeyTO) {
					if (projId == undefined || projId == null) {
						GenericAlertService.alertMessage("Please select project to get Weather", 'Warning');
						return;
					}
					var weatherPopup = [];
					weatherPopup = CreateWeatherPopupFactory.weatherDetailsList(projId);
					weatherPopup.then(function (data) {
						weatherLabelKeyTO.id = data.weatherTO.id;
						weatherLabelKeyTO.code = data.weatherTO.code;
						weatherLabelKeyTO.name = data.weatherTO.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting Weather Details", 'Error');
					});
				}, $scope.addCostCodeItemsToWorkDairy = function (projId, crewId) {
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
				}, $scope.getWageFactor = function (workDetails, workDairyEmpWageTOs) {
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
				}

			$scope.createWorkDiaryTabs = [{
				title: 'Progress',
				url: 'views/timemanagement/workdairy/createworkdairy/workdairyprogress.html',
				createWorkDairySeleniumLocator: 'AsbuiltRecords_WorkDiary_CreateWorkDiary_Progress'
			}, {
				title: 'Manpower Utilisation',
				url: 'views/timemanagement/workdairy/createworkdairy/workdairymanpower.html',
				createWorkDairySeleniumLocator: 'AsbuiltRecords_WorkDiary_CreateWorkDiary_ManpowerUtilization'
			}, {
				title: 'Plant Utilisation',
				url: 'views/timemanagement/workdairy/createworkdairy/workdairyplant.html',
				createWorkDairySeleniumLocator: 'AsbuiltRecords_WorkDiary_CreateWorkDiary_PlantUtilization'
			}, {
				title: 'Material and Store Consumption',
				url: 'views/timemanagement/workdairy/createworkdairy/workdairymaterial.html',
				createWorkDairySeleniumLocator: 'AsbuiltRecords_WorkDiary_CreateWorkDiary_MaterialandStoreConsumption'
			}];

			$scope.currentTab = 'views/timemanagement/workdairy/createworkdairy/workdairyprogress.html';
			$scope.onClickTab = function (tab) {
				$scope.currentTab = tab.url;
				if ($scope.empFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
					}, function (error) {
						$scope.workDairyEmpDtlTOs = [];
						$scope.empFlag = false;
						$scope.onClickTab(tab);
					})
					return;
				}

				else if ($scope.plantFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyPlantDetails();
					}, function (error) {
						$scope.workDairyPlantDtlTOs = [];
						$scope.plantFlag = false;
						$scope.onClickTab(tab);
					})
					return;
				} else if ($scope.materialFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyMaterialDetails();
					}, function (error) {
						$scope.materialFlag = false;
						$scope.onClickTab(tab);
					})
					return;
				} else if ($scope.progressFlag == true) {
					GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
						$scope.saveWorkDairyProgressDetails();
					}, function (error) {
						$scope.workDairyProgressDtlTOs = [];
						$scope.progressFlag = false;
						$scope.onClickTab(tab);
					})
					return;
				}
			}, $scope.isActiveTab = function (tabUrl) {
				return tabUrl == $scope.currentTab;
			}

			/* ==========Manpower============== */
			$scope.manpowerRowSelect = function (man) {
				if (man.select) {
					editMans.push(man);
				} else {
					editMans.pop(man);
				}
			}
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
				$scope.deleteEmpRecords = function () {
					if (editMans.length <= 0) {
						GenericAlertService.alertMessage(
							'Please Select Atleast One Row to Delete', "Warning");
						return;
					}
					if (editMans.length < $scope.workDairyEmpDtlTOs.length) {
						angular.forEach(editMans, function (value, key) {
							$scope.workDairyEmpDtlTOs.splice($scope.workDairyEmpDtlTOs
								.indexOf(value), 1);
							editMans = [];

						});
					} else {
						GenericAlertService.alertMessage(
							'Please leave atleast one row', "Warning");
					}
				}


			$scope.calculateUsedTotal = function (workDetails, empMaxHrs) {

				$scope.empFlag = true;
				workDetails.usedTotal = 0;
				var usedTime = 0;
				var usedTimeHours = 0;
				var usedTimeMinutes = 0;
				var totHours = 0;
				var totMinutes = 0;
				angular.forEach(workDetails.workDairyEmpCostDtlTOs, function (value, key) {

					if (value.usedTime == null && value.usedTime == undefined) {
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
					workDetails.usedTotal = totHours + totMinutes;
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
					return;
				}
				return (workDetails.usedTotal).toFixed(2);
			}

			$scope.calculateIdleTotal = function (workDetails, empMaxHrs) {
				$scope.empFlag = true;
				var idleTime = 0;
				var idleTimeHours = 0;
				var idleTimeMinutes = 0;
				var totHours = 0;
				var totMinutes = 0;
				workDetails.idleTotal = 0;
				angular.forEach(workDetails.workDairyEmpCostDtlTOs, function (value, key) {
					if (value.idleTime == null && value.idleTime == undefined) {
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
					return;
				}
				return (workDetails.idleTotal).toFixed(2);

			}
			
			$scope.onLoadCalculatePlantTotal = function (usedTotal, idleTotal, obj) {
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
			}
			
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
			}
			/* ==========plant============== */

			$scope.plantRowSelect = function (plant) {
				if (plant.select) {
					editPlants.push(plant);
				} else {
					editPlants.pop(plant);
				}
			}
			$scope.addPlantRegDetails = function () {
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
					angular.forEach(data.workDairyPlantDtlTOs, function (value, key) {
						$scope.workDairyPlantDtlTOs.push(angular.copy(value));
					});
					$scope.plantFlag = true;
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while fetching Plants", 'Error');
				});
			}, $scope.deletePlantRecords = function () {
				if (editPlants.length <= 0) {
					GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
					return;
				}
				if (editPlants.length < $scope.workDairyPlantDtlTOs.length) {
					angular.forEach(editPlants, function (value, key) {
						$scope.workDairyPlantDtlTOs.splice($scope.workDairyPlantDtlTOs.indexOf(value), 1);
						editPlants = [];

					});
				} else {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
				GenericAlertService.alertMessage('Plant deleted Successfully', 'Info');
				return;
			}, $scope.saveWorkDairyPlantDetails = function (plantForm) {
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
					} else {
						$scope.errorPlantFlag = false;
						$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
						GenericAlertService.alertMessage('Plant Details saved successfully', "Info");
					}
				}, function (error) {
					blockUI.stop();
					GenericAlertService.alertMessage('Plant Details are failed to save', "Error");
				});
			}
				, $scope.calculatePlantUsedTotal = function (plantStatusTO, plantMaxHrs) {
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
						return;
					}
					var tota = plantStatusTO.usedTotal + plantStatusTO.idleTotal;
					if (tota > plantMaxHrs) {
						$scope.plantFlag = false;
						GenericAlertService.alertMessage('Work Diary Total Hours cannot be more than Max Hours', "Warning");
						return;
					}
					return plantStatusTO.usedTotal;
				}

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
					return;
				}
				var tota = plantStatusTO.usedTotal + plantStatusTO.idleTotal;
				if (tota > plantMaxHrs) {
					$scope.plantFlag = false;
					GenericAlertService.alertMessage('Work Diary Total Hours cannot be more than Max Hours', "Warning");
					return;
				}
				return plantStatusTO.idleTotal;
			}
			/* ===========material========== */

			$scope.materialStoreRowSelect = function (supplier) {
				if (supplier.select) {
					editMatestores.push(supplier);
				} else {
					editMatestores.pop(supplier);
				}
			}

			$scope.dockettype = function () {

				var materialPopup = WorkDairyDocketFactory.docTypeDetails();
				materialPopup.then(function (data) {
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while fetching dockettype details", 'Error');
				});
			}

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
			}, $scope.getPurchaseOrdersForMaterial = function (materialObj) {
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

			}, $scope.getScheduleItems = function (materialObj) {
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

			}, $scope.onReceivedQtyChanged = function (materialObj) {
				if (materialObj.receivedQty > materialObj.maxReceivedQty) {
					GenericAlertService.alertMessage("Received quantity cannot be higher than " + materialObj.maxReceivedQty + " (available quantity). ", 'Error');
				}

			}, $scope.populateMaterialMaps = function (materialProjResp) {

				if (materialProjResp.registerOnLoadTO != null) {
					$scope.companyMap = materialProjResp.registerOnLoadTO.companyMap;
					$scope.classificationMap = materialProjResp.registerOnLoadTO.classificationMap;
				}
				$scope.userProjectMap = materialProjResp.userProjMap;
				angular.forEach(materialProjResp.materialProjDtlTOs, function (value, key) {
					$scope.materialProjDtlMap[value.id] = value;
				});
			}, $scope.getMaterialDeliveryDockets = function (materialObj) {
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
				}, $scope.getMaterialProjDeliveryDockets = function () {
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
				}, $scope.saveWorkDairyMaterialDetails = function () {
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

				}, $scope.addMaterial = function (materialTOs, value, scheduleItemId) {
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
				}, $scope.populateMaterialObj = function (materialTOs, value) {
					for (var i = 0; i < value.materialProjDtlTO.length; i++) {
						$scope.addMaterial(materialTOs, value, value.materialProjDtlTO[i]);
					}
				}, $scope.deleteMatestoreRecords = function () {
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
				}, $scope.calculateMaterialTotal = function (materialObjStatusTO) {
					materialObjStatusTO.total = 0;
					angular.forEach(materialObjStatusTO.workDairyMaterialCostTOs, function (value, key) {
						if (value.quantity == undefined) {
							value.quantity = 0;
						}
						materialObjStatusTO.total = parseFloat(materialObjStatusTO.total || 0) + parseFloat(value.quantity || 0);
					});
					return materialObjStatusTO.total;
				}

			/* ============progress========== */
				
			$scope.onLoadCalculateProgressTotal = function (progress) {
				progress.total = 0;
				progress.total = progress.total + parseFloat(progress.value || 0);
				return progress.total;
			}

			$scope.progressRowSelect = function (progress) {
				if (progress.select) {
					editProgress.push(progress);
				} else {
					editProgress.pop(progress);
				}
			}
			$scope.calculateProgressTotal = function (progress) {
				progress.total = 0;
				progress.total = progress.total + parseFloat(progress.value || 0);
				return progress.total;
			}
			$scope.addProgressDetails = function () {
				console.log("addProgressDetails function");
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
			}, $scope.addMoreSowItems = function () {
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
			}, $scope.addProgressPOScheduleItems = function () {
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
					GenericAlertService.alertMessage('WorkDairy cannot be booked more tham SOW Item Quantity', "Warning");
					return;
				}
				if($scope.balanceQty){
					GenericAlertService.alertMessage('Progress Quantity cannot exceed SOW item Quantity', 'Warning');
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
				/*$scope.checkProgressQuantity = function (revisedquantity, progress) {
				$scope.progressFlag = true;
				progress.errorFlag = false;
				var inputvalue = progress.value;
				console.log(inputvalue > (progress.quantity - progress.actualQty))	
			 	if (inputvalue > (progress.quantity - progress.actualQty)) {
					$scope.balanceQty = (inputvalue > (progress.quantity - progress.actualQty));
					$scope.progressFlag = false;
					progress.errorFlag = true;
					GenericAlertService.alertMessage('WorkDairy cannot be booked more tham SOW Item Quantity', "Warning");
					return;
				}
				return false;

			},*/
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
			}, $scope.deleteProgressRecords = function () {
				if (editProgress.length <= 0) {
					GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
					return;
				}
				if (editProgress.length < $scope.workDairyProgressDtlTOs.length) {
					angular.forEach(editProgress, function (value, key) {
						$scope.workDairyProgressDtlTOs.splice($scope.workDairyProgressDtlTOs.indexOf(value), 1);

					});
				} else {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
			}, $scope.getCopyTemplate = function () {
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
						data.workDairyEmpDtlTOs[key].id = null;
						data.workDairyEmpDtlTOs[key].empCategory = null;
						data.workDairyEmpDtlTOs[key].unitOfMeasure = null;
						data.workDairyEmpDtlTOs[key].mobilizationDate = null;
						data.workDairyEmpDtlTOs[key].empClassId = null;
						angular.forEach(data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs, function (value1, key1) {
							data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs[key1].id = null;
							data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs[key1].empDtlId = null;
							angular.forEach(data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs[key1].workDairyEmpCostDtlTOs, function (value2, key2) {
								data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs[key1].workDairyEmpCostDtlTOs[key2].id = null;
								data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs[key1].workDairyEmpCostDtlTOs[key2].wageCostId = null;
								data.workDairyEmpDtlTOs[key].workDairyEmpWageTOs[key1].workDairyEmpCostDtlTOs[key2].oldUsedTime = null;
							});
						});
					});
					angular.forEach(data.workDairyPlantDtlTOs, function (value, key) {
						$scope.workDairyPlantDtlTOs.push(value);
						if(value.workDairyPlantStatusTOs[0].usedTotal != null  || value.workDairyPlantStatusTOs[0].idleTotal != null) {
							$scope.onLoadCalculatePlantTotal(value.workDairyPlantStatusTOs[0].usedTotal, value.workDairyPlantStatusTOs[0].idleTotal, value.workDairyPlantStatusTOs[0]);
						}
						data.workDairyPlantDtlTOs[key].id = null;
						data.workDairyPlantDtlTOs[key].plantClassId = null;
						angular.forEach(data.workDairyPlantDtlTOs[key].workDairyPlantStatusTOs, function (value1, key1) {
							data.workDairyPlantDtlTOs[key].workDairyPlantStatusTOs[key1].id = null;
							data.workDairyPlantDtlTOs[key].workDairyPlantStatusTOs[key1].plantDtlId = null;
							angular.forEach(data.workDairyPlantDtlTOs[key].workDairyPlantStatusTOs[key1].workDairyPlantCostDtlTOs, function (value2, key2) {
								data.workDairyPlantDtlTOs[key].workDairyPlantStatusTOs[key1].workDairyPlantCostDtlTOs[key2].id = null;
								data.workDairyPlantDtlTOs[key].workDairyPlantStatusTOs[key1].workDairyPlantCostDtlTOs[key2].plantStatusId = null;
							});
						});
					});
					angular.forEach(data.workDairyProgressDtlTOs, function (value, key) {
						$scope.workDairyProgressDtlTOs.push(value);
						data.workDairyProgressDtlTOs[key].id = null;
						if(value.value != null) {
							$scope.onLoadCalculateProgressTotal(value);
						}
					});
					angular.forEach(data.workDairyMaterialDtlTOs, function (value, key) {
						
						angular.forEach(value.workDairyMaterialStatusTOs, function (value1, key1) {
							value.workDairyMaterialStatusTOs[key1].id = null;
							value.workDairyMaterialStatusTOs[key1].materialDtlId = null;
							angular.forEach(value.workDairyMaterialStatusTOs[key1].workDairyMaterialCostTOs, function (value2, key2) {
								value.workDairyMaterialStatusTOs[key1].workDairyMaterialCostTOs[key2].workDairyId = $scope.workDairySearchReq.workDairyId;
								value.workDairyMaterialStatusTOs[key1].workDairyMaterialCostTOs[key2].id = null;
								value.workDairyMaterialStatusTOs[key1].workDairyMaterialCostTOs[key2].materialStatusId = null;
							})
						});
						
						let data = {
							"id": null,
							"selected": false,
							"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"sourceType": value.sourceType,
							"docketType": value.docketType,
							"projDocketSchId": value.projDocketSchId,
							"scheduleItemId": value.scheduleItemId[0],
							"schItemCmpId": value.materialProjDtlTO[0].purchaseSchLabelKeyTO.displayNamesMap.schItemCmpId,
							"deliveryDocketId": null,
							
							"docketNum": value.docketNum,
							"docketDate": value.docketDate,
							"supplierDocket": undefined,
							
							"onLoadDocket": false,
							"deliveryPlace": value.deliveryPlace,
							
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
							"workDairyMaterialStatusTOs" : value.workDairyMaterialStatusTOs,
							"transitQty": value.transitQty,
							"openingStock": value.openingStock,
							"closingStock": value.closingStock
						}
						$scope.workDairyMaterialDtlTOs.push(data);
					});

				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
				})

			}, $scope.getWorkDairyNotifications = function (flag) {

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

				$scope.resetCreateWorkDairy = function () {
					if ($scope.empFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyEmpDetails($scope.workDairySearchReq);
						}, function (error) {
							$scope.workDairyEmpDtlTOs = [];
							$scope.empFlag = false;
							$scope.resetCreateWorkDairy();
						})
						return;
					} else if ($scope.plantFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyPlantDetails();
						}, function (error) {
							$scope.plantFlag = false;
							$scope.resetCreateWorkDairy();
						})
						return;
					} else if ($scope.materialFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyMaterialDetails();
						}, function (error) {
							$scope.materialFlag = false;
							$scope.resetCreateWorkDairy();
						})
						return;
					} else if ($scope.progressFlag == true) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveWorkDairyProgressDetails();
						}, function (error) {
							$scope.progressFlag = false;
							$scope.resetCreateWorkDairy();
						})
						return;
					}
					$scope.empWageFactorMap = [];
					$scope.empRegMap = [];
					$scope.plantRegMap = [];
					$scope.materialRegMap = [];
					$scope.timeFlag = false;
					$scope.workDairyEmpDtlTOs = [];
					$scope.workDairyPlantDtlTOs = [];
					$scope.workDairyMaterialDtlTOs = [];
					$scope.workDairyProgressDtlTOs = [];
					$scope.workDairyCostCodeList = [];
					$scope.workDairyCostCodeMap = [];
					$scope.workDairySearchReq = {
						"projectLabelKeyTO": {
							"projId": null,
							"parentName": null,
							"projName": null

						},
						"crewLabelKeyTO": {
							"id": null,
							"code": null,
							"name": null

						},
						"weatherLabelKeyTO": {
							"id": null,
							"code": null,
							"name": null

						},
						"shiftLabelKeyTO": {
							"id": null,
							"code": null,
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
						"workDairyDate": null
					};
				}, $scope.checkDecimal = function (costObj, indexValue, empMaxHrs) {
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
				}
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
			}
			$scope.rowSelectEmpReg = function (empObj, indexValue) {
				$scope.selectedEmp = empObj;
				$scope.selectedRow = indexValue;
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

				}, $scope.deleteEmpReg = function (selectedEmp, workDetails) {
					selectedEmp.workDairyEmpWageTOs.pop(workDetails);
				}
			$scope.checkProgressQuantity = function (revisedquantity, progress) {
				$scope.progressFlag = true;
				progress.errorFlag = false;
				var inputvalue = progress.value;
				console.log(inputvalue > (progress.quantity - progress.actualQty))	
			 	if (inputvalue > (progress.quantity - progress.actualQty)) {
					$scope.balanceQty = (inputvalue > (progress.quantity - progress.actualQty));
					$scope.progressFlag = false;
					progress.errorFlag = true;
					GenericAlertService.alertMessage('WorkDairy cannot be booked more tham SOW Item Quantity', "Warning");
					return;
				}
				return false;

			}, $scope.validateQuantity = function (progress) {
				if (progress.value > progress.revisedquantity) {
					return true;
				}
				return false;
			}, $scope.checkMaterialQuantity = function (materialObj, materialObjStatusTO, usedMaterialObj) {
				var total = materialObjStatusTO.total;
				usedMaterialObj.errorFlag = false;
				if (materialObj.supplierDocket) {
					if (total > materialObj.receivedQty || total > materialObj.maxReceivedQty) {
						usedMaterialObj.errorFlag = true;
						GenericAlertService.alertMessage('WorkDiary cannot be booked more than Received Quantity', "Warning");
						return;
					}
				} else {
					if (total > materialObj.availableQty) {
						usedMaterialObj.errorFlag = true;
						GenericAlertService.alertMessage('WorkDairy cannot be booked more tham Received Quantity', "Warning");
						return;
					}
				}
				usedMaterialObj.errorFlag = false;
			}, $scope.validateMaterialQuantity = function (materialObj) {
				if (materialObj.availableQty < materialObj.workDairyMaterialStatusTOs[0].total) {
					return true;
				}
				return false;
			}

			/*
			 * $scope.validateTotalDayWiseWageHrs =
			 * function(workDairyEmpCostDtlTOs, indexValue, maxHrs) { var
			 * workDetails = {}; var totalWageHrs = 0; workDetails.errorFlag =
			 * false; angular.forEach(workDairyEmpCostDtlTOs, function(value,
			 * key) { if (indexValue == 1) { totalWageHrs = totalWageHrs +
			 * parseFloat(value.usedTime); } else if (indexValue == 2) {
			 * totalWageHrs = totalWageHrs + parseFloat(value.idleTime); }
			 * workDetails = value; }); if (totalWageHrs > maxHrs) {
			 * workDetails.errorFlag = true;
			 * GenericAlertService.alertMessage('workdairy cannot be booked
			 * morethan max hours', "Warning"); return; } workDetails.errorFlag =
			 * false; }
			 */
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
				$scope.supplierReceivedVal = function (material) {
					if (material.supplierDocket) {
						if (material.receivedQty > material.maxReceivedQty) {
							GenericAlertService.alertMessage("Received cannot exceed available quantity of " + material.maxReceivedQty, "Warning");
						}
					}
				}

			$scope.clear1 = function () {
				$scope.workDairySearchReq.workDairyDate = null;
				$scope.workDairySearchReq.crewLabelKeyTO.code = null;
				$scope.workDairySearchReq.shiftLabelKeyTO.code = null;
				$scope.workDairySearchReq.weatherLabelKeyTO.code = null;
				
			}
			var HelpService = {};
			$scope.helpPage = function () {
				var help = HelpService.pageHelp();
				help.then(function(data) {

				}, function(error) {
					GenericAlertService.alertMessage("Error",'Info');
				})
			}
			var helppagepopup;
			HelpService.pageHelp = function () {
				var deferred = $q.defer();
				helppagepopup = ngDialog.open({
					template: 'views/help&tutorials/asbuiltrecordshelp/workdiary/createworkdiaryhelp.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom1',
					scope: $scope,
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function ($scope) {
			
					}]
				});
				return deferred.promise;
			}
		}]);