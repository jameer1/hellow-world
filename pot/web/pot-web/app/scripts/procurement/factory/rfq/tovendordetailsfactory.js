'use strict';

app.factory('ToVendorDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "RFQService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, RFQService,
	GenericAlertService) {

	var service = {};
	service.getToVendorDocuments = function (preContractCmpTO,
		companyMap, usersMap) {
		var deferred = $q.defer();
		var req = {
			"preContractCmpId": preContractCmpTO.preContractTO.id,
			"fromVendor": false
		}
		RFQService.getPreContratCmpDocs(req).then(
			function (data) {
				var docpopup = service.openPopup(data.preContractCmpDocsTOs, preContractCmpTO,
					data.companyMap, usersMap);
				docpopup.then(function (data) {
					deferred.resolve(data);
				});
			},
			function (error) {
				GenericAlertService.alertMessage(
					"Error occured while getting vendor documents", 'Error');
			});
		return deferred.promise;
	};

	service.openPopup = function (preContractCmpDocsTOs, preContractCmpTO, companyMap, usersMap) {
		var deferred = $q.defer();
		var toVendorPopUp = ngDialog
			.open({
				template: 'views/procurement/RFQ/tovendor.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				showClose: false,
				closeByDocument: false,
				controller: ['$scope', function ($scope) {
					$scope.preContractCmpTO = preContractCmpTO
					$scope.companyMap = companyMap;
					$scope.preContractReqApprList = [];
					$scope.vendorDocuments = preContractCmpDocsTOs;
					$scope.approverUserMap = [];
					var selectedDocs = [];
					$scope.usersMap = usersMap;
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
						"preContractCmpId": preContractCmpTO.id,
						"projId": preContractCmpTO.preContractTO.projId
					};

					$scope.addVendorDocument = function () {
						$scope.vendorDocuments.push(angular.copy($scope.referDockObj));
					};
					$scope.uploadVendorDocument = function (documentTO) {
						GenericAlertService.alertMessage("To Be Implemented", 'Warning');
					};
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
							angular.forEach(selectedDocs, function (value, key) {
								$scope.vendorDocuments.splice($scope.vendorDocuments.indexOf(value), 1);
							});
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
								"modeType": 'Email',
								"uploadViewFile": '',
								"sizeofFile": '',
								"mimeType": null,
								"fromVendor": false,
								"preContractCmpId": preContractCmpTO.id,
								"projId": preContractCmpTO.preContractTO.projId
							};
							selectedDocs = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					};
					
					$scope.downloadFile = function(documentId,document_name) {
						//$scope.folderCategory = ( $scope.refDocumentMode == "Reference Documents" ) ? "PreContract List-Reference Documents" : "Stage2 Approval-Generate PO-Appendices";
						let req = {
							"recordId" : documentId,
							"category" : "PreContract List-Reference Documents",
							"fileName" : document_name
						}
						console.log(req);
						//EmpRegisterService.downloadRegisterDocs(req);
						RFQService.downloadProcurementDocs(req);
						console.log("downloadFile function");
					}
					
					$scope.saveVendorDocuments = function () {
						var req = {
							"preContractCmpDocsTOs": $scope.vendorDocuments
						}
						RFQService.savePreContratCompnayDocs(req).then(function (data) {
							var succMsg = GenericAlertService.alertMessageModal('Vendor Documents saved successfully ',
								'Info');
							succMsg.then(function (popData) {
								var returnPopObj = {
									"preContractDocsTOs": data.preContractDocsTOs
								};
								$scope.closeThisDialog();
								deferred.resolve(returnPopObj);
							});
						}, function (error) {
							GenericAlertService.alertMessage('Vendor Documents  are failed to Save', 'Error');
						});

					};

				}]
			});
		return deferred.promise;
	};
	return service;
}]);
