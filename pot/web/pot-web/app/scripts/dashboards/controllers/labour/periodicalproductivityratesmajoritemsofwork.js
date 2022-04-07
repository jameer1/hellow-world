'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("periodicalproductivityratesmajoritemsofwork", {
		url: '/periodicalproductivityratesmajoritemsofwork',
		data: {
			dashboards: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/dashboards/labour/periodicalproductivityratesmajoritemsofwork.html',
				controller: 'PeriodicalProductivityRatesMajorItemsOfWork'
			}
		}
	})
}]).controller("PeriodicalProductivityRatesMajorItemsOfWork", ["$scope", "$q", "$filter", "ngDialog", "GenericAlertService", "EpsProjectSelectFactory", "progressReportService", "PickTangibleFactory", "ProjectSettingsService", "chartService",
	function ($scope, $q, $filter, ngDialog, GenericAlertService, EpsProjectSelectFactory, progressReportService, PickTangibleFactory, ProjectSettingsService, chartService) {
	chartService.getChartMenu($scope);
	let selectedTangibleItems = [], projectTangibleTOs = [];
	let weekDays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let weekBeginsOn = 'Monday';
    let weekEndsOn = 'Sunday';
    var series1 = ['Estimated Productivity','Actual Productivity','Cum Actual Productivity'];
    var series = [];
	$scope.timeScales = [
    	{name: 'Daily', code: "daily", filter: "dd-MMM-yyyy"}, 
    	{name: 'Weekly', code: "weekly", filter: "week"}, 
    	{name: 'Monthly', code: "monthly", filter: "MMM-yyyy"}, 
    	{name: 'Yearly', code: "yearly", filter: "yyyy"}
    ];
	$scope.selectedTimeScale = $scope.timeScales[0];
	$scope.searchFilter = {};
	
	$scope.getUserProjects = function () {
		EpsProjectSelectFactory.getEPSUserProjects().then(function (data) {
			$scope.searchFilter = {};
			$scope.searchFilter.searchProject = data.searchProject;
			progressReportService.getProjectsDatesForProgressSCurveReport({status: 1, projIds: [data.searchProject.projId]}).then(function (data) {
				for (let i=0; i < data.projStatusDatesTOs.length; i++) {
					if (data.projStatusDatesTOs[i].scheduleStartDate != null) {
						if ($scope.searchFilter.projectFromDate == null) {
							$scope.searchFilter.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						} else if ($scope.searchFilter.projectFromDate > new Date(data.projStatusDatesTOs[i].scheduleStartDate)) {
							$scope.searchFilter.projectFromDate = new Date(data.projStatusDatesTOs[i].scheduleStartDate);
						}
					}
					if (data.projStatusDatesTOs[i].scheduleFinishDate != null) {
						if ($scope.searchFilter.projectToDate == null) {
							$scope.searchFilter.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						} else if ($scope.searchFilter.projectToDate < new Date(data.projStatusDatesTOs[i].scheduleFinishDate)) {
							$scope.searchFilter.projectToDate = new Date(data.projStatusDatesTOs[i].scheduleFinishDate);
						}
					}
				}
			}, function(error){
				GenericAlertService.alertMessage("Error occured while selecting start finish dates", 'Error');
			});
			ProjectSettingsService.projReportsOnLoad({projId: data.searchProject.projId, status: 1}).then(function (data) {
				weekBeginsOn = data.projectReportsTOs[0].week;
				if (weekDays.indexOf(weekBeginsOn) == 0)
					weekEndsOn = weekDays[weekDays.length - 1];
				else
					weekEndsOn = weekDays[weekDays.indexOf(weekBeginsOn) - 1];
				prepareReport();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.getTangibleItems = function () {
		if ($scope.searchFilter.searchProject == null || $scope.searchFilter.searchProject.projId == 0) {
			GenericAlertService.alertMessage("Please select the Project", 'Info');
			return;
		}
		PickTangibleFactory.selectMultiple([$scope.searchFilter.searchProject.projId], selectedTangibleItems, true).then(function (data) {
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
		if ($scope.searchFilter.searchProject == null || $scope.searchFilter.searchProject.projId == 0) {
			GenericAlertService.alertMessage("Please select Project", 'Info');
			return;
		}
		if (selectedTangibleItems.length == 0) {
			GenericAlertService.alertMessage("Please select Tangible Items", 'Info');
			return;
		}
		ProjectSettingsService.getManpowerProductivityAnalysisReportData({projectIds: [$scope.searchFilter.searchProject.projId], 
			projectTangibleTOs: selectedTangibleItems, fromDate: $scope.searchFilter.projectFromDate, toDate: $scope.searchFilter.projectToDate}).then(function (data) {
				projectTangibleTOs = data.projectTangibleTOs;
				$scope.subReportChanged();
				if (data.projectTangibleTOs.length == 0) GenericAlertService.alertMessage("Data not available for the selected criteria", 'Info');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		});
	},
	$scope.subReportChanged = function () {
		$scope.resetChartDetails();
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
		prepareChartData(periodicalData);
	};
/*	let prepareChartData = function (periodicalData) {
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
	};*/
	
	 $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
	 $scope.labels1 = [];
	 $scope.data=[];

 function prepareChartData(periodicalData) {	
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
	/* for (const index in chartReportMap) {
	 $scope.estimatedProductivity1.push(chartReportMap[index].estimatedProductivity);
     $scope.actualProductivity1.push(chartReportMap[index].actualProductivity);
	 $scope.labels1.push(chartReportMap[index].mapKey);
 }
 for(let i=0;i<2;i++)
		 {
			if(i==0)
			{
			$scope.data.push($scope.actualProductivity1);
			}
			 if(i==1)
			{
				$scope.data.push($scope.estimatedProductivity1);			
			}
		 }
	 initGraph();*/
	 
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
		   /* $scope.series1=['Actual Productivity','Estimated Productivity']; 
	        $scope.labels = $scope.labels1;				
			$scope.datasetOverride = new Array();		
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
			$scope.options.scales.xAxes[0].stacked = false;
	 		$scope.options.scales.yAxes[0].stacked = false;*/
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
}])
