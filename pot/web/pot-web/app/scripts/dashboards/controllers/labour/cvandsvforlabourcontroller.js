
'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("cvandsvforlaboursbubblechart", {
		url: '/cvandsvforlaboursbubblechart',
		data: {
			dashboards: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/dashboards/labour/cvandsvforlabourfilter.html',
				controller: 'CVandSVForLabourController'
			}
		}
	})
}]).controller("CVandSVForLabourController", ["$rootScope", "$scope", "$q", "ngDialog", "GenericAlertService", "UserEPSProjectService", 
	"$location", "PerformanceDashboardService", "EpsProjectMultiSelectFactory","TreeService", "EstimateToCompleteService", "ProjectScheduleService", 
	"ProjectStatusService", "ProjectSettingsService", function($rootScope, $scope, $q, ngDialog, GenericAlertService, UserEPSProjectService, 
			$location,PerformanceDashboardService, EpsProjectMultiSelectFactory, TreeService, EstimateToCompleteService, ProjectScheduleService, 
			ProjectStatusService, ProjectSettingsService) {

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.selectedClientIds = data.searchProject.clientIds
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	
	var performanceList=[];
	var plannedVal=[];
	$scope.projCostStmtDtls = [];
	$scope.projCostValues = [];
	$scope.plannedValuesMap = [];
	$scope.projManPowerHours = [];
	
	let task1Completed = false, task2Completed = false, taskPlannedValue = false, taskFull = false;

	$scope.labourCvSvTabs = new Array();
	$scope.getCVandSvDetails = function () {
		task1Completed = false;
		task2Completed = false;
		taskPlannedValue = false;
		taskFull = false;
		var req ={
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
					task1Completed = true;
					mergingValues($scope.projManPowerValues, $scope.plannedValuesMap);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
				});
			} else {
				console.log("else");
				$scope.projManpowerDetails = data.projManpowerTOs;
				EstimateToCompleteService.manpower($scope.projManpowerDetails);
				calculateManhoursTotal($scope.projManpowerDetails);
				task1Completed = true;
				console.log("$scope.projManpowerDetails", $scope.projManpowerDetails);
				mergingValues($scope.projManPowerValues, $scope.plannedValuesMap);
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting manpower CostCode Wise details", 'Error');
		});
		ProjectSettingsService.getProjPlannedValue(req).then(function (data) {
			$scope.plannedValuesMap = data.projPlannedValueTO;
			console.log("$scope.plannedValuesMap",$scope.plannedValuesMap);
			taskPlannedValue = true;
			mergingValues($scope.projManPowerValues, $scope.plannedValuesMap);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Planned Value", "Error");
		});
	}
	
	function mergingValues(projManPowerValues, plannedValuesMap) {
		if (task1Completed && taskPlannedValue) {
			$scope.projManPowerHours = new Array();
			for (const index in projManPowerValues) {
				for (const index1 in plannedValuesMap) {
					if (plannedValuesMap[index1].projId == projManPowerValues[index].projId) {
						projManPowerValues[index].plannedManHoursTotal = plannedValuesMap[index1].directManHours;
						projManPowerValues[index].costVarianceHrs = parseFloat(projManPowerValues[index].earnedManHoursTotal - projManPowerValues[index].actualHrsTotal).toFixed(2);
						projManPowerValues[index].scheduleVarianceHrs = parseFloat(projManPowerValues[index].earnedManHoursTotal - projManPowerValues[index].plannedManHoursTotal).toFixed(2);
						projManPowerValues[index].budget = 0;
						/*if(projManPowerValues[index].revisedHrsTotal != null && projManPowerValues[index].revisedHrsTotal != 0) {
							projManPowerValues[index].budget = 	projManPowerValues[index].revisedHrsTotal;
						} else {
							projManPowerValues[index].budget = 	projManPowerValues[index].originalHrsTotal;
						}
						
						if (projManPowerValues[index].budget > 100)
							projManPowerValues[index].budget = projManPowerValues[index].budget % 100;
						if (projManPowerValues[index].budget > 50)
							projManPowerValues[index].budget = projManPowerValues[index].budget / 2;*/
					}
				}
				projManPowerValues[index].epsName=$scope.searchProject.parentName.split(',')[index];
				projManPowerValues[index].projName=$scope.searchProject.projName.split(',')[index];
				$scope.projManPowerHours.push(projManPowerValues[index]);
			}
			setGraphData($scope.projManPowerHours)
		}
	}
	
	
	$scope.costItemClick = function (item, expand) {
		TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
	}
	
	function populateCostData(data, level, costTOs, isChild, parent) {
		return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOs',isChild, parent);
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
					"projId": $scope.projId
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

	function setGraphData(calculatedValues) {
		const graphData = new Array();
		const series = new Array();
		calculatedValues.map(o => {
			graphData.push([{
				x: o.costVarianceHrs,
				y: o.scheduleVarianceHrs,
				r: o.budget
			}]);
			series.push(o.projName);
		});
		$scope.labourCvSvTabs.unshift({ 'data': graphData, 'series': series });
		initGraph();
	}

	function initGraph() {
		$scope.colors = ['#00ff00', '#ff0000', '#0000ff', '#000000', '#abcdef','#f70000','#003af7', '#f79000', '#f7eb00', '#00f780', '#00d2f7','#f70077' ];
		$scope.options = {
			title: {
				display: true,
				text: 'Cost and Schedule Variance for Labour Bubble Chart'
			},
			legend: {
				display: true,
			},
			scales: {
				xAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Cost Variance'
					},
					ticks: {
						beginAtZero: true
					}

				}],
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Schedule Variance'
					},
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	}

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
			template: 'views/help&tutorials/dashboardshelp/labour/cvandsvforlabourhelp.html',
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
