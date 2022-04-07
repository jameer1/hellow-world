'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("projschedules", {
		url: '/projschedules',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectschedules/scheduleprojectview.html',
				controller: 'ProjectScheduleController'
			}
		}
	})
}]).controller('ProjectScheduleController',	["$scope", "$state", "$q", "$timeout", "EpsProjectSelectFactory", "GenericAlertService", "ProjectScheduleService", "ScheduleResourceCurvesFactory", "ResourceCurveService", "stylesService", "ngGridService", "ngDialog", "ProjectStatusService", "SchedulePlannedValueServiceV2", "DatasetListFactory", 
	function ($scope, $state, $q, $timeout, EpsProjectSelectFactory, GenericAlertService, ProjectScheduleService, ScheduleResourceCurvesFactory, ResourceCurveService, stylesService, ngGridService,ngDialog, ProjectStatusService, SchedulePlannedValueServiceV2, DatasetListFactory) {
		
		var MANPOWER = 'E';
		var MATERIAL = 'M';
		var PLANT = 'P';
		var COST_CODE = 'C';
		var SOW = 'S';
		
		$scope.timeScales = [
			{value: 7, type: "Weekly"}, 
			{value: 30, type: "Monthly"}, 
			{value: 365, type: "Yearly"},
		];
		$scope.selectedTimeScale = $scope.timeScales[0];
		$scope.searchCurve = {};
		$scope.resourceCurveTypeMap = [];
		$scope.scheduleItemDetails = [];
		$scope.searchProject = {};
		var selectedItemRow = {};
		var selectedItemRows = [];
		var selectedItemList = [];
		$scope.calNonWorkingDays = [];
		$scope.actualWorkingDayMap = [];
		$scope.selectedRow = null;
		$scope.empClassificationMap = [];
		$scope.plantClassificationMap = [];
		$scope.materialClassificationMap = [];
		$scope.projCostItemMap = [];
		$scope.sowItemMap = [];
		var scheduleItemType = MATERIAL;
		$scope.labelsFullDate = [];
		$scope.actualQuantityId = "materialClassId";
		var selectedTab = null;
		$scope.selectedScheduleActivityDataSetTOs = [];
		$scope.isPrimaveraIntegrationEnabled = 'Yes';
		
		let materialColumnDefs = [
			{ field: 'materialId', displayName: "Material Id", headerTooltip : "Material Id", enableFiltering: false },
			{ field: 'materialName', displayName: "Name", headerTooltip : "Material Name", enableFiltering: false },
			{ field: 'measureName', displayName: "Units", headerTooltip : "Unit of Measure", enableFiltering: false },
			{ field: 'originalQty', displayName: "Original Qty", headerTooltip : "Original Budget Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'revisedQty', displayName: "Revised Qty", headerTooltip : "Revised Budget Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'actualQty', displayName: "Actual Qty", headerTooltip : "Actual Quantity to Date", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ name: 'remainingQty',cellFilter: 'number: 2', displayName: "Remaining Qty", headerTooltip : "Remaining Quantity", enableFiltering: false}, 
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) >= 0 ? ((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) : 0)|number:2}}</div>"},
*/			{ field: 'estimateToComplete', displayName: "Estimate to Complete", headerTooltip : "Estimate to Complete", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'estimateAtCompletion', displayName: "Estimate At Completion", headerTooltip : "Estimation at Completion Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'startDate', displayName: "Start Date", headerTooltip : "Schedule Start", enableFiltering: false },
			{ field: 'finishDate', displayName: "Finish Date", headerTooltip : "Schedule Finish", enableFiltering: false },
			{ field: 'minStartDateOfBaseline', displayName: "Baseline Start", headerTooltip : "Baseline Start", enableFiltering: false, cellFilter: 'date' },
			{ field: 'maxFinishDateOfBaseline', displayName: "Baseline Finish", headerTooltip : "Baseline Finish", enableFiltering: false, cellFilter: 'date' },
			{ name: 'curve', displayName: "Curve", headerTooltip : "Resource Curve", enableFiltering: false,
				cellTemplate: "<div class='ui-grid-cell-contents'><input type='text' class='form-Control-for-grid' data-test='Schedules_ProjectSchedules_MaterialsTab_GetCurve'" 
				+ "ng-click=' grid.appScope.getResourceCurve($event, row.entity)'" 
				+ "ng-model=' grid.appScope.resourceCurveTypeMap[ row.entity.resourceCurveId].curveType'"
				+ "readonly='readonly' placeholder='Curve Type'></div>"}
		];
		let manpowerColumnDefs = [
			{ field: 'empClassTO.code', displayName: "Resource Id", headerTooltip : "Resource ID", enableFiltering: false},
			{ field: 'empClassTO.name', displayName: "Name", headerTooltip : "Resource Name", enableFiltering: false },
			{ field: 'projEmpCategory', displayName: "Category Id", headerTooltip : "Category ID", enableFiltering: false },
			{ field: 'measureUnitTO.name',displayName: "Units", headerTooltip : "Unit of Measure", enableFiltering: false },
			{ field: 'originalQty', displayName: "Original Qty", headerTooltip : "Original Budget Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'revisedQty', displayName: "Revised Qty", headerTooltip : "Revised Budget Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'actualQty', displayName: "Actual Qty", headerTooltip : "Actual Quantity to Date", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ name: 'remainingQty',cellFilter: 'number: 2', 
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) >= 0 ? ((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) : 0 | number: 2}}</div>", 
*/				displayName: "Remaining Quantity", headerTooltip : "Remaining Quantity", enableFiltering: false },
			{ name: 'estimateToComplete',cellFilter: 'number: 2',
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(grid.appScope.calcEstimateToComplete(row.entity, 1)) >= 0 ? (grid.appScope.calcEstimateToComplete(row.entity, 1)) : 0 | number: 2}}</div>",  
*/				displayName: "Estimate to Complete", headerTooltip : "Estimate to Complete", enableFiltering: false },
			{ name: 'estimateAtCompletion', cellFilter: 'number: 2',
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(grid.appScope.calcEstimateAtCompletion(row.entity)) >= 0 ? (grid.appScope.calcEstimateAtCompletion(row.entity)) : 0 | number: 2}}</div>",  
*/				displayName: "Estimation at Completion", headerTooltip : "Estimation at Completion Quantity", enableFiltering: false },
			{ field: 'startDate', displayName: "StartDate", headerTooltip : "Schedule Start", enableFiltering: false },
			{ field: 'finishDate', displayName: "FinishDate", headerTooltip : "Schedule Finish", enableFiltering: false },
			{ field: 'minStartDateOfBaseline', displayName: "Baseline Start", headerTooltip : "Baseline Start", enableFiltering: false, cellFilter: 'date' },
			{ field: 'maxFinishDateOfBaseline', displayName: "Baseline Finish", headerTooltip : "Baseline Finish", enableFiltering: false, cellFilter: 'date' },
			{ name: 'curve', cellTemplate: 
				"<div class='ui-grid-cell-contents'><input type='text' class='form-Control-for-grid' data-test='Schedules_ProjectSchedules_ManpowerTab_GetCurve'" 
				+ "ng-click=' grid.appScope.getResourceCurve($event, row.entity)'" 
				+ "ng-model=' grid.appScope.resourceCurveTypeMap[ row.entity.resourceCurveId].curveType'"
				+ "readonly='readonly' placeholder='Curve Type'></div>", 
				displayName: "Curve", headerTooltip : "Resource Curve", enableFiltering: false }
		];
		let plantColumnDefs = [
			{ field: 'plantClassTO.code', displayName: "Resource Id", headerTooltip : "Plant Classification ID", enableFiltering: false },
			{ field: 'plantClassTO.name',displayName: "Name", headerTooltip : "Plant Classification Name", enableFiltering: false },
			{ field: 'plantClassTO.measureUnitTO.name',displayName: "Units", headerTooltip : "Unit of Measure", enableFiltering: false },
			{ field: 'originalQty', displayName: "Original Qty", headerTooltip : "Original Budget Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'revisedQty', displayName: "Revised Qty", headerTooltip : "Revised Budget Quantity", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'actualQty', displayName: "Actual Qty", headerTooltip : "Actual Quantity to Date", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ name: 'remainingQty', cellFilter: 'number: 2',
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) >= 0 ? ((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) : 0) | number: 2}}</div>", 
*/				displayName: "Remaining Quantity", headerTooltip : "Remaining Quantity", enableFiltering: false },
			{ name: 'estimateToComplete',cellFilter: 'number: 2',
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{((grid.appScope.calcEstimateToComplete(row.entity, 1)) >= 0 ? (grid.appScope.calcEstimateToComplete(row.entity, 1)) : 0) | number: 2}}</div>",  
*/				displayName: "Estimate to Complete", headerTooltip : "Estimate to Complete", enableFiltering: false },
			{ name: 'estimateAtCompletion', cellFilter: 'number: 2',
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(grid.appScope.calcEstimateAtCompletion(row.entity)) >= 0 ? (grid.appScope.calcEstimateAtCompletion(row.entity)) : 0 | number: 2}}</div>",  
*/				displayName: "Estimation at Completion", headerTooltip : "Estimation at Completion Quantity", enableFiltering: false },
			{ field: 'startDate', displayName: "StartDate", headerTooltip : "Schedule Start", enableFiltering: false },
			{ field: 'finishDate', displayName: "FinishDate", headerTooltip : "Schedule Finish", enableFiltering: false },
			{ field: 'minStartDateOfBaseline', displayName: "Baseline Start", headerTooltip : "Baseline Start", enableFiltering: false, cellFilter: 'date' },
			{ field: 'maxFinishDateOfBaseline', displayName: "Baseline Finish", headerTooltip : "Baseline Finish", enableFiltering: false, cellFilter: 'date' },
			{ name: 'curve', cellTemplate: 
				"<div class='ui-grid-cell-contents'><input type='text' class='form-Control-for-grid' data-test='Schedules_ProjectSchedules_PlantTab_GetCurve'" 
				+ "ng-click=' grid.appScope.getResourceCurve($event, row.entity)'" 
				+ "ng-model=' grid.appScope.resourceCurveTypeMap[ row.entity.resourceCurveId].curveType'"
				+ "readonly='readonly' placeholder='Curve Type'></div>", 
				displayName: "Curve", headerTooltip : "Resource Curve", enableFiltering: false }
		];
		let costColumnDefs = [
			{ field: 'projCostItemTO.code',displayName: "Cost Code Id", headerTooltip : "Cost ID", enableFiltering: false },
			{ field: 'projCostItemTO.name', displayName: "Name", headerTooltip : "Cost Name", enableFiltering: false },
			{ field: 'originalQty', displayName: "Original Budget", headerTooltip : "Original Budget", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'revisedQty', displayName: "Revised Budget", headerTooltip : "Revised Budget", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ field: 'actualQty', displayName: "Actual Cost", headerTooltip : "Actual Cost to Date", enableFiltering: false, cellFilter: 'number: 2', cellClass: "justify-right" },
			{ name: 'rmqty', cellFilter: 'number: 2',
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) >= 0 ? ((row.entity.revisedQty ? row.entity.revisedQty : row.entity.originalQty) - row.entity.actualQty) : 0)|number:2}}</div>", 
*/				displayName: "Remaining Budget", headerTooltip : "Remaining Budget", enableFiltering: false },
			{ name: 'spentCost',cellFilter: 'number: 2', 
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{(grid.appScope.calcEstimateToComplete(row.entity, 1) >= 0 ? grid.appScope.calcEstimateToComplete(row.entity, 1) : 0)|number:2}}</div>", 
*/				displayName: "% Spent Cost", headerTooltip : "% Spent Cost", enableFiltering: false },
			{ name: 'progressOfWork',cellFilter: 'number: 2', 
/*				cellTemplate: "<div class='ui-grid-cell-contents' style='text-align:right;'>{{grid.appScope.calcEstimateAtCompletion(row.entity, 1) >= 0 ? grid.appScope.calcEstimateAtCompletion(row.entity, 1) : 0|number: 2}}</div>", 
*/				displayName: "% Progress of Work", headerTooltip : "% Progress of work", enableFiltering: false },
			{ field: 'startDate', displayName: "StartDate", headerTooltip : "Actual Start", enableFiltering: false },
			{ field: 'finishDate', displayName: "FinishDate", headerTooltip : "Actual Finish", enableFiltering: false },
			{ field: 'minStartDateOfBaseline', displayName: "Baseline Start", headerTooltip : "Baseline Start", enableFiltering: false, cellFilter: 'date' },
			{ field: 'maxFinishDateOfBaseline', displayName: "Baseline Finish", headerTooltip : "Baseline Finish", enableFiltering: false, cellFilter: 'date' },
			{ name: 'curve', cellTemplate: 
				"<div class='ui-grid-cell-contents'><input type='text' class='form-Control-for-grid' data-test='Schedules_ProjectSchedules_CostTab_GetCurve'" 
				+ "ng-click=' grid.appScope.getResourceCurve($event, row.entity)'" 
				+ "ng-model=' grid.appScope.resourceCurveTypeMap[ row.entity.resourceCurveId].curveType'"
				+ "readonly='readonly' placeholder='Curve Type'></div>", 
				displayName: "Curve", headerTooltip : "Resource Curve", enableFiltering: false }
		];
		
		let setGridOptions = function() {
			$scope.gridOptions.enableRowHeaderSelection = true;
			$scope.gridOptions.enableFullRowSelection = true;
			$scope.gridOptions.enableSelectAll = true;
			$scope.gridOptions.columnDefs[$scope.gridOptions.columnDefs.length-1].visible = ($scope.isPrimaveraIntegrationEnabled == 'Yes') ? false : true;
		};
		
		let loadMaterialGridOptions = function() {
			let columnDefs = materialColumnDefs;
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Schedule_Resource Schedule_Material");
			$timeout(function () {
				setGridOptions();
            });
		};
		let loadManpowerGridOptions = function() {
			let columnDefs = manpowerColumnDefs;
			let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Schedule_Resource Schedule_Manpower");
			$timeout(function () {
				setGridOptions();
            });
		};
		let loadPlantGridOptions = function() {
			let columnDefs = plantColumnDefs;
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Schedule_Resource Schedule_Plant&Equipment");
			$timeout(function () {
				setGridOptions();
            });
		};
		let loadCostGridOptions = function() {
			let columnDefs = costColumnDefs;
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Schedule_Resource Schedule_Cost Budget");
			$timeout(function () {
				setGridOptions();
            });
		}

		$scope.calcEstimateToComplete = function (budgetObj, formulaType) {
			var BAC = 0;
			if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0 && budgetObj.actualQty) {
				BAC = budgetObj.revisedQty - budgetObj.actualQty;
			} else {
				BAC = budgetObj.originalQty - budgetObj.actualQty
			}
			if (formulaType == 1) {
				budgetObj.estimateComplete = BAC;
				return budgetObj.estimateComplete;
			} else if (formulaType == 2) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue);
				return budgetObj.estimateComplete;
			} else if (formulaType == 3) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.costPermIndex;
				return budgetObj.estimateComplete;
			} else if (formulaType == 4) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.costPermIndex * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			} else if (formulaType == 5) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.progressFactor;
				return budgetObj.estimateComplete;
			} else if (formulaType == 6) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.progressFactor * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			}
		},
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) 
					loadData($scope.currentTab);
		}),
		$scope.calcEstimateAtCompletion = function (budgetObj) {
			budgetObj.estimateCompletion = parseFloat(budgetObj.actualQty) + budgetObj.estimateComplete;
			return budgetObj.estimateCompletion;
		},
		$scope.getResourceCurveTypes = function () {
			var req = {"status": 1};
			ResourceCurveService.getResourceCurves(req).then(function (data) {
				angular.forEach(data.projResourceCurveTOs, function (value, key) {
					$scope.resourceCurveTypeMap[value.id] = value;
				});
			}, function (error) {
				GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
			});
		};
		$scope.getResourceCurveTypes();

		$scope.getUserProjects = function () {
			$scope.selectedTimeScale = $scope.timeScales[0];
			var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
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
		$scope.getFullDatasetname = function(scheduleActivityDataSetTO) {
			if (scheduleActivityDataSetTO.current)
				return scheduleActivityDataSetTO.datasetName + " (Current)";
			if (scheduleActivityDataSetTO.baseline)
				return scheduleActivityDataSetTO.datasetName + " (Baseline)";
			else
				return scheduleActivityDataSetTO.datasetName;
		},
		$scope.resetData = function () {
			$scope.searchProject = {};
			$scope.selectedTimeScale = $scope.timeScales[0];
			$scope.scheduleItemDetails = [];
			$scope.data = [];
			$scope.cummulativeData = [];
			selectedItemRow = {};
			selectedItemRows = [];
			selectedItemList = [];
			$scope.selectedRow = null;
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
			loadData();
		},
		$scope.gridItemSelected = function (selectedRow) {
			let index = -1;
			let firstUOM = "";
			if (selectedItemRows.length > 0)
				firstUOM = getValue(selectedItemRows[0], 'uom');
			for (let i=0; i < selectedItemRows.length; i++) {
				if (firstUOM.length == 0)
					firstUOM = getValue(selectedItemRows[i], 'uom');
				if (getValue(selectedItemRows[i], 'id') == getValue(selectedRow, 'id'))
					index = i;
			}
			if (index > -1)
				selectedItemRows.splice(index, 1);
			else if (firstUOM.length == 0 || firstUOM == getValue(selectedRow, 'uom'))
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
				firstUOM = getValue(selectedItemRows[0], 'uom');
			for (let i=0; i < selectedRows.length; i++) {
				if (firstUOM.length == 0)
					firstUOM = getValue(selectedRows[i].entity, 'uom');
				if (selectedRows[i].isSelected && firstUOM == getValue(selectedRows[i].entity, 'uom'))
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
		$scope.selectScheduleItems = function (selectedItem) {
			if (selectedItem.select) {
				selectedItemList.push(selectedItem);
			} else {
				selectedItemList.pop(selectedItem);
			}
		};

		function addActualValues() {
			switch (scheduleItemType) {
				case MANPOWER:
					$scope.scheduleItemDetails.map(schItm => {
						schItm.actualQty = ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]) 
							? ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]).code : 0 ;
					 	schItm.remainingQty1=(schItm.revisedQty ? schItm.revisedQty : schItm.originalQty)-schItm.actualQty ;
					    schItm.remainingQty=schItm.remainingQty1 >= 0 ? schItm.remainingQty1 : 0;
	                    schItm.actualQty = ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]) 
							? ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]).code : undefined ;
						schItm.estimateToComplete = $scope.calcEstimateToComplete(schItm, 1)>= 0 ? $scope.calcEstimateToComplete(schItm, 1) : '';
						schItm.estimateAtCompletion = $scope.calcEstimateAtCompletion(schItm)>= 0 ? $scope.calcEstimateAtCompletion(schItm) : '';
					
					});
					break;
				case MATERIAL:
					$scope.scheduleItemDetails.map(schItm => {
						schItm.materialId = schItm.materialClassTO.code;
						schItm.materialName = schItm.materialClassTO.name;
						schItm.measureName = schItm.materialClassTO.measureUnitTO.name;
						schItm.materialCode =  ($scope.actualWorkingDayMap && $scope.actualWorkingDayMap.length > 0) ? $scope.actualWorkingDayMap[schItm.materialClassId].code : undefined;
						schItm.actualQty = ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]) 
						? ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]).code : 0;
						schItm.remainingQty1 = (schItm.revisedQty ? schItm.revisedQty : schItm.originalQty) - schItm.actualQty;
						schItm.remainingQty=schItm.remainingQty1 >= 0 ? schItm.remainingQty1 : 0 ;
						schItm.actualQty = ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]) 
						? ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]).code : undefined ;
						schItm.estimateToComplete = $scope.calcEstimateToComplete(schItm, 1)>= 0 ? $scope.calcEstimateToComplete(schItm, 1) : '';
						schItm.estimateAtCompletion = $scope.calcEstimateAtCompletion(schItm)>= 0 ? $scope.calcEstimateAtCompletion(schItm) : '';
					});
					break;
				case PLANT:
					$scope.scheduleItemDetails.map(schItm => {
						schItm.actualQty = ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]) 
							? ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]).code : 0;
							schItm.remainingQty1=(schItm.revisedQty ? schItm.revisedQty : schItm.originalQty)-schItm.actualQty ;
					    schItm.remainingQty=schItm.remainingQty1 >= 0 ? schItm.remainingQty1 : 0;
	                    schItm.actualQty = ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]) 
							? ($scope.actualWorkingDayMap[schItm[$scope.actualQuantityId]]).code : undefined ;						
						schItm.estimateToComplete = $scope.calcEstimateToComplete(schItm, 1)>= 0 ? $scope.calcEstimateToComplete(schItm, 1) : '';
						schItm.estimateAtCompletion = $scope.calcEstimateAtCompletion(schItm)>= 0 ? $scope.calcEstimateAtCompletion(schItm) : '';
					
					});
					break;
				case COST_CODE:
					for (var i = 0; i < $scope.scheduleItemDetails.length; i++) {
						$scope.scheduleItemDetails[i].actualQty = ($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]) ? ($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]).labourCost : 0;
						$scope.scheduleItemDetails[i].actualQty = ($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]) ? (($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]).materialCost + $scope.scheduleItemDetails[i].actualQty) : $scope.scheduleItemDetails[i].actualQty;
						$scope.scheduleItemDetails[i].actualQty = ($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]) ? (($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]).plantCost + $scope.scheduleItemDetails[i].actualQty) : $scope.scheduleItemDetails[i].actualQty;
						$scope.scheduleItemDetails[i].actualQty = ($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]) ? (($scope.actualWorkingDayMap[$scope.scheduleItemDetails[i][$scope.actualQuantityId]]).othersCost + $scope.scheduleItemDetails[i].actualQty) : $scope.scheduleItemDetails[i].actualQty;
					}
					break;
			}
		};

		$scope.scheduleTabs = [
			{title: 'Manpower', url: 'views/projectschedules/schedulemanpower.html', appCodeTemplateKey: 'PRJ_SCHEDULES_MANPOWER_VIEW'},
			{title: 'Plant & Equipment', url: 'views/projectschedules/scheduleplant.html', appCodeTemplateKey: 'PRJ_SCHEDULES_PLANTEQUIMENT_VIEW'},
			{title: 'Materials', url: 'views/projectschedules/schedulematerial.html', appCodeTemplateKey: 'PRJ_SCHEDULES_MATERIALS_VIEW'},
			{title: 'Cost Budget', url: 'views/projectschedules/schedulecost.html', appCodeTemplateKey: 'PRJ_SCHEDULES_COSTBUDGET_VIEW' }
		];

		$scope.currentTab = $scope.scheduleTabs[0].url;
		$scope.onClickTab = function (tab) {
			$scope.scheduleItemDetails = [];
			selectedItemList = [];
			selectedItemRow = {};
			selectedItemRows = [];
			$scope.data = [];
			$scope.cummulativeData = [];
			$scope.labels = [];
			$scope.colors = [];
			$scope.selectedRow = null;
			
			loadData(tab);

			if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
				GenericAlertService.alertMessage("Please select the project", "Info");
				return;
			}
		};

		let loadData = function(tab) {
			let url = (tab.url) ? tab.url : tab;
			if ($scope.scheduleTabs[0].url === url) {
				loadManpowerGridOptions();
				if ($scope.searchProject.projId) $scope.getScheduleManpower();
			} else if ($scope.scheduleTabs[1].url === url) {
				loadPlantGridOptions();
				if ($scope.searchProject.projId) $scope.getSchedulePlants();
			} else if ($scope.scheduleTabs[2].url === url) {
				loadMaterialGridOptions();						
				if ($scope.searchProject.projId) $scope.getScheduleMaterial();
			} else if ($scope.scheduleTabs[3].url === url) {
				loadCostGridOptions();
				if ($scope.searchProject.projId) $scope.getProjBudgetCostCodeDetails();
			}
			selectedTab = tab;
			$scope.currentTab = (tab.url) ? tab.url : tab;
		}

		$scope.isActiveTab = function (tabUrl) {
			return tabUrl == $scope.currentTab;
		},
		$scope.openTabs = function () {
			if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
				GenericAlertService.alertMessage("Please select the project", "Info");
				return;
			}
			$scope.onClickTab($scope.scheduleTabs[0]);
		},
		$scope.getScheduleManpower = function () {
			scheduleItemType = MANPOWER;
			selectedItemList = [];
			var getManpowerReq = {
				"status": 1,
				"projId": $scope.searchProject.projId
			};
			ProjectScheduleService.getProjBudgetManPowerDetails(getManpowerReq).then(function (data) {
				$scope.actualQuantityId = "empClassId";
				$scope.scheduleItemDetails = data.projManpowerTOs;
				$scope.calNonWorkingDays = data.calNonWorkingDays;
				$scope.actualWorkingDayMap = data.actualWorkingDayMap;
				$scope.empClassificationMap = data.empClassificationMap;
				$scope.calSplWorkingDays = data.calSplWorkingDays;
				$scope.projectCalendar = data.regularHolidays;
				$scope.reportProjectSetting = data.projReportsTo;
				$scope.dayWiseActualQuantity = data.dateWiseActualQuantity;
				addActualValues();
				if ($scope.gridOptions) 
					$scope.gridOptions.data = $scope.scheduleItemDetails;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Manpower budget details", "Error");
			});
		},
		$scope.getResourceCurve = function ($event, selectedSchItem) {
			$event.stopPropagation();
			var details = ScheduleResourceCurvesFactory.getResourceCurves();
			details.then(function (data) {
				$scope.searchCurve = data.selectedCurve;
				selectedSchItem.resourceCurveId = data.selectedCurve.id;
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while selecting resource curve", 'Error');
			})
		},
		$scope.getSchedulePlants = function () {
			scheduleItemType = PLANT;
			var req = {
				"status": 1,
				"projId": $scope.searchProject.projId
			};
			ProjectScheduleService.getProjBudgetPlantDetails(req).then(function (data) {
				$scope.actualQuantityId = "plantId";
				$scope.scheduleItemDetails = data.projectPlantsDtlTOs;
				$scope.calNonWorkingDays = data.calNonWorkingDays;
				$scope.actualWorkingDayMap = data.actualWorkingDayMap;
				$scope.plantClassificationMap = data.plantClassificationMap;
				$scope.calSplWorkingDays = data.calSplWorkingDays;
				$scope.projectCalendar = data.regularHolidays;
				$scope.reportProjectSetting = data.projReportsTo;
				$scope.dayWiseActualQuantity = data.dateWiseActualQuantity;
				addActualValues();

				if ($scope.gridOptions) 
					$scope.gridOptions.data = $scope.scheduleItemDetails;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting plant budget details", "Error");
			});
		},
		$scope.getScheduleMaterial = function () {
			scheduleItemType = MATERIAL;
			var req = {
				"status": 1,
				"projId": $scope.searchProject.projId
			};
			ProjectScheduleService.getProjBudgetMaterialDetails(req).then(function (data) {
				$scope.actualQuantityId = "materialClassId";
				$scope.scheduleItemDetails = data.projectMaterialDtlTOs;
				$scope.calNonWorkingDays = data.calNonWorkingDays;
				$scope.actualWorkingDayMap = data.actualWorkingDayMap;
				$scope.materialClassificationMap = data.materialClassificationMap;
				$scope.calSplWorkingDays = data.calSplWorkingDays;
				$scope.projectCalendar = data.regularHolidays;
				$scope.reportProjectSetting = data.projReportsTo;
				$scope.dayWiseActualQuantity = data.dateWiseActualQuantity;
				addActualValues();
				if ($scope.gridOptions) 
					$scope.gridOptions.data = $scope.scheduleItemDetails;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting material details", "Error");
			});
		},
		$scope.getProjBudgetCostCodeDetails = function () {
			scheduleItemType = COST_CODE;
			var req = {
				"status": 1,
				"projId": $scope.searchProject.projId
			};
			ProjectScheduleService.getProjBudgetCostCodeDetails(req).then(function (data) {
				$scope.actualQuantityId = "costCodeId";
				$scope.scheduleItemDetails = data.projScheduleCostCodeTOs;
				$scope.calNonWorkingDays = data.calNonWorkingDays;
				$scope.actualWorkingDayMap = data.actualWorkingDayMap;
				$scope.projCostItemMap = data.projCostItemMap;
				$scope.calSplWorkingDays = data.calSplWorkingDays;
				$scope.projectCalendar = data.regularHolidays;
				$scope.reportProjectSetting = data.projReportsTo;
				$scope.dayWiseActualQuantity = data.dateWiseActualQuantity;
				addActualValues();
				for(var cost of $scope.scheduleItemDetails){
					cost.rmqty1=((cost.revisedQty ? cost.revisedQty : cost.originalQty) - cost.actualQty) >= 0 ? ((cost.revisedQty ? cost.revisedQty : cost.originalQty) - cost.actualQty) : 0
					cost.rmqty=cost.rmqty1 >= 0 ? cost.rmqty1 : 0 ;
					cost.spentCost=($scope.calcEstimateToComplete(cost, 1) >= 0 ? $scope.calcEstimateToComplete(cost, 1) : 0);
					cost.progressOfWork=$scope.calcEstimateAtCompletion(cost, 1) >= 0 ? $scope.calcEstimateAtCompletion(cost, 1) : 0;
				}
				if ($scope.gridOptions) 
					$scope.gridOptions.data = $scope.scheduleItemDetails;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting cost details", "Error");
			});
		},
		$scope.getProjBudgetSOWDetails = function () {
			scheduleItemType = SOW;
			var req = {
				"status": 1,
				"projId": $scope.searchProject.projId
			};
			ProjectScheduleService.getProjBudgetSOWDetails(req).then(function (data) {
				$scope.actualQuantityId = "sowId";
				$scope.scheduleItemDetails = data.projSOWItemTOs;
				$scope.calNonWorkingDays = data.calNonWorkingDays;
				$scope.actualWorkingDayMap = data.actualWorkingDayMap;
				$scope.sowItemMap = data.sowItemMap;
				$scope.calSplWorkingDays = data.calSplWorkingDays;
				$scope.projectCalendar = data.regularHolidays;
				$scope.reportProjectSetting = data.projReportsTo;
				$scope.dayWiseActualQuantity = data.dateWiseActualQuantity;
				
				if ($scope.gridOptions) 
					$scope.gridOptions.data = $scope.scheduleItemDetails;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting progress measure details", "Error");
			});
		},
		$scope.getCurve = function () {
			resetCurve()
			if (selectedItemRows.length == 0) {
                GenericAlertService.alertMessage("Please select at least one resource", "Info");
                return;
            }
			let resourceIds = [];
			for (let i=0; i < selectedItemRows.length; i++)
				resourceIds.push(getValue(selectedItemRows[i], 'id'));
			ProjectScheduleService.getDateWiseForecastActualForResource({"projId": $scope.searchProject.projId, "scheduleActivityDataSetTOs": $scope.selectedScheduleActivityDataSetTOs, "resourceIds": resourceIds}).then(function(data) {
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
						} else if (data.scheduleActivityDataSetTOs[i].id > 0) {
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
						"reportProjectSetting": $scope.reportProjectSetting,
						"specialNonWorkingDays": $scope.calNonWorkingDays,
						"projectCalendar": $scope.projectCalendar,
						"specialWorkingDays": $scope.calSplWorkingDays,
						"resourceCurveTypeMap": $scope.resourceCurveTypeMap,
						"data": data
					};
					let resp = SchedulePlannedValueServiceV2.createPlannedCurve(req);
					$scope.data = resp.data;
					$scope.labels = resp.labels;
					$scope.series = resp.series;
					$scope.datasetOverride = resp.datasetOverride;
					$scope.options = resp.options;
					if (actualSelected && actualCounter == 0)
						GenericAlertService.alertMessage("No actual values exist", 'Info');
					if (datasetSelected && datasetCounter == 0)
						GenericAlertService.alertMessage("No planned values exist", 'Info');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting resource assignment data", 'Error');
			});
		},
		$scope.saveResourceCurveMapping = function() {
			let req = [];
			angular.forEach($scope.gridOptions.data, function(row, key){
				switch (scheduleItemType) {
				case MANPOWER:
					req.push({curveId: row.resourceCurveId, resourceReferenceId: row.id, resourceReferenceType: "POT_EMPLOYEE"});
					break;
				case MATERIAL:
					req.push({curveId: row.resourceCurveId, resourceReferenceId: row.id, resourceReferenceType: "POT_MATERIAL"});
					break;
				case PLANT:
					req.push({curveId: row.resourceCurveId, resourceReferenceId: row.id, resourceReferenceType: "POT_PLANT"});
					break;
				case COST_CODE:
					console.log(row)
					req.push({curveId: row.resourceCurveId, resourceReferenceId: row.costCodeId, resourceReferenceType: "POT_COST"});
					break;
				}
			})
			ProjectScheduleService.saveResourceCurveMapping({resourceCurveMappingTOs: req}).then(function (data) {
				GenericAlertService.alertMessage("Saved successfully", 'Info');
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting saving", "Error");
			});
		};
		
		function resetCurve(){
			$scope.data = [];
			$scope.labels = [];
			$scope.series = [];
			$scope.datasetOverride = [];
			$scope.options = [];
			$scope.actualQuatityItemsOfSelectedItemRow = [];
		};
		
		function getValue(item, key) {
			if (item.materialClassTO != null) {
				if (key == 'id')
					return item.materialClassTO.id;
				if (key == 'uom')
					return item.measureName;
			} else if (item.empClassTO != null) {
				if (key == 'id')
					return item.empClassTO.id;
				if (key == 'uom')
					return 'Hourly';
			} else if (item.plantClassTO != null) {
				if (key == 'id')
					return item.plantClassTO.id;
				if (key == 'uom')
					return item.plantClassTO.measureUnitTO.name;
			} else if (item.projCostItemTO != null) {
				if (key == 'id')
					return item.projCostItemTO.id;
				if (key == 'uom')
					return 'Hourly';
			}
		};

		var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/scheduleshelp/resourceschedulehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	}]).filter('slice', function () {
		return function (arr, start, end) {
			return (arr || []).slice(start, end);
		}
	}).filter('projCostStatementFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o.projCostStmtChildTOs && o.projCostStmtChildTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					newObj.push(o);
					if (o.expand == true) {
						recursive(o.projCostStmtChildTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {
						o.leaf = true;
						newObj.push(o);
					} else {
						obj.splice(obj.indexOf(o), 1);
						o.leaf = false;
					}
					return false;
				}
			});
		}
		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	}).filter('projCostFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o != undefined && o.projCostCodeItemTOs && o.projCostCodeItemTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					newObj.push(o);
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					if (o.expand == true) {
						recursive(o.projCostCodeItemTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {
						o.leaf = true;
					} else {
						o.leaf = false;
					}
					newObj.push(o);
					return false;
				}
			});
		}
		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	}).filter('sowFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o.childSOWItemTOs && o.childSOWItemTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					newObj.push(o);
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					if (o.expand == true) {
						recursive(o.childSOWItemTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {
						o.leaf = true;
					} else {
						o.leaf = false;
					}
					newObj.push(o);
					return false;
				}
			});
		}

		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	}).filter('sowProgMeasureFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o.childProjProgressTOs && o.childProjProgressTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					newObj.push(o);
					if (o.expand == true) {
						recursive(o.childProjProgressTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {
						o.leaf = true;
						newObj.push(o);
					} else {
						obj.splice(obj.indexOf(o), 1);
						o.leaf = false;
					}
					return false;
				}
			});
		}

		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	});
