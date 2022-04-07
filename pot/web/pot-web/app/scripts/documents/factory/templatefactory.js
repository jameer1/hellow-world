'use strict';

app.factory('TemplateFactory', ["ngDialog", "$q", "blockUI", "$state", "GenericAlertService", "DocumentService","UserService","$rootScope", function(ngDialog, $q,blockUI, $state, GenericAlertService, DocumentService,UserService,$rootScope) {
	var service = {};
	var approvalUserPopup;
	var projectId;
	
	service.approvalUserPopup = function(templateData,approvalMode,approvalType,isExternalApprovalDisplay,displayApproverComments) {
		var deferred = $q.defer();
		var approvalPopup = ngDialog.open({
			template: 'views/documents/approveruserspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom6',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log(templateData);
				console.log(approvalMode);
				console.log(approvalType);
				$scope.title = ( approvalMode == "INTERNAL" ) ? ( approvalType == "APPROVE" ) ? "Internal Approval" : "Internal Approver User List" : ( approvalType == "APPROVE" ) ? "External Approval" : "External Approver User List";
				$scope.btnTitle = ( approvalType == "APPROVE" ) ? "Approve" : "Submit for Approval";
				$scope.approverUserId = "";
				$scope.approverUserDisplayName = "";
				$scope.approvalMode = approvalMode;
				$scope.isexternalapprovalrequired = null;
				$scope.isDisplayExternalApprovalRequired = isExternalApprovalDisplay;
				$scope.displayApproverComments = displayApproverComments;
				$scope.displayApproverList = ( approvalType == "APPROVE" ) ? false : true;
				$scope.displayExternalApproverList = false;				
				
				$scope.toggleExternalApprovalRequired = function(event) {
					$scope.isexternalapprovalrequired = event.target.checked;
					/*if( event.target.checked )
					{
						$scope.displayApproverList = true;
					}
					else
					{
						$scope.displayApproverList = false;
					}*/
				}
				$scope.selectApprover = function() {
					console.log("approveruser function");
					var approverUserList = service.selectApproverUser();
					approverUserList.then(function(data){
						$scope.approverUserId = data.userId;
						$scope.approverUserDisplayName = data.dispName;
					});
				}
				$scope.submit = function() {
					let approval_status = "";
					if( $scope.approvalMode == "INTERNAL" )
					{
						approval_status = ( approvalType == "SUBMITTED" ) ? "SUBMIT_FOR_INTERNAL_APPROVAL" : "INTERNAL_APPROVAL";
					}
					else
					{
						approval_status = ( approvalType == "SUBMITTED" ) ? "SUBMIT_FOR_EXTERNAL_APPROVAL" : "EXTERNAL_APPROVAL";
					}
					console.log("submit function");
					let template_request = {
						templateId : templateData.templateId,
						templateCategoryId : templateData.templateCategoryId,
						status : approval_status,						
						categoryMstrId : templateData.categoryMstrId
					};
					let errors_cnt = 0;
					if( $scope.isexternalapprovalrequired != null )
					{
						template_request.isExternalApprovedRequired = ( $scope.isexternalapprovalrequired == true ) ? "Y" : "N";
					}
					else
					{
						template_request.isExternalApprovedRequired = "N";
					}
					
					if( approvalType == "SUBMITTED" )
					{
						template_request.createdBy = $scope.approverUserId;
						if( $scope.approverUserId == "" )
						{
							errors_cnt++;
						}
					}
					/*else
					{
						if( $scope.approvalMode == "INTERNAL" )
						{
							if( template_request.isExternalApprovedRequired == "Y" )
							{
								if( $scope.approverUserId == "" )
								{
									errors_cnt++;
								}
								else
								{
									template_request.createdBy = $scope.approverUserId;
								}
							}														
						}
					}*/				
					console.log(template_request);					
					
					if( errors_cnt > 0 )
					{
						GenericAlertService.alertMessage("Please select the Approver for approval", 'Warning');
					}
					else
					{
						var msg = "";
						if( approvalType == "SUBMITTED" )
						{
							msg = "Your request sent to the Approver";
						}
						else
						{
							msg = "You approved the template";
						}
						DocumentService.templateApproval(template_request).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage(msg, 'Info');
							$state.go('templates',{selected_category_id:data.templatesTOs[0].templateCategoryId,templateMode:"NEW_TEMPL"});
							ngDialog.close(approvalPopup);
							deferred.resolve(data);
						})
					}		
				}
			}]
		});
		return deferred.promise;
	},
	service.selectApproverUser = function() {	
		var deferred = $q.defer();
		var approverUserListPopup = ngDialog.open({
			template : 'views/common/approveruserslist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1"
				}
				$scope.users = [];
				UserService.getUsers($scope.userReq).then(function(data) {
					let usersList = data.users;
					for(let j=0;j<usersList.length;j++)
					{
						if( usersList[j].userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[j]);
						}
					}
					
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				$scope.selectedUser = function(approverTo) {
					deferred.resolve(approverTo);
					$scope.closeThisDialog();
				}			
			}]
		});
		return deferred.promise;
	}
	return service;

}]);