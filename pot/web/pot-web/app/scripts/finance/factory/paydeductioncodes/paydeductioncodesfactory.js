'use strict';

app.factory('PayDeductionCodesFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "PayDeductionCodesPastRecordsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, PayDeductionCodesPastRecordsFactory) {
	var payDeductionCodesPopup;
	var selectedPayDeductionCodes = [];
	var service = {};
	service.payDeductionCodesDetails = function (actionType, countryName, financeCenterRecords) {
		var deferred = $q.defer();
		payDeductionCodesPopup = ngDialog.open({
			template: 'views/finance/paydeductioncodes/paydeductioncodes.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				let editpayDeductions = [];
				editpayDeductions = financeCenterRecords.payDeductionCodes;
				$scope.action = actionType;
				if(actionType == 'View'){
					//console.log(actionType)
					//console.log(financeCenterRecords.payDeductionCodes)
					$scope.payDeductionsDisplayId = financeCenterRecords.payDeductionCodes[0].payDeductionsDisplayId;
				}
				
				$scope.countryName = financeCenterRecords.countryName;
				$scope.provisionName = financeCenterRecords.provisionName;
				$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
			
				$scope.payDeductions = [];
				var selectedpayDeductions = [];
				if (actionType === 'Add' && financeCenterRecords.payDeductionCodes == "") {
					$scope.payDeductions.push({
						"actionType": 'Add',
						'selected': false,
						'clientId': null,
						'code': null,
						'description': null
					});
				} else if (actionType === 'Add' && financeCenterRecords.payDeductionCodes != "") {
					$scope.payDeductions = angular.copy(financeCenterRecords.payDeductionCodes);
					//console.log("$scope.payDeductions",$scope.payDeductions)
					for (var i = 0; i < $scope.payDeductions.length; i++) {
						$scope.payDeductions[i].actionType = 'View';
					}
					//$scope.actionTypeSave = 'View';
					$scope.actionTypeSave = 'Edit';
					//console.log("$scope.actionTypeSave",$scope.actionTypeSave)
					editpayDeductions = [];

				} else if (actionType === 'View') {
					//console.log("1",actionType)
					$scope.payDeductions = angular.copy(financeCenterRecords.payDeductionCodes);
					//console.log("2",actionType)
					for (var i = 0; i < $scope.payDeductions.length; i++) {
						//console.log("3",$scope.payDeductions.length)
						$scope.payDeductions[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'View';
					//console.log("4",$scope.actionTypeSave)
					editpayDeductions = [];
					//console.log("5")
				}
				else {
					$scope.payDeductions = angular.copy(editpayDeductions);
					for (var i = 0; i < $scope.payDeductions.length; i++) {
						$scope.payDeductions[i].actionType = 'Edit';
					}
					$scope.actionTypeSave = 'Edit';
					editpayDeductions = [];
				}
				
				$scope.addRows = function () {

					 $scope.payDeductions.push({
					 	'selected' : false,
					 	'code' : null,
						'description' : null,
					 	'status' : 1

					 });
				},

					$scope.savePayDeduction = function () {
						$scope.closeThisDialog();
						//console.log("===$scope.payDeductions=====",$scope.payDeductions)
						deferred.resolve($scope.payDeductions);
					}

				$scope.payDeductionPopupRowSelect = function (payDeduction) {
					if (payDeduction.selected) {
						selectedpayDeductions.push(payDeduction);
					} else {
						selectedpayDeductions.pop(payDeduction);
					}
				}, $scope.deleteRows = function () {
					if (selectedpayDeductions.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedpayDeductions.length < $scope.payDeductions.length) {
						angular.forEach(selectedpayDeductions, function (value, key) {
							$scope.payDeductions.splice($scope.payDeductions.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedpayDeductions = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				// view past records 
				$scope.viewpastrecords = function () {

					var financeCenterFilterReq = {
						"status": 1,
		       			"countryCode": $scope.countryCode,
						"provisionCode": $scope.provisionCode,
						//$scope.countryId
					};
					UnitPayRateService.getUnitOfRate(financeCenterFilterReq).then(function (data) {
						$scope.projCostStmtDtls = data.financeCenterRecordTos;
						$scope.financeCenterRecordsData = data.financeCenterRecordTos;
						//console.log("$scope.financeCenterRecordsData",$scope.financeCenterRecordsData) 
						//console.log("$scope.projCostStmtDtls",$scope.projCostStmtDtls) 
						//$scope.financeCenterRecordsData
						var popupUnitOfPayRate = PayDeductionCodesPastRecordsFactory.payDeductionCodesPastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
						//console.log("popupUnitOfPayRate",popupUnitOfPayRate)
						popupUnitOfPayRate.then(function (data) {
							//console.log("popupUnitOfPayRate==",popupUnitOfPayRate)
							//console.log("data==",data)
	
						},
						function (error) {
							GenericAlertService.alertMessage("Error occurred while getting Unit of Pay Rate Details",'Info');
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting finance center details", "Error");
					});
				}
			}]

		});
		return deferred.promise;
	}
	return service;
}]);