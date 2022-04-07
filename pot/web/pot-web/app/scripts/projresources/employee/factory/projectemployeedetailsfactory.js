'use strict';
app.factory('ProjectEmployeeClassificationFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjEmpClassService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjEmpClassService) {
	var empDetailsPopUp;
	var service = {};
	service.empDetailsPopUp = function(projectEmpDetails) {
		var deferred = $q.defer();
		empDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/empservicehistory/projectemployeelist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {

				$scope.projectEmpDetails = projectEmpDetails;
				$scope.plantDetailsPopUp = function(projectEmployeeTO) {
					var returnEmpDetailsTO = {
						"projectEmployeeTO" : projectEmployeeTO
					};
					deferred.resolve(returnEmpDetailsTO);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	},

	service.getProjectEmpDetails = function(req) {
		var deferred = $q.defer();
		var projectEmpDetailsPromise = ProjEmpClassService.getProjEmpClasses(req);
		projectEmpDetailsPromise.then(function(data) {
			var projectEmpDetails = [];
			projectEmpDetails = data.projEmpClassTOs;
			
			var projectEmpDetailsPopup = service.empDetailsPopUp(projectEmpDetails);
			projectEmpDetailsPopup.then(function(data) {
				var returnPopObj = {
					"projectEmployeeTO" : data.projectEmployeeTO
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Project Employee Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]);
