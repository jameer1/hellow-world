'use strict';

app.factory('RegularPayAllowancePastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService","RegularPayAllowanceMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService,RegularPayAllowanceMoreDetailsFactory) {
	var regularPayAllowancePastRecordsPopup;
	var service = {};
	service.regularPayAllowancePastRecordsPopupDetails = function (countryName,provisionName, financeCenterRecords) {
		var deferred = $q.defer();
		regularPayAllowancePastRecordsPopup = ngDialog.open({
			template: 'views/finance/regularpayallowance/regularpayallowancepastrecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				//console.log("financeCenterRecordsData===2323==",financeCenterRecords);
				$scope.unitOfRegular = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
				
				$scope.moreDetailsRegularPayRate = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				RegularPayAllowanceMoreDetailsFactory.moreDetailsRegularPayRate(request).then(function (data) {
				//console.log("data",data)
					});
               }
               
			}]

		});
		return deferred.promise;
	}
	return service;
}]);

