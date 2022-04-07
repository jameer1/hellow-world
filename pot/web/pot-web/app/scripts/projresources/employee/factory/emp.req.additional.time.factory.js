'use strict';
app.factory('RequestForEmployeeAdditionalTimeFactory', ["ngDialog", "$q", "$filter", "EpsProjectSelectFactory", "$timeout", "$rootScope", "ApproveUserFactory", "EmpRegisterService", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, EpsProjectSelectFactory,$timeout, 
	$rootScope,ApproveUserFactory, EmpRegisterService, MaterialRegisterService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getAdditionalTimeDetails = function(selectedValue) {
		console.log(selectedValue);
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/projresources/projempreg/approvaltransfer/requestforadditionaltime.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.selectedValue = selectedValue;
			
				$scope.searchProject = {};
				$scope.approverTo={
						"id":null,
						"code":null,
						"name":null
				};
				
				$scope.additionalTimeDetails = function(){
					$scope.fromProjId=$scope.selectedValue.fromProjId;
					$scope.toProjId=$scope.selectedValue.toProjId;
					$scope.reqCode=$scope.selectedValue.reqCode;
					$scope.reqNotifyCode=$scope.selectedValue.reqNotifyCode;
					$scope.destinationProjName = $scope.selectedValue.toProjName;
					$scope.notifyId = selectedValue.notifyId;
				};
				$scope.additionalTimeDetails();
				$scope.getModuleUserDetails = function(approverTo) {
					if($scope.destinationProjName==undefined || $scope.destinationProjName==null){
						GenericAlertService.alertMessage('Please Selct the ProjectName', "Warning");
						return;
					}
					var getReq = {
						"moduleCode" : "EMPTRSFR", 
						"actionCode" : "APPROVE",
						"permission" : "PRJ_PRJSTG_EMPTRSFR_APPROVE",
						"projId" : $scope.toProjId
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
					selectedUser.then(function(data) {
						approverTo.id = data.approverTo.id;
						approverTo.name = data.approverTo.name;
					});
				},			
				$scope.submitNotifications = function() {
					console.log($scope.selectedValue);
					console.log($scope.fromProjId);
					console.log($scope.toProjId);
					console.log($scope.reqCode);
					console.log($scope.reqNotifyCode);
					console.log($scope.destinationProjName);
					console.log($scope.notifyId);
					var saveReqObj={
						"projId": $scope.fromProjId,
						"apprUserId":$scope.approverTo.id,
						"notifyRefId":1,
						"status" : 1,
						"type":'Additional Time For Submit',
						"notifyStatus":'Pending',
						"reqId":1,
						"comments":$scope.comments,
						"forProject": $scope.toProjId,
						"notifyId" : $scope.notifyId
					};
					
					var saveReq = {
						"employeeNotificationsTOs":[saveReqObj],
						"status" : 1,
						"projId" :  $scope.fromProjId
					};
					console.log(saveReq);
					
					EmpRegisterService.saveEmployeeNotifications (saveReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Additional Time Details are Submitted successfully', "Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							deferred.resolve('Success');
						});
					}, function(error) {
						GenericAlertService.alertMessage('Additional Time Details are  failed to Submit', "Error");
					});
				}
				
				
				
					
			} ]   
		});
		return deferred.promise;
	}
	return service;
}]);
