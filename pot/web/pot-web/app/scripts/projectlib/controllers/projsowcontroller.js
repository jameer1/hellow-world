'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("projsow", {
		url : '/projsow',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/sow/projsow.html',
				controller : 'ProjSOWController'
			}
		}
	})
}]).controller("ProjSOWController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "ProjSOWService", "ProjSORFactory", "ProjTanFactory", "ProjCostFactory", "EpsProjectSelectFactory", "GenericAlertService", "TreeService", "ProjectStatusService", "ProjCostCodeService", function($rootScope, $scope, $q, $state, ngDialog,blockUI, ProjSOWService, 
	ProjSORFactory, ProjTanFactory, ProjCostFactory, EpsProjectSelectFactory, GenericAlertService, TreeService, ProjectStatusService, ProjCostCodeService) {
	$scope.deletecodes = [];
	var editSOWData = [];
	$scope.SOWData = [];
	$scope.searchProject = {};
	var deleteSowData=[];
	var sowRowData=[];
	$scope.activeFlag=0;
	$scope.totalActualMap=[];
	$scope.getSOWItems = function(projId) {
		var sowReq = {
			"projId" : $scope.searchProject.projId,
			"status" : "1",
			"loggedInUser" : $rootScope.account.userId
		};
		if (sowReq.projId == null || sowReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjSOWService.getProjSOWDetails(sowReq).then(function(data) {
			$scope.activeFlag=1;
			let dataSOWItems = [];
			for( var i=0;i<data.projSOWItemTOs.length;i++ )
			{
				let result = validateTree(data.projSOWItemTOs[i]);
				if( result != null )
				{
					dataSOWItems.push(result);
				}
			}
			//console.log(dataSOWItems);
			//validateTree(data.projSOWItemTOs);
			$scope.SOWData = populateSowData(dataSOWItems, 0, []);			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	};
	function validateTree(input)
	{
		var treedata = null;
		var inpudata = input;
		//console.log(input);
		for(var i=0;i<input.childSOWItemTOs.length;i++)
		{
			if( input.childSOWItemTOs[i].childSOWItemTOs.length >= 0 )
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
	
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection	.then(function(data) {
			$scope.SOWData = [];
							$scope.searchProject = data.searchProject;
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
						});
	},

	$scope.resetSOWData = function() {
		$scope.activeFlag=0;
		$scope.searchProject.projName = null;
		$scope.searchProject.parentName = null;
		$scope.SOWData = [];
		$scope.searchProject = [];
		$scope.projId = null;
		$scope.editSOWData = [];
	}, $scope.rowSelect = function(sowRowData) {
		if (sowRowData.select) {
			deleteSowData.push(sowRowData.id);
		} else {
			deleteSowData.splice(deleteSowData.indexOf(sowRowData.id), 1);
		}

	}
	var sowPopup;
	var addSOWservice = {};
	var deferred = $q.defer();
	$scope.editSOWDetails = function(itemData, projId) {
		ProjCostCodeService.getCalendarSpecialWorkingNonworkingDays(projId).then(function(data) {
			sowPopup = addSOWservice.addSOWDetails(itemData, projId, {weeklyHolidays: data.weeklyHolidays, specialWorkingDays: data.specialWorkingDays, specialNonworkingDays: data.specialNonworkingDays});
			sowPopup.then(function(data) {
				$scope.SOWData = populateSowData(data.projSOWItemTOs,0,[]);
			}, function(error) {
				GenericAlertService.alertMessage("SOW(s) is/are failed to save", "Error");
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting calendar", 'Error');
		});
	},
	addSOWservice.addSOWDetails = function(itemData, projId, calendarInfo) {
		var deferred = $q.defer();
		sowPopup = ngDialog.open({
			template : 'views/projectlib/sow/projsowpopuptab.html',
			scope : $scope,
			className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.projId = projId;
				$scope.editSOWData = [];
				$scope.isPrimaveraIntegrationEnabled = 'Yes';
				ProjectStatusService.getProjGenerals({"projId": $scope.projId, "status": 1}).then(function(data){
					$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
				}, function (error) {
					cosole.log(error)
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
				
				$scope.projSowPopupItemClick = function(item, expand) {
					TreeService.dynamicTreeItemClick ($scope.editSOWData, item, expand);
				},
				$scope.getProjSOWItemsById = function() {
					var sowEditReq = {
						"status" : "1",
						"sowId" : itemData.id,
						"projId" : $scope.projId
					};
					ProjSOWService.getProjSOWItemsById(sowEditReq).then(function(data) {
						console.log(data)
						$scope.editSOWData = TreeService.populateDynamicTreeData(data.projSOWItemTOs, 0, [], 'code',
							'childSOWItemTOs');
						var sowReq = {
							"projId" : $scope.searchProject.projId,
							"status" : "1"
						};
						ProjSOWService.getSOWActualRevisedQuantities(sowReq).then(function(data) {
							$scope.totalActualMap = data.actualRevisedMap;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while actual and revised quantities *", 'Error');
						});
					});
				},
				$scope.sorDetails = function(tab) {
					var projSorPopup = ProjSORFactory.sorPopupDetails($scope.projId);
					projSorPopup.then(function(data) {
						tab.sorId = data.selectedSORItem.id;
						if (tab.projSORItemTO == null) {
							tab.projSORItemTO = {
								"id" : null,
								"code" : null,
								"name" : null
							};
						}
						tab.projSORItemTO.code = data.selectedSORItem.code;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting SOR details", 'Error');
					});
				},
				$scope.tangibleDetails = function(tab) {
					var projTanPopup = ProjTanFactory.tanPopupDetails($scope.projId);
					projTanPopup.then(function(data) {
						tab.tangibleItemId = data.selectedTangibleItem.id;
						if (tab.tangibleClassTO == null) {
							tab.tangibleClassTO = {
								"id" : null,
								"code" : null,
								"name" : null
							};
						}
						tab.tangibleClassTO.code = data.selectedTangibleItem.code;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Tangible details", 'Error');
					});
				},
				$scope.costDetails = function(tab) {
					var projCostPopup = ProjCostFactory.costPopupDetails($scope.projId);
					projCostPopup.then(function(data) {
						tab.projCostId = data.selectedCostItem.id;
						if (tab.projCostItemTO == null) {
							tab.projCostItemTO = {
								"id" : null,
								"code" : null,
								"name" : null
							};
						}
						tab.projCostItemTO.code = data.selectedCostItem.code;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting cost details", 'Error');
					});
				}, 
				$scope.saveSOWDetails = function() {
					let isValid = true;
					for (let i=0; i<$scope.editSOWData.length; i++) {
						let result = validateEntry($scope.editSOWData[i], calendarInfo);
						isValid = isValid && result;
					}
					if (isValid) {
						const data = TreeService.extractTreeDataForSaving($scope.editSOWData, 'childSOWItemTOs');
						var sowSaveReq = {
							"projSOWItemTOs" : data,
							"projId" : projId
						};
						blockUI.start();
						ProjSOWService.saveSOWItems(sowSaveReq).then(function(data) {
							blockUI.stop();
							if (data.status != null && data.status !== undefined && data.status === 'Info') {
								var projSOWTOs = data.projSOWItemTOs;
								var succMsg = GenericAlertService.alertMessageModal('Project SOW(s) saved successfully',"Info");
								succMsg.then(function(data) {
									var returnPopObj = {
										"projSOWItemTOs" : projSOWTOs
									};
									$scope.closeThisDialog();
									deferred.resolve(returnPopObj);
								}, function(error) {
									blockUI.stop();
									GenericAlertService.alertMessage("SOW(s) is/are Failed to Save", "Error");
								});
							}
						});
					}
				};
				function validateEntry(entry, calendarInfo) {
					let isValid = true;
					let isBlankEntry = false;
					delete entry.sorErrorMessage;
					delete entry.codeErrorMessage;
					delete entry.startDateErrorMessage;
					delete entry.finishDateErrorMessage;
				//	delete entry.revErrorMessage;
					
					if (entry.item) {
						if (entry.tangibleClassTO == null && entry.projSORItemTO == null && entry.projCostItemTO == null  && (entry.startDate == null || entry.startDate == '') 
								&& (entry.finishDate == null || entry.finishDate == '' ))
							isBlankEntry = true;
						
						/*if(!isBlankEntry){
							entry.revErrorMessage = 'Negative values are not allowed';
							isValid = isValid && false;
						}*/
						if ((!isBlankEntry) && entry.projSORItemTO == null) {
							entry.sorErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if ((!isBlankEntry) && entry.projCostItemTO == null) {
							entry.codeErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if ((!isBlankEntry) && ($scope.isPrimaveraIntegrationEnabled == 'No') && entry.startDate == null || entry.startDate == '') {
							entry.startDateErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
						if ((!isBlankEntry) && ($scope.isPrimaveraIntegrationEnabled == 'No') && entry.finishDate == null || entry.finishDate == '') {
							entry.finishDateErrorMessage = 'This Field is Mandatory';
							isValid = isValid && false;
						}
					}
					if ((!isBlankEntry) && (!(entry.startDate == null || entry.startDate == ''))) {
						if (!isWorkingDay(new Date(entry.startDate), calendarInfo.weeklyHolidays, calendarInfo.specialWorkingDays, calendarInfo.specialNonworkingDays)) {
							entry.startDateErrorMessage = 'This is a holiday';
							isValid = isValid && false;
						}
					}
					if ((!isBlankEntry) && (!(entry.finishDate == null || entry.finishDate == ''))) {
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

	},

	$scope.deactivateSOWDetails = function() {
		if (deleteSowData == undefined || deleteSowData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one SOW to Deactivate", "Warning");
			return;
		}
		var sowDeactivateReq = {
			"projSOWItemIds" : deleteSowData,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
		ProjSOWService.deleteSOWItems(sowDeactivateReq).then(function(data) {
			GenericAlertService.alertMessageModal("Scope of Work(s) is/are Deactivated successfully", "Info");
			deleteSowData=[];
			$scope.getSOWItems();
		}, function(error) {
			GenericAlertService.alertMessage("SOW(s) is/are Failed to Deactivate", "Error");
	})
	},function(data){
		deleteSowData=[];
		$scope.getSOWItems();
		})
	}

	$scope.sowItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childSOWItemTOs');
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
	}
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
			template: 'views/help&tutorials/projectshelp/projsowhelp.html',
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
