'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("employee", {
		url: '/employee',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/empclass/empclass.html',
				controller: 'EmpClassController'
			}
		}
	})
}]).controller("EmpClassController", ["$rootScope", "$scope","uiGridGroupingConstants", "uiGridConstants", "utilservice", "blockUI", "$q", "ngDialog", "EmpService",'stylesService', 'ngGridService', "GenericAlertService", "MeasureService", "generalservice", function ($rootScope, $scope, uiGridGroupingConstants, uiGridConstants, utilservice, blockUI, $q, ngDialog, EmpService,stylesService, ngGridService, GenericAlertService, MeasureService, generalservice) {
	$scope.stylesSvc = stylesService;
	$scope.employees = {};
	var deferred = $q.defer();
	$scope.employeesClassification = [];
	$scope.deletecodes = [];
	$scope.sortType = "code"
	var editemployeesClassification = [];
	$scope.uniqueCodeMap = [];
	$scope.empReq = {
		"empCode": null,
		"empName": null,
		"status": "1"
	};
	$scope.procCategory = generalservice.getprocures;
	// $scope.activeFlag = 0;
	$scope.empSearch = function (click) {
		EmpService.getEmpClasses($scope.empReq).then(function (data) {
			editemployeesClassification = [];
			// $scope.activeFlag = 0;
			$scope.employeesClassification = data.empClassTOs;
			$scope.gridOptions.data = $scope.employeesClassification;
			// var dummyClassificatin = angular.copy($scope.employeesClassification).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{
			// item.status = 'Inactive'}
			// return item;});
			// $scope.gridOptions.data = dummyClassificatin;
			// if(click=='click'){
			// 	if ($scope.employeesClassification.length <= 0) {
			// 		GenericAlertService.alertMessage('Employee Details are not available for given search criteria', "Warning");
			// 		return;
			// 	}
			// }
			// removed code

			// if ($scope.employeesClassification != null && $scope.employeesClassification.length > 0) {
			// 	if ($scope.employeesClassification[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.employeesClassification[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// }
			// else {
			// 	if ($scope.empReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.empReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.empReq = {
			// 		"empCode": null,
			// 		"empName": null,
			// 		"status": "1"
			// 	};
			// 	GenericAlertService.alertMessage('Emplolyee(s) is/are not available for given search criteria', "Warning");
			// }

		});
	};
	$scope.reset = function () {
		$scope.empReq = {
			"empCode": null,
			"empName": null,
			"status": "1"
		}
		// $scope.activeFlag = 0;
		$scope.empSearch();
	};
	$scope.rowSelect = function (employees) {
		if (employees.selected) {
			utilservice.addItemToArray(editemployeesClassification, "id", employees);
		} else {
			editemployeesClassification.splice(editemployeesClassification.indexOf(employees), 1);
		}
	};
	var service = {};
	var empPopUp;
	$scope.employeesCls = function (actionType) {
		angular.forEach(editemployeesClassification, function (value, key) {
			value.selected = false;
		});
		if (editemployeesClassification.length > 0 && actionType === 'Add') {
			editemployeesClassification = [];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;

		}
		empPopUp = service.addEmpClasses(actionType);
		empPopUp.then(function (data) {
			$scope.employeesClassification = data.empClassTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Employee Details", 'Error');
		});
	};
	service.addEmpClasses = function (actionType) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editemployeesClassification <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		empPopUp = ngDialog.open({
			template: 'views/centrallib/empclass/empclasspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $scope,
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.employeesCls = [];
				var selectedEmployees = [];
				var copyEditArray = angular.copy(editemployeesClassification);
				$scope.action = actionType;
				$scope.employeesCls = [];
				if (actionType === 'Add') {
					$scope.employeesCls.push({
						"code": '',
						"name": '',
						"status": 1,
						"selected": false
					});
				}
				else {
					$scope.employeesCls = angular.copy(editemployeesClassification)
					editemployeesClassification = [];

				}
				$scope.addRows = function () {
					$scope.employeesCls.push({
						"code": '',
						"name": '',
						"status": 1,
						"selected": false
					});
				},
					$scope.getMeasuresByProcureType = function () {
						var req = {
							"status": 1
				//			"procureClassName": $scope.procCategory[0].name,
             
						};
						MeasureService.getMeasuresByProcureType(req).then(function (data) {
							$scope.proCategory = data.measureUnitTOs;

						});
					}, $scope.updatePlantCode = function (data, employees) {

						employees.measureId = data.id;
					},
					$scope.getEmpClassMap = function () {
						var req = {

						}
						EmpService.getEmpClassMap(req).then(function (data) {
							$scope.uniqueCodeMap = data.uniqueCodeMap;
						})
					},
					$scope.duplicateCode = function (employee) {
						employee.duplicateFlag = false;
						employee.code = employee.code.toUpperCase();
						if ($scope.uniqueCodeMap[employee.code] != null) {
							employee.duplicateFlag = true;
							GenericAlertService.alertMessage('Duplicate Emp Classfication  codes are not allowed', "Warning");
							return;
						}
						employee.duplicateFlag = false;
					},
					$scope.saveEmpClasses = function (employeesForm) {
						var flag = false;
						var employeeMap = [];
						angular.forEach($scope.employeesCls, function (value, key) {
							if (employeeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							} else {
								value.duplicateFlag = false;
								employeeMap[value.code.toUpperCase()] = true;
							}
						});
						if (actionType === 'Add') {
							angular.forEach($scope.employeesCls, function (value, key) {
								if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
									value.duplicateFlag = true;
									flag = true;
								}
							});
						} else {
							angular.forEach($scope.employeesCls, function (value, key) {
								angular.forEach(copyEditArray, function (value1, key) {
									if (value1.code == value.code) {
										value.duplicateFlag = false;
										flag = false;
									} else {
										if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
											value.duplicateFlag = true;
											flag = true;
										} else {
											value.duplicateFlag = false;
											employeeMap[value.code.toUpperCase()] = true;
										}
									}
								});
							});
						}
						if (flag) {
							GenericAlertService.alertMessage('Duplicate Emp Classfication  codes are not allowed', "Warning");
							return;
						}
						var req = {
							"empClassTOs": $scope.employeesCls
						}
						var results = [];
						blockUI.start();
						EmpService.saveEmpClasses(req).then(function (data) {
							blockUI.stop();
							results = data.empClassTOs;
							$scope.closeThisDialog(empPopUp);
							var returnPopObj = {
								"empClassTOs": results
							}
							// GenericAlertService.alertMessage("Employee Classification(s) is/are Saved/Updated Successfully", "Info");
							GenericAlertService.alertMessage("Employee Classification(s) saved successfully", "Info");
							$scope.closeThisDialog();
							deferred.resolve(returnPopObj);
							$scope.empSearch();
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Employee(s) is/are failed to save', "Error");
						});
					},


					/*		$scope.$on('$destroy', function () {
								editemployeesClassification =  [] ;
									deferred.resolve();
									return deferred.promise;
								});*/

					$scope.popUpRowSelect = function (employees) {
						if (employees.selected) {
							selectedEmployees.push(employees);
						} else {
							selectedEmployees.splice(selectedEmployees.indexOf(employees), 1);
						}
					}, $scope.deleteRows = function () {
						if (selectedEmployees.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						}
						else if (selectedEmployees.length < $scope.employeesCls.length) {
							angular.forEach(selectedEmployees, function (value, key) {
								$scope.employeesCls.splice($scope.employeesCls.indexOf(value), 1);

							});
							selectedEmployees = [];
							GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
						} else if (selectedEmployees.length > 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						} else if (selectedEmployees.length == 1) {
							$scope.employeesCls[0] = {
								"code": '',
								"name": '',
								"status": 1,
								"selected": false
							};
							selectedEmployees = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					}
			}]
		});
		return deferred.promise;

	}
	$scope.activeEmpClasses = function () {
		if (editemployeesClassification.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.employeesClassification = [];
		} else {
			angular.forEach(editemployeesClassification, function (value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"empClassIds": deleteIds,
				"status": 1
			};
			GenericAlertService.confirmMessageModal('Do you really want to Activate the record', 'Warning', 'YES', 'NO').then(function() {
			EmpService.deleteEmpClasses(req).then(function (data) { });
			GenericAlertService.alertMessage('Employee(s) activated successfully', 'Info');
			angular.forEach(editemployeesClassification, function (value, key) {
				$scope.employeesClassification.splice($scope.employeesClassification.indexOf(value), 1);
			},
				function (error) {
					GenericAlertService.alertMessage('Employee(s) is/are failed to activate', "Error");
				});
			editemployeesClassification = [];
			$scope.deleteIds = [];
			});
		}
	}
	$scope.deleteEmpClasses = function () {
		if (editemployeesClassification.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.employeesClassification = [];
		} else {
			angular.forEach(editemployeesClassification, function (value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"empClassIds": deleteIds,
				"status": 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				EmpService.deleteEmpClasses(req).then(function (data) {
					$scope.empSearch();
				});
				GenericAlertService.alertMessage('Employee(s) deactivated successfully', 'Info');
				angular.forEach(editemployeesClassification, function (value, key) {
					$scope.employeesClassification.splice($scope.employeesClassification.indexOf(value), 1);
					editemployeesClassification = [];
					$scope.deleteIds = [];
				},
					function (error) {
						GenericAlertService.alertMessage('Employee(s) is/are  failed to deactivate', "Error");
					});
			}, function (data) {
				angular.forEach(editemployeesClassification, function (value) {
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
				{ field: 'code', displayName: "Employee Classification Code", headerTooltip: "Employee Classification Code", groupingShowAggregationMenu: false },
				{ field: 'name', displayName: "Employee Classification Name", headerTooltip: "Employee Classification Name", groupingShowAggregationMenu: false },	
				{ field: 'measureUnitTO.name', displayName: "Unit of Measure", headerTooltip: "Unit of Measure", groupingShowAggregationMenu: false },				
				{ name: 'status',displayName:'Status',cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status", }				
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_Central_Employee_Classification");
			$scope.empSearch();
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
			template: 'views/help&tutorials/empclassificationhelp.html',
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
