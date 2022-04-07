'use strict';
app.factory('NonRegularPayPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "NonRegularPayService", "PayRollService", "TaxCodeService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, NonRegularPayService, PayRollService, TaxCodeService, GenericAlertService) {
	var nonRegularPayPopup;
	var selectedNonRegularPay = [];
	var service = {};
	service.nonRegularPayPopupDetails = function(actionType, editNonRegularPay, codeTypeCountryProvisionId) {
		var deferred = $q.defer();
		nonRegularPayPopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/nonregularpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedNonRegularPay = [];
				$scope.addNonRegularPayTOs = [];
				$scope.nonRegularPayList = [];
				$scope.taxNamesList = [];

				if (actionType === 'Add') {
					$scope.addNonRegularPayTOs.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'nonRegularLabelKeyTO' : {
							"id":null,
							"name" : null
						}

					});
				} else {
					$scope.addNonRegularPayTOs = angular.copy(editNonRegularPay);
					editNonRegularPay = [];
				}
				$scope.addRows = function() {

					$scope.addNonRegularPayTOs.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'nonRegularLabelKeyTO' : {
							"id":null,
							"name" : null
						}

					});
				}, $scope.payPeriodCycle = function() {
					var dropDownReq = {
						"status" : "1"
					}
					PayRollService.getPayRollCycle(dropDownReq).then(function(data) {
						$scope.nonRegularPayList = data.taxableTypeTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting pay period cycles", 'Error');
					});
				}, $scope.taxTypes = function() {
					var dropDownReq = {
						"taxId" : 1,
						"status" : "1"
					}
					TaxCodeService.getTaxCodes(dropDownReq).then(function(data) {
						$scope.taxNamesList = data.taxCodesTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting tax types", 'Error');
					});
				}, $scope.saveNonRegularPay = function() {
					var req = {
						"nonRegularPayAllowanceTOs" : $scope.addNonRegularPayTOs,
					}
					blockUI.start();
					NonRegularPayService.saveNonRegularPay(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('NonRegularPay Detail(s) is/are ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('NonRegular Pay Detail(s) is/are failed to Save', 'Error');
					});
					$scope.closeThisDialog();
				};

				$scope.popUpRowSelect = function(nonRegularPay) {
					if (nonRegularPay.selected) {
						selectedNonRegularPay.push(nonRegularPay);
					} else {
						selectedNonRegularPay.pop(nonRegularPay);
					}
				}, $scope.deleteRows = function() {
					if (selectedNonRegularPay.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedNonRegularPay.length < $scope.addNonRegularPayTOs.length) {
						angular.forEach(selectedNonRegularPay, function(value, key) {
							$scope.addNonRegularPayTOs.splice($scope.addNonRegularPayTOs.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedNonRegularPay = [];
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