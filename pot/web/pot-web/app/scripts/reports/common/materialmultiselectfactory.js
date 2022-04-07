'use strict';

app.factory('MaterialMultiSelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialClassService", "GenericAlertService", "TreeService", function (ngDialog, $q, $filter, $timeout, $rootScope, MaterialClassService, GenericAlertService, TreeService) {
	var service = {};

	service.getMultiMaterials = function (materialReq) {

		var deferred = $q.defer();

		MaterialClassService.getMaterialGroups(materialReq).then(function (data) {
			var materialPopup = service.openMaterialPopup(data.materialClassTOs);
			materialPopup.then(function (data) {
				deferred.resolve(data);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Material Details", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Material Details", 'Error');
		});
		return deferred.promise;

	}, service.openMaterialPopup = function (materialClassTOs) {
		var materialPopupFactory = [];
		var deferred = $q.defer();
		materialPopupFactory = ngDialog.open({
			template: 'views/reports/common/multimaterialselectpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.tabData = TreeService.populateTreeData(materialClassTOs, 0, [], 'code',
					'childMaterialItemTOs');
				$scope.itemClick = function (item, collapse) {
					TreeService.treeItemClick(item, collapse, 'childMaterialItemTOs');
				};
				$scope.tabData.map(material => {
					$scope.itemClick(material, false);
				});

				var selectedMaterialItems = [];
				var materialNameDisplay = '';
				var selectedMaterialIds = [];

				$scope.selectAll = false;

				$scope.selectMaterialItem = function (materialItemTO) {
					if (materialItemTO.select) {
						selectedMaterialItems.push(materialItemTO);
					} else {
						selectedMaterialItems.pop(materialItemTO);
					}
				}

				$scope.selectAllMaterials = function (items) {
					items.map(item => {
						if (item.childMaterialItemTOs.length > 0) {
							$scope.selectAllMaterials(item.childMaterialItemTOs);
						}
						if (item.item) {
							item.select = !item.select;
							$scope.selectMaterialItem(item);
						}
					});
				}

				$scope.addMaterialItems = function () {
					angular.forEach(selectedMaterialItems, function (value, key) {
						selectedMaterialIds.push(value.id);
						materialNameDisplay = materialNameDisplay + value.code + ",";
					});
					var returnPopObj = {
						"selectedMaterial": {
							"materialName": materialNameDisplay.slice(0, -1),
							"materialIds": selectedMaterialIds
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}


			}]
		});
		return deferred.promise;
	}
	return service;

}]);
