'use strict';
app.factory('unitOfPayRateFactory', ["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "unitOfPayrateViewPreviousfactory", function(ngDialog, $q, $rootScope,blockUI, GenericAlertService,unitOfPayrateViewPreviousfactory) {
	var empUnitOfPayRate;
	var service = {};
	service.empUnitOfPayRate = function() {
		var deferred = $q.defer();
		empUnitOfPayRate = ngDialog.open({
			template :'views/finance/financecentre/empunitofpayratepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			controller : [ '$scope', function($scope) {
				$scope.viewPastrecords=function(){
					var previousrecords=unitOfPayrateViewPreviousfactory.viewPreviousRecords();
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);

app.factory("unitOfPayrateViewPreviousfactory",["$rootScope", "ngDialog", "$q", "unitOfPayrateMoreDetailsfactory", function($rootScope,ngDialog,$q,unitOfPayrateMoreDetailsfactory){
	    var services1={};
    	var viewPreviousRecordsPopup;
	    services1.viewPreviousRecords=function(){
		var deffered=$q.defer();
		viewPreviousRecordsPopup=ngDialog.open({
			templateUrl:'views/finance/financecentre/unitofpayratepastrecordspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose : false,
			scope:$rootScope,
			controller : [ '$scope', function($scope) {
				$scope.moreDetails=function(){
					alert();
					var previousrecords=unitOfPayrateMoreDetailsfactory.moreDetails();
				}
			} ]
		});
		
		return deffered.promise;
	}
	
return	services1;

}]);
app.factory("unitOfPayrateMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/empunitofpayratepopup.html',
		className : 'ngdialog-theme-plain ng-dialogueCustom2',
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			$scope.items=['Current','Superseded','All'];
			$scope.status=	$scope.items[1];
				
			var req = {
					"status" : 1,
					"projIds" : $scope.selectedProjIds
				};
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);