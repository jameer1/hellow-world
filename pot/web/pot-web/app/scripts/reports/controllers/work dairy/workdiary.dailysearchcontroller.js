'use strict';


app.config(
	["$stateProvider", function ($stateProvider) {
		$stateProvider
			.state(
				"workdairydailysearch",
				{
					url: '/workdairydailysearch',
					data: {
						reports: []
					},
					views: {
						'content@': {
							templateUrl: 'views/reports/work dairy/workdiary.dialysearchrecords.html',
							controller: 'WorkDiaryDailySearchController'
						}
					}
				})
	}]).controller("WorkDiaryDailySearchController", ["$scope", "$q", "ngDialog","$filter", "EpsProjectSelectFactory", "ProjectCrewPopupService", "GenericAlertService", "WorkDiaryService", "stylesService", "ngGridService", 'chartService', "uiGridConstants", function ($scope, $q, ngDialog,$filter, EpsProjectSelectFactory,
		ProjectCrewPopupService, GenericAlertService, WorkDiaryService, stylesService, ngGridService, chartService, uiGridConstants) {
		chartService.getChartMenu($scope);
		$scope.stylesSvc = stylesService;
		$scope.workDiaryDetails = [];
		$scope.workDairySearchReq = {
			"workDairyId": null,
			"workDairyDate": null,
			"projectLabelKeyTO": {
				"projId": null,
				"parentName": null,
				"projName": null

			},
			"crewLabelKeyTO": {
				"id": null,
				"code": null,
				"name": null
			},
			"userLabelKeyTO": {
				"id": null,
				"code": null,
				"name": null
			},
		};
		$scope.subReportsList = [{
			id: 0,
			name: 'Man Power Trade Wise Used Vs Idle Hours',
			code: "manpowerTrade"
		}, {
			id: 1,
			name: 'Plant Classification Wise Used Vs Idle Hours',
			code: "plantWise"
		}, {
			id: 2,
			name: 'Material Item Wise Consumption Quantity',
			code: "material"
		}, {
			id: 3,
			name: 'SOW Item Wise Progress Quantity',
			code: "progress"
		}
		];

		$scope.date = new Date();
		var defaultDate = new Date($scope.date);
		$scope.workDairySearchReq.workDairyDate = $filter('date')((defaultDate), "dd-MMM-yyyy");

		$scope.clear = function () {
			$scope.workDairySearchReq.crewLabelKeyTO = [];
		}
		$scope.workDairyCostCodeList = [];
		$scope.WorkDiaryReportTabs = [
			{
				id: 3,
				title: 'Progress',
				url: 'views/reports/work dairy/workdiary.progressreport.html',
				workDairySeleniumLocatorReport: "WorkDiarySearchandItsRecords_Progress"
			},
			{
				id: 0,
				title: 'Manpower Utilisation',
				url: 'views/reports/work dairy/workdiary.manpowerreport.html',
				workDairySeleniumLocatorReport: 'WorkDiarySearchandItsRecords_ManpowerUtilisation'

			},
			{
				id: 1,
				title: 'Plant Utilisation',
				url: 'views/reports/work dairy/workdiary.plantreport.html',
				workDairySeleniumLocatorReport: 'WorkDiarySearchandItsRecords_PlantUtilisation'
			},
			{
				id: 2,
				title: 'Material and Store Consumption Utilisation',
				url: 'views/reports/work dairy/workdiary.materialreport.html',
				workDairySeleniumLocatorReport: 'WorkDiarySearchandItsRecords_Materialstoreconsumption'
			}
		];
		$scope.currentTab = $scope.WorkDiaryReportTabs[0].url;
		$scope.currentTabId = 0;

		$scope.isActiveTab = function (tabUrl) {
			return tabUrl == $scope.currentTab;
		}
		$scope.getWorkDairyRecordsByStatus = function (arr, propertyName) {
			let result = new Array();
				for (const obj of arr) {
						result.push(obj[propertyName]);
					
				}
				
			return result;
		};
	
		$scope.onClickTab = function (tab) {
			$scope.currentTab = tab.url;
			$scope.currentTabId = tab.id;
		};
		$scope.isActiveTab = function (tabUrl) {
			return tabUrl == $scope.currentTab;
		};
		$scope.getUserProjects = function (projectLabelKeyTO) {
			var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = data.searchProject;
				projectLabelKeyTO.projId = data.searchProject.projId;
				projectLabelKeyTO.parentName = data.searchProject.parentName;
				projectLabelKeyTO.projName = data.searchProject.projName;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCrewList = function (projId, crewLabelKeyTO) {
			if (projId == null || $scope.workDairySearchReq.workDairyDate == null) {
				GenericAlertService.alertMessage("Please select project and work dairy date to get crews", 'Warning');
				return;
			}
			const crewReq = {
				"status": 1,
				"projId": projId
			};
			var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				crewLabelKeyTO.id = data.projCrewTO.id;
				crewLabelKeyTO.name = data.projCrewTO.desc;
				$scope.getWorkDairy();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			});
		};

		$scope.changeDisplayData = function () {
			if ($scope.subReport && $scope.subReport.code)
				$scope.changeSubReport();
		}

		$scope.changeSubReport = function () {
			$scope.type = 'chartTable';
			let subReportSource;
			let compareProperty;
			let codeProperty;
			let valuesProperty;
			const subReportMap = new Array();
			switch ($scope.subReport.code) {
				case "manpowerTrade":
					compareProperty = "empClassId";
					codeProperty = "classType";
					subReportSource = $scope.workDiaryManpowerDetails;
					valuesProperty = 'workDairyEmpWageTOs';
					$scope.tableHeading = "Trade";
					if ($scope.subReport.code == "manpowerTrade") {
						let overAllData = [
							{ field: 'code', displayName: "Trade Name", headerTooltip: "Trade Name" },
							{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Used Time", headerTooltip: "Used Time" },
							{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Time", headerTooltip: "Idle Time" },
							{ field: 'totalHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },

						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, 'Reports_WorkDiary_WorkDiaryDailySearch&ItsRecords_ManpowerTrade');
						$scope.gridOptions.showColumnFooter = true;
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					break;
				case "plantWise":
					compareProperty = "plantClassId";
					codeProperty = "classType";
					subReportSource = $scope.workDairyPlantDtlTOs;
					valuesProperty = 'workDairyPlantStatusTOs';
					$scope.tableHeading = "Plant";
					if ($scope.subReport.code == "plantWise") {
						let overAllData = [
							{ field: 'code', displayName: "Plant Name", headerTooltip: "Plant Name" },
							{ field: 'usedHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Used Time", headerTooltip: "Used Time" },
							{ field: 'idleHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle Time", headerTooltip: "Idle Time" },
							{ field: 'totalHrs', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" },

						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, 'Reports_WorkDiary_WorkDiaryDailySearch&ItsRecords_PlantWise');
						$scope.gridOptions.showColumnFooter = true;
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					break;
				case "material":
					compareProperty = "materialObj.materialProjDtlTO[0].purchaseSchLabelKeyTO.name";
					codeProperty = "parentProjName";
					subReportSource = $scope.workDairyMaterialDtlTOs;
					$scope.series = ['Quantity'];
					if ($scope.subReport.code == "material") {
						let overAllData = [
							{ field: 'code', displayName: "Material Name", headerTooltip: "Material Name" },
							{ field: 'quantity', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Quantity", headerTooltip: "Quantity" },

						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data);
						$scope.gridOptions.showColumnFooter = true;
					}
					for (const object of subReportSource) {
						const key = object.materialProjDtlTO[0].purchaseSchLabelKeyTO.name;
						if (!subReportMap[key]) {
							subReportMap[key] = {
								'code': key,
								'quantity': 0,
							};
						}
						// check and calculate results for selected display data filter
						if ($scope.displayStatus && $scope.displayStatus.code) {

							if ($scope.displayStatus.code.includes(object.workDairyMaterialStatusTOs[0].apprStatus)) {
								subReportMap[key].quantity += object.workDairyMaterialStatusTOs[0].total;
							}
						}
					}
					$scope.labels = new Array();
					$scope.data = new Array();
					$scope.subReportData = new Array();
					$scope.total = { 'qty': 0 };
					const materialQty = new Array();
					for (const key in subReportMap) {
						materialQty.push(subReportMap[key].quantity);
						$scope.total.qty += subReportMap[key].quantity;
						$scope.labels.push(subReportMap[key].code);
						$scope.subReportData.push(subReportMap[key]);
					}
					$scope.gridOptions.data = angular.copy($scope.subReportData);
					$scope.data.push(materialQty);
					initGraph();
					return;
				case "progress":
					compareProperty = "sowId";
					codeProperty = "sowCode";
					subReportSource = $scope.workDairyProgressDtlTOs;
					valuesProperty = null;
					if ($scope.subReport.code == "progress") {
						let overAllData = [
							{ field: 'sowCode', displayName: "SOW Item ID", headerTooltip: "SOW Item ID" },
							{ field: 'sowName', displayName: "SOW Item Name", headerTooltip: "SOW Item Name" },
							{ field: 'subGroup', displayName: "Sub Group Name", headerTooltip: "Sub Group Name" },
							{ field: 'measureUnit', displayName: "Unit of Measure", headerTooltip: "Unit of Measure" },
							{ field: 'quantity', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Quantity", headerTooltip: "Quantity" },

						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, 'Reports_WorkDiary_WorkDiaryDailySearch&ItsRecords_Progress');
						$scope.gridOptions.showColumnFooter = true;
						$scope.gridOptions.gridMenuCustomItems = false;
					}
					for (const object of subReportSource) {
						const key = object[compareProperty] + "_" + object.measureId;
						if (!subReportMap[key]) {
							subReportMap[key] = {
								'code': object[codeProperty] + " " + object.measureCode,
								'sowCode': object.sowCode,
								'sowName': object.sowDesc,
								'subGroup': object.soeCode,
								'measureUnit': object.measureCode,
								'quantity': 0,
							};
						}
						// check and calculate results for selected display data filter
						if ($scope.displayStatus && $scope.displayStatus.code) {
							if ($scope.displayStatus.code.includes(object.apprStatus)) {
								subReportMap[key].quantity += object.value;
							}
						}
					}
					$scope.labels = new Array();
					$scope.data = new Array();
					$scope.subReportData = new Array();
					$scope.total = { 'qty': 0 };
					const progressQty = new Array();
					for (const key in subReportMap) {
						progressQty.push(subReportMap[key].quantity);
						$scope.total.qty += subReportMap[key].quantity;
						$scope.labels.push(subReportMap[key].code);
						$scope.subReportData.push(subReportMap[key]);
					}
					$scope.gridOptions.data = angular.copy($scope.subReportData);
					$scope.data.push(progressQty);
					initGraph();
					return;
			} // End of switch

			for (const obj of subReportSource) {
				if (!subReportMap[obj[compareProperty]]) {
					subReportMap[obj[compareProperty]] = {
						'code': obj[codeProperty],
						'usedHrs': 0,
						'idleHrs': 0,
					};
				}

				// check and calculate results for selected display data filter
				
					for (const value of obj[valuesProperty]) {
						 
							subReportMap[obj[compareProperty]].usedHrs += value.usedTotal;
							subReportMap[obj[compareProperty]].idleHrs += value.idleTotal;
						
					}
				
			}
			setGraphData(subReportMap);
		};

		$scope.displayStatusList = [{
			name: 'SubmittedForApproval',
			code: ["SubmittedForApproval", 'SubmittedForClientApproval', null]
		}, {
			name: 'Approved',
			code: ["Approved"]
		}, {
			name: 'Client Approved',
			code: ["Client Approved"]
		},{
		name: 'under Preparation',
		code: ["under preparation"]
		},
		{
			name: 'All',
			code: ['SubmittedForApproval', 'SubmittedForClientApproval', 'Approved', 'Client Approved','under preparation', null]
		}];

		function setGraphData(subReportMap) {
			$scope.labels = new Array();
			$scope.data = new Array();
			let obj = null;
			const usedHrs = new Array();
			const idleHrs = new Array();
			$scope.subReportData = new Array();
			$scope.total = { 'used': 0, 'idle': 0 };
			for (const key in subReportMap) {
				obj = subReportMap[key];
				usedHrs.push(obj.usedHrs);
				$scope.total.used += obj.usedHrs;

				idleHrs.push(obj.idleHrs);
				$scope.total.idle += obj.idleHrs;
				$scope.labels.push(obj.code);
				$scope.subReportData.push(obj);
			}
			for (let obj of $scope.subReportData) {
				obj.totalHrs = obj.idleHrs + obj.usedHrs;
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.data.push(usedHrs);
			$scope.data.push(idleHrs);
			$scope.series = ['Used Hours', 'Idle Hours'];
			initGraph();
		}


		function initGraph() {
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			if ($scope.subReport.code === "progress") {
				$scope.yAxislabels = 'QUANTITY';
			} else
				$scope.yAxislabels = 'Quantity';
			chartService.defaultBarInit($scope.yAxislabels);
		};

		$scope.getWorkDairy = function () {
			$scope.workDairyEmpDtlTOs = [];
			$scope.workDairyPlantDtlTOs = [];
			$scope.workDairyMaterialDtlTOs = [];
			$scope.workDairyProgressDtlTOs = [];
			if ($scope.workDairySearchReq.workDairyDate == null) {
				GenericAlertService.alertMessage(
					"Please select Workdairy date",
					'Warning');
			}
			var workDairyReq = {
				"status": 1,
				"workDairyDate": $scope.workDairySearchReq.workDairyDate,
				"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
				"crewId": $scope.workDairySearchReq.crewLabelKeyTO.id
			};
			WorkDiaryService.getWorkDairy(workDairyReq).then(function (data) {
				$scope.workDairySearchReq.workDairyId = data.workDairyTO.id;
				$scope.workDairySearchReq.code = data.workDairyTO.workDiaryCode;
				$scope.workDairySearchReq.apprStatus = data.workDairyTO.apprStatus;
				$scope.workDairyCostCodeList = data.workDairyCostCodeTOs.reverse();
				$scope.workDairyCostCodeMap = data.costCodeMap;
				$scope.empWageFactorMap = data.empWageFactorMap;
				$scope.empRegMap = data.empRegMap;
				$scope.plantRegMap = data.plantRegMap;
				$scope.materialRegMap = data.materialRegMap;
				$scope.sowMap = data.sowMap;
				$scope.weatherMap = data.weatherMap;
				$scope.projShiftMap = data.projShiftMap;
				$scope.workDairyTO = data.workDairyTO;
			}, function (error) {
				GenericAlertService.alertMessage(
					"Error occured while getting work dairy Details", 'Error');
			});
		};
		$scope.getWorkDairyDetails = function () {
			if ($scope.workDairySearchReq.projectLabelKeyTO.projId == null) {
				GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
				return;
			}
			if ($scope.workDairySearchReq.workDairyDate == null) {
				GenericAlertService.alertMessage("Please select Date to fetch report", 'Warning');
				return;
			}
			if ($scope.workDairySearchReq.crewLabelKeyTO.id == null) {
				GenericAlertService.alertMessage("Please select Crew to fetch report", 'Warning');
				return;
			}
			$scope.activeFlag = true;
		
				for (const status of $scope.displayStatusList) {
					
						$scope.displayStatus = status;
					
				}
			
			const workDairyGetReq = {
				"workDairyId": $scope.workDairySearchReq.workDairyId,
				"status": 1,
				"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId
			};
			WorkDiaryService.getWorkDairyDetails(workDairyGetReq).then(function (data) {
				$scope.workDiaryManpowerDetails = $filter('unique')(data.workDairyEmpDtlTOs,'code');
				$scope.workDairyPlantDtlTOs = data.workDairyPlantDtlTOs;
				$scope.workDairyProgressDtlTOs = data.workDairyProgressDtlTOs;
				$scope.workDairyCostCodeList = data.workDairyCostCodeTOs;
				$scope.timeFlag = data.timeFlag;
				$scope.workDairyTO = data.workDairyTO;
				$scope.workDairyMaterialDtlTOs = data.workDairyMaterialDtlTOs;
				if ($scope.subReport && $scope.subReport.code)
					$scope.changeSubReport();
			}, function (error) {
				GenericAlertService.alertMessage("Work Diary Records not available for the search criteria", 'Warning');
			});
		};

		$scope.calculatePlantTotal = function (plantCostTos, property) {
			let total = 0;
			for (const plantCost of plantCostTos) {
				total += plantCost[property];
			}
			return (total === 0) ? '' : total;
		};


		$scope.getProjSettingsWorkDairyDetails = function () {
			var req = {
				"projId": $scope.workDairySearchReq.projectLabelKeyTO.projId,
				"workDairyId": $scope.workDairySearchReq.workDairyId,
				"apprStatus": 'Internal Approval'
			}
			WorkDiaryService
				.getProjSettingsWorkDairyDetails(req)
				.then(
					function (data) {
						$scope.ProjSettingsWorkDairyDetails = data.labelKeyTOs[0];
						if ($scope.ProjSettingsWorkDairyDetails.id <= 0) {
							$scope.timeFlag = true;
						}
					},
					function (error) {
						GenericAlertService
							.alertMessage(
								"Error occured while getting settings workDairy Details",
								'Error');

					})
		};

		$scope.resetWorkDiaryDetails = function () {
			$scope.workDiaryManpowerDetails = [];
			$scope.workDairyPlantDtlTOs = [];
			$scope.workDairyProgressDtlTOs = [];
			$scope.workDairyMaterialDtlTOs = [];
			$scope.workDairyCostCodeList = [];
			$scope.workDiaryPlantDetails = [];
			$scope.workDiaryMaterialDetails = [];
			$scope.workDiaryProgressDetails = [];
			$scope.workDairySearchReq.projectLabelKeyTO = [];
			$scope.workDairySearchReq.crewLabelKeyTO = [];
			$scope.workDairySearchReq.code = '';
			$scope.data = [];
			$scope.labels = [];
			$scope.workDairySearchReq.workDairyDate = $filter('date')((defaultDate), "dd-MMM-yyyy");
			$scope.searchProject = {};
			$scope.crewLabelKeyTO = [];
			$scope.type = '';
			$scope.crewLabelKeyTO.name = null;
			$scope.subReportCode = null;
			$scope.subReport = null;
			$scope.subReportName = null;
			$scope.displayStatus = [];
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
				template: 'views/help&tutorials/reportshelp/workdairyhelp/workdairysearchhelp.html',
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
