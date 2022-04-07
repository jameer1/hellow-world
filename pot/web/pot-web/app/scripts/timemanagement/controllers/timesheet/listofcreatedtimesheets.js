'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state('listofcreatetimesheet', {
        url: '/listofcreatetimesheet',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/timemanagement/timesheet/createtimesheet/listofcreatedtimesheets.html',
                controller: 'ListOfCreateTimeSheetController'
            }
        }
    })
}]).controller(
    "ListOfCreateTimeSheetController",
    ["$q", "$rootScope", "$scope", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$state", "$filter", "ngDialog", "blockUI", "GenericAlertService", "EpsProjectMultiSelectFactory", "TimeSheetService", "CreateTimeSheetMoreDetailFactory", function ($q, $rootScope, $scope, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $state, $filter, ngDialog, blockUI, GenericAlertService, EpsProjectMultiSelectFactory, TimeSheetService, CreateTimeSheetMoreDetailFactory) {
    	$scope.stylesSvc = stylesService;
        $scope.searchCriteria = {};
    	$scope.getUserProjects = function () {
            var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
            	$scope.searchCriteria.searchProject = data.searchProject;
            },
            function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        },
        $scope.search = function() {
        	if (new Date($scope.searchCriteria.fromDate) > new Date($scope.searchCriteria.toDate)) {
    			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
    			return;
    		}
        	if ($scope.getNumberOfDays() > 90) {
    			GenericAlertService.alertMessage("Please select only 90 days", 'Warning');
    			return;
    		}
        	if ($scope.searchCriteria.searchProject != null && $scope.searchCriteria.searchProject.projIds.length > 0)
        		$scope.searchCriteria.projIds = $scope.searchCriteria.searchProject.projIds;
        	TimeSheetService.getCreatedTimeSheets($scope.searchCriteria).then(function (data) {
    			$scope.timeSheetTOs = data.timeSheetTOs;
                $scope.gridOptions.data = angular.copy($scope.timeSheetTOs);
    			if ($scope.timeSheetEmpDtlTOs.length <= 0)
    				GenericAlertService.alertMessage("Time Sheets are not available for the selected criteria", 'Warning');
    		}, function (error) {
    			GenericAlertService.alertMessage("Error occured while gettting Time Sheet details", 'Error');
    		});
        },
        $scope.resetSearch = function() {
        	$scope.searchCriteria = {
            	userType: '1',
            	fromDate: $filter('date')(new Date(new Date().setDate(new Date().getDate() - 4 * 7)), "dd-MMM-yyyy"),
            	toDate: $filter('date')(new Date(), "dd-MMM-yyyy"),
            };
        },
        $scope.showMore = function(timeSheetTO) {
        	CreateTimeSheetMoreDetailFactory.openPopup(timeSheetTO).then(function(data) {
        		$scope.search();
    		}, function(error) {
    			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
    		});
        },
        $scope.getNumberOfDays = function() {
        	var dt1 = new Date($scope.searchCriteria.fromDate);
        	var dt2 = new Date($scope.searchCriteria.toDate);
        	var Difference_In_Time = dt2.getTime() - dt1.getTime();
        	return Difference_In_Time / (1000 * 3600 * 24);
        }
        $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ name: 'weekStartDate', cellFilter: 'date', displayName:'Week Commencing Date', headerTooltip: "Week Commencing Date", groupingShowAggregationMenu: false },
					{ field: 'timeSheetCode', displayName: "Time Sheet ID", headerTooltip: "Time Sheet ID", groupingShowAggregationMenu: false },
					{ field: 'parentName', displayName: "EPS", headerTooltip: "EPS", groupingShowAggregationMenu: false },
					{ field: 'name', displayName: "Project", headerTooltip: "Project", groupingShowAggregationMenu: false },
					{ field: 'crewName', displayName: "Crew / Individual", headerTooltip: "Crew / Individual", cellTemplate:"<div>{{row.entity.crewId == null ? 'Individual' : row.entity.crewName}}</div>", groupingShowAggregationMenu: false },
					{ field: 'reqUserDisplayName', displayName: "Supervisor/Originator", headerTooltip: "Supervisor/Originator", groupingShowAggregationMenu: false },
					{ field: 'apprUserDisplayName', displayName: "Approver", headerTooltip: "Approver", groupingShowAggregationMenu: false },
					{ name: 'apprStatus', cellClass: 'justify-center', headerCellClass:"justify-center", displayName:'Status', headerTooltip: "Status"},
					{ name: 'button', cellClass: 'justify-center', headerCellClass:"justify-center", cellTemplate: '<button ng-disabled="row.entity.disableFlag"ng-click="grid.appScope.showMore(row.entity, workDairyTO)" class="btn btn-primary btn-sm" class="btn bottomaligned btn-brown btn-sm">More Details</botton>', 
				displayName: "More Details", headerTooltip : "Resource Curve", enableFiltering: false,groupingShowAggregationMenu: false }
					]
					console.log(columnDefs,'newValue');
                    $scope.search();
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Asbuilt_Records_ListofCreatedWorkDiaries");
			}
		});
        $scope.resetSearch();
        $scope.search();
        
    }]);
