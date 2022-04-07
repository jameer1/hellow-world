'use strict';
app.factory('PayRollFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PayRollService", "PayCycleMasterFactory", "PayRollPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PayRollService, PayCycleMasterFactory,PayRollPopUpFactory, GenericAlertService) {
	var service = {};
	$rootScope.projEmpClassmap = [];
	service.getPayRollDetails = function(taxCountryProvisionId) {
		var deferred = $q.defer();
		var payRollReq = {
			"taxId" : taxCountryProvisionId,
			"status" : "1"
		};
		PayRollService.getPayRoll(payRollReq).then(function(data) {
			var payRoll = service.openPayRollPopup(data, taxCountryProvisionId);
			payRoll.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting  PayRoll Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PayRoll Details", 'Error');
		});
		return deferred.promise;

	}, 
	service.openPayRollPopup = function(data, taxCountryProvisionId) {
		var deferred = $q.defer();
		var payRoll = ngDialog.open({
			template : 'views/finance/taxcodetypes/payrollcycle.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editPayRoll = [];
				$scope.payRollList = data.employeeWiseCycleTOs;
				$scope.projEmpClassMap = data.projEmpClassMap;
				$scope.procureCategoryMap = data.procureCategoryMap;
				$scope.payPeriodCycleMap = data.payPeriodCycleMap;
				
				$scope.payRollRowSelect = function(payRoll) {
					if (payRoll.selected) {
						editPayRoll.push(payRoll);
					} else {
						editPayRoll.pop(payRoll);
					}
				}, $scope.addPayRollCyCle = function() {
					var popupDetails = PayCycleMasterFactory.getPayCycleDetails();
					popupDetails.then(function(data) {
				 $scope.payRollList = data.payRollTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
					})

				}, $scope.addPayRoll = function(actionType) {
					if (actionType == 'Edit' && editPayRoll <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = PayRollPopUpFactory.payRollPopupDetails(data,actionType, editPayRoll, taxCountryProvisionId);
						popupDetails.then(function(data) {
							$scope.payRollList = data.payRollTOs;
							editPayRoll = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.deletePayRoll = function() {
					if (editPayRoll.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.payRollList = [];
					} else {
						angular.forEach(editPayRoll, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						PayRollService.deletePayRoll(req).then(function(data) {
							GenericAlertService.alertMessage(' PayRoll Detail(s) is/are Deactivated successfully', 'Info');
						});

						angular.forEach(editPayRoll, function(value, key) {
							$scope.payRollList.splice($scope.payRollList.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('PayRoll Detail(s) is/are failed to Deactivate', "Error");
						});
						editPayRoll = [];
						$scope.deleteIds = [];

					}
				},$scope.comments = function(comments) {
					GenericAlertService.comments(comments);
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
