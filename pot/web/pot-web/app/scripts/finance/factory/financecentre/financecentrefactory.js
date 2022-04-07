'use strict';
app.factory('financeCentrePopupFactory', ["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "CountryService", "UnitPayRateFactory", "UnitPayRateService", "EmpRoleFactory","RegularPayAllowanceFactory" ,"NonRegularPayAllowanceFactory" , "SuperFundProvidentFundFactory", "TaxCodesTaxRulesFactory", "PayDeductionCodesFactory", "TaxPaymentsReceiverDetailsFactory", function (ngDialog, $q, $rootScope, blockUI, GenericAlertService,
	CountryService, UnitPayRateFactory, UnitPayRateService, EmpRoleFactory,   RegularPayAllowanceFactory ,NonRegularPayAllowanceFactory,  SuperFundProvidentFundFactory, TaxCodesTaxRulesFactory, PayDeductionCodesFactory, TaxPaymentsReceiverDetailsFactory) {
	var financeCentrePopup;
	var service = {};
	service.financeCentre = function (actionType, countries, countryCode, editFinanceCenterRecords) {
		var deferred = $q.defer();
		financeCentrePopup = ngDialog.open({
			template: 'views/finance/financecentre/financecentrepopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.editFinancecentreDetails = angular.copy(editFinanceCenterRecords);
				//console.log(editFinanceCenterRecords)
				$scope.countries = countries;
				$scope.countryCode = countryCode;
				const countryObj = $scope.countries.find(function (country) {
					return country.countryCode === countryCode;
				});
				if (countryObj) {
					$scope.countryName = countryObj.countryName;
					const req = { "geonameId": countryObj.geonameId };
					CountryService.getProvince(req).then(function (data) {
						$scope.provisionTOs = data.provisionTOs;
						
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Province Details", "Error");
					});
				}
				$scope.financeCenterRecords = [];
				$scope.financeCenterRecordsData = {
					"id": '',
					"actionType": 'Add',
					"status": 1,
					"effectiveFrom": '',
					"provisionName": '',
					"financeCentreCode": '',
					"countryName": $scope.countryName,
					"countryCode": $scope.countryCode,
					"unitOfPayRates": '',
					"empPayRollCycles": '',
					"nonRegularPayAllowances": '',
					"regularPayAllowances": '',
					"superFundCodes": '',
					"payDeductionCodes": '',
					"taxCodesTaxRules": '',
					"taxPaymentsReceivers":''
				}
				if (actionType === 'Add') {
					$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
				} else {
					$scope.financeCenterRecordsData = angular.copy($scope.editFinancecentreDetails);
					for (var i = 0; i < $scope.financeCenterRecordsData.length; i++) {
						$scope.financeCenterRecordsData[i].actionType = 'Edit';
					}

					$scope.financeCenterRecords = angular.copy($scope.financeCenterRecordsData);

				}
                 console.log("====$scope.financeCenterRecordsData=====",$scope.financeCenterRecordsData)
				$scope.addUnitOfPayRate = function (actionType, financeCenterRecord) {
					//console.log("====addUnitOfPayRate========",financeCenterRecord)
					//console.log("====actionType========",actionType)
					//console.log("====financeCenterRecord.unitOfPayRates==>",financeCenterRecord.unitOfPayRates)
					if (actionType != 'Edit' && financeCenterRecord.unitOfPayRates == '') {
						/*console.log("******---unitOfPayRates---******")*/
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						
						//console.log("====provisionCode==>",$scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2)
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						//console.log("====provisionName==>",provisionName)
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.unitOfPayRates != '') {
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
							$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						 }
					var popupUnitOfPayRate = UnitPayRateFactory.unitPayPopupDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.unitOfPayRates = data;
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting country Details",
									'Info');
						});
				}

				$scope.addNonRegularPayAllowance = function (actionType, financeCenterRecord) {
					if (actionType != 'Edit' && financeCenterRecord.nonRegularPayAllowances == '') {
						//console.log("actionType===",actionType,"financeCenterRecord.nonRegularPayAllowances====",financeCenterRecord.nonRegularPayAllowances)
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						//console.log("==addNonRegularPayAllowance==>",$scope.financeCenterRecordsData[2].provisionName)
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
						//console.log("==addNonRegularPayAllowance=length=>",financeCenterRecord.nonRegularPayAllowances)
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.nonRegularPayAllowances != '') {
						/*console.log("actionType===",actionType,"financeCenterRecord.nonRegularPayAllowances====",financeCenterRecord.nonRegularPayAllowances)
						console.log("==second loop==>",$scope.financeCenterRecordsData.nonRegularPayAllowances.length)*/
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
						//console.log("actionType===",actionType,"financeCenterRecord.nonRegularPayAllowances====",financeCenterRecord.nonRegularPayAllowances)
						//console.log("==secthird ond loop==>",financeCenterRecord.length)
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						//$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
					}
					var popupUnitOfPayRate = NonRegularPayAllowanceFactory.nonRegularPayAllowanceDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.nonRegularPayAllowances = data;
						//console.log("nonRegularPayAllowances",$scope.financeCenterRecordsData.nonRegularPayAllowances)
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting NonRegularPayAllowance Details",
									'Info');
						});
				}

				// Regular Pay Allowance codes

				$scope.addRegularPayAllowance = function (actionType, financeCenterRecord) {
					if (actionType != 'Edit' && financeCenterRecord.regularPayAllowances == '') {
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.regularPayAllowances != '') {
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						//$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
					}
					var popupUnitOfPayRate = RegularPayAllowanceFactory.regularPayAllowanceDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.regularPayAllowances = data;
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting RegularPayAllowance Details",
									'Info');
						});
				}
				
				// end 
				// Super Fund/Provident Fund codes
				$scope.addSuperFundProvident = function (actionType, financeCenterRecord) {
					
					if (actionType != 'Edit' && financeCenterRecord.superFundCodes=='') {
						//console.log("actionType==1",actionType)
						//console.log("financeCenterRecord==1",financeCenterRecord.superFundCodes)
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.superFundCodes != '') {
						//console.log("actionType==2",actionType)
						//console.log("$scope.financeCenterRecordsData.superFundCodes",$scope.financeCenterRecordsData.superFundCodes)
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
						//console.log("actionType==3",actionType)
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						//$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
					}
						
					var popupUnitOfPayRate = SuperFundProvidentFundFactory.superProvidentFundDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.superFundCodes = data;
						//console.log("data==3",data)
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting RegularPayAllowance Details",
									'Info');
						});
				}
				
				// end
				// Tax code rates & rules

				$scope.addTaxCodeTaxRules = function (actionType, financeCenterRecord) { 
					
					if (actionType != 'Edit' && financeCenterRecord.taxCodesTaxRules == '') {
						//console.log("financeCenterRecord.taxCodesTaxRules===",financeCenterRecord.taxCodesTaxRules)
						//console.log("financeCenterRecord.taxRateRulesCodes",financeCenterRecord.taxRateRulesCodes)
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.taxCodesTaxRules != '') {
						//console.log("$scope.financeCenterRecordsData.taxCodesTaxRules",$scope.financeCenterRecordsData.taxCodesTaxRules)
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						//$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
					}
						

					var popupUnitOfPayRate = TaxCodesTaxRulesFactory.taxCodesTaxRulesDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.taxCodesTaxRules = data;
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting Tax codes, rates, rules code Details",
									'Info');
						});
				}

				// end
				// Pay Deduction codes

				$scope.addPayDeductionCodes = function (actionType, financeCenterRecord) {

					if (actionType != 'Edit' && financeCenterRecord.payDeductionCodes == '') {
						//console.log("actionType==1",actionType)
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.payDeductionCodes != '') {
						//console.log("actionType==2",actionType)
					//	console.log("$scope.financeCenterRecordsData.payDeductionCodes",$scope.financeCenterRecordsData.payDeductionCodes)
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
						//console.log("actionType==3",actionType)
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						//$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
					}
					var popupUnitOfPayRate = PayDeductionCodesFactory.payDeductionCodesDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.payDeductionCodes = data;
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting pay deduction codes Details",
									'Info');
						});
				}

				// end
				// Tax payment receiver code

				$scope.addTaxPaymentsReceiverDetails = function (actionType, financeCenterRecord) {
					
					//console.log('test  ', financeCenterRecord);
					if (actionType != 'Edit' && financeCenterRecord.taxPaymentsReceivers == '') {
						//console.log("actionType=1==",actionType)
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
						
						//console.log($scope.taxPaymentsReceivers);
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.taxPaymentsReceivers != '') {
						//console.log("actionType===2",actionType)
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
						//console.log("$scope.financeCenterRecordsData.taxPaymentsReceiver",$scope.financeCenterRecordsData.taxPaymentsReceivers)
					}
					else {
						//console.log("actionType===3",actionType)
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
						//$scope.financeCenterRecords.push($scope.financeCenterRecordsData);
					}

					var popupUnitOfPayRate = TaxPaymentsReceiverDetailsFactory.taxPaymentsReceiverDetails(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data) {
						$scope.financeCenterRecordsData.taxPaymentsReceivers = data;
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting tax payments receiver Details",
									'Info');
						});
				}

				// end


				$scope.addEmployeePayRole = function (actionType, financeCenterRecord) {
					if (actionType != 'Edit' && financeCenterRecord.empPayRollCycles == '') {
						
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					} else if (actionType == 'Add' && $scope.financeCenterRecordsData.empPayRollCycles != '') {
						$scope.financeCenterRecordsData = angular.copy($scope.financeCenterRecordsData);
					}
					else {
						$scope.financeCenterRecordsData = angular.copy(financeCenterRecord);
					}
					var popupUnitOfPayRate = EmpRoleFactory.empPayRole(actionType, $scope.countryName, $scope.financeCenterRecordsData);
					popupUnitOfPayRate.then(function (data, provisionName) {
						$scope.financeCenterRecordsData.empPayRollCycles = data;
					},
						function (error) {
							GenericAlertService
								.alertMessage(
									"Error occurred while getting country Details",
									'Info');
						});
				}

				$scope.saveFinancialCenter = function () {
					if (actionType != 'Edit') {
						var provisionCode = $scope.financeCenterRecordsData[2].provisionName.adminCodes1.ISO3166_2;
						//console.log("1----",provisionCode)
						var provisionName = $scope.financeCenterRecordsData[2].provisionName.name;
						//console.log("2----",provisionName)
						$scope.financeCenterRecordsData.provisionName = provisionName;
						$scope.financeCenterRecordsData.provisionCode = provisionCode;
					}
					let financeCenterRecordsSaveReq = $scope.financeCenterRecordsData;
					//console.log("$scope.financeCenterRecordsData=====saveFinancialCenter",$scope.financeCenterRecordsData)
					//console.log("financeCenterRecordsSaveReq=====",financeCenterRecordsSaveReq)
					UnitPayRateService.addFinancialCenter(financeCenterRecordsSaveReq).then(function (data) {
						GenericAlertService.alertMessage("Finance Center code(s)  is/are saved successfully", "Info");
						$scope.closeThisDialog();
						deferred.resolve($scope.financeCenterRecords);
					}, function (error) {
						GenericAlertService.alertMessage('Finance Center code(s)  is/are failed to save"', "Error");
					});
				}

			}]
		});
		return deferred.promise;
	};
	return service;
}]);
