'use strict';
app.factory('RegisterPurchaseOrderFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService","ChangeOrdersService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantRegisterService,ChangeOrdersService) {
	var plantPurchaseOrderPopUp;
	var service = {};
	service.plantPurchaseOrderPopUp = function(projectPurchaseOrderDetails) {
		var deferred = $q.defer();
		plantPurchaseOrderPopUp = ngDialog.open({
			template : 'views/projresources/common/registerpurchaseorderlist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {

				$scope.plantPurchaseOrderDetails = projectPurchaseOrderDetails;
				$scope.plantPurchaseOrderPopUp = function(purchaseOrderTO) {
					var returnPurchaseOrderTO = {
						"purchaseOrderTO" : purchaseOrderTO
					};
					deferred.resolve(returnPurchaseOrderTO);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	},

	service.getProjectPODetails = function(req) {
		var coreq=req.data;
		var req1 = {
			"status": 1,
			"projId": coreq[0].projId,
			"preContractType":coreq[0].preContractType
		}
		console.log(req1)
		var deferred = $q.defer();
		ChangeOrdersService.getCoMainDetails(req1).then(function(data) {
			console.log(data)
			if (data.labelKeyTOs != undefined && data.labelKeyTOs.length > 0) {
				var plantPurchaseOrderDetails = [];
				plantPurchaseOrderDetails = data.labelKeyTOs;

				var projectPODetailsPopup = service.plantPurchaseOrderPopUp(plantPurchaseOrderDetails);
				projectPODetailsPopup.then(function(data) {
					var returnPopObj = {
						"purchaseOrderTO" : data.purchaseOrderTO
					};
					deferred.resolve(returnPopObj);
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
				})

			} else {
				GenericAlertService.alertMessage("Purchase orders are not available for selected project", "Warning");
			}
			}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
			
		});
	        //Old code
/*		var projectPODetailsPromise = PlantRegisterService.getPlantProjectPODetails(req);
		projectPODetailsPromise.then(function(data) {
			if (data.labelKeyTOs != undefined && data.labelKeyTOs.length > 0) {
				var plantPurchaseOrderDetails = [];
				plantPurchaseOrderDetails = data.labelKeyTOs;

				var projectPODetailsPopup = service.plantPurchaseOrderPopUp(plantPurchaseOrderDetails);
				projectPODetailsPopup.then(function(data) {
					var returnPopObj = {
						"purchaseOrderTO" : data.purchaseOrderTO
					};
					deferred.resolve(returnPopObj);
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
				})

			} else {
				GenericAlertService.alertMessage("Purchase orders are not available for selected project", "Warning");
			}

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting purchase order details", "Error");
		});*/
		return deferred.promise;
	}

	return service;
}]);
