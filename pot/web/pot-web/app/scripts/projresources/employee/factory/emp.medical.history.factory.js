'use strict';
app.factory('EmpMedicalHistoryFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {
	var service = {};
	service.getEmpLatestServiceHistory = function(actionType, itemData) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id
		};
		EmpRegisterService.getEmpLatestServiceHistory(req).then(function(data) {
			var popupdata = service.openPopup(actionType, itemData, data.projEmpRegisterTOs);
			popupdata.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee medical history", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function(actionType, itemData, projEmpRegisterTOs) {
		var deferred = $q.defer();
		var medicalHistoryPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/medicalhistory/empmedicalrecordspopup.html',
			closeByDocument : false,
			showClose : false,
			className:'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.actionType = actionType;
				var selectedEmpMedicalRecords = [];
				$scope.empMedicalHistoryData = [];
				$scope.selectedFileMap = [];
				$scope.empDropDown = itemData.empDropDown;
				$scope.projEmpRegisterTO = projEmpRegisterTOs[0];
				if (actionType === 'Add') {
					$scope.empMedicalHistoryData.push({
						"selected" : false,
						"empRegId" : $scope.projEmpRegisterTO.empId,
						"empProjId" : $scope.projEmpRegisterTO.id,
						"recordId" : '',
						"item" : '',
						"particular" : '',
						"fileName" : '',
						"comments" : ''
					});
				} else {
					$scope.empMedicalHistoryData = angular.copy(itemData.editEmpMedicalHistory);
					itemData.editEmpMedicalHistory = [];
				}
				/*$scope.checkFile = function(file,medicalrecord){
					medicalrecord.documents = file;
				};*/
				/*var files = [];
				for (let index = 0; index < $scope.selectedFileMap.length; index++) {
					const value = $scope.selectedFileMap[index];
					if (value) {
						$scope.refdocumentslist[index].fileObjectIndex = files.length;
						files.push(value);
					}
				}*/
				$scope.addRows = function() {
					/*$scope.empMedicalHistoryData.push({
						"selected" : false,
						"empRegId" : $scope.projEmpRegisterTO.empId,
						"empProjId" : $scope.projEmpRegisterTO.id,
						"fromDte" : '',
						"recordId" : '',
						"item" : '',
						"particular" : '',
						"category" : '',
						"documents" : '',
						"comments" : '',
						"fileName" : ''
					});*/
					$scope.empMedicalHistoryData.push({
						"selected" : false,
						"empRegId" : $scope.projEmpRegisterTO.empId,
						"empProjId" : $scope.projEmpRegisterTO.id,
						"recordId" : '',
						"item" : '',
						"particular" : '',
						"fileObjectIndex" : '',
						"comments" : '',
						"fileName" : ''
					});
				}, $scope.medicalHistoryPopUpRowSelect = function(selectedMedicalTO) {
					if (selectedMedicalTO.selected) {
						selectedEmpMedicalRecords.push(selectedMedicalTO);
					} else {
						selectedEmpMedicalRecords.pop(selectedMedicalTO);
					}
				}, $scope.deleteRows = function() {
					if (selectedEmpMedicalRecords.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedEmpMedicalRecords.length < $scope.empMedicalHistoryData.length) {
						angular.forEach(selectedEmpMedicalRecords, function(value, key) {
							$scope.empMedicalHistoryData.splice($scope.empMedicalHistoryData.indexOf(value), 1);
						});
						selectedEmpMedicalRecords = [];
						GenericAlertService.alertMessage('Row(s) is/are deleted Successfully', "Info");
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.saveEmpMedicalHistory = function() {
					var req = {
						"status" : 1,
						"empId" : $rootScope.selectedEmployeeData.id,
						"code" : $rootScope.selectedEmployeeData.code,
						"empMedicalHistoryTOs" : $scope.empMedicalHistoryData,
						"folderCategory" : "Medical History",
						"userId" : $rootScope.account.userId
					}
					//console.log("saveEmpMedicalHistory function");
					var files = [];
					for (let index = 0; index < $scope.selectedFileMap.length; index++) {
						const value = $scope.selectedFileMap[index];
						if (value) {
							$scope.empMedicalHistoryData[index].fileObjectIndex = files.length;
							files.push(value);
						}
					}
					console.log(req);
					console.log(files);
					EmpRegisterService.saveEmpMedicalHistory(files,req).then(function(data) {
						// var succMsg = GenericAlertService.alertMessageModal('Employee Medical History Details is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Employee Medical History Details saved successfully',"Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
						
					}, function(error) {
						GenericAlertService.alertMessage('Employee Medical History Details are Failed to Save ', "Error");
					});
				},
				$scope.fileUpload = function (fileObject, medicalRecord, index) {
					/*if (fileObject) {
						// Max file size should be 5 MB
						if (fileObject.size > 5 * 1024 * 1024) {
							GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
							return;
						}
						refdocument.name = fileObject.name;
						refdocument.fileType = fileObject.type;
						refdocument.fileSize = formatBytes(fileObject.size);
					} else {
						refdocument.name = null;
						refdocument.fileType = null;
						refdocument.fileSize = null;
					}*/
					if (fileObject) {
						// Max file size should be 5 MB
						if (fileObject.size > 5 * 1024 * 1024) {							
							GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
							return;
						}
						medicalRecord.fileName = fileObject.name;
					}
					$scope.selectedFileMap[index] = fileObject;
				}
				function formatBytes(bytes) {
					if(bytes < 1024) return bytes + " Bytes";
					else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
					else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
					else return(bytes / 1073741824).toFixed(3) + " GB";
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
