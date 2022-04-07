'use strict';
app.factory('MortgagePaymentFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,generalservice,assetidservice) {
	var mortgagePopUp;
	var service = {};
	service.mortgagePopUp = function(actionType, editMortgageeValueData) {
		var deferred = $q.defer();
		mortgagePopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/mortgagepayments/mortgagepaymentspopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {

				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.addMortgageeValueData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.mortgageeValue = [];
				var selectedMortgageeValueData = [];
				$scope.accountStatusTypes = generalservice.accountStatus;
				$scope.paymentCycles = ["Daily","Weekly","Fortnightly","Monthly","Quarterly","Half Yearly","Yearly"]; // generalservice.pymentCycles;
				$scope.weekTOs = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
				$scope.monthsTOs = generalservice.monthly;
				$scope.monthTOs = generalservice.years;
				var addMortgagee = {
						"id" : null,
						"effectiveDate":null,
						"mortgageeName":null,
						"mortgageeAdress":null,
						"originalLoanPrincipalAmount":null,
						"remainingLoanPrinicipalAmount":null,
						"rateOfInterestPerAnnum":null,
						"paymentCycle": '',//null,
						"paymentCycleDueDate":'', //null,
						"paymentAmountPerCycel":null,
						"taxAmount":null,
						"netAmount":null,
						"receiverBank":null,
						"receiverBankCode":null,
						"receiverBankAC":null,
						"accountStatus":null,
						"status" : "1",
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"fixedAssetid" : $scope.fixedAssetid
					};

				if (actionType === 'Add') {
					$scope.mortgageeValue.push(addMortgagee);
				} else {
					$scope.mortgageeValue = angular.copy(editMortgageeValueData)
				}

				$scope.addRows = function() {
					$scope.mortgageeValue.push({
						"id" : null,
						"effectiveDate":null,
						"mortgageeName":null,
						"mortgageeAdress":null,
						"originalLoanPrincipalAmount":null,
						"remainingLoanPrinicipalAmount":null,
						"rateOfInterestPerAnnum":null,
						"paymentCycle":null,
						"paymentCycleDueDate":null,
						"paymentAmountPerCycel":null,
						"taxAmount":null,
						"netAmount":null,
						"receiverBank":null,
						"receiverBankCode":null,
						"receiverBankAC":null,
						"accountStatus":null,
						"status" : "1",
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"fixedAssetid" : $scope.fixedAssetid

					});
				},

				$scope.assetMortgageeValuePopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedMortgageeValueData.push(asset);
					} else {
						selectedMortgageeValueData.pop(asset);
					}
				},

				$scope.calNetAmount = function(asset){
					asset.netAmount = parseInt(asset.paymentAmountPerCycel)-parseInt(asset.taxAmount);
				}
				var orgAmt=0;
				var remAmut = 0;
                $scope.validateRemainingQuantity = function(){
                angular.forEach($scope.mortgageeValue,function(value,key){
                   orgAmt=value.originalLoanPrincipalAmount;
                                            
                }) 
                angular.forEach($scope.mortgageeValue, function(value, key) {
						if (!isNaN(value.remainingLoanPrinicipalAmount) && value.remainingLoanPrinicipalAmount != null) {
							remAmut = value.remainingLoanPrinicipalAmount;
				
						}
					});
                 if (parseInt(orgAmt) >= parseInt(remAmut)) {
                 
						$scope.qtyMismatch = false;
					} else {
						$scope.qtyMismatch = true;
						GenericAlertService.alertMessage("Remaining Amount should not greater than  Original Amount", "Warning");
					}          
                }
                                               
                var taxAmnt=0;
				var payAmt =0;
                $scope.validateNetAmount = function(){
                angular.forEach($scope.mortgageeValue,function(value,key){
                  payAmt=value.paymentAmountPerCycel;                                          
                }) 
                angular.forEach($scope.mortgageeValue, function(value, key) {
						if (!isNaN(value.taxAmount) && value.taxAmount != null) {
							taxAmnt = value.taxAmount;							
						}
					});
                 if (parseInt(payAmt) >= parseInt(taxAmnt)) {
                 
						$scope.netAmtMismatch = false;
					} else {
						$scope.netAmtMismatch = true;
						GenericAlertService.alertMessage("Tax Amount should not greater than  Gross Amount", "Warning");
					}          
                }
                
              //console.log(asset.paymentAmountPerCycel,'popup');
				$scope.deleteRows = function() {
					if (selectedMortgageeValueData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedMortgageeValueData.length < $scope.mortgageeValue.length) {
						angular.forEach(selectedMortgageeValueData, function(value, key) {
							$scope.mortgageeValue.splice($scope.mortgageeValue.indexOf(value), 1);
						});
						selectedMortgageeValueData = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedMortgageeValueData.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedMortgageeValueData.length == 1) {
						$scope.mortgageeValue[0] = {
							"status" : "1",
							"selected" : false,
							"effectiveDate":null,
							"mortgageeName":null,
							"mortgageeAdress":null,
							"originalLoanPrincipalAmount":null,
							"remainingLoanPrinicipalAmount":null,
							"rateOfInterestPerAnnum":null,
							"paymentCycle":null,
							"paymentCycleDueDate":null,
							"paymentAmountPerCycel":null,
							"taxAmount":null,
							"netAmount":null,
							"receiverBank":null,
							"receiverBankCode":null,
							"receiverBankAC":null,
							"accountStatus":null,
							"plantCommissioningDate":$scope.plantCommissioningDate,
							'fixedAssetid' : $scope.fixedAssetid

						};
						selectedMortgageeValueData = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},                    
				$scope.save = function() {					
				if ($scope.qtyMismatch == true) {
						GenericAlertService.alertMessage('Remaining Amount should not greater than  Original Amount', "Warning");
							return;
					}
					
					if ($scope.netAmtMismatch == true) {
						GenericAlertService.alertMessage('Tax Amount should not greater than  Gross Amount', "Warning");
							return;
					}        
					var saveAssetMortgageReq = {
						"mortgageValueDtlTOs" : $scope.mortgageeValue
					}
					AssetRegisterService.saveMortgageePayments(saveAssetMortgageReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Mortgage(s) Saved successfully','Info');
						succMsg.then(function() {
							var returnPopObj = {
								"mortgageValueDtlTOs" : data.mortgageValueDtlTOs,
							};

							deferred.resolve(returnPopObj);
						});

					}, function(error) {

						if (error.message != null && error.status != null) {
							GenericAlertService.alertMessageModal('Asset Mortgage details is/are failed to Save/Update', "Error");
						}
					});

					ngDialog.close();
				}
			} ]
		});
		return deferred.promise;
	}

	service.mortgagePopUpOnLoad = function(actionType, editMortgageeValueData) {
		var deferred = $q.defer();
		var mortgagePopUp = service.mortgagePopUp(actionType, editMortgageeValueData);
		mortgagePopUp.then(function(data) {

			var returnPopObj = {
				"mortgageValueDtlTOs" : data.mortgageValueDtlTOs
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}

	return service;
}]);
