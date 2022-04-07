'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("performanceindices", {
		url : '/performanceindices',
		data : {
			dashboards : []
		},
		views : {
			'content@' : {
				'templateUrl' : 'views/dashboards/performance/progressvarianceperformancefilter.html',
				controller : 'ProgressVariancePerformanceController'
			}
		}
	})
}]).controller("ProgressVariancePerformanceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
		"ProjectStatusService", "ProjectSettingsService",'ngGridService','stylesService', function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, UserEPSProjectService, 
				$location,PerformanceDashboardService, EpsProjectMultiSelectFactory, TreeService, EstimateToCompleteService, ProjectScheduleService, 
				ProjectStatusService, ProjectSettingsService,ngGridService, stylesService) {
	
	$scope.showTable=null;
	$scope.stylesSvc = stylesService;
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}
	var performanceList=[];
	var plannedVal=[];
	$scope.projCostStmtDtls = [];
	$scope.projCostValues = [];
	$scope.plannedValuesMap = [];
	
	let task1Completed = false, task2Completed = false, taskPlannedValue = false;
	$scope.getPerformanceDetails = function() {
		task1Completed = false;
		task2Completed = false;
		taskPlannedValue = false;
		
		var req = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		if (req.projIds == null) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		console.log("req",req);
		PerformanceDashboardService.getProjCostStatements(req).then(function (data) {
			//console.log(data.projCostStmtDtlTOs);
			let costData = populateCostData(data.projCostStmtDtlTOs, 0, []);
			costData.map((treeItem) => {
				$scope.costItemClick(treeItem, false);
			});
			let projCostStmtDtls = costData;
			if (projCostStmtDtls.length > 0 && projCostStmtDtls.find(x => x.item == true).estimateType &&
				projCostStmtDtls.find(x => x.item == true).estimateType.contains('SPI')) {
				ProjectScheduleService.getProjBudgetCostCodeDetails(req).then(function (data1) {
					$scope.projCostStmtDtls = projCostStmtDtls;
					calculatePlannedValues(data1, $scope.projCostStmtDtls, "costId");
					EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
					calculateCostTotal($scope.projCostStmtDtls);
					task1Completed = true;
					mergValues($scope.projCostValues, $scope.projProgressStatus);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while gettting Progress, Variance & Performance Indices details", 'Error');
				});
			} else {
				$scope.projCostStmtDtls = projCostStmtDtls;
				//console.log("$scope.projCostStmtDtls", $scope.projCostStmtDtls);
				EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
				calculateCostTotal($scope.projCostStmtDtls);
				task1Completed = true;
				mergValues($scope.projCostValues, $scope.projProgressStatus);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting Cost details", 'Error');
		});
		ProjectStatusService.getProjStatusDates(req).then(function (data) {
			$scope.projProgressStatus = data.projStatusDatesTOs;
			task2Completed = true;
			mergValues($scope.projCostValues, $scope.projProgressStatus);
			//console.log($scope.projProgressStatus);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Progress Status", "Error");
		});
		ProjectSettingsService.getProjPerformenceThreshold(req).then(function (data) {
			$scope.performenceThresholdData = data.projPerformenceThresholdTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
		});
		ProjectSettingsService.getProjPlannedValue(req).then(function (data) {
			$scope.plannedValuesMap = data.projPlannedValueTO;
			taskPlannedValue = true;
			mergValues($scope.projCostValues, $scope.projProgressStatus);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Planned Value", "Error");
		});
	}
	
	
	function mergValues(projCostValues, projProgressStatus) {
		
		var thresholdData = $scope.performenceThresholdData;
		if (task1Completed && task2Completed && taskPlannedValue) {
			console.log("projCostValues", projCostValues);
			console.log("projProgressStatus", projProgressStatus);
			console.log("$scope.performenceThresholdData", $scope.performenceThresholdData);
			$scope.progressVarienceData = new Array();
			for (const index in projCostValues) {
				for (const index1 in projProgressStatus) {
					if (projProgressStatus[index1].projId == projCostValues[index].projId) {
						projCostValues[index].currentPhase = projProgressStatus[index1].currentPhase;
					}
				}
				projCostValues[index].epsName=$scope.searchProject.parentName.split(',')[index];
				projCostValues[index].projName=$scope.searchProject.projName.split(',')[index];
				$scope.progressVarienceData.push(projCostValues[index]);
			}
			//console.log("progressVarienceData", progressVarienceData);
			var progVarienceData = $scope.progressVarienceData;
			console.log("progVarienceData", progVarienceData);
			$scope.progVarienceDataInfo = new Array();
			for (const index2 in progVarienceData) {
				for (const index3 in thresholdData) {
					console.log("thresholdData[index3].projId", thresholdData[index3].projId);
					var cvLowLimit;
					var cvLowLimit1;
					var cvUpLimit;
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Exceptional") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].exTcpiLowerLimit = thresholdData[index3].tcpiLowerLimit;
						progVarienceData[index2].exTcpiUpperLimit = thresholdData[index3].tcpiUpperLimit;
						progVarienceData[index2].excpiLowerLimit = thresholdData[index3].cpiLowerLimit;
						progVarienceData[index2].excpiUpperLimit = thresholdData[index3].cpiUpperLimit;
						progVarienceData[index2].excvLowerLimit = (thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].excvUpperLimit = thresholdData[index3].cvUpperLimit;
						progVarienceData[index2].exspiLowerLimit = (thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].exspiUpperLimit = thresholdData[index3].spiUpperLimit;
						progVarienceData[index2].exsvLowerLimit = (thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].exsvUpperLimit = thresholdData[index3].svUpperLimit;
						console.log("Available");
					} 
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Acceptable") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].acTcpiLowerLimit = thresholdData[index3].tcpiLowerLimit;
						progVarienceData[index2].acTcpiUpperLimit = thresholdData[index3].tcpiUpperLimit;
						progVarienceData[index2].accpiLowerLimit = thresholdData[index3].cpiLowerLimit;
						progVarienceData[index2].accpiUpperLimit = thresholdData[index3].cpiUpperLimit;
						progVarienceData[index2].accvLowerLimit = (thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].accvUpperLimit = (thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].acspiLowerLimit = (thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].acspiUpperLimit = (thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].acsvLowerLimit = (thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].acsvUpperLimit = (thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, "");
						console.log("Available");
					}
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Warning") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].waTcpiLowerLimit = thresholdData[index3].tcpiLowerLimit;
						progVarienceData[index2].waTcpiUpperLimit = thresholdData[index3].tcpiUpperLimit;
						progVarienceData[index2].wacpiLowerLimit = thresholdData[index3].cpiLowerLimit;
						progVarienceData[index2].wacpiUpperLimit = thresholdData[index3].cpiUpperLimit;
						progVarienceData[index2].wacvLowerLimit = (thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].wacvUpperLimit = (thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].waspiLowerLimit = (thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].waspiUpperLimit = (thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].wasvLowerLimit = (thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].wasvUpperLimit = (thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, "");
						console.log("Available");
					}
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Critical") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].crTcpiLowerLimit = thresholdData[index3].tcpiLowerLimit;
						progVarienceData[index2].crTcpiUpperLimit = thresholdData[index3].tcpiUpperLimit;
						progVarienceData[index2].crcpiLowerLimit = thresholdData[index3].cpiLowerLimit;
						progVarienceData[index2].crcpiUpperLimit = thresholdData[index3].cpiUpperLimit;
						progVarienceData[index2].crcvLowerLimit = (thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].crcvUpperLimit = (thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].crspiLowerLimit = (thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].crspiUpperLimit = (thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, "");
						progVarienceData[index2].crsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crsvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
						console.log("Available");
					}
				}
				$scope.progVarienceDataInfo.push(progVarienceData[index2]);
			}
			console.log("TEST");
			
			console.log("$scope.progressVarienceData", $scope.progressVarienceData);
			console.log("$scope.progVarienceDataInfo", $scope.progVarienceDataInfo);
			console.log("$scope.plannedValuesMap", $scope.plannedValuesMap);
			
			mergePlannedValue($scope.progVarienceDataInfo, $scope.plannedValuesMap);
		}
	}
	
	function mergePlannedValue(progVarienceDataInfo, plannedValuesMap) { 
		console.log("MERGING PLANNED VALUE");
		$scope.progVarPerIdxData = new Array();
		for (const index_1 in progVarienceDataInfo) {
			for (const index_2 in plannedValuesMap) {
				if(plannedValuesMap[index_2].projId == progVarienceDataInfo[index_1].projId) {
					progVarienceDataInfo[index_1].plannedCost = plannedValuesMap[index_2].cost;
				}
			}
			progVarienceDataInfo[index_1].schedulePerformanceIndex = progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].plannedCost;
			//progVarienceDataInfo[index_1].schedulePerformanceIndexPercentage=SFI= (EV-PV)/PV*100%
			progVarienceDataInfo[index_1].scheduleVarienceCost = progVarienceDataInfo[index_1].earnedValueTotal - progVarienceDataInfo[index_1].plannedCost;
			console.log("SCHEDULE VARIENCE", progVarienceDataInfo[index_1].scheduleVarienceCost);
			console.log("SV LOWER LIMIT", progVarienceDataInfo[index_1].crsvLowerLimit);
			
			if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].exsvLowerLimit
					&& progVarienceDataInfo[index_1].exsvUpperLimit == 'Infinite') {
				console.log("SHOW EXCEPTIONAL SV VALUE");
				progVarienceDataInfo[index_1].svExceptional = true;
			}
			if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].acsvLowerLimit
					&& progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].acsvUpperLimit) {
				console.log("SHOW ACCEPTABLE SV VALUE");
				progVarienceDataInfo[index_1].svAcceptable = true;
			}
			if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].wasvLowerLimit
					&& progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].wasvUpperLimit) {
				console.log("SHOW WARNING SV VALUE");
				progVarienceDataInfo[index_1].svWarning = true;
			}
			if(progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].crsvLowerLimit) {
				console.log("SHOW CRITICAL SV VALUE");
				progVarienceDataInfo[index_1].svCritical = true;
			}
			
			progVarienceDataInfo[index_1].schedulePerformanceIndexPercentage = (progVarienceDataInfo[index_1].schedulePerformanceIndex / progVarienceDataInfo[index_1].plannedCost)*100;
			$scope.progVarPerIdxData.push(progVarienceDataInfo[index_1]);
		}
		
		
		for(var pvpi of $scope.progVarienceDataInfo){
			
			//Schedulevariance
			if(pvpi.svExceptional == true){
				pvpi.Schedulevariance=pvpi.scheduleVarienceCost.toFixed(2)+" Ahead of  Schedule";
			}
			else if(pvpi.svAcceptable == true){
				pvpi.Schedulevariance=pvpi.scheduleVarienceCost.toFixed(2)+" Ahead of Schedule";
			}
			else if(pvpi.svWarning == true){
				pvpi.Schedulevariance=pvpi.scheduleVarienceCost.toFixed(2)+" Behind Schedule";
			}
			else if(pvpi.svCritical == true){
				pvpi.Schedulevariance=pvpi.scheduleVarienceCost.toFixed(2)+" Behind Schedule";
			}
			//costVarience1
			if(pvpi.costVarience > pvpi.excvLowerLimit && pvpi.excvUpperLimit == 'Infinite'){
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
				pvpi.CostVariance1=pvpi.costVarience.toFixed(2)+" Under Budget";
				
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
			}
			 if(pvpi.costVarience > pvpi.wacvLowerLimit && pvpi.costVarience <= pvpi.wacvUpperLimit){
				
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
				
				pvpi.CostVariance1+=pvpi.costVarience.toFixed(2) +" Over Budget";
				
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
			}
			 if(pvpi.costVarience < pvpi.accvLowerLimit && pvpi.costVarience <= pvpi.accvUpperLimit){
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
				pvpi.CostVariance1+=pvpi.costVarience.toFixed(2) +" Under Budget";
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
			}
			 if(pvpi.costVarience < pvpi.crcvLowerLimit && pvpi.costVarience <= pvpi.crcvUpperLimit){
				if(pvpi.CostVariance1 == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
				pvpi.CostVariance1+=pvpi.costVarience.toFixed(2) +" Over Budget";
				if(pvpi.costVarience == undefined || pvpi.CostVariance1 == null || pvpi.CostVariance1 == NaN){
					pvpi.CostVariance1="";
				}
			}
			//Shedule perfomance index
			if(pvpi.schedulePerformanceIndex > pvpi.exspiLowerLimit && pvpi.exspiUpperLimit == 'Infinite'){
				pvpi.ShedulepIndex=pvpi.schedulePerformanceIndex.toFixed(2)+" Project in Acceleration";
			}
			if(pvpi.schedulePerformanceIndex > pvpi.waspiLowerLimit && pvpi.schedulePerformanceIndex <= pvpi.waspiUpperLimit){
				pvpi.ShedulepIndex=pvpi.schedulePerformanceIndex.toFixed(2) +"Project is Delayed";
			}
			if(pvpi.schedulePerformanceIndex > pvpi.acspiLowerLimit && pvpi.schedulePerformanceIndex <= pvpi.acspiUpperLimit ){
				pvpi.ShedulepIndex=pvpi.schedulePerformanceIndex.toFixed(2) +" On Schedule";
			}
			if(pvpi.schedulePerformanceIndex < pvpi.crspiLowerLimit || pvpi.schedulePerformanceIndex == pvpi.crspiUpperLimit){
				pvpi.ShedulepIndex=pvpi.schedulePerformanceIndex.toFixed(2) +" Project is Delayed";
			}
			//Cost perfomance index
			if(pvpi.costPerformanceIndex > (pvpi.excpiLowerLimit + '').replace('>', '') && pvpi.excpiUpperLimit == 'Infinite'){
				pvpi.CostPIndex=pvpi.costPerformanceIndex.toFixed(2) +"Project in Acceleration";
			}
			if(pvpi.costPerformanceIndex > (pvpi.wacpiLowerLimit + '').replace('>', '') && pvpi.costPerformanceIndex <= pvpi.wacpiUpperLimit){
				pvpi.CostPIndex=pvpi.costPerformanceIndex.toFixed(2) +" Project is Delayed";
			}
			if(pvpi.costPerformanceIndex > (pvpi.accpiLowerLimit + '').replace('>', '') && pvpi.costPerformanceIndex <= pvpi.accpiUpperLimit){
				pvpi.CostPIndex=pvpi.costPerformanceIndex.toFixed(2) +" Project in Acceleration";
			}
			if(pvpi.costPerformanceIndex < (pvpi.crcpiLowerLimit + '').replace('<', '') && pvpi.costPerformanceIndex == pvpi.crcpiUpperLimit){
				pvpi.CostPIndex=pvpi.costPerformanceIndex.toFixed(2) +"Project is Delayed";
			}
			//perfomance index
			if(pvpi.toCompleteCostPerformanceIndex >= pvpi.exTcpiUpperLimit && pvpi.toCompleteCostPerformanceIndex < (pvpi.exTcpiLowerLimit + '').replace('<', '')){
				pvpi.PerfomanceIndex=pvpi.toCompleteCostPerformanceIndex.toFixed(2)  +" No need for  budget revision";
			}
			if(pvpi.toCompleteCostPerformanceIndex >= pvpi.waTcpiUpperLimit && pvpi.toCompleteCostPerformanceIndex < (pvpi.waTcpiLowerLimit + '').replace('<', '') ){
				pvpi.PerfomanceIndex=pvpi.toCompleteCostPerformanceIndex.toFixed(2) +"Need for  budget revision";
			}
			if(pvpi.toCompleteCostPerformanceIndex >= pvpi.acTcpiUpperLimit && pvpi.toCompleteCostPerformanceIndex < (pvpi.acTcpiLowerLimit + '').replace('<', '')){
				pvpi.PerfomanceIndex=pvpi.toCompleteCostPerformanceIndex.toFixed(2)+"No need for budget revision";
			}
			if(pvpi.toCompleteCostPerformanceIndex >= pvpi.crTcpiUpperLimit && pvpi.toCompleteCostPerformanceIndex >= (pvpi.crTcpiLowerLimit + '').replace('>', '')){
				pvpi.PerfomanceIndex=pvpi.toCompleteCostPerformanceIndex.toFixed(2) +"Need for  budget revision";
			}
			pvpi.crTcpiLowerLimit1=pvpi.crTcpiLowerLimit.slice(1);
			pvpi.acTcpiLowerLimit1=pvpi.acTcpiLowerLimit.slice(1);
			pvpi.waTcpiLowerLimit1=pvpi.waTcpiLowerLimit.slice(1);
			pvpi.exTcpiLowerLimit1=pvpi.exTcpiLowerLimit.slice(1);
		}
		console.log($scope.progVarienceDataInfo,1111111111111)
		$scope.gridOptions.data = angular.copy($scope.progVarienceDataInfo);
		console.log("FULL", $scope.progVarPerIdxData);
	}
	$scope.costItemClick = function (item, expand) {
		TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
	}
	
	function populateCostData(data, level, costTOs, isChild, parent) {
		return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',isChild, parent)
	}
	
	function calculateCostTotal(projCostStmtDtls) {
		let projCostValues = [];
		for (const costValue of projCostStmtDtls) {
			let costFound = false;
			for (let i=0; i < projCostValues.length; i++) {
				//projCostValues[i].estCompletion = 0;
				var actualCostTotal1 = (costValue.actualCostBudget.labourCost
						+costValue.actualCostBudget.materialCost+costValue.actualCostBudget.plantCost+costValue.actualCostBudget.otherCost);
				if (projCostValues[i].projId == costValue.projId) {
					costFound = true;
					
					projCostValues[i].projId = costValue.projId;
					projCostValues[i].epsName = $scope.searchProject.parentName;
					
					projCostValues[i].originalBudgetTotal += costValue.originalCostBudget.total;
					projCostValues[i].revisedBudgetTotal += costValue.revisedCostBudget.total;
					projCostValues[i].actualCostTotal += actualCostTotal1;
					projCostValues[i].earnedValueTotal += costValue.earnedValue;
					if(costValue.revisedQty > 0) {
						projCostValues[i].remainingBudgetTotal += costValue.revisedCostBudget.total - actualCostTotal1;
					} else {
						projCostValues[i].remainingBudgetTotal += costValue.originalCostBudget.total - actualCostTotal1;
					}
					if(costValue.estimateType == "Remaining Units") {
						if(costValue.revisedCostBudget.total > 0) {
							projCostValues[i].estCompletion += costValue.revisedCostBudget.total - actualCostTotal1;
							projCostValues[i].etcTotal += costValue.revisedCostBudget.total - actualCostTotal1;
						} else {
							projCostValues[i].estCompletion += costValue.originalCostBudget.total - actualCostTotal1;
							projCostValues[i].etcTotal += costValue.originalCostBudget.total - actualCostTotal1;
						}
					} else if(costValue.estimateType == "BAC-EV") {
						if(costValue.revisedCostBudget.total > 0) {
							projCostValues[i].estCompletion += (costValue.revisedCostBudget.total - costValue.earnedValue);
							projCostValues[i].etcTotal += (costValue.revisedCostBudget.total - costValue.earnedValue);
						} else {
							projCostValues[i].estCompletion += (costValue.originalCostBudget.total - costValue.earnedValue);
							projCostValues[i].etcTotal += (costValue.originalCostBudget.total - costValue.earnedValue);
						}
					} else if(costValue.estimateType == "(BAC-EV)/CPI") {
						var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
						var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
						var etcBAC_EV_CPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0)  ? bac_ev :
		            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/cpi;
						projCostValues[i].estCompletion += etcBAC_EV_CPI;
						projCostValues[i].etcTotal += etcBAC_EV_CPI;
					} else if(costValue.estimateType == "(BAC-EV)/(CPI*SPI)") {
						var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
						var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
						var spi=(costValue.earnedValue==0 || costValue.earnedValue==null || costValue.plannedValue==0) ? 0 : (costValue.earnedValue / costValue.plannedValue);
						var etcBAC_EV_CPI_SPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0 || costValue.plannedValue==0)  ? bac_ev :
		            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/(cpi*spi);
						projCostValues[i].estCompletion += etcBAC_EV_CPI_SPI;
						projCostValues[i].etcTotal += etcBAC_EV_CPI_SPI;
					} else if(costValue.estimateType == "New Estimate") {
						projCostValues[i].estCompletion += costValue.estimateCompleteBudget.total;
						projCostValues[i].etcTotal += costValue.estimateCompleteBudget.total;
					}
					projCostValues[i].estAtCompletionTotal = projCostValues[i].actualCostTotal + projCostValues[i].etcTotal;
					console.log("EAC", projCostValues[i].estAtCompletionTotal);
					console.log("EV", projCostValues[i].earnedValueTotal);
					console.log("EAC", projCostValues[i].actualCostTotal);
					projCostValues[i].toCompleteCostPerformanceIndex = (projCostValues[i].estAtCompletionTotal - projCostValues[i].earnedValueTotal) / (projCostValues[i].estAtCompletionTotal - projCostValues[i].actualCostTotal);
					if (projCostValues[i].revisedBudgetTotal > 0 ) {
						projCostValues[i].progressOfWorkPercentage = (projCostValues[i].earnedValueTotal / projCostValues[i].revisedBudgetTotal)*100;
					} else {
						projCostValues[i].progressOfWorkPercentage = (projCostValues[i].earnedValueTotal / projCostValues[i].originalBudgetTotal)*100;
					}
					projCostValues[i].costVarience = (projCostValues[i].earnedValueTotal - projCostValues[i].actualCostTotal);
					projCostValues[i].costPerformanceIndex = (projCostValues[i].earnedValueTotal / projCostValues[i].actualCostTotal);
					projCostValues[i].costVariencePercentage = (projCostValues[i].costVarience / projCostValues[i].earnedValueTotal) * 100;
					
				}
			}
			if (!costFound) {
				let etctotal1 = 0;
				let estCompletion1 = 0;
				let remainingCostTotal = 0;
				
				var actualCostTotal1 = (costValue.actualCostBudget.labourCost
						+costValue.actualCostBudget.materialCost+costValue.actualCostBudget.plantCost+costValue.actualCostBudget.otherCost);
				if(costValue.revisedQty > 0) {
					remainingCostTotal += costValue.revisedCostBudget.total - actualCostTotal1;
				} else {
					remainingCostTotal += costValue.originalCostBudget.total - actualCostTotal1;
				}
				if(costValue.estimateType == "Remaining Units") {
					if(costValue.revisedCostBudget.total > 0) {
						estCompletion1 += costValue.revisedCostBudget.total - actualCostTotal1;
						etctotal1 += costValue.revisedCostBudget.total - actualCostTotal1;
					} else {
						estCompletion1 += costValue.originalCostBudget.total - actualCostTotal1;
						etctotal1 += costValue.originalCostBudget.total - actualCostTotal1;
					}
				} else if(costValue.estimateType == "BAC-EV") {
					if(costValue.revisedCostBudget.total > 0) {
						estCompletion1 += (costValue.revisedCostBudget.total - costValue.earnedValue);
						etctotal1 += (costValue.revisedCostBudget.total - costValue.earnedValue);
					} else {
						estCompletion1 += (costValue.originalCostBudget.total - costValue.earnedValue);
						etctotal1 += (costValue.originalCostBudget.total - costValue.earnedValue);
					}
				} else if(costValue.estimateType == "(BAC-EV)/CPI") {
					var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
					var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
					var etcBAC_EV_CPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0)  ? bac_ev :
	            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/cpi;
					estCompletion1 += etcBAC_EV_CPI;
					etctotal1 += etcBAC_EV_CPI;
				} else if(costValue.estimateType == "(BAC-EV)/(CPI*SPI)") {
					var bac_ev=((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue);
					var cpi=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0) ? 0 : (costValue.earnedValue / actualCostTotal1);
					var spi=(costValue.earnedValue==0 || costValue.earnedValue==null || costValue.plannedValue==0) ? 0 : (costValue.earnedValue / costValue.plannedValue);
					var etcBAC_EV_CPI_SPI=(costValue.earnedValue==0 || costValue.earnedValue==null || actualCostTotal1==0 || costValue.plannedValue==0)  ? bac_ev :
	            		((costValue.revisedCostBudget.total ? costValue.revisedCostBudget.total : costValue.originalCostBudget.total) - costValue.earnedValue)/(cpi*spi);
					estCompletion1 += etcBAC_EV_CPI_SPI;
					etctotal1 += etcBAC_EV_CPI_SPI;
				} else if(costValue.estimateType == "New Estimate") {
					estCompletion1 += costValue.estimateCompleteBudget.total;
					etctotal1 += costValue.estimateCompleteBudget.total;
				}
				projCostValues.push({
					"projId": costValue.projId,
					"epsName": $scope.searchProject.parentName, 
					"projName": $scope.searchProject.projName, 
					originalBudgetTotal: costValue.originalCostBudget.total,
					revisedBudgetTotal: costValue.revisedCostBudget.total,
					earnedValueTotal: costValue.earnedValue,
					actualCostTotal: actualCostTotal1,
					plannedValueTotal:0,
					estCompletion: estCompletion1,
					remainingBudgetTotal:remainingCostTotal,
					etcTotal:etctotal1,
					estAtCompletionTotal:0,
					toCompleteCostPerformanceIndex:0,
					progressOfWorkPercentage:0,
					costVarience:0,
					costPerformanceIndex:0,
					costVariencePercentage:0,
					scheduleVarience:0,
					scheduleVariencePercentage:0
				});
			}
		}
		$scope.projCostValues = projCostValues;
		console.log("$scope.projCostValues",$scope.projCostValues);
	}
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'epsName', width: '6%', displayName: "EPS Name", headerTooltip: "EPS Name" },
				{ field: 'projName', width: '11%', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'currentPhase', width: '6%', displayName: "Project Status", headerTooltip: "Project Status" },
	
				{
					field: 'progressOfWorkPercentage', cellFilter:'number:2',width: '6%', displayName: "Progress %", headerTooltip: "Progress %",
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:center;">{{ row.entity.progressOfWorkPercentage | number:2 }}</div>'
				},
	
				{
					field: 'Schedulevariance', width: '12%', displayName: "Schedule Variance (Cost)", headerTooltip: "Schedule Variance (Cost)",
					cellTemplate: '<span ng-if="row.entity.svExceptional == true"><i class="fa fa-star" style="color:blue;"></i> {{row.entity.scheduleVarienceCost | number: 2}} Ahead of Schedule </span>'
						+ '<span ng-if="row.entity.svAcceptable == true"><i class="fa fa-square" style="color:green;"></i> {{row.entity.scheduleVarienceCost | number: 2}} Ahead of Schedule </span>' +
						'<span ng-if="row.entity.svWarning == true"><i class="fa fa-exclamation-triangle" style="color:blue;"></i> {{row.entity.scheduleVarienceCost | number: 2}} Behind Schedule </span>' +
						'<span ng-if="row.entity.svCritical == true"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.scheduleVarienceCost | number: 2}} Behind Schedule </span>'
				},
	
				{
					field: 'CostVariance1', width: '19%', displayName: "Cost Variance (Cost)", headerTooltip: "Cost Variance (Cost)",
					cellTemplate: '<span ng-if="row.entity.costVarience > row.entity.excvLowerLimit && excvUpperLimit == Infinite"> <i class="fa fa-star" style="color:blue;"></i> {{row.entity.costVarience | number: 2}} Under Budget</span>'
						+ '<span ng-if="row.entity.costVarience > row.entity.wacvLowerLimit && row.entity.costVarience <= row.entity.wacvUpperLimit"> <i class="fa fa-exclamation-triangle" style="color:green;"></i> {{row.entity.costVarience | number: 2}} Over Budget</span>' +
						'<span ng-if="row.entity.costVarience < row.entity.accvLowerLimit && row.entity.costVarience <= row.entity.accvUpperLimit"> <i class="fa fa-square" style="color:green;"></i> {{row.entity.costVarience | number: 2}} Under Budget</span>' +
						'<span ng-if="row.entity.costVarience < row.entity.crcvLowerLimit && row.entity.costVarience <= row.entity.crcvUpperLimit"> <img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.costVarience | number: 2}} Over Budget</span>'
				},
	
				{
					field: 'ShedulepIndex', width: '12%', displayName: "Schedule Performance Index (SPI)", headerTooltip: "Schedule Performance Index (SPI)",
					cellTemplate: '<span ng-if="row.entity.schedulePerformanceIndex > row.entity.exspiLowerLimit && exspiUpperLimit == Infinite"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.schedulePerformanceIndex | number: 2}} Project in Acceleration </span>'
						+ '<span ng-if="row.entity.schedulePerformanceIndex > row.entity.waspiLowerLimit && row.entity.schedulePerformanceIndex <= row.entity.waspiUpperLimit"> <i class="fa fa-exclamation-triangle" style="color:green"></i> {{row.entity.schedulePerformanceIndex | number: 2}} Project is Delayed </span>' +
						'<span ng-if="row.entity.schedulePerformanceIndex > row.entity.acspiLowerLimit && row.entity.schedulePerformanceIndex <= row.entity.accvUpperLimit"> <i class="fa fa-square" style="color:green;"></i>  {{row.entity.schedulePerformanceIndex | number: 2}} Under Budget </span>' +
						'<span ng-if="row.entity.schedulePerformanceIndex < row.entity.crspiLowerLimit || row.entity.schedulePerformanceIndex == row.entity.crspiUpperLimit"> <img src="images/critical.png" style="padding-left:5px !important;">  {{row.entity.schedulePerformanceIndex | number: 2}} Project is Delayed </span>'
				},
				{
					field: 'CostPIndex', width: '12%', displayName: "Cost Performance Index (CPI)", headerTooltip: "Cost Performance Index (CPI)",
					cellTemplate: '<span ng-if="row.entity.costPerformanceIndex > row.entity.excpiLowerLimit && row.entity.exspiUpperLimit == Infinite"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.costPerformanceIndex | number: 2}} Project in Acceleration </span>'
						+ '<span ng-if="row.entity.costPerformanceIndex > row.entity.waspiLowerLimit && row.entity.costPerformanceIndex <= row.entity.waspiUpperLimit"> <i class="fa fa-exclamation-triangle" style="color:green"></i> {{row.entity.costPerformanceIndex | number: 2}} Project is Delayed </span>' +
						'<span ng-if="row.entity.costPerformanceIndex > row.entity.acspiLowerLimit && row.entity.costPerformanceIndex <= row.entity.accvUpperLimit"> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.costPerformanceIndex | number: 2}} Project in Acceleration </span>' +
						'<span ng-if="row.entity.costPerformanceIndex < row.entity.crspiLowerLimit && row.entity.costPerformanceIndex == row.entity.crspiUpperLimit"> <img src="images/critical.png" style="padding-left:5px !important;">  {{row.entity.costPerformanceIndex | number: 2}} Project is Delayed </span>'
				},
					
				{
	                field: 'PerfomanceIndex', width: '15%', displayName: "To Complete Performance Index (TCPI)", headerTooltip: "To Complete Performance Index (TCPI)",
					cellTemplate: '<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.exTcpiUpperLimit &&  row.entity.toCompleteCostPerformanceIndex <  row.entity.exTcpiLowerLimit1 "> <i class="fa fa-star" style="color:blue;"></i>  {{row.entity.toCompleteCostPerformanceIndex | number: 2}} No need for budget revision</span>'
						+ '<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.waTcpiUpperLimit && row.entity.toCompleteCostPerformanceIndex < row.entity.waTcpiLowerLimit1"> <i class="fa fa-exclamation-triangle" style="color:green"></i>  {{row.entity.toCompleteCostPerformanceIndex | number: 2}} Need for budget revision </span>' +
						'<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.acTcpiUpperLimit && row.entity.toCompleteCostPerformanceIndex < row.entity.acTcpiLowerLimit1"> <i class="fa fa-square" style="color:green;"></i> {{row.entity.toCompleteCostPerformanceIndex | number: 2}} No need for budget revision </span>' +
						'<span ng-if="row.entity.toCompleteCostPerformanceIndex >= row.entity.crTcpiUpperLimit && row.entity.toCompleteCostPerformanceIndex >= row.entity.crTcpiLowerLimit1"> <img src="images/critical.png" style="padding-left:5px !important;">  {{row.entity.toCompleteCostPerformanceIndex | number: 2}} Need for budget revision </span>'
				},
	
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Cost_progressVarienceData");
		}
	});
	
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

}])