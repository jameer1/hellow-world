'use strict';

import { isNumber } from "util";

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpoweractualvsstandhrs", {
		url: '/manpoweractualvsstandhrs',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpower.actualVsStandardHrsReport.html',
				controller: 'ManpowerActualVsStandardHrsReportController'
			}
		}
	})
}]).controller("ManpowerActualVsStandardHrsReportController", ["$scope", "$q", "ngDialog","$filter", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "CostCodeMultiSelectFactory", "CompanyMultiSelectFactory",
	"ManpowerReportService", "ProjectSettingsService", "generalservice", "dateGroupingService", "stylesService", "ngGridService", 'uiGridGroupingConstants', 'uiGridConstants', 'chartService', function ($scope,$q, ngDialog, $filter, GenericAlertService,
		EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, CostCodeMultiSelectFactory, CompanyMultiSelectFactory,
		ManpowerReportService, ProjectSettingsService, generalservice, dateGroupingService, stylesService, ngGridService, uiGridGroupingConstants, uiGridConstants, chartService) {
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'HOURS';
		$scope.stylesSvc = stylesService;
		let manpowerActualHrsData;
		let projReportsSettings;
		$scope.manpowerDetails = [];
		$scope.selectedProjIds = [];
		$scope.selectedCrewIds = [];
		$scope.selectedCompanyIds = [];
		$scope.selectedCostCodeIds = [];
		$scope.categoryName = [];
		$scope.date = new Date();
		$scope.toDate = new Date();
		let todayDate = new Date();
		var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
		$scope.fromDate = new Date($scope.toDate);
		$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		var defaultFromDate = new Date($scope.fromDate);
		var defaultToDate = new Date($scope.toDate);

		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

		$scope.subReport = {
			name: null
		}
		$scope.category = {
			name: null
		}
		$scope.periodicSubReport = {
			name: null
		}
		$scope.categories = [{
			id: 1,
			name: "Direct",
			code: generalservice.employeeCategory[0]
		}, {
			id: 2,
			name: "In-Direct",
			code: generalservice.employeeCategory[1]
		}, {
			id: 3,
			name: "All",
			code: null
		}];
		$scope.$watch('fromDate', function () {
			$scope.checkErr();
			// $scope.clearSubReportDetails();
		});
		$scope.$watch('toDate', function () {
			$scope.checkErr();
			// $scope.clearSubReportDetails();
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
				$scope.selectedClientIds = data.searchProject.clientIds;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
			$scope.clearSubReportDetails();
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
				$scope.manpowerDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
			});
			$scope.clearSubReportDetails();
		};
		$scope.getCostCodes = function () {
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null
				|| !$scope.selectedProjIds.length) {
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
				$scope.manpowerDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
			})
			$scope.clearSubReportDetails();
		};

		$scope.getCompanyList = function () {
			var companyReq = {
				"status": 1
			}
			var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
			companyPopUp.then(function (data) {
				$scope.companyNameDisplay = data.selectedCompanies.companyName;
				$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
				$scope.manpowerDetails = [];
			})
			$scope.clearSubReportDetails();
		};

		$scope.subReports = [{
			name: 'Crew wise Utilisation Hours',
			code: "crew"
		}, {
			name: 'Project Wise Records',
			code: "proj"
		}, {
			name: 'EPS Wise Records',
			code: "eps"
		}, {
			name: 'Periodical Report',
			code: "periodical"
		},];

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

		$scope.changeSubReport = function () {
			$scope.type = 'chartTable';
			let compareProperty;
			let codeProperty;
			switch ($scope.subReport.code) {
				case "eps":
					compareProperty = "parentProjId";
					codeProperty = "parentProjName";
					$scope.tableHeading = "EPS Name";
					if ($scope.subReport.code == "eps") {
						let epsData = [
							{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name" },
							{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
							{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hours" },
							{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
							{ field: 'totalUsedIdle', displayName: "Total Actual Hrs", headerTooltip: "Total Actual Hours" },
							{ field: 'stanHrs', displayName: "Standard Hrs", headerTooltip: "Standard Hours" }
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_ActualVsStandard_HR_EPSWise");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					$scope.periodicSubReport = {};
					break;
				case "proj":
					compareProperty = "projId";
					codeProperty = "projName";
					$scope.tableHeading = "Project Name";
					if ($scope.subReport.code == "proj") {
						let epsData = [
							{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name" },
							{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
							{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hours" },
							{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
							{ field: 'totalUsedIdle', displayName: "Total Actual Hrs", headerTooltip: "Total Actual Hours" },
							{ field: 'stanHrs', displayName: "Standard Hrs", headerTooltip: "Standard Hours" }
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_ActualVsStandard_HR_ProjWise");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					$scope.periodicSubReport = {};
					break;
				case "crew":
					compareProperty = "crewId";
					codeProperty = "crewName";
					$scope.tableHeading = "Crew Name";
					if ($scope.subReport.code == "crew") {
						let epsData = [
							{ field: 'code', displayName: "Crew Name", headerTooltip: "Crew Name" },
							{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
							{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hours" },
							{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
							{ field: 'totalUsedIdle', displayName: "Total Actual Hrs", headerTooltip: "Total Actual Hours" },
							{ field: 'stanHrs', displayName: "Standard Hrs", headerTooltip: "Standard Hours" }
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_ActualVsStandard_HR_CrewWise");
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					$scope.periodicSubReport = {};
					break;
				case "periodical":
					ProjectSettingsService.projReportsOnLoad({ "projIds": $scope.selectedProjIds }).then(function (data) {
						projReportsSettings = data.projectReportsTOs;
						if (!$scope.periodicSubReport || !$scope.periodicSubReport.code) {
							$scope.periodicSubReport = $scope.periodicSubReports[0];
						}
						$scope.changePeriodicSubReport();
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
					});
					return;
				default:
					$scope.manpowerDetails = [];
					$scope.type = '';
					$scope.subReportName = '';
					$scope.subReportCode = '';
					let columnDefs = [
						{ field: 'dateStr', displayName: "Date", headerTooltip: "Date" },
						{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name" },
						{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" },
						{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name" },
						{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name" },
						{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id" },
						{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name" },
						{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID" },
						{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description" },
						{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor" },
						{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID" },
						{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name"},
						{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name"},
						{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Category"},
						{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure"},
						{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hrs" },
						{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hrs"},
						{ field: 'projId', displayName: "Stand Hrs", headerTooltip: "Standard Hrs" },
						{ field: 'utilHrs', displayName: "Util Hrs", headerTooltip: "Utilization Hrs"}
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_ActualVsStandard_HR");
					$scope.getManpowerDetails();
					$scope.gridOptions.gridMenuCustomItems = false;
			}
			const subReportMap = new Array();
			for (const manPower of manpowerActualHrsData) {
				if (!subReportMap[manPower[compareProperty]]) {
					subReportMap[manPower[compareProperty]] = {
						'code': manPower[codeProperty],
						'usedHrs': 0,
						'idleHrs': 0,
						'measureUnit': manPower.unitOfMeasure,
						'manDays': 0,
						'projectId': manPower.projId
					};
				}
				const totalHrs = calculateHrs(manPower.hrsList);
				subReportMap[manPower[compareProperty]].usedHrs += totalHrs.usedHrs;
				subReportMap[manPower[compareProperty]].idleHrs += totalHrs.idleHrs;
				subReportMap[manPower[compareProperty]].manDays += calculateManDays(manPower.hrsList);
			}
			setGraphData(subReportMap);
		}

		function calculateHrs(hrsList) {
			let usedHrs = 0;
			let idleHrs = 0;
			for (const hr of hrsList) {
				usedHrs += hr.usedHrs;
				idleHrs += hr.idleHrs;
			}
			return { 'usedHrs': usedHrs, 'idleHrs': idleHrs };
		}

		function calculateManDays(hrsList) {
			let manDays = 0;
			for (const hr of hrsList) {
				if (hr.usedHrs || hr.idleHrs)
					manDays++;
			}
			return manDays;
		}

		function setGraphData(subReportMap, dateFormat) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let obj = null;
			const actualHrs = new Array();
			const standardHrs = new Array();
			$scope.subReportData = new Array();
			let objectKeys = null;
			if ($scope.subReport.code === 'periodical') {
				objectKeys = _.sortBy(Object.keys(subReportMap), function (i) { return moment(i.split("_")[0], dateFormat)._d });
			} else {
				objectKeys = Object.keys(subReportMap);
			}
			for (const key of objectKeys) {
				console.log('key: ' + key + ', new date: ', new Date(key.split("_")[0]));
				obj = subReportMap[key];
				if (obj.idleHrs || obj.usedHrs) {
					actualHrs.push(obj.usedHrs + obj.idleHrs);
					standardHrs.push(obj.manDays * $scope.standardHrsMap[obj.projectId]);
					$scope.labels.push(obj.code);
					$scope.subReportData.push(obj);
				}
			}
			for (let manpower of $scope.subReportData) {
				manpower.totalUsedIdle = manpower.usedHrs + manpower.idleHrs;
				manpower.stanHrs = manpower.manDays * $scope.standardHrsMap[manpower.projectId];
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.data.push(actualHrs);
			$scope.data.push(standardHrs);
			initGraph();
		}

		$scope.changePeriodicSubReport = function () {
			$scope.type = 'chartTable';
			let groupByFunction = null;
			let dateFormat;
			let momentDateFormat;
			let reportSettingProp;
			if ($scope.subReport.code == "periodical") {
				let epsData = [
					{ field: 'code', displayName: "Periodical -" + $scope.periodicSubReport.name, headerTooltip: "Periodical -" + $scope.periodicSubReport.name },
					{ field: 'measureUnit', displayName: "Unit Of Measure", headerTooltip: "Unit Of Measure" },
					{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hours" },
					{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hours" },
					{ field: 'totalUsedIdle', displayName: "Total Actual Hrs", headerTooltip: "Total Actual Hours" },
					{ field: 'stanHrs', displayName: "Standard Hrs", headerTooltip: "Standard Hours" }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data,"Reports_Manpower_ActualVsStandard_HR");
				$scope.gridOptions.gridMenuCustomItems = false;
			}
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
			// Group by Unit of measure
			const unitMeasureMap = new Array();
			for (const manPower of manpowerActualHrsData) {
				if (!unitMeasureMap[manPower.unitOfMeasureId]) {
					unitMeasureMap[manPower.unitOfMeasureId] = {
						'measureUnit': manPower.unitOfMeasure,
						'hrsList': new Array(),
						'projId': manPower.projId
					};
				}
				unitMeasureMap[manPower.unitOfMeasureId].hrsList =
					unitMeasureMap[manPower.unitOfMeasureId].hrsList.concat(manPower.hrsList);
			}
			const periodicalReport = new Array();

			for (const key in unitMeasureMap) {
				const object = unitMeasureMap[key];
				let projReportSetting = getProjReportSettings(object.projId);
				const groupedList = groupByFunction(object.hrsList, projReportSetting[reportSettingProp]);
				for (const group of groupedList) {
					if (!periodicalReport[group.date + "_" + key]) {
						periodicalReport[group.date + "_" + key] = {
							'code': null,
							'measureUnit': object.measureUnit,
							'usedHrs': 0,
							'idleHrs': 0,
							'manDays': 0,
							'projectId': object.projId
						};
					}
					periodicalReport[group.date + "_" + key].code =
						$filter('date')(dateGroupingService.getGroupedDate(group), dateFormat);
					for (const val of group.values) {
						periodicalReport[group.date + "_" + key].usedHrs += val.usedHrs;
						periodicalReport[group.date + "_" + key].idleHrs += val.idleHrs;
					}
					periodicalReport[group.date + "_" + key].manDays += calculateManDays(group.values);

				}
			}
			setGraphData(periodicalReport, momentDateFormat);

		};

		function getProjReportSettings(projId) {
			for (const projSetting of projReportsSettings) {
				if (projSetting.projId == projId) {
					return projSetting;
				}
			}
			return null;
		}

		let series = ['Actual Hours', 'Standard Hours'];
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
			
		}

		$scope.clearSubReportDetails = function () {
			$scope.manpowerDetails = [];
			$scope.type = "";
			$scope.periodicSubReport = {};
		}
		$scope.selectType = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select the Project", 'Warning');
				return;
			}
			if ($scope.subReport.name == null) {
				GenericAlertService.alertMessage("Please select Sub Report", 'Warning');
				return;
			}
		};

		$scope.getManpowerDetails = function () {
			if ($scope.selectedProjIds.length <= 0) {
				GenericAlertService.alertMessage("Please select the Project", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds.length <= 0) {
				GenericAlertService.alertMessage("Please select the Company", 'Warning');
				return;
			}
			if ($scope.selectedCrewIds.length <= 0) {
				GenericAlertService.alertMessage("Please select the Crew", 'Warning');
				return;
			}
			if ($scope.selectedCostCodeIds.length <= 0) {
				GenericAlertService.alertMessage("Please select the Cost Code", 'Warning');
				return;
			}
			if ($scope.categoryName == '') {
				GenericAlertService.alertMessage("Please select the Category", 'Warning');
				return;
			}
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"crewIds": $scope.selectedCrewIds,
				"cmpIds": $scope.selectedCompanyIds,
				"costCodeIds": $scope.selectedCostCodeIds,
				"category": $scope.categoryName,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate,
			}
			ManpowerReportService.getManpowerActualStandardReport(req).then(function (data) {
				manpowerActualHrsData = data.manPowerActualHrsTOs;
				$scope.standardHrsMap = data.standardHrsMap;
				$scope.gridOptions.data = angular.copy($scope.manpowerDetails);
				if ($scope.subReport.code) {
					$scope.changeSubReport();
				} else {
					const empMap = new Array();
					$scope.employeeDetailsMap = new Array();
					let uniqueId = 0;
					const actualHrsList = new Array();
					for (const manHrs of manpowerActualHrsData) {
						empMap[uniqueId] = manHrs;
						for (const hr of manHrs.hrsList) {
							const newObj = { 'uniqueId': uniqueId };
							// Deep copy hr object
							for (const key in hr) {
								newObj[key] = hr[key];
							}
							actualHrsList.push(newObj);
						}
						uniqueId++;
					}
					$scope.manpowerDetails = actualHrsList;
					$scope.employeeDetailsMap = empMap;
					for (let manpower of actualHrsList) {
						manpower.dateStr = $filter('date')((manpower.date), "dd-MMM-yyyy");
						manpower.parentProjName = empMap[manpower.uniqueId].parentProjName;
						manpower.projName = empMap[manpower.uniqueId].projName;
						manpower.companyName = empMap[manpower.uniqueId].companyName;
						manpower.crewName = empMap[manpower.uniqueId].crewName;
						manpower.parentCostCode = empMap[manpower.uniqueId].parentCostCode;
						manpower.parentCostCodeName = empMap[manpower.uniqueId].parentCostCodeName;
						manpower.costCodeName = empMap[manpower.uniqueId].costCodeName;
						manpower.costCodeDesc = empMap[manpower.uniqueId].costCodeDesc;
						manpower.wageCode = empMap[manpower.uniqueId].wageCode;
						manpower.empCode = empMap[manpower.uniqueId].empCode;
						manpower.empFirstname = empMap[manpower.uniqueId].empFirstname;
						manpower.empLastname = empMap[manpower.uniqueId].empLastname;
						manpower.empCategoryName = empMap[manpower.uniqueId].empCategoryName;
						manpower.unitOfMeasure = empMap[manpower.uniqueId].unitOfMeasure;
						manpower.projId = $scope.standardHrsMap[empMap[manpower.uniqueId].projId];
						manpower.utilHrs = (((manpower.usedHrs + manpower.idleHrs) / $scope.standardHrsMap[empMap[manpower.uniqueId].projId]) * 100).toFixed(2);

					}
					$scope.gridOptions.data = angular.copy($scope.manpowerDetails);

				}
				if ($scope.manpowerDetails.length <= 0) {
					GenericAlertService.alertMessage("Manpower Actual vs Standard Hours Records not available for the search Criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  details", 'Error');
			});
		};

		$scope.resetManpowerDetails = function () {
			$scope.manpowerDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.selectedCompanyIds = [];
			$scope.selectedCostCodeIds = [];
			$scope.categoryName = [];
			$scope.crewNameDisplay = null;
			$scope.companyNameDisplay = null;
			$scope.costCodeNameDisplay = null;
			$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
			$scope.type = '';
			$scope.category = null;
			$scope.periodicSubReport = null;
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};


		
		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'dateStr', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
					{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name", groupingShowAggregationMenu: false },
					{ field: 'parentCostCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", groupingShowAggregationMenu: false  },
					{ field: 'parentCostCodeName', displayName: "Cost Code Sub Group Name", headerTooltip: "Cost Code Sub Group Name",  groupingShowAggregationMenu: false},
					{ field: 'costCodeName', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false },
					{ field: 'costCodeDesc', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description",  groupingShowAggregationMenu: false},
					{ field: 'wageCode', displayName: "Wages", headerTooltip: "Wage Factor", groupingShowAggregationMenu: false },
					{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID", groupingShowAggregationMenu: false },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "First Name",  groupingShowAggregationMenu: false},
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Last Name", groupingShowAggregationMenu: false },
					{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false },
					{ field: 'unitOfMeasure', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },
					{ field: 'usedHrs', displayName: "Used Hrs", headerTooltip: "Used Hrs", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					{ field: 'idleHrs', displayName: "Idle Hrs", headerTooltip: "Idle Hrs", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					{ field: 'projId', displayName: "Stand Hrs", headerTooltip: "Standard Hrs", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} },
					{ field: 'utilHrs', displayName: "Util Hrs", headerTooltip: "Utilization Hrs", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }


				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Manpower_ActualVsStandard_HR");
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
				template: 'views/help&tutorials/reportshelp/manpowerhelp/manpoweractvsstandhrshelp.html',
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
