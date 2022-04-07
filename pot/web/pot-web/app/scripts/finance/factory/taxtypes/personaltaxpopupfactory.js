'use strict';
app.factory('PersonalTaxPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "PersonalTaxService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, PersonalTaxService, GenericAlertService) {
	var personalTaxPopup;
	var selectedPersonalTax = [];
	var service = {};
	service.personalTaxPopupDetails = function(actionType, editPersonalTax, taxTypeId) {
		var deferred = $q.defer();
		personalTaxPopup = ngDialog.open({
			template : 'views/finance/taxtypes/personaltaxpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedPersonalTax = [];
				$scope.personalList = [];

				if (actionType === 'Add') {
					$scope.personalList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						"annualMinTax" : '',
						"annualMaxTax" : '',
						"fixedTax" : '',
						"variableTax" : '',
						"comments" : '',

					});
				} else {
					$scope.personalList = angular.copy(editPersonalTax);
					editPersonalTax = [];
				}
				$scope.addPersonalTaxRows = function() {

					$scope.personalList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						"taxCalMstrId" : '',
						"annualMinTax" : '',
						"annualMaxTax" : '',
						"fixedTax" : '',
						"variableTax" : '',
						"comments" : '',
					});
				}, $scope.savepersonal = function() {
					var req = {
						"personalTaxTOs" : $scope.personalList,
					}
					blockUI.start();
					PersonalTaxService.savePersonalTaxRates(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('PersonalTax Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('PersonalTax Details  are failed to Save', 'Error');
					});
					ngDialog.close();
				};

				$scope.personalPopUpRowSelect = function(personal) {
					if (personal.selected) {
						selectedPersonalTax.push(personal);
					} else {
						selectedPersonalTax.pop(personal);
					}
				}, $scope.deleteProjRows = function() {
					if (selectedPersonalTax.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPersonalTax.length < $scope.personalList.length) {
						angular.forEach(selectedPersonalTax, function(value, key) {
							$scope.personalList.splice($scope.personalList.indexOf(value), 1);
						});
						selectedPersonalTax = [];
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