'use strict';

app.factory('EmpPayRoleCyclePastRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService","EmpPayRoleCycleMoreDetailsFactory", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService,EmpPayRoleCycleMoreDetailsFactory) {
	var empPayRoleCyclePastRecordsPopup;
	var service = {};
	service.empPayRoleCyclePastRecordsPopupDetails = function (countryName,provisionName, financeCenterRecords) {
		var deferred = $q.defer();
		empPayRoleCyclePastRecordsPopup = ngDialog.open({
			template: 'views/finance/financecentre/emppayrolecyclepastrecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
               //console.log("financeCenterRecords===2323==",financeCenterRecords);
				$scope.emppayrolecycle = financeCenterRecords;
				//console.log("countryName",countryName);
				$scope.countryName = countryName;
				//console.log("provisionName",provisionName);
				$scope.provisionName = provisionName;
				$scope.moreDetailsEmployeePayRate = function(request){
				//console.log("==moreDetailsPayRate==")
				//console.log("==financeCenterRecordsData=id=",request.id)
				EmpPayRoleCycleMoreDetailsFactory.moreDetailsEmployeePayRatePopupDetails(request).then(function (data) {
				//console.log("data",data)
					});
               }
			}]

		});
		return deferred.promise;
	}
	return service;
}]);