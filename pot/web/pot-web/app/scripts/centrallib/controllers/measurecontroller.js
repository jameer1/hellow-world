'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("measure", {
		url : '/measure',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/measure/measurement.html',
				controller : 'MeasureController'
			} 
		}
	})
}]).controller("MeasureController", ["$rootScope", "$q", "$scope","uiGridGroupingConstants", "uiGridConstants", "utilservice", "blockUI", "ngDialog", "MeasureService",'stylesService', 'ngGridService', "GenericAlertService", "generalservice", function($rootScope, $q, $scope,uiGridGroupingConstants, uiGridConstants, utilservice, blockUI, ngDialog, MeasureService,stylesService, ngGridService, GenericAlertService,generalservice) {
	 $scope.stylesSvc = stylesService;
	var service = {}
	returnedData: [], $scope.measurement = {};
	$scope.measuringUnits = [];
	$scope.sortType = "code"
	var deferred = $q.defer();
	$scope.deletecodes = [];
	var editMeasuringUnits = [];
	$scope.proCategory = [];
	$scope.uniqueCodeMap = [];
	$scope.measReq = {
		"mesureCode" : null,
		"mesureName" : null,
		"status" : "1"
	}, 
	
	$scope.measureSearch = function(click) {
		MeasureService.getMeasurements($scope.measReq).then(function(data) {
			editMeasuringUnits = [];
			$scope.measuringUnits = data.measureUnitTOs;
			$scope.gridOptions.data = $scope.measuringUnits;
			// var copyMeasuring = angular.copy($scope.measuringUnits).map((item) => {
			// 						if(item.status == 1 ) {
			// 						item.status = 'Active';}
			// 						else {
			// 						item.status = 'Inactive';
			// 						}
			// 						return item;
			// 						});
			$scope.gridOptions.data = copyMeasuring;
			if(click=='click'){
				if ($scope.measuringUnits.length <= 0) {
					GenericAlertService.alertMessage('Measuring Units details are not available for given search criteria', "Warning");
					return;
				}
			}
		});
	}, $scope.reset = function() {
		$scope.measReq = {
			"mesureCode" : null,
			"mesureName" : null,
			"status" : "1"
		}
		$scope.measureSearch();
	}, $scope.rowSelect = function(measurement) {
		if (measurement.selected) {
			utilservice.addItemToArray(editMeasuringUnits, "id", measurement)
		} else {
			editMeasuringUnits.splice(editMeasuringUnits.indexOf(measurement), 1);		
		}
	}
	var service = {};
	var measurePopUp;
	$scope.addMeasureUnits = function(actionType) {
		angular.forEach(editMeasuringUnits, function(value, key) {
			value.selected=false;
		});
		if(editMeasuringUnits.length >0 && actionType=="Add"){
			editMeasuringUnits=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		measurePopUp = service.addMeasures(actionType);
		measurePopUp.then(function(data) {
			$scope.measuringUnits = data.measureUnitTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting Measurement  details", 'Error');
		});
	}
	service.addMeasures = function(actionType) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editMeasuringUnits <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		measurePopUp = ngDialog.open({
			template : 'views/centrallib/measure/addmeasurepopup.html',
			scope : $scope,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.measureUnits = [];
				$scope.proCategory = [];
				var copyEditArray = angular.copy(editMeasuringUnits);
				var selectedMeasureUnits = [];
				var addMeasure = {
					"code" : '',
					"name" : '',
					"status" : "1",
					"procurementTO" : {
						"id" : null,
						"code" : null,
						"name" : null
					},
					"selected" : false
				};
				if (actionType === 'Add') { // Add
					$scope.measureUnits.push(addMeasure);
				} else {
					$scope.measureUnits = angular.copy(editMeasuringUnits);
					editMeasuringUnits = [];
					for (const measureUnit of $scope.measureUnits) {
						for (const proCatg of generalservice.getprocures) {
							if (measureUnit.procureClassName === proCatg.name) {
								measureUnit.procurementTO = proCatg;
							}else{
								measureUnit.procurementTO = {
									"id" : null,
									"code" : null,
									"name" : null
								}
							}
						}
					}
				}
				var req = {
					"status" : 1
				};
				
				$scope.proCategory = generalservice.getprocures;
				$scope.updateMeasurementCode = function(data, measurement) {
					measurement.procureClassId = data.id;
				};
				 $scope.addRows = function() {
					/*angular.forEach($scope.measureUnits,function(value){
						if(value.procurementTO.name==null){
							GenericAlertService.alertMessage('please enter classifications', "Warning");
							forEach.break();
							return;
						}
					
					})*/
					$scope.measureUnits.push({
						"code" : '',
						"name" : '',
						"status" : "1",
						"procurementTO" : {
							"id" : null,
							"code" : null,
							"name" : null
						},
						"selected" : false
					});
				},

				$scope.getMeasurementMap = function() {
					var req = {

					}
					MeasureService.getMeasurements(req).then(function(data) {console.log(data);
						$scope.uniqueCodeMap = data.measureUnitTOs;
					})
					
				}, $scope.checkDuplicate = function(measurement) {
					measurement.duplicateFlag = false;
					measurement.code = measurement.code.toUpperCase();
					for(let i=0;i<$scope.uniqueCodeMap.length;i++){
					if(measurement.code == $scope.uniqueCodeMap[i].code){
					measurement.duplicateFlag = true;
					GenericAlertService.alertMessage('Duplicate measurement  codes are not allowed', "Warning");
					return;
						}
					}measurement.duplicateFlag = false;
				},
				/*$scope.checkDuplicate = function(measurement) {console.log($scope.uniqueCodeMap);
					measurement.duplicateFlag = false;
					measurement.code = measurement.code.toUpperCase();
					if ($scope.uniqueCodeMap[measurement.code + " " + measurement.name.toUpperCase() + " " + measurement.procurementTO.name] != null) {
						measurement.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate measurement  codes are not allowed', "Warning");
						return;
					}
					measurement.duplicateFlag = false;
				},*/ $scope.saveMeasurements = function(measureForm) {
					
					var flag = false;
					var duplicate = false;
					var measureUnitsMap = [];
					angular.forEach($scope.measureUnits, function(value, key) {
						if (measureUnitsMap[value.code.toUpperCase() + " " + value.name.toUpperCase() + " " + value.procurementTO.name] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							measureUnitsMap[value.code.toUpperCase() + " " + value.name.toUpperCase() + " " + value.procurementTO.name] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.measureUnits, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase() + " " + value.name.toUpperCase() + " " + value.procurementTO.name] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					} 
					if (flag) {
						GenericAlertService.alertMessage('Duplicate measurement  codes are not allowed', "Warning");
						return;
					}
					angular.forEach($scope.measureUnits, function(value, key) { 
						for(var i=0;i<$scope.uniqueCodeMap.length;i++){
							if (value.code == $scope.uniqueCodeMap[i].code) {
								duplicate = true;
							}
						}		
					})
					if(duplicate){
						GenericAlertService.alertMessage('Duplicate measurement  codes are not allowed', "Warning");
						return;
					}	
					var req = {
						"measureUnitTOs" : $scope.measureUnits
					}
					blockUI.start();
					MeasureService.saveMeasurements(req).then(function(data) {
						blockUI.stop();
						var results = data.measureUnitTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Measurement(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Measurement(s) saved successfully',"Info");
						succMsg.then(function(data) {
							$scope.closeThisDialog(measurePopUp);
							var returnPopObj = {
								"measureUnitTOs" : results
							}
							deferred.resolve(returnPopObj);
							$scope.measureSearch();
						})
						
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Measurement(s) is/are failed to save', "Error");

					});
					
				}, $scope.popUpRowSelect = function(measurement) {
					if (measurement.selected) {
						selectedMeasureUnits.push(measurement);
					} else {
						selectedMeasureUnits.splice(selectedMeasureUnits.indexOf(measurement), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedMeasureUnits.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedMeasureUnits.length < $scope.measureUnits.length) {
						angular.forEach(selectedMeasureUnits, function(value, key) {
							$scope.measureUnits.splice($scope.measureUnits.indexOf(value), 1);

						});
						selectedMeasureUnits = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedMeasureUnits.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedMeasureUnits.length == 1) {
						$scope.measureUnits[0] = {
							"code" : '',
							"name" : '',
							"status" : "1",
							"procurementTO" : {
								"id" : null,
								"code" : null,
								"name" : null
							},
							"selected" : false
						};
						selectedMeasureUnits = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
		
		
		/*
		 * $scope.$on('$destroy', function () {
		 * 
		 * editMeasuringUnits = [] ; deferred.resolve(); return
		 * deferred.promise; });
		 */
			} ]
		});
		return deferred.promise;

	}, $scope.activeMeasurements = function() {
		if (editMeasuringUnits.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.measuringUnits = [];
		} else {
			angular.forEach(editMeasuringUnits, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"measureIds" : deleteIds,
				"status" : 1
			};
			MeasureService.deleteMeasurements(req).then(function(data) {
			});
			GenericAlertService.alertMessage('Measurement(s) activated successfully', 'Info');
			angular.forEach(editMeasuringUnits, function(value, key) {
				$scope.measuringUnits.splice($scope.measuringUnits.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage(' Measurement(s) is/are failed to activate', "Error");
			});
			editMeasuringUnits = [];
			$scope.deleteIds = [];

		}
	}
	$scope.deleteMeasurements = function() {
		if (editMeasuringUnits.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.measuringUnits = [];
		} else {
			angular.forEach(editMeasuringUnits, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"measureIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				MeasureService.deleteMeasurements(req).then(function(data) {
					$scope.measureSearch();
				});
				GenericAlertService.alertMessageModal('Measurement(s) deactivated successfully', 'Info');
				angular.forEach(editMeasuringUnits, function(value, key) {
					$scope.measuringUnits.splice($scope.measuringUnits.indexOf(value), 1);
					editMeasuringUnits = [];
					$scope.deleteIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Measurement(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(editMeasuringUnits, function(value) {
					value.selected = false;
				})
			})

		}
	}
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select', width:'5%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate, 
				   displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false },
				{ field: 'code', displayName: "Measurement Code", headerTooltip: "Measurement Code", groupingShowAggregationMenu: false },
				{ field: 'name', displayName: "Measurement Name", headerTooltip: "Measurement Name", groupingShowAggregationMenu: false },				
				{ name: 'status',displayName:'Status',cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status", groupingShowAggregationMenu: false}				
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_Measuring_Units");
			$scope.measureSearch();
			$scope.gridOptions.showColumnFooter = false;
		}
	});
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/measuringunitshelp.html',
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
