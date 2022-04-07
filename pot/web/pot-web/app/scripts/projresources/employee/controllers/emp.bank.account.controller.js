'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empbankaccount", {
		url : '/empbankaccount',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projempreg/bankaccountdetails/empbankaccountdetails.html',
				controller : 'EmpBankAccountController'
			}
		}
	})
}]).controller("EmpBankAccountController", ["$rootScope", "$scope", "utilservice", "BankAccountFactory", "GenericAlertService", "$location", "EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, utilservice, BankAccountFactory, GenericAlertService, $location, EmpRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.empBankAccountDtlTOs = [];
	var editEmpBankAccountDetails = [];

	$scope.getEmpbankAccountDetails = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the Employee", "Warning");
			return;
		}
		var getBankAccountReq = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id
		};
		EmpRegisterService.getEmpBankAccountDetails(getBankAccountReq).then(function(data) {
			editEmpBankAccountDetails = [];
			$scope.empBankAccountDtlTOs = data.empBankAccountDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.empBankAccountDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error Occured While Getting Employee Bank Account Details", 'Error');
		});
	}, $scope.getEmpbankAccountDetails();
	$scope.backAccountRowSelect = function(account) {
		if (account.selected) {
			utilservice.addItemToArray(editEmpBankAccountDetails, "id", account);
		} else {
			editEmpBankAccountDetails.splice(editEmpBankAccountDetails.indexOf(account), 1);
		}
	}, $scope.deactivateBankAccountDtls = function() {
		if (editEmpBankAccountDetails.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.empBankAccountDtlTOs = [];
		} else {
			angular.forEach(editEmpBankAccountDetails, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"empBankAccountIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do You Want to Deactivate Employee Bank Account Detail(s)', 'Info', 'YES', 'NO').then(function(data) {
				EmpRegisterService.deactivateBankAccDetails(req).then(function(data) {
					angular.forEach(editEmpBankAccountDetails, function(value, key) {
						GenericAlertService.alertMessage('Employee Bank Account Detail(s) is/are Deactivated successfully', 'Info');
						$scope.empBankAccountDtlTOs.splice($scope.empBankAccountDtlTOs.indexOf(value), 1);
						editEmpBankAccountDetails = [];
						$scope.deleteIds = [];
					})
				}, function(error) {
					angular.forEach(editEmpBankAccountDetails, function(value) {
						value.selected = false;
					});
					GenericAlertService.alertMessage('Employee  Bank Account Detail(s) is/are failed to Deactivate', "Error");
				})
			}, function(error) {
				angular.forEach(editEmpBankAccountDetails, function(value) {
					value.selected = false;
				});
				editEmpBankAccountDetails = [];
				GenericAlertService.alertMessage('Employee  Bank Account Detail(s) is/are not Deactivated', "Info");
			})
		}
	},

	$scope.addBankAccountDetails = function(actionType) {
		angular.forEach(editEmpBankAccountDetails, function(value) {
			value.selected = false;
		});
		if (actionType == 'Edit' && editEmpBankAccountDetails <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var bankAccountPopup = BankAccountFactory.getEmpBankAccountDetails(actionType, editEmpBankAccountDetails, $rootScope.selectedEmployeeData.id);
		bankAccountPopup.then(function(data) {
			$scope.empBankAccountDtlTOs = data.empBankAccountDtlTOs;
			editEmpBankAccountDetails = [];
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching emp bank account details", 'Error');
		});
	},

	
	$scope.$on("resetBankAccount", function() {
		$scope.empBankAccountDtlTOs = [];
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.backAccountRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'fromDate', displayName: "Effective From", headerTooltip: "Effective From"},
						{ field: 'bankName', displayName: "Name of Bank / Financial Institute", headerTooltip: "Name of Bank / Financial Institute", },
						{ field: 'address', displayName: "Bank Street Address", headerTooltip: "Bank Street Address"},
						{ field: 'ifscCode', displayName: "Bank / Financial Institute Code", headerTooltip: "Bank / Financial Institute Code",},
						{ field: 'accName', displayName: "Account Holder Name", headerTooltip: "Account Holder Name",},
						{ field: 'accNumber', displayName: "Account Holder Number", headerTooltip: "Account Holder Number"},
						{ field: 'accType', displayName: "Type of Account", headerTooltip: "Type of Account"},
						{ field: 'accStatus', displayName: "Account Status", headerTooltip: "Account Status"},
						{ field: 'accComments', displayName: "Notes", headerTooltip: "Notes"},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_BankAccount");
					$scope.getEmpbankAccountDetails();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);