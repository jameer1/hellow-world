'use strict';
app.factory('RequestForSoeAdditionalTimeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "UserService", "ProjSOEService","PotCommonService",function(ngDialog, $q, $filter, $timeout, 
	$rootScope, GenericAlertService, UserService,ProjSOEService,PotCommonService) {
	var dateWisePopUp;
	var service = {};
    var projectId;
	service.getAdditionalTimeDetails = function(projectId,projectName,soeItemIdsData) {
		console.log(projectId);	
		console.log(soeItemIdsData);	
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/projectlib/soe/soeadditionaltime.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose : false,
			controller : [ '$scope', function($scope) {		
			$scope.projectId = projectId;
			$scope.projectName = projectName;
			console.log($scope.projectName);
			console.log($scope.projectId);		
				 $scope.userReq = {
					"status" : "1",
					"projId" : projectId,
					"permission" : "PRJ_PRJLIB_SCHEDOFESTQTY_APPROVE"
				   }
				var req = {
								"moduleCode" : "INTERNALREQAPPOVAL",
								"actionCode" : "APPROVE",
								"permission" : "PROCURMT_INTRNLSTAGE1APRVL_APPROVE",
								"projId":projectId,
								"status":1
							};
				console.log($scope.userReq);
				$scope.users = [];
				PotCommonService.getProjUsersOnly(req).then(function(data) {
				console.log(data);
					let usersList = data.labelKeyTOs;
					console.log(usersList);
					for(let j=0;j<usersList.length;j++)
					{
						if( usersList[j].userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[j]);
						}
					}
					console.log($scope.users);
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				
				$scope.submitSoeNotifications = function() {
				$scope.approverUserId = $scope.approverUserId;
				    console.log($scope.approverUserId);
					var saveReqObj={
					    "toUserId":$scope.approverUserId,
						"status" : 1,
						"notificationStatus":'Additional Time For Submit',
						"comments":$scope.comments,
						"type": soeItemIdsData[0].soeStatus,
						"notifyRefId":soeItemIdsData[0].id,
						"projId" :  projectId
					};
					
					var saveReq = {
						"soeNotificationsTOs":[saveReqObj],
						"status" : 1
					};
					console.log(saveReq);
					
					ProjSOEService.saveSoeNotifications(saveReq).then(function(data) {
					console.log(data);
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
