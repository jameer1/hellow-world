'use strict';

app.factory('UnitPayRateFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var unitPayRatePopup;
	var selectedUnitPayRate = [];
	var service = {};
	service.unitPayPopupDetails = function(actionType,countryName,financeCenterRecords) {
		var deferred = $q.defer();
		unitPayRatePopup = ngDialog.open({
			template : 'views/finance/unitofpay/unitpayratespopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				let editUnitPayRate = [];
			 editUnitPayRate = financeCenterRecords.unitOfPayRates;
				$scope.action = actionType;
				if(actionType == 'View'){
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName.adminName1;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				}
				else {
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName.adminName1;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				}
				var selectedUnitPayRate = [];
				$scope.unitOfPayRates = [];
				if (actionType === 'Add' && financeCenterRecords.unitOfPayRates =="") {
					$scope.unitOfPayRates.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'code' : null,
						'name' : null,
						'notes' : null,
						'status' : 1,
						'UnitOfPayDisplayId':null
					});
				} else if (actionType === 'Add' && financeCenterRecords.unitOfPayRates!=""){
					$scope.unitOfPayRates = angular.copy(financeCenterRecords.unitOfPayRates);
					for ( var i=0; i< $scope.unitOfPayRates.length;i++ ){
					$scope.unitOfPayRates[i].actionType='Edit';
				     }
					$scope.actionTypeSave='Edit';
					editUnitPayRate = [];
					
				}else if(actionType === 'View') {
					$scope.unitOfPayRates = angular.copy(financeCenterRecords.unitOfPayRates);
					for ( var i=0; i< $scope.unitOfPayRates.length;i++ ){
					$scope.unitOfPayRates[i].actionType='View';
				     }
				     $scope.actionTypeSave='View';
					editUnitPayRate = [];
				}
				else {
					$scope.unitOfPayRates = angular.copy(editUnitPayRate);
					for ( var i=0; i< $scope.unitOfPayRates.length;i++ ){
					$scope.unitOfPayRates[i].actionType='Edit';
				     }
					$scope.actionTypeSave='Edit';
					editUnitPayRate = [];
				}
				$scope.addRows = function() {

					$scope.unitOfPayRates.push({
						'selected' : false,
						'code' : null,
						'name' : null,
						'notes' : null,
						'status' : 1

					});
				},

				$scope.saveUnitPayRate = function() {
					$scope.closeThisDialog();
					deferred.resolve($scope.unitOfPayRates);
				}

				$scope.unitPayRatePopupRowSelect = function(unitPay) {
					if (unitPay.selected) {
						selectedUnitPayRate.push(unitPay);
					} else {
						selectedUnitPayRate.pop(unitPay);
					}
				}, $scope.deleteRows = function() {
					if (selectedUnitPayRate.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedUnitPayRate.length < $scope.addUnitPayrates.length) {
						angular.forEach(selectedUnitPayRate, function(value, key) {
							$scope.addUnitPayrates.splice($scope.addUnitPayrates.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedUnitPayRate = [];
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