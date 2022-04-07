'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("taxtypes", {

		url : '/taxtypes',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/taxtypes/taxcodecountrymapping.html',
				controller : 'TaxTypeController'
			}
		}
	})
}]).controller("TaxTypeController", ["$scope", "$q", "$state", "ngDialog", "TaxCountryFactory", "TaxCodeService", "TaxCountryMappingFactory", "TaxCodeCountryService", "CompanyTaxService", "TaxCodeCountryFactService", "CompanyTaxFactory", "CompanyTaxPopUpFactory", "PersonalTaxFactory", "PersonalTaxService", "MedicalTaxFactory", "MedicalTaxService", "EmpPayRollTaxFactory", "EmployeePayRollTaxService", "TaxonSuperfundFactory", "TaxonSuperFundService", "GoodsTaxPaymentFactory", "GoodsTaxService", "GenericAlertService", "CountryService", function($scope, $q, $state, ngDialog, TaxCountryFactory,/* TaxProvinceFactory, */
TaxCodeService, TaxCountryMappingFactory, TaxCodeCountryService, CompanyTaxService, TaxCodeCountryFactService, 
CompanyTaxFactory, CompanyTaxPopUpFactory, PersonalTaxFactory, PersonalTaxService, MedicalTaxFactory,
 MedicalTaxService, EmpPayRollTaxFactory, EmployeePayRollTaxService, TaxonSuperfundFactory, 
 TaxonSuperFundService, GoodsTaxPaymentFactory, GoodsTaxService, GenericAlertService, CountryService) {
	var editCountry = [];
	$scope.taxCodeCountryProvisions = [];
	$scope.provisionMap = [];
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
	$scope.provisions = [];
	$scope.sortType="taxCodesTO.code"
	$scope.searchProject = {};
	$scope.getTaxDetails = function(taxId) {
		if (taxId == undefined || taxId == null) {
			GenericAlertService.alertMessage("Please select tax code to get the tax type details", 'Warning');
			return;
		}
		var getTaxReq = {
			"status" : '1',
			"taxId" : taxId
		};
		
		TaxCodeCountryService.getTaxCodeCountryProvisions(getTaxReq).then(function(data) {
			$scope.taxCodeCountryProvisions = data.taxCodeCountryProvisionTOs;
			if ($scope.taxCodeCountryProvisions == null || $scope.taxCodeCountryProvisions.length <= 0) {
				GenericAlertService.alertMessage("There are no records avaialable for the selected tax code", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");

		});
	}

	$scope.resetTaxDetails = function() {
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
		$scope.taxCodeCountryProvisions = [];
	},

	$scope.taxrowselect = function(taxcodemapping) {
		if (taxcodemapping.selected) {
			editCountry.push(taxcodemapping);
			$("#row-taxcodemapping-"+taxcodemapping.taxCodesTO.code).css({
				background:"skyblue"
			});
		} else {
			editCountry.pop(taxcodemapping);
			$("#row-taxcodemapping-"+taxcodemapping.taxCodesTO.code).css({
				background:""
			});
		}
	}, $scope.addTaxmapping = function(actionType, taxId) {
		if(editCountry.length >0 && actionType=="Add"){
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		if (actionType == 'Edit' && editCountry <= 0) {
			GenericAlertService.alertMessagse("Please select atleast one row to modify", 'Warning');
			return;
		} else {
			var existingTaxTypesMap=[];
			angular.forEach($scope.taxCodeCountryProvisions,function(value,key)
			{
				existingTaxTypesMap[value.taxCodesTO.id]=true;
			});
			
			var popupDetails = TaxCountryMappingFactory.getTaxCodeDetails(taxId, editCountry, existingTaxTypesMap);
			popupDetails.then(function(data) {
				$scope.taxCodeCountryProvisions = data.taxCodeCountryProvisionTOs;
				$scope.periodTypes = data.periodTypes;
				editCountry = [];
			},

			function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Error');
			})
		}
	}
	// company popup
	$scope.getTaxType = function(taxcodemapping) {
		var taxType = taxcodemapping.taxCodesTO.id;

		var taxTypeId = taxcodemapping.id;
		if (taxType == 1) {
			var details = PersonalTaxFactory.getPersonalTaxDetails(taxTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting companydetails details", 'Error');
			})

		} else if (taxType == 2) {
			var details = CompanyTaxFactory.getCompanyDetails(taxTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting companydetails details", 'Error');
			})

		} else if (taxType == 3) {
			var details = MedicalTaxFactory.getMedicalTaxDetails(taxTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting companydetails details", 'Error');
			})

		} else if (taxType == 4) {
			var details = EmpPayRollTaxFactory.getPayRollTaxDetails(taxTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting companydetails details", 'Error');
			})

		} else if (taxType == 5) {
			var details = TaxonSuperfundFactory.getTaxOnSuperFundDetails(taxTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting companydetails details", 'Error');
			})

		} else if (taxType == 6) {
			var details = GoodsTaxPaymentFactory.getGoodsPaymentDetails(taxTypeId);
			details.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting companydetails details", 'Error');
			})

		}

	},
	// country popup get
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
			const req = { "geonameId": data.selectedCountry.geonameId };
			CountryService.getProvince(req).then(function (data) {
				$scope.provisions = data.provisionTOs;
			}, function (error) {
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
		})
	},
	$scope.getCountryProvisionDetails = function(countryProvision) {
		var getTaxReq = {
			"countryId" : countryProvision.countryId,
			"id":countryProvision.id,
			"status" : '1'
		};
		alert("re"+JSON.stringify(getTaxReq));
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

	$scope.getProvisionDetails = function(countryProvisionObj) {
		var taxCodeDetail = TaxCodeCountryFactService.getTaxCodeProvisions(countryProvisionObj.countryName,
			$scope.provisionMap);
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
	}
	$scope.taxcodemapping = [];

	$scope.deteteTaxmapping = function() {
		if (editCountry.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.taxCodeCountryProvisions = [];
		} else {
			angular.forEach(editCountry, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"taxIds" : deleteIds,
				"status" : 2
			};
			TaxCodeCountryService.deactivateTaxCodeCountryProvisions(req).then(function(data) {
				GenericAlertService.alertMessage('TaxType Details are  Deactivated successfully', 'Info');
			});

			angular.forEach(editCountry, function(value, key) {
				$scope.taxCodeCountryProvisions.splice($scope.taxCodeCountryProvisions.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('TaxType Details are failed to Deactivate', "Error");
			});
			editCountry = [];
			$scope.deleteIds = [];

		}
	}

}]);