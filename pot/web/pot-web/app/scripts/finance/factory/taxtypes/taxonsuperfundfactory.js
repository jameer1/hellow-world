'use strict';
app.factory('TaxonSuperfundFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "TaxonSuperFundService", "TaxOnSuperFundPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, TaxonSuperFundService, TaxOnSuperFundPopUpFactory, GenericAlertService) {
	var superFund;
	var service = {};

	service.getTaxOnSuperFundDetails = function(taxTypeId) {
		var deferred = $q.defer();
		var superFundReq = {
			"taxId" : taxTypeId,
			"status" : "1"
		};
		TaxonSuperFundService.getSuperfundTax(superFundReq).then(function(data) {
			var superFund = [];
			superFund = service.openTaxOnSuperFundPopup(data.taxOnSuperFundTOs, taxTypeId);
			superFund.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting TaxOnSuper Fund Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting TaxOnSuper Fund Details", 'Error');
		});
		return deferred.promise;

	}, service.openTaxOnSuperFundPopup = function(taxOnSuperFundTOs, taxTypeId) {
		var deferred = $q.defer();
		regularPay = ngDialog.open({
			template : 'views/finance/taxtypes/taxonsuperfund.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editSuperFund = [];
				$scope.superFund = taxOnSuperFundTOs;

				$scope.taxonrowselect = function(taxon) {
					if (taxon.selected) {
						editSuperFund.push(taxon);
					} else {
						editSuperFund.pop(taxon);
					}
				}
				$scope.addTaxonSuperFund = function(actionType) {
					if (actionType == 'Edit' && editSuperFund <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = TaxOnSuperFundPopUpFactory.taxOnSuperFundPopupDetails(actionType, editSuperFund, taxTypeId);
						popupDetails.then(function(data) {
							$scope.superFund = data.taxOnSuperFundTOs;
							editSuperFund = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deteteTaxonSuperFund = function() {
					if (editSuperFund.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.superFund = [];
					} else {
						angular.forEach(editSuperFund, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						TaxonSuperFundService.deleteSuperfundTax(req).then(function(data) {
							GenericAlertService.alertMessage('TaxOnSuper Fund Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editSuperFund, function(value, key) {
							$scope.superFund.splice($scope.superFund.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('TaxOnSuper Fund Details are failed to Deactivate', "Error");
						});
						editSuperFund = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
