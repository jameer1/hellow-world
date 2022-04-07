'use strict';

app.factory('TaxPaymentsReceiverDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "TaxPaymentsReceiverDetailsPastRecordsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, TaxPaymentsReceiverDetailsPastRecordsFactory) {
	var taxPaymentsReceiverPopup;
	var selectedNonRegularPayAllowance = [];
	var service = {};
	service.taxPaymentsReceiverDetails = function (actionType, countryName, financeCenterRecords) {
		var deferred = $q.defer();
		taxPaymentsReceiverPopup = ngDialog.open({
			template: 'views/finance/taxpaymentsreceiverdetails/taxpaymentsreceiverdetails.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				let editTaxPaymentsReceiver = [];
				editTaxPaymentsReceiver = financeCenterRecords.taxPaymentsReceivers;
				//console.log('editTaxPaymentsReceiver',editTaxPaymentsReceiver)
				//editTaxPaymentsReceiver = financeCenterRecords.taxPaymentsReceivers;
				$scope.action = actionType;

				
					//console.log($scope.countryCode)
					
					//console.log($scope.provisionName)
					//console.log($scope.provisionName)
					if(actionType == 'View'){
					
					$scope.taxPaymentReceiverDisplayId = financeCenterRecords.taxPaymentsReceivers[0].taxPaymentReceiverDisplayId;
				}
				
				$scope.countryName = financeCenterRecords.countryName;
				$scope.provisionName = financeCenterRecords.provisionName;
				$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				$scope.countryCode = financeCenterRecords.countryCode;
				
				var selectedTaxPaymentsReceiver = [];
				$scope.taxPaymentsReceivers = []; 
				if (actionType === 'Add' && financeCenterRecords.taxPaymentsReceivers == "") {
					//console.log("=======1=======")
					$scope.taxPaymentsReceivers.push({
						"actionType": 'Add',
						'selected': false,
						'clientId': null,
						'code': null,
						'description': null,
						'nameofDepartment':null,
						'registerdAddress':null,
						'accountNumber':null,
						'bankInstituteName':null,
						'bankCode':null
						
					});
				} else if (actionType === 'Add' && financeCenterRecords.taxPaymentsReceivers != "") {
					$scope.taxPaymentsReceivers = angular.copy(financeCenterRecords.taxPaymentsReceivers);
					for (var i = 0; i < $scope.taxPaymentsReceivers.length; i++) {
						$scope.taxPaymentsReceivers[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'Edit';
					//$scope.actionTypeSave = 'View';
					editTaxPaymentsReceiver = [];

				} else if (actionType === 'View') {
					$scope.taxPaymentsReceivers = angular.copy(financeCenterRecords.taxPaymentsReceivers);
					for (var i = 0; i < $scope.taxPaymentsReceivers.length; i++) {
						$scope.taxPaymentsReceivers[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'View';
					editTaxPaymentsReceiver = [];
				}
				else {
					$scope.taxPaymentsReceivers = angular.copy(editTaxPaymentsReceiver);
					for (var i = 0; i < $scope.taxPaymentsReceivers.length; i++) {
						$scope.taxPaymentsReceivers[i].actionType = 'Edit';
					}
					$scope.actionTypeSave = 'Edit';
					editTaxPaymentsReceiver = [];
				}
				$scope.taxable = [{
					id: 1,
					name: "Yes"
				}, {
					id: 2,
					name: "No"
				}];
				$scope.addRows = function () {
                    
					 $scope.taxPaymentsReceivers.push({
					 	'selected' : false,
					    'code' : null,
					 	'description' : null,
					 	'nameofDepartment' : null,
					 	'registerdAddress' : null,
					 	'accountNumber' : null,
						'bankInstituteName' : null,
					  	'bankCode' : null,					
					 	'status' : 1

					 });
				},
						

					$scope.saveTaxPaymentsReceiver = function () {
						//console.log("===saveTaxPaymentsReceiver====")
						//console.log("===$scope.taxPaymentsReceivers====",$scope.taxPaymentsReceivers);
						$scope.taxPaymentRecDup = $scope.taxPaymentsReceivers;
						//console.log("===$scope.taxPaymentRecDup====",$scope.taxPaymentRecDup)
						$scope.closeThisDialog();
						deferred.resolve($scope.taxPaymentsReceivers);
					}

				$scope.taxPaymentsReceiverPopupRowSelect = function (taxPaymentsReceivers) {
					//if (taxPaymentsReceivers.selected) {
						if (taxPaymentReceiver.selected) {
						selectedTaxPaymentsReceiver.push(taxPaymentsReceivers);
					} else {
						selectedTaxPaymentsReceiver.pop(taxPaymentsReceivers);
					}
				}, $scope.deleteRows = function () {
					if (selectedTaxPaymentsReceiver.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedTaxPaymentsReceiver.length < $scope.addUnitPayrates.length) {
						angular.forEach(selectedTaxPaymentsReceiver, function (value, key) {
							$scope.taxPaymentsReceivers.splice($scope.taxPaymentsReceivers.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedTaxPaymentsReceiver = [];
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
						$scope.financeCenterRecordsData
						var popupUnitOfPayRate = TaxPaymentsReceiverDetailsPastRecordsFactory.taxPaymentsReceiverDetailsPastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
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