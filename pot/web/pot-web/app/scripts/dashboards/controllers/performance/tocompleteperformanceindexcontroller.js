'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("tocompleteperformanceindex", {
		url : '/tocompleteperformanceindex',
		data : {
			dashboards : []
		},
		views : {
			'content@' : {
				'templateUrl' : 'views/dashboards/performance/tocompleteperformanceindexfilter.html',
				controller : 'ToCompletePerformanceController'
			}
		}
	})
}]).controller("ToCompletePerformanceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PerformanceDashboardService", 
	"GenericAlertService", "UserEPSProjectService", "$location", "EpsProjectMultiSelectFactory","ProjectScheduleService", "EstimateToCompleteService",
	"ProjectBudgetService", "TreeService", "ProjectSettingsService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, PerformanceDashboardService, GenericAlertService, UserEPSProjectService, 
			$location, EpsProjectMultiSelectFactory, ProjectScheduleService, EstimateToCompleteService, ProjectBudgetService, TreeService,
			ProjectSettingsService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.projCostStmtDtls = [];
	$scope.projManPowerValues = [];
	$scope.projCostValues = [];
	
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

	$scope.getToCompleteDetails = function() {
		var req = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		}
		if (req.projIds == null) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
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
		PerformanceDashboardService.getProjCostStatements(req).then(function (data) {
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
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
				});
			} else {
				$scope.projCostStmtDtls = projCostStmtDtls;
				EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
				calculateCostTotal($scope.projCostStmtDtls);
			}
		});
		ProjectSettingsService.getProjPerformenceThreshold(req).then(function (data) {
			$scope.performenceThresholdData = data.projPerformenceThresholdTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
		});

	}
	
	$scope.costItemClick = function (item, expand) {
		TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
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
						projManPowerValues[i].earnedValueTotal += value.earnedValue;
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
						console.log("estCompletion", projManPowerValues[i].estAtCompletionHrsTotal);
						console.log("earnedValueTotal", projManPowerValues[i].earnedValueTotal);
						console.log("actualHrsTotal", projManPowerValues[i].actualHrsTotal);
						projManPowerValues[i].manPowerPI = (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].earnedValueTotal) / (projManPowerValues[i].estAtCompletionHrsTotal - projManPowerValues[i].actualHrsTotal);
						console.log("manPowerPI",projManPowerValues[i].manPowerPI);
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
						earnedValueTotal: value.earnedValue,
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
					projCostValues[i].costPerformanceIndex = (projCostValues[i].estAtCompletionTotal - projCostValues[i].earnedValueTotal) / (projCostValues[i].estAtCompletionTotal - projCostValues[i].actualCostTotal);
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
					costPerformanceIndex:0
				});
			}
		}
		$scope.projCostValues = projCostValues;
		console.log("$scope.projCostValues",$scope.projCostValues);

		mergValues($scope.projManPowerValues, $scope.projCostValues);
	}
	
	function mergValues(projManPowerValues, projCostValues) {
		$scope.performanceIndexData = new Array();
		for (const index in projManPowerValues) {
			for (const index1 in projCostValues) {
				if (projCostValues[index1].projId == projManPowerValues[index].projId) {
					projManPowerValues[index].costPI = projCostValues[index1].costPerformanceIndex;
				}
			}
			projManPowerValues[index].epsName=$scope.searchProject.parentName.split(',')[index];
			projManPowerValues[index].projName=$scope.searchProject.projName.split(',')[index];
			$scope.performanceIndexData.push(projManPowerValues[index]);
			
		}
		console.log("$scope.performanceIndexData", $scope.performanceIndexData);
		mergeThresholdValues($scope.performanceIndexData,$scope.performenceThresholdData);
	}
	
	function mergeThresholdValues(performanceIndexData,performenceThresholdData) {
		console.log("Merging Threshold Values");
		$scope.performanceIndexInfo = new Array();
		for (const index2 in performanceIndexData) {
			for (const index3 in performenceThresholdData) {
				if(performenceThresholdData[index3].projId == performanceIndexData[index2].projId && performenceThresholdData[index3].category == "Exceptional") {
					performanceIndexData[index2].category = performenceThresholdData[index3].category;
					performanceIndexData[index2].exTcpiLowerLimit = parseFloat((performenceThresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
					performanceIndexData[index2].exTcpiUpperLimit = parseFloat((performenceThresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
				} 
				if(performenceThresholdData[index3].projId == performanceIndexData[index2].projId && performenceThresholdData[index3].category == "Acceptable") {
					performanceIndexData[index2].category = performenceThresholdData[index3].category;
					performanceIndexData[index2].acTcpiLowerLimit = parseFloat((performenceThresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
					performanceIndexData[index2].acTcpiUpperLimit = parseFloat((performenceThresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
				}
				if(performenceThresholdData[index3].projId == performanceIndexData[index2].projId && performenceThresholdData[index3].category == "Warning") {
					performanceIndexData[index2].category = performenceThresholdData[index3].category;
					performanceIndexData[index2].waTcpiLowerLimit = parseFloat((performenceThresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
					performanceIndexData[index2].waTcpiUpperLimit = parseFloat((performenceThresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
				}
				if(performenceThresholdData[index3].projId == performanceIndexData[index2].projId && performenceThresholdData[index3].category == "Critical") {
					performanceIndexData[index2].category = performenceThresholdData[index3].category;
					performanceIndexData[index2].crTcpiLowerLimit = parseFloat((performenceThresholdData[index3].tcpiLowerLimit).replace(/[^0-9-.]/g, ""));
					performanceIndexData[index2].crTcpiUpperLimit = parseFloat((performenceThresholdData[index3].tcpiUpperLimit).replace(/[^0-9-.]/g, ""));
				}
				/*----------- To calculate Man Power -----------*/
				
				if(performanceIndexData[index2].manPowerPI >= performanceIndexData[index2].exTcpiUpperLimit
						&& performanceIndexData[index2].manPowerPI < performanceIndexData[index2].exTcpiLowerLimit) {
					performanceIndexData[index2].TcpiManPowerExceptional = true;
				}
				if(performanceIndexData[index2].manPowerPI >= performanceIndexData[index2].acTcpiUpperLimit
						&& performanceIndexData[index2].manPowerPI < performanceIndexData[index2].acTcpiLowerLimit) {
					performanceIndexData[index2].TcpiManPowerAcceptable = true;
				}
				if(performanceIndexData[index2].manPowerPI >= performanceIndexData[index2].waTcpiUpperLimit
						&& performanceIndexData[index2].costPerformanceIndex <= performanceIndexData[index2].waTcpiLowerLimit) {
					performanceIndexData[index2].TcpiManPowerWarning = true;
				}
				if(performanceIndexData[index2].manPowerPI >= performanceIndexData[index2].crTcpiLowerLimit) {
					performanceIndexData[index2].TcpiManPowerCritical = true;
				}
				
				/*----------- To calculate Cost -----------*/

				if(performanceIndexData[index2].costPI >= performanceIndexData[index2].exTcpiUpperLimit
						&& performanceIndexData[index2].costPI < performanceIndexData[index2].exTcpiLowerLimit) {
					performanceIndexData[index2].TcpiExceptional = true;
				}
				if(performanceIndexData[index2].costPI >= performanceIndexData[index2].acTcpiUpperLimit
						&& performanceIndexData[index2].costPI < performanceIndexData[index2].acTcpiLowerLimit) {
					performanceIndexData[index2].TcpiAcceptable = true;
				}
				if(performanceIndexData[index2].costPI >= performanceIndexData[index2].waTcpiUpperLimit
						&& performanceIndexData[index2].costPerformanceIndex <= performanceIndexData[index2].waTcpiLowerLimit) {
					performanceIndexData[index2].TcpiWarning = true;
				}
				if(performanceIndexData[index2].costPI >= performanceIndexData[index2].crTcpiLowerLimit) {
					performanceIndexData[index2].TcpiCritical = true;
				}
			}
			$scope.performanceIndexInfo.push(performanceIndexData[index2]);
			console.log("$scope.performanceIndexInfo", $scope.performanceIndexInfo);
			for(var tcpi of $scope.performanceIndexInfo){
			//TCPI labour hours
           	if(tcpi.TcpiManPowerExceptional == true){
	               tcpi.labourHours=Object.is(tcpi.manPowerPI, NaN) ? "No need for budget revision" : tcpi.manPowerPI.toFixed(2) +" "+"No need for budget revision";
             }
             if(tcpi.TcpiManPowerAcceptable == true){
	               tcpi.labourHours=Object.is(tcpi.manPowerPI, NaN) ? "No need for budget revision" : tcpi.manPowerPI.toFixed(2) +" "+"No need for budget revision";
             }
             if(tcpi.TcpiManPowerWarning == true){
	               tcpi.labourHours=Object.is(tcpi.manPowerPI, NaN) ? "No need for budget revision" : tcpi.manPowerPI.toFixed(2) +" "+"No need for budget revision";
             }
             if(tcpi.TcpiManPowerCritical == true){
	               tcpi.labourHours=Object.is(tcpi.manPowerPI, NaN) ? "No need for budget revision" : tcpi.manPowerPI.toFixed(2) +" "+"No need for budget revision";
             }
             //TCPI cost
             if(tcpi.TcpiExceptional == true){
	               tcpi.TCPIcost=Object.is(tcpi.costPI, NaN) ? "No need for budget revision" : tcpi.costPI.toFixed(2) +" "+"No need for budget revision";
             }
             if(tcpi.TcpiAcceptable == true){
	               tcpi.TCPIcost=Object.is(tcpi.costPI, NaN) ? "No need for budget revision" : tcpi.costPI.toFixed(2) +" "+"No need for budget revision";
             }
             if(tcpi.TcpiWarning == true){
	               tcpi.TCPIcost=Object.is(tcpi.costPI, NaN) ? "Need for budget revision" : tcpi.costPI.toFixed(2) +" "+"Need for budget revision";
             }
             if(tcpi.TcpiCritical == true){
	               tcpi.TCPIcost=Object.is(tcpi.costPI, NaN) ? "Need for budget revision" : tcpi.costPI.toFixed(2) +" "+"Need for budget revision";
             }
		}
			$scope.gridOptions.data = angular.copy($scope.performanceIndexInfo);
		}
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'labourHours', displayName: "TCPI - Labour Hours", headerTooltip: "TCPI (To Complete Performance Index) - Labour Hours",
						cellTemplate:'<span ng-if="row.entity.TcpiManPowerExceptional == true" class="exceptional"><i class="fa fa-star"></i>{{ row.entity.manPowerPI | number:2 }} No need for budget revision</span><span ng-if="row.entity.TcpiManPowerAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{ row.entity.manPowerPI | number:2 }} No need for budget revision</span><span ng-if="row.entity.TcpiManPowerWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i> {{ row.entity.manPowerPI | number:2 }} Need for budget revision</span><span ng-if="row.entity.TcpiManPowerCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;"> {{ row.entity.manPowerPI | number:2 }} Need for budget revision</span>'},
						{ field: 'TCPIcost', displayName: "TCPI - Cost", headerTooltip: "TCPI (To Complete Performance Index) - Cost",
						cellTemplate: '<span ng-if="row.entity.TcpiExceptional == true" class="exceptional"><i class="fa fa-star"></i> {{ row.entity.costPI | number:2 }} No need for budget revision	</span><span ng-if="row.entity.TcpiAcceptable == true" class="acceptable"><i class="fa fa-square"></i>{{ row.entity.costPI | number:2 }} No need for budget revision</span><span ng-if="row.entity.TcpiWarning == true" class="warning"><i class="fa fa-exclamation-triangle"></i>{{ row.entity.costPI | number:2 }} Need for budget revision</span><span ng-if="row.entity.TcpiCritical == true" class="critical"><img src="images/critical.png" style="padding-left:5px !important;">{{ row.entity.costPI | number:2 }} Need for budget revision </span>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Dashboard_Performance_PerformanceIndex");
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