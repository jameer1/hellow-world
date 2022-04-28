'use strict';

app.factory('ProjSOEFactory', ["ngDialog", "$q", "blockUI", "GenericAlertService", "UserService","$rootScope","ProjSOEService","TreeService","PotCommonService", function(ngDialog, $q,blockUI, GenericAlertService, UserService,$rootScope,ProjSOEService,TreeService,PotCommonService) {
	var service = {};
	var approvalUserPopup;
	var projectId;
	
	service.approvalUserPopup = function(selectedSoeItemId,soeItemIdsData,projectId,approvalMode,approvalType,soeData) {
		var deferred = $q.defer();
		var approvalPopup = ngDialog.open({
			template: 'views/projectlib/soe/soesubmit.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log(approvalMode);
				console.log(approvalType);
			//	$scope.title = ( approvalMode == "INTERNAL" ) ? ( approvalMode == "EXTERNAL" ) ? "Internal Approver User List" : "Returned with Comments" : "External Approver User List" ;
				$scope.title = ( approvalMode == "INTERNAL" ) ? "Submit for Internal Approval" : ( approvalMode == "EXTERNAL" ) ? "Submit for External Approval " : "Returned with Comments"
				$scope.approverUserId = "";
				$scope.approvalMode = approvalMode;
				$scope.displayApproverComments = ( approvalMode != null ) ? ( approvalType == "APPROVAL" ) ? true : false : true;
				$scope.displayApproverList = ( approvalMode != null ) ? ( approvalType == "APPROVAL" ) ? false : true : false;
				$scope.displayApproveBtn = ( approvalMode != null ) ? ( approvalType == "APPROVAL" ) ? true : false : false;
				$scope.displaySubmitBtn = ( approvalMode != null ) ? ( approvalType == "SUBMIT" ) ? true : false : true;
				$scope.displayExternalApprovalRequired = ( approvalMode != null ) ? ( approvalMode == "INTERNAL" ) ? ( approvalType=='APPROVAL' ) ? true : false : false : false;
				$scope.isExternalApprovalRequired = ( approvalMode != null )? ( approvalMode == "INTERNAL" && approvalType == "APPROVAL" ) ? true : false : false;
				if( $scope.isExternalApprovalRequired )
				{
					$scope.displayApproverList = true;
				}
				var soeParentIds =[];
				for(var i =0;i<soeData.length;i++){
					soeParentIds.push(soeData[i].id);
				}
				
				/*$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1",
					"projId" : projectId
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
				});*/
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
						if( usersList[j].id != $rootScope.account.userId )
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
				
				$scope.toggleExternalApprovalRequired = function(event) {
					$scope.isexternalapprovalrequired = event.target.checked;
					if( event.target.checked )
					{
						$scope.displayApproverList = true;
					}
					else
					{
						$scope.displayApproverList = false;
					}
				}
				
				let activity_request = {
					soeIds : soeItemIdsData
				};
				var soeActivityIds = [];
				ProjSOEService.viewActivityRecords(activity_request).then(function(data){
					var soeTrackData=data.projSOEActivityLogTOs;
					 $scope.soeFalg = false;		
					for (var i = 0; i < soeTrackData.length; i++) {
						soeActivityIds.push(soeTrackData[i].soeId)

						}					 	
				});
				$scope.approve = function() {
					let approval_status = "";
					let approval_request = {
						soeIds : soeItemIdsData,
						projId : projectId,
						loggedInUser : $rootScope.account.userId,
						soeParentIds : soeParentIds,
						viewSoeIds : soeActivityIds
					};
					if( $scope.approvalMode == "INTERNAL" )
					{
						approval_status = ( approvalType == "SUBMIT" ) ? "SUBMIT_FOR_INTERNAL_APPROVAL" : "INTERNAL_APPROVAL";
						if( approvalType == "SUBMIT" )
						{
							approval_request.internalApprovalUserId = $scope.approverUserId;
						}
						else
						{
							approval_request.externalApprovalUserId = $scope.approverUserId;
						}
					}
					else if( $scope.approvalMode == "EXTERNAL" )
					{
						approval_status = ( approvalType == "SUBMIT" ) ? "SUBMIT_FOR_EXTERNAL_APPROVAL" : "EXTERNAL_APPROVAL";
						if( approvalType == "SUBMIT" )
						{
							approval_request.externalApprovalUserId = $scope.approverUserId;
						}
					}					
					approval_request.isExternalApprovalRequired = ( $scope.isExternalApprovalRequired == true ) ? "Y" : "N";
					approval_request.comments = $scope.approverComments;
					approval_request.soeStatus = approval_status;
					let errors_cnt = 0;
					console.log(approval_request);
					ProjSOEService.projSoeApproval(approval_request).then(function(data){
						console.log(data);
						GenericAlertService.alertMessage("Submitted successfully for the approval", 'Info');
						ngDialog.close(approvalPopup);
						$scope.trackDetails();
						$rootScope.$broadcast('soeRefresh');			
					})
					console.log("submitForApproval function");
				}
			
		
				$scope.trackDetails = function(){
					var soeDtls = [];
					var soeReq = {
						"status": 1,
						"projId": projectId,
						"loggedInUser": $rootScope.account.userId
					};
					ProjSOEService.getSOEDetails(soeReq).then(function(data) {
						console.log(data.projSOEItemTOs);
						console.log(soeData);
						soeDtls = data.projSOEItemTOs;
						var soeTrackSaveReq = {
								"projSOETrackTOs": soeDtls,
								"projId": projectId,
								"actionType": 'Add',
								"loggedInUser": $rootScope.account.userId
						}
						if(soeDtls[0].soeItemStatus == 'SUBMITTED_FOR_INTERNAL_APPROVAL' && soeData[0].soeItemStatus == 'RETURNED_FROM_INTERNAL_APPROVER'){
							soeTrackSaveReq.resubmitStatus = 'Resubmitted For Internal Approval';
						}
						if(soeDtls[0].soeItemStatus == 'SUBMITTED_FOR_EXTERNAL_APPROVAL' && soeData[0].soeItemStatus == 'RETURNED_FROM_EXTERNAL_APPROVER'){
							soeTrackSaveReq.resubmitStatus = 'Resubmitted For External Approval';
						}
						console.log(soeTrackSaveReq);			
						ProjSOEService.saveSOETrackDetails(soeTrackSaveReq).then(function(data) {
								console.log(data);
							});	
					})
							
				}
				
				$scope.submit = function() {
				$scope.approverUserId = $scope.approverUserId;
				console.log($scope.approverUserId)
			//	console.log($scope.approvalMode+"...."+$scope.approvalType)
					let approval_request = {
						soeIds : soeItemIdsData,
						projId : projectId,
						loggedInUser : $rootScope.account.userId,
						soeParentIds : soeParentIds,
						viewSoeIds : soeActivityIds
					};
					if( $scope.approvalMode == "INTERNAL" )
					{
						if( approvalType == "SUBMIT" )
						{
							approval_request.internalApprovalUserId = $scope.approverUserId;
						}
					}
					else if( $scope.approvalMode == "EXTERNAL" )
					{					
						if( approvalType == "SUBMIT" )
						{
							approval_request.externalApprovalUserId = $scope.approverUserId;
						}
					}
					else
					{
						approval_request.comments = $scope.approverComments;
					}
					
					console.log("submit function");
					if( $scope.approvalMode == "INTERNAL" || $scope.approvalMode == "EXTERNAL" )
					{
						console.log("if condition");
						approval_request.soeStatus = ( $scope.approvalMode == "INTERNAL" ) ? "SUBMIT_FOR_INTERNAL_APPROVAL" : "SUBMIT_FOR_EXTERNAL_APPROVAL";
						if(soeData[0].soeItemStatus == 'RETURNED_FROM_INTERNAL_APPROVER' && approval_request.soeStatus == 'SUBMIT_FOR_INTERNAL_APPROVAL'){
						 approval_request.resubmitStatus = 'Resubmitted For Internal Approval';
					    }
	                    if(soeData[0].soeItemStatus == 'RETURNED_FROM_EXTERNAL_APPROVER' && approval_request.soeStatus == 'SUBMIT_FOR_EXTERNAL_APPROVAL'){
		                 approval_request.resubmitStatus = 'Resubmitted For External Approval';
	                    }
						ProjSOEService.projSoeApproval(approval_request).then(function(data){
							console.log(data);
							if( $scope.approvalMode == "INTERNAL"){
								$scope.trackDetails();
								GenericAlertService.alertMessage("Internal Approval completed  successfully", 'Info');
							} else{
								$scope.trackDetails();
								GenericAlertService.alertMessage("Submitted successfully for the approval", 'Info');
							}
							ngDialog.close(approvalPopup);
							$rootScope.$broadcast('soeRefresh');
						})
					}
					else
					{
						approval_request.soeId = selectedSoeItemId;
						approval_request.soeStatus = "RETURN_FROM_INTERNAL_APPROVER";
						console.log("else condition");						
						ProjSOEService.returnWithComments(approval_request).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage("Returned with comments successfully....", 'Info');
							ngDialog.close(approvalPopup);
							$rootScope.$broadcast('soeRefresh');
                         if(approval_request.soeStatus == "RETURN_FROM_INTERNAL_APPROVER")
						   $scope.trackDetails();		
						})
					}									
					console.log(approval_request);
				}				
			}]
		});
		return deferred.promise;
	},
	
	service.viewRecordsPopup = function(deleteSOEData,projectId) {
		var deferred = $q.defer();
		var viewRecordsPopup = ngDialog.open({
			template: 'views/projectlib/soe/viewrecordspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("inside the controller of viewRecordsPopup");
				console.log(deleteSOEData);
				let activity_request = {
					soeIds : deleteSOEData
				};
				console.log(activity_request);
				ProjSOEService.viewActivityRecords(activity_request).then(function(data){
					$scope.activityData = data.projSOEActivityLogTOs;
					console.log(data);
				})
				$scope.viewDetail = function(status) {
					var viewRecordsPopup = ngDialog.open({
						template: 'views/projectlib/soe/viewtrackpopup.html',
			            className: 'ngdialog-theme-plain ng-dialogueCustom4',
			            closeByDocument: false,
			            scope: $rootScope,
			            showClose: false,
                        controller: ['$scope','$rootScope',function($scope,$rootScope){
	                       console.log("inside the viewDetail function");
                           $scope.SOEData = [];
                           console.log(status);
                           var soeReq = {
			                              "status" : 1,
			                              "projId" : projectId,
			                              "loggedInUser" : $rootScope.account.userId,
                                          "soeStatus" : status
		                   };
		                    console.log(soeReq);
							ProjSOEService.getProjSOETrackDetails(soeReq).then(function(data) {
								console.log(data);
								$scope.soeDataItems = data.projSOETrackTOs;
								$scope.SOEData = populateSoeData(data.projSOETrackTOs, 0, []);
							})	
							function populateSoeData(data, level, soeTOs) {
								return TreeService.populateTreeData(data, level, soeTOs, 'code', 'childSOEItemTOs');
							}
							$scope.soeItemClick = function(item, expand) {
								TreeService.treeItemClick(item, expand, 'childSOEItemTOs');
							};
                        }]
					})
					
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);