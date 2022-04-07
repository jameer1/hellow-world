'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantperiodical", {
		url: '/plantperiodical',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/plants/plant.periodicalreport.html',
				controller: 'PlantPeriodicalReportController'
			}
		}
	})
}]).controller("PlantPeriodicalReportController", ["$filter", "$scope", "$q", "$state","ngDialog",
	"dateGroupingService", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "CompanyMultiSelectFactory",
	"PlantReportService", "$interval", "stylesService", "ngGridService","uiGridGroupingConstants","uiGridConstants", "chartService",
	function ($filter, $scope, $q, $state,ngDialog, dateGroupingService, GenericAlertService,
		EpsProjectMultiSelectFactory, CompanyMultiSelectFactory,
		PlantReportService, $interval,  stylesService, ngGridService, uiGridGroupingConstants, uiGridConstants,chartService) {
		$scope.stylesSvc = stylesService;
		$scope.subReport = 'None';
		$scope.subReportCode = "";
		$scope.plantPeriodicalDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCompanyIds = [];

		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Hours';

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
				$scope.plantPeriodicalDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCompanyList = function () {
			var companyReq = {
				"status": 1
			}
			var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
			companyPopUp.then(function (data) {
				$scope.companyNameDisplay = data.selectedCompanies.companyName;
				$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
				$scope.plantPeriodicalDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting companies list", 'Error');
			});
		};
		$scope.subReports = [{
			name: 'Overall Used vs Idle Hrs Summary',
			code: "overall"
		}, {
			name: 'Name Wise Plant Records',
			code: "name"
		}, {
			name: 'CostCode Wise Plant Records',
			code: "costcode"
		}, {
			name: 'Project Wise Plant Records',
			code: "proj"
		}, {
			name: 'EPS Wise Plant Records',
			code: "eps"
		}, {
			name: 'Plant Classification wise Records',
			code: "periodical"
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
			
				if ($scope.subReport.code == "overall") {
					let overAllData = [
						{ field: 'mapValue', displayName: "Period", headerTooltip: "Period", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total", headerTooltip: "Total" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Periodical_Actual_Hours_Overall_UsedvsIdle_HS");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
				}				
				else if($scope.subReport.code == "name"){
					let overAllData = [
						{ field: 'mapValue', displayName: "Plant Name", headerTooltip: "Plant Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Used Hrs", headerTooltip: "Used Hrs" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hrs", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Hours", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Periodical_Actual_Hours_NameWise_PR");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "costcode"){
					let overAllData = [
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID", groupingShowAggregationMenu: false },
						{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
						{ field: 'mapValue', displayName: "Cost Code Item ID", headerTooltip: "Cost Code Item ID", groupingShowAggregationMenu: false },
						{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total", headerTooltip: "Total" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Periodical_Actual_Hours_CostCodeWise_PR");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "proj"){
					let overAllData = [
						{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Used Hrs", headerTooltip: "Used Hrs" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hrs", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Hours", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Periodical_Actual_Hours_ProjWise_PR");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				
			else if($scope.subReport.code == "eps"){
					let overAllData = [
						{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Used Hrs", headerTooltip: "Used Hrs" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Hrs", headerTooltip: "Idle Hrs", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Hours", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Periodical_Actual_Hours_EPSWise_PR");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "periodical"){
					let overAllData = [
						{ field: 'mapValue', displayName: "Plant Classification", headerTooltip: "Plant Classification", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Previous Period", headerTooltip: "Previous Period" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Reporting Period", headerTooltip: "Reporting Period", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total Hours", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Periodical_Actual_Hours_PeriodicalReport_TradeWise");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;	
				}	
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.plantPeriodicalDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				$scope.getPlantDetails();
			}
		};	
		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "overall") {
				generateOverAllReport();
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "name") {
				generateSubReportData("plantId", "plantName");
			} else if ($scope.subReport.code == "costcode") {
				generateSubReportData("costCodeId", "costCodeName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("parentProjId", "parentProjName");
			} else if ($scope.subReport.code == "periodical") {
				if ($scope.periodicSubReport && $scope.periodicSubReport.code) {
					$scope.changePeriodicSubReport();
				}
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
			const periodicalMap = new Array();
			for (const object of $scope.plantPeriodicalDetails) {
				const groupedList = groupByFunction(object.hrsList, reportSettingProp);
				for (const group of groupedList) {
					const mapKey = group.date;
					if (!periodicalMap[mapKey]) {
						periodicalMap[mapKey] = {
							'mapKey': mapKey,
							'mapValue': object.plantName,//$filter('date')(dateGroupingService.getGroupedDate(group), dateFormat),
							'unitOfMeasure': object.unitOfMeasure,
							'usedHrsCount': 0,
							'idleHrsCount': 0,
						};
					}
					for (const val of group.values) {
						periodicalMap[group.date].usedHrsCount += val.usedHrs;
						periodicalMap[group.date].idleHrsCount += val.idleHrs;
					}
				}
			}
			setGraphData(periodicalMap);
		};
		function generateOverAllReport() {
			let subReportMap = [];
			const prevKey = "Previous Period", reportKey = "Reporting Period", uptoDateKey = "Up to Date";
			for (let plantDtl of $scope.plantPeriodicalDetails) {
				if (!subReportMap[prevKey]) {
					subReportMap[prevKey] = {
						"mapKey": prevKey,
						"mapValue": prevKey,
						"unitOfMeasure": plantDtl["unitOfMeasure"],
						"usedHrsCount": 0,
						"idleHrsCount": 0
					};
				}
				if (!subReportMap[reportKey]) {
					subReportMap[reportKey] = {
						"mapKey": reportKey,
						"mapValue": reportKey,
						"unitOfMeasure": plantDtl["unitOfMeasure"],
						"usedHrsCount": 0,
						"idleHrsCount": 0
					};
				}
				if (!subReportMap[uptoDateKey]) {
					subReportMap[uptoDateKey] = {
						"mapKey": uptoDateKey,
						"mapValue": uptoDateKey,
						"unitOfMeasure": plantDtl["unitOfMeasure"],
						"usedHrsCount": 0,
						"idleHrsCount": 0
					};
				}
				subReportMap[prevKey].usedHrsCount += plantDtl["prevUsedHrs"];
				subReportMap[prevKey].idleHrsCount += plantDtl["prevIdleHrs"];
				subReportMap[reportKey].usedHrsCount += plantDtl["currentUsedHrs"];
				subReportMap[reportKey].idleHrsCount += plantDtl["currentIdleHrs"];
				subReportMap[uptoDateKey].usedHrsCount += (plantDtl["prevUsedHrs"] + plantDtl["currentUsedHrs"]);
				subReportMap[uptoDateKey].idleHrsCount += (plantDtl["prevIdleHrs"] + plantDtl["currentIdleHrs"]);
			}
			setGraphData(subReportMap);
		}
		function generateSubReportData(key, value) {
			let subReportMap = [];
			for (let plantDtl of $scope.plantPeriodicalDetails) {
				let mapKey = plantDtl[key];
				let mapValue = plantDtl[value];
				if (!subReportMap[mapKey]) {
					let usedHrsCount = 0, idleHrsCount = 0;
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"unitOfMeasure": plantDtl["unitOfMeasure"],
						"usedHrsCount": usedHrsCount,
						"idleHrsCount": idleHrsCount
					};
					if (key == "costCodeId") {
						subReportMap[mapKey].parentCostCode = plantDtl["parentCostCode"];
						subReportMap[mapKey].parentCostName = plantDtl["parentCostCodeName"];
						subReportMap[mapKey].costName = plantDtl["costCodeDesc"];
					}
				}
				subReportMap[mapKey].usedHrsCount += plantDtl["currentUsedHrs"];
				subReportMap[mapKey].idleHrsCount += plantDtl["currentIdleHrs"];
			}
			setGraphData(subReportMap);
		};
		function setGraphData(subReportMap) {
			console.log(subReportMap);
			$scope.labels = new Array();
			$scope.data = new Array();
			const usedHrs = new Array();
			const idleHrs = new Array();
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				if (subReportMap[index].usedHrsCount > 0 || subReportMap[index].idleHrsCount > 0) {
					usedHrs.push(subReportMap[index].usedHrsCount);
					idleHrs.push(subReportMap[index].idleHrsCount);
					$scope.labels.push(subReportMap[index].mapValue);
					$scope.subReportData.push(subReportMap[index]);
				}
			}
			$scope.totalUsedHrs = 0;
			for(var i=0;i<$scope.subReportData.length;i++){
				$scope.totalUsedHrs += $scope.subReportData[i].usedHrsCount;
			}
			$scope.totalIdleHrs	= 0;
			for(var i=0;i<$scope.subReportData.length;i++){
				$scope.totalIdleHrs += $scope.subReportData[i].idleHrsCount;
			}
			$scope.data.push(usedHrs);
			$scope.data.push(idleHrs);
			initGraph();
			if ($scope.subReport.code == "periodical" && $scope.periodicSubReport.code == "daily") {				
					initGraph1();										
				}
			if($scope.periodicSubReport.code=="weekly"){
				initGraph1();
			}
			if($scope.periodicSubReport.code=="monthly"){
				initGraph1();
			}
			if($scope.periodicSubReport.code=="yearly"){
				initGraph1();
			}
			for (let subReport of $scope.subReportData) {
				subReport.totalUsedIdle = subReport.usedHrsCount + subReport.idleHrsCount;
			}
			
			$scope.gridOptions1.data = angular.copy($scope.subReportData);
			$scope.gridOptions2.data = angular.copy($scope.subReportData);
		
		};
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'parentProjName', displayName: "EPS", visible: false, headerTooltip: 'Enterprise' },
						{ field: 'projName', displayName: "Project", headerTooltip: 'Project' },
						{ field: 'companyName', displayName: "Company", visible: false, headerTooltip: 'Company Name' },
						{ field: 'parentCostCode', displayName: "SubGroupId", visible: false, headerTooltip: 'Costcode SubGroup Id' },
						{ field: 'parentCostCodeName', displayName: "SubGroupName", visible: false, headerTooltip: 'Costcode SubGroup Name' },
						{ field: 'costCodeName', displayName: "CostCode", headerTooltip: 'Costcode Name' },
						{ field: 'costCodeDesc', displayName: "CostCodeDesc", visible: false, headerTooltip: 'Costcode Description' },
						{ field: 'plantTradeName', displayName: "Classification", headerTooltip: 'Trade' },
						{ field: 'plantAssertId', displayName: "PlantId", headerTooltip: 'Plant Id' },
						{ field: 'plantName', displayName: "PlantName", visible: false, headerTooltip: 'Plant Name' },
						{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: 'Unit Of Measure' },
						{ field: 'prevUsedHrs', displayName: "Prev.Used Hrs", visible: false, headerTooltip: 'Previous Used Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'prevIdleHrs', displayName: "Prev.Idle Hrs", visible: false, headerTooltip: 'Previous Idle Hours',groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'prevTotalHrs', displayName: "Prev.Total Hrs", headerTooltip: 'Pervious Total Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'currentUsedHrs', displayName: "Curr.Used Hrs", visible: false, headerTooltip: 'Current Used Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'currentIdleHrs', displayName: "Curr.Idle Hrs", visible: false, headerTooltip: 'Current Idle Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'currentTotalHrs', displayName: "Curr.Total Hrs", headerTooltip: 'Current Total Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'currentPrevUsedHrs', displayName: "Used Hrs", visible: false, headerTooltip: 'UptoDate Used Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'currentPrevIdleHrs', displayName: "Idle Hrs", visible: false, headerTooltip: 'UptoDate Idle Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }, 
						{ field: 'currentPrevTotHrs', displayName: "Total Hrs", headerTooltip: 'UptoDate Total Hours', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				         customTreeAggregationFinalizerFn: function (aggregation) {
					     aggregation.rendered = aggregation.value;
				        } }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Plant_Periodical_AH");
				}
			});
		$scope.getPlantDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds.length <= 0) {
				GenericAlertService.alertMessage("Please select companies to fetch report", 'Warning');
				return;
			}
			var getReq = {
				"projIds": $scope.selectedProjIds,
				"cmpIds": $scope.selectedCompanyIds,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			$scope.plantPeriodicalDetails = [];
			PlantReportService.getPlantsPeriodicalReport(getReq).then(function (data) {
				$scope.plantPeriodicalDetails = data;
				console.log($scope.plantPeriodicalDetails);
				$scope.plantPeriodicalDetails.map(plant => {
					plant.currentPrevTotHrs = plant.prevUsedHrs + plant.currentUsedHrs + plant.prevIdleHrs + plant.currentIdleHrs;
					plant.currentTotalHrs = plant.currentUsedHrs + plant.currentIdleHrs;
					plant.prevTotalHrs = plant.prevUsedHrs + plant.prevIdleHrs;
					plant.currentPrevIdleHrs = plant.prevIdleHrs + plant.currentIdleHrs;
					plant.currentPrevUsedHrs = plant.prevUsedHrs + plant.currentUsedHrs;
				});
				if ($scope.gridOptions)
					$scope.gridOptions.data = $scope.plantPeriodicalDetails;					
					if ($scope.plantPeriodicalDetails.length <= 0) {
						GenericAlertService.alertMessage("Plant Periodical-Actual Hours not available for the search criteria", 'Warning');
					}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Plant details", 'Error');
			});
		};
		$scope.data = [];
		function initGraph1() {
			$scope.series = ['Previous Period', 'Reporting Period'];			
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
		function initGraph() {
			$scope.series = ['Used Hours', 'Idle Hours'];			
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
		$scope.clearSubReportDetails = function () {
			$scope.plantPeriodicalDetails = [];
			$scope.type = "";
			$scope.subReportName = "";
			$scope.subReportCode = "";
		};
		$scope.resetPlantDetails = function () {
			$scope.plantPeriodicalDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.selectedProjIds = [];
			$scope.selectedCompanyIds = [];
			$scope.searchProject = {};
			$scope.companyNameDisplay = null;
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.type = '';
			$scope.subReportName = null;
			$scope.subReportCode = "";
			$scope.subReport = 'None';
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
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
				template: 'views/help&tutorials/reportshelp/planthelp/plantperiodicalhelp.html',
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
