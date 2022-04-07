'use strict';
app.factory('RfqTenderDocumentFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "RFQService", "CompanyContactDetailsFactory", "CompanyAddressDetailsFactory", "GenericAlertService", "CompanyService", function(ngDialog, $q, $filter, $timeout, $rootScope,
		RFQService, CompanyContactDetailsFactory, CompanyAddressDetailsFactory,
		GenericAlertService, CompanyService) {
	var service = {};
	service.getPreContractDocuments = function(preContractObj) {
		var deferred = $q.defer();
		var req = {
			"projId" : preContractObj.projId,
			"contractId" : preContractObj.id,
			"status" : 1
		};
		var serviceData = RFQService.getPreContratDocs(req);
		serviceData.then(function(data) {
			var popupData = service.openPopUp(data.preContractDocsTOs);
			popupData.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting tender documents",
						'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting tender documents",
					'Error');
		});
		return deferred.promise;
	}, service.openPopUp = function(preContractDocsTOs) {
		var deferred = $q.defer();
		var documentPopUp = ngDialog.open({
			template : 'views/procurement/RFQ/documentdetailslist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.modeType = {
					id : "1",
					name : "Email"
				};

				$scope.modeTypeList = [ {
					id : "1",
					name : "Email"
				}, {
					id : "2",
					name : "Fax"
				}, {
					id : "3",
					name : "By Post"
				} ];
				$scope.preContractDocsTOs = preContractDocsTOs;
				var selectedDocuments = [];
				$scope.documentRowSelect = function(documentTO) {
					if (documentTO.select) {
						selectedDocuments.push(documentTO);
					} else {
						selectedDocuments.splice(selectedDocuments.indexOf(documentTO), 1);
					}
				},

				$scope.addToDistributionList = function() {
					var returnPopObj = {
						"documentTOs" : angular.copy(selectedDocuments)
					};console.log(returnPopObj.documentTOs[0].select);
					returnPopObj.documentTOs[0].select=false;
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	}

	return service;
}]);
