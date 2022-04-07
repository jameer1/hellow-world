'use strict';
app.factory('ProjectPlantClassificationFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjPlantClassService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjPlantClassService) {
	var plantDetailsPopUp;
	var service = {};
	service.plantDetailsPopUp = function(projectPlantDetails) {
		var deferred = $q.defer();
		plantDetailsPopUp = ngDialog.open({
			template : 'views/common/projectplantclassificationlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {

				$scope.projectPlantDetails = projectPlantDetails;
				$scope.plantDetailsPopUp = function(projectPlantTO) {
					var returnPlantDetailsTO = {
						"projectPlantTO" : projectPlantTO
					};
					deferred.resolve(returnPlantDetailsTO);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	},

	service.getProjectPlantDetails = function(req) {
		var deferred = $q.defer();
		var projectPlantDetailsPromise = ProjPlantClassService.getProjPlantClasses(req);
		projectPlantDetailsPromise.then(function(data) {
			var projectPlantDetails = [];
			projectPlantDetails = data.projPlantClassTOs;
			
			var projectPlantDetailsPopup = service.plantDetailsPopUp(projectPlantDetails);
			projectPlantDetailsPopup.then(function(data) {
				var returnPopObj = {
					"projectPlantTO" : data.projectPlantTO
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
