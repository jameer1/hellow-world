'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("workdairyclientapproval", {
		url : '/workdairyclientapproval',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapproval.html',
				controller : 'WorkDairyClientApprovalController'
			}
		}
	})
}]).controller(
		'WorkDairyClientApprovalController',
		["$scope", "$state","$q", "ngDialog", "blockUI", "WorkDairyClientApprovalFactory", "WageFactory", "POScheduleItemsFactory", "WorkDairyDocketFactory", "WorkDairyProgressFactory", "EpsProjectSelectFactory", "UserService", "$rootScope", "WorkDairyEmpFactory", "WorkDairyPlantFactory", "MaterialFactory", "ProjectCrewPopupService", "CreateWorkShiftPopupFactory", "WorkDiaryService", "ModuleUserFactory", "CreateWeatherPopupFactory", "WorkDairyCostCodeFactory", "RegisterPurchaseOrderFactory", "WorkDairyMaterialProjDocketFactory", "GenericAlertService", "RequestForAdditionalTimeFactory", "MaterialRegisterService", "WorkDairyAddProgressFactory", function($scope, $state,$q, ngDialog, blockUI, WorkDairyClientApprovalFactory, WageFactory, POScheduleItemsFactory, WorkDairyDocketFactory, WorkDairyProgressFactory, EpsProjectSelectFactory,
				UserService, $rootScope, WorkDairyEmpFactory, WorkDairyPlantFactory, MaterialFactory, ProjectCrewPopupService,
				CreateWorkShiftPopupFactory, WorkDiaryService, ModuleUserFactory, CreateWeatherPopupFactory, WorkDairyCostCodeFactory, RegisterPurchaseOrderFactory,
				WorkDairyMaterialProjDocketFactory, GenericAlertService, RequestForAdditionalTimeFactory, MaterialRegisterService, WorkDairyAddProgressFactory) {
			$scope.currentTab = null;
			var editMans = [];
			var editPlants = [];
			var editMatestores = [];
			var editProgress = [];
			$scope.supplierDockets = [];
			$scope.workDairyCostCodeMap = [];
			$scope.empWageFactorMap = [];
			$scope.empRegMap = [];
			$scope.plantRegMap = [];
			$scope.materialRegMap = [];
			$scope.materialProjDtlMap = [];
			$scope.sowMap = [];
			$scope.activeFlag = false;
			$scope.weatherMap = [];
			$scope.projShiftMap = [];
			$scope.workDairyEmpDtlTOs = [];
			$scope.workDairyPlantDtlTOs = [];
			$scope.workDairyMaterialDtlTOs = [];
			$scope.workDairyProgressDtlTOs = [];
			$scope.workDairyCostCodeList = [];
			$scope.disableButton = false;
			$scope.timeFlag=false;
			$scope.workDairySearchReq = {
				"workDairyId" : null,
				"code" : null,
				"workDairyDate" : null,
				"projectLabelKeyTO" : {
					"projId" : null,
					"parentName" : null,
					"projName" : null

				},
				"crewLabelKeyTO" : {
					"id" : null,
					"code" : null,
					"name" : null

				},
				"weatherLabelKeyTO" : {
					"id" : null,
					"code" : null,
					"name" : null

				},
				"shiftLabelKeyTO" : {
					"id" : null,
					"code" : null,
					"name" : null

				},
				"costLabelKeyTO" : {
					"id" : null,
					"code" : null,
					"name" : null
				},
				"userLabelKeyTO" : {
					"id" : null,
					"code" : null,
					"name" : null

				},
				"approverTo" : {
					"id" : null,
					"code" : null,
					"name" : null

				},
				"workDairyDate" : null,
				"empMaxHrs" : null,
				"plantMaxHrs" : null,
				"contractType" : 'Head-Company',
				"purId" : null,
				"quantity" : null,
				"contractNo" : null,
				"apprStatus" : null,
				"type":'External',
				"notificationMsg": 'Request for Additional Time'
			};
			$scope.contractNo = null;
			$scope.workDairySearchReq.workDairyId = null;
			$scope.getUserProjects = function(projectLabelKeyTO) {
				var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
				userProjectSelection.then(function(data) {
					projectLabelKeyTO.projId = data.searchProject.projId;
					projectLabelKeyTO.parentName = data.searchProject.parentName;
					projectLabelKeyTO.projName = data.searchProject.projName;
					$scope.workDairySearchReq.contractNo = null;
					$scope.workDairySearchReq.contractType = 'Head-Company';
					$scope.getProjSettingsForWorkDairy(projectLabelKeyTO.projId);
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			},

			$scope.getProjSettingsForWorkDairy = function(projId) {
				if (projId == undefined || projId == null) {
					GenericAlertService.alertMessage("Please select project to get contract number", 'Warning');
					return;
				}
				var req = {
					"status" : 1,
					"projId" : projId

				};
				var resultData = WorkDiaryService.getProjSettingsForWorkDairy(req);
				resultData.then(function(data) {
					$scope.workDairySearchReq.empMaxHrs = data.labelKeyTO.id;
					$scope.workDairySearchReq.plantMaxHrs = 24;
					$scope.workDairySearchReq.contractNo = data.labelKeyTO.code;
					$scope.contractNo = data.labelKeyTO.code;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while gettting project contract number", 'Error');
				});
			}, $scope.selectContractType = function() {
				if ($scope.workDairySearchReq.contractType == 'Head-Company') {
					$scope.workDairySearchReq.contractNo = $scope.contractNo;
				} else {
					$scope.workDairySearchReq.contractNo = null;
				}

			}, $scope.selectSubContract = function(projId) {
				if (projId == undefined || projId == null) {
					GenericAlertService.alertMessage("Please select project  to get sub-contracts", 'Warning');
					return;
				}
				var req = {
					"status" : 1,
					"projId" : projId,
					"procureType" : 'SOW'
				};
				RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function(data) {
					$scope.workDairySearchReq.purId = data.purchaseOrderTO.id;
					$scope.workDairySearchReq.contractNo = data.purchaseOrderTO.code;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
				});

			}, $scope.getCrewList = function(projId, crewLabelKeyTO) {
				if (projId == null || $scope.workDairySearchReq.workDairyDate == null) {
					GenericAlertService.alertMessage("Please select project and work dairy date to get crews", 'Warning');
					return;
				}
				var crewReq = {
					"status" : 1,
					"projId" : projId

				};
				var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
				crewSerivcePopup.then(function(data) {
					crewLabelKeyTO.id = data.projCrewTO.id;
					crewLabelKeyTO.code = data.projCrewTO.code;
					$scope.getWorkDairy();
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
				});
			}, $scope.getWorkDairy = function() {
				$scope.disableButton = false;
				if ($scope.workDairySearchReq.workDairyDate == null) {
					GenericAlertService.alertMessage("Please select Workdairy date", 'Warning');
				}
				var workDairyReq = {
					"status" : 1,
					'workDairyDate' : $scope.workDairySearchReq.workDairyDate,
					"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
					"crewId" : $scope.workDairySearchReq.crewLabelKeyTO.id,
					"apprStatus" : "SubmittedForClientApproval" 
				};
				WorkDiaryService.getWorkDairyForClientApproval(workDairyReq).then(function(data) {
					$scope.workDairySearchReq.workDairyId = data.workDairyTO.id;
					$scope.workDairySearchReq.code = data.workDairyTO.code;
					$scope.workDairySearchReq.clientApprUserId = data.workDairyTO.clientApprUserId;
					$scope.workDairySearchReq.clientApproval = data.workDairyTO.clientApproval;
					$scope.workDairySearchReq.internalApprUserId = data.workDairyTO.internalApprUserId;
					if (data.workDairyTO.contractType != undefined && data.workDairyTO.contractType != null) {
						$scope.workDairySearchReq.contractType = data.workDairyTO.contractType;
						$scope.workDairySearchReq.contractNo = data.workDairyTO.contractNo;
						$scope.workDairySearchReq.purId = data.workDairyTO.purId;
					}
					$scope.workDairySearchReq.weatherLabelKeyTO.id = data.workDairyTO.weatherId;
					$scope.workDairySearchReq.shiftLabelKeyTO.id = data.workDairyTO.shiftId;
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
					$scope.getMaterialMaps();
					if ($scope.workDairySearchReq.apprStatus != undefined && $scope.workDairySearchReq.apprStatus != null && $scope.workDairySearchReq.apprStatus == 'Client Approved') {
						$scope.disableButton = true;
					}
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting work dairy Details", 'Error');
				});
			}, $scope.getWorkDairyDetails = function(projId) {
				$scope.activeFlag = true;
				$rootScope.projId = projId;
				var workDairyGetReq = {
					'workDairyId' : $scope.workDairySearchReq.workDairyId,
					"status" : 1,
					"projId" : projId,
					"apprStatus": 'External Approval'					
				};
				// if ($scope.workDairySearchReq == undefined || projId <= 0 || $scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null
				// 		|| $scope.workDairySearchReq.workDairyDate == null) {
				// 	GenericAlertService.alertMessage("Please select project,crewList and Date", 'Warning');
				// 	return;
				// }
				// if (projId <= 0) {
				// 	GenericAlertService.alertMessage("Please select project", 'Warning');
				// 	return;
				// }
				// if ($scope.workDairySearchReq.workDairyDate == null) {
				// 	GenericAlertService.alertMessage("Please select date", 'Warning');
				// 	return;
				// }
				// if ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) {
				// 	GenericAlertService.alertMessage("Please select crew", 'Warning');
				// 	return;
				// }
				
				if (projId <= 0 && $scope.workDairySearchReq.workDairyDate == null && ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) ){
					GenericAlertService.alertMessage("Please select Project, Date, Crew", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.workDairyDate == null && ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) ){
					GenericAlertService.alertMessage("Please select Date, Crew", 'Warning');
					return;
				}
				if ($scope.workDairySearchReq.crewLabelKeyTO == null || $scope.workDairySearchReq.crewLabelKeyTO.id == null) {
					GenericAlertService.alertMessage("Please select Crew", 'Warning');
					return;
				}
				if (workDairyGetReq.workDairyId == null) {
					GenericAlertService.alertMessage("Work Diary Details does not exist for given search criteria", 'Warning');
					return;
				}
				WorkDiaryService.getWorkDairyDetails(workDairyGetReq).then(function(data) {
					$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
					$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
					$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
					$scope.workDairyMaterialDtlTOs = [];
					$scope.timeFlag = data.timeFlag;
					angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
						$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting workDairy Details", 'Error');

				});
			},
			
			$scope.workdairyClientApproval = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to Approve", 'Warning');
					return;
				}
				var clientapprovepopup = WorkDairyClientApprovalFactory.getWorkDairyApproverDetails($scope.workDairySearchReq, $scope.workDairyEmpDtlTOs, $scope.workDairyPlantDtlTOs,
						$scope.workDairyMaterialDtlTOs, $scope.workDairyProgressDtlTOs);
				clientapprovepopup.then(function(data) {

					$scope.workDairySearchReq.apprStatus = data.workDairyTO.apprStatus;
					if ($scope.workDairySearchReq.apprStatus != undefined && $scope.workDairySearchReq.apprStatus != null && $scope.workDairySearchReq.apprStatus == 'Client Approved') {
						$scope.disableButton = true;
					}
					$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
					$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
					$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
					$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
					$scope.workDairyMaterialDtlTOs = [];
					angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
						$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
					});
					$scope.getWorkDairyDetails(projId);
					
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while approving WorkDairy", 'Error');
				});
			}, $scope.getWorkingShiftId = function(projId, shiftLabelKeyTO) {
				if (projId == undefined || projId == null) {
					GenericAlertService.alertMessage("Please select project to get WorkingShift", 'Warning');
					return;
				}
				var workshiftPopup = [];
				workshiftPopup = CreateWorkShiftPopupFactory.workShiftDetailsList(projId);
				workshiftPopup.then(function(data) {
					shiftLabelKeyTO.id = data.projWorkShiftTO.id;
					shiftLabelKeyTO.code = data.projWorkShiftTO.code;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting Working Shift Details", 'Error');
				});
			},

			$scope.getWeatherClassId = function(projId, weatherLabelKeyTO) {
				if (projId == undefined || projId == null) {
					GenericAlertService.alertMessage("Please select project to get Weather", 'Warning');
					return;
				}
				var weatherPopup = [];
				weatherPopup = CreateWeatherPopupFactory.weatherDetailsList(projId);
				weatherPopup.then(function(data) {
					weatherLabelKeyTO.id = data.weatherTO.id;
					weatherLabelKeyTO.code = data.weatherTO.code;
					weatherLabelKeyTO.name = data.weatherTO.name;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting Weather Details", 'Error');
				});
			}, $scope.getModuleUserDetails = function(approverTo) {

				var getReq = {
					"moduleCode" : "CREATTIMESHET",
					"actionCode" : "APPROVE",
					"permission" : "ASBUILTRCRD_TIMSHET_CREATTIMESHET_APPROVE"

				};
				var selectedUser = ModuleUserFactory.getUsersByModulePermission(getReq);

				selectedUser.then(function(data) {
					approverTo.id = data.approverTo.id;
					approverTo.name = data.approverTo.name;
				});
			}, $scope.getWageFactor = function(workDetails) {
				var getReq = {
					"status" : 1
				};
				var wagePopup = [];
				wagePopup = WageFactory.wageFactorDetailsList(getReq);
				wagePopup.then(function(data) {
					workDetails.wageId = data.employeeWageRateTO.wageRateId;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting WageFactor Details", 'Error');
				});
			}

			$scope.approveWorkDiaryTabs = [ {
				title : 'Progress',
				url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalprogress.html',
				clientapprovalSeleniumLocator : 'AsbuiltRecords_WorkDiary_ClientApproval_Progress'
			}, {
				title : 'Manpower Utilisation',
				url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalmanpower.html',
				clientapprovalSeleniumLocator : 'AsbuiltRecords_WorkDiary_ClientApproval_PlantUtilization'
		
			}, {
				title : 'Plant Utilisation',
				url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalplants.html',
				clientapprovalSeleniumLocator : 'AsbuiltRecords_WorkDiary_ClientApproval_PlantUtilization'
		
			}, {
				title : 'Material and Store Consumption',
				url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalmaterials.html',
				clientapprovalSeleniumLocator : 'AsbuiltRecords_WorkDiary_ClientApproval_MaterialandStoreConsumption'
		
			} ];

			$scope.currentTab = 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalprogress.html';
			$scope.onClickTab = function(tab) {
				$scope.currentTab = tab.url;
			}, $scope.isActiveTab = function(tabUrl) {
				return tabUrl == $scope.currentTab;
			}
			/* ==========Manpower============== */
			$scope.manpowerRowSelect = function(man) {
				if (man.select) {
					editMans.push(man);
				} else {
					editMans.pop(man);
				}
			}
			$scope.addEmpRegDetails = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to Add Employees", 'Warning');
					return;
				}
				var popup = WorkDairyEmpFactory.getEmpRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList);
				popup.then(function(data) {
					angular.forEach(data.workDairyEmpDtlTOs, function(value, key) {
						$scope.workDairyEmpDtlTOs.push(value);
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
				});
			}, $scope.deleteEmpRecords = function() {
				if (editMans.length <= 0) {
					GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
					return;
				}
				if (editMans.length < $scope.workDairyEmpDtlTOs.length) {
					angular.forEach(editMans, function(value, key) {
						$scope.workDairyEmpDtlTOs.splice($scope.workDairyEmpDtlTOs.indexOf(value), 1);

					});

					GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
				} else {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
			}, $scope.calculateUsedTotal = function(workDetails) {
				workDetails.usedTotal = 0;
				var usedTime = 0;
				var usedTimeHours = 0;
				var usedTimeMinutes = 0;
				var totHours = 0;
				var totMinutes = 0;
				angular.forEach(workDetails.workDairyEmpCostDtlTOs, function(value, key) {
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
					workDetails.usedTotal = totHours + totMinutes;
					//workDetails.usedTotal = parseFloat(workDetails.usedTotal) + parseFloat(value.usedTime);
				});
				return workDetails.usedTotal;
			}
			$scope.calculateIdleTotal = function(workDetails) {
				workDetails.idleTotal = 0;
				var idleTime = 0;
				var idleTimeHours = 0;
				var idleTimeMinutes = 0;
				var totHours = 0;
				var totMinutes = 0;
				angular.forEach(workDetails.workDairyEmpCostDtlTOs, function(value, key) {
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
					workDetails.idleTotal = totHours + totMinutes;
					//workDetails.idleTotal = parseFloat(workDetails.idleTotal) + parseFloat(value.idleTime);
				});
				return workDetails.idleTotal;
			},
			$scope.totalCount = function(usedTotal, idleTotal) {
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
			
			$scope.saveWorkDairyEmpDetails = function(workDairySearchReq) {
				var errorFlag = false;
				angular.forEach($scope.workDairyEmpDtlTOs, function(empDtlTO, key) {
					angular.forEach(empDtlTO.workDairyEmpWageTOs, function(wageTO, key) {
						angular.forEach(wageTO.workDairyEmpCostDtlTOs, function(costTO, key) {
							if ($scope.validateHrs(costTO)) {
								errorFlag = true;
							}
						});
					});

				});
				if (errorFlag) {
					GenericAlertService.alertMessage('WorkDairy  entered time  cannot be more than submiited Used or Idle Time', "Warning");
					return;
				}
				if (workDairySearchReq.projectLabelKeyTO.projId <= 0) {
					GenericAlertService.alertMessage("Please select project ", 'Warning');
					return;
				}
				var saveEmpReq = {
					"status" : 1,
					"workDairyEmpDtlTOs" : $scope.workDairyEmpDtlTOs,
					"workDairyTO" : {
						"status" : 1,
						"id" : $scope.workDairySearchReq.workDairyId,
						"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"crewId" : $scope.workDairySearchReq.crewLabelKeyTO.id,
						"clientApproval" : $scope.workDairySearchReq.clientApproval,
						"apprStatus" : $scope.workDairySearchReq.apprStatus,
						"empMaxHrs" : $scope.workDairySearchReq.empMaxHrs
					}
				};
				blockUI.start();
				WorkDiaryService.saveWorkDairyEmpDetails(saveEmpReq).then(function(data) {
					blockUI.stop();
					GenericAlertService.alertMessage('Employees details are saved successfully', "Info");
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage('Employees Details are failed to save', "Error");
				});
			}
			/* ==========plant============== */

			$scope.plantRowSelect = function(plant) {
				if (plant.select) {
					editPlants.push(plant);
				} else {
					editPlants.pop(plant);
				}
			}
			$scope.addPlantRegDetails = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to Add Plants", 'Warning');
					return;
				}
				var resultData = WorkDairyPlantFactory.getPlantRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList);
				resultData.then(function(data) {
					angular.forEach(data.workDairyPlantDtlTOs, function(value, key) {
						$scope.workDairyPlantDtlTOs.push(value);
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching Plants", 'Error');
				});
			}, $scope.deletePlantRecords = function() {
				if (editPlants.length <= 0) {
					GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
					return;
				}
				if (editPlants.length < $scope.workDairyPlantDtlTOs.length) {
					angular.forEach(editPlants, function(value, key) {
						$scope.workDairyPlantDtlTOs.splice($scope.workDairyPlantDtlTOs.indexOf(value), 1);

					});
				} else {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
			}, $scope.saveWorkDairyPlantDetails = function() {
				var errorFlag = false;
				angular.forEach($scope.workDairyPlantDtlTOs, function(plantDtlTO, key) {
					angular.forEach(plantDtlTO.workDairyPlantStatusTOs, function(wageTO, key) {
						angular.forEach(wageTO.workDairyPlantCostDtlTOs, function(costTO, key) {
							if ($scope.validateHrs(costTO)) {
								errorFlag = true;
							}
						});
					});

				});
				if (errorFlag) {
					GenericAlertService.alertMessage('WorkDairy  entered time  cannot be more than submiited Used or Idle Time', "Warning");
					return;
				}
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId <= 0) {
					GenericAlertService.alertMessage("Please select project ", 'Warning');
					return;
				}
				var savePlantReq = {
					"status" : 1,
					"workDairyPlantDtlTOs" : $scope.workDairyPlantDtlTOs,
					"workDairyTO" : {
						"status" : 1,
						"id" : $scope.workDairySearchReq.workDairyId,
						"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"crewId" : $scope.workDairySearchReq.crewLabelKeyTO.id,
						"clientApproval" : $scope.workDairySearchReq.clientApproval,
						"apprStatus" : $scope.workDairySearchReq.apprStatus,
						"plantMaxHrs" : $scope.workDairySearchReq.plantMaxHrs
					}
				};
				blockUI.start();
				WorkDiaryService.saveWorkDairyPlantDetails(savePlantReq).then(function(data) {
					blockUI.stop();
					GenericAlertService.alertMessage('Plant Details are saved successfully', "Info");
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage('Plant Details are failed to save', "Error");
				});
			}, $scope.calculatePlantUsedTotal = function(plantStatusTo) {
				plantStatusTo.usedTotal = 0;
				var usedTime = 0;
				var usedTimeHours = 0;
				var usedTimeMinutes = 0;
				var totHours = 0;
				var totMinutes = 0;
				angular.forEach(plantStatusTo.workDairyPlantCostDtlTOs, function(value, key) {
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
					plantStatusTo.usedTotal = totHours + totMinutes;
				});
				return plantStatusTo.usedTotal;
			}, $scope.calculatePlantIdleTotal = function(plantStatusTo) {
				plantStatusTo.idleTotal = 0;
				var idleTime = 0;
				var idleTimeHours = 0;
				var idleTimeMinutes = 0;
				var totHours = 0;
				var totMinutes = 0;
				angular.forEach(plantStatusTo.workDairyPlantCostDtlTOs, function(value, key) {
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
					plantStatusTo.idleTotal = totHours + totMinutes;
				});
				return plantStatusTo.idleTotal;
			}

			/* ===========material========== */

			$scope.materialStoreRowSelect = function(supplier) {
				if (supplier.select) {
					editMatestores.push(supplier);
				} else {
					editMatestores.pop(supplier);
				}
			}

			$scope.dockettype = function() {

				var materialPopup = WorkDairyDocketFactory.docTypeDetails();
				materialPopup.then(function(data) {
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching dockettype details", 'Error');
				});
			}

			var schItemData = {
				"id" : null,
				"selected" : false,
				"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
				"sourceType" : 'Suppler Delivery',
				"docketType" : 'Delivery Docket',
				"deliveryDocketId" : null,
				"docketNum" : '',
				"docketDate" : '',
				"purchaseLabelKeyTO" : {
					id : null,
					code : null,
					name : null
				},
				"purchaseSchLabelKeyTO" : {
					id : null,
					code : null,
					name : null,
					displayNamesMap : {}
				},
				"stockLabelKeyTO" : {
					id : null,
					code : null,
					name : null,
					type : null
				},
				"receivedQty" : '',
				"defectComments" : '',
				"consumptionQty" : '',
				"comments" : '',
				"workDairyMaterialStatusTOs" : []
			};
			$scope.addSupplierDockets = function() {
				var workDairyMaterialCostTOs = [];
				angular.forEach($scope.workDairyCostCodeList, function(value, key) {
					workDairyMaterialCostTOs.push(angular.copy({
						"costId" : value.costId,
						"quantity" : null,
						"workDairyId" : $scope.workDairySearchReq.workDairyId,
						"status" : 1
					}))
				});
				schItemData.workDairyMaterialStatusTOs.push({
					"apprStatus" : null,
					"materialDtlId" : null,
					"status" : 1,
					"comments" : null,
					"apprStatus" : null,
					"workDairyMaterialCostTOs" : angular.copy(workDairyMaterialCostTOs)
				});
				$scope.workDairyMaterialDtlTOs.push(angular.copy(schItemData));
			}, $scope.getPurchaseOrdersForMaterial = function(materialObj) {
				var req = {
					"status" : 1,
					"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
					"procureType" : 'M'
				};
				RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function(data) {
					materialObj.purchaseLabelKeyTO.id = data.purchaseOrderTO.id;
					materialObj.purchaseLabelKeyTO.code = data.purchaseOrderTO.code;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
				});

			}, $scope.getScheduleItems = function(materialObj) {
				var req = {
					"status" : 1,
					"purId" : materialObj.purchaseLabelKeyTO.id,
					"procureType" : 'M'
				};
				RegisterPurchaseOrderItemsFactory.getPOItemDetails(req).then(function(data) {
					materialObj.purchaseSchLabelKeyTO.id = data.selectedRecord.id;
					materialObj.purchaseSchLabelKeyTO.code = data.selectedRecord.code;
					materialObj.purchaseSchLabelKeyTO.displayNamesMap = data.selectedRecord.displayNamesMap;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
				});

			}, $scope.getMaterialMaps = function() {
				var materialMapsReq = {
					"status" : 1,
					"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId
				};
				MaterialRegisterService.getProjMaterialSchItems(materialMapsReq).then(function(data) {
					$scope.companyMap = data.registerOnLoadTO.companyMap;
					$scope.classificationMap = data.registerOnLoadTO.classificationMap;
					$scope.userProjectMap = data.userProjMap;
					angular.forEach(data.materialProjDtlTOs, function(value, key) {
						$scope.materialProjDtlMap[value.id] = value;
					});
				});
			}
			$scope.getMaterialDeliveryDockets = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to add materials", 'Warning');
					return;
				}
				var materialPopup = MaterialRegisterService.getMaterialProjDocketsByDoctype();
				materialPopup.then(function(data) {
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching manpower details", 'Error');
				});
			}, $scope.getMaterialProjDeliveryDockets = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to add materials", 'Warning');
					return;
				}
				var resultData = WorkDairyMaterialProjDocketFactory.getMaterialProjDockets($scope.workDairySearchReq, $scope.workDairyCostCodeList, $scope.materialProjDtlMap, $scope.companyMap,
						$scope.classificationMap, $scope.userProjectMap);
				resultData.then(function(data) {
					angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
						$scope.workDairyMaterialDtlTOs.push(value);
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching Material details", 'Error');
				});
			}, $scope.saveWorkDairyMaterialDetails = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId <= 0) {
					GenericAlertService.alertMessage("Please select project ", 'Warning');
					return;
				}
				var saveMaterialreq = [];
				var maretailSchReq = {
					"purchaseSchLabelKeyTO" : {
						id : null,
						code : null,
						name : null,
						displayNamesMap : {
							"purId" : null,
							"purcode" : null
						}
					},
					"materialPODeliveryDocketTOs" : null

				};
				angular.forEach($scope.workDairyMaterialDtlTOs, function(value, key) {
					if (value.id <= 0 && value.deliveryDocketId <= 0 && value.docketType == 'Delivery Docket') {
						var purchaseSchLabelKeyTO = {};
						purchaseSchLabelKeyTO = value.purchaseSchLabelKeyTO;
						purchaseSchLabelKeyTO.displayNamesMap.purId = value.purchaseLabelKeyTO.id;
						purchaseSchLabelKeyTO.displayNamesMap.purCode = value.purchaseLabelKeyTO.code;
						var maretailSchReq = {
								"purchaseSchLabelKeyTO" : purchaseSchLabelKeyTO,
								"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
								"issueQty" : value.receivedQty,
								"materialPODeliveryDocketTOs" : [ angular.copy({
									"status" : 1,
									"sourceType" : value.sourceType,
									"id" : value.deliveryDocketId,
									"docketNumber" : value.docketNum,
									"deliveryPlace" : value.deliveryPlace,
									"regMaterialId" : value.scheduleItemId,
									"docketDate" : value.docketDate,
									"receivedQty" : value.receivedQty,
									"defectComments" : value.defectComments,
									"comments" : value.comments,
									"receivedBy" : value.receivedBy
								}) ]
							};
					}
					saveMaterialreq.push({
						"id" : value.id,
						"status" : 1,
						"workDairyId" : $scope.workDairySearchReq.workDairyId,
						"projDocketSchId" : value.projDocketSchId,
						"openingStock" : value.openingStock,
						"closingStock" : value.closingStock,
						"consumpitonUpdated" : value.consumpitonUpdated,
						"sourceType" : value.sourceType,
						"deliveryDocketId" : value.deliveryDocketId,
						"scheduleItemId" : value.scheduleItemId,
						"docketType" : value.docketType,
						"projDocketId" : value.projDocketId,
						"docketNum" : value.docketNum,
						"docketDate" : value.docketDate,
						"deliveryPlace" : value.deliveryPlace,
						"defectComments" : value.defectComments,
						"apprStatus" : null,
						"receivedQty" : value.receivedQty,
						"apprComments" : value.comments,
						"workDairyMaterialStatusTOs" : value.workDairyMaterialStatusTOs,
						"materialProjDtlTO" : maretailSchReq
					});
				});
				var req = {
					"status" : 1,
					"workDairyMaterialDtlTOs" : saveMaterialreq,
					"workDairyTO" : {
						"status" : 1,
						"id" : $scope.workDairySearchReq.workDairyId,
						"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"crewId" : $scope.workDairySearchReq.crewLabelKeyTO.id,
						"clientApproval" : $scope.workDairySearchReq.clientApproval,
						"apprStatus" : $scope.workDairySearchReq.apprStatus,
						"newRequired" : false
					}
				};
				blockUI.start();
				WorkDiaryService.saveWorkDairyMaterialDetails(req).then(function(data) {
					blockUI.stop();
					$scope.workDairyMaterialDtlTOs = [];
					angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
						$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
					});
					GenericAlertService.alertMessage('Material Details are saved successfully', "Info");
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage('Material Details are failed to save', "Error");
				});
			}, $scope.populateMaterialObj = function(materialTOs, value) {
				for (let i = 0; i < value.scheduleItemId.length; i++) {
					var dataObj = {
						"id" : value.id,
						"selected" : false,
						"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"openingStock" : value.openingStock,
						"closingStock" : value.closingStock,
						"sourceType" : value.sourceType,
						"docketType" : value.docketType,
						"deliveryDocketId" : value.deliveryDocketId,
						"projDocketSchId" : value.projDocketSchId,
						"docketNum" : value.docketNum,
						"docketDate" : value.docketDate,
						"deliveryPlace" : $scope.deliveryPlace,
						"scheduleItemId" : value.scheduleItemId,
						"consumpitonUpdated" : value.consumpitonUpdated,
						"purchaseLabelKeyTO" : {
							id : null,
							code : value.materialProjDtlTO[i].purchaseSchLabelKeyTO.displayNamesMap['purCode'],
							name : null
						},
						"purchaseSchLabelKeyTO" : {
							id : value.scheduleItemId,
							code : value.materialProjDtlTO[i].purchaseSchLabelKeyTO.code,
							name : value.materialProjDtlTO[i].purchaseSchLabelKeyTO.name,
							displayNamesMap : value.materialProjDtlTO[i].purchaseSchLabelKeyTO.displayNamesMap
						},
						"stockLabelKeyTO" : {
							id : null,
							code : null,
							name : null,
							type : null
						},
						"receivedQty" : value.receivedQty,
						"defectComments" : value.defectComments,
						"consumptionQty" : '',
						"comments" : '',
						"workDairyMaterialStatusTOs" : value.workDairyMaterialStatusTOs
					};
					materialTOs.push(dataObj);
				}
				}, $scope.deleteMatestoreRecords = function() {
				if (editMatestores.length <= 0) {
					GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

				} else

				if (editMatestores.length < $scope.supplierDockets.length) {
					angular.forEach(editMatestores, function(value, key) {
						$scope.supplierDockets.splice($scope.supplierDockets.indexOf(value), 1);

					});
					editMatestores = [];
				} else if (editMatestores.length > 1) {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				} else if (editMatestores.length <= 1) {
					editMatestores = [];
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
			}, $scope.deleteMatestoreRecords = function() {
				if (editMatestores.length <= 0) {
					GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

				} else

				if (editMatestores.length < $scope.supplierList.length) {
					angular.forEach(editMatestores, function(value, key) {
						$scope.supplierList.splice($scope.supplierList.indexOf(value), 1);

					});
					editMatestores = [];
					GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
				} else if (editMatestores.length > 1) {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				} else if (editMatestores.length <= 1) {
					editMatestores = [];
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
			}

			/* ============progress========== */

			$scope.calculateProgressUsedTotal = function(workDetails) {
				workDetails.usedTotal = 0;
				angular.forEach(workDetails.workDairyEmpCostDtlTOs, function(value, key) {
					if (value.usedTime == undefined) {
						value.usedTime = 0;
					}
					workDetails.usedTotal = parseFloat(workDetails.usedTotal) + parseFloat(value.usedTime);
				});
				return workDetails.usedTotal;
			}
			$scope.calculateProgressIdleTotal = function(workDetails) {
				workDetails.idleTotal = 0;
				angular.forEach(workDetails.workDairyEmpCostDtlTOs, function(value, key) {
					if (value.idleTime == undefined) {
						value.idleTime = 0;
					}
					workDetails.idleTotal = parseFloat(workDetails.idleTotal) + parseFloat(value.idleTime);
				});
				return workDetails.idleTotal;
			}

			$scope.progressRowSelect = function(progress) {
				if (progress.select) {
					editProgress.push(progress);
				} else {
					editProgress.pop(progress);
				}
			}

			$scope.addProgressDetails = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to Add Progress Details", 'Warning');
					return;
				}

				var workProgressMap = [];
				angular.forEach($scope.workDairyProgressDtlTOs, function(value, key) {
					workProgressMap[value.sowId] = true;
				});
				var progressPopup = WorkDairyAddProgressFactory.getSOWDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, workProgressMap);
				progressPopup.then(function(data) {

					angular.forEach(data.workDairyProgressDtlTOs, function(value, key) {
						$scope.workDairyProgressDtlTOs.push(value);
						angular.forEach($scope.workDairyProgressDtlTOs, function(value, key) {
							$scope.workDairySearchReq.quantity = value.quantity;
						})
					});

					$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;

				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
				});
			}, $scope.addMoreSowItems = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to Add Progress Details", 'Warning');
					return;
				}
				var workProgressMap = [];
				angular.forEach($scope.workDairyProgressDtlTOs, function(value, key) {
					workProgressMap[value.sowId] = true;
				});
				var progressPopup = WorkDairyProgressFactory.getSOWDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList, workProgressMap);
				progressPopup.then(function(data) {
					$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
					$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
					$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
					$scope.workDairyMaterialDtlTOs = [];
					angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
						$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
					});
					$scope.workDairyCostCodeList = data.workDairyProgressDtlTOs;
					$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;

				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
				});
			}, $scope.addProgressPOScheduleItems = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
					GenericAlertService.alertMessage("Please select project to Add Progress Details", 'Warning');
					return;
				}
				var workProgressMap = [];
				angular.forEach($scope.workDairyProgressDtlTOs, function(value, key) {
					workProgressMap[value.sowId] = true;
				});
				var progressPopup = POScheduleItemsFactory.getPOScheduleItems($scope.workDairySearchReq, $scope.workDairyCostCodeList, workProgressMap);
				progressPopup.then(function(data) {
					angular.forEach(data.projSOWItemTOs, function(value, key) {
						$scope.workDairyProgressDtlTOs.push(value);
					});
					$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching progress details", 'Error');
				});
			}, $scope.saveWorkDairyProgressDetails = function() {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId <= 0) {
					GenericAlertService.alertMessage("Please select project ", 'Warning');
					return;
				}
				var req = {
					"status" : 1,
					"workDairyProgressDtlTOs" : $scope.workDairyProgressDtlTOs,
					"workDairyTO" : {
						"status" : 1,
						"id" : $scope.workDairySearchReq.workDairyId,
						"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
						"crewId" : $scope.workDairySearchReq.crewLabelKeyTO.id,
						"clientApproval" : $scope.workDairySearchReq.clientApproval,
						"apprStatus" : $scope.workDairySearchReq.apprStatus,
					}
				};
				blockUI.start();
				WorkDiaryService.saveWorkDairyProgressDetails(req).then(function(data) {
					blockUI.stop();
					GenericAlertService.alertMessage('Progress Details are saved successfully', "Info");
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage('Progress Details are failed to save', "Error");
				});
			}, $scope.deleteProgressRecords = function() {
				if (editProgress.length <= 0) {
					GenericAlertService.alertMessage('Please Select Atleast One Row to Delete', "Warning");
					return;
				}
				if (editProgress.length < $scope.workDairyProgressDtlTOs.length) {
					angular.forEach(editProgress, function(value, key) {
						$scope.workDairyProgressDtlTOs.splice($scope.workDairyProgressDtlTOs.indexOf(value), 1);

					});
				} else {
					GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
				}
			},

			$scope.resetCreateWorkDairy = function() {
				$scope.empWageFactorMap = [];
				$scope.empRegMap = [];
				$scope.plantRegMap = [];
				$scope.materialRegMap = [];
				$scope.timeFlag=false;
				$scope.workDairyEmpDtlTOs = [];
				$scope.workDairyPlantDtlTOs = [];
				$scope.workDairyMaterialDtlTOs = [];
				$scope.workDairyProgressDtlTOs = [];
				$scope.workDairyCostCodeList = [];
				$scope.workDairyCostCodeMap = [];
				$scope.workDairySearchReq = {
					"projectLabelKeyTO" : {
						"projId" : null,
						"parentName" : null,
						"projName" : null

					},
					"crewLabelKeyTO" : {
						"id" : null,
						"code" : null,
						"name" : null

					},
					"weatherLabelKeyTO" : {
						"id" : null,
						"code" : null,
						"name" : null

					},
					"shiftLabelKeyTO" : {
						"id" : null,
						"code" : null,
						"name" : null

					},
					"costLabelKeyTO" : {
						"id" : null,
						"code" : null,
						"name" : null
					},
					"approverTo" : {
						"id" : null,
						"code" : null,
						"name" : null

					},
					"workDairyDate" : null
				};
			},
			
			$scope.getWorkDairyNotifications = function(projId) {
				if ($scope.workDairySearchReq.projectLabelKeyTO.projId == undefined || $scope.workDairySearchReq.projectLabelKeyTO.projId <= 0
						|| $scope.workDairySearchReq.crewLabelKeyTO.id == undefined || $scope.workDairySearchReq.crewLabelKeyTO.id <= 0) {
					GenericAlertService.alertMessage("Please select Project and Crew to get RequesAdditionalTime details", 'Warning');
					return;
				}
				var popup = RequestForAdditionalTimeFactory.getAdditionalTimeDetails($scope.workDairySearchReq);
				popup.then(function(data) {
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while getting  RequesAdditionalTime   details", 'Error');
				})
			},

			$scope.addCostCodeItemsToWorkDairy = function(projId, crewId) {
				if (projId !== null && crewId != null) {
					var costCodepopup = WorkDairyCostCodeFactory.getCostCodeDetails(projId, crewId, $scope.workDairyCostCodeList, $scope.workDairySearchReq.workDairyId);
					costCodepopup.then(function(data) {
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
					})
				} else {
					GenericAlertService.alertMessage("Please select project  and crew To Add CostCodes", 'Warning');
				}
			}, $scope.checkDecimal = function(timeObj, indexValue) {
				if (indexValue == 1 && timeObj.usedTime > timeObj.oldUsedTime) {
					GenericAlertService.alertMessage('Work Diary Used Time cannot be more than submitted Used Time', "Warning");
					return;
				} else if (indexValue == 2 && timeObj.idleTime > timeObj.oldIdleTime) {
					GenericAlertService.alertMessage('Work Diary Idle Time cannot be more than submitted Idle Time', "Warning");
					return;
				}
				return false;
			}, $scope.validateHrs = function(timeObj) {
				if (timeObj.usedTime > timeObj.oldUsedTime || timeObj.idleTime > timeObj.oldIdleTime) {
					return true;
				}
				return false;
			}
			$scope.checkProgressQuantity = function(quantity, progress) {

				var inputvalue = progress.value;

				progress.errorFlag = false;

				if (inputvalue > quantity) {
					progress.errorFlag = true;
					GenericAlertService.alertMessage('WorkDairy cannot be booked more tham SOW Item Quantity', "Warning");
					return;
				}
				progress.errorFlag = false;

			}
			$scope.clear1 = function() {
				$scope.workDairySearchReq.workDairyDate = null;
				$scope.workDairySearchReq.crewLabelKeyTO.code = null;
				$scope.workDairySearchReq.code = null;
				$scope.workDairySearchReq.shiftLabelKeyTO.id = null;
				$scope.workDairySearchReq.weatherLabelKeyTO.id = null;
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
					template: 'views/help&tutorials/asbuiltrecordshelp/workdiary/clientworkdiaryapprovalhelp.html',
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
