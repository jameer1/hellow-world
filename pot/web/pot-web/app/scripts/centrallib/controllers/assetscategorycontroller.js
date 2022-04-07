'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetscategory", {
		url : '/assetscategory',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/assets/immovableassetscategory.html',
				controller : 'ImmovableAssetsCategoryController'
			}
		}
	})
}]).controller("ImmovableAssetsCategoryController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "AssetsCategoryService", "GenericAlertService", "TreeService", "utilservice", function($rootScope, $scope, $q, $state, ngDialog, blockUI, 
	AssetsCategoryService, GenericAlertService, TreeService, utilservice) {
	var deleteAssetData = [];
	$scope.AssetsData = [];
	$scope.sortType = 'code', $scope.sortReverse = false;
	$scope.searchProject = {};
	$scope.immovableAssetSearch = { 'name': "", 'code': "" };
	let immovableAssetDataCopy = null;
	var deferred = $q.defer();

	$scope.getAssetCategory = function() {
		var req = {
			"status" : 1
		};
		AssetsCategoryService.getAssetCategory(req).then(function(data) {
			deleteAssetData = [];
			immovableAssetDataCopy = angular.copy(data.assetCategoryTOs);
			$scope.AssetsData = populateAssetCategoryData(data.assetCategoryTOs,0,[]);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Assets Category", 'Error');
		});
	}, $scope.assetCategorySelect = function(tab) {
		if (tab.select) {
			utilservice.addItemKeyValueToArray(deleteAssetData, "id", tab);
		} else {
			deleteAssetData.splice(deleteAssetData.indexOf(tab.id), 1);
		}
	}

	function populateAssetCategoryData(data, level, assetCatgories, isChild, parent) {
		return TreeService.populateTreeData(data, level, assetCatgories, 'code', 'childAssetCategoryTOs');
	}

	$scope.searchAssetCategory = function () {
		if ($scope.immovableAssetSearch.code || $scope.immovableAssetSearch.name) {
			const assetCatgTOs = new Array();
			for (const assetObj of immovableAssetDataCopy) {
				if (($scope.immovableAssetSearch.code && assetObj.code &&
					assetObj.code.toUpperCase() === $scope.immovableAssetSearch.code.toUpperCase())
					|| ($scope.immovableAssetSearch.name && assetObj.name && assetObj.name.toUpperCase() === $scope.immovableAssetSearch.name.toUpperCase())) {
					assetCatgTOs.push(assetObj);
				}
			}
			$scope.AssetsData = populateAssetCategoryData(assetCatgTOs, 0, []);
			if ($scope.AssetsData.length <= 0) {
				GenericAlertService.alertMessage('Immovable Assets Category Details are not available for given search criteria', "Warning");
				return;
			}
		}
	};

	$scope.resetAssetCategory = function () {
		$scope.AssetsData = [];
		$scope.immovableAssetSearch = { 'name': "", 'code': "" };
		$scope.AssetsData = populateAssetCategoryData(angular.copy(immovableAssetDataCopy), 0, []);
	};

	var assetspopup;
	var addAssetCategoryservice = {};
	$scope.editAssetCategoryDetails = function(actionType, itemData) {
		assetspopup = addAssetCategoryservice.addAssetDetails(actionType,itemData);
		assetspopup.then(function(data) {
			$scope.AssetsData = populateAssetCategoryData(data.assetCategoryTOs, 0, []);
		}, function(error) {
			GenericAlertService.alertMessage("Error Occured While Getting  Assets Category", "Error");
		});
	}

	addAssetCategoryservice.addAssetDetails = function(actionType, itemData) {
		var deferred = $q.defer();
		assetspopup = ngDialog.open({
			template : 'views/centrallib/assets/assetcategorypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $scope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.pcode = null;
				$scope.action = actionType;
				$scope.editAssetData  = [];
				$scope.proCategory = [];
				$scope.itemClick1 = function(itemId, expand) {
					$scope.itemId1 = itemId;
					$scope.isExpand1 = !expand;
				};
				if (itemData) {
					$scope.pcode = itemData.name;
					$scope.projAssetCategoryId = itemData.id;
				}
				if (actionType === 'Add') {
					$scope.editAssetData .push({
						"select" : false,
						"clientId" : $scope.clientId,
						"parentId" : null,
						"item" : false,
						"deleteFlag" : false,
						"status" : 1,
						"code" : '',
						"name" : '',
						"childAssetCategoryTOs" : []
					});
					$scope.itemClick1(null, false);
				} else {
					$scope.editAssetData  = angular.copy([ itemData ]);
					itemData = [];
				}
				$scope.editAssetData = TreeService.populateDynamicTreeData($scope.editAssetData, 0, [], 'code', 'childAssetCategoryTOs');
				$scope.addAssetCategorySubGroup = function (tabData, itemIndex) {
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
							"childAssetCategoryTOs": [],
							"level" : (tabData.level + 1),
							"collapse" : false,
						    "expand" : true,
						});
						$scope.editAssetData = TreeService.addItemToTree($scope.editAssetData, tabData,
							obj, itemIndex, 'childAssetCategoryTOs');

					}

				}
				$scope.addAssetItem = function (tabData, itemIndex) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData)) {
						const obj= angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"status": 1,
							"code": null,
							"name": null,
							"item": true,
							"deleteFlag": false,
							"level" : (tabData.level + 1),
							"collapse" : false,
						    "expand" : true,
						});
						$scope.editAssetData = TreeService.addItemToTree($scope.editAssetData, tabData,
							obj, itemIndex, 'childAssetCategoryTOs');
					}
				}
				$scope.deleteAssetsCategory = function(index) {
					TreeService.deleteDynamicTreeItem($scope.editAssetData,index);
				};

				$scope.popupAssetCatgClick = function(item, collapse){
					TreeService.dynamicTreeItemClick ($scope.editAssetData, item, collapse);
				};
				 $scope.saveAssetCategory = function() {
					if(actionType === 'Add' && checkForDuplicateAssetCode()){
						GenericAlertService.alertMessage("Duplicate Asset Category Code", "Warning");
						return;
					}
					const data = TreeService.extractTreeDataForSaving($scope.editAssetData, 'childAssetCategoryTOs');
					var assetssaveReq = {
						"assetCategoryTOs" : data,
					};
					//blockUI.start();
					AssetsCategoryService.saveAssetCategory(assetssaveReq).then(function(data) {
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var results = data.assetCategoryTOs;
							//blockUI.stop();
							// var succMsg = GenericAlertService.alertMessageModal('Assets Category  Details   is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Assets Category details saved successfully',"Info");
							succMsg.then(function(data) {
								$scope.closeThisDialog(assetspopup);
								var returnPopObj = {
									"assetCategoryTOs" : results
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								GenericAlertService.alertMessage("Assets Category  Details is/are failed to Save", "Error");
							});
						}
					});
				}

				function checkForDuplicateAssetCode(){
					for (const asset of $scope.AssetsData) {
						if (!asset.parentId && asset.code && asset.code.toUpperCase() ===
							$scope.editAssetData[0].code.toUpperCase())
							return true;
					}
					return false;
				}
			} ]

		});
		return deferred.promise;
	}

	$scope.deleteAssetCategory = function() {
		if (deleteAssetData == undefined || deleteAssetData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Assets Category to deactivate", 'Warning');
			return;
		}
		var assetsDeactivateReq = {
			"assetIds" : deleteAssetData,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			AssetsCategoryService.deleteAssetCategory(assetsDeactivateReq).then(function(data) {
				GenericAlertService.alertMessage("Assets Category(s) Deactivated successfully", "Info");
				deleteAssetData = [];
				$scope.getAssetCategory();
			}, function(error) {
				GenericAlertService.alertMessage('Assets Category(s) is/are failed to deactivate', "Error");
			});
		}, function(data) {
			$scope.getAssetCategory();
		})
	}

	$scope.assetCategItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childAssetCategoryTOs');
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

		if (tabData.childAssetCategoryTOs && tabData.childAssetCategoryTOs.length) {
			var childs = tabData.childAssetCategoryTOs;
			for (var index = 0; index < childs.length; index++) {
				if (!$scope.checkDataValidationRecursively(childs[index])) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
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
			template: 'views/help&tutorials/immovableassethelp.html',
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
