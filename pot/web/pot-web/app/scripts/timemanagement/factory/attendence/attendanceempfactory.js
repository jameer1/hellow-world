'use strict';

app.factory('AttendanceEmpPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "EmpAttendanceService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, EmpAttendanceService, GenericAlertService) {
	var service = {};
	service.getEmpRegDetails = function(empAttendReq, projId) {

		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId,
			"crewId" : empAttendReq.crewLabelKeyTO.id,
			"attendenceId" : empAttendReq.attendenceId,
			"attendenceMonth" : empAttendReq.attendenceMonth
		};
		EmpAttendanceService.getNonAttendanceEmpRegDetails(req).then(function(data) {
			var empDtlsPopup = service.openPopup(data.empRegLabelKeyTOs, empAttendReq, projId);
			empDtlsPopup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage('An error occurred while fetching Employee details', "Error");
			});
		}, function(error) {
			GenericAlertService.alertMessage('An error occurred while fetching Employee details', "Error");
		});
		return deferred.promise;

	}, service.openPopup = function(empRegLabelKeyTOs, empAttendReq, projId) {
		var deferred = $q.defer();
		var empDtlsPopup = [];
		empDtlsPopup = ngDialog.open({
			template : 'views/timemanagement/attendance/attendanceempregdtl.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',

			controller : [ '$scope', function($scope) {
				$scope.empRegLabelKeyTOs = empRegLabelKeyTOs;
				var selectedEmpRegTOs = [];
				$scope.selectEmpRegDtls = function(empRegLabelKeyTO) {
					if (empRegLabelKeyTO.select) {
						selectedEmpRegTOs.push(empRegLabelKeyTO);
					} else {
						selectedEmpRegTOs.pop(empRegLabelKeyTO);
					}
				}, $scope.addEmpRegToAttendance = function(empRegisterDtlTO) {
					var empRegList = [];
					angular.forEach(selectedEmpRegTOs, function(value, key) {
						empRegList.push({
							"attandanceId" : empAttendReq.attendenceId,
							"empId" : value.id,
							"crewId" : empAttendReq.crewLabelKeyTO.id,
							"status" : 1
						});
					});

					var saveReg = {
						"empAttendenceTOs" : empRegList,
						"status" : 1,
						"projId" : projId,
						"crewId" : empAttendReq.crewLabelKeyTO.id,
						"attendenceId" : empAttendReq.attendenceId
					};
					blockUI.start();
					EmpAttendanceService.saveEmpRegDetails(saveReg).then(function(data) {
						blockUI.stop();
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('An error occurred while adding Employee details', "Error");
					});
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);