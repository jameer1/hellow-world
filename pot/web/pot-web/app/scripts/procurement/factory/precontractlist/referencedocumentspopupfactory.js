'use strict';
app.factory('ReferenceDocumentsPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope,
	PreContractInternalService, GenericAlertService) {
	var service = {};
	var refPopup = null;
	service.referenceDocumentDetails = function (preContractTO, hideDeleteButton) {
		var deferred = $q.defer();
		refPopup = ngDialog.open({
			template: 'views/procurement/pre-contractlist/referencedocument.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: [
				'$scope',
				function ($scope) {
					var selectedRef = [];
					$scope.date = $filter('date')(new Date());
					$scope.refdocumentslist = [];
					$scope.preContractTO = preContractTO;
					$scope.projId = preContractTO.projId;
					var deleteRefDocIds = [];
					$scope.selectedFileMap = [];
					$scope.refDocumentMode = ( $rootScope.referenceDocumentMode != undefined ) ? $rootScope.referenceDocumentMode : "";
					$scope.hideDeleteButton = (hideDeleteButton === true) ? true : false;
					$scope.referDockObj = {
						'selected': false,
						'status': 1,
						"date": '',
						"name": '',
						"code": '',
						"version": '',
						"preContractId": $scope.preContractTO.id,
						"projId": $scope.projId
					};
					$scope.getDocumentDetails = function () {
						var req = {
							"contractId": $scope.preContractTO.id,
						}
						console.log(req);
						var documentDetailsPromise = PreContractInternalService
							.getPreContratDocs(req);
						documentDetailsPromise.then(function (data) {
							$scope.refdocumentslist = data.preContractDocsTOs;

						}, function (error) {
							GenericAlertService.alertMessage(
								"Error occured while getting precontract documents",
								"Error");
						});
						return deferred.promise;
					}
					$scope.addRows = function () {
						$scope.refdocumentslist.push(angular.copy($scope.referDockObj));
					}
					$scope.fileUpload = function (fileObject, refdocument, index) {
						if (fileObject) {
							// Max file size should be 5 MB
							if (fileObject.size > 5 * 1024 * 1024) {
								GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
								return;
							}
							refdocument.name = fileObject.name;
							refdocument.fileType = fileObject.type.split('.').pop();
							refdocument.fileSize = formatBytes(fileObject.size);
						} else {
							refdocument.name = null;
							refdocument.fileType = null;
							refdocument.fileSize = null;
						}
						$scope.selectedFileMap[index] = fileObject;
					}
					function formatBytes(bytes) {
						if(bytes < 1024) return bytes + " Bytes";
						else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
						else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
						else return(bytes / 1073741824).toFixed(3) + " GB";
					}
					$scope.referencerowSelect = function (reference) {
						if (reference.select) {
							selectedRef.push(reference);
						} else {
							selectedRef.pop(reference);
						}
					}, $scope.deleteRows = function () {
						const tempRefDocRequest = [];
						var flag = false;
						for (let index = 0; index < $scope.refdocumentslist.length; index++) {
							if ($scope.refdocumentslist[index].select) {
								$scope.selectedFileMap[index] = null;
								flag = true;
							} else {
								tempRefDocRequest.push($scope.refdocumentslist[index]);
							}
						}
						for (let index = 0; index < $scope.refdocumentslist.length; index++) {
							if ($scope.refdocumentslist[index].select <= 0) {
								flag = false;
							}
						}
						if (flag == true) {
							GenericAlertService.alertMessage("Document(s) deleted successfully", "Info");
						} else {
							GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");
						}
						$scope.refdocumentslist = tempRefDocRequest;

					}, $scope.saveReferenceDocument = function () {
						console.log("saveReferenceDocument function");
						var files = [];
						for (let index = 0; index < $scope.selectedFileMap.length; index++) {
							const value = $scope.selectedFileMap[index];
							if (value) {
								$scope.refdocumentslist[index].fileObjectIndex = files.length;
								files.push(value);
							}
						}
						for (const doc of $scope.refdocumentslist) {
							if (isNaN(doc.version) || (doc.fileObjectIndex == null && !doc.code)) {
								GenericAlertService.alertMessage('Please enter version number (or) select file to upload', 'Warning');
								return;
							}

						}
						angular.forEach($scope.refdocumentslist, function (value, key) {
							delete value.selected;
						});
						$scope.folderCategory = ( $scope.refDocumentMode == "Reference Documents" ) ? "PreContract List-Reference Documents" : "Stage2 Approval-Generate PO-Appendices";						
						var req = {
							"preContractDocsTOs": $scope.refdocumentslist,
							"projId": $scope.projId,
							"clientId": $rootScope.account.clientId,
							"category": $scope.folderCategory
						}
						PreContractInternalService.savePreContratDocs(files, req).then(function (response) {
							var succMsg = GenericAlertService.alertMessageModal('Reference Document Details Successfully Saved', 'Info');
							succMsg.then(function (popData) {
								var returnPopObj = {
									"preContractDocsTOs": response.data.preContractDocsTOs
								};
								refPopup.close();
								deferred.resolve(returnPopObj);
							});
						}, function (error) {
							GenericAlertService.alertMessage('Reference Document Details  are failed to Save', 'Error');
						});
					};

				}]

		});
		return deferred.promise;
	}
	return service;
}]);
