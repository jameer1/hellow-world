'use strict';

app
		.factory(
				'RfqDistributionCmpFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "RFQService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, RFQService,
						GenericAlertService) {
					var service = {};
					service.getPreContarctCompanies = function(preContractCmpMap,
							preContractCmpTOs, precontractObj, selectedDistributionDocs) {
						var deferred = $q.defer();
						var popupdata = ngDialog
								.open({
									template : 'views/procurement/RFQ/rfqdistributioncmppopup.html',
									showClose : false,
									closeByDocument : false,
									controller : [
											'$scope',
											function($scope) {
												$scope.preContractCmpMap = preContractCmpMap;
												$scope.precontractCmpTOs = preContractCmpTOs;
												var selectedPreContractCmpTOs = [];
														$scope.rowSelect = function(companyTO) {
															if (companyTO.select) {
																selectedPreContractCmpTOs.push(companyTO);
															} else {
																selectedPreContractCmpTOs.push(companyTO);
															}
														},

														$scope.sendPreContractDocsToCompanies = function() {
															var preContractCmpDistributionDocTOs = [];
															angular
																	.forEach(
																			selectedPreContractCmpTOs,
																			function(value, key) {
																				preContractCmpDistributionDocTOs
																						.push({
																							"preContractCmpId" : value.id,
																							"transmit" : value.transmit
																						});
																			});
															var req = {
																"status" : 1,
																"contractId" : precontractObj.id,
																"preContractCmpDistributionDocTOs" : preContractCmpDistributionDocTOs,
																"preContractDistributionDocTOs" : selectedDistributionDocs
															};
															if (preContractCmpDistributionDocTOs.length <= 0) {
																GenericAlertService
																		.alertMessage(
																				"Pelase select atleast one company to send documents",
																				'Warning');
																return;
															}
															var resultData = RFQService
																	.sendPreContractDocsToCompanies(
																			req)
																	.then(
																			function(data) {
																				selectedDistributionDocs=[];
																				var returnObj = {
																					"preContractDistributionDocTOs" : data.preContractDistributionDocTOs
																				};
																				GenericAlertService
																						.alertMessage(
																								"Pre-Contract  document are sent to selected bidders successfully",
																								'Info');
																				deferred
																						.resolve(returnObj);
																				$scope
																						.closeThisDialog();
																			},
																			function(error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while sending  distribution documents",
																								'Error');
																			});
														}
											} ]
								});
						return deferred.promise;
					};
					return service;
				}]);
