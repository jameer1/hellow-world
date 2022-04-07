'use strict';

app.factory('AssignBaseLineFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjectScheduleService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjectScheduleService, GenericAlertService) {
	var service = {};
	service.getAssignBaselineDetails = function(scheduleItemType, projId) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId,
			"scheduleItemType" : scheduleItemType
		};
		ProjectScheduleService.getProjScheduleBaseLines(req).then(function(data) {
			var resultData = service.openPopup(scheduleItemType, data.projScheduleBaseLineTOs, projId);
			resultData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while assigning baselines", "Error");
		});
		return deferred.promise;
	}, service.openPopup = function(scheduleItemType, projScheduleBaseLineTOs, projId) {
		var deferred = $q.defer();
		var popUp = ngDialog.open({
			template : 'views/projectschedules/assignbaselinepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			controller : [ '$scope', function($scope) {
				$scope.projScheduleBaseLineTOs = projScheduleBaseLineTOs;
				$scope.title = scheduleItemType;
				$scope.assignBaseLine = function(selectedBaseLine) {
					var req = {
						"status" : 1,
						"projId" : projId,
						"baseLineId" : selectedBaseLine.id,
						"scheduleItemType" : scheduleItemType 
					};
					if (scheduleItemType == 'E') {
						ProjectScheduleService.getProjScheduleManPowerDetails(req).then(function(data) {
							var returnObj = {
								"selectedBaseLine" : selectedBaseLine,
								"scheduleItemDetails" : data.projScheduleManPowerTOs
							};
							deferred.resolve(returnObj);
							$scope.closeThisDialog();
						});
					} else if (scheduleItemType == 'P') {
						ProjectScheduleService.getProjSchedulePlantDetails(req).then(function(data) {
							var returnObj = {
								"selectedBaseLine" : selectedBaseLine,
								"scheduleItemDetails" : data.projSchedulePlantTOs
							};
							deferred.resolve(returnObj);
							$scope.closeThisDialog();
						});
					}else if (scheduleItemType == 'M') {
						ProjectScheduleService.getProjScheduleMaterialDetails(req).then(function(data) {
							var returnObj = {
								"selectedBaseLine" : selectedBaseLine,
								"scheduleItemDetails" : data.projScheduleMaterialTOs
							};
							deferred.resolve(returnObj);
							$scope.closeThisDialog();
						});
					}else if (scheduleItemType == 'C') {
						ProjectScheduleService.getProjScheduleCostCodeDetails(req).then(function(data) {
							var returnObj = {
								"selectedBaseLine" : selectedBaseLine,
								"scheduleItemDetails" : data.projScheduleCostCodeTOs
							};
							deferred.resolve(returnObj);
							$scope.closeThisDialog();
						});
					}else if (scheduleItemType == 'S') {
						ProjectScheduleService.getProjScheduleSOWDetails(req).then(function(data) {
							var returnObj = {
								"selectedBaseLine" : selectedBaseLine,
								"scheduleItemDetails" : data.projScheduleSOWTOs
							};
							deferred.resolve(returnObj);
							$scope.closeThisDialog();
						});
					}
				}

			} ]
		});
		return deferred.promise;
	};

	return service;

}]);
