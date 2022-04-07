'use strict';
app.factory('PlantTypeDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractExternalService", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope,
	PreContractExternalService,  CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var getPlantTypeDetails;
	var service = {};
	service.getPlantTypeDetails = function(searchProject, purcahseOrder) {
		var deferred = $q.defer();
		getPlantTypeDetails = ngDialog.open({
			template : 'views/projresources/projplantpo/planttypelist.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {

				$scope.projPlantTypeDetails = [];

				$scope.getPlantTypes = function() {

					
					var onLoadReq = {
						"preContractId" : purcahseOrder.refId,
						"contractCmpId" : purcahseOrder.cmpId,
						"projId" : searchProject,
						"status" : 1
					};

					PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
						$scope.projPlantTypeDetails = data.preContractPlantDtlTOs;
					});
				}
				$scope.plantTypePopUp = function(projPlantTypeTO) {
					var returnProjPlantTypeTO = {
						"projPlantTypeTO" : projPlantTypeTO
					};
					deferred.resolve(returnProjPlantTypeTO);
					$scope.closeThisDialog();

				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
