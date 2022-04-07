'use strict';
app.factory('CountryProvisionFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "GenericAlertService", "TaxCountryFactory", "TaxCodeCountryService", "CountryService", function(ngDialog, $q, $filter, $timeout,blockUI, GenericAlertService, 
	TaxCountryFactory, TaxCodeCountryService, CountryService) {
	var TaxPopUp;
	var service = {};
	service.countryPopUpDetails = function(actionType, editTaxCountry, countryMap ,provisionMap) {
		var deferred = $q.defer();
		TaxPopUp = ngDialog.open({
			template : 'views/finance/taxcountryprovision/countryprovisionpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.countryProvisionObj = [];
				$scope.countryMap = countryMap;
				$scope.provisionMap=provisionMap
				$scope.countryProvisionObj = {
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
					"fromDate" : '',
					"toDate" : '',
					"effectiveDate" : ''
				};

				if (actionType === 'Edit') {
					$scope.countryProvisionObj = angular.copy(editTaxCountry[0]);
					$scope.countryProvisionObj.proisionLabelKeyTO.name=	$scope.provisionMap[editTaxCountry[0].proisionLabelKeyTO.id].name
					$scope.countryProvisionObj.proisionLabelKeyTO.id=$scope.provisionMap[editTaxCountry[0].proisionLabelKeyTO.id].id
					$scope.countryProvisionObj.proisionLabelKeyTO.code=$scope.provisionMap[editTaxCountry[0].proisionLabelKeyTO.id].code
					$scope.countryProvisionObj.countryLabelKeyTO.name=$scope.countryMap[editTaxCountry[0].countryLabelKeyTO.id].name
					$scope.countryProvisionObj.countryLabelKeyTO.id=$scope.countryMap[editTaxCountry[0].countryLabelKeyTO.id].id
					$scope.countryProvisionObj.countryLabelKeyTO.code=$scope.countryMap[editTaxCountry[0].countryLabelKeyTO.id].code
					editTaxCountry = [];
				}

				
				$scope.getCountryDetails = function(countryLabelKeyTO) {
					var popupDetails = TaxCountryFactory.getCountryDetails();

					popupDetails.then(function(data) {
						countryLabelKeyTO.code = data.selectedCountry.countryCode;
						countryLabelKeyTO.name = data.selectedCountry.countryName;
						countryLabelKeyTO.id = data.selectedCountry.id;
						
						const req = { "geonameId": data.selectedCountry.geonameId };
						CountryService.getProvince(req).then(function (data) {
							$scope.provisions = data.provisionTOs;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting Province Details", "Error");
						});

						$scope.taxCodeMap();
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while selecting countryprovision Details", 'Info');
					})
				}, $scope.updateProvision = function(proisionLabelKeyTO, countryProvisionObj) {
					proisionLabelKeyTO.id = countryProvisionObj.proisionLabelKeyTO.id;
					proisionLabelKeyTO.code = countryProvisionObj.proisionLabelKeyTO.code;
					proisionLabelKeyTO.name = countryProvisionObj.proisionLabelKeyTO.name;
					
					
				},
				$scope.taxCodeMap=function(){
					var req={
							
					}
					TaxCodeCountryService.getTaxCountryProvisionMap(req).then(function(data) {
						
						$scope.taxCodeCountryUniqueCodeMap=data.taxCodeCountryUniqueCodeMap;
						
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting taxcode  Details", "Error");
					});
					
				},
				$scope.$watch('countryProvisionObj.effectiveDate', function (newValue, oldValue, scope) {
				  
				  $scope.effectDate=newValue;
				 
				
				}, true);
				$scope.$watch('countryProvisionObj.fromDate', function (newValue, oldValue, scope) {
					  
					  $scope.fromDate=newValue;
					 
					
					}, true);
				/*$scope.checkDuplicate = function(taxcode) {
					taxcode.duplicateFlag = false;
					taxcode.countryId = taxcode.countryLabelKeyTO.id;
					taxcode.provisionId = taxcode.proisionLabelKeyTO.id;
					
					var unique = taxcode.countryId + "  ,  " + taxcode.provisionId+ "  , " + $scope.effectDate ;
				
					if ($scope.taxCodeCountryUniqueCodeMap[unique] != null) {
						taxcode.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate are For not allowed', "Warning");
						return;
					}
					taxcode.duplicateFlag = false;
				}*/
				$scope.saveCountryProvision = function() {
					
					if($scope.countryProvisionObj.effectiveDate==null || $scope.countryProvisionObj.effectiveDate=="" ||
							$scope.countryProvisionObj.effectiveDate==undefined){
						$scope.countryProvisionObj.effectiveDate=$scope.countryProvisionObj.fromDate;
					}
					else{
						$scope.countryProvisionObj.effectiveDate=$scope.countryProvisionObj.effectiveDate;
					}
                   
					if ($scope.countryProvisionObj.fromDate > $scope.countryProvisionObj.toDate) {

						GenericAlertService.alertMessage("formDate < toDate", 'Info');

					} else if (($scope.countryProvisionObj.effectiveDate >= $scope.countryProvisionObj.fromDate) && ($scope.countryProvisionObj.effectiveDate <= $scope.countryProvisionObj.toDate)) {
						
						var flag=false;
					
							
						$scope.countryId=$scope.countryProvisionObj.countryLabelKeyTO.id;
						$scope.provisionId=$scope.countryProvisionObj.proisionLabelKeyTO.id;
						if($scope.effectDate!=null){
							if ($scope.taxCodeCountryUniqueCodeMap[$scope.countryId + "  ,  " + $scope.provisionId+ "  , " + $scope.effectDate ]) {
								flag = true;
							}
						}
						else{
							if ($scope.taxCodeCountryUniqueCodeMap[$scope.countryId + "  ,  " + $scope.provisionId+ "  , " + $scope.fromDate ]) {
								flag = true;
							}
						}
							
						
						if(flag)
							{
							GenericAlertService.alertMessage('Duplicate are not allowed', "Warning");
							return;
							}
						var saveReq = {
							"taxCountryProvisionTO" : $scope.countryProvisionObj
						};
						blockUI.start();
						TaxCodeCountryService.saveTaxCountryProvision(saveReq).then(function(data) {
							blockUI.stop();
							var result = data.taxCountryProvisionTOs;
							var message = GenericAlertService.alertMessageModal('Country provision detail(s) is/are ' + data.message, "Info");
							message.then(function(popData) {
								var returnPopObj = {
									"taxCountryProvisionTO" : result,
									"countryMap" : data.countryMap
								};
								deferred.resolve(returnPopObj);
								$scope.closeThisDialog();
							});

						}, function(error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Country Detail(s) is/are failed to Save', 'Error');
						});

					} else {
						GenericAlertService.alertMessage('Effective Date must be in between fromDate and toDate', 'Warning');
					}
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
