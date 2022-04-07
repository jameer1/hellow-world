'use strict';

app.factory('PreContractCostCodeBudgetFactory',
	["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", "ProjCostCodeService", function (ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService,
		GenericAlertService, ProjCostCodeService) {
		var service = {};
		var tabName;
		service.getPreContractCostCodeSummary = function (preContractObj,contractType) {
		$rootScope.tabName = contractType;
		console.log('aslhkfjsdalhfs@@@@@@@@@@@',contractType);
		
			var deferred = $q.defer();
			var req = {
				"contractId": preContractObj.id,
				"contractType":contractType
			};
			PreContractInternalService.getPreContractCostCodeSummary(req).then(function (data) {
				var resultData = service.openPopup(
					data.labelKeyTOs, preContractObj);
				resultData.then(function (data) {

				});
			}, function (error) {
				GenericAlertService.alertMessage(
					"Error ocurred while getting PreContract cost code budget details", "Error");
			});
			return deferred.promise;

		};
		service.openPopup = function (preContractCostCodeSummaryTOs,
			preContractObj) {
			var deferred = $q.defer();
			var procInternalApprovalPopUp = ngDialog.open({
				template: 'views/procurement/pre-contracts/internalApproval/precontractcostcodebudget.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				showClose: false,
				closeByDocument: false,
				controller: [
					'$scope',
					function ($scope) {
						$scope.preContractObj = preContractObj;
						$scope.preContractCostCodeSummaryTOs = preContractCostCodeSummaryTOs;
					}]
			});
			return deferred.promise;
		};
		return service;

	}]);
