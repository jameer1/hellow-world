'use strict';

app.factory('NonRegularPayAllowancePastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService","NonRegularPayAllowanceMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, NonRegularPayAllowanceMoreDetailsFactory) {
    var nonRegularPayAllowancePastRecordsPopup;
    var service = {};
    service.nonRegularPayAllowancePastRecordsPopupDetails = function (countryName,provisionName, financeCenterRecords) {
        var deferred = $q.defer();
        nonRegularPayAllowancePastRecordsPopup = ngDialog.open({
            template: 'views/finance/nonregularpayallowance/nonregularpayallowancepastrecords.html',
            className: 'ngdialog-theme-plain ng-dialogueCustom3',
            scope: $rootScope,
            closeByDocument: false,
            showClose: false,
            controller: ['$scope', function ($scope) {
			//console.log("financeCenterRecordsData===2323==",financeCenterRecords);
				$scope.unitOfNonRegular = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
				$scope.moreDetailsNonRegularPayRate = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				NonRegularPayAllowanceMoreDetailsFactory.moreDetailsNonRegularPayRate(request).then(function (data) {
				//console.log("data",data)
					});
               }
            }]

        });
        return deferred.promise;
    }
    return service;
}]);

app.factory('NonRegularPayAllowanceMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var moreDetailsRegularPayRatePopup;
	var service1 = {};
	service1.moreDetailsNonRegularPayRate = function (request) {
		var deferred = $q.defer();
		moreDetailsRegularPayRatePopup = ngDialog.open({
			template: 'views/finance/nonregularpayallowance/nonregularpayallowancemorerecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				//console.log("request===2323==",request.id);
				//console.log("request===2323==",request.regularPayAllowances[0].regularPayCycleDisplayId);
				$scope.code=request.nonRegularPayAllowances[0].code
				$scope.description=request.nonRegularPayAllowances[0].description
				$scope.isTaxable=request.nonRegularPayAllowances[0].isTaxable
				$scope.nonRegularPayCycleDisplayId=request.nonRegularPayAllowances[0].nonRegularPayCycleDisplayId
               
			}]

		});
		return deferred.promise;
	}
	return service1;
}]);	