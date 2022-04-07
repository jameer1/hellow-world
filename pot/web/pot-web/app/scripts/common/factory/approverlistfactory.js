'use strict';
//This file is to display the list of users based on Project Id
app.factory('ApproverListUserFactory', ["ngDialog", "$q", "PotCommonService", "GenericAlertService", function(ngDialog, $q, PotCommonService, GenericAlertService) {
	var service = {}; 
	service.getProjUsersByIdOnly = function(getReq) {
		var deferred1 = $q.defer();

		PotCommonService.getUsersByProjectId(getReq).then(function(data) {
			console.log(getReq);
			console.log(data);
			for(var i=0;i<data.labelKeyTOs.length;i++)
			{
				data.labelKeyTOs[i].displayNamesMap.userEmail = data.labelKeyTOs[i].email; 
			}
			var popupData = service.openUserPopup(data.labelKeyTOs,getReq.requestType);
			popupData.then(function(data) {
				var returnPopObj = {
					"approverTo" : data
				};
				deferred1.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting Approvers", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting Approvers", 'Error');
		});		
		return deferred1.promise;
	}, service.openUserPopup = function(userList,requestType) {
		var deferred = $q.defer();
		var approverUserPopup = ngDialog.open({
			template : 'views/common/approveusers.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.userList = userList;
				$scope.requestType = requestType;
				$scope.selectedUser = function(approverTo) {
					deferred.resolve(approverTo);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);