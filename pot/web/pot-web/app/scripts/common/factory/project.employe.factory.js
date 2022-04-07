'use strict';
app.factory('ProjectEmployeFactory',["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "EmpRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope,
						GenericAlertService, EmpRegisterService) {
					var plantEmployeePopUp;
					var service = {};
					service.plantEmployeePopUp = function(employeeDetails) {
						var deferred = $q.defer();
						plantEmployeePopUp = ngDialog
								.open({
									template : 'views/common/projectemployeelist.html',
									className : 'ngdialog-theme-plain ng-dialogueCustom1',
									controller : ['$scope',function($scope) {

												$scope.plantEmployeeDetails = employeeDetails;								
												$scope.plantEmployeePopUp = function(empRegisterDtlTOs) {
													var returnPlantEmployeeTO = {
														"plantEmployeeTO" : empRegisterDtlTOs
													};
													deferred.resolve(returnPlantEmployeeTO);
													$scope.closeThisDialog();

												}
											} ]

								});
						return deferred.promise;
					},
					
					service.getProjectEmployeeDetails = function(req){
						var deferred = $q.defer();
						var projectEmployeeDetailsPromise = EmpRegisterService.empRegisterOnLoad(req);
						projectEmployeeDetailsPromise.then(function(data) {
							var plantEmployeeDetails= [];
							plantEmployeeDetails = data.empRegisterDtlTOs;
							var plantEmployeePopUp  = service.plantEmployeePopUp(plantEmployeeDetails);
							plantEmployeePopUp.then(function(data) {
								var returnPopObj = {
										"plantEmployeeTO" : data.plantEmployeeTO
									};
									deferred.resolve(returnPopObj);
							},function(error) {
								GenericAlertService.alertMessage("Error occured while selecting Purchase Order Details",'Error');
							})
						},function(error) {
						  	GenericAlertService.alertMessage("Error occured while getting Purchase Order Details","Error");
						});
						return deferred.promise;
					}
					
					
					
					return service;
				}]);
