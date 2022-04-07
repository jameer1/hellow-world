'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("taxcountryprovison", {

		url : '/taxcountryprovison',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/taxcountryprovision/countryprovision.html',
				controller : 'TaxCountryController'
			}
		}
	})
}]).controller("TaxCountryController", ["$scope", "$q", "$state", "ngDialog", "TaxCodeCountryFactory", "TaxCountryFactory", "TaxCodeCountryService", "CountryProvisionFactory", "TaxCodeCountryFactService", "GenericAlertService", function($scope, $q, $state, ngDialog, TaxCodeCountryFactory, TaxCountryFactory, TaxCodeCountryService, CountryProvisionFactory, TaxCodeCountryFactService, GenericAlertService) {
	var editTaxCountry = [];
	$scope.countryProvisions = [];
	$scope.provisions = [];
	$scope.countryMap = [];
	$scope.provisionMap = [];
	$scope.countryProvision = {
		"id" : null,
		"countryCode" : null,
		"countryName": "",
		"countryLabelKeyTO" : {
			"id" : null,
			"code" : null,
			"name" : null
		},
		"proisionLabelKeyTO" : {
			"id" : null,
			"code" : null,
			"name" : null
		},
		"fromDate" : null,
		"toDate" : null,
		"effectiveDate" : null
	};
// TODO On search fetch countries from getCountryProvisions
	$scope.getCountryProvisionDetails = function(countryProvision) {
		/*if (countryProvision.id == undefined || countryProvision.id == null) {
			GenericAlertService.alertMessage("Please select tax code to get the tax type details", 'Warning');
			return;
		}*/
		var getTaxReq = {
			"countryName" : countryProvision.countryName,
			"id":countryProvision.id,
			"status" : '1'
		};
		$scope.taxCountryProvisionTOs = [];
		TaxCodeCountryService.getTaxCountryProvision(getTaxReq).then(function(data) {
			$scope.countryProvisions = data.taxCountryProvisionTOs;
			$scope.countryMap = data.countryMap;
			$scope.provisionMap = data.provisionMap;
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");
		});

	},
	

	$scope.getProvisionDetails = function(countryProvisionObj) {

		$scope.countryProvisions = [];
		var taxCodeDetail = TaxCodeCountryFactService.getTaxCodeProvisions(countryProvisionObj.countryId);
		taxCodeDetail.then(function(data) {
			countryProvisionObj.id = data.selectedDetails.id;
			countryProvisionObj.code = data.selectedDetails.code;
			countryProvisionObj.provisionLabelKeyTO.id = data.selectedDetails.proisionLabelKeyTO.id;
			countryProvisionObj.provisionLabelKeyTO.code = data.selectedDetails.proisionLabelKeyTO.code;
			countryProvisionObj.fromDate = data.selectedDetails.fromDate;
			countryProvisionObj.toDate = data.selectedDetails.toDate;
			countryProvisionObj.effectiveDate = data.selectedDetails.effectiveDate;
			$scope.taxCodeMap();

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");

		});
	},

	$scope.resetTaxDetails = function() {
		$scope.countryProvision = {
			"id" : null,
			"code" : null,
			"countryLabelKeyTO" : {
				"id" : null,
				"code" : null,
				"name" : null
			},
			"proisionLabelKeyTO" : {
				"id" : null,
				"code" : null,
				"name" : null
			},
			"fromDate" : null,
			"toDate" : null,
			"effectiveDate" : null
		};

		$scope.req = {
			"name" : null,
			"province" : null,
			"effectiveDate" : null
		}
		$scope.countryProvisions = [];

	},

	$scope.taxrowselect = function(taxcodes) {
		if (taxcodes.selected) {
			editTaxCountry.push(taxcodes);
		} else {
			editTaxCountry.pop(taxcodes);
		}
	}, $scope.addCoutryProvisions = function(actionType) {
		angular.forEach(editTaxCountry,function(value,key){
			value.selected=false;
				});

		if(editTaxCountry.length >0 && actionType=="Add"){
			editTaxCountry=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		if(actionType == 'Add' && $scope.countryProvisions!= null){
			$scope.resetTaxDetails();

			var popupDetails = CountryProvisionFactory.countryPopUpDetails(actionType, editTaxCountry, $scope.countryMap,$scope.provisionMap);
			editTaxCountry = [];
			popupDetails.then(function(data) {
				$scope.countryProvisions = data.taxCountryProvisionTO;
				$scope.countryMap = data.countryMap;
				$scope.provisionMap = data.provisionMap;
				editTaxCountry = [];
				
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while adding country provision details", 'Error');
		});
		}
		else if (actionType == 'Edit' && editTaxCountry <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else {
			var popupDetails = CountryProvisionFactory.countryPopUpDetails(actionType, editTaxCountry, $scope.countryMap,$scope.provisionMap);
			editTaxCountry = [];
			popupDetails.then(function(data) {
				$scope.countryProvisions = data.taxCountryProvisionTO;
				$scope.countryMap = data.countryMap;
				
				editTaxCountry = [];
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while adding country provision details", 'Error');
			})
		}

	}, $scope.getCountryDetails = function(countryProvisionObj) {

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
			countryProvisionObj.countryCode = data.selectedCountry.countryCode;
			$scope.provisions = data.selectedCountry.provisionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
		})
	}, $scope.getCountryDetailsById = function(countryProvisionObj) {
		var popupDetails = TaxCountryFactory.getCountryDetails();
		popupDetails.then(function(data) {
			countryProvisionObj.countryName = data.selectedCountry.name;
			countryProvisionObj.countryId = data.selectedCountry.id;
			$scope.provisions = data.selectedCountry.provisionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
		})
	};

}]);