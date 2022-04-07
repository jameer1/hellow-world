'use strict';

app.factory('LeaseSrNoFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI","ProjSORService","TangibleClassService", "MeasureService", "GenericAlertService", "TreeService", "AssetRegisterService",function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI,ProjSORService,
		TangibleClassService, MeasureService,GenericAlertService, TreeService,AssetRegisterService) {
	var leaseSrNoPopup;
	var service = {};
	service.leaseSrNoPopupDetails = function (leaseSrNoPopupDetails) {
		
		var deferred = $q.defer();
				blockUI.start();
		
				AssetRegisterService.getLongtermleaseActiveAllRecord(leaseSrNoPopupDetails).then(function(data) {
					blockUI.stop();
					
					var popupdata = service.openPopup(data.longTermLeasingDtlTOs);
					popupdata.then(function (resultData) {
						deferred.resolve(resultData);
					});
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while fetching LongTermLease Value/Revenue(s) details", "Error");
				});
		return deferred.promise;
	},service.openPopup = function (assetgetallactive) {
		var deferred = $q.defer();
		leaseSrNoPopup = ngDialog.open({
			template: 'views/projresources/projassetreg/longtermleaserental/leasesrnopopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.longTermLeasingDtlTOs = assetgetallactive;
				$scope.selectAssetTenant = function (item,index) {
					
					var returnPopObj = {
						"searchCompany": {
							'lease':index,
							"leaseAgreement": item.leaseAgreement,
							"tenantId": item.tenantid,
							"tenantName": item.tenantName,
							"addlongterm" :item.netRentAmountPerCycle+item.assetMaintenanceCharges,
					 	 "taxAmount1": item.taxAmount,
						 "leaseStart": item.leaseStart,
						 "paymentCycle":item.paymentCycle,
						 "netRentAmountPerCycle":item.netRentAmountPerCycle,
						 "taxableAmount" :item.taxableAmount,
						 "tax":item.tax

						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				};
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
