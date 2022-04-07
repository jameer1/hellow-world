'use strict';
app.factory('PlantTranserDetailsFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, GenericAlertService, PlantRegisterService) {
//	$scope.users = [];
	var service = {};
	var users = [];
	service.getPlantTransferReqDetails = function(req, plantTransferExistingMap) {
		var deferred = $q.defer();
		var resultData = PlantRegisterService.getPlantTransferReqDetails(req);
		resultData.then(function(data) {
			let userList =  data.labelKeyTOs;
			for(let j=0;j<userList.length;j++){
				if(userList[j].displayNamesMap.expectedDate != null ){
					users.push(userList[j]);
				}
			}
	//		var resultPopupData = service.openPopup(data.labelKeyTOs, plantTransferExistingMap);
			var resultPopupData = service.openPopup(users, plantTransferExistingMap);
			resultPopupData.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting project plants", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting project plants", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function(labelKeyTOs, plantTransferExistingMap) {
		var deferred = $q.defer();
		var plantDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projplantreg/plantnewrequest/plantregdetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
			controller : [ '$scope', function($scope) {
	//			$scope.labelKeyTOs = labelKeyTOs;
				$scope.labelKeyTOs = users;
				$scope.plantTransferExistingMap = plantTransferExistingMap;
				var selectedPlants = [];
				var transferPlantMap = [];
				users = [];
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedPlants.push(tab);
						transferPlantMap[tab.id] = tab;
					} else {
						selectedPlants.pop(tab);
						transferPlantMap[tab.id] = null;
					}
				}
				$scope.addToTransferRequest = function() {
					var returnPopObj = {
						"plantRegisterDtlTOs" : angular.copy(selectedPlants),
						"transferPlantMap" : transferPlantMap
					};
					deferred.resolve(returnPopObj);
					
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
