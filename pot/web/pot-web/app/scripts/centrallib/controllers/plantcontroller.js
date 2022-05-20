'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plant", {
		url : '/plant',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/plantclass/plantclass.html',
				controller : 'PlantClassController'
			}
		}
	})
}]).controller("PlantClassController", ["$rootScope", "$q", "$scope","uiGridGroupingConstants", "uiGridConstants", "utilservice", "ngDialog", "blockUI", "PlantClassService", "MeasureService",'stylesService', 'ngGridService', "GenericAlertService", "generalservice", function($rootScope, $q, $scope, uiGridGroupingConstants, uiGridConstants, utilservice, ngDialog,blockUI, PlantClassService, MeasureService,stylesService, ngGridService, GenericAlertService,generalservice) {
	$scope.stylesSvc = stylesService;
	$scope.central = {};
	$scope.plantClassification = [];
	$scope.deletecodes = [];
	$scope.uniqueCodeMap = [];
	$scope.sortType = "code"
	var deferred = $q.defer();
	$scope.plantReq = {
		"plantCode" : null,
		"plantName" : null,
		"status" : "1"
	};
	$scope.procCategory = generalservice.getprocures;
	var editplantClassification = [];

	// $scope.activeFlag = 0;
	$scope.plantSearch = function(click) {
		PlantClassService.getPlantClasses($scope.plantReq).then(function(data) {
			editplantClassification = [];
			// $scope.activeFlag = 0;
			$scope.plantClassification = data.plantClassTOs;
			// var dummyPlant = angular.copy($scope.plantClassification).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{
			// item.status = 'Inactive'}
			// return item;
			// });
			$scope.gridOptions.data = $scope.plantClassification;
			if(click=='click'){
				if ($scope.plantClassification.length <= 0) {
					GenericAlertService.alertMessage('Plant Details are not available for given search criteria', "Warning");
					return;
				}
			}
			// removed code

			// if ($scope.plantClassification != null && $scope.plantClassification.length > 0) {
			// 	if ($scope.plantClassification[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.plantClassification[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.plantReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.plantReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.plantReq = {
			// 		"plantCode" : null,
			// 		"plantName" : null,
			// 		"status" : "1"
			// 	};
			// }
		});
	}, $scope.reset = function() {
		$scope.plantReq = {
			"plantCode" : null,
			"plantName" : null,
			"status" : "1"
		}
		// $scope.activeFlag = 0;
		$scope.plantSearch();
	},

	$scope.rowSelect = function(central) {
		if (central.selected) {
			utilservice.addItemToArray(editplantClassification, "id", central);
		} else {
			editplantClassification.splice(editplantClassification.indexOf(central), 1);
		}
	}
	var service = {};
	var plantPopUp;
	$scope.plantUnits = function(actionType) {

		angular.forEach(editplantClassification,function(value,key){
			value.selected=false;
				});

		if(editplantClassification.length >0 && actionType==="Add" ){
			editplantClassification=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}

		plantPopUp = service.addPlantUnits(actionType);
		plantPopUp.then(function(data) {
			$scope.plantClassification = data.plantClassTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting Plant details", 'Error');
		});
	}
	service.addPlantUnits = function(actionType) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editplantClassification <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		plantPopUp = ngDialog.open({
			template : 'views/centrallib/plantclass/plantclasspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $scope,
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.plantUnits = [];
				var selectedPlant = [];
				$scope.action = actionType;
				$scope.plantUnits = [];
				$scope.proCategory = [];
				var CpcCode="";
				var CpcName="";
				var copyEditArray = angular.copy(editplantClassification);
				var copyEditArray = angular.copy(editplantClassification);
				if (actionType === 'Add') {
					$scope.plantUnits.push({
						"code" : '',
						"name" : '',
						"measureUnitTO.name" : '',
						"status" : 1,
						"selected" : false
					});
				} else {
					$scope.plantUnits = angular.copy(editplantClassification)
					editplantClassification = [];
				}
				$scope.getMeasuresByProcureType = function() {
					var req = {
						"status" : 1,
						//"procureClassName" : $scope.procCategory[2].name
					};
					MeasureService.getMeasuresByProcureType(req).then(function(data) {
						$scope.proCategory = data.measureUnitTOs;
					});
				}, $scope.updatePlantCode = function(data, central) {
					central.measureId = data.id;
				}, $scope.addRows = function() {
					$scope.plantUnits.push({
						"code" : '',
						"name" : '',
						"measureUnitTO.name" : '',
						"status" : 1,
						"selected" : false
					});
				}, $scope.getPlantClassMap = function() {
					var req = {

					}
					PlantClassService.getPlantClassMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})

				},

				$scope.checkDuplicate = function(plant) {
					plant.duplicateFlag = false;
					CpcCode=plant.code
				angular.forEach($scope.plantClassification, function(value,key){
					if(value.code === plant.code){
						plant.duplicateFlag = true;
					}
				});	
				if(plant.duplicateFlag){
					GenericAlertService.alertMessage('Duplicate Plant Classfication codes are not allowed', "Warning");
							return;
				}	
										
					/*if(plant.code && plant.measureUnitTO){										
						plant.duplicateFlag = false;
						plant.code = plant.code.toUpperCase();
						if ($scope.uniqueCodeMap[plant.code+" "+plant.measureUnitTO.name] != null) {
							plant.duplicateFlag = true;							
							//alert(plant.duplicateFlag);
							GenericAlertService.alertMessage('Duplicate Plant Classfication codes are not allowed', "Warning");
							return;
						}
						plant.duplicateFlag = false;
					}*/
				},
				
				$scope.checkDuplicate1 = function(plant) {
					CpcName=plant.name;
					plant.duplicateFlag1 = false;
					angular.forEach($scope.plantClassification, function(value,key){
					if(value.name === plant.name){
						 plant.duplicateFlag1 = true;
					}
				});
				if(plant.duplicateFlag1){
					GenericAlertService.alertMessage('Duplicate Plant Classfication name are not allowed', "Warning");
					return;
				}	
				},
				 $scope.savePlantClasses = function(centralForm) {
					var flag = false;
					var flag1 = false;
					var plantClassMap = [];
					/*angular.forEach($scope.plantUnits, function(value, key) {
						if (plantClassMap[value.code.toUpperCase()] != null) {						
							value.duplicateFlag = true;
							value.duplicateFlag11 = true;
							flag = true;
						} else {												
							value.duplicateFlag = false;
							plantClassMap[value.code.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add'|| actionType === 'Edit'){
						angular.forEach($scope.plantUnits, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase() + " " + value.measureUnitTO.name] != null) {
								value.duplicateFlag = true;
								value.duplicateFlag1 = true;
								flag = true;
							}
						});
					}*/
					
					angular.forEach($scope.plantClassification, function(value, key) {
						if(value.code === CpcCode){
						flag = true;
					}
					});
					
					angular.forEach($scope.plantClassification, function(value, key) {
						if(value.name === CpcName){
						flag1 = true;
					}
					});
					
					
					if (flag) {						
						GenericAlertService.alertMessage('Duplicate Plant Classfication codes are not allowed', "Warning");						
						return;
					}
					if (flag1) {						
						GenericAlertService.alertMessage('Duplicate Plant Classfication name are not allowed', "Warning");						
						return;
					}
					var req = {
						"plantClassTOs" : $scope.plantUnits
					}
					var results = [];
					blockUI.start();
					PlantClassService.savePlantClasses(req).then(function(data) {
						blockUI.stop();
						results = data.plantClassTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Plant Classification(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Plant Classification(s) saved successfully',"Info");
						succMsg.then(function(data1) {
							$scope.closeThisDialog(plantPopUp);
							var returnPopObj = {
								"plantClassTOs" : results
							}
							deferred.resolve(returnPopObj);
							$scope.plantSearch(); 
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant Classification(s) is/are  are failed to save', "Error");
					});
				}, $scope.popUpRowSelect = function(central) {
					if (central.selected) {
						selectedPlant.push(central);
					} else {
						selectedPlant.splice(selectedPlant.indexOf(central), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedPlant.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedPlant.length < $scope.plantUnits.length) {
						angular.forEach(selectedPlant, function(value, key) {
							$scope.plantUnits.splice($scope.plantUnits.indexOf(value), 1);
						});
						selectedPlant = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedPlant.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedPlant.length == 1) {
						$scope.plantUnits[0] = {
							"code" : '',
							"name" : '',
							"measureUnitTO.name" : '',
							"status" : 1,
							"selected" : false
						};
						selectedPlant = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			} ]
		});
		return deferred.promise;
	}, $scope.activePlantClasses = function() {
		if (editplantClassification.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.plantClassification = [];
		} else {
			angular.forEach(editplantClassification, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"plantClassIds" : deleteIds,
				"status" : 1
			};
			PlantClassService.deletePlantClasses(req).then(function(data) {
			});
			angular.forEach(editplantClassification, function(value, key) {
				GenericAlertService.alertMessage('Plant Classification(s) activated successfully', 'Info');
				$scope.plantClassification.splice($scope.plantClassification.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Plant Classification(s) is/are failed to activate', "Error");
			});
			editplantClassification = [];
			$scope.deleteIds = [];
		}
	}
	$scope.deletePlantClasses = function() {
		if (editplantClassification.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.plantClassification = [];
		} else {
			angular.forEach(editplantClassification, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"plantClassIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				PlantClassService.deletePlantClasses(req).then(function(data) {
					$scope.plantSearch();
				});
				GenericAlertService.alertMessage('Plant Classification(s) deactivated successfully', 'Info');
				angular.forEach(editplantClassification, function(value, key) {
					//GenericAlertService.alertMessage('Plant Classification(s) deactivated successfully', 'Info');
					$scope.plantClassification.splice($scope.plantClassification.indexOf(value), 1);					
					editplantClassification = [];
					$scope.deleteIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Plant Classification(s) is/are failed to deactivate', "Error");
				});
			}, function(data) {				
				angular.forEach(editplantClassification, function(value) {
					value.selected = false;
				})
			})
		}
		
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'5%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate, 
				   displayName: "Select", headerTooltip : "Select" },
				{ field: 'code', displayName: "Plant Classification Code", headerTooltip: "Plant Classification Code", groupingShowAggregationMenu: false },
				{ field: 'name', displayName: "Plant Classification Name", headerTooltip: "Plant Classification Name", groupingShowAggregationMenu: false },	
				{ field: 'measureUnitTO.name', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },				
				{ field: 'status',displayName:'Status',cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status", }				
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_Central_Plant_Classification");
			$scope.plantSearch(); 
			$scope.gridOptions.showColumnFooter = false;
		}
	});
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/plantclassificationhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
