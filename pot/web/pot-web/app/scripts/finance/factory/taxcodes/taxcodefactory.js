'use strict';
app.factory('TaxCodeFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "TaxCodeService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout,blockUI, TaxCodeService, GenericAlertService,
	generalservice) {
	var taxCodePopUp;
	var service = {};
	service.taxcodePopUpDetails = function(actionType, editTaxCode) {
		var deferred = $q.defer();
		taxCodePopUp = ngDialog.open({
			template : 'views/finance/taxcodes/taxcodepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedTaxCode = [];
				$scope.taxcodeList = [];
				$scope.taxcode = {"taxType" : ""};
				$scope.taxTypes = generalservice.taxTypes;

				if (actionType === 'Add') {
					$scope.taxcodeList.push({
						"selected" : false,
						"code" : '',
						"name" : '',
						"taxType" : "",
						"status" : '1'

					});
				} else {
					$scope.taxcodeList = angular.copy(editTaxCode);
					editTaxCode = [];
				}

				$scope.addTaxCodeRows = function() {
					$scope.taxcodeList.push({
						"selected" : false,
						"code" : '',
						"name" : '',
						"taxType" : '',
						"status" : '1'

					});
				},
				$scope.getTaxCodesMap = function() {
					var req = {

					}
					TaxCodeService.getTaxCodesMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				}, $scope.checkDuplicate = function(taxcode) {
					taxcode.duplicateFlag = false;
					taxcode.code = taxcode.code.toUpperCase();
					if ($scope.uniqueCodeMap[taxcode.code] != null) {
						taxcode.duplicateFlag = true;
						return;
					}
					taxcode.duplicateFlag = false;
				},
				$scope.taxcodePopUpRowSelect = function(taxcode) {
					if (taxcode.selected) {
						selectedTaxCode.push(taxcode);
					} else {
						selectedTaxCode.pop(taxcode);
					}
				}, 
				$scope.deleteProjRows = function() {
					if (selectedTaxCode.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedTaxCode.length < $scope.taxcodeList.length) {
						angular.forEach(selectedTaxCode, function(value, key) {
							$scope.taxcodeList.splice($scope.taxcodeList.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedTaxCode = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				
				$scope.saveTaxCodes = function() {
					
					var flag = false;
					var taxCodeMap = [];
					angular.forEach($scope.taxcodeList, function(value, key) {
						if (taxCodeMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							taxCodeMap[value.code.toUpperCase()] = true;
						}
					});
					angular.forEach($scope.taxcodeList, function(value, key) {
						if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						}  
					});
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Tax codes are not allowed', "Warning");
						return;
					}
					
					
					
					var req = {
						"taxCodesTOs" : $scope.taxcodeList
					}
					blockUI.start();
					TaxCodeService.saveTaxCodes(req).then(function(data) {
						blockUI.stop();
						$scope.closeThisDialog();
						var returnPopObj = {
							"taxCodesTOs" : data.taxCodesTOs
						};
						deferred.resolve(returnPopObj);
						GenericAlertService.alertMessage('TaxCode  Details are  ' + data.message, data.status);
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('TaxCode  Details are Failed to Save ', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
