'use strict';

app.factory('EmpPayRoleCycleMoreDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, UnitPayRateService, GenericAlertService) {
var service1 = {};		
	service1.moreDetailsEmployeePayRatePopupDetails = function (request) {
		var deferred = $q.defer();
		var moreDetailsEmployeePayRate;
		moreDetailsEmployeePayRate = ngDialog.open({
			template: 'views/finance/financecentre/employeeCyclemoredetails.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				
				//console.log("request===2323==",request.id);
				$scope.cycleDueDate=request.empPayRollCycles[0].cycleDueDate
				$scope.cyclePeriodStartFrom=request.empPayRollCycles[0].cyclePeriodStartFrom
				$scope.empPayRoleCycleDisplayId=request.empPayRollCycles[0].empPayRoleCycleDisplayId
				$scope.employeeCategory=request.empPayRollCycles[0].employeeCategory
				$scope.employeeType=request.empPayRollCycles[0].employeeType
				$scope.status=request.status
				$scope.payrollcycle=request.empPayRollCycles[0].payrollcycle
				
				
				
			}]

		});
		return deferred.promise;
	}
	return service1;
}]);				
	