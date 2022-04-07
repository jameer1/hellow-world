'use strict';
app.factory('CompanyBankFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "CompanyService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, CompanyService, GenericAlertService) {
	var companyBankPopUp;
	var service = {};
	service.companyBankDetails = function(actionType, editBank, companyId, bankList) {
		var deferred = $q.defer();
		companyBankPopUp = ngDialog.open({
			template : 'views/centrallib/companylist/addbankpopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			controller : [ '$scope', function($scope) {
				var selectedBank= [];
				$scope.bankList = [];
				$scope.action = actionType;
				$scope.names=[];
				angular.forEach(bankList,function(value,key){
					$scope.names.push(value);
				});
				console.log($scope.names.length);
				$scope.duplicateFlag = false;
				$scope.checkDuplicate1 = function(bank){
					angular.forEach(bankList,function(value,key){
						if(bank.accountNumber == value.accountNumber){
							$scope.duplicateFlag = true;  
					}
					})
						
					  
                 
				}
				if (actionType === 'Add') {
					$scope.bankList.push({
						'companyId' : companyId,
						'accountName' : '',
						'bankName' : '',
						'bankCode' : '',
						'accountNumber' : '',
						'bankAddress' : '',
						'selected' : false
					});
				} else {
					$scope.bankList = angular.copy(editBank);
					editBank = [];
				}
				$scope.addRows = function() {
					$scope.bankList.push({
						'companyId' : companyId,
						'accountName' : '',
						'bankName' : '',
						'bankCode' : '',
						'accountNumber' : '',
						'bankAddress' : '',
						'selected' : false
					});
				}, $scope.saveBank = function() {
					if($scope.duplicateFlag){
						GenericAlertService.alertMessage('Account Number already Exist', "Warning");
							return; 
					}
					var req = {
						"bankTOs" : $scope.bankList,
						'companyId' : companyId,
					}
					blockUI.start();
					CompanyService.saveBank(req).then(function(data) {
						blockUI.stop();
						var results = data.bankTOs;
						// var succMsg = GenericAlertService.alertMessageModal(' Company Address is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal(' Company bank saved successfully',"Info");
						succMsg.then(function(popData) {
							ngDialog.close(companyBankPopUp);
							var returnPopObj = {
								"bankTOs" : results
							};
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('bank(s) is/are failed to Save', 'Error');
					});
				}, $scope.bankPopUpRowSelect = function(bank) {
					if (bank.selected) {
						selectedBank.push(bank);
					} else {
						selectedBank.splice(selectedBank.indexOf(bank), 1);
					}
				}, $scope.deleteBankRows = function() {
					if (selectedBank.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedBank.length < $scope.bankList.length) {
						angular.forEach(selectedBank, function(value, key) {
							$scope.bankList.splice($scope.bankList.indexOf(value), 1);
						});
						selectedBank = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedBank.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedBank.length == 1) {
						$scope.bankList[0] = {
								'companyId' : companyId,
								'accountName' : '',
								'bankName' : '',
								'bankCode' : '',
								'accountNumber' : '',
								'bankAddress' : '',
								'selected' : false

						};
						selectedBank = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
