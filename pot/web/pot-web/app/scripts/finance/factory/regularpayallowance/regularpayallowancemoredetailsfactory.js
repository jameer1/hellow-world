'use strict';

app.factory('RegularPayAllowanceMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
	var moreDetailsRegularPayRatePopup;
	var service = {};
	service.moreDetailsRegularPayRate = function (request) {
		var deferred = $q.defer();
		moreDetailsRegularPayRatePopup = ngDialog.open({
			template: 'views/finance/regularpayallowance/regularpayallowancemorerecords.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				//console.log("request===2323==",request.id);
				//console.log("request===2323==",request.regularPayAllowances[0].regularPayCycleDisplayId);
				$scope.code=request.regularPayAllowances[0].code
				$scope.description=request.regularPayAllowances[0].description
				$scope.isTaxable=request.regularPayAllowances[0].isTaxable
				$scope.regularPayCycleDisplayId=request.regularPayAllowances[0].regularPayCycleDisplayId
               
			}]

		});
		return deferred.promise;
	}
	return service;
}]);			
	