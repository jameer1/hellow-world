'use strict';

app.factory('EmpRegisterDataShareFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", function(ngDialog, $q, $filter, $timeout, $rootScope) {
	var service = {};
	var returnPopObj = null;
	service.getShareObjData = function(empMapsData) {
		var deferred = $q.defer();
		if (empMapsData != null) {
			returnPopObj = {
				"empMapsData" : empMapsData
			};
		}
		deferred.resolve(returnPopObj);
		return deferred.promise;
	}
	return service;
}]);
