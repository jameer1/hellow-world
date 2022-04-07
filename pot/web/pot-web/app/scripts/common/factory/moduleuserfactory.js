'use strict';

app.factory('ModuleUserFactory', ["ngDialog", "$q", "PotCommonService", "GenericAlertService", function(ngDialog, $q, PotCommonService, GenericAlertService) {
	var service = {};
	service.getUsersByModulePermission = function(getReq) {
		var deferred1 = $q.defer();
		var result = PotCommonService.getUsersByModulePermission(getReq).then(function(data) {
			var popup = service.openUserPopup(data.users);
			popup.then(function(data) {
				var returnPopObj = {
					"userLabelKeyTO" : data
				};
				deferred1.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting users", 'Error');
			});

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting users", 'Error');
		});
		return deferred1.promise;

	}, service.openUserPopup = function(userList) {
		var deferred = $q.defer();
		var approverUserPopup = ngDialog.open({
			template : 'views/common/moduleusers.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.userList = userList;
				$scope.selectedUser = function(userTO) {
					deferred.resolve(userTO);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);