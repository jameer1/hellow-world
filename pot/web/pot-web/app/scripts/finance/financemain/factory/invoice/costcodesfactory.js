'use strict';
app.factory('CostCodesFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjCostCodeService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, ProjCostCodeService) {
	var service = {};
	
	service.getCostDetails = function(projId) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId
		};
		ProjCostCodeService.getCostDetails(req).then(function(data) {
			var popupdata = service.openPopup(data.projCostItemTOs);
			popupdata.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting CostCodes", 'Error');
		});
		return deferred.promise;
	},
	service.openPopup = function(projCostItemTOs) {
		var deferred = $q.defer();
		var popUp = ngDialog.open({
			template : 'views/finance/financemain/invoice/costcodespopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.userType = '2';
				$scope.itemId = 1;
				$scope.isExpand = true;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};
				$scope.projCostItemTOs = projCostItemTOs;
				$scope.selectCostCode = function(projCostStmtDtlTO) {
					deferred.resolve(projCostStmtDtlTO);
					$scope.closeThisDialog();
				}

			} ]

		})
		return deferred.promise;
	}
	return service;
}]).filter('projCostFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o) {
			if (o.projCostCodeItemTOs && o.projCostCodeItemTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				if (o.id == itemId) {
					o.expand = isExpand;
				}
				if (o.expand == true) {
					recursive(o.projCostCodeItemTOs, newObj, o.level + 1, itemId, isExpand);
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

});
