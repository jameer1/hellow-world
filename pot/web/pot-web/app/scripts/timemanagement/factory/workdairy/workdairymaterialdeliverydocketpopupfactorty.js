'use strict';
app
		.factory(
				'WorkDairyMaterialDeliveryDocketFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "WorkDairyProjDocketPopup", "WorkDiaryService", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, WorkDairyProjDocketPopup,
						WorkDiaryService, MaterialRegisterService, GenericAlertService) {
					var service = {};

							service.getMaterialDeliveryDocketDetails = function(req, existingDocketIds) {
								var deferred = $q.defer();
								var resultData = MaterialRegisterService
										.getMaterialDeliveryDocketDetails(req);
								resultData.then(function(data) {
									let dockets = angular.copy(data.labelKeyTOs);
									data.labelKeyTOs.map(docket => {
										 if (existingDocketIds.indexOf(parseInt(docket.displayNamesMap.deliveryDocketId)) > -1) 
										 	dockets.splice(data.labelKeyTOs.indexOf(docket), 1);
									});
									var popupData = service.openPopup(dockets)
									popupData.then(function(data) {
										deferred.resolve(data);
									});
								}, function(error) {
									GenericAlertService
											.alertMessage(
													"Error occured while getting material dockets",
													'Error');
								});
								return deferred.promise;

							},

							service.openPopup = function(labelKeyTOs) {
								var deferred = $q.defer();
								var materialFactoryPopup = ngDialog
										.open({
											template : 'views/timemanagement/workdairy/createworkdairy/workdairydeliverydocketspopup.html',
											className : 'ngdialog-theme-plain ng-dialogueCustom0',
											closeByDocument : false,
											showClose : false,
											controller : [
													'$scope',
													function($scope) {
														$scope.labelKeyTOs = labelKeyTOs;
														var selectedMaterials = [];
														$scope.workDairyMaterialDtlTOs = [];
														$scope.convertToNumber = function(number) {
															return parseInt(number);
														}
														$scope.selectMaterialDockcet = function(
																materialObj, doNotSelect) {
															if (!doNotSelect) {
																deferred.resolve(materialObj);
																$scope.closeThisDialog();
															}
														}

													} ]
										});
								return deferred.promise;
							}
					return service;
				}]);
