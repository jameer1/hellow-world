'use strict';
app.factory('PlantServiceCategoryFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantServiceHistoryService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantServiceHistoryService) {
	var plantServiceCategoryPopUp;
	var service = {};
	service.plantServiceCategoryPopUp = function(plantServiceDetails) {
		var deferred = $q.defer();
		plantServiceCategoryPopUp = ngDialog.open({
			template : 'views/projresources/projplantreg/plantservicehistoryrepairs/plantregservicecategorylist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.plantServiceCategoryDetails = plantServiceDetails;

				$scope.plantServiceCategoryPopUp = function(serviceClass) {

					$scope.serviceClassTO = angular.copy(serviceClass);

					if (!serviceClass.level) {
						GenericAlertService.alertMessage("Please Select Only Sub Category", 'Warning');
						return;

					} else {
						var returnServiceCategoryTO = {
							"serviceClassTO" : $scope.serviceClassTO
						};
						deferred.resolve(returnServiceCategoryTO);
						$scope.closeThisDialog();

					}

				}, $scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};
			} ]

		});
		return deferred.promise;
	},

	service.getPlantServiceDetails = function(req) {
		var deferred = $q.defer();
		var plantServiceDetailsPromise = PlantServiceHistoryService.getPlantServiceHistroyDetails(req);
		plantServiceDetailsPromise.then(function(data) {
			var plantServiceCategoryDetails = [];
			plantServiceCategoryDetails = data.plantServiceClassificationMstrTOs;
			var plantServiceDetailsPopup = service.plantServiceCategoryPopUp(plantServiceCategoryDetails);
			plantServiceDetailsPopup.then(function(data) {
				var returnPopObj = {
					"serviceClassTO" : data.serviceClassTO
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Purchase Order Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Purchase Order Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]).filter('plantRegServiceClassFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o) {
			if (o.plantServiceClassificationMstrTOs && o.plantServiceClassificationMstrTOs.length != 0) {
				o.level = level;
				newObj.push(o);
				o.expand = true;
				recursive(o.plantServiceClassificationMstrTOs, newObj, o.level + 1, itemId, isExpand);
			} else {
				o.level = level;
				if (o.item) {
					o.leaf = true;
					newObj.push(o);
				} else {
					obj.splice(obj.indexOf(o), 1);
				}
				return false;
			}
		});
	}
	return function(obj, itemId, isExpand) {
		var newObj = [];
		recursive(obj, newObj, 0, itemId, isExpand);
		return newObj;
	}
});
