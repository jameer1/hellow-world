'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetsmaintenancecategory", {
		url : '/assetsmaintenancecategory',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/assetsmaintenance/assetmaintenancecategory.html',
				controller : 'AssetsMaintenanceCategoryController'
			}
		}
	})
}]).controller("AssetsMaintenanceCategoryController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "AssetsMaintenanceCategoryService", "GenericAlertService", "TreeService", "utilservice", function($rootScope, $scope, $q, $state, ngDialog, blockUI, 
	AssetsMaintenanceCategoryService, GenericAlertService, TreeService, utilservice) {
	var deleteAssetMaintenanceData = [];
	$scope.AssetsMaintenanceData = [];
	$scope.sortType = 'code',
	$scope.sortReverse = false;
	$scope.searchProject = {};
	$scope.assetMaintenanceSearch = { 'name': "", 'code': "" };
	let assetMaintenanceDataCopy = null;
	var deferred = $q.defer();

	$scope.getAssetMaintenanceCategory = function() {
		var req = {
			"status" : 1
		};
		AssetsMaintenanceCategoryService.getAssetMaintenanceCategory(req).then(function(data) {
			deleteAssetMaintenanceData = [];
			assetMaintenanceDataCopy = angular.copy(data.assetMaintenanceCategoryTOs);
			$scope.AssetsMaintenanceData = populateAssetMaintananceData(data.assetMaintenanceCategoryTOs,0,[]);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Assets Maintenance Category", 'Error');
		});
	}, $scope.assetMaintenanceCategorySelect = function(tab) {
		if (tab.select) {
			utilservice.addItemKeyValueToArray(deleteAssetMaintenanceData, "id", tab);
		} else {
			deleteAssetMaintenanceData.splice(deleteAssetMaintenanceData.indexOf(tab.id), 1);
		}
	}

	function populateAssetMaintananceData(data, level, assetMaintananceTOs) {
		return TreeService.populateTreeData(data, level, assetMaintananceTOs, 'code',
			'childAssetMaintenanceCategoryTOs')
	};

	$scope.SearchAssetMaintenanceCategory = function () {
		
		if ($scope.assetMaintenanceSearch.code || $scope.assetMaintenanceSearch.name) {
			//$scope.getAssetMaintenanceCategory();
			const assetMaintenanceTOs = new Array();
			for (const assetObj of assetMaintenanceDataCopy) {
				if (($scope.assetMaintenanceSearch.code && assetObj.code && assetObj.code.toUpperCase() ===
					$scope.assetMaintenanceSearch.code.toUpperCase())
					|| ($scope.assetMaintenanceSearch.name && assetObj.name && assetObj.name.toUpperCase() ===
						$scope.assetMaintenanceSearch.name.toUpperCase())) {
					assetMaintenanceTOs.push(assetObj);
				}
			}
			$scope.AssetsMaintenanceData = populateAssetMaintananceData(assetMaintenanceTOs, 0, []);
			if ($scope.AssetsMaintenanceData.length <= 0) {
				GenericAlertService.alertMessage('Assets Maintenance And Repair Category Details are not available for given search criteria', "Warning");
				return;
			}
		}
	};


	$scope.resetAssetMaintenanceCategory = function () {
		$scope.AssetsMaintenanceData = [];
		$scope.assetMaintenanceSearch = { 'name': "", 'code': "" };
		$scope.AssetsMaintenanceData = populateAssetMaintananceData(angular.copy(assetMaintenanceDataCopy), 0, []);
	};

	var assetspopup;
	var addAssetCategoryservice = {};
	$scope.editAssetMaintenanceCategory = function(actionType, itemData) {
		assetspopup = addAssetCategoryservice.addAssetMaintenanceDetails(actionType,itemData);
		assetspopup.then(function(data) {
			$scope.AssetsMaintenanceData = populateAssetMaintananceData(data.assetMaintenanceCategoryTOs,0,[]);
			$scope.getAssetMaintenanceCategory();
		}, function(error) {
			GenericAlertService.alertMessage("Error Occured While Getting  Assets Maintenance Category", "Error");
		});
	}

	addAssetCategoryservice.addAssetMaintenanceDetails = function(actionType, itemData) {
		var deferred = $q.defer();
		assetspopup = ngDialog.open({
			template : 'views/centrallib/assetsmaintenance/assetmaintenancecategorypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $scope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.pcode = null;
				$scope.action = actionType;
				$scope.editAssetMaintenanceData  = [];
				$scope.assetMaintenancePopupItemClick = function(item, expand) {
					TreeService.dynamicTreeItemClick($scope.editAssetMaintenanceData, item, expand);
				};
				if (itemData) {
					$scope.pcode = itemData.name;
					$scope.projAssetCategoryId = itemData.id;
				}
				if (actionType === 'Add') {
					$scope.editAssetMaintenanceData.push({
						"select" : false,
						"clientId" : $scope.clientId,
						"parentId" : null,
						"item" : false,
						"deleteFlag" : false,
						"status" : 1,
						"code" : '',
						"name" : '',
						"childAssetMaintenanceCategoryTOs" : []
					});
				} else {
					$scope.editAssetMaintenanceData  = angular.copy([ itemData ]);
					itemData = [];
				}
				$scope.editAssetMaintenanceData = TreeService.populateDynamicTreeData($scope.editAssetMaintenanceData, 0, [],
					'code', 'childAssetMaintenanceCategoryTOs');

				$scope.deleteAssetsMaintenanceCategory = function(index) {
					TreeService.deleteDynamicTreeItem($scope.editAssetMaintenanceData,index);
				}

				$scope.addAssetMaintenance = function (tabData, itemIndex) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData)) {
						const obj = angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"item": false,
							"deleteFlag": false,
							"status": 1,
							"code": null,
							"name": null,
							"childAssetMaintenanceCategoryTOs": [],
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editAssetMaintenanceData = TreeService.addItemToTree($scope.editAssetMaintenanceData, tabData,
							obj, itemIndex, 'childAssetMaintenanceCategoryTOs');
					}

				}
				$scope.addAssetItem = function (tabData, itemIndex) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData)) {
						const obj = angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"status": 1,
							"code": null,
							"name": null,
							"item": true,
							"deleteFlag": false,
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editAssetMaintenanceData = TreeService.addItemToTree($scope.editAssetMaintenanceData, tabData,
							obj, itemIndex, 'childAssetMaintenanceCategoryTOs');
					}
				}
				 $scope.saveAssetMaintenanceCategory = function() {
					if(actionType === 'Add' && checkForDuplicateCode()){
						GenericAlertService.alertMessage("Duplicate Maintenance Category Code", "Warning");
						return;
					}
					const data = TreeService.extractTreeDataForSaving($scope.editAssetMaintenanceData, 'childAssetMaintenanceCategoryTOs');
					var assetsaveReq = {
						"assetMaintenanceCategoryTOs" : data,
					};
					blockUI.start();
					AssetsMaintenanceCategoryService.saveAssetMaintenanceCategory(assetsaveReq).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var results = data.assetMaintenanceCategoryTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Assets Maintenance Category  Details   is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Assets Maintenance Category details saved successfully',"Info");
							succMsg.then(function(data) {
								$scope.closeThisDialog(assetspopup);
								var returnPopObj = {
									"assetMaintenanceCategoryTOs" : results
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Assets Maintenance Category  Details is/are failed to Save", "Error");
							});
						}
					});
				}

				function checkForDuplicateCode() {
					for (const asset of $scope.AssetsMaintenanceData) {
						if (!asset.parentId && asset.code && asset.code.toUpperCase() ===
							$scope.editAssetMaintenanceData[0].code.toUpperCase())
							return true;
					}
					return false;
				}
			} ]

		});
		return deferred.promise;
	}

	$scope.deleteAssetMaintenanceCategory = function() {
		if (deleteAssetMaintenanceData == undefined || deleteAssetMaintenanceData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Assets Maintenance Category to deactivate", 'Warning');
			return;
		}
		var assetsDeactivateReq = {
			"assetIds" : deleteAssetMaintenanceData,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			AssetsMaintenanceCategoryService.deleteAssetMaintenanceCategory(assetsDeactivateReq).then(function(data) {
				GenericAlertService.alertMessage("Assets Maintenance Category(s) Deactivated successfully", "Info");
				deleteAssetMaintenanceData = [];
				$scope.getAssetMaintenanceCategory();
			}, function(error) {
				GenericAlertService.alertMessage('Assets Maintenance Category(s) is/are failed to deactivate', "Error");
			});
		}, function(data) {
			$scope.getAssetMaintenanceCategory();
		})
	}

	$scope.assetMaintananceItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childAssetMaintenanceCategoryTOs');
	};

	/**
	 * Checks all childs for data validation recursively.
	 * @param {*} tabData 
	 */
	$scope.checkDataValidationRecursively = function(tabData) {
		if (!tabData.code || !tabData.name) {
			tabData.invalidField = true;
			return false;
		}

		if (tabData.childAssetMaintenanceCategoryTOs && tabData.childAssetMaintenanceCategoryTOs.length) {
			var childs = tabData.childAssetMaintenanceCategoryTOs;
			for (var index = 0; index < childs.length; index++) {
				if (!$scope.checkDataValidationRecursively(childs[index])) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	};
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
			template: 'views/help&tutorials/assetmaintanancehelp.html',
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
