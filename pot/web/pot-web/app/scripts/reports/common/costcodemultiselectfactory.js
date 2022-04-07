'use strict';

app.factory('CostCodeMultiSelectFactory', ["ngDialog", "$q", "ProjCostCodeService", "GenericAlertService", "TreeService", function (ngDialog, $q, ProjCostCodeService, GenericAlertService, TreeService) {
	var service = {};

	service.getMultiProjCostCodes = function (costReq) {

		var deferred = $q.defer();

		ProjCostCodeService.getMultiProjCostDetails(costReq).then(function (data) {
			var costPopup = service.opeCostCodePopup(data.projCostItemTOs);
			costPopup.then(function (data) {
				deferred.resolve(data);
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
		});
		return deferred.promise;

	}, service.opeCostCodePopup = function (projCostItemTOs, projId, crewId, workDairyId) {
		var costCodepopupFactory = [];
		var deferred = $q.defer();
		costCodepopupFactory = ngDialog.open({
			template: 'views/reports/common/multiprojcostcodeselectpopup.html',
			closeByDocument: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose: false,
			controller: ['$scope', function ($scope) {

				$scope.tabData = TreeService.populateTreeData(projCostItemTOs, 0, [], 'code', 'projCostCodeItemTOs');
				var costCodeNameDisplay = '';
				var selectedCostCodeIds = [];

				$scope.selectAllCostCodes = function () {
					$scope.tabData.map(o => {
						if (o.item)
							o.select = $scope.selectAll;
					});
				};

				$scope.addCostItems = function () {
					const costItems = $scope.tabData.filter(o => o.item && o.select);
					for (const cost of costItems) {
						selectedCostCodeIds.push(cost.id);
						costCodeNameDisplay = costCodeNameDisplay + cost.code + ",";
					}
					var returnPopObj = {
						"selectedCostCodes": {
							"costCodesName": costCodeNameDisplay.slice(0, -1),
							"costCodeIds": selectedCostCodeIds
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				};

				$scope.multiCostSelectClick = function (item, collapse) {
					TreeService.treeItemClick(item, collapse, 'projCostCodeItemTOs');
				};

				$scope.tabData.map(tab => {
					$scope.multiCostSelectClick(tab, false);
				});
			}]
		});
		return deferred.promise;
	}
	return service;

}]);