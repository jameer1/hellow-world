'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantlogbook", {
		url: '/plantlogbook',
		data: {
			roles: []
		},
		views: {
			'current@': {
				templateUrl: 'views/projresources/projplantreg/plantlogbook/plantreglogbook.html',
				controller: 'PlantLogBookController'
			}
		}
	})
}]).controller("PlantLogBookController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "PlantLogBookFactory", "PlantRegisterService", "GenericAlertService","stylesService", "ngGridService", function ($rootScope, $scope, $q, $state, $filter, ngDialog, PlantLogBookFactory, PlantRegisterService, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantLogBookTable = true;
	$scope.plantLogRecordsTOs = [];
	var editLogBook = [];
	$scope.userProjMap = [];

	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

	$scope.logbookbuttonshow = false;
	/*$scope.getPlantLogRecords = function () {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the plant", "Warning");
			return;
		}
		logBookRecords();
	}*/
	/*function logBookRecords() {
		
	}*/
	//$scope.getPlantLogRecords();
	$scope.getPlantLogSearch = function () {
		if ($scope.fromDate == null || $scope.fromDate == undefined || $scope.toDate == null || $scope.toDate == undefined) {
			GenericAlertService.alertMessage("Please enter input dates", "Warning");
			return;
		}
		var startDate = new Date($scope.fromDate);
		var finishDate = new Date($scope.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('Start Date can not be greater than Finish Date', 'Warning');
			return;
		}
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the Plant", "Warning");
			return;
		}
		if ($rootScope.selectedPlantData.projId == null || $rootScope.selectedPlantData.projId == undefined) {
			GenericAlertService.alertMessage("Selected plant is not assigned to any project", "Warning");
			return;
		}
		$scope.logbookbuttonshow = true;
		var req = {
			"status": 1,
			"projId": $rootScope.selectedPlantData.projId,
			"plantId": $rootScope.selectedPlantData.id,
			"fromDate": $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		};
		PlantRegisterService.getPlantLogRecords(req).then(function (data) {
			$scope.plantLogRecordsTOs = data.plantLogRecordsTOs;
			$scope.gridOptions.data = angular.copy($scope.plantLogRecordsTOs);
			$scope.userProjMap = data.userProjMap;
			$scope.plantLogBookTable = true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting plant logbook details", "Error");
		});
	}
	$scope.getPlantLogSearch();
	
	$scope.logBookRowSelect = function (logbook) {
		if (logbook.select) {
			editLogBook.push(logbook);
		} else {
			editLogBook.pop(logbook);
		}
	}

	$scope.getLogBookDetails = function () {
		if (editLogBook.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		var itemData = {
			"userProjMap": $scope.userProjMap,
			"editLogBook": editLogBook
		};
		var plantLogBookPopup = PlantLogBookFactory.getLogBookDetails(itemData);
		plantLogBookPopup.then(function (data) {
			$scope.plantLogBookTable = false;
			$scope.plantLogRecordsTOs = data.plantLogRecordsTOs;
			$scope.getPlantLogSearch();
			editLogBook = [];			
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant log book details", 'Error');
		});
	},
	$scope.showplantLogBookTO = function (remarks) {
		GenericAlertService.comments(remarks);
	}
	/*$scope.$on("resetLogBook", function () {
		$scope.plantLogRecordsTOs = [];
	});*/
    $scope.resetLogBook = function(){
    $scope.plantLogRecordsTOs = [];
    $scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	$scope.gridOptions.data = [];
    }
	
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-disabled="!row.entity.latest" ng-change="grid.appScope.logBookRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'fromDate', displayName: "Date", headerTooltip: "Date"},
						{ field: 'plantRegProjTO.assertId', displayName: "Asset ID", headerTooltip: "Asset ID", },
						{ field: 'plantRegProjTO.parentName', displayName: "EPS", headerTooltip: "EPS"},
						{ field: 'plantRegProjTO.name', displayName: "Project", headerTooltip: "Project",},
						{ field: 'empLabelKeyTO.code', displayName: "Emp ID  of Driver/Operator", headerTooltip: "Emp ID  of Driver/Operator",},
						
						{ field: 'empLabelKeyTO.name', displayName: "Name of Driver/Operator", headerTooltip: "Name of Driver/Operator"},
						{ field: 'startMeter', displayName: "Start Meter Reading", headerTooltip: "Start Meter Reading", },
						{ field: 'endMeter', displayName: "End Meter Reading", headerTooltip: "End Meter Reading"},
						{ field: 'netUnits', displayName: "Net Unit Used", headerTooltip: "Net Unit Used",},
						{ field: 'comments', displayName: "Purpose", headerTooltip: "Purpose",
						celltemplate:'<div ng-click="grid.appScope.showplantLogBookTO(row.entity.comments)">{{(row.entity.comments.length>0)?(row.entity.comments | limitTo: 10) + (row.entity.comments.length > 10 ? "..." : "") : ""}}</div>'},
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_Plant_LogBookRecords");
					$scope.getPlantLogSearch();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);