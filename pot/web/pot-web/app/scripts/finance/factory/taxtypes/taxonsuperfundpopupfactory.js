'use strict';
app.factory('TaxOnSuperFundPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "TaxonSuperFundService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, TaxonSuperFundService, GenericAlertService) {
	var taxSuperFundPopup;
	var selectedSuperFund = [];
	var service = {};
	service.taxOnSuperFundPopupDetails = function(actionType, editSuperFund, taxTypeId) {
		var deferred = $q.defer();
		taxSuperFundPopup = ngDialog.open({
			template : 'views/finance/taxtypes/taxonsuperfundpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedSuperFund = [];
				$scope.taxonList = [];

				if (actionType === 'Add') {
					$scope.taxonList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						"taxCalMstrId" : '',
						"limitIncome" : '',
						"fundAmount" : '',
						"taxRate" : '',
						"comments" : '',
					});
				} else {
					$scope.taxonList = angular.copy(editSuperFund);
					editSuperFund = [];
				}
				$scope.addtaxonRows = function() {

					$scope.taxonList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						"taxCalMstrId" : '',
						"limitIncome" : '',
						"fundAmount" : '',
						"taxRate" : '',
						"comments" : '',

					});
				}, $scope.save = function() {
					var req = {
						"taxOnSuperFundTOs" : $scope.taxonList,
					}
					blockUI.start();
					TaxonSuperFundService.saveSuperfundTax(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('SuperFund Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('SuperFund Details  are failed to Save', 'Error');
					});
					ngDialog.close();
				};

				$scope.taxonPopUpRowSelect = function(taxon) {
					if (taxon.selected) {
						selectedSuperFund.push(taxon);
					} else {
						selectedSuperFund.pop(taxon);
					}
				}, $scope.deleteRows = function() {
					if (selectedSuperFund.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedSuperFund.length < $scope.taxonList.length) {
						angular.forEach(selectedSuperFund, function(value, key) {
							$scope.taxonList.splice($scope.taxonList.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedSuperFund = [];
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