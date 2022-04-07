'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:EPSController
 * @description # EPS Controller of the potApp
 */
app.config(['$qProvider', function($qProvider) {
	$qProvider.errorOnUnhandledRejections(false);
}]);
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("epsproject", {
		url: '/epsproject',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/epsproject/epsprojecttree.html',
				controller: 'EPSProjectController'
			}
		}
	})
}]).controller('EPSProjectController', ["$scope", "$state", "$q", "ngDialog", "EpsService", "blockUI", "ProjEmpClassService", "GenericAlertService",
	"UserEPSProjectService", "ProjectSettingsService", "TreeService", "ProjectStatusService", "CalendarService", function($scope, $state, $q, ngDialog, EpsService, blockUI,
		ProjEmpClassService, GenericAlertService, UserEPSProjectService, ProjectSettingsService, TreeService, ProjectStatusService, CalendarService) {
		$scope.epsData = [];
		$scope.calendarList = [];
		var deleteTreeData = [];
		var editTree = [];
		var previouslyOpenedProject = null;
		$scope.getEPSDetails = function() {
			var epsReq = {
				"status": 1
			};
			EpsService.getEPSDetails(epsReq).then(function(data) {
				console.log(data);
				let epsValues = populateData(data.ePSProjectTOs, 0, []);
				console.log(epsValues);
				//budgetValues(epsValues);
				//$scope.epsData = budgetValues(epsValues);
				$scope.epsData = epsValues;
			})

		}

		function budgetValues(epsValues) {
			console.log("Test budgetValues ", epsValues);

			//$scope.epsData.projGeneralMstrTO.budgetAmount=0
			for (const value of epsValues) {
				if (value.projGeneralMstrTO != null && value.projGeneralMstrTO.countryName != null) {
					console.log(value.projId);
					//console.log(value.projGeneralMstrTO.countryName);
					var projSummary = {
						"projId": value.projId,
						"status": "1"
					};
					ProjectStatusService.getCostUnits(projSummary).then(function(data) {
						//console.log(data.projCostStatementsSummaryTOs);
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Cost Units", "Error");
					});
				}
			}
		}

		var populateData = function(data, level, projects) {
			return TreeService.populateTreeData(data, level, projects, 'projCode',
				'childProjs');
		}
		$scope.rowSelect = function(rowData) {
			if (rowData.select) {
				deleteTreeData.push(rowData.projId);
			} else {
				deleteTreeData.pop(rowData.projId);

			}
		}

		var epspopup;
		var addEPSservice = {};
		$scope.editTreeDetails = function(actionType, itemData) {
			const parent = $scope.findParent(actionType, $scope.epsData, itemData);
			epspopup = addEPSservice.addTreeDetails(actionType, itemData, parent);
			epspopup.then(function(data) {
				$scope.epsData = populateData(data.epsProjs, 0, []);
				for (let i = 0; i < $scope.epsData.length; i++) {
					if (previouslyOpenedProject && previouslyOpenedProject === $scope.epsData[i].projId) {
						let parent = $scope.findParent(actionType, $scope.epsData, $scope.epsData[i]);
						while (parent.parentId) {
							parent = $scope.findParent(actionType, $scope.epsData, parent);
						}
						$scope.itemClick(parent, false);
					}
				}
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Details  ", 'Error');
			});
		},
			$scope.findParent = function(actionType, epsData, itemData) {
				let parent = null;
				// if (actionType === 'Edit') {
				for (let eps of epsData) {
					if (eps.projId == itemData.parentId) {
						parent = eps;
					}
					if (parent) {
						break;
					}
				}

				if (parent === null) {
					parent = itemData;
				}
				/*
					} else {
						parent = itemData;
					}
				*/
				return parent;
			},
			addEPSservice.addTreeDetails = function(actionType, itemData, parent) {
				var deferred = $q.defer();
				epspopup = ngDialog.open({
					template: 'views/epsproject/epsprojecttreepopup.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom2',
					scope: $scope,
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function($scope) {
						$scope.pcode = null;
						$scope.action = actionType;
						$scope.editTreeData = [];
						var treeData = [];
						$scope.parent = parent;

						$scope.popupItemClick = function(item, collapse) {
							if (item.childProjs && item.childProjs.length > 0) {
								item.expand = !collapse;
								for (let i = 0; i < item.childProjs.length; i++) {
									item.childProjs[i].collapse = collapse;
									if (item.childProjs[i].childProjs && item.childProjs[i].childProjs.length > 0)
										$scope.popupItemClick(item.childProjs[i], collapse);
								}
							}
						};
						$scope.addTreeItem = function(tabData, level) {
							tabData.childProjs.push(angular.copy({
								"select": false,
								"parentId": tabData.projId,
								"status": 1,
								"deleteFlag": false,
								"projCode": '',
								'leaf': true,
								"projName": '',
								"proj": true,
								"startDate": '',
								"finishDate": '',
								"assignedStatus": '',
								"usrProj": '',
								"collapse": false,
								"expand": true,
								"level": tabData.level + 1
							}));
							$scope.popupItemClick(tabData, false);
						}
						if (itemData) {
							$scope.pcode = itemData.projCode;
						}
						if (actionType === 'Add') {
							$scope.editTreeData = [angular.copy(itemData)];
							$scope.editTreeData[0].childProjs = [];
							$scope.addTreeItem($scope.editTreeData[0]);
							$scope.editTreeData.push($scope.editTreeData[0].childProjs[0]);
						} else {
							$scope.editTreeData = new Array(angular.copy(itemData));
						}
						$scope.addTreeGroup = function() {
							$scope.editTreeData.push({
								"select": false,
								"status": 1,
								"deleteFlag": false,
								"projCode": '',
								"projName": '',
								"startDate": '',
								"finishDate": '',
								"assignedStatus": '',
								"usrProj": '',
								"childProjs": [],
								"level": 0,
								"collapse": false,
								"expand": true
							});
							$scope.popupItemClick(null, false);
						}

						$scope.addTreeSubGroup = function(tabData) {
							tabData.childProjs.push(angular.copy({
								"select": false,
								"parentId": tabData.projId,
								"status": 1,
								"deleteFlag": false,
								"projCode": '',
								"projName": '',
								"startDate": '',
								"finishDate": '',
								"assignedStatus": '',
								"usrProj": '',
								"childProjs": [],
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true
							}));
							let index = (($scope.editTreeData.indexOf(tabData) == 0) ? 1 : $scope.editTreeData.indexOf(tabData)) + tabData.childProjs.length;
							index = calculateIndexBasedOnChildProjs(tabData.childProjs, index);
							tabData.childProjs.push(obj);
							$scope.editTreeData.splice((index + 1), 0, obj);

							$scope.popupItemClick(tabData.projId, false);

						}
						var calculateIndexBasedOnChildProjs = function(childProjs, count) {
							for (let i = 0; i < childProjs.length; i++) {
								if (childProjs[i].childProjs && childProjs[i].childProjs.length > 0) {
									count += childProjs[i].childProjs.length;
									calculateIndexBasedOnChildProjs(childProjs[i].childProjs, count);
								}
							}
							return count;
						}

						$scope.deleteTree = function(tab) {
							for (let i = 0; i < $scope.editTreeData.length; i++) {
								if ($scope.editTreeData[i].projId === tab.projId) {
									$scope.editTreeData[i].childProjs.splice($scope.editTreeData[i].childProjs.indexOf(tab), 1);
								}
							}
							deleteItemInMainTree(tab, $scope.editTreeData[0]);
							$scope.editTreeData.splice($scope.editTreeData.indexOf(tab), 1);
						}
						var deleteItemInMainTree = function(tab, mainTree) {
							let list = [];
							let found = false;
							for (let i = 0; i < mainTree.childProjs.length; i++) {
								if (mainTree.childProjs[i].projId === tab.projId) {
									list = mainTree.childProjs;
									found = true;
									break;
								} else if (mainTree.childProjs[i].childProjs && mainTree.childProjs[i].childProjs.length > 0) {
									deleteItemInMainTree(tab, mainTree.childProjs[i]);
								}
							}

							if (found) {
								list.splice(list.indexOf(tab), 1);
								return;
							}
						}

						$scope.checkDuplicate = function(item) {
							item.duplicateFlag = false;
							item.projCode = item.projCode ? item.projCode : item.projCode.toUpperCase();
							if ($scope.parent.childProjs) {
								angular.forEach($scope.parent.childProjs, function(data) {
									if (item.projId != data.projId && item.projCode == data.projCode) {
										item.duplicateFlag = true;
										GenericAlertService.alertMessage('Duplicate eps projects are not allowed', "Warning");
										return;
									}
								});
							}
						},

							$scope.findProjectFromList = function(projName, results) {
								var projId;
								for (var index = 0; index < results.length; index++) {
									var result = results[index];
									if (result.projCode == projName) {
										projId = result.projId;
									} else {
										// find in childProjs
										projId = $scope.findProjectFromList(projName, result.childProjs);
									}
									if (projId) {
										break;
									}
								}
								return projId;
							},

							$scope.saveProjects = function(epsForm) {
								let dupProjs = false;
								let dupProjNames = '';
								angular.forEach($scope.editTreeData, function(editData) {
									angular.forEach($scope.parent.childProjs, function(data) {
										if (editData.projId != data.projId && editData.projCode == data.projCode) {
											editData.duplicateFlag = true;
											dupProjNames += ' ' + editData.projCode;
											dupProjs = true;
										}
									});
								});
								if (dupProjs) {
									GenericAlertService.alertMessage(dupProjNames + ' are duplicated. Duplicate Eps Projects are not allowed', "Warning");
									return;
								}

								//Default calendar checking		
								var req = {
									"status": 1,
									"calType": 'GCAL',

								};

								CalendarService.getCalendars(req).then(function(data) {
									$scope.calendarList = data.calenderTOs;
								});
								var count = 0;
								for (var i = 0; i < $scope.calendarList.length; i++) {
									if ($scope.calendarList[i].calDefaultValue == 1) {
										count++;
									}
								}
								if (count < 1) {
									GenericAlertService.alertMessage(' Please create Default calendar', "Warning");
									return;
								}


								if (!dupProjs && epsForm.$valid) {
									var epsSaveReq = {
										"projs": [$scope.editTreeData[0]]
									};
									blockUI.start();
									EpsService.saveProjects(epsSaveReq).then(function(data) {
										blockUI.stop();
										if (data.status != null && data.status !== undefined && data.status === 'Info') {
											var results = data.epsProjs;
											var newProjId = null;
											if (actionType == 'Edit') {
												// newProjId = $scope.findProjectFromList($scope.editTreeData[0].projCode, results);
											} else {
												newProjId = $scope.findProjectFromList($scope.editTreeData[0].childProjs[0].projCode, results);
											}

											if (newProjId) {
												var req = {
													"projIds": [newProjId]
												}
												ProjectSettingsService.saveProjDefaultSettings(req).then(function() {
												}, function(data) {
													GenericAlertService.alertMessage("Unable to save default settings for project " + $scope.editTreeData[0].childProjs[0].projCode, "Error");
												});
											}

											// var succMsg = GenericAlertService.alertMessageModal('Project Detail(s) is/are ' + data.message, data.status);
											var succMsg = GenericAlertService.alertMessageModal('Project detail(s) saved successfully', "Info");
											succMsg.then(function(data1) {
												$scope.closeThisDialog();
												var returnPopObj = {
													"epsProjs": results
												};
												previouslyOpenedProject = $scope.editTreeData[0].projId;
												deferred.resolve(returnPopObj);
											}, function(error) {
												blockUI.stop();
												GenericAlertService.alertMessage("Error occured while saving EPS Details", "Error");
											});
										}
									});
								} else {
									GenericAlertService.alertMessage("Please Fill Project ID and Project Name ", "Warning");
								}
							}
					}]
				});
				return deferred.promise;
			}

		$scope.deactivateEPSDetails = function() {
			if (deleteTreeData == undefined || deleteTreeData.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one Project to deactivate", 'Warning');
				return;
			}
			var epsDeactivateReq = {
				"projectIds": deleteTreeData,
				"status": 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				EpsService.deactivateEPSDetails(epsDeactivateReq).then(function(data) {
					if (data.code && data.code == 412) {
						GenericAlertService.alertMessage("Couldn't Deactivate, Selected Project(s) Assigned to users", "Warning");
					} else {
						GenericAlertService.alertMessage("Project(s) Deactivated successfully", "Info");
						deleteTreeData = [];
						$scope.getEPSDetails();
					}
				}, function(error) {
					GenericAlertService.alertMessage(' Project(s) is/are failed to deactivate', "Error");
				});
			}, function(data) {
				deleteTreeData = [];
				$scope.getEPSDetails();
			});
		}

		$scope.itemClick = function(item, collapse) {
			TreeService.treeItemClick(item, collapse, 'childProjs');
		};

		$scope.checkAll = function() {
			angular.forEach($scope.epsData, function(tab) {
				if ($scope.selectedAll) {
					tab.selected = false;
				} else {
					tab.selected = true;
				}
			});
		}
		var HelpService = {};
		$scope.helpPage = function() {
			var help = HelpService.pageHelp();
			help.then(function(data) {

			}, function(error) {
				GenericAlertService.alertMessage("Error", 'Info');
			})
		}

		var req = {
			"status": 1

		};

		CalendarService.getCalendars(req).then(function(data) {
			$scope.calendarList = data.calenderTOs;
		});



		var helppagepopup;
		HelpService.pageHelp = function() {
			var deferred = $q.defer();
			helppagepopup = ngDialog.open({
				template: 'views/help&tutorials/projectshelp/projectlitshelp.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function($scope) {

				}]
			});
			return deferred.promise;
		}
	}]);
