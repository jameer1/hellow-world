'use strict';
app.factory('CountryProvinceCodeFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "TaxCodeService", "GenericAlertService", "generalservice", "CountryService", "FinancialYearFactory", "FinancialHalfYearFactory", "FinancialQuarterYearFactory", "CountryProvinceCodeService","$rootScope", function(ngDialog, $q, $filter, $timeout,blockUI, TaxCodeService, GenericAlertService,
	generalservice,CountryService,FinancialYearFactory,FinancialHalfYearFactory,FinancialQuarterYearFactory,CountryProvinceCodeService,$rootScope) {
	var taxCodePopUp;
	var service = {};
	service.countryprovincecodePopUpDetails = function(actionType, editCountryProvinceDetails) {
		var deferred = $q.defer();
		taxCodePopUp = ngDialog.open({
			template : 'views/finance/countryprovince/countryprovincecodepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.CountryProvinceDetails=[];
				$scope.item=[];
				var countryCode;
				$scope.actionType = actionType;
                $scope.getCountryDetailsByIdz = function(item) {
					countryCode = item[0].countryCode;
					$scope.getCountriesz(item);
				}
				$scope.getCountriesz = function() {
					console.log("hllo")
					CountryService.getCountries().then(function(data) {
						console.log(data)
						$scope.countriesz = data.countryInfoTOs;
						$scope.countryObjz = $scope.countriesz.find(function(country) {
							return country.countryCode === countryCode;
						});
						if ($scope.countryObjz) {
							const req = { "geonameId": $scope.countryObjz.geonameId };
							CountryService.getProvince(req).then(function(data) {
								$scope.item = data.provisionTOs;
								$scope.datas=editCountryProvinceDetails;
								for(var i=0; i<$scope.datas.length; i++){
									$scope.datas[i].provisionTOs=[];
									$scope.datas[i].provisionTOs=$scope.item
								}
								$scope.CountryProvinceDetails = angular.copy($scope.datas);
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while getting Province Details", "Error");
							});
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
					});
				}
				let financialYearData ;
				let financialHalfYearData;
				let financialQuarterYearData ;
				
				 $scope.CountryProvinceDetailsData={
							"id":'',
							"status":1,
							"countryCode":'',
							"countryName":'',
							"provisionName":'',
							"financialYearData" : '',
							"financialHalfYearData" : '',
							"financialQuarterYearData" : ''
					}
				if (actionType === 'Add') {
					$scope.CountryProvinceDetails.push($scope.CountryProvinceDetailsData);
				} else {
					$scope.getCountryDetailsByIdz(editCountryProvinceDetails);
				}
				$scope.details = editCountryProvinceDetails;
				
				$scope.getCountries = function() {
					CountryService.getCountries().then(function(data) {
						$scope.countries = data.countryInfoTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
					});
				},
				$scope.getCountryDetailsById = function(item) {
					const countryCode = item.countryCode;
					const countryObj = $scope.countries.find(function(country){
						return country.countryCode === countryCode;
					});
					
					if(countryObj) {
						item.currency = countryObj.currencyCode;
						item.countryName = countryObj.countryName;
						const req = { "geonameId": countryObj.geonameId };
						CountryService.getProvince(req).then(function(data) {
								item.provisionTOs = data.provisionTOs;	
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting Province Details", "Error");
						});
					}
				}
				 $scope.addFinancialYear = function(item) {						 
					
					   if(item.id && item.financialYearData)
						 {
						 actionType =null;
						 }
					   else  if(item.id=="" && item.financialYearData=='')
						 {
							 actionType = 'Add';
						}
					 else
						 {
						 actionType='Edit'
						 }
						var financialYearData = [];
						financialYearData.push(item.financialYearData);
						var popupDetails = FinancialYearFactory.financialYearPopUpDetails(actionType,financialYearData);
						popupDetails.then(function(data) {
							$scope.CountryProvinceDetails[0].financialYearData = data[0];
							//console.log(data[0])
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while adding financial year details", 'Error');
					});
						
	            },
	            $scope.addFinancialHalfYear = function(item)
	            {
	           	 var actionType;
	           	 if(item.id && item.financialHalfYearData)
				 {
				 actionType =null;
				 }
			   else  if(item.id=="" && item.financialHalfYearData=='')
				 {
					 actionType = 'Add';
				}
				 else
					 {
					 actionType='Edit'
					 }
	            	var financialHalfYearData= [];
						//financialHalfYearData.$scgetToDate(item)
	            	financialHalfYearData.push(item.financialHalfYearData);
						var popupDetails = FinancialHalfYearFactory.financialHalfYearPopUpDetails(actionType,financialHalfYearData);
						popupDetails.then(function(data) {
							$scope.CountryProvinceDetails[0].financialHalfYearData = data[0];
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while  adding financial half year details", 'Error');
					});
						
	            },
	            $scope.addFinancialQuarterYear = function(item)
	            {
	           	 var actionType;
	           	if(item.id && item.financialQuarterYearData)
				 {
				 actionType =null;
				 }
			   else  if(item.id=="" && item.financialQuarterYearData=='')
				 {
					 actionType = 'Add';
				}
				 else
					 {
					 actionType='Edit'
					 }
	            	var financialQuarterYearData = [];
	            	financialQuarterYearData.push(item.financialQuarterYearData)
						var popupDetails = FinancialQuarterYearFactory.financialQuarterYearPopUpDetails(actionType,financialQuarterYearData);
						popupDetails.then(function(data) {
							$scope.CountryProvinceDetails[0].financialQuarterYearData = data[0];
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while  adding financial quarter year details", 'Error');
					});
						
	            }
				$scope.saveCountryProvinceCodes = function () {
					let countryProvinceCodeSaveReq = {
						"id": $scope.CountryProvinceDetails[0].id,
						"status": $scope.CountryProvinceDetails[0].status,
						"countryCode": $scope.CountryProvinceDetails[0].countryCode,
						"countryName": $scope.CountryProvinceDetails[0].countryName,
						"provisionName": $scope.CountryProvinceDetails[0].provisionName,
						"financialYearData": $scope.CountryProvinceDetails[0].financialYearData,
						"financialHalfYearData": $scope.CountryProvinceDetails[0].financialHalfYearData,
						"financialQuarterYearData": $scope.CountryProvinceDetails[0].financialQuarterYearData
					}
					if(countryProvinceCodeSaveReq.countryCode == '' || countryProvinceCodeSaveReq.countryCode == null || countryProvinceCodeSaveReq.countryCode == undefined) {
						GenericAlertService.alertMessage('Country not selected', "Warning");
						return;
					}
					if(countryProvinceCodeSaveReq.provisionName == '' || countryProvinceCodeSaveReq.provisionName == null || countryProvinceCodeSaveReq.provisionName == undefined) {
						GenericAlertService.alertMessage('Province not selected', "Warning");
						return;
					}
					if(countryProvinceCodeSaveReq.financialYearData == '' || countryProvinceCodeSaveReq.financialYearData == null || countryProvinceCodeSaveReq.financialYearData == undefined) {
						GenericAlertService.alertMessage('Financial Year data not selected', "Warning");
						return;
					}
					if(countryProvinceCodeSaveReq.financialHalfYearData == '' || countryProvinceCodeSaveReq.financialHalfYearData == null || countryProvinceCodeSaveReq.financialHalfYearData == undefined) {
						//GenericAlertService.alertMessage('Financial Half Year data not selected', "Warning");
						GenericAlertService.alertMessage('Financial Half Year data not saved', "Warning");
						return;
					}
					if(countryProvinceCodeSaveReq.financialQuarterYearData == '' || countryProvinceCodeSaveReq.financialQuarterYearData == null || countryProvinceCodeSaveReq.financialQuarterYearData == undefined) {
						//GenericAlertService.alertMessage('Financial Quarter Year data not selected', "Warning");
						GenericAlertService.alertMessage('Financial Quarter Year data not saved', "Warning");
						return;
					}

					CountryProvinceCodeService.saveCountryProvinceCode(countryProvinceCodeSaveReq).then(function (data) {
						GenericAlertService.alertMessage("Country Province codes saved successfully", "Info");
						$rootScope.financialYearStartDate='';//After saving the data rootScope variable is nullifying
						$scope.closeThisDialog();
						deferred.resolve($scope.CountryProvinceDetails);
					}, function (error) {
						GenericAlertService.alertMessage('Country Province codes  is/are failed to save"', "Error");
					});

				}
			}]
		});
		
		return deferred.promise;
	}
	return service;
}]);
