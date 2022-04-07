'use strict';

app.factory('TaxPaymentsReceiverDetailsPastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "TaxPaymentsReceiverDetailsMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, TaxPaymentsReceiverDetailsMoreDetailsFactory) {
	var taxPaymentsReceiverDetailsPastRecordsPopup;
	var service = {};
	service.taxPaymentsReceiverDetailsPastRecordsPopupDetails = function (countryName,provisionName, financeCenterRecords) {
		var deferred = $q.defer();
		taxPaymentsReceiverDetailsPastRecordsPopup = ngDialog.open({
			template: 'views/finance/taxpaymentsreceiverdetails/taxpaymentsreceiverdetailspastrecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				//console.log("financeCenterRecordsData===2323==",financeCenterRecords);
				$scope.unitOfTaxReceiver = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
				$scope.moreDetailsTaxPaymentsReceiver = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				TaxPaymentsReceiverDetailsMoreDetailsFactory.moreDetailsTaxPaymentsReceiverPopupDetails(request).then(function (data) {
				//console.log("data",data)
					});
               }
               
			}]

		});
		return deferred.promise;
	}
	return service;
}]);

app.factory('TaxPaymentsReceiverDetailsMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
var service1 = {};		
	service1.moreDetailsTaxPaymentsReceiverPopupDetails = function (request) {
		var deferred = $q.defer();
		var moreDetailsSuperFundPopup;
		moreDetailsSuperFundPopup = ngDialog.open({
			template: 'views/finance/financecentre/taxPaymentsReceiverMoreDetailspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				
				//console.log("request===2323==",request);
				//console.log("request===2323==",request.superProvidentFund[0].unitOfPayDisplayId);
				$scope.code=request.taxPaymentsReceivers[0].code
				//console.log("code===1==",$scope.code);
				$scope.description=request.taxPaymentsReceivers[0].description
					//console.log("code===2==",$scope.description);
					$scope.nameofDepartment=request.taxPaymentsReceivers[0].nameofDepartment
					$scope.registerdAddress=request.taxPaymentsReceivers[0].registerdAddress
					$scope.accountNumber=request.taxPaymentsReceivers[0].accountNumber
					$scope.bankInstituteName=request.taxPaymentsReceivers[0].bankInstituteName
					$scope.bankCode=request.taxPaymentsReceivers[0].bankCode
				
					$scope.taxPaymentReceiverDisplayId=request.taxPaymentsReceivers[0].taxPaymentReceiverDisplayId
					//console.log("code===6==",$scope.superProvidentFundDisplayId);
				
				
			}]

		});
		//console.log("close===2323==");
		return deferred.promise;
	}
	return service1;
}]);