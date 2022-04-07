'use strict';
app.factory('ProjMaterialClassPopupService',["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjMaterialClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI,ProjMaterialClassService, GenericAlertService) {
					var projMaterialClassifiPopUp;
					var service = {};
					service.projMaterialClassifiPopUp = function(actionType,selectedProject, editTableData) {
					var deferred = $q.defer();
						projMaterialClassifiPopUp = ngDialog .open({
									template : 'views/projectlib/materialclass/projmaterialpopup.html',
									scope : $rootScope,
									closeByDocument : false,
									showClose : false,
									controller : ['$scope',function($scope) {
												$scope.resourceData = [];
												$scope.catgData = [];
												$scope.addTableData = [];
												var selectedMaterial=[];
												var emptyProjMaterialClassifyObj =[];
												$scope.action = actionType;
												if (actionType === 'Edit') {
													$scope.addTableData = angular.copy(editTableData);
												}
															var onLoadReq = {
																"status" : 1,
																"projId" : selectedProject.projId
															};
															ProjMaterialClassService.projMaterialClassifyOnLoad(onLoadReq).then(function(data) {
																				$scope.resourceData = data.materialSubResp.materialSubGroupTOs;
																				$scope.updateResCode = function(data,tab) {
																					tab.groupId= data.id;
																				}
																				$scope.catgData = data.measureUnitResp.measureUnitTOs;
																				$scope.updateCatgCode = function(data,tab) {
																					tab.measureId = data.id;
																				}
																				if (actionType === 'Add') {
																					emptyProjMaterialClassifyObj = data.projMaterialClassTO;
																					var localprojMaterialClassTO = angular.copy(emptyProjMaterialClassifyObj);
																					$scope.addTableData.push(localprojMaterialClassTO);
																				}
																			});
															$scope.addMaterialClass = function() {
																var localprojMaterialClassTO = angular.copy(emptyProjMaterialClassifyObj);
																$scope.addTableData.push(localprojMaterialClassTO);
															},
														$scope.saveProjMaterialClasses = function() {
																var materialClassMap=[];
																angular.forEach($scope.addTableData,function(value,key) {
																					if(materialClassMap[value.code]!=null){
																						GenericAlertService.alertMessage('Duplicate Resource IDs  are not allowed',"Error");
																						forEach.break();
																						}else
																							{
																							materialClassMap[value.code]=true;
																							}
																				});
															var req = {
																"projMaterialClassTOs" : $scope.addTableData,
																"projId" : selectedProject.projId
															};
															blockUI.start();
															ProjMaterialClassService.saveProjMaterialClasses(req).then(function(data) {
																				blockUI.stop();
																				if (data.status != null&& data.status !== undefined&& data.status === 'Info') {
																					var projectClassTOs = data.projMaterialClassTOs;
																					var succMsg = GenericAlertService.alertMessageModal('Material Classification(s) is/are '+ data.message,data.status);
																					       succMsg.then(function(popData) {
																										ngDialog.close(projMaterialClassifiPopUp);
																										var returnPopObj = {
																											"projMaterialClassTOs" : projectClassTOs
																										};
																										deferred.resolve(returnPopObj);
																									},
																									function(error) {
																										blockUI.stop();
																										GenericAlertService.alertMessage("Error occurred while closing popup",'Info');
																									});
																				}
																			},
																			function(error) {
																				blockUI.stop();
																				GenericAlertService.alertMessage('Material Classification(s)  is/are failed to Save','Error');
																			});
														},$scope.popUpRowSelect = function(tab) {
															if (tab.select) {
																selectedMaterial.push(tab);
															} else {
																selectedMaterial.splice(selectedMaterial.indexOf(tab), 1);
															}
														},
														$scope.deleteRows = function() {
															if(selectedMaterial.length==0){
																GenericAlertService.alertMessage('Please select atleast one row to delete',"Warning");
															}
															else if(selectedMaterial.length<$scope.addTableData.length)
															{
															angular.forEach(selectedMaterial, function(value,key) {
																$scope.addTableData.splice($scope.addTableData.indexOf(value), 1);
															});
															selectedMaterial=[];
															GenericAlertService.alertMessage("Row(s) is/are deleted successfully","Info");
															}else if(selectedMaterial.length > 1){
																GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
															}
															else if(selectedMaterial.length == 1){
															$scope.addTableData[0] = {
																'materialSubGroupTO.code' : '',
																'materialSubGroupTO.name' : '',
																'code' : '',
																'name' : '',
																'measureUnitTO.name' : '',
																'status' : '1',
																'selected' : false
															};
															selectedMaterial = [];
															GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
														}
														}
																			
											} ]
								});
						return deferred.promise;
					};
					return service;

				}]);
