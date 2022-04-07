'use strict';
app.factory('GoodsTaxPaymentFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GoodsTaxService", "GoodsTaxPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, GoodsTaxService, GoodsTaxPopUpFactory, GenericAlertService) {
	var goods;
	var service = {};

	service.getGoodsPaymentDetails = function(taxTypeId) {
		var deferred = $q.defer();
		var getReq = {
			"taxId" : taxTypeId,
			"status" : "1"
		};
		GoodsTaxService.getServiceTax(getReq).then(function(data) {
			var goods = [];
			goods = service.openGoodsTaxPopup(data.serviceTaxTOs, taxTypeId);
			goods.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
		});
		return deferred.promise;

	}, service.openGoodsTaxPopup = function(serviceTaxTOs, taxTypeId) {
		var deferred = $q.defer();
		goods = ngDialog.open({
			template : 'views/finance/taxtypes/goodsandservicetax.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editGoodsTax = [];
				$scope.servicetax = serviceTaxTOs;

				$scope.goodstaxrowselect = function(goods) {
					if (goods.selected) {
						editGoodsTax.push(goods);
					} else {
						editGoodsTax.pop(goods);
					}
				}
				$scope.addGoodsTax = function(actionType) {
					if (actionType == 'Edit' && editGoodsTax <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = GoodsTaxPopUpFactory.goodsPopupDetails(actionType, editGoodsTax, taxTypeId);
						popupDetails.then(function(data) {
							$scope.servicetax = data.serviceTaxTOs;
							editGoodsTax = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deteteGoodsTax = function() {
					if (editGoodsTax.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.servicetax = [];
					} else {
						angular.forEach(editGoodsTax, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						GoodsTaxService.deleteServiceTax(req).then(function(data) {
							GenericAlertService.alertMessage('GoodsTax Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editGoodsTax, function(value, key) {
							$scope.servicetax.splice($scope.servicetax.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('GoodsTax Details are failed to Deactivate', "Error");
						});
						editGoodsTax = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
