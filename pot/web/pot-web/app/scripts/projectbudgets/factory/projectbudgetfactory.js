'use strict';

app.factory('ProjectBudgetFactory', ["ngDialog", "$q", "$filter", "$rootScope", "blockUI", "ProjectBudgetService", "GenericAlertService","UserService", function(ngDialog, $q, $filter, $rootScope,blockUI, ProjectBudgetService, GenericAlertService,UserService) {
	var projBudgetService = {};
	
	projBudgetService.budgetApprovalPopup = function(approvalMode,manPowerIdsData,plantIdsData,materialIdsData,costIdsData,costItemIdsData) {
		var deferred = $q.defer();
		var submitApprovalPopup = ngDialog.open({
			template: 'views/projectbudgets/approvalpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("inside the controller of submitApprovalPopup");
				$scope.displayApproveBtn = ( approvalMode == "APPROVAL" ) ? true : false;			
				$scope.displaySubmitBtn = ( approvalMode == "SUBMIT" ) ? true : false;
				$scope.displayCommentsDiv = ( approvalMode == "APPROVAL" ) ? true : false;
				$scope.displayApproverList = ( approvalMode == "SUBMIT" ) ? true : false;
				$scope.approverUserId = "";
				$scope.title = approvalMode == "SUBMIT" ? "Submit for Approver" : approvalMode == "APPROVAL" ? "Approver Comments" :  "Return with Comments" ;
				
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
					console.log($scope.users);
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				console.log("costStatementIds",costIdsData);
				console.log("manpowerIds",manPowerIdsData);
				console.log("plantIds",plantIdsData);
				console.log("materialIds",materialIdsData);
				console.log("costItemIds",costItemIdsData);
			
				$scope.submit = function() {
					console.log("submit function");
					let submit_request = {
						manpowerIds : manPowerIdsData,						
						plantIds : plantIdsData,
						materialIds : materialIdsData,
						costStatementIds : costIdsData,
						itemStatus : "SUBMITTED_FOR_APPROVAL",
						costItemIds : costItemIdsData,
						approverUserId : $scope.approverUserId
					}
					if(manPowerIdsData == [])
					GenericAlertService.alertMessage("Save Manpower details", "Info");
					if(plantIdsData == [])
					GenericAlertService.alertMessage("Save Plant details", "Info");
					if(costIdsData == [])
					GenericAlertService.alertMessage("Save cost Statement details", "Info");
					if(materialIdsData == [])
					GenericAlertService.alertMessage("Save Materials details", "Info");
					console.log(submit_request);
					ProjectBudgetService.projBudgetApproval(submit_request).then(function (data) {
						console.log(data);
						GenericAlertService.alertMessage("Submitted for approval", "Info");
					}, function (error) {
						GenericAlertService.alertMessage("Error while submitting for approval the Project Budget", "Error");
					});
					ngDialog.close(submitApprovalPopup);
				},
				$scope.approve = function() {
					console.log("approve function");
					let approval_request = {
						manpowerIds : manPowerIdsData,						
						plantIds : plantIdsData,
						materialIds : materialIdsData,
						costStatementIds : costIdsData,
						itemStatus : "APPROVED",
						costItemIds : costItemIdsData,
						comments : $scope.comments
					}
					console.log(approval_request);
					ProjectBudgetService.projBudgetApproval(approval_request).then(function (data) {
						console.log(data);
						GenericAlertService.alertMessage("You have approved the Project Budgets", "Info");						
					}, function (error) {
						GenericAlertService.alertMessage("Error while approving the Project Budget", "Error");
					});
					ngDialog.close(submitApprovalPopup);
				}
			}]
		});
		return deferred.promise;
	},
	projBudgetService.budgetReturnPopup = function(budgetType,data,manPowerIdsData,plantIdsData,materialIdsData,costIdsData,costItemIdsData) {
		var deferred = $q.defer();
		var submitApprovalPopup = ngDialog.open({
			template: 'views/projectbudgets/approvalpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("inside the controller of submitApprovalPopup");
				$scope.displayApproveBtn = false;			
				$scope.displaySubmitBtn = true;
				$scope.displayCommentsDiv = true;
				$scope.displayApproverList = false;
				$scope.title = "Return with Comments" ;
				
				$scope.submit = function() {
					console.log("submit function");
					let submit_request = {
						manpowerIds : manPowerIdsData,						
						plantIds : plantIdsData,
						materialIds : materialIdsData,
						costStatementIds : costIdsData,
						itemStatus : "RETURNED_WITH_COMMENTS",
						costItemIds : costItemIdsData,
						comments : $scope.comments // "Something"
					}
					if( budgetType == "Plants" )
					{
						submit_request.plantId = data.id;
					}
					if( budgetType == "Manpower" )
					{
						submit_request.manpowerId = data.id;
					}
					if( budgetType == "Materials" )
					{
						submit_request.materialId = data.id;
					}
					if( budgetType == "CostStatement" )
					{
						submit_request.costStatementId = data.id;
						submit_request.costItemId = data.costId;
					}
					
					console.log(submit_request);
					ProjectBudgetService.projBudgetReturn(submit_request).then(function (data) {				
						GenericAlertService.alertMessage("Selected budget item(s) returned successfully....", "Info");
						console.log(data);
					}, function (error) {
						GenericAlertService.alertMessage("Error while submitting for approval the Project Budget", "Error");
					});
					ngDialog.close(submitApprovalPopup);
				}
			}]
		});
		return deferred.promise;
	}
	return projBudgetService;
}]);