'use strict';
app.factory('PlantMasterDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantRegisterService) {
	var plantDetailsPopUp;
	var service = {};
	service.plantDetails = function(plantData) {
		var deferred = $q.defer();
		plantDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projplantreg/plantnewrequest/plantregdetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
			controller : [ '$scope', function($scope) {
				$scope.plantData = plantData;
				var selectedPlants = [];
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedPlants.push(tab);
					} else {
						selectedPlants.splice(selectedPlants.indexOf(tab), 1);
					}
				}
				$scope.save = function() {
					var returnPopObj = {
							"plantRegisterDtlTOs" : angular.copy(selectedPlants)
						};
						$scope.closeThisDialog();
						deferred.resolve(returnPopObj);
						
				}
			} ]

		});
		return deferred.promise;
	},

	service.getPlantMasterDetails = function(req) {
		var deferred = $q.defer();
		var projectPlantDetailsPromise = PlantRegisterService.getPlantRegistersOnLoad(req);
		projectPlantDetailsPromise.then(function(data) {
			var plantData = [];
			plantData = data.plantRegisterDtlTOs;
			var projectPlantDetailsPopup = service.plantDetails(plantData);
			projectPlantDetailsPopup.then(function(data) {
				var returnPopObj = {
					"plantMasterTO" : data.plantRegisterDtlTOs
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Project Plant Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Project Plant Details", "Error");
		});
		return deferred.promise;
	}
	return service;
}]);
