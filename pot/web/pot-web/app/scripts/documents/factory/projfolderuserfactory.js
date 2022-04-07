'use strict';

app.factory(

'ProjFolderUserFactory', ["ngDialog", "$q", "blockUI", "GenericAlertService", "DocumentService", function(ngDialog, $q,blockUI, GenericAlertService, DocumentService) {
	var service = {};
	var approverUserPopup;
	var projectId;
	
	service.getProjDocFolderPermissions = function(selectedFolders, selectedFolderType, projId) {
	
		var deferred = $q.defer();
		var req = {
			"projId" : projId,
			"status" : 1,
			"id" : selectedFolders[0]
		};		
		projectId = projId;
		 DocumentService.getProjDocFolderPermissions(req).then(function(data) {
			var popup = service.openUserPopup(data.projDocFolderPermissionTOs, selectedFolders, selectedFolderType, projId);
			popup.then(function(data) {
				
				deferred.resolve(data);
				
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting users", 'Error');
			});

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting users", 'Error');
		});
		return deferred.promise;

	}, service.openUserPopup = function(projDocFolderPermissionTOs , selectedFolders, selectedFolderType, projId) {
		
		var deferred = $q.defer();
		var approverUserPopup = ngDialog.open({
			template : 'views/documents/projfolderuser.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : ['$scope', function($scope) {
				$scope.selectedFolders=selectedFolders;
				$scope.selectedFolderType = selectedFolderType[0];
				console.log($scope.selectedFolderType);
				$scope.projDocFolderPermissionTOs = projDocFolderPermissionTOs ;
				var selectedUsersMap = [];
				$scope.selectedUser = function(userTO) {
					selectedUsersMap[userTO.labelKeyTO.id] = userTO;
				},
				
				$scope.saveFodlerUserPermissions = function() {
					var saveUserReq = [];
					angular.forEach($scope.selectedFolders, function(folderObj, key) {
						angular.forEach(selectedUsersMap, function(value, key) {
							saveUserReq.push({
								"status" : 1,
								"folderId" : folderObj,
								"readAccess" : value.readAccess,
								"writeAccess" : value.writeAccess,
								"projId" : projId,
								"userId" : key
							});
						});
					});
					
					var req = {
						"projDocFolderPermissionTOs" : saveUserReq,
						"folderId" : selectedFolders[0]
					};
					console.log("selected project id"+projectId);
					blockUI.start();
					DocumentService.saveProjDocFolderPermissions(req).then(function(data) {
						blockUI.stop();
						// var succMsg = GenericAlertService.alertMessageModal('User Permissions Details is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('User Permissions Details saved successfully',"Info" );
						succMsg.then(function(data) {
							$scope.closeThisDialog();
							deferred.resolve(data);
						}, function(error) {
							blockUI.stop();
							GenericAlertService.alertMessage("Error occured while saving User Permissions", "Error");
						});
					});

				}

			}]
		});
		return deferred.promise;
	};
	return service;

}]);