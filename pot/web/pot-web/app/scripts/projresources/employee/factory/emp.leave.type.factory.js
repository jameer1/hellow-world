'use strict';
app.factory('EmpLeaveTypeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjLeaveTypeService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjLeaveTypeService, GenericAlertService, generalservice) {
	var service = {};
	service.getLeaveTypes = function(leaveExistingMap) {
		var deferred = $q.defer();

		var req = service.openPopup(leaveExistingMap);
		req.then(function (data) {
			var returnPopObj = {
				"leaveTypeTO": data
			};
			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}, service.openPopup = function(leaveExistingMap) {
		var deferred = $q.defer();
		var leaveTypePopUp = ngDialog.open({
			template : 'views/projresources/projempreg/leaverequestform/empleavetypepopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.leaveExistingMap=leaveExistingMap;
				$scope.leaveTypeTOs = generalservice.empLeaveTypes1;
				
				$scope.selectLeaveType = function(leaveTypeTO) {
					$scope.closeThisDialog();
					deferred.resolve(leaveTypeTO);
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
