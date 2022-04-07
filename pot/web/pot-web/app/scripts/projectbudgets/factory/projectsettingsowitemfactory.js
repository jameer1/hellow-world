'use strict';

app.factory('ProjectSettingSOWItemFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjectBudgetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, ProjectBudgetService, GenericAlertService) {
	var SOWItemService = {};
	var projSOWPopUp = [];
	SOWItemService.getSOWItemDetails = function(projId) {
		var deferred = $q.defer();
		projSOWPopUp = ngDialog.open({
			template : 'views/projectbudgets/sowitempopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.SOWData = [];
				var selectedSOWItems = [];
				$scope.itemId = 1;
				$scope.isExpand = false;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};
				var sowReq = {
					"projId" : projId,
					"status" : 1
				};
				ProjectBudgetService.getProjSOWDetails(sowReq).then(function(data) {
					$scope.SOWData = data.projSOWItemTOs;
				});
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedSOWItems.push(tab);
					} else {
						selectedSOWItems.splice(selectedSOWItems.indexOf(tab), 1);
					}
				},
				
				
				$scope.saveProjSOWItems = function() {
					var saveProgMesureReq = {
						"projSOWItemTOs" : angular.copy(selectedSOWItems)
					};
					blockUI.start();
					ProjectBudgetService.saveProjSOWItems(saveProgMesureReq).then(function(data) {
						blockUI.stop();
						var results = data.projSOWItemTOs;
						var succMsg = GenericAlertService.alertMessageModal('Project SOW Item(s) is/are ' + data.message, data.status);
						succMsg.then(function(data) {
							$scope.returnPopObj = {
								"projSOWItemTOs" : results
							};
						});
						deferred.resolve($scope.returnPopObj);
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Project SOW Item(s) is/are failed to save', "Error");
					});
				}
			} ]
		})
		return deferred.promise;
	}
	return SOWItemService;
}]);