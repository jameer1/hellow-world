'use strict';
app.factory('RevenueSaleFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,generalservice,assetidservice) {
	var revenueSalePopUp;
	var service = {};
	service.revenueSalePopUp = function(actionType,editRevenueValueData) {
		var deferred = $q.defer();
		revenueSalePopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/revenuesales/revenuesalespopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.addRevenueValueData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.revenueValue = [];
				var selectedRevenueValueData = [];
				$scope.currentstatusTypes = generalservice.accountStatus;
				$scope.paymentTerms = generalservice.paymentTermsTypes;
				$scope.revenueCycles = generalservice.revenueCycles;
				var assetReneval={
						"id" : null,
						"effectiveDate" : null,
						"buyerName" : null,
						"buyerAddress" : null,
						"totalSaleAmount" : null,
						"initialDepositAmount" : null,
						"remainingSaleAmount" : null,
						"paymentTermsForRemainingAmount" : null,
						"dueDateForSinglePayent" : null,
						"numberOfPartPayments" : null,
						"paymentCycleForInstallments" : null,
						"dueDatePerCycle" : null,
						"firstInstallmentDueDate" : null,
						"lastInstallmentsDueDate" : null,
						"principalAmountPerCycle" : null,
						"rateOfInterestPerAmount" : null,
						"grossAmountDuePerCycle" : null,
						"payerBankName" : null,
						"payerBankCode" : null,
						"payerBankAccount" : null,
						"accountStatus" : null,
						"status":1,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"fixedAssetid":$scope.fixedAssetid
					};


				if (actionType === 'Add') {
					$scope.revenueValue.push(assetReneval);
				} else {
					$scope.revenueValue = angular.copy(editRevenueValueData)
				}

				$scope.addRows = function() {
					$scope.revenueValue.push({
						"id" : null,
						"effectiveDate" : null,
						"buyerName" : null,
						"buyerAddress" : null,
						"totalSaleAmount" : null,
						"initialDepositAmount" : null,
						"remainingSaleAmount" : null,
						"paymentTermsForRemainingAmount" : null,
						"dueDateForSinglePayent" : null,
						"numberOfPartPayments" : null,
						"paymentCycleForInstallments" : null,
						"dueDatePerCycle" : null,
						"firstInstallmentDueDate" : null,
						"lastInstallmentsDueDate" : null,
						"principalAmountPerCycle" : null,
						"rateOfInterestPerAmount" : null,
						"grossAmountDuePerCycle" : null,
						"payerBankName" : null,
						"payerBankCode" : null,
						"payerBankAccount" : null,
						"accountStatus" : null,
						"status":1,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"fixedAssetid":$scope.fixedAssetid

					});
				},
    $scope.buyerDetails = function(asset){            
		var buyerdetailspopup = ngDialog.open({
			template : 'views/projresources/projassetreg/revenuesales/buyerdetails.html',
			showClose : false,
			className :'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				
				$scope.fixedAssetid = assetidservice.fixedAssetId;                 
				var buyerDetails = {
							"status" : 1,
							"fixedAssetid" : $scope.fixedAssetid
				};
				AssetRegisterService.getSalesRecord(buyerDetails).then(function(data) {
					$scope.salesRecordsDtlTO=data.salesRecordsDtlTOs;
					console.log(data.salesRecordsDtlTOs.buyerName,'details');
				});	
				$scope.selectRecord = function(salesDetails) {
					console.log(salesDetails);
					asset.buyerName = salesDetails.buyerName;
					asset.buyerAddress= salesDetails.buyerAddress;
					asset.totalSaleAmount =salesDetails.totalSalesAmount;
					buyerdetailspopup.close();
				}
				}]						
				});
      return deferred.promise;   
	},
	
				$scope.calcRemainingSaleAmount  = function(asset) {
					if(asset.totalSaleAmount!=undefined && asset.initialDepositAmount!=undefined){
					asset.remainingSaleAmount = parseFloat(asset.totalSaleAmount) - parseFloat(asset.initialDepositAmount) ;
					}
				},

				$scope.assetRevenueValuePopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedRevenueValueData.push(asset);
					} else {
						selectedRevenueValueData.pop(asset);
					}
				},

				$scope.paymentTermsType = function(asset) {
					if (asset.paymentTermsForRemainingAmount == 'Single') {
						asset.numberOfPartPayments = '';
						asset.paymentCycleForInstallments= '';
						asset.dueDatePerCycle = '';
						asset.firstInstallmentDueDate = '';
						asset.lastInstallmentsDueDate = '';
						asset.principalAmountPerCycle = '';
						asset.rateOfInterestPerAmount = '';
						asset.grossAmountDuePerCycle = '';
					} else {
						asset.dueDateForSinglePayent = '';
					}
				},

				$scope.deleteRows = function() {
					if (selectedRevenueValueData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedRevenueValueData.length < $scope.revenueValue.length) {
						angular.forEach(selectedRevenueValueData, function(value, key) {
							$scope.revenueValue.splice($scope.revenueValue.indexOf(value), 1);
						});
						selectedRevenueValueData = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedRevenueValueData.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedRevenueValueData.length == 1) {
						$scope.revenueValue[0] = {
								"selected" : false,
								"effectiveDate" : null,
								"buyerName" : null,
								"buyerAddress" : null,
								"totalSaleAmount" : null,
								"initialDepositAmount" : null,
								"remainingSaleAmount" : null,
								"paymentTermsForRemainingAmount" : null,
								"dueDateForSinglePayent" : null,
								"numberOfPartPayments" : null,
								"paymentCycleForInstallments" : null,
								"dueDatePerCycle" : null,
								"firstInstallmentDueDate" : null,
								"lastInstallmentsDueDate" : null,
								"principalAmountPerCycle" : null,
								"rateOfInterestPerAmount" : null,
								"grossAmountDuePerCycle" : null,
								"payerBankName" : null,
								"payerBankCode" : null,
								"payerBankAccount" : null,
								"accountStatus" : null,
								"status":1,
								"plantCommissioningDate":$scope.plantCommissioningDate,
								"fixedAssetid":$scope.fixedAssetid

						};
						selectedRevenueValueData = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},


				$scope.save = function() {


					var saveAssetRevenueSaleReq = {
						"revenueSalesDtlTOs":$scope.revenueValue
						}

					AssetRegisterService.saveRevenueSales(saveAssetRevenueSaleReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Rent Value Detail(s) Saved Successfully','Info');
						succMsg.then(function() {
							$scope.closeThisDialog();
						var returnPopObj = {
							"revenueSalesDtlTOs" : data.revenueSalesDtlTOs
						};
						deferred.resolve(returnPopObj);
					});
					}, function(error) {
						if (error.status != null && error.status != undefined) {
							GenericAlertService.alertMessageModal('Asset Rent Value Detail(s) is/are Failed to Save/Update ', "Error");
						}
					});

					ngDialog.close();

				}
			} ]
		});
		return deferred.promise;
	}

	service.revenueSalePopUpOnLoad = function(actionType, editRevenueValueData) {
		var deferred = $q.defer();
		var revenueSalePopUp = service.revenueSalePopUp(actionType, editRevenueValueData);
		revenueSalePopUp.then(function(data) {

			var returnPopObj = {
				"revenueSalesDtlTOs" : data.revenueSalesDtlTOs
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}

	return service;
}]);
