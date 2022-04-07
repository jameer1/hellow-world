'use strict';
app.factory('CreateBaseLineFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjectScheduleService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, ProjectScheduleService, GenericAlertService) {
	var popup;
	var service = {};
	service.createBaseLineDetails = function(scheduleItemType,scheduleType, timeScale, projId,dataObj) {
		var deferred = $q.defer();
		popup = ngDialog.open({

			template : 'views/projectschedules/baselinepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.tabs = [ {
					title : 'View',
					url : 'views/projectschedules/viewbaselinepopup.html',
					SeleniumLocator:'Projects_ProjectSettings_CreateBaseLineTab_ViewTab'
				}, {
					title : 'Create',
					url : 'views/projectschedules/createbaselinepopup.html',
					SeleniumLocator:'Projects_ProjectSettings_CreateBaseLineTab_CreateTab'
				} ]
				
				var editProjScheduleBaseLines=[]
				$scope.rowSelect = function(baseLine) {
					if (baseLine.selected) {
						editProjScheduleBaseLines.push(baseLine);
					} else {
						editProjScheduleBaseLines.splice(editProjScheduleBaseLines.indexOf(baseLine), 1);
					}
				}				
				
				
				$scope.currentTab = $scope.tabs[0].url;
				$scope.onClickTab = function(tabs) {
					if ($scope.tabs[0].url === tabs.url) {
					}
					if ($scope.tabs[1].url === tabs.url) {
						// $scope.addRows();
					}
					$scope.currentTab = tabs.url;
				}, $scope.isActiveTab = function(tabsUrl) {
					return tabsUrl == $scope.currentTab;
				}
				$scope.projScheduleBaseLines = [];
				var selectedBaseLines = [];
				$scope.projScheduleBaseLineTOs = [];
				$scope.scheduleItemType=scheduleItemType;
				var scheduleItemDetails=[];
				var nextDate =null;
								
				var baseLineObj = {
					'select' : false,
					'id' : null,
					'code' : null,
					'name' : null,
					'date' : $filter('date')((new Date()), "dd-MMM-yyyy"),
					'status' : 1,
					"projId" : projId,
					"timeScale" : timeScale.type,
					"scheduleType" : scheduleType,
					"scheduleItemType" : scheduleItemType 
				};
			
				$scope.deleteRows = function() {
					if (selectedBaseLines.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedBaseLines.length < $scope.projScheduleBaseLineTOs.length) {
						angular.forEach(selectedBaseLines, function(value, key) {
							$scope.projScheduleBaseLineTOs.splice($scope.projScheduleBaseLineTOs.indexOf(value), 1);
						});
						selectedBaseLine = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.popupRowSelect = function(baseLine) {
					if (baseLine.select) {
						selectedBaseLines.push(baseLine);

					} else {
						selectedBaseLines.pop(baseLine);
					}
				}, $scope.getProjScheduleBaseLines = function() {
					var req = {
						"status" : 1,
						"projId" : projId,
						"scheduleItemType" : scheduleItemType
					};
					ProjectScheduleService.getProjScheduleBaseLines(req).then(function(data) {
						$scope.projScheduleBaseLines = data.projScheduleBaseLineTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting baselines", "Error");
					});
				}, $scope.saveProjScheduleManPowerDetails = function() {
					if($scope.projScheduleBaseLines.length>=5){
						GenericAlertService.alertMessage('Manpower Baselines cannot be created more than 5 Records', 'Warning');
						return;
					}
					var req = {
						"projScheduleBaseLineTO" : $scope.projScheduleBaseLineTOs[0],
						"projScheduleManPowerTOs": dataObj.scheduleItemDetails,
						"status" : 1,
						"projId" : projId,
						"scheduleItemType" : scheduleItemType
					};
					ProjectScheduleService.saveProjScheduleManPowerDetails(req).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal("Manpower Baseline created successfully", 'Info');
						succMsg.then(function() {
							deferred.resolve(data);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Man Power baseline details  are failed to Save', 'Error');
					});
				 },
				 $scope.saveProjSchedulePlantDetails = function() {
						if($scope.projScheduleBaseLines.length>=5){
							GenericAlertService.alertMessage('Plant Baselines cannot be created more than 5 Records', 'Warning');
							return;
						}
						var req = {
							"projScheduleBaseLineTO" : $scope.projScheduleBaseLineTOs[0],
							"projSchedulePlantTOs": dataObj.scheduleItemDetails,
							"status" : 1,
							"projId" : projId,
							"scheduleItemType" : scheduleItemType
						};
						ProjectScheduleService.saveProjSchedulePlantDetails(req).then(function(data) {
							var succMsg = GenericAlertService.alertMessageModal("Plant Baseline created successfully", 'Info');
							succMsg.then(function() {
								deferred.resolve(data);
							});
						}, function(error) {
							GenericAlertService.alertMessage('Plant baseline details  are failed to Save', 'Error');
						});
					 },
					 $scope.saveProjScheduleMaterialDetails = function() {
							if($scope.projScheduleBaseLines.length>=5){
								GenericAlertService.alertMessage('Material Baselines cannot be created more than 5 Records', 'Warning');
								return;
							}
							var req = {
								"projScheduleBaseLineTO" : $scope.projScheduleBaseLineTOs[0],
								"projScheduleMaterialTOs": dataObj.scheduleItemDetails,
								"status" : 1,
								"projId" : projId,
								"scheduleItemType" : scheduleItemType
							};
							ProjectScheduleService.saveProjScheduleMaterialDetails(req).then(function(data) {
								var succMsg = GenericAlertService.alertMessageModal("Material Baseline created successfully", 'Info');
								succMsg.then(function() {
									$scope.closeThisDialog();
									deferred.resolve(data);
								});
							}, function(error) {
								GenericAlertService.alertMessage('Material baseline details  are failed to Save', 'Error');
							});
						 },
						 $scope.saveProjScheduleCostCodeDetails = function() {
								if($scope.projScheduleBaseLines.length>=5){
									GenericAlertService.alertMessage('Cost Code Baselines cannot be created more than 5 Records', 'Warning');
									return;
								}
								var req = {
									"projScheduleBaseLineTO" : $scope.projScheduleBaseLineTOs[0],
									"projScheduleCostCodeTOs": dataObj.scheduleItemDetails,
									"status" : 1,
									"projId" : projId,
									"scheduleItemType" : scheduleItemType
								};
								ProjectScheduleService.saveProjScheduleCostCodeDetails(req).then(function(data) {
									var succMsg = GenericAlertService.alertMessageModal("Cost Code Baseline created successfully", 'Info');
									succMsg.then(function() {
										deferred.resolve(data);
									});
								}, function(error) {
									GenericAlertService.alertMessage('Cost Code  baseline details  are failed to Save', 'Error');
								});
							 },
							 
								$scope.addRows=function(){
									$scope.projScheduleBaseLineTOs.push({
										'select' : false,
										'id' : null,
										'code' : null,
										'name' : null,
										'date' : $filter('date')((new Date()), "dd-MMM-yyyy"),
										'status' : 1,
										"projId" : projId,
										"timeScale" : timeScale.type,
										"scheduleType" : scheduleType,
										"scheduleItemType" : scheduleItemType 
									});
							 }
							 $scope.deleteRows = function() {
									if (selectedBaseLines.length == 0) {
										GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
									} else if (selectedBaseLines.length < $scope.projScheduleBaseLineTOs.length) {
										angular.forEach(selectedBaseLines, function(value, key) {
											$scope.projScheduleBaseLineTOs.splice($scope.projScheduleBaseLineTOs.indexOf(value), 1);
										});
										selectedBaseLines = [];
										GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
									} else if (selectedBaseLines.length = 1) {
										GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
										selectedBaseLines = [];
									}
								}
							 
							 $scope.deleteProjScheduleBaseLines = function() {
									if (editProjScheduleBaseLines.length <= 0) {
										GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
										return;
									}
									var deleteIds = [];
									$scope.nondeleteIds = [];
									if ($scope.selectedAll) {
										$scope.projScheduleBaseLines = [];
									} else {
										angular.forEach(editProjScheduleBaseLines, function(value, key) {
											deleteIds.push(value.id);
										});
										var req = {
											"baseLineIds" : deleteIds,
											"status" : 2
										};
										GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
											ProjectScheduleService.deleteProjScheduleBaseLines(req).then(function(data) {});
											GenericAlertService.alertMessage('Baseline(s) Deactivated successfully', 'Info');
											$scope.closeThisDialog();
											angular.forEach(editProjScheduleBaseLines, function(value, key) {
												$scope.projScheduleBaseLines.splice($scope.projScheduleBaseLines.indexOf(value), 1);
												editProjScheduleBaseLines = [];
												$scope.deleteIds = [];
												
											},
												function(error) {
													GenericAlertService.alertMessage('Base Line(s) is/are  failed to Deactivate', "Error");
												});
										}, function(data) {
											angular.forEach(editProjScheduleBaseLines, function(value) {
												value.selected = false;
											})
										})

									}
								}
							 $scope.saveProjScheduleSOWDetails = function() {
									var req = {
										"projScheduleBaseLineTO" : $scope.projScheduleBaseLineTOs[0],
										"projScheduleSOWTOs": dataObj.scheduleItemDetails,
										"status" : 1,
										"projId" : projId,
										"scheduleItemType" : scheduleItemType
									};
									ProjectScheduleService.saveProjScheduleSOWDetails(req).then(function(data) {
										var succMsg = GenericAlertService.alertMessageModal("Progress Baseline created successfully", 'Info');
										succMsg.then(function() {
											deferred.resolve(data);
										});
									}, function(error) {
										GenericAlertService.alertMessage('Progress baseline details  are failed to Save', 'Error');
									});
								 }
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);