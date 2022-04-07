'use strict';
app.factory('ProjStocksPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjStoreStockService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, ProjStoreStockService, GenericAlertService, generalservice) {
	var projStoreClassifyPopUp;
	var service = {};
	service.projStoreClassifyPopUp = function(actionType, selectedProject, editStoreData) {
		var deferred = $q.defer();
		projStoreClassifyPopUp = ngDialog.open({
			template : 'views/projectlib/storeandstock/storeyardpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var copyEditArray = angular.copy(editStoreData);
				$scope.addstoreyard = [];
				$scope.proCategory = [];
				var emptyProjStoreClassifyObj = {};
				var selectedStore = [];
				$scope.action = actionType;
				if (actionType === 'Edit') {
					$scope.addstoreyard = angular.copy(editStoreData);
				}
				var req = {
					"status" : 1,
					"projId" : selectedProject.projId
				};
				ProjStoreStockService.projStoreStockifOnLoad(req).then(function(data) {
					// $scope.proCategory = data.categoriesTOs;
					
					if (actionType === 'Add') {
						emptyProjStoreClassifyObj = data.projStoreStockTO;
						var localprojStoreStockTO = angular.copy(emptyProjStoreClassifyObj);
						$scope.addstoreyard.push(localprojStoreStockTO);
					}
				});
				$scope.addstoreyardClass = function() {
					var localprojStoreStockTO = angular.copy(emptyProjStoreClassifyObj);
					$scope.addstoreyard.push(localprojStoreStockTO);
				}
			
				$scope.getStoreMap = function() {
				var req = {
					"status" : 1,
					"projId" : selectedProject.projId
				}
				ProjStoreStockService.getProjStockPileMap(req).then(function(data) {
					$scope.uniqueCodeMap = data.projUniqueCodeMap;
				})
			}, $scope.checkDuplicate = function(tab) {
				tab.duplicateFlag = false;
				tab.code = tab.code.toUpperCase();
				if ($scope.uniqueCodeMap[tab.code] != null) {
					tab.duplicateFlag = true;
					return;
				}
				tab.duplicateFlag = false;
			},
				$scope.projStockPileOnLoad = function() {
					$scope.proCategory = generalservice.getStockCategories();
				},
				$scope.saveProjStoreStocks = function() {
					var flag = false;
					var storeMap = [];
				
					angular.forEach($scope.addstoreyard, function(value, key) {
						if (storeMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							storeMap[value.code.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add'){	
						angular.forEach($scope.addstoreyard, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
						}else{	
							angular.forEach($scope.addstoreyard, function(value, key) {
								angular.forEach(copyEditArray, function(value1, key) {
									if(value1.code == value.code ){
										value.duplicateFlag = false;
										flag = false;
									}else{
										if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
											value.duplicateFlag = true;
											flag = true;
										} else {
											value.duplicateFlag = false;
											storeMap[value.code.toUpperCase()] = true;
										}
									}
								});
							});
						}
						if (flag) {
							GenericAlertService.alertMessage('Duplicate StoreLocations are not allowed', "Warning");
							return;
						}
					var storeProjSaveReq = {
						"projStoreStockTOs" : $scope.addstoreyard,
						"projId" : selectedProject.projId
					};
					blockUI.start();
					ProjStoreStockService.saveProjStoreStocks(storeProjSaveReq).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var projectClassTOs = data.projStoreStockTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Store Stock(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Store Stock(s) saved successfully ',"Info");
							succMsg.then(function(data) {
								$scope.closeThisDialog();
								var returnPopObj = {
									"projStoreStockTOs" : projectClassTOs
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Store and Stocks(s) is/are failed to Save', 'Error');
					});
				}, $scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedStore.push(tab);
					} else {
						selectedStore.splice(selectedStore.indexOf(tab), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedStore.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedStore.length < $scope.addstoreyard.length) {
						angular.forEach(selectedStore, function(value, key) {
							$scope.addstoreyard.splice($scope.addstoreyard.indexOf(value), 1);
						});
						selectedStore = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if(selectedStore.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
					}
					else if(selectedStore.length == 1){
					$scope.addstoreyard[0] = {
						'code' : '',
						'desc' : '',
						'status' : '1',
						'selected' : false
					};
					selectedStore = [];
					GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
				}

				}

			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
