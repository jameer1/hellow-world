'use strict';
app.factory('regularPayAndAllowancesFactory', ["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "regularPayAllowancesPastRecordsfactory", function(ngDialog, $q, $rootScope,blockUI, GenericAlertService,regularPayAllowancesPastRecordsfactory) {
	var regularPayAndAllowancesPopup;
	var service = {};
	service.regularPayAndAllowances = function(actionType) {
		var deferred = $q.defer();
		regularPayAndAllowancesPopup = ngDialog.open({
			template :'views/finance/financecentre/regularpayallowancespopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.viewPastRecords=function(){
					var viewpastrecordsDetails=regularPayAllowancesPastRecordsfactory.viewPreviousRecords();
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
app.factory("regularPayAllowancesPastRecordsfactory",["$rootScope", "ngDialog", "$q", "regularPayAllowancesMoreDetailsfactory", function($rootScope,ngDialog,$q,regularPayAllowancesMoreDetailsfactory){
    var services1={};
	var viewPreviousRecordsPopup;
    services1.viewPreviousRecords=function(){
	var deffered=$q.defer();
	viewPreviousRecordsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/regularpayallowancespastrecordspopup.html',
		className : 'ngdialog-theme-plain ng-dialogueCustom2',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			$scope.moreDetails=function(){
				var financeMoreDetails=regularPayAllowancesMoreDetailsfactory.moreDetails();
			}
		} ]
	});
	
	return deffered.promise;
}

return	services1;
}]);
app.factory("regularPayAllowancesMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/regularPayAllowancesmoredetailspopup.html',
		className : 'ngdialog-theme-plain ng-dialogueCustom0',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);