'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("planvsactualvsearnedvaluedirectmanhours", {
		url: '/planvsactualvsearnedvaluedirectmanhours',
		data: {
			dashboards: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/dashboards/labour/planactualearneddirectmanhrsfilter.html',
				controller: 'PlanActualEarnedDirectManHrsController'
			}
		}
	})
}]).controller("PlanActualEarnedDirectManHrsController", ["$rootScope", "$scope", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$q", "$state", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
	"ProjectStatusService", "ProjectSettingsService", function($rootScope, $scope, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $q, $state, ngDialog, GenericAlertService, UserEPSProjectService, 
			$location,PerformanceDashboardService, EpsProjectMultiSelectFactory, TreeService, EstimateToCompleteService, ProjectScheduleService, 
			ProjectStatusService, ProjectSettingsService) {
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

	let task1Completed = false, taskManHours = false, taskPlannedValue = false, taskFull = false;
	$scope.getDirectManHrsDetails = function () {
		task1Completed = false;
		taskManHours = false;
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
					taskManHours = true;
					mergValues($scope.projManPowerValues, $scope.plannedValuesMap);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
				});
			} else {
				console.log("else");
				$scope.projManpowerDetails = data.projManpowerTOs;
				EstimateToCompleteService.manpower($scope.projManpowerDetails);
				console.log("$scope.projManpowerDetails",$scope.projManpowerDetails)
				calculateManhoursTotal($scope.projManpowerDetails);
				taskManHours = true;
				mergValues($scope.projManPowerValues, $scope.plannedValuesMap);
				console.log("$scope.projManpowerDetails", $scope.projManpowerDetails);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting manpower CostCode Wise details", 'Error');
		});
		
		ProjectSettingsService.getProjPlannedValue(req).then(function (data) {
			$scope.plannedValuesMap = data.projPlannedValueTO;
			console.log("$scope.plannedValuesMap",$scope.plannedValuesMap);
			taskPlannedValue = true;
			mergValues($scope.projManPowerValues, $scope.plannedValuesMap);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Planned Value", "Error");
		});
		
	}
	
	
	function mergValues(projManPowerValues, plannedValuesMap) { 
		console.log("MERGING PLANNED VALUE1");
		console.log("taskPlannedValue", taskPlannedValue);
		console.log("taskFull", taskFull);
		if (taskManHours && taskPlannedValue) {
			console.log("MERGING PLANNED VALUE2");
			$scope.planActualEarnedDirManHrs = new Array();
			for (const index_1 in projManPowerValues) {
				for (const index_2 in plannedValuesMap) {
					if(plannedValuesMap[index_2].projId == projManPowerValues[index_1].projId) {
						projManPowerValues[index_1].plannedCost = plannedValuesMap[index_2].cost;
						projManPowerValues[index_1].plannedDirectManHours = plannedValuesMap[index_2].directManHours;
					}
				}
				projManPowerValues[index_1].projName=$scope.searchProject.projName.split(',')[index_1];
				$scope.planActualEarnedDirManHrs.push(projManPowerValues[index_1]);
			}
			console.log("FINAL", $scope.planActualEarnedDirManHrs);
			$scope.gridOptions.data = angular.copy($scope.planActualEarnedDirManHrs);
		}
		//mergeCostManHours($scope.costSchedVarienceData, $scope.projManPowerValues);
	}
	
	
	
	$scope.costItemClick = function (item, expand) {
		TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
	}
	
	function populateCostData(data, level, costTOs, isChild, parent) {
		return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',isChild, parent)
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
						projManPowerValues[i].projName = $scope.searchProject.projName;
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
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'projName', displayName: 'Project Name', headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{
					name: 'plannedDirectManHours', displayName: 'Planned Hours', headerTooltip: "Planned Hours", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'actualHrsTotal', displayName: 'Actual Hours', headerTooltip: "Actual Hours", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'earnedManHoursTotal', displayName: 'Earned Hours', headerTooltip: "Earned Hours", cellFilter: 'number: 2', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Contacts");


		}
	});
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
			template: 'views/help&tutorials/dashboardshelp/labour/planacteardirectmanhrshelp.html',
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
