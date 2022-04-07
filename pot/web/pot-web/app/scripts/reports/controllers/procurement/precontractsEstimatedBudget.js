'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("precontractsEstimatedBudget", {
		url: '/precontractsEstimatedBudget',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/procurement/precontractsEstimatedBudget.html',
				controller: 'precontractsEstimatedBudgetController'
			}
		}
	})
}]).controller("precontractsEstimatedBudgetController", ["$scope", "$filter", "$q", "$state", "ngDialog", "EmpAttendanceService", "GenericAlertService", "PreContractInternalService","EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "generalservice", "stylesService", "ngGridService", "chartService", "uiGridConstants",
	function ($scope, $filter, $q, $state, ngDialog, EmpAttendanceService, GenericAlertService,PreContractInternalService,
		EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, generalservice, stylesService, ngGridService, chartService, uiGridConstants) {
		
	chartService.getChartMenu($scope);
		$scope.subReportCode = "";
		$scope.subReport = "None";
		$scope.stylesSvc = stylesService;
		$scope.selectedProjIds = '';
		$scope.yearTos = [];
		$scope.moreFlag = 0;
		$scope.empAttendenceDetails = [];
		$scope.selectedCrewIds = [];
		$scope.daysList = [];
		$scope.contractlist = [];
		var budget = 0;
		$scope.monthTOs = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		var currDate = new Date();
		var currYear = currDate.getFullYear();
		var futureYear = currYear + 5;
		var pastYear = currYear - 5;
		for (var i = pastYear; i <= futureYear; i++) {
			$scope.yearTos.push(i);
		}
		let totalValues = {
			pCount: 0,
			abCount: 0,
			phCount: 0,
			alCount: 0,
			lslCount: 0,
			slCount: 0,
			mlCount: 0,
			cblCount: 0,
			cslCount: 0,
			uplCount: 0
		};
    $scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate(($scope.toDate.getDate()-90) - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");    
	
		$scope.totalValues = angular.copy(totalValues);
		var labels = [];
		let tempInitColList = null;
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
		$scope.tempCurrentCols = [];
		$scope.getSelectedMonthDays = function (monthDetails, year) {
			var req = {
				"status": 1,
				"attendenceMonth": monthDetails + "-" + year
			}
			EmpAttendanceService.getAttendanceDays(req).then(function (data) {
				$scope.daysList = data.attendenceDays;
				if (!tempInitColList)
					tempInitColList = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.columnDefs = angular.copy(tempInitColList);
				$scope.daysList.map(day => {
					let column = {};
					column.field = day;
					column.displayName = day;
					column.headerTooltip = day;
					column.visible = false;
					$scope.gridOptions.columnDefs.push(column);
				});
				$scope.initialColumnSet = $scope.gridOptions.columnDefs;
			})
		};

		$scope.subReports = [{
			name: 'Contract Type wise-Precontracts Budget Estimate',
			code: "direct"
		}, {
			name: 'Current Stage Wise-Precontracts Budget Estimate',
			code: "indirect"
		}];
	
	
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				//$scope.empAttendenceDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getEstimateBudgetReports();
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
					{ field: 'mapValue', displayName: "Eps", headerTooltip: "Eps Name" },
					{ field: 'mapKey',  displayName: "Project", headerTooltip: "Project Name"},
					{ field: 'currencyCode', aggregationType: uiGridConstants.aggregationTypes.sum, displayName: "Currency", headerTooltip: "Currency" },
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
							"currencyCode": empDtl.currencyCode,							
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
					empAttendence.totalCount =empAttendence.EngineerCount + empAttendence.LabourCount + empAttendence.PlantCount + empAttendence.SupplyCount +  empAttendence.PurchaseCount + empAttendence.SubCount + empAttendence.ProfCount;
				}
		    $scope.gridOptions11.data = angular.copy($scope.subReportData);
	        $scope.data.push(abCountArr);		    
            $scope.data.push(pCountArr);
			$scope.data.push(mlCountArr);
			$scope.data.push(phCountArr);
			$scope.data.push(slCountArr);
			$scope.data.push(lslCountArr);
			$scope.data.push(alCountArr);			
		/*	$scope.initGraph();*/
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
		
		
		$scope.subReportData = [];
		$scope.data = [];
		var labels = [];
		var series = ['Present', 'Absent', 'Public Holiday', 'Annual Leave', 'Long Service Leave', 'Sick Leave', 'Maternity / Parental Leave', 'Compassionate & Bereavement Leave', 'Community Service Leave', 'Unpaid Leave'];
		$scope.initGraph = function (type) {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit();

		};
		$scope.getEstimateBudgetReports = function(){
		var req = {
			"status" : 1,
			"projIds" : $scope.selectedProjIds,
			"loginUser" : false,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		}
		console.log(req);
		if ($scope.userType == '2') {
			req.loginUser = false;
		}
		$scope.searchReq = req;
		//editPreContractList = [];
		PreContractInternalService.getPreContractListReport(req).then(function(data) {
		console.log(data);
			$scope.userProjMap = data.userProjMap;
			$scope.contractlist = data.preContractTOs;
			//$scope.getInternalDetailsById();
			for(var i=0;i<$scope.contractlist.length;i++){
			var budget = 0;
			
			for(var j=0;j<$scope.contractlist[i].preContractEmpDtlTOs.length;j++){
			budget+=(($scope.contractlist[i].preContractEmpDtlTOs[j].estimateCost)*($scope.contractlist[i].preContractEmpDtlTOs[j].quantity));
			}
			
			for(var j=0;j<$scope.contractlist[i].preContractMaterialDtlTOs.length;j++){
			budget+=(($scope.contractlist[i].preContractMaterialDtlTOs[j].estimateCost)*($scope.contractlist[i].preContractMaterialDtlTOs[j].quantity));
			}
			
			for(var j=0;j<$scope.contractlist[i].preContractPlantDtlTOs.length;j++){
			budget+=(($scope.contractlist[i].preContractPlantDtlTOs[j].estimateCost)*($scope.contractlist[i].preContractPlantDtlTOs[j].quantity));
			}
			
			for(var j=0;j<$scope.contractlist[i].preContractServiceDtlTOs.length;j++){
			budget+=(($scope.contractlist[i].preContractServiceDtlTOs[j].estimateCost)*($scope.contractlist[i].preContractServiceDtlTOs[j].quantity));
			}
			
			for(var j=0;j<$scope.contractlist[i].precontractSowDtlTOs.length;j++){
			budget+=(($scope.contractlist[i].precontractSowDtlTOs[j].estimateCost)*($scope.contractlist[i].precontractSowDtlTOs[j].quantity));
			
			
			}
			$scope.contractlist[i].budgets=budget;
		
			}
			console.log($scope.contractlist);
			
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
		$scope.getInternalDetailsById = function () {
			
			var onLoadReq = {
				"projId": 2114,
				"contractId": 3250,
				"status": 1
			};
			PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
				console.log(data);
				
			});
			
		};
		
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
				/*{ name: 'eps', displayName: "EPS Name", headerTooltip: "EPS Id",
				cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].code}}</div>' },
				{ name: 'project', displayName: "Project Name", headerTooltip: "Project Name",
				cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].name}}</div>' },*/
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Id" },
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name" },
				{ field: 'code', displayName: "Pre-Contract Id", headerTooltip: "Pre-ContractId" },
				{ field: 'preContractType', displayName: "Contract type", headerTooltip: "ContractType" },
				{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description" },
				{ field: 'contractStageStatus', displayName: "Pre-Contract Stage", headerTooltip: "Pre-Contract Stage" },
				{ field: 'workFlowStatusTO.desc', displayName: "Post-Contract Status", headerTooltip: "Post-Contract Status" },
				{ field: 'budgets', displayName: "Budget Estimate", headerTooltip: "Budget Estimate" },
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
				}
			});
	
	$scope.resetEstimateBudgetData = function () {
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
			$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy"); 
			$scope.subReport = "None";
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
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
		};
		
		
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
