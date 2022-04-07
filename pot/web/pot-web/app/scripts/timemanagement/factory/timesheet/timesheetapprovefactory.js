'use strict';
app.factory('TimesheetApproveFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ModuleUserFactory", "blockUI", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ModuleUserFactory, blockUI,TimeSheetService, GenericAlertService) {
	var dateWisePopUp;
	var submitservice = {};

	submitservice.getTimeSheetAproveDetails = function(crewTypeTO, timeSheetSearchReq, timeSheetDetails) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/timesheetapprove.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom5',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.comments == null;
				$scope.userLabelKeyTO = {
					"id" : null,
					"code" : null,
					"name" : null

				}
				$scope.approveTimeSheetDetails = function() {
					if (crewTypeTO.id == 1) {
						$scope.approveCrewTimeSheetDetails();
					} else if (crewTypeTO.id == 2) {
						$scope.approveIndividualTimeSheetDetails();
					}
				},
				$scope.approveCrewTimeSheetDetails = function() {
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
							"apprStatus" : 'Approved',
							"notificationStatus" : 'Approved',
							"notificationmsg" : 'Request Approved',
							"apprUserId" : $scope.userLabelKeyTO.id,
							"apprComments" : $scope.comments,
							"maxHrs" : timeSheetSearchReq.maxHrs
						}
					};
					blockUI.start();
					TimeSheetService.approveCrewTimeSheetDetails(saveReq).then(function(data) {
						blockUI.stop();
						var resultData = GenericAlertService.alertMessageModal('Time Sheet Details Approved successfully', "Info");
						resultData.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('TimeSheet Details are  failed to Approve', "Error");
					});
				}, $scope.approveIndividualTimeSheetDetails = function() {
					var saveReq = {
						"timeSheetEmpDtlTOs" : timeSheetDetails,
						"status" : 1,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"empId" : timeSheetSearchReq.timesheetUserLabelKeyTO.id,
							"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"apprStatus" : 'Approved',
							"apprUserId" : $scope.userLabelKeyTO.id,
							"apprComments" : $scope.comments,
							"maxHrs" : timeSheetSearchReq.maxHrs
						}
					};
					blockUI.start();
					TimeSheetService.approveIndividualTimeSheetDetails(saveReq).then(function(data) {
						blockUI.stop();
						var resultData = GenericAlertService.alertMessageModal('Time Sheet Details Approved successfully', "Info");
						resultData.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('TimeSheet Details are  failed to Approve', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return submitservice;
}]);
