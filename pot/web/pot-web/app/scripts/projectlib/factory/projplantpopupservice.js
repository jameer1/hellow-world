'use strict';
app.factory('ProjPlantClassificationPopupService', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "ProjPlantClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, blockUI,$rootScope, ProjPlantClassService, GenericAlertService) {
	var projPlantClassifiPopUp;
	var service = {};
	service.projPlantClassifiPopUp = function(actionType, selectedProject, editPlantClass) {
		var deferred = $q.defer();
		projPlantClassifiPopUp = ngDialog.open({
			template : 'views/projectlib/plantclass/projplantpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var copyEditArray = angular.copy(editPlantClass);
				$scope.resourceData = [];
				var selectedPlant = [];
				$scope.measureUnits = [];
				$scope.addprojetplant = [];
				var emptyProPlantClassifyObj = [];
				$scope.action = actionType;
				if (actionType === 'Edit') {
					$scope.addprojetplant = angular.copy(editPlantClass);
				}
				var onLoadReq = {
					"status" : 1,
					"projId" : selectedProject.projId
				};
				ProjPlantClassService.projPlantClassifOnLoad(onLoadReq).then(function(data) {
					$scope.resourceData = data.plantClassResp.plantClassTOs;
					$scope.updateResCode = function(data, tab) {
						tab.plantClassId = data.id;
					}
					$scope.measureUnits = data.measureUnitResp.measureUnitTOs;
					$scope.updateMeasure = function(data, tab) {
						tab.measureId = data.id;
					}
					if (actionType === 'Add') {
						emptyProPlantClassifyObj = data.projPlantClassTO;
						var localprojPlantClassTO = angular.copy(emptyProPlantClassifyObj);
						$scope.addprojetplant.push(localprojPlantClassTO);
					}
				});
				$scope.addprojetplantClass = function() {
					var localprojPlantClassTO = angular.copy(emptyProPlantClassifyObj);
					$scope.addprojetplant.push(localprojPlantClassTO);
				},
				

				$scope.getProjPlantMap = function() {
					var req = {
							"projId" : selectedProject.projId
					}
					ProjPlantClassService.getProjPlantClassMap(req).then(function(data) {
					$scope.uniqueCodeMap = data.projUniqueCodeMap;
					})
					}, 
				 $scope.checkDuplicate = function(tab) {
				 tab.duplicateFlag = false;
				 if ($scope.uniqueCodeMap[tab.plantClassTO.id] != null) {
				 tab.duplicateFlag = true;
				 return;
				 }
				 tab.duplicateFlag = false;
				 },
				
				$scope.saveProjPlantClasses = function() {
					
					var flag = false;
					var plantMap=[];
					angular.forEach($scope.addprojetplant, function(value, key) {
						if (plantMap[value.plantClassTO.id] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							plantMap[value.plantClassTO.id] = true;
						}
					});
					
					 if (actionType === 'Add'){				
							angular.forEach($scope.addprojetplant, function(value, key) {
								 if ($scope.uniqueCodeMap[value.plantClassTO.id] != null) {
									value.duplicateFlag = true;
									flag = true;
								}
							});
							}else{							
								angular.forEach($scope.addprojetplant, function(value, key) {
									angular.forEach(copyEditArray, function(value1, key) {
										if(value1.plantClassTO.id == value.plantClassTO.id ){
											value.duplicateFlag = false;
											flag = false;
										}else{
											 if ($scope.uniqueCodeMap[value.plantClassTO.id] != null) {
												value.duplicateFlag = true;
												flag = true;
											} else {
												value.duplicateFlag = false;
											}
										}
									});
								});
							}
					 if (flag) {
					 GenericAlertService.alertMessage('Duplicate plants are not allowed', "Warning");
					 return;
					 }
					var req = {
						"projPlantClassTOs" : $scope.addprojetplant,
						"projId" : selectedProject.projId
					};
					blockUI.start();
					ProjPlantClassService.saveProjPlantClasses(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var projectClassTOs = data.projPlantClassTOs;
							var succMsg = GenericAlertService.alertMessageModal('Plant Classification(s) is/are ' + data.message, data.status);
							succMsg.then(function(popData) {
								var returnPopObj = {
									"projPlantClassTOs" : projectClassTOs
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant Classification(s) is/are failed to Save', 'Error');
					});
				}, $scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedPlant.push(tab);
					} else {
						selectedPlant.splice(selectedPlant.indexOf(tab), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedPlant.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedPlant.length < $scope.addprojetplant.length) {
						angular.forEach(selectedPlant, function(value, key) {
							$scope.addprojetplant.splice($scope.addprojetplant.indexOf(value), 1);
						});
						selectedPlant = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if(selectedPlant.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
					}
					else if(selectedPlant.length == 1){
					$scope.addprojetplant[0] = {
						'plantClassTO.code' : '',
						'plantClassTO.name' : '',
						'plantContrName' : '',
						'plantClassTO.measureUnitTO.name' : '',
						'status' : '1',
						'selected' : false
					};
					selectedPlant = [];
					GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
				}
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);
