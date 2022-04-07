'use strict';
app.factory('EmpProvidentFundFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, blockUI, EmpRegisterService, GenericAlertService) {
	var service = {};
	service.getEmpProvidentfundDetails = function(itemData) {
		var deferred = $q.defer();
		var req = {
			"id" : itemData.empProvidentFundTO.id,
			"countryId" : itemData.projGeneralLabelTO.displayNamesMap['country'],
			"provinceId" : itemData.projGeneralLabelTO.displayNamesMap['province'],
			"payTypeId" : 3
		};
	//	blockUI.start();
		EmpRegisterService.getEmpProvidentFundDetails(req).then(function(data) {
		//	blockUI.stop();
			var popupdata = service.openPopUp(itemData, data.empProvidentFundContributionTOs, data.empProvidentFundTaxTOs, data.empProvidentFundDetailTOs, data.providentFundCodeMap);
			popupdata.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
		//	blockUI.stop();
			GenericAlertService.alertMessage("Error occured while getting employee provident fund details", 'Error');
		});
		return deferred.promise;
	}, service.openPopUp = function(itemData, empProvidentFundContributionTOs, empProvidentFundTaxTOs, empProvidentFundDetailTOs, providentFundCodeMap) {
		var deferred = $q.defer();
		var empPopup = ngDialog.open({
			template : 'views/projresources/projempreg/empprovident/empprovidentpopup.html',
			closeByDocument : false,
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empDropDown = itemData.empDropDown;
				$scope.projEmpRegisterTO = {};
				$scope.projGeneralLabelTO = itemData.projGeneralLabelTO;
				$scope.empProvidentFundTO = angular.copy(itemData.empProvidentFundTO);
				$scope.empProvidentFundTO.empProvidentFundContributionTOs = empProvidentFundContributionTOs;
				$scope.empProvidentFundTO.empProvidentFundTaxTOs = empProvidentFundTaxTOs;
				$scope.empProvidentFundTO.empProvidentFundDetailTOs = empProvidentFundDetailTOs;
				var selectedFundDetails = [];
				$scope.providentFundCodeMap = providentFundCodeMap;
				var oldEffectiveFromDate = $scope.empProvidentFundTO.fromDate;


                $scope.maxdate = new Date($rootScope.selectedEmployeeData.projEmpRegisterTO.enrollmentDate);
                
				$scope.superFund = [];
				$scope.contributionId = [];
				$scope.accountStatus = [ {
					id : 1,
					name : "Current"
				}, {
					id : 2,
					name : "Superseded"
				} ];
				$scope.contributionTypes = [ {
					id : 1,
					name : "Variable Amount"
				}, {
					id : 2,
					name : "Fixed Amount"
				} ];
				$scope.validateBasiPayPercentage = function(type, empProvidentContributionTO) {
					if (type == 1) {
						empProvidentContributionTO.fixedAmount = null;
					} else {
						empProvidentContributionTO.percentage = null;
					}
				}, $scope.validateTaxBasiPayPercentage = function(type, empProvidentTaxTO) {
					if (type == 1) {
						empProvidentTaxTO.fixedAmount = null;
					} else {
						empProvidentTaxTO.percentage = null;
					}
				}, 
				$scope.calculateTotal = function(data){
					var totPercentage = 0;
					var totFixedAmount = 0;
					
					angular.forEach(data,function(value,key){
						if (value.percentage == null || value.percentage == undefined || value.percentage == "") {
							value.percentage = 0;
						}
						if (value.fixedAmount == null || value.fixedAmount == undefined || value.fixedAmount == "") {
							value.fixedAmount = 0;
						}
						totPercentage = totPercentage + parseFloat(value.percentage);
						totFixedAmount = totFixedAmount + parseFloat(value.fixedAmount);
						
					})
					return totPercentage+ totFixedAmount;
				}
				$scope.addFundDetails = function() {
					$scope.empProvidentFundTO.empProvidentFundDetailTOs.push({
						"status" : 1,
						"desc" : null,
						"details" : null,
						"comments" : null
					});
				}, $scope.rowSelect = function(fundDetail) {
					if (fundDetail.select) {
						selectedFundDetails.push(fundDetail);
					} else {
						selectedFundDetails.pop(fundDetail);
					}
				}, $scope.deleteFundDetails = function() {
					if (selectedFundDetails.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}
					if ($scope.empProvidentFundTO.empProvidentFundDetailTOs.length == 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						return;
					}
					if (selectedFundDetails.length < $scope.empProvidentFundTO.empProvidentFundDetailTOs.length) {
						angular.forEach(selectedFundDetails, function(value, key) {
							$scope.empProvidentFundTO.empProvidentFundDetailTOs.splice($scope.empProvidentFundTO.empProvidentFundDetailTOs.indexOf(value), 1);
						});
						selectedFundDetails = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.saveEmpProvidentFund = function() {
					if ($scope.empProvidentFundTO.id  > 0 ) {
						var oldFromDate = new Date(oldEffectiveFromDate);
						var fromDate = new Date($scope.empProvidentFundTO.fromDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
						if (days <= 0 ) {
							GenericAlertService.alertMessage("Please select  the new Period From date", 'Warning');
							return;
						}
						}
					var req = {
						"empProvidentFundTOs" : [ $scope.empProvidentFundTO ],
						"status" : 1,
						"projId" : $rootScope.selectedEmployeeData.projId,
						"empId" : $rootScope.selectedEmployeeData.id
					};
				//	blockUI.start();
					EmpRegisterService.saveEmpProvidentFunds(req).then(function(data) {
				//		blockUI.stop();
						var succMsg = GenericAlertService.alertMessageModal('Employee Provident Fund(s) is/are ' + data.message, data.status);
						succMsg.then(function() {
						$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
					//	blockUI.stop();
						GenericAlertService.alertMessage('Error occured while saving the employee provident fund details', "Error");
					});
				}
				/*
				 * $scope.checkDecimal = function(emp) { emp.split('.');
				 * 
				 * 
				 * 
				 * if (decimalInput[0].length > 2) {
				 * 
				 * GenericAlertService.alertMessage('Please enterdd Only digits
				 * with decimals', "Warning"); return; } if ((decimalInput[0] !=
				 * undefined && decimalInput[0].length > 2)) {
				 * 
				 * GenericAlertService.alertMessage('Please enter Only digits
				 * with decimals', "Warning"); return; }
				 *  }
				 * 
				 * 
				 */

			} ]
		});
		return deferred.promise;
	}
	return service;

}]);
