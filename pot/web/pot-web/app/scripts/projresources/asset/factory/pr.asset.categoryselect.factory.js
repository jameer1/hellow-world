'use strict';
app.factory('AssetCategorySelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetsCategoryService", "TreeService", function(ngDialog, $q, $filter, $timeout, $rootScope,
	 GenericAlertService, AssetsCategoryService, TreeService) {
	var categoryPopUp;
	var service = {};
	service.assetCategorySelectPopUp = function(assetCategoryTOs) {
		var deferred = $q.defer();
		categoryPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/assetdetails/assetcategoryselectpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
			$scope.assetCategoryTOs = TreeService.populateTreeData(assetCategoryTOs, 0, [],
				 'code', 'childAssetCategoryTOs');
				$scope.assetItemClick = function(item, collapse) {
					TreeService.treeItemClick (item, collapse, 'childAssetCategoryTOs');
				}
				$scope.selectRecord = function(record) {
					var returnRecord = {
						"selectedRecord" : record
					};
					deferred.resolve(returnRecord);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	},

	service.getAssetCategory = function(onLoadReq) {
		var deferred = $q.defer();
		var categoryselectPromise =  AssetsCategoryService.getAssetCategory(onLoadReq)
		categoryselectPromise.then(function(data) {
			var categoryPopUp = service.assetCategorySelectPopUp(data.assetCategoryTOs);
			categoryPopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting ProfitCentre Details  ", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting ProfitCentre Details", "Error");
		});
		return deferred.promise;
	}

	return service;
	

}]);