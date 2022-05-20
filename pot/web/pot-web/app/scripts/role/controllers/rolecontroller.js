'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:RoleController
 * @description # Role Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state('clientroles', {
		url: '/clientroles',
		parent: 'site',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/role/role.html',
				controller: 'RoleController'
			}
		}
	})
}]).controller('RoleController', ["$scope", "ngDialog", "$q", "$state", "RoleService", "UserService", "blockUI", "GenericAlertService", "moduleservice","stylesService", "ngGridService", function ($scope, ngDialog, $q, $state, RoleService, UserService, blockUI, GenericAlertService, moduleservice, stylesService, ngGridService) {
	$scope.users = {};
	$scope.currentRole = null;
	$scope.moduleTreeData = [];
	$scope.userProfiles = [];
	$scope.uniqueRoleMap = [];
	var deleteIds = [];
	var saveData = [];
	$scope.stylesSvc = stylesService;
	var editProfiles = [];
	$scope.defaultRole = false;
	$scope.roleId = null;
	$scope.setClickedRow = function (row) {
		$scope.selectedRow = row;
	}
	var rolePermissions = [];
	var permissions = [];

	$scope.rowSelect = function (profile) {
		if (profile.selected) {
			if (!editProfiles.find(x => x.roleId === profile.roleId))
				editProfiles.push(profile);
		} else {
			editProfiles.splice(editProfiles.indexOf(profile), 1);
		}
	}
	var userProfileService = {};
	var profilePopUp;
	$scope.addProfiles = function (actionType) {
		if (actionType !== 'Edit') {
			angular.forEach(editProfiles, function(profile) {
				profile.selected = false;
			});
			editProfiles = [];
		}
		profilePopUp = userProfileService.addProfile(actionType, editProfiles);
		profilePopUp.then(function (data) {
			$scope.userProfiles = data.userProfiles;
			editProfiles = [];
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Company details", 'Error');
		});
	}
	userProfileService.addProfile = function (actionType, editProfiles) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editProfiles.length === 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		profilePopUp = ngDialog.open({
			template: 'views/role/addrolepopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			closeByDocument: false,
			showClose: false,
			scope: $scope,
			controller: ['$scope', function ($scope) {
				$scope.userProfilesList = [];
				var copyEditArray = angular.copy(editProfiles);
				var selectedProfiles = [];
				$scope.action = actionType;
				if (actionType === 'Add') {
					$scope.userProfilesList.push({
						"roleId": null,
						"roleName": null,
						"status": 1
					});
				} else {
					$scope.userProfilesList = angular.copy(editProfiles);
					editProfiles = [];
				}
				$scope.addRows = function () {
					$scope.userProfilesList.push({
						"roleId": null,
						"roleName": null,
						"status": 1
					});
				},
					$scope.getRoleServiceMap = function () {
						var req = {};
						RoleService.getRoleServiceMap(req).then(function (data) {
							$scope.uniqueRoleMap = data.uniqueRoleMap;
						})

					},
					$scope.checkDuplicate = function (userprofile) {
						userprofile.duplicateFlag = false;
						if (userprofile.roleName) {
							userprofile.roleName = userprofile.roleName.toUpperCase();
							if ($scope.uniqueRoleMap[userprofile.roleName] != null) {
								userprofile.duplicateFlag = true;
								return;
							}
							userprofile.duplicateFlag = false;
						}
					},

					$scope.saveRoles = function () {
						for (var i = 0; i < $scope.userProfilesList.length; i++) {
							if ($scope.userProfilesList[i].duplicateFlag) {
								GenericAlertService.alertMessage($scope.userProfilesList[i].roleName + ' already exists. Duplicate Role Names are not allowed', "Warning");
								return;
							}
						}

						var saveRoleReq = {
							"roleTOs": $scope.userProfilesList
						};
						blockUI.start();
						RoleService.saveRoles(saveRoleReq).then(function (data) {
							blockUI.stop();
							var succMsg = GenericAlertService.alertMessageModal("User Profile saved successfully", "Info");
							$scope.getRoles();
							succMsg.then(function () {
								$scope.closeThisDialog();
								var returnPopObj = {
									"userProfiles": data.roleTOs
								};
								$scope.editProfiles = [];
								deferred.resolve(returnPopObj);
							})
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage("Error Occured While Creating Role Name", "Error");
						});
					}, $scope.popUpRowSelect = function (selectedProfile) {
						if (selectedProfile.selected) {
							selectedProfiles.push(selectedProfile);
						} else {
							selectedProfiles.splice(selectedProfiles.indexOf(selectedProfile), 1);
						}
					}
				$scope.deleteRows = function () {
					if (selectedProfiles.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedProfiles.length < $scope.userProfilesList.length) {
						angular.forEach(selectedProfiles, function (value, key) {
							$scope.userProfilesList.splice($scope.userProfilesList.indexOf(value), 1);
						});
						selectedProfiles = [];
						GenericAlertService.alertMessage('User Profile(s) deleted successfully', "Info");
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			}]
		});
		return deferred.promise;
	}, $scope.getRoles = function () {
		$scope.defaultRole = false;
		var roleReq = {
			"status": 1
		};
		RoleService.getRoles(roleReq).then(function (data) {
			$scope.userProfiles = data.roleTOs;
			$scope.gridOptions.data = angular.copy($scope.userProfiles);
		});
	}
	var userRoles = [];
	$scope.getUserRoles = function () {
		var userReq = {
			"status": "1"
		}
		UserService.getUsers(userReq).then(function (data) {
			angular.forEach(data.users, function (value) {
				angular.forEach(value.userRoles, function (value) {
					userRoles.push(value.roleName);
				})
			})
		});
	},
		$scope.getUserRoles();
	$scope.deleteRoles = function () {
		let deleteIds = [];
		$scope.nondeleteIds = [];

		if (editProfiles.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		angular.forEach(editProfiles, function (value) {
			for (let i = 0; i < editProfiles.length; i++) {
				deleteIds.push(value.roleId);

			}
		});
		if (deleteIds.length > 0) {
			if ($scope.selectedAll) {
				$scope.userProfiles = [];
			} else {
				const roleDeactivateReq = {
					"roleIds": deleteIds,
					"status": 2
				};
				GenericAlertService.confirmMessageModal('Do you really want to delete User Profile detail(s)?', 'Warning', 'YES', 'NO').then(function (data) {
					RoleService.deactivateRoles(roleDeactivateReq).then(function (data) {
						angular.forEach(editProfiles, function (value, key) {
							GenericAlertService.alertMessage("User Profile(s) deactivated successfully", "Info");
							$scope.userProfiles.splice($scope.userProfiles.indexOf(value), 1);
							$scope.userProfiles = data.roleTOs;
							editProfiles = [];
							$scope.deleteIds = [];
							$scope.moduleTreeData = [];
						})
					}, function (error) {
						angular.forEach(editProfiles, function (value) {
							value.selected = false;
						});
						if (error.data && error.data.message) {
							GenericAlertService.alertMessage(error.data.message, "Error");
						} else {
							GenericAlertService.alertMessage('User Profile Detail(s) is/are failed to delete.', "Error");
						}
					})
				}, function (error) {
					angular.forEach(editProfiles, function (value) {
						value.selected = false;
					});

				});
			}
		}
	}, $scope.getRolePermissions = function (roleObj) {
		$scope.roleId = roleObj.roleId;
		$scope.defaultRole = roleObj.defaultRole;
		$scope.currentRole = roleObj.roleName;
		var rolePermissionReq = {
			"roleId": roleObj.roleId
		};
		RoleService.getRolePermissions(rolePermissionReq).then(function (data) {
			var moduleTreeData = angular.copy(moduleservice.modules);
			if (data) {
				setPermissions(data.permissions, moduleTreeData);
				rolePermissions = data.rolePermissions;
			}
			$scope.moduleTreeData = populateModuleData(moduleTreeData,0,[]);
		});
	}

	function populateModuleData(data, level, modules, isChild, parent) {
		for (let i = 0; i < data.length; i++) {
			let module = data[i];

			if( module.moduleName == "Client Registration" && $scope.currentRole != "ADMIN" ){
				continue;
			}

			module.level = level;
			if (module.moduleURLValue != "" && module.childModules && !module.childModules.length) {
				module.item = true;
			} else {
				module.item = false;
			}
			if (!isChild) {
				module.collapse = false;
				module.parent = module.moduleName;
			} else {
				module.collapse = true;
				module.parent = parent;
			}
			modules.push(module);
			if (module.childModules && module.childModules.length > 0) {
				populateModuleData(module.childModules, level + 1, modules, true, module.parent);
			}
		}
		return modules;
	}

	var setPermissions = function (permissions, moduleTreeData) {
		for (var i = 0; i < moduleTreeData.length; i++) {
			var module = moduleTreeData[i];
			var permissionTos = module.permissionTOs;
			var childModules = module.childModules;
			for (var j = 0; j < permissionTos.length; j++) {
				var permission = permissionTos[j];
				if (permissions.indexOf(permission.permissionKey) > -1) {
					permission.permission = true;
				}
			}

			if (childModules.length > 0) {
				setPermissions(permissions, childModules);
			}
		}
	}

	$scope.saveRolePermission = function () {
		if ($scope.moduleTreeData.length <= 0) {
			GenericAlertService.alertMessage("Please select a profile to assign permissions ", "Warning");
			return;
		}
		var savePermissionReq = {
			"permissions": permissions,
			"roleId": $scope.roleId
		};
		blockUI.start();
		RoleService.saveRolePermission(savePermissionReq).then(function (data) {
			permissions = [];
			blockUI.stop();
			// GenericAlertService.alertMessage('Role Permission(s) is/are ' + data.message, data.status);
			GenericAlertService.alertMessage('User Profile permissions saved successfully', "Info");
			$scope.getRoles();
		}, function (error) {
			permissions = [];
			blockUI.stop();
			GenericAlertService.alertMessage("Error Occured While Saving Permissions", "Error");
		});
	};

	$scope.moduleItemClick = function (item, collapse) {
		if (item.childModules && item.childModules.length > 0) {
			item.expand = !collapse;
			for (let i = 0; i < item.childModules.length; i++) {
				item.childModules[i].collapse = collapse;
				if (collapse && item.childModules[i].childModules && item.childModules[i].childModules.length > 0)
					$scope.moduleItemClick(item.childModules[i], collapse);
			}
		}
	};

	$scope.childSelected = function (selectedPermission) {
		var index = permissions.indexOf(selectedPermission.toBeSavedPerm);
		if (selectedPermission.toBeSavedPerm && index > -1) {
			permissions[index].remove = !selectedPermission.permission;
		} else {
			var permission = {
				rolePermId: getRolePermId(selectedPermission.permissionKey),
				permission: selectedPermission.permissionKey,
				remove: !selectedPermission.permission
			};
			selectedPermission.toBeSavedPerm = permission;
			permissions.push(permission);
		}
	};

	function getRolePermId(permission) {
		let rolePermId = null;
		rolePermissions.map((rolePerm) => {
			if (permission === rolePerm.permission) {
				rolePermId = rolePerm.id;
				return false;
			}
		});
		return rolePermId;
	}

	/**
	 * Search for child node and returns true if child found
	 * undefined if child module not found
	 * @param {*} parentModule parent module
	 * @param {*} moduleIdToSearch moduleId for searching
	 */
	function findChildModule(parentModule, moduleIdToSearch) {
		for (var i = 0; i < parentModule.childModules.length; i++) {
			var child = parentModule.childModules[i];
			if (child.moduleId == moduleIdToSearch) {
				return true;
			} else if (child.childModules.length > 0) {
				var childFound = findChildModule(child, moduleIdToSearch);
				if (childFound) {
					return childFound;
				}
			}
		}
	}

	/**
	 * Returns number of childs selected under the given parent
	 * @param {*} parentModule
	 * @param {*} selectedChildsCount
	 */
	function findSelectedChilds(parentModule, selectedChildsCount) {
		for (var i = 0; i < parentModule.childModules.length; i++) {
			var child = parentModule.childModules[i];
			var selectedChilds = [];
			for (var j = 0; j < child.permissionTOs.length; j++) {
				if (child.permissionTOs[j].permission === true) {
					selectedChilds.push(child.permissionTOs[j]);
				}
			}
			selectedChildsCount += selectedChilds.length;
			if (child.childModules.length > 0) {
				selectedChildsCount = findSelectedChilds(child, selectedChildsCount);
			}
		}
		return selectedChildsCount;
	}
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var linkCellTemplate ='	<input type="checkbox" ng-disabled="row.entity.defaultRole" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'roleName',cellTemplate:"<span style='width:750px;' ng-click='grid.appScope.getRolePermissions(row.entity)'>{{row.entity.roleName}}</span>", displayName: "Profile Name", headerTooltip: "Profile Name"}
						];
					let data = [];
					$scope.getRoles();
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Admin_User profiles & privileges");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
	
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/adminhelp/userprofileshelp.html',
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
