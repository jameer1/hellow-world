'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("manpowerproductivityanalysis", {
		url: '/manpowerproductivityanalysis',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowerproductivityanalysis.html',
				controller: 'ManpowerProductivityAnalysisController'
			}
		}
	})
}]).controller("ManpowerProductivityAnalysisController", ["$scope", "$filter", "EpsProjectMultiSelectFactory", "ngGridService", "GenericAlertService", "PickTangibleFactory", "ProjectSettingsService","chartService",
	function ($scope, $filter, EpsProjectMultiSelectFactory, ngGridService, GenericAlertService, PickTangibleFactory, ProjectSettingsService,chartService) {
   chartService.getChartMenu($scope);
	let today = new Date();
	let aMonthBefore = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate() + 1);
	let selectedTangibleItems = [], projectTangibleTOs = [];
	let weekDays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let weekBeginsOn = 'Monday';
    let weekEndsOn = 'Sunday';
    var series1 = ['Estimated Productivity','Actual Productivity','Cum Actual Productivity'];
    var series = [];
    $scope.yAxislabels = 'Cum  per Hour';
	$scope.subReports = ['Tangible Item Wise - Productivity Rates', 'Item Wise - Periodical Production Rates'];
	$scope.timeScales = [
    	{name: 'Daily', code: "daily", filter: "dd-MMM-yyyy"}, 
    	{name: 'Weekly', code: "weekly", filter: "week"}, 
    	{name: 'Monthly', code: "monthly", filter: "MMM-yyyy"}, 
    	{name: 'Yearly', code: "yearly", filter: "yyyy"}
    ];
	$scope.selectedTimeScale = $scope.timeScales[0];
	$scope.searchFilter = {};
	$scope.searchFilter.today = $filter('date')(today, "dd-MMM-yyyy");;
	$scope.searchFilter.fromDate = $filter('date')(aMonthBefore, "dd-MMM-yyyy");;
	$scope.searchFilter.toDate = $filter('date')(today, "dd-MMM-yyyy");
	
	$scope.getUserProjects = function () {
		EpsProjectMultiSelectFactory.getEPSUserProjects().then(function (data) {
			$scope.searchFilter.searchProject = {};
			$scope.searchFilter.searchProject = data.searchProject;
			$scope.searchFilter.selectedProjIds = data.searchProject.projIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.getTangibleItems = function () {
		if ($scope.searchFilter.selectedProjIds == null || $scope.searchFilter.selectedProjIds == 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Info');
			return;
		}
		PickTangibleFactory.selectMultiple($scope.searchFilter.selectedProjIds, selectedTangibleItems, false).then(function (data) {
			selectedTangibleItems = data;
			$scope.searchFilter.searchProject.selectedTangibleItemNames = "";
			for (let i=0; i < data.length; i++)
				$scope.searchFilter.searchProject.selectedTangibleItemNames += data[i].tangibleCode + ", ";
			if ($scope.searchFilter.searchProject.selectedTangibleItemNames.length > 0)
				$scope.searchFilter.searchProject.selectedTangibleItemNames = $scope.searchFilter.searchProject.selectedTangibleItemNames.substr(0, $scope.searchFilter.searchProject.selectedTangibleItemNames.length-2);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.generateReport = function () {
		$scope.resetChartDetails();
		if ($scope.searchFilter.selectedProjIds == null || $scope.searchFilter.selectedProjIds == 0) {
			GenericAlertService.alertMessage("Please select Project", 'Info');
			return;
		}
		if (selectedTangibleItems.length == 0) {
			GenericAlertService.alertMessage("Please select Tangible Items", 'Info');
			return;
		}
		ProjectSettingsService.getManpowerProductivityAnalysisReportData({projectIds: $scope.searchFilter.selectedProjIds, 
			projectTangibleTOs: selectedTangibleItems, fromDate: new Date($scope.searchFilter.fromDate), toDate: new Date($scope.searchFilter.toDate)}).then(function (data) {
			projectTangibleTOs = data.projectTangibleTOs;
			$scope.subReportChanged();
			if (projectTangibleTOs.length == 0) GenericAlertService.alertMessage("Data not available for the selected criteria", 'Info');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		});
	},
	$scope.reset = function () {
		$scope.searchFilter = {};
		$scope.searchFilter.fromDate = $filter('date')(aMonthBefore, "dd-MMM-yyyy");
		$scope.searchFilter.toDate = $filter('date')(today, "dd-MMM-yyyy");
		$scope.selectedSubReport = null;
		selectedTangibleItems = [];
		projectTangibleTOs = [];
		$scope.subReportChanged();
		$scope.chartData = [];
	},
	$scope.subReportChanged = function () {
		switch ($scope.selectedSubReport) {
		case 'Tangible Item Wise - Productivity Rates':
			let tangibleItemWiseolumnDefs = [
				{ field: 'parentCode', displayName: "EPS", headerTooltip: "EPS"},
				{ field: 'parentName', displayName: "Project", headerTooltip: "Project"},
				{ field: 'tangibleCode', displayName: "Tangible Item ID", headerTooltip: "Tangible Item ID"},
				{ field: 'tangibleName', displayName: "Tangible Item Name", headerTooltip: "Tangible Item Name"},
				{ field: 'uom', displayName: "Unit of Measure", headerTooltip: "Unit of Measure"},
				{ field: 'estimatedQuantity', displayName: 'Estimated Quantity', headerTooltip: 'Estimated Quantity', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'estimatedHours', displayName: 'Estimated Hours', headerTooltip: 'Estimated Hours', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{
					name: 'estimatedProductivity',
					headerTooltip: 'Estimated Productivity',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ (row.entity.estimatedQuantity / row.entity.estimatedHours) | number:2 }}</div>'
				},
				{ field: 'actualQuantity', displayName: 'Actual Quantity', headerTooltip: 'Actual Quantity', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'actualHours', displayName: 'Actual Hours', headerTooltip: 'Actual Hours', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{
					name: 'actualProductivityRate',
					headerTooltip: 'Actual Productivity Rate ( Units / Hr)',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ row.entity.actualQuantity / row.entity.actualHours | number:2 }}</div>'
				},
				{
					name: 'Variance in Productivity Rate',
					headerTooltip: 'Variance in Productivity Rate ( Units / Hr)',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ (row.entity.actualQuantity / row.entity.actualHours) - (row.entity.estimatedQuantity / row.entity.estimatedHours)| number:2 }}</div>'
				}
			];
			$scope.gridOptions.gridMenuCustomItems = false;
			let data = [];
			for (let i=0; i<projectTangibleTOs.length; i++) {
				let index = data.findIndex(e => e.tangibleItemId == projectTangibleTOs[i].tangibleItemId);
				if (index == -1) {
					data.push({parentCode: projectTangibleTOs[i].parentCode,
						parentName: projectTangibleTOs[i].parentName,
						tangibleCode: projectTangibleTOs[i].tangibleCode,
						tangibleName: projectTangibleTOs[i].tangibleName,
						uom: projectTangibleTOs[i].uom,
						estimatedQuantity: projectTangibleTOs[i].estimatedQuantity,
						estimatedHours: projectTangibleTOs[i].estimatedHours,
						actualQuantity: projectTangibleTOs[i].actualQuantity,
						actualHours: projectTangibleTOs[i].actualHours});
				} else {
					data[index].estimatedQuantity += projectTangibleTOs[i].estimatedQuantity;
					data[index].estimatedHours += projectTangibleTOs[i].estimatedHours;
					data[index].actualQuantity += projectTangibleTOs[i].actualQuantity;
					data[index].actualHours += projectTangibleTOs[i].actualHours;
				}
			}
			$scope.gridOptions = ngGridService.initGrid($scope, tangibleItemWiseolumnDefs, data,"Reports_Manpower_Productivity_TangibleWise");
			break;
		case 'Item Wise - Periodical Production Rates':
			let itemWiseColumnDefs = [
				{ field: 'period', displayName: "Date", headerTooltip: "Date" },
				{ field: 'tangibleName', displayName: "Tangible Item Name", headerTooltip: "Tangible Item Name"},
				{ field: 'uom', displayName: "Unit of Measure", headerTooltip: "Unit of Measure"},
				{
					name: 'estimatedProductivity',
					headerTooltip: 'Estimated Productivity',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ (row.entity.estimatedQuantity / row.entity.estimatedHours) | number:2 }}</div>'
				},
				{
					name: 'actualProductivityRate',
					headerTooltip: 'Actual Productivity Rate ( Units / Hr)',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ row.entity.actualQuantity / row.entity.actualHours | number:2 }}</div>'
				}
			];
			$scope.gridOptions.gridMenuCustomItems = false;
			let periodicalData = [];
			for (let i=0; i<projectTangibleTOs.length; i++) {
				let period = '';
				if ($scope.selectedTimeScale.filter == "week")
					period = weekStartEndDateFormat(projectTangibleTOs[i].date);
				else
					period = $filter('date')(projectTangibleTOs[i].date, $scope.selectedTimeScale.filter);
				
				let index = periodicalData.findIndex(e => e.period == period && e.tangibleItemId == projectTangibleTOs[i].tangibleItemId);
				if (index == -1) {
					periodicalData.push({
						period: period,
						tangibleCode: projectTangibleTOs[i].tangibleCode,
						tangibleName: projectTangibleTOs[i].tangibleName,
						uom: projectTangibleTOs[i].uom,
						estimatedQuantity: projectTangibleTOs[i].estimatedQuantity,
						estimatedHours: projectTangibleTOs[i].estimatedHours,
						actualQuantity: projectTangibleTOs[i].actualQuantity,
						actualHours: projectTangibleTOs[i].actualHours});
				} else {
					periodicalData[index].estimatedQuantity += projectTangibleTOs[i].estimatedQuantity;
					periodicalData[index].estimatedHours += projectTangibleTOs[i].estimatedHours;
					periodicalData[index].actualQuantity += projectTangibleTOs[i].actualQuantity;
					periodicalData[index].actualHours += projectTangibleTOs[i].actualHours;
				}
			}
			$scope.gridOptions = ngGridService.initGrid($scope, itemWiseColumnDefs, periodicalData,"Reports_Manpower_Productivity_Analysis_ItemWise");
		    prepareChartData1(periodicalData);
			prepareChartData(periodicalData);
			break;
		default:
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", cellFilter: 'date:\'dd-MMM-yyyy\'' },
				{ field: 'parentCode', displayName: "EPS", headerTooltip: "EPS"},
				{ field: 'parentName', displayName: "Project", headerTooltip: "Project"},
				{ field: 'code', displayName: "Cost Code", headerTooltip: "Cost Code"},
				{ field: 'name', displayName: "Cost Code Description", headerTooltip: "Cost Code Description"},
				{ field: 'tangibleCode', displayName: "Tangible Item ID", headerTooltip: "Tangible Item ID"},
				{ field: 'tangibleName', displayName: "Tangible Item Name", headerTooltip: "Tangible Item Name"},
				{ field: 'sowCode', displayName: "SOW Item ID", headerTooltip: "SOW Item ID"},
				{ field: 'sowName', displayName: "SOW Item Description", headerTooltip: "SOW Item Description"},
				{ field: 'uom', displayName: "Unit of Measure", headerTooltip: "Unit of Measure"},
				{ field: 'estimatedQuantity', displayName: 'Estimated Quantity', headerTooltip: 'Estimated Quantity', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'estimatedHours', displayName: 'Estimated Hours', headerTooltip: 'Estimated Hours', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{
					name: 'estimatedProductivity',
					headerTooltip: 'Estimated Productivity',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ (row.entity.estimatedQuantity / row.entity.estimatedHours) | number:2 }}</div>'
				},
				{ field: 'actualQuantity', displayName: 'Actual Quantity', headerTooltip: 'Actual Quantity', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{ field: 'actualHours', displayName: 'Actual Hours', headerTooltip: 'Actual Hours', cellFilter: 'number: 2', cellClass: "justify-right", headerCellClass: "justify-right" },
				{
					name: 'actualProductivityRate',
					headerTooltip: 'Actual Productivity Rate ( Units / Hr)',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ row.entity.actualQuantity / row.entity.actualHours | number:2 }}</div>'
				},
				{
					name: 'Variance in Productivity Rate',
					headerTooltip: 'Variance in Productivity Rate ( Units / Hr)',
					cellTemplate: '<div class="ui-grid-cell-contents" style="text-align:right;">{{ (row.entity.actualQuantity / row.entity.actualHours) - (row.entity.estimatedQuantity / row.entity.estimatedHours)| number:2 }}</div>'
				}
			];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, projectTangibleTOs,"Reports_Manpower_Productivity_Analysis");
			break;
		}
	};
	$scope.subReportChanged();
	
	let prepareChartData = function (periodicalData) {
		periodicalData.sort(function(a, b){
		    if (a.tangibleName < b.tangibleName) return -1 
		    if (a.tangibleName > b.tangibleName) return 1
		    if (a.period < b.period) return -1 
		    if (a.period > b.period) return 1
		});
		let tangibleId = 0;
		let labels = [];
		let data =[[],[]];
		let tangibleName = "";
		$scope.chartData = [];
		for (let i=0; i<periodicalData.length; i++) {
			if (tangibleId != 0 && tangibleId != periodicalData[i].tangibleItemId) {
				$scope.chartData.push({
					labels: labels,
					series : ['Estimated Productivity', 'Actual Productivity Rate'],
					data: data,
					options: {title: {display: true, text: 'Periodical Production Rates - ' + tangibleName }}
				});
				labels = [];
				data = [[],[]];
				tangibleName = "";
			}
			labels.push(periodicalData[i].period)
			data[0].push(parseFloat(periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours).toFixed(2));
			data[1].push(parseFloat(periodicalData[i].actualQuantity / periodicalData[i].actualHours).toFixed(2));
			tangibleName = periodicalData[i].tangibleName;
		}
		$scope.chartData.push({
			labels: labels,
			series : ['Estimated Productivity', 'Actual Productivity Rate'],
			data: data,
			options: {title: {display: true, text: 'PERIODICAL PRODUCTION RATES - ' + tangibleName },
				legend: {display: true, position: 'top'}}
		});
	};
		
	 $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
	 $scope.labels1 = [];
	 $scope.data=[];

 function prepareChartData1(periodicalData) {
	$scope.resetChartDetails();	
	$scope.chartData=periodicalData;
let chartData11 = [];
		let chartReportMap = [];
		let actual=[];
		for (let i=0; i<periodicalData.length; i++) {
			let index = chartData11.findIndex(e => e.period == periodicalData[i].period);
			if (index == -1) {
				
				chartData11.push({period: periodicalData[i].period,
					estimatedProductivity: periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours,
					actualProductivity: periodicalData[i].actualQuantity / periodicalData[i].actualHours});
			} else {		
				chartData11[index].estimatedProductivity += periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours;				
				chartData11[index].actualProductivity += periodicalData[i].actualQuantity / periodicalData[i].actualHours;			
			}
		}		
		$scope.chartData111=chartData11;	
 for (const catDtl of $scope.chartData111) {	
	$scope.series=['Actual Productivity','Estimated Productivity'];		
	 let mapKey;
	 let mapValue;
	 let estimatedProductivity;
	 let actualProductivity;
	 let parentName;
		 mapKey = catDtl.period;
		 mapValue = catDtl.period;
		 parentName=catDtl.parentName;
     	 estimatedProductivity=parseFloat(catDtl.estimatedProductivity).toFixed(2);
		 actualProductivity=parseFloat(catDtl.actualProductivity).toFixed(2);
		 if(actualProductivity == Infinity){
			actualProductivity = 0;
		}
	 if (!chartReportMap[mapKey]) {		
		 chartReportMap[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "parentName": parentName,
			 "estimatedProductivity": estimatedProductivity,
			 "actualProductivity": actualProductivity
		 };
	   }			
 }		
	 setGraphData(chartReportMap);			
 };
 function setGraphData(chartReportMap) {
        series=series1;	
		$scope.labels = new Array();
		$scope.data = new Array();		
		let cumulativeActualValue = 0;	
			for (const index in chartReportMap) {
			cumulativeActualValue += parseFloat(chartReportMap[index].actualProductivity);
			chartReportMap[index].cumulativeActual = cumulativeActualValue;
			}
		const valueArr = new Array();
		const plannedArr = new Array();
		const valueArrCum = new Array();
		
		$scope.subReportData = new Array();
		for (let plan of $scope.subReportData) {		
				plan.cumulativeActual = parseFloat(plan.cumulativeActual).toFixed(2);
			}
		$scope.subReportData = new Array();
		for (const index in chartReportMap) {	
			valueArr.push(chartReportMap[index].estimatedProductivity);		
			plannedArr.push(chartReportMap[index].actualProductivity);			
			valueArrCum.push(chartReportMap[index].cumulativeActual);
			$scope.labels.push(chartReportMap[index].mapKey);
		}
		$scope.data.push(valueArr);
		$scope.data.push(plannedArr);	
		$scope.data.push(valueArrCum);
		initGraph(series);
 }; 

 $scope.data= [];
 $scope.labels1= [];
 function initGraph(graphDataPropertiesArray) {	
		 $scope.menuOptions = [];
			$scope.chart_type = 'bar';
			$scope.options = {
				indexLabel: "low",
				legend: { display: true },
				scales: {
					xAxes: [{
						stacked: false,
					}],
					yAxes: [{
						stacked: false,
						scaleLabel: {
							display: true,
							labelString: 'Cum  per Hour',
							fontSize: "15"
						}
					}]
				}
			};
			
			$scope.datasetOverride = new Array();
			for (let i=0;i<graphDataPropertiesArray.length;i++) {
				if (graphDataPropertiesArray[i]=="Cum Actual Productivity") {
					$scope.datasetOverride.push({
						label: graphDataPropertiesArray[i],
						borderWidth: 3,
						type: 'line',
						fill: false,
					});
				}				
				 else {
					$scope.datasetOverride.push({
						label: graphDataPropertiesArray[i],
						borderWidth: 1,
						type: 'bar',
					});
				}
			}
 };
$scope.resetChartDetails = function () {
	 $scope.chartData=[];
	 $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
	 $scope.labels1 = [];
	 $scope.data=[];
     $scope.Labels = [];
	 $scope.series = [];			
	 $scope.datasetOverride = [];
	 $scope.chart_type = [];
	}
		
	let weekStartEndDateFormat = function (date) {
    	let startDate = new Date(angular.copy(date));
    	while ($filter('date')(startDate, "EEEE") != weekBeginsOn)
    		startDate.setDate(startDate.getDate() - 1);

    	let endDate = new Date(angular.copy(date));
    	while ($filter('date')(endDate, "EEEE") != weekEndsOn)
    		endDate.setDate(endDate.getDate() + 1);

    	return startDate.getDate() + "-" + endDate.getDate() + " " + $filter('date')(endDate, "MMM yyyy");
    }
}]);