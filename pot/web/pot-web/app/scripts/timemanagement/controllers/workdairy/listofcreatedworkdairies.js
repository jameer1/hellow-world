'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("listofcreatedworkdairy", {
        url: '/listofcreatedworkdairy',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/timemanagement/workdairy/createworkdairy/listofcreatedworkdairies.html',
                controller: 'ListOfCreatedWorkDairyController'
            }
        }
    })

}]).controller('ListOfCreatedWorkDairyController', ["$scope", "uiGridGroupingConstants", "uiGridConstants", "$state", "ngDialog", "blockUI", "$rootScope", "$filter", "GenericAlertService", "EpsProjectMultiSelectFactory", "WorkDiaryService", "WorkDairyMoreDetailFactory", "stylesService", "ngGridService",
        function ($scope, uiGridGroupingConstants, uiGridConstants, $state, ngDialog, blockUI, $rootScope, $filter, GenericAlertService, EpsProjectMultiSelectFactory, WorkDiaryService, WorkDairyMoreDetailFactory, stylesService, ngGridService) {
    	
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
        	if ($scope.getNumberOfDays() > 31) {
    			GenericAlertService.alertMessage("Please select only 31 days", 'Warning');
    			return;
    		}
        	if ($scope.searchCriteria.searchProject != null && $scope.searchCriteria.searchProject.projIds.length > 0)
        		$scope.searchCriteria.projIds = $scope.searchCriteria.searchProject.projIds;
        	WorkDiaryService.getCreatedWorkDiaries($scope.searchCriteria).then(function (data) {
    			$scope.workDairyTOs = data.workDairyTOs;
    			var dummyCreate = angular.copy($scope.workDairyTOs).map((item) => {
			    if(item.apprStatus == null){
			    item.apprStatus = 'Under Preparation'}
			    else{item.apprStatus = item.apprStatus;}
			    return item;
			    });
			$scope.gridOptions.data = dummyCreate;
    			
				/*$scope.gridOptions.data = angular.copy($scope.workDairyTOs);*/
    			console.log($scope.workDairyTOs);
    			/*if ($scope.workDairyTOs.length <= 0)
    				//GenericAlertService.alertMessage("Work Diaries are not available for the selected criteria", 'Warning');*/
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
        	WorkDairyMoreDetailFactory.openPopup(workDairyTO).then(function(data) {
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
					{ name: 'workDairyDate', cellFilter: 'date', displayName:'Month', headerTooltip: "Month", groupingShowAggregationMenu: false },
					{ field: 'workDiaryCode', displayName: "Work Dairy ID", headerTooltip: "Work Dairy ID", groupingShowAggregationMenu: false },
					{ field: 'parentName', displayName: "EPS", headerTooltip: "EPS", groupingShowAggregationMenu: false },
					{ field: 'name', displayName: "Project", headerTooltip: "Project", groupingShowAggregationMenu: false },
					{ field: 'crewCode', displayName: "Crew", headerTooltip: "Crew", groupingShowAggregationMenu: false },
					{ field: 'shiftCode', displayName: "Shift", headerTooltip: "Shift", groupingShowAggregationMenu: false },
					{ field: 'createdBy', displayName: "Supervisor/Originator", headerTooltip: "Supervisor/Originator", groupingShowAggregationMenu: false },
					{ name: 'internalApprBy',displayName:'Internal Approver',  headerTooltip: "Internal Approver", groupingShowAggregationMenu: false },
					{ name: 'clientApprBy',displayName:'External Approver',  headerTooltip: "External Approver", groupingShowAggregationMenu: false },
					{ field: 'apprStatus',displayName:'Status', headerTooltip: "Status"},
					{ name: 'More Details', cellClass: 'justify-center', headerCellClass:"justify-center", cellTemplate: '<button ng-disabled="row.entity.timeFlag" ng-click="grid.appScope.showMore(row.entity, workDairyTO)" class="btn btn-primary btn-sm" class="btn bottomaligned btn-brown btn-sm">More Details</botton>', 
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