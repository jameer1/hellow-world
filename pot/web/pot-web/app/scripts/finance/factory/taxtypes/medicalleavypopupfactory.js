'use strict';
app.factory('MedicalTaxPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "MedicalTaxService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, MedicalTaxService, GenericAlertService) {
	var medicalTaxPopup;
	var selectedMedicalTax = [];
	var service = {};
	service.medicalTaxPopupDetails = function(actionType, editMedicalTax, taxTypeId) {
		var deferred = $q.defer();
		medicalTaxPopup = ngDialog.open({
			template : 'views/finance/taxtypes/medicalleavypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedMedicalTax = [];
				$scope.medicalList = [];

				if (actionType === 'Add') {
					$scope.medicalList.push({
						"selected" : false,
						"taxCodeCountryProvisionId" : taxTypeId,
						"annualMinTax" : '',
						"annualMaxTax" : '',
						"fixedTax" : '',
						"variableTax" : '',
						"comments" : '',
						"status" : '1'
					});
				} else {
					$scope.medicalList = angular.copy(editMedicalTax);
					editMedicalTax = [];
				}
				$scope.addmedicalTaxRows = function() {

					$scope.medicalList.push({
						"selected" : false,
						"taxCodeCountryProvisionId" : taxTypeId,
						"annualMinTax" : '',
						"annualMaxTax" : '',
						"fixedTax" : '',
						"variableTax" : '',
						"comments" : '',
						"status" : '1'

					});
				}, $scope.savemedicaltax = function() {
					var req = {
						"medicalLeaveTaxTOs" : $scope.medicalList,
					}
					blockUI.start();
					MedicalTaxService.saveMedicalLeaveTax(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('MedicalTax Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('MedicalTax Details  are failed to Save', 'Error');
					});
					ngDialog.close();
				};

				$scope.medicalPopUpRowSelect = function(medical) {
					if (medical.selected) {
						selectedMedicalTax.push(medical);
					} else {
						selectedMedicalTax.pop(medical);
					}
				}, $scope.deleteProjRows = function() {
					if (selectedMedicalTax.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedMedicalTax.length < $scope.medicalList.length) {
						angular.forEach(selectedMedicalTax, function(value, key) {
							$scope.medicalList.splice($scope.medicalList.indexOf(value), 1);
						});
						selectedMedicalTax = [];
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