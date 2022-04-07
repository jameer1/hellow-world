'use strict';
/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("store", {
		url: '/store',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/storeandstock/storestock.html',
				controller: 'StoreStockController'
			}
		}
	})
}]).controller('StoreStockController', ["$scope", "$q", "utilservice", "ngDialog", "blockUI", "StoreService", "GenericAlertService","stylesService", "ngGridService", function ($scope, $q, utilservice, ngDialog, blockUI, StoreService, GenericAlertService,stylesService, ngGridService) {
	var editStocks = [];
	$scope.stock = [];
	$scope.stylesSvc = stylesService;
	var deferred = $q.defer();
	$scope.sortType = "code"
	$scope.stocks = [];
	$scope.deleteIds = [];
	$scope.uniqueCodeMap = [];
	$scope.storeReq = {
		"stockCode": null,
		"stackName": null,
		"status": "1"
	}

	$scope.storeSearch = function (click) {
		StoreService.getStocks($scope.storeReq).then(function (data) {
			editStocks = [];
			// $scope.activeFlag = 0;
			
			$scope.stocks = data.stockAndStoreTOs;
			// var dummyStock = angular.copy($scope.stocks).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{item.status = 'Inactive'}
			// return item;
			// });
			$scope.gridOptions.data = $scope.stocks;
			if(click=='click'){
				if ($scope.stocks.length <= 0) {
					GenericAlertService.alertMessage('Warehouse & Stockyard List Details are not available for given search criteria', "Warning");
					return;
				}
			}
			// if ($scope.stocks != null && $scope.stocks.length > 0) {
			// 	if ($scope.stocks[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.stocks[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.storeReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.storeReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.storeReq = {
			// 		"stockCode" : null,
			// 		"stackName" : null,
			// 		"status" : "1"
			// 	}
			// }
		});
	},

		$scope.reset = function () {
			$scope.storeReq = {
				"stockCode ": null,
				"stackName ": null,
				"status": "1"
			}
			// $scope.activeFlag = 0;
			$scope.storeSearch();
		}, $scope.rowSelect = function (stock) {
			if (stock.selected) {
				utilservice.addItemToArray(editStocks, "id", stock);
			} else {
				editStocks.splice(editStocks.indexOf(stock), 1);

			}
		}
	var service = {};
	var storePopUp;
	$scope.addStocks = function (actionType) {
		angular.forEach(editStocks, function (value, key) {
			value.selected = false;
		});
		if (editStocks.length > 0 && actionType == "Add") {
			editStocks = [];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		storePopUp = service.addStores(actionType);
		storePopUp.then(function (data) {
			$scope.stocks = data.stockAndStoreTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting store details", 'Error');
		});
	}
	service.addStores = function (actionType) {
		var deferred = $q.defer();
		if (actionType === 'Edit' && editStocks.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		storePopUp = ngDialog.open({
			template: 'views/centrallib/storeandstock/addstorestockpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $scope,
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', 'generalservice', function ($scope, generalservice) {
				var selectedStocks = [];
				var stockCategories = generalservice.getStockCategories();
				$scope.action = actionType;
				$scope.addStocks = [];
				var copyEditArray = angular.copy(editStocks);
				if (actionType === 'Add') {
					$scope.addStocks.push({
						"code": '',
						"desc": '',
						"category": stockCategories[0],
						"status": 1,
						"selected": false
					});
				} else {
					$scope.addStocks = angular.copy(editStocks)
					editStocks = [];
				}
				$scope.addRows = function () {
					$scope.addStocks.push({
						"code": '',
						"desc": '',
						"category": stockCategories[0],
						"status": 1,
						"selected": false
					});
				}, $scope.stockCategoryOnLoad = function () {
					$scope.stockCategories = stockCategories;
				}, $scope.getWareHouseMap = function () {
					var req = {

					}
					StoreService.getWareHouseMap(req).then(function (data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				}, $scope.checkDuplicate = function (store) {
					store.duplicateFlag = false;
					store.code = store.code.toUpperCase();
					if ($scope.uniqueCodeMap[store.code + " " + store.category] != null) {
						store.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate Store Stock codes are not allowed', "Warning");
						return;
					}
					store.duplicateFlag = false;
				}, $scope.saveStocks = function (storeform) {
					var flag = false;
					var storeStockMap = [];
					angular.forEach($scope.addStocks, function (value, key) {
						if (storeStockMap[value.code.toUpperCase() + " " + value.category] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							storeStockMap[value.code.toUpperCase() + " " + value.category] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.addStocks, function (value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase() + " " + value.category] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Store Stock codes are not allowed', "Warning");
						return;
					}
					var req = {
						"stockAndStoreTOs": $scope.addStocks
					}
					var results = [];
					blockUI.start();
					StoreService.saveStocks(req).then(function (data) {
						blockUI.stop();
						results = data.stockAndStoreTOs;
						$scope.storeSearch();
						// var succMsg = GenericAlertService.alertMessageModal('Store and stock(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Store and stock(s) saved successfully',"Info");
						succMsg.then(function (data1) {
							$scope.closeThisDialog(storePopUp);
							var returnPopObj = {
								"stockAndStoreTOs": results
							}
							deferred.resolve(returnPopObj);
						})
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Store and stock(s)  is/are failed to save', "Error");
					});
				}
				$scope.popUpRowSelect = function (stock) {
					if (stock.selected) {
						selectedStocks.push(stock);
					} else {
						selectedStocks.splice(selectedStocks.indexOf(stock), 1);
					}
				}, $scope.deleteRows = function () {
					if (selectedStocks.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedStocks.length < $scope.addStocks.length) {
						angular.forEach(selectedStocks, function (value, key) {
							$scope.addStocks.splice($scope.addStocks.indexOf(value), 1);

						});
						selectedStocks = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedStocks.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedStocks.length == 1) {
						$scope.addStocks[0] = {
							"code": '',
							"desc": '',
							"status": 1,
							"selected": false
						};
						selectedStocks = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			}]
		});
		return deferred.promise;
	}, $scope.activeStocks = function () {
		if (editStocks.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.stocks = [];
		} else {
			angular.forEach(editStocks, function (value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"stockProjsIds": deleteIds,
				"status": 1
			};
			StoreService.deleteStocks(req).then(function (data) {

			});
			GenericAlertService.alertMessage('Store and stock(s) activated successfully', 'Info');
			angular.forEach(editStocks, function (value, key) {
				$scope.stocks.splice($scope.stocks.indexOf(value), 1);
			}, function (error) {
				GenericAlertService.alertMessage('Store and stock(s) is/are failed to activate', "Error");
			});
			editStocks = [];
			$scope.deleteIds = [];

		}
	}

	$scope.deleteStocks = function () {
		if (editStocks.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.stocks = [];
		} else {
			angular.forEach(editStocks, function (value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"stockProjsIds": deleteIds,
				"status": 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				StoreService.deleteStocks(req).then(function (data) {
                $scope.storeSearch();
				});
				GenericAlertService.alertMessage('Store and stock(s) deactivated successfully', 'Info');
				angular.forEach(editStocks, function (value, key) {
					$scope.stocks.splice($scope.stocks.indexOf(value), 1);
					editStocks = [];
					$scope.deleteIds = [];
				}, function (error) {
					GenericAlertService.alertMessage('Store and stock(s) is/are failed to deactivate', "Error");
				});
			}, function (data) {
				angular.forEach(editStocks, function (value) {
					value.selected = false;
				})
			})

		}
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'code', displayName: "Store/Location ID", headerTooltip: "Store/Location ID"},
						{ field: 'desc', displayName: "Store/Location Name", headerTooltip: "Store/Location Name", },
						{ field: 'category', displayName: "Category", headerTooltip: "Category", },
						{ field: 'status',displayName: "Status",cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_stockyard");
					$scope.storeSearch();
					
				}
			});
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
			template: 'views/help&tutorials/warehousehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
