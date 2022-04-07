'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantservicehistoryrepair", {
		url: '/plantservicehistoryrepair',
		data: {
			roles: []
		},
		views: {
			'current@': {
				templateUrl: 'views/projresources/projplantreg/plantservicehistoryrepairs/plantregservicehistoryrepair.html',
				controller: 'PlantServiceHistoryRepairController'
			}
		}
	})
}]).controller("PlantServiceHistoryRepairController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PlantServiceHistoryRepairFactory", "PlantRegisterService", "GenericAlertService", function ($rootScope, $scope, $q, $state, ngDialog, PlantServiceHistoryRepairFactory, PlantRegisterService, GenericAlertService) {
	$scope.plantServiceRepairTable = true;
	$scope.plantRepairsTOs = [];
	$scope.getPlantServiceHistoryRepairs = function () {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			GenericAlertService.alertMessage("Please select the project assigned plant", "Warning");
			return;
		}
		var req = {
			"status": 1,
			"projId": $rootScope.selectedPlantData.projId,
			"plantId": $rootScope.selectedPlantData.id
		};
		PlantRegisterService.plantRepairsOnLoad(req).then(function (data) {
			$scope.plantRepairsTOs = data.plantRepairsTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting service history repairs", "Error");
		});
		$scope.buttonShow = true;
	};

	$scope.getPlantServiceHistoryRepairs();

	$scope.serviceHistoryRepairRowSelect = function (servicehistoryrepair) {
		if (servicehistoryrepair.selected) {
			editServiceHistoryRepair.push(servicehistoryrepair);
		} else {
			editServiceHistoryRepair.pop(servicehistoryrepair);
		}
	};

	$scope.addServiceHistoryRepair = function (actionType) {
		if ($rootScope.selectedPlantData.projId == undefined || $rootScope.selectedPlantData.projId == null) {
			GenericAlertService.alertMessage("Please select the project assigned plant", "Warning");
			return;
		}
		var resultData = PlantServiceHistoryRepairFactory.addServiceRepairs();
		resultData.then(function (data) {
			$scope.plantServiceRepairTable = false;
			$scope.plantRepairsTOs = data.plantRepairsTOs;
			$scope.plantServiceRepairTable = true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching Service History Repair Details", 'Error');
		});
	};
	$scope.deleteServiceHistoryRepair = function () {
		if (editServiceHistoryRepair.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.plantRepairsTOs = [];
		} else {
			angular.forEach(editServiceHistoryRepair, function (value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"plantIds": deleteIds,
				"status": 2
			};
			PlantRegisterService.deletePlantRepairRecords(req).then(function (data) {
			});
			angular.forEach(editServiceHistoryRepair, function (value, key) {
				GenericAlertService.alertMessage('Plant ServiceHistoryRepair Detail(s) are  Deactivated successfully', 'Info');
				$scope.plantRepairsTOs.splice($scope.plantRepairsTOs.indexOf(value), 1);
			}, function (error) {
				GenericAlertService.alertMessage('Plant ServiceHistoryRepair Detail(s) is/are failed to Deactivate', "Error");
			});
			editServiceHistoryRepair = [];
			$scope.deleteIds = [];
		}
	};

	$scope.showServiceHistoryRepair = function (remarks) {
		GenericAlertService.comments(remarks);
	};

	$scope.$on("resetRecordsofRepair", function () {
		$scope.plantRepairsTOs = [];
	});
}]);