'use strict'

app
.config(
	["$stateProvider", function($stateProvider) {

		$stateProvider
		.state(
			"financecentre",
			{
				url : "/financecentre",
				data : {
					roles : []
				},
				views : {
					'content@' : {
						templateUrl : 'views/finance/financecentre/financecentremaster.html',
						controller : 'financeCentreController'
					}
				}
			})
	}])
.controller('financeCentreController', ["$q","$scope", "ngDialog", "GenericAlertService", "financeCentrePopupFactory", "unitOfPayRateFactory", "TaxCountryFactory", "EmpRoleFactory", "RegularPayAllowanceFactory", "FinanceCountryFactory", "NonRegularPayAllowanceFactory", "CountryService", "UnitPayRateService", "UnitPayRateFactory", "SuperFundProvidentFundFactory", "TaxCodesTaxRulesFactory", "PayDeductionCodesFactory", "TaxPaymentsReceiverDetailsFactory", "utilservice","stylesService", "ngGridService", function($q,$scope, ngDialog, GenericAlertService, financeCentrePopupFactory, unitOfPayRateFactory, TaxCountryFactory, EmpRoleFactory, RegularPayAllowanceFactory,FinanceCountryFactory, NonRegularPayAllowanceFactory,CountryService,UnitPayRateService,UnitPayRateFactory, SuperFundProvidentFundFactory, TaxCodesTaxRulesFactory, PayDeductionCodesFactory, TaxPaymentsReceiverDetailsFactory, utilservice, stylesService, ngGridService) {
	$scope.countries =[];
	$scope.countryProvisionObj={};
	$scope.stylesSvc = stylesService;
    $scope.financeCentreSearch = function () {

      $scope.getFinanceCenters();
  },
		$scope.countryProvision = {
			"id" : null,
			"code" : null,
			"countryLabelKeyTO" : {
				"id" : null,
				"code" : null,
				"name" : null
			},

		};

		/*==================================GET COUNTRY DETAILS===========================================*/
		$scope.getCountryDetails=function(countryProvisionObj){
			var popupDetails = FinanceCountryFactory.getCountryDetails();
			popupDetails.then(function(data) {
				countryProvisionObj.countryName = data.selectedCountry.countryName;
				$scope.countryId = data.selectedCountry.id;
				$scope.countryCode = data.selectedCountry.countryCode;
				//	console.log("$scope.countryCode",$scope.countryCode);
				$scope.provisions = data.selectedCountry.provisionTOs;
				$scope.provisions = data.selectedCountry.provisionTOs;
				//console.log("$scope.provisions",$scope.provisions);
				$scope.provisionName = data.selectedCountry.provisionName;
				//console.log("$scope.provisionName",$scope.provisionName);
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
			})
		}


		$scope.items=['Current','Superseded','All'];
		$scope.status=	$scope.items[0];

		/*======================================RESET===================================================*/
		$scope.reset=function(){
			$scope.countryProvision = {
				"id" : null,
				"code" : null,
				"countryLabelKeyTO" : {
					"id" : null,
					"code" : null,
					"name" : null
				}
			}			
		}		

		/*=============================CREATE NEW  FINANCE CENTRE=========================================*/

	var editFinancecentreDetails =[];
	$scope.financeCentreRowSelect = function(financecentreDetails) {
		$scope.fixedAssetid = null;
		if (financecentreDetails.selected) {
			utilservice.addItemToArray(editFinancecentreDetails,"id", financecentreDetails);
		} else {
			editFinancecentreDetails.splice(editFinancecentreDetails.indexOf(editFinancecentreDetails), 1)
		}
	},
		$scope.getCountries = function() {
			CountryService.getCountries().then(function(data) {
				$scope.countries = data.countryInfoTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
			});
		}
		$scope.getFinanceCenters= function () {
			var financeCenterCodeGetReq = {
				"status": 1,
        "countryCode": $scope.countryCode,
				//$scope.countryId
			};
			UnitPayRateService.getFinanceCenters(financeCenterCodeGetReq).then(function (data) {
				//console.log("data====>",data)
				$scope.financeCenterRecordTos = data.financeCenterRecordTos;
				// var dummyFinance = angular.copy($scope.financeCenterRecordTos).map((item) => {
				// if(item.status == 1){
				// item.status = 'Active'}
				// else{
				// item.status = 'Inactive'}
				// return item;
				// });
				$scope.gridOptions.data = $scope.financeCenterRecordTos;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting finance center details", "Error");
			});
		}
		$scope.addFinanceCentre = function(actionType) {

			if(actionType != 'Edit' && $scope.countryId==null && 	$scope.countryProvision.countryName == null){
				GenericAlertService
				.alertMessage(
					"Please select Country",
					'Warning');
				return;
			}
			var popupfinanceCentre = financeCentrePopupFactory.financeCentre(actionType,$scope.countries,$scope.countryCode,editFinancecentreDetails);
			popupfinanceCentre.then(function(data) {
				$scope.getFinanceCenters();
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting country Details",
					'Info');
			});
		}

		
		$scope.viewUnitOfPay = function(item){
			var actionType="View";
			var popupUnitOfPayRate = UnitPayRateFactory.unitPayPopupDetails(actionType,$scope.countryName,item);
			popupUnitOfPayRate.then(function(data) {
				$scope.financeCenterRecordsData.unitOfPayRates=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},

		$scope.viewEmpPayRoll= function(item){
			var actionType="View";
			var empPayRollCycle = EmpRoleFactory.empPayRole(actionType,$scope.countryName,item);
			console.log(empPayRollCycle)
			empPayRollCycle.then(function(data) {
				$scope.financeCenterRecordsData.empPayRollCycles=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},
		$scope.viewRegularPay = function(item){
			var actionType="View";
			//console.log("actionType",actionType);
			var popupRegularPay = RegularPayAllowanceFactory.regularPayAllowanceDetails(actionType,$scope.countryName,item);
			popupRegularPay.then(function(data) {
				$scope.financeCenterRecordsData.regularPayAllowances=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},
		$scope.viewNonRegularOfPay = function(item){
			var actionType="View";
			//console.log("actionType",actionType);
			//console.log("item",item);
			var popupNonRegularOfPay = NonRegularPayAllowanceFactory.nonRegularPayAllowanceDetails(actionType,$scope.countryName,item);
			//console.log("===",popupNonRegularOfPay)
			popupNonRegularOfPay.then(function(data) {
				$scope.financeCenterRecordsData.nonRegularPayAllowances=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},
		$scope.viewSuperFund = function(item){
			var actionType="View";
			//console.log("actionType",actionType);
			var popupSuperFund = SuperFundProvidentFundFactory.superProvidentFundDetails(actionType,$scope.countryName,item);
			popupSuperFund.then(function(data) {
				$scope.financeCenterRecordsData.superFundCodes=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},
		$scope.viewTaxCodes = function(item){
			var actionType="View";
			//console.log("actionType",actionType);
			var popupTaxCodes = TaxCodesTaxRulesFactory.taxCodesTaxRulesDetails(actionType,$scope.countryName,item);
			popupTaxCodes.then(function(data) {
				$scope.financeCenterRecordsData.taxCodesTaxRules=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},
		$scope.viewPayDeduction = function(item){
			var actionType="View";
			//console.log("actionType",actionType);
			var popupPayDeduction = PayDeductionCodesFactory.payDeductionCodesDetails(actionType,$scope.countryName,item);
			popupPayDeduction.then(function(data) {
				$scope.financeCenterRecordsData.payDeductionCodes=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},
		$scope.viewTaxReciver = function(item){
			var actionType="View";
			//console.log("actionType",actionType);
			var popupTaxReciver = TaxPaymentsReceiverDetailsFactory.taxPaymentsReceiverDetails(actionType,$scope.countryName,item);
			popupTaxReciver.then(function(data) {
				//console.log('data=====',data)
				$scope.financeCenterRecordsData.taxPaymentsReceivers=data;
			},
			function(error) {
				GenericAlertService
				.alertMessage(
					"Error occurred while getting unitofpay Details",
					'Info');
			});
		},

		  $scope.deactivateFinanceCenterRecords = function() {
	        		if (editFinancecentreDetails == undefined || editFinancecentreDetails.length <= 0) {
	        			GenericAlertService.alertMessage("Please select atleast one CountryProvince to deactivate", 'Warning');
	        			return;
					}
					let deleteIds = [];
					for (const deleteFinance of editFinancecentreDetails) {
						deleteIds.push(deleteFinance.id);
					}
	        		var financeCenterDeactiveReq = {
	        			"status" : 2,
	        			"financeCenterIds" : deleteIds 
	        		};
	        		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
	        			UnitPayRateService.deactivateFinanceCenterRecords(financeCenterDeactiveReq).then(function(data) {
	        				GenericAlertService.alertMessage("FinanceCenter(s) is/are Deactivated successfully", "Info");
	        				$scope.getFinanceCenters();
	        				editFinancecentreDetails = [];
	        				
	        			}, function(error) {
	        				GenericAlertService.alertMessage(' FinanceCenter(s) is/are failed to deactivate', "Error");
	        			});
	        		}, function(data) {
	        			editFinancecentreDetails = [];
	        			$scope.getFinanceCenters();
	        		})
	        	}


		$scope.employeeUnitOfPayRates = function() {
			var unitOfPayRates = unitOfPayRateFactory
			.empUnitOfPayRate();
		}
		$scope.empPayRoleCycle = function() {
			var empPayRollCycles = EmpRoleFactory.empPayRole();
		}

		$scope.regularPayAllowances = function() {
			var payAndAllowances = regularPayAndAllowancesFactory
			.regularPayAndAllowances();
		}
		$scope.nonregularPayAllowances = function() {
			var nonpayAndAllowances = nonregularPayAndAllowancesFactory
			.nonregularPayAndAllowances();
		}

		$scope.superFundProivdent = function() {
			var superFundandProivdent = superFundProivdentFactory
			.superFundProivdent();
		}
		$scope.taxfundratesrules = function() {
			var taxfundratesandrules = TaxCodeRatesRulesFactory.TaxCodeRatesRules();


		}
		$scope.paydeductioncodes = function() {
			var PayDeductionCodes= PayDeductionCodeFactory.PayDeductionCode();
		}
		$scope.taxpaymentreceiver = function() {
			var taxpaymentreceiverDetails= taxpaymentReceiverFactory.taxPaymentReceiver();
		}
		var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.financeCentreRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'effectiveFrom', displayName: "Effective From", headerTooltip: "Effective From"},
						{ field: 'provisionName', displayName: "Province", headerTooltip: "Province", },
						{ field: 'displayFinanceCenterId', displayName: "Finance Center Code", headerTooltip: "Finance Center Code", },
						
						{ name: 'Button1', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Employee - Unit of Pay Rates", headerTooltip : "Employee - Unit of Pay Rates",
						cellTemplate: '<button ng-click="grid.appScope.viewUnitOfPay(row.entity, item)" class="btn btn-primary btn-sm">View</button>'},
			      		 
			      		 { name: 'Button2', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Employee Pay Roll Cycle / Credit Cycle", headerTooltip : "Employee Pay Roll Cycle / Credit Cycle",
						cellTemplate: '<button ng-click="grid.appScope.viewEmpPayRoll(row.entity, item)" class="btn btn-primary btn-sm">View</button>'},
			      		 
			      		 { name: 'Button3', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Regular Pay Allowances Codes", headerTooltip : "Regular Pay Allowances Codes",
						cellTemplate: '<button ng-click="grid.appScope.viewRegularPay(row.entity, item)" class="btn btn-primary btn-sm">View</button>'},
						
						{ name: 'Button4', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Non Regular Pay Allowances Codes", headerTooltip : "Non Regular Pay Allowances Codes",
						cellTemplate: '<button ng-click="grid.appScope.viewNonRegularOfPay(row.entity, item)" data-test="CountryProvinceCodes_FinancialHalfYear_View" class="btn btn-primary btn-sm">View</button>'},
			      		 
			      		 { name: 'Button5', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Super fund / Provident Fund Code", headerTooltip : "Super fund / Provident Fund Code",
						cellTemplate: '<button ng-click="grid.appScope.viewSuperFund(row.entity, item)" data-test="CountryProvinceCodes_FinancialHalfYear_View" class="btn btn-primary btn-sm">View</button>'},
			      		 
			      		 { name: 'Button6', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Tax Codes, Rates & Rules Code", headerTooltip : "Tax Codes, Rates & Rules Code",
						cellTemplate: '<button ng-click="grid.appScope.viewTaxCodes(row.entity, item)" data-test="CountryProvinceCodes_FinancialQuarterYear_View" class="btn btn-primary btn-sm">View</button>'},
						
						{ name: 'Button7', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Pay Deduction Codes", headerTooltip : "Pay Deduction Codes",
						cellTemplate: '<button ng-click="grid.appScope.viewPayDeduction(row.entity, item)" data-test="CountryProvinceCodes_FinancialHalfYear_View" class="btn btn-primary btn-sm">View</button>'},
			      		 
			      		 { name: 'Button8', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Tax Payment Receiver Code", headerTooltip : "Tax Payment Receiver Code",
						cellTemplate: '<button ng-click="grid.appScope.viewTaxReciver(row.entity, item)" data-test="CountryProvinceCodes_FinancialQuarterYear_View" class="btn btn-primary btn-sm">View</button>'},
						
						{ field: 'status', cellFilter: 'potstatusfilter:tab.status', displayName: "Status", headerTooltip: "Status"},
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_FinanceCoded_ProvinceCodes");
					$scope.getFinanceCenters();
					
				}
			});
		var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/Enterprisehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	}]);