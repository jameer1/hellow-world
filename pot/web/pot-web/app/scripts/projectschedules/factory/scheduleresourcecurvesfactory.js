'use strict';

app.factory('ScheduleResourceCurvesFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ResourceCurveService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ResourceCurveService) {
	var popUp;
	var service = {};

	service.getResourceCurves = function() {
		var deferred = $q.defer();
		popUp = ngDialog.open({
			template : 'views/projectschedules/resourcecurveselect.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			controller : [ '$scope', function($scope) {
				$scope.curves = [];
				$scope.init = function() {
					var req = {
						"status" : 1,
						"clientId" : null
					};
					ResourceCurveService.getResourceCurves(req).then(function(data) {
						$scope.curves = data.projResourceCurveTOs;
					}, function(error) {
						GenericAlertService.alertMessage('An error occurred while fetching Resource Curves', "Error");
					});
				}
				$scope.init();
				$scope.selectCurve = function(curve) {
					var returnPopObj = {
						"selectedCurve" : curve
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};

	return service;

}]);
