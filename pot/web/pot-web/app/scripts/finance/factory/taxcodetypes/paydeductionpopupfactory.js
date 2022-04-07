'use strict';
app.factory('PayDeductionPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "PayDeductionService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, blockUI, PayDeductionService, GenericAlertService) {
	var payDeductionPopup;
	var selectedRegularPay = [];
	var service = {};
	service.payDeductionPopupDetails = function(actionType, editpayDeduction, codeTypeCountryProvisionId) {
		var deferred = $q.defer();
		payDeductionPopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/paydeductionpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedPayDeduction = [];
				$scope.addPayDeductionCodes = [];

				if (actionType === 'Add') {
					$scope.addPayDeductionCodes.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,

					});
				} else {
					$scope.addPayDeductionCodes = angular.copy(editpayDeduction);
					editpayDeduction = [];
				}
				$scope.addRows = function() {

					$scope.addPayDeductionCodes.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,

					});
				}, $scope.savePayDeductions = function() {
					var req = {
						"payDeductionTOs" : $scope.addPayDeductionCodes,
					}
					blockUI.start();
					PayDeductionService.savePayDeductions(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('PayDeduction Detail(s) is/are ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage(' Paydeduction Detail(s)  is/are failed to Save', 'Error');
					});
					$scope.closeThisDialog();
				};

				$scope.popUpRowSelect = function(payDeduction) {
					if (payDeduction.selected) {
						selectedPayDeduction.push(payDeduction);
					} else {
						selectedPayDeduction.pop(payDeduction);
					}
				}, $scope.deleteRows = function() {
					if (selectedPayDeduction.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPayDeduction.length < $scope.addPayDeductionCodes.length) {
						angular.forEach(selectedPayDeduction, function(value, key) {
							$scope.addPayDeductionCodes.splice($scope.addPayDeductionCodes.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedPayDeduction = [];
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