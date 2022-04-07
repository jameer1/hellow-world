'use strict';

app
		.factory(
				'PrecontractSOWFactory',
				["ngDialog", "$q", "$filter", "$rootScope", "$timeout", "GenericAlertService", "ProjSOWService","PreContractInternalService", function(ngDialog, $q, $filter, $rootScope, $timeout, GenericAlertService,
						ProjSOWService,PreContractInternalService) {

					var service = {};
							service.getSOWDetails = function(projId, existedSOWMap, type,preContractObj) {
								var deferred = $q.defer();
								var req = {
									"status" : 1,
									"projId" : projId
								};
								var popup = ProjSOWService.getProjSOWDetails(req, type)

								popup
										.then(
												function(data) {
													var resultData = service.getSORwDetails(
															data.projSOWItemTOs, existedSOWMap, type,preContractObj);
													resultData
															.then(
																	function(data) {
																		deferred.resolve(data);
																	},
																	function(error) {
																		GenericAlertService
																				.alertMessage(
																						"Error occured while gettting  Scope Of Work details",
																						'Error');
																	});
												},
												function(error) {
													GenericAlertService
															.alertMessage(
																	"Error occured while gettting  Scope Of Work details",
																	'Error');
												});
								return deferred.promise;

							},

							service.getSORwDetails = function(projSOWItemTOs, existedSOWMap, type,preContractObj) {
							   var deferred = $q.defer();
								var sowDtlsPopup = ngDialog
										.open({
											template : '/views/procurement/pre-contracts/internalApproval/precontractsowpopup.html', 
											className : 'ngdialog-theme-plain ng-dialogueCustom0',
											showClose : false,
											closeByDocument : false,
											controller : [
													'$scope',
													function($scope) {
														if(type == 'servicesow') {
															$scope.serviceSOW = true;
														}else {
															$scope.serviceSOW = false;
														}
														$scope.budget = [];
														$scope.existedSOWMap = existedSOWMap;
														var selectedSowTOs = [];
														$scope.sowData = projSOWItemTOs;
														$scope.itemId = 1;
														$scope.isExpand = true;
														$scope.itemClick = function(itemId, expand) {
															$scope.itemId = itemId;
															$scope.isExpand = !expand;
														};

																$scope.sowpopup = function(tab) {
																	if($scope.serviceSOW) {
																		selectedSowTOs = [];
																	}
																	if(type == 'servicesow'){
																		if (tab.select) {
																			selectedSowTOs.push(tab);
																		}else{
																			selectedSowTOs.pop(tab);
																		}
																			
																	}
																	if (tab.select) {
																	var req = {
																		"contractId": preContractObj.id,
																		"contractType":"Project Sub Contract",
																		"costIds":[tab.projCostId]
																		};
																		PreContractInternalService.getPreContractCostCodeSummary(req).then(function (data) {
																			
																			$scope.budget = data.labelKeyTOs;
																			if($scope.budget[0] == null){
																				selectedSowTOs.push(tab);
																			}
																			if(((parseInt($scope.budget[0].displayNamesMap.estimateAmount))-(parseInt($scope.budget[0].displayNamesMap.budgetAmount)))<0){
																				GenericAlertService.confirmMessageModal('Selected CostCode has budget in negative. Do you really want to add the CostCode',	'Warning', 'YES', 'NO').then(function() {
																				selectedSowTOs.push(tab);
																				},function(){
																					tab.select = false;
																				})
																			}
																		})
																		}else{
																			selectedSowTOs.pop(tab);
																		}
																	
																},

																$scope.addSowItemsToProcurement = function() {
																	if (selectedSowTOs.length <= 0) {
																		GenericAlertService
																				.alertMessage(
																						"Please select atleast one Scope Of Work item to add to Precontract",
																						"Warning");
																		return;
																	}
																	
																				var returnPopObj = {
																					"projSOWItemTOs" : selectedSowTOs,
																				};
																				deferred
																						.resolve(returnPopObj);
																				$scope.closeThisDialog();
																}

													} ]
										});
								return deferred.promise;
							};
					return service;
				}]).filter(
				'procurementSOWPopupFilterTree',
				function() {
					function recursive(obj, newObj, level, itemId, isExpand, existedSOWMap) {
						angular.forEach(obj, function(o) {
						 if ((o.projSORItemTO && o.projSORItemTO.length != 0) && (o.projCostItemTO && o.projCostItemTO.length != 0 )) {
							if (o.childSOWItemTOs && o.childSOWItemTOs.length != 0) {
								o.level = level;
								o.leaf = false;
								newObj.push(o);
								recursive(o.childSOWItemTOs, newObj, o.level + 1, itemId, isExpand,
										existedSOWMap);
							} else {
								o.level = level;
								if (o.item) {
									o.leaf = true;
									if (existedSOWMap[o.id] == null) {
										newObj.push(o);
									} else {
										obj.splice(obj.indexOf(o), 1);
									}
								} else {
									obj.splice(obj.indexOf(o), 1);
								}

								return false;
							}
							}
						});
					}
					return function(obj, itemId, isExpand, existedSOWMap) {
						var newObj = [];
						recursive(obj, newObj, 0, itemId, isExpand, existedSOWMap);
						return newObj;
					}
				});
