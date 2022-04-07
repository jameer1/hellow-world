'use strict';

app.factory('WorkDairyCostCodeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "WorkDiaryService", "ProjCostCodeService", "GenericAlertService", "TreeService", function(ngDialog, $q, $filter, $timeout, $rootScope, WorkDiaryService, 
	ProjCostCodeService, GenericAlertService, TreeService) {
	var service = {};

	service.getCostCodeDetails = function(projId, crewId, workDairyCostCodeList, workDairyId) {

		var deferred = $q.defer();

		var costReq = {
			"status" : 1,
			"projId" : projId
		};

		var populateData = function(data, level, projects) {
			return TreeService.populateTreeData(data, level, projects, 'code', 'projCostCodeItemTOs');
		}	

		ProjCostCodeService.getCostDetails(costReq).then(function(data) {
			var wokDairyCostPopup = service.opeCostCodePopup(populateData(data.projCostItemTOs, 0, []), workDairyCostCodeList, projId, crewId, workDairyId);
			wokDairyCostPopup.then(function(data) {
				GenericAlertService.alertMessage("Cost Codes Added Successfully", 'Info');
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
		});
		return deferred.promise;

	}, service.opeCostCodePopup = function(projCostItemTOs, workDairyCostCodeList, projId, crewId, workDairyId) {
		var costCodepopupFactory = [];
		var deferred = $q.defer();
		costCodepopupFactory = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairyprojcostcode.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			draggable:true,
			controller : [ '$scope', function($scope) {
				$scope.workDairyCostCodeMap = [];
				$scope.tabData = projCostItemTOs;
				$scope.itemId = 1;
				$scope.isExpand = true;
				var selectedCostItems = [];
				$scope.workDairyCostCodeTOId = [];
				let deleteWorkDairyCostCodes = [];
				angular.forEach(workDairyCostCodeList, function(value, key) {
					$scope.workDairyCostCodeMap[value.costId] = {
						"crewCostId" : value.id,
						"id" : value.costId,
						"select" : true,
					};
				});

				$scope.itemClick = function(itemId, collapse) {
					TreeService.treeItemClick(itemId, collapse, 'projCostCodeItemTOs');
				}

				projCostItemTOs.map((item) => {
					$scope.itemClick(item, false);
					if ($scope.workDairyCostCodeMap[item.id]) {
						item.select = true;
						item.crewCostId = $scope.workDairyCostCodeMap[item.id].crewCostId;
					} else {
						item.select = item.workdairyEntry;
					}
					if (item.select) {
						selectedCostItems.push(item);
					}
				});
				
				$scope.deleteId = null;
				$scope.selectWorkDairyCostItem = function (costCode) {
					if (!costCode.select && selectedCostItems.indexOf(costCode) > -1) {
						selectedCostItems.splice(costCode, 1);
						if ($scope.workDairyCostCodeMap[costCode.id]) {
							deleteWorkDairyCostCodes.push(costCode.id);
						}
					} else if (selectedCostItems.indexOf(costCode) == -1) {
						selectedCostItems.push(costCode);
					}
				}, $scope.addCostItemsToWorkDairy = function() {

					var workDairyCostCodeTOs = [];
					angular.forEach(selectedCostItems, function(value, key) {
						workDairyCostCodeTOs.push(angular.copy({
							"id" : value.crewCostId,
							"costId" : value.id,
							"crewId" : crewId,
							"projId" : projId,
							"workDairyId" : workDairyId,
							"status" : 1,
							"select" : value.select
						}));
					});

					if (workDairyId) {
						var saveReq = {
							"workDairyCostCodeTOs" : workDairyCostCodeTOs,
							"crewId" : crewId,
							"projId" : projId,
							"workDairyId" : workDairyId,
							"status" : 1,
							"deleteWorkDairyCostIds" : deleteWorkDairyCostCodes
						};
						WorkDiaryService.saveWorkDairyCostCodes(saveReq).then(function(data) {
							$scope.closeThisDialog();
							deferred.resolve(data.workDairyCostCodeTOs);
						});
					} else {
						$scope.closeThisDialog();
						deferred.resolve(workDairyCostCodeTOs);
					}
				} 
			} ]
		});
		return deferred.promise;

	}
	return service;

}]);
