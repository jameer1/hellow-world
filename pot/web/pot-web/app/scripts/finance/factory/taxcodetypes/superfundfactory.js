'use strict';
app.factory('SuperFundFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "SuperFundService", "SuperFundPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, SuperFundService, SuperFundPopUpFactory, GenericAlertService) {
	var superFund;
	var service = {};

	service.getSuperProvidentFundDetails = function(taxCountryProvisionId) {
		var deferred = $q.defer();
		var superfundReq = {
			"taxId" : taxCountryProvisionId,
			"status" : "1"
		};
		SuperFundService.getSuperFund(superfundReq).then(function(data) {
			var superFund = [];
			superFund = service.openSuperFundPopup(data.providentFundTOs, taxCountryProvisionId);
			superFund.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting SuperFund Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting SuperFund Details", 'Error');
		});
		return deferred.promise;

	}, service.openSuperFundPopup = function(superFundTOs, taxCountryProvisionId) {
		var deferred = $q.defer();
		superFund = ngDialog.open({
			template : 'views/finance/taxcodetypes/superprovidentfund.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editSuperFund = [];
				$scope.superFundList = superFundTOs;

				$scope.superFundRowSelect = function(superFund) {
					if (superFund.selected) {
						editSuperFund.push(superFund);
					} else {
						editSuperFund.pop(superFund);
					}
				}

				$scope.addSuperFund = function(actionType) {
					if (actionType == 'Edit' && editSuperFund <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = SuperFundPopUpFactory.superFundPopupDetails(actionType, editSuperFund, taxCountryProvisionId);
						popupDetails.then(function(data) {
							$scope.superFundList = data.superFundTOs;
							editSuperFund = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deleteSuperFund = function() {
					if (editSuperFund.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.superFundList = [];
					} else {
						angular.forEach(editSuperFund, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						SuperFundService.deleteSuperFund(req).then(function(data) {
							GenericAlertService.alertMessage('SuperFund Detail(s) is/are Deactivated successfully', 'Info');
						});

						angular.forEach(editSuperFund, function(value, key) {
							$scope.superFundList.splice($scope.superFundList.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('SuperFund Detail(s) is/are failed to Deactivate', "Error");
						});
						editSuperFund = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
