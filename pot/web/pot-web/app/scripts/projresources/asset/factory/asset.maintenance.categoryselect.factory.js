'use strict';
app.factory('AssetMaintenanceCategorySelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetsMaintenanceCategoryService", "TreeService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetsMaintenanceCategoryService, TreeService) {
	var categoryPopUp;
	var service = {};
	service.assetMaintenanceCategorySelectPopUp = function(assetMaintenanceCategoryTOs) {
		var deferred = $q.defer();
		categoryPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/assetmaintenancecategoryselect.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				console.log(assetMaintenanceCategoryTOs)
			$scope.assetMaintenanceCategoryTOs = TreeService.populateTreeData(assetMaintenanceCategoryTOs, 0, [], 'code', 'childAssetMaintenanceCategoryTOs');
		//		$scope.itemId = 1;
		//		$scope.isExpand = true;
				$scope.itemClick = function(itemId, expand) {
		//			$scope.itemId = itemId;
		//			$scope.isExpand = !expand;
		TreeService.treeItemClick(itemId, expand, 'childAssetMaintenanceCategoryTOs');
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

	service.getAssetMaintenanceCategory = function(req) {
		var deferred = $q.defer();
		var maintenanceselectPromise =  AssetsMaintenanceCategoryService.getAssetMaintenanceCategory(req)
		maintenanceselectPromise.then(function(data) {
			var categoryPopUp = service.assetMaintenanceCategorySelectPopUp(data.assetMaintenanceCategoryTOs);
			categoryPopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord.name
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
/*.filter('assetsMaintenanceFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o, key) {
			if (o.childAssetMaintenanceCategoryTOs && o.childAssetMaintenanceCategoryTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				if (o.id == itemId) {
					o.expand = isExpand;
				}
				if (o.expand == true) {
					recursive(o.childAssetMaintenanceCategoryTOs, newObj, o.level + 1, itemId, isExpand);
				}
			} else {
				o.level = level;
				if (o.item) {
					o.leaf = true;
				} else {
					o.leaf = false;
				}
				newObj.push(o);
				return false;
			}
		});
	}

	return function(obj, itemId, isExpand) {
		var newObj = [];
		recursive(obj, newObj, 0, itemId, isExpand);
		return newObj;
	}
})*/