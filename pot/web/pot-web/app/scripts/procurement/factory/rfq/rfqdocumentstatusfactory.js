'use strict';

app
	.factory(
		'RFQDocumentStatusFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "EpsProjectSelectFactory", "PrecontractStage1ApprovedFactory", "RFQService", "RfqTenderDocumentFactory", "GenericAlertService", "RfqDistributionCmpFactory", "RfqDistributionDocumentFactory", "RfqTransmittalDocumentFactory", "TransmittalDetailsFactory", function (ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService,
			EpsProjectSelectFactory, PrecontractStage1ApprovedFactory, RFQService,
			RfqTenderDocumentFactory, GenericAlertService, RfqDistributionCmpFactory,
			RfqDistributionDocumentFactory, RfqTransmittalDocumentFactory, TransmittalDetailsFactory) {
			var service = {};
			service.openTenderDocumentStatusPopup = function () {
				var deferred = $q.defer();
				var popUpData = ngDialog
					.open({
						template: 'views/procurement/RFQ/documentstatuslist.html',
						className: 'ngdialog-theme-plain ng-dialogueCustom0',
						showClose: false,
						closeByDocument: false,
						controller: [
							'$scope',
							function ($scope) {
								$scope.searchProject = {};
								$scope.precontractObj = {};
								$scope.preContractDistributionDocTOs = [];
								var preContractCmpTOs = [];
								var preContractCmpMap = [];

								var selectedDistributionDocs = [];
								$scope.addTenderDocuments = function () {
									if ($scope.precontractObj.id == null
										|| $scope.precontractObj.id == undefined
										|| $scope.precontractObj.id <= 0) {
										GenericAlertService
											.alertMessage(
												"Please select Precontract ID to get the pre-contract documents",
												'Warning');

										return;
									}
									var documentDetailsPopup = RfqTenderDocumentFactory
										.getPreContractDocuments($scope.precontractObj);
									documentDetailsPopup
										.then(
											function (data) {
												angular
													.forEach(
														data.documentTOs,
														function (
															value,
															key) {
															$scope.preContractDistributionDocTOs
																.push(value)
														});

											},
											function (error) {
												GenericAlertService
													.alertMessage(
														"Error occurred while selecting pre-contract documents",
														'Error');
											})
								},
									$scope.getPreContractTransmittalMessage = function (
										documentTO, searchProject) {
										var popupDetails = RfqTransmittalDocumentFactory
											.getPreContractCmpDistributionDocs(
												preContractCmpMap,
												preContractCmpTOs,
												documentTO,
												$scope.precontractObj, $scope.searchProject);
										popupDetails
											.then(
												function (data) {
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occurred while selecting pre-contract documents",
															'Error');
												})
									},
									$scope.getPreContractCmpDistributionDocs = function (
										documentTO) {
										var popupDetails = RfqDistributionDocumentFactory
											.getPreContractCmpDistributionDocs(
												preContractCmpMap,
												preContractCmpTOs,
												documentTO,
												$scope.precontractObj);
										popupDetails
											.then(
												function (data) {
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occurred while selecting pre-contract documents",
															'Error');
												})
									},

									$scope.getPreContractDistributionDocs = function (
										precontractObj) {
										var req = {
											"status": 1,
											"contractId": precontractObj.id
										};
										var resultData = RFQService
											.getPreContractDistributionDocs(
												req)
											.then(
												function (data) {
													preContractCmpMap = data.preContractCompanyMap;
													preContractCmpTOs = data.preContractCmpTOs;
													$scope.preContractDistributionDocTOs = $filter('unique')(data.preContractDistributionDocTOs,'code');

												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occured while getting Pre-Contractt document details",
															'Error');
												});
									},
									$scope.savePreContractDistributionDocs = function () {
										if (selectedDistributionDocs.length <= 0) {
												GenericAlertService.alertMessage("Pelase select atleast one document to save",'Warning');
												return;
											}
										var req = {
											"status": 1,
											"contractId": $scope.precontractObj.id,
											"preContractDistributionDocTOs": $scope.preContractDistributionDocTOs
										};
										var resultData = RFQService
											.savePreContractDistributionDocs(
												req)
											.then(
												function (data) {
													$scope.preContractDistributionDocTOs = data.preContractDistributionDocTOs;
													GenericAlertService
														.alertMessage(
															"Pre-Contract Document Details saved successfully",
															'Info');
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occured while saving Pre-Contractt  Document Details",
															'Error');
												});
									},
									$scope.rowSelect = function (documentTO) {
										if (documentTO.select) {
											selectedDistributionDocs
												.push(documentTO);
										} else {
											selectedDistributionDocs
												.pop(documentTO);
										}
									},
									$scope.sendPreContractDocsToCompanies = function () {
										if (selectedDistributionDocs.length <= 0) {
											GenericAlertService
												.alertMessage(
													"Pelase select atleast one document to send to bidders",
													'Warning');
											return;
										}
										var resultData = RfqDistributionCmpFactory
											.getPreContarctCompanies(
												preContractCmpMap,
												preContractCmpTOs,
												$scope.precontractObj,
												selectedDistributionDocs);
										resultData
											.then(
												function (data) {
													$scope.preContractDistributionDocTOs = data.preContractDistributionDocTOs;
													selectedDistributionDocs = [];
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occured while saving Pre-Contract document Details",
															'Error');
												});
									},

									$scope.getUserProjects = function (
										precontract) {
										var userProjectSelection = EpsProjectSelectFactory
											.getEPSUserProjects();
										userProjectSelection
											.then(
												function (data) {
													$scope.searchProject = data.searchProject;
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occured while selecting EPS Project name",
															'Error');
												});
									},
									$scope.getPreContractDetails = function () {
										if ($scope.searchProject.projId == null
											|| $scope.searchProject.projId == undefined) {
											GenericAlertService
												.alertMessage(
													"Please select the Project",
													"Warning");
											return;
										}
										var getContractReq = {
											"projId": $scope.searchProject.projId,
											"approveStatus": 5,
											"status": 1
										};
										PrecontractStage1ApprovedFactory.getPreContractDetails(
											getContractReq)
											.then(
												function (data) {
													$scope.precontractObj = data.selectedRecord;
													$scope
														.getPreContractDistributionDocs($scope.precontractObj);
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occured while getting Pre-Contract Stage1 Approved Details",
															"Error");
												});

									},
									$scope.getTransmittalDetails = function (
										preContractId) {
										var popupDetails = TransmittalDetailsFactory
											.reqApprDetails(preContractId);
										popupDetails
											.then(
												function (data) {
												},
												function (error) {
													GenericAlertService
														.alertMessage(
															"Error occurred while getting request approvals",
															'Info');
												})
									}
							}]
					});
				return deferred.promise;
			};
			return service;
		}]);
