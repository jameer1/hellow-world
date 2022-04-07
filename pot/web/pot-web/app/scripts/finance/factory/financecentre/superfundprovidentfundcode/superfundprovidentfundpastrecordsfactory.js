'use strict';

app.factory('SuperFundProvidentFundPastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService","SuperFundProvidentFundMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService, SuperFundProvidentFundMoreDetailsFactory) {
    var superFundProvidentFundPastRecordsPopup;
    var service = {};
    service.superFundProvidentFundPastRecordsPopupDetails = function (countryName,provisionName,financeCenterRecords) {
        var deferred = $q.defer();
        superFundProvidentFundPastRecordsPopup = ngDialog.open({
            template: 'views/finance/superfundprovidentfund/superfundprovidentfundpastrecords.html',
            className: 'ngdialog-theme-plain ng-dialogueCustom3',
            scope: $rootScope,
            closeByDocument: false,
            showClose: false,
            controller: ['$scope', function ($scope) {

                //console.log("financeCenterRecordsData===2323==",financeCenterRecords);
				$scope.unitOfSuperFunds = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
					$scope.moreDetailsSuperFunds = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				SuperFundProvidentFundMoreDetailsFactory.moreDetailsSuperFundPopupDetails(request).then(function (data) {
				//console.log("data",data)
					});
               }

            }]

        });
        return deferred.promise;
    }
    return service;
}]);

app.factory('SuperFundProvidentFundMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
var service1 = {};		
	service1.moreDetailsSuperFundPopupDetails = function (request) {
		var deferred = $q.defer();
		var moreDetailsSuperFundPopup;
		moreDetailsSuperFundPopup = ngDialog.open({
			template: 'views/finance/financecentre/superfundprovidentmoredetailspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				
				//console.log("request===2323==",request);
				//console.log("request===2323==",request.superProvidentFund[0].unitOfPayDisplayId);
				$scope.code=request.superFundCodes[0].code
				//console.log("code===1==",$scope.code);
				$scope.description=request.superFundCodes[0].description
					//console.log("code===2==",$scope.description);
				$scope.creditRunCycle=request.superFundCodes[0].creditRunCycle
					//console.log("code===3==",$scope.creditRunCycle);
				
				$scope.isTaxable=request.superFundCodes[0].isTaxable
					//console.log("code===4==",$scope.isTaxable);
				$scope.creditRunDueDate=request.superFundCodes[0].creditRunDueDate
					//console.log("code===5==",$scope.creditRunDueDate);
					$scope.superProvidentFundDisplayId=request.superFundCodes[0].superProvidentFundDisplayId
					//console.log("code===6==",$scope.superProvidentFundDisplayId);
				
				
			}]

		});
		console.log("close===2323==");
		return deferred.promise;
	}
	return service1;
}]);	