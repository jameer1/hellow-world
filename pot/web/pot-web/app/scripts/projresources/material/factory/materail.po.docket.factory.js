'use strict';
app.factory('MaterialDeliveryDocketFactory', ["blockUI", "ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialRegisterService", "GenericAlertService", function(blockUI, ngDialog, $q, $filter, $timeout, $rootScope, MaterialRegisterService, GenericAlertService) {
	var service = {};
	service.addMaterialDeliveryDockets = function(materialProjSchItemTO) {
		var deferred = $q.defer();
		if (materialProjSchItemTO.id == null) {
			var resultData = service.openPopUp(materialProjSchItemTO);
			resultData.then(function(data) {
				deferred.resolve(data);
			});
		} else {
			var resultData = service.openPopUp(materialProjSchItemTO);
			resultData.then(function(data) {
				deferred.resolve(data);
			});
		}
		return deferred.promise;
	}, service.openPopUp = function(materialProjSchItemTO) {
		var deferred = $q.defer();
		var materailDocketPOPopUp = ngDialog.open({
			template : 'views/projresources/projmaterialreg/deliverysuply/matprojdocketdetails.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.materialProjSchItemTO = materialProjSchItemTO;
				var selectedMaterialDocketTOs = [];
				var deliveryDocketObj = {
					"select" : false,
					"id" : null,
					"docketDate" : null,
					"docketNumber" : null,
					"receivedQty" : null,
					"receivedBy" : null,
					"defectComments" : null,
					"comments" : null
				};
				if ($scope.materialProjSchItemTO.materialPODeliveryDocketTOs == undefined || $scope.materialProjSchItemTO.materialPODeliveryDocketTOs.length <= 0) {
					$scope.materialProjSchItemTO.materialPODeliveryDocketTOs.push(angular.copy(deliveryDocketObj));
				}
				$scope.addRows = function() {
					$scope.materialProjSchItemTO.materialPODeliveryDocketTOs.push(angular.copy(deliveryDocketObj));
				}, $scope.deleteRows = function() {
					if (selectedMaterialDocketTOs.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedMaterialDocketTOs.length < $scope.materialProjSchItemTO.materialPODeliveryDocketTOs.length) {
						angular.forEach(selectedMaterialDocketTOs, function(value, key) {
							$scope.materialProjSchItemTO.materialPODeliveryDocketTOs.splice($scope.materialProjSchItemTO.materialPODeliveryDocketTOs.indexOf(value), 1);
						});
						selectedMaterialDocketTOs = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.save = function() {
					var invalidRecordExist = false;
					if ($scope.addMaterialData.projId == null || $scope.addMaterialData.purchaseSchLabelKeyTO.id == null) {
						GenericAlertService.alertMessage("Please enter Material Schedule Items", "Info");
						return;
					}
					
					angular.forEach($scope.addMaterialData.materialPODeliveryDocketTOs, function(value, key) {
						if (value.docketDate == null || value.docketNumber == null || value.receivedQty == null || value.receivedBy == null || (value.stockLabelKeyTO.id == null && value.projStockLabelKeyTO.id == null)) {
							invalidRecordExist = true;
							return;
						}
					});
					
					if (invalidRecordExist) {
						GenericAlertService.alertMessage("Please enter delivery docket details", "Info");
						return;
					}
					
					if ($scope.qtyMismatch) {
						GenericAlertService.alertMessage("Received quantity value is mismatch with actuals", "Info");
						return;
					}
					var req = {
						"materialProjDtlTOs" : [ $scope.materialProjSchItemTO ]
					}
					blockUI.start();
					MaterialRegisterService.saveProjMaterialSchItems(req).then(function(data) {
						blockUI.stop();
						var succMsg = GenericAlertService.alertMessageModal('Material Delivery/Supply Details ' + data.message, data.status);
						succMsg.then(function() {
							var returnPopObj = {
								"companyMap" : $scope.companyMap,
								"classificationMap" : $scope.classificationMap,
								"userProjMap" : $scope.userProjMap,
								"materialProjDtlTOs" : data.materialProjDtlTOs
							}
							deferred.resolve(returnPopObj);
						})

					}, function(error) {
						blockUI.stop();
						if (error.message != null && error.message != undefined) {
							GenericAlertService.alertMessage(error.message, error.status);
						} else {
							GenericAlertService.alertMessage('Material DailySupply(s) is/are Failed to Save ', "Error");
						}
					});
				}
			} ]

		});
		return deferred.promise;
	}

	return service;
}]);
