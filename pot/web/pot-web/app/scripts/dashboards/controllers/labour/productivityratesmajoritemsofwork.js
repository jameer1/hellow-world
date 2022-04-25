'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("productivityratesmajoritemsofwork", {
		url: '/productivityratesmajoritemsofwork',
		data: {
			dashboards: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/dashboards/labour/productivityratesmajoritemsofwork.html',
				controller: 'ProductivityRatesMajorItemsOfWork'
			}
		}
	})
}]).controller("ProductivityRatesMajorItemsOfWork", ["$scope","$q", "ngDialog", "GenericAlertService", "EpsProjectSelectFactory", "PickTangibleFactory", "progressReportService", "ProjectSettingsService", "chartService",
	function ($scope,$q, ngDialog, GenericAlertService, EpsProjectSelectFactory, PickTangibleFactory, progressReportService, ProjectSettingsService,chartService) {
	let selectedTangibleItems = [];
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'Units Per Hour';
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
				$scope.resetChartDetails();
			    prepareChartData1(data.projectTangibleTOs);
				$scope.chartData=data.projectTangibleTOs;
				if (data.projectTangibleTOs.length == 0) GenericAlertService.alertMessage("Data not available for the selected criteria", 'Info');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting report data", 'Error');
		});
	};
     $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
     $scope.estimatedProductivity11 = [];
     $scope.actualProductivity22 = [];
	 $scope.labels1 = [];
	 $scope.data=[];
 function setGraphData(chartReportMap) {
	console.log(chartReportMap);
	 for (const index in chartReportMap) {
	 $scope.estimatedProductivity1.push(chartReportMap[index].estimatedProductivity1);
     $scope.actualProductivity1.push(chartReportMap[index].actualProductivity1);
	 $scope.labels1.push(chartReportMap[index].mapKey);
 }
for (var i=0;i<$scope.estimatedProductivity1.length;i++)
   {
	  var data = [];
for (var j=0;j<$scope.estimatedProductivity1.length;j++)
   {
	if(i==0)
	{
	data.push($scope.actualProductivity1[j]);
	}
		if(i==1)
	{
		  data.push($scope.estimatedProductivity1[j]);				
	}      
}
		$scope.data.push(data);
}
	 initGraph();
 };


 $scope.data= [];
 $scope.labels1= [];
 function initGraph() {	
		    $scope.series1 = ['Actual Productivity','Estimated Productivity'];
	        $scope.labels = $scope.labels1;				
			$scope.datasetOverride = new Array();		
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
			$scope.options.scales.xAxes[0].stacked = false;
	 		$scope.options.scales.yAxes[0].stacked = false;
 };
$scope.resetChartDetails = function () {
	 $scope.estimatedProductivity1 = [];
     $scope.actualProductivity1 = [];
	 $scope.labels1 = [];
	 $scope.data=[];
     $scope.Labels = [];
	 $scope.series = [];			
	 $scope.datasetOverride = [];
	 $scope.chart_type = [];
	}
	let prepareChartData1 = function (periodicalData) {
		console.log(periodicalData,"SKSKSKS");
		let chartData = [];
		let chartReportMap = [];
		let actual=[];
		for (let i=0; i<periodicalData.length; i++) {
			let index = chartData.findIndex(e => e.tangibleItemId == periodicalData[i].tangibleItemId);
			if (index == -1) {				
				chartData.push({tangibleItemId: periodicalData[i].tangibleItemId, tangibleName: periodicalData[i].tangibleName,
					estimatedProductivity: periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours,
					actualProductivity: periodicalData[i].actualQuantity / periodicalData[i].actualHours});
			} else {
				let actual = periodicalData[i].actualQuantity / periodicalData[i].actualHours;
				if(periodicalData[i].actualQuantity / periodicalData[i].actualHours == Infinity || (periodicalData[i].actualQuantity==0 && periodicalData[i].actualHours==0)){
					actual = 0;	
					}
				chartData[index].estimatedProductivity += periodicalData[i].estimatedQuantity / periodicalData[i].estimatedHours;				
				chartData[index].actualProductivity += actual;			
			}
		}		
		$scope.chartData1=chartData;
		 for (const catDtl of $scope.chartData1) {		
     let mapKey;
	 let mapValue;
	 let estimatedProductivity1;
	 let actualProductivity1;
	 let parentName;
		 mapKey = catDtl.tangibleName;
		 mapValue = catDtl.tangibleName;
     	 estimatedProductivity1 =parseFloat(catDtl.estimatedProductivity).toFixed(2);
		 actualProductivity1 =parseFloat(catDtl.actualProductivity).toFixed(2);
	
	   if(actualProductivity1 == Infinity){
			actualProductivity1 = 0;
		 }
		if(estimatedProductivity1 == Infinity){
			estimatedProductivity1 = 0;
		}


       if (!chartReportMap[mapKey]) {		
		 chartReportMap[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "estimatedProductivity1": estimatedProductivity1,
			 "actualProductivity1":actualProductivity1
	
		 };
	   }

            }
		setGraphData(chartReportMap);	
		
		/*$scope.chartLabels = [];
		$scope.chartData = [[],[]];
		for (let i=0; i<chartData.length; i++) {
			$scope.chartLabels.push(chartData[i].tangibleName);
			$scope.chartData[0].push(parseFloat(chartData[i].estimatedProductivity).toFixed(2));
			$scope.chartData[1].push(parseFloat(chartData[i].actualProductivity).toFixed(2));
		}*/
	};
}])
