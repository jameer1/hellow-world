'use strict';
app.factory('PlantTransfersCreateFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PlantAssestDetailsFactory", "ProjectEmployeFactory", "GenericAlertService", "ProjectCrewPopupService", "PlantTransferService", "UserEPSProjectService", function(ngDialog, $q, $filter, $timeout, $rootScope, PlantAssestDetailsFactory, ProjectEmployeFactory, GenericAlertService, ProjectCrewPopupService, PlantTransferService, UserEPSProjectService) {
	var getPlantTransferDetailsPopup;
	var service = {};
	service.getPlantTransferDetailsPopup = function(statusType, selectedPlantReq) {
		var deferred = $q.defer();
		getPlantTransferDetailsPopup = ngDialog.open({
			template : 'views/projresources/planttransfer/plantregtransferpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.dateOfReq = new Date();
				$scope.statusType = statusType;
				$scope.addPlantTransfers = [];
				var selectedTranasferRecords = [];
				var editPlantTransfer = [];
				$scope.searchProject = {};
				$scope.getPlantFromProjects = function(projectLabelKeyTO) {
					var userProjectSelection = UserEPSProjectService.epsProjectPopup(projectLabelKeyTO);
					userProjectSelection.then(function(userEPSProjData) {
						projectLabelKeyTO.id = userEPSProjData.selectedProject.projId;
						projectLabelKeyTO.code = userEPSProjData.selectedProject.projName;
						projectLabelKeyTO.name = userEPSProjData.selectedProject.parentName;
					},function(error) {
										GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
									});
				}, $scope.getApproveDetails = function(empRegisterDtlTO) {
					var getPlantEmpReq = {
						"status" : 1,
						"projId" : $scope.projId
					};
					ProjectEmployeFactory.getProjectEmployeeDetails(getPlantEmpReq).then(function(data) {
						empRegisterDtlTO.id = data.plantEmployeeTO.id;
						empRegisterDtlTO.code = data.plantEmployeeTO.code;
						empRegisterDtlTO.name = data.plantEmployeeTO.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
					});
				}, $scope.getPlantAsssestDetails = function(plantRegisterDtlTO) {
					var getPlantEmpReq = {
						"status" : 1,
						"projId" : $scope.projId
					};
					PlantAssestDetailsFactory.getPlantAssetDetails(getPlantEmpReq).then(function(data) {
						plantRegisterDtlTO.id = data.plantAssetTO.id;
						plantRegisterDtlTO.assertId = data.plantAssetTO.assertId;
						plantRegisterDtlTO.regNumber = data.plantAssetTO.regNumber;
						plantRegisterDtlTO.desc = data.plantAssetTO.desc;
						plantRegisterDtlTO.manfacture = data.plantAssetTO.manfacture;
						plantRegisterDtlTO.model = data.plantAssetTO.model;
						plantRegisterDtlTO.transferFromProject = data.plantAssetTO.transferFromProject;
						plantRegisterDtlTO.transferToProject = data.plantAssetTO.transferToProject;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Details", "Error");
					});
				}, $scope.plantTransferOnLoad = function() {
					var onLoadReq = {
						"status" : 1
					}
					PlantTransferService.plantReqTransSearch(onLoadReq).then(function(data) {
						$scope.plantRequestDetails = data.plantReqForTransTOs;
						$scope.userMap = data.plantReqForTransUserMap;
						$scope.userProjectsMap = data.userProjMap;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Transfer details", 'Error');
					})
				}, $scope.plantTransferOnLoad();

				$scope.getApproversList = function(reqUserLabelKeyTO) {
					var ApproverSerivcePopup = [];
					ApproverSerivcePopup = ProjectCrewPopupService.approverDetailsList($scope.searchProject.projId);
					ApproverSerivcePopup.then(function(data) {
						reqUserLabelKeyTO.id = data.projApproverTO.userId;
						reqUserLabelKeyTO.code = data.projApproverTO.firstName;
						reqUserLabelKeyTO.name = data.projApproverTO.lastName;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Approver List Details", 'Error');
					});
				}
				if (statusType === 'Create') {
					$scope.addPlantTransfers.push({
						"status" : 1,
						"selected" : false,
						"apprStatus" : 1,
						"dateOfRequest" : '',
						"reqNotification" : '',
						"plantRegisterDtlTO" : {
							"id":'',
							"assertId" : '',
							"regNumber" : '',
							"desc" : '',
							"manfacture" : '',
							"model" : ''
						},
						"transferFromProject" : '',
						"transferToProject" : '',
						"projectLabelKeyTO":{
							"id":'',
							"code":'',
							"name":''
						},
						"deMobilisationDate" : '',
						"transDate" : '',
						"empRegisterDtlTO" : {
							id : null,
							code : null,
							name : null
						},
						"assestTransfer" : '',
						"actualDeliveredDate" : '',
						"receivedBy" : '',
						"notesFromReceiver" : '',
						"transferNoteRecords" : ''
					})
				} else {
					$scope.addPlantTransfers = angular.copy(selectedPlantReq);
					selectedPlantReq = [];
				}
				$scope.addRows = function() {
					$scope.addPlantTransfers.push({
						"status" : 1,
						"selected" : false,
						"apprStatus" : 1,
						"dateOfRequest" : '',
						"reqNotification" : '',
						"plantRegisterDtlTO" : {
							"id":'',
							"assertId" : '',
							"regNumber" : '',
							"desc" : '',
							"manfacture" : '',
							"model" : ''
						},
						"transferFromProject" : '',
						"transferToProject" : '',
						"projectLabelKeyTO":{
							"id":'',
							"code":'',
							"name":''
						},
						"deMobilisationDate" : '',
						"transDate" : '',
						"empRegisterDtlTO" : {
							id : null,
							code : null,
							name : null
						},
						"assestTransfer" : '',
						"actualDeliveredDate" : '',
						"receivedBy" : '',
						"notesFromReceiver" : '',
						"transferNoteRecords" : ''
					});
				}
				$scope.plantTranferPopUpRowSelect = function(transfer) {
					if (transfer.select) {
						selectedTranasferRecords.push(transfer);
					} else {
						selectedTranasferRecords.pop(transfer);
					}
				}, $scope.deleteRows = function() {
					if (selectedTranasferRecords.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedTranasferRecords.length < $scope.addPlantTransfers.length) {
						angular.forEach(selectedTranasferRecords, function(value, key) {
							$scope.addPlantTransfers.splice($scope.addPlantTransfers.indexOf(value), 1);
						});
						selectedTranasferRecords = [];
						GenericAlertService.alertMessage('Row(s) is/are deleted Successfully', "Info");
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				$scope.savePlantTransfersferDetails = function() {
					var savePlantTransReq = {
						"plantReqForTransTOs" : $scope.addPlantTransfers
					};
					PlantTransferService.savePlantreqForTrans(savePlantTransReq).then(function(data) {
						GenericAlertService.alertMessage('Plant  Approval Transfer Detail(s) is/are ' + data.message, data.status);
					}, function(error) {
						GenericAlertService.alertMessage('Plant  Approval Transfer Detail(s) is/are Failed to Save', "Error");
					});
					ngDialog.close();
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
