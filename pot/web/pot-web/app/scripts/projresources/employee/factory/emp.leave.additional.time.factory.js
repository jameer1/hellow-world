'use strict';
app.factory('RequestForEmployeeLeaveAdditionalTimeFactory', ["ngDialog", "$q", "$filter", "EpsProjectSelectFactory", "$timeout", "$rootScope", "ApproveUserFactory", "EmpRegisterService", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, EpsProjectSelectFactory,$timeout, $rootScope,ApproveUserFactory, EmpRegisterService, MaterialRegisterService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getAdditionalTimeDetails = function(empLeaveReqApprTO,leaveCodeMap) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/projresources/projempreg/leaveapprovalform/requestforadditionaltime.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empLeaveReqApprTO = empLeaveReqApprTO;
				$scope.to = $scope.empLeaveReqApprTO.toProjId;
				$scope.leaveCodeMap=leaveCodeMap;
				$scope.searchProject = {};
				$scope.approverTo = {
						"id":null,
						"code":null,
						"name":null
				};
				
				$scope.empProjId = $scope.empLeaveReqApprTO.empProjId;
				$scope.reqCode = $scope.empLeaveReqApprTO.reqCode;
				$scope.notifyCode = $scope.empLeaveReqApprTO.notifyCode;	

				$scope.getModuleUserDetails = function(approverTo) {
					var getReq = {
						"moduleCode" : "EMPTRSFR",
						"actionCode" : "APPROVE",
						"permission" : "PRJ_PRJSTG_EMPTRSFR_APPROVE",
						"projId" : $scope.empProjId
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
					selectedUser.then(function(data) {
						approverTo.id = data.approverTo.id;
						approverTo.name = data.approverTo.name;
					});
				},			
				$scope.submitNotifications = function() {
					var saveReqObj = {
						"projId": $scope.empProjId,
						"apprUserId":$scope.approverTo.id,
						"notifyRefId":1,
						"status" : 1,
						"type":'Additional Time for Request',
						"notifyStatus":'Pending',
						"reqId":1,
						"comments":$scope.comments,
						"notifyId": $scope.empLeaveReqApprTO.notifyId
					};
					
					var saveReq = {
						"employeeNotificationsTOs":[saveReqObj],
						"status" : 1,
						"projId" :  $scope.empProjId
					};

					EmpRegisterService.saveEmployeeLeaveNotifications (saveReq).then(function(data) {
						$scope.closeThisDialog();
						GenericAlertService.alertMessage('Additional Time Details are Submitted successfully', "Info");
						deferred.resolve(data);
					}, function(error) {
						GenericAlertService.alertMessage('Additional Time Details are  failed to Submit', "Error");
					});
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
