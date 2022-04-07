'use strict';
app.factory('GoodsTaxPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "GoodsTaxService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, GoodsTaxService, GenericAlertService) {
	var goodsPopup;
	var selectedGoods = [];
	var service = {};
	service.goodsPopupDetails = function(actionType, editGoodsTax, taxTypeId) {
		var deferred = $q.defer();
		goodsPopup = ngDialog.open({
			template : 'views/finance/taxtypes/goodstaxpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedGoods = [];
				$scope.goodsList = [];

				if (actionType === 'Add') {
					$scope.goodsList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						"taxCalMstrId" : '',
						"invoiceAmount" : '',
						"taxRate" : '',
						"comments" : '',

					});
				} else {
					$scope.goodsList = angular.copy(editGoodsTax);
					editGoodsTax = [];
				}
				$scope.addRows = function() {

					$scope.goodsList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						"taxCalMstrId" : '',
						"invoiceAmount" : '',
						"taxRate" : '',
						"comments" : '',

					});
				}, $scope.saveTax = function() {
					var req = {
						"serviceTaxTOs" : $scope.goodsList,
					}
					blockUI.start();
					GoodsTaxService.saveServiceTax(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('GoodsTax Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('GoodsTax Details  are failed to Save', 'Error');
					});
					ngDialog.close();
				};

				$scope.goodsPopUpRowSelect = function(goods) {
					if (goods.selected) {
						selectedGoods.push(goods);
					} else {
						selectedGoods.pop(goods);
					}
				}, $scope.deleteRows = function() {
					if (selectedGoods.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedGoods.length < $scope.goodsList.length) {
						angular.forEach(selectedGoods, function(value, key) {
							$scope.goodsList.splice($scope.goodsList.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedGoods = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);