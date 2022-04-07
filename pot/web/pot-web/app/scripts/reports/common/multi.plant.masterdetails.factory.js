'use strict';
app.factory('MultiProjPlantMasterDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantReportService", function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantReportService) {
	var plantDetailsPopUp;
	var service = {};
	service.plantDetails = function (plantData) {
		var deferred = $q.defer();
		plantDetailsPopUp = ngDialog.open({
			template: 'views/reports/common/multiplantregdetailspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0-5',
			controller: ['$scope', function ($scope) {
				$scope.plantData = plantData;

				var selectedPlants = [];
				var selectedPlantIds = [];
				var plantName = '';
				$scope.selectedPlants = function (plant) {
					if (plant.select) {
						selectedPlants.push(plant);
					} else {
						selectedPlants.pop(plant);
					}
				};
				$scope.selectAllPlants = function () {
					if ($scope.selectAll) {
						angular.forEach($scope.plantData, function (value, key) {
							value.select = true;
							selectedPlants.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.plantData, function (value, key) {
							value.select = false;
						});
						selectedPlantIds = [];
					}
				};
				$scope.addPlants = function () {
					angular.forEach(selectedPlants, function (value, key) {
						selectedPlantIds.push(value.id);
						plantName = plantName + value.assertId + ",";
					});

					var returnPopObj = {
						"selectedPlants": {
							"selectedPlantIds": selectedPlantIds,
							"plantName": plantName.slice(0, -1),
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				};
			}]

		});
		return deferred.promise;
	};

	service.getPlantMasterDetails = function (req) {
		var deferred = $q.defer();
		var projectPlantDetailsPromise = PlantReportService.getPlantsInUserProjects(req);
		projectPlantDetailsPromise.then(function (data) {
			var plantData = [];
			plantData = data.plantRegisterDtlTOs;
			var projectPlantDetailsPopup = service.plantDetails(plantData);
			projectPlantDetailsPopup.then(function (data) {
				var returnPopObj = {
					"selectedPlants": data.selectedPlants
				};
				deferred.resolve(returnPopObj);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Project Plant Details", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Project Plant Details", "Error");
		});
		return deferred.promise;
	}
	return service;
}]);
