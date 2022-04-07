'use strict';

app.factory('UnitPayRateViewMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
var service1 = {};		
	service1.moreDetailsPayRatePopupDetails = function (request) {
		var deferred = $q.defer();
		var moreDetailsPayRatePopup;
		moreDetailsPayRatePopup = ngDialog.open({
			template: 'views/finance/unitpayratemoredetails.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				
				console.log("request===2323==",request.id);
				console.log("request===2323==",request.unitOfPayRates[0].unitOfPayDisplayId);
				$scope.code=request.unitOfPayRates[0].code
				$scope.name=request.unitOfPayRates[0].name
				$scope.notes=request.unitOfPayRates[0].notes
				$scope.unitOfPayDisplayId=request.unitOfPayRates[0].unitOfPayDisplayId
				
				
				
			}]

		});
		return deferred.promise;
	}
	return service1;
}]);				
	