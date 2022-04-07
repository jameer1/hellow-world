'use strict';
app.factory('CrewPopupTimeSheetFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjectSettingsService, GenericAlertService) {
	var crewServicePopup = [];
	var crewService = {};
	crewService.getCrewList = function(crewId, req) {
		var deferred = $q.defer();
		var prjcrewListPromise = ProjectSettingsService.getProjCrewLists(req);
		prjcrewListPromise.then(function(data) {
			var projCrewTOs = [];
			projCrewTOs = data.projCrewTOs;
			var crewSerivcePopup = crewService.openCrewPopup(crewId, projCrewTOs);
			crewSerivcePopup.then(function(data) {
				var returnPopObj = {
					"projCrewTO" : data.projCrewTO
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Crew List Details", "Error");
		});
		return deferred.promise;
	}, crewService.openCrewPopup = function(crewId, projCrewTOs) {
		var deferred = $q.defer();
		crewServicePopup = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/crewpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.projCrewTOs = projCrewTOs;
				/*$scope.existingCrewId = crewId;*/
				$scope.crewPopUp = function(crewData) {
					var returnPopObj = {
						"projCrewTO" : crewData
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}
	return crewService;
}]);