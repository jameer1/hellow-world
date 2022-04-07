'use strict'
app.factory("TaxCodeRatesRulesFactory",["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "taxCodesRatesRulesPastRecordsfactory", function(ngDialog,$q,$rootScope,blockUI,GenericAlertService,taxCodesRatesRulesPastRecordsfactory){
	var TaxCodeRatesRulesPopup;
	var services={};
	services.TaxCodeRatesRules=function(){
		var deffered=$q.defer();
		TaxCodeRatesRulesPopup=ngDialog.open({
			templateUrl:'views/finance/financecentre/taxcodesratesrulespopup.html',
				scope : $rootScope,
				closeByDocument : false,
				showClose : false,
				controller : [ '$scope', function($scope) {
					
					$scope.viewPastrecords=function(){
						var 	viewPastrecordsDetails	=	taxCodesRatesRulesPastRecordsfactory.viewPreviousRecords();
								}
				} ]
		});
		return deferred.promise;
	
	}
	
	return services;
	
	
}]);



app.factory("taxCodesRatesRulesPastRecordsfactory",["$rootScope", "ngDialog", "$q", "taxcoderatesrulesMoreDetailsfactory", function($rootScope,ngDialog,$q,taxcoderatesrulesMoreDetailsfactory){
    var services1={};
	var viewPreviousRecordsPopup;
    services1.viewPreviousRecords=function(){
	var deffered=$q.defer();
	viewPreviousRecordsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/taxcodesratesrulespastrecordspopup.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
		
			
			$scope.moreDetails=function(){
				var financeMoreDetails=	taxcoderatesrulesMoreDetailsfactory.moreDetails();
				}
		} ]
	});
	
	return deffered.promise;
}

return	services1;
}]);


app.factory("taxcoderatesrulesMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/taxcoderatesrulesMoreDetailsfactory.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);