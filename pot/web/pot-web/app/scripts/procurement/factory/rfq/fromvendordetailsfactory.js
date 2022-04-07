'use strict';

app.factory('FromVendorDetailsFactory', ["ngDialog", "$q", "RFQService","$rootScope", "GenericAlertService", function (ngDialog, $q, RFQService,$rootScope, GenericAlertService) {

	var service = {};
	service.getFromVendorDetails = function (preContractCmpTO,
		preContractCompanyMap, usersMap) {
		var deferred = $q.defer();
		var req = {
			"preContractCmpId": preContractCmpTO.id,
			"fromVendor": true,
			"status" : 1
		}
		RFQService.getPreContratCmpDocsByType(req).then(
			function (data) {
				var docpopup = service.openPopup(data.preContractCmpDocsTOs, preContractCmpTO,
					data.companyMap, usersMap);
				docpopup.then(function (data) {
					deferred.resolve(data);
				});
			},
			function (error) {
				GenericAlertService.alertMessage("Error occured while getting vendor documents",
					'Error');
			});
		return deferred.promise;
	};

	service.openPopup = function (preContractCmpDocsTOs, preContractCmpTO,
		companyMap, usersMap) {
		var deferred = $q.defer();
		var toVendorPopUp = ngDialog.open({
			template: 'views/procurement/RFQ/fromvendor.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose: false,
			closeByDocument: false,
			controller: [
				'$scope',
				function ($scope) {
					$scope.preContractCmpTO = preContractCmpTO
					$scope.preContractCompanyMap = companyMap;
					$scope.usersMap = usersMap;
					$scope.preContractReqApprList = [];
					$scope.vendorDocuments = preContractCmpDocsTOs;
					console.log($scope.vendorDocument);
					$scope.approverUserMap = [];
					$scope.selectedFileMap = [];
					var selectedDocs = [];
					$scope.referDockObj = {
						"id": null,
						'selected': false,
						"date": '',
						"name": '',
						"code": '',
						"version": '',
						"modeType": 'Email',
						"sender": '',
						"receiver": '',
						"uploadViewFile": '',
						"fileSize": '',
						"mimeType": null,
						"description": '',
						"preContractCmpId": preContractCmpTO.id,
						"projId": preContractCmpTO.preContractTO.projId
					};

					$scope.addVendorDocument = function () {
						console.log("from addVendorDocument function");
						$scope.vendorDocuments.push(angular.copy($scope.referDockObj));
					};
					$scope.uploadVendorDocument = function (fileObject,vendor,index) {
						if (fileObject) {
							// Max file size should be 5 MB
							if (fileObject.size > 5 * 1024 * 1024) {
								GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
								return;
							}
							vendor.name = fileObject.name;
							vendor.mimeType = fileObject.type.split('.').pop();
							vendor.fileSize = formatBytes(fileObject.size);
						} else {
							vendor.name = null;
							vendor.mimeType = null;
							vendor.fileSize = null;
						}
						$scope.selectedFileMap[index] = fileObject;
					};
					function formatBytes(bytes) {
						if(bytes < 1024) return bytes + " Bytes";
						else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
						else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
						else return(bytes / 1073741824).toFixed(3) + " GB";
					}
					$scope.rowSelect = function (vendorDocTO) {
						if (vendorDocTO.select) {
							selectedDocs.push(vendorDocTO);
						} else {
							const index = selectedDocs.indexOf(vendorDocTO);
							if (index != -1)
								selectedDocs.splice(index, 1);
						}
					};
					$scope.deleteVendorDocument = function () {

						if (selectedDocs.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						} else if (selectedDocs.length < $scope.vendorDocuments.length) {
							for (const value of selectedDocs) {
								$scope.vendorDocuments.splice($scope.vendorDocuments.indexOf(value), 1);
							}
							selectedDocs = [];
							GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
						} else if (selectedDocs.length > 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						} else if (selectedDocs.length == 1) {
							$scope.vendorDocuments[0] = {
								"id": null,
								'selected': false,
								"date": '',
								"name": '',
								"code": '',
								"version": '',
								"sender": '',
								"modeType": 'Email',
								"uploadViewFile": '',
								"fileSize": '',
								"mimeType": null,
								"preContractCmpId": preContractCmpTO.id,
								"projId": preContractCmpTO.preContractTO.projId,
								"description": ''
							};
							selectedDocs = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					};

					$scope.saveFromVendorDocuments = function () {
					if (preContractCmpDocsTOs[(preContractCmpDocsTOs.length)-1].description == "" ||
							preContractCmpDocsTOs[(preContractCmpDocsTOs.length)-1].version == "" ||
							preContractCmpDocsTOs[(preContractCmpDocsTOs.length)-1].sender == "" ||
							preContractCmpDocsTOs[(preContractCmpDocsTOs.length)-1].receiver == "") {
							GenericAlertService.alertMessage('Please enter all required details', 'Warning');
							return;
						}
						const req = {
							"preContractCmpDocsTOs": $scope.vendorDocuments,
							"folderCategory": "RFQ-Correspondance From Vendor",
							"projId": preContractCmpTO.preContractTO.projId,
							"clientId": $rootScope.account.clientId,
							"userId" : $rootScope.account.userId
						}
						var files = [];
						for (let index = 0; index < $scope.selectedFileMap.length; index++) {
							const value = $scope.selectedFileMap[index];
							if (value) {
								$scope.vendorDocuments[index].fileObjectIndex = files.length;
								files.push(value);
							}
						}
						angular.forEach($scope.vendorDocuments, function (value, key) {
							delete value.selected;
							delete value.uploadViewFile;
						});
						
						RFQService.savePreContratCompnayDocs(files,req).then(function (data) {
							var succMsg = GenericAlertService.alertMessageModal(
								'Vendor Documents saved successfully ', 'Info');
							succMsg.then(function (popData) {
								const returnPopObj = {
									"preContractDocsTOs": data.preContractDocsTOs
								};
								$scope.closeThisDialog();
								deferred.resolve(returnPopObj);
							});
						}, function (error) {
							GenericAlertService.alertMessage('Vendor Documents failed to Save', 'Error');
						});
					};
				}]
		});
		return deferred.promise;
	};
	return service;
}]);
