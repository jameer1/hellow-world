'use strict';
app.factory('CompanyAddressFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "CompanyService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, CompanyService, GenericAlertService) {
	var companyAddressPopUp;
	var service = {};
	service.companyAddressDetails = function(actionType, editAddress, companyId) {
		var deferred = $q.defer();
		companyAddressPopUp = ngDialog.open({
			template : 'views/centrallib/companylist/addaddresspopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			controller : [ '$scope', function($scope) {
				var selectedAddress = [];
				$scope.addressList = [];
				$scope.action = actionType;
				if (actionType === 'Add') {
					$scope.addressList.push({
						'companyId' : companyId,
						'address' : '',
						'city' : '',
						'state' : '',
						'pincode' : '',
						'status' : '1',
						'selected' : false
					});
				} else {
					$scope.addressList = angular.copy(editAddress);
					editAddress = [];
				}
				$scope.addRows = function() {
					$scope.addressList.push({
						'companyId' : companyId,
						'address' : '',
						'city' : '',
						'state' : '',
						'pincode' : '',
						'status' : '1',
						'selected' : false
					});
				}, $scope.saveAddress = function() {
					var req = {
						"addressTOs" : $scope.addressList,
						'companyId' : companyId,
					}
					blockUI.start();
					CompanyService.saveAddress(req).then(function(data) {
						blockUI.stop();
						var results = data.addressTOs;
						// var succMsg = GenericAlertService.alertMessageModal(' Company Address is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal(' Company Address saved successfully',"Info");
						succMsg.then(function(popData) {
							ngDialog.close(companyAddressPopUp);
							var returnPopObj = {
								"addressTOs" : results
							};
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Address(s) is/are failed to Save', 'Error');
					});
				}, $scope.addressPopUpRowSelect = function(address) {
					if (address.selected) {
						selectedAddress.push(address);
					} else {
						selectedAddress.splice(selectedAddress.indexOf(address), 1);
					}
				}, $scope.deleteAddressRows = function() {
					if (selectedAddress.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedAddress.length < $scope.addressList.length) {
						angular.forEach(selectedAddress, function(value, key) {
							$scope.addressList.splice($scope.addressList.indexOf(value), 1);
						});
						selectedAddress = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedAddress.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedAddress.length == 1) {
						$scope.addressList[0] = {
							'companyId' : companyId,
							'address' : '',
							'city' : '',
							'state' : '',
							'pincode' : '',
							'status' : '1',
							'selected' : false
						};
						selectedAddress = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
