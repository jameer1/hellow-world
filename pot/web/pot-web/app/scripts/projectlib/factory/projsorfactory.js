'use strict';

app.factory('ProjSORFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjSORService", "GenericAlertService", "TreeService", "UserService", function (ngDialog, $q, $filter, $timeout, $rootScope, ProjSORService,
	GenericAlertService, TreeService, UserService) {
	var projSorPopup;
	var service = {};
	service.sorPopupDetails = function (projId) {
		var deferred = $q.defer();
		projSorPopup = ngDialog.open({
			template: 'views/projectlib/sow/projsorviewpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.SORData = [];
				$scope.getSORDetails = function () {
					var sorReq = {
						"projId": projId,
						"status": 1
					}
					ProjSORService.getSORDetails(sorReq).then(function (data) {
						$scope.SORData = TreeService.populateTreeData(data.projSORItemTOs, 0, [], 'code',
							'childSORItemTOs');
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Schedule Of Rates Details", 'Error');
					});

				};

				$scope.selectSORDetails = function (sorItemData) {

					var returnPopObj = {
						"selectedSORItem": sorItemData
					};

					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				};

				$scope.sorFactoryItemClick = function (tab, collapse) {
					TreeService.treeItemClick(tab, collapse, 'childSORItemTOs');
				};
			}]
		});
		return deferred.promise;
	},
	service.approvalUserPopup = function(SORData,selectedSORItemId,selectedSORData,projectId,approvalMode,approvalType) {
		var deferred = $q.defer();
		var approvalPopup = ngDialog.open({
			template: 'views/projectlib/sor/sorsubmit.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log(approvalMode);
				console.log(approvalType);
				//		$scope.title = ( approvalMode == "INTERNAL" ) ? "Internal Approver User List" : "External Approver User List";
				$scope.title = ( approvalMode == "INTERNAL" ) ? "Submit for Internal Approval" : ( approvalMode == "EXTERNAL" ) ? "Submit for External Approval " : "Returned with Comments"
				$scope.approverUserId = "";
				$scope.approvalMode = approvalMode;
				$scope.displayApproverComments = ( approvalMode != null ) ? ( approvalType == "APPROVAL" ) ? true : false : true;
				$scope.displayApproverList = ( approvalMode != null ) ? ( approvalType == "SUBMIT" ) ? true : false : false;
				$scope.displayApproveBtn = ( approvalMode != null ) ? ( approvalType == "APPROVAL" ) ? true : false : false;
				$scope.displaySubmitBtn = ( approvalMode != null ) ? ( approvalType == "SUBMIT" ) ? true : false : true;
				$scope.displayExternalApprovalRequired = ( approvalMode != null ) ? ( approvalMode == "INTERNAL" ) ? ( approvalType=='APPROVAL' ) ? true : false : false : false;
				$scope.isExternalApprovalRequired = ( approvalMode != null )? ( approvalMode == "INTERNAL" && approvalType == "APPROVAL" ) ? true : false : false;
				
				/*$scope.displayApproverComments = ( approvalType == "APPROVAL" ) ? true : false;
				$scope.displayApproverList = ( approvalType == "SUBMIT" ) ? true : false;
				$scope.displayApproveBtn = ( approvalType == "APPROVAL" ) ? true : false;
				$scope.displaySubmitBtn = ( approvalType == "SUBMIT" ) ? true : false;
				$scope.displayExternalApprovalRequired = ( approvalMode == "INTERNAL" ) ? ( approvalType=='APPROVAL' ) ? true : false : false;
				$scope.isExternalApprovalRequired = ( approvalMode == "INTERNAL" && approvalType == "APPROVAL" ) ? true : false;*/
				
				if( $scope.isExternalApprovalRequired )
				{
					$scope.displayApproverList = true;
				}
				var viewSORIds = [];
				let activity_request = {
					sorIds : selectedSORData
				};
				
				console.log(activity_request);
				ProjSORService.viewSORActivityLog(activity_request).then(function(data){
				//	$scope.activityData = data.projSORActivityLogTOs;
					for(var i=0;i<data.projSORActivityLogTOs.length;i++){
						viewSORIds.push(data.projSORActivityLogTOs[i].sorId);
					}
					console.log(data);
				})
				console.log(viewSORIds);
				/*$scope.approveSelection = true;
				$scope.returnSelection = false;
				$scope.displayApproveSelection = true;*/
				$scope.userReq = {
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
				});
				
				$scope.toggleExternalApprovalRequired = function(event){
					$scope.isExternalApprovalRequired = event.target.checked;
					if(event.target.checked){
						$scope.displayApproverList = true;
					}else{
						$scope.displayApproverList = false;
					}
				}
				//console.log($scope.approvalSelect);
				$scope.approve = function() {
					let approval_status = "";
					let approval_request = {
						sorIds : selectedSORData,
						projId : projectId,
						loggedInUser : $rootScope.account.userId,
						projSORItemTOs : SORData,
						viewSORIds : viewSORIds 				
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
						//	approval_request.comments = $scope.approverComments;
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
					approval_request.sorStatus = approval_status;
					let errors_cnt = 0;
					console.log(approval_request);
					ProjSORService.projSorApproval(approval_request).then(function(data){
						console.log(data);
						GenericAlertService.alertMessage("Submitted successfully for the approval", 'Info');
						ngDialog.close(approvalPopup);
						$rootScope.$broadcast('sorRefresh');	
					})
					console.log("submitForApproval function");
				}
				$scope.submit = function() {
				$scope.approverUserId = $rootScope.account.userId								
				
				
				if($scope.approverUserId == ''){
				
					GenericAlertService.alertMessage("please select approver from list", 'Info');
					return;
				}
				
					let approval_request = {
						sorIds : selectedSORData,
						projId : projectId,
						loggedInUser : $rootScope.account.userId,
						projSORItemTOs : SORData,
						viewSORIds : viewSORIds 	
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
					console.log(approval_request);
					if( $scope.approvalMode == "INTERNAL" || $scope.approvalMode == "EXTERNAL" )
					{
						approval_request.sorStatus = ( $scope.approvalMode == "INTERNAL" ) ? "SUBMIT_FOR_INTERNAL_APPROVAL" : "SUBMIT_FOR_EXTERNAL_APPROVAL";
						ProjSORService.projSorApproval(approval_request).then(function(data){
							console.log(data);
							if($scope.approvalMode == "INTERNAL"){
								GenericAlertService.alertMessage("Internal Approval completed successfully", 'Info');
							}else{
							GenericAlertService.alertMessage("Submitted successfully for the approval", 'Info');
							}
							ngDialog.close(approvalPopup);
							$rootScope.$broadcast('sorRefresh');
						})
					}else{
						approval_request.sorId = selectedSORItemId;//selectedSorItemId;
						approval_request.sorStatus = "RETURN_FROM_INTERNAL_APPROVER";
						console.log("else condition");
						console.log(approval_request.sorId)
						ProjSORService.returnWithComments(approval_request).then(function (data){
							console.log(data);
							GenericAlertService.alertMessage("Returned with comments successfully....",'Info');
						//	ngDialog.close(approvalUserPopup)
							ngDialog.close(approvalPopup)
							$rootScope.$broadcast('sorRefresh');
						})
					}
					console.log(approval_request)										
				}				
			}]
		});
		return deferred.promise;
	},
	service.viewSORHistory = function(selectedSORItemsIds,projectId) {
		var deferred = $q.defer();
		var viewRecordsPopup = ngDialog.open({
			template: 'views/projectlib/soe/viewrecordspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("inside the controller of viewRecordsPopup");
				console.log(selectedSORItemsIds);
		//		$scope.activityData = [];
		//		$scope.activityDataCount = 0;
				let activity_request = {
					sorIds : selectedSORItemsIds
				};
				//console.log(activity_request);
				ProjSORService.viewSORActivityLog(activity_request).then(function(data){
					$scope.activityData = data.projSORActivityLogTOs;
		//			if( data.projSORActivityLogTOs.length != 0 )
		//			{
		//				$scope.activityDataCount = $scope.activityData.length;
		//			}
					console.log(data);
				})
				$scope.viewDetail = function(description) {
					console.log("viewDetail function");
					var viewSORDetailsPopup = ngDialog.open({
						template: 'views/projectlib/sor/projsortrackrecords.html',
			            className: 'ngdialog-theme-plain ng-dialogueCustom4',
			            closeByDocument: false,
			            scope: $rootScope,
			            showClose: false,
                        controller: ['$scope','$rootScope',function($scope,$rootScope){
	                   //    console.log("inside the viewDetail function");
                            $scope.SORData = [];
                       //    console.log(status);
                           var sorReq = {
			                              "status" : 1,
			                              "projId" : projectId,
			                              "loggedInUser" : $rootScope.account.userId,
                                          "sorStatus" : description
		                   };
		                    console.log(sorReq);
							ProjSORService.getProjSORTrackDetails(sorReq).then(function(data) {
								console.log(data);
								$scope.sorDataItems = data.projSORTrackTOs;
								$scope.SORData = populateSorData(data.projSORTrackTOs);
							//	$scope.SORData = populateSoeData(data.projSORTrackTOs, 0, []);
							})	
							function populateSorData(data) {
								return TreeService.populateTreeData(data, 0, [], 'code', 'childSORItemTOs');
							}
							$scope.sorItemClick = function(item, collapse) {
								TreeService.treeItemClick(item, collapse, 'childSORItemTOs');
							};
                        }]
					})
				}
			}]
		});
		return deferred.promise;
	}
	/*service.returnComments = function(selectedSOR,,sorStatus) {
		var deferred = $q.defer();
		var viewReturnPopup = ngDialog.open({
			template: 'views/projectlib/sor/viewcommentspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("inside the controller of returnComments");
				console.log(selectedSOR);
				$scope.displayReturnCommentsDiv = true;
				$scope.displayCommentsDiv = false;
				$scope.returncomments = "";
				console.log(sorStatus);
				$scope.return = function() {
					console.log(return_request);
					console.log($scope.returncomments);
					//console.log(event.target.id);
					if( $scope.returncomments.length == 0 )
					{
						GenericAlertService.alertMessage("Submitted successfully for the approval", 'Info');
					}
					else
					{
						return_request.comments = $scope.returncomments;
						console.log(return_request);						
						ProjSORService.returnWithComments(approval_request).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage("Returned with comments successfully.....", 'Info');
							ngDialog.close(approvalPopup);
						})
					}
				}
				/*console.log(selectedSORItemsIds);
				$scope.activityData = [];
				$scope.activityDataCount = 0;
				let activity_request = {
					sorIds : selectedSORItemsIds
				};
				console.log(activity_request);
				ProjSORService.viewSORActivityLog(activity_request).then(function(data){
					$scope.activityData = data.projSORActivityLogTOs;
					if( data.projSORActivityLogTOs.length != 0 )
					{
						$scope.activityDataCount = $scope.activityData.length;
					}
					console.log(data);
				})
				$scope.viewDetail = function() {
					console.log("viewDetail function");
				}  -> end of comment here
			}]
		});
		return deferred.promise;
	} */
	return service; 
}]);