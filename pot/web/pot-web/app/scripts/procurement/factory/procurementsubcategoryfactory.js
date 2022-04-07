'use strict';
app
		.factory(
				'ProcurementSubCategoryFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProcureService", "GenericAlertService", "PreContractInternalService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProcureService,
						GenericAlertService, PreContractInternalService) {
					var service = {};
							service.getProcureCatgs = function(req) {
								console.log('procurment sub category *** ', req);
								var deferred = $q.defer();
								ProcureService
										.getProcureCatgs(req)
										.then(
												function(data) {
													var popupData = service
															.openPopup(data.procureMentCatgTOs);
													popupData.then(function(data) {
														deferred.resolve(data);
													});
												},
												function(error) {
													GenericAlertService
															.alertMessage(
																	'Error occurred while fetching procurement sub-catagories',
																	"Error");
												});
								return deferred.promise;
							},
							service.openPopup = function(procureMentCatgTOs) {
								var deferred = $q.defer();
								var popUp = ngDialog
										.open({
											template : '/views/procurement/pre-contracts/internalApproval/procurementsubcategorypopup.html',
											className : 'ngdialog-theme-plain ng-dialogueCustom4',
											controller : [
													'$scope',
													function($scope) {
														$scope.procurementSubCatagories = procureMentCatgTOs;
														$scope.selectProcureSubCategory = function(
																subcategory) {
															console.log('subcategory ==>  ', subcategory);
															var returnPopObj = {
																"selectedSubcategory" : subcategory
															};
															deferred.resolve(returnPopObj);
															$scope.closeThisDialog();
														}
													} ]
										});
								return deferred.promise;
							};

					return service;

				}]);
