'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("currentdatesandprogresspercentage", {
		url : '/currentdatesandprogresspercentage',
		data : {
			dashboards : []
		},
		views : {
			'content@' : {
				'templateUrl' : 'views/dashboards/time/currentdatesandprogresspercentagefilter.html',
				controller : 'CurrentDatesProgressPercentageController'
			}
		}
	})
}]).controller("CurrentDatesProgressPercentageController", ["$rootScope", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
	"ProjectStatusService", "ProjectSettingsService", function($rootScope, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $scope, $q, $state, ngDialog, GenericAlertService, UserEPSProjectService, 
			$location,PerformanceDashboardService, EpsProjectMultiSelectFactory, TreeService, EstimateToCompleteService, ProjectScheduleService, 
			ProjectStatusService, ProjectSettingsService) {
				$scope.stylesSvc = stylesService;
	$scope.showTable=null;
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
	
	let task1Completed = false, task2Completed = false, taskPlannedValue = false, taskFull = false;
	$scope.getCostPercentageDetails = function() {
		task1Completed = false;
		task2Completed = false;
		taskPlannedValue = false;
		taskFull = false;
		var req = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		if (req.projIds == null) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
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
			console.log("$scope.plannedValuesMap",$scope.plannedValuesMap);
			taskPlannedValue = true;
			mergValues($scope.projCostValues, $scope.projProgressStatus);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Planned Value", "Error");
		});
	}
	
	
	function mergValues(projCostValues, projProgressStatus) {
		console.log(projProgressStatus);
		var thresholdData = $scope.performenceThresholdData;
		console.log("task1Completed", task1Completed)
		console.log("task2Completed", task2Completed)
		console.log("taskPlannedValue", taskPlannedValue)
		if (task1Completed && task2Completed && taskPlannedValue) {
			$scope.progressVarienceData = new Array();
			for (const index in projCostValues) {
				for (const index1 in projProgressStatus) {
					if (projProgressStatus[index1].projId == projCostValues[index].projId) {
						projCostValues[index].currentPhase = projProgressStatus[index1].currentPhase;
						projCostValues[index].actualStartDate = projProgressStatus[index1].startDate;
						projCostValues[index].actualFinishDate = projProgressStatus[index1].finishDate;
						projCostValues[index].baselineStartDate = projProgressStatus[index1].scheduleStartDate;
						projCostValues[index].baselineFinishDate = projProgressStatus[index1].scheduleFinishDate;
						projCostValues[index].forecastStartDate = projProgressStatus[index1].forecastStartDate;
						projCostValues[index].forecastFinishDate = projProgressStatus[index1].forecastFinishDate;
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
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Exceptional") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].exTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].exTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].excpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].excpiUpperLimit = thresholdData[index3].cpiUpperLimit;
						progVarienceData[index2].excvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].excvUpperLimit = thresholdData[index3].cvUpperLimit;
						progVarienceData[index2].exspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].exspiUpperLimit = thresholdData[index3].spiUpperLimit;
						progVarienceData[index2].exsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].exsvUpperLimit = thresholdData[index3].svUpperLimit;
					} 
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Acceptable") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].acTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accpiUpperLimit = parseFloat((thresholdData[index3].cpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].accvUpperLimit = parseFloat((thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acspiUpperLimit = parseFloat((thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].acsvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
					}
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Warning") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].waTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].waTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacpiUpperLimit = parseFloat((thresholdData[index3].cpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wacvUpperLimit = parseFloat((thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].waspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].waspiUpperLimit = parseFloat((thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wasvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].wasvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
					}
					if(thresholdData[index3].projId == progVarienceData[index2].projId && thresholdData[index3].category == "Critical") {
						progVarienceData[index2].category = thresholdData[index3].category;
						progVarienceData[index2].crTcpiLowerLimit = parseFloat((thresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crTcpiUpperLimit = parseFloat((thresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcpiLowerLimit = parseFloat((thresholdData[index3].cpiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcpiUpperLimit = parseFloat((thresholdData[index3].cpiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcvLowerLimit = parseFloat((thresholdData[index3].cvLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crcvUpperLimit = parseFloat((thresholdData[index3].cvUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crspiLowerLimit = parseFloat((thresholdData[index3].spiLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crspiUpperLimit = parseFloat((thresholdData[index3].spiUpperLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crsvLowerLimit = parseFloat((thresholdData[index3].svLowerLimit).replace(/[^0-9-.]/g, ""));
						progVarienceData[index2].crsvUpperLimit = parseFloat((thresholdData[index3].svUpperLimit).replace(/[^0-9-.]/g, ""));
					}
				}
				$scope.progVarienceDataInfo.push(progVarienceData[index2]);
			}
			taskFull = true;			
			mergePlannedValue($scope.progVarienceDataInfo, $scope.plannedValuesMap);
		}
	}
	
	function mergePlannedValue(progVarienceDataInfo, plannedValuesMap) { 
		if (taskPlannedValue && taskFull) {
			console.log("MERGING PLANNED VALUE");
			$scope.currDatesProgPercentageData = new Array();
			for (const index_1 in progVarienceDataInfo) {
				for (const index_2 in plannedValuesMap) {
					if(plannedValuesMap[index_2].projId == progVarienceDataInfo[index_1].projId) {
						progVarienceDataInfo[index_1].plannedCost = plannedValuesMap[index_2].cost;
					}
				}
				progVarienceDataInfo[index_1].schedulePerformanceIndex = progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].plannedCost;
				progVarienceDataInfo[index_1].scheduleVarienceCost = progVarienceDataInfo[index_1].earnedValueTotal - progVarienceDataInfo[index_1].plannedCost;
				if (progVarienceDataInfo[index_1].revisedBudgetTotal > 0) {
					progVarienceDataInfo[index_1].actualProgress = (progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].revisedBudgetTotal)*100;
					progVarienceDataInfo[index_1].plannedProgress = (progVarienceDataInfo[index_1].plannedCost / progVarienceDataInfo[index_1].revisedBudgetTotal)*100;
				} else {
					progVarienceDataInfo[index_1].actualProgress = (progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].originalBudgetTotal)*100;
					progVarienceDataInfo[index_1].plannedProgress = (progVarienceDataInfo[index_1].plannedCost / progVarienceDataInfo[index_1].originalBudgetTotal)*100;
				}
				
				// Cost Varience
				if(progVarienceDataInfo[index_1].costVarience > progVarienceDataInfo[index_1].excvLowerLimit
						&& progVarienceDataInfo[index_1].excvUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].cvExceptional = true;
				}
				if(progVarienceDataInfo[index_1].costVarience > progVarienceDataInfo[index_1].accvLowerLimit
						&& progVarienceDataInfo[index_1].costVarience <= progVarienceDataInfo[index_1].accvUpperLimit) {
					progVarienceDataInfo[index_1].cvAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].costVarience > progVarienceDataInfo[index_1].wacvLowerLimit
						&& progVarienceDataInfo[index_1].costVarience <= progVarienceDataInfo[index_1].wacvUpperLimit) {
					progVarienceDataInfo[index_1].cvWarning = true;
				}
				if(progVarienceDataInfo[index_1].costVarience <= progVarienceDataInfo[index_1].crcvLowerLimit) {
					progVarienceDataInfo[index_1].cvCritical = true;
				}
				// Schedule Varience
				if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].exsvLowerLimit
						&& progVarienceDataInfo[index_1].exsvUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].svExceptional = true;
				}
				if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].acsvLowerLimit
						&& progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].acsvUpperLimit) {
					progVarienceDataInfo[index_1].svAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].scheduleVarienceCost > progVarienceDataInfo[index_1].wasvLowerLimit
						&& progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].wasvUpperLimit) {
					progVarienceDataInfo[index_1].svWarning = true;
				}
				if(progVarienceDataInfo[index_1].scheduleVarienceCost <= progVarienceDataInfo[index_1].crsvLowerLimit) {
					progVarienceDataInfo[index_1].svCritical = true;
				}
				//Schedule Performance Index
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex > progVarienceDataInfo[index_1].exspiLowerLimit
						&& progVarienceDataInfo[index_1].exspiUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].spiExceptional = true;
				}
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex > progVarienceDataInfo[index_1].acspiLowerLimit
						&& progVarienceDataInfo[index_1].schedulePerformanceIndex <= progVarienceDataInfo[index_1].acspiUpperLimit) {
					progVarienceDataInfo[index_1].spiAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex > progVarienceDataInfo[index_1].waspiLowerLimit
						&& progVarienceDataInfo[index_1].schedulePerformanceIndex <= progVarienceDataInfo[index_1].waspiUpperLimit) {
					progVarienceDataInfo[index_1].spiWarning = true;
				}
				if(progVarienceDataInfo[index_1].schedulePerformanceIndex <= progVarienceDataInfo[index_1].crspiLowerLimit) {
					progVarienceDataInfo[index_1].spiCritical = true;
				}
				// Cost Performance Index
				if(progVarienceDataInfo[index_1].costPerformanceIndex > progVarienceDataInfo[index_1].excpiLowerLimit
						&& progVarienceDataInfo[index_1].excpiUpperLimit == 'Infinite') {
					progVarienceDataInfo[index_1].cpiExceptional = true;
				}
				if(progVarienceDataInfo[index_1].costPerformanceIndex > progVarienceDataInfo[index_1].accpiLowerLimit
						&& progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].accpiUpperLimit) {
					progVarienceDataInfo[index_1].cpiAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].costPerformanceIndex > progVarienceDataInfo[index_1].wacpiLowerLimit
						&& progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].wacpiUpperLimit) {
					progVarienceDataInfo[index_1].cpiWarning = true;
				}
				if(progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].crcpiLowerLimit) {
					progVarienceDataInfo[index_1].cpiCritical = true;
				}
				// To Complete Performance Index
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].exTcpiUpperLimit
						&& progVarienceDataInfo[index_1].exTcpiUpperLimit < progVarienceDataInfo[index_1].exTcpiLowerLimit) {
					console.log("EXCEPTIONAL");
					progVarienceDataInfo[index_1].TcpiExceptional = true;
				}
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].acTcpiUpperLimit
						&& progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex < progVarienceDataInfo[index_1].acTcpiLowerLimit) {
					progVarienceDataInfo[index_1].TcpiAcceptable = true;
				}
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].waTcpiUpperLimit
						&& progVarienceDataInfo[index_1].costPerformanceIndex <= progVarienceDataInfo[index_1].waTcpiLowerLimit) {
					progVarienceDataInfo[index_1].TcpiWarning = true;
				}
				if(progVarienceDataInfo[index_1].toCompleteCostPerformanceIndex >= progVarienceDataInfo[index_1].crTcpiLowerLimit) {
					console.log("CRITICAL");
					progVarienceDataInfo[index_1].TcpiCritical = true;
				}
				progVarienceDataInfo[index_1].schedulePerformanceIndexPercentage = (progVarienceDataInfo[index_1].schedulePerformanceIndex / progVarienceDataInfo[index_1].plannedCost)*100;
				$scope.currDatesProgPercentageData.push(progVarienceDataInfo[index_1]);
			}
			console.log("FINAL", $scope.currDatesProgPercentageData);
			$scope.gridOptions.data = angular.copy($scope.currDatesProgPercentageData);
		}
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
				{ name: 'epsName', displayName: 'EPS Name', headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ name: 'projName', displayName: 'Project Name', headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ name: 'actualStartDate', displayName: 'Start Date', headerTooltip: "Start Date", cellTemplate:'<div><span ng-show="row.entity.actualStartDate != null">{{row.entity.actualStartDate}}</span><span ng-show="row.entity.actualStartDate == null && row.entity.forecastStartDate != null ">{{row.entity.forecastStartDate}}</span><span ng-show="row.entity.actualStartDate == null && row.entity.forecastStartDate == null && row.entity.baselineStartDate != null">{{row.entity.baselineStartDate}}</span></div>', groupingShowAggregationMenu: false },
				{ name: 'actualFinishDate', displayName: 'Finish Date', headerTooltip: "Finish Date", cellTemplate:'<div><span ng-show="row.entity.actualFinishDate != null">{{row.entity.actualFinishDate}}</span><span ng-show="row.entity.actualFinishDate == null && row.entity.forecastFinishDate != null ">{{row.entity.forecastFinishDate}}</span><span ng-show="row.entity.actualFinishDate == null && row.entity.forecastFinishDate == null && row.entity.baselineFinishDate != null">{{row.entity.baselineFinishDate}}</span></div>', groupingShowAggregationMenu: false },
				{ name: 'actualProgress', displayName: '% Complete', headerTooltip: "% Complete", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				} },
				{ name: 'currentPhase', displayName: 'Project Status', headerTooltip: "Project Status", groupingShowAggregationMenu: false }
				
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Contacts");


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
	}



}])