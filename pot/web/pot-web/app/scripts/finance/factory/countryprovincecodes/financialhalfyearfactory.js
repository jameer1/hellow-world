'use strict';
app.factory('FinancialHalfYearFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "TaxCodeService", "GenericAlertService", "generalservice", "CountryService","$rootScope", function(ngDialog, $q, $filter, $timeout,blockUI, TaxCodeService, GenericAlertService,
	generalservice,CountryService,$rootScope) {
	var taxCodePopUp;
	var service = {};
	service.financialHalfYearPopUpDetails = function(actionType,editFinancialHalfYear) {
		var deferred = $q.defer();
		taxCodePopUp = ngDialog.open({
			template : 'views/finance/countryprovince/financialhalfyearpopup.html',
			className  : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				console.log("financialHalfYearPopUpDetails")
				console.log("actionType",actionType)
				$scope.FinancialHalfYearDetails=[];
				if (actionType === 'Add') {
					$scope.actionTypeSave='Add';
					$scope.FinancialHalfYearDetails.push({
						"actionType":'Add',
						"firstFromDate":'',
						"firstToDate":'',
						"secondFromDate":'',
						"secondToDate":'',
					})
				} else if(actionType === null){
					$scope.FinancialHalfYearDetails= angular.copy(editFinancialHalfYear);
				}else if(actionType === 'Edit') { 
					$scope.FinancialHalfYearDetails = angular.copy(editFinancialHalfYear);
					$scope.FinancialHalfYearDetails[0].actionType='Edit';
					$scope.actionTypeSave='Edit';
				}else
					{
					$scope.FinancialHalfYearDetails = angular.copy(editFinancialHalfYear);
					$scope.FinancialHalfYearDetails[0].actionType='View';
					$scope.actionTypeSave='View';
					}
				
				$scope.saveFinancialHalfYear=function()
				{
					//let FinancialHalfYearDetails=angular.copy($scope.FinancialHalfYearDetails);
					$scope.closeThisDialog();
					deferred.resolve($scope.FinancialHalfYearDetails);
				}
				
				$scope.getToDate=function(item){
					console.log("====getToDate======")
					$scope.FinancialHalfYearDetails= angular.copy($scope.FinancialHalfYearDetails)
					console.log("$scope.FinancialHalfYearDetails======1",$scope.FinancialHalfYearDetails)
					
					
					console.log("rootscope variable-------",$rootScope.financialYearStartDate)
					//var d1 = $filter('date')(new Date($scope.FinancialHalfYearDetails[0].firstFromDate), "dd-MMM-yyyy");
					var d1 = $filter('date')(new Date(item.firstFromDate), "dd-MMM-yyyy");
					$rootScope.financialYearStartDate=item.firstFromDate;
					var day=new Date(item.firstFromDate).getDate()-1;
					//console.log("d1 value",d1)
			        var mon=new Date(item.firstFromDate).getMonth()+6;
			        var year=new Date(item.firstFromDate).getFullYear();			        

					var d2 = $filter('date')(new Date(year, mon, day), "dd-MMM-yyyy");
					var d3 = $filter('date')(new Date(year, mon, day+1), "dd-MMM-yyyy");
					var d4 = $filter('date')(new Date(year, mon+6, day), "dd-MMM-yyyy");
					
					$scope.FinancialHalfYearDetails[0].firstFromDate=d1;
					$scope.FinancialHalfYearDetails[0].firstToDate=d2
					$scope.FinancialHalfYearDetails[0].secondFromDate=d3
					$scope.FinancialHalfYearDetails[0].secondToDate=d4
					//$scope.saveFinancialHalfYear();
					
				}
				
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
