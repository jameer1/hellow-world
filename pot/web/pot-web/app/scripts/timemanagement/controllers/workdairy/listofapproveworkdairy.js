'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("listofapproveworkdiary", {
        url: '/listofapproveworkdiary',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/timemanagement/workdairy/approveworkdairy/listofworkdairyapproval.html',
                controller: 'ListOfApproveWorkDairyController'
            }
        }
    })
}]).controller(
    'ListOfApproveWorkDairyController',
    ["$scope", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$state", "$filter", "ngDialog", "blockUI", "GenericAlertService", "EpsProjectMultiSelectFactory", "WorkDiaryService", "InternalApprovalFactory", "ClientApprovalFactory", function ($scope, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $state, $filter, ngDialog, blockUI, GenericAlertService, EpsProjectMultiSelectFactory, WorkDiaryService, InternalApprovalFactory, ClientApprovalFactory) {
        $scope.stylesSvc = stylesService;
    	$scope.searchCriteria = {};
    	$scope.getUserProjects = function () {
            $scope.empAttendenceDetails = [];
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
        	if ($scope.getNumberOfDays() > 31) {
    			GenericAlertService.alertMessage("Please select only 31 days", 'Warning');
    			return;
    		}
        	if ($scope.searchCriteria.searchProject != null && $scope.searchCriteria.searchProject.projIds.length > 0)
        		$scope.searchCriteria.projIds = $scope.searchCriteria.searchProject.projIds;
        	WorkDiaryService.getSubmittedWorkDiaries($scope.searchCriteria).then(function (data) {
    			$scope.workDairyTOs = data.workDairyTOs;
				$scope.gridOptions.data = angular.copy($scope.workDairyTOs);
    			if ($scope.workDairyTOs.length <= 0)
    				GenericAlertService.alertMessage("Work Diaries are not available for the selected criteria", 'Warning');
    		}, function (error) {
    			GenericAlertService.alertMessage("Error occured while gettting Work Diaries details", 'Error');
    		});
        },
        $scope.resetSearch = function() {
        	$scope.searchCriteria = {
            	userType: '1',
            	fromDate: $filter('date')(new Date(Date.now() - 12096e5), "dd-MMM-yyyy"),
            	toDate: $filter('date')(new Date(), "dd-MMM-yyyy"),
            };
        },
        $scope.showMore = function(workDairyTO) {
        	if (workDairyTO.apprStatus=='Submitted For Approval' || workDairyTO.apprStatus=='Approved'){
        		InternalApprovalFactory.openPopup(workDairyTO).then(function(data) {
        			var index = $scope.workDairyTOs.findIndex(workDairyTO => workDairyTO.id == data.workDairyTO.id);
        			$scope.workDairyTOs.splice(index, 0, data.workDairyTO);
        		}, function(error) {
        			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
        		})
        	}
        	if (workDairyTO.apprStatus=='Submitted For Client Approval' || workDairyTO.apprStatus=='Client Approved'){
        		ClientApprovalFactory.openPopup(workDairyTO).then(function(data) {
        			$scope.search();
        		}, function(error) {
        			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
        		})
        	}
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
					{ name: 'workDairyDate', cellFilter: 'date', displayName:'Month', headerTooltip: "Month", groupingShowAggregationMenu: false },
					{ field: 'workDiaryCode', displayName: "Attendance Record Sheet ID", headerTooltip: "Attendance Record Sheet ID", groupingShowAggregationMenu: false },
					{ field: 'parentName', displayName: "EPS", headerTooltip: "EPS", groupingShowAggregationMenu: false },
					{ field: 'name', displayName: "Project", headerTooltip: "Project", groupingShowAggregationMenu: false },
					{ field: 'crewCode', displayName: "Crew", headerTooltip: "Crew", groupingShowAggregationMenu: false },
					{ field: 'shiftCode', displayName: "Shift", headerTooltip: "Crew", groupingShowAggregationMenu: false },
					{ field: 'createdBy', displayName: "Supervisor/Originator", headerTooltip: "Supervisor/Originator", cellClass:'black', groupingShowAggregationMenu: false },
					{ name: 'internalApprBy',displayName:'Internal Approver',  headerTooltip: "Internal Approver", cellClass: 'color', groupingShowAggregationMenu: false },
					{ name: 'clientApprBy',displayName:'External Approver',  headerTooltip: "External Approver",cellClass:'green', groupingShowAggregationMenu: false },
					{ name: 'apprStatus',displayName:'Status', headerTooltip: "Status"},
					{ name: 'More Details', cellClass: 'justify-center', headerCellClass:"justify-center", cellTemplate: '<button ng-disabled="row.entity.timeFlag" ng-click="grid.appScope.showMore(row.entity, workDairyTO)" class="btn btn-primary btn-sm" class="btn bottomaligned btn-brown btn-sm" ng-disabled= timeFlag >More Details</button>', 
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
