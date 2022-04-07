'use strict';

app.factory('DashboardsSelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
	var service = {};
	var dashboardsList;	
	service.openPopup = function(categoryDetails) {
		var deferred = $q.defer();
		var dashboardsList = ngDialog.open({
			template : 'views/dashboards/masterdashboard/dashboardspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.selectedDashboardDetails = [];
				$scope.selDashboards = [];
				var companyName = '';
				if (categoryDetails.catgName == "Performance") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						/*"code" : "Progress,Variance & Performance Indices"*/
						"code" : "Progress_Variance_Performance_Indices",
						"url" : "views/dashboards/performance/progressvarianceperformance.html",
						"title" : "Progress, Variance & Performance Indices"
					}, {
						"select" : false,
						"code" : "Cost_Schedule_Performance",
						"url" : "views/dashboards/performance/costscheduleandperformance.html",
						"title" : "Cost Schedule & Performance"
					}, {
						"select" : false,
						"code" : "Cost_Schedule_Variance_Units",
						"url" : "views/dashboards/performance/costschedulevarianceunits.html",
						"title" : "Cost Schedule Variance - Units"
					}, {
						"select" : false,
						"code" : "Cost_Schedule_Variance_Percentage",
						"url" : "views/dashboards/performance/costschedulevariancepercentage.html",
						"title" : "Cost Schedule Variance - %"
					}, {
						"select" : false,
						"code" : "Cost_Schedule_Performance_Indices",
						"url" : "views/dashboards/performance/costscheduleperformanceindices.html",
						"title" : "Cost Schedule Performance Indices"
					}, {
						"select" : false,
						"code" : "To_Complete_Performance_Index",
						"url" : "views/dashboards/performance/tocompleteperformanceindex.html",
						"title" : "To Complete Performance Index"
					} ]

				}
				if (categoryDetails.catgName == "Progress") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Plan_Vs_Actual_Progress",
						"url" : "views/dashboards/progress/planvsactualprogress.html",
						"title" : "Plan Vs Actual Progress"
					}, {
						"select" : false,
						"code" : "S Curve - Cost",
						"url" : "views/dashboards/progress/progress.scurvecost.html",
						"title" : "S Curve - Cost"
					},
					{
						"select" : false,
						"code" : "S Curve - Labour",
						"url" : "views/dashboards/progress/progress.scurvelabour.html",
						"title" : "S Curve - Labour"
					}
					]

				}
				if (categoryDetails.catgName == "Time") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Cost_And_Schedule_Variance_Bubble_Chart",
						"url" : "views/dashboards/time/costschedulevariancebubblechart.html",
						"title" : "CV and SV for Man Hours (Bubble Chart)"
					},{
						"select" : false,
						"code" : "Current_Dates_Progress_Percentage",
						"url" : "views/dashboards/time/currentdatesandprogresspercentage.html",
						"title" : "Current Dates & Progress %"
					},{
						"select" : false,
						"code" : "Cost_Schedule_Variance_Units",
						"url" : "views/dashboards/time/costandschedulevarianceunits.html",
						"title" : "Cost Schedule Variance - Units"
					},{
						"select" : false,
						"code" : "Projects_Gantt_Chart",
						"url" : "views/dashboards/time/projganttchart.html",
						"title" : "Projects Gantt Chart"
					}]

				}
				if (categoryDetails.catgName == "Budget") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Budget_By_Country",
						"url" : "views/dashboards/budget/budgetbycountry.html",
						"title" : "Budget by Country"
					},{
						"select" : false,
						"code" : "Budget_By_Province",
						"url" : "views/dashboards/budget/budgetbyprovince.html",
						"title" : "Budget by Province"
							
					},{
						"select" : false,
						"code" : "Budget_By_Project",
						"url" : "views/dashboards/budget/budgetbyproject.html",
						"title" : "Budget by Project"
					},{
						"select" : false,
						"code" : "Budget_By_Project_Manager",
						"url" : "views/dashboards/budget/budgetbyprojectmanager.html",
						"title" : "Budget by Project Manager"
					}]

				}
				if (categoryDetails.catgName == "Cost") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Cost_Health_Check",
						"url" : "views/dashboards/cost/costhealthcheck.html",
						"title" : "Cost Health Check"
					},{
						"select" : false,
						"code" : "CV_And_SV_For_Cost_Bubble_Chart",
						"url" : "views/dashboards/cost/cvandsvforcostbubblechart.html",
						"title" : "CV And SV For Cost (Bubble Chart)"
					},{
						"select" : false,
						"code" : "Original_Vs_Estimate_At_Completion_Cost",
						"url" : "views/dashboards/cost/originalvsestimatecompletioncost.html",
						"title" : "Original Vs Estimate At Completion Cost"
					},{
						"select" : false,
						"code" : "Plan_Vs_Actual_Vs_EarnedValue_Cost",
						"url" : "views/dashboards/cost/planvsactualearnedvaluecost.html",
						"title" : "Plan Vs Actual Vs Earned Value Cost"
					},{
						"select" : false,
						"code" : "Cost_Work_Sheet",
						"url" : "views/dashboards/cost/costworksheet.html",
						"title" : "Cost Work Sheet"
					}]

				}
				if (categoryDetails.catgName == "Labour") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Labour_Hours_Health_Check",
						"url" : "views/dashboards/labour/labourhourshealthcheck.html",
						"title" : "Labour Hours Health Check"
							
					},{
						"select" : false,
						"code" : "CV_And_SV_For_Labour_Bubble_Chart",
						"url" : "views/dashboards/labour/cvandsvforlabour.html",
						"title" : "CV And SV For Labour (Bubble Chart)"
					},{
						"select" : false,
						"code" : "Original_Vs_Estimate_At_Completion_Man_Hours",
						"url" : "views/dashboards/labour/originalvsestimatecompletionmanhours.html",
						"title" : "Original Vs Estimate At Completion Man Hours"
					},{
						"select" : false,
						"code" : "Plan_Vs_Actual_Vs_Earned_Direct_Man_Hours",
						"url" : "views/dashboards/labour/planactualearneddirectmanhrs.html",
						"title" : "Plan Vs Actual Vs Earned - Direct Man Hours"
					},{
						"select" : false,
						"code" : "Productivity Rates - Major Items of Work",
						"url" : "views/dashboards/labour/productivityratesmajoritemsofwork.html",
						"title" : "Productivity Rates - Major Items of Work"
					},{
						"select" : false,
						"code" : "Periodical Productivity Rates - Major Items of Work",
						"url" : "views/dashboards/labour/periodicalproductivityratesmajoritemsofwork.html",
						"title" : "Periodical Productivity Rates - Major Items of Work"
					}]

				}
				
				if (categoryDetails.catgName == "Actual Cost") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Actual_Cost_By_Country",
						"url" : "views/dashboards/actualcost/actualcostbycountry.html",
						"title" : "Actual Cost by Country"
					},{
						"select" : false,
						"code" : "Actual_Cost_By_Province",
						"url" : "views/dashboards/actualcost/actualcostbyprovince.html",
						"title" : "Actual Cost by Province"
					},{
						"select" : false,
						"code" : "Actual_Cost_By_Project",
						"url" : "views/dashboards/actualcost/actualcostbyproject.html",
						"title" : "Actual Cost by Project"
					},{
						"select" : false,
						"code" : "Actual_Cost_By_Project_Manager",
						"url" : "views/dashboards/actualcost/actualcostbyprojmanager.html",
						"title" : "Actual Cost by Project Manager"
					}]

				}
				if (categoryDetails.catgName == "Earned Value") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Earned_Value_By_Country",
						"url" : "views/dashboards/earnedvalue/earnedvaluebycountry.html",
						"title" : "Earned Value by Country"
					},{
						"select" : false,
						"code" : "Earned_Value_By_Province",
						"url" : "views/dashboards/earnedvalue/earnedvaluebyprovince.html",
						"title" : "Earned Value by Province"
					},{
						"select" : false,
						"code" : "Earned_Value_By_Project",
						"url" : "views/dashboards/earnedvalue/earnedvaluebyproject.html",
						"title" : "Earned Value by Project"
					},{
						"select" : false,
						"code" : "Earned_Value_By_Project_Manager",
						"url" : "views/dashboards/earnedvalue/earnedvaluebyprojmanager.html",
						"title" : "Earned Value by Project Manager" 
					}]
					
				}
				if (categoryDetails.catgName == "Remaining Budget") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Remaining_Budget_By_Country",
						"url" : "views/dashboards/remainingbudget/remainingbudgetbycountry.html",
						"title" : "Remaining Budget by Country"
					},{
						"select" : false,
						"code" : "Remaining_Budget_By_Province",
						"url" : "views/dashboards/remainingbudget/remainingbudgetbyprovince.html",
						"title" : "Remaining Budget by Province"
					},{
						"select" : false,
						"code" : "Remaining_Budget_By_Project",
						"url" : "views/dashboards/remainingbudget/remainingbudgetbyproject.html",
						"title" : "Remaining Budget by Project"
					},{
						"select" : false,
						"code" : "Remaining_Budget_By_Project_Manager",
						"url" : "views/dashboards/remainingbudget/remainingbudgetbyprojmanager.html",
						"title" : "Remaining Budget by Project Manager"
					}]

				}
				if (categoryDetails.catgName == "Estimate to Complete") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Estimate_To_Complete_By_Country",
						"url" : "views/dashboards/estimatetocomplete/estimatetocompletebycountry.html",
						"title" : "Estimate to Complete by Country"
					},{
						"select" : false,
						"code" : "Estimate_To_Complete_By_Province",
						"url" : "views/dashboards/estimatetocomplete/estimatetocompletebyprovince.html",
						"title" : "Estimate to Complete by Province"
					},{
						"select" : false,
						"code" : "Estimate_To_Complete_By_Project",
						"url" : "views/dashboards/estimatetocomplete/estimatetocompletebyproject.html",
						"title" : "Estimate to Complete by Project"
					},{
						"select" : false,
						"code" : "Estimate_To_Complete_By_Project_Manager",
						"url" : "views/dashboards/estimatetocomplete/estimatetocompletebyprojmanager.html",
						"title" : "Estimate to Complete by Project Manager"
					}]

				}
				if (categoryDetails.catgName == "Estimate At Completion") {
					$scope.selectedDashboardDetails = [ {
						"select" : false,
						"code" : "Estimate_At_Completion_By_Country",
						"url" : "views/dashboards/estimateatcompletion/estimateatcompletionbycountry.html",
						"title" : "Estimate At Completion by Country"
						
					},{
						"select" : false,
						"code" : "Estimate_At_Completion_By_Province",
						"url" : "views/dashboards/estimateatcompletion/estimateatcompletionbyprovision.html",
						"title" : "Estimate At Completion by Province"
					},{
						"select" : false,
						"code" : "Estimate_At_Completion_By_Project",
						"url" : "views/dashboards/estimateatcompletion/estimateatcompletionbyproject.html",
						"title" : "Estimate At Completion by Project"
					},{
						"select" : false,
						"code" : "Estimate_At_Completion_By_Project_Manager",
						"url" : "views/dashboards/estimateatcompletion/estimateatcompletionbyprojmanager.html",
						"title" : "Estimate At Completion by Project Manager"
					}]

				} var selectedData =[];
				$scope.selectedDashboards = function(selectedDashboard) {
					if (selectedDashboard.select) {
						selectedData.push(selectedDashboard);
					} else {
						selectedData.pop(selectedDashboard);
					}
				}
				
				var selectedTitles ='';
				$scope.addDashboards = function() {
					deferred.resolve(selectedData);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};
	return service;
}]);