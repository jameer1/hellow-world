'use strict';

app.factory('BankAccountFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {
	var service = {};
	service.openPopup = function(addEmpBankAccountDetails, empId) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/projresources/projempreg/bankaccountdetails/empbankaccountdetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.addEmpBankAccountDetails = addEmpBankAccountDetails;
				var selectedEmpBankDetails = [];
				$scope.accountTypes = [ 'Current', 'Savings' ];
				$scope.accountStatusTypes = [ 'Current', 'Superseded ' ];
				$scope.addRows = function() {
					$scope.addEmpBankAccountDetails.push({
						"selected" : false,
						"status" : 1,
						"empRegDtlId" : empId,
						"date" : null,
						"bankName" : null,
						"address" : null,
						"ifscCode" : null,
						"accName" : null,
						"accNumber" : null,
						"accType" : null,
						"accStatus" : null,
						"accComments" : null
					});
				}, $scope.BankAccPopUpRowSelect = function(account) {
					if (account.selected) {
						selectedEmpBankDetails.push(account);
					} else {
						selectedEmpBankDetails.pop(account);
					}
				}, $scope.deleteRows = function() {
					if (selectedEmpBankDetails.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedEmpBankDetails.length < $scope.addEmpBankAccountDetails.length) {
						angular.forEach(selectedEmpBankDetails, function(value, key) {
							$scope.addEmpBankAccountDetails.splice($scope.addEmpBankAccountDetails.indexOf(value), 1);
						});
						selectedEmpBankDetails = [];
						GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.saveEmpBankAccountDetails = function() {
					var req = {
						"empBankAccountDtlTOs" : $scope.addEmpBankAccountDetails,
						"empId" : $rootScope.selectedEmployeeData.id,
						"status":1
					};
					EmpRegisterService.saveEmpBankAccountDetails(req).then(function(data) {
						// var succMsg = GenericAlertService.alertMessageModal('Employee Bank Account Detail(s) is/are  ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Employee Bank Account Detail(s) saved successfully',"Info" );
						succMsg.then(function() {
							$scope.closeThisDialog();
							var returnPopObj = {
								"empBankAccountDtlTOs" : data.empBankAccountDtlTOs
							};
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Employee Bank Account Detail(s) is/are Failed to Save ', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}

	service.getEmpBankAccountDetails = function(actionType, editEmpBankAccountDetails, empId) {
		var deferred = $q.defer();
		var addEmpBankAccountDetails = [];
		$rootScope.action = actionType;
		if (actionType === 'Add') {
			addEmpBankAccountDetails.push({
				"selected" : false,
				"status" : 1,
				"empRegDtlId" : empId,
				"date" : null,
				"bankName" : null,
				"address" : null,
				"ifscCode" : null,
				"accName" : null,
				"accNumber" : null,
				"accType" : null,
				"accStatus" : null,
				"accComments" : null
			});
		}

		else if (actionType === 'Edit') {
			addEmpBankAccountDetails = angular.copy(editEmpBankAccountDetails);
		}
		var popdata = service.openPopup(addEmpBankAccountDetails, empId);
		popdata.then(function(data) {
			var returnPopObj = {
				"empBankAccountDtlTOs" : data.empBankAccountDtlTOs
			};
			deferred.resolve(returnPopObj);
		});
		return deferred.promise;
	}

	return service;
}]);