'use strict';

app.factory('PrecontractReqApprovalFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService, GenericAlertService) {
	var service = {};
	service.getReqApprDetails = function(req,usersMap) {
		var deferred = $q.defer();
		var procureReqApprDetailsPromise = PreContractInternalService.getPreContractReqApprs(req,usersMap);
		procureReqApprDetailsPromise.then(function(data) {
			var reqApprDetailsPopup = service.openPopUp(req,usersMap, data.preContractReqApprTOs);
			reqApprDetailsPopup.then(function(data) {
				deferred.resolve(data.reqapprTO);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Request Approver Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Request Approver Details", "Error");
		});
		return deferred.promise;
	}, service.openPopUp = function(req,usersMap, preContractReqApprTOs) {
		
		var deferred = $q.defer();
		var popupdate = ngDialog.open({
			template : '/views/procurement/pre-contracts/internalApproval/precontractinternalApproval.html',
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.preContractReqApprTOs = preContractReqApprTOs;
				$scope.userMap = usersMap;
			} ]
		});
		return deferred.promise;
	};

	return service;
}]);
