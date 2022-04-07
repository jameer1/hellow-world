'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantidlehrs", {
		url: '/plantidlehrs',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/plants/plant.idllehrsreport.html',
				controller: 'PlantIdleHrsReportController'
			}
		}
	})
}]).controller("PlantIdleHrsReportController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "GenericAlertService", "MultiProjPlantMasterDetailsFactory", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "dateGroupingService", "CostCodeMultiSelectFactory", "PlantReportService", "CompanyMultiSelectFactory", "stylesService", "ngGridService","uiGridGroupingConstants","uiGridConstants", "chartService",
	function ($rootScope, $scope, $q, $state, $filter, ngDialog, GenericAlertService,
		MultiProjPlantMasterDetailsFactory, EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, dateGroupingService,
		CostCodeMultiSelectFactory, PlantReportService, CompanyMultiSelectFactory, stylesService, ngGridService,uiGridGroupingConstants, uiGridConstants, chartService) {
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.data = [];
		$scope.labels = [];
		$scope.subReport = "None";
		$scope.subReportCode = "";
		$scope.plantDetails = [];
		var series = ['Idle Hours'];
		let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.$watch('fromDate', function () {
			$scope.checkErr();
			$scope.clearSubReportDetails();
		});
		$scope.$watch('toDate', function () {
			$scope.checkErr();
			$scope.clearSubReportDetails();
		});
		$scope.checkErr = function () {
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
		};

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCrewList = function () {
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select project to get crews", 'Warning');
				return;
			}
			var crewReq = {
				"status": 1,
				"projIds": $scope.selectedProjIds
			};
			var crewSerivcePopup = MultipleCrewSelectionFactory.crewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				$scope.crewNameDisplay = data.selectedCrews.crewName;
				$scope.selectedCrewIds = data.selectedCrews.crewIds;
				$scope.plantDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
			});
		};
		$scope.getCostCodes = function () {
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select project to get CostCodes", 'Warning');
				return;
			}
			var costCodeReq = {
				"status": 1,
				"projIds": $scope.selectedProjIds
			};
			var costCodePopUp = CostCodeMultiSelectFactory.getMultiProjCostCodes(costCodeReq);
			costCodePopUp.then(function (data) {
				$scope.costCodeNameDisplay = data.selectedCostCodes.costCodesName;
				$scope.selectedCostCodeIds = data.selectedCostCodes.costCodeIds;
				$scope.plantDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
			})
		};
		$scope.getCompanyList = function () {
			var companyReq = {
				"status": 1
			}
			var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
			companyPopUp.then(function (data) {
				$scope.companyNameDisplay = data.selectedCompanies.companyName;
				$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
				$scope.plantDetails = [];
			})
		};
		$scope.getPlantList = function () {
			var plantReq = {
				"status": 1,
				"projIds": $scope.selectedProjIds
			}
			var plantPopUp = MultiProjPlantMasterDetailsFactory.getPlantMasterDetails(plantReq);
			plantPopUp.then(function (data) {
				$scope.plantNameDisplay = data.selectedPlants.plantName;
				$scope.selectedPlantIds = data.selectedPlants.selectedPlantIds;
				$scope.plantDetails = [];
			})
		};
		$scope.subReports = [{
			name: 'Plant Wise - Idle Hours',
			code: "plant"
		}, {
			name: 'Cost Code Wise - Idle Hours',
			code: "costcode"
		}, {
			name: 'Project Wise - Plant Utilization',
			code: "proj"
		}, {
			name: 'EPS Wise - Plant Utilization',
			code: "eps"
		}, {
			name: 'Periodical - Plant Wise Idle Hours',
			code: "idleHours"
		}, {
			name: 'Periodical - Plant Wise Idle Amount',
			code: "idleAmount"
		}];
		$scope.periodicSubReports = [{
			name: 'Daily',
			code: "daily"
		}, {
			name: 'Weekly',
			code: "weekly"
		}, {
			name: 'Monthly',
			code: "monthly"
		}, {
			name: 'Yearly',
			code: "yearly"
		}];
		$scope.periodicSubReport = $scope.periodicSubReports[0];

		$scope.changeSubReport = function () {
			
			if ($scope.subReport.code == "plant") {
					let overAllData = [
						{ field: 'mapValue', displayName: "Plant Name", headerTooltip: "Plant Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'idleAmountCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Amount", headerTooltip: "Amount" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions2 = ngGridService.initGrid($scope, overAllData, data,"Report_Plant_Plant_IdleHours_PlantWise_IdleHours");
					$scope.gridOptions2.showColumnFooter = true;
					$scope.gridOptions2.gridMenuCustomItems = false;					
				}								
				else if($scope.subReport.code == "costcode"){
					let overAllData = [
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID", groupingShowAggregationMenu: false },
						{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
						{ field: 'mapValue', displayName: "Cost Code Item ID", headerTooltip: "Cost Code Item ID", groupingShowAggregationMenu: false },
						{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },					
						{ field: 'plantClassName', displayName: "Plant Classification", headerTooltip: "Plant Classification", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'idleAmountCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Amount", headerTooltip: "Amount" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Report_Plant_Plant_IdleHours_CostCodeWise_IdleHours");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "proj"){
					let overAllData = [
						{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'idleAmountCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Amount", headerTooltip: "Amount" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Report_Plant_Plant_IdleHours_ProjectWise_PlantUtilization");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				
			else if($scope.subReport.code == "eps"){
					let overAllData = [
						{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'idleAmountCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Amount", headerTooltip: "Amount" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Report_Plant_Plant_IdleHours_EPSWise_PlantUtilization");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "idleHours"){
					let overAllData = [
						{ field: 'roundOffDate', displayName: 'Period ', headerTooltip: 'Period', groupingShowAggregationMenu: false },
						{ field: 'plantClassName', displayName: "Plant Classification Name", headerTooltip: "Plant Classification Name", groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hours", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false }
					]
					let data = [];
					$scope.gridOptions3 = ngGridService.initGrid($scope, overAllData, data,"Report_Plant_Plant_IdleHours_Periodical_PlantWise_IH");
					$scope.gridOptions3.showColumnFooter = true;
					$scope.gridOptions3.gridMenuCustomItems = false;	
				}	
				else if($scope.subReport.code == "idleAmount"){
					let overAllData = [
						{ field: 'roundOffDate', displayName: 'Period', headerTooltip: 'Period', groupingShowAggregationMenu: false },
						{ field: 'plantClassName', displayName: "Plant Classification Name", headerTooltip: "Plant Classification Name", groupingShowAggregationMenu: false },
						{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Amount", headerTooltip: "Idle Amount", groupingShowAggregationMenu: false }
					]
					let data = [];
					$scope.gridOptions3 = ngGridService.initGrid($scope, overAllData, data,"Report_Plant_Plant_IdleHours_Periodical_PlantWise_IdleAmount");
					$scope.gridOptions3.showColumnFooter = true;
					$scope.gridOptions3.gridMenuCustomItems = false;
					
				}	
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.plantDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				$scope.getPlantDetails();
			}
		};
		function prepareSubReport() {
			if ($scope.subReport.code == "plant") {
				generatePlantWiseSubReportData("plantId", "plantName");
			} else if ($scope.subReport.code == "costcode") {
				generateSubReportData("costId", "costCode");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			} else if ($scope.subReport.code == "idleHours" || $scope.subReport.code == "idleAmount") {
				if ($scope.periodicSubReport && $scope.periodicSubReport.code) {
					$scope.changePeriodicSubReport();
				}
			}
		};
		function generatePlantWiseSubReportData(key, value) {
			let subReportMap = [];
			for (let plantDtl of $scope.plantIdleHrsDetails) {
				let mapKey = plantDtl.displayNamesMap[key];
				let mapValue = plantDtl.displayNamesMap[value];
				if (!subReportMap[mapKey]) {
					let idleAmountCount = 0, idleHrsCount = 0;
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"unitOfMeasure": plantDtl.displayNamesMap["unitOfMeasure"],
						"idleHrsCount": idleHrsCount,
						"idleAmountCount": idleAmountCount
					};
					if (key == "costId") {
						subReportMap[mapKey].parentCostCode = plantDtl.displayNamesMap["parentCostCode"];
						subReportMap[mapKey].parentCostName = plantDtl.displayNamesMap["parentCostName"];
						subReportMap[mapKey].costName = plantDtl.displayNamesMap["costName"];
					}
				}
				subReportMap[mapKey].idleHrsCount += parseFloat(plantDtl.displayNamesMap["idleHrs"]);
				const unitOfRate = plantDtl.displayNamesMap["unitOfRate"];
				if (unitOfRate)
					subReportMap[mapKey].idleAmountCount += (parseFloat(plantDtl.displayNamesMap["idleHrs"]) * parseFloat(unitOfRate));
			}			
			setGraphData(subReportMap);
			$scope.series = series;
			initGraph();
		};
		function generateSubReportData(key, value) {
			$scope.labels = new Array();
			let costClassMap = new Map();
			let plantClassIds = new Set(), plantClassNames = new Set();
			for (let plantDtl of $scope.plantIdleHrsDetails) {
				plantClassIds.add(plantDtl.displayNamesMap.plantClassId);
				plantClassNames.add(plantDtl.displayNamesMap.plantClassName);
				let mapKey = plantDtl.displayNamesMap[key];
				let mapValue = plantDtl.displayNamesMap[value];
				let subReportData = {
					"mapKey": mapKey,
					"mapValue": mapValue,
					"unitOfMeasure": plantDtl.displayNamesMap.unitOfMeasure,
					"plantClassId": plantDtl.displayNamesMap.plantClassId,
					"plantClassName": plantDtl.displayNamesMap.plantClassName,
					"idleHrsCount": parseFloat(plantDtl.displayNamesMap.idleHrs),
					"idleAmountCount": 0
				};
				const unitOfRate = plantDtl.displayNamesMap.unitOfRate;
				if (unitOfRate)
					subReportData.idleAmountCount = (parseFloat(plantDtl.displayNamesMap.idleHrs) * parseFloat(unitOfRate));
				if (key == "costId") {
					subReportData.parentCostCode = plantDtl.displayNamesMap["parentCostCode"];
					subReportData.parentCostName = plantDtl.displayNamesMap["parentCostName"];
					subReportData.costName = plantDtl.displayNamesMap["costName"];
				}
				if (costClassMap.has(mapKey)) {
					let existingCost = costClassMap.get(mapKey).find(function (data) {
						return subReportData.plantClassId === data.plantClassId;
					});
					if (existingCost) {
						existingCost.idleHrsCount += subReportData.idleHrsCount;
						existingCost.idleAmountCount += subReportData.idleAmountCount;
					} else {
						costClassMap.get(mapKey).push(subReportData);
					}
				} else {
					costClassMap.set(mapKey, [subReportData]);
					$scope.labels.push(subReportData.mapValue);
				}
			}
			$scope.series = Array.from(plantClassNames);
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();
			$scope.data = new Array();
			$scope.subReportData = new Array();
			for (let classId of plantClassIds) {
				let subReportMap = new Array();
				costClassMap.forEach(v => {
					let existing = v.find(function (data) {
						return classId === data.plantClassId;
					});
					if (existing) {
						$scope.subReportData.push(existing);
						subReportMap.push(existing.idleHrsCount);
					} else {
						subReportMap.push(0);
					}
				});
				
				$scope.data.push(subReportMap);
				$scope.gridOptions1.data = angular.copy($scope.subReportData);
			}
		};
		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartable';
			let groupByFunction = null;
			let dateFormat;
			let reportSettingProp;
			switch ($scope.periodicSubReport.code) {
				case "daily":
					groupByFunction = dateGroupingService.groupByDate;
					dateFormat = "dd-MMM-yyyy";
					break;
				case "weekly":
					groupByFunction = dateGroupingService.groupByWeek;
					dateFormat = "dd-MMM-yyyy";
					reportSettingProp = 'Monday';
					break;
				case "monthly":
					groupByFunction = dateGroupingService.groupByMonth;
					dateFormat = "MMM-yyyy";
					reportSettingProp = '';
					break;
				case "yearly":
					groupByFunction = dateGroupingService.groupByYear;
					dateFormat = "yyyy";
					reportSettingProp = '';
					break;
				default:
					break;
			}
			// Group by plant trade
			let tradeMap = new Array();
			let tradeIdsArray = new Array();
			let tradeNamesArray = new Array();
			let plantsList = new Array();
			for (const plantDtl of $scope.plantIdleHrsDetails) {
				let idleNum = 0;
				if ($scope.subReport.code == "idleHours") {
					idleNum = parseFloat(plantDtl.displayNamesMap['idleHrs']);
				} else if ($scope.subReport.code == "idleAmount") {
					const unitOfRate = plantDtl.displayNamesMap["unitOfRate"];
					if (unitOfRate)
						idleNum = (parseFloat(plantDtl.displayNamesMap["idleHrs"]) * parseFloat(unitOfRate));
				}
				if (idleNum == 0) {
					continue;
				}
				let existing = plantsList.find(function (data) {
					return data.plantClassId === plantDtl.displayNamesMap.plantClassId && data.mapValue === plantDtl.displayNamesMap.workDairyDate;
				});
				if (existing) {
					existing.idleHrsCount += idleNum;
				} else {
					const plantClassObj = {
						'date': new Date(plantDtl.displayNamesMap['workDairyDate']).getTime(),
						'mapValue': plantDtl.displayNamesMap['workDairyDate'],
						'idleHrsCount': idleNum,
						'plantClassId': plantDtl.displayNamesMap['plantClassId'],
						'plantClassName': plantDtl.displayNamesMap['plantClassName'],
						'measureUnit': plantDtl.displayNamesMap['unitOfMeasure']
					};
					plantsList.push(plantClassObj);
					if (!tradeIdsArray.includes(plantClassObj.plantClassId)) {
						tradeIdsArray.push(plantClassObj.plantClassId);
						tradeNamesArray.push(plantClassObj.plantClassName);
					}
				}
			}
			const groupedList = groupByFunction(plantsList, reportSettingProp);
			groupedList.sort(function (a, b) { return a.date - b.date; });
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();
			$scope.labels = new Array();
			$scope.series = tradeNamesArray;
			$scope.subReportData = new Array();
			let dateWiseTradeValues = new Object();
			for (const group of groupedList) {
				const key = group.date;
				dateWiseTradeValues[key] = new Object();
				for (const tradeName of tradeNamesArray) {
					dateWiseTradeValues[key][tradeName] = 0;
				}
				for (const trade of group.values) {
					const dateKey = $filter('date')(dateGroupingService.getGroupedDate(trade), dateFormat);
					const existing = $scope.subReportData.find(function (data) {
						return data.mapValue === key && data.plantClassId == trade.plantClassId;
					});
					if (existing) {
						existing.idleHrsCount += trade.idleHrsCount;
						dateWiseTradeValues[key][trade.plantClassName] = existing.idleHrsCount;
					} else {
						trade.roundOffDate = $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
						trade.mapValue = key;
						dateWiseTradeValues[group.date][trade.plantClassName] = trade.idleHrsCount;
						$scope.subReportData.push(trade);
					}
				}
				$scope.labels.push($filter('date')(dateGroupingService.getGroupedDate(group), dateFormat));
				$scope.gridOptions3.data = angular.copy($scope.subReportData);
			}
			$scope.data = new Array();
			for (const tradeName of tradeNamesArray) {
				const individualTradeValues = new Array();
				for (const key in dateWiseTradeValues) {
					individualTradeValues.push(dateWiseTradeValues[key][tradeName]);
				}
				$scope.data.push(individualTradeValues);
			}
		};
		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			const idleHrsCountArr = new Array();
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				idleHrsCountArr.push(subReportMap[index].idleHrsCount);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}						
			$scope.gridOptions2.data = angular.copy($scope.subReportData);
			$scope.data = idleHrsCountArr;
			$scope.series = series;
			initGraph();
		};
		function initGraph() {
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();
			$scope.options.tooltips = {
				callbacks: {
					label: function (tooltipItem, data) {
						return series[tooltipItem.datasetIndex] + ":" + tooltipItem.yLabel;
					}
				}
			}
		};
		$scope.getPlantDetails = function () {
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select Project", 'Warning');
				return;
			}
			if ($scope.selectedCrewIds == undefined || $scope.selectedCrewIds == null) {
				GenericAlertService.alertMessage("Please select Crew", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds == undefined || $scope.selectedCompanyIds == null) {
				GenericAlertService.alertMessage("Please select Company", 'Warning');
				return;
			}
			if ($scope.selectedPlantIds == undefined || $scope.selectedPlantIds == null) {
				GenericAlertService.alertMessage("Please select plants", 'Warning');
				return;
			}
			if ($scope.selectedCostCodeIds == undefined || $scope.selectedCostCodeIds == null) {
				GenericAlertService.alertMessage("Please select cost codes", 'Warning');
				return;
			}
			if (!$scope.fromDate) {
				GenericAlertService.alertMessage("Please select the from date to fetch report", 'Warning');
				return;
			}
			if (!$scope.toDate) {
				GenericAlertService.alertMessage("Please select the to date to fetch report", 'Warning');
				return;
			}	
			var req = {
				"projIds": $scope.selectedProjIds,
				"crewIds": $scope.selectedCrewIds,
				"cmpIds": $scope.selectedCompanyIds,
				"plantIds": $scope.selectedPlantIds,
				"costCodeIds": $scope.selectedCostCodeIds,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			PlantReportService.getPlantsIdlePeriodicalReport(req).then(function (data) {
				$scope.plantIdleHrsDetails = data;
				
				for (let plant of $scope.plantIdleHrsDetails) {
				 if(plant.displayNamesMap.unitOfRate == undefined ){
				   plant.idleAmt = parseFloat(plant.displayNamesMap.idleHrs * (plant.displayNamesMap.unitOfRate = "")).toFixed(2);
				 }
				   plant.idleAmt = parseFloat(plant.displayNamesMap.idleHrs * plant.displayNamesMap.unitOfRate).toFixed(2);				  
				}
				$scope.gridOptions.data = $scope.plantIdleHrsDetails;
				if ($scope.gridOptions.data.length <= 0) {
					GenericAlertService.alertMessage("Plant Idle Hours not available for the search criteria", 'Warning');
				}
			}, function (_err) {
				GenericAlertService.alertMessage("Error occured while gettting Plant details", 'Error');
			});
		};
		$scope.clearSubReportDetails = function () {
			$scope.plantIdleHrsDetails = [];
			$scope.type = "";
			$scope.subReportCode = "";
			$scope.subReportName = "";
			$scope.periodicSubReportCode = "";
			$scope.subReport = 'None';
		};
		$scope.resetPlantDetails = function () {
			$scope.plantDetails = [];
			$scope.plantNameDisplay = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.subReportName = null;
			$scope.selectedProjIds = null;
			$scope.crewNameDisplay = null;
			$scope.companyNameDisplay = null;
			$scope.costCodeNameDisplay = null;
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.subReportCode = "";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};

		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'displayNamesMap.workDairyDate', displayName: "Date", headerTooltip: 'Date' },
						{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: 'EPS Name' },
						{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: 'Project Name' },
						{ field: 'displayNamesMap.crewName', displayName: "Crew", headerTooltip: 'Crew Name' },
						{ field: 'displayNamesMap.plantAssertId', displayName: "Plant ID", headerTooltip: 'Plant ID ' },
						{ field: 'displayNamesMap.plantName', displayName: "Plant Name", headerTooltip: 'Plant Name' },
						{ field: 'displayNamesMap.cmpName', displayName: "Company", headerTooltip: 'Company Name' },
						{ field: 'displayNamesMap.plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification' },
						{ field: 'displayNamesMap.parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: 'Cost Code Sub Group ID' },
						{ field: 'displayNamesMap.parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: 'Cost Code Sub Group Name', visible: false },
						{ field: 'displayNamesMap.costCode', displayName: "Cost Code Item ID", headerTooltip: 'Cost Code Item ID' },
						{ field: 'displayNamesMap.costName', displayName: "Cost Code Desc", headerTooltip: 'Cost Code Description', visible: false },
						{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit of Measure", headerTooltip: 'Unit of Measure', visible: false },
						{ field: 'displayNamesMap.idleHrs', displayName: "Idle Time", headerTooltip: 'Idle Time' },
						{
							name: 'displayNamesMap.unitOfRate', cellFilter: 'number: 2',
							cellTemplate: '<div>{{row.entity.displayNamesMap.unitOfRate | number: 2 }}</div>',
							displayName: "Idle Rate", headerTooltip: 'Idle Rate'
						},
						{ field: 'idleAmt', displayName: "Idle Amt", headerTooltip: 'Idle Amount' },
						{ field: 'displayNamesMap.currency', displayName: "Currency", headerTooltip: 'Currency' }
					];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [],"Reports_Plant_PlantIdleHours");
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
					template: 'views/help&tutorials/reportshelp/planthelp/plantidlehrshelp.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom1',
					scope: $scope,
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function ($scope) {
			
					}]
				});
				return deferred.promise;
			}
	}]);
