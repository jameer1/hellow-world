'use strict';

app.factory('TaxCodeAndRulesFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var taxCodesTaxRulesPopup;
	var service = {};
	service.taxCodesandRulesPopupDetails = function () {
		var deferred = $q.defer();
		taxCodesTaxRulesPopup = ngDialog.open({
			template: 'views/finance/taxcodetaxrules/taxcodeandrulesdetails.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.taxRatesRulesCodeDetails = [];
			    //console.log("addRows")
                $scope.saveTaxRatesRules = function () {
				//console.log($scope.minRange);
							
						//console.log("saveTaxRatesRules")
						$scope.taxRatesRulesCodeDetailsData={
					 	'id': '',
						'type': '',
						'notes':$scope.notes,
						'minRange':$scope.minRange,
						'maxRange':$scope.maxRange,
						'taxAmount':$scope.taxAmount,
						'taxPercentage':$scope.taxPercentage,
					 	'status' : 1

					 }	
                          //console.log("====$scope.saveTaxAndRules====") 
						  $scope.taxRatesRulesCodeDetails.push($scope.taxRatesRulesCodeDetailsData);
					      $scope.closeThisDialog();
						  deferred.resolve($scope.taxRatesRulesCodeDetails);
						  //console.log("====$scope.taxRatesRulesCodeDetails===data=",$scope.taxRatesRulesCodeDetails) 
				} 
			}]

		});
		return deferred.promise;
	}
	return service;
}]);