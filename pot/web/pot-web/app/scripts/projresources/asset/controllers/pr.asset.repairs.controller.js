'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetrepairsschedules", {
		url : '/assetrepairsschedules',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/repairsandnonschedule/repairsandnonschedule.html',
				controller : 'AssetRepairsController'
			}
		}
	})
}]).controller("AssetRepairsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "RepairsNonScheduleFactory", "AssetRegisterService", "EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,GenericAlertService,RepairsNonScheduleFactory,AssetRegisterService,EmpRegisterService, stylesService, ngGridService) {
	var editRepairsSchedules= [];
	$scope.nonSchedules = [];
	$scope.editButtonDisable =false;
	$scope.stylesSvc = stylesService;
	$scope.repairsrowSelect = function(repairs) {
		if (repairs.selected) {
			editRepairsSchedules.push(repairs);
		} else {
			editRepairsSchedules.pop(repairs);
		}
	},
	$scope.getPlantRepairRecordsOnLoad=function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var carParkingTollGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getRepairRecordsOnLoad(carParkingTollGetReq).then(function(data) {
			$scope.fixedAssetRepairsRecordsDtlTO = data.fixedAssetRepairsRecordsDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.fixedAssetRepairsRecordsDtlTO);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching repair details", "Error");
		});
	},
	$scope.editRepairRecord = function (actionType) {

		angular.forEach(editRepairsSchedules, function (value) {
			value.selected = false;
		});
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}

		if (actionType == 'Edit' && editRepairsSchedules.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
		var repairRecordpopup = RepairsNonScheduleFactory.repairRecordPopUpOnLoad(actionType, editRepairsSchedules);
		repairRecordpopup.then(function (data) {
			$scope.fixedAssetRepairsRecordsDtlTO = data.fixedAssetRepairsRecordsDtlTOs;
			editRepairsSchedules = [];
			$scope.getPlantRepairRecordsOnLoad();

		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching repair details", 'Error');
		});

	}
	$scope.downloadRecordRepairFile = function (repairRecordId,file_name) {
		console.log("downloadRecordRepairFile function");
		//AssetRegisterService.downloadRecordRepairFile(repairRecordId); // this line of code is to download document from AWS
		let req = {
			"recordId" : repairRecordId,
			"category" : "Repairs and Non Schedule maintenance Records",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadRecordRepairFile");
	},
	$scope.deleteRepairRecord = function () {
		if (editRepairsSchedules.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.editRepairsSchedules = [];
		} else {
			angular.forEach(editRepairsSchedules, function (value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var repairDeactiveReq = {
				"repairRecordsIds": deactivateIds,
				"status": 2,
				"fixedAssetid": $scope.fixedAssetid
			}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				AssetRegisterService.deleteRepairRecords(repairDeactiveReq).then(function (data) {
					GenericAlertService.alertMessageModal('Repair Record(s) deactivated successfully', 'Info');
					$scope.getPlantRepairRecordsOnLoad();
					editRepairsSchedules = [];
				});
				var succMsg = GenericAlertService.alertMessageModal('Repair Records deactivation is' + data.message, data.status);
				angular.forEach(selectedPurchaseRecords, function (value, key) {
					$scope.editRepairsSchedules.splice($scope.editRepairsSchedules.indexOf(value), 1);
					editRepairsSchedules = [];
					$scope.deactivateIds = [];
				}, function (error) {
					GenericAlertService.alertMessage('Repair Record(s) is/are  failed to deactivate', "Error");
				});
			}, function (data) {
				angular.forEach(editRepairsSchedules, function (value) {
					value.selected = false;
				})
			})

		}
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.repairsrowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false },
						{ field: 'startDate', displayName: "Start Date", headerTooltip: "Start Date", groupingShowAggregationMenu: false},
						{ field: 'finishDate', displayName: "Finish Date", headerTooltip: "Finish Date", groupingShowAggregationMenu: false},
						{ field: 'responsibleSupervisor', displayName: "Responsible Supervisor", headerTooltip: "Responsible Supervisor", groupingShowAggregationMenu: false},
						{ field: 'maintenanceCategory', displayName: "Maintenance Category", headerTooltip: "Maintenance Category",groupingShowAggregationMenu: false},
						{ field: 'maintenanceSubCategory', displayName: "Maintenance Sub Category", headerTooltip: "Maintenance Sub Category",groupingShowAggregationMenu: false},
						{ field: 'purchaseOrderNumber', displayName: "Purchase Order Number (Sub Contractor Services)", headerTooltip: "Purchase Order Number (Sub Contractor Services)",groupingShowAggregationMenu: false},
						{ field: 'materialsConsumptionRecords', displayName: "Materials Consumption Records (Project Docket #)", headerTooltip: "Materials Consumption Records (Project Docket #)", groupingShowAggregationMenu: false},
						{ name: 'Completion Records', displayName: "Completion Records (Upload & View)", headerTooltip: "Completion Records (Upload & View)",cellClass: 'justify-center', headerCellClass:"justify-center",groupingShowAggregationMenu: false,
						cellTemplate:'<div ng-click="grid.appScope.downloadRecordRepairFile(row.entity.id, row.entity.repairsRecordsDetailsFileName)" ng-if="row.entity.repairsRecordsDetailsFileName" class="fa fa-download">{{repairs.repairsRecordsDetailsFileName}}</div>' },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_NonSchedule");
					$scope.getPlantRepairRecordsOnLoad();
				}
			});
}]);