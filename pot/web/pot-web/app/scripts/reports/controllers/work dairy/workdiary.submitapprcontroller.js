'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("workdiarysubmitappr", {
		url: '/workdiarysubmitappr',
		data: {
			reports: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/work dairy/workdiary.submissionappr.html',
				controller: 'WorkDiarySubmitApprController'
			}
		}
	})
}]).controller("WorkDiarySubmitApprController", ["$scope","$q", "ngDialog", "$filter", "EpsProjectMultiSelectFactory", "GenericAlertService", "WorkDiaryService", "stylesService", "ngGridService", 'chartService', function ($scope,$q, ngDialog, $filter, EpsProjectMultiSelectFactory,
	GenericAlertService, WorkDiaryService, stylesService, ngGridService, chartService) {
	$scope.stylesSvc = stylesService;
	chartService.getChartMenu($scope);
	$scope.workDiaryDetails = [];
	$scope.selectedProjIds = '';
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);
	let todayDate = new Date();
	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
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
	$scope.apprStatusList = [{
		name: "Submitted for Approval",
		code: 'submittedforapproval'
	}, {
		name: "Client Approved",
		code: 'client approved'
	}, {
		name: "Approved",
		code: 'approved'
	}, {
		name: "Submitted for Client Approval",
		code: 'submittedforclientapproval'
	}, {
		name: "Under Preparation",
		code: 'preparation'
	},{
		name: "All",
		code: null
	}];

	$scope.subReports = [{
		name: "Supervisor ID Wise-Workdiary Approval status Report",
		code: "supervisor"
	}, {
		name: "Project Wise-Workdiary Approval status Report",
		code: "proj"
	}, {
		name: "EPS ID Wise-Workdiary Approval status Report",
		code: "eps"
	}]

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
	function noSubReportData() {
		let columnDefs = [
			{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false },
			{ field: 'projectTO.parentName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
			{ field: 'projectTO.name', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
			{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew", groupingShowAggregationMenu: false },
			{ field: 'code', displayName: "Work Diary Id", headerTooltip: "Work Diary Id", groupingShowAggregationMenu: false },
			{ field: 'reqUserName', displayName: "Supervisor Name", headerTooltip: "Supervisor Name", groupingShowAggregationMenu: false },
			{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status", groupingShowAggregationMenu: false },
			{ field: 'apprUserName', displayName: "Approver", headerTooltip: "Approver Name", groupingShowAggregationMenu: false },
		];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
	}
	$scope.changeSubReport = function () {
		$scope.type = 'chartTable';
		let compareProperty = null;
		let codeProperty = null;
		switch ($scope.subReport.code) {
			case 'supervisor':
				compareProperty = "reqUserId";
				codeProperty = "reqUserName";
				$scope.tableHeading = "Supervisor";
				if ($scope.subReport.code == "supervisor") {
					let overAllData = [
						{ field: 'code', displayName: "Supervisor Name", headerTooltip: "Supervisor Name", groupingShowAggregationMenu: false },
						{ field: 'notSubmittedCount', displayName: "Under Preparation Count", headerTooltip: "Under Preparation Count", groupingShowAggregationMenu: false },
						{ field: 'inProgressCount', displayName: "Approval InProgress Count", headerTooltip: "Approval InProgress Count", groupingShowAggregationMenu: false },
						{ field: 'approvedCount', displayName: "Approved Count", headerTooltip: "Approved Count", groupingShowAggregationMenu: false },
						{ field: 'clientApprovedCount', displayName: "Client Approved Count", headerTooltip: "Client Approved Count", groupingShowAggregationMenu: false },
						{ field: 'totalCount', displayName: "Total Count", headerTooltip: "Total Count", groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_WD_SAAS_Supervisor");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case 'proj':
				compareProperty = "projId";
				codeProperty = "projectTO.name";
				$scope.tableHeading = "Project";
				if ($scope.subReport.code == "proj") {
					let overAllData = [
						{ field: 'code', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'notSubmittedCount', displayName: "Under Preparation Count", headerTooltip: "Under Preparation Count", groupingShowAggregationMenu: false },
						{ field: 'inProgressCount', displayName: "Approval InProgress Count", headerTooltip: "Approval InProgress Count", groupingShowAggregationMenu: false },
						{ field: 'approvedCount', displayName: "Approved Count", headerTooltip: "Approved Count", groupingShowAggregationMenu: false },
						{ field: 'clientApprovedCount', displayName: "Client Approved Count", headerTooltip: "Client Approved Count", groupingShowAggregationMenu: false },
						{ field: 'totalCount', displayName: "Total Count", headerTooltip: "Total Count", groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_WD_SAAS_Proj");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			case 'eps':
				compareProperty = "parentProjId";
				codeProperty = "projectTO.parentName";
				$scope.tableHeading = "EPS";
				if ($scope.subReport.code == "eps") {
					let overAllData = [
						{ field: 'code', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
						{ field: 'notSubmittedCount', displayName: "Under Preparation Count", headerTooltip: "Under Preparation Count", groupingShowAggregationMenu: false },
						{ field: 'inProgressCount', displayName: "Approval InProgress Count", headerTooltip: "Approval InProgress Count", groupingShowAggregationMenu: false },
						{ field: 'approvedCount', displayName: "Approved Count", headerTooltip: "Approved Count", groupingShowAggregationMenu: false },
						{ field: 'clientApprovedCount', displayName: "Client Approved Count", headerTooltip: "Client Approved Count", groupingShowAggregationMenu: false },
						{ field: 'totalCount', displayName: "Total Count", headerTooltip: "Total Count", groupingShowAggregationMenu: false },
					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, overAllData, data, "Reports_WD_SAAS_EPS");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
				break;
			default:
				$scope.workDiaryDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getWorkDairyApprovalReport();
		}
		const subReportMap = new Array();
		for (const workDairy of $scope.workDiaryDetails) {
			if (!subReportMap[workDairy[compareProperty]]) {
				subReportMap[workDairy[compareProperty]] = {
					'code': null,
					'approvedCount': 0,
					'inProgressCount': 0,
					'notSubmittedCount': 0,
					'clientApprovedCount': 0,
				};
				if (codeProperty.includes(".")) {
					const propArray = codeProperty.split(".");
					subReportMap[workDairy[compareProperty]].code = workDairy[propArray[0]][propArray[1]];
				} else {
					subReportMap[workDairy[compareProperty]].code = workDairy[codeProperty];
				}
			}

			switch (workDairy.apprStatus) {
				// Not submitted
				case 'Under Prepration':
					subReportMap[workDairy[compareProperty]].notSubmittedCount += 1;
					
					break;

				// Pending Approval
				case 'SubmittedForApproval':
				case 'SubmittedForClientApproval':
					subReportMap[workDairy[compareProperty]].inProgressCount += 1;
					break;

				// Approved
				case 'Approved':
					subReportMap[workDairy[compareProperty]].approvedCount += 1;
					break;

				// client Approved
				case 'Client Approved':
					subReportMap[workDairy[compareProperty]].clientApprovedCount += 1;
					break;
					
				//Under Preparation
				case 'preparation':
				subReportMap[workDairy[compareProperty]].preparationCount += 1;
					break;
			}
		}
		setGraphData(subReportMap);
	};

	function setGraphData(subReportMap) {
		$scope.labels = new Array();
		$scope.data = new Array();
		let obj = null;
		const approved = new Array();
		const inProgress = new Array();
		const notSubitted = new Array();
		const clientApproved = new Array();
		$scope.subReportData = new Array();
		$scope.series = ['Under Preparation', 'Pending for Approval', 'Approved', 'Client Approved',];
		for (const key in subReportMap) {
			obj = subReportMap[key];
			approved.push(obj.approvedCount);
			inProgress.push(obj.inProgressCount);
			notSubitted.push(obj.notSubmittedCount);
			clientApproved.push(obj.clientApprovedCount);
			$scope.labels.push(obj.code);
			$scope.subReportData.push(obj);
		}
		for (let workDiary of $scope.subReportData) {
			workDiary.totalCount = workDiary.notSubmittedCount + workDiary.inProgressCount + workDiary.approvedCount + workDiary.clientApprovedCount;
		}
		$scope.gridOptions.data = angular.copy($scope.subReportData);
		$scope.data.push(notSubitted);		
		$scope.data.push(inProgress);
		$scope.data.push(approved);
		$scope.data.push(clientApproved);
		initGraph();
	}

	function initGraph() {
		$scope.datasetOverride = new Array();
		$scope.chart_type = 'bar';
		chartService.defaultBarInit();
	};

	$scope.getWorkDairyApprovalReport = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
			return;
		}
		if ($scope.apprStatus == undefined) {
			GenericAlertService.alertMessage("Please select Approval Status to fetch report", 'Warning');
			return;
		}
		
		
		var req = {
			"projIds": $scope.selectedProjIds,
			"apprStatus": $scope.apprStatus.code,
			"fromDate": $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
		};
		WorkDiaryService.getWorkDairyApprovalReport(req).then(function (data) {
			$scope.workDiaryDetails = data;
			// console.log('==================',data)
			for (let workDiary of $scope.workDiaryDetails) {
				workDiary.date = $filter('date')((workDiary.date), "dd-MMM-yyyy");
				if (!workDiary.apprStatus) {
					workDiary.apprStatus = 'Under Prepration';
					workDiary.apprUserName = workDiary.reqUserName;
					workDiary.apprUserId = workDiary.reqUserId;
					workDiary.apprUserCode = workDiary.reqUserCode;
				}
			}
			$scope.gridOptions.data = angular.copy($scope.workDiaryDetails);
			if ($scope.subReport && $scope.subReport.code) {
				$scope.changeSubReport();
			}
			if ($scope.workDiaryDetails.length <= 0) {
				GenericAlertService.alertMessage("Work Diary-Approval Status Report not available for the selected criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Workdiary Details", 'Error');
		});

	};
	$scope.clearSubReportDetails = function () {
		$scope.subReport = 'None';
		$scope.workDiaryDetails = [];
		$scope.subReportCode = "";
		$scope.subReportName = "";
	};
	$scope.resetWorkDiaryDetails = function () {
		$scope.workDiaryDetails = [];
		$scope.data = [];
		$scope.labels = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = '';
		$scope.type = '';
		$scope.fromDate = null;
		$scope.toDate = null;
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.apprStatus = null;
		$scope.subReport = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}

	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'date', width: '6%', displayName: "Date", headerTooltip: "Date", cellFilter: 'date', type: 'date', groupingShowAggregationMenu: false  },
				{ field: 'projectTO.parentName', width: '6%', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
				{ field: 'projectTO.name', width: '10%', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
				{ field: 'crewName', width: '14%', displayName: "Crew", headerTooltip: "Crew", groupingShowAggregationMenu: false },
				{ field: 'code', width: '13%', displayName: "Work Diary Id", headerTooltip: "Work Diary Id", groupingShowAggregationMenu: false },
				{ field: 'reqUserName', width: '20%', displayName: "Supervisor Name", headerTooltip: "Supervisor Name", groupingShowAggregationMenu: false },
				{ field: 'apprStatus', width: '9%', displayName: "Approval Status", headerTooltip: "Approval Status", groupingShowAggregationMenu: false },
				{ field: 'apprUserName', width: '20%', displayName: "Approver", headerTooltip: "Approver Name", groupingShowAggregationMenu: false },
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs,  data, "Reports_WD_SAAS");
			
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
			template: 'views/help&tutorials/reportshelp/workdairyhelp/workdairysubmissionhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}

	//Grouping list
	$scope.group = function() {
		$scope.gridApi.grouping.clearGrouping();
		$scope.gridApi.grouping.groupColumn('crewName');
		// $scope.gridApi.grouping.aggregateColumn('code', uiGridGroupingConstants.aggregation.COUNT);
	};
	  
	// Export Excel    
$scope.export = function()
{
  $scope.export_format == 'csv'
  var myElement = angular.element(document.querySelectorAll(".custom-csv-link-location"));
  $scope.gridApi.exporter.csvExport( 'all', $scope.export_column_type, myElement );
} ;
	// print PDF 
$scope.print = function()
{
  $scope.export_format == 'pdf'
  var myElement = angular.element(document.querySelectorAll(".custom-csv-link-location"));
  $scope.gridApi.exporter.pdfExport( 'all', $scope.export_column_type, myElement,);
  window.print();
};
	  
}]);