'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("remainingbudgetbyprovince", {
		url: '/remainingbudgetbyprovince',
		data: {
			dashboards: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/dashboards/remainingbudget/remainingbudgetbyprovincefilter.html',
				controller: 'RemainingBudgetByProvinceController'
			}
		}
	})
}]).controller("RemainingBudgetByProvinceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
	"ProjectStatusService", "ProjectSettingsService", "EstimateToCompleteDashboardGroupingService",  function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, UserEPSProjectService, 
			$location,PerformanceDashboardService, EpsProjectMultiSelectFactory, TreeService, EstimateToCompleteService, ProjectScheduleService, 
			ProjectStatusService, ProjectSettingsService, EstimateToCompleteDashboardGroupingService) {

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
	$scope.projGeneralValues = [];
	
	$scope.remainingBudgetByProvinceTabs = new Array();
	
	let task1Completed = false, task2Completed = false, taskPlannedValue = false, taskFull = false;
	$scope.getRemainingBudgetByProvinceDetails = function () {
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
		ProjectStatusService.getMultiProjGenerals(req).then(function (data) {
			$scope.projGeneralValues = data.projGeneralMstrTOs;
			console.log("$scope.projGeneralValues",$scope.projGeneralValues);
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
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
				projCostValues[index].epsName=$scope.searchProject.parentName.split(',')[index];
				projCostValues[index].projName=$scope.searchProject.projName.split(',')[index];
				$scope.progressVarienceData.push(projCostValues[index]);
			}
			//console.log("progressVarienceData", progressVarienceData);
			var progVarienceData = $scope.progressVarienceData;
			console.log("progVarienceData", progVarienceData);
			$scope.progVarienceDataInfo = new Array();
			for (const index2 in progVarienceData) {
				$scope.progVarienceDataInfo.push(progVarienceData[index2]);
			}
			taskFull = true;			
			mergePlannedValue($scope.progVarienceDataInfo, $scope.plannedValuesMap);
		}
	}
	
	function mergePlannedValue(progVarienceDataInfo, plannedValuesMap) { 
		
		if (taskPlannedValue && taskFull) {
			console.log("MERGING PLANNED VALUE");
			$scope.costSchedPerformanceData = new Array();
			for (const index_1 in progVarienceDataInfo) {
				for (const index_2 in plannedValuesMap) {
					if(plannedValuesMap[index_2].projId == progVarienceDataInfo[index_1].projId) {
						progVarienceDataInfo[index_1].plannedCost = plannedValuesMap[index_2].cost;
					}
				}

				$scope.costSchedPerformanceData.push(progVarienceDataInfo[index_1]);
			}
			console.log("FINAL", $scope.costSchedPerformanceData);
			var estAtCompletionDetails = $scope.costSchedPerformanceData;
			var projGeneral = $scope.projGeneralValues;
			$scope.estimateAtCountryDetails = new Array();
			for (const index_3 in estAtCompletionDetails){
				for (const index_4 in projGeneral) {
					if(projGeneral[index_4].projId == estAtCompletionDetails[index_3].projId) {
						estAtCompletionDetails[index_3].provisionName = projGeneral[index_4].provisionName
					}
				}
				$scope.estimateAtCountryDetails.push(estAtCompletionDetails[index_3]);
				
			}
			mergeVal($scope.estimateAtCountryDetails);
		}
	}
	
	function mergeVal(estAtCompByCountry) {
		console.log("estAtCompByCountry", estAtCompByCountry);
		let newData = [];
		for (let i=0; i < estAtCompByCountry.length; i++) {
			let index = newData.findIndex(e => e.provisionName == estAtCompByCountry[i].provisionName);
			if (index == -1) {
				newData.push({"provisionName": estAtCompByCountry[i].provisionName, "remainingBudget" : estAtCompByCountry[i].remainingBudgetTotal});
			} else {
				newData[index].remainingBudget += estAtCompByCountry[i].remainingBudgetTotal;
			}
		}
		console.log(newData)
		setGraphData(newData);
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
	
	/*
	$scope.getRemainingBudgetByProvinceDetails = function () {
		RemainingBudgetDashboardGroupingService.getRemainingBudgetDashboards($scope.selectedProjIds, "country").then(data => {
			setGraphData(data);
		});

	};
	*/
	function setGraphData(budgetDetails) {
		const labels = new Array();
		const data = new Array();
		for (const key in budgetDetails) {
			labels.push(budgetDetails[key].provisionName);
			budgetDetails[key].remainingBudget = parseFloat(budgetDetails[key].remainingBudget).toFixed(2);
			data.push(budgetDetails[key].remainingBudget);
		}
		$scope.remainingBudgetByProvinceTabs.unshift({ 'data': data, 'labels': labels });
		initGraph("pie");
	}


	function initGraph(type) {
		$scope.chart_type = type;
		$scope.options = {
			title: {
				display: true,
				text: 'Remaining Budget',
			},
			legend: {
				display: true,
				position: 'bottom'
			},
			scales: {
				xAxes: [{
					display: true,
					stacked: true,
				}],
				yAxes: [{
					display: true,
					stacked: true,
					/*ticks: {
						beginAtZero: true
					} */
				}]
			}
		};

		$scope.options.scales.xAxes[0].reverse = false;
		$scope.options.scales.yAxes[0].reverse = false;
		if ($scope.chart_type === 'pie' || $scope.chart_type === 'polarArea' || $scope.chart_type === 'doughnut') {
			// $scope.labels = series;
			$scope.options.scales.xAxes[0].display = false;
			$scope.options.scales.yAxes[0].display = false;
		} else {
			// $scope.labels = labels;
			// $scope.series = series;
			if ($scope.chart_type === 'radar') {
				$scope.options.scales.xAxes[0].display = false;
				$scope.options.scales.yAxes[0].display = false;
			}
		}


	};

	$scope.menuOptions = [['Chart Type', [['Area', function ($itemScope) {
		$scope.chart_type = 'Area';
		$scope.initGraph($scope.chart_type);
	}], ['Bar', function ($itemScope) {
		$scope.chart_type = 'bar';
		$scope.initGraph($scope.chart_type);
	}], ['HorizantalBar', function ($itemScope) {
		$scope.chart_type = 'horizontalBar';
		$scope.initGraph($scope.chart_type);
	}], ['PolarArea', function ($itemScope) {
		$scope.chart_type = 'polarArea';
		$scope.initGraph($scope.chart_type);
	}], ['Doughnut', function ($itemScope) {
		$scope.chart_type = 'doughnut';
		$scope.initGraph($scope.chart_type);
	}], ['Line', function ($itemScope) {
		$scope.chart_type = 'line';
		$scope.initGraph($scope.chart_type);
	}], ['Pie', function ($itemScope) {
		$scope.chart_type = 'pie';
		$scope.initGraph($scope.chart_type);
	}], ['Radar', function ($itemScope) {
		$scope.chart_type = 'radar';
		$scope.initGraph($scope.chart_type);
	}]]], null, ['Fill', [['Filled', function ($itemScope) {
	}], ['Unfilled', function ($itemScope) {
	}], ['Auto', function ($itemScope) {
	}]]], null, ['Stack', [['Stacked', function ($itemScope) {
		$scope.options.scales.xAxes[0].stacked = true;
		$scope.options.scales.yAxes[0].stacked = true;
	}], ['Unstacked', function ($itemScope) {
		$scope.options.scales.xAxes[0].stacked = false;
		$scope.options.scales.yAxes[0].stacked = false;
	}], ['Auto', function ($itemScope) {
	}]]], ['Swap Facets', function ($itemScope) {
	}], null, ['Proportional', function ($itemScope) {
	}]];
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
			template: 'views/help&tutorials/dashboardshelp/remainbudget/remainbudgetbyprovincehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}])
