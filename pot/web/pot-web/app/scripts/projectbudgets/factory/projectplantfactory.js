'use strict';
app.factory('ProjPlantFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MeasureService", "blockUI", "ProjectBudgetService", "ProjPlantClassService", "GenericAlertService", "ProjectStatusService", function(ngDialog, $q, $filter, $timeout, $rootScope,MeasureService,blockUI, ProjectBudgetService, ProjPlantClassService, GenericAlertService, ProjectStatusService) {
	var projPlantPopup;
	var service = {};
	service.plantPopupDetails = function(actionType, projId, editPlant) {
		var deferred = $q.defer();
		projPlantPopup = ngDialog.open({
			template : 'views/projectbudgets/plantspopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			preCloseCallback:function(value){
				if(value)
				  deferred.resolve();
			},
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedPlants = [];
				$scope.plantsList = [];
				var copyEditArray = angular.copy(editPlant);
				$scope.isPrimaveraIntegrationEnabled = 'Yes';
				ProjectStatusService.getProjGenerals({"projId": $scope.projId, "status": 1}).then(function(data){
					$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
				}, function (error) {
					cosole.log(error)
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
				if (actionType === 'Add') {
					$scope.plantsList.push({
						'projId' : $scope.projId,
						'originalQty' : null,
						'revisedQty' : null,
						'actualQty' : null,
						'remainingQty' : null,
							"plantClassTO" : {
								id : null,
								code : null,
								name : null
							},
							"measureUnitTO" : {
								id:null,
								code:null,
								name : null
							},
						'estimateComplete' : null,
						'estimateCompletion' : null,
						'startDate' : null,
						'finishDate' : null,
						'status' : 1
					});
				} else {
					$scope.plantsList = angular.copy(editPlant);
					editPlant = [];
				}	$scope.addRows = function() {
					$scope.plantsList.push({
						'projId' : $scope.projId,
						'originalQty' : null,
						'revisedQty' : null,
						'actualQty' : null,
						'remainingQty' : null,
							"plantClassTO" : {
								id : null,
								code : null,
								name : null
							},
							"measureUnitTO" : {
								id:null,
								code:null,
								name : null
							},
						'estimateComplete' : null,
						'estimateCompletion' : null,
						'startDate' : null,
						'finishDate' : null,
						'status' : 1
					});
				},
				$scope.calcRemainingUnits = function(budgetObj) {
					if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0) {

						budgetObj.remainingQty = budgetObj.revisedQty - budgetObj.actualQty;
						return budgetObj.remainingQty;
					} else {
						budgetObj.remainingQty = budgetObj.originalQty - budgetObj.actualQty;
						return budgetObj.remainingQty;
					}
				}
				var plantService = {};
				$scope.plantsDetails = function(tab) {
					var projPlantsPopup = plantService.getPlantDetails($scope.projId);
					projPlantsPopup.then(function(data) {
						tab.plantClassTO = data.plantClassTO;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting project plant class Details", 'Error');
					});
				}

				plantService.getPlantDetails = function(projId) {
					var deferred = $q.defer();
					ngDialog.open({
						template : 'views/projectbudgets/plantslist.html',
						closeByDocument : false,
						showClose : false,
						className : 'ngdialog-theme-plain ng-dialogueCustom4',
						controller : [ '$scope', function($scope) {
							$scope.getPlantClasses = function() {
								var req={
										"status" : 1,
										"projId" : projId
								}
								ProjectBudgetService.getPlantClasses(req).then(function(data) {
									$scope.plantClassification = data.plantClassTOs;
								});
							},
							$scope.projPlantClassPopUp = function(plantClassTO) {
								var returnProjPlantClassTO = {
									"plantClassTO" : plantClassTO
								};
								deferred.resolve(returnProjPlantClassTO);
								$scope.closeThisDialog();

							}
						} ]
					});
					return deferred.promise;
				}


				$scope.getMeasuresByProcureType = function() {
					var req = {
						"status" : 1,
						//"procureClassName" : "Plants"
					};
					MeasureService.getMeasuresByProcureType(req).then(function(data) {
						$scope.measureUnitTO = data.measureUnitTOs;
					});
				}, $scope.updatePlantCode = function(data, manpower) {

					manpower.measureId = data.id;
				},$scope.savePlants = function() {
					console.log("savePlants function"),
					angular.forEach($scope.plantsList, function(value) {
						if ($scope.isPrimaveraIntegrationEnabled=='No') {
							if(value.startDate==null|| value.finishDate==null){
								GenericAlertService.alertMessage('Date fields can not be empty  ', 'Warning');
								forEach.break();
								return;
							}
							var startDate = new Date(value.startDate);
							var finishDate = new Date(value.finishDate);
								if (startDate > finishDate) {
								GenericAlertService.alertMessage('Schedule Start Date must be less than Schedule Finish Date', 'Warning');
								forEach.break();
								return;
							}
						}
					})


					angular.forEach($scope.plantsList, function(value) {
						if(value.plantClassTO.code==null || value.plantClassTO.code==undefined || value.plantClassTO.name==null || value.plantClassTO.name==undefined){
						GenericAlertService.alertMessage('Please select ResourceId  ', 'Warning');
					forEach.break();
					return;
						}
					})

					var plantsavereq = {
						"projectPlantsDtlTOs" : $scope.plantsList,
						"projId" : $scope.projId
					}
					console.log(plantsavereq);
					blockUI.start();
					ProjectBudgetService.saveProjectPlants(plantsavereq).then(function(data) {
						blockUI.stop();
						var results=data.projectPlantsDtlTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Plant(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant saved successfully ',"Info");
						succMsg.then(function(data) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"projectPlantsDtlTOs" : results
							}
							deferred.resolve(returnPopObj)
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant(s)  is/are failed to save', "Error");
					});
				},

				$scope.plantsPopUpRowSelect = function(plants) {
					if (plants.selected) {
						selectedPlants.push(plants);
					} else {
						selectedPlants.splice(selectedPlants.indexOf(plants), 1);
					}
				}, $scope.deleteProjRows = function() {
					if (selectedPlants.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPlants.length < $scope.plantsList.length) {
						angular.forEach(selectedPlants, function(value, key) {
							$scope.plantsList.splice($scope.plantsList.indexOf(value), 1);
							GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
						});
						selectedPlants = [];
					} else if(selectedPlants.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
					}
					else if(selectedPlants.length == 1){
					$scope.plantsList[0] = {
							'projId' : $scope.projId,
							"plantClassTO" : {
								id : null,
								code : null,
								name : null
							},
							"measureUnitTO" : {
								id:null,
								code:null,
								name : null
							},
							'originalQty' : '',
							'revisedQty' : '',
							'actualQty' : '',
							'remainingQty' : '',
							'estimateComplete' : '',
							'estimateCompletion' : '',
							'startDate' : '',
							'finishDate' : '',
							'status' : 1
					};
					selectedPlants = [];
					GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
				}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
