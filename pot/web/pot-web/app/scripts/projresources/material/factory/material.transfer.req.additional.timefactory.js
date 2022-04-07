'use strict';
app.factory('RequestForMaterialAdditionalTimeFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ModuleUserFactory", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,ModuleUserFactory, MaterialRegisterService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getAdditionalTimeDetails = function() {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/common/requestforadditionaltime.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			showClose : false,
			controller : [ '$scope', function($scope) {
					/*$scope.projId = projId;
					$scope.projName = projName;*/
				$scope.userLabelKeyTO={
					"id":null,
					"code":null,
					"name":null
			};
		$scope.submitNotifications = function() {
			var saveReqObj={
				"projId":203 ,
				"apprUserId":$scope.userLabelKeyTO.id,
				"notifyRefId":1,
				"status" : 1,
				"type":'Additional Time For Submit',
				"reqComments":$scope.comments,
				"notificationStatus":'Pending',
				"reqId":1
			};
			var saveReq = {
				"materialNotificationsTOs":[saveReqObj],
				"status" : 1,
				"projId" : 203
			};
			MaterialRegisterService.saveAdditionalMaterialNotifications(saveReq).then(function(data) {
				$scope.closeThisDialog();
				GenericAlertService.alertMessage('Additional Time Details are Submitted successfully', "Info");
			}, function(error) {
				GenericAlertService.alertMessage('Additional Time Details are  failed to Submit', "Error");
			});
		},$scope.getModuleUserDetails = function(userLabelKeyTO) {
			var getReq = {
					"moduleCode" : "NTFYMATRLTRANSFER",
					"actionCode" : "APPROVE",
					"projId" : 203
				};
				var selectedUser = ModuleUserFactory.getUsersByModulePermission(getReq);
				selectedUser.then(function(data) {
					userLabelKeyTO.id = data.userLabelKeyTO.id;
					userLabelKeyTO.name = data.userLabelKeyTO.name;
				});
			}
					
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
