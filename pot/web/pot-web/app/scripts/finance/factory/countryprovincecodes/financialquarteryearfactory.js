'use strict';
app.factory('FinancialQuarterYearFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "TaxCodeService", "GenericAlertService", "generalservice", "CountryService","$rootScope", function(ngDialog, $q, $filter, $timeout,blockUI, TaxCodeService, GenericAlertService,
	generalservice,CountryService,$rootScope) {
	var taxCodePopUp;
	var service = {};
	service.financialQuarterYearPopUpDetails = function(actionType,editFinancialQuarterYear) {
		var deferred = $q.defer();
		taxCodePopUp = ngDialog.open({
			template : 'views/finance/countryprovince/financialquarteryearpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.FinancialQuarterYearDetails=[];
				if (actionType === 'Add') {
					$scope.actionTypeSave='Add';
					$scope.FinancialQuarterYearDetails.push({
						"actionType":'Add',
						"quarterOneFromDate":'',
						"quarterOneToDate":'',
						"quarterSecondFromDate":'',
						"quarterSecondToDate":'',
						"quarterThirdFromDate":'',
						"quarterThirdToDate":'',
						"quarterFourthFromDate":'',
						"quarterFourthToDate":''
					});
				} 
				 else if(actionType === null){
						$scope.FinancialQuarterYearDetails= angular.copy(editFinancialQuarterYear);
					}
				 else if(actionType === 'Edit') {
					$scope.FinancialQuarterYearDetails = angular.copy(editFinancialQuarterYear);
					$scope.FinancialQuarterYearDetails[0].actionType='Edit';
					$scope.actionTypeSave='Edit';
				}
				else
				{
				$scope.FinancialQuarterYearDetails = angular.copy(editFinancialQuarterYear);
				$scope.FinancialQuarterYearDetails[0].actionType='View';
				$scope.actionTypeSave='View';
				}
			
				$scope.saveFinancialQuarterYear=function()
				{
					$scope.closeThisDialog();
					deferred.resolve($scope.FinancialQuarterYearDetails);
				}
				
				
				$scope.goToDate=function(item){
					var d1 = $filter('date')(new Date(item.quarterOneFromDate), "dd-MMM-yyyy");
					$rootScope.financialYearStartDate = item.quarterOneFromDate;
					var day=new Date(d1).getDate()-1;
			        var mon=new Date(d1).getMonth()+3;
			        var year=new Date(d1).getFullYear();			        

					var d2 = $filter('date')(new Date(year, mon, day), "dd-MMM-yyyy");
					var d3 = $filter('date')(new Date(year, mon, day+1), "dd-MMM-yyyy");
					var d4 = $filter('date')(new Date(year, mon+3, day), "dd-MMM-yyyy");
					var d5 = $filter('date')(new Date(year, mon+3, day+1), "dd-MMM-yyyy");
					var d6 = $filter('date')(new Date(year, mon+6, day), "dd-MMM-yyyy");
					var d7 = $filter('date')(new Date(year, mon+6, day+1), "dd-MMM-yyyy");
					var d8 = $filter('date')(new Date(year, mon+9, day), "dd-MMM-yyyy");
					
					$scope.FinancialQuarterYearDetails[0].quarterOneFromDate=d1;
					$scope.FinancialQuarterYearDetails[0].quarterOneToDate=d2;
					$scope.FinancialQuarterYearDetails[0].quarterSecondFromDate=d3;
					$scope.FinancialQuarterYearDetails[0].quarterSecondToDate=d4;
					$scope.FinancialQuarterYearDetails[0].quarterThirdFromDate=d5;
					$scope.FinancialQuarterYearDetails[0].quarterThirdToDate=d6;
					$scope.FinancialQuarterYearDetails[0].quarterFourthFromDate=d7;
					$scope.FinancialQuarterYearDetails[0].quarterFourthToDate=d8;
					
					//$scope.saveFinancialQuarterYear();
					
				}
				
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
