'use strict';
app.factory('PlantServiceHistoryRepairFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "GenericAlertService", "MaterialRegisterService", "PlantRegisterService", "PlantServiceCategoryFactory", "PlantRepairMaterialProjDocketFactory", "MaterialClassificationFactory", function (ngDialog, $q, $filter, blockUI, $timeout, $rootScope, GenericAlertService, MaterialRegisterService, PlantRegisterService, PlantServiceCategoryFactory, PlantRepairMaterialProjDocketFactory, MaterialClassificationFactory) {
	var plantServiceHistoryRepairPopUp;
	var service = {};
	service.addServiceRepairs = function () {
		var deferred = $q.defer();
		plantServiceHistoryRepairPopUp = ngDialog.open({
			template: 'views/projresources/projplantreg/plantservicehistoryrepairs/plantregservicehistoryrepairpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				var selectedServiceHistoryRepair = [];
				$scope.addServiceHistoryRepairData = [];
				var addObj = {
					"selected": false,
					"plantId": $rootScope.selectedPlantData.id,
					"plantRegProjTO": {
						"plantId": $rootScope.selectedPlantData.id,
						"id": $rootScope.selectedPlantData.plantRegProjTO.id
					},
					"projId": $rootScope.selectedPlantData.projId,
					"date": '',
					"odoMeter": '',
					"serviceId": '',
					"materialId": '',
					"quantity": '',
					"projDocket": '',
					"comments": ''
				};
				$scope.addServiceHistoryRepairData.push(angular.copy(addObj));
				$scope.addRows = function () {
					$scope.addServiceHistoryRepairData.push(angular.copy(addObj));
				};
				$scope.serviceHistoryRepairPopUpRowSelect = function (servicehistoryrepair) {
					if (servicehistoryrepair.selected) {
						selectedServiceHistoryRepair.push(servicehistoryrepair);
					} else {
						selectedServiceHistoryRepair.pop(servicehistoryrepair);
					}
				};
				$scope.getMaterialDetails = function (repairTO) {
					var req = {
						"status": 1
					};
					MaterialClassificationFactory.getPlantMaterialDetails(req).then(function (data) {
						repairTO.materialId = data.projMaterialTO.id;
						repairTO.materialName = data.projMaterialTO.name;
						repairTO.materialUnit = data.projMaterialTO.measureUnitTO.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting plant material classications", "Error");
					});

				};
				$scope.getPlantMaterialProjDocketDetails = function (repairTO) {
					$scope.projId = $rootScope.selectedPlantData.projId;
					if (repairTO.materialId == undefined || repairTO.materialId == null || repairTO.materialId <= 0) {
						GenericAlertService.alertMessage("Please select matrial classification to get project docket details", "Warning");
						return;
					}
					PlantRepairMaterialProjDocketFactory.getPlantMaterialProjDocketDetails($scope.projId, repairTO).then(function (data) {
						repairTO.projDocket = data.docNum;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting plant material project docket details", "Error");
					});
				}
				$scope.deleteRows = function () {
					if (selectedServiceHistoryRepair.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedServiceHistoryRepair.length < $scope.addServiceHistoryRepairData.length) {
						angular.forEach(selectedServiceHistoryRepair, function (value, key) {
							$scope.addServiceHistoryRepairData.splice($scope.addServiceHistoryRepairData.indexOf(value), 1);
						});
						selectedServiceHistoryRepair = [];
						GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				$scope.saveRepairDetails = function () {
					if (!$scope.addServiceHistoryRepairData[0].serviceId) {
						GenericAlertService.alertMessage('Please Enter Item of Repair', "Warning");
						return;
					}
					if (!$scope.addServiceHistoryRepairData[0].materialId) {
						GenericAlertService.alertMessage('Please Enter Major Spare Part Items ', "Warning");
						return;
					}
					var req = {
						"plantId": $rootScope.selectedPlantData.id,
						"projId": $rootScope.selectedPlantData.projId,
						"plantRepairsTOs": $scope.addServiceHistoryRepairData
					}
					blockUI.start();
					PlantRegisterService.savePlantRepairRecords(req).then(function (data) {
						blockUI.stop();
						$scope.closeThisDialog();
						// var succMsg = GenericAlertService.alertMessageModal('Plant ServiceHistory Repairs Details are  ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant Service History Repairs Details saved successfully  ',"Info");
						succMsg.then(function () {
							var returnPopObj = {
								"plantRepairsTOs": data.plantRepairsTOs
							};
							deferred.resolve(returnPopObj);
						});
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant ServiceHistory Repairs Details are Failed to Save ', "Error");
					});
				};
				$scope.getServiceDetails = function (tab) {
					var getPlantServiceReq = {
						"status": 1
					};
					PlantServiceCategoryFactory.getPlantServiceDetails(getPlantServiceReq).then(function (data) {
						tab.serviceId = data.serviceClassTO.id;
						tab.serviceParentName = data.serviceClassTO.parentName;
						tab.serviceName = data.serviceClassTO.name;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Purchase Order Details", "Error");
					});
				}
				$scope.getUserProjects = function () {
					var userProjectSelection = UserEPSProjectService.epsProjectPopup();
					userProjectSelection.then(function (userEPSProjData) {
						$scope.searchProject = userEPSProjData.selectedProject;
						$scope.addDeploymentData.projId = $scope.searchProject.projId;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}

			}]
		});
		return deferred.promise;
	}
	return service;
}]);
