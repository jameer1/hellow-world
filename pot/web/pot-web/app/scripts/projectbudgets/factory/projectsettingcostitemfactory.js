'use strict';

app.factory('ProjectSettingCostItemFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjectBudgetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, ProjectBudgetService, GenericAlertService) {
	var costCodeItemService = {};
	var costStmtPopUp = [];
	costCodeItemService.getCostItemDetails = function(projId) {
		var deferred = $q.defer();
		costStmtPopUp = ngDialog.open({
			template : 'views/projectbudgets/costitempopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.popUpData = [];
				var selectedCostItems = [];
				$scope.itemId = 1;
				$scope.isExpand = false;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};
				var costReq = {
					"status" : 1,
					"projId" : $rootScope.projId
				};
				ProjectBudgetService.getCostDetails(costReq).then(function(data) {
					$scope.popUpData = data.projCostItemTOs;
				});
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedCostItems.push(tab);
					} else {
						selectedCostItems.splice(selectedCostItems.indexOf(tab), 1);
					}
				}, $scope.saveProjCostCodes = function() {
					var saveCostStmtReq = {
						"projCostItemTOs" : angular.copy(selectedCostItems)
					};
					blockUI.start();
					ProjectBudgetService.saveProjCostCodes(saveCostStmtReq).then(function(data) {
						blockUI.stop();
						var results = data.projCostStmtDtlTOs
						var succMsg=GenericAlertService.alertMessageModal('Project Cost Code(s) is/are  ' + data.message, data.status);
						succMsg.then(function(data){
						var returnPopObj = {
							"projCostStmtDtlTOs" : results
						};
						});
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Project Cost Code(s) is/are failed to save', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return costCodeItemService;
}]);