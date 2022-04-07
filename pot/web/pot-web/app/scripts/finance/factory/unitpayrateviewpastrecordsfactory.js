'use strict';

app.factory('UnitPayRateViewPastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService","UnitPayRateViewMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService,UnitPayRateViewMoreDetailsFactory) {
	var unitPayRatePastRecordsPopup;
	var service = {};
	service.unitPayRatePastRecordsPopupDetails = function (countryName,provisionName,financeCenterRecordsData) {
		var deferred = $q.defer();
		unitPayRatePastRecordsPopup = ngDialog.open({
			template: 'views/finance/unitpayratepastrecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				
				//console.log("financeCenterRecordsData===2323==",financeCenterRecordsData);
				$scope.unitOfPayRates = financeCenterRecordsData;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
				$scope.provisionName = provisionName;
				
				$scope.moreDetailsPayRate = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				UnitPayRateViewMoreDetailsFactory.moreDetailsPayRatePopupDetails(request).then(function (data) {
				//console.log("data",data)
					});
               }
			}]

		});
		return deferred.promise;
	}
	
	return service;
}]);


		
	