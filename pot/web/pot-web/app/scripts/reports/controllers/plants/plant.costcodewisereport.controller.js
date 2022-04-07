'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantcostcodewise", {
		url: '/plantcostcodewise',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/plants/plant.costcodewisereport.html',
				controller: 'PlantCostCodeReportController'
			}
		}
	})
}]).controller("PlantCostCodeReportController", ["$rootScope", "$scope", "$filter", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "CostCodeMultiSelectFactory", "PlantReportService", "stylesService", "ngGridService", "uiGridGroupingConstants","uiGridConstants", "chartService",
	function ($rootScope, $scope, $filter, $q, $state, ngDialog, GenericAlertService,
		EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, CostCodeMultiSelectFactory, PlantReportService, stylesService, ngGridService, uiGridGroupingConstants, uiGridConstants, chartService) {
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Total Count';
		$scope.data = [];
		$scope.labels = [];
		$scope.subReportCode = "";
		$scope.selectedProjIds = [];
		$scope.selectedCrewIds = [];
		$scope.selectedCostCodeIds = [];
		$scope.plantCostCodeDetails = [];
		$scope.date = new Date();
		var defaultDate = new Date($scope.date);
		$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");

		$scope.subReports = [{
			name: 'CostCodeWise Plant Records',
			code: "costcode"
		}, {
			name: 'Crew Wise Plant Records',
			code: "crew"
		}, {
			name: 'ProjectWise Plant Records',
			code: "proj"
		}, {
			name: 'EPSWise Plant Records',
			code: "eps"
		}];
		$scope.subReport = "None";
		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.plantCostCodeDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCrewList = function () {
			if (!$scope.selectedProjIds) {
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
				$scope.plantCostCodeDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
			});
		};
		$scope.getCostCodes = function () {
			if (!$scope.selectedProjIds) {
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
				$scope.plantCostCodeDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
			})
		};
		$scope.changeSubReport = function () {
			if ($scope.subReport.code == "costcode") {
					let overAllData = [
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: "Cost Code Sub Group ID", groupingShowAggregationMenu: false },
						{ field: 'parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name", groupingShowAggregationMenu: false },
						{ field: 'plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification Name' },
						{ field: 'mapValue', displayName: "Cost Code Item ID", headerTooltip: "Cost Code Item ID" , groupingShowAggregationMenu: false},
						{ field: 'costName', displayName: "Cost Code Item Name", headerTooltip: "Cost Code Item Name", groupingShowAggregationMenu: false },
						{ field: 'plantCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Plant Count", headerTooltip: "Plant Count" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_CostCodewise_DailyDeploymentList_CostCodeWise_PlantRecords");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;					
				}				
				else if($scope.subReport.code == "crew"){
					let overAllData = [
						{ field: 'mapValue', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false },
						{ field: 'plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification Name' },
						{ field: 'plantCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Plant Count", headerTooltip: "Plant Count" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_CostCodewise_DailyDeploymentList_CrewWise_PlantRecords");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "proj"){
					let overAllData = [
						{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification Name' },
						{ field: 'plantCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Plant Count", headerTooltip: "Plant Count" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_CostCodewise_DailyDeploymentList_ProjectWise_PlantRecords");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}	
				else if($scope.subReport.code == "eps"){
					let overAllData = [
						{ field: 'mapValue', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
						{ field: 'plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification Name' },
						{ field: 'plantCount', aggregationType: uiGridConstants.aggregationTypes.sum,displayName: "Plant Count", headerTooltip: "Plant Count" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_CostCodewise_DailyDeploymentList_EPSWise_PlantRecords");
					$scope.gridOptions1.showColumnFooter = true;
					$scope.gridOptions1.gridMenuCustomItems = false;
					
				}
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.plantCostCodeDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				$scope.getPlantCostCodeWiseReport();
			}
		};
		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "costcode") {
				generateSubReportData("costId", "costCode");
			} else if ($scope.subReport.code == "crew") {
				generateSubReportData("crewId", "crewName");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			}
		};
		function generateSubReportData(key, value) {
			let plantCountArr = [];
			let subReportMap = [];
			for (let plantDtl of $scope.plantCostCodeDetails) {
				let mapKey = plantDtl.displayNamesMap[key];
				let mapValue = plantDtl.displayNamesMap[value];
				if (!subReportMap[mapKey]) {
					let plantCount = 0;
					let plantClassName=[];
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"plantCount": plantCount,
						"plantClassName": plantClassName
					};
					
					if (key == "costId") {
						subReportMap[mapKey].parentCostCode = plantDtl.displayNamesMap["parentCostCode"];
						subReportMap[mapKey].parentCostName = plantDtl.displayNamesMap["parentCostName"];
						subReportMap[mapKey].costName = plantDtl.displayNamesMap["costName"];
						subReportMap[mapKey].plantClassName = plantDtl.displayNamesMap["plantClassName"];
					}
				}
				if (plantDtl.displayNamesMap['plantId']) {
					subReportMap[mapKey].plantCount += 1;
					subReportMap[mapKey].plantClassName = plantDtl.displayNamesMap["plantClassName"];
				}
			}
			for (const index in subReportMap) {
				plantCountArr.push(subReportMap[index].plantCount);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			$scope.data = plantCountArr;
			$scope.gridOptions1.data = angular.copy($scope.subReportData);
			$scope.initGraph();			
		};

		var series = ['TotalCount'];
		$scope.initGraph = function () {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
		$scope.getPlantCostCodeWiseReport = function () {
			$scope.data = [];
			$scope.labels = [];
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
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
			if (!$scope.date) {
				GenericAlertService.alertMessage("Please select the date to fetch report", 'Warning');
				return;
			}
			var getReq = {
				"projIds": $scope.selectedProjIds,
				"crewIds": $scope.selectedCrewIds,
				"costCodeIds": $scope.selectedCostCodeIds,
				"workDairyDate": $scope.date
			};

			PlantReportService.getPlantCostCodeWiseReport(getReq).then(function (data) {
				$scope.plantCostCodeDetails = data;
				$scope.gridOptions.data = $scope.plantCostCodeDetails;
				if ($scope.plantCostCodeDetails.length <= 0) {
					GenericAlertService.alertMessage("Cost Code wise Daily Deployment List not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Plant Cost Code Wise Report details", 'Error');
			});
			$scope.initGraph();
		};
		$scope.clearSubReportDetails = function () {
			$scope.type = '';
			$scope.subReport = "None";
			$scope.subReportName = "";
			$scope.subReportCode = "";
			$scope.plantCostCodeDetails = [];
		};
		$scope.resetPlantDetails = function () {
			$scope.plantCostCodeDetails = [];
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.selectedCostCodeIds = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.subReport = "None";
			$scope.subReportCode = "";
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.costCodeNameDisplay = "";
			$scope.date = $filter('date')((defaultDate), "dd-MMM-yyyy");
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};

		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: 'EPS Name' },
						{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: 'Project Name' },
						{ field: 'displayNamesMap.cmpName', displayName: "Company", headerTooltip: 'Company Name', visible: false },
						{ field: 'displayNamesMap.crewName', displayName: "Crew", headerTooltip: 'Crew Name', visible: false },
						{ field: 'displayNamesMap.parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: 'Cost Code Sub Group ID' },
						{ field: 'displayNamesMap.parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: 'Cost Code Sub Group Name' },
						{ field: 'displayNamesMap.costCode', displayName: "Cost Code Item ID", headerTooltip: 'Cost Code Item ID' },
						{ field: 'displayNamesMap.costName', displayName: "Cost Code Desc", headerTooltip: 'Cost Code Description' },
						{ field: 'displayNamesMap.plantAssertId', displayName: "Plant ID", headerTooltip: 'Plant ID ' },
						{ field: 'displayNamesMap.plantName', displayName: "Plant Name", headerTooltip: 'Plant Name' },
						{ field: 'displayNamesMap.plantClassName', displayName: "Plant Classification", headerTooltip: 'Plant Classification Name' },
						{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit of Measure", headerTooltip: 'Unit of Measure' },
						{ field: 'displayNamesMap.currency', displayName: "Currency", headerTooltip: 'Currency' },
						{ field: 'displayNamesMap.unitOfRate', displayName: "Rate Per Unit", headerTooltip: 'Rate Per Unit' }
					];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [],"Reports_Plant_Cost Codewise_DDL");
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
					template: 'views/help&tutorials/reportshelp/planthelp/plantcostcodewisehelp.html',
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
