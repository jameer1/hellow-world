'use strict';
app.factory('EmpPayRollTaxFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpPayRollTaxPopUpFactory", "EmployeePayRollTaxService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpPayRollTaxPopUpFactory, EmployeePayRollTaxService, GenericAlertService) {
	var empPayRoll;
	var service = {};

	service.getPayRollTaxDetails = function(taxTypeId) {
		var deferred = $q.defer();
		var empPayRollTaxReq = {
			"taxId" : taxTypeId,
			"status" : "1"
		};
		EmployeePayRollTaxService.getEmployeePayroll(empPayRollTaxReq).then(function(data) {
			var empPayRoll = [];
			empPayRoll = service.openEmpPayRollTaxPopup(data.employeePayRollTaxTOs, taxTypeId);
			empPayRoll.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Regular Pay Details", 'Error');
		});
		return deferred.promise;

	}, service.openEmpPayRollTaxPopup = function(employeePayRollTaxTOs, taxTypeId) {
		var deferred = $q.defer();
		empPayRoll = ngDialog.open({
			template : 'views/finance/taxtypes/employeepayrolltax.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var service = {}
				var editPayRollTax = [];
				$scope.payrollTax = employeePayRollTaxTOs;

				$scope.emptaxrowselect = function(emp) {
					if (emp.selected) {
						editPayRollTax.push(emp);
					} else {
						editPayRollTax.pop(emp);
					}
				}
				$scope.addEmpPayrollTax = function(actionType) {
					if (actionType == 'Edit' && editPayRollTax <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
						return;
					} else {
						var popupDetails = EmpPayRollTaxPopUpFactory.empPayRollTaxPopupDetails(actionType, editPayRollTax, taxTypeId);
						popupDetails.then(function(data) {
							$scope.payrollTax = data.employeePayRollTaxTOs;
							editPayRollTax = [];
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
						})
					}
				}
				$scope.detetePayrollTax = function() {
					if (editPayRollTax.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
						return;
					}
					var deleteIds = [];
					$scope.nondeleteIds = [];
					if ($scope.selectedAll) {
						$scope.payrollTax = [];
					} else {
						angular.forEach(editPayRollTax, function(value, key) {
							if (value.id) {
								deleteIds.push(value.id);
							}
						});
						var req = {
							"taxIds" : deleteIds,
							"status" : 2
						};
						EmployeePayRollTaxService.deleteEmployeePayroll(req).then(function(data) {
							GenericAlertService.alertMessage('editPayRollTax Details are Deactivated successfully', 'Info');
						});

						angular.forEach(editPayRollTax, function(value, key) {
							$scope.payrollTax.splice($scope.payrollTax.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('editPayRollTax Details are failed to Deactivate', "Error");
						});
						editPayRollTax = [];
						$scope.deleteIds = [];

					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
