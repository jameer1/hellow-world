'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("sorschedules", {
        url: '/sorschedules',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/projectschedules/soritemsschedule.html',
                controller: 'SORItemScheduleController'
            }
        }
    })
}]).controller('SORItemScheduleController', ["$scope", "uiGridGroupingConstants", "uiGridConstants","$q", "$timeout", "EpsProjectSelectFactory", "GenericAlertService", "ngDialog", "stylesService", "ngGridService", "ProjectScheduleService", "DatasetListFactory", "SchedulePlannedValueServiceV2", "ProjectStatusService", "ResourceCurveService", "ScheduleResourceCurvesFactory",
    function ($scope,uiGridGroupingConstants, uiGridConstants, $q, $timeout, EpsProjectSelectFactory, GenericAlertService, ngDialog, stylesService, ngGridService, ProjectScheduleService, DatasetListFactory, SchedulePlannedValueServiceV2, ProjectStatusService, ResourceCurveService, ScheduleResourceCurvesFactory) {
    	
	var selectedItemRows = [];
	let settings = {};
	$scope.stylesSvc = stylesService;
	$scope.timeScales = [
		{value: 7, type: "Weekly"}, 
		{value: 30, type: "Monthly"}, 
		{value: 365, type: "Yearly"},
	];
	$scope.selectedTimeScale = $scope.timeScales[0];
	$scope.selectedScheduleActivityDataSetTOs = [];
	$scope.resourceCurveTypeMap = [];
	$scope.isPrimaveraIntegrationEnabled = 'Yes';
	
	let scheduleOfRateColumnDefs = [
		{ field: 'scheduleOfRateTO.name', displayName: "Name", headerTooltip : "Name", enableFiltering: false,groupingShowAggregationMenu: false,},
		{ field: 'scheduleOfRateTO.measureUnitTO.name', displayName: "UOM", headerTooltip : "Unit of Measure", enableFiltering: false,groupingShowAggregationMenu: false,},
		{ field: 'originalQty', displayName: "Original Budget Amount", headerTooltip : "Original Budget Amount", enableFiltering: false,groupingShowAggregationMenu: false, cellFilter: 'number: 2', cellClass: "justify-right" },
		{ field: 'revisedQty', displayName: "Revised Budget Amount", headerTooltip : "Revised Budget Amount", enableFiltering: false, groupingShowAggregationMenu: false,cellFilter: 'number: 2', cellClass: "justify-right" },
		{ field: 'actualQty', displayName: "Actual Amount", headerTooltip : "Actual Amount to Date", enableFiltering: false,groupingShowAggregationMenu: false, cellFilter: 'number: 2', cellClass: "justify-right" },
		{ name: 'Remainingamount', displayName: "Remaining Amount", headerTooltip : "Remaining Amount", enableFiltering: false,groupingShowAggregationMenu: false},
		{ field: 'startDt', displayName: "Start Date", headerTooltip : "Schedule Start Date", enableFiltering: false,groupingShowAggregationMenu: false, cellFilter: 'date' },
		{ field: 'finishDt', displayName: "Finish Date", headerTooltip : "Schedule Finish Date", enableFiltering: false,groupingShowAggregationMenu: false, cellFilter: 'date' },
		{ field: 'minStartDateOfBaseline', displayName: "Baseline Start", headerTooltip : "Baseline Start Date", enableFiltering: false,groupingShowAggregationMenu: false, cellFilter: 'date' },
		{ field: 'maxFinishDateOfBaseline', displayName: "Baseline Finish", headerTooltip : "Baseline Finish Date", enableFiltering: false, groupingShowAggregationMenu: false,cellFilter: 'date' },
		{ name: 'curve', cellTemplate: 
			"<div class='ui-grid-cell-contents'><input type='text' class='form-Control-for-grid' data-test='Schedules_ProjectSchedules_PlantTab_GetCurve'" 
			+ "ng-click=' grid.appScope.getResourceCurve($event, row.entity)'" 
			+ "ng-model=' grid.appScope.resourceCurveTypeMap[ row.entity.resourceCurveId].curveType'"
			+ "readonly='readonly' placeholder='Curve Type'></div>", 
			displayName: "Curve", headerTooltip : "Resource Curve", enableFiltering: false,groupingShowAggregationMenu: false }
	];
	let setGridOptions = function() {
		$scope.gridOptions.enableRowHeaderSelection = true;
		$scope.gridOptions.enableFullRowSelection = true;
		$scope.gridOptions.enableSelectAll = true;
		$scope.gridOptions.columnDefs[$scope.gridOptions.columnDefs.length-1].visible = ($scope.isPrimaveraIntegrationEnabled == 'Yes') ? false : true;
	};
	let loadGridOptions = function() {
		let columnDefs = scheduleOfRateColumnDefs;
		let data = [];
		$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
		$timeout(function () {
			setGridOptions();
        });
	};
	let resetCurve = function (){
		$scope.data = [];
		$scope.labels = [];
		$scope.series = [];
		$scope.datasetOverride = [];
		$scope.options = [];
	}
	let resetGrid = function () {
		$scope.gridOptions.data = [];
		selectedItemRows = [];
	};
	let getResourceCurveTypes = function () {
		ResourceCurveService.getResourceCurves({"status": 1}).then(function (data) {
			angular.forEach(data.projResourceCurveTOs, function (value, key) {
				$scope.resourceCurveTypeMap[value.id] = value;
			});
		}, function (error) {
			GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
		});
	}
	
	$scope.getUserProjects = function () {
		EpsProjectSelectFactory.getEPSUserProjects().then(function (data) {
			$scope.searchProject = data.searchProject;
			ProjectStatusService.getProjGenerals({"projId": $scope.searchProject.projId, "status": 1}).then(function(data){
				$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
			}, function (error) {
				cosole.log(error)
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
			ProjectScheduleService.getDatasetList({"projId": $scope.searchProject.projId, "type": "R"}).then(function(data){
				$scope.selectedScheduleActivityDataSetTOs = [];
				if (data.scheduleActivityDataSetTOs.length == 0)
					$scope.selectedScheduleActivityDataSetTOs.push({"id": -1, "datasetName":"Current Plan", "current": false, "baseline": false});
				$scope.selectedScheduleActivityDataSetTOs.push({"id": 0, "datasetName":"Actual", "current": false, "baseline": false});
        		for (let i=0; i < data.scheduleActivityDataSetTOs.length; i++) {
        			if (data.scheduleActivityDataSetTOs[i].current) {
        				$scope.selectedScheduleActivityDataSetTOs.push(data.scheduleActivityDataSetTOs[i]);
        				break;
        			}
        		}
        	}, function (error) {
        		cosole.log(error)
				GenericAlertService.alertMessage("Error occured while getting dataset", 'Error');
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.onSearchClick = function() {
		resetGrid();
		resetCurve();
		loadGridOptions();
		if ($scope.searchProject == null || $scope.searchProject.projId == null) {
			GenericAlertService.alertMessage("Please select the project", "Info");
			return;
		}
		ProjectScheduleService.getScheduleOfRates({"projId": $scope.searchProject.projId}).then(function(data){
				for (let manpower of data.projScheduleSOWTOs) {
				manpower.Remainingamount = (((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty)-manpower.actualQty) >= 0 ? ((manpower.revisedQty ? manpower.revisedQty : manpower.originalQty)-manpower.actualQty) :0);
			}
			$scope.gridOptions.data = data.projScheduleSOWTOs;
			settings.reportProjectSetting = data.projReportsTo,
			settings.specialNonWorkingDays = data.calNonWorkingDays,
			settings.projectCalendar = data.regularHolidays,
			settings.specialWorkingDays = data.calSplWorkingDays
		}, function (error) {
    		cosole.log(error)
			GenericAlertService.alertMessage("Error occured while getting schedule of rates", 'Error');
		});
	},
	$scope.resetData = function() {
		$scope.selectedTimeScale = $scope.timeScales[0];
		$scope.selectedScheduleActivityDataSetTOs = [];
		$scope.searchProject = {};
		resetGrid();
		resetCurve();
	},
	$scope.getFullDatasetname = function(scheduleActivityDataSetTO) {
		if (scheduleActivityDataSetTO.current)
			return scheduleActivityDataSetTO.datasetName + " (Current)";
		if (scheduleActivityDataSetTO.baseline)
			return scheduleActivityDataSetTO.datasetName + " (Baseline)";
		else
			return scheduleActivityDataSetTO.datasetName;
	},
	$scope.selectMultipleScheduleActivityDataSetTOs = function() {
		if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
			GenericAlertService.alertMessage("Please select the project", "Info");
			return;
		}
		DatasetListFactory.selectMultiple($scope.searchProject, "R", $scope.selectedScheduleActivityDataSetTOs, true, true).then(function(data){
			$scope.selectedScheduleActivityDataSetTOs = data;
		}, function (error) {
    		cosole.log(error)
			GenericAlertService.alertMessage("Error occured while selecting datasets", 'Error');
		});
	},
	$scope.gridItemSelected = function (selectedRow) {
		let index = -1;
		let firstUOM = "";
		for (let i=0; i < selectedItemRows.length; i++) {
			if (firstUOM.length == 0)
				firstUOM = selectedItemRows[i].scheduleOfRateTO.measureUnitTO.name;
			if (selectedItemRows[i].scheduleOfRateTO.id == selectedRow.scheduleOfRateTO.id)
				index = i;
		}
		if (index > -1)
			selectedItemRows.splice(index, 1);
		else if (firstUOM.length == 0 || firstUOM == selectedRow.scheduleOfRateTO.measureUnitTO.name)
			selectedItemRows.push(selectedRow);
		else {
			$scope.gridApi.selection.unSelectRow(selectedRow);
			GenericAlertService.alertMessage("Please select rows with common Unit", "Info");
		}
	},
	$scope.gridItemsSelected = function (selectedRows) {
		let showAlert = false;
		let firstUOM = "";
		let deleteItemRows = [];
		if (selectedItemRows.length > 0)
			firstUOM = selectedItemRows[0].scheduleOfRateTO.measureUnitTO.name;
		for (let i=0; i < selectedRows.length; i++) {
			if (firstUOM.length == 0)
				firstUOM = selectedRows[i].entity.scheduleOfRateTO.measureUnitTO.name;
			if (selectedRows[i].isSelected && firstUOM == selectedRows[i].entity.scheduleOfRateTO.measureUnitTO.name)
				selectedItemRows.push(selectedRows[i].entity);
			else if (!selectedRows[i].isSelected)
				deleteItemRows.push(selectedItemRows[i]);
			else {
				$scope.gridApi.selection.unSelectRow(selectedRows[i].entity);
				showAlert = true;
			}
		}
		for (let i=0; i < deleteItemRows.length; i++) {
			for (let j=0; j < selectedRows.length; j++) {
				if (deleteItemRows[i].id == selectedItemRows[j].id) {
					selectedItemRows.splice(j, 1);
					break;
				}
			}
		}
		if (showAlert)
			GenericAlertService.alertMessage("Selected rows with common Unit [" + firstUOM + "]", "Info");
	},
	$scope.getCurve = function() {
		resetCurve()
		if (selectedItemRows.length == 0) {
            GenericAlertService.alertMessage("Please select the item or resource curve", "Info");
            return;
        }
		let resourceIds = [];
		for (let i=0; i < selectedItemRows.length; i++)
			resourceIds.push(selectedItemRows[i].scheduleOfRateTO.id);
		ProjectScheduleService.getDateWiseForecastActualForResource({"projId": $scope.searchProject.projId, "scheduleActivityDataSetTOs": $scope.selectedScheduleActivityDataSetTOs, "resourceIds": resourceIds}).then(function(data){
			let datasetCounter = 0, actualCounter = 0;
			let datasetSelected = false, actualSelected = false, currentPlanSelected = false;
			for (let i=0; i < data.scheduleActivityDataSetTOs.length; i++) {
				for (let j=0; j < data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs.length; j++) {
					if (data.scheduleActivityDataSetTOs[i].id == -1) {
						currentPlanSelected = true;
					} else if (data.scheduleActivityDataSetTOs[i].id == 0) {
						actualSelected = true;
						if (data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[j].resourceAssignmentDataValueTOs != null)
							actualCounter += data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[j].resourceAssignmentDataValueTOs.length;
					} else {
						datasetSelected = true;
						if (data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[j].resourceAssignmentDataValueTOs != null)
							datasetCounter += data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[j].resourceAssignmentDataValueTOs.length;
					}
				}
			}
			if (currentPlanSelected == false && datasetCounter + actualCounter == 0){
				GenericAlertService.alertMessage("No data to plot curve", 'Info');
			} else {
				let req = {
					"selectedItemRows": selectedItemRows,
					"selectedTimeScale": $scope.selectedTimeScale,
					"reportProjectSetting": settings.reportProjectSetting,
					"specialNonWorkingDays": settings.specialNonWorkingDays,
					"projectCalendar": settings.projectCalendar,
					"specialWorkingDays": settings.specialWorkingDays,
					"resourceCurveTypeMap": $scope.resourceCurveTypeMap,
					"data": data
				};
				let resp = SchedulePlannedValueServiceV2.createPlannedCurve(req);
				$scope.data = resp.data;
				$scope.labels = resp.labels;
				$scope.series = resp.series;
				$scope.datasetOverride = resp.datasetOverride;
				$scope.options = resp.options;
				/*if (actualSelected && actualCounter == 0)
					GenericAlertService.alertMessage("No actual values exist", 'Info');
				if (datasetSelected && datasetCounter == 0)
					GenericAlertService.alertMessage("No planned values exist", 'Info');*/
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting resource assignment data", 'Error');
		});
	},
	$scope.getResourceCurve = function ($event, selectedSchItem) {
		$event.stopPropagation();
		ScheduleResourceCurvesFactory.getResourceCurves().then(function (data) {
			$scope.searchCurve = data.selectedCurve;
			selectedSchItem.resourceCurveId = data.selectedCurve.id;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting resource curve", 'Error');
		})
	},
	$scope.saveResourceCurveMapping = function() {
		let req = [];
		angular.forEach($scope.gridOptions.data, function(row, key){
			req.push({curveId: row.resourceCurveId, resourceReferenceId: row.scheduleOfRateTO.id, resourceReferenceType: "POT_SOR"});
		})
		ProjectScheduleService.saveResourceCurveMapping({resourceCurveMappingTOs: req}).then(function (data) {
			GenericAlertService.alertMessage("Saved successfully", 'Info');
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting saving", "Error");
		});
	};
	loadGridOptions();
	getResourceCurveTypes();

        var HelpService = {};
        $scope.helpPage = function () {
            var help = HelpService.pageHelp();
            help.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error", 'Info');
            })
        }
        var helppagepopup;
        HelpService.pageHelp = function () {
            var deferred = $q.defer();
            helppagepopup = ngDialog.open({
                template: 'views/help&tutorials/Enterprisehelp.html',
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


