'use strict';
app.factory('PlantClassPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantClassService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantClassService) {
	var plantClassPopUp;
	var service = {};
	service.getPlants = function(plantReq) {
		var deferred = $q.defer();
		var plantDetailsPromise =  PlantClassService.getPlantClasses(plantReq)
		plantDetailsPromise.then(function(data) {
			var plantClassPopUp = service.plantDetailListPopUp(data.plantClassTOs);
			plantClassPopUp.then(function(data) {
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
	
	service.plantDetailListPopUp = function(plantClassTOs) {
		var deferred = $q.defer();
		plantClassPopUp = ngDialog.open({
			template : 'views/finance/financemain/plantclasspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose: false,
			controller : [ '$scope', function($scope) {
				$scope.plantClassTOs = plantClassTOs;
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