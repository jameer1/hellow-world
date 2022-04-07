'use strict';
app.factory('ProcureCategoryMultipleFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProcureService", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, 
								ProcureService,GenericAlertService, PlantRegisterService) {
	var procureDetailsPopUp;
	var service = {};
	service.getProcureCategoryDetails = function(procureCategoryReq) {
		var deferred = $q.defer();
			ProcureService.getProcureCatgs(procureCategoryReq).then(function(data) {
				var procureTypePopup =  service.getProcureTypePopup(data.procureMentCatgTOs);
				procureTypePopup.then(function(data) {
					var returnPopObj = {
						"selectedRecord" : data.selectedRecords
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
			template : 'views/common/procurcategorymultiplelist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.plantCategoryDetails = plantCategoryDetails;
			//	var selectedRecords = [];
				var selectedPlantIds = [];
				var plantName = '';
				$scope.selectAllPlants = function(){
					$scope.plantCategoryDetails.map(o => o.select = $scope.selectAll);
				}
				$scope.addPlants = function(){
					const selectedPlants = $scope.plantCategoryDetails.filter(o => o.select);
					for(const plants of selectedPlants){
						console.log(plants);
						selectedPlantIds.push(plants.proCatgId);
						plantName = plantName + plants.code + "," ;
					}
					var returnPopObj ={
						"selectedRecords" :{
							"selectedPlantIds": selectedPlantIds,
							"plantName":plantName.slice(0,-1),
						}
					}
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}	
				/*$scope.plantCategoryPopUp = function(plantCategoryTO) {
					var returnPlantCategoryTO = {
						"procureCategoryTO" : plantCategoryTO
					};
					
					deferred.resolve(returnPlantCategoryTO);
					$scope.closeThisDialog();

				}*/
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
