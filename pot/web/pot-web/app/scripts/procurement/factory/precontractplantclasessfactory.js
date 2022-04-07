'use strict';

app.factory('PreContractProjPlantClassFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PlantClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PlantClassService, GenericAlertService) {
	var projPlantClassServicepopup = [];
	var projPlantClassService = {};
	projPlantClassService.getProjPlantClasses = function(projId) {
		var deferred = $q.defer();
		projPlantClassServicepopup = ngDialog.open({
			template : '/views/procurement/pre-contracts/internalApproval/precontractplantpopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.projPlantClassData = [];
				$scope.getProjPlantClasses = function() {
					var planClassReq = {
						"status" : 1
					};
					PlantClassService.getPlantClasses(planClassReq).then(function(data) {
						$scope.projPlantClassData = data.plantClassTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Plant classes", 'Error');
					});
				}, $scope.projPlantClassPopup = function(projPlantClassTO) {
					var returnPopObj = {
						"projPlantClassTO" : projPlantClassTO
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};
	return projPlantClassService;

}]);
