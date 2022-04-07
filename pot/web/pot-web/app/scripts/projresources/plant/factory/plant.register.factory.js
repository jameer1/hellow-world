'use strict';
app.factory('PlantDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "PlantDetailsListFactory", "ProcureCategoryFactory", "CompanyListPopUpFactory", "GenericAlertService", "PlantRegisterService", function (ngDialog, $q, $filter, $timeout, blockUI, $rootScope, PlantDetailsListFactory, ProcureCategoryFactory, CompanyListPopUpFactory, GenericAlertService, PlantRegisterService) {
	var plantDetailsPopUp;
	var service = {};

	service.plantDetailsPopUp = function (assertTypes, actionType, editPlantData) {
		var deferred = $q.defer();
		plantDetailsPopUp = ngDialog.open({
			template: 'views/projresources/projplantreg/plantdetails/plantregdetailspopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			controller: ['$scope', function ($scope) {
				$scope.action = null;
				$scope.action = actionType;
				$scope.addPlantData = [];
				var selectedPlant = [];
				$scope.assetIds = [];
				$scope.assertTypes = assertTypes;
				let newPlant = {
					"id": null,
					"selected": false,
					"assertId": null,
					"regNumber": null,
					"plantClassMstrId": null,
					"desc": null,
					"manfacture": null,
					"model": null,
					"assetType": null,
					"cmpId": null,
					"procurecatgId": null
				};

				$scope.duplicatePlantCode = function (plants) {
					plants.duplicateFlag = false;
					plants.assertId = plants.assertId ? plants.assertId.toUpperCase() : plants.assertId;
					PlantRegisterService.isPlantCodeUnique(plants.assertId).then(function (data) {
						plants.duplicateFlag = !data;
					});
					// start
					let dupAssetIds = 0;
					$scope.isValid = false;
				var getPlantRegisterReq = {
						"status": "1",
		//				"projIds": null
					}
					PlantRegisterService.getPlantRegistersOnLoad(getPlantRegisterReq).then(function (data) {
		
						angular.forEach(data.plantRegisterDtlTOs, function(key, value){
			
								$scope.assetIds.push(angular.copy(key));
								if(key.assertId == $scope.addPlantData[0].assertId){
									dupAssetIds =  1;
					//			GenericAlertService.alertMessage('Duplicate Asset Ids are not allowed',"Warning");
							
								}
					//		}
						});
							if(dupAssetIds == 1){
								$scope.CallBacktoUser();
							}
							return;
					});
					// end
					// if proj Id is null then..
					var getPlantRegisterRequest = {
						"status" : "1",
						"projIds" : null
					}
					PlantRegisterService.getPlantRegistersOnLoad(getPlantRegisterRequest).then(function (data) {
						angular.forEach(data.plantRegisterDtlTOs, function(key, value){
							if(key.projId == null){
				
								$scope.assetIds.push(angular.copy(key));
								if(key.assertId == $scope.addPlantData[0].assertId){
									console.log(dupAssetIds)
									dupAssetIds =  1;
									console.log(dupAssetIds);
					
							
								}
							}
					});
						if(dupAssetIds == 1){
									$scope.CallBacktoUser();
								}
								return;
					});
			$scope.CallBacktoUser =	function() {
				console.log("call CallBacktoUser "+dupAssetIds)
				$scope.isValid = true;
					$scope.errorMessage = "Asset Ids already taken";
					return;
				}
				};
				if (actionType === 'Add') {
					$scope.addPlantData.push(angular.copy(newPlant));
				} else {
					$scope.addPlantData = angular.copy(editPlantData);
					editPlantData = [];
				}
				$scope.addRows = function () { 
					$scope.addPlantData.push(angular.copy(newPlant));
				},
					$scope.plantDetailsPopUpRowSelect = function (plant) {
						if (plant.selected) {
							selectedPlant.push(plant);
						} else {
							selectedPlant.pop(plant);
						}
					},
					$scope.deleteRows = function () {
						if (selectedPlant.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
							return;
						}
						if (selectedPlant.length < $scope.addPlantData.length) {
							angular.forEach(selectedPlant, function (value, key) {
								$scope.addPlantData.splice($scope.addPlantData.indexOf(value), 1);
							});
							selectedPlant = [];
							GenericAlertService.alertMessage('Row(s) deleted successfully', "Info");
							$rootScope.$broadcast("resetPlant");
						} else {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					}
				$scope.save = function () {
					var flag = false;
					var plantMap = [];
					if (actionType === 'Add') {
						angular.forEach($scope.addPlantData, function (value, key) {
							if (plantMap[value.assertId.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							} else {
								value.duplicateFlag = false;
								plantMap[value.assertId.toUpperCase()] = true;
							}
						});

						let dupFlag = false, dupMessage = "Duplicate Asset Ids are not allowed for ";

						for (let plant of $scope.addPlantData) {
							if (plant.duplicateFlag) {
								dupFlag = true;
								dupMessage += plant.assertId;
								break;
							}
						}

						if (dupFlag) {
							GenericAlertService.alertMessage(dupMessage, "Warning");
							return;
						}
					}

					var req = {
						"plantRegisterDtlTOs": $scope.addPlantData
					}
					blockUI.start();
					PlantRegisterService.savePlantRegisters(req).then(function (data) {
						blockUI.stop();
						// var succMsg = GenericAlertService.alertMessageModal('Plant Registration Details is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant Registration Details saved successfully ',"Info" );
						succMsg.then(function (data) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"plantRegisterDtlTOs": data.plantRegisterDtlTOs
							};
							deferred.resolve(returnPopObj);
						});
						editPlantData = [];
						$rootScope.$broadcast('plantRefresh');
					}, function (error) {
						blockUI.stop();
						if (error.status != undefined && error.status != null) {
							GenericAlertService.alertMessage(message, error.status);
						} else {
							GenericAlertService.alertMessage("Plant Registration Detail(s) is/are Failed to Save", 'Error');
						}
					});
				},
					$scope.plantClassDetails = function (inputPlantData) {
						var getPlantReq = {
							"status": 1
						};
						PlantDetailsListFactory.getPlantClassDetails(getPlantReq).then(function (data) {
							inputPlantData.plantClassMstrId = data.plantClassTO.id;
							inputPlantData.plantClassMstrCode = data.plantClassTO.code;
							inputPlantData.plantClassMstrName = data.plantClassTO.name;
							inputPlantData.plantClassMeasureId = data.plantClassTO.measureUnitTO.id;
							inputPlantData.plantClassMeasureName = data.plantClassTO.measureUnitTO.name;
						});
					},
					$scope.plantCategoryDetails = function (inputPlantData) {
						$scope.plantCategoryReq = {
							"status": 1,
							"procureClassName": "Plants"
						};
						var plantCategoryDetailsPopup = ProcureCategoryFactory.getProcureCategoryDetails($scope.plantCategoryReq);
						plantCategoryDetailsPopup.then(function (data) {
							inputPlantData.procurecatgId = data.selectedRecord.proCatgId;
							inputPlantData.procurecatgCode = data.selectedRecord.code;
							inputPlantData.procurecatgName = data.selectedRecord.desc;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting project plant classes", 'Error');
						});
					},
					$scope.plantCompanyDetails = function (inputPlantData) {
						var plantCompanyDetailsPopup = CompanyListPopUpFactory.getCompanies();
						plantCompanyDetailsPopup.then(function (data) {
							inputPlantData.cmpId = data.selectedRecord.id;
							inputPlantData.cmpCode = data.selectedRecord.cmpCode;
							inputPlantData.cmpName = data.selectedRecord.cmpName;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting company details", 'Error');
						});
					},
					$scope.plantOwnerCompanyDetails = function (inputPlantData) {
						var plantCompanyDetailsPopup = CompanyListPopUpFactory.getCompanies();
						plantCompanyDetailsPopup.then(function (data) {
							inputPlantData.ownerCmpId = data.selectedRecord.id;
							inputPlantData.ownerCmpCode = data.selectedRecord.cmpCode;
							inputPlantData.ownerCmpName = data.selectedRecord.cmpName;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting company details", 'Error');
						});
					}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
