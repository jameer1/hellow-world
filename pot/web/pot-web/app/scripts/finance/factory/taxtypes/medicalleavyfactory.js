'use strict';
app.factory('MedicalTaxFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MedicalTaxService", "MedicalTaxPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, MedicalTaxService, MedicalTaxPopUpFactory, GenericAlertService) {
	var medicalTax;
	var service = {};

	service.getMedicalTaxDetails = function(taxTypeId) {
		var deferred = $q.defer();
		var medicalTaxReq = {
			"taxId" : taxTypeId,
			"status" : "1"
		};
		MedicalTaxService.getMedicalLeaveTax(medicalTaxReq).then(function(data) {
			var medicalTax = [];
			medicalTax = service.openMedicalTaxPopup(data.medicalLeaveTaxTOs, taxTypeId);
			medicalTax.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
		});
		return deferred.promise;

	}, service.openMedicalTaxPopup = function(medicalLeaveTaxTOs, taxTypeId) {
		var deferred = $q.defer();
		medicalTax = ngDialog.open({
			template : 'views/finance/taxtypes/medicalleavetax.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editMedicalTax = [];
				$scope.medicalTax = medicalLeaveTaxTOs;

				$scope.medicaltaxrowselect = function(medical) {
					if (medical.selected) {
						editMedicalTax.push(medical);
					} else {
						editMedicalTax.pop(medical);
					}
				}
				$scope.addMedicalTax = function(actionType) {
					if (actionType == 'Edit' && editMedicalTax <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = MedicalTaxPopUpFactory.medicalTaxPopupDetails(actionType, editMedicalTax, taxTypeId);
						popupDetails.then(function(data) {
							$scope.medicalTax = data.medicalLeaveTaxTOs;
							editMedicalTax = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deteteMedicalTax = function() {
					if (editMedicalTax.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.medicalTax = [];
					} else {
						angular.forEach(editMedicalTax, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						MedicalTaxService.deleteMedicalLeaveTax(req).then(function(data) {
							GenericAlertService.alertMessage('MedicalTax Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editMedicalTax, function(value, key) {
							$scope.medicalTax.splice($scope.medicalTax.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('MedicalTax Details are failed to Deactivate', "Error");
						});
						editMedicalTax = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
