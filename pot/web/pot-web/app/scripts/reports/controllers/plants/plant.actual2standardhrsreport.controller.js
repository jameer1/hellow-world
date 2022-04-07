'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantactualvsstandardhrs", {
		url: '/plantactualvsstandardhrs',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/plants/plant.actual2standardhrsreport.html',
				controller: 'PlantActualVsStandardHrsReportController'
			}
		}
	})
}]).controller("PlantActualVsStandardHrsReportController", ["$scope", "$q", "$state","ngDialog", "$filter", "GenericAlertService", "dateGroupingService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "CostCodeMultiSelectFactory", "PlantReportService", "CompanyMultiSelectFactory", "stylesService", "ngGridService","uiGridGroupingConstants","uiGridConstants", "chartService",
	function ($scope, $q, $state, ngDialog,$filter, GenericAlertService, dateGroupingService,
		EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, CostCodeMultiSelectFactory, PlantReportService, CompanyMultiSelectFactory, stylesService, ngGridService,uiGridGroupingConstants, uiGridConstants, chartService) {
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Hours';
		$scope.labels = [];
		$scope.parseFloat = parseFloat;
		$scope.subReport = "None";
		$scope.plantDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCrewIds = [];
		$scope.selectedCompanyIds = [];
		$scope.selectedCostCodeIds = [];
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
				$scope.clearSubReportDetails();
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
		$scope.subReports = [{
			name: 'Plant Classification Wise Utilisation Report',
			code: "name"
		}, {
			name: 'Date Wise Utilisation Hours',
			code: "date"
		}, {
			name: 'Project Wise Plant utilization hours',
			code: "proj"
		}, {
			name: 'EPS Wise Plant utilization hours',
			code: "eps"
		}, {
			name: 'Periodical Report',
			code: "period"
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
			if ($scope.subReport.code == "name") {
					let overAllData = [
						{ field: 'plantAssertId', displayName: "Plant Classification ID", headerTooltip: "Plant Classification ID", groupingShowAggregationMenu: false },
						{ field: 'mapValue', displayName: "Plant Classification Name", headerTooltip: "Plant Classification Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalActualhrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Actual Hours", headerTooltip: "Total Actual Hours" , groupingShowAggregationMenu: false},
						{ field: 'standardActualHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Standard Hours", headerTooltip: "Total Standard Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"ReportsPlant_PlantActualVsStandard_HR_Plant_ClassificationWise_UtilisationReport");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
				}	
				else if ($scope.subReport.code == "date") {
					let overAllData = [
						{ field: 'mapValue', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalActualhrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Actual Hours", headerTooltip: "Total Actual Hours" , groupingShowAggregationMenu: false},
						{ field: 'standardActualHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Standard Hours", headerTooltip: "Total Standard Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"ReportsPlant_PlantActualVsStandard_HR_DateWise_UtilisationHours");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
				}		
				else if ($scope.subReport.code == "proj") {
					let overAllData = [
						{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalActualhrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Actual Hours", headerTooltip: "Total Actual Hours" , groupingShowAggregationMenu: false},
						{ field: 'standardActualHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Standard Hours", headerTooltip: "Total Standard Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"ReportsPlant_PlantActualVsStandard_HR_Plant_ProjectWisePlant_utilization_hours");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
				}		
				else if ($scope.subReport.code == "eps") {
					let overAllData = [
						{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalActualhrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Actual Hours", headerTooltip: "Total Actual Hours" , groupingShowAggregationMenu: false},
						{ field: 'standardActualHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Standard Hours", headerTooltip: "Total Standard Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"ReportsPlant_PlantActualVsStandard_HR_EPSWisePlant_utilization_hours");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
				}			
				else if ($scope.subReport.code == "period") {
					let overAllData = [
						{ field: 'mapValue', displayName: "Period", headerTooltip: "Period", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalActualhrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Actual Hours", headerTooltip: "Total Actual Hours" , groupingShowAggregationMenu: false},
						{ field: 'standardActualHrs', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Standard Hours", headerTooltip: "Total Standard Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"ReportsPlant_PlantActualVsStandard_HR_Periodical_Report");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
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
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "date") {
				generateSubReportData("workDairyDate", "workDairyDate");
			} else if ($scope.subReport.code == "name") {
				generateSubReportData("plantClassId", "plantClassName");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			} else if ($scope.subReport.code == "period") {
				if ($scope.periodicSubReport && $scope.periodicSubReport.code) {
					$scope.changePeriodicSubReport();
				}
			}
		};
		function generateSubReportData(key, value) {
			let subReportMap = [];
			for (let plantDtl of $scope.plantDetails) {
				let mapKey = plantDtl.displayNamesMap[key];
				let mapValue = plantDtl.displayNamesMap[value];
				if (!subReportMap[mapKey]) {
					let usedHrsCount = 0, idleHrsCount = 0;
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"unitOfMeasure": plantDtl.displayNamesMap["unitOfMeasure"],
						"usedHrsCount": usedHrsCount,
						"idleHrsCount": idleHrsCount,
						"standardHrs": parseFloat(plantDtl.displayNamesMap["standardHrs"]),
						"plantIds": new Set(),
						"standardActualHrs": 0
					};
					if ($scope.subReportCode == 'name') {
						subReportMap[mapKey].plantAssertId = plantDtl.displayNamesMap['plantAssertId'];
					}
				}
				subReportMap[mapKey].plantIds.add(plantDtl.displayNamesMap["plantId"]);
				subReportMap[mapKey].usedHrsCount += parseFloat(plantDtl.displayNamesMap["usedHrs"]);
				subReportMap[mapKey].idleHrsCount += parseFloat(plantDtl.displayNamesMap["idleHrs"]);
			}
			setGraphData(subReportMap);
		};
		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartable';
			let groupByFunction = null;
			let dateFormat;
			let reportSettingProp;
			// Get date format and group function
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
			// Set date property
			for (const plantDtl of $scope.plantDetails) {
				plantDtl.date = plantDtl.displayNamesMap['workDairyDate'];
			}
			// groupedList contains group and respective values
			const groupedList = groupByFunction($scope.plantDetails, reportSettingProp);
			const subReportMap = new Array();
			for (const group of groupedList) {
				const mapKey = group.date;
				const mapValue = $filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
				// Iterate every group and add the values as required
				for (const plantDtl of group.values) {
					if (!subReportMap[mapKey]) {
						let usedHrsCount = 0, idleHrsCount = 0;
						subReportMap[mapKey] = {
							"mapKey": mapKey,
							"mapValue": mapValue,
							"unitOfMeasure": plantDtl.displayNamesMap["unitOfMeasure"],
							"standardHrs": parseFloat(plantDtl.displayNamesMap["standardHrs"]),
							"usedHrsCount": usedHrsCount,
							"idleHrsCount": idleHrsCount,
							"plantIds": new Set(),
							"standardActualHrs": 0
						};
					}
					subReportMap[mapKey].plantIds.add(plantDtl.displayNamesMap["plantId"]);
					subReportMap[mapKey].usedHrsCount += parseFloat(plantDtl.displayNamesMap["usedHrs"]);
					subReportMap[mapKey].idleHrsCount += parseFloat(plantDtl.displayNamesMap["idleHrs"]);
				}
			}
			setGraphData(subReportMap);
		};
		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			const usedHrs = new Array();
			const idleHrs = new Array();
			$scope.subReportData = new Array();
			for (const index in subReportMap) {
				subReportMap[index].totalActualhrs = subReportMap[index].usedHrsCount + subReportMap[index].idleHrsCount;
				if (subReportMap[index].plantIds)
					subReportMap[index].standardActualHrs = subReportMap[index].plantIds.size * subReportMap[index].standardHrs;
				else
					subReportMap[index].standardActualHrs = 0;
				usedHrs.push(subReportMap[index].totalActualhrs);
				idleHrs.push(subReportMap[index].standardActualHrs);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.data.push(usedHrs);
			$scope.data.push(idleHrs);
			$scope.gridOptions1.data = angular.copy($scope.subReportData);
			initGraph();
		};
		var series = ['Actual Hours', 'Standard Hours'];
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
			$scope.options.scales.xAxes[0].stacked = false;
			$scope.options.scales.yAxes[0].stacked = false;
		};
		$scope.getPlantDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds.length <= 0) {
				GenericAlertService.alertMessage("Please select companies to fetch report", 'Warning');
				return;
			}
			if ($scope.selectedCrewIds.length <= 0) {
				GenericAlertService.alertMessage("Please select crews to fetch report", 'Warning');
				return;
			}
			if ($scope.selectedCostCodeIds.length <= 0) {
				GenericAlertService.alertMessage("Please select cost codes to fetch report", 'Warning');
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
				"costCodeIds": $scope.selectedCostCodeIds,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate,
			};
			$scope.plantDetails = [];
			PlantReportService.getPlantsStandardActual(req).then(function (data) {
				$scope.plantDetails = data;
				for (let plant of $scope.plantDetails) {
					plant.usedHrs = parseFloat(plant.displayNamesMap.usedHrs).toFixed(2);
					plant.idleHrs = parseFloat(plant.displayNamesMap.idleHrs).toFixed(2);
					plant.standardHrs = plant.displayNamesMap.standardHrs;
					plant.perUtilCalc = ((parseFloat(plant.usedHrs) + parseFloat(plant.idleHrs)) / parseFloat(plant.standardHrs)) * 100;
					plant.perUtilCalc = parseFloat(plant.perUtilCalc).toFixed(2);
					plant.perUtil = ifNaN(plant.perUtilCalc);
				}
				$scope.gridOptions.data = $scope.plantDetails;
				if ($scope.plantDetails.length <= 0) {
					GenericAlertService.alertMessage("Plant Actual vs Standard Hours Records not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Plant details", 'Error');
			});
		};
		function ifNaN(value) {
			return isNaN(value) ? 0 : value;
		}
		$scope.clearSubReportDetails = function () {
			$scope.plantDetails = [];
			$scope.type = "";
			$scope.subReportName = "";
			$scope.subReportCode = "";
		};
		$scope.resetPlantDetails = function () {
			$scope.plantDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.selectedCompanyIds = [];
			$scope.selectedCostCodeIds = [];
			$scope.searchProject = {};
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.companyNameDisplay = null;
			$scope.costCodeNameDisplay = null;
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.type = "";
			$scope.subReportName = "";
			$scope.subReportCode = "";
			$scope.subReport = "None";
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
						{ field: 'displayNamesMap.plantAssertId', displayName: "Plant ID", headerTooltip: 'Plant ID ' },
						{ field: 'displayNamesMap.plantName', displayName: "Plant Name", headerTooltip: 'Plant Name' },
						{ field: 'displayNamesMap.plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification' },
						{ field: 'displayNamesMap.cmpName', displayName: "Company", headerTooltip: 'Company Name' },
						{ field: 'displayNamesMap.crewName', displayName: "Crew", headerTooltip: 'Crew Name' },
						{ field: 'displayNamesMap.parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: 'Cost Code Sub Group ID' },
						{ field: 'displayNamesMap.parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: 'Cost Code Sub Group Name', visible: false },
						{ field: 'displayNamesMap.costCode', displayName: "Cost Code Item ID", headerTooltip: 'Cost Code Item ID' },
						{ field: 'displayNamesMap.costName', displayName: "Cost Code Desc", headerTooltip: 'Cost Code Description', visible: false },
						{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit of Measure", headerTooltip: 'Unit of Measure', visible: false },
						{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: 'Used Hours' },
						{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: 'Idle Hours' },
						{ field: 'displayNamesMap.standardHrs', displayName: "Standard Hrs", headerTooltip: 'Standard Hours' },
						{ field: 'perUtil', displayName: "% of Utilisation", headerTooltip: '% of Utilisation' }
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Plant_Plant ActualVsStandard_HR");
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
					template: 'views/help&tutorials/reportshelp/planthelp/plantactual2standhrshelp.html',
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
