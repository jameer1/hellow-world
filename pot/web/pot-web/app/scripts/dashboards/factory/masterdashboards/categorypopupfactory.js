'use strict';
app.factory('CategorySelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", 
	function(ngDialog, $q, $filter, $timeout, $rootScope,GenericAlertService) {
	var service = {};
	var getCategoryList;
	
	service.openPopup = function(data) {
		var deferred = $q.defer();
		var getCategoryList = ngDialog.open({
			template : 'views/dashboards/masterdashboard/categorypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedcategories = [];
				var categoryName = [];
				$scope.categoryList=[];
				$scope.categoryList=[{
					name : 'Performance',
					/*dashboards: [{report1: url: 'html',}, report2]*/
				}, {
					name : 'Progress',
				}, {
					name : 'Time',
				}, {
					name : 'Budget',
				}, {
					name : 'Cost',
				}, {
					name : 'Labour',
				}, {
					name : 'Actual Cost',
				}, {
					name : 'Earned Value',
				}, {
					name : 'Remaining Budget',
				}, {
					name : 'Estimate to Complete',
				}, {
					name : 'Estimate At Completion',
				}];
				$scope.selectedcategories = function(category) {	
					var returnPopObj = {
						"selectedcategory" : {
							"catgName" : category.name,
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			}]
		});
		return deferred.promise;
	};
	return service;
}]);