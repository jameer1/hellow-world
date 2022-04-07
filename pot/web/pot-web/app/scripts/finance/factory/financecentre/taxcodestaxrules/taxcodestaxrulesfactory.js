'use strict';

app.factory('TaxCodesTaxRulesFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "TaxCodesTaxRulesPastRecordsFactory","generalservice", "TaxCodeAndRulesFactory",
function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, TaxCodesTaxRulesPastRecordsFactory,generalservice, TaxCodeAndRulesFactory) {
	var taxCodesTaxRulesPopup;
	var selectedTaxCodesTaxRules = [];
	var service = {};
	service.taxCodesTaxRulesDetails = function (actionType, countryName, financeCenterRecords) {
		var deferred = $q.defer();
		taxCodesTaxRulesPopup = ngDialog.open({
			template: 'views/finance/taxcodetaxrules/taxcodetaxrules.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				let editTaxCodes = [];
				editTaxCodes = financeCenterRecords.taxCodesTaxRules;
				//console.log("financeCenterRecords.taxCodesTaxRules",financeCenterRecords.taxCodesTaxRules)
				//console.log("financeCenterRecords.taxRateRulesCodes",financeCenterRecords.taxRateRulesCodes)
				$scope.action = actionType;

				$scope.countryName = financeCenterRecords.countryName;
				$scope.provisionName = financeCenterRecords.provisionName;
				$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)

				var selectedTaxCodes = []; 
				$scope.taxCodes = [];
				if (actionType === 'Add' && financeCenterRecords.taxCodesTaxRules == "") {
					//console.log("ADD null value")
					$scope.taxCodes.push({
						"actionType": 'Add',
						'selected': false,
						'clientId': null,
						'code': null,
						'description': null,
						'isTaxable':null,
						'creditRunCycle':null,
						'creditRunDueDates':null,
						'applicablefor':null,
						'taxRateandRules':null
					});
				} else if (actionType === 'Add' && financeCenterRecords.taxCodesTaxRules != "") {
					//console.log("ADD with value")
					$scope.taxCodes = angular.copy(financeCenterRecords.taxCodesTaxRules);
					for (var i = 0; i < $scope.taxCodes.length; i++) {
						$scope.taxCodes[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'Edit';
					//$scope.actionTypeSave = 'View';
					editTaxCodes = [];

				} else if (actionType === 'View') {
					$scope.taxCodes = angular.copy(financeCenterRecords.taxCodesTaxRules);
					for (var i = 0; i < $scope.taxCodes.length; i++) {
						$scope.taxCodes[i].actionType = 'View';
					}
					$scope.actionTypeSave = 'View';
					editTaxCodes = [];
				}
				else {
					$scope.taxCodes = angular.copy(editTaxCodes);
					for (var i = 0; i < $scope.taxCodes.length; i++) {
						$scope.taxCodes[i].actionType = 'Edit';
					}
					$scope.actionTypeSave = 'Edit';
					editTaxCodes = [];
				}
				$scope.applicablefor = [{
					id: 1,
					name: "Individual"
				}, {
					id: 2,
					name: "Company"
				}];
				
				$scope.creditRunCycles = [ {
					id : 1,
					name : "Weekly"
				}, {
					id : 2,
					name : "Fortnightly"
				},
				{
					id : 3,
					name : "Monthly"
				},
				{
					id : 4,
					name : "Quarterly"
				},
				{
					id : 5,
					name : "Half Yearly"
				},
				{
					id : 6,
					name : "Yearly"
				}
				];
                    $scope.displayFundCreditCycle = function(taxCodesTaxRule) {
					
					if(taxCodesTaxRule.creditRunCycle == "Weekly")
					{
						taxCodesTaxRule.creditRunDueDates = generalservice.weeakDays
					}else if(taxCodesTaxRule.creditRunCycle == "Fortnightly")
					{
						taxCodesTaxRule.creditRunDueDates = generalservice.weeakDays
					}else if(taxCodesTaxRule.creditRunCycle == "Monthly")
					{
						taxCodesTaxRule.creditRunDueDates = generalservice.monthly
					}else if(taxCodesTaxRule.creditRunCycle == "Quarterly")
					{
						taxCodesTaxRule.creditRunDueDates = generalservice.years
					}
					else if(taxCodesTaxRule.creditRunCycle == "Half Yearly")
					{
						taxCodesTaxRule.creditRunDueDates = generalservice.years
					}
					else if(taxCodesTaxRule.creditRunCycle == "Yearly")
					{
						taxCodesTaxRule.creditRunDueDates = generalservice.years
					}
				}
				$scope.addRows = function () {

					 $scope.taxCodes.push({
					 	'selected' : false,
						'clientId': null,
						'code': null,
						'description': null,
						'isTaxable':null,
						'creditRunCycle':null,
						'creditRunDueDates':null,
						'taxRateandRules':null,
						'applicablefor':null,
					 	'status' : 1

					 });
				},

					$scope.savetaxCodesTaxRules = function () {
						$scope.closeThisDialog();
						deferred.resolve($scope.taxCodes);
					}

				$scope.taxCodesTaxRulesPopupRowSelect = function (taxCodes) {
					if (taxCodes.selected) {
						selectedTaxCodes.push(taxCodes);
					} else {
						selectedTaxCodes.pop(taxCodes);
					}
				}, $scope.deleteRows = function () {
					if (selectedTaxCodes.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedTaxCodes.length < $scope.taxCodes.length) {
						angular.forEach(selectedTaxCodes, function (value, key) {
							$scope.taxCodes.splice($scope.taxCodes.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedTaxCodes = [];
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
						},
						function (error) {
							GenericAlertService.alertMessage("Error occurred while getting Unit of Pay Rate Details",'Info');
						});

					var popupPastRecords = TaxCodesTaxRulesPastRecordsFactory.taxCodesTaxRulesPastRecordsPopupDetails($scope.countryName, $scope.financeCenterRecordsData);
					popupPastRecords.then(function (data) {

					},
						function (error) {
							GenericAlertService.alertMessage("Error occurred while getting Tax Code Tax Rules Details",'Info');
						});
				}
				// Tax rate and rules popup
				$scope.taxRateAndRules = function () {
				//console.log("taxRateAndRules========")
					    var popupTaxRateAndRules = TaxCodeAndRulesFactory.taxCodesandRulesPopupDetails();
						//console.log("popupTaxRateAndRules==")
						popupTaxRateAndRules.then(function (data) {
						//console.log("popupUnitOfPayRate==",popupUnitOfPayRate)
						//console.log("data==",data)
						$scope.taxRateandRules=data;
						//console.log("$scope.taxRateandRules==",$scope.taxRateandRules)
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting taxRate And Rules details", "Error");
					});
				}
			}]

		});
		return deferred.promise;
	}
	return service;
}]);