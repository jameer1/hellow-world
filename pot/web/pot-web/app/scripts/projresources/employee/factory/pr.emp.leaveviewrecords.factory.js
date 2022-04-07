'use strict';

app.factory('LeaveApprovalViewFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
	var leaveapprovalviewPopUp;
	var selectedLeaveApproval = [];
	var service = {};
	service.leaveapprovalviewPopUp = function(actionType, editviewreq) {
		var deferred = $q.defer();
		leaveapprovalviewPopUp = ngDialog.open({

			template : 'views/projresources/projempreg/leaverequestform/viewleaverequestpopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedViewRecords = [];
				$scope.viewleavereq = [];
				if (actionType === 'Add') {
					$scope.viewleavereq.push({
						"selected" : false,
						"leaveCode" : null,
						"leaveType" : null,
						"startDate" : null,
						"endDate" : null,
						"workdays" : null,
						"reason" : null,
						"approverID" : null,
						"approverComments" : null,
						"applicantComments" : null,
					});
				} else {
					$scope.viewleavereq = angular.copy(editviewreq);
					editviewreq = [];
				}
				$scope.addRows = function() {
					$scope.viewleavereq.push({
						"selected" : false,
						"leaveCode" : null,
						"leaveType" : null,
						"startDate" : null,
						"endDate" : null,
						"workdays" : null,
						"reason" : null,
						"approverID" : null,
						"approverComments" : null,
						"applicantComments" : null,
					});
				}

			
				$scope.viewPopUpRowSelect = function(view) {
					if (view.selected) {
						selectedViewRecords.push(view);
					} else {
						selectedViewRecords.pop(view);
					}
				}, $scope.deleteRows = function() {
					if (selectedViewRecords.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					}

					if ($scope.viewleavereq.length == 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						return;
					}

					if (selectedViewRecords.length < $scope.viewleavereq.length) {
						angular.forEach(selectedViewRecords, function(value, key) {
							$scope.viewleavereq.splice($scope.viewleavereq.indexOf(value), 1);
						});

						selectedViewRecords = [];

					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				$scope.saveApprovalReq = function() {
					var req = {

					}
					EmpRegisterService.saveEmpLeaveAppr(req).then(function(data) {
						GenericAlertService.alertMessage('EmpLeaveAppr (s) is/are ' + data.message, data.status);
					}, function(error) {
						GenericAlertService.alertMessage('EmpLeaveAppr(s) is/are  Failed to Save ', "Info");
					});
					ngDialog.close();
				}
				
				
				$scope.approvedLeave = function() {
					var req = {

					}
					EmpRegisterService.approvedLeave(req).then(function(data) {
						GenericAlertService.alertMessage('approvedLeave (s) is/are ' + data.message, data.status);
					}, function(error) {
						GenericAlertService.alertMessage('approvedLeave(s) is/are  Failed to Save ', "Info");
					});
					ngDialog.close();
				}
				$scope.notapprovedLeave = function() {
					var req = {

					}
					EmpRegisterService.notapprovedLeave(req).then(function(data) {
						GenericAlertService.alertMessage('notapprovedLeave (s) is/are ' + data.message, data.status);
					}, function(error) {
						GenericAlertService.alertMessage('notapprovedLeave(s) is/are  Failed to Save ', "Info");
					});
					ngDialog.close();
				}
				
			} ]
		});
		return deferred.promise;
	}

	return service;
}]);