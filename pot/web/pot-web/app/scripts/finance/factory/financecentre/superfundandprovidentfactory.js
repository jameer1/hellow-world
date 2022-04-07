'use strict';
app.factory('superFundProivdentFactory', ["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "superFundProvidentPastRecordsfactory", function(ngDialog, $q, $rootScope,blockUI, GenericAlertService,superFundProvidentPastRecordsfactory) {
	var superFundProivdentPopup;
	var service = {};
	service.superFundProivdent = function(actionType) {
		var deferred = $q.defer();
		superFundProivdentPopup = ngDialog.open({
			template :'views/finance/financecentre/superfundandprovidentpopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.viewPastrecords=function(){
					var 	viewPastrecordsDetails	=superFundProvidentPastRecordsfactory.viewPreviousRecords();
							}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
app.factory("superFundProvidentPastRecordsfactory",["$rootScope", "ngDialog", "$q", "superfundprovidentMoreDetailsfactory", function($rootScope,ngDialog,$q,superfundprovidentMoreDetailsfactory){
    var services1={};
	var viewPreviousRecordsPopup;
    services1.viewPreviousRecords=function(){
	var deffered=$q.defer();
	viewPreviousRecordsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/superfundandprovidentpastrecordspopup.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			$scope.moreDetails=function(){
				var financeMoreDetails=	superfundprovidentMoreDetailsfactory.moreDetails();
				}
		} ]
	});
	
	return deffered.promise;
}

return	services1;
}]);


app.factory("superfundprovidentMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/superfundprovidentmoredetailspopup.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);