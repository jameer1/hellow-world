'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("clientusers", {
		url : '/clientusers',
		parent : 'site',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/users/clientusers.html',
				controller : 'ClientUserController'
			}
		}
	})
}]).controller('ClientUserController', ["$scope", "ngDialog", "$q", "$state", "UserService", "blockUI", "RoleService", "GenericAlertService", "UsersByClientPopupService", "localStorageService", function($scope, ngDialog, $q, $state, UserService,blockUI, RoleService, GenericAlertService, UsersByClientPopupService, localStorageService) {
	$scope.tabopen = 'home';
	$scope.sortType = "userName"
	$scope.userId = null;
	var editUsers = [];
	$scope.user = [];
	$scope.users = [];
	$scope.userProjects = [];
	var deferred = $q.defer();
	$scope.ngDialog = ngDialog, $scope.tableData = [];
	$scope.registeredUsers = localStorageService.get('registeredUsers');
	$scope.exceededMaxUsers = false;
	$scope.deleteIds = [];
	$scope.flag1= true;
	$scope.userReq = {
		"userName" : null,
		"empCode" : null,
		"status" : "1"
	}

	// $scope.activeFlag = 0;
	$scope.searchUsers = function(isClick) {
		$scope.userProjects = [];
		editUsers = [];
		UserService.getUsers($scope.userReq).then(function(data) {
			// $scope.activeFlag = 0;
			$scope.users = data.users;
			if($scope.users.length >= 1)
				$scope.registeredUsers = $scope.users[0].registeredUsers;
			if ($scope.users.length <= 0) {
				GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
				return;
			}
			// if ($scope.users != null && $scope.users.length > 0) {
			// 	if ($scope.users[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.users[0].status == 2) {
			// 		$scope.userProjects = [];
			// 		$scope.activeFlag = 2;
			// 	}
			 	if ($scope.registeredUsers == null) {
			 		$scope.exceededMaxUsers = false;
			 	} else {
			 		$scope.exceededMaxUsers = ($scope.users.length >= $scope.registeredUsers);
			 	}
			// } else {
			// 	if ($scope.userReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.userReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.userReq = {
			// 		"userName" : null,
			// 		"empCode" : null,
			// 		"status" : "1"
			// 	};
			// 	if (isClick)
			// 		GenericAlertService.alertMessage('Users are not available for given search criteria', "Warning");
			// }

		});
	}, $scope.originUser = angular.copy($scope.resetUser);
	$scope.reset = function() {
		$scope.resetUser = angular.copy($scope.originUser);
		editUsers = [];

	}, $scope.resetUser = function() {
		$scope.userProjects = [];
		$scope.userReq = {
			"userName" : null,
			"empCode" : null,
			"status" : "1"
		}, $scope.searchUsers();
		$scope.activeFlag = 0;
	},

	$scope.passwordPattern = function(input) {
		var password, pattern;

		password = input.value;
		pattern = /^((?=^.{5,}$)(?=.*\d)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*)$/g;

		if( password.match(pattern) === null ){
			/*
			GenericAlertService.alertMessageReturns("Atleast 5 Characters with Uppercase , Lowercase, Numbers mandatory", 'Error').then(function(data){
			});
			*/
			input.parentNode.querySelector(".display-rule").classList.remove("hide");
		}else{
			input.parentNode.querySelector(".display-rule").classList.add("hide");
		}
	},

	$scope.passwordChanged = function() {
		var input = event.target;
		$scope.passwordPattern(input);
	},

	$scope.getUsersByClientId = function(employeeList) {
		var req = {

		};
		var userSerivcePopup = UsersByClientPopupService.getUserDetailsByClient(req);
		userSerivcePopup.then(function(data) {
			$scope.employeeFlag=data.employeeFlag;
			employeeList.empRegId = data.userByClientTO.id;
			employeeList.empCode = data.userByClientTO.code;
			employeeList.empDesg = data.userByClientTO.displayNamesMap.designation;
			employeeList.dispName = data.userByClientTO.displayNamesMap.displayName;
			employeeList.firstName = data.userByClientTO.displayNamesMap.firstName;
			employeeList.lastName = data.userByClientTO.displayNamesMap.lastName;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting Emp Registration Details", 'Error');
		});
	}

	$scope.activateUsers = function() {
		if (editUsers.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to Activate', "Warning");
			return;
		}
		var activateIds = [];
		angular.forEach(editUsers, function(value, key) {
			activateIds.push(value.userId);
		});

		var req = {
			"userIds" : activateIds,
			"status" : 1
		};

		GenericAlertService.confirmMessageModal('Do you want to activate the record', 'Warning', 'YES', 'NO').then(function() {
			UserService.activateUser(req).then(function(data) {
				GenericAlertService.alertMessage('User(s) Activated successfully', 'Info');
				angular.forEach(editUsers, function(value, key) {
					$scope.users.splice($scope.users.indexOf(value), 1);
					editUsers = [];
				}, function(error) {
					GenericAlertService.alertMessage('users are failed to activate', "Error");
				});
			});
		}, function(data) {
			angular.forEach(editUsers, function(value) {
				value.select = false;
			})
		});
	}

	$scope.deleteUser = function() {
		if (editUsers.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to Deactivate', "Warning");
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.users = [];
		} else {
			angular.forEach(editUsers, function(value, key) {
				deleteIds.push(value.userId);
			});
			var req = {
				"userIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				UserService.deleteUser(req).then(function(data) {
				});
				GenericAlertService.alertMessage('User(s) deactivated successfully', 'Info');
				angular.forEach(editUsers, function(value, key) {
					$scope.users.splice($scope.users.indexOf(value), 1);
					editUsers = [];
					$scope.deleteIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('users are failed to Deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(editUsers, function(value) {
					value.select = false;
				})
			})

		}
	}

	$scope.activeUser = function() {
		if (editUsers.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to Activate', "Warning");
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.users = [];
		} else {
			angular.forEach(editUsers, function(value, key) {
				deleteIds.push(value.userId);
			});
			var req = {
				"userIds" : deleteIds,
				"status" : 1
			};
			UserService.deleteUser(req).then(function(data) {
			});
			GenericAlertService.alertMessage('users are Activated successfully', 'Info');
			angular.forEach(editUsers, function(value, key) {
				$scope.users.splice($scope.users.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('users are failed to Activate', "Error");
			});
			editUsers = [];
			$scope.deleteIds = [];

		}
	}

	$scope.rowSelect = function(user) {

		if (user.select) {
			editUsers.push(user);
		} else {
			editUsers.pop(user);
		}
	}

	$scope.setSelected = function(row) {
		$scope.selectedRow = row;
	}

	var addservice = {};
	var adduserpopup = [];
	$scope.addUsers = function(actionType) {
		adduserpopup = addservice.addUserDetails(actionType, editUsers);
		adduserpopup.then(function(data) {
			$scope.users = data.users;
			editUsers = [];
		});
	}
	addservice.addUserDetails = function(actionType, editUsersList) {
		if ($scope.exceededMaxUsers && (actionType != 'Edit')) {
			GenericAlertService.alertMessage('Maximum registered usres limit exceeded.', "Warning");
		} else {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editUsersList <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to edit', "Warning");
			return;
		}
		ngDialog.open({
			template : 'views/users/addclientuserpopup.html',
			scope : $scope,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.addUsers = [];
				var copyEditArray = angular.copy(editUsersList);
				$scope.userUniqueMap = [];
				$scope.roles = [];
				var selectedUser = [];
				var req = {
					status : 1
				};
				RoleService.getRoles(req).then(function(data) {
					$scope.roles = data.roleTOs;
				});
				$scope.updateRoles = function(data, user) {
					user.roleId = data.id;
				}
				if (actionType === 'Add') {
					$scope.addUsers.push({
						"userId" : null,
						"select" : false,
						"status" : 1,
						"userName" : '',
						"password" : '',
						"empCode" : '',
						"empDesg" : '',
						"dispName" : '',
						"firstName" : '',
						"lastName" : '',
						"email" : '',
						"phone" : '',
						"mobile" : '',
						"remarks" : '',
						"userRoles" : []
					});
				} else {
					$scope.addUsers = angular.copy(editUsersList);
					editUsersList = [];
				}
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedUser.push(tab);
					} else {
						selectedUser.pop(tab);
					}
				}, $scope.deleteRows = function() {
					if (selectedUser.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedUser.length < $scope.addUsers.length) {
						angular.forEach(selectedUser, function(value, key) {
							$scope.addUsers.splice($scope.addUsers.indexOf(value), 1);
						});
						selectedUser = [];
						GenericAlertService.alertMessage('Rows deleted successfully', "Info");
					} else if (selectedUser.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedUser.length == 1) {
						$scope.addUsers[0] = {
							"userId" : null,
							"select" : false,
							"status" : 1,
							"userName" : '',
							"password" : '',
							"empCode" : '',
							"empDesg" : '',
							"dispName" : '',
							"firstName" : '',
							"lastName" : '',
							"email" : '',
							"phone" : '',
							"mobile" : '',
							"remarks" : '',
							"userRoles" : []
						};
						selectedUser = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.name = null;
				var servicevar = {};
				var popVar = [];
				$scope.rolesdisplay = function(user) {
					popVar = servicevar.getRoles();
					popVar.then(function(data) {
						user.userRoles = [];
						user.userRoles.push(data.userRoles);
						user.roleDisplay = data.userRoles.roleName;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Roles details", 'Error');
					});

				}

				servicevar.getRoles = function() {
					var deferred = $q.defer();
					ngDialog.open({
						template : 'views/users/rolepopup.html',
						scope : $scope,
						className : 'ngdialog-theme-plain ng-dialogueCustom7',
						showClose : false,
						closeByDocument : false,
						controller : [ '$scope', function($scope) {
							$scope.roleLlist = angular.copy($scope.roles);
							$scope.selectedRole = function(role) {
								var returnPopObj = {
									"userRoles" : role
								};
								$scope.closeThisDialog();
								deferred.resolve(returnPopObj);
							}
						} ]

					});
					return deferred.promise;

				}, $scope.addRows = function() {
					$scope.addUsers.push({
						"userId" : null,
						"select" : false,
						"status" : 1,
						"userName" : '',
						"password" : '',
						"empCode" : '',
						"empDesg" : '',
						"dispName" : '',
						"firstName" : '',
						"lastName" : '',
						"email" : '',
						"phone" : '',
						"mobile" : '',
						"remarks" : '',
						"userRoles" : []
					});

				}, $scope.getUserServiceMap = function() {
					var req = {};
					UserService.getUserServiceMap(req).then(function(data) {
						$scope.userUniqueMap = data.userUniqueMap;
					});

				}, $scope.checkDuplicate = function(user) {
					user.duplicateFlag = false;
					// user.userName = user.userName.toUpperCase();
					user.empCode = user.empCode.toUpperCase();
					var unique = user.userName + "-" + user.empCode;
					if ($scope.userUniqueMap[unique] != null) {
						user.duplicateFlag = true;
						return;
					}
					user.duplicateFlag = false;
				}
				$scope.saveUser = function() {
					var flag = false;
					var userClassMap = [];
					console.log($scope.users);
					angular.forEach($scope.addUsers, function(value, key) {
						value.empCode = (value.empCode) ? value.empCode : "";
						if ($scope.userUniqueMap[value.userName.toUpperCase() + "-" + value.empCode.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							userClassMap[value.userName.toUpperCase() + "-" + value.empCode.toUpperCase()] = true;
						}
					});
					angular.forEach($scope.addUsers, function(value, key) {
						value.empCode = (value.empCode) ? value.empCode : "";
						if ($scope.userUniqueMap[value.userName.toUpperCase() + "-" + value.empCode.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						}
					});
					$scope.errMessage = "";
					$scope.duplicateMobile = false;
					if (actionType === 'Add') {
						angular.forEach($scope.addUsers, function(value, key) {
							value.empCode = (value.empCode) ? value.empCode : "";
							if ($scope.userUniqueMap[value.userName.toUpperCase() + "-" + value.empCode.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
						angular.forEach($scope.users,function(value,key){
							if( value.userName == $scope.addUsers[0].userName )
							{
								$scope.addUsers.duplicateFlag = true;
								flag = true;
								$scope.errMessage += "Duplicate User Names are not allowed";
							}
							if( value.email == $scope.addUsers[0].email )
							{
								$scope.addUsers.duplicateFlag = true;
								flag = true;
								if( !$scope.duplicateEmail )
								{
									$scope.errMessage += ( $scope.errMessage != "" ) ? "\nDuplicate Email are not allowed" :"Duplicate Email are not allowed";
									$scope.duplicateEmail = true;
								}								
							}
							if( value.mobile == $scope.addUsers[0].mobile )
							{
								$scope.addUsers.duplicateFlag = true;
								flag = true;
								if( !$scope.duplicateMobile )
								{
									$scope.errMessage += ( $scope.errMessage != "" ) ? "\nDuplicate Mobile Number are not allowed" :"Duplicate Mobile Number are not allowed";
									$scope.duplicateMobile = true;
								}								
							}
						});
					} else {
						angular.forEach($scope.addUsers, function(value, key) {
							angular.forEach(copyEditArray, function(value1, key) {
								if (value1.userName == value.userName && value1.empCode == value.empCode) {
									value.duplicateFlag = false;
									flag = false;
								} else {
									value.empCode = (value.empCode) ? value.empCode : "";
									if ($scope.userUniqueMap[value.userName.toUpperCase() + "-" + value.empCode.toUpperCase()] != null) {
										value.duplicateFlag = true;
										flag = true;
									} else {
										value.duplicateFlag = false;
										userClassMap[value.userName.toUpperCase() + "-" + value.empCode.toUpperCase()] = true;
									}
								}
							});
						});
					}
					if (flag) {
						GenericAlertService.alertMessage($scope.errMessage, "Warning");
						return;
					}

					var req = {
						"userTOs" : $scope.addUsers,
					}
					var results = [];
					blockUI.start();
					UserService.saveUser(req).then(function(data) {
						blockUI.stop();
						results = data.users;
						// var succMsg = GenericAlertService.alertMessageModal('User Details ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('User Details saved successfully',"Info");
						succMsg.then(function(data1) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"users" : results
							}
							deferred.resolve(returnPopObj);
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('User Details  are failed to save', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	}, $scope.getUserProjects = function(user) {
		$scope.userProjects = [];
		$scope.flag1= true;

		$scope.changeField = function(proj) {
			if (proj.usrProj) {
			$scope.flag1= false;
			}
			else{

				$scope.flag1= false;


			}
		}
		var req = {
			"userId" : user.userId,
			"status" : 1
		};


		UserService.getUserProjects(req).then(function(data) {
			$scope.userProjects = data.userProjDetailsTOs;
			$scope.copyProjects = angular.copy($scope.userProjects);
			/*
			 * $scope.c = 0;
			 * angular.forEach($scope.userProjects,function(value,key){
			 * if(value.usrProj) { $scope.c = $scope.c +1;
			 *
			 *
			 */


		});

	}

	$scope.assignProjects = function(userProjects) {	
		var count=0;
		for(var i=0;i<userProjects.length;i++){
			if(userProjects[i].usrProj== true){
				count++;
			}
		}	

		//if (userProjects.length <= 0) {
			if(count<=0){
			GenericAlertService.alertMessage('Please select atleast one project to assign ', "Warning");
			return;
		}


		/*
		 * angular.forEach($scope.copyProjects, function(value, key) {
		 * angular.forEach(userProjects, function(value1, key) {
		 * if(value1.projName == value.projName && (value1.usrProj &&
		 * value.usrProj==true)){ var result = (value1.projName ==
		 * value.projName && (value1.usrProj && value.usrProj==true)); flag =
		 * true;
		 * }else{ if ($scope.userUniqueMap[value.userName.toUpperCase() +
		 * "-" + value.empCode.toUpperCase] != null) {
		 * value.duplicateFlag = true; flag = true; } else { value.duplicateFlag =
		 * false; userClassMap[value.userName.toUpperCase() + "-" +
		 * value.empCode.toUpperCase()] = true; } } }); }); if(flag){
		 * GenericAlertService.alertMessage('Please select/deselect projects',
		 * "Warning"); return; }else{ flag = false; }
		 */
		/*
		 * var c1 = 0; angular.forEach(userProjects,function(value,key){
		 * if(value.usrProj) { c1 = c1 +1; console.log(JSON.stringify(c1)) } });
		 * if($scope.c==c1){ GenericAlertService.alertMessage('Please
		 * select/deselect projects', "Warning"); return; }
		 */

		var req = {
			"userProjectTOs" : userProjects
		};


		blockUI.start();

		UserService.saveUserProjects(req).then(function(data) {
			blockUI.stop();
			GenericAlertService.alertMessage(count+ ' Projects Assigned Successfully', "Info");	
			//window.location.reload();	
			 $state.go($state.current, {}, {reload: true});
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Projects Assigned Failed', "Error");
		});
	}, $scope.tabActivate = function(tabName) {
		$scope.tabopen = tabName;
	}, $scope.show = function(remarks) {
		ngDialog.open({
			template : 'views/users/viewpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.remarks = remarks;
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
			template: 'views/help&tutorials/adminhelp/userlisthelp.html',
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
