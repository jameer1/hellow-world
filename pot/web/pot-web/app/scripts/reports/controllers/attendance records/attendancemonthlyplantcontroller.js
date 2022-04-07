'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("monthlyplant", {
		url: '/monthlyplant',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/attendance records/attendmonthlyplant.html',
				controller: 'AttendanceMonthlyPlantController'
			}
		}
	})
}]).controller("AttendanceMonthlyPlantController", ["$scope", "uiGridGroupingConstants", "$q", "$state", "ngDialog", "$filter", "GenericAlertService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "PlantAttendanceService", "generalservice", "stylesService", "ngGridService", "chartService", "uiGridConstants", function ($scope, uiGridGroupingConstants, $q, $state, ngDialog, $filter, GenericAlertService,
	EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, PlantAttendanceService, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {
	chartService.getChartMenu($scope);
	$scope.subReport = "None";
	var labels = [];
	$scope.stylesSvc = stylesService;
	$scope.subReportCode = "";
	$scope.plantAttendenceDetails = [];
	$scope.selectedProjIds = [];
	$scope.selectedCrewIds = [];
	let totalValues = {
		wCount: 0,
		nwCount: 0,
		idleCount: 0
	};
	$scope.totalValues = angular.copy(totalValues);
	$scope.subReports = [{
		name: 'Periodical Records',
		code: "periodical"
	}, {
		name: 'TradeWise Counting Records',
		code: "trade"
	}, {
		name: 'CompanyWise Counting Records',
		code: "cmp"
	}, {
		name: 'ProjectWise Counting Records',
		code: "proj"
	}, {
		name: 'EPSWise Counting Records',
		code: "eps"
	}];

	let todayDate = new Date();
	let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
	var fromdate = $filter('date')((lastMonthDate), "MMM-yyyy");
	// $scope.fromDate = fromdate;
	// $scope.toDate = $filter('date')((todayDate), "MMM-yyyy");

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
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	$scope.getCrewList = function () {
		if ($scope.selectedProjIds.length <= 0) {
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
			$scope.plantAttendenceDetails = [];
			$scope.clearSubReportDetails();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
		});
	};
	
	function noSubReportData() {
		let columnDefs = [
			{ field: 'displayNamesMap.month', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.plantCode', displayName: "Asset ID", headerTooltip: "Asset ID", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.plantDesc', displayName: "Plant Desc", headerTooltip: "Plant Description", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.plantMake', displayName: "Plant Make", headerTooltip: "Plant Make", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.plantContrName', displayName: "Resource", headerTooltip: "Resource Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.plantModel', displayName: "Plant Model", headerTooltip: "Plant Model", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
			{ field: 'displayNamesMap.companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false  },
			{
				name: 'displayNamesMap.W',
				cellTemplate: '<div>{{row.entity.displayNamesMap.W ? row.entity.displayNamesMap.W : 0 }}</div>',
				displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}

			},
			{
				name: 'displayNamesMap.NW',
				cellTemplate: '<div>{{row.entity.displayNamesMap.NW ? row.entity.displayNamesMap.NW : 0 }}</div>',
				displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}

			},
			{
				name: 'displayNamesMap.I',
				cellTemplate: '<div>{{row.entity.displayNamesMap.I ? row.entity.displayNamesMap.I : 0 }}</div>',
				displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}

			}
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [], "Report_Atten_AttenMonthlyPlant");
	}
	$scope.changeSubReport = function () {
		if ($scope.subReport && $scope.subReport != "None") {
			$scope.type = 'chartTable';
			$scope.subReportName = $scope.subReport.name;
			$scope.subReportCode = $scope.subReport.code
			prepareSubReport();
		} else {
			$scope.plantAttendenceDetails = [];
			
			$scope.type = '';
			$scope.subReportName = '';
			$scope.subReportCode = '';
			noSubReportData();
			$scope.getMonthlyPlantAttendenceDetails();
		}
	};

	function prepareSubReport() {
		$scope.labels = [];
		$scope.subReportData = [];
		$scope.data = [];
		if ($scope.subReport.code == "periodical") {
			generateSubReportData("", "", true);
		} else if ($scope.subReport.code == "trade") {
			generateSubReportData("plantClassId", "plantContrName");
		} else if ($scope.subReport.code == "cmp") {
			generateSubReportData("companyId", "companyName");
		} else if ($scope.subReport.code == "proj") {
			generateSubReportData("projId", "projName");
		} else if ($scope.subReport.code == "eps") {
			generateSubReportData("epsId", "epsName");
		}
	};

	function generateSubReportData(key, value, monthWise) {
		let wCountArr = [], nwCountArr = [], idleCountArr = [];
		$scope.totalValues = angular.copy(totalValues);
		let subReportMap = [];
		if ($scope.subReport.code == "periodical") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
				{ field: 'wCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
},
				{ field: 'nwCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
},
				{ field: 'idleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
},
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}
},
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Report_Atten_AttenMonthlyPlant_Periodical");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "trade") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Resource Name", headerTooltip: "Resource Name", groupingShowAggregationMenu: false  },
				{ field: 'wCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'nwCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'idleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Atten_AttenMonthlyPlant_Trade");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "cmp") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Company", headerTooltip: "Company", groupingShowAggregationMenu: false  },
				{ field: 'wCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'nwCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'idleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Report_Atten_AttenMonthlyPlant_CMP");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "proj") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
				{ field: 'wCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'nwCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'idleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Report_Atten_AttenMonthlyPlant_Proj");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		if ($scope.subReport.code == "eps") {
			let costCodeData = [
				{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
				{ field: 'wCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'nwCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'idleCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total", groupingShowAggregationMenu: false, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Report_Atten_AttenMonthlyPlant_EPS");
			$scope.gridOptions.showColumnFooter = true;
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		for (const attdDtl of $scope.plantAttendenceDetails) {
			let mapKey;
			let mapValue;
			if (monthWise) {
				mapKey = attdDtl.displayNamesMap.month;
				mapValue = attdDtl.displayNamesMap.month;
			} else {
				mapKey = attdDtl.displayNamesMap[key];
				mapValue = attdDtl.displayNamesMap[value];
			}
			if (!subReportMap[mapKey]) {
				let wCount = 0, nwCount = 0, idleCount = 0;
				subReportMap[mapKey] = {
					"empCategory": value,
					"mapKey": mapKey,
					"mapValue": mapValue,
					"wCount": wCount,
					"nwCount": nwCount,
					"idleCount": idleCount
				};
			}
			for (const attdType of generalservice.plantAttendanceTypes) {
				if (attdDtl.displayNamesMap[attdType.code]) {
					const codeCount = parseInt(attdDtl.displayNamesMap[attdType.code]);
					switch (attdType.code) {
						case generalservice.plantAttendanceTypes[0].code:
							subReportMap[mapKey].wCount += codeCount;
							$scope.totalValues.wCount += codeCount;
							break;
						case generalservice.plantAttendanceTypes[1].code:
							subReportMap[mapKey].nwCount += codeCount;
							$scope.totalValues.nwCount += codeCount;
							break;
						case generalservice.plantAttendanceTypes[2].code:
							subReportMap[mapKey].idleCount += codeCount;
							$scope.totalValues.idleCount += codeCount;
							break;
						default:
							break;
					}
				}
			}
		}
		for (const index in subReportMap) {
			wCountArr.push(subReportMap[index].wCount);
			nwCountArr.push(subReportMap[index].nwCount);
			idleCountArr.push(subReportMap[index].idleCount);
			$scope.labels.push(subReportMap[index].mapValue);
			$scope.subReportData.push(subReportMap[index]);
		}
		for (let plantAttendence of $scope.subReportData) {
			plantAttendence.totalCount = plantAttendence.wCount + plantAttendence.nwCount + plantAttendence.idleCount;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(wCountArr);
		$scope.data.push(nwCountArr);
		$scope.data.push(idleCountArr);
	};

	$scope.data = [];
	var labels = [];
	var series = ['Working', 'Non Working', 'Idle'];
	$scope.initGraph = function () {
		$scope.series = series;
		$scope.labels = labels;
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit();
	}
	
	$scope.getMonthlyPlantAttendenceDetails = function () {
		$scope.plantAttendenceDetails = [];
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select Project to get details", 'Warning');
			return;
		}
		if ($scope.selectedCrewIds.length <= 0) {
			GenericAlertService.alertMessage("Please select Crew to get details", 'Warning');

			return;
		}
		if ($scope.fromDate == undefined) {
			GenericAlertService.alertMessage("Please select From Date to get details", 'Warning');

			return;
		}
		if ($scope.toDate == undefined) {
			GenericAlertService.alertMessage("Please select To Date to get details", 'Warning');

			return;
		}
		if (new Date($scope.fromDate) > new Date($scope.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}
		var attendReq = {
			"projIds": $scope.selectedProjIds,
			"crewIds": $scope.selectedCrewIds,
			"fromDate": $scope.fromDate,
			"toDate": $scope.toDate,
			"subReportType": $scope.subReportCode
		};
		PlantAttendanceService.getDailyPlantAttendanceReportBtwnDates(attendReq).then(function (data) {
			$scope.plantAttendenceDetails = data;
			   
			 for(var sample of $scope.plantAttendenceDetails){
				sample.idle=sample.displayNamesMap.I ? sample.displayNamesMap.I : 0;
			}    
			 for(var sample of $scope.plantAttendenceDetails){
				sample.Nonworking=sample.displayNamesMap.NW ? sample.displayNamesMap.NW : 0;
			} 
			   
			$scope.gridOptions1.data = angular.copy($scope.plantAttendenceDetails);
			if ($scope.subReport && $scope.subReport != "None") {
				prepareSubReport();
			}
			if ($scope.plantAttendenceDetails.length <= 0) {
				GenericAlertService.alertMessage("Monthly Plant Attendance Records not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting Plants Attendance details", 'Error');
		});
		$scope.initGraph();
	};
	$scope.clearSubReportDetails = function () {
		$scope.plantAttendenceDetails = [];
		$scope.type = "";
		$scope.subReportName = "";
		$scope.subReportCode = "";
		$scope.subReport = "None";
	};
	$scope.resetPlantAttendData = function () {
		$scope.plantAttendenceDetails = [];
		$scope.selectedCrewIds = [];
		$scope.selectedProjIds = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.gridOptions.showColumnFooter = false;
		$scope.type = '';
		$scope.subReportName = null;
		$scope.crewNameDisplay = null;
		$scope.fromDate = $filter('date')((lastMonthDate), "MMM-yyyy");
		$scope.toDate = $filter('date')((todayDate), "MMM-yyyy");
		$scope.subReportCode = "";
		$scope.subReport = "None";
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'displayNamesMap.month', displayName: "Month", headerTooltip: "Month", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.plantCode', displayName: "Asset ID", headerTooltip: "Asset ID", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.plantDesc', displayName: "Plant Desc", headerTooltip: "Plant Description", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.plantMake', displayName: "Plant Make", headerTooltip: "Plant Make", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.plantContrName', displayName: "Resource", headerTooltip: "Resource Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.plantModel', displayName: "Plant Model", headerTooltip: "Plant Model", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false  },
				{ field: 'displayNamesMap.companyName', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false  },
				
				{
					name: 'displayNamesMap.W', headerCellClass: "justify-right",
					cellTemplate: '<div class="justify-right ui-grid-cell-contents">{{row.entity.displayNamesMap.W ? row.entity.displayNamesMap.W : 0}}</div>',
					displayName: "Working", headerTooltip: "Working", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'Nonworking',
					displayName: "Non Working", headerTooltip: "Non Working", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				},
				{
					name: 'idle',
					displayName: "Idle", headerTooltip: "Idle", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}
				}
			]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data, "Reports_Atten_AttenMonthlyPlant");
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
			template: 'views/help&tutorials/reportshelp/attendancerecordshelp/monthlyplanthelp.html',
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
