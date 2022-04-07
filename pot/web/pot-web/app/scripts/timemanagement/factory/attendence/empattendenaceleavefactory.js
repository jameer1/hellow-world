'use strict';

app.factory('EmpAttendenaceLeaveFactory', ["ngDialog", "$q", "$filter", "$timeout", "EmpAttendanceService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, EmpAttendanceService, GenericAlertService, generalservice) {
	var leaveTypesPopup;
	var service = {};

	service.getProjLeaveCodes = function(clientId) {
		var deferred = $q.defer();
		leaveTypesPopup = ngDialog.open({
			template : 'views/timemanagement/attendance/leavetypes.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.leaveTypes = [];
				$scope.getLeaveTypes = function() {
					$scope.leaveTypes = generalservice.empLeaveTypes;
				}
				$scope.selectdType = function(leaveType) {
					var returnPopObj = {
						"projLeaveType" : leaveType
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();

				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);