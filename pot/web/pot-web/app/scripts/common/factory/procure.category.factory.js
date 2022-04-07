'use strict';
app.factory('ProcureCategoryFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProcureService", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, 
								ProcureService,GenericAlertService, PlantRegisterService) {
	var procureDetailsPopUp;
	var service = {};
	service.getProcureCategoryDetails = function(procureCategoryReq) {
		var deferred = $q.defer();
			ProcureService.getProcureCatgs(procureCategoryReq).then(function(data) {
				var procureTypePopup =  service.getProcureTypePopup(data.procureMentCatgTOs);
				procureTypePopup.then(function(data) {
					var returnPopObj = {
						"selectedRecord" : data.procureCategoryTO
					};
					deferred.resolve(returnPopObj);
				}, function(error) {
					if (error.status != undefined && error.status != null){
						GenericAlertService.alertMessage(message, error.status);
					}else {
					GenericAlertService.alertMessage("Error occured while selecting Company List Details", 'Error');
					}
				})
				
			});
		return deferred.promise;
	}
	service.getProcureTypePopup = function(plantCategoryDetails) {
		var deferred = $q.defer();
		procureDetailsPopUp = ngDialog.open({
			template : 'views/common/procurcategorylist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.plantCategoryDetails = plantCategoryDetails;
				
				$scope.plantCategoryPopUp = function(plantCategoryTO) {
					var returnPlantCategoryTO = {
						"procureCategoryTO" : plantCategoryTO
					};
					
					deferred.resolve(returnPlantCategoryTO);
					$scope.closeThisDialog();

				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
