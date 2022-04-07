'use strict';
app.factory('EmpLeaveBalanceFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "ProjLeaveTypeService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, ProjLeaveTypeService, GenericAlertService, generalservice) {
	var service = {};
	service.getEmpLeaveBalanceDetails = function( empLeaveAccuredTOs, empLeaveAttendanceYearTO) {

		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/projresources/projempreg/leave&attendence/empleavebalanceepopup.html',
			closeByDocument : false,
			className:'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.leaveCodeTypeTOs = generalservice.empLeaveTypes;
				$scope.empLeaveAccuredTOs = empLeaveAccuredTOs;
				$scope.empLeaveAttendanceYearTOs = [];

				$scope.total = 0;

				empLeaveAttendanceYearTO.period = 'Days';
				$scope.empLeaveAttendanceYearTOs.push(empLeaveAttendanceYearTO);

				angular.forEach($scope.empLeaveAttendanceYearTOs, function(value, key) {
					angular.forEach(value.empLeaveAccuredTOs, function(value1, key) {
						let usedLeaves = 0;
						if (value.empLeaveAttendanceMap[value1.leaveCode]) {
							usedLeaves = parseInt(value.empLeaveAttendanceMap[value1.leaveCode].displayNamesMap["total"]);
						}
						$scope.total += (value1.totalLeaves - usedLeaves);
					});
				});
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
