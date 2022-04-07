'use strict';

app.factory('PayDeductionCodesPastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "PayDeductionCodesMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, PayDeductionCodesMoreDetailsFactory) {
	var payDeductionCodesPastRecordsPopup;
	var service = {};
	service.payDeductionCodesPastRecordsPopupDetails = function (countryName,provisionName,financeCenterRecords) {
		var deferred = $q.defer();
		payDeductionCodesPastRecordsPopup = ngDialog.open({
			template: 'views/finance/paydeductioncodes/paydeductioncodespastrecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				//console.log("financeCenterRecordsData===2323==",financeCenterRecords);
				$scope.unitOfPayDeduction = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
				
				$scope.moreDetailsPayDeductioncodes = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				PayDeductionCodesMoreDetailsFactory.payDeductionCodesPastRecordsDetails(request).then(function (data) {
				//console.log("data",data)
					});
               }
               
			}]

		});
		return deferred.promise;
	}
	return service;
}]);

app.factory('PayDeductionCodesMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var payDeductionCodesPastRecordsPopup;
	var service1 = {};
	service1.payDeductionCodesPastRecordsDetails = function (request) {
		var deferred = $q.defer();
		payDeductionCodesPastRecordsPopup = ngDialog.open({
			template: 'views/finance/paydeductioncodes/paydeductioncodesmoredetails.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
					//console.log("request===2323==",request.id);
				//console.log("request===2323==",request.payDeductionCodes[0].payDeductionsDisplayId);
				$scope.code=request.payDeductionCodes[0].code
				$scope.description=request.payDeductionCodes[0].description
				$scope.payDeductionsDisplayId=request.payDeductionCodes[0].payDeductionsDisplayId
			}]

		});
		return deferred.promise;
	}
	return service1;
}]);