'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("projcostcode", {
		url : '/projcostcode',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/costcode/projcostcode.html',
				controller : 'ProjCostController'
			}
		}
	})
}]).controller("ProjCostController", ["$rootScope", "$scope", "$state", "ngDialog", "blockUI", "$q", "ProjCostCodeService", "ProjEmpClassService", "GenericAlertService", "EpsProjectSelectFactory", "TreeService", "ProjectStatusService", function($rootScope, $scope, $state, ngDialog,	blockUI, $q,
	 ProjCostCodeService, ProjEmpClassService, GenericAlertService, EpsProjectSelectFactory, TreeService, ProjectStatusService) {
	var deactivateData = [];
	var deferred = $q.defer();
	$scope.tableData = [];
	$scope.searchProject = {};
	$scope.activeFlag=0;
	
	$scope.costCodeItemStatus = null;
	$scope.disableCreateBtn = false;
	$scope.displayEditBtn = true;
	
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection	.then(function(data) {
			$scope.tableData = [];
							$scope.searchProject = data.searchProject;
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
						});
	}, $scope.getCostDetails = function(projId) {
		var costReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId,
			"displayActiveItems": 0
		};
		if (costReq.projId == null || costReq.status == undefined) {
			GenericAlertService.alertMessage("Please select project/EPS", 'Warning');
			return;
		}
		ProjCostCodeService.getCostDetails(costReq).then(function(data) {
			$scope.activeFlag=1;
			console.log(data.projCostItemTOs);
			$scope.tableData = populateCostCodeData(data.projCostItemTOs, 0, []);
			console.log($scope.tableData);
			updateCostCodeItemStatus($scope.tableData);
			console.log($scope.costCodeItemStatus);
			if( $scope.costCodeItemStatus == "APPROVED" )
			{
				$scope.disableCreateBtn = true;
				$scope.displayEditBtn = false;
			}					
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting CostCodes", 'Error');
		});
	};

	function updateCostCodeItemStatus(costCodeData) {
		for( var i=0;i<costCodeData.length;i++ )
		{
			if( costCodeData[i].item != null && costCodeData[i].item == true && $scope.costCodeItemStatus == null )
			{
				$scope.costCodeItemStatus = costCodeData[i].costCodeItemStatus;
			}
		}
		console.log("updateCostCodeItemStatus:",$scope.costCodeItemStatus);
	}
	
	function populateCostCodeData(data, level, costTOs) {
		return TreeService.populateTreeData(data, level, costTOs, 'code', 'projCostCodeItemTOs');
	}

	$scope.resetProjCostData = function() {
		$scope.tableData = [];
		$scope.activeFlag=0;
		$scope.searchProject = {};
	}, $scope.rowSelect = function(rowData) {
		if (rowData.select) {
			deactivateData.push(rowData.id);
		} else {
			deactivateData.splice(deactivateData.indexOf(rowData.id), 1);
		}
	}
	var projCostCodePopUp;
	var projCostEditPopUpService = {};
	$scope.addCost = function(actionType, itemData, projId) {
		ProjCostCodeService.getCalendarSpecialWorkingNonworkingDays(projId).then(function(data) {
			projCostCodePopUp = projCostEditPopUpService.addCostData(actionType, itemData, projId, $scope.tableData, {weeklyHolidays: data.weeklyHolidays, specialWorkingDays: data.specialWorkingDays, specialNonworkingDays: data.specialNonworkingDays});
			projCostCodePopUp.then(function(data) {
				$scope.tableData = populateCostCodeData( data.projCostItemTOs,0,[]);
			}, function(error) {
				GenericAlertService.alertMessage("Cost(s) is/are failed to save ", "Error");
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting calendar", 'Error');
		});
	}
	projCostEditPopUpService.addCostData = function(actionType, itemData, projId, codeList, calendarInfo) {
		var deferred = $q.defer();
		if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {

			projCostCodePopUp = ngDialog

			.open({
				template : 'views/projectlib/costcode/projcosteditpopup.html',
				closeByDocument : false,
				scope : $scope,
				showClose : false,
				className:'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
				controller : [ '$scope', function($scope) {
					$scope.pCode = null;
					$scope.action = actionType;
					$scope.editCostData = [];
					$scope.costCodeData = [];
					$scope.projCostId = null;
					var costData = [];
					$scope.itemId1 = 1;
					$scope.isExpand1 = false;
					$scope.isPrimaveraIntegrationEnabled = 'Yes';
					ProjectStatusService.getProjGenerals({"projId": projId, "status": 1}).then(function(data){
						$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
					}, function (error) {
						cosole.log(error)
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
					$scope.projCostPopupItemClick = function(item, expand) {
						TreeService.dynamicTreeItemClick($scope.editCostData, item, expand);
					};
					if (itemData) {
						$scope.pCode = itemData.name;
						$scope.projCostId = itemData.id;
					}
					$scope.getProjCostItemsOnLoad = function() {
						var costReq = {
							"status" : "1",
							"projCostId" : $scope.projCostId,
							"projId" : projId
						};
						ProjCostCodeService.projCostItemsOnLoad(costReq).then(function(data) {
							$scope.costCodeData = data.costCodeTOs;
							if ($scope.projCostId != null) {
								$scope.editCostData = data.projCostItemTOs;
							}
							costData = data.projCostItemTOs;
							if(costData && costData.length)
								$scope.editCostData = TreeService.populateDynamicTreeData(costData, 0, [], 
									'code', 'projCostCodeItemTOs');
						});
					}, $scope.updateCostCode = function(data, tab) {
						tab.costId = data.id;
					}
					if (actionType === "Add") {
						$scope.editCostData.push({
							"select" : false,
							"projId" : projId,
							"parentId" : null,
							"status" : 1,
							"deleteFlag" : false,
							"item" : false,
							"code" : '',
							"name" : '',
							"projCostCodeItemTOs" : []
						});
						
					} 
					$scope.editCostData = TreeService.populateDynamicTreeData($scope.editCostData, 0, [], 'code',
					 'projCostCodeItemTOs');
						
					$scope.addCostSubGroup = function (tabData, projId, indexValue) {
						// check for input fileds validations
						let isValid = true;
						for (let i=0; i<$scope.editCostData.length; i++)
							isValid = isValid && validateEntry($scope.editCostData[i], $scope.editCostData, codeList, calendarInfo);
						if (isValid) {
							const obj = {
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"parentIndex": indexValue,
								"status": 1,
								"deleteFlag": false,
								"item": false,
								"code": '',
								"name": '',
								"projCostCodeItemTOs": [],
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							};
							$scope.editCostData = TreeService.addItemToTree($scope.editCostData, tabData,
								obj, indexValue, 'projCostCodeItemTOs');
						}
					},
					$scope.addCostItem = function (tabData, projId, indexValue) {
						// check for input fileds validations
						let isValid = true;
						for (let i=0; i<$scope.editCostData.length; i++)
							isValid = isValid && validateEntry($scope.editCostData[i], $scope.editCostData, codeList, calendarInfo);
						if (isValid) {
							const obj = {
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"parentIndex": indexValue,
								"status": 1,
								"code": '',
								"name": '',
								"item": true,
								"costId": '',
								"startDate": '',
								"finishDate": '',
								"comments": '',
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							};
							$scope.editCostData = TreeService.addItemToTree($scope.editCostData, tabData,
								obj, indexValue, 'projCostCodeItemTOs');
						}
					},
					$scope.deleteCostGroup = function(index) {
						TreeService.deleteDynamicTreeItem($scope.editCostData,index);
					},
					$scope.saveProjCostItems = function(tab,projCostForm) {
						let isValid = true;
						if(calendarInfo.weeklyHolidays == null){
						GenericAlertService.alertMessage("No working days created", "Info");
						}
						for (let i=0; i<$scope.editCostData.length; i++) {
							let result = validateEntry($scope.editCostData[i], $scope.editCostData, codeList, calendarInfo);
							isValid = isValid && result;
						}alert("1");
						if (isValid) {
							const data = TreeService.extractTreeDataForSaving($scope.editCostData, 'projCostCodeItemTOs');
							var req = {
								"projCostItemTOs" : data,
								"projId" : $scope.searchProject.projId
							}
							blockUI.start();
							ProjCostCodeService.saveProjCostItems(req).then(function(data) {
								blockUI.stop();
								var results = data.projCostItemTOs;
								// var succMsg = GenericAlertService.alertMessageModal('Cost Code(s) is/are ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal('Cost Code(s) saved successfully',"Info");
								succMsg.then(function(data) {
								$scope.getCostDetails();
									$scope.closeThisDialog();
									var returnPopObj = {
										"projCostItemTOs" : results
									}
									deferred.resolve(returnPopObj);
								})
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage('Cost Code(s)  is/are failed to save', "Error");
							});
						} else {
							GenericAlertService.alertMessage("Please Fill all mandatory fields data", "Info");
						}
					};
					function validateEntry(entry, currentCostList, projectCostList, calendarInfo) {
						let isValid = true;
						delete entry.codeErrorMessage;
						delete entry.nameErrorMessage;
						delete entry.uomErrorMessage;
						delete entry.startDateErrorMessage;
						delete entry.finishDateErrorMessage;
						if (entry.code == null || entry.code == "") {
							entry.codeErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if (entry.name == null || entry.name == "") {
							entry.nameErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if (entry.item) {
							if (entry.costCodeTO == null || entry.costCodeTO == '') {
								entry.uomErrorMessage = 'This Field is Mandatory';
								isValid = isValid && false;
							}
							if ($scope.isPrimaveraIntegrationEnabled != 'Yes') {
								if (entry.startDate == null || entry.startDate == '') {
									entry.startDateErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
								if (entry.finishDate == null || entry.finishDate == '') {
									entry.finishDateErrorMessage = 'This Field is Mandatory';
									isValid = isValid && false;
								}
							}
						}
						if (entry.code != null) {
							if (entry.id) {
				    			if (projectCostList.find(e => e.code.toLowerCase().replace(/\s/g, "") == entry.code.toLowerCase().replace(/\s/g, "") && e.id != entry.id) != null) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    		} else {
				    			if (projectCostList.find(e => e.code.toLowerCase().replace(/\s/g, "") == entry.code.toLowerCase().replace(/\s/g, "")) != null) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    			let count = 0;
				    			for (let i=0; i<currentCostList.length; i++)
				    				if (currentCostList[i].code.toLowerCase() == entry.code.toLowerCase()) count++;
				    			if (count > 1) {
				    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
				    				isValid = isValid && false;
				    			}
				    		}
						}
						if (!(entry.startDate == null || entry.startDate == '')) {
							if (!isWorkingDay(new Date(entry.startDate), calendarInfo.weeklyHolidays, calendarInfo.specialWorkingDays, calendarInfo.specialNonworkingDays)) {
								entry.startDateErrorMessage = 'This is a holiday';
								isValid = isValid && false;
							}
						}
						if (!(entry.finishDate == null || entry.finishDate == '')) {
							if (!isWorkingDay(new Date(entry.finishDate), calendarInfo.weeklyHolidays, calendarInfo.specialWorkingDays, calendarInfo.specialNonworkingDays)) {
								entry.finishDateErrorMessage = 'This is a holiday';
								isValid = isValid && false;
							}
						}
						return isValid;						
					};
					function isWorkingDay(date, projectCalendar, specialWorkingDays, specialNonWorkingDays) {
				    	let isWorkingDay = false;
				    	switch(date.getDay()){
				    	case 0: isWorkingDay = !projectCalendar.sunday; break;
				    	case 1: isWorkingDay = !projectCalendar.monday; break;
				    	case 2: isWorkingDay = !projectCalendar.tuesday; break;
				    	case 3: isWorkingDay = !projectCalendar.wednesday; break;
				    	case 4: isWorkingDay = !projectCalendar.thursday; break;
				    	case 5: isWorkingDay = !projectCalendar.friday; break;
				    	case 6: isWorkingDay = !projectCalendar.saturday; break;
				    	}
				    	if (isWorkingDay) {
				    		for (let j = 0; j < specialNonWorkingDays.length; j++) {
				                if (new Date(specialNonWorkingDays[j]).getTime() == date.getTime()) {
				                	isWorkingDay = false;
				                    break;
				                }
				            }
				    	} else {
				    		for (let j = 0; j < specialWorkingDays.length; j++) {
				                if (new Date(specialWorkingDays[j]).getTime() == date.getTime()) {
				                	isWorkingDay = true;
				                    break;
				                }
				            }
				    	}
				    	return isWorkingDay;
				    }
				}]
			});
			return deferred.promise;
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}

	}, $scope.deactivateCostDetails = function() {
		if (deactivateData == undefined || deactivateData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Cost Item to Deactivate", "Warning");
			return;
		}
		var costDeactivateReq = {
			"projCostItemIds" : deactivateData,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to deactivate the cost code?', 'Warning','YES','NO').then(function() {
		ProjCostCodeService.deactivateCostDetails(costDeactivateReq).then(function(data) {
			GenericAlertService.alertMessage("Cost Code(s) deactivated successfully", "Info");
			deactivateData = [];
			$scope.tableData = data.projCostItemTOs;
			$scope.getCostDetails();
		}, function(error) {
			GenericAlertService.alertMessage("Cost(s) is/are failed to Deactivate", "Error");
		});
	},function(data){
		deactivateData = [];
			$scope.getCostDetails();
	})
	}
	$scope.costItemClick = function (item, expand) {
		TreeService.treeItemClick(item, expand, 'projCostCodeItemTOs');
	};

	$scope.show = function(comments) {
		ngDialog.open({
			template : 'views/projectlib/costcode/viewpopup.html',
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
			template: 'views/help&tutorials/projectshelp/projcostcodehelp.html',
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