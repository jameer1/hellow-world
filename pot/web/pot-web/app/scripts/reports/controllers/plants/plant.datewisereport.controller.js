'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantdatewise", {
		url: '/plantdatewise',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/plants/plant.datewisereport.html',
				controller: 'PlantDateWiseReportController'
			}
		}
	})
}]).controller("PlantDateWiseReportController", ["$scope", "$filter", "$q", "$state", "ngDialog","GenericAlertService", "ManpowerReportService", "PlantReportService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "CompanyMultiSelectFactory", "ProjCostCodeService", "stylesService", "ngGridService","uiGridGroupingConstants","uiGridConstants", "chartService",
	function ($scope, $filter, $q, $state,ngDialog, GenericAlertService, ManpowerReportService, PlantReportService, EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, CompanyMultiSelectFactory, ProjCostCodeService, stylesService, ngGridService,uiGridGroupingConstants, uiGridConstants, chartService) {
		chartService.getChartMenu($scope);
		$scope.stylesSvc = stylesService;
		$scope.parseFloat = parseFloat;
		$scope.subReportCode = "";
		$scope.plantDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCrewIds = [];
		$scope.selectedCompanyIds = [];
		$scope.subReport = "None";
		$scope.data = [];
		$scope.labels = [];
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
			name: 'DateWise Plant Records',
			code: "date"
		}, {
			name: 'NameWise Plant Records',
			code: "name"
		}, {
			name: 'CostCodeWise Plant Records',
			code: "costcode"
		}, {
			name: 'ProjectWise Plant Records',
			code: "proj"
		}, {
			name: 'EPSWise Plant Records',
			code: "eps"
		}];
		$scope.changeSubReport = function () {
			if ($scope.subReport.code == "date") {
					let overAllData = [
						{ field: 'mapValue', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
						{ field: 'unitOfMeasure', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure", groupingShowAggregationMenu: false },
						{ field: 'usedHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Used Hrs", headerTooltip: "Used Hours" , groupingShowAggregationMenu: false},
						{ field: 'idleHrsCount', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Idle Hrs", headerTooltip: "Idle Hours", groupingShowAggregationMenu: false },
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hours", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_DateWise_AH_DateWise_PlantRecords");
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
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_Date Wise_AH_NameWise_PlantRecords");
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
						{ field: 'totalUsedIdle', aggregationType: uiGridConstants.aggregationTypes.sum, footerCellFilter: 'number:2', displayName: "Total Hours", headerTooltip: "Total Hours" , groupingShowAggregationMenu: false}
					]
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_DateWise_AH_CostCodeWise_PlantRecords");
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
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_DateWise_AH_ProjectWise_PlantRecords");
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
					$scope.gridOptions1 = ngGridService.initGrid($scope, overAllData, data,"Reports_Plant_DateWise_AH_EPSWise_PlantRecords");
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
			$scope.subReportData = [];
			if ($scope.subReport.code == "date") {
				generateSubReportData("workDairyDate", "workDairyDate");
			} else if ($scope.subReport.code == "name") {
				generateSubReportData("plantId", "plantName");
			} else if ($scope.subReport.code == "costcode") {
				generateSubReportData("costId", "costCode");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			}
		};
		function generateSubReportData(key, value) {
			$scope.data = [];
			$scope.labels = [];
			let usedHrsCountArr = [], idleHrsCountArr = [];
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
						"idleHrsCount": idleHrsCount
					};
					if (key == "costId") {
						subReportMap[mapKey].parentCostCode = plantDtl.displayNamesMap["parentCostCode"];
						subReportMap[mapKey].parentCostName = plantDtl.displayNamesMap["parentCostName"];
						subReportMap[mapKey].costName = plantDtl.displayNamesMap["costName"];
					}
				}
				subReportMap[mapKey].usedHrsCount += parseFloat(plantDtl.displayNamesMap["usedHrs"]);
				subReportMap[mapKey].idleHrsCount += parseFloat(plantDtl.displayNamesMap["idleHrs"]);
			}
			for (const index in subReportMap) {
				usedHrsCountArr.push(subReportMap[index].usedHrsCount);
				idleHrsCountArr.push(subReportMap[index].idleHrsCount);
				$scope.labels.push(subReportMap[index].mapValue);
				$scope.subReportData.push(subReportMap[index]);
			}
			 console.log($scope.subReportData.length);
		    $scope.usedTotal=0;
		    for(var i=0;i<$scope.subReportData.length;i++){
				$scope.usedTotal += $scope.subReportData[i].usedHrsCount;
			}
			$scope.idleHoursTotal=0;
		    for(var i=0;i<$scope.subReportData.length;i++){
				$scope.idleHoursTotal += $scope.subReportData[i].idleHrsCount;
			}
			$scope.data.push(usedHrsCountArr);
			$scope.data.push(idleHrsCountArr);
			
			for (let subReport of $scope.subReportData) {
				subReport.totalUsedIdle = subReport.usedHrsCount + subReport.idleHrsCount;
			}			
			$scope.gridOptions1.data = angular.copy($scope.subReportData);	
			$scope.initGraph();
		};
		var series = ['Used Hours', 'Idle Hours'];
		$scope.initGraph = function () {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);

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
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			PlantReportService.getPlantDateWiseReport(req).then(function (data) {
				$scope.plantDetails = data;

				for (let plant of $scope.plantDetails) {
					plant.totalHrs = parseFloat(parseInt(plant.displayNamesMap.usedHrs) + parseInt(plant.displayNamesMap.idleHrs)).toFixed(2);
					
				}
				$scope.gridOptions.data = $scope.plantDetails;
				if ($scope.plantDetails.length <= 0) {
					GenericAlertService.alertMessage("Datewise-Actual Hours not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Plant details", 'Error');
			});
			$scope.initGraph();
		};
		$scope.resetPlantDetails = function () {
			$scope.plantDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.selectedCompanyIds = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.companyNameDisplay = null;
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy"); $scope.subReportCode = "";
			$scope.subReport = "None";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		$scope.clearSubReportDetails = function () {
			$scope.plantDetails = [];
			$scope.type = "";
			$scope.subReportCode = "";
			$scope.subReportName = "";
		};



		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'displayNamesMap.workDairyDate', displayName: "Date", headerTooltip: 'Date' },
						{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: 'EPS Name' },
						{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: 'Project Name' },
						{ field: 'displayNamesMap.cmpName', displayName: "Company", headerTooltip: 'Company Name' },
						{ field: 'displayNamesMap.crewName', displayName: "Crew", headerTooltip: 'Crew Name' },
						{ field: 'displayNamesMap.parentCostCode', displayName: "Cost Code Sub Group ID", headerTooltip: 'Cost Code Sub Group ID' },
						{ field: 'displayNamesMap.parentCostName', displayName: "Cost Code Sub Group Name", headerTooltip: 'Cost Code Sub Group Name', visible: false },
						{ field: 'displayNamesMap.costCode', displayName: "Cost Code Item ID", headerTooltip: 'Cost Code Item ID' },
						{ field: 'displayNamesMap.costName', displayName: "Cost Code Desc", headerTooltip: 'Cost Code Description', visible: false },
						{ field: 'displayNamesMap.plantAssertId', displayName: "Plant ID", headerTooltip: 'Plant ID ' },
						{ field: 'displayNamesMap.plantName', displayName: "Plant Name", headerTooltip: 'Plant Name' },
						{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit of Measure", headerTooltip: 'Unit of Measure', visible: false },
						{
							name: 'displayNamesMap.usedHrs',
							cellTemplate: '<div>{{row.entity.displayNamesMap.usedHrs | number: 2 }}</div>',
							displayName: "Used Hrs", headerTooltip: 'Used Hours'
						},
						{
							name: 'displayNamesMap.idleHrs',
							cellTemplate: '<div>{{row.entity.displayNamesMap.idleHrs | number: 2 }}</div>',
							displayName: "Idle Hrs", headerTooltip: 'Idle Hours'
						},
						{ field: 'totalHrs', displayName: "Total Hrs", headerTooltip: 'Total Hours' }

					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Plant_DateWise_AH");
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
					template: 'views/help&tutorials/reportshelp/planthelp/plantdatewisehelp.html',
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
