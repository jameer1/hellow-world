'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("projsoe", {
		url : '/projsoe',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/soe/projsoe.html',
				controller : 'ProjSOEController'
			}
		}
	})
}]).controller("ProjSOEController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "ProjSOEService", "ProjEmpClassService", "ProjectSettingsService","GenericAlertService", "EpsProjectSelectFactory", "TreeService", "ProjSOEFactory", "RequestForSoeAdditionalTimeFactory",function($rootScope, $scope, $q, $state, ngDialog,blockUI, ProjSOEService,
	 ProjEmpClassService, ProjectSettingsService,GenericAlertService, EpsProjectSelectFactory, TreeService, ProjSOEFactory, RequestForSoeAdditionalTimeFactory) {
	var deleteSOEData = [];
	var soeItemIdsData = [];
	$scope.SOEData = [];
	$scope.soeDataItems = null;
	$scope.soeDetails = [];
	$scope.soeItems = [];
	$scope.projSoeDetails=[];
	$scope.schofEstimatesApprTO =[];
	$scope.searchProject = {};
	var deferred = $q.defer();
	$scope.activeFlag=0;
	$scope.flag = false;
	$scope.submitTime = false;
	$scope.submitReTime = false;
//	$scope.submitTime = true;
	$scope.displayEdit = false;
	$scope.retimeFlaggg = true;
	$scope.soeStatus = "";
	$scope.disableBtns = {"disableInternalSubmitBtn":true,"disableInternalApproveBtn":true,"disableExternalSubmitBtn":true,"disableExternalApproveBtn":true,"displayReturnBtn":true,"displayAddtlTime":false};
	$scope.disableCreateBtn = true;
	$scope.disableEditBtn = true;
	$scope.returnButton = true;
	$scope.disableEditValues = true;
	$scope.isUserOriginator = false;
	$scope.isUserInternalApprover = false;
	$scope.isUserExternalApprover = false;
//	$scope.retimeFlag = true;
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.SOEData = [];
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getSOEDetails = function() {
		console.log($rootScope.account);
		/*var estireq = {
				              "status": 1,
				              "projId": $scope.searchProject.projId
			            };
			            console.log(estireq)
			              ProjectSettingsService.projScheduleEstimatesOnLoad(estireq).then(function (data){
				           $scope.schofEstimatesApprTO = data.schofEstimatesApprTOs;
                           $scope.projSoeDetails.push($scope.schofEstimatesApprTO);
			            });	*/
               
		var soeReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId,
			"loggedInUser" : $rootScope.account.userId
		};
		console.log(soeReq);
	     if(!$scope.flag){
		 if (soeReq.projId == null || soeReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		 }	
		}
		ProjSOEService.getSOEDetails(soeReq).then(function(data) {
			console.log(data);
			$scope.soeDataItems = data.projSOEItemTOs;
			$scope.soeDetails.push($scope.soeDataItems[0]);
			$scope.activeFlag=1
			$scope.SOEData = populateSoeData(data.projSOEItemTOs, 0, []);
			retrieveSOEStatus($scope.SOEData);
			soeItemIdsData = [];
			ProjectSettingsService.projScheduleEstimatesOnLoad(soeReq).then(function (data){
				$scope.schofEstimatesApprTO = data.schofEstimatesApprTOs;
                   
            var date = parseInt($scope.soeDataItems[0].addlDate);
			var dateString = new Date(date);				
			const soeAddlTime =  $scope.schofEstimatesApprTO[2];;
			if (soeAddlTime.cutOffDays) {
			 dateString.setDate(dateString.getDate() + 1 + soeAddlTime.cutOffDays);
			}
			if (soeAddlTime.cutOffHours) {
		     dateString.setHours(dateString.getHours() + soeAddlTime.cutOffHours);
			}
			if (soeAddlTime.cutOffMinutes) {
			dateString.setMinutes(dateString.getMinutes() + soeAddlTime.cutOffMinutes);
			}
			const addlTime = new Date();
			$scope.addlTimeFlag = (dateString >= addlTime);



            const soeSubmit =  $scope.schofEstimatesApprTO[2];
            console.log(soeSubmit);
			var dates = new Date($scope.soeDetails[0].submittedDate);
			if (soeSubmit.cutOffDays) {
			 dates.setDate(dates.getDate() + 1 + soeSubmit.cutOffDays);
			}
			if (soeSubmit.cutOffHours) {
		     dates.setHours(dates.getHours() + soeSubmit.cutOffHours);
			}
			if (soeSubmit.cutOffMinutes) {
			dates.setMinutes(dates.getMinutes() + soeSubmit.cutOffMinutes);
			}
			
			const today = new Date();
			$scope.timeFlag = (dates.getTime() <= today);
			console.log($scope.timeFlag);
			if($scope.timeFlag){
				$scope.disableBtns.disableInternalApproveBtn = true;
				$scope.disableBtns.displayReturnBtn = true;
				$scope.disableBtns.displayAddtlTime = true;
			}
			if($scope.timeFlag && $scope.addlTimeFlag){
				console.log($scope.addlTimeFlag);
				$scope.disableBtns.displayAddtlTime = false;
				$scope.disableBtns.disableInternalApproveBtn = false;
				$scope.disableBtns.displayReturnBtn = false;
			}
			
			const soeReSubmit =  $scope.schofEstimatesApprTO[0];
			console.log(soeReSubmit);
			var dates1 = new Date($scope.soeDataItems[0].submittedDate);
			if (soeReSubmit.cutOffDays) {
			 dates1.setDate(dates1.getDate() + 1 + soeReSubmit.cutOffDays);
			}
			if (soeReSubmit.cutOffHours) {
		     dates1.setHours(dates1.getHours() + soeReSubmit.cutOffHours);
			}
			if (soeReSubmit.cutOffMinutes) {
			dates1.setMinutes(dates1.getMinutes() + soeReSubmit.cutOffMinutes);
			}
			console.log(dates1);
			const newtoday = new Date();
			$scope.retimeFlag = (dates1.getTime() <= newtoday);
			console.log($scope.retimeFlag);
			if($scope.retimeFlag){
				$scope.disableBtns.disableExternalSubmitBtn = true;
				$scope.disableBtns.displayAddtlTime = true;
			}
			if($scope.retimeFlag == false){
				$scope.disableBtns.disableInternalSubmitBtn = false;
				$scope.disableBtns.disableExternalSubmitBtn = false;
				if($scope.soeStatus == 'SUBMITTED_FOR_INTERNAL_APPROVAL'){
					$scope.disableBtns.disableInternalSubmitBtn = true;
				}
				
			}
			
			const soeExtSubmit = $scope.schofEstimatesApprTO[1];
			var dates2 = new Date($scope.soeDataItems[0].submittedDate);
			console.log(dates2);
			if (soeExtSubmit.cutOffDays) {
			 dates2.setDate(dates2.getDate() + 1 + soeExtSubmit.cutOffDays);
			}
			if (soeExtSubmit.cutOffHours) {
		     dates2.setHours(dates2.getHours() + soeExtSubmit.cutOffHours);
			}
			if (soeExtSubmit.cutOffMinutes) {
			dates2.setMinutes(dates2.getMinutes() + soeExtSubmit.cutOffMinutes);
			}
			
			const extoday = new Date();
			$scope.extTimeFlag = (dates2.getTime() <= extoday);
			console.log($scope.extTimeFlag);
			if ($scope.extTimeFlag) {
		        $scope.disableBtns.displayReturnBtn	= true;
                $scope.disableBtns.disableExternalApproveBtn = true;
                $scope.disableBtns.displayAddtlTime = true;
	         }
             if($scope.extTimeFlag && $scope.addlTimeFlag){
				console.log($scope.addlTimeFlag);
				$scope.disableBtns.displayAddtlTime = false;
				$scope.disableBtns.disableExternalApproveBtn = false;
				$scope.disableBtns.displayReturnBtn = false;
			}

            if($scope.soeStatus == 'INTERNAL_APPROVED' || $scope.soeStatus == 'SUBMITTED_FOR_EXTERNAL_APPROVAL' || $scope.soeStatus == 'FINALIZED'){
	           $scope.disableBtns.disableInternalSubmitBtn = true;
           //    $scope.disableBtns.disableExternalSubmitBtn = true;
               $scope.disableBtns.disableInternalApproveBtn = true;
            }
	        if($scope.soeStatus == 'SUBMITTED_FOR_EXTERNAL_APPROVAL' || $scope.soeStatus == 'FINALIZED'){
                $scope.disableBtns.disableExternalSubmitBtn = true;
            }
            if($scope.soeStatus == 'SUBMITTED_FOR_INTERNAL_APPROVAL'){
	          $scope.disableBtns.disableExternalSubmitBtn = true;
            }
            if($scope.soeStatus == 'RETURNED_FROM_EXTERNAL_APPROVER'){
	             $scope.disableBtns.disableInternalSubmitBtn = true;   
            }
            if($scope.soeStatus == 'RETURNED_FROM_INTERNAL_APPROVER'){
	             $scope.disableBtns.disableExternalSubmitBtn = true;  
            }
            if($scope.soeStatus == 'DRAFT'){
	       //    $scope.disableBtns.disableInternalSubmitBtn = true;
               $scope.disableBtns.displayAddtlTime = false;
               $scope.disableBtns.disableInternalApproveBtn = true;
               $scope.disableBtns.disableExternalSubmitBtn = true;
               $scope.disableCreateBtn = false;
            }
            if($scope.soeStatus == 'DRAFT' || $scope.soeStatus == 'RETURNED_FROM_EXTERNAL_APPROVER' || $scope.soeStatus == 'RETURNED_FROM_INTERNAL_APPROVER'){
	          $scope.returnButton = false;
            }
		    });	
           
            
			
			if($scope.soeStatus != null && $scope.soeStatus != "")
			{
				if( $scope.soeStatus.indexOf('RETURN') > -1 )
				{
					let soeStstusArry = $scope.soeStatus.split("_");
					if( soeStstusArry[2] == "INTERNAL" )
					{
						$scope.disableBtns.disableInternalSubmitBtn = true;	
				//		$scope.disableBtns.displayAddtlTime = true;
					}
					else
					{
						$scope.disableBtns.disableExternalSubmitBtn = false;
											
					}					
				}
				else if( $scope.soeStatus.indexOf('DRAFT') > -1 ) {
					$scope.disableBtns.disableInternalSubmitBtn = false;
					
				}
				else if( $scope.soeStatus.indexOf('SUBMITTED') > -1 ) {
					if( $scope.soeStatus == 'SUBMITTED_FOR_INTERNAL_APPROVAL' )
					{
				
						$scope.disableBtns.disableInternalApproveBtn = false;
						
						
					}
					else
					{
						$scope.disableBtns.disableExternalApproveBtn = false;
					}
					
		 		$scope.disableBtns.displayReturnBtn = false;
			    
				}
				else if( $scope.soeStatus.indexOf('INTERNAL_APPROVED') > -1 ) {
					$scope.disableBtns.disableExternalSubmitBtn = false;					
				}
				else if( $scope.soeStatus.indexOf('FINALIZED') > -1 ) {
					$scope.disableCreateBtn = true;
					$scope.disableEditValues = false;
				}
			}
			else
			{
				console.log("else condition");
				$scope.disableCreateBtn = false;
			}
			
			if($scope.soeStatus == 'RETURNED_FROM_INTERNAL_APPROVER'){
				$scope.disableBtns.displayReturnBtn = true;
			}
			/*if($scope.soeStatus == 'DRAFT'){
			          $scope.disableCreateBtn = false;
               //       $scope.disableBtns.disableInternalSubmitBtn = false;
			}*/
			if($scope.soeStatus == 'RETURNED_FROM_EXTERNAL_APPROVER'){
				$scope.disableBtns.disableInternalSubmitBtn = true;
				
			}
			/*if($scope.soeStatus == 'RETURNED_FROM_INTERNAL_APPROVER' && $scope.timeFlag == false){
					$scope.disableBtns.disableInternalApproveBtn = true;
				}*/
			/*if($scope.soeStatus == 'RETURNED_FROM_INTERNAL_APPROVER' || $scope.soeStatus == 'RETURNED_FROM_EXTERNAL_APPROVER'){
				$scope.disableCreateBtn = false;
			}*/
			
			
			angular.forEach($scope.SOEData,function(value,key){
				soeItemIdsData.push(value.id);
			});
			if ($scope.SOEData <= 0) {
				GenericAlertService.alertMessage("SOE Details are not available for given search criteria", 'Warning');
			}			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	};
	$rootScope.$on('soeRefresh', function(){
	   $scope.resetSOEDatas();
    });
    $scope.resetSOEDatas = function(){
	  if($scope.soeStatus != null)
		$scope.soeStatus = "";  
		$scope.flag = true;
      $scope.getSOEDetails();
      $scope.disableBtns = {"disableInternalSubmitBtn":true,"disableInternalApproveBtn":true,"disableExternalSubmitBtn":true,"disableExternalApproveBtn":true,"displayReturnBtn":true,"displayAddtlTime":false};
    }
	function retrieveSOEStatus(soeItemsData) {
		angular.forEach(soeItemsData,function(value,key){
			$scope.soeStatus = null;
			if( value.item && ( $scope.soeStatus==null || $scope.soeStatus == "" ) )
			{
				$scope.soeStatus = value.soeItemStatus;
			}
		});
		console.log($scope.soeStatus);
	}

	function populateSoeData(data, level, soeTOs) {
		return TreeService.populateTreeData(data, level, soeTOs, 'code', 'childSOEItemTOs');
	}

	$scope.resetSOEData = function() {
		$scope.SOEData = [];
		$scope.soeStatus = "";
		$scope.activeFlag=0;
		$scope.searchProject = {};
		$scope.disableBtns = {"disableInternalSubmitBtn":true,"disableInternalApproveBtn":true,"disableExternalSubmitBtn":true,"disableExternalApproveBtn":true,"displayReturnBtn":true,"displayAddtlTime":false};
	},
			
    $scope.requestForAddtinalTimeSoe = function(){
	console.log(soeItemIdsData);
	    RequestForSoeAdditionalTimeFactory.getAdditionalTimeDetails($scope.searchProject.projId,$scope.searchProject.projName,$scope.soeDataItems).then(function(data){
	})
    }
	
	$scope.rowSelect = function(rowData) {
		console.log("rowSelect function");
		if (rowData.select) {
			deleteSOEData.push(rowData.id);			
		} else {
			deleteSOEData.splice(deleteSOEData.indexOf(rowData.id), 1);			
		}
		console.log(deleteSOEData);
	}
	$scope.projSOESubmission = function(mode,approvalType) {
		ProjSOEFactory.approvalUserPopup(deleteSOEData,soeItemIdsData,$scope.searchProject.projId,mode,approvalType,$scope.soeDataItems);
	}
	$scope.projSOEApproval = function(mode,approvalType) {
		ProjSOEFactory.approvalUserPopup(deleteSOEData,soeItemIdsData,$scope.searchProject.projId,mode,approvalType,$scope.soeDataItems);
	}
	$scope.returnWithComments = function(soeitem) {
		ProjSOEFactory.approvalUserPopup(soeitem.id,soeItemIdsData,$scope.searchProject.projId,null,"RETURN",$scope.soeDataItems);
	}
	
	$scope.viewHistory = function() {
		console.log("viewHistory function");
		console.log(deleteSOEData);
		ProjSOEFactory.viewRecordsPopup(soeItemIdsData,$scope.searchProject.projId);
	}
	
	var soepopup;
	var addSOEservice = {};
	$scope.editSOEDetails = function(actionType, itemData, projId) {
		if (deleteSOEData.length > 0 && actionType == 'Add') {
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		soepopup = addSOEservice.addSOEDetails(actionType, itemData, projId, $scope.SOEData);
		soepopup.then(function(data) {
			$scope.SOEData = populateSoeData(data.projSOEItemTOs, 0, []);
		}, function(error) {
			GenericAlertService.alertMessage(" SOE(s) is/are", "Error");
		});
	}

	addSOEservice.addSOEDetails = function(actionType, itemData, projId, soeList) {
		var deferred = $q.defer();
		if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
			soepopup = ngDialog.open({
				template : 'views/projectlib/soe/projsoeeditpopup.html',
				scope : $scope,
				closeByDocument : false,
				showClose : false,
				className:'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
				controller : [ '$scope', function($scope) {
					$scope.pcode = null;
					$scope.action = actionType;
					$scope.editSOEData = [];
					$scope.measurements = [];
					$scope.projSoeId = null;
					var soeData = [];
					$scope.itemId1 = 1;
					$scope.isExpand1 = true;
					$scope.projSoePopupItemClick = function (item, expand) {
						TreeService.dynamicTreeItemClick($scope.editSOEData, item, expand);
					};
					if (itemData) {
						$scope.pcode = itemData.name;
						$scope.projSoeId = itemData.id;
					}
					$scope.projSoeifOnLoad = function() {
						var measurementReq = {
							"status" : "1",
							"soeId" : $scope.projSoeId,
							"projId" : projId,
							"loggedInUser" : $rootScope.account.userId
						};
						console.log(measurementReq);
						ProjSOEService.projSoeifOnLoad(measurementReq).then(function(data) {
							$scope.measurements = data.measureUnitResp.measureUnitTOs;
							if ($scope.projSoeId != null) {
								$scope.editSOEData = data.projSOEItemTOs;
							}							
							soeData = data.projSOEItemTOs;
							console.log(soeData);
							if(soeData && soeData.length)
							    $scope.editSOEData = TreeService.populateDynamicTreeData(soeData, 0, [], 'code', 'childSOEItemTOs');							
						});

					}, $scope.updateMeasureId = function(tab, data) {
						tab.measureId = data.id;
					}					
					if (actionType === 'Add') {
						$scope.editSOEData.push({
							"select" : false,
							"projId" : projId,
							"parentId" : null,
							"item" : false,
							"deleteFlag" : false,
							"status" : 1,
							"code" : '',
							"name" : '',
							"comments" : '',
							"childSOEItemTOs" : []
						});
					} 
					$scope.editSOEData = TreeService.populateDynamicTreeData($scope.editSOEData, 0, [], 'code', 'childSOEItemTOs');

					$scope.addSOESubGroup = function (tabData, projId, itemIndex) {
						// check for input fileds validations
						let isValid = true;
						for (let i=0; i<$scope.editSOEData.length; i++)
							isValid = isValid && validateEntry($scope.editSOEData[i], $scope.editSOEData, soeList);
						if (isValid) {
							const obj = angular.copy({
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"item": false,
								"deleteFlag": false,
								"status": 1,
								"code": null,
								"name": null,
								"comments": '',
								"childSOEItemTOs": [],
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							});
							$scope.editSOEData = TreeService.addItemToTree($scope.editSOEData, tabData,
								obj, itemIndex, 'childSOEItemTOs');
						}
					}
					$scope.addSOEItem = function (tabData, projId, itemIndex) {
						// check for input fileds validations
						let isValid = true;
						for (let i=0; i<$scope.editSOEData.length; i++)
							isValid = isValid && validateEntry($scope.editSOEData[i], $scope.editSOEData, soeList);
						if (isValid) {
							const obj = angular.copy({
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"status": 1,
								"code": null,
								"name": null,
								"item": true,
								"deleteFlag": false,
								"measureUnitTO": '',
								"measureId": null,
								"quantity": 0,
								"manHours": 0,
								"comments": '',
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							});
							console.log(obj);
							$scope.editSOEData = TreeService.addItemToTree($scope.editSOEData, tabData,
								obj, itemIndex, 'childSOEItemTOs');
						}
					},
					$scope.deleteSOE = function(index) {
						TreeService.deleteDynamicTreeItem($scope.editSOEData,index);
					},
					$scope.saveSOEDetails = function() {
						let isValid = true;
						for (let i=0; i<$scope.editSOEData.length; i++) {
							let result = validateEntry($scope.editSOEData[i], $scope.editSOEData, soeList);
							isValid = isValid && result;
						}
						if (isValid) {
							const data = TreeService.extractTreeDataForSaving($scope.editSOEData, 'childSOEItemTOs');
							var soeSaveReq = {
								"projSOEItemTOs" : data,
								"projId" : projId,
								"actionType" : actionType,
								"loggedInUser" : $rootScope.account.userId
							};
							blockUI.start();
							ProjSOEService.saveSOEDetails(soeSaveReq).then(function(data) {
								blockUI.stop();
								if (data.status != null && data.status !== undefined && data.status === 'Info') {
									var projSOETOs = data.projSOEItemTOs;
									// var succMsg = GenericAlertService.alertMessageModal('Project SOE(s) is/are ' + data.message, data.status);
									var succMsg = GenericAlertService.alertMessageModal('Project SOE(s) saved successfully',"Info");
									succMsg.then(function(data) {
										$scope.closeThisDialog();
										var returnPopObj = {
											"projSOEItemTOs" : projSOETOs
										};
										deferred.resolve(returnPopObj);
									}, function(error) {
										blockUI.stop();
										GenericAlertService.alertMessage("SOE(s) is/are failed to Save", "Error");
									});
									const datas = TreeService.extractTreeDataForSaving($scope.editSOEData, 'childSOEItemTOs');
									var soeTrackSaveReq = {
										"projSOETrackTOs": datas,
										"projId": projId,
										"actionType": actionType,
										"loggedInUser": $rootScope.account.userId
									}
									console.log(soeTrackSaveReq);									
									ProjSOEService.saveSOETrackDetails(soeTrackSaveReq).then(function(data) {
										console.log(data);
									});
									$scope.getSOEDetails();
								}
							});
						} /*else {
							GenericAlertService.alertMessage("Please fill all mandatory fields data", "Info");
						}*/
					};
					function validateEntry(entry, currentSoeList, projectSoeList) {
						let isValid = true;
						delete entry.codeErrorMessage;
						delete entry.nameErrorMessage;
						delete entry.uomErrorMessage;
						delete entry.quantityErrorMessage;
						delete entry.manHoursErrorMessage;
						delete entry.commentsErrorMessage;
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
							if (entry.quantity == null) {
								entry.quantityErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
							if (entry.manHours == null) {
								entry.manHoursErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
							if (entry.comments == null || entry.comments == '') {
								entry.commentsErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
						}
						if (entry.code != null) {
							if (entry.id) {
				    			if (projectSoeList.find(e => e.code.toLowerCase().replace(/\s/g, "") == entry.code.toLowerCase().replace(/\s/g, "") && e.id != entry.id) != null) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    		} else {
				    			if (projectSoeList.find(e => e.code.toLowerCase().replace(/\s/g, "") == entry.code.toLowerCase().replace(/\s/g, "")) != null) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    			let count = 0;
				    			for (let i=0; i<(currentSoeList.length)-1; i++)
				    				if (currentSoeList[i].code.toLowerCase() == entry.code.toLowerCase()) count++;
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
	}

	$scope.deactivateSOEDetails = function() {
		if (deleteSOEData == undefined || deleteSOEData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Schedule Of Estimate to deactivate", 'Warning');
			return;
		}
		var soeDeactivateReq = {
			"projSOEItemIds" : deleteSOEData,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			ProjSOEService.deactivateSOEDetails(soeDeactivateReq).then(function(data) {
				GenericAlertService.alertMessage("Schedule of Estimate(s) Deactivated successfully", "Info");
				deleteSOEData = [];
				$scope.getSOEDetails();
			}, function(error) {
				GenericAlertService.alertMessage(' Schedule Of Estimate(s) is/are failed to deactivate', "Error");
			});
		}, function(data) {
			deleteSOEData = [];
			$scope.getSOEDetails();
		})
	}

	$scope.soeItemClick = function(item, expand) {
		TreeService.treeItemClick(item, expand, 'childSOEItemTOs');
	};
	$scope.show = function(comments) {
		ngDialog.open({
			template : 'views/projectlib/sow/viewpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.comments = comments;
			} ]
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
			template: 'views/help&tutorials/projectshelp/projsoehelp.html',
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
