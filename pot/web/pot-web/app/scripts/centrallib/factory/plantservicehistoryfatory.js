'use strict';

app.factory('PlantServiceHistoryFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantServiceHistoryService", "GenericAlertService", "TreeService", function(ngDialog, $q, $filter, $timeout, blockUI,$rootScope, 
	PlantServiceHistoryService, GenericAlertService, TreeService) {
	var plantServiceHistoryPopUp;
	var plantPopUpService = {};

	plantPopUpService.addPlantServiceHistoryDetails = function(actionType, itemData) {
		var deferred = $q.defer();
		plantServiceHistoryPopUp = ngDialog.open({
			template : 'views/centrallib/plantservicehistory/plantservicehistorypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom5',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.editPlantData = [];
				$scope.plantServiceHistoryData = [];
				$scope.uniqueCodeMap = [];
				$scope.projPlantId = null;
				var palntData = [];
				if (actionType === "Add") {
					$scope.editPlantData.push({
						"select" : false,
						"parentId" : null,
						"status" : 1,
						"deleteFlag" : false,
						"item" : false,
						"code" : '',
						"name" : '',
						"plantServiceClassificationMstrTOs" : []
					});
				} else {
					$scope.editPlantData = angular.copy([ itemData ]);
					itemData = [];
				}
				$scope.editPlantData = TreeService.populateDynamicTreeData($scope.editPlantData, 0, [], 'code',
					'plantServiceClassificationMstrTOs');
				$scope.addPlantSubGroup = function(tabData, indexValue) {
						if(tabData.code==null || tabData.code=="" ||  tabData.name==""  || tabData.name==null){
							GenericAlertService.alertMessage('Please enter Category Id and Category Name', "Warning");
							tabData.break();
							return;
						}
						angular.forEach($scope.editPlantData,function(value){
							if(value.code=="" ||  value.name==""){
								GenericAlertService.alertMessage('Please enter Category Id and Category Name', "Warning");
								value.break();
								return;
							}
						})
							
					const itemToAdd = angular.copy({
						"select": false,
						"parentId": tabData.id,
						"parentIndex": indexValue,
						"status": 1,
						"deleteFlag": false,
						"item": true,
						"code": '',
						"name": '',
						"plantServiceClassificationMstrTOs": [],
						"level" : 1
					});
					$scope.editPlantData = TreeService.addItemToTree($scope.editPlantData, tabData, itemToAdd,
						indexValue);
				},

				$scope.deletePlantGroup = function(index) {
					TreeService.deleteDynamicTreeItem($scope.editPlantData, index);
				}, 

				$scope.plantServiceItemClick = function (item, collapse) {
					TreeService.dynamicTreeItemClick($scope.editPlantData, item, collapse);
				};
				
				$scope.getPlantServiceHistoryMap = function() {
					var req = {}
					PlantServiceHistoryService.getPlantServiceHistoryMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				},

				$scope.checkDuplicate = function(tab) {
					tab.duplicateFlag = false;
					tab.code = tab.code.toUpperCase();
					if ($scope.uniqueCodeMap[tab.code] != null) {
						tab.duplicateFlag = true;
						return;
					}
					tab.duplicateFlag = false;
				}, $scope.savePlantServiceHistoryDetails = function(plantServiceHistoryForm) {
					var flag = false;
					var plantHistoryMap = [];
					angular.forEach($scope.editPlantData, function(value, key) {
						if (plantHistoryMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							plantHistoryMap[value.code.toUpperCase()] = true;
						}
					});
					if (actionType === "Add") {
						angular.forEach($scope.editPlantData, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()]) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					} 
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Plant Service History  codes are not allowed', "Warning");
						return;
					}
					const data = TreeService.extractTreeDataForSaving($scope.editPlantData, 
						            'plantServiceClassificationMstrTOs');
					var req = {
						"plantServiceClassificationMstrTOs" :data ,
						"status" : 1
					}
					blockUI.start();
					PlantServiceHistoryService.savePlantServiceHistoryRecords(req).then(function(data) {
						blockUI.stop();
						var results = data.plantServiceClassificationMstrTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Plant Service History is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant Service History saved successfully ',"Info");
						succMsg.then(function(data) {
							$scope.closeThisDialog(plantServiceHistoryPopUp);
							var returnPopObj = {
								"plantServiceClassificationMstrTOs" : results
							}
							deferred.resolve(returnPopObj);
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant Service Histroy is/are failed to save', "Error");
					});

				}
			} ]

		});
		return deferred.promise;
	}
	return plantPopUpService;
}]);