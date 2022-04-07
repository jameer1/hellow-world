'use strict';

app.factory('SOWMultiSelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "WorkDiaryService", "ProjSOWService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, WorkDiaryService, ProjSOWService, GenericAlertService) {
	var service = {};

	service.getMultiProjSow = function(sowReq) {

		var deferred = $q.defer();

		ProjSOWService.getMultiProjSOWDetails(sowReq).then(function(data) {
			var sowPopup = service.openSOWPopup(data.projSOWItemTOs);
			sowPopup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting SOW", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting SOW", 'Error');
		});
		return deferred.promise;

	}, service.openSOWPopup = function(projSOWItemTOs) {
		var sowpopupFactory = [];
		var deferred = $q.defer();
		sowpopupFactory = ngDialog.open({
			template : 'views/reports/common/multiprojsowselectpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.tabData = projSOWItemTOs;
				$scope.itemId = 1;
				$scope.isExpand = true;
				var selectedSOWItems = [];
				var sowNameDisplay = '';
				var selectedSOWIds = [];
				$scope.selectSOWItem = function(projSOWItemTO) {
					if (projSOWItemTO.select) {
						selectedSOWItems.push(projSOWItemTO);
					} else {
						selectedSOWItems.pop(projSOWItemTO);
					}
				}, $scope.addSOWItems = function() {

					angular.forEach(selectedSOWItems, function(value, key) {
						selectedSOWIds.push(value.id);
						sowNameDisplay = sowNameDisplay + value.code + ",";
					});
					var returnPopObj = {
						"selectedSOWs" : {
							"sowNameDisplay" : sowNameDisplay.slice(0, -1),
							"sowIds" : selectedSOWIds
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}, $scope.selectAllSowItems = function() {

					if ($scope.selectAll) { 
						angular.forEach($scope.tabData, function(value, key) {
							angular.forEach(value.childSOWItemTOs, function(value1, key1) {
								if (value1.item) {
									value1.select = true;
									selectedSOWItems.push(angular.copy(value1));
								}
								angular.forEach(value1.childSOWItemTOs, function(value2, key2) {
									if (value2.item) {
										value2.select = true;
										selectedSOWItems.push(angular.copy(value2));
									}
								});
							});
						});
					} else {
						selectedSOWItems = [];
						angular.forEach($scope.tabData, function(value, key) {
							angular.forEach(value.childSOWItemTOs, function(value1, key1) {
								if (value1.item) {
									value1.select = false;
									selectedSOWItems.push(angular.copy(value1));
								}
								angular.forEach(value1.childSOWItemTOs, function(value2, key2) {
									value2.select = false;
								});
							});
						});
					}
				}, $scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				}
			} ]
		});
		return deferred.promise;
	}
	return service;

}]).filter('multiProjSOWFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o) {
			if (o != undefined && o.childSOWItemTOs && o.childSOWItemTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				o.expand == true
				recursive(o.childSOWItemTOs, newObj, o.level + 1, itemId, isExpand);
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
