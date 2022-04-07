'use strict';
app.factory('PersonalTaxFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PersonalTaxService", "PersonalTaxPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PersonalTaxService, PersonalTaxPopUpFactory, GenericAlertService) {
	var personalTax;
	var service = {};

	service.getPersonalTaxDetails = function(taxTypeId) {
		var deferred = $q.defer();
		var personalTaxReq = {
			"taxId" : taxTypeId,
			"status" : "1"
		};
		PersonalTaxService.getPersonalTaxRates(personalTaxReq).then(function(data) {
			var personalTax = [];
			personalTax = service.openPersonalTaxPopup(data.personalTaxTOs, taxTypeId);
			personalTax.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
		});
		return deferred.promise;

	}, service.openPersonalTaxPopup = function(personalTaxTOs, taxTypeId) {
		var deferred = $q.defer();
		personalTax = ngDialog.open({
			template : 'views/finance/taxtypes/personaltax.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editPersonalTax = [];
				$scope.personalTax = personalTaxTOs;

				$scope.personaltaxrowselect = function(personal) {
					if (personal.selected) {
						editPersonalTax.push(personal);
					} else {
						editPersonalTax.pop(personal);
					}
				}
				$scope.addPersonalTax = function(actionType) {
					if (actionType == 'Edit' && editPersonalTax <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = PersonalTaxPopUpFactory.personalTaxPopupDetails(actionType, editPersonalTax, taxTypeId);
						popupDetails.then(function(data) {
							$scope.personalTax = data.personalTaxTOs;
							editPersonalTax = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.detetePersonalTax = function() {
					if (editPersonalTax.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.personalTax = [];
					} else {
						angular.forEach(editPersonalTax, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						PersonalTaxService.deletePersonalTaxRates(req).then(function(data) {
							GenericAlertService.alertMessage('PersonalTax Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editPersonalTax, function(value, key) {
							$scope.personalTax.splice($scope.personalTax.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('PersonalTax Details are failed to Deactivate', "Error");
						});
						editPersonalTax = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);