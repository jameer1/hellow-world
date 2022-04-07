'use strict';

app.factory('MaterialRegStoreFactory', ["ngDialog", "$q", "$filter", "$timeout", "StoreService", "ProjStoreStockService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, StoreService, ProjStoreStockService, GenericAlertService) {
	var service = {};
	var projectId;

	service.getStoreStocks = function(projId) {
		if( isNaN(parseInt(projId)) ){
			GenericAlertService.alertMessage("No Project Selected", 'Error');
			return;
		}
		projectId = projId;

		var deferred = $q.defer();
		var storeYardReq = {
			"status" : 1
		};
		StoreService.getStocks(storeYardReq).then(function(data) {
			var pouppData = service.openPopUp(data.stockAndStoreTOs);
			pouppData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Store Stock Details", 'Error');
		});
		return deferred.promise;
	}, service.openPopUp = function(stockStoreTOs) {
		var deferred = $q.defer();
		var storeYardPopup = ngDialog.open({
			template : 'views/projresources/projmaterialreg/maerialregstorestockpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.stockStoreTOs = stockStoreTOs;
				$scope.deliveryPlacePopup = function(stockTO, sourceType) { // XXX - This function requires modification
					let selectedPlace = {
						sourceType: sourceType,
						stock: stockTO
					};
					deferred.resolve( selectedPlace );
					$scope.closeThisDialog();
				};
				
				$scope.getProjStoreStocks  = function(projId){
					var storeYardReq = {
						"status" : 1,
						"projIds" : Array.isArray(projId) ? projId : [projId]
					}; 
					ProjStoreStockService.getMultipleProjsStoreList(storeYardReq).then(function(data) {
						$scope.projStoreYardData = data.projStoreStockTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Store Stock Details", 'Error');
					});
				}
				$scope.getProjStoreStocks(projectId);
				
				$scope.chosenTab = "store";
				$scope.storeChosen = function(){
					$scope.chosenTab = "store";
				};
				$scope.projectChosen = function(){
					$scope.chosenTab = "project";
				};
				
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
