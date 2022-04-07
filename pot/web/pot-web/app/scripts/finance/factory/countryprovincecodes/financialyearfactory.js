'use strict';
app.factory('FinancialYearFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "TaxCodeService", "GenericAlertService", "generalservice", "CountryService","$rootScope", function(ngDialog, $q, $filter, $timeout,blockUI, TaxCodeService, GenericAlertService,
	generalservice,CountryService,$rootScope) {		
	$rootScope.financialYearStartDate='';
	var taxCodePopUp;
	var service = {};
	service.financialYearPopUpDetails = function(actionType,editFinancialYear) {
		var deferred = $q.defer();
		taxCodePopUp = ngDialog.open({
			template : 'views/finance/countryprovince/financialyearpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.FinancialYearDetails=[];
				console.log("=====FinancialYearDetails from Factory========")
				console.log("=====actionType========",actionType)
				console.log("=====editFinancialYear========",editFinancialYear)
				if (actionType === 'Add') {
					$scope.actionTypeSave='Add';
					console.log("=====$scope.actionTypeSave========",$scope.actionTypeSave)
					$scope.FinancialYearDetails.push({
						"actionType":'Add',
						"fromDate": '',
						"toDate": ''
					});
					console.log("-----else-------")
				} else if(actionType === null){
					$scope.FinancialYearDetails= angular.copy(editFinancialYear);
				}else if(actionType === 'Edit') {
					$scope.FinancialYearDetails= angular.copy(editFinancialYear);
					$scope.FinancialYearDetails[0].actionType='Edit';
					$scope.actionTypeSave='Edit';
				}
				else
					{
					$scope.FinancialYearDetails= angular.copy(editFinancialYear);
					$scope.FinancialYearDetails[0].actionType='View';
					$scope.actionTypeSave='View';
					}
				
				$scope.saveFinancialYear=function()
				{
					$scope.closeThisDialog();
					deferred.resolve($scope.FinancialYearDetails);
				}
				
			$scope.getToDate=function(item){				
				
				var d1 = $filter('date')(new Date(item.fromDate), "dd-MMM-yyyy");
				$rootScope.financialYearStartDate = item.fromDate;
			    
			    var day=new Date(item.fromDate).getDate()-1;
			    var mon=new Date(item.fromDate).getMonth();
			    var year=new Date(item.fromDate).getFullYear()+1;
			    			
			    var d3=new Date(year, mon, day);
			    var d2 = $filter('date')((d3), "dd-MMM-yyyy");			     
					
					$scope.FinancialYearDetails[0].fromDate=d1;
					$scope.FinancialYearDetails[0].toDate=d2
					//$scope.FinancialHalfYearFactory.getToDate(d1);
					//console.log("fromDate",$rootScope.financialYearStartDate)
					//console.log("toDate",$scope.toDate)
				}
				
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
