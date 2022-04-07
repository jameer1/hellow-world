'use strict';

app.factory('TaxCountryMappingFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "TaxCodeCountryService", "PayRollService", "GenericAlertService", "TaxCodeService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, TaxCodeCountryService, PayRollService, GenericAlertService, TaxCodeService) {
	var getCountry;
	var taxCodeservice = {};
	taxCodeservice.getTaxCodeDetails = function(taxId, editCountry, existingTaxTypesMap) {
		var deferred = $q.defer();

		var taxcodeReq = {
			"status" : 1,
			"taxId" : taxId
		};

		TaxCodeCountryService.getTaxCodeCountryProvisionsOnload(taxcodeReq).then(function(data) {
			var taxCodeSerivcePopup = taxCodeservice.openTaxCodePoupup(data.taxCodeCountryProvisionTOs, taxId, editCountry, existingTaxTypesMap);
			taxCodeSerivcePopup.then(function(data) {
				var returnPopObj = {
					"taxCodeCountryProvisionTOs" : data.taxCodeCountryProvisionTOs,
					"periodTypes" : data.periodTypes

				};
				deferred.resolve(returnPopObj);

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			})

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode Details", "Error");
		});
		return deferred.promise;

	},

	taxCodeservice.openTaxCodePoupup = function(taxCodeCountryProvisionTOs, taxId, editCountry, existingTaxTypesMap) {
		var deferred = $q.defer();
		ngDialog.open({
			template : 'views/finance/taxtypes/taxcodemappingpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			scope : $rootScope,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.existingTaxTypesMap = existingTaxTypesMap;
				var selectedTaxCodes = [];
				$scope.taxmappingDetails = []
				$scope.periodCycles = []
				$scope.taxmappingDetails = taxCodeCountryProvisionTOs;

				$scope.init = function() {
					var req = {
						"status" : 1
					};
					TaxCodeCountryService.getTaxCodeCountryProvisionsOnload(req).then(function(data) {
						$scope.periodCycles = data.periodCycles;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Pay Period Details", "Error");
					});
				}
				$scope.init();

				$scope.selectedTaxCodes = function(taxcodemapping) {

					if (taxcodemapping.select) {

						selectedTaxCodes.push(taxcodemapping);

					} else {
						selectedTaxCodes.pop(taxcodemapping);
					}

				}, $scope.saveTaxCodeDetails = function() {
					var req = {
						"taxCodeCountryProvisionTOs" : selectedTaxCodes,
						"taxId" : taxId
					};
					blockUI.start();
					TaxCodeCountryService.saveTaxCodeCountryProvisions(req).then(function(data) {
						blockUI.stop();
						var message = GenericAlertService.alertMessage('Tax Code Country provision detail ' + data.message, "Info");
						var returnPopObj = {
							"taxCodeCountryProvisionTOs" : data.taxCodeCountryProvisionTOs
						}
						$scope.closeThisDialog();
						deferred.resolve(returnPopObj);
					}, function (error) {
							blockUI.stop();
						});
				}

			} ]
		});
		return deferred.promise;

	}
	return taxCodeservice;
}]);