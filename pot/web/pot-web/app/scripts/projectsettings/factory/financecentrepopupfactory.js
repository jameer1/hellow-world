'use strict';
app
		.factory(
				'FinanceCentrePopUpFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProcureService", "GenericAlertService", "UnitPayRateService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProcureService,
						GenericAlertService, UnitPayRateService) {
					var service = {};
							service.getFinancecentre = function(req) {
							var deferred = $q.defer();
							console.log(req);
							UnitPayRateService.getFinanceCenters(req).then(function (data) {
													var popupData = service
															.openPopup(data.financeCenterRecordTos);
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
							service.openPopup = function(financecentreids) {
								var deferred = $q.defer();
								var popUp = ngDialog
										.open({
											template : 'views/projectsettings/financecentre.html',
											className : 'ngdialog-theme-plain ng-dialogueCustom4',
											controller : [
													'$scope',
													function($scope) {
														$scope.financeCentreIds = financecentreids;
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
