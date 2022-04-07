'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("taxcodetypes", {
		url : '/taxcodetypes',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/taxcodetypes/taxcodetype.html',
				controller : 'TaxCodeTypeController'
			}
		}
	})
}]).controller("TaxCodeTypeController", ["$rootScope", "$scope", "$state", "ngDialog", "TaxCodeCountryFactService", "TaxCountryFactory", "GenericAlertService", "RegularPayservice", "NonRegularPayService", "TaxTypeService", "PayDeductionService", "PayRollService", "TaxPaymentService", "SuperFundService", "RegularPayFactory", "TaxCodeService", "TaxCodeCountryService", "NonRegularPayedFactory", "PayDeductionFactory", "PayRollFactory", "PaymentReceiverFactory", "TaxCodeTypePopUpFactory", "SuperFundFactory", function($rootScope, $scope, $state, ngDialog, TaxCodeCountryFactService, TaxCountryFactory, GenericAlertService, RegularPayservice, NonRegularPayService, TaxTypeService, PayDeductionService
	, PayRollService, TaxPaymentService, SuperFundService, RegularPayFactory, TaxCodeService,TaxCodeCountryService, NonRegularPayedFactory, PayDeductionFactory, PayRollFactory, PaymentReceiverFactory, TaxCodeTypePopUpFactory, SuperFundFactory) {
	$scope.taxCodeCountryProvisions = [];
	$scope.sortType='codeTypeMstrTO.name',
	$scope.sortReverse=false;
	var editTaxCodeType = [];
	$scope.countryProvision = {
		"id" : null,
		"code" : null,
		"countryId" : null,
		"countryName" : null,
		"provisionLabelKeyTO" : {
			"id" : null,
			"code" : null,
			"name" : null
		},
		"fromDate" : null,
		"toDate" : null,
		"effectiveDate" : null
	};
	$scope.countryProvisionObj = {};
	var taxcodeReq = {
		"status" : 1,
		"name" : null
	};
	$scope.getTaxCodeTypeDetails = function(taxId) {
		if (taxId == undefined || taxId == null) {
			GenericAlertService.alertMessage("Please select tax code to get the tax type details", 'Warning');
			return;
		}
		var getTaxReq = {
			"status" : '1',
			"taxId" : taxId
		};
		$scope.codeTypesTOs = [];
		TaxTypeService.getCodeTypes(getTaxReq).then(function(data) {
			$scope.codeTypesTOs = data.codeTypesTOs;
			if ($scope.codeTypesTOs == null || $scope.codeTypesTOs.length <= 0) {
				GenericAlertService.alertMessage("There are no records avaialable for the selected tax code", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");

		});
	},
	$scope.getCountryProvisionDetails = function(countryProvision) {
		var getTaxReq = {
			"countryId" : countryProvision.countryId,
			"id":countryProvision.id,
			"status" : '1'
		};
		
		$scope.taxCountryProvisionTOs = [];
		TaxCodeCountryService.getTaxCountryProvision(getTaxReq).then(function(data) {
			$scope.countryProvisions = data.taxCountryProvisionTOs;
			$scope.countryMap = data.countryMap;
			$scope.provisionMap = data.provisionMap;
			$scope.getProvisionDetails(countryProvision);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");
		});

	},
	$scope.rowSelect = function(taxTypeTO) {
		if (taxTypeTO.select) {
			editTaxCodeType.push(taxTypeTO);
		} else {
			editTaxCodeType.pop(taxTypeTO);
		}
	},

	$scope.addTaxCodeType = function(actionType, taxId) {
		if (taxId == null || taxId == undefined) {
			GenericAlertService.alertMessage("Please select Country & Province", 'Warning');
			return;
		}
		if (actionType == 'Edit' && editTaxCodeType <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else {
			var existingCodeTypesMap=[];
			angular.forEach($scope.codeTypesTOs,function(value,key)
			{
				existingCodeTypesMap.push(value.financeCodeType);
			});
			var popupDetails = TaxCodeTypePopUpFactory.taxcodetypeDetails(taxId,editTaxCodeType,existingCodeTypesMap);
			popupDetails.then(function(data) {
				$scope.codeTypesTOs = data.codeTypesTOs;
				editTaxCodeType = [];
			},

			function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Error');
			})
		}
	}

	$scope.getCountryDetails = function(countryProvisionObj) {
		countryProvisionObj.id = null;
		countryProvisionObj.code = null;
		countryProvisionObj.provisionLabelKeyTO = {
			"id" : null,
			"code" : null,
			"name" : null
		};
		countryProvisionObj.fromDate = null;
		countryProvisionObj.toDate = null;
		countryProvisionObj.effectiveDate = null;
		var popupDetails = TaxCountryFactory.getCountryDetails();
		popupDetails.then(function(data) {
			countryProvisionObj.countryName = data.selectedCountry.countryName;
			countryProvisionObj.countryId = data.selectedCountry.id;
			$scope.provisions = data.selectedCountry.provisionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
		})
	}, $scope.getProvisionDetails = function(countryProvisionObj) {
		var taxCodeDetail = TaxCodeCountryFactService.getTaxCodeProvisions(countryProvisionObj.countryName,$scope.provisionMap);
		taxCodeDetail.then(function(data) {
			countryProvisionObj.id = data.selectedDetails.id;
			countryProvisionObj.code = data.selectedDetails.code;
			countryProvisionObj.provisionLabelKeyTO.id = data.selectedDetails.provisionLabelKeyTO.id;
			countryProvisionObj.provisionLabelKeyTO.code = data.selectedDetails.provisionLabelKeyTO.code;
			countryProvisionObj.fromDate = data.selectedDetails.fromDate;
			countryProvisionObj.toDate = data.selectedDetails.toDate;
			countryProvisionObj.effectiveDate = data.selectedDetails.effectiveDate;

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");

		});
	}, $scope.getCodeTypeDetails = function(taxTypeTO) {
		var taxTypeId = taxTypeTO.codeTypeMstrTO.id;
		var taxCodeTypeId = taxTypeTO.id;
		if (taxTypeId == 1) {
			var details = RegularPayFactory.getRegularPayDetails(taxCodeTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting RegularPay details", 'Info');
			})

		} else if (taxTypeId == 2) {
			var details = NonRegularPayedFactory.getNonRegularPayDetails(taxCodeTypeId);
			details.then(function(data) {

			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Non RegularPay  details", 'Info');
			})

		} else if (taxTypeId == 3) {
			var details = PayRollFactory.getPayRollDetails(taxCodeTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting PayRoll details", 'Info');
			})

		} else if (taxTypeId == 4) {
			var details = PayDeductionFactory.getPayDeductionDetails(taxCodeTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Pay deduction details", 'Info');
			})

		} else if (taxTypeId == 5) {
			var details = PaymentReceiverFactory.getPaymentReceiverDetails(taxCodeTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Pay deduction details", 'Info');
			})

		} else if (taxTypeId == 6) {
			var details = SuperFundFactory.getSuperProvidentFundDetails(taxCodeTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Pay deduction details", 'Info');
			})

		}
	}
	$scope.reset = function() {
		$scope.countryProvision = {
			"id" : null,
			"code" : null,
			"countryId" : null,
			"countryName" : null,
			"provisionLabelKeyTO" : {
				"id" : null,
				"code" : null,
				"name" : null
			},

		};
		$scope.codeTypesTOs=[];
	},
	$scope.deleteTaxCodeType = function() {
		if (editTaxCodeType.length <= 0) {
			GenericAlertService.alertMessage( "Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.codeTypesTOs = [];
		} else {
			angular.forEach(editTaxCodeType, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"taxIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning','YES','NO').then(function() {
				TaxTypeService.deleteTaxCodeTypes(req).then(function(data) {
			});
			GenericAlertService.alertMessageModal('Tax Code Type(s) is/are   Deactivated successfully', 'Info');
			angular.forEach(editTaxCodeType, function(value, key) {
				$scope.codeTypesTOs.splice($scope.codeTypesTOs.indexOf(value), 1);
				editTaxCodeType = [];
				$scope.deleteIds = [];
			}, function(error) {
				GenericAlertService.alertMessage('Tax Code Type(s) is/are  failed to Deactivate', "Error");
			});
			},function(data){
				angular.forEach(editTaxCodeType, function(value) {
					value.selected = false;
			})
			})
			

		}
	}

}]);
