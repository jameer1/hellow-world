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
}]).controller("AssetPeriodicalRecordsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "RepairsNonScheduleFactory", "PeriodicalScheduleFactory", "AssetRegisterService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, RepairsNonScheduleFactory, PeriodicalScheduleFactory,AssetRegisterService) {
	var editPeriodicalRecords= [];
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
		/*if(type == "Plan"){
		AssetRegisterService.downloadRecordPlanPeriodicalFile(periodicalRecordId);
		}
		else
			{
			AssetRegisterService.downloadRecordActualPeriodicalFile(periodicalRecordId);
			}*/
		
	}
	
}]);