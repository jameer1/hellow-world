'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("costscheduleperformanceindices", {
		url : '/costscheduleperformanceindices',
		data : {
			dashboards : []
		},
		views : {
			'content@' : {
				'templateUrl' : 'views/dashboards/performance/costscheduleperformanceindicesfilter.html',
				controller : 'CostSchedulePerformanceIndicesController'
			}
		}
	})
}]).controller("CostSchedulePerformanceIndicesController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
	"ProjectStatusService", "ProjectSettingsService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, UserEPSProjectService, 
			$location,PerformanceDashboardService, EpsProjectMultiSelectFactory, TreeService, EstimateToCompleteService, ProjectScheduleService, 
			ProjectStatusService, ProjectSettingsService, stylesService, ngGridService) {
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
	$scope.getindicesDetails = function() {
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
		
		PerformanceDashboardService.getPerformanceIndex(req).then(function (data) {
			console.log("data.projManpowerTOs",data.projManpowerTOs);
			if (data.projManpowerTOs.length > 0 && data.projManpowerTOs[0] && data.projManpowerTOs[0].estimateType
				&& data.projManpowerTOs[0].estimateType.contains('SPI')) {
				console.log("if");
				ProjectScheduleService.getProjBudgetManPowerDetails(req).then(function (data1) {
					console.log(data1);
					$scope.projManpowerDetails = data.projManpowerTOs;
					calculatePlannedValues(data1, $scope.projManpowerDetails, "empClassId");
					EstimateToCompleteService.manpower($scope.projManpowerDetails);
					calculateManhoursTotal($scope.projManpowerDetails);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
				});
			} else {
				console.log("else");
				$scope.projManpowerDetails = data.projManpowerTOs;
				EstimateToCompleteService.manpower($scope.projManpowerDetails);
				calculateManhoursTotal($scope.projManpowerDetails);
				console.log("$scope.projManpowerDetails", $scope.projManpowerDetails);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting manpower CostCode Wise details", 'Error');
		});
		
	}
	
	
	function mergValues(projCostValues, projProgressStatus) {
		var thresholdData = $scope.performenceThresholdData;
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
		console.log("MERGING PLANNED VALUE1");
		console.log("taskPlannedValue", taskPlannedValue);
		console.log("taskFull", taskFull);
		if (taskPlannedValue && taskFull) {
			console.log("MERGING PLANNED VALUE2");
			$scope.costSchedVarienceData = new Array();
			for (const index_1 in progVarienceDataInfo) {
				for (const index_2 in plannedValuesMap) {
					if(plannedValuesMap[index_2].projId == progVarienceDataInfo[index_1].projId) {
						progVarienceDataInfo[index_1].plannedCost = plannedValuesMap[index_2].cost;
						progVarienceDataInfo[index_1].plannedDirectManHours = plannedValuesMap[index_2].directManHours;
					}
				}
				progVarienceDataInfo[index_1].schedulePerformanceIndex = progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].plannedCost;
				//progVarienceDataInfo[index_1].schedulePerformanceIndexPercentage=SFI= (EV-PV)/PV*100%
				progVarienceDataInfo[index_1].scheduleVarienceCost = progVarienceDataInfo[index_1].earnedValueTotal - progVarienceDataInfo[index_1].plannedCost;
				progVarienceDataInfo[index_1].scheduleVarienceCostPercentage = (progVarienceDataInfo[index_1].scheduleVarienceCost / progVarienceDataInfo[index_1].plannedCost) * 100;
				progVarienceDataInfo[index_1].spiCost = progVarienceDataInfo[index_1].earnedValueTotal / progVarienceDataInfo[index_1].plannedCost;
				
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
				$scope.costSchedVarienceData.push(progVarienceDataInfo[index_1]);
			}
			console.log("FINAL", $scope.costSchedVarienceData);
		}
		mergeCostManHours($scope.costSchedVarienceData, $scope.projManPowerValues);
	}
	
	function mergeCostManHours(costData, manPowerData) {
		$scope.costSchedPerformanceIndex = new Array();
		for (const index_3 in costData) {
			for (const index_4 in manPowerData) {
				if(manPowerData[index_4].projId == costData[index_3].projId) {
					costData[index_3].earnedManHoursTotal = manPowerData[index_4].earnedManHoursTotal;
					costData[index_3].actualHrsTotal = manPowerData[index_4].actualHrsTotal;
					costData[index_3].scheduleVarienceDirectManHours = costData[index_3].earnedManHoursTotal - costData[index_3].plannedDirectManHours;
					costData[index_3].costVarienceManHours = costData[index_3].earnedManHoursTotal - costData[index_3].actualHrsTotal;
					costData[index_3].schedVarDirManHrsPercentage = (costData[index_3].scheduleVarienceDirectManHours / costData[index_3].plannedDirectManHours)*100;
					costData[index_3].costVarManHrsPercentage = (costData[index_3].costVarienceManHours / costData[index_3].earnedManHoursTotal)*100;
					costData[index_3].spiDirManHrs = costData[index_3].earnedManHoursTotal / costData[index_3].plannedDirectManHours;
					costData[index_3].cpiManHrs = costData[index_3].earnedManHoursTotal / costData[index_3].actualHrsTotal;
					
					// Schedule Varience
					if(costData[index_3].scheduleVarienceDirectManHours > costData[index_3].exsvLowerLimit
							&& costData[index_3].exsvUpperLimit == 'Infinite') {
						costData[index_3].svManHoursExceptional = true;
					}
					if(costData[index_3].scheduleVarienceDirectManHours > costData[index_3].acsvLowerLimit
							&& costData[index_3].scheduleVarienceDirectManHours <= costData[index_3].acsvUpperLimit) {
						costData[index_3].svManHoursAcceptable = true;
					}
					if(costData[index_3].scheduleVarienceDirectManHours > costData[index_3].wasvLowerLimit
							&& costData[index_3].scheduleVarienceDirectManHours <= costData[index_3].wasvUpperLimit) {
						costData[index_3].svManHoursWarning = true;
					}
					if(costData[index_3].scheduleVarienceDirectManHours <= costData[index_3].crsvLowerLimit) {
						costData[index_3].svManHoursCritical = true;
					}
					
					// Cost Varience
					if(costData[index_3].costVarienceManHours > costData[index_3].excvLowerLimit
							&& costData[index_3].excvUpperLimit == 'Infinite') {
						costData[index_3].cvManHoursExceptional = true;
					}
					if(costData[index_3].costVarienceManHours > costData[index_3].accvLowerLimit
							&& costData[index_3].costVarienceManHours <= costData[index_3].accvUpperLimit) {
						costData[index_3].cvManHoursAcceptable = true;
					}
					if(costData[index_3].costVarienceManHours > costData[index_3].wacvLowerLimit
							&& costData[index_3].costVarienceManHours <= costData[index_3].wacvUpperLimit) {
						costData[index_3].cvManHoursWarning = true;
					}
					if(costData[index_3].costVarienceManHours <= costData[index_3].crcvLowerLimit) {
						costData[index_3].cvManHoursCritical = true;
					}
					
					// Schedule Performance Index
					if(costData[index_3].spiDirManHrs > costData[index_3].exspiLowerLimit
							&& costData[index_3].exspiUpperLimit == 'Infinite') {
						costData[index_3].spiManHoursExceptional = true;
					}
					if(costData[index_3].spiDirManHrs > costData[index_3].acspiLowerLimit
							&& costData[index_3].spiDirManHrs <= costData[index_3].acspiUpperLimit) {
						costData[index_3].spiManHoursAcceptable = true;
					}
					if(costData[index_3].spiDirManHrs > costData[index_3].waspiLowerLimit
							&& costData[index_3].spiDirManHrs <= costData[index_3].waspiUpperLimit) {
						costData[index_3].spiManHoursWarning = true;
					}
					if(costData[index_3].spiDirManHrs <= costData[index_3].crspiLowerLimit) {
						costData[index_3].spiManHoursCritical = true;
					}
					
					// Cost Performance Index
					if(costData[index_3].cpiManHrs > costData[index_3].excpiLowerLimit
							&& costData[index_3].excpiUpperLimit == 'Infinite') {
						costData[index_3].cpiManHoursExceptional = true;
					}
					if(costData[index_3].cpiManHrs > costData[index_3].accpiLowerLimit
							&& costData[index_3].cpiManHrs <= costData[index_3].accpiUpperLimit) {
						costData[index_3].cpiManHoursAcceptable = true;
					}
					if(costData[index_3].cpiManHrs > costData[index_3].wacpiLowerLimit
							&& costData[index_3].cpiManHrs <= costData[index_3].wacpiUpperLimit) {
						costData[index_3].cpiManHoursWarning = true;
					}
					if(costData[index_3].cpiManHrs <= costData[index_3].crcpiLowerLimit) {
						costData[index_3].cpiManHoursCritical = true;
					}
				}
			}
			$scope.costSchedPerformanceIndex.push(costData[index_3]);
		}
		 for(var cspi of $scope.costSchedPerformanceIndex){
			//SPI labour hours
			 if(cspi.spiManHoursExceptional == true){
			    cspi.spiLabour1=Object.is(cspi.spiDirManHrs, NaN) ? "Project in Acceleration" :cspi.spiDirManHrs.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.spiManHoursAcceptable == true){
			    cspi.spiLabour1=Object.is(cspi.spiDirManHrs, NaN) ? "Project in Acceleration" :cspi.spiDirManHrs.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.spiManHoursWarning == true){
			    cspi.spiLabour1=Object.is(cspi.spiDirManHrs, NaN) ? "Project in Delayed" :cspi.spiDirManHrs.toFixed(2)+" "+"Project in Delayed";
			}
			if(cspi.spiManHoursCritical == true){
			    cspi.spiLabour1=Object.is(cspi.spiDirManHrs, NaN) ? "Project in Delayed" :cspi.spiDirManHrs.toFixed(2)+" "+"Project in Delayed";
			}
			//SPI cost
			 if(cspi.spiExceptional == true){
			    cspi.spiCost1=Object.is(cspi.spiCost, NaN) ? "Project in Acceleration" :cspi.spiCost.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.spiAcceptable == true){
			    cspi.spiCost1=Object.is(cspi.spiCost, NaN) ? "Project in Acceleration" :cspi.spiCost.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.spiWarning == true){
			    cspi.spiCost1=Object.is(cspi.spiCost, NaN) ? "Project in Delayed" :cspi.spiCost.toFixed(2)+" "+"Project in Delayed";
			}
			if(cspi.spiCritical == true){
			    cspi.spiCost1=Object.is(cspi.spiCost, NaN) ? "Project in Delayed" :cspi.spiCost.toFixed(2)+" "+"Project in Delayed";
			}
            //CPI labour hours
            if(cspi.cpiManHoursExceptional == true){
			    cspi.spiLabourHour1=Object.is(cspi.cpiManHrs, NaN) ? "Project in Acceleration" :cspi.cpiManHrs.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.cpiManHoursAcceptable == true){
			    cspi.spiLabourHour1=Object.is(cspi.cpiManHrs, NaN) ? "Project in Acceleration" :cspi.cpiManHrs.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.cpiManHoursWarning == true){
			    cspi.spiLabourHour1=Object.is(cspi.cpiManHrs, NaN) ? "Project in Delayed" :cspi.cpiManHrs.toFixed(2)+" "+"Project in Delayed";
			}
			if(cspi.cpiManHoursCritical == true){
			    cspi.spiLabourHour1=Object.is(cspi.cpiManHrs, NaN) ? "Project in Delayed" :cspi.cpiManHrs.toFixed(2)+" "+"Project in Delayed";
			}
			//CPI cost
            if(cspi.cpiExceptional == true){
			    cspi.cpiCost1=Object.is(cspi.cpiCost, NaN) ? "Project in Acceleration" :cspi.cpiCost.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.cpiAcceptable == true){
			    cspi.cpiCost1=Object.is(cspi.cpiCost, NaN) ? "Project in Acceleration" :cspi.cpiCost.toFixed(2)+" "+"Project in Acceleration";
			}
			if(cspi.cpiWarning == true){
			    cspi.cpiCost1=Object.is(cspi.cpiCost, NaN) ? "Project in Delayed" :cspi.cpiCost.toFixed(2)+" "+"Project in Delayed";
			}
			if(cspi.cpiCritical == true){
			    cspi.cpiCost1=Object.is(cspi.cpiCost, NaN) ? "Project in Delayed" :cspi.cpiCost.toFixed(2)+" "+"Project in Delayed";
			}
			
		}
		$scope.gridOptions.data = angular.copy($scope.costSchedPerformanceIndex);
		console.log("costSchedPerformanceIndex", $scope.costSchedPerformanceIndex);
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
					projCostValues[i].costVarienceCostPercentage = (projCostValues[i].costVarience / projCostValues[i].earnedValueTotal) * 100;
					projCostValues[i].cpiCost = projCostValues[i].earnedValueTotal / projCostValues[i].actualCostTotal;
					
					
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
					costVarienceCostPercentage:0,
					scheduleVarience:0,
					scheduleVarienceCostPercentage:0,
					costVarienceLabourHours:0
				});
			}
		}
		$scope.projCostValues = projCostValues;
		console.log("$scope.projCostValues",$scope.projCostValues);
	}
	
	function calculateManhoursTotal(projManpowerDetails) {
		let projManPowerValues = [];
		for (const value of projManpowerDetails) {
			if (value.projEmpCategory == 'DIRECT') {
				let found = false;
				for (let i=0; i < projManPowerValues.length; i++) {
					projManPowerValues[i].estCompletion = 0;
					if (projManPowerValues[i].projId == value.projId) {
						found = true;
						projManPowerValues[i].projId = value.projId;
						projManPowerValues[i].epsName = $scope.searchProject.parentName;
						
						projManPowerValues[i].originalHrsTotal += value.originalQty;
						if(value.revisedQty == null || value.revisedQty == undefined || value.revisedQty == ""){
							value.revisedQty=0;
						}
						projManPowerValues[i].revisedHrsTotal += value.revisedQty;
						projManPowerValues[i].actualHrsTotal += value.actualQty;
						projManPowerValues[i].earnedManHoursTotal += value.earnedValue;
						if(value.revisedQty > 0) {
							projManPowerValues[i].remainingHrsTotal += value.revisedQty - value.actualQty;
						} else {
							projManPowerValues[i].remainingHrsTotal += value.originalQty - value.actualQty;
						}
						if(value.estimateType == "Remaining Units") {
							if(value.revisedQty > 0) {
								projManPowerValues[i].estCompletion += (value.revisedQty - value.actualQty);
								projManPowerValues[i].etcTotal += value.revisedQty - value.actualQty;
							} else {
								projManPowerValues[i].estCompletion += (value.originalQty - value.actualQty);
								projManPowerValues[i].etcTotal += value.originalQty - value.actualQty;
							}
						} else if(value.estimateType == "BAC-EV") {
							if(value.revisedQty > 0) {
								projManPowerValues[i].estCompletion += (value.revisedQty - value.earnedValue);
								projManPowerValues[i].etcTotal += (value.revisedQty - value.earnedValue);
							} else {
								projManPowerValues[i].estCompletion += (value.originalQty - value.earnedValue);
								projManPowerValues[i].etcTotal += (value.originalQty - value.earnedValue);
							}
						} else if(value.estimateType == "(BAC-EV)/PF") {
							var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
							var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
							var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
							projManPowerValues[i].estCompletion += etcBAC_EV_PF;
							projManPowerValues[i].etcTotal += etcBAC_EV_PF;
						} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
							var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
							var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
							var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
							var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
							projManPowerValues[i].estCompletion += etcBAC_EV_PF_SPI;
							projManPowerValues[i].etcTotal += etcBAC_EV_PF_SPI;
						} else if(value.estimateType == "New Estimate") {
							projManPowerValues[i].estCompletion += value.estimateCompletion;
							projManPowerValues[i].etcTotal += value.estimateComplete;
						}
						projManPowerValues[i].estAtCompletionHrsTotal = projManPowerValues[i].actualHrsTotal + projManPowerValues[i].etcTotal;
						projManPowerValues[i].manPowerPI = (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].earnedManHoursTotal) / (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].actualHrsTotal);
					}
				}
				if (!found) {
					let etctotal1 = 0;
					let estCompletion1 = 0;
					let remainingTotal = 0;
					if(value.revisedQty > 0) {
						remainingTotal += value.revisedQty - value.actualQty;
					} else {
						remainingTotal += value.originalQty - value.actualQty;
					}
					if(value.estimateType == "Remaining Units") {
						if(value.revisedQty > 0) {
							estCompletion1 += (value.revisedQty - value.actualQty);
							etctotal1 += value.revisedQty - value.actualQty;
						} else {
							estCompletion1 += (value.originalQty - value.actualQty);
							etctotal1 += value.originalQty - value.actualQty;
						}
					} else if(value.estimateType == "BAC-EV") {
						if(value.revisedQty > 0) {
							estCompletion1 += (value.revisedQty - value.earnedValue);
							etctotal1 += (value.revisedQty - value.earnedValue);
						} else {
							estCompletion1 += (value.originalQty - value.earnedValue);
							etctotal1 += (value.originalQty - value.earnedValue);
						}
					} else if(value.estimateType == "(BAC-EV)/PF") {
						var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
						var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
						var etcBAC_EV_PF=(pf==0)  ? bac_ev : (bac_ev)/pf;
						estCompletion1 += etcBAC_EV_PF;
						etctotal1 += etcBAC_EV_PF;
					} else if(value.estimateType == "(BAC-EV)/(PF*SPI)") {
						var bac_ev=((value.revisedQty ? value.revisedQty : value.originalQty) - value.earnedValue);
						var pf=(value.earnedValue==0 || value.earnedValue==null || value.actualQty==0) ? 0 : (value.earnedValue / value.actualQty);
						var spi=(value.earnedValue==0 || value.earnedValue==null || value.plannedValue==0) ? 0 : (value.earnedValue / value.plannedValue);
						var etcBAC_EV_PF_SPI=(pf==0 || spi == 0)  ? bac_ev : (bac_ev)/(pf*spi);
						console.log("etcBAC_EV_PF_SPI " + etcBAC_EV_PF_SPI);
						estCompletion1 += etcBAC_EV_PF_SPI;
						etctotal1 += etcBAC_EV_PF_SPI;
					} else if(value.estimateType == "New Estimate") {
						estCompletion1 += value.estimateCompletion;
						etctotal1 += value.estimateComplete;
					}
					projManPowerValues.push({
						"projId": value.projId,
						"epsName": '', 
						"projName": $scope.searchProject.projName, 
						originalHrsTotal: value.originalQty,
						revisedHrsTotal: value.revisedQty,
						earnedManHoursTotal: value.earnedValue,
						actualHrsTotal: value.actualQty,
						remainingHrsTotal: remainingTotal,
						plannedValueTotal: 0,
						estCompletion: estCompletion1,
						etcTotal: etctotal1,
						estAtCompletionHrsTotal: 0,
						manPowerPI: 0,
						costPI: 0
					});
				}
			}
		}
		$scope.projManPowerValues = projManPowerValues;
		console.log("$scope.projManPowerValues",$scope.projManPowerValues);
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'spiLabour1', displayName: "SPI - Labour Hours", headerTooltip: "SPI (Schedule Performance Index) - Labour Hours",
						cellTemplate:'<span ng-if="row.entity.spiManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.spiDirManHrs | number: 2}} Project in Acceleration</span><span ng-if="row.entity.spiManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i> {{row.entity.spiDirManHrs | number: 2}} Project in Acceleration</span><span ng-if="row.entity.spiManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.spiDirManHrs | number: 2}} Project is Delayed</span><span ng-if="row.entity.spiManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{row.entity.spiDirManHrs | number: 2}} Project is Delayed</span>'},
						{ field: 'spiCost1', displayName: "SPI - Cost", headerTooltip: "SPI (Shedule Performance Index) - Cost",
						cellTemplate: '<span ng-if="row.entity.spiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.spiCost | number: 2}} Project in Acceleration</span><span ng-if="row.entity.spiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.spiCost | number: 2}}  Project in Acceleration</span><span ng-if="row.entity.spiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.spiCost | number: 2}} Project is Delayed</span><span ng-if="row.entity.spiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.spiCost | number: 2}} Project is Delayed</span>'},
						{ field: 'spiLabourHour1', displayName: "CPI - Labour Hours", headerTooltip: "CPI (Cost Performance Index) - Labour Hours",
						cellTemplate: '<span ng-if="row.entity.cpiManHoursExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.cpiManHrs | number: 2}} Project in Acceleration </span><span ng-if="row.entity.cpiManHoursAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.cpiManHrs | number: 2}} Project in Acceleration</span><span ng-if="row.entity.cpiManHoursWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{row.entity.cpiManHrs | number: 2}} Project is Delayed</span><span ng-if="row.entity.cpiManHoursCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.cpiManHrs | number: 2}} Project is Delayed</span>'},
						{ field: 'cpiCost1', displayName: "CPI - Cost", headerTooltip: "CPI (Cost Performance Index) - Cost", 
						cellTemplate: '<span ng-if="row.entity.cpiExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{row.entity.cpiCost | number: 2}} Project in Acceleration</span><span ng-if="row.entity.cpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{row.entity.cpiCost | number: 2}} Project in Acceleration</span><span ng-if="row.entity.cpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i> {{row.entity.cpiCost | number: 2}} Project is Delayed</span><span ng-if="row.entity.cpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{row.entity.cpiCost | number: 2}} Project is Delayed</span>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_PerformanceIndices");
					
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