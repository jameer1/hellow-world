'use strict';
app.factory('CompanyTaxPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "CompanyTaxService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, CompanyTaxService, GenericAlertService) {
	var companyPopup;
	var selectedCompanyTax = [];
	var service = {};
	service.companyTaxPopupDetails = function(actionType, editCompanyTax, taxTypeId) {
		var deferred = $q.defer();
		companyPopup = ngDialog.open({
			template : 'views/finance/taxtypes/companytaxpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedCompanyTax = [];
				$scope.companyList = [];

				if (actionType === 'Add') {
					$scope.companyList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						'comments' : null,
						'taxStatus' : null,

						"taxCalMstrId" : '',
						"annualMinTax" : '',
						"annualMaxTax" : '',
						"fixedTax" : '',
						"variableTax" : ''
					});
				} else {
					$scope.companyList = angular.copy(editCompanyTax);
					editCompanyTax = [];
				}
				$scope.addCompanyTaxRows = function() {

					$scope.companyList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						'comments' : null,
						'taxStatus' : null,

						"taxCalMstrId" : '',
						"annualMinTax" : '',
						"annualMaxTax" : '',
						"fixedTax" : '',
						"variableTax" : '',

					});
				}, $scope.savecompany = function() {
					var req = {
						"companyTaxTOs" : $scope.companyList,
					}
					blockUI.start();
					CompanyTaxService.saveCompanyTax(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('companyTax Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('companyTax Details  are failed to Save', 'Error');
					});
					ngDialog.close();
				};

				$scope.companyPopUpRowSelect = function(company) {
					if (company.selected) {
						selectedCompanyTax.push(company);
					} else {
						selectedCompanyTax.pop(company);
					}
				}, $scope.deleteProjRows = function() {
					if (selectedCompanyTax.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedCompanyTax.length < $scope.companyList.length) {
						angular.forEach(selectedCompanyTax, function(value, key) {
							$scope.companyList.splice($scope.companyList.indexOf(value), 1);
						});
						selectedCompanyTax = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
