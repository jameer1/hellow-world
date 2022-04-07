'use strict';

app.factory('PreContractMaterialClassFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, MaterialClassService, GenericAlertService) {
	var service = {};
	service.getMaterialSubGroups = function() {
		var deferred = $q.defer();
		var req = {
			"status" : 1
		};
		MaterialClassService.getMaterialGroups(req).then(function(data) {
			var popupdata = service.openPopup(data.materialClassTOs);
			popupdata.then(function(resultData) {
				deferred.resolve(resultData);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  Material Item Details", "Error");
		});
		return deferred.promise;

	}, service.openPopup = function(materialClassTOs) {
		var deferred = $q.defer();
		var materialTreePopup = ngDialog.open({
			template : 'views/procurement/pre-contracts/internalApproval/precontractmaterialpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedMaterialItems = [];
				$scope.projMaterialClassData = materialClassTOs;
				$scope.itemId = 1;
				$scope.isExpand = true;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				},
				$scope.rowSelect = function(materialClassTO) {
					var returnPopObj = {
						"materialClassTO" : materialClassTO
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};

	return service;

}]).filter('materialProcurementFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o) {
			if (o.childMaterialItemTOs && o.childMaterialItemTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				o.expand = isExpand;
				if (o.expand == true) {
					recursive(o.childMaterialItemTOs, newObj, o.level + 1, itemId, isExpand);
				}
			} else {
				o.level = level;
				if (o.id) {
					newObj.push(o);
					o.leaf = true;
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
