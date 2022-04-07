'use strict';
app.factory('RequestForMaterialAdditionalTimeFactory', ["ngDialog", "$q", "blockUI", "$filter", "EpsProjectSelectFactory", "$timeout", "$rootScope", "ApproveUserFactory", "EmpRegisterService", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, blockUI, $filter, EpsProjectSelectFactory,$timeout, 
	$rootScope,ApproveUserFactory, EmpRegisterService, MaterialRegisterService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getAdditionalTimeDetails = function(selectedValue) {
		console.log(selectedValue);
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/projresources/projmaterialreg/approvalmaterialtransfer/requestforaddition.html',
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
					$scope.notifyCode=$scope.selectedValue.notifyCode;
					$scope.destinationProjName = $scope.selectedValue.toProjName;
					$scope.fromStoreId = selectedValue.fromStoreId
					$scope.toStoreId = selectedValue.toStoreId
					$scope.notifyId = selectedValue.notifyId;
				};
				$scope.additionalTimeDetails();
				$scope.getModuleUserDetails = function(approverTo) {
					if($scope.destinationProjName==undefined || $scope.destinationProjName==null){
						GenericAlertService.alertMessage('Please Selct the ProjectName', "Warning");
						return;
					}
					var getReq = {
							"moduleCode" : "MATRLTRSFR",
							"actionCode" : "APPROVE",
							"permission" : "PRJ_PRJSTG_MATRLTRSFR_APPROVE",
							"projId" : $scope.toProjId
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
					selectedUser.then(function(data) {
						approverTo.id = data.approverTo.id;
						approverTo.name = data.approverTo.name;
					});
				},			
				$scope.submitNotifications = function() {
					
					var saveReqObj={
						"projId":$scope.fromProjId ,
						"apprUserId":$scope.approverTo.id,
						"notifyRefId":1,
						"status" : 1,
						"type":'Additional Time For Submit',
						"notifyStatus":'Pending',
						"reqId":1,
						"reqComments":$scope.comments,
						"forProjId":$scope.toProjId,
						"requisitionCode": $scope.reqCode,
						"notificationCode": $scope.notifyCode,
						"fromStoreId" : $scope.fromStoreId,
						"toStoreId" : $scope.toStoreId,
						"notifyId" : $scope.notifyId
					};
					
					var saveReq = {
						"materialNotificationsTOs":[saveReqObj],
						"status" : 1,
						"projId" : $scope.fromProjId
					};
					console.log(saveReq);	
					//return;
					blockUI.start();
					MaterialRegisterService.saveAdditionalMaterialNotifications(saveReq).then(function(data) {
						blockUI.stop();
						$scope.closeThisDialog();
						GenericAlertService.alertMessage('Additional Time Details are Submitted successfully', "Info");
						/*
						var succMsg = GenericAlertService.alertMessageModal('Additional Time Details are Submitted successfully', "Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							deferred.resolve('Success');
						});
						*/
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Additional Time Details are failed to Submit', "Error");
					});
				}
				
				
				
					
			} ]   
		});
		return deferred.promise;
	}
	return service;
}]);
