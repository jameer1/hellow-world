'use strict';
app.factory('TimesheetSubmitFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ApproveUserFactory", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, blockUI, ApproveUserFactory, TimeSheetService, GenericAlertService) {
	var dateWisePopUp;
	var submitservice = {};

	submitservice.getTimeSheetSubmitDetails = function(crewTypeTO, timeSheetSearchReq, timeSheetDetails, maxHrs) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetsubmit.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom5',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.approver = [];
				$scope.comments == null;
				$scope.approverTo = {
					"id" : null,
					"code" : null,
					"name" : null

				}
				$scope.getModuleUserDetails = function(approverTo) {
					var getReq = {
						"moduleCode" : "CREATTIMESHET",
						"actionCode" : "APPROVE",
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"status" : 1,
						"permission" : "ASBUILTRCRD_TIMSHET_CREATTIMESHET_APPROVE"
					};

					ApproveUserFactory.getProjUsersOnly(getReq).then(function(data) {
						$scope.approverTo.id = data.approverTo.id;
						$scope.approverTo.name = data.approverTo.name;

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting ApproverList ", 'Error');
					});
				}, $scope.submitTimeSheetDetails = function() {
					if (crewTypeTO.id == 1) {
						$scope.submitCrewTimeSheetDetails();
					} else if (crewTypeTO.id == 2) {
						$scope.submitIndividualTimeSheetDetails();
					}
				}, $scope.submitCrewTimeSheetDetails = function() {
					var saveReq = {
						"timeSheetEmpDtlTOs" : timeSheetDetails,
						"status" : 1,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
							"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"apprStatus" : 'Submitted',
							"notificationStatus" : 'Pending',
							"notificationMsg" : 'Request for Approval',
							"apprUserId" : $scope.approverTo.id,
							"additional" : timeSheetSearchReq.additional,
							"reqComments" : $scope.comments,
							"maxHrs" : maxHrs
						}
					};
					blockUI.start();
					TimeSheetService.submitCrewTimeSheetDetails(saveReq).then(function(data) {
						blockUI.stop();
						if (data.status == "Warning") {
							GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
						} else {
							var resultData = GenericAlertService.alertMessageModal('TimeSheet Details submitted successfully', "Info");
							resultData.then(function() {
								$scope.closeThisDialog();
								deferred.resolve(data);
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('TimeSheet Details are  failed to Submit', "Error");
					});
				}, $scope.submitIndividualTimeSheetDetails = function() {
					var saveReq = {
						"timeSheetEmpDtlTOs" : timeSheetDetails,
						"status" : 1,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"empId" : timeSheetDetails[0].empRegId,
							"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"apprStatus" : 'Submitted',
							"apprUserId" : $scope.approverTo.id,
							"additional" : timeSheetSearchReq.additional,
							"reqComments" : $scope.comments,
							"maxHrs" : maxHrs
						}
					};
					blockUI.start();
					TimeSheetService.submitIndividualTimeSheetDetails(saveReq).then(function(data) {
						blockUI.stop();
						if (data.status == "Warning") {
							GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
						} else {
							var resultData = GenericAlertService.alertMessageModal('TimeSheet Details are Submitted successfully', "Info");
							resultData.then(function() {
								$scope.closeThisDialog();
								deferred.resolve(data);
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('TimeSheet Details are  failed to Submit', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return submitservice;
}]);
