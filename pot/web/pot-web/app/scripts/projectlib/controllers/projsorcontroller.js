'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("projsor", {
		url: '/projsor',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectlib/sor/projsor.html',
				controller: 'ProjSORController'
			}
		}
	})
}]).controller("ProjSORController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "ProjSORService", "ProjEmpClassService", "GenericAlertService", "EpsProjectSelectFactory", "TreeService","ProjSORFactory", function ($rootScope, $scope, $q, $state, ngDialog, blockUI, ProjSORService,
	ProjEmpClassService, GenericAlertService, EpsProjectSelectFactory, TreeService, ProjSORFactory) {
	$scope.SORData = [];
	$scope.sorData =[];
	var deleteSORData = [];
	$scope.flag = false;
	var selectedSORItemsData = [];
	var selectedSORIds = [];
	$scope.searchProject = {};
	$scope.activeFlag = 0;
	$scope.subCategoryRatesNotRequired = false;
	$scope.sorStatus = null;
	$scope.disableBtnsArry = {"SUBMIT_FOR_INTERNAL_APPROVAL":true,"SUBMIT_FOR_EXTERNAL_APPROVAL":true,"INTERNAL_APPROVAL":true,"EXTERNAL_APPROVAL":true,"RETURN_WITH_COMMENTS":true};
	$scope.isUserInternalApprover = false;
	$scope.isUserExternalApprover = false;
	$scope.isUserOriginator = false;
	$scope.isExternalApprovalRequired = "N";
	$scope.isAllowEdit = true;
	$scope.isAllowCreate = true;
	
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.SORData = [];
			$scope.searchProject = data.searchProject;
		},
			function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
	}, $scope.getSORDetails = function () {
		var sorReq = {
			"status": 1,
			"projId": $scope.searchProject.projId,
			"displayActiveItems" : 0,
			"loggedInUser": $rootScope.account.userId
		};
		if(!$scope.flag){
		  if (sorReq.projId == null || sorReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		  }	
		}
		ProjSORService.getSORDetails(sorReq).then(function (data) {
		//	$scope.sorStatus = null;
			console.log(data);
			$scope.sorData = data.projSORItemTOs;
			$scope.activeFlag = 1
			$scope.SORData = populateSorData(data.projSORItemTOs);
			if(data!=null && data.projSORItemTOs.length!=0)
			{
				selectedSORItemsData = [];
				selectedSORIds = [];
				generateSORIds($scope.SORData);
				console.log(selectedSORItemsData);
				console.log($scope.sorStatus);
				console.log($scope.isUserOriginator);
				console.log($scope.isUserInternalApprover);
				console.log($scope.isUserExternalApprover);
				$scope.isAllowEdit = false;
				if( $scope.sorStatus != null )
				{
					if( $scope.sorStatus.indexOf("SUBMITTED") > -1 )
					{
						/*$scope.disableBtnsArry.INTERNAL_APPROVAL = ( $scope.sorStatus=="SUBMITTED_FOR_INTERNAL_APPROVAL" && $scope.isUserInternalApprover ) ? false : true;
						$scope.disableBtnsArry.EXTERNAL_APPROVAL = ( $scope.sorStatus=="SUBMITTED_FOR_EXTERNAL_APPROVAL" && $scope.isUserExternalApprover ) ? false : true;
						$scope.isAllowEdit = ( ( $scope.sorStatus=="SUBMITTED_FOR_INTERNAL_APPROVAL" && $scope.isUserInternalApprover ) || ( $scope.sorStatus=="SUBMITTED_FOR_EXTERNAL_APPROVAL" && $scope.isUserExternalApprover ) ) ? true : false;
						$scope.disableBtnsArry.RETURN_WITH_COMMENTS = ( ( $scope.sorStatus=="SUBMITTED_FOR_INTERNAL_APPROVAL" && $scope.isUserInternalApprover ) || ( $scope.sorStatus=="SUBMITTED_FOR_EXTERNAL_APPROVAL" && $scope.isUserExternalApprover ) ) ? false : true;
						$scope.isAllowCreate = ( ( $scope.sorStatus=="SUBMITTED_FOR_INTERNAL_APPROVAL" && $scope.isUserInternalApprover ) || ( $scope.sorStatus=="SUBMITTED_FOR_EXTERNAL_APPROVAL" && $scope.isUserExternalApprover ) ) ? true : false;*/
						$scope.disableBtnsArry.INTERNAL_APPROVAL = ( $scope.sorStatus=="SUBMITTED_FOR_INTERNAL_APPROVAL" ) ? false : true;
						$scope.disableBtnsArry.EXTERNAL_APPROVAL = ( $scope.sorStatus=="SUBMITTED_FOR_EXTERNAL_APPROVAL" ) ? false : true;
						$scope.disableBtnsArry.RETURN_WITH_COMMENTS = false;
					}
					else if( $scope.sorStatus.indexOf("INTERNAL_APPROVED") > -1 )
					{
						//$scope.isAllowEdit = $scope.isUserInternalApprover ? true : false;
						//$scope.isAllowCreate = $scope.isUserInternalApprover ? true : false;
						if( $scope.isExternalApprovalRequired == "Y" )
						{
							console.log("if condition of isexternalapprovalrequired");
							$scope.disableBtnsArry.SUBMIT_FOR_EXTERNAL_APPROVAL = ( $scope.isUserInternalApprover ) ? false :true;
						}
					}
					else if( $scope.sorStatus.indexOf("RETURN") > -1 )
					{
						if( $scope.sorStatus == "RETURNED_FROM_INTERNAL_APPROVER" )
						{
							//$scope.isAllowEdit = ( $scope.isUserOriginator ) ? true : false;
							//$scope.isAllowCreate = ( $scope.isUserOriginator ) ? true : false;
							$scope.disableBtnsArry.SUBMIT_FOR_INTERNAL_APPROVAL = ( $scope.isUserOriginator ) ? false : true;		
						}
						else
						{
							//$scope.isAllowEdit = ( $scope.isUserInternalApprover ) ? true : false;
							//$scope.isAllowCreate = ( $scope.isUserInternalApprover ) ? true : false;
							$scope.disableBtnsArry.SUBMIT_FOR_EXTERNAL_APPROVAL = ( $scope.isUserInternalApprover ) ? false : true;
						}
					}
					else if( $scope.sorStatus.indexOf("DRAFT") > -1 ) {
						$scope.disableBtnsArry.SUBMIT_FOR_INTERNAL_APPROVAL = false;
					}
					else
					{
						console.log("else condition of sorstatus");
						if( $scope.sorStatus.indexOf("FINALIZED") > -1 )
						{
							$scope.isAllowEdit = false;
							$scope.isAllowCreate = false;
						}
					}				
					console.log($scope.disableBtnsArry);
					console.log($scope.isAllowEdit);
					console.log($scope.isAllowCreate);
				}
				else
				{
					console.log("else condition");
					$scope.isAllowCreate = false;
				}
			}
			//console.log($scope.SORData);
			if ($scope.SORData <= 0) {
				GenericAlertService.alertMessage("SOR Details  are not available for given search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	};
	
	$scope.viewActivityLog = function() {
		console.log("viewActivityLog function");
		if ($scope.searchProject.projId == null) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		console.log(selectedSORItemsData);
		ProjSORFactory.viewSORHistory(selectedSORIds,$scope.searchProject.projId);
		/*if( selectedSORItemsData.length != 0 )
		{
			ProjSORFactory.viewSORHistory(selectedSORItemsData);
		}
		else
		{
			GenericAlertService.alertMessage("No SOR Items to display the past records", 'Warning');
			return;
		}*/
	}

	function generateSORIds(sorDataIds)
	{
		for( var i=0;i<sorDataIds.length;i++ )
		{
			console.log(sorDataIds[i]);
			/*if( sorDataIds[i].childSORItemTOs.length == 0 )
			{
				console.log(sorDataIds[i].id);
				if( !selectedSORIds.includes(sorDataIds[i].id) )
				{
					console.log(sorDataIds[i]);
					console.log(sorDataIds[i].item);
					selectedSORIds.push(sorDataIds[i].id);
					if( $scope.sorStatus == null || $scope.sorStatus =="" )
					{
						$scope.sorStatus = sorDataIds[i].sorStatus;
					}					
					$scope.isUserInternalApprover = sorDataIds[i].isUserInternalApprover;
					//$scope.isUserExternalApprover = true;
					$scope.isUserExternalApprover = sorDataIds[i].isUserExternalApprover;
					$scope.isUserOriginator = sorDataIds[i].isUserOriginator;
					$scope.isExternalApprovalRequired = sorDataIds[i].isExternalApprovalRequired;
				}
				selectedSORItemsData.push(sorDataIds[i].id);				
			}*/
			if( !selectedSORIds.includes(sorDataIds[i].id) )
			{
				console.log(sorDataIds[i]);
				console.log(sorDataIds[i].item);
				selectedSORIds.push(sorDataIds[i].id);
				if( $scope.sorStatus == null || $scope.sorStatus =="" )
				{
					$scope.sorStatus = sorDataIds[i].sorStatus;
				}					
				$scope.isUserInternalApprover = sorDataIds[i].isUserInternalApprover;
				//$scope.isUserExternalApprover = true;
				$scope.isUserExternalApprover = sorDataIds[i].isUserExternalApprover;
				$scope.isUserOriginator = sorDataIds[i].isUserOriginator;
				$scope.isExternalApprovalRequired = sorDataIds[i].isExternalApprovalRequired;
			}
			/*else
			{
				generateSORIds(sorDataIds[i].childSORItemTOs);
			}*/
		}
		console.log($scope.sorStatus);
		console.log(selectedSORIds);
	}
	
	function populateSorData(data) {
		return TreeService.populateTreeData(data, 0, [], 'code', 'childSORItemTOs');
	}
    $rootScope.$on('sorRefresh', function(){
	   $scope.resetSORDatas();
    });
    $scope.resetSORDatas = function(){
		$scope.sorStatus = "";  
		 $scope.flag = true;
      $scope.getSORDetails();
      selectedSORIds = [];
      $scope.disableBtnsArry = {"SUBMIT_FOR_INTERNAL_APPROVAL":true,"SUBMIT_FOR_EXTERNAL_APPROVAL":true,"INTERNAL_APPROVAL":true,"EXTERNAL_APPROVAL":true,"RETURN_WITH_COMMENTS":true};
    }
	$scope.resetSORData = function () {
		$scope.activeFlag = 0;
		$scope.SORData = [];
		$scope.sorStatus = "";
		$scope.searchProject = {};
		$scope.disableBtnsArry = {"SUBMIT_FOR_INTERNAL_APPROVAL":true,"SUBMIT_FOR_EXTERNAL_APPROVAL":true,"INTERNAL_APPROVAL":true,"EXTERNAL_APPROVAL":true,"RETURN_WITH_COMMENTS":true};
	}
	$scope.rowSelect = function (rowData) {
		if (rowData.select) {
			deleteSORData.push(rowData.id);
		} else {
			deleteSORData.splice(deleteSORData.indexOf(rowData.id), 1);
		}
	}

	$scope.projSORSubmission = function(mode,approvalType) {
		console.log("returnWithComments function");
		ProjSORFactory.approvalUserPopup($scope.sorData,deleteSORData,selectedSORIds,$scope.searchProject.projId,mode,approvalType);
	}
	
	$scope.projSORApproval = function(mode,approvalType) {
		console.log("projSORApproval function");
		ProjSORFactory.approvalUserPopup($scope.sorData,deleteSORData,selectedSORIds,$scope.searchProject.projId,mode,approvalType);
	}
	$scope.returnWithComments = function(sorData) {
		console.log("returnWithComments function"+JSON.stringify(sorData)+"!!!!"+selectedSORIds);
		ProjSORFactory.approvalUserPopup($scope.sorData,sorData.id,selectedSORIds,$scope.searchProject.projId,null,"RETURN");
	//	ProjSORFactory.returnComments(sorData.id,selectedSORIds,$scope.searchProject.projIdsorData,$scope.sorStatus,sorData,null,"RETURN");
	//	ProjSORFactory.returnComments(sorData.id,selectedSORIds,$scope.searchProject.projId,null,"RETURN");	
	}
	/*$scope.return = function() {
		//var sorIdsData = [selectedSOR.id];
		$scope.approverUserId = $rootScope.account.userId
		let return_request = {
			sorIds : selectedSORIds, // [1151,1335],
			projId : $scope.searchProject.projId, //	sorId : 1335,
			sorStatus : "RETURN_FROM_INTERNAL_APPROVER"
		};
	//	/*if( $scope.sorStatus == "SUBMITTED_FOR_INTERNAL_APPROVAL" )
   //	{
	//		return_request.sorStatus = "RETURN_FROM_INTERNAL_APPROVER";
	//	}
	//	else
	//	{
	//		return_request.sorStatus = "RETURN_FROM_EXTERNAL_APPROVER";
	//	}
	//	//console.log($scope.returncomments);
		if( $scope.returncomments.length == 0 )
		{
			GenericAlertService.alertMessage("Please enter comments", "Error");
		}
		else
		{
			return_request.comments = $scope.returncomments;
		}
		ProjSORService.returnWithComments(return_request).then(function(data){
			console.log(data);
			GenericAlertService.alertMessage("Resubmitted successfully.....", 'Info');
			ngDialog.close(approvalPopup);
		})
		console.log(return_request);
	}*/
	
	var sorpopup;
	var addSORservice = {};
	$scope.addSORDetails = function (actionType, itemData, projId) {
		if (deleteSORData.length > 0 && actionType == 'Add') {
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		sorpopup = addSORservice.addProjSORDetails(actionType, itemData, projId, $scope.SORData, $scope.subCategoryRatesNotRequired);
		sorpopup.then(function (data) {
			$scope.SORData = populateSorData(data.projSORItemTOs);
		}, function (error) {
			GenericAlertService.alertMessage("SOR(s) is/are failed to save", "Error");
		});
	}
	addSORservice.addProjSORDetails = function (actionType, itemData, projId, sorList, subCategoryRateNotRequired) {
		var deferred = $q.defer();
		if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
			sorpopup = ngDialog.open({
				template: 'views/projectlib/sor/projsoreditpopup.html',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
				controller: ['$scope', function ($scope) {
					$scope.action = actionType;
					$scope.addSORData = [];
					$scope.measurements = [];
					$scope.projSorId = null;
					$scope.subCategoryRateNotRequired = subCategoryRateNotRequired;
					var sorData = [];
					$scope.projSorPopupItemClick = function (item, expand) {
						TreeService.dynamicTreeItemClick($scope.addSORData, item, expand);
					};
					if (itemData) {
						$scope.pcode = itemData.name;
						$scope.projSorId = itemData.id;
					}
					$scope.projSorifOnLoad = function () {
						var measurementReq = {
							"status": "1",
							"sorId": $scope.projSorId,
							"projId": $scope.searchProject.projId
						};
						console.log(measurementReq);
						ProjSORService.projSorifOnLoad(measurementReq).then(function (data) {
							$scope.measurements = data.measureUnitResp.measureUnitTOs;
							if ($scope.projSorId != null) {
								$scope.addSORData = data.projSORItemTOs;
							}
							sorData = data.projSORItemTOs;
							console.log("sordata",sorData);
							if (sorData && sorData.length)
								$scope.addSORData = TreeService.populateDynamicTreeData(sorData, 0, [], 'code', 'childSORItemTOs');
						});

					}, $scope.updateMeasureId = function (tab, data) {
						tab.measureId = data.id;
					}

					if (actionType === 'Add') {
						$scope.addSORData.push(angular.copy({
							"select": false,
							"projId": projId,
							"parentId": null,
							"item": false,
							"deleteFlag": false,
							"status": 1,
							"code": '',
							"name": '',
							"comments": '',
							"childSORItemTOs": []
						}));
					}

					$scope.addSORData = TreeService.populateDynamicTreeData($scope.addSORData, 0, [], 'code', 'childSORItemTOs');

					$scope.addSORSUBGroup = function (tabData, projId, itemIndex) {
						// check for input fileds validations
						let isValid = true;
						for (let i=0; i<$scope.addSORData.length; i++)
							isValid = isValid && validateEntry($scope.addSORData[i], $scope.addSORData, sorList);
						if (isValid) {
							const obj = angular.copy({
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"item": false,
								"deleteFlag": false,
								"status": 1,
								"code": '',
								"name": '',
								"comments": '',
								"childSORItemTOs": [],
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							});
							$scope.addSORData = TreeService.addItemToTree($scope.addSORData, tabData,
								obj, itemIndex, 'childSORItemTOs');
						}


					}, $scope.addSORItem = function (tabData, projId, index) {
						// check for input fileds validations
						let isValid = true;
						for (let i=0; i<$scope.addSORData.length; i++)
							isValid = isValid && validateEntry($scope.addSORData[i], $scope.addSORData, sorList);
						if (isValid) {
							const obj = angular.copy({
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"status": 1,
								"deleteFlag": false,
								"code": '',
								"name": '',
								"item": true,
								"measureUnitTO": '',
								"measureId": null,
								"manPowerHrs": 0,
								"labourRate": 0,
								"plantRate": 0,
								"materialRate": 0,
								"othersRate": 0,
								"quantity": 0,
								"total": 0,
								"amount": 0,
								"comments": '',
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							});
							$scope.addSORData = TreeService.addItemToTree($scope.addSORData, tabData,
								obj, index, 'childSORItemTOs');
						}
					};

					$scope.deleteSOR = function (index) {
						TreeService.deleteDynamicTreeItem($scope.addSORData, index);
					},
					$scope.convertToNumber = function (val) {
						return Number(val);
					},
					$scope.saveSORDetails = function () {
						let isValid = true;
						for (let i=0; i<$scope.addSORData.length; i++) {
							let result = validateEntry($scope.addSORData[i], $scope.addSORData, sorList);
							isValid = isValid && result;
						}
						if (isValid) {
							const data = TreeService.extractTreeDataForSaving($scope.addSORData, 'childSORItemTOs');
							var sorSaveReq = {
								"projSORItemTOs": data,
								"projId": $scope.searchProject.projId,
								"actionType" : actionType
							};
							blockUI.start();
							ProjSORService.saveSORDetails(sorSaveReq).then(function (data) {
								blockUI.stop();
								if (data.status != null && data.status !== undefined && data.status === 'Info') {
									var projSORTOs = data.projSORItemTOs;
									// var succMsg = GenericAlertService.alertMessageModal('Project SOR(s) is/are ' + data.message, data.status);
									var succMsg = GenericAlertService.alertMessageModal('Project SOR(s) saved successfully ',"Info");
									succMsg.then(function (data) {
									$scope.getSORDetails();
										$scope.closeThisDialog();
										var returnPopObj = {
											"projSORItemTOs": projSORTOs
										};
										deferred.resolve(returnPopObj);
									}, function (error) {
										blockUI.stop();
										GenericAlertService.alertMessage("SOR(s) is/are failed to save", "Error");
									});
								}
							});
						}/* else {
							GenericAlertService.alertMessage("Please Fill all mandatory fields data", "Info");
						}*/
					};
					function validateEntry(entry, currentSorList, projectSorList) {
						let isValid = true;
						delete entry.codeErrorMessage;
						delete entry.nameErrorMessage;
						delete entry.uomErrorMessage;
						delete entry.manpowerErrorMessage;
						delete entry.labourErrorMessage;
						delete entry.plantErrorMessage;
						delete entry.materialErrorMessage;
						delete entry.otherErrorMessage;
						delete entry.quantityErrorMessage;
						delete entry.commentsErrorMessage;
						delete entry.totalRateIfNoSubCategoryErrorMessage;
						if (entry.code == null || entry.code == "") {
							entry.codeErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if (entry.name == null || entry.name == "") {
							entry.nameErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if (entry.item) {
							if (entry.measureUnitTO == null || entry.measureUnitTO == '') {
								entry.uomErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
							if (entry.manPowerHrs == null) {
								entry.manpowerErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
							if (subCategoryRateNotRequired) {
								entry.labourRate = null;
								entry.plantRate = null;
								entry.materialRate = null;
								entry.othersRate = null;
								if (entry.totalRateIfNoSubCategory == null) {
									entry.totalRateIfNoSubCategoryErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
							} else {
								entry.totalRateIfNoSubCategory = null;
								if (entry.labourRate == null) {
									entry.labourErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
								if (entry.plantRate == null) {
									entry.plantErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
								if (entry.materialRate == null) {
									entry.materialErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
								if (entry.othersRate == null) {
									entry.otherErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
							}
							if (entry.quantity == null) {
								entry.quantityErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
							/*if (entry.comments == null || entry.comments == '') {
								entry.commentsErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}*/
						}
						if (entry.code != null) {
							if (entry.id) {
				    			if (projectSorList.find(e => e.code.toLowerCase().replace(/\s/g, "") == entry.code.toLowerCase().replace(/\s/g, "") && e.id != entry.id) != null) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    		} else {
				    			if (projectSorList.find(e => e.code.toLowerCase().replace(/\s/g, "") == entry.code.toLowerCase().replace(/\s/g, "")) != null) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    			let count = 0;
				    			for (let i=0; i<currentSorList.length; i++)
				    				if (currentSorList[i].code.toLowerCase() == entry.code.toLowerCase()) count++;
				    			if (count > 1) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    		}
						}
						return isValid;						
					};
				}]
			});
			return deferred.promise;
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	$scope.getSORDetails();
	}, $scope.deactivateSORDetails = function () {

		if (deleteSORData == undefined || deleteSORData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one SOR to Deactivate", "Warning");
			return;
		}
		var sorDeactivateReq = {
			"projSORItemIds": deleteSORData,
			"status": 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
			ProjSORService.deactivateSORDetails(sorDeactivateReq).then(function (data) {
				GenericAlertService.alertMessage("SOR(s) Deactivated successfully", "Info");
				deleteSORData = [];
				$scope.getSORDetails();
			}, function (error) {
				GenericAlertService.alertMessage("SOR(s) is/are failed to deactivate", "Error");
			});
		}, function (data) {
			deleteSORData = [];
			$scope.getSORDetails();
		})
	};

	$scope.sorItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childSORItemTOs');
	};

	$scope.show = function (comments) {
		ngDialog.open({
			template: 'views/projectlib/sor/viewcommentspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.comments = comments;
				$scope.displayCommentsDiv = true;
				$scope.displayReturnCommentsDiv = false;
			}]
		});
	};
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/projectshelp/projsorhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
}]);