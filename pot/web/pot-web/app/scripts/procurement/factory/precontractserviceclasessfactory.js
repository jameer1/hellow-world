'use strict';

app
		.factory(
				'PreContractProjServiceClassFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "ClassificationService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
						PreContractInternalService, ClassificationService,
						GenericAlertService) {
					var serviceClassificationPopup = [];
					var serviceClassificationService = {};
					serviceClassificationService.getServiceClasses = function() {
						var deferred = $q.defer();
						serviceClassificationPopup = ngDialog
								.open({
									template : '/views/procurement/pre-contracts/internalApproval/precontractservicepopup.html',
									closeByDocument : false,
									className : 'ngdialog-theme-plain ng-dialogueCustom4',
									showClose : false,
									controller : [
											'$scope',
											function($scope) {
												$scope.serviceClassificationData=[];
														$scope.getServiceClasses = function() {
															var serviceClassReq = {
																"status" : 1
															};
															ClassificationService
																	.getServiceClasses(
																			serviceClassReq)
																	.then(
																			function(
																					data) {
																				$scope.serviceClassificationData = data.serviceClassTOs;
																			},
																			function(
																					error) {
																				GenericAlertService
																						.alertMessage(
																								"Error occured while getting Project Employee classes",
																								'Error');
																			});
														},
														$scope.serviceClassificationPopup = function(
																serviceTO) {

															var returnPopObj = {
																"serviceClassTO" : serviceTO
															};
															deferred
																	.resolve(returnPopObj);
															$scope
																	.closeThisDialog();
														}
											} ]
								});
						return deferred.promise;
					};
					return serviceClassificationService;

				}]);
