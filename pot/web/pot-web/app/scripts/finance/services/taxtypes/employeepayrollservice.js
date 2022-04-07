'use strict';

app.factory('EmployeePayRollTaxService', ["Restangular", "$http", function(Restangular, $http) {
	return {
		getEmployeePayroll : function(req) {
			var employeeTax = Restangular.one("finance/getEmployeePayroll")
					.customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return employeeTax;
		},
		saveEmployeePayroll : function(req) {
			var empStatus = Restangular.one("finance/saveEmployeePayroll").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return empStatus;
		},
		deleteEmployeePayroll: function(req) {
			var deleteEmp = Restangular.one("finance/deleteEmployeePayroll").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return deleteEmp;
		}
		
	}
}]);
