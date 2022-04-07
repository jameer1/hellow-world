'use strict';
app
		.factory(
				'WorkDairyProjDocketPopup',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "WorkDiaryService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, WorkDiaryService,
						GenericAlertService) {
					var service = {};
							service.getMaterialProjDockets = function(req,workMaterialProjectDocketMap) {
								var deferred = $q.defer();
								var resultData = WorkDiaryService
										.getMaterialProjDocketsByDoctype(req);
								resultData
										.then(
												function(data) {
													var serviceData = service
															.openPopup(data.materialProjDocketTOs,workMaterialProjectDocketMap);
													serviceData.then(function(data) {
														deferred.resolve(data);
													});
												},
												function(error) {
													GenericAlertService
															.alertMessage(
																	"Error occured while selecting EPS Project name",
																	'Error');
												});
								return deferred.promise;
							},
							service.openPopup = function(materialProjDocketTOs,workMaterialProjectDocketMap) {
								var deferred = $q.defer();
								var popup = ngDialog
										.open({
											template : 'views/timemanagement/workdairy/createworkdairy/wprokdairyprojectocketspopup.html',
											className : 'ngdialog-theme-plain ng-dialogueCustom1',
											closeByDocument : false,
											showClose : false,
											controller : [ '$scope', function($scope) {
												$scope.workMaterialProjectDocketMap=workMaterialProjectDocketMap;
												$scope.materialProjDockets = materialProjDocketTOs;
												$scope.selectProjDocket = function(projDockObj) {
													deferred.resolve(projDockObj);
													$scope.closeThisDialog();
												}
											} ]
										});
								return deferred.promise;
							}
					return service;
				}]);
