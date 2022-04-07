'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("materialclass", {
		url: '/materialclass',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/materialclass/materialclass.html',
				controller: 'MaterialClassController'
			}
		}
	})
}]).controller("MaterialClassController", ["$rootScope", "$scope", "$q", "utilservice", "ngDialog", "blockUI", "MaterialClassService", "MeasureService", "GenericAlertService", "generalservice", "TreeService", function ($rootScope, $scope, $q, utilservice, ngDialog, blockUI,
	MaterialClassService, MeasureService, GenericAlertService, generalservice, TreeService) {
	var deleteMaterialData = [];
	$scope.MaterialData = [];
	$scope.sortType = 'code',
		$scope.sortReverse = false;
	$scope.searchProject = {};
	$scope.searchForm = {
		'name' : '',
		'code' : ''
	};
	var deferred = $q.defer();

	$scope.getMaterialGroups = function (click) {
		var materialName, materialCode;
		if ($scope.searchForm.name) {
			materialName = $scope.searchForm.name;
		}
		if ($scope.searchForm.code) {
			materialCode = $scope.searchForm.code;
		}
		var req = {
			"status": 1,
			"materialName": materialName,
			"materialCode": materialCode
		};
		$scope.procCategory = generalservice.getprocures;
		MaterialClassService.getCentralMaterial(req).then(function (data) {
			deleteMaterialData = [];
			$scope.MaterialData = populateMaterialClassData(data.materialClassTOs, 0, []);
			if(click=='click'){
				if ($scope.MaterialData.length <= 0) {
					GenericAlertService.alertMessage('Material Details are not available for given search criteria', "Warning");
					return;
				}
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Material Groups", 'Error');
		});
	};

	function populateMaterialClassData(data, level, materialClassTOs, isChild, parent) {
		return TreeService.populateTreeData(data, level, materialClassTOs, 'code', 'childMaterialItemTOs',
			isChild, parent)
	}

	$scope.materialSelect = function (tab) {
		if (tab.select) {
			utilservice.addItemKeyValueToArray(deleteMaterialData, "id", tab);
		} else {
			deleteMaterialData.splice(deleteMaterialData.indexOf(tab.id), 1);
		}
	};
	$scope.resetMaterialData = function () {
		$scope.MaterialData = [];
		$scope.searchForm.name = "";
		$scope.searchForm.code = "";
		$scope.getMaterialGroups();
	}



	var materialpopup;
	var addMaterialservice = {};
	$scope.editMaterialDetails = function (actionType, itemData) {
		if (deleteMaterialData > 0 && actionType === "Add") {
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}


		materialpopup = addMaterialservice.addMaterialDetails(actionType, itemData);
		materialpopup.then(function (data) {
			$scope.MaterialData = populateMaterialClassData(data.materialClassTOs, 0, []);
		}, function (error) {
			GenericAlertService.alertMessage("Error Occured While Getting  Material Groups", "Error");
		});
	}

	addMaterialservice.addMaterialDetails = function (actionType, itemData) {
		var deferred = $q.defer();
		materialpopup = ngDialog.open({
			template: 'views/centrallib/materialclass/materialclasspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.pcode = null;
				$scope.action = actionType;
				$scope.editMaterialData = [];
				$scope.proCategory = [];
				$scope.measurements = [];
				$scope.projMaterialId = null;
				$scope.popupMaterialClassClick = function (item, expand) {
					TreeService.dynamicTreeItemClick($scope.editMaterialData, item, expand);
				};
				if (itemData) {
					$scope.pcode = itemData.name;
					$scope.projMaterialId = itemData.id;
				}
				if (actionType === 'Add') {
					$scope.editMaterialData.push({
						"select": false,
						"clientId": $scope.clientId,
						"parentId": null,
						"item": false,
						"deleteFlag": false,
						"status": 1,
						"code": '',
						"name": '',
						"childMaterialItemTOs": [],
						"level": 0,
						"collapse": false,
						"expand": true,
					});
					$scope.editMaterialData = TreeService.populateDynamicTreeData($scope.editMaterialData, 0, [],
						'code', 'childMaterialItemTOs');
				} else {
					$scope.editMaterialData = TreeService.populateDynamicTreeData(angular.copy([itemData]), 0, [],
						'code', 'childMaterialItemTOs');
					itemData = [];
				}

				$scope.addMaterialSubGroup = function (tabData, index) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData, index)) {
						const itemToAdd = angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"item": false,
							"deleteFlag": false,
							"status": 1,
							"code": null,
							"name": null,
							"childMaterialItemTOs": [],
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editMaterialData = TreeService.addItemToTree($scope.editMaterialData, tabData, itemToAdd, index);
					}

				}
				$scope.addMaterialItem = function (tabData, index) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData, index)) {
						const itemToAdd = angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"status": 1,
							"code": null,
							"name": null,
							"item": true,
							"deleteFlag": false,
							"measureUnitTO": '',
							"measureId": null,
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editMaterialData = TreeService.addItemToTree($scope.editMaterialData, tabData, itemToAdd, index);
					}
				}
				$scope.deleteMaterialClass = function (index) {
					TreeService.deleteDynamicTreeItem($scope.editMaterialData, index);
				}
				$scope.getMeasuresByProcureType = function () {
					var req = {
						"status": 1,
						//"procureClassName": $scope.procCategory[1].name
					};
					MeasureService.getMeasuresByProcureType(req).then(function (data) {
						$scope.proCategory = data.measureUnitTOs;

					});
				}, $scope.updatePlantCode = function (data, tab) {
					tab.measureId = data.id;
				};

				$scope.saveMaterialGroups = function () {
					if ($scope.checkDataValidationRecursively($scope.editMaterialData[0], 0)) {
						const data = TreeService.extractTreeDataForSaving($scope.editMaterialData, 'childMaterialItemTOs');
						var materialsaveReq = {
							"materialClassTOs": data,
						};
						if(actionType === 'Add' && checkForDuplicateGroupId()){
							GenericAlertService.alertMessage("Duplicate Material Group ID", "Warning");
							return;
						}
						blockUI.start();
						MaterialClassService.saveMaterialGroups(materialsaveReq).then(function (data) {
							blockUI.stop();
							if (data.status != null && data.status !== undefined && data.status === 'Info') {
								var results = data.materialClassTOs;
								// var succMsg = GenericAlertService.alertMessageModal('Material Group  Details   is/are ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal('Material Classification(s) details saved successfully',"Info");
								succMsg.then(function (data) {
									$scope.closeThisDialog(materialpopup);
									var returnPopObj = {
										"materialClassTOs": results
									};
									deferred.resolve(returnPopObj);
								}, function (error) {
									blockUI.stop();
									GenericAlertService.alertMessage("Material Group  Details is/are failed to Save", "Error");
								});
							}
						});
					}
				};

				$scope.checkDataValidationRecursively = function (tabData, index) {
					if (!tabData.code || !tabData.name) {
						tabData.invalidField = true;
						return false;
					}

					if (tabData.item && !tabData.measureId) {
						tabData.invalidField = true;
						return false;
					}

					for (++index; index < $scope.editMaterialData.length; index++) {
						return $scope.checkDataValidationRecursively($scope.editMaterialData[index], index);
					}
					return true;

				};

				function checkForDuplicateGroupId(){
					for (const group of $scope.MaterialData) {
						if (!group.parentId && group.code && group.code.toUpperCase() === $scope.editMaterialData[0].code.toUpperCase()) {
							return true;
						}
					}
					return false;
				}
			}]

		});
		return deferred.promise;
	}

	$scope.deleteMaterialGroups = function () {
		if (deleteMaterialData == undefined || deleteMaterialData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Material to deactivate", 'Warning');
			return;
		}
		var materialDeactivateReq = {
			"materialIds": deleteMaterialData,
			"status": 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
			MaterialClassService.deleteMaterialGroups(materialDeactivateReq).then(function (data) {
				GenericAlertService.alertMessage("Material Group(s) Deactivated successfully", "Info");
				deleteMaterialData = [];
				$scope.getMaterialGroups();
			}, function (error) {
				GenericAlertService.alertMessage('Material Group(s) is/are failed to deactivate', "Error");
			});
		}, function (data) {
			$scope.getMaterialGroups();
		})
	}

	$scope.materialClassItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childMaterialItemTOs');
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
			template: 'views/help&tutorials/materialclassificationhelp.html',
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
