'use strict';

app
		.factory(
				'PrecontractExternalApprRequestFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractExternalService", "PreContractApproverFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, PreContractExternalService,
						PreContractApproverFactory, GenericAlertService) {
					var service = {};
					service.submitOrApproveInternalRequest = function(preContractObj, apprvStatus,
							projId, approverUserMap) {
						var deferred = $q.defer();
						var popupData = ngDialog
								.open({
									template : 'views/procurement/pre-contracts/externalApproval/precontractexternalrequestapprpopup.html',
									className : 'ngdialog-theme-plain ng-dialogueCustom6',
									showClose : false,
									closeByDocument : false,
									controller : [
											'$scope',
											function($scope) {
												$scope.preContractObj = preContractObj;
												$scope.approverUserMap = approverUserMap;
														$scope.saveExternalRequest = function(projId) {
															var validateFalg = true;
															if (apprvStatus == 2) {
																validateFalg = $scope
																		.validateApproverId();
															}
															if (validateFalg) {
																var savereq = {
																	"preContractTO" : preContractObj,
																	"apprvStatus" : apprvStatus,
																	"projId" : projId
																};
																PreContractExternalService
																		.saveExternalPreContracts(
																				savereq)
																		.then(
																				function(data) {
																					var succMsg = null;
																					if (apprvStatus == 2) {
																						succMsg = GenericAlertService
																								.alertMessageModal(
																										"Precontract submitted for approval successfully ",
																										"Info");
																					} else if (apprvStatus == 3) {
																						succMsg = GenericAlertService
																								.alertMessageModal(
																										"Precontract send back to requestor successfully ",
																										"Info");

																					} else if (apprvStatus == 4) {
																						succMsg = GenericAlertService
																								.alertMessageModal(
																										"Precontract put on-hold successfully ",
																										"Info");
																					} else if (apprvStatus == 5) {
																						succMsg = GenericAlertService
																								.alertMessageModal(
																										"Precontract got approved successfully ",
																										"Info");
																					} else if (apprvStatus == 6) {
																						succMsg = GenericAlertService
																								.alertMessageModal(
																										"Precontract got rejected successfully ",
																										"Info");
																					}
																					succMsg
																							.then(function(
																									popData) {
																								preContractObj = data.preContractTOs[0];
																								deferred
																										.resolve(preContractObj);
																							});

																				},

																				function(error) {
																					GenericAlertService
																							.alertMessage(
																									"Error occured,Please try again",
																									"Error");
																				});

															}
														},
														$scope.validateApproverId = function() {
															if (preContractObj.preContractReqApprTO.apprUserLabelkeyTO != null
																	&& preContractObj.preContractReqApprTO.apprUserLabelkeyTO.id > 0) {
																return true;
															} else {
																GenericAlertService
																		.alertMessage(
																				"Please select approver  user",
																				"Warning");
																return false;
															}
														},
														
														$scope.getUsersByModulePermission = function() {
															var approverFactoryPopup = PreContractApproverFactory
																	.getUsersByModulePermission(projId);
															approverFactoryPopup
																	.then(
																			function(data) {
																				preContractObj.preContractReqApprTO.apprUserLabelkeyTO = data.approverUserTO;
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while gettting approver users",
																								'Error');
																			});
														}

											} ]

								});
						return deferred.promise;
					}
					return service;
				}]);