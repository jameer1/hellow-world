'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("projbudgets", {
		url: '/projbudgets',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectbudgets/budgetview.html',
				controller: 'ProjectBudgetController'
			}
		}
	})
}]).controller('ProjectBudgetController', ["$rootScope", "$scope", "uiGridGroupingConstants","uiGridConstants","$q", "blockUI", "ProjectBudgetService", "ProjectScheduleService",
	"GenericAlertService", "ProjManPowerFactory", "EstimateToCompleteService", "ProjPlantFactory", "ProjBudgetMaterialFactory", 'EpsService',
	"ProjectSettingCostItemFactory", "ProjectSettingSOWItemFactory", "TreeService", "SchedulePlannedValueService", "ResourceCurveService","ngDialog", "ProjectStatusService", "ProjectBudgetFactory", "stylesService", "ngGridService",
	function ($rootScope, $scope, uiGridGroupingConstants, uiGridConstants, $q, blockUI, ProjectBudgetService, ProjectScheduleService, GenericAlertService, ProjManPowerFactory,
		EstimateToCompleteService, ProjPlantFactory, ProjBudgetMaterialFactory,EpsService, ProjectSettingCostItemFactory,
		ProjectSettingSOWItemFactory, TreeService, SchedulePlannedValueService, ResourceCurveService,ngDialog, ProjectStatusService, ProjectBudgetFactory ,stylesService, ngGridService) {

		$rootScope.projId = null;
		$scope.treeData = [];
		$scope.stylesSvc = stylesService;
		var deferred = $q.defer();

		$scope.moreFlag = 0;
		$scope.currentTab = null;
		$scope.currentTab1 = null;

		$scope.projSummaryTab = null;

		var editManpower = [];
		var editPlants = [];
		let plantEditDialogOpened = false;
		let manPowerEditDialogOpened = false;
		var editMeterials = [];
		var editNoteBook = [];
		var selectedPlants = [];
		var selectedManpower = [];
		var selectedMeterials = [];

		$scope.editing = false;
		$scope.supervisors = [];
		$scope.reqNumbers = [];

		$scope.projTabs = [];
		$scope.costDatamoreFlag = 0;
		$scope.projManpowerDetails = [];
		$scope.projPlantsDetails = [];
		$scope.projectMaterialDtlTOs = [];
		$scope.projCostStmtDtls = [];
		$scope.projMeasureDetails = [];
		$scope.exitingSchMaterilMap = [];
		var exitingMaterialItemsMap = [];
		$scope.measureunits = [];
		$scope.plantunits = [];
		$scope.costunits = [];
		$scope.dateunits = [];
		$scope.tableData = [];
		$scope.isPrimaveraIntegrationEnabled = 'No';
		$scope.submitClicked=false;
		
		
		var manPowerIdsData = []; //manpower ids
		var plantIdsData = []; //plants ids data
		var materialIdsData = []; // material ids data
		var costIdsData = []; // cost statement ids data
		var costItemIdsData = []; // cost code item ids data
		$scope.manpowerItemStatus = "";
		$scope.materialItemStatus = "";
		$scope.costStmtItemStatus = "";
		$scope.plantItemStatus = "";
		
		$scope.disableBtnsJson = {"approveBtn":true,"submitBtn":true,"returnBtn":false}; // data which handles approve,submit and return with comments buttons
		$scope.disableCreateBtn = false;
		$scope.displayEditBtn = false;
		$scope.disableEditValues = false;
		
		var getReq = {
			"status": 1,
			"projId": null
		};

		// leaf highlighted
		$scope.setClickedRow = function (row) {
			$scope.selectedRow = row;
		}
		// end
		/*---------Project Tabs----------*/

		$scope.preventCopyPaste = function () {
			$(document).ready(function () {
				$("body").on("contextmenu cut copy paste", function (e) {
					return false;
				});

			});

		},

			$scope.projTabs = [{
				title: 'Manpower',
				url: 'views/projectbudgets/manpower.html',
				appCodeTemplateKey: 'PRJ_PROJBUDGET_MANPOWER_VIEW',
				SelenumLocator: 'Projects_ProjectBudgets_ManPower'
			}, {
				title: 'Plant',
				url: 'views/projectbudgets/plants.html',
				appCodeTemplateKey: 'PRJ_PROJBUDGET_PLANT_VIEW',
				SelenumLocator: 'Projects_ProjectBudgets_Plants'
			}, {
				title: 'Materials',
				url: 'views/projectbudgets/projbudgetmaterials.html',
				appCodeTemplateKey: 'PRJ_PROJBUDGET_MATERIAL_VIEW',
				SelenumLocator: 'Projects_ProjectBudgets_Materials'
			}, {
				title: 'Cost (Cost Statement)',
				url: 'views/projectbudgets/coststatement.html',
				appCodeTemplateKey: 'PRJ_PROJBUDGET_COSTSTATEMENT_VIEW',
				SelenumLocator: 'Projects_ProjectBudgets_CostStatement'
			}]

		$scope.currentTab = $scope.projTabs[0].url;
	/*
	 * $scope.onClickTab = function(tab) { $scope.currentTab = tab.url; },
	 */ $scope.isActiveTab = function (tabUrl) {
			return tabUrl == $scope.currentTab;
		},
			$scope.onClickTab = function (tab) {
				if ($rootScope.projId == null || $rootScope.projId == undefined) {
					GenericAlertService.alertMessage("Please select project", "Warning");
					return;
				}
				if ($scope.projTabs[0].url == tab.url) {
					$scope.getManpowerRecords();
				}
				if ($scope.projTabs[1].url == tab.url) {
					$scope.getPlantRecords();
				}
				if ($scope.projTabs[2].url == tab.url) {
					$scope.getMaterialRecords();
				}
				if ($scope.projTabs[3].url == tab.url) {
					$scope.getCostStatementRecords();
				}
				$scope.currentTab = tab.url;
			},

			$scope.clickForwardCostData = function (costDatamoreFlag1) {
				if ($scope.costDatamoreFlag < 6) {
					$scope.costDatamoreFlag = costDatamoreFlag1 + 1;
				}
				
				if(costDatamoreFlag1==1){
					$scope.submitClicked=true;
				}
				else if(costDatamoreFlag1==2|| costDatamoreFlag1==3) {
					$scope.submitClicked=true;
				}
				else if(costDatamoreFlag1==4|| costDatamoreFlag1==5){
					$scope.submitClicked=true;
				}
			}, $scope.clickBackwardCostData = function (costDatamoreFlag1) {
				if ($scope.costDatamoreFlag > 0) {
					$scope.costDatamoreFlag = costDatamoreFlag1 - 1;
				}
				if(costDatamoreFlag1==2){
					$scope.submitClicked=false;
				}
				
			},
			/* EPS Projects */
			$scope.openSettings = function (projId, row) {

				$scope.selectedRow = row;
				$rootScope.projId = projId;
				$scope.onClickTab($scope.projTabs[0]);
				$scope.selectedRow = row;
				ProjectStatusService.getProjGenerals({"projId": projId, "status": 1}).then(function(data){
					$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
				}, function (error) {
					cosole.log(error)
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			}, $scope.getEPSDetails = function () {
				var req = {
					"status": 1
				};
				EpsService.getEPSUserProjects(req).then(function (data) {
					$scope.epsData = populateData(data.epsProjs);
					$scope.epsData.map(eps => {
						$scope.itemClick(eps, false);
					});
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
				});
			};

		var populateData = function (data) {
			return TreeService.populateTreeData(data, 0, [], 'projCode', 'childProjs');
		}

		$scope.itemClick = function (item, collapse) {
			TreeService.treeItemClick(item, collapse, 'childProjs');
		};

		$scope.calcEstimateToComplete = function (budgetObj, formulaType) {
			var BAC = 0;
			if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0) {
				BAC = budgetObj.revisedQty - budgetObj.actualQty;
			} else {
				BAC = budgetObj.originalQty - budgetObj.actualQty
			}
			if (formulaType == 1) {
				budgetObj.estimateComplete = BAC;
				return budgetObj.estimateComplete;
			} else if (formulaType == 2) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue);
				return budgetObj.estimateComplete;
			} else if (formulaType == 3) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.costPermIndex;
				return budgetObj.estimateComplete;
			} else if (formulaType == 4) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.costPermIndex * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			} else if (formulaType == 5) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.progressFactor;
				return budgetObj.estimateComplete;
			} else if (formulaType == 6) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.progressFactor * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			}

		}

		let attendResp = false;
		let manpowerResp = false;
		$scope.resourceCurveTypeMap = [];
		function getResourceCurveTypes() {
			var req = {
				"status": 1
			};
			ResourceCurveService.getResourceCurves(req).then(function (data) {
				angular.forEach(data.projResourceCurveTOs, function (value, key) {
					$scope.resourceCurveTypeMap[value.id] = value;
				});
			}, function (error) {
				GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
			});
		};
		getResourceCurveTypes();
		/* Man Power Tab */
		$scope.getManpowerRecords = function () {
			var getManpowerReq = {
				"status": "1",
				"projId": $rootScope.projId
			};
			console.log(getManpowerReq);
			ProjectBudgetService.getProjManpowers(getManpowerReq).then(function (data) {
				console.log(data.projManpowerTOs);
				console.log(data.projManpowerTOs,"projManpowerTOs")
				
						for(var manpower of data.projManpowerTOs ){
							manpower.revisedQty1=(manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.actualQty;
							
							var remQty1=(manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.actualQty;
							var b=(manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue
							var c=(manpower.earnedValue==0 || manpower.actualQty==0)  ? ((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue)   
								: (((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue) / manpower.productivityFactor)
							var bac_ev=((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue);
							var pf=(manpower.earnedValue==0 || manpower.earnedValue==null || manpower.actualQty==0) ? 0 : (manpower.earnedValue / manpower.actualQty);
							var spi=(manpower.earnedValue==0 || manpower.earnedValue==null || manpower.plannedValue==0) ? 0 : (manpower.earnedValue / manpower.plannedValue);
							var bac_ev_pf_spi=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
							var etc=manpower.estimateComplete;
														
							if(manpower.estimateType =='Remaining Units' ){
							   manpower.estimateto=remQty1;
							}
							else if(b > 0 ){
								manpower.estimateto=b;
							} 
							else if(manpower.estimateType == '(BAC-EV)/PF'){
								manpower.estimateto=c > 0 ? c : "";
							} 
							else if(manpower.estimateType == '(BAC-EV)/(PF*SPI)'){
								manpower.estimateto= bac_ev_pf_spi >0 ? 0 :"";
							}
							else if(manpower.estimateType == 'New Estimate' && etc >= 0){
								manpower.estimateto=etc;
							}
						
							var etcRemQty=(manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.actualQty;
							var etcRemQty1=etcRemQty > 0 ? etcRemQty : etcRemQty=0;
							var etcBAC_EV=(manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue;
							var etcBAC_EV1=etcBAC_EV > 0 ? etcBAC_EV : etcBAC_EV=0;
							var etcBAC_EV_PF=(manpower.earnedValue==0 || manpower.actualQty==0)  ? ((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue)   
								: (((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue) / manpower.productivityFactor);
						    var etcBAC_EV_PF1=etcBAC_EV_PF > 0 ? etcBAC_EV_PF : etcBAC_EV_PF=0 ;
							var bac_ev1=((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty) - manpower.earnedValue);
							var pf1=(manpower.earnedValue==0 || manpower.earnedValue==null || manpower.actualQty==0) ? 0 : (manpower.earnedValue / manpower.actualQty);
							var spi1=(manpower.earnedValue==0 || manpower.earnedValue==null || manpower.plannedValue==0) ? 0 : (manpower.earnedValue / manpower.plannedValue);
							var etcBAC_EV_PF_SPI=(pf1==0 || spi1 == 0)  ? bac_ev1 : (bac_ev1)/(pf1*spi1);
							var etcBAC_EV_PF_SPI1=etcBAC_EV_PF_SPI > 0 ? etcBAC_EV_PF_SPI : etcBAC_EV_PF_SPI=0;
							var estimateComplete=manpower.estimateComplete > 0 ? manpower.estimateComplete : manpower.estimateComplete=0;
							var etscNewEstimate=manpower.actualQty + estimateComplete;
							
							if(manpower.estimateType == 'Remaining Units'){
									manpower.estimatecom=manpower.actualQty + etcRemQty1;
							}
							else if(manpower.estimateType == 'BAC-EV'){
								manpower.estimatecom=manpower.actualQty + etcBAC_EV1
							}
							else if(manpower.estimateType == '(BAC-EV)/PF'){
								manpower.estimatecom=manpower.actualQty + etcBAC_EV_PF1; 
							}
							else if(manpower.estimateType == '(BAC-EV)/(PF*SPI)'){
								manpower.estimatecom=manpower.actualQty + etcBAC_EV_PF_SPI1;
							}
							else if(manpower.estimateType == 'New Estimate'){
								manpower.estimatecom=etscNewEstimate > 0 ? etscNewEstimate: "";
							}
				}
				$scope.gridOptions2.data = angular.copy(data.projManpowerTOs);
				if (data.projManpowerTOs.length > 0 && data.projManpowerTOs[0] && data.projManpowerTOs[0].estimateType
					&& data.projManpowerTOs[0].estimateType.contains('SPI')) {
					console.log("if");
					ProjectScheduleService.getProjBudgetManPowerDetails(getManpowerReq).then(function (data1) {
						console.log(data1);
						$scope.projManpowerDetails = data.projManpowerTOs;
						calculatePlannedValues(data1, $scope.projManpowerDetails, "empClassId");
						EstimateToCompleteService.manpower($scope.projManpowerDetails);
						calculateManhoursTotal($scope.projManpowerDetails);
						retrieveManpowerIds($scope.projManpowerDetails);
						if( $scope.projManpowerDetails != null && $scope.projManpowerDetails.length != 0 )
						{
							$scope.manpowerItemStatus = $scope.projManpowerDetails[0].itemStatus;
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
					});
				} else {
					$scope.projManpowerDetails = data.projManpowerTOs;
					EstimateToCompleteService.manpower($scope.projManpowerDetails);
					console.log("else");
					calculateManhoursTotal($scope.projManpowerDetails);
					retrieveManpowerIds($scope.projManpowerDetails);
					if( $scope.projManpowerDetails != null && $scope.projManpowerDetails.length != 0 )
					{
						$scope.manpowerItemStatus = $scope.projManpowerDetails[0].itemStatus;
					}
				}
				console.log("Item status:",$scope.manpowerItemStatus);
				if( $scope.projManpowerDetails[0].itemStatus != null )
				{
					if( $scope.manpowerItemStatus.indexOf('SUBMITTED_FOR_APPROVAL') > -1 )
					{
						$scope.disableBtnsJson.returnBtn = true;
						$scope.disableBtnsJson.approveBtn = false;
					}
					else if( $scope.manpowerItemStatus.indexOf('RETURNED_WITH_COMMENTS') > -1 ) {
						$scope.disableBtnsJson.submitBtn = false;
					}
					else if( $scope.manpowerItemStatus.indexOf('DRAFT') > -1 )
					{
						console.log("else condition");					
						$scope.disableCreateBtn = true;
						$scope.displayEditBtn = false;
						//$scope.disableEditValues = true;
						$scope.disableBtnsJson.submitBtn = false;
					}
					else if( $scope.manpowerItemStatus == "APPROVED" || $scope.manpowerItemStatus == "FINALIZED" ) {
						$scope.disableBtnsJson.submitBtn = true;
						$scope.disableCreateBtn = true;
						$scope.disableEditValues = true;
					}
				}				
				console.log($scope.disableBtnsJson);
				$scope.getPlantRecords();
				$scope.getMaterialRecords();
				$scope.getCostStatementRecords();
				console.log(manPowerIdsData);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
			});
		};

		function retrieveManpowerIds(manpowerData) {
			console.log("retrieveManpowerIds function");
			console.log(manpowerData);
			manPowerIdsData = [];
			angular.forEach(manpowerData,function(value,key){
				if( value != null )
				{
					manPowerIdsData.push(value.id);
				}
			});
			console.log(manPowerIdsData);
		}
		
		function calculatePlannedValues(budgetValue, dailyResources, key) {
			const currentDate = new Date();
			for (const dailyResource of dailyResources) {
				const value = dailyResource[key];
				if (!value)
					continue;
				let budgetKey = key;
				let projTOs = budgetValue.projManpowerTOs;
				if (key == 'costId') {
					budgetKey = 'costCodeId';
					projTOs = budgetValue.projScheduleCostCodeTOs;
				}
				dailyResource.plannedValue = 0;
				const selectedRow = projTOs.find(x => x[budgetKey] == value);
				if (!selectedRow)
					continue;
				const startdate = selectedRow.startDate;
				if (!startdate)
					continue;
				let regularNonWorkingDays = [];
				if (budgetValue.regularHolidays) {
					regularNonWorkingDays.push((budgetValue.regularHolidays['sunday']) ? 0 : "");
					regularNonWorkingDays.push((budgetValue.regularHolidays['monday']) ? 1 : "");
					regularNonWorkingDays.push((budgetValue.regularHolidays['tuesday']) ? 2 : "");
					regularNonWorkingDays.push((budgetValue.regularHolidays['wednesday']) ? 3 : "");
					regularNonWorkingDays.push((budgetValue.regularHolidays['thursday']) ? 4 : "");
					regularNonWorkingDays.push((budgetValue.regularHolidays['friday']) ? 5 : "");
					regularNonWorkingDays.push((budgetValue.regularHolidays['saturday']) ? 6 : "");
				}
				let req = {
					"actualAndPlanned": false,
					"forReport": true,
					"reportPeriod": [startdate],
					"selectedItemRow": selectedRow,
					"selectedTimeScale": {
						value: 7,
						type: "Weekly"
					},
					"calNonWorkingDays": budgetValue.calNonWorkingDays,
					"regularNonWorkingDays": regularNonWorkingDays,
					"searchProject": {
						"projId": $rootScope.projId
					},
					"resourceCurveTypeMap": $scope.resourceCurveTypeMap,
					"reportProjectSetting": budgetValue.projReportsTo,
					"calSplWorkingDays": budgetValue.calSplWorkingDays
				};
				let resp = SchedulePlannedValueService.createPlannedCurve(req);
				if (resp && resp.labels) {
					for (let index = 0; index < resp.labels.length; index++) {
						if (new Date(resp.labels[index]) <= currentDate) {
							let plannedValue = resp.data[0][index];
							if (plannedValue) {
								dailyResource.plannedValue += parseFloat(plannedValue);
							}
						}
					}
				} else {
					dailyResource.plannedValue = 0;
				}
			}
		};

		$scope.getProjManpowerMap = function () {
			var req = {
				"projId": $rootScope.projId,
				"status": 1
			};
			ProjectBudgetService.getProjManpowerMap(req).then(function (data) {
				$scope.manpowerMap = data.projSettingsUniqueMap;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Manpower ActualHours", 'Error');
			});
		}, $scope.manPowerRowSelect = function (manpower) {
			if (manPowerEditDialogOpened) {
				manPowerEditDialogOpened = false;
				editManpower = [];
			}

			if (manpower.select) {
				editManpower.push(manpower);
			} else {
				editManpower.splice(editManpower.indexOf(manpower), 1);
			}
		}
		$scope.editManPowerDetails = function (actionType, projId) {
			angular.forEach(editManpower, function (value, key) {
				value.select = false;
			});

			if (editManpower.length > 0 && actionType == 'Add') {
				editManpower = [];
				GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
				return;
			}
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select project", "Info");

				return;
			}
			angular.forEach(editManpower, function (value) {
				value.select = false;
			});
			if (actionType == 'Edit' && editManpower <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
				return;
			} else if ($scope.projId !== undefined && $scope.projId != null) {
				manPowerEditDialogOpened = true;
				var popupDetails = ProjManPowerFactory.manPowerPopupDetails(actionType, projId, editManpower);
				editManpower = [];
				popupDetails.then(function (data) {
					$scope.projManpowerDetails = data.projManpowerTOs;
					editManpower = [];
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while selecting Project Man Power Details", 'Info');
				})
			}
		}, $scope.calcRemainingUnits = function (manpower) {
			ProjManPowerFactory.manPowerPopupDetails().calcRemainingUnits(manpower);
		},
		$scope.returnWithComments = function(budgetType,data) {
			console.log(data);
			console.log(budgetType);
			
			var returned_comments = $scope.comments;
			console.log(returned_comments);
			console.log(costItemIdsData);
			ProjectBudgetFactory.budgetReturnPopup(budgetType,data,manPowerIdsData,plantIdsData,materialIdsData,costIdsData,costItemIdsData);
		},

			/* Plants Tab */
			$scope.getPlantRecords = function () {
				var getPlantReq = {
					"status": 1,
					"projId": $rootScope.projId
				};
				ProjectBudgetService.getProjectPlants(getPlantReq).then(function (data) {
					$scope.projPlantsDetails = data.projectPlantsDtlTOs;
					console.log($scope.projPlantsDetails);
					calculatePlantTotal($scope.projPlantsDetails);
					retrievePlantIds($scope.projPlantsDetails);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Plant Details", "Error");
				});
			}, $scope.rowSelectPlant = function (plant) {
				if (plantEditDialogOpened) {
					plantEditDialogOpened = false;
					editPlants = [];
				}
				if (plant.select) {
					editPlants.push(plant);
				} else {
					editPlants.splice(editPlants.indexOf(plant), 1);
				}
			},

			$scope.editPlantDetails = function (actionType, projId) {
				angular.forEach(editPlants, function (value, key) {
					value.select = false;
				});

				if (editPlants.length > 0 && actionType == 'Add') {
					GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
					return;
				}
				if (actionType == 'Edit' && editPlants <= 0) {
					GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
					return;
				} else if ($scope.projId !== undefined && $scope.projId != null) {
					plantEditDialogOpened = true;
					var popupDetails = ProjPlantFactory.plantPopupDetails(actionType, projId, editPlants);
					popupDetails.then(function (data) {
						if (data)
							$scope.projPlantsDetails = data.projectPlantsDtlTOs;
							$scope.getPlantRecords();
						editPlants = [];
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while selecting Project Plant Details", 'Info');
					})
				}

			}
		function retrievePlantIds(plantsData) {
			plantIdsData = [];
			angular.forEach(plantsData,function(value,key){
				plantIdsData.push(value.id);
			});	
			console.log(plantIdsData);
		}
		
		function populateMaterialClassData(data, level, materialClassTOs, isChild, parent) {
		  console.log('VV data');
      console.log(data);

      console.log('level');
      console.log(level);

      console.log('materialClassTOs');
      console.log(materialClassTOs);

       console.log('isChild');
       console.log(isChild);

       console.log('parent');
       console.log(parent);

			return TreeService.populateTreeData(data, level, materialClassTOs, 'id', 'projectMaterialDtlTOs',
				isChild, parent)
		}

		/* Material Tab */
		$scope.getMaterialRecords = function () {
			var req = {
				"status": 1,
				"projId": $rootScope.projId
			};
			console.log(req);
			ProjectBudgetService.getProjectMaterials(req).then(function (data) {
				console.log(data);
				let materialData = populateMaterialClassData(data.projectMaterialDtlTOs, 0, []);
				console.log(materialData);
				materialData.map((treeItem) => {
					$scope.materialItemClick(treeItem, false);
				});
				$scope.projectMaterialDtlTOs = materialData;
				console.log($scope.projectMaterialDtlTOs);
				retrieveMaterialIds($scope.projectMaterialDtlTOs);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Material Details", "Error");
			});
		},
			$scope.rowSelectMaterial = function (material) {
				if (material.select) {
					editMeterials.push(material);
				} else {
					editMeterials.splice(editMeterials.indexOf(material), 1);
				}
			},
			$scope.materialItemClick = function (item, expand) {
				TreeService.treeItemClick(item, expand, 'projectMaterialDtlTOs');
			}
		function retrieveMaterialIds(materialsData) {
			materialIdsData = [];
			angular.forEach(materialsData,function(value){
				if( value.item )
				{
					materialIdsData.push(value.id);	
				}				
			});
			console.log(materialIdsData);
		} 
		
		$scope.addMaterialDetails = function () {
			if ($scope.projId !== undefined && $scope.projId != null) {
				var popupDetails = ProjBudgetMaterialFactory.getMaterialClasses($scope.projId, $scope.exitingSchMaterilMap);
				popupDetails.then(function (data) {
					let materialData = populateMaterialClassData(data.projectMaterialDtlTOs, 0, []);
					materialData.map((treeItem) => {
						$scope.materialItemClick(treeItem, false);
					});
					$scope.projectMaterialDtlTOs = materialData;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while adding Material Details", "Error");
				})
			} else {
				GenericAlertService.alertMessage("Please select project to add materials", 'Warning');
			}
		},
			$scope.saveMeterials = function () {
				if( $scope.isPrimaveraIntegrationEnabled == 'No' )
				{
					angular.forEach(selectedMeterials, function (value) {					
						if (value.startDate == null || value.finishDate == null) {
							GenericAlertService.alertMessage('Please enter Schedule Start Date and Schedule Finish Date ', 'Warning');
							//forEach.break();
							return;
						}
						var startDate = new Date(value.startDate);
						var finishDate = new Date(value.finishDate);
						if (startDate > finishDate) {
							GenericAlertService.alertMessage('Start Date must be less than Finish Date', 'Warning');
							forEach.break();
							return;
						}
					})
				}		

				var req = {
					"projectMaterialDtlTOs": selectedMeterials,
					"projId": $scope.projId
				}
				blockUI.start();
				ProjectBudgetService.saveProjectMaterials(req)
					.then(
						function (data) {
							blockUI.stop();

							// GenericAlertService.alertMessage('Meterials(s) is/are '+ data.message,data.status);
							GenericAlertService.alertMessage('Materials saved successfully',"Info");
							let materialData = populateMaterialClassData(data.projectMaterialDtlTOs, 0, []);
							materialData.map((treeItem) => {
								$scope.materialItemClick(treeItem, false);
							});
							$scope.projectMaterialDtlTOs = materialData;
							selectedMeterials = [];
						},
						function (error) {
							blockUI.stop();
							GenericAlertService
								.alertMessage(
									'Meterials(s) is/are failed to save',
									"Error");
						});
			},

			$scope.materialRowSelect = function (meterialTO) {
				var found = selectedMeterials.some(function (el) {
					if (el.id === meterialTO.id) {
						el = meterialTO
						return true;
					}
				});

				if (!found) {
					selectedMeterials.push(meterialTO);
				}
			}

		/* Cost Statement Tab */
		$scope.originalDuration = function (tab) {
			var oneDay = 24 * 60 * 60 * 1000;
			var startDate = new Date(tab.startDate);
			var finishDate = new Date(tab.finishDate)
			return (finishDate - startDate) / oneDay;
		}

		$scope.costItemClick = function (item, expand) {
			TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
		}

		$scope.getCostStatementRecords = function () {
			var getCostStatReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectBudgetService.getProjCostStatements(getCostStatReq).then(function (data) {
				console.log(data);				
				let costData = populateCostData(data.projCostStmtDtlTOs, 0, []);
				console.log(costData);
				if( costData != null && costData.length > 0 )
				{
					for(var j=0;j<costData.length;j++)
					{
						if( costData[j].item )
						{
							$scope.itemStatus = costData[j].itemStatus;
						}
					}
				}
				console.log($scope.itemStatus);
				costData.map((treeItem) => {
					$scope.costItemClick(treeItem, false);
				});
				let projCostStmtDtls = costData;
				if (projCostStmtDtls.length > 0 && projCostStmtDtls.find(x => x.item == true).estimateType &&
					projCostStmtDtls.find(x => x.item == true).estimateType.contains('SPI')) {
					ProjectScheduleService.getProjBudgetCostCodeDetails(getCostStatReq).then(function (data1) {
						$scope.projCostStmtDtls = projCostStmtDtls;
						console.log("$scope.projCostStmtDtls",$scope.projCostStmtDtls)
						calculatePlannedValues(data1, $scope.projCostStmtDtls, "costId");
						EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
						console.log("$scope.projCostStmtDtls",$scope.projCostStmtDtls)
						calculateTotal($scope.projCostStmtDtls);
						retrieveCostStatementIds($scope.projCostStmtDtls);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
					});
				} else {
					$scope.projCostStmtDtls = projCostStmtDtls;
					EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
					calculateTotal($scope.projCostStmtDtls);
					console.log("$scope.projCostStmtDtls",$scope.projCostStmtDtls)
					retrieveCostStatementIds($scope.projCostStmtDtls);
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Cost Details", "Error");
			});
		}
		function retrieveCostStatementIds(costStatementData) {
			console.log(costStatementData);
			costIdsData = [];
			costItemIdsData = [];
			if( costStatementData.length != 0 )
			{
				angular.forEach(costStatementData,function(value,key){
					if( value.item )
					{
						costItemIdsData.push(value.costId);
						costIdsData.push(value.id);
					}
					else
					{
						costItemIdsData.push(value.id);	
					}					
				});
			}
		}
		
		function calculateManhoursTotal(projManpowerDetails) {
			console.log("projManpowerDetails", projManpowerDetails);
			let columnTotals = {
				originalHrsTotal: 0,
				revisedHrsTotal:0,
				earnedValueTotal:0,
				actualHrsTotal:0,
				plannedValueTotal:0,
				remainingHrsTotal:0,
				etcTotal:0,
				estAtCompletionHrsTotal:0
			};
			for (const value of projManpowerDetails) {
				var estCompletion = 0;
				columnTotals.originalHrsTotal += value.originalQty;
				if(value.revisedQty == null || value.revisedQty == undefined || value.revisedQty == ""){
					value.revisedQty=0;
				}
				columnTotals.revisedHrsTotal += value.revisedQty;
				columnTotals.actualHrsTotal += value.actualQty;
				if(value.revisedQty > 0) {
					columnTotals.remainingHrsTotal += value.revisedQty - value.actualQty;
				} else {
					columnTotals.remainingHrsTotal += value.originalQty - value.actualQty;
				}
				if(value.estimateType == "Remaining Units") {
					if(value.revisedQty > 0) {
						estCompletion += (value.revisedQty - value.actualQty);
						columnTotals.etcTotal += value.revisedQty - value.actualQty;
					} else {
						estCompletion += (value.originalQty - value.actualQty);
						columnTotals.etcTotal += value.originalQty - value.actualQty;
					}
				}
				if(value.estimateType == "BAC-EV") {
					if(value.revisedQty > 0) {
						estCompletion += (value.revisedQty - value.earnedValue);
						columnTotals.etcTotal += (value.revisedQty - value.earnedValue);
					} else {
						estCompletion += (value.originalQty - value.earnedValue);
						columnTotals.etcTotal += (value.originalQty - value.earnedValue);
					}
				} else if(value.estimateType == "(BAC-EV)/PF") {
					var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
					var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
					var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
					estCompletion += etcBAC_EV_PF;
					columnTotals.etcTotal += etcBAC_EV_PF;
				} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
					var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
					var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
					var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
					console.log("bac_ev " + bac_ev);
					console.log("pf " + pf);
					console.log("spi " + spi);
					var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
					console.log("etcBAC_EV_PF_SPI " + etcBAC_EV_PF_SPI);
					estCompletion += etcBAC_EV_PF_SPI;
					columnTotals.etcTotal += etcBAC_EV_PF_SPI;
				}
				columnTotals.estAtCompletionHrsTotal += value.actualQty + estCompletion;
			}
			$scope.columnTotal = columnTotals;
			console.log($scope.columnTotal);
		}
		
		function calculatePlantTotal(projPlantsDetails) {
			console.log("projPlantsDetails", projPlantsDetails);
			
			for(let projPlant of projPlantsDetails )
			{
				projPlant.actualQty1=projPlant.actualQty + projPlant.estimateComplete;
				console.log(projPlant.total,"DATA");
				projPlantsDetails.push = projPlant.actualQty1;
			}
			$scope.gridOptions1.data = angular.copy(projPlantsDetails);
			let columnTotals = {
				originalHrsTotal: 0,
				revisedHrsTotal:0,
				earnedValueTotal:0,
				actualHrsTotal:0,
				plannedValueTotal:0,
				remainingHrsTotal:0,
				etcTotal:0,
				estAtCompletionHrsTotal:0
			};
			for (const value of projPlantsDetails) {
				var estCompletion = 0;
				columnTotals.originalHrsTotal += value.originalQty;
				if(value.revisedQty == null || value.revisedQty == undefined || value.revisedQty == ""){
					value.revisedQty=0;
				}
				columnTotals.revisedHrsTotal += value.revisedQty;
				columnTotals.actualHrsTotal += value.actualQty;
				if(value.revisedQty > 0) {
					columnTotals.remainingHrsTotal += value.revisedQty - value.actualQty;
				} else {
					columnTotals.remainingHrsTotal += value.originalQty - value.actualQty;
				}
				if(value.estimateType == "Remaining Units") {
					if(value.revisedQty > 0) {
						estCompletion += value.revisedQty - value.actualQty;
						columnTotals.etcTotal += value.revisedQty - value.actualQty;
					} else {
						estCompletion += value.originalQty - value.actualQty;
						columnTotals.etcTotal += value.originalQty - value.actualQty;
					}
				} else if(value.estimateType == "New Estimate") {
					estCompletion += value.estimateComplete;
					columnTotals.etcTotal += value.estimateComplete;
				}
				columnTotals.estAtCompletionHrsTotal += value.actualQty + estCompletion;
				
			}
			$scope.columnTotal = columnTotals;
			console.log($scope.columnTotals);
		}
		
		function calculateTotal(projCostStmtDtls) {
			let columnTotals = {
				originalBudgetTotal: 0,
				revisedBudgetTotal:0,
				earnedValueTotal:0,
				actualCostTotal:0,
				plannedValueTotal:0,
				remainingBudgetTotal:0,
				etcTotal:0,
				estAtCompletionTotal:0
			};
			for (const value of projCostStmtDtls) {
				var actualTotal = (value.actualCostBudget.labourCost
						+value.actualCostBudget.materialCost+value.actualCostBudget.plantCost+value.actualCostBudget.otherCost);
				var estCompletion = 0;
				columnTotals.originalBudgetTotal += value.originalCostBudget.total;
				columnTotals.revisedBudgetTotal += value.revisedCostBudget.total;
				columnTotals.earnedValueTotal += value.earnedValue;
				columnTotals.actualCostTotal += actualTotal;
				if(value.plannedValue == null || value.plannedValue == undefined || value.plannedValue == ""){
					value.plannedValue=0;
				}
				columnTotals.plannedValueTotal += value.plannedValue;
				if(value.revisedCostBudget.total > 0) {
					columnTotals.remainingBudgetTotal += value.revisedCostBudget.total - actualTotal;
				} else {
					columnTotals.remainingBudgetTotal += value.originalCostBudget.total - actualTotal;
				}
				if(value.estimateType == "Remaining Units") {
					if(value.revisedCostBudget.total > 0) {
						estCompletion += value.revisedCostBudget.total - actualTotal;
						columnTotals.etcTotal += value.revisedCostBudget.total - actualTotal;
					} else {
						estCompletion += value.originalCostBudget.total - actualTotal;
						columnTotals.etcTotal += value.originalCostBudget.total - actualTotal;
					}
				} else if(value.estimateType == "BAC-EV") {
					if(value.revisedCostBudget.total > 0) {
						estCompletion += (value.revisedCostBudget.total - value.earnedValue);
						columnTotals.etcTotal += (value.revisedCostBudget.total - value.earnedValue);
					} else {
						estCompletion += (value.originalCostBudget.total - value.earnedValue);
						columnTotals.etcTotal += (value.originalCostBudget.total - value.earnedValue);
					}
				} else if(value.estimateType == "(BAC-EV)/CPI") {
					var bac_ev=((value.revisedCostBudget.total ? value.revisedCostBudget.total : value.originalCostBudget.total) - value.earnedValue);
					var cpi=(value.earnedValue==0 || value.earnedValue==null || actualTotal==0) ? 0 : (value.earnedValue / actualTotal);
					var etcBAC_EV_CPI=(value.earnedValue==0 || value.earnedValue==null || actualTotal==0)  ? bac_ev :
	            		((value.revisedCostBudget.total ? value.revisedCostBudget.total : value.originalCostBudget.total) - value.earnedValue)/cpi;
					estCompletion += etcBAC_EV_CPI;
					columnTotals.etcTotal += etcBAC_EV_CPI;
				} else if(value.estimateType == "(BAC-EV)/(CPI*SPI)") {
					var bac_ev=((value.revisedCostBudget.total ? value.revisedCostBudget.total : value.originalCostBudget.total) - value.earnedValue);
					var cpi=(value.earnedValue==0 || value.earnedValue==null || actualTotal==0) ? 0 : (value.earnedValue / actualTotal);
					var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
					var etcBAC_EV_CPI_SPI=(value.earnedValue==0 || value.earnedValue==null || actualTotal==0 || value.plannedValue==0)  ? bac_ev :
	            		((value.revisedCostBudget.total ? value.revisedCostBudget.total : value.originalCostBudget.total) - value.earnedValue)/(cpi*spi);
					estCompletion += etcBAC_EV_CPI_SPI;
					columnTotals.etcTotal += etcBAC_EV_CPI_SPI;
				} else if(value.estimateType == "New Estimate") {
					estCompletion += value.estimateCompleteBudget.total;
					columnTotals.etcTotal += value.estimateCompleteBudget.total;
				}
				columnTotals.estAtCompletionTotal += actualTotal + estCompletion;
			}
			$scope.columnTotals = columnTotals;
			console.log($scope.columnTotals);
		}
		function populateCostData(data, level, costTOs, isChild, parent) {
		    console.log('VB data');
              console.log(data);

              console.log('level');
              console.log(level);

              console.log('costTOs');
              console.log(costTOs);

               console.log('isChild');
               console.log(isChild);

               console.log('parent');
               console.log(parent);

			return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',isChild, parent)
		}

		$scope.saveCostStmts = function () {

			var saveCostStmtReq = {
				"projCostStmtDtlTOs": TreeService.extractTreeDataForSaving($scope.projCostStmtDtls, 'projCostStmtDtlTOs'),
				"projId": $rootScope.projId
			};
			console.log(saveCostStmtReq);
			blockUI.start();
			ProjectBudgetService.saveCostStatement(saveCostStmtReq).then(function (data) {
				blockUI.stop();
				$scope.getCostStatementRecords();
				// GenericAlertService.alertMessage('Project Cost Statement(s) is/are saved successfully', "Info");
				GenericAlertService.alertMessage('Project Cost Statement saved successfully', "Info");
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Cost Statement(s) is/are failed to save', "Error");
			});
		}
		$scope.calculateDurationDays = function (tabObj) {
			if (tabObj.actualStartDate && tabObj.actualFinishDate) {
				var millsDifference = Math.abs(new Date(tabObj.actualStartDate).getTime() - new Date(tabObj.actualFinishDate).getTime());
				tabObj.totalDurationDays = Math.ceil(millsDifference / (1000 * 3600 * 24));
			}
		};
		var selectedCostCodes = [];
		$scope.selectCostCode = function (tab) {
			if (tab.selected) {
				selectedCostCodes.push(tab);
			} else {
				selectedCostCodes.splice(selectedCostCodes.indexOf(tab), 1);
			}
		}
		$scope.deactivateCostStmt = function () {
			if (selectedCostCodes.length <= 0) {
				GenericAlertService.alertMessage("please select records to Deactivate", 'Warning');
				return;
			}
			var deleteIds = [];
			$scope.nondeleteIds = [];
			if ($scope.selectedAll) {
				$scope.projCostStmtDtls = [];
			} else {
				angular.forEach(selectedCostCodes, function (value, key) {
					deleteIds.push(value.id);
				});
				var deactivateCostCodeReq = {
					"ProjCostStmtsIds": deleteIds,
					"status": 2
				};
				ProjectBudgetService.deactivateCostCodes(deactivateCostCodeReq).then(function (data) {
				});
				angular.forEach(selectedCostCodes, function (value, key) {
					GenericAlertService.alertMessage('Cost Code Status(s) is/are  Deactivated successfully', 'Info');
					$scope.projCostStmtDtls.splice($scope.projCostStmtDtls.indexOf(value), 1);
				}, function (error) {
					GenericAlertService.alertMessage('Cost Code Status(s) is/are failed to Deactivate', "Error");
				});
				selectedCostCodes = [];
				$scope.deleteIds = [];
			}
		}

		/* Progress Measure Tab */

		$scope.getProjMeasureRecords = function () {
			var getProgMeasureReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectBudgetService.getProjProgMeasure(getProgMeasureReq).then(function (data) {
				$scope.projMeasureDetails = data.projProgressTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Progress Measure Details", "Error");
			});
		};

		$scope.saveProgMeasures = function () {
			angular.forEach($scope.projMeasureDetails, function (value) {
				angular.forEach(value.childProjProgressTOs, function (value1) {
					angular.forEach(value1.childProjProgressTOs, function (value2) {
						var fromDate = new Date(value2.fromDate);
						var toDate = new Date(value2.toDate);
						if (fromDate > toDate) {
							GenericAlertService.alertMessage('Start Date must be less than  Finish Date', 'Warning');
							forEach.break();
							return;
						}
					})
				})
			})
			var saveProgMeasureReq = {
				"projProgressTOs": $scope.projMeasureDetails
			};
			blockUI.start();
			ProjectBudgetService.saveProjProgMeasure(saveProgMeasureReq).then(function (data) {
				blockUI.stop();
				$scope.getProjMeasureRecords();
				GenericAlertService.alertMessage('Project Progress Measure(s) is/are ' + data.message, data.status);
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Progress Measure(s) is/are failed to save', "Error");
			});
		}

		/* Project Summary Tab */
		/* Man Power Tab */
		$scope.getMeasureUnitsRecords = function () {
			var getMeasureUnitsReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectBudgetService.getMeasureUnits(getMeasureUnitsReq).then(function (data) {
				$scope.measureunits = data.projManPowerStatusTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting Manpower Units", "Error");
			});
		},
			/* Plant Tab */
			$scope.getPlantUnitsRecords = function () {
				var getPlantUnitsReq = {
					"status": 1,
					"projId": $rootScope.projId
				};
				ProjectBudgetService.getPlantUnits(getPlantUnitsReq).then(function (data) {
					$scope.plantunits = data.projectPlantsStatusTOs;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Plant Units", "Error");
				});
			},
			/* Cost Tab */
			$scope.getCostUnitsRecords = function () {
				var getCostUnitsReq = {
					"status": 1,
					"projId": $rootScope.projId
				};
				ProjectBudgetService.getCostUnits(getCostUnitsReq).then(function (data) {
					$scope.projCostStatementsSummaryTOs = data.projCostStatementsSummaryTOs;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Cost Units", "Error");
				});
			},


			$scope.addCostItemDetails = function () {
				var costCodePopup = [];
				costCodePopup = ProjectSettingCostItemFactory.getCostItemDetails($rootScope.projId);
				costCodePopup.then(function (data) {
					$scope.projCostStmtDtls = data.projCostStmtDtlTOs;
				}, function (error) {
					GenericAlertService.alertMessage("Error osccured while gettting  cost codes", 'Error');
				});
			}

		$scope.calculateBudgetAmount = function (tab) {
			var budgetAmount = tab.projCostBudgetTOs[0];
			tab.projCostBudgetTOs[0].total = budgetAmount.labourCost + budgetAmount.materialCost + budgetAmount.plantCost + budgetAmount.otherCost;
		}

		var originalBudgetTotal = 0;
		$scope.calculateRevisedBudget = function (tab) {
			if (tab.projCostBudgetTOs[1].total == undefined || tab.projCostBudgetTOs[1].total == NaN) {
				tab.projCostBudgetTOs[1].total = 0;
			}
			var revisedBudget = tab.projCostBudgetTOs[1];
			tab.projCostBudgetTOs[1].total = revisedBudget.labourCost +
				revisedBudget.materialCost + revisedBudget.plantCost +
				revisedBudget.otherCost;
		}

		var originalBudgetTotal = 0;
		var revisedBudgetTotal = 0;
		var actualCost = 100000;
		$scope.costPercentage = function (tab) {

			// var actualCost = tab.actualCost;
			var originalBudget = tab.projCostBudgetTOs[0];
			originalBudgetTotal = originalBudget.labourCost + originalBudget.materialCost + originalBudget.plantCost + originalBudget.otherCost;

			var revisedBudget = tab.projCostBudgetTOs[1];
			revisedBudgetTotal = revisedBudget.labourCost + revisedBudget.materialCost + revisedBudget.plantCost + revisedBudget.otherCost;

			if (revisedBudgetTotal != undefined && revisedBudgetTotal > 0) {
				tab.costPercentage = (actualCost / revisedBudgetTotal) * 100;
			} else if (originalBudgetTotal != undefined) {
				tab.costPercentage = (actualCost / originalBudgetTotal) * 100;
			}
		}, $scope.percentageOfWork = function (tab) {
			var earnedValueAmount = tab.earnedValue;
			if (revisedBudgetTotal != undefined && revisedBudgetTotal > 0) {
				tab.percentageOfWork = (earnedValueAmount / revisedBudgetTotal) * 100;
			} else if (originalBudgetTotal != undefined) {
				tab.percentageOfWork = (earnedValueAmount / revisedBudgetTotal) * 100;
				tab.percentageOfWork = (earnedValueAmount / originalBudgetTotal) * 100;
			}
		}, $scope.productivityFactor = function (tab) {
			var earnedAmount = tab.earnedValue;
			// var actualCost = tab.actualCost;
			if (earnedAmount != undefined && earnedAmount > 0) {
				tab.productivityFactor = (earnedAmount / actualCost) * 100;
			}
		}, $scope.actualAmount = function (tab) {
			var workDairy = 0;
			var registers = 0;
			var empWages = 0;
			var timsSheet = 0;
			var projLib = 0;
			var attendance = 0;
			var purchaseOrder = 0;
			tab.projCostBudgetTOs[2].labourCost = ((workDairy.usedValue * registers.ratePerUnit * empWages.wageRate) * (workDairy.idleValue * registers.idleTimeRate * empWages.wageRate) + (timsSheet.timsSheetHrs * timsSheet.wageFactor) + (timsSheet.timeSheetExpenses) + (attendance.attenValue * projLib.paidLeaves) + (registers.mobilisationRate * registers.deMobilisationRate));
			tab.projCostBudgetTOs[2].materialCost = ((workDairy.value * purchaseOrder.purcahseOrderListRate) + purchaseOrder.paidAmount);
			tab.projCostBudgetTOs[2].plantCost = ((workDairy.usedValue * registers.rateWithFuel) + (workDairy.idleValue * registers.plantIdleTime));
			tab.projCostBudgetTOs[2].otherCost = purchaseOrder.paidAmount;
		}, $scope.remainingAmount = function (tab) {
			var actualAmount = tab.projCostBudgetTOs[0];
			var revisedBudget = tab.projCostBudgetTOs[1];
			tab.projCostBudgetTOs[2].labourCost = actualAmount.labourCost - revisedBudget.labourCost;
			tab.projCostBudgetTOs[2].materialCost = actualAmount.materialCost - revisedBudget.materialCost;
			tab.projCostBudgetTOs[2].plantCost = actualAmount.plantCost - revisedBudget.plantCost;
			tab.projCostBudgetTOs[2].otherCost = actualAmount.otherCost - revisedBudget.otherCost;
			tab.projCostBudgetTOs[2].total = tab.projCostBudgetTOs[1].total - tab.projCostBudgetTOs[0].total;
		}, $scope.applyProgsMeasure = function (fromDate, toDate) {

		}, $scope.addSOWItems = function () {
			var sowPopup = [];
			sowPopup = ProjectSettingSOWItemFactory.getSOWItemDetails($rootScope.projId);
			sowPopup.then(function (data) {
				$scope.projMeasureDetails = data.projSOWDtlTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  cost codes", 'Error');
			});
		},
			$scope.checkErr = function (startDate, endDate) {
				$scope.errMessage = '';
				var curDate = new Date();
				if (!(startDate && endDate)) {
					$scope.errMessage = 'Please Select Date';
					return false;
				}
				if (new Date(startDate) >= new Date(endDate)) {
					$scope.errMessage = 'From Date less than To Date';
					return false;
				}
			},
			$scope.validateMaterialSchItemDates = function () {
				angular.forEach($scope.materialClassTOs, function (value) {
					if (value.schStartDate > value.schFinishDate) {
						GenericAlertService.alertMessage('Start Date must be less than Finish Date', 'Warning');
						forEach.break();
						return;
					}
				});
			},
			$scope.calcRemainingUnits = function (budgetObj) {
				if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0) {

					budgetObj.remainingQty = budgetObj.revisedQty - budgetObj.actualQty;
					return budgetObj.remainingQty;
				} else {
					budgetObj.remainingQty = budgetObj.originalQty - budgetObj.actualQty;
					return budgetObj.remainingQty;
				}
			}, $scope.editItem = function () {
				$scope.editing = true;
			}, $scope.itemId = 1;
		$scope.isExpand = true;
		var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	$scope.budgetApproval = function(approvalMode) {
		console.log("submitForApproval function");
		console.log(manPowerIdsData);
		console.log(plantIdsData);
		console.log(materialIdsData);
		console.log(costIdsData);
		console.log(costItemIdsData);
		//add one condition if anyone(manpower,plant,materials,cost) if not given throw error msg
		if(manPowerIdsData.length <= 0){
			GenericAlertService.alertMessage("Please  save manpower details",'Info')
			return;
		}
		if(plantIdsData.length <= 0){
			GenericAlertService.alertMessage('Please save plant details',"Info")
			return;
		}
		if(materialIdsData <= 0){
			GenericAlertService.alertMessage('Please save materials details',"Info")
			return;
		}
		if(costItemIdsData <= 0){
			GenericAlertService.alertMessage('Please save cost details',"Info")
			return;
		}
		ProjectBudgetFactory.budgetApprovalPopup(approvalMode,manPowerIdsData,plantIdsData,materialIdsData,costIdsData,costItemIdsData);
	}
	 var linkCellTemplate ='<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.rowSelectPlant(row.entity)">';
    	$scope.$watch(function () { return stylesService.finishedStyling;}, 
         
           function (newValue, oldValue) {
			if (newValue) {
				let columnDefs =  [
					{ name: 'select', width:'5%', cellClass:"justify-center", headerCellClass:"justify-center",cellTemplate: linkCellTemplate,displayName: "Select", headerTooltip: "Select", groupingShowAggregationMenu: false},
					{ field: 'plantClassTO.code', displayName: "Plant Classification Code", headerTooltip: "Plant Classification Code", groupingShowAggregationMenu: false },
					{ field: 'plantClassTO.name', displayName: "Plant Classification Name", headerTooltip: "Plant Classification Name", groupingShowAggregationMenu: false },
					{ field: 'measureUnitTO.name', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
					{ field: 'originalQty', displayName: "Original Budget Hours", headerTooltip: "Original Budget Hours",
					groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) { 
					aggregation.rendered = aggregation.value;
					}  },
				    { field: 'revisedQty', displayName: "Revised Budget Hours",  headerTooltip: "Revised Budget Hours",cellTemplate:'<div ng-if=row.entity.revisedQty > 0 ? {{row.entity.revisedQty : ""}}></div>',
                    groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
                     customTreeAggregationFinalizerFn: function (aggregation) { 
					 aggregation.rendered = aggregation.value;
					} },
					{ field: 'actualQty', displayName: "Actual Hours to Date", headerTooltip: "Actual Hours to Date",
					cellTemplate:'<span ng-show="(row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) < row.entity.actualQty"><span style="color:red;font-weight:bold;">{{row.entity.actualQty}}</span></span><span ng-show="(row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) > row.entity.actualQty">{{row.entity.actualQty > 0 ? row.entity.actualQty : "" }}</span>',
			         groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
		             customTreeAggregationFinalizerFn: function (aggregation) { 
				     aggregation.rendered = aggregation.value;
					} },
					{ field: 'originalQty', displayName: "Remaining Hours", headerTooltip: "Remaining Hours",
					cellTemplate:'<span ng-init="remQty=(row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty ">{{remQty > 0 ? remQty : "" | number:2}}</span>',
					groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
		             customTreeAggregationFinalizerFn: function (aggregation) { 
			         aggregation.rendered = aggregation.value;
					 } },
					{ field: 'estimateComplete', displayName: "Estimate to Complete", headerTooltip: "Estimate to Complete",
					cellTemplate:'<span ng-if="row.entity.estimateComplete>0">{{row.entity.estimateComplete }}</span>',
					 groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) { 
					aggregation.rendered = aggregation.value;
					} },
					{ name: 'actualQty1', displayName: "Estimation at Completion Hours", headerTooltip: "Estimation at Completion Hours",
					cellTemplate:'<span>{{row.entity.actualQty1}}</span>',
					 groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) { 
					aggregation.rendered = aggregation.value;
					} },
					{ field: 'startDate', displayName: "Start Date", headerTooltip: "Start Date", groupingShowAggregationMenu: false },
					{ field: 'finishDate', displayName: "Finish Date", headerTooltip: "Finish Date", groupingShowAggregationMenu: false },
					{ field: 'minStartDateOfBaseline', displayName: "Baseline Start Date", headerTooltip: "Baseline Start Date", groupingShowAggregationMenu: false },
					{ field: 'maxFinishDateOfBaseline', displayName: "Baseline Finish Date", headerTooltip: "Baseline Finish Date", groupingShowAggregationMenu: false },
					
				]
				
				let data = [];
				$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data,"Projects_Project Budgets_Plant");
				calculatePlantTotal();				
			}
		});
		var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.manPowerRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'empClassTO.code', displayName: "ID", headerTooltip: "ID" ,  groupingShowAggregationMenu: false },
						{ field: 'empClassTO.name', displayName: "Name", headerTooltip: "Name",  groupingShowAggregationMenu: false},
						{ field: 'projEmpCategory', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false},
						{ field: 'measureUnitTO.name', displayName: "Unit of Measure", headerTooltip: "Unit of Measure",  groupingShowAggregationMenu: false},
						{ field: 'originalQty', displayName: "Original Hrs", headerTooltip: "Original Hrs",  groupingShowAggregationMenu: false, cellTemplate:'<span ng-if="row.entity.originalQty>0">{{row.entity.originalQty}}</span>',aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					    customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'revisedQty',  cellTemplate:'<span ng-if="row.entity.revisedQty>0">{{row.entity.revisedQty}}</span>',displayName: "Revised Hrs", headerTooltip: "Revised Hrs",  groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'actualQty',cellTemplate:'<span ng-show="(row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) < row.entity.actualQty"><span style="color:red;font-weight:bold;">{{row.entity.actualQty}}</span></span><span ng-show="(row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) > row.entity.actualQty">	{{row.entity.actualQty > 0 ? row.entity.actualQty : "" }}</span>' ,displayName: "Actual Hrs", headerTooltip : "Actual Hrs", groupingShowAggregationMenu: false ,
						aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'revisedQty1',  displayName: "Remaining Hrs", headerTooltip : "Remaining Hrs" ,  groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ name: 'estimateto',cellFilter:'number:2',displayName: "Estimate to Complete", headerTooltip: "Estimate to Complete",  groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ name: 'estimatecom',cellFilter:'number:2',displayName: "Estimation at Completion Hours", headerTooltip : "Estimation at Completion Hours" ,  groupingShowAggregationMenu: false,aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
						{ field: 'startDate',  displayName: "Start Date", headerTooltip : "Start Date",  groupingShowAggregationMenu: false },
						{ field: 'finishDate',  displayName: "Finish Date", headerTooltip : "Finish Date" ,  groupingShowAggregationMenu: false},
						{ field: 'minStartDateOfBaseline | date',  displayName: "Baseline Start Date", headerTooltip : "Baseline Start Date",  groupingShowAggregationMenu: false },
						{ field: 'maxFinishDateOfBaseline | date',  displayName: "Baseline Finish Date", headerTooltip : "Baseline Finish Date" ,  groupingShowAggregationMenu: false}
						
						];
					let data = [];
					$scope.gridOptions2 = ngGridService.initGrid($scope, columnDefs, data, "Project Budgets Manpower");
					
				}
			});
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/projectshelp/projbudgethelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}

	}]).filter('slice', function () {
		return function (arr, start, end) {
			return (arr || []).slice(start, end);
		}
	}).filter('projCostFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o != undefined && o.projCostStmtDtlTOs && o.projCostStmtDtlTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					newObj.push(o);
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					if (o.expand == true) {
						recursive(o.projCostStmtDtlTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {

						o.leaf = true;
					} else {
						o.leaf = false;
					}
					newObj.push(o);
					return false;
				}
			});
		}
		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	}).filter('sowFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o.childSOWItemTOs && o.childSOWItemTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					newObj.push(o);
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					if (o.expand == true) {
						recursive(o.childSOWItemTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {
						o.leaf = true;
					} else {
						o.leaf = false;
					}
					newObj.push(o);
					return false;
				}
			});
		}

		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	}).filter('sowProgMeasureFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o.childProjProgressTOs && o.childProjProgressTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					newObj.push(o);
					if (o.expand == true) {
						recursive(o.childProjProgressTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {
						if (o.actualQty != undefined) {
							o.actualQty = 0
							if (o.quantity > 0 && o.quantity != undefined && o.quantity != NaN) {
								o.actualQty += parseFloat(o.quantity);
							}
							if (o.revisedQty > 0 && o.revisedQty != undefined && o.revisedQty != NaN) {
								o.actualQty += parseFloat(o.revisedQty);
							}
						}
						o.leaf = true;
						newObj.push(o);
					} else {
						obj.splice(obj.indexOf(o), 1);
						o.leaf = false;
					}
					return false;
				}
			});
		}

		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	});
