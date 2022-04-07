'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("PrecontractslistCurrentStatus", {
		url: '/PrecontractslistCurrentStatus',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/procurement/PrecontractslistCurrentStatus.html',
				controller: 'precontractslistCurrentStatusController'
			}
		}
	})
}]).controller("precontractslistCurrentStatusController", ["$scope", "$filter", "$q", "$state", "ngDialog", "EmpAttendanceService", "GenericAlertService", "PreContractInternalService","EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "generalservice", "stylesService", "ngGridService", "chartService", "uiGridConstants",
	function ($scope, $filter, $q, $state, ngDialog, EmpAttendanceService, GenericAlertService,PreContractInternalService,
		EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {		
	  
      chartService.getChartMenu($scope);	
		$scope.subReportCode = "";
		$scope.subReport = "None";
		$scope.stylesSvc = stylesService;
		$scope.yAxislabels = 'Hours';
		$scope.data = [];
		$scope.labels = [];
		$scope.selectedProjIds = '';
		$scope.yearTos = [];
		$scope.moreFlag = 0;
		$scope.empAttendenceDetails = [];
		$scope.selectedCrewIds = [];
		$scope.daysList = [];
		var currDate = new Date();
		var currYear = currDate.getFullYear();
		var futureYear = currYear + 5;
		var pastYear = currYear - 5;
		for (var i = pastYear; i <= futureYear; i++) {
			$scope.yearTos.push(i);
		}		    		
    $scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate(($scope.toDate.getDate()-90) - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");    

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
		
		$scope.clearSubReportDetails = function () {
			$scope.empAttendenceDetails = [];
			$scope.subReportName = "";
			$scope.subReport = "None";
			$scope.type = "";
			$scope.subReportCode = "";
		};

		$scope.subReports = [{
			name: 'Contract Type wise-Precontracts Budget Estimate',
			code: "direct"
		}, {
			name: 'Current Stage Wise-Precontracts Budget Estimate',
			code: "indirect"
		}];
		function noSubReportData() {
				let columnDefs = [
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Id" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'code', displayName: "Pre-Contract Id", headerTooltip: "Pre-ContractId" },
				{ field: 'preContractType', displayName: "Contract type", headerTooltip: "ContractType" },
				{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description" },
				{ field: 'precontractStage', displayName: "Pre-Contract Stage", headerTooltip: "Pre-Contract Stage" },
				{ field: 'purchaseOrderStatus', displayName: "Post-Contract Status", headerTooltip: "Post-Contract Status" }				
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, []);
			$scope.gridOptions.gridMenuCustomItems = false;
		}
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getcurrentStatusReports();
			}
		};
		
		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];

			if ($scope.subReport.code == "direct") {
				generateSubReportData("projName", "epsName");
			} else if ($scope.subReport.code == "indirect") {
				generateSubReportData("projName", "epsName");
			} 
		};

		function generateSubReportData(key, value) {				
		
			let pCountArr = [], abCountArr = [], phCountArr = [], alCountArr = [], lslCountArr = [], slCountArr = [], mlCountArr = [], cblCountArr = [], cslCountArr = [], uplCountArr = [];			
		    let Stage1ReqCountArr=[],Stage1ApprCountArr=[],RFQCountArr=[],BiddingCountArr=[],Stage2ReqCountArr=[],Stage2ApprCountArr=[],RejectedCountArr=[],POCountArr=[];
			let subReportMap = [];
			$scope.subReportData=[];
			
			if ($scope.subReport.code == "direct") {
			if ($scope.subReport.code == "direct") {
				let costCodeData = [					
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS" },
					{ field: 'mapKey',  displayName: "Project", headerTooltip: "Project"},
					{ field: 'EngineerCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Engineering Services", headerTooltip: "Engineering Services" },
					{ field: 'LabourCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Labour Hire Agreements", headerTooltip: "Labour Hire Agreements" },
					{ field: 'PlantCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Plant Hire Agreements", headerTooltip: "Plant Hire Agreements" },
					{ field: 'SupplyCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Supply Agreements", headerTooltip: "Supply Agreements" },
					{ field: 'PurchaseCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Purchase Order", headerTooltip: "Purchase Order" },
					{ field: 'SubCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Subcontract Agreements", headerTooltip: "Subcontract Agreements" },
					{ field: 'ProfCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Prfessionioal Service Agreements", headerTooltip: "Prfessionioal Service Agreements" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" }
				]
				let data = [];
				$scope.gridOptions11 = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions11.showColumnFooter = true;
				$scope.gridOptions11.gridMenuCustomItems = false;
			}
			$scope.con=$scope.contractlist;
	for (let empDtl of $scope.con) {
					const mapKey = empDtl[key];
					const mapValue = empDtl[value];
					if (!subReportMap[mapKey]) {
						subReportMap[mapKey] = {
							"mapValue": mapValue,
							"mapKey": mapKey,
							"EngineerCount": 0,
							"LabourCount": 0,						
							"PlantCount": 0,
							"SupplyCount": 0,
							"PurchaseCount": 0,							
							"SubCount": 0,
							"ProfCount": 0,						
						};
					}
							
						if (empDtl.preContractType) {
							switch (empDtl.preContractType) {
								case "Engineering Services":
									subReportMap[mapKey].EngineerCount++;
									break;
								case "Labour Hire Agreement":
									subReportMap[mapKey].LabourCount++;
									break;
								case "Plant Hire Agreement":
									subReportMap[mapKey].PlantCount++;
									break;
								case "Supply Agreement":
									subReportMap[mapKey].SupplyCount++;									
									break;
									case "Purchase  Order":
									subReportMap[mapKey].PurchaseCount++;
									break;
									case "Sub Contract agreement":
									subReportMap[mapKey].SubCount++
									break;
								case "Professional Services agreement":
									subReportMap[mapKey].ProfCount++						
									break;								
								default:
									break;
							}
						}
				}
for (const index in subReportMap) {
	                abCountArr.push(subReportMap[index].EngineerCount);
					pCountArr.push(subReportMap[index].LabourCount);
					mlCountArr.push(subReportMap[index].PlantCount);
					phCountArr.push(subReportMap[index].SupplyCount);
					slCountArr.push(subReportMap[index].PurchaseCount);
					lslCountArr.push(subReportMap[index].SubCount);
					alCountArr.push(subReportMap[index].ProfCount);		
				    $scope.labels.push(subReportMap[index].mapKey);				
				

$scope.subReportData.push(subReportMap[index]);
				}
				for (let empAttendence of $scope.subReportData) {
					empAttendence.totalCount = empAttendence.LabourCount + empAttendence.EngineerCount + empAttendence.SupplyCount + empAttendence.ProfCount + empAttendence.PurchaseCount + empAttendence.PlantCount;
				}
		    $scope.gridOptions11.data = angular.copy($scope.subReportData);
	        $scope.data.push(abCountArr);		    
            $scope.data.push(pCountArr);
			$scope.data.push(mlCountArr);
			$scope.data.push(phCountArr);
			$scope.data.push(slCountArr);
			$scope.data.push(lslCountArr);
			$scope.data.push(alCountArr);			
			$scope.initGraph();
			}
			
         if ($scope.subReport.code == "indirect") {
			if ($scope.subReport.code == "indirect") {
				let costCodeData = [			
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS" },
					{ field: 'mapKey',  displayName: "Project", headerTooltip: "Project"},
					{ field: 'Stage1ReqCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Stage1 Request", headerTooltip: "Stage1 Request" },
					{ field: 'Stage1ApprCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Stage1 Approval", headerTooltip: "Stage1 Approval" },
					{ field: 'RFQCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "RFQ/Tendering", headerTooltip: "RFQ/Tendering" },
					{ field: 'BiddingCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Bidding Analysis", headerTooltip: "Bidding Analysis" },
					{ field: 'Stage2ReqCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Stage2 Request", headerTooltip: "Stage2 Request" },
					{ field: 'Stage2ApprCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Stage2 Approval", headerTooltip: "Stage2 Approval" },
					{ field: 'RejectedCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Rejected", headerTooltip: "Rejected" },
					{ field: 'POCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "PO Issued", headerTooltip: "PO Issued" },
					{ field: 'totalCount', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Total", headerTooltip: "Total" }					
				]
				let data = [];
				$scope.gridOptions12 = ngGridService.initGrid($scope, costCodeData, data);
				$scope.gridOptions12.showColumnFooter = true;
				$scope.gridOptions12.gridMenuCustomItems = false;
			}
			$scope.con1=$scope.contractlist;
			
            for (let empDtl of $scope.con1) {
	
					const mapKey = empDtl[key];
					const mapValue = empDtl[value];
					if (!subReportMap[mapKey]) {
						subReportMap[mapKey] = {
							"mapValue": mapValue,
							"mapKey": mapKey,
							"Stage1ReqCount": 0,
							"Stage1ApprCount": 0,						
							"RFQCount": 0,
							"BiddingCount": 0,
							"Stage2ReqCount": 0,							
							"Stage2ApprCount": 0,
							"RejectedCount": 0,	
							"POCount": 0,						
						};
					}
							
						if (empDtl.contractStageStatus) {
							switch (empDtl.contractStageStatus) {
								case "Stage 1 Request":
									subReportMap[mapKey].Stage1ReqCount++;
									break;
								case "Stage 1 Approval":
									subReportMap[mapKey].Stage1ApprCount++;
									break;
								case "RFQ/Tendering":
									subReportMap[mapKey].RFQCount++;
									break;
								case "Bidding Analysis":
									subReportMap[mapKey].BiddingCount++;									
									break;
									case "Stage 2 Request":
									subReportMap[mapKey].Stage2ReqCount++;
									break;
									case "Stage 2 Approved":
									subReportMap[mapKey].Stage2ApprCount++
									break;
								case "Rejected":
									subReportMap[mapKey].RejectedCount++						
									break;	
								case "Purchase Order":
									subReportMap[mapKey].POCount++						
									break;							
								default:
									break;
							}
						}
				}

                 for (const index in subReportMap) {
	                Stage1ReqCountArr.push(subReportMap[index].Stage1ReqCount);
					Stage1ApprCountArr.push(subReportMap[index].Stage1ApprCount);
					RFQCountArr.push(subReportMap[index].RFQCount);
					BiddingCountArr.push(subReportMap[index].BiddingCount);
					Stage2ReqCountArr.push(subReportMap[index].Stage2ReqCount);
					Stage2ApprCountArr.push(subReportMap[index].Stage2ApprCount);
					RejectedCountArr.push(subReportMap[index].RejectedCount);	
					POCountArr.push(subReportMap[index].POCount);										
				    $scope.labels.push(subReportMap[index].mapKey);
					$scope.subReportData.push(subReportMap[index]);
				}
					for (let empAttendence of $scope.subReportData) {
					empAttendence.totalCount = empAttendence.Stage1ReqCount + empAttendence.Stage1ApprCount + empAttendence.RFQCount + empAttendence.BiddingCount + empAttendence.Stage2ReqCount + empAttendence.Stage2ApprCount + empAttendence.RejectedCount + empAttendence.POCount;
				}
		    $scope.gridOptions12.data = angular.copy($scope.subReportData);
	        $scope.data.push(Stage1ReqCountArr);		    
            $scope.data.push(Stage1ApprCountArr);
			$scope.data.push(RFQCountArr);
			$scope.data.push(BiddingCountArr);
			$scope.data.push(Stage2ReqCountArr);
			$scope.data.push(Stage2ApprCountArr);
			$scope.data.push(RejectedCountArr);	
			$scope.data.push(POCountArr);			
			$scope.initGraph();
			}
	
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				
			} else {
				$scope.plantDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				
			}
		};
		
		$scope.getcurrentStatusReports = function(){
		var req = {
			"status" : 1,
			"projIds" : $scope.selectedProjIds,
			"loginUser" : false,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		}
		if ($scope.userType == '2') {
			req.loginUser = false;
		}
		$scope.searchReq = req;
		//editPreContractList = [];
		PreContractInternalService.getPreContractList(req).then(function(data) {
			$scope.contractlist = data.userProjMap;
			$scope.contractlist = data.preContractTOs;
			$scope.gridOptions1.data = $scope.contractlist;
			  generateSubReportData1('projName','projName');
			$scope.gridOptions.data = angular.copy($scope.contractlist);			
			if(click=='click'){
				if ($scope.contractlist.length <= 0) {
					GenericAlertService.alertMessage("Pre-contracts not available for given search criteria", 'Warning');
				}
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});
		}
				
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
				{ name: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Id" },
				{ name: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'code', displayName: "Pre-Contract Id", headerTooltip: "Pre-ContractId" },
				{ field: 'preContractType', displayName: "Contract type", headerTooltip: "ContractType" },
				{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description" },
				{ field: 'contractStageStatus', displayName: "Pre-Contract Stage", headerTooltip: "Pre-Contract Stage"},
				{ field: 'workFlowStatusTO.desc', displayName: "Post-Contract Status", headerTooltip: "Post-Contract Status" },
				
					];
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data);
				}
			});
		$scope.resetPrecontractStatus = function () {
			$scope.plantDetails = [];
			$scope.data = [];
			$scope.series=[];
			$scope.series2=[];
			series1=[];
			$scope.srk=[];
			$scope.labels = [];
			$scope.selectedProjIds = [];
			$scope.selectedCrewIds = [];
			$scope.selectedCompanyIds = [];
			$scope.searchProject = {};
			$scope.type = '';
			$scope.subReportName = null;
			$scope.crewNameDisplay = null;
			$scope.companyNameDisplay = null;
			$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy"); 
			$scope.subReport = "None";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};

		$scope.clickForward = function (moreFlag) {
			if ($scope.moreFlag < 32) {
				$scope.moreFlag = moreFlag + 5;
			}
		};
		$scope.clickBackward = function (moreFlag) {
			if ($scope.moreFlag > 0) {
				$scope.moreFlag = moreFlag - 5;
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
		
		$scope.subReportData = [];
		$scope.data = [];
		var labels = [];
	$scope.series2 = ['Stage 1 Request', 'Stage 1 Approval', 'RFQ/Tendering', 'Bidding Analysis', 'Stage 2 Request', 'Stage 2 Approved', 'Rejected', 'PO Issued'];
		var series1 = ['Engineering Services', 'Labour Hire Agreements', 'Plant Hire Agreements', 'Supply Agreements', 'Purchase Order', 'Subcontract Agreements', 'Prfessionioal Service Agreements'];
	

$scope.initGraph = function () {
			$scope.series = series1;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();
		};
		
		var helppagepopup;
		HelpService.pageHelp = function () {
			var deferred = $q.defer();
			helppagepopup = ngDialog.open({
				template: 'views/help&tutorials/reportshelp/attendancerecordshelp/dailyemphelp.html',
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
