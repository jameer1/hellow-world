'use strict';
app.factory('PlantDetailsListFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PlantClassService", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, PlantClassService, GenericAlertService, PlantRegisterService) {
	var plantDetailsPopUp;
	var service = {};
	service.plantDetailsPopUp = function(projPlantTypeDetails) {
		var deferred = $q.defer();
		plantDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projplantreg/plantdetails/plantregdetailslist.html',
			closeByDocument : false,
			showClose : false,
			className:'ngdialog ngdialog-theme-plain ng-dialogueCustom4',
			controller : [ '$scope', function($scope) {
				$scope.projPlantTypeDetails = projPlantTypeDetails;
				$scope.plantDetailsPopUp = function(plantClassTO) {
					var returnPlantClassTO = {
						"plantClassTO" : plantClassTO
					};
					deferred.resolve(returnPlantClassTO);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}, service.getPlantClassDetails = function(req) {
		var deferred = $q.defer();
		var projectPlantDetailsPromise = PlantClassService.getPlantClasses(req);
		projectPlantDetailsPromise.then(function(data) {
			var plantClassDetails = [];
			plantClassDetails = data.plantClassTOs;
			
			var plantsDetailsPopup = service.plantDetailsPopUp(plantClassDetails);
			plantsDetailsPopup.then(function(data) {
				var returnPopObj = {
					"plantClassTO" : data.plantClassTO
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Plant Class Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Plant Class Details", "Error");
		});
		return deferred.promise;
	}
	return service;
}]);
