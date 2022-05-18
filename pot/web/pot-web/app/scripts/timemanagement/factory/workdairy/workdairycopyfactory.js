'use strict';
app
	.factory(
		'WorkDairyCopyFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectCrewPopupService", "WorkDiaryService", function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService,
			ProjectCrewPopupService, WorkDiaryService) {
			var service = {};
			service.getWorkDairyDetails = function (workDairySearchReq, empRegMap,
				plantRegMap, materialRegMap, sowMap, workDairyCostCodes,
				empExistingMap, existingPlantMap) {
				var deferred = $q.defer();
				if (workDairySearchReq.crewLabelKeyTO <= 0
					|| workDairySearchReq.workDairyDate <= 0) {
					GenericAlertService.alertMessage(
						"Please select WorkDairy Date and Crew copy Workdairy",
						'Error');
					return;
				}

				var toDate = new Date(workDairySearchReq.workDairyDate);

				$scope.fromWorkDairyDate = toDate;
				$scope.fromWorkDairyDate.setDate(toDate.getDate() - 1);

				var Req = {
					"status": 1,
					"projId": workDairySearchReq.projectLabelKeyTO.projId,
					"crewId": workDairySearchReq.crewLabelKeyTO.id,
					"workDairyDate": workDairySearchReq.workDairyDate,
					"fromWorkDairyDate": $scope.fromWorkDairyDate
				};
				var copyResults = WorkDiaryService.copyWorkDairy(Req);

				copyResults
					.then(
						function (data) {
							var openPopup = service.getWorkDairyDetails(
								workDairySearchReq, empRegMap,
								plantRegMap, materialRegMap, sowMap,
								workDairyCostCodes, empExistingMap,
								existingPlantMap);
							openPopup
								.then(
									function (data) {
										deferred.resolve(data);
									},
									function (error) {
										GenericAlertService
											.alertMessage(
												"Error occured while gettting  workdairycopydetails",
												'Error');
									});

						},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occured while copying workdairy details",
									'Error');
						});
				return deferred.promise;
			},

				service.getWorkDairyDetails = function (workDairySearchReq, empRegMap,
					plantRegMap, materialRegMap, sowMap, workDairyCostCodes,
					empExistingMap, existingPlantMap) {

					var deferred = $q.defer();
					var popUp = ngDialog
						.open({
							template: 'views/timemanagement/workdairy/createworkdairy/copyworkdairydetails.html',
							className: 'ngdialog-theme-plain ng-dialogueCustom3',
							closeByDocument: false,
							showClose: false,
							className: 'ngdialog-theme-plain ng-dialogueCustom0',
							controller: [
								'$scope',
								function ($scope) {

									$scope.workDairySearchReq = [];
									$scope.selectedEmpLabelKeyTOs = [];
									let selectedPlantLabelKeyTOs = [];
									let selectedProgressLabelKeyTOs = [];
									let selectedMaterialLabelKeyTOs = [];
									$scope.eps = workDairySearchReq.projectLabelKeyTO.projName;
									$scope.workDairySearchReq.workDairyDate = workDairySearchReq.workDairyDate;
									$scope.workDairySearchReq.crewLabelKeyTO = workDairySearchReq.crewLabelKeyTO.code;
									$scope.empExistingMap = empExistingMap;
									$scope.existingPlantMap = existingPlantMap;
									$scope.empRegMap = empRegMap;
									$scope.plantRegMap = plantRegMap;
									$scope.materialRegMap = materialRegMap;
									$scope.costCodes = [];
									$scope.selectplants=[];
									$scope.selectplants.push(workDairyCostCodes);
									$scope.sowMap = [];
									$scope.workDairyEmpDtlTOs = [];
									$scope.workDairyPlantDtlTOs = [];
									$scope.workDairyMaterialDtlTOs = [];
									$scope.workDairyProgressDtlTOs = [];
									$scope.selectedEmpLabelKeyTO = [];
									var flag= false;
                                    console.log(workDairyCostCodes);
                                    angular.forEach(workDairyCostCodes,function(value,key){
	                                   $scope.costCodes.push(value.costId);
                                    });
									let toDate = new Date(
										workDairySearchReq.workDairyDate);

									$scope.fromWorkDairyDate = toDate;
									$scope.fromWorkDairyDate.setDate(toDate
										.getDate() - 1);
									$scope.fromWorkDairyDate = moment($scope.fromWorkDairyDate).format("DD-MMM-YYYY");
									$scope.createWorkDiaryTabs = [
										{
											title: 'Progress',
											url: 'views/timemanagement/workdairy/createworkdairy/copyworkdairyprogress.html'
										},
										{
											title: 'Manpower Utilisation',
											url: 'views/timemanagement/workdairy/createworkdairy/copyworkdairymanpower.html'
										},
										{
											title: 'Plant Utilisation',
											url: 'views/timemanagement/workdairy/createworkdairy/copyworkdairyplant.html'
										},
										{
											title: 'Material and Store Consumption',
											url: 'views/timemanagement/workdairy/createworkdairy/copyworkdairymaterial.html'
										}];

									$scope.currentTab = 'views/timemanagement/workdairy/createworkdairy/copyworkdairyprogress.html';
									$scope.onClickTab = function (tab) {
										$scope.currentTab = tab.url;
									}, $scope.isActiveTab = function (tabUrl) {
										return tabUrl == $scope.currentTab;
									}
									$scope.crewLabelKeyTO = [];
									$scope.crewLabelKeyTO = angular
										.copy(workDairySearchReq.crewLabelKeyTO);
									$scope.getCrewList = function () {
										let crewReq = {
											"status": 1,
											"projId": workDairySearchReq.projectLabelKeyTO.projId,
										};
										var crewSerivcePopup = ProjectCrewPopupService
											.getCrewPopup(crewReq);
										crewSerivcePopup
											.then(
												function (data) {
													$scope.crewLabelKeyTO.id = data.projCrewTO.id;
													$scope.crewLabelKeyTO.code = data.projCrewTO.code;
													$scope.getWorkDairyDetails();

												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occured while selecting Crew Details",
															'Error');
												});
									},
										$scope.getWorkDairyDetails = function () {
											if ($scope.workDairySearchReq.crewLabelKeyTO <= 0
												|| $scope.workDairySearchReq.workDairyDate <= 0) {
												GenericAlertService
													.alertMessage(
														"Please select Workdairy Date and  Crew to get workdairy details",
														'Error');
												return;
											}
											var Req = {
												"status": 1,
												"projId": workDairySearchReq.projectLabelKeyTO.projId,
												"crewId": $scope.crewLabelKeyTO.id,
												"workDairyDate": $scope.fromWorkDairyDate,
												//"workDairyDate": moment($scope.fromWorkDairyDate).format("DD-MMM-YYYY"),
												"fromWorkDairyDate": $scope.fromWorkDairyDate,
												"costIds" : $scope.costCodes

											};
											console.log(workDairySearchReq.workDairyDate);
											var copyResults = [];
											copyResults = WorkDiaryService
												.copyWorkDairy(Req);
											copyResults
												.then(function(data) {
													console.log(data);
												//	$scope.workDairyEmpDtlTOs = data.workDairyEmpDtlTOs;
													$scope.workDairyEmpDtlTOs = $filter('unique')(data.workDairyEmpDtlTOs, 'code');
													$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
											//		$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
													$scope.workDairyProgressDtlTOs = $filter('unique')(data.workDairyProgressDtlTOs, 'code');
													$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;     
													$scope.workDairyMaterialDtlTOs = data.workDairyMaterialDtlTOs;
												}, function (error) {
													GenericAlertService.alertMessage("Error occured while getting workdairy details.", 'Error');
												});
										},
										$scope.selectEmp = function (emp) {
											//if (emp.id)
											//		resetEmp(emp);
											if (emp.select) {
												var flag = true;
												$scope.selectedEmpLabelKeyTOs.push(emp);
												console.log($scope.selectedEmpLabelKeyTOs);
												for (let i = 0; i < $scope.selectedEmpLabelKeyTOs.length; i++) {
													$scope.selectedEmpLabelKeyTO[i] = $scope.selectedEmpLabelKeyTOs[i];
												}

											} else {
												$scope.selectedEmpLabelKeyTOs.pop(emp);
											}
										}
									for (let i = 0; i < $scope.selectedEmpLabelKeyTOs.length; i++) {
										$scope.selectedEmpLabelKeyTO[i] = $scope.selectedEmpLabelKeyTOs[i];
									}	
										$scope.copyReset = function(){
											$scope.crewLabelKeyTO.code ="";
											$scope.fromWorkDairyDate = "";
											$scope.workDairyEmpDtlTOs = [];
									        $scope.workDairyPlantDtlTOs = [];
									        $scope.workDairyMaterialDtlTOs = [];
									        $scope.workDairyProgressDtlTOs = [];
									        $scope.selectedEmpLabelKeyTO = [];
										}

									let resetEmp = function (emp) {
										emp.id = null;
										emp.workDairyId = null;
										emp.workDairyEmpWageTOs.map(empWage => {
											empWage.id = null;
											empWage.empDtlId = null;
											empWage.idleTotal = null;
											empWage.usedTotal = null;
											empWage.apprComments = null;
											empWage.workDairyEmpCostDtlTOs.map(empCost => {
												empCost.idleTime = null;
												empCost.oldIdleTime = null;
												empCost.usedTime = null;
												empCost.workDairyId = null;
											});
										});
									}

									$scope.selectPlant = function (plant) {
										//if (plant.id) 
										//	resetPlants(plant);
										if (plant.select) {
											selectedPlantLabelKeyTOs
												.push(plant);
										} else {
											selectedPlantLabelKeyTOs.pop(plant);
										}
									}

									let resetPlants = function(plant) {
										plant.id = null;
										plant.workDairyId = null;
										plant.workDairyPlantStatusTOs.map(plantStatus => {
											plantStatus.plantDtlId = null;
											plantStatus.id = null;
											plantStatus.idleTotal = null;
											plantStatus.usedTotal = null;
											plantStatus.workDairyPlantCostDtlTOs.map(plantCost => {
												plantCost.id = null;
												plantCost.idleTime = null;
												plantCost.oldIdleTime = null;
												plantCost.oldUsedTime = null;
												plantCost.usedTime = null;
												plantCost.workDairyId = null;
												plantCost.plantStatusId = null;
											});
										});
									}

									$scope.selectProgress = function (progress) {
										//if (progress.id)
										//		resetProgress(progress);
										if (progress.select) {
											selectedProgressLabelKeyTOs
												.push(progress);
										} else {
											//selectedProgressLabelKeyTOs.pop(progress);
											selectedProgressLabelKeyTOs.splice(selectedProgressLabelKeyTOs.indexOf(progress), 1);
										}
									}

									let resetProgress = function (progress) {
										progress.id = null;
										progress.workDairyId = null;
										progress.value = null;
									}

									$scope.selectMaterial = function (material) {
										//if (material.id) {
										//	resetMaterial(material);
										//}
										if (material.select) {
											selectedMaterialLabelKeyTOs
												.push(material);
										} else {
											selectedMaterialLabelKeyTOs
												.pop(material);
										}
									}

									let resetMaterial = function(material) {
										material.id = null;
										material.availableQty = material.closingStock;
										material.apprComments = null;
										material.transitQty = null;
										material.workDairyId = null;
										material.workDairyMaterialStatusTOs.map(wrkDairyMat => {
											wrkDairyMat.id = null;
											wrkDairyMat.materialDtlId = null;
											wrkDairyMat.total = null;
											wrkDairyMat.workDairyMaterialCostTOs.map(workDairyCost => {
												workDairyCost.id = null;
												workDairyCost.materialStatusId = null;
												workDairyCost.quantity = null;
												workDairyCost.workDairyId = null;
											});
										});
									}								

									$scope.selectAllSows = function (selectAll) {
										$scope.workDairyProgressDtlTOs.map(progress => {
											progress.select = selectAll;
											$scope.selectProgress(progress);
										});
									}

									$scope.selectAllEmps = function (selectAll) {
										$scope.workDairyEmpDtlTOs.map(emp => {
											emp.select = selectAll;
											$scope.selectEmp(emp);
										});
									}

									$scope.selectAllPlants = function (selectAll) {
										$scope.workDairyPlantDtlTOs.map(plant => {
											plant.select = selectAll;
											$scope.selectPlant(plant);
										});
									}

									$scope.selectAllMaterials = function (selectAll) {
										$scope.workDairyMaterialDtlTOs.map(material => {
											material.select = selectAll;
											$scope.selectMaterial(material);
										});
									}
	
									$scope.addToWorkDairy = function() {
										
										var workDairyPlantCostDtlTOs = [];
										angular.forEach(workDairyCostCodes, function(value, key) {
											angular.forEach(selectedPlantLabelKeyTOs,function(values,key){
												if(values.workDairyPlantStatusTOs[key].workDairyPlantCostDtlTOs[key].costId == value.costId)
												workDairyPlantCostDtlTOs.push(angular.copy({
												"costId": values.workDairyPlantStatusTOs[key].workDairyPlantCostDtlTOs[key].costId,
												"code": values.workDairyPlantStatusTOs[key].workDairyPlantCostDtlTOs[key].code,
												"usedTime": values.workDairyPlantStatusTOs[key].workDairyPlantCostDtlTOs[key].usedTime,
												"idleTime": values.workDairyPlantStatusTOs[key].workDairyPlantCostDtlTOs[key].idleTime,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											})) 
											 else
										       workDairyPlantCostDtlTOs.push(angular.copy({
												"costId": value.costId,
												"code": value.code,
												"usedTime": null,
												"idleTime": null,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											})) 
											});							
											
										});
										var workDairyPlantStatusTOs = []
										workDairyPlantStatusTOs
											.push({
												"plantStatusId": null,
												"status": 1,
												"workDairyPlantCostDtlTOs": workDairyPlantCostDtlTOs

											});
										var workDairyPlantDtlTOs = [];
										angular.forEach(selectedPlantLabelKeyTOs, function(value, key) {
											workDairyPlantDtlTOs.push(angular.copy({
												"workDairyId": workDairySearchReq.workDairyId,
												"plantRegId": value.plantRegId,
												"code": value.code,
												"name": value.name,
												"shiftName": value.shiftName,
												"status": 1,
												"plantRegNum": value.plantRegNum,
												"classType": value.classType,
												"manufacture": value.manufacture,
												"model": value.model,
												"cmpCategoryName": value.cmpCategoryName,
												"procureCatg": value.procureCatg,
												"workDairyPlantStatusTOs": workDairyPlantStatusTOs
											}));
										});
										
										
										var workDairyEmpTOs = [];
										var workDairyEmpCostDtlTOs = [];
										angular.forEach(workDairyCostCodes, function(value, key) {
											angular.forEach($scope.selectedEmpLabelKeyTO, function(values,key){
									 if(values.workDairyEmpWageTOs[key].workDairyEmpCostDtlTOs[key] != undefined){
										if(values.workDairyEmpWageTOs[key].workDairyEmpCostDtlTOs[key].costId == value.costId)		
												workDairyEmpCostDtlTOs.push(angular.copy({
												"costId": values.workDairyEmpWageTOs[key].workDairyEmpCostDtlTOs[key].costId,
												"code": values.workDairyEmpWageTOs[key].workDairyEmpCostDtlTOs[key].code,
												"usedTime": values.workDairyEmpWageTOs[key].workDairyEmpCostDtlTOs[key].usedTime,
												"idleTime": values.workDairyEmpWageTOs[key].workDairyEmpCostDtlTOs[key].idleTime,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											})) 
											else
											workDairyEmpCostDtlTOs.push(angular.copy({
												"costId": value.costId,
												"code": value.code,
												"usedTime": null,
												"idleTime": null,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											}))
								     }	
										
											});
											
										});
										if(flag){
											var workDairyEmpWageTOs = []
											workDairyEmpWageTOs.push({
												"wageId": null,
												"status": 1,
												"code": $scope.selectedEmpLabelKeyTO[0].workDairyEmpWageTOs[0].code,
												"workDairyEmpCostDtlTOs": workDairyEmpCostDtlTOs
											});
										}
										
										angular.forEach($scope.selectedEmpLabelKeyTO, function(value, key) {
											workDairyEmpTOs.push({
												"workDairyId": workDairySearchReq.workDairyId,
												"empRegId": value.empRegId,
												"code": value.code,
												"classType": value.classType,
												"cmpCatgName": value.cmpCatgName,
												"firstName": value.firstName,
												"gender": value.gender,
												"lastName": value.lastName,
												"procureCatg": value.procureCatg,
												"status": 1,
												"workDairyEmpWageTOs": workDairyEmpWageTOs,
												"unitOfMeasure": value.unitOfMeasure
											});
										});
										
										var workDairyMaterialDtlTOs = [];
										var workDairyMaterialStatusTOs = [];
										var workDairyMaterialCostTOs = [];
										angular.forEach(workDairyCostCodes, function(value, key) {
											angular.forEach(selectedMaterialLabelKeyTOs,function(values,key){
										 if(values.workDairyMaterialStatusTOs[key].workDairyMaterialCostTOs[key] != undefined){
											if(values.workDairyMaterialStatusTOs[key].workDairyMaterialCostTOs[key].costId == value.costId)			
												workDairyMaterialCostTOs.push(angular.copy({
												"costId": values.workDairyMaterialStatusTOs[key].workDairyMaterialCostTOs[key].costId,
												"quantity": values.workDairyMaterialStatusTOs[key].workDairyMaterialCostTOs[key].quantity,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											}))
											else
											workDairyMaterialCostTOs.push(angular.copy({
												"costId": value.costId,
												"quantity": null,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											}))
										}								
											
											})
											
										});
                                        
										workDairyMaterialStatusTOs.push({
											"apprStatus": null,
											"materialDtlId": null,
											"status": 1,
											"id":null,
											"comments": null,
											"apprStatus": null,
											"workDairyMaterialCostTOs": workDairyMaterialCostTOs
										})
                                        console.log(workDairyMaterialStatusTOs)
										angular.forEach(selectedMaterialLabelKeyTOs, function(value, key) {
											workDairyMaterialDtlTOs.push(angular.copy({
												"workDairyId": workDairySearchReq.workDairyId,
												"apprComments":value.apprComments,
												"status": 1,
												"id": value.id,
												"projId": value.projId,
												"sourceType": value.sourceType,
												"docketType": value.docketType,
												"projDocketSchId": value.projDocketSchId,
												"scheduleItemId": value.scheduleItemId,
												"schItemCmpId": value.materialProjDtlTO[key].purchaseSchLabelKeyTO.displayNamesMap.schItemCmpId,
												"deliveryDocketId": value.deliveryDocketId,
												"docketId": value.docketId,
												"docketNum": value.docketNum,
												"docketDate": value.docketDate,
												"deliveryPlace":value.deliveryPlace,
												"purchaseLabelKeyTO": {
													id: value.materialProjDtlTO[key].purchaseSchLabelKeyTO.id,
													code: value.materialProjDtlTO[key].purchaseSchLabelKeyTO.code,
													name: null
												},
												"purchaseSchLabelKeyTO": {
													id: value.materialProjDtlTO[key].purchaseSchLabelKeyTO.id,
													code: value.materialProjDtlTO[key].purchaseSchLabelKeyTO.code,
													name: value.materialProjDtlTO[key].purchaseSchLabelKeyTO.name,
													displayNamesMap: value.materialProjDtlTO[key].purchaseSchLabelKeyTO.displayNamesMap
												},
												"stockLabelKeyTO": {
													id: null,
													code: null,
													name: null,
													type: null
												},
												"materialProjDtlTO":value.materialProjDtlTO,
												"receivedQty": value.receivedQty,
												"defectComments": value.defectComments,
												"transitQty": value.transitQty,
												"consumptionQty": value.consumptionQty,
												"unitOfRate": value.unitOfRate,
												"comments": value.comments,
												"workDairyMaterialStatusTOs": workDairyMaterialStatusTOs,
												"availableQty": null

											}));
										});
										console.log(workDairyMaterialDtlTOs)
										
										/*var selectedSowitems = [];
										var selectedcostCodes = [];
										var workDairyProgressDtlTOs =[];
										angular.forEach(selectedProgressLabelKeyTOs, function(value, key) {
											selectedSowitems.push({
												"sowId": value.id,
												"costId": value.projCostId,
												"quantity": value.quantity,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											});
											selectedcostCodes.push({
												"status": 1,
												"costId": value.projCostId,
												"crewId": workDairySearchReq.crewLabelKeyTO.id,
												"projId": workDairySearchReq.projectLabelKeyTO.projId,
												"workDairyId": workDairySearchReq.workDairyId
											});
										});
										workDairyProgressDtlTOs.push(angular.copy({
											"workDairyProgressDtlTOs": selectedSowitems,
											"status": 1,
											"projId": workDairySearchReq.projectLabelKeyTO.projId,
											"workDairyTO": {
												"status": 1,
												"id": workDairySearchReq.workDairyId,
												"projId": workDairySearchReq.projectLabelKeyTO.projId,
												"crewId": workDairySearchReq.crewLabelKeyTO.id,
												"clientApproval": workDairySearchReq.clientApproval,
												"apprStatus": workDairySearchReq.apprStatus,
												"newRequired": false
											},
											"workDairyCostCodeSaveReq": {
												"workDairyCostCodeTOs": selectedcostCodes,
												"crewId": workDairySearchReq.crewLabelKeyTO.id,
												"projId": workDairySearchReq.projectLabelKeyTO.projId,
												"workDairyId": workDairySearchReq.workDairyId,
												"status": 1
											}
										}))*/
										var saveSowReqDetails = [];
										angular.forEach(selectedProgressLabelKeyTOs, function(value, key) {
											saveSowReqDetails.push({
												"sowId": value.sowId,
												"sowCode": value.code,
												"sowDesc": value.name,
												"soeId": value.soeId,
												"soeCode": value.soeCode,
												"sorId": value.sorId,
												"sorCode": value.sorCode,
												"costId": value.costId,
												"costCode": value.costId,
												"measureId": value.measureId,
												"measureCode": value.measureCode,
												"quantity": value.quantity,
												"actualQty": value.actualQty,
												"value":value.value,
												"sowCode":value.sowCode,
												"sowDesc":value.sowDesc,
												"status": 1
											});
										//	if ($scope.workDairyCostCodeMap[value.projCostId] == null) {
					//							$scope.workDairyCostCodeMap[value.projCostId] = true;
												/*workDairyCostCodeTOs.push({
													"status": 1,
													"costId": value.projCostId
												});*/
									//		}
										});
										var req = {
											"workDairyEmpDtlTOs": workDairyEmpTOs,
											"workDairyPlantDtlTOs": workDairyPlantDtlTOs,
											"workDairyProgressDtlTOs": saveSowReqDetails,
											"workDairyMaterialDtlTOs": workDairyMaterialDtlTOs
										}
										console.log(req);
										console.log(req.workDairyEmpDtlTOs.length)
										if($scope.selectedEmpLabelKeyTOs.length=0){
											GenericAlertService.alertMessage('Please select atleast one row','Warning');
											return;
										}
										var resultData = GenericAlertService.alertMessageModal('Selected items added successfully to Work Diary ', "Info");
										resultData.then(function() {
											$scope.closeThisDialog();
											deferred.resolve(req);

										}, function(error) {
											GenericAlertService.alertMessage("Error occured while copying workdairy", 'Error');
										});
									}
								}]
						});
					return deferred.promise;
				}
			return service;
		}]);
