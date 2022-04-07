'use strict';
app.factory('nonregularPayAndAllowancesFactory', ["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "nonregularPayAllowancesPastRecordsfactory", function(ngDialog, $q, $rootScope,blockUI, GenericAlertService,nonregularPayAllowancesPastRecordsfactory) {
	var nonregularPayAndAllowancesPopup;
	var service = {};
	service.nonregularPayAndAllowances = function(actionType) {
		var deferred = $q.defer();
		nonregularPayAndAllowancesPopup = ngDialog.open({
			template :'views/finance/financecentre/nonregularpayallowancepopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				
				$scope.viewPastrecords=function(){
		var 	viewPastrecordsDetails	=	nonregularPayAllowancesPastRecordsfactory.viewPreviousRecords();
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
app.factory("nonregularPayAllowancesPastRecordsfactory",["$rootScope", "ngDialog", "$q", "nonregularallowanceMoreDetailsfactory", function($rootScope,ngDialog,$q,nonregularallowanceMoreDetailsfactory){
    var services1={};
	var viewPreviousRecordsPopup;
    services1.viewPreviousRecords=function(){
	var deffered=$q.defer();
	viewPreviousRecordsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/nonregularpayallowancespastrecordspopup.html',
		showClose: false,
		className : 'ngdialog-theme-plain ng-dialogueCustom2',
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			$scope.moreDetails=function(){
			var financeMoreDetails=	nonregularallowanceMoreDetailsfactory.moreDetails();
			}
		} ]
	});
	
	return deffered.promise;
}

return	services1;
}]);

app.factory("nonregularallowanceMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/nonregularallowancesmoredetailspopup.html',
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);