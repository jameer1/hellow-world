'use strict';
app.factory('PlantPostApprovalReceiverFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProjectEmployeFactory", "EpsProjectSelectFactory", "PlantTranserDetailsFactory", "EmployeeMasterDetailsFactory", "EmpReqApprUserFactory", "PlantReceivedByEmpDetailsFactory", "EmpRegisterService", "PlantRegisterService", function(ngDialog, $q, blockUI,$filter, $timeout, $rootScope, GenericAlertService, ProjectEmployeFactory, EpsProjectSelectFactory, PlantTranserDetailsFactory,
		EmployeeMasterDetailsFactory, EmpReqApprUserFactory, PlantReceivedByEmpDetailsFactory,EmpRegisterService,PlantRegisterService) {
	var service = {};
	service.getPlantTransReceiverDetails = function(actionType, editNewRequest, plantReqTransMap) {
		var deferred = $q.defer();
		if (actionType == 'Edit') {
			var req = {
				"id" : editNewRequest.id,
				"status" : 1
			};
			var resultData = PlantRegisterService.getPlantTransferDetails(req);
			resultData.then(function(data) {
				var popupdata = service.openPopup(plantReqTransMap, data.plantTransferReqApprTOs, data.transferPlantMap);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting plant request details", 'Error');
			});
		} else {
			var transferPlantMap = [];
			var plantTransferReqApprTOs = [ {
				"status" : 1,
				"reqUserTO" : {},
				"apprUserTO" : {},
				"plantTransReqApprDtlTOs" : []
			} ];
			var popupdata = service.openPopup(plantReqTransMap, plantTransferReqApprTOs, transferPlantMap);
			popupdata.then(function(data) {
				deferred.resolve(data);
			});
		}
		return deferred.promise;

	}, service.openPopup = function(plantReqTransMap, plantTransferReqApprTOs, transferPlantMap) {
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template : 'views/projresources/projplantreg/requestForTransfer/postviewreceiverpopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose : false,
			controller : [
					'$scope',
					function($scope) {
						var selectedPlantNewRequest = [];
						$scope.plantTransReqData = plantTransferReqApprTOs[0];
						
						$scope.userProjMap = plantReqTransMap.userProjMap;
						$scope.plantClassMap = plantReqTransMap.plantClassMap;
						$scope.plantCompanyMap = plantReqTransMap.plantCompanyMap;
						$scope.userMap = plantReqTransMap.userMap;
						$scope.transferPlantMap = transferPlantMap;
						$scope.approverDate= $scope.plantTransReqData.apprDate;
					
						$scope.activeFlag = false;
						$scope.projId = null;
						$scope.employeeSearch = function(projId) {
							$scope.projId=projId;
						
							var empReq = {
								"status" : 1,
								"projId" :$scope.projId

							};
						
							var empDetailsPopup = PlantReceivedByEmpDetailsFactory.getPlantReceivedEmpDetails($scope.projId);
							empDetailsPopup.then(function(data) {
						
								$scope.plantTransReqData.plantTransReqApprDtlTOs[0].receivedBy = data.empCode;
								
							}, function(error) {
								GenericAlertService.alertMessage("Error occured while getting employees", 'Error');
							});
						}
					
						$scope.savePostTransferReceverDetails = function() {
							if ($scope.plantTransReqData.id > 0 ) {
								var oldFromDate = new Date($scope.approverDate);
								var fromDate = new Date($scope.plantTransReqData.actualDeliveryDate);
								var dateDiff = new Date(fromDate - oldFromDate);
								var days = dateDiff/1000/60/60/24;
							
								if (days < 0) {
									GenericAlertService.alertMessage("Actual Delivery date must be Future or equal date of ApproverDate", 'Warning');
									return;
								}
								}
							
							var req = {
								"plantTransferReqApprTOs" : [ $scope.plantTransReqData ],
								"status" : 1,
								"projId" : $scope.plantTransReqData.fromProjId
							}
							
							blockUI.start();
							PlantRegisterService.savePlantTransfers(req).then(function(data) {
								blockUI.stop();
								var succMsg = GenericAlertService.alertMessageModal('Plant Receiver Details are Saved Successfully  ',"Info");
								succMsg.then(function() {
									deferred.resolve(data);
								});
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage('Plant Receiver Details are Failed to Save ', "Error");
							});
						}
					} ]
		});
		return deferred.promise;
	}
	return service;
}]);
