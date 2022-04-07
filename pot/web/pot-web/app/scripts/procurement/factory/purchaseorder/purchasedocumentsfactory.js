'use strict';
app.factory('PurchaseDocumentsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		PreContractInternalService, GenericAlertService) {
	var service = {};
	service.referenceDocumentDetails = function(preContractTO) {
		var deferred = $q.defer();
		var refPopup = ngDialog.open({
			template : 'views/procurement/purchaseorders/purchasedocument.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [
					'$scope',
					function($scope) {
						var selectedRef = [];
						$scope.refdocumentslist = [];
						$scope.preContractTO = preContractTO;
						$scope.projId = preContractTO.projId;
						$scope.referDockObj = {
							'selected' : false,
							'status' : 1,
							"date" : '',
							"name" : '',
							"code" : '',
							"version" : '',
							"uploadViewFile" : '',
							"typeofFile" : '',
							"sizeofFile" : '',
							"preContractId" : $scope.preContractTO.id,
							"projId" : $scope.projId
						};
						$scope.getDocumentDetails = function() {
							var req = {
								"contractId" : $scope.preContractTO.id,
							}
							var documentDetailsPromise = PreContractInternalService
									.getPreContratDocs(req);
							documentDetailsPromise.then(function(data) {
								$scope.refdocumentslist = data.preContractDocsTOs;

							}, function(error) {
								GenericAlertService.alertMessage(
										"Error occured while getting precontract documents",
										"Error");
							});
							return deferred.promise;
						}

						

					} ]

		});
		return deferred.promise;
	}
	return service;
}]);
