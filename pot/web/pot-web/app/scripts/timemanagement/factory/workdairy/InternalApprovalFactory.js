'use strict';

app.factory('InternalApprovalFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "GenericAlertService", "RequestForAdditionalTimeFactory", "WorkDiaryService", "ProjectCrewPopupService", "CreateWorkShiftPopupFactory", "CreateWeatherPopupFactory", "MaterialRegisterService", "WorkDairyPlantFactory", "WorkDairyApproveFactory", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, blockUI, GenericAlertService, RequestForAdditionalTimeFactory, WorkDiaryService, ProjectCrewPopupService, CreateWorkShiftPopupFactory, CreateWeatherPopupFactory, MaterialRegisterService, WorkDairyPlantFactory, WorkDairyApproveFactory, PlantRegisterService) {
	var attendancePopup;
	var service = {};
	service.openPopup = function(workDairyTO) {
		var deferred = $q.defer();
		attendancePopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/approveworkdairy/approval.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.workDairyTO = workDairyTO;
				$scope.timeFlag = false;
				$scope.materialProjDtlMap = [];
				$scope.workDairySearchReq = {
					"workDairyId" : workDairyTO.id,
					"code" : workDairyTO.workDiaryCode,
					"workDairyDate" : workDairyTO.workDairyDate,
					"projectLabelKeyTO" : {
						"projId" : workDairyTO.projId,
						"parentName" : workDairyTO.parentName,
						"projName" : workDairyTO.name
					},
					"crewLabelKeyTO" : {
						"id" : workDairyTO.crewId,
						"code" : workDairyTO.crewCode,
						"name" : null
					},
					"weatherLabelKeyTO" : {
						"id" : workDairyTO.weatherId,
						"code" : workDairyTO.weatherCode,
						"name" : null
					},
					"shiftLabelKeyTO" : {
						"id" : workDairyTO.shiftId,
						"code" : workDairyTO.shiftCode,
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
					"approverTo ": {
							"id" : null,
							"code" : null,
							"name" : null
					},
					"workDairyDate" : workDairyTO.workDairyDate,
					"empMaxHrs" : null,
					"plantMaxHrs" : null,
					"contractType" : workDairyTO.contractType,
					"purId" : null,
					"contractNo" : workDairyTO.contractNo,
					"quantity":null,
					'type':'Internal',
					"apprStatus" : workDairyTO.apprStatus,
					"notificationMsg": 'Request for Additional Time',
					'clientApprUserId': workDairyTO.clientApprUserId,
					'clientApproval': workDairyTO.clientApproval,
					'internalApprUserId': workDairyTO.internalApprUserId
				};
				$scope.currentTab = 'views/timemanagement/workdairy/approveworkdairy/workdairyapproverprogress.html';
				$scope.approveWorkDiaryTabs = [ {
					title : 'Progress',
					url : 'views/timemanagement/workdairy/approveworkdairy/workdairyapproverprogress.html'
				}, {
					title : 'Manpower Utilisation',
					url : 'views/timemanagement/workdairy/approveworkdairy/workdairyapprovemanpower.html'
				}, {
					title : 'Plant Utilisation',
					url : 'views/timemanagement/workdairy/approveworkdairy/workdairyapproveplants.html'
				}, {
					title : 'Material and Store Consumption',
					url : 'views/timemanagement/workdairy/approveworkdairy/workdairyapprovematerials.html'
				}];
				$scope.isActiveTab = function(tabUrl) {
					return tabUrl == $scope.currentTab;
				},
				$scope.onClickTab = function(tab) {
					$scope.currentTab = tab.url;
				},
				$scope.populateMaterialObj = function(materialTOs, value) {
					var dataObj = {
						"id" : value.id,
						"selected" : false,
						"projId" : workDairyTO.projId,
						"sourceType" : value.sourceType,
						"docketType" : value.docketType,
						"openingStock" : value.openingStock,
						"closingStock" : value.closingStock,
						"deliveryDocketId" : value.deliveryDocketId,
						"projDocketSchId" : value.projDocketSchId,
						"docketNum" : value.docketNum,
						"docketDate" : value.docketDate,
						"deliveryPlace" : $scope.deliveryPlace,
						"scheduleItemId" : value.scheduleItemId,
						"consumpitonUpdated" : value.consumpitonUpdated,
						"purchaseLabelKeyTO" : {
							id : null,
							code : $scope.materialProjDtlMap[value.scheduleItemId].purchaseSchLabelKeyTO.displayNamesMap['purCode'],
							name : null
						},
						"purchaseSchLabelKeyTO" : {
							id : value.scheduleItemId,
							code : $scope.materialProjDtlMap[value.scheduleItemId].purchaseSchLabelKeyTO.code,
							name : $scope.materialProjDtlMap[value.scheduleItemId].purchaseSchLabelKeyTO.name,
							displayNamesMap : $scope.materialProjDtlMap[value.scheduleItemId].purchaseSchLabelKeyTO.displayNamesMap
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
						"apprComments" : value.apprComments,
						"workDairyMaterialStatusTOs" : value.workDairyMaterialStatusTOs
					};
					materialTOs.push(dataObj);
				},
				$scope.checkDecimal = function(timeObj, indexValue) {
					if (indexValue == 1 && timeObj.usedTime > timeObj.oldUsedTime) {
						GenericAlertService.alertMessage('Work Diary Used Time cannot be more than submitted Used Time', "Warning");
						return;
					} else if (indexValue == 2 && timeObj.idleTime > timeObj.oldIdleTime) {
						GenericAlertService.alertMessage('Work Diary Idle Time cannot be more than submitted Idle Time', "Warning");
						return;
					}
					return false;
				},
				$scope.getWorkDairyNotifications = function (flag) {
					var popup = RequestForAdditionalTimeFactory.getAdditionalTimeDetails($scope.workDairySearchReq);
					popup.then(function (data) {
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while getting  request additional time", 'Error');
					})
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
				$scope.calculatePlantUsedTotal = function(plantStatusTO) {
					plantStatusTO.usedTotal = 0;
					var usedTime = 0;
					var usedTimeHours = 0;
					var usedTimeMinutes = 0;
					var totHours = 0;
					var totMinutes = 0;
					angular.forEach(plantStatusTO.workDairyPlantCostDtlTOs, function(value, key) {
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
					return plantStatusTO.usedTotal;
				},
				$scope.calculateUsedTotal = function(workDetails) {
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
				});
				return workDetails.usedTotal;
			},
				$scope.calculatePlantIdleTotal = function(plantStatusTO) {
					plantStatusTO.idleTotal = 0;
					var idleTime = 0;
					var idleTimeHours = 0;
					var idleTimeMinutes = 0;
					var totHours = 0;
					var totMinutes = 0;
					angular.forEach(plantStatusTO.workDairyPlantCostDtlTOs, function(value, key) {
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
					return plantStatusTO.idleTotal;
				},
				$scope.workdairyApproval = function() {
					var approvePopup = WorkDairyApproveFactory.getWorkDairyApproverDetails($scope.workDairySearchReq, $scope.workDairyEmpDtlTOs, $scope.workDairyPlantDtlTOs,
							$scope.workDairyMaterialDtlTOs, $scope.workDairyProgressDtlTOs);
					approvePopup.then(function(data) {
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while approving WorkDairy", 'Error');
					});
				},
				$scope.saveWorkDairyEmpDetails = function(workDairySearchReq) {
					var errorFlag=false;
					angular.forEach($scope.workDairyEmpDtlTOs, function(empDtlTO, key) {
						angular.forEach(empDtlTO.workDairyEmpWageTOs, function(wageTO, key) {
							angular.forEach(wageTO.workDairyEmpCostDtlTOs, function(costTO, key) {
								if ($scope.validateHrs(costTO)) {
									errorFlag=true;
								}
							});
						});
					});
					if(errorFlag) {
						GenericAlertService.alertMessage('WorkDairy  entered time  cannot be more than submiited Used or Idle Time', "Warning");
						return;
					}
					var saveEmpReq = {
						"status" : 1,
						"workDairyEmpDtlTOs" : $scope.workDairyEmpDtlTOs,
						"workDairyTO" : {
							"status" : 1,
							"id" : workDairyTO.id,
							"projId" : workDairyTO.projId,
							"crewId" : workDairyTO.crewId,
							"clientApproval" : workDairyTO.clientApproval,
							"apprStatus" : workDairyTO.apprStatus,
							"empMaxHrs" : $scope.workDairySearchReq.empMaxHrs,
							"newRequired" : false
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
				},
				$scope.saveWorkDairyPlantDetails = function() {
					var errorFlag=false;
					angular.forEach($scope.workDairyPlantDtlTOs, function(plantDtlTO, key) {
						angular.forEach(plantDtlTO.workDairyPlantStatusTOs, function(wageTO, key) {
							angular.forEach(wageTO.workDairyPlantCostDtlTOs, function(costTO, key) {
								if ($scope.validateHrs(costTO)) {
									errorFlag=true;
								}
							});
						});

					});
					if(errorFlag){
						GenericAlertService.alertMessage('WorkDairy  entered time  cannot be more than submiited Used or Idle Time', "Warning");
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
				},
				$scope.addPlantRegDetails = function() {
					var resultData = WorkDairyPlantFactory.getPlantRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList);
					resultData.then(function(data) {
						angular.forEach(data.workDairyPlantDtlTOs, function(value, key) {
							$scope.workDairyPlantDtlTOs.push(value);
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching Plants", 'Error');
					});
				},
				$scope.calculateProgressTotal = function (progress) {
					progress.total = 0;
					progress.total = progress.total + parseFloat(progress.value || 0);
					return progress.total;
				},
				$scope.validateHrs = function(timeObj) {
					if (timeObj.usedTime > timeObj.oldUsedTime || timeObj.idleTime > timeObj.oldIdleTime) {
						return true;
					}
					return false;
				},
				$scope.checkProgressQuantity=function(quantity,progress){
					var inputvalue = progress.value;
					progress.errorFlag = false;
						if (inputvalue > quantity) {
							progress.errorFlag = true;
							GenericAlertService.alertMessage('WorkDairy cannot be booked more tham SOW Item Quantity', "Warning");
							return;
						}
						progress.errorFlag = false;
				}
				
				WorkDiaryService.getProjSettingsForWorkDairy({"status" : 1, "projId" : workDairyTO.projId}).then(function(data) {
					$scope.workDairySearchReq.empMaxHrs = data.labelKeyTO.id;
					$scope.workDairySearchReq.plantMaxHrs = 24;
					$scope.workDairySearchReq.contractNo = data.labelKeyTO.code;
					$scope.contractNo = data.labelKeyTO.code;
					
					WorkDiaryService.getWorkDairyForInternalApproval({"status" : 1, 'workDairyDate' : workDairyTO.workDairyDate, "projId" : workDairyTO.projId, "crewId": workDairyTO.crewId,  "apprStatus" : "SubmittedForApproval"}).then(function(data) {
						$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
						$scope.workDairyCostCodeMap = data.costCodeMap;
						$scope.empWageFactorMap = data.empWageFactorMap;
						$scope.empRegMap = data.empRegMap;
						$scope.plantRegMap = data.plantRegMap;
						$scope.materialRegMap = data.materialRegMap;
						$scope.sowMap = data.sowMap;
						$scope.weatherMap = data.weatherMap;
						$scope.projShiftMap = data.projShiftMap;
						
						if (workDairyTO.contractType == 'Sub-Contract'){
							PlantRegisterService.getPlantProjectPODetails({"status" : 1, "projId" : workDairyTO.projId, "procureType" : 'SOW'}).then(function(data) {
								$scope.workDairySearchReq.purId = data.purchaseOrderTO.id;
								$scope.workDairySearchReq.contractNo = data.purchaseOrderTO.code;
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
							});
						}
						MaterialRegisterService.getProjMaterialSchItems({"status" : 1, "projId" : workDairyTO.projId}).then(function(data) {
							$scope.companyMap = data.registerOnLoadTO.companyMap;
							$scope.classificationMap = data.registerOnLoadTO.classificationMap;
							$scope.userProjectMap = data.userProjMap;
							angular.forEach(data.materialProjDtlTOs, function(value, key) {
								$scope.materialProjDtlMap[value.id] = value;
							});
							WorkDiaryService.getWorkDairyDetails({"workDairyId" : workDairyTO.id, "status" : 1, "projId" : workDairyTO.projId, "apprStatus": 'Internal Approval',"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id}).then(function(data) {
						//		$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
								$scope.workDairyEmpDtlTOs = $filter('unique')(data.workDairyEmpDtlTOs, 'code');
								$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
								$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
								$scope.workDairyMaterialDtlTOs = [];
								$scope.timeFlag = data.timeFlag;
								angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
									$scope.deliveryPlace = value.deliveryPlace;
									$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
								});
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while getting ApproveworkDairy Details", 'Error');
							});
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting work dairy Details", 'Error');
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while gettting project contract number", 'Error');
				});
			}]
		});
		return deferred.promise;
	};
	return service;
}]);