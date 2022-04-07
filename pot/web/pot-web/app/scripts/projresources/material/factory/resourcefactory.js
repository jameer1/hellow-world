'use strict';
app.factory('ResourceFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialClassService", "GenericAlertService", "PlantClassService", function(ngDialog, $q, $filter, $timeout, $rootScope, MaterialClassService, GenericAlertService, PlantClassService) {
	var resourcePopUp;
	var service = {};
	service.resourceDetails = function(resourceReq) {
		var deferred = $q.defer();
		var plantDetailsPromise = MaterialClassService.getMaterialSubGroups(resourceReq)
		plantDetailsPromise.then(function(data) {
			var resourcePopUp = service.resourceDetailPopUp(data.materialSubGroupTOs);
			resourcePopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Company List Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Company List Details", "Error");
		});
		return deferred.promise;
	}

	service.resourceDetailPopUp = function(materialSubGroupTOs) {
		var deferred = $q.defer();
		resourcePopUp = ngDialog.open({
			template : 'views/projresources/projmaterialreg/reqmaterialtransfer/resourcepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.materialclassifications = materialSubGroupTOs;
				$scope.selectRecord = function(record) {
					var returnRecord = {
						"selectedRecord" : record
					};
					deferred.resolve(returnRecord);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	}

	return service;
}]);