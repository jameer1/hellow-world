'use strict';

app.factory('ProjCostFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjCostCodeService", "GenericAlertService", "TreeService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjCostCodeService,
	 GenericAlertService, TreeService) {
	var costservice = {};
	var projCostPopup;
	costservice.costPopupDetails = function(projId) {
		var deferred = $q.defer();
		projCostPopup = ngDialog.open({
			template : 'views/projectlib/sow/projcostviewpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.costData = [];
				$scope.getCostDetails = function() {
					var costReq = {
						"projId" : projId,
						"status" : 1
					}
					ProjCostCodeService.getCostDetails(costReq).then(function(data) {
						$scope.costData = TreeService.populateTreeData(data.projCostItemTOs, 0, [], 'code',
							'projCostCodeItemTOs');
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting CostCode", 'Error');
					});

				}
				$scope.selectCostDetails = function(costItemData) {

					var returnCostPopObj = {
						"selectedCostItem" : costItemData
					};
					// ngDialog.close();
					deferred.resolve(returnCostPopObj);
					$scope.closeThisDialog();
				};

				$scope.projCostFactoryItemClick = function (tab, collapse) {
					TreeService.treeItemClick(tab, collapse, 'projCostCodeItemTOs');
				};
			} ]
		});
		return deferred.promise;
	};
	return costservice;
}]);