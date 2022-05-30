'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("periodicalrecords", {
		url : '/periodicalrecords',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/periodicalandschedulerecords/periodicalrecords.html',
				controller : 'AssetPeriodicalRecordsController'
			}
		}
	})
}]).controller("AssetPeriodicalRecordsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService","EmpRegisterService", "RepairsNonScheduleFactory", "PeriodicalScheduleFactory", "AssetRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService,EmpRegisterService, RepairsNonScheduleFactory, PeriodicalScheduleFactory,AssetRegisterService,stylesService, ngGridService) {
	var editPeriodicalRecords= [];
	$scope.stylesSvc = stylesService;
	$scope.nonSchedules = [];
	$scope.editButtonDisable =false;
	
	$scope.periodicalRecordsSelect = function(repairs) {
		if (repairs.selected) {
			editPeriodicalRecords.push(repairs);
		} else {
			editPeriodicalRecords.pop(repairs);
		}
	},
	$scope.getPlantPeriodicalRecordsRecordsOnLoad=function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var periodicalRecordsGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getPeriodicalRecordsOnLoad(periodicalRecordsGetReq).then(function(data) {
			$scope.fixedAssetRepairsRecordsDtlTO = data.fixedAssetRepairsRecordsDtlTOs;
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Plant Periodical details", "Error");
		});
	},
	$scope.getPeriodicalRecords=function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var periodicalRecordsGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getPeriodicalRecordsOnLoad(periodicalRecordsGetReq).then(function(data) {
			$scope.PeriodicalScheduleMaintenanceDtlTO = data.periodicalScheduleMaintenanceDtlTOs;// data.PeriodicalScheduleMaintenanceDtlTOs;
			$scope.gridOptions.data = angular.copy(data.periodicalScheduleMaintenanceDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Periodical details", "Error");
		});
	},
	$scope.deleteperiodicalRecord = function () {
		if (editPeriodicalRecords.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.editPeriodicalRecords = [];
		} else {
			angular.forEach(editPeriodicalRecords, function (value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var periodicalDeactiveReq = {
				"periodicalScheduleMaintenanceIds": deactivateIds,
				"status": 2,
				"fixedAssetid": $scope.fixedAssetid
			}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				AssetRegisterService.deletePeriodicalRecords(periodicalDeactiveReq).then(function (data) {
					GenericAlertService.alertMessageModal('Periodical Record(s) deactivated successfully', 'Info');
					$scope.getPeriodicalRecords();
					editPeriodicalRecords = [];
				});
				angular.forEach(editPeriodicalRecords, function (value, key) {
					$scope.editPeriodicalRecords.splice($scope.editPeriodicalRecords.indexOf(value), 1);
					editPeriodicalRecords = [];
					$scope.deactivateIds = [];
				}, function (error) {
					GenericAlertService.alertMessage('Periodical  Record(s) is/are  failed to deactivate', "Error");
				});
			}, function (data) {
				angular.forEach(editPeriodicalRecords, function (value) {
					value.selected = false;
				})
			})

		}
	},
	$scope.addPeriodicalRecords = function (actionType) {

		angular.forEach(editPeriodicalRecords, function (value) {
			value.selected = false;
		});
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}

		if (actionType == 'Edit' && editPeriodicalRecords.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
		var periodicalRecordpopup = PeriodicalScheduleFactory.periodicalsRecordPopUpOnLoad(actionType, editPeriodicalRecords);
		periodicalRecordpopup.then(function (data) {
			$scope.getPeriodicalRecords();
			editPeriodicalRecords = [];

		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching periodical Record details", 'Error');
		});

	}
	$scope.downloadRecordPeriodicalFile = function (periodicalRecordId,file_name,type) {
		console.log(periodicalRecordId);
		if(type == 'Plan'){
		//AssetRegisterService.downloadRecordPlanPeriodicalFile(periodicalRecordId);
		//change Service by mamatha
		let req = {
					"recordId" : periodicalRecordId,
					"category" : "PlanPeriodicalRecords",
					"fileName" : file_name
				}
				EmpRegisterService.downloadRegisterDocs(req);
		}
		else
			{
			//AssetRegisterService.downloadRecordActualPeriodicalFile(periodicalRecordId);
				let request = {
					"recordId" : periodicalRecordId,
					"category" : "ActualPeriodicalRecords",
					"fileName" : file_name
				}
				EmpRegisterService.downloadRegisterDocs(request);
			}
		
	}
	 var linkCellTemplate = '<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.periodicalRecordsSelect(row.entity)">';
      	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    {  name: 'select',width:'4%',cellClass:'justify-center',headerCellClass:'justify-center',cellTemplate: linkCellTemplate,displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
						{ name: 'dueDate', displayName: "Plan-Due Date", headerTooltip : "Plan-Due Date"},
						{ field: 'maintenanceCategory', displayName: "plan-Maintenance Category", headerTooltip: "plan-Maintenance Category", groupingShowAggregationMenu: false},
						{ field: 'maintenanceSubCategory', displayName: "plan-Maintenance Sub Category", headerTooltip: "plan-Maintenance Sub Category", groupingShowAggregationMenu: false},
						{ field: 'planResponsibleSupervisor', displayName: "plan-Responsible Supervisor", headerTooltip: "plan-Responsible Supervisor", groupingShowAggregationMenu: false},
						{ name: 'planRecordDocumentFileName',cellClass:"justify-center", displayName: "plan-Maintenance List(Upload Documents)", headerTooltip: "plan-Maintenance List(Upload Documents)", groupingShowAggregationMenu: false,
						cellTemplate:'<div ng-click="grid.appScope.downloadRecordPeriodicalFile(row.entity.id, row.entity.planRecordDocumentFileName,\'Plan\')" ng-if="row.entity.planRecordDocumentFileName" class="fa fa-download"></div>'},
						{ field: 'currentStatus', displayName: "plan-Current Status", headerTooltip: "plan-Current Status", groupingShowAggregationMenu: false},
						{ field: 'startDate', displayName: "Actual-Start Date", headerTooltip: "Actual-Start Date", groupingShowAggregationMenu: false},
						{ field: 'finishDate', displayName: "Actual-Finish Date", headerTooltip: "Actual-Finish Date", groupingShowAggregationMenu: false},
						{ field: 'actualResponsibleSupervisor', displayName: "Actual-Responsible Supervisor", headerTooltip: "Actual-Responsible Supervisor", groupingShowAggregationMenu: false},
						{ field: 'purchaseOrderNumber', displayName: "Actual-Purchase Order Number(Sub Contractor Services)", headerTooltip: "Actual-Purchase Order Number(Sub Contractor Services)", groupingShowAggregationMenu: false},
						{ field: 'materialsConsumptionRecords', displayName: "Actual-Materials ConsumptionRecords(ProjectDocket #)", headerTooltip: "Actual-Materials ConsumptionRecords(ProjectDocket #)", groupingShowAggregationMenu: false},
						{ name: 'actualRecordsDocumentFileName',cellClass:"justify-center",headerCellClass:"justify-center",displayName: "Actual-Completion Records(Upload Documents)", headerTooltip: "Actual-Completion Records(Upload Documents)", groupingShowAggregationMenu: false,
						cellTemplate:'<div ng-click="grid.appScope.downloadRecordPeriodicalFile(row.entity.id, row.entity.actualRecordsDocumentFileName,\'Actual\')" ng-if="row.entity.actualRecordsDocumentFileName" class="fa fa-download"></div>'},
						];
					let data = [];
					$scope.getPeriodicalRecords();
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovable Assets_Periodical And Schedule Maintenance Records");
				}
				});
	
}]);