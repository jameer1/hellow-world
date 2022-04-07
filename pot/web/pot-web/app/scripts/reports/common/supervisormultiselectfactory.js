'use strict';

app.factory('ModuleUserMultiSelectFactory', ["ngDialog", "$q", "PotCommonService", "GenericAlertService", function(ngDialog, $q, PotCommonService, GenericAlertService) {
	var service = {};
	service.getUsersByModulePermission = function(getReq) {
		var deferred = $q.defer();
		var result = PotCommonService.getUsersByModulePermission(getReq).then(function(data) {
			var popup = service.openPopUp(data.users);
			popup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting users", 'Error');
			});

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting users", 'Error');
		});
		return deferred.promise;

	}, service.openPopUp = function(userList) {
		var deferred = $q.defer();
		var approverUserPopup = ngDialog.open({
			template : 'views/reports/common/multiselectmoduleusers.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.userList = userList;
				var selectedUsers = [];
				var selectedUserIds = [];
				var userName = '';
				$scope.selectedUser = function(userTO) {
					if (userTO.select) {
						selectedUsers.push(userTO);
					} else {
						selectedUsers.pop(userTO);
					}
				},

				$scope.selectAllUsers = function() {
					if ($scope.selectAll) {
						angular.forEach($scope.userList, function(value, key) {
							value.select = true;
							selectedUsers.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.userList, function(value, key) {
							value.select = false;
						});
						selectedUsers = [];
					}
				},

				$scope.addUsers = function() {
					angular.forEach(selectedUsers, function(value, key) {

						selectedUserIds.push(value.id);
						userName = userName + value.name + ",";
					});

					var returnPopObj = {
						"userLabelKeyTO" : {
							"userIds" : selectedUserIds,
							"userName" : userName.slice(0, -1),
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);