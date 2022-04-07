'use strict';

app.factory('ChangeOrdersSowFactory', ["ngDialog", "$q", "$rootScope", "GenericAlertService", "ProjSOEService", "TreeService", "blockUI", "ProjSOWService", "ProjCostCodeService", "ProjSORFactory", "ProjTanFactory", "ProjCostFactory", "ProjectStatusService","ChangeOrdersService",  
	function (ngDialog, $q, $rootScope, GenericAlertService, ProjSOEService, TreeService, blockUI, ProjSOWService, ProjCostCodeService, ProjSORFactory, ProjTanFactory, ProjCostFactory, ProjectStatusService, ChangeOrdersService ) {

	var coCreatePopup;
	var service = {};
	var coInsertData = {};
	var projectSowData = [];
	/* Scope of Work Tab changes */
	service.openCreatePopup = function(projId) {
		var deferred = $q.defer();
		var createSoePoup = ngDialog.open({
			template: 'views/changeorders/cosoe/soelist.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("controller changeorderssowfactory",projId);
				$scope.SOEData1 = [];
				$scope.projectId = projId;
				$scope.getSOEDetails = function() {
					var soeReq = {
						"status" : 1,
						"projId" : projId,
						"loggedInUser" : $rootScope.account.userId
					};
					console.log(soeReq);
					if (soeReq.projId == null || soeReq.status == undefined) {
						GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
						return;
					}
					ProjSOEService.getSOEDetails(soeReq).then(function(data) {
						console.log(data);
						$scope.activeFlag=1;
						$scope.SOEData1 = populateSoeData(data.projSOEItemTOs, 0, []);
						if ($scope.SOEData1 <= 0) {
							GenericAlertService.alertMessage("SOE Details are not available for given search criteria", 'Warning');
						}			
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
					});
				};
				$scope.soeItemClick = function(item, expand) {
					TreeService.treeItemClick(item, expand, 'childSOEItemTOs');
				};
				var soepopup;
				var addSOEservice = {};
				$scope.editSOEDetails = function(actionType, itemData, projId) {
					/*if (deleteSOEData.length > 0 && actionType == 'Add') {
						GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
						return;
					}*/
					console.log(actionType+"->"+projId);
					console.log(itemData);
					soepopup = addSOEservice.addSOEDetails(actionType, itemData, projId, $scope.SOEData1);
					soepopup.then(function(data) {
						$scope.SOEData1 = populateSoeData(data.projSOEItemTOs, 0, []);
					}, function(error) {
						GenericAlertService.alertMessage(" SOE(s) is/are", "Error");
					});
				}
			
				addSOEservice.addSOEDetails = function(actionType, itemData, projId, soeList) {
					let project_id = projId;
					var deferred = $q.defer();
					if (projId !== undefined && projId != null) {
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
										"projId" : project_id,
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
										"projId" : project_id,
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
											"projId": project_id,
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
											"projId": project_id,
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
								$scope.saveCOSOEDetails = function() {
									let isValid = true;
									for (let i=0; i<$scope.editSOEData.length; i++) {
										let result = validateEntry($scope.editSOEData[i], $scope.editSOEData, soeList);
										isValid = isValid && result;
									}
									if (isValid) {
										const data = TreeService.extractTreeDataForSaving($scope.editSOEData, 'childSOEItemTOs');
										var soeSaveReq = {
											"projSOEItemTOs" : data,
											"projId" : project_id,
											"actionType" : actionType,
											"loggedInUser" : $rootScope.account.userId,
											"requestedFromModule" : "Change Order"
										};										
										console.log(soeSaveReq);
										
										//blockUI.start();
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
											}
										});
									} else {
										GenericAlertService.alertMessage("Please input valid data", "Info");
									}
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
							    			if (projectSoeList.find(e => e.code.toLowerCase() == entry.code.toLowerCase() && e.id != entry.id) != null) {
							    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
							    				isValid = isValid && false;
							    			}
							    		} else {
							    			if (projectSoeList.find(e => e.code.toLowerCase() == entry.code.toLowerCase()) != null) {
							    				entry.codeErrorMessage = entry.code + ' is already in use (case insensitive)';
							    				isValid = isValid && false;
							    			}
							    			let count = 0;
							    			for (let i=0; i<currentSoeList.length; i++)
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
				$scope.displaySOWDetails = function() {
					var retDat = service.openSowPopup($scope.projectId);
					console.log(retDat);
				}
				
				function populateSoeData(data, level, soeTOs) {
					return TreeService.populateTreeData(data, level, soeTOs, 'code', 'childSOEItemTOs');
				}
			}]
		});
		return deferred.promise;
	},	
	service.openSowPopup = function(projId) {
		var deferred = $q.defer();
		var createSowPoup = ngDialog.open({
			template: 'views/changeorders/cosoe/sowlist.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $rootScope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log("controller changeorderssowfactory",projId);
				$scope.SOWData1 = [];
				$scope.projectId = projId;
				
				$scope.getSOWDetails = function() {
					var sowReq = {
						"projId" : projId,
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
						$scope.SOWData1 = populateSowData(dataSOWItems, 0, []);
								
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
					});
				}
				$scope.sowItemClick = function (item, collapse) {
					TreeService.treeItemClick(item, collapse, 'childSOWItemTOs');
				}
				var sowPopup;
				var addSOWservice = {};
				var deferred = $q.defer();
				$scope.editSOWDetails = function(itemData) {
					ProjCostCodeService.getCalendarSpecialWorkingNonworkingDays($scope.projectId).then(function(data) {
						console.log(data);
						let projId = $scope.projectId;
						sowPopup = addSOWservice.addSOWDetails(itemData, projId, {weeklyHolidays: data.weeklyHolidays, specialWorkingDays: data.specialWorkingDays, specialNonworkingDays: data.specialNonworkingDays});
						sowPopup.then(function(data) {
							console.log(data);
							$scope.SOWData1 = populateSowData(data.projSOWItemTOs,0,[]);
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
								TreeService.dynamicTreeItemClick($scope.editSOWData, item, expand);
							},
							$scope.getProjSOWItemsById = function() {
								var sowEditReq = {
									"status" : "1",
									"sowId" : itemData.id,
									"projId" : $scope.projId
								};
								console.log(sowEditReq);
								ProjSOWService.getProjSOWItemsById(sowEditReq).then(function(data) {
									console.log(data)
									$scope.editSOWData = TreeService.populateDynamicTreeData(data.projSOWItemTOs, 0, [], 'code',
										'childSOWItemTOs');
									var sowReq = {
										"projId" : $scope.projId,
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
								console.log("sorDetails function");
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
									console.log(sowSaveReq);
									blockUI.start();
									/*ProjSOWService.saveSOWItems(sowSaveReq).then(function(data) {
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
									});*/
								}
							};
							function validateEntry(entry, calendarInfo) {
								let isValid = true;
								let isBlankEntry = false;
								delete entry.sorErrorMessage;
								delete entry.codeErrorMessage;
								delete entry.startDateErrorMessage;
								delete entry.finishDateErrorMessage;
								
								if (entry.item) {
									if (entry.tangibleClassTO == null && entry.projSORItemTO == null && entry.projCostItemTO == null && (entry.startDate == null || entry.startDate == '') 
											&& (entry.finishDate == null || entry.finishDate == ''))
										isBlankEntry = true;
									
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
			
				}
				$scope.next = function(projectId){
					console.log("next function");
					var sowpopup = $scope.displaySowItemsPopup(projectId);
					console.log(sowpopup);
					//ngDialog.close(createSowPoup);
				}
				function validateTree(input)
				{
					var treedata = null;
					var inpudata = input;
					for(var i=0;i<input.childSOWItemTOs.length;i++)
					{
						if( input.childSOWItemTOs[i].childSOWItemTOs.length > 0 )
						{
							treedata=inpudata;
						}
					}
					return treedata;
				}	
				function populateSowData(data, level, sowTOs) {
					return TreeService.populateTreeData(data, level, sowTOs, 'code', 'childSOWItemTOs');	
				}
				$scope.displaySowItemsPopup = function(projId) {
					var deferred = $q.defer();
					var displaySowItemsListPopup = ngDialog.open({
						template: 'views/changeorders/cosoe/sowitemslist.html',
						className: 'ngdialog-theme-plain ng-dialogueCustom4',
						closeByDocument: false,
						scope: $rootScope,
						showClose: false,
						controller: ['$scope','$rootScope', function ($scope,$rootScope) {
							console.log("displaySowItemsPopup function controller");
							$scope.COSowData = [];							
							let display_request = {
								"projId" : projId
							};							
							ChangeOrdersService.getCoSowDetails(display_request).then(function(data) {
								console.log(data);
								$scope.COSowData = populateSowData(data.projSOWItemTOs, 0, []);
								console.log($scope.COSowData);
							}, function(error) {
									GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
							});
							$scope.projCoSowPopupItemClick = function(item, expand) {
								TreeService.dynamicTreeItemClick($scope.COSowData, item, expand);
							}
							$scope.addItems = function() {
								for( var k=0;k<$scope.COSowData.length;k++ )
								{
									if( $scope.COSowData[k].item )
									{
										$rootScope.projSowData.push($scope.COSowData[k]);
										projectSowData.push($scope.COSowData[k]);
									}									
								}
								//ngDialog.close(displaySowItemsListPopup);
								//ngDialog.close(openSowPopup);
								console.log("projSowData",$rootScope.projSowData);
								console.log("projSowData",projectSowData);
							}
						}]
					});									
					return projectSowData;
				}							
			}]
		});
		return projectSowData;
	}	
	return service;
}]);

