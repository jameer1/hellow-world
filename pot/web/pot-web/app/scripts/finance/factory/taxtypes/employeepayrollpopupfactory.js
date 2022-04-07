'use strict';
app.factory('EmpPayRollTaxPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "EmployeePayRollTaxService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, EmployeePayRollTaxService, GenericAlertService) {
	var payRollPopup;
	var selectedPayRollTax = [];
	var service = {};
	service.empPayRollTaxPopupDetails = function(actionType, editPayRollTax, taxTypeId) {
		var deferred = $q.defer();
		payRollPopup = ngDialog.open({
			template : 'views/finance/taxtypes/employeepayrollpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedPayRollTax = [];
				$scope.empList = [];

				if (actionType === 'Add') {
					$scope.empList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'taxStatus' : null,

					});
				} else {
					$scope.empList = angular.copy(editPayRollTax);
					editPayRollTax = [];
				}
				$scope.addempTaxRows = function() {

					$scope.empList.push({
						'selected' : false,
						'status' : 1,
						"taxCodeCountryProvisionId" : taxTypeId,
						'code' : null,
						'name' : null,
						'comments' : null,
						'taxStatus' : null,

					});
				}, $scope.saveemppayroll = function() {
					var req = {
						"employeePayRollTaxTOs" : $scope.empList,
					}
					blockUI.start();
					EmployeePayRollTaxService.saveEmployeePayroll(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							GenericAlertService.alertMessageModal('EmployeePayRollTax Details ' + data.message, data.status);
						}

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('EmployeePayRollTax Details  are failed to Save', 'Error');
					});
					ngDialog.close();
				};

				$scope.empPopUpRowSelect = function(emp) {
					if (emp.selected) {
						selectedPayRollTax.push(emp);
					} else {
						selectedPayRollTax.pop(emp);
					}
				}, $scope.deleteProjRows = function() {
					if (selectedPayRollTax.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPayRollTax.length < $scope.empList.length) {
						angular.forEach(selectedPayRollTax, function(value, key) {
							$scope.empList.splice($scope.empList.indexOf(value), 1);

						});
						selectedPayRollTax = [];
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