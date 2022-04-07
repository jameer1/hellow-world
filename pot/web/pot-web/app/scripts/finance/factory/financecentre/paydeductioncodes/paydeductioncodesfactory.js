'use strict';

app.factory('PayDeductionCodesFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var nonRegularPayAllowancePopup;
	var selectedNonRegularPayAllowance = [];
	var service = {};
	service.regularPayAllowanceDetails = function (actionType, countryName, financeCenterRecords) {
		var deferred = $q.defer();
		nonRegularPayAllowancePopup = ngDialog.open({
			template: 'views/finance/paydeductioncodes/paydeductioncodes.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				let editRegularPay = [];
				editRegularPay = financeCenterRecords.payDeductionCodes;
				$scope.action = actionType;
				if(actionType == 'View'){
					//console.log(actionType)
					//console.log(financeCenterRecords.payDeductionCodes)
					$scope.payDeductionsDisplayId = financeCenterRecords.payDeductionCodes[0].payDeductionsDisplayId;
				}
				else {

				$scope.countryName = financeCenterRecords.countryName;
				$scope.provisionName = financeCenterRecords.provisionName.adminName1;
				$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				}

				var selectedUnitPayRate = [];
				$scope.nonRegularPays = [];
				if (actionType === 'Add' && financeCenterRecords.payDeductionCodes == "") {
					$scope.nonRegularPays.push({
						"actionType": 'Add',
						'selected': false,
						'clientId': null,
						'code': null,
						'description': null,
						'isTaxable': null,
						'notes': null,
						'status': 1,
						'UnitOfPayDisplayId': null
					});
				} else if (actionType === 'Add' && financeCenterRecords.payDeductionCodes != "") {
					$scope.nonRegularPays = angular.copy(financeCenterRecords.payDeductionCodes);
					for (var i = 0; i < $scope.nonRegularPays.length; i++) {
						$scope.nonRegularPays[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'View';
					editRegularPay = [];

				} else if (actionType === 'View') {
					$scope.nonRegularPays = angular.copy(financeCenterRecords.payDeductionCodes);
					for (var i = 0; i < $scope.nonRegularPays.length; i++) {
						$scope.nonRegularPays[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'View';
					editRegularPay = [];
				}
				else {
					$scope.nonRegularPays = angular.copy(editRegularPay);
					for (var i = 0; i < $scope.nonRegularPays.length; i++) {
						$scope.nonRegularPays[i].actionType = 'Edit';
					}
					$scope.actionTypeSave = 'Edit';
					editRegularPay = [];
				}
				$scope.taxable = [{
					id: 1,
					name: "Yes"
				}, {
					id: 2,
					name: "No"
				}];
				$scope.addRows = function () {

					// $scope.editRegularPay.push({
					// 	'selected' : false,
					// 	'code' : null,
					// 	'description' : null,
					// 	'isTaxable' : null,
					// 	'notes' : null,
					// 	'status' : 1

					// });
				},

					$scope.saveUnitPayRate = function () {
						$scope.closeThisDialog();
						//console.log("====$scope.nonRegularPays====",$scope.nonRegularPays)
						deferred.resolve($scope.nonRegularPays);
					}

				$scope.unitPayRatePopupRowSelect = function (unitPay) {
					if (unitPay.selected) {
						selectedUnitPayRate.push(unitPay);
					} else {
						selectedUnitPayRate.pop(unitPay);
					}
				}, $scope.deleteRows = function () {
					if (selectedUnitPayRate.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedUnitPayRate.length < $scope.addUnitPayrates.length) {
						angular.forEach(selectedUnitPayRate, function (value, key) {
							$scope.addUnitPayrates.splice($scope.addUnitPayrates.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedUnitPayRate = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			}]

		});
		return deferred.promise;
	}
	return service;
}]);