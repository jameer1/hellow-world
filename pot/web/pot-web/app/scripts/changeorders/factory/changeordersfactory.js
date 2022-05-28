'use strict';

app.factory('ChangeOrdersFactory', ["ngDialog", "$q", "$rootScope", "GenericAlertService","EpsProjectSelectFactory","RegisterPurchaseOrderFactory","ChangeOrdersService","WorkDiaryService","ProjSOEService","TreeService", "ProjSOWService", "PreContractInternalService", "MaterialFactory", "MaterialClassService", "PreContractProjEmpClassFactory", "ApproverListUserFactory", "ProjectBudgetService",   
function (ngDialog, $q, $rootScope, GenericAlertService, EpsProjectSelectFactory, RegisterPurchaseOrderFactory, ChangeOrdersService, WorkDiaryService, ProjSOEService, TreeService, ProjSOWService, PreContractInternalService, MaterialFactory, MaterialClassService, PreContractProjEmpClassFactory, ApproverListUserFactory, ProjectBudgetService ) {

	var coCreatePopup;
	var service = {};
	service.coCreatePopup = function (coSearchData,coEditType) {
		var deferred = $q.defer();

		coCreatePopup = ngDialog.open({
			template: '/views/changeorders/createcopopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.currentTab1 = null;
				$scope.currentTabTitle = "";
				var temporaryStoredData = null;
				var currentActiveTab = null;
				$rootScope.selectedEmployeeId = null;
				$scope.SOEData = [];
				var SelectCo = [];
				$scope.noteBookList = [];
				$scope.costDatamoreFlag = 0;
				$scope.coObj = null;
				$scope.preContractType="";
				console.log(coSearchData);
				console.log(coEditType);
				$scope.current_date = new Date();
				var coHeadSelect="";
				$scope.coSubItemtype=null
				console.log($scope.coSubItemtype)
				
				//Onchange for Head Contract SOW Item ID
				
				$scope.changeItemType = function(val) {
					coHeadSelect=val
					console.log(val)
				}
				
				//On change for Sub Contract SOW Item ID
				$scope.coSubChange=function(val){
					$scope.coSubItemtype=val
					console.log(val)
				}
				if( coEditType == 'CREATE' )
				{
					$scope.coSearchReq = {
						projId : null,
						projName : null,
						contractType : null,
						contractId : null,
						purchaseOrderId : null,
						purchaseOrderCode : null,
						reasonForChange : null,
						description : null,
						reasonForChange : null
					}	
				}
				else
				{
					$scope.coSearchReq = {
						projId : coSearchData.projId,
						projName : coSearchData.name,
						contractType : null,
						contractId : null,
						purchaseOrderId : null,
						purchaseOrderCode : null,
						description : coSearchData.description,
						reasonForChange : coSearchData.reasonForChange,
						createdOn : coSearchData.createdOn
					}
					$scope.coSearchReq.code = coSearchData.coCode+"-"+coSearchData.id;
					$scope.coSearchReq.coId = coSearchData.id;
				}
				console.log($scope.coSearchReq);
				$scope.coHeadContractRowData={
					coSowSelect:false,
					approvedSow : null,
					pendingSow : null,
					currentSow : null,
				    coSowItemId:null,
					coSowItemDescription:null,
					coSorItemId:null,
					unitOfMeasure:null,
					approvedSowQty:null,
					pendingSowQty:null,
					currentSowQty:null,
					cumulativeSowQty:null,
					approvedRates:null,
					newRates:null,
					newItemRate:null,
					newItemRate:null,
					prevApprovedAmt:null,
					pendingOrderAmt:null,
					currentAmt:null,
					cumulativeAmt:null,
					currency:null
				};
				$scope.coSubContractRowData={
					approvedSow : null,
					pendingSow : null,
					currentSow : null,
					coSowItemId:null,
					coSowItemDescription:null,
					unitOfMeasure:null,
					approvedSowQty:null,
					pendingSowQty:null,
					currentSowQty:null,
					cumulativeSowQty:null,
					currency:null,
					newRates:null,
					prevApprovedAmt:null,
					pendingOrderAmt:null,
					currentAmt:null,
					cumulativeAmt:null,
					notes:null
					
				};
				
				$scope.coMaterialRowData={
					materialClassificationId : null,
					materialDescription : null,
					approvedMaterialQty : 0,
					pendingMaterialQty : 0,
					currentMaterialQty : 0,
					cumulativeMaterialQty : 0,
					notes : null
				};
				$scope.coPlantRowData={
					plantClassificationId : null,
					plantDescription : null,
					unitOfMeasure : 0,
					approvedPlantHrs : 0,
					pendingPlantHrs : 0,
					currentPlantHrs : 0,
					cumulativePlantHrs : 0,
					notes : null
				};
				console.log("before display blocks");
				$scope.displayHeadContractBlock = false;
				$scope.displaySubContractBlock = false;
				$scope.displayPoContractBlock = false;
				$scope.coHeadContractRows = [];
				$scope.coSubContractRows = [];
				console.log(coSearchData);
				
				$scope.disableInternalBtn = ( $scope.coEditType == "EDIT" ) ? ( coSearchData.approvalStatus.indexOf('CO_DRAFT') > 0 ) ? false : true : true;
				$scope.disableExternalBtn = ( $scope.coEditType == "EDIT" ) ? ( coSearchData.approvalStatus.indexOf('INTERNALLY_APPROVED') >= 0 ) ? false : true : true;
				$scope.disableApproveBtn = ( $scope.coEditType == "EDIT" ) ? ( coSearchData.approvalStatus.indexOf('SUBMITTED_FOR_INTERNAL') >= 0 || coSearchData.approvalStatus.indexOf('SUBMITTED_FOR_EXTERNAL') >= 0 ) ? false : true : true;
				$scope.disableRejectBtn = ( $scope.coEditType == "EDIT" ) ? ( coSearchData.approvalStatus.indexOf('SUBMITTED_FOR_INTERNAL') >= 0 || coSearchData.approvalStatus.indexOf('SUBMITTED_FOR_EXTERNAL') >= 0 ) ? false : true : true;
				$scope.disableReturnBtn = ( $scope.coEditType == "EDIT" ) ? ( coSearchData.approvalStatus.indexOf('SUBMITTED_FOR_INTERNAL') >= 0 || coSearchData.approvalStatus.indexOf('SUBMITTED_FOR_EXTERNAL') >= 0 ) ? false : true : true;				
								
				$scope.coMaterialRows = [];
				$scope.coPlantRows = [];
				
				$scope.isExternalApprovalRequired = false;
				$scope.selectedProjs = ( coEditType=='EDIT' ) ? coSearchData.projName : null;
				$rootScope.projSowData = [];
				//$scope.displayCreateButton = ( coEditType == 'CREATE' ) ? true: false;
				$scope.coEditType = coEditType;
				$scope.tabsChange=function(val){
					$scope.preContractType=val;
					if(val == 'Head Contract'){
						$scope.currentTab1 = 'views/changeorders/tabs/cosow.html';
						
						$scope.coDetailTabs = [{
						title: 'Scope of Work',
						url: 'views/changeorders/tabs/cosow.html',
						nameOfVariable: 'coSOWTOs',
					},
					{
						title: 'Manpower',
						url: 'views/changeorders/tabs/comanpower.html',
						nameOfVariable: 'coManpowerTOs',
					}, {
						title: 'Materials',
						url: 'views/changeorders/tabs/comaterial.html',
						nameOfVariable: 'coMaterialsTOs',
					}, {
						title: 'Plants',
						url: 'views/changeorders/tabs/coplants.html',
						nameOfVariable: 'coPlantsTOs',
					}, 
					{
						title: 'Cost',
						url: 'views/changeorders/tabs/cocost.html',
						nameOfVariable: 'coCostTOs',
					}];
					$scope.CoReqFo();
					
						$scope.isActiveTab = function(taburlValue) {
							return taburlValue == $scope.currentTab1;
						}
					}
					
					
					if(val == "Sub Contract agreement" || val == "Professional Services agreement" ||val =="Purchase  Order") {
						$scope.currentTab1 = 'views/changeorders/tabs/cosow.html';
						
						$scope.coDetailTabs = [{
						title: 'Scope of Work',
						url: 'views/changeorders/tabs/cosow.html',
						nameOfVariable: 'coSOWTOs',
					}];
					$scope.CoReqFo();
					$scope.isActiveTab = function(taburlValue) {
							return taburlValue == $scope.currentTab1;
						}
					}
				}
				console.log("before coDetailTabs");
				$scope.coDetailTabs = [{
						title: 'Scope of Work',
						url: 'views/changeorders/tabs/cosow.html',
						nameOfVariable: 'coSOWTOs',
					},
					{
						title: 'Manpower',
						url: 'views/changeorders/tabs/comanpower.html',
						nameOfVariable: 'coManpowerTOs',
					}, {
						title: 'Materials',
						url: 'views/changeorders/tabs/comaterial.html',
						nameOfVariable: 'coMaterialsTOs',
					}, {
						title: 'Plants',
						url: 'views/changeorders/tabs/coplants.html',
						nameOfVariable: 'coPlantsTOs',
					}, 
					{
						title: 'Cost',
						url: 'views/changeorders/tabs/cocost.html',
						nameOfVariable: 'coCostTOs',
					}];
				
				
				
				$scope.coSearchReq.requestorUser = {"id":$rootScope.account.userId,"name":$rootScope.account.name};
				console.log($rootScope.account);
				console.log($scope.coSearchReq);
				/*$scope.coDetailTabs[0].disabled = true;
				$scope.coDetailTabs[1].disabled = true;
                $scope.coDetailTabs[2].disabled = true;
                $scope.coDetailTabs[3].disabled = true;
                $scope.coDetailTabs[4].disabled = true;*/
				$scope.onClickTab1 = function (tab) {
					console.log(tab);
					if (temporaryStoredData) {
						var diff = difference($scope.coDetailTabs[currentActiveTab.nameOfVariable], temporaryStoredData);
						_.isEmpty(diff) ? switchTab(tab) : $scope.$broadcast('activeTabEvent', currentActiveTab.title) && GenericAlertService.alertMessage("Please save (or) remove your changes!", "Warning");
					} else {
						switchTab(tab);
					}
				};
				$scope.selectCoContractType = function() {
					if( $scope.coSearchReq.contractType == 'Head Contract' ) {
						$scope.coSearchReq.contractId = $scope.contractId;
					} else {
						$scope.coSearchReq.contractId = null;
					}
				},
				$scope.clickForwardCostData = function (costDatamoreFlag1) {
					if ($scope.costDatamoreFlag < 1) {
						$scope.costDatamoreFlag = costDatamoreFlag1 + 1;
					}
				}, $scope.clickBackwardCostData = function (costDatamoreFlag1) {
					if ($scope.costDatamoreFlag > 0) {
						$scope.costDatamoreFlag = costDatamoreFlag1 - 1;
					}
				},
				$scope.clickForwardCostData1 = function (costDatamoreFlag1) {
					if ($scope.costDatamoreFlag < 3) {
						$scope.costDatamoreFlag = costDatamoreFlag1 + 1;
					}
				}, $scope.clickBackwardCostData1 = function (costDatamoreFlag1) {
					if ($scope.costDatamoreFlag > 0) {
						$scope.costDatamoreFlag = costDatamoreFlag1 - 1;
					}
				},			
				$scope.selectCoSubContract = function (projId) {
					console.log(projId)
					if (projId == undefined || projId == null) {
						GenericAlertService.alertMessage("Please select project to get sub-contracts", 'Warning');
						return;
					}
					console.log(coSearchData.projId)
					var req = {
						"status": 1,
						"projId": projId,
						"procureType": 'SOW',
						"data":[
							{
						"status": 1,
						"projId": projId,
						"preContractType": $scope.preContractType
						}
						]
					};
					RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function (data) {
						console.log(data);
						$scope.coSearchReq.purchaseOrderCode = data.purchaseOrderTO.code;
						$scope.coSearchReq.purchaseOrderId = data.purchaseOrderTO.id;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
					});
				}
				$scope.getUserProjects = function (coSearchReq) {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function (data) {
						console.log(data);
						$scope.searchProject = data.searchProject;
						$scope.coSearchReq.projId = data.searchProject.projId;
						$scope.coProjId=data.searchProject.projId;
						$rootScope.selectedEmployeeId = $scope.coProjId;
						
					console.log($rootScope.selectedEmployeeData);
						//projectLabelKeyTO.parentName = data.searchProject.parentName;
						$scope.coSearchReq.projName = data.searchProject.projName;
						//$scope.coSearchReq.contractNo = null;
						$scope.coSearchReq.contractType = 'Head Contract';
						console.log($scope.coSearchReq);
						$rootScope.selectedProject = data.searchProject;
						$scope.getProjSettingsForContract($scope.coSearchReq.projId);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}
				$scope.getProjSettingsForContract = function (projId) {
					if (projId == undefined || projId == null) {
						GenericAlertService.alertMessage("Please select project to get contract number", 'Warning');
						return;
					}
					var req = {
						"status": 1,
						"projId": projId	
					};
					var resultData = WorkDiaryService.getProjSettingsForWorkDairy(req);
					resultData.then(function (data) {		
						console.log(data);				
						$scope.coSearchReq.contractId = data.labelKeyTO.code;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while gettting project contract number", 'Error');
					});
				}
				$scope.CoReqFo = function () {
					if ($scope.coSearchReq.contractType == "Head Contract") {
						$scope.displayHeadContractBlock = true;
						$scope.displaySubContractBlock = false;
						$scope.displayPoContractBlock = false;
					}
					else if ($scope.coSearchReq.contractType == "Sub Contract agreement") {
						$scope.displaySubContractBlock = true;
						$scope.displayHeadContractBlock = false;
						$scope.displayPoContractBlock = false;
					}
					else if ($scope.coSearchReq.contractType == "Professional Services agreement" || "Purchase  Order") {
						$scope.displayPoContractBlock = true;
						$scope.displayHeadContractBlock = false;
						$scope.displaySubContractBlock = false;
					}
					else { return value; }
				}
				$scope.createCONew = function() {
					
					console.log($scope.coSearchReq);
					var co_search = {
						"changeOrderTOs" : []
					};
					co_search.changeOrderTOs.push($scope.coSearchReq);
					console.log(co_search)
					//enable the below code to save the change order details
					ChangeOrdersService.saveOrderDetails(co_search).then(function(data) {
						console.log(data);
						GenericAlertService.alertMessage("Change Order details saved successfully", 'Info');
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
					});
				}
				/* Scope of Work Tab changes */
				$scope.addCoRows = function() {
					if( $scope.coSearchReq.contractType == "Head Contract" )
					{
						$scope.displayHeadContractBlock = true;
						$scope.displaySubContractBlock = false;
						$scope.displayPoContractBlock = false;
						$scope.coHeadContractRows.push(angular.copy($scope.coHeadContractRowData));
					}
					else
					{
						if( $scope.coSearchReq.contractType == "Sub Contract agreement" )
						{
							$scope.displaySubContractBlock = true;
							$scope.displayHeadContractBlock = false;
							$scope.displayPoContractBlock = false;
							$scope.coSubContractRows.push(angular.copy($scope.coSubContractRowData));
						}
						else
						{
							$scope.displayPoContractBlock = true;
							$scope.displayHeadContractBlock = false;
							$scope.displaySubContractBlock = false;
						}
					}				
					//$scope.coHeadContractRows.push(angular.copy($scope.coHeadContractRowData));
					console.log($scope.coHeadContractRows);
					console.log($scope.coSubContractRows);
				}
				var sowPopupService = {};
				$scope.sowPopup = function(sowRowData) {
					//console.log(projId)
					console.log($scope.coSearchReq.contractType);
					console.log(sowRowData)
					//ChangeOrdersSowFactory.openCreatePopup(projId);
					//console.log($rootScope.projSowData);
					//ChangeOrdersSowFactory.openExistingSowPopup(projId);
					var projId=$rootScope.selectedProject.projId;
					console.log(projId)
					var sowpopupservice;					
					sowpopupservice = sowPopupService.openExistingSowPopup(projId);
					sowpopupservice.then(function(data){
						console.log(data);
						console.log($scope.coSearchReq.projId);
						//$scope.getProjSettingsForProcurement();

							$scope.getProjSettingsForProcurement(sowRowData);
							console.log(data.selectedCostItem.code);
							sowRowData.coSowItemId = data.selectedCostItem.code;
							sowRowData.coSowItemDescription = data.selectedCostItem.name;
							sowRowData.coSorItemId =data.selectedCostItem.projSORItemTO.code;
							sowRowData.unitOfMeasure = data.selectedCostItem.measureUnitTO.name;
							sowRowData.approvedSowQty = coSubItemtype != "newitem" ? data.selectedCostItem.quantity : 0;
							sowRowData.pendingSowQty = data.selectedCostItem.code;;
							sowRowData.currentSowQty = 0;
							sowRowData.cumulativeSowQty = sowRowData.approvedSowQty + sowRowData.pendingSowQty + sowRowData.currentSowQty;
							sowRowData.approvedRates = 0;
							sowRowData.newRates =coSubItemtype != "newitem" ? sowRowData.newRates : "";
							sowRowData.newItemRate=coSubItemtype != "newitem" ? sowRowData.newItemRate : "";
							sowRowData.prevApprovedAmt = sowRowData.approvedSowQty + sowRowData.currentSowQty;
							sowRowData.pendingOrderAmt=0;
							sowRowData.currentAmt = sowRowData.currentSowQty * sowRowData.approvedRates;
							sowRowData.cumulativeAmt =sowRowData.prevApprovedAmt + sowRowData.pendingOrderAmt+ sowRowData.currentAmt;
							//sowRowData.currency = $scope.getProjSettingsForProcurement(sowRowData);
						
						//console.log($scope.coHeadContractRows)			
					});
				},
				$scope.getProjSettingsForProcurement = function(currentData) {
					var req = {"projId": $scope.coSearchReq.projId}
					PreContractInternalService.getProjSettingsForProcurement(req).then(function (data) {
						console.log(data);
						currentData.currency =  data.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while gettting workflow status",'Error');
					});
				}
				$scope.calculateCumulativeQty1 = function(currentdata) {
					console.log(currentdata.currentSowQty);
					currentdata.cumulativeSowQty = Number(currentdata.currentSowQty) +Number(currentdata.pendingSowQty)+Number(currentdata.approvedSowQty);
				}
				$scope.calculateCumulativeQty2 = function(currentdata) {
					currentdata.cumulativeSowQty =  Number(currentdata.currentSowQty) +Number(currentdata.pendingSowQty)+Number(currentdata.approvedSowQty);
				}
				$scope.calculateCumulativeQty3 = function(currentdata) {
					currentdata.cumulativeSowQty = Number(currentdata.currentSowQty) +Number(currentdata.pendingSowQty)+Number(currentdata.approvedSowQty);;
				}
				//
				$scope.calculateCumulativeAmt1 = function(currentdata) {
					console.log(currentdata.currentAmt);
					currentdata.cumulativeAmt = Number(currentdata.currentAmt) +Number(currentdata.pendingOrderAmt)+Number(currentdata.prevApprovedAmt);
				}
				$scope.calculateCumulativeAmt2 = function(currentdata) {
					currentdata.cumulativeAmt =  Number(currentdata.currentAmt) +Number(currentdata.pendingOrderAmt)+Number(currentdata.prevApprovedAmt);
				}
				$scope.calculateCumulativeAmt3 = function(currentdata) {
					currentdata.cumulativeAmt = Number(currentdata.currentAmt) +Number(currentdata.pendingOrderAmt)+Number(currentdata.prevApprovedAmt);;
				}
				
				//-------------Sub Contract ow id search function----------
				$scope.subContractSowid=function(rowdata){
					console.log($scope.coSubItemtype)
					if($scope.coSubItemtype != "" || $scope.coSubItemtype !=null){
						var projId=$rootScope.selectedProject.projId;
					console.log(projId)
					let sowpopupservice;					
					sowpopupservice = sowPopupService.openExistingSowPopup(projId);
					sowpopupservice.then(function(data){
						console.log(data)
						
						$scope.getProjSettingsForProcurement(rowdata);
							console.log(data.selectedCostItem.code);
							rowdata.coSowItemId = data.selectedCostItem.code;
							rowdata.coSowItemDescription = data.selectedCostItem.name;
							//sowRowData.coSorItemId =data.selectedCostItem.projSORItemTO.code;
							rowdata.unitOfMeasure = data.selectedCostItem.measureUnitTO.name;
							rowdata.approvedSowQty = coHeadSelect != "newitem" ? data.selectedCostItem.quantity : 0;
							rowdata.pendingSowQty = 0;
							rowdata.currentSowQty = 0;
							rowdata.cumulativeSowQty = sowRowData.approvedSowQty + sowRowData.pendingSowQty + sowRowData.currentSowQty;
							rowdata.approvedRates = 0;
							rowdata.newRates =coHeadSelect != "newitem" ? sowRowData.newRates : "";
							rowdata.newItemRate=coHeadSelect != "newitem" ? sowRowData.newItemRate : "";
							rowdata.prevApprovedAmt = sowRowData.approvedSowQty + sowRowData.currentSowQty;
							rowdata.pendingOrderAmt=0;
							rowdata.currentAmt = sowRowData.currentSowQty * sowRowData.approvedRates;
							rowdata.cumulativeAmt =sowRowData.prevApprovedAmt + sowRowData.pendingOrderAmt+ sowRowData.currentAmt;

					});
					}
				}
				sowPopupService.openExistingSowPopup = function(projId) {
					var deferred = $q.defer();
					var returnCostPopObj = {};
					var createExistingSowPoup = ngDialog.open({
						template: 'views/changeorders/cosoe/existingsowlistpopup.html',
						className: 'ngdialog-theme-plain ng-dialogueCustom1',
						closeByDocument: false,
						scope: $rootScope,
						showClose: false,
						controller: ['$scope','$rootScope', function ($scope,$rootScope) {
							console.log("controller openExistingSowPopup",projId);
							$scope.existingSowData = [];
							$scope.projectId = projId;
							console.log($scope.projectId);
							$scope.getExistingSOWDetails = function() {
								var sowReq = {
									"projId" : $scope.projectId,
									"status" : "1",
									"loggedInUser" : $rootScope.account.userId
								};
								console.log(sowReq);		
								ProjSOWService.getProjSOWDetails(sowReq).then(function(data) {
									$scope.activeFlag=1;
									console.log(data);	
									let dataSOWItems = [];
									for( var i=0;i<data.projSOWItemTOs.length;i++ )
									{
										let result = validateTree(data.projSOWItemTOs[i]);
										if( result != null )
										{
											dataSOWItems.push(result);
										}
									}
									$scope.existingSowData = populateSowData(dataSOWItems, 0, []);
									console.log($scope.existingSowData);
								}, function(error) {
									GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
								});
							}
							$scope.selectSowDetails = function(sowItemData) {
								returnCostPopObj = {
									"selectedCostItem" : sowItemData
								};
								console.log(returnCostPopObj);
								deferred.resolve(returnCostPopObj);
								$scope.closeThisDialog();
							};
							$scope.itemClick = function (item, expand) {
								TreeService.treeItemClick(item, expand, 'childSOWItemTOs');
							};
							function validateTree(input)
							{
								var treedata = null;
								var inpudata = input;
								//console.log(input);
								for(var i=0;i<input.childSOWItemTOs.length;i++)
								{
									if( input.childSOWItemTOs[i].childSOWItemTOs.length > 0 )
									{
										treedata=inpudata;
									}
								}
								//console.log(treedata);
								return treedata;
							}
							function populateSowData(data, level, sowTOs) {
								return TreeService.populateTreeData(data, level, sowTOs, 'code', 'childSOWItemTOs');	
							}
						}]
					});			
					return deferred.promise;
				}
				
				// ---------------------------saveScope Of Work------------------------------------
				$scope.saveScopeOfWorks=function(){
				var coProjSOWTOs=$scope.coHeadContractRows;
					if(coProjSOWTOs.length <= 0){
					GenericAlertService.alertMessage("Please select project details to save the record", 'Warning');
					return;
					}
					
				var co_save={
					"changeOrderTOs":[{"id":$scope.coProjId}],
					"coProjSOWTOs": coProjSOWTOs
				}
				console.log(co_save)
				ChangeOrdersService.saveCoScopeOfWorks(co_save).then(function(data) {
					console.log(data)
					
						var succMsg = GenericAlertService.alertMessageModal('Change Order Details saved successfully', "Info");
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Change Order details is/are failed to save', "Error");
				});
				}
				
			  //-----------Delete Scope of Work rowRecord------------------
				$scope.deleteSowRows = function() {
				var deleteSowDtlIds = [];
				var tempInternalRequest = [];
				var flag = false;
				angular.forEach($scope.coHeadContractRows, function(value, key) {
					console.log(value)
					if (!value.coSowSelect) {
						tempInternalRequest.push(value);
					} else {
						if (value.coSowSelect) {
							deleteSowDtlIds.push(value.id);
						}
						flag = true;
					}
				});
				if (!flag) {
					GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

				}
				$scope.coHeadContractRows = tempInternalRequest;
               }
				
				//-----------------------------------------------------------------
				/*$scope.addMaterialRows = function() {
					$scope.coMaterialRows.push(angular.copy($scope.coMaterialRowData));
				}
				
				$scope.displayCurrent = function() {
					console.log("displayCurrent function");
				}
				$scope.materialOpenPopup = function(projId, materialClassTOs, exitingSchMaterilMap,coMaterialRow) {
					console.log(projId);
					console.log(materialClassTOs);
					console.log(exitingSchMaterilMap);
					var deferred = $q.defer();
					var projMaterialPopup = ngDialog.open({
						template : '/views/changeorders/centralmaterial.html',
						closeByDocument : false,
						className : 'ngdialog-theme-plain ng-dialogueCustom4',
						showClose : false,
						controller : [ '$scope', function($scope) {
							var selectedMeterials = [];
							$scope.exitingSchMaterilMap = exitingSchMaterilMap;
							$scope.materialClassTOs = materialClassTOs;
							$scope.itemId = 1;
							$scope.isExpand = true;
							console.log(coMaterialRow);
							$scope.itemClick = function(itemId, expand) {
								$scope.itemId = itemId;
								$scope.isExpand = !expand;
							},
							$scope.selectedMaterialItem = function(selectedCentralMaterialData) {	
								console.log(selectedCentralMaterialData);												
								coMaterialRow.materialClassificationId = selectedCentralMaterialData.code;
								coMaterialRow.materialDescription = selectedCentralMaterialData.name;
								$scope.closeThisDialog();
							}
						} ]				
					});
					return deferred.promise;
				}*/
			//	$scope.changeItemType = function() {
					//console.log($scope.coSowItemType);
				//}
				$scope.submitForApproval = function(requestType) {
					console.log(requestType);
					console.log($scope.coSearchReq);
					
					let co_request = {
						"projId" : $scope.coSearchReq.projId,
						"id" : $scope.coSearchReq.coId,
						"requestType" : requestType
					}
					console.log(co_request);
					let isApprovalType = false;
					//$scope.submitApprovalPopup(requestType,$scope.coSearchReq.projId);
					var submitApprPopup = $scope.submitApprovalPopup(requestType,$scope.coSearchReq,isApprovalType);
					submitApprPopup.then(function(data){
						console.log(data);
						if( requestType == "INTERNAL" )
						{
							$scope.coSearchReq.internalApproverUser = {"id":data.approverTo.id, "name":data.approverTo.name};							
						}
						console.log($scope.coSearchReq);
					});												
				},
				$scope.coApproveNothing = function() {
					let requestType = 'INTERNAL';
					let isApprovalType = true;
					console.log($scope.coSearchReq);
					var submitApprPopup = $scope.submitApprovalPopup(requestType,$scope.coSearchReq,isApprovalType);
					submitApprPopup.then(function(data){
						console.log(data);						
						console.log($scope.coSearchReq);
						var submitApprPopup = $scope.submitApprovalPopup(requestType,$scope.coSearchReq,isApprovalType);
						submitApprPopup.then(function(data){
							console.log(data);
							if( requestType == "INTERNAL" )
							{
								$scope.coSearchReq.internalApproverUser = {"id":data.approverTo.id, "name":data.approverTo.name};							
							}
							console.log($scope.coSearchReq);
						});
					});	
				},
				$scope.coApprove = function() {
					let requestType = ( coSearchData.isExternalApprovalRequired && coSearchData.approvalStatus.indexOf("SUBMITTED_FOR_EXTERNAL_APPROVAL") >= 0 ) ? 'EXTERNAL' : 'INTERNAL';					
					let isApprovalType = true;
					console.log(coSearchData);
					var submitApprPopup = $scope.submitApprovalPopup(requestType,$scope.coSearchReq,isApprovalType);
					submitApprPopup.then(function(data){
						console.log(data);						
						console.log($scope.coSearchReq);						
					});	
				},
				$scope.submitApprovalPopup = function(requestType,coSearchData,isApprovalType) {
					var deferred = $q.defer();
					var approverUserPopup = ngDialog.open({
						template : 'views/changeorders/requestapprovalpopup.html',
						closeByDocument : false,
						className : 'ngdialog-theme-plain ng-dialogueCustom4',
						showClose : false,
						controller : ['$scope',	function($scope) {
							$scope.approverUserList = [];
							$scope.requestType = requestType;
							$scope.apprUserLabelkeyTO = { "name" : null,"name" : null, "email" : null };
							$scope.popupTitle = ( $scope.requestType == "INTERNAL" ) ? "Submit For Internal Approval" : "Submit For External Approval";
							$scope.coData = coSearchData;
							$scope.coData.comments = null;
							$scope.coData.isExtApprovalRequired = false;
							console.log($scope.coData);
							$scope.displayApprovalList = ( requestType == 'INTERNAL' || requestType == 'EXTERNAL' ) ? !isApprovalType ? true : false : false;
							$scope.displayExtApprovalRequired = ( requestType == 'INTERNAL' && !isApprovalType ) ? true : false;
							$scope.getApproverUsersList = function() {
								let req = {
									"projId" : $scope.coData.projId,
									"status" : 1
								}
								ApproverListUserFactory.getProjUsersByIdOnly(req).then(function(data){
									console.log(data);
									$scope.apprUserLabelkeyTO = data.approverTo;
									deferred.resolve(data);
									service.closeThisDialog();
								});
							}
							$scope.submit = function() {
								console.log($scope.coData);
								console.log($scope.apprUserLabelkeyTO);
								$scope.closeThisDialog(0);
								let co_search = {
									"changeOrderTOs" : [{
										"id" : $scope.coData.coId,
										"internalApprovalLabelKeyTO" : null,
										"isExternalApprovalRequired" : null,
										"comments" : $scope.coData.comments,
										"reqApprovalType" : null
									}]
								};
								if( requestType.indexOf('INTERNAL') > -1 )
								{
									if( !isApprovalType )
									{
										co_search.changeOrderTOs[0].internalApproverLabelKeyTO = $scope.apprUserLabelkeyTO;
										co_search.changeOrderTOs[0].reqApprovalType = "SUBMITTED_FOR_INTERNAL_APPROVAL";
										co_search.changeOrderTOs[0].isExternalApprovalRequired = ( $scope.coData.isExtApprovalRequired ) ? 1 : 0;
									}									
									else
									{
										co_search.changeOrderTOs[0].reqApprovalType = "INTERNAL_APPROVE";
									}
								}
								else
								{
									if( !isApprovalType )
									{
										co_search.changeOrderTOs[0].externalApproverLabelKeyTO = $scope.apprUserLabelkeyTO;
										co_search.changeOrderTOs[0].reqApprovalType = "SUBMITTED_FOR_EXTERNAL_APPROVAL";
									}									
									else
									{
										co_search.changeOrderTOs[0].reqApprovalType = "EXTERNAL_APPROVE";
									}
								}								
								console.log(co_search);
								ChangeOrdersService.updateApproverDetails(co_search).then(function(data) {
									console.log(data);
									GenericAlertService.alertMessage("Your request sent to the approver", 'Info');
								}, function(error) {
									GenericAlertService.alertMessage("Error occured while updating the approver details", 'Error');
								});
							}
						}]
					});
					return deferred.promise;
				}		
				function switchTab(tab) {
					console.log(tab)
					console.log("switchTab function");
					currentActiveTab = tab;
					$scope.currentTabTitle = tab.title;
					$scope.currentTab1 = tab.url;
					storeTemporaryData(tab);
				}
			
				function storeTemporaryData(tab) {
					temporaryStoredData = angular.copy($scope.coDetailTabs[tab.nameOfVariable]);
				}
				function difference(object, base) {
					function changes(object, base) {
						return _.transform(object, function (result, value, key) {
							if (key !== '$$hashKey') {
								if (!_.isEqualWith(value, base[key], customizer)) {
									var diff = (_.isObject(value) && _.isObject(base[key])) ? changes(value, base[key]) : value;
									if (!_.isEmpty(diff)) {
										result[key] = diff;
									}
								}
							}
			
						});
					}
					return changes(object, base);
				}
				if($scope.coDetailTabs) {
					$scope.onClickTab1($scope.coDetailTabs[0]);
			    }			
			}]
		});
		return deferred.promise;
	},	
	//This is to display the manpower classification list in case of existing item is selected under the manpower tab
	service.getProjBudgetPopup = function(projBudgetData,type,itemType) {
		var deferred = $q.defer();
		//projManpowerPopupService.openPopup(data.projManpowerTOs);
		var projManpowerPopup = ngDialog.open({
			template: 'views/changeorders/tabs/projmanpowerpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$rootScope','$scope', function ($rootScope,$scope) {
				$scope.projManpowerData = [];
				$scope.projPlantData = [];
				$scope.selectedProjManpowerData = [];
				$scope.coProjType = type;
				$scope.coItemType = itemType;
				
				console.log(type);
				console.log($scope.coItemType);
				var proj_manpower_req = {
					"status": 1,
					"projId": $scope.selectedProject.projId
				};
				console.log(projBudgetData);
				console.log(proj_manpower_req);
				console.log($scope.coProjType);
				if( type.indexOf('MANPOWER') > -1 )
				{
					ProjectBudgetService.getProjManpowers(proj_manpower_req).then(function (data) {
						$scope.projManpowerData = data.projManpowerTOs;
						console.log($scope.projManpowerData);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
					});
				}
				else if( type.indexOf('MATERIAL') > -1 )
				{
					console.log(itemType.indexOf('NEW'));
					if( itemType.indexOf('NEW') > -1 )
					{
						var material_req = {
							"status" : 1
						}
						$scope.exitingSchMaterilMap = [];
						//$scope.materialClassTOs = materialClassTOs;
						$scope.itemId = 1;
						$scope.isExpand = true;
						$scope.itemClick = function(itemId, expand) {
							$scope.itemId = itemId;
							$scope.isExpand = !expand;
						};
						MaterialClassService.getMaterialGroups(material_req).then(function(data) {
							console.log(data);
							$scope.materialClassTOs = data.materialClassTOs;
							/*var popupData = service.openPopup(projId, data.materialClassTOs, exitingSchMaterilMap, preContractObj);
							popupData.then(function(data) {
								deferred.resolve(data);
							});*/
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting Material Groups", 'Error');
						});	
					}
					else
					{
						ProjectBudgetService.getProjectMaterials(proj_manpower_req).then(function (data) {
							console.log(data);
							let materialData = populateMaterialClassData(data.projectMaterialDtlTOs, 0, []);
							console.log(materialData);
							materialData.map((treeItem) => {
								$scope.materialItemClick(treeItem, false);
							});
							$scope.projectMaterialDtlTOs = materialData;
							console.log($scope.projectMaterialDtlTOs);
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting Material Details", "Error");
						});
					}											
				}
				else
				{
					ProjectBudgetService.getProjectPlants(proj_manpower_req).then(function (data) {
						console.log(data);
						$scope.projPlantData = data.projectPlantsDtlTOs;
						console.log($scope.projPlantData);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Details", "Error");
					});
				}
				/*$scope.selectedProjManpowerData = [];
				$scope.projManpowerSelection = function(selectedManpower) {
					$scope.selectedProjManpowerData.push(selectedManpower);
				}*/
				$scope.materialItemClick = function (item, expand) {
					//console.log(item);
					TreeService.treeItemClick(item, expand, 'projectMaterialDtlTOs');
				}
				$scope.materialRowSelect = function (meterialTO) {
					deferred.resolve(meterialTO);
					$scope.closeThisDialog();
				}
				$scope.projManpowerSelection = function(selectedManpower) {
					deferred.resolve(selectedManpower);
					$scope.closeThisDialog();
				},
				$scope.projPlantClassificationSelection = function(selectedPlant) {
					deferred.resolve(selectedPlant);
					$scope.closeThisDialog();
				},
				$scope.centralMaterialItemSelection = function(selectedCentralMaterialData) {
					console.log(selectedCentralMaterialData);
					deferred.resolve(selectedCentralMaterialData);
					$scope.closeThisDialog();
				}
				function populateMaterialClassData(data, level, materialClassTOs, isChild, parent) {
					return TreeService.populateTreeData(data, level, materialClassTOs, 'id', 'projectMaterialDtlTOs',isChild, parent)
				}
			}]
		});	
		return deferred.promise;
	}
	return service;
}]).filter('procurementMaterialClassFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand, exitingSchMaterilMap) {
		angular.forEach(obj, function(o, key) {
			if (o.childMaterialItemTOs && o.childMaterialItemTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				if (o.id == itemId) {
					o.expand = isExpand;
				}
				if (o.expand = isExpand) {
					recursive(o.childMaterialItemTOs, newObj, o.level + 1, itemId, isExpand, exitingSchMaterilMap);
				}
			} else {
				o.level = level;
				if (o.item && exitingSchMaterilMap[o.id] == null) {
					o.leaf = true;
					newObj.push(o);
				} else {
					obj.splice(obj.indexOf(o), 1);
				}
				return false;
			}
		});
	}

	return function(obj, itemId, isExpand, exitingSchMaterilMap) {
		var newObj = [];
		recursive(obj, newObj, 0, itemId, isExpand, exitingSchMaterilMap);
		return newObj;
	}
});;

