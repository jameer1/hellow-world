'use strict';
app.factory('PreContractDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PreContractInternalService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PreContractInternalService) {
	var preContractDetailsPopUp;
	var service = {};
	service.getPreContractDetails = function(req) {
		var deferred = $q.defer();
		var preContractDetailsPromise = PreContractInternalService.getInternalPreContracts(req);
		preContractDetailsPromise.then(function(data) {
			var contractData = data.preContractTOs;
			var preContractDetailsPopUp = service.openPopUp(contractData);
			preContractDetailsPopUp.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting precontracts", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting precontracts", "Error");
		});
		return deferred.promise;
	}, service.openPopUp = function(contractData) {
		var deferred = $q.defer();
		preContractDetailsPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/precontractdetails.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.contractData = contractData;
				$scope.selectRecord = function(record) {
					var returnRecord = {
						"selectedRecord" : record
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
