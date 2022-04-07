'use strict';
app.factory('WorkDairySubmitFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "ApproveUserFactory", "WorkDiaryService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, ApproveUserFactory, WorkDiaryService, GenericAlertService) {
	var submitservice = {};
	submitservice.getWorkDairySubmitDetails = function(workDairySearchReq, workDairyEmpDtlTOs, workDairyPlantDtlTOs, workDairyMaterialDtlTOs, workDairyProgressDtlTOs) {
		var deferred = $q.defer();
		var dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairysubmit.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.comments == null;
				$scope.clientApproval == null;
				var saveMaterialreq = [];
				$scope.displayClientApproverList = false;

				$scope.approverTo = {
					"id" : null,
					"code" : null,
					"name" : null

				}
				$scope.clientApproveUserLabelKeyTO = {
					"id" : null,
					"code" : null,
					"name" : null
				};
				$scope.getModuleUserDetails = function(approverTo,clientApproveUserLabelKeyTO,approvalType) {
					var getReq = {
						"moduleCode" : "CREATWRKDIRY",
						"actionCode" : "APPROVE",
						"projId" : workDairySearchReq.projectLabelKeyTO.projId,
						"status" : 1,
						"permission" : "ASBUILTRCRD_WRKDIRY_CREATWRKDIRY_APPROVE"

					};
					ApproveUserFactory.getProjUsersOnly(getReq).then(function(data) {
						if( approvalType == 'clientApproval' )
						{
							$scope.clientApproveUserLabelKeyTO.id = data.approverTo.id;
							$scope.clientApproveUserLabelKeyTO.name = data.approverTo.name;
						}
						else
						{
							$scope.approverTo.id = data.approverTo.id;
							$scope.approverTo.name = data.approverTo.name;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting ApproverList ", 'Error');
					});
				}, $scope.submitWorkDairy = function() {
					var req = {
						"workDairyEmpDtlTOs" : workDairyEmpDtlTOs,
						"workDairyPlantDtlTOs" : workDairyPlantDtlTOs,
						"workDairyMaterialDtlTOs" : saveMaterialreq,
						"workDairyProgressDtlTOs" : workDairyProgressDtlTOs,
						"status" : 1,
						"workDairyTO" : {
							"status" : 1,
							"id" : workDairySearchReq.workDairyId,
							"code" : workDairySearchReq.code,
							"projId" : workDairySearchReq.projectLabelKeyTO.projId,
							"crewId" : workDairySearchReq.crewLabelKeyTO.id,
							"weatherId" : workDairySearchReq.weatherLabelKeyTO.id,
							"shiftId" : workDairySearchReq.shiftLabelKeyTO.id,
							"workDairyDate" : workDairySearchReq.workDairyDate,
							"internalApprUserId" : $scope.approverTo.id,
							"clientApprUserId" : $scope.clientApproveUserLabelKeyTO.id,
							"clientApproval" : $scope.clientApproval,
							"apprStatus" : 'SubmittedForApproval',
							"contractType":workDairySearchReq.contractType,
							"contractNo":workDairySearchReq.contractNo,
							"reqComments" : $scope.comments,
							"empMaxHrs" : workDairySearchReq.empMaxHrs,
							"plantMaxHrs" : workDairySearchReq.plantMaxHrs,
							"newRequired" : true,
							"notificationMsg" : 'Request for Internal Approval',
							"notificationStatus" : 'Pending',
						}
					};
					blockUI.start();
					WorkDiaryService.submitWorkDairy(req).then(function(data) {
						blockUI.stop();
						if (data.status == "Warning") {
							GenericAlertService.alertMessage("WorkDairy hours cannot be booked more than max hours ", "Warning");
						} else {
							var resultData = GenericAlertService.alertMessageModal('Work Diary Submitted successfully', "Info");
							resultData.then(function() {
								$scope.closeThisDialog();
								deferred.resolve(data);
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Error occurred while submitting Workdairy', "Error");
					});
				}, $scope.consustructMaterialSaveReq = function(workDairyMaterialDtlTOs, saveMaterialreq) {

					var maretailSchReq = {
						"purchaseSchLabelKeyTO" : {
							id : null,
							code : null,
							name : null,
							displayNamesMap : {
								"purId" : null,
								"purcode" : null
							}
						},
						"materialPODeliveryDocketTOs" : null

					};
					angular.forEach(workDairyMaterialDtlTOs, function(value, key) {
						if (value.id <= 0 && value.docketType == 'Delivery Docket') {
							var purchaseSchLabelKeyTO = {};
							purchaseSchLabelKeyTO = value.purchaseSchLabelKeyTO;
							purchaseSchLabelKeyTO.displayNamesMap.purId = value.purchaseLabelKeyTO.id;
							purchaseSchLabelKeyTO.displayNamesMap.purCode = value.purchaseLabelKeyTO.code;
							var maretailSchReq = {
								"purchaseSchLabelKeyTO" : purchaseSchLabelKeyTO,
								"projId" : workDairySearchReq.projectLabelKeyTO.projId,
								"issueQty" : value.receivedQty,
								"materialPODeliveryDocketTOs" : [ angular.copy({
									"status" : 1,
									"sourceType" : value.sourceType,
									"id" : value.deliveryDocketId,
									"deliveryDocketId" : value.deliveryDocketId,
									"regMaterialId" : value.scheduleItemId,
									"docketNumber" : value.docketNum,
									"docketDate" : value.docketDate,
									"receivedQty" : value.receivedQty,
									"defectComments" : value.defectComments,
									"comments" : value.comments,
									"receivedBy" : value.receivedBy
								}) ]
							};
						}
						let materialObj = value;
						materialObj.status = 1;
						materialObj.workDairyId = workDairySearchReq.workDairyId;
						materialObj.apprStatus = null;
						materialObj.apprComments = value.comments;
						materialObj.materialProjDtlTO = maretailSchReq;
						saveMaterialreq.push(materialObj);
					});
				},
				$scope.changeCLientApproval = function() {
					if( $scope.clientApproval )
					{
						$scope.displayClientApproverList = true;
					}
					else
					{
						$scope.displayClientApproverList = false;
						$scope.clientApproveUserLabelKeyTO.id = null; 
						$scope.clientApproveUserLabelKeyTO.code = null;
						$scope.clientApproveUserLabelKeyTO.name = null;
					}
				}
				$scope.consustructMaterialSaveReq(workDairyMaterialDtlTOs, saveMaterialreq);
			} ]
		});
		return deferred.promise;
	}
	return submitservice;
}]);
