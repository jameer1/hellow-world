'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("enterprise", {
		url: '/enterprise',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/eps/epstree.html',
				controller: 'EPSController'
			}
		}
	})
}]).controller('EPSController', [ "$rootScope", "$scope", "$state", "$q", "ngDialog", "EpsService", "blockUI",
	"ProjEmpClassService", "GenericAlertService", "UserEPSProjectService", "TreeService" ,function ($rootScope, $scope, $state, $q, ngDialog, EpsService, blockUI,
	ProjEmpClassService, GenericAlertService, UserEPSProjectService, TreeService) {
	$scope.epsData = [];
	var deferred = $q.defer();
	var deleteTreeData = [];
	var deleteData = [];
	var editTree = [];
	$scope.sortType = "projCode"
	$scope.treeFlag = false;
	$scope.getEPSDetails = function () {
		var epsReq = {
			"status": "1"
		};
		EpsService.getEPSOnly(epsReq).then(function (data) {
			$scope.epsData = populateData(data.ePSProjectTOs, 0, []);
		});
	}

	var populateData = function (data, level, projects) {
		return TreeService.populateTreeData(data, level, projects, 'projCode', 'childProjs');
	}

	$scope.rowSelect = function (rowData) {
		if (rowData.select) {
			$scope.treeFlag = true;
			deleteTreeData.push(rowData.projId);
			deleteData.push(rowData);
		} else {
			$scope.treeFlag = false;
			deleteTreeData.pop(rowData.projId);
			deleteData.pop(rowData);
		}
	}
	$scope.changes = 0;
	$scope.change = function () {
		$scope.changes += 1;
	};

	var epspopup;
	var addEPSservice = {};
	$scope.editTreeDetails = function (actionType, itemData) {
		$scope.treeFlag = false;
		angular.forEach(deleteData, function (value, key) {
			value.select = false;
		});
		epspopup = addEPSservice.addTreeDetails(actionType, itemData);
		epspopup.then(function (data) {
			$scope.epsData = populateData(data.epsProjs, 0, []);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Details  ", 'Error');
		});
	}
	addEPSservice.addTreeDetails = function (actionType, itemData) {
		var deferred = $q.defer();
		epspopup = ngDialog.open({
			template: 'views/eps/epstreepopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.pcode = null;
				$scope.action = actionType;
				$scope.editTreeData = [];
				var treeData = [];

				var popupPopulateData = function (data, level, projects, isChild, parentProj) {
					return TreeService.populateDynamicTreeData(data, level, projects, 'projCode', 'childProjs',
						isChild, parentProj);
				}

				$scope.popupItemClick = function (item, collapse) {
					TreeService.dynamicTreeItemClick($scope.editTreeData, item, collapse);
				};
				if (itemData) {
					$scope.pcode = itemData.projCode;
				}

				if (actionType === 'Add') {
					$scope.editTreeData.push({
						"select": false,
						"status": 1,
						"deleteFlag": false,
						"projCode": '',
						"projName": '',
						"startDate": '',
						"finishDate": '',
						"usrProj": '',
						"childProjs": [],
						"level": 0,
						"collapse": false,
						"expand": true
					});
					$scope.editTreeData = popupPopulateData($scope.editTreeData, 0, []);
				} else {
					$scope.editTreeData = popupPopulateData([angular.copy(itemData)], 0, []);
				}

				$scope.addTreeSubGroup = function (tabData, itemIndex) {
					if ($scope.checkDataValidationRecursively(tabData, itemIndex)) {
						let obj = angular.copy({
							"select": false,
							"parentId": tabData.projId,
							"status": 1,
							"deleteFlag": false,
							"projCode": '',
							"projName": '',
							"startDate": '',
							"finishDate": '',
							"usrProj": '',
							"childProjs": [],
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editTreeData = TreeService.addItemToTree($scope.editTreeData, tabData,
							obj, itemIndex, 'childProjs');
					}
				}
				$scope.checkDataValidationRecursively = function (tabData, index) {
					if (!tabData.projCode || !tabData.projName) {
						tabData.invalidField = true;
						return false;
					}

					for (++index; index < $scope.editTreeData.length; index++) {
						return $scope.checkDataValidationRecursively($scope.editTreeData[index], index);
					}
					return true;

				};

				$scope.deleteTree = function (index) {
					TreeService.deleteDynamicTreeItem($scope.editTreeData, index);
				}

				$scope.getEpsListMap = function () {
					var req = {

					}

					EpsService.getEpsListMap(req).then(function (data) {
						$scope.uniqueCodeMap = data.projUniqueCodeMap;
					})
				};

				$scope.checkDuplicate = function (item) {
					item.duplicateFlag = false;
					item.projCode && (item.projCode = item.projCode.toUpperCase());
					if ($scope.uniqueCodeMap[item.projCode] != null) {
						item.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate EPS Id\'s are not allowed', "Warning");
						return;
					}
					item.duplicateFlag = false;
				}, $scope.saveProjects = function (item, epsForm) {
					var flag = false;
					var EpsListMap = [];
					angular.forEach($scope.editTreeData, function (value, key) {
						if (EpsListMap[value.projCode.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							EpsListMap[value.projCode.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.editTreeData, function (value, key) {
							if ($scope.uniqueCodeMap[value.projCode.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
						for (const eps of $scope.epsData) {
							if (eps.projCode === $scope.editTreeData[0].projCode) {
								$scope.editTreeData[0].duplicateFlag = true;
								flag = true;
							}
						}

					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate EPS ID\'s are not allowed', "Warning");
						return;
					}
					if (epsForm.$valid) {
						const data = TreeService.extractTreeDataForSaving($scope.editTreeData, 'childProjs');
						var epsSaveReq = {
							"projs": data
						};
						blockUI.start();
						EpsService.saveEpsStacture(epsSaveReq).then(function (data) {
							blockUI.stop();
							if (data.status != null && data.status !== undefined && data.status === 'Info') {
								var results = data.epsProjs;
								// var succMsg = GenericAlertService.alertMessageModal('EPS Details is/are  ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal('EPS Details saved successfully', "Info");
								succMsg.then(function (data) {
									$scope.closeThisDialog();
									var returnPopObj = {
										"epsProjs": results
									};
									deferred.resolve(returnPopObj);
								}, function (error) {
									blockUI.stop();
									GenericAlertService.alertMessage('EPS Details is/are failed to save', "Error");
								});
							}
						});
					} else {
						GenericAlertService.alertMessage('Please Fill out EPS ID , EPS Name fields', "Error");
					}
				}
			}]
		});
		return deferred.promise;
	}

	$scope.deactivateEPSDetails = function () {
		if (deleteTreeData == undefined || deleteTreeData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one EPS to deactivate", 'Warning');
			return;
		}
		var epsDeactivateReq = {
			"projectIds": deleteTreeData,
			"status": 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to Deactive the EPS Id\'s?', 'Warning', 'YES', 'NO').then(function () {
			EpsService.deactivateEPSDetails(epsDeactivateReq).then(function (data) {
				if (data.msgCode && data.msgCode == 412) {
					GenericAlertService.alertMessage("Couldn't Deactivate, Selected EPS(s) has active projects", "Warning");
				} else {
					GenericAlertService.alertMessage("Selected EPS Id\'s deactivated successfully", "Info");
					deleteTreeData = [];
					$scope.getEPSDetails();
				}
			}, function (error) {
				GenericAlertService.alertMessage('Selected Eps level(s) is/are Failed Deactivate ', "Error");
			});
		}, function (data) {
			deleteTreeData = [];
			$scope.getEPSDetails();
		})
	}

	$scope.itemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childProjs');
	};

	$scope.checkAll = function () {
		angular.forEach($scope.epsData, function (tab) {
			if ($scope.selectedAll) {
				tab.selected = false;
			} else {
				tab.selected = true;
			}
		});
	};

	$scope.getEPSDetails();

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
			template: 'views/help&tutorials/Enterprisehelp.html',
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
