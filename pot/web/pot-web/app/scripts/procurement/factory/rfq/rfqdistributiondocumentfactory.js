'use strict';

app.factory('RfqDistributionDocumentFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "RFQService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		RFQService, GenericAlertService) {
	var service = {};
	service.getPreContractCmpDistributionDocs = function(preContractCompanyMap, preContractCmpTOs,
			documentStatusTO, precontractObj) {
		var deferred = $q.defer();

		var docReq = {
			"status" : 1,
			"distributionDocId" : documentStatusTO.id
		};
		RFQService.getPreContractCmpDistributionDocs(docReq).then(
				function(data) {
					var popupdata = service
							.openPopup(preContractCompanyMap, preContractCmpTOs,
									data.preContractCmpDistributionDocTOs, documentStatusTO,
									precontractObj);
					popupdata.then(function(data) {
						deferred.resolve(data);
					});
				},
				function(error) {
					GenericAlertService.alertMessage(
							"Error occured while getting PreContract approval details", 'Error');
				});
		return deferred.promise;

	}, service.openPopup = function(companyMap, preContractCmpTOs,
			preContractCmpDistributionDocTOs, documentStatusTO, precontractObj) {
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template : 'views/procurement/RFQ/rfqdistributiondocuments.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.preContractCmpMap = companyMap;
				$scope.preContractCmpTOs = preContractCmpTOs;
				$scope.documentStatusTO = documentStatusTO;
				$scope.precontractObj = precontractObj;
				$scope.distibutedCmpDocMap = [];
				angular.forEach(preContractCmpDistributionDocTOs, function(value, key) {
					$scope.distibutedCmpDocMap[value.preContractCmpId] = true;
				});
				$scope.saveVendorDocuments = function(){
				$scope.cmp = [];
				for(let i=0;i<$scope.preContractCmpTOs.length;i++){
					if($scope.preContractCmpTOs[i].internalApproved == 1){
						$scope.cmp.push($scope.preContractCmpTOs[i]);
					}
				}
				var preContractCmpDistributionDocTOs = [];
				var precontractdocs = [];
					angular
						.forEach(
							$scope.cmp,
							function(value, key) {
							preContractCmpDistributionDocTOs
							.push({
									"preContractCmpId" : value.id,
									"transmit" : true
									});
								});
					precontractdocs.push({
							"preContractId":$scope.documentStatusTO.preContractId,
							"preDocContentId":$scope.documentStatusTO.preDocContentId,
							"code":$scope.documentStatusTO.code,
							"name":$scope.documentStatusTO.name,
							"mimeType":$scope.documentStatusTO.fileType,
							"version":$scope.documentStatusTO.version,
							"date":$scope.documentStatusTO.date,
							"modeType":$scope.documentStatusTO.modeType,
							"precontractTO":$scope.documentStatusTO.precontractTO,
							"preContractDocContentTOs":$scope.documentStatusTO.preContractDocContentTOs
						
						});
				var req = {
							"status" : 1,
							"contractId" : precontractObj.id,
							"preContractCmpDistributionDocTOs" : preContractCmpDistributionDocTOs,
							"preContractDistributionDocTOs" : precontractdocs
						};
				var resultData = RFQService.sendPreContractDocsToCompanies(req).then(function(data) {
								
								var returnObj = {
								"preContractDistributionDocTOs" : data.preContractDistributionDocTOs
							};
								GenericAlertService.alertMessage("Pre-Contract  document are sent to selected bidders successfully",'Info');
								deferred.resolve(returnObj);
								$scope.closeThisDialog();
							},
								function(error) {
													GenericAlertService.alertMessage("Error occured while sending  distribution documents",'Error');
					});
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
