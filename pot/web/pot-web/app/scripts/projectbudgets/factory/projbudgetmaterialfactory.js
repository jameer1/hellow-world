'use strict';

app.factory('ProjBudgetMaterialFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "MaterialClassService", "ProjectBudgetService", "GenericAlertService", "TreeService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, 
	MaterialClassService, ProjectBudgetService, GenericAlertService, TreeService) {
	var service = {};
	
	function populateMaterialClassData(data, level, materialClassTOs, isChild, parent) {
		return TreeService.populateTreeData(data, level, materialClassTOs, 'code', 'childMaterialItemTOs',
			isChild, parent)
	}

	service.getMaterialClasses = function(projId, exitingSchMaterilMap) {
		var deferred = $q.defer();
		var req = {
			"projId" : projId
		};
		ProjectBudgetService.getMaterials(req).then(function(data) {
			let materialData = populateMaterialClassData(data.materialClassTOs, 0, []);
			materialData.map((treeItem) => {
				TreeService.treeItemClick(treeItem, false, 'childMaterialItemTOs');
			});
			var popupData = service.openPopup(projId, materialData, exitingSchMaterilMap);
			popupData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Material Groups", 'Error');
		});
		return deferred.promise;

	},

	service.openPopup = function(projId, materialClassTOs, exitingSchMaterilMap) {
		var deferred = $q.defer();
		var projMaterialPopup = ngDialog.open({
			template : 'views/projectbudgets/projbudgetmeterialspopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			controller : [ '$scope', function($scope) {
				var selectedMeterials = [];
				$scope.exitingSchMaterilMap = exitingSchMaterilMap;
				$scope.materialClassTOs = materialClassTOs;
				$scope.itemId = 1;
				$scope.isExpand = true;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};

				$scope.saveMeterials = function() {
					var saveMaterials = [];
					angular.forEach(selectedMeterials, function(value, key) {
						saveMaterials.push({
							"materialId" : value.id,
							"status" : 1,
							"projId" : projId
						});
					});
					var req = {
						"projectMaterialDtlTOs" : saveMaterials,
						"projId" : projId
					}
					var results = [];
					blockUI.start();
					ProjectBudgetService.saveProjectMaterials(req).then(function(data) {
						blockUI.stop();
						// var succMsg = GenericAlertService.alertMessageModal('Materials(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Materials(s) added successfully',"Info");
						succMsg.then(function(data1) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"projectMaterialDtlTOs" : data.projectMaterialDtlTOs
							}
							deferred.resolve(returnPopObj);
						})

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Materials(s) is/are failed to save', "Error");
					});
				}, 
				
				$scope.materialRowSelect = function(meterialTO) {
					if (meterialTO.select) {
						selectedMeterials.push(meterialTO);
					} else {
						selectedMeterials.pop(meterialTO);
					}
				}

				$scope.materialClassItemClick = function (item, collapse) {
					TreeService.treeItemClick(item, collapse, 'childMaterialItemTOs');
				};
			} ]

		});
		return deferred.promise;
	}
	return service;

}]);