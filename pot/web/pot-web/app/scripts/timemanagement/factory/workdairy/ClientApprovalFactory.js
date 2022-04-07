'use strict';

app.factory('ClientApprovalFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "GenericAlertService", "WorkDiaryService", "PlantRegisterService", "MaterialRegisterService", "WageFactory", "WorkDairyClientApprovalFactory", "WorkDairyEmpFactory", "WorkDairyPlantFactory", function(ngDialog, $q, $filter, $timeout, blockUI, GenericAlertService, WorkDiaryService, PlantRegisterService, MaterialRegisterService, WageFactory, WorkDairyClientApprovalFactory, WorkDairyEmpFactory, WorkDairyPlantFactory) {
	var attendancePopup;
	var service = {};
	service.openPopup = function(workDairyTO) {
		var deferred = $q.defer();
		attendancePopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/workdairyclientapproval/approval.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.workDairyTO = workDairyTO;
				$scope.materialProjDtlMap = [];
				var editMans = [];
				var editPlants = [];
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
				$scope.currentTab = 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalprogress.html';
				$scope.approveWorkDiaryTabs = [{
					title : 'Progress',
					url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalprogress.html'
				}, {
					title : 'Manpower Utilisation',
					url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalmanpower.html'
				}, {
					title : 'Plant Utilisation',
					url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalplants.html'
				}, {
					title : 'Material and Store Consumption',
					url : 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovalmaterials.html'
				}];
				$scope.isActiveTab = function(tabUrl) {
					return tabUrl == $scope.currentTab;
				},
				$scope.onClickTab = function(tab) {
					$scope.currentTab = tab.url;
				},
				$scope.populateMaterialObj = function(materialTOs, value) {
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
				},
				$scope.calculateProgressTotal = function (progress) {
				progress.total = 0;
				progress.total = progress.total + parseFloat(progress.value || 0);
				return progress.total;
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

				});
				return workDetails.idleTotal;
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
			}
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
			}
				$scope.getWageFactor = function(workDetails) {
					WageFactory.wageFactorDetailsList({"status" : 1}).then(function(data) {
						workDetails.wageId = data.employeeWageRateTO.wageRateId;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting WageFactor Details", 'Error');
					});
				},
				$scope.manpowerRowSelect = function(man) {
					if (man.select) {
						editMans.push(man);
					} else {
						editMans.pop(man);
					}
				}
				$scope.addEmpRegDetails = function() {
					WorkDairyEmpFactory.getEmpRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList).then(function(data) {
						angular.forEach(data.workDairyEmpDtlTOs, function(value, key) {
							$scope.workDairyEmpDtlTOs.push(value);
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching Employee details", 'Error');
					});
				},
				$scope.deleteEmpRecords = function() {
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
				},
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
				},
				$scope.validateHrs = function(timeObj) {
				if (timeObj.usedTime > timeObj.oldUsedTime || timeObj.idleTime > timeObj.oldIdleTime) {
					return true;
				}
				return false;
			    }
				$scope.workdairyClientApproval = function() {
					var clientapprovepopup = WorkDairyClientApprovalFactory.getWorkDairyApproverDetails($scope.workDairySearchReq, $scope.workDairyEmpDtlTOs, $scope.workDairyPlantDtlTOs,
							$scope.workDairyMaterialDtlTOs, $scope.workDairyProgressDtlTOs);
					clientapprovepopup.then(function(data) {
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while approving WorkDairy", 'Error');
					});
				},
				$scope.plantRowSelect = function(plant) {
					if (plant.select) {
						editPlants.push(plant);
					} else {
						editPlants.pop(plant);
					}
				},
				$scope.addPlantRegDetails = function() {
					WorkDairyPlantFactory.getPlantRegDetails($scope.workDairySearchReq, $scope.workDairyCostCodeList).then(function(data) {
						angular.forEach(data.workDairyPlantDtlTOs, function(value, key) {
							$scope.workDairyPlantDtlTOs.push(value);
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching Plants", 'Error');
					});
				},
				$scope.deletePlantRecords = function() {
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
				},
				$scope.saveWorkDairyPlantDetails = function() {
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
				}
				
				WorkDiaryService.getProjSettingsForWorkDairy({"status" : 1, "projId" : workDairyTO.projId}).then(function(data) {
					$scope.workDairySearchReq.empMaxHrs = data.labelKeyTO.id;
					$scope.workDairySearchReq.plantMaxHrs = 24;
					$scope.workDairySearchReq.contractNo = data.labelKeyTO.code;
					$scope.contractNo = data.labelKeyTO.code;
					
					$scope.disableButton = false;
					WorkDiaryService.getWorkDairyForClientApproval({"status" : 1, 'workDairyDate' : workDairyTO.workDairyDate, "projId" : workDairyTO.projId, "crewId" : workDairyTO.crewId, "apprStatus" : "SubmittedForClientApproval"}).then(function(data) {
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
						if ($scope.workDairySearchReq.apprStatus != undefined && $scope.workDairySearchReq.apprStatus != null && $scope.workDairySearchReq.apprStatus == 'Client Approved')
							$scope.disableButton = true;

						MaterialRegisterService.getProjMaterialSchItems({"status" : 1, "projId" : workDairyTO.projId}).then(function(data) {
							$scope.companyMap = data.registerOnLoadTO.companyMap;
							$scope.classificationMap = data.registerOnLoadTO.classificationMap;
							$scope.userProjectMap = data.userProjMap;
							angular.forEach(data.materialProjDtlTOs, function(value, key) {
								$scope.materialProjDtlMap[value.id] = value;
							});
							$scope.activeFlag = true;
							WorkDiaryService.getWorkDairyDetails({'workDairyId' : workDairyTO.id, "status" : 1, "projId" : workDairyTO.projId, "apprStatus": 'External Approval',"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id}).then(function(data) {
								$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
								$scope.workDairyEmpDtlTOs = $filter('unique')(data.workDairyEmpDtlTOs, 'code');
								$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
								$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
								$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
								$scope.workDairyMaterialDtlTOs = [];
								$scope.timeFlag = data.timeFlag;
								angular.forEach(data.workDairyMaterialDtlTOs, function(value, key) {
									$scope.deliveryPlace = value.deliveryPlace;
									$scope.populateMaterialObj($scope.workDairyMaterialDtlTOs, value);
								});
								console.log($scope.workDairyProgressDtlTOs)
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while getting workDairy Details", 'Error');
							});
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
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