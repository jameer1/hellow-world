'use strict';
app.factory('RegularPayPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "RegularPayservice", "PayRollService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, RegularPayservice, PayRollService, GenericAlertService) {
	var regularPayPopup;
	var selectedRegularPay = [];
	var service = {};
	service.regularPayPopupDetails = function(actionType, editRegularPay, codeTypeCountryProvisionId) {
		var deferred = $q.defer();
		regularPayPopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/regularpaypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedRegularPay = [];
				$scope.addregularPayTOs = [];
				$scope.regularPayList = [];
				if (actionType === 'Add') {
					$scope.addregularPayTOs.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'regularLabelKeyTO' : {
							"name" : null
						},

					});
				} else {
					$scope.addregularPayTOs = angular.copy(editRegularPay);
					editRegularPay = [];
				}
				$scope.addRows = function() {

					$scope.addregularPayTOs.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'regularLabelKeyTO' : {
							"name" : null
						},

					});
				}, $scope.payPeriodCycle = function() {
					var dropDownReq = {
						"status" : "1"
					}
					PayRollService.getPayRollCycle(dropDownReq).then(function(data) {
						$scope.regularPayList = data.taxableTypeTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting RegularPay Detail(s)", 'Error');
					});
				}, $scope.saveregularPayRate = function() {
					var req = {
						"regularPayAllowanceTOs" : $scope.addregularPayTOs,
					}
					blockUI.start();
					RegularPayservice.saveregularPayRate(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('RegularPay Detail(s) is/are ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('RegularPay Detail(s) is/are failed to Save', 'Error');
					});
					$scope.closeThisDialog();
				};

				$scope.regularPayRatePopupRowSelect = function(regularPay) {
					if (regularPay.selected) {
						selectedRegularPay.push(regularPay);
					} else {
						selectedRegularPay.pop(regularPay);
					}
				}, $scope.deleteRows = function() {
					if (selectedRegularPay.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedRegularPay.length < $scope.addregularPayTOs.length) {
						angular.forEach(selectedRegularPay, function(value, key) {
							$scope.addregularPayTOs.splice($scope.addregularPayTOs.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedRegularPay = [];
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