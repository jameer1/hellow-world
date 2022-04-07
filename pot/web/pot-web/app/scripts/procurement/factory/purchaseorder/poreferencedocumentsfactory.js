'use strict';
app.factory('POReferenceDocumentsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		PreContractInternalService, GenericAlertService) {
	var service = {};
	service.referenceDocumentDetails = function(preContractTO) {
		var deferred = $q.defer();
		var refPopup = ngDialog.open({
			template : 'views/procurement/purchaseorders/poreferencedocument.html',
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
						$scope.addRows = function() {
							$scope.refdocumentslist.push(angular.copy($scope.referDockObj));
						}
						$scope.fileUpload = function(refdocumentslist) {
							refdocumentslist.typeofFile = refdocumentslist.type;
							refdocumentslist.sizeofFile = refdocumentslist.size;
						}

						$scope.referencerowSelect = function(reference) {
							if (reference.select) {
								selectedRef.push(reference);
							} else {
								selectedRef.pop(reference);
							}
						}, $scope.deleteRows = function() {
							if (selectedRef.length == 0) {
								GenericAlertService.alertMessage(
										'Please select atleast one row to delete', "Warning");
								return;
							}
							if ($scope.refdocumentslist.length == 1) {
								GenericAlertService.alertMessage('Please leave atleast one row',
										"Warning");
								return;
							}

							if (selectedRef.length < $scope.refdocumentslist.length) {
								angular.forEach(selectedRef, function(value, key) {
									$scope.refdocumentslist.splice($scope.refdocumentslist
											.indexOf(value), 1);
								});

								selectedRef = [];

							} else {
								GenericAlertService.alertMessage('Please leave atleast one row',
										"Warning");
							}
						}, $scope.saveReferenceDocument = function() {
							var req = {
								"preContractDocsTOs" : $scope.refdocumentslist
							}
							PreContractInternalService.savePreContratDocs(req).then(
									function(data) {
										var succMsg = GenericAlertService.alertMessageModal(
												'Reference Document Details ' + data.message,
												data.status);
										succMsg.then(function(popData) {
											var returnPopObj = {
												"preContractDocsTOs" : data.preContractDocsTOs
											};
											deferred.resolve(returnPopObj);
										});
									},
									function(error) {
										GenericAlertService.alertMessage(
												'Reference Document Details  are failed to Save',
												'Error');
									});
						};

					} ]

		});
		return deferred.promise;
	}
	return service;
}]);
