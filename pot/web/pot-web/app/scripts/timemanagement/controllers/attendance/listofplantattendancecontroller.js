'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("listofplantattendence", {
        url: '/listofplantempattendence',
        parent: 'site',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/timemanagement/attendance/listofplantattendancedetails.html',
                controller: 'ListOfPlantAttendenceController'
            }
        }
    })
}]).controller('ListOfPlantAttendenceController', ["$filter", "$scope", "uiGridGroupingConstants", "uiGridConstants", "$q", "$state", "blockUI", "ngDialog", "GenericAlertService", "PlantAttendanceService", "PlantAttendanceRecordSheetMoreDetail", "EpsProjectMultiSelectFactory", "stylesService", "ngGridService", function ($filter, $scope, uiGridGroupingConstants, uiGridConstants, $q, $state, blockUI, ngDialog, 
    GenericAlertService, PlantAttendanceService, PlantAttendanceRecordSheetMoreDetail, EpsProjectMultiSelectFactory, stylesService, ngGridService,) {
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
    	if ($scope.searchMonthDiff() > 12) {
			GenericAlertService.alertMessage("Please select only 12 months", 'Warning');
			return;
		}
    	if ($scope.searchCriteria.searchProject != null && $scope.searchCriteria.searchProject.projIds.length > 0) {
    		$scope.searchCriteria.projIds = $scope.searchCriteria.searchProject.projIds;
    	}
    	$scope.currMonthToDate = $filter('date')(new Date(), "MMM-yyyy");
    	if (($scope.searchCriteria.fromDate == $scope.searchCriteria.toDate) && ($scope.searchCriteria.toDate == $scope.currMonthToDate)) {
    		var fromDate = new Date();
        	fromDate.setMonth(fromDate.getMonth()-2);
        	$scope.searchCriteria.fromDate = $filter('date')(fromDate, "MMM-yyyy");
    	}
    	PlantAttendanceService.getPlantAttendanceRecordSheets($scope.searchCriteria).then(function (data) {
			$scope.plantAttendanceMstrTOs = data.plantAttendanceMstrTOs;
			/*if ($scope.plantAttendanceMstrTOs.length <= 0)
				//GenericAlertService.alertMessage("Plants are not available for the selected criteria", 'Warning');*/
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
		});
    },
    
    $scope.search12 = function() {
    	if (new Date($scope.searchCriteria.fromDate) > new Date($scope.searchCriteria.toDate)) {
			GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			return;
		}
    	if ($scope.searchMonthDiff() > 12) {
			GenericAlertService.alertMessage("Please select only 12 months", 'Warning');
			return;
		}
    	if ($scope.searchCriteria.searchProject != null && $scope.searchCriteria.searchProject.projIds.length > 0) {
    		$scope.searchCriteria.projIds = $scope.searchCriteria.searchProject.projIds;
    	}
    	$scope.currMonthToDate = $filter('date')(new Date(), "MMM-yyyy");
    	if (($scope.searchCriteria.fromDate == $scope.searchCriteria.toDate) && ($scope.searchCriteria.toDate == $scope.currMonthToDate)) {
    		var fromDate = new Date();
        	fromDate.setMonth(fromDate.getMonth()-2);
        	$scope.searchCriteria.fromDate = $filter('date')(fromDate, "MMM-yyyy");
    	}
    	PlantAttendanceService.getPlantAttendanceRecordSheets($scope.searchCriteria).then(function (data) {
			$scope.plantAttendanceMstrTOs = data.plantAttendanceMstrTOs;
			$scope.gridOptions.data = angular.copy($scope.plantAttendanceMstrTOs);
			if ($scope.plantAttendanceMstrTOs.length <= 0)
				GenericAlertService.alertMessage("Plants are not available for the selected criteria", 'Warning');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
		});
    },
    $scope.resetSearch = function() {
    	var fromDate = new Date();
    	fromDate.setMonth(fromDate.getMonth()-2);
    	$scope.searchCriteria = {
        	userType: '1',
        	fromDate: $filter('date')(fromDate, "MMM-yyyy"),
        	toDate: $filter('date')(new Date(), "MMM-yyyy"),
        };
    	$scope.search();
    },
    $scope.showMore = function(empAttendanceMstrTO) {
    	var refreq = PlantAttendanceRecordSheetMoreDetail.openPopup(empAttendanceMstrTO);
		$scope.gridOptions.data = angular.copy($scope.empAttendanceMstrTOs);
		refreq.then(function(data) {
			$scope.search();
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
    },
    $scope.searchMonthDiff = function() {
    	var dt1 = new Date($scope.searchCriteria.fromDate);
    	var dt2 = new Date($scope.searchCriteria.toDate);
    	var months = 1;
    	while (dt1 < dt2){
    		months += 1;
    		dt1.setMonth(dt1.getMonth()+1);
    	}
    	return months;
    }
    $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'attendanceMonth', cellFilter: 'date:"MMM-yyyy\"', displayName:'Month', headerTooltip: "Month", groupingShowAggregationMenu: false },
				{ field: 'attendenceName', displayName: "Plant Working Status Records Sheet ID", headerTooltip: "Attendance Record Sheet ID", groupingShowAggregationMenu: false },
				{ field: 'parentName', displayName: "EPS", headerTooltip: "EPS", groupingShowAggregationMenu: false },
				{ field: 'name', displayName: "Project", headerTooltip: "Project1234", groupingShowAggregationMenu: false },
				{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew", groupingShowAggregationMenu: false },
				{ field: 'createdBy', displayName: "Supervisor/Originator", headerTooltip: "Supervisor/Originator", groupingShowAggregationMenu: false },
			    { name: 'status',displayName:'Status', cellFilter: 'openclosefilter :row.entity.status', headerTooltip: "Status", cellTemplate: '<div class="ngCellText">&nbsp{{row.entity.status | openclosefilter :row.entity.status}}</div>'},
				{ name: 'More Details', cellClass: 'justify-center', headerCellClass:"justify-center", cellTemplate: '<button ng-click="grid.appScope.showMore(row.entity, empAttendanceMstrTOs)" class="btn btn-primary btn-sm" class="btn bottomaligned btn-brown btn-sm">More Details</botton>', 
			displayName: "More Details", headerTooltip : "Resource Curve", enableFiltering: false,groupingShowAggregationMenu: false }
				]
				console.log(columnDefs,'newValue');
				$scope.search();
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Asbuilt_Records_ListofPlantAttendanceRecordSheets");
		}
	});
    $scope.resetSearch();
    $scope.search();
}]);