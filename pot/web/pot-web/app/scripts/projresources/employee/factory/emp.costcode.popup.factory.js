'use strict';

app.factory('EmpCostCodeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjCostCodeService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjCostCodeService, GenericAlertService) {
	var costCodePopUp;
	var service = {};
	service.costCodeListPopUp = function(projCostItemTOs) {
		var deferred = $q.defer();
		costCodePopUp = ngDialog.open({
			template : 'views/projresources/projempreg/chargerate/projcostcodepopupselect.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.projCostItemTOs = projCostItemTOs;
				$scope.selectCostRecord = function(record) {
					if (record.item) {
						var returnRecord = {
							"selectedRecord" : record
						};
						deferred.resolve(returnRecord);
						$scope.closeThisDialog();

					} else {
						GenericAlertService.alertMessage("Please Select Only Cost Code Items", 'Info');
						return;
					}

				}, $scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				}
			} ]

		});
		return deferred.promise;
	},

	service.getCostCodeDetails = function(costCodeOnLoadReq) {
		var deferred = $q.defer();
		var costCodeDetailsPromise = ProjCostCodeService.getCostDetails(costCodeOnLoadReq)
		costCodeDetailsPromise.then(function(data) {
			var costCodeListPopUp = service.costCodeListPopUp(data.projCostItemTOs);
			costCodeListPopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Cost Code Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Cost Code Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]).filter('workDairyProjCostFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o) {
			if (o != undefined && o.projCostCodeItemTOs && o.projCostCodeItemTOs.length != 0) {
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
