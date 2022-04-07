app.factory("taxpaymentReceiverFactory",["ngDialog", "$q", "$rootScope", "taxpaymentReceiverPastRecordsfactory", function(ngDialog,$q,$rootScope,taxpaymentReceiverPastRecordsfactory){
	
	var taxPaymentReceiverPopup;
	var services={};
	
	services.taxPaymentReceiver=function(){
		
		var deffered=$q.defer();
		taxPaymentReceiverPopup=ngDialog.open({
			
			template:"views/finance/financecentre/taxpaymentreceiverpopup.html",
			scope:$rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.viewPastrecords=function(){
					var 	viewPastrecordsDetails	=	taxpaymentReceiverPastRecordsfactory.viewPreviousRecords();
							}
			} ]
			
		});
		return deffered.promise;
	}
	return services;
	
}]);

app.factory("taxpaymentReceiverPastRecordsfactory",["$rootScope", "ngDialog", "$q", "taxPaymentReceiverMoreDetailsfactory", function($rootScope,ngDialog,$q,taxPaymentReceiverMoreDetailsfactory){
    var services1={};
	var viewPreviousRecordsPopup;
    services1.viewPreviousRecords=function(){
	var deffered=$q.defer();
	viewPreviousRecordsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/taxpaymentreceiverpastrecordspopup.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			$scope.moreDetails=function(){
				var financeMoreDetails=	taxPaymentReceiverMoreDetailsfactory.moreDetails();
				}
		} ]
	});
	
	return deffered.promise;
}

return	services1;
}]);


app.factory("taxPaymentReceiverMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/taxpaymentreceivermoredetailspopup.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);