'use strict';

app.factory('TimeSheetCostCodeFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "ProjCostCodeService", "GenericAlertService", "TreeService", function (ngDialog, $q, blockUI, $filter, $timeout, $rootScope,
	ProjCostCodeService, GenericAlertService, TreeService) {
	var costService = {};
	costService.getCostCodeDetails = function (timeSheetSearchReq, existingEmpCostCodeMap) {
		var deferred = $q.defer();
		var costReq = {
			"status": 1,
			"projId": timeSheetSearchReq.projectLabelKeyTO.projId
		};
		blockUI.start();
		ProjCostCodeService.getCostDetails(costReq).then(function (data) {
			blockUI.stop();
			var costPopup = costService.opeCostCodePopup(data.projCostItemTOs, existingEmpCostCodeMap);
			costPopup.then(function (data) {
				var returnPopObj = {
					"projCostItemTO": data.projCostItemTO
				};
				deferred.resolve(returnPopObj);
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
			})
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occurred while getting cost codes", 'Error');
		});
		return deferred.promise;

	}, costService.opeCostCodePopup = function (projCostItemTOs, existingEmpCostCodeMap) {
		var costCodepopupFactory = [];
		var deferred = $q.defer();
		costCodepopupFactory = ngDialog.open({
			template: 'views/timemanagement/timesheet/createtimesheet/timesheetprojcostcode.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			controller: ['$scope', function ($scope) {

				$scope.tabData = TreeService.populateTreeData(projCostItemTOs, 0, [], 'code', 'projCostCodeItemTOs');
				$scope.existingEmpCostCodeMap = existingEmpCostCodeMap;
				$scope.itemClick = function (item, collapse) {
					TreeService.treeItemClick(item, collapse, 'projCostCodeItemTOs');
				}, $scope.selectedCostCode = function (projCostItemTO) {

					if (projCostItemTO.item) {

						var returnPopObj = {
							"projCostItemTO": projCostItemTO
						};
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();

					}
					else {
						GenericAlertService.alertMessage("Please Select Only Cost Code Items", 'Warning');
						return;

					}

				}
				for (const obj of $scope.tabData) {
					$scope.itemClick(obj, false);
				}

			}]
		});
		return deferred.promise;

	}
	return costService;

}]);
