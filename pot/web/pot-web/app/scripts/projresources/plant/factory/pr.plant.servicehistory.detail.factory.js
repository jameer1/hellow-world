 'use strict';
app.factory('PlantServiceHistoryDetailFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", "PlantServiceCategoryFactory", function(ngDialog, $q, $filter, blockUI,$timeout, $rootScope, GenericAlertService, PlantRegisterService, PlantServiceCategoryFactory) {
	var service = {};
	service.plantServiceHistoryPopUp = function(plantServiceClassMap) {
		var deferred = $q.defer();
	var popup = 	ngDialog.open({
		template : 'views/projresources/projplantreg/plantservicehistory/plantregservicehistorypopup.html',
		closeByDocument : false,
		showClose : false,
		className:'ngdialog-theme-plain ng-dialogueCustom1',
		controller : ['$scope',function($scope){
			var selectedServiceHistory = [];
					$scope.addServiceHistoryData = [];
					$scope.plantServiceClassMap = plantServiceClassMap;
					var addObj={
							"selected" : false,
							"plantRegProjTO":
								{
								"plantId" : $rootScope.selectedPlantData.id,
								"id":$rootScope.selectedPlantData.plantRegProjTO.id
								},
							"projId": $rootScope.selectedPlantData.projId,
							"date" : '',
							"currentOdoMeter" : '',
							"currentPlantServiceId" : '',
							"prevOdoMeter" : '',
							"prevPlantServiceId" : '',
							"comments" : ''
						};
					$scope.addServiceHistoryData.push(angular.copy(addObj));

					$scope.addRows = function() {
						$scope.addServiceHistoryData.push(angular.copy(addObj));
					}
					
					$scope.serviceHistoryPopUpRowSelect = function(servicehistory) {
						if (servicehistory.selected) {
							selectedServiceHistory.push(servicehistory);
						} else {
							selectedServiceHistory.pop(servicehistory);
						}
					},
					
					$scope.getServiceDetails = function(tab, no) {
						var req = {
							"status" : 1
						};
						PlantServiceCategoryFactory.getPlantServiceDetails(req).then(function(data) {
							if (no == 1) {
								tab.currentPlantServiceId = data.serviceClassTO.id;
								tab.currentPlantServiceName = data.serviceClassTO.name;
								tab.currentPlantServiceParentName = data.serviceClassTO.parentName;
							}
							if (no == 2) {
								tab.prevPlantServiceId = data.serviceClassTO.id;
								tab.prevPlantServiceName = data.serviceClassTO.name;
								tab.prevPlantServiceParentName = data.serviceClassTO.parentName;
							}
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
						});
					}, $scope.deleteRows = function() {
						if (selectedServiceHistory.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						} else if (selectedServiceHistory.length < $scope.addServiceHistoryData.length) {
							angular.forEach(selectedServiceHistory, function(value, key) {
								$scope.addServiceHistoryData.splice($scope.addServiceHistoryData.indexOf(value), 1);
							});
							selectedServiceHistory = [];
							GenericAlertService.alertMessage('Row(s) is/are deleted Successfully', "Info");
						} else {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					}
					$scope.saveServiceHistoryDetails = function() { 
						angular.forEach($scope.addServiceHistoryData,function(value,key) {
							$scope.currentPlantServiceId=value.currentPlantServiceId;
							$scope.prevPlantServiceId=value.prevPlantServiceId;
						});
						
						// if(	!$scope.currentPlantServiceId || !$scope.prevPlantServiceId ) {
						// 	GenericAlertService.alertMessage('Please Enter Service Sub Category', "Warning");
						// 	return;
						// }
						angular.forEach($scope.addServiceHistoryData,function(value,key){
							if(value.lastOdoMeterReading < value.currentOdoMeterReading){
								GenericAlertService.alertMessage('Previous Odo Meter Reading Less Than (or) Equal To Next Odo Meter Reading', "Warning");
								forEach.break();
							}
						});
						var req = {
							"plantId":$rootScope.selectedPlantData.id,
							"projId":	$rootScope.selectedPlantData.projId,
							"plantServiceHistoryTOs" : $scope.addServiceHistoryData
						}
						blockUI.start();
						PlantRegisterService.savePlantServiceHistory(req).then(function(data) {
							blockUI.stop();
							// var succMsg=GenericAlertService.alertMessageModal('Plant ServiceHistory Detail(s) is/are  ' + data.message, data.status);
							var succMsg=GenericAlertService.alertMessageModal('Plant Service History Detail(s) saved successfully',"Info");
							succMsg.then(function() {	
								 $scope.closeThisDialog();						
								 var returnPopObj = {
		                                 "plantServiceHistoryTOs": data.plantServiceHistoryTOs,
		                                 "plantServiceClassMap" : data.plantServiceClassMap
		                             };
		                             deferred.resolve(returnPopObj); 
							});
							
						}, function(error) {
							blockUI.stop();
							if (error.message != undefined && error.message != null){
								GenericAlertService.alertMessage(error.message, error.status);
							}else {
								GenericAlertService.alertMessage('Plant Service History Detail(s) is/are Failed to Save ', "Error");
							}
						});
					}
					
					$scope.getUserProjects = function() {
						var userProjectSelection = UserEPSProjectService.epsProjectPopup();
						userProjectSelection.then(function(userEPSProjData) {
							$scope.searchProject = userEPSProjData.selectedProject;
							$scope.addDeploymentData.projId = $scope.searchProject.projId;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
						});
					}
					
					
					
					} ]
		});
					return deferred.promise;
				}
				
				return service;
}]);
