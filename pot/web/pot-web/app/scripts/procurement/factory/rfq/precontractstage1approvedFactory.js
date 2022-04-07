'use strict';
app.factory('PrecontractStage1ApprovedFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "RFQService", function(ngDialog, $q, $filter, $timeout,
		$rootScope, GenericAlertService, RFQService) {
	var service = {};
	service.getPreContractDetails = function(req) {
		var deferred = $q.defer();
		var preContractDetailsPromise = RFQService.getPreContractsForRfq(req);
		preContractDetailsPromise.then(function(data) {
			var preContractDetailsPopUp = service.preContractDetailsPopUp(data.preContractTOs);
			preContractDetailsPopUp.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Pre-Contracts",
						'Error');
			});
		}, function(error) {
			GenericAlertService
					.alertMessage("Error occured while selecting Pre-Contracts", 'Error');
		});
		return deferred.promise;
	}, service.preContractDetailsPopUp = function(contractData) {
		var deferred = $q.defer();
		var preContractDetailsPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/precontractdetails.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.contractData = contractData;
				$scope.selectRecord = function(selectedRecord) {
					var returnRecord = {
						"selectedRecord" : selectedRecord
					};
					deferred.resolve(returnRecord);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	}

	return service;
}]);
