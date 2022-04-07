'use strict';
app.factory('ViewInvoicePODetailsFactory', ["$rootScope", "ngDialog", "$q", "$filter", "blockUI", "PlantRegisterService", "ProjectInvoiceService", "PurchaseOrderService", "$timeout", "GenericAlertService", function($rootScope, ngDialog, $q, $filter, blockUI,PlantRegisterService, ProjectInvoiceService, PurchaseOrderService, $timeout, GenericAlertService) {
	var service = {};
	service.getInvoiceDocketItems = function(selectedInvoiceData,procureType) {
		var deferred = $q.defer();
		if(procureType.name == 'Manpower'){
			//$scope.getManpowerPoDetails = function() {
				var req = {
					"purId" : selectedInvoiceData.id,
					"procureType" : procureType.code,
					"status" : 1
				};
				var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req).then(function(data){;
				//poItemDetailsPromise.then(function(data) {
					var popupdata = service.openPopup(data.labelKeyTOs);
					popupdata.then(function(resultData) {
						deferred.resolve(resultData);
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting Purchase Order Item Details", "Error");
				});
			//},
			
			/*var req = {
			"purId" : invoice.id,
			'projId':invoice.projId,
			"procureType":procureType.name
		};
		blockUI.start();
		PurchaseOrderService.getManpowerInvoiceDocketItems(req).then(function(data) {
			blockUI.stop();
			var popupdata = service.openPopup(data.labelKeyTOs);
			popupdata.then(function(resultData) {
				deferred.resolve(resultData);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
		});*/
		return deferred.promise;
		}else if(procureType.name == 'Plant'){
			var req = {
				"purId" : selectedInvoiceData.id,
				'projId':selectedInvoiceData.projId,
				//"procureType":procureType.name
			};
			blockUI.start();
			PurchaseOrderService.getPlantInvoiceDocket(req).then(function(data) {
				blockUI.stop();
				var popupdata = service.openPopup(data.labelKeyTOs,procureType);
				popupdata.then(function(resultData) {
					deferred.resolve(resultData);
				});
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
			});
			return deferred.promise;
			
		}else if(procureType.name == 'Material'){
			var req = {
				"purId" : selectedInvoiceData.id,
				'projId':selectedInvoiceData.projId,
				//"procureType":procureType.name
			};
			blockUI.start();
			PurchaseOrderService.getMaterialInvoiceDocket(req).then(function(data) {
				blockUI.stop();
				var popupdata = service.openPopup(data.labelKeyTOs,procureType);
				popupdata.then(function(resultData) {
					deferred.resolve(resultData);
				});
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
			});
			return deferred.promise;
			
		}else if(procureType.name == 'Service'){
			var req = {
				"purId" : selectedInvoiceData.id,
				'projId':selectedInvoiceData.projId,
				"procureType":procureType.name
			};
			blockUI.start();
			PlantRegisterService.getPOItemDetails(req).then(function(data) {
				blockUI.stop();
				var popupdata = service.openPopup(data.labelKeyTOs,procureType);
				popupdata.then(function(resultData) {
					deferred.resolve(resultData);
				});
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
			});
			return deferred.promise;
			
		}else if(procureType.name == 'Sub Contract'){
			var req = {
				"purId" : selectedInvoiceData.id,
				'projId':selectedInvoiceData.projId,
				"procureType":procureType.name
			};
			blockUI.start();
			PlantRegisterService.getPOItemDetails(req).then(function(data) {
				blockUI.stop();
				var popupdata = service.openPopup(data.labelKeyTOs,procureType);
				popupdata.then(function(resultData) {
					deferred.resolve(resultData);
				});
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
			});
			return deferred.promise;
		}
	}
	service.openPopup = function(labelKeyTOs,procureType) {
		var deferred = $q.defer();
		var invoicePODetailsPopUp = ngDialog.open({
			template : 'views/finance/financemain/invoice/invoicepodetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedPODetails = [];
				$scope.poDetailsData = labelKeyTOs;
				$scope.procureType=procureType;
				$scope.poDetailsDataPopup = function(docket) {
					var returnPopObj = {
						"invoiceData" : docket,
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}				
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
