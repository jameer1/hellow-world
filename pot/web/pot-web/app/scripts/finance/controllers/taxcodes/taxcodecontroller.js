'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("taxcodes", {
		url : '/taxcodes',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/taxcodes/taxcodes.html',
				controller : 'TaxCodeController'
			}
		}
	})
}]).controller("TaxCodeController", ["$scope", "$q", "$state", "ngDialog", "$rootScope", "TaxCodeFactory", "TaxCodeService", "TaxCodeCountryFactService", "GenericAlertService", function($scope, $q, $state, ngDialog, $rootScope, TaxCodeFactory, TaxCodeService, TaxCodeCountryFactService, GenericAlertService) {
	var service = {}

	$scope.taxDetails = [];
	$scope.sortType="code"

	var taxcodeReq = {
		"status" : 1,
		"code" : null
	};

	$scope.getTaxDetails = function(taxId) {
		var taxcodeReq = {
			"status" : 1,
		};
		$scope.taxCodeCountryProvisions = [];
		TaxCodeService.getTaxCodes(taxcodeReq).then(function(data) {
			$scope.taxDetails = data.taxCodesTOs;
			if ($scope.taxDetails == null) {
				GenericAlertService.alertMessage("There are no records avaialable for the selected tax code", 'Warning');
			}
			/*else {
				GenericAlertService.alertMessage('tax codeare not available for given search criteria', "Warning");
			}*/
		});

	}

	$scope.resetTaxCodes = function() {
		$scope.code = [];
	},

	$scope.taxcodecalculation = [];

	var editTaxCode = [];
	var tax = [];

	$scope.taxcoderowselect = function(taxcode) {
		if (taxcode.selected) {
			editTaxCode.push(taxcode);
		
		} else {
			editTaxCode.pop(taxcode);
	
		}
	}, $scope.addTaxCode = function(actionType) {
		angular.forEach(editTaxCode,function(value,key){
			value.selected=false;
				});

		
		if(editTaxCode.length >0 && actionType=="Add"){
			editTaxCode=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}

		if (actionType == 'Edit' && editTaxCode <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else {
			var taxDetails = TaxCodeFactory.taxcodePopUpDetails(actionType, editTaxCode);
			editTaxCode = [];
			taxDetails.then(function(data) {
				$scope.taxDetails = data.taxCodesTOs;
				editTaxCode = [];
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
			})
		}
	}

	$scope.deteteTaxCode = function() {
		if (editTaxCode.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tax = [];
		} else {
			angular.forEach(editTaxCode, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"taxIds" : deleteIds,
				"status" : 2
			};
			TaxCodeService.deactivateTaxCodes(req).then(function(data) {
				$scope.taxDetails = data.taxCodesTOs;
				editTaxCode = [];
				$scope.deleteIds = [];
				GenericAlertService.alertMessage('TaxCode(s) is/are  Deactivated successfully', 'Info');

			}, function(error) {
				GenericAlertService.alertMessage('TaxCode(s) is/are failed to Deactivate', "Error");
			});

		}
	}

}]);
