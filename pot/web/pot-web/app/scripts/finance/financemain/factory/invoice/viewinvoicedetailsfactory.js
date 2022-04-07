'use strict';

app.factory('ViewInvoiceDetailsFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "ProjectInvoiceService", "PurchaseOrderService", "GenericAlertService", "ViewInvoicePODetailsFactory", "$rootScope", function(ngDialog, $q, $filter,blockUI, $timeout,ProjectInvoiceService,PurchaseOrderService ,GenericAlertService, ViewInvoicePODetailsFactory, $rootScope) {
	var viewInvoiceDetails;
	var service = {};
	service.viewInvoiceDocketDetails = function(procureType,selectedInvoiceData) {
		var deferred = $q.defer();
		var invoice = invoice;
		var selectedInvoiceData =selectedInvoiceData;
		var viewInvoiceDetails = ngDialog.open({
			template : 'views/finance/financemain/invoice/deliverydocketpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {

				$scope.invoice = invoice;
				$scope.selectedInvoiceData = selectedInvoiceData;
				var procureTypeMap = [];
				procureTypeMap['E'] = 'Manpower';
				procureTypeMap['P'] = 'Plant';
				procureTypeMap['M'] = 'Material';
				procureTypeMap['S'] = 'Service';
				procureTypeMap['SOW'] = 'Sub Contract';
				$scope.procureType = {
					"code" : null,
					"name" : null
				}
				$scope.procureTypes = [];
				angular.forEach(procureType.split('#'), function(value, key) {
					if (procureTypeMap[value] != undefined && procureTypeMap[value] != null) {
						$scope.procureTypes.push({
							"code" : value,
							"name" : procureTypeMap[value]
						});
					}
				});
				
				$scope.addInvoiceWisePODetails = function(selectedInvoiceData,procureType) {
				var popupDetails = ViewInvoicePODetailsFactory.getInvoiceDocketItems(selectedInvoiceData,procureType);
				popupDetails.then(function(data) {
					$scope.invoiceDocketData = [];
					$scope.invoiceDocketData = [data.invoiceData];
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while getting invoice dockets", 'Info');
				});
		},
		$scope.getInvoiceDetails = function(procureType) {
			var deferred = $q.defer();
			if(procureType.name == 'Manpower'){
			var req = {
				"purId" : $scope.selectedInvoiceData.id,
				'projId':$scope.selectedInvoiceData.projId,
				"procureType":procureType.name
			};
			blockUI.start();
			PurchaseOrderService.getManpowerInvoiceDocketItems(req).then(function(data) {
				blockUI.stop();
				$scope.invoiceDocketData = data.labelKeyTOs;
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
			});
			return deferred.promise;
			}else if(procureType.name == 'Plant'){
				var req = {
					"purId" : selectedInvoiceData.id,
					'projId':selectedInvoiceData.projId,
					"procureType":procureType.name
				};
				blockUI.start();
				PurchaseOrderService.getPlantInvoiceDocket(req).then(function(data) {
					blockUI.stop();
					$scope.invoiceDocketData = {};
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
				});
				return deferred.promise;
				
			}else if(procureType.name == 'Material'){
				var req = {
					"purId" : selectedInvoiceData.id,
					'projId':selectedInvoiceData.projId,
					"procureType":procureType.name
				};
				blockUI.start();
				PurchaseOrderService.getMaterialInvoiceDocket(req).then(function(data) {
					blockUI.stop();
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
				});
				return deferred.promise;
				
			}else if(procureType.name == 'Service'){
				var req = {
					//"purId" : invoice.id,
					'projId':selectedInvoiceData.projId,
					"procureType":procureType.name
				};
				blockUI.start();
				PurchaseOrderService.getPlantInvoiceDocket(req).then(function(data) {
					blockUI.stop();
					$scope.invoiceDocketData = {};
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
				PurchaseOrderService.getPlantInvoiceDocket(req).then(function(data) {
					blockUI.stop();
					$scope.invoiceDocketData = {};
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while getting invoice po details ", 'Error');
				});
				return deferred.promise;
			}
		}
		$scope.saveInvoiceDocketDetails = function(docket,invoice) {
			if($scope.invoiceDocketData == null){
				GenericAlertService.alertMessage("Please add the schedule items before save",'Warning');
				return;
			}
			var req = {
				"manpowerInvoiceDocketItemTOs":$scope.invoiceDocketData,
				"purId":selectedInvoiceData.id,
				"projId":selectedInvoiceData.projId
			}
			blockUI.start();
			ProjectInvoiceService.saveInvoiceDocketItems(req).then(function(data) {
				blockUI.stop();
				GenericAlertService.alertMessage("Invoice wise payment details are saved successfulley", 'Info');
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Invoice docket item Detail(s) is/are failed to Save', 'Error');
			});
		}
		
			} ]
		});
		return deferred.promise;
	};
	return service;

}]);
