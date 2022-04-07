'use strict';
app.factory('EmpDocumentsPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService","EmpRegisterService", function (ngDialog, $q, $filter, $timeout, $rootScope,
	GenericAlertService, EmpRegisterService) {
	var service = {};
	var refPopup = null;
	service.referenceDocumentDetails = function (empRefDocumentsTO,mode,category) {
		var deferred = $q.defer();
		refPopup = ngDialog.open({
			template: 'views/projresources/common/empdocumentspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $rootScope,
			closeByDocument: false,
			showClose: false,
			controller: [
				'$scope',
				function ($scope) {
					$scope.title = ( mode == "Add" ) ? "Add Employee Documents" : "Download Documents";
					$scope.documentMode = mode;
					var selectedRef = [];
					$scope.date = $filter('date')(new Date());
					$scope.refdocuments1 = [];
					$scope.empRefDocumentsTO = empRefDocumentsTO;
					$scope.projId = empRefDocumentsTO.projId;
					var deleteRefDocIds = [];
					$scope.selectedFileMap = [];
					$scope.hideDeleteButtonDiv = false;
					$scope.documentCategory = category;
					
					$scope.referDockObj = {
						'selected': false,
						'status': 1,
						"date": '',
						"name": '',
						"code": '',
						"version": '',						
						"description" : '',
						"fileName" : '',
						"fileSize" : '',
						"empDocumentId" : null					
					};
					$scope.getEmpDocumentDetails = function () {
						console.log("getDocumentDetails function");
						console.log($rootScope.selectedEmployeeData);
						var req = {
							"empId": $rootScope.selectedEmployeeData.id,
							"empProjId" : $rootScope.selectedEmployeeData.projEmpRegisterTO.id,
							"folderCategory" : $scope.documentCategory
						}
						console.log(req);
						
						var documentDetailsPromise = EmpRegisterService.getEmployeeDocs(req);
						documentDetailsPromise.then(function (data) {
							angular.forEach( data.projDocFileTOs, function(value,key){
								console.log(key);
								console.log(value);
								value.selected = true
							});
							$scope.refdocuments1 = data.projDocFileTOs;
							//console.log(data);
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting precontract documents","Error");
						});
						return deferred.promise;
					}
					$scope.addRows = function () {
						$scope.refdocuments1.push(angular.copy($scope.referDockObj));
					}
					$scope.fileUpload = function (fileObject, refdocument, index) {
						if (fileObject) {
							// Max file size should be 5 MB
							if (fileObject.size > 5 * 1024 * 1024) {
								GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
								return;
							}
							refdocument.name = fileObject.name;
						//	refdocument.fileType = fileObject.type;
							refdocument.fileType = refdocument.name.split(".").pop();
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
					}, 
					$scope.deleteRows = function () {
						const tempRefDocRequest = [];
						var flag = false;
						for (let index = 0; index < $scope.refdocuments1.length; index++) {
							if ($scope.refdocuments1[index].select) {
								$scope.selectedFileMap[index] = null;
								flag = true;
							} else {
								tempRefDocRequest.push($scope.refdocuments1[index]);
							}
						}
						for (let index = 0; index < $scope.refdocuments1.length; index++) {
							if ($scope.refdocuments1[index].select <= 0) {
								flag = false;
							}
						}
						if (flag == true) {
							GenericAlertService.alertMessage("Document(s) deleted successfully", "Info");
						} else {
							GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");
						}
						$scope.refdocuments1 = tempRefDocRequest;
					}, 
					$scope.saveReferenceDocumentList = function () {
						console.log($scope.refdocuments1);
						console.log("saveReferenceDocument function");
						var files = [];
						for (let index = 0; index < $scope.selectedFileMap.length; index++) {
							const value = $scope.selectedFileMap[index];
							if (value) {
								$scope.refdocuments1[index].fileObjectIndex = files.length;
								files.push(value);
							}
						}
						for (const doc of $scope.refdocuments1) {
							if(doc.empDocumentId==null)
							{
								console.log(doc);
								if (doc.version == undefined || doc.version == null || doc.version == "" || doc.name == "") {
									GenericAlertService.alertMessage('Please enter version number (or) select file to upload', 'Warning');
									return;
								}
							}
						}
						angular.forEach($scope.refdocuments1, function (value, key) {
							delete value.selected;
						});
						//$scope.folderCategory = ( $scope.refDocumentMode == "Reference Documents" ) ? "PreContract List-Reference Documents" : "Stage2 Approval-Generate PO-Appendices";
						$scope.projId = $rootScope.selectedEmployeeData.projId;					
						var req = {
							"projDocFileTOs": $scope.refdocuments1,
							"projId": $scope.projId,
							"clientId": $rootScope.account.clientId,
							"folderCategory": $scope.documentCategory,
							"empProjId": $rootScope.selectedEmployeeData.projEmpRegisterTO.id,
							"empId" : $rootScope.selectedEmployeeData.id
						}
						console.log(req);
						console.log(files);						
						EmpRegisterService.saveEmployeeDocs(files,req).then(function (data) {
							var succMsg = GenericAlertService.alertMessageModal('Employee Documents uploaded successfully ', 'Info');
							succMsg.then(function (popData) {
								const returnPopObj = {
									"projDocFileTO": data.projDocFileTO
								};
								$scope.closeThisDialog();
								deferred.resolve(returnPopObj);
							});
						}, function (error) {
							GenericAlertService.alertMessage('Employee Documents failed to upload', 'Error');
						});
					},
					$scope.downloadEmpDocument = function(downloadRecord) {
						console.log(downloadRecord);
						let req={
							"recordId" : downloadRecord.id,
							"category" : $scope.documentCategory,
							"fileName" : downloadRecord.name
						}
						EmpRegisterService.downloadRegisterDocs(req);
					}
				}]

		});
		return deferred.promise;
	}
	return service;
}]);
