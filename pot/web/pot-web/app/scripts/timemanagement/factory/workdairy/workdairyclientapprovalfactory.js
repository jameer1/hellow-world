'use strict';
app
	.factory(
		'WorkDairyClientApprovalFactory',
		["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "WorkDiaryService", "GenericAlertService", function (ngDialog, $q, $filter, blockUI, $timeout, $rootScope, WorkDiaryService,
			GenericAlertService) {
			var dateWisePopUp;
			var approveservice = {};

			approveservice.getWorkDairyApproverDetails = function (workDairySearchReq,
				workDairyEmpDtlTOs, workDairyPlantDtlTOs, workDairyMaterialDtlTOs,
				workDairyProgressDtlTOs) {
				var deferred = $q.defer();
				dateWisePopUp = ngDialog
					.open({
						template: 'views/timemanagement/workdairy/workdairyclientapproval/workdairyclientapprovebutton.html',
						className: 'ngdialog-theme-plain ng-dialogueCustom5',
						closeByDocument: false,
						showClose: false,
						controller: [
							'$scope',
							function ($scope) {
								$scope.comments == null;
								var saveMaterialreq = [];
								$scope.approverTo = {
									"id": null,
									"code": null,
									"name": null

								}
								$scope.clientApproveWorkDairyDetails = function () {

									var saveReq = {
										"status": 1,
										"workDairyEmpDtlTOs": workDairyEmpDtlTOs,
										"workDairyPlantDtlTOs": workDairyPlantDtlTOs,
										"workDairyMaterialDtlTOs": saveMaterialreq,
										"workDairyProgressDtlTOs": workDairyProgressDtlTOs,

										"workDairyTO": {
											"status": 1,
											"id": workDairySearchReq.workDairyId,
											"code": workDairySearchReq.code,
											"projId": workDairySearchReq.projectLabelKeyTO.projId,
											"crewId": workDairySearchReq.crewLabelKeyTO.id,
											"weatherId": workDairySearchReq.weatherLabelKeyTO.id,
											"shiftId": workDairySearchReq.shiftLabelKeyTO.id,
											"workDairyDate": workDairySearchReq.workDairyDate,
											"internalApprUserId": workDairySearchReq.internalApprUserId,
											"clientApprUserId": workDairySearchReq.clientApprUserId,
											"clientApproval": workDairySearchReq.clientApproval,
											"apprStatus": 'Client Approved',
											"apprComments": $scope.comments,
											"empMaxHrs": workDairySearchReq.empMaxHrs,
											"plantMaxHrs": workDairySearchReq.plantMaxHrs,
											"contractNo": workDairySearchReq.contractNo,
											"contractType": workDairySearchReq.contractType
										}

									};
									blockUI.start();
									WorkDiaryService
										.approveWorkDairy(saveReq)
										.then(
											function (data) {
												blockUI.stop();
												var resultData = GenericAlertService
													.alertMessageModal(
														'Wordairy Details are Approved successfully',
														"Info");
												resultData
													.then(function () {
														$scope.closeThisDialog();
														deferred
															.resolve(data);
														blockUI.stop();
													});
											},
											function (error) {
												blockUI.stop();
												GenericAlertService
													.alertMessage(
														'workdairy Details are  failed to Approve',
														"Error");
											});
								},
									$scope.consustructMaterialSaveReq = function (workDairyMaterialDtlTOs, saveMaterialreq) {

										var maretailSchReq = {
											"purchaseSchLabelKeyTO": {
												id: null,
												code: null,
												name: null,
												displayNamesMap: {
													"purId": null,
													"purcode": null
												}
											},
											"materialPODeliveryDocketTOs": null

										};
										angular.forEach(workDairyMaterialDtlTOs, function (value, key) {
											if (value.id <= 0 && value.docketType == 'Delivery Docket') {
												var purchaseSchLabelKeyTO = {};
												purchaseSchLabelKeyTO = value.purchaseSchLabelKeyTO;
												purchaseSchLabelKeyTO.displayNamesMap.purId = value.purchaseLabelKeyTO.id;
												purchaseSchLabelKeyTO.displayNamesMap.purCode = value.purchaseLabelKeyTO.code;
												var maretailSchReq = {
													"purchaseSchLabelKeyTO": purchaseSchLabelKeyTO,
													"projId": workDairySearchReq.projectLabelKeyTO.projId,
													"issueQty": value.receivedQty,
													"materialPODeliveryDocketTOs": [angular.copy({
														"status": 1,
														"sourceType": value.sourceType,
														"id": value.deliveryDocketId,
														"deliveryDocketId": value.deliveryDocketId,
														"regMaterialId": value.scheduleItemId,
														"docketNumber": value.docketNum,
														"docketDate": value.docketDate,
														"receivedQty": value.receivedQty,
														"defectComments": value.defectComments,
														"comments": value.comments,
														"receivedBy": value.receivedBy
													})]
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
									}
								$scope.consustructMaterialSaveReq(workDairyMaterialDtlTOs, saveMaterialreq);

							}]
					});
				return deferred.promise;
			}
			return approveservice;
		}]);
