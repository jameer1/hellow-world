'use strict';
app.factory('MaterialRegMultipleSchItemFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "GenericAlertService", "PlantRegisterService", "MaterialRegisterService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, GenericAlertService, PlantRegisterService, MaterialRegisterService) {
	var service = {};
	service.getPOItemsByPurchaseOrder = function (req, purId, purCode, projId, existingSchItemsMap) {
		var deferred = $q.defer();
		var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req);
		poItemDetailsPromise.then(function (data) {
			let schItems = [];
			data.labelKeyTOs.map((sch) => {
				let found = false;
				existingSchItemsMap.map((existingSch) => {
					if (sch.id === existingSch.purchaseSchLabelKeyTO.id)
						found = true;
				});
				if (!found)
					schItems.push(sch);
			});

			var resultData = service.openPopup(schItems, purId, purCode, projId, existingSchItemsMap);
			resultData.then(function (data) {
				deferred.resolve(data);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting purchase order schedule items", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting purchase order schedule items", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function (labelKeyTOs, purId, purCode, projId, existingSchItemsMap) {
		var deferred = $q.defer();
		var plantPOItemPopUp = ngDialog.open({
			template: 'views/projresources/projmaterialreg/deliverysuply/multiplescheduleitemspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.labelKeyTOs = labelKeyTOs;
				var selectedPutSchItems = [];
				$scope.existingSchItemsMap = existingSchItemsMap;
				$scope.selectRecord = function (schItem) {
					if (schItem.select) {
						selectedPutSchItems.push(schItem);
					} else {
						selectedPutSchItems.pop(schItem);
					}
				}, $scope.saveScheduleItems = function () {
					if (selectedPutSchItems.length <= 0) {
						GenericAlertService.alertMessage("Please select  atleast one schedule item", 'Warning');
						return;
					}
					var materialProjDtlTOs = [];
					angular.forEach(selectedPutSchItems, function (value, key) {
						value.displayNamesMap.purId = purId;
						value.displayNamesMap.purCode = purCode;
						materialProjDtlTOs.push({
							"status": 1,
							"id": null,
							"projId": projId,
							"stockId": value.displayNamesMap.stockId,
							"projStockId": value.displayNamesMap.projStockId,
							"deliveryPlace": value.displayNamesMap.deliveryPlace,
							"purchaseSchLabelKeyTO": {
								"id": value.id,
								"code": value.code,
								"name": value.name,
								"displayNamesMap": value.displayNamesMap
							}
						});
					});
					var req = {
						"materialProjDtlTOs": materialProjDtlTOs,
						"purId": purId,
						"status": 1
					}
					blockUI.start();
					MaterialRegisterService.saveProjMaterialSchItems(req).then(function (data) {
						blockUI.stop();
						GenericAlertService.alertMessage('Material Schedule Item(s) added successfully', 'Info');
						var returnPopObj = {
							"materialProjDtlTOs": data.materialProjDtlTOs
						}
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();
					}, function (error) {
						blockUI.stop();
						if (error.message != null && error.message != undefined) {
							GenericAlertService.alertMessage(error.message, error.status);
						} else {
							GenericAlertService.alertMessage('Material Schedule Item(s) are Failed to Save ', "Error");
						}
					});

				}
			}]

		});
		return deferred.promise;
	}

	return service;
}]);
