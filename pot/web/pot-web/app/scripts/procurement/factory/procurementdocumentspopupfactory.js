'use strict';
app.factory('ProcurementDocumentsPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService","RFQService", function (ngDialog, $q, $filter, $timeout, $rootScope,
	PreContractInternalService, GenericAlertService,RFQService) {
	var service = {};
	var refPopup = null;
	service.getReferenceDocumentDetails = function (preContractTO, hideDeleteButton) {
		var deferred = $q.defer();
		refPopup = ngDialog.open({
			template: 'views/common/downloadrefdocument.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: [
				'$scope',
				function ($scope) {
					var selectedRef = [];
					$scope.date = $filter('date')(new Date());
					$scope.refdocumentslist = [];
					$scope.preContractTO = preContractTO;
					$scope.projId = preContractTO.projId;
					$scope.refDocumentMode = ( $rootScope.referenceDocumentMode != undefined ) ? $rootScope.referenceDocumentMode : "";
					$scope.hideDeleteButton = (hideDeleteButton === true) ? true : false;					
					$scope.getDocumentDetails = function () {
						var req = {
							"contractId": $scope.preContractTO.id,
						}
						console.log(req);
						var documentDetailsPromise = PreContractInternalService.getPreContratDocs(req);
						documentDetailsPromise.then(function (data) {
							console.log(data);
							$scope.refdocumentslist = data.preContractDocsTOs;

						}, function (error) {
							GenericAlertService.alertMessage(
								"Error occured while getting precontract documents",
								"Error");
						});
						return deferred.promise;
					},
					$scope.downloadFile = function(documentId,document_name) {
						//$scope.folderCategory = ( $scope.refDocumentMode == "Reference Documents" ) ? "PreContract List-Reference Documents" : "Stage2 Approval-Generate PO-Appendices";
						let req = {
							"recordId" : documentId,
							"category" : "PreContract List-Reference Documents",
							"fileName" : document_name
						}
						//EmpRegisterService.downloadRegisterDocs(req);
						RFQService.downloadProcurementDocs(req);
						console.log("downloadFile function");
					}
				}]

		});
		return deferred.promise;
	}
	return service;
}]);
