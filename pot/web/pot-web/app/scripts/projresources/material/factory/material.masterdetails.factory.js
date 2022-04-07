'use strict';
app.factory('MaterialMasterDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialRegisterService", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, MaterialRegisterService,GenericAlertService, PlantRegisterService) {
	var materialListPopUp;
	var service = {};
	service.materialList = function(materialData) {
		var deferred = $q.defer();
		materialListPopUp = ngDialog.open({
			template : 'views/projresources/projmaterialreg/reqmaterialtransfer/resourcepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.materialData = materialData;
				var selectedMaterials = [];
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedMaterials.push(tab);
					} else {
						selectedMaterials.splice(selectedPlants.indexOf(tab), 1);
					}
				}
				$scope.save = function() {
					var returnPopObj = {
							"materialRegisterDtlTOs" : angular.copy(selectedMaterials)
						};
						$scope.closeThisDialog();
						deferred.resolve(returnPopObj);
				}
			} ]

		});
		return deferred.promise;
	},

	service.getMaterialMasterDetails = function(req) {
		var deferred = $q.defer();
		var materialDetailsPromise = MaterialRegisterService.getPlantRegistersOnLoad(req);
		materialDetailsPromise.then(function(data) {
			var materialData = [];
			materialData = data.plantRegisterDtlTOs;
			var materialDetailsPopup = service.materialList(materialData);
			materialDetailsPopup.then(function(data) {
				var returnPopObj = {
					"materialMasterTO" : data.materialRegisterDtlTOs
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
