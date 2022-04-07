'use strict';
app.factory('MaterialPurchaseOrderSchItemFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, MaterialRegisterService, GenericAlertService) {
	var materialissuedocketpopup;
	var service = {};
	service.projectMaterialDocketSchItemDetails = function(itemData) {

		materialissuedocketpopup = ngDialog.open({
			template : 'views/projresources/projmaterialreg/issuedocket/projdocketschitems.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedIssueDocket = [];
				$scope.addStoreIssueDocket = itemData.matProjSchItems;
				$scope.userProjMap = itemData.userProjMap;
				$scope.companyMap = itemData.companyMap;
				$scope.classificationMap = itemData.classificationMap;
				$scope.actionType=null;
				var materialSchItemObj = {
					"id" : null,
					"select" : false,
					"projId" : null,
					"supplyDeliveryDate" : null,
					"purchaseLabelKeyTO" : {
						id : null,
						code : null,
						name : null
					},
					"purchaseSchLabelKeyTO" : {
						id : null,
						code : null,
						name : null,
						displayNamesMap : {}
					},
					"materialPODeliveryDocketTOs" : []

				}
				if (actionType === 'Add') {
					$scope.addMaterialData.materialPODeliveryDocketTOs.push(angular.copy(deliveryDocketObj));
				} else {
					$scope.addMaterialData = angular.copy(materialProjectPODtlTO[0]);
					$scope.inputDisable = true;
					var poQty = $scope.addMaterialData.purchaseSchLabelKeyTO.displayNamesMap['qty'];
					var localqty = 0.0;
					angular.forEach($scope.addMaterialData.materialPODeliveryDocketTOs, function(value, key) {
						if (!isNaN(value.receivedQty) && value.receivedQty != null) {
							localqty = parseFloat(value.receivedQty) + parseFloat(localqty);
						}
					});
					if (parseFloat(poQty) == parseFloat(localqty)) {
						$scope.saveButton = false;
					}
				}
			} ]
		});

	}

	return service;
}]);
