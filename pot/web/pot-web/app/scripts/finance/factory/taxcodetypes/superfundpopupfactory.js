'use strict';
app.factory('SuperFundPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "SuperFundService", "PayRollService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, SuperFundService, PayRollService, GenericAlertService) {
	var superfundPopup;
	var selectedSuperFund = [];
	var service = {};
	service.superFundPopupDetails = function(actionType, editSuperFund, codeTypeCountryProvisionId) {
		var deferred = $q.defer();
		superfundPopup = ngDialog.open({
			template : 'views/finance/taxcodetypes/superfundpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedSuperFund = [];
				$scope.addSuperFund = [];
				$scope.dropDownLists = [];
				$scope.superFundPayList1 = [];
				$scope.payPeriodCycleList = [];
				if (actionType === 'Add') {
					$scope.addSuperFund.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'superFundLabelKeyTO' : {
							"name" : null
						},
						'creditCycleLabelKeyTO' : {
							"creditCycle" : null
						}

					});
				} else {
					$scope.addSuperFund = angular.copy(editSuperFund);
					editSuperFund = [];
				}
				$scope.addRows = function() {

					$scope.addSuperFund.push({
						'selected' : false,
						'status' : 1,
						"codeTypeCountryProvisionId" : codeTypeCountryProvisionId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'superFundLabelKeyTO' : {
							"name" : null
						},
						'creditCycleLabelKeyTO' : {
							"creditCycle" : null
						}

					});
				}, $scope.payPeriodCycle = function() {
					var dropDownReq = {
						"status" : "1"
					}
					PayRollService.getPayRollCycle(dropDownReq).then(function(data) {
						$scope.dropDownLists = data;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting SuperFund Detail(s) is/are", 'Error');
					});
				}, $scope.saveSuperFund = function() {
					var req = {
						"providentFundTOs" : $scope.addSuperFund,
					}
					blockUI.start();
					SuperFundService.saveSuperFund(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('Superfund Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Superfund Detail(s) is/are failed to Save', 'Error');
					});
					$scope.closeThisDialog();
				};

				$scope.superFundPopupRowSelect = function(superFund) {
					if (superFund.selected) {
						selectedSuperFund.push(superFund);
					} else {
						selectedSuperFund.pop(superFund);
					}
				}, $scope.deleteRows = function() {
					if (selectedSuperFund.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedSuperFund.length < $scope.addSuperFund.length) {
						angular.forEach(selectedSuperFund, function(value, key) {
							$scope.addSuperFund.splice($scope.addSuperFund.indexOf(value), 1);
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