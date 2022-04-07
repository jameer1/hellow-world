'use strict';
app.factory('CompanyTaxFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CompanyTaxService", "CompanyTaxPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, CompanyTaxService, CompanyTaxPopUpFactory, GenericAlertService) {
	var companyTax;
	var service = {};

	service.getCompanyDetails = function(taxTypeId) {
		var deferred = $q.defer();
		var companyTaxReq = {
			"taxId" : taxTypeId,
			"status" : "1"
		};
		CompanyTaxService.getCompanyTax(companyTaxReq).then(function(data) {
			var companyTax = [];
			companyTax = service.companyTaxPopup(data.companyTaxTOs, taxTypeId);
			companyTax.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting CompanyTax Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting CompanyTax Details", 'Error');
		});
		return deferred.promise;

	}, service.companyTaxPopup = function(companyTaxTOs, taxTypeId) {
		var deferred = $q.defer();
		companyTax = ngDialog.open({
			template : 'views/finance/taxtypes/companytax.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editCompanyTax = [];
				$scope.companyTax = companyTaxTOs;

				$scope.companytaxrowselect = function(company) {
					if (company.selected) {
						editCompanyTax.push(company);
					} else {
						editCompanyTax.pop(company);
					}
				}, $scope.addCompanyTax = function(actionType) {
					if (actionType == 'Edit' && editCompanyTax <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = CompanyTaxPopUpFactory.companyTaxPopupDetails(actionType, editCompanyTax, taxTypeId);
						popupDetails.then(function(data) {
							$scope.companyTax = data.companyTaxTOs;
							editCompanyTax = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deteteCompanyTax = function() {
					if (editCompanyTax.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.companyTax = [];
					} else {
						angular.forEach(editCompanyTax, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						CompanyTaxService.deleteCompanyTax(req).then(function(data) {
							GenericAlertService.alertMessage(' companyTax Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editCompanyTax, function(value, key) {
							$scope.companyTax.splice($scope.companyTax.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('companyTax Details are failed to Deactivate', "Error");
						});
						editCompanyTax = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
