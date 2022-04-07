'use strict';

app.factory(
	'PurchaseToVendorDetailsFactory',
	["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "RFQService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, RFQService,
		GenericAlertService) {

		var service = {};
		service.getToVendorDocuments = function (preContractCmpTO,
			companyMap) {
			var deferred = $q.defer();
			var req = {
				"preContractCmpId": preContractCmpTO.id,
				"fromVendor": false
			}
			RFQService.getPreContratCmpDocs(req).then(
				function (data) {
					var docpopup = service.openPopup(data.preContractCmpDocsTOs, preContractCmpTO, companyMap);
					docpopup.then(function (data) {
						deferred.resolve(data);
					});
				},
				function (error) {
					GenericAlertService.alertMessage("Error occured while getting vendor documents", 'Error');
				});
			return deferred.promise;
		};

		service.openPopup = function (preContractCmpDocsTOs, preContractCmpTO,
			companyMap) {
			var deferred = $q.defer();
			var toVendorPopUp = ngDialog
				.open({
					template: 'views/procurement/purchaseorders/purchasetovendor.html',
					showClose: false,
					className: 'ngdialog-theme-plain ng-dialogueCustom0',
					closeByDocument: false,
					controller: [
						'$scope',
						function ($scope) {
							$scope.preContractCmpTO = preContractCmpTO
							$scope.preContractCompanyMap = companyMap;
							$scope.preContractReqApprList = [];
							$scope.vendorDocuments = preContractCmpDocsTOs;
							$scope.approverUserMap = [];
							var selectedDocs = [];
							$scope.referDockObj = {
								"id": null,
								'selected': false,
								"date": '',
								"name": '',
								"code": '',
								"version": '',
								"modeType": 'Email',
								"uploadViewFile": '',
								"sizeofFile": '',
								"mimeType": null,
								"fromVendor": false,
								"preContractCmpId": preContractCmpTO.id,
								"projId": preContractCmpTO.preContractTO.projId
							};

							$scope.addVendorDocument = function () {
								$scope.vendorDocuments.push(angular.copy($scope.referDockObj));
							};
							$scope.uploadVendorDocument = function (
								documentTO) {
								GenericAlertService.alertMessage("To Be Implemented", 'Warning');
							};
							$scope.rowSelect = function (
								vendorDocTO) {
								if (vendorDocTO.select) {
									selectedDocs.push(vendorDocTO);
								} else {
									selectedDocs.pop(vendorDocTO);
								}
							};
							$scope.deleteVendorDocument = function () {
								if (selectedDocs.length == 0) {
									GenericAlertService.alertMessage(
										'Please select atleast one row to delete', "Warning");
									return;
								}
								if ($scope.vendorDocuments.length == 1) {
									GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
									return;
								}

								if (selectedDocs.length < $scope.vendorDocuments.length) {
									angular.forEach(selectedDocs, function (value, key) {
										$scope.vendorDocuments.splice($scope.vendorDocuments.indexOf(value), 1);
									});

									selectedDocs = [];

								} else {
									GenericAlertService.alertMessage('Please leave atleast one row',
										"Warning");
								}
							};
							$scope.saveVendorDocuments = function () {
								var req = {
									"preContractCmpDocsTOs": $scope.vendorDocuments
								}
								RFQService.savePreContratCompnayDocs(req)
									.then(function (data) {
										var succMsg = GenericAlertService.alertMessageModal(
											'Vendor Documents are saved successfully ', 'Info');
										succMsg.then(function (popData) {
											var returnPopObj = {
												"preContractDocsTOs": data.preContractDocsTOs
											};
											deferred.resolve(returnPopObj);
										});
									}, function (error) {
										GenericAlertService.alertMessage(
											'Vendor Documents  are failed to Save', 'Error');
									});
							};

						}]
				});
			return deferred.promise;
		};
		return service;
	}]);
