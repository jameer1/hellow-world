'use strict';

app.factory(
		'TaxCodeSelectFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "TaxCodeCountryService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, TaxCodeCountryService, GenericAlertService) {
			var service = {};
			service.getTaxCodes = function() {
				var deferred = $q.defer();
				var req = {
					"status" : 1
				};
				blockUI.start();
				TaxCodeCountryService.getTaxCodeCountryProvisionsOnload(req).then(
						function(data) {
							blockUI.stop();
							var popupdata = service.openPopup(data.taxCodeCountryProvisionTOs);
							popupdata.then(function(resultData) {
								deferred.resolve(resultData);
							});
						},
						function(error) {
							blockUI.stop();
							GenericAlertService.alertMessage(
									"Error occured while getting  TaxCode Details", "Error");
						});
				return deferred.promise;

			}, service.openPopup = function(taxCodeCountryProvisionTOs) {
				var deferred = $q.defer();
				var epsProjTreePopup = ngDialog.open({
					template : 'views/finance/taxcodeselectpopup.html',
					className : 'ngdialog-theme-plain ng-dialogueCustom4',
					closeByDocument : false,
					showClose : false,
					controller : [ '$scope', function($scope) {
						var selectedTaxCode = [];
						$scope.taxCodesDetails =[];
						$scope.taxCodeCountryProvisionTOs = taxCodeCountryProvisionTOs;
						
						angular.forEach($scope.taxCodeCountryProvisionTOs,function(value,key){
							$scope.taxCodesDetails.push(value.taxCodesTO)
						})
					$scope.selectedTax = function(taxCode) {
							var returnPopObj = {
								"taxCodesDetails" : taxCode
							};
							deferred.resolve(returnPopObj);
							$scope.closeThisDialog();
						}

				} ]
				});
				return deferred.promise;
			};

			return service;

		}])