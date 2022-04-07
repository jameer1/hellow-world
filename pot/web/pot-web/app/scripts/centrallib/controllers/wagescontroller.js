'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empwage", {
		url : '/empwage',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/employeewages/wages.html',
				controller : 'WageController'
			}
		}
	})
}]).controller('WageController', ["$scope", "$q", "utilservice", "ngDialog", "blockUI", "WageService", "GenericAlertService","stylesService", "ngGridService", function($scope, $q, utilservice, ngDialog,blockUI, WageService, GenericAlertService, stylesService, ngGridService) {
	var editWages = [];
	$scope.wage = [];
	$scope.stylesSvc = stylesService;
	$scope.sortType = "code"
	var deferred = $q.defer();
	$scope.deleteIds = [];
	$scope.uniqueCodeMap = [];
	$scope.wagesReq = {
		"empWageCode" : null,
		"empWageName" : null,
		"status" : "1"
	}, $scope.wagesSearch = function(click) {
		WageService.getEmpWages($scope.wagesReq).then(function(data) {
			editWages = [];
			// $scope.activeFlag = 0;
			$scope.wages = data.employeeWageRateTOs;
			// var dummyData = angular.copy($scope.wages).map((item) => {
			// if(item.status == 1 ) {
			// 				item.status = 'Active'}
			// 				else {
			// 				item.status = 'Inactive';
			// 			  }
			// 			return item;
			// 			});
			$scope.gridOptions.data = $scope.wages;
			if(click=='click'){
				if ($scope.wages.length <= 0) {
					GenericAlertService.alertMessage('Employee Wage Details are not available for given search criteria', "Warning");
					return;
				}
			}
			// removed code

			// if ($scope.wages != null && $scope.wages.length > 0) {
			// 	if ($scope.wages[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.wages[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.wagesReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.wagesReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.wagesReq = {
			// 		"empWageCode" : null,
			// 		"empWageName" : null,
			// 		"status" : "1"
			// 	}
			// }
		});
	}, $scope.reset = function() {
		$scope.wagesReq = {
			"empWageCode" : null,
			"empWageName" : null,
			"status" : "1"
		}
		// $scope.activeFlag = 0;
		$scope.wagesSearch();
	},

	$scope.rowSelect = function(wage) {
		if (wage.selected) {
			utilservice.addItemToArray(editWages, "wageRateId", wage);
		} else {
			editWages.splice(editWages.indexOf(wage), 1);
		}
	}
	var service = {};
	var wagesPopUp;
	$scope.addWages = function(actionType) {
		angular.forEach(editWages,function(value,key){
			value.selected=false;
				});
		if(editWages.length >0 && actionType=="Add"){
			editWages=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
		return;
		}
		wagesPopUp = service.addEmpWages(actionType);
		wagesPopUp.then(function(data) {
			$scope.wages = data.employeeWageRateTOs;
			$scope.reset();
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting wages details", 'Error');
		});
	}
	service.addEmpWages = function(actionType) {
		var deferred = $q.defer();
		if (actionType === 'Edit' && editWages.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		wagesPopUp = ngDialog.open({
			template : 'views/centrallib/employeewages/addwagespopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			scope : $scope,
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedWages = [];
				$scope.addWages = [];
				var selectedWages = [];
				var copyEditArray = angular.copy(editWages);
				if (actionType === 'Add') {
					$scope.addWages.push({
						"code" : '',
						"name" : '',
						"wageFactor" : '',
						"status" : 1,
						"selected" : false

					});
				} else {
					$scope.addWages = angular.copy(editWages);
					editWages = [];
				}
				$scope.addRows = function() {
					$scope.addWages.push({
						"code" : '',
						"name" : '',
						"wageFactor" : '',
						"status" : 1,
						"selected" : false
					});
				}, $scope.getEmpWageFactorMap = function() {
					var req = {

					}
					WageService.getEmpWageFactorMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				}, $scope.checkDuplicate = function(wage) {
					wage.duplicateFlag = false;
					wage.code = wage.code.toUpperCase();
					if ($scope.uniqueCodeMap[wage.code] != null) {
						wage.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate Wage Factor codes are not allowed', "Warning");
						return;
					}
					wage.duplicateFlag = false;
				}, $scope.saveEmpWages = function(wageForm) {
					var flag = false;
					var empWageMap = [];
					angular.forEach($scope.addWages, function(value, key) {
					  if(key===0){
              if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
                value.duplicateFlag = true;
                flag = true;
              }
            }
						if (empWageMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							empWageMap[value.code.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.addWages, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					} else {
					angular.forEach($scope.addWages, function (value, key) {
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
									empWageMap[value.code.toUpperCase()] = true;
								}
							}
						});
					});
				}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Wage Factor codes are not allowed', "Warning");
						return;
					}
					var req = {
						"employeeWageRateTOs" : $scope.addWages
					}
					var results = [];
					blockUI.start();
					WageService.saveEmpWages(req).then(function(data) {
						blockUI.stop();
						results = data.employeeWageRateTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Employee Wage Factor(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Employee Wage Factor(s) saved successfully ',"Info");
						succMsg.then(function(data1) {
							$scope.closeThisDialog(wagesPopUp);
							var returnPopObj = {
								"employeeWageRateTOs" : results
							}
							deferred.resolve(returnPopObj);
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Employee Wage Factors is/are failed to save', "Error");
					});

				}, $scope.popUpRowSelect = function(wage) {
					if (wage.selected) {
						selectedWages.push(wage);
					} else {
						selectedWages.splice(selectedWages.indexOf(wage), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedWages.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedWages.length < $scope.addWages.length) {
						angular.forEach(selectedWages, function(value, key) {
							$scope.addWages.splice($scope.addWages.indexOf(value), 1);

						});
						selectedWages = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedWages.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedWages.length == 1) {
						$scope.addWages[0] = {
							"code" : '',
							"name" : '',
							"wageFactor" : '',
							"status" : 1,
							"selected" : false
						};
						selectedWages = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			} ]
		});
		return deferred.promise;
	}

	$scope.activeEmpWages = function() {
		if (editWages.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to activate', "Warning");
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.wages = [];
		} else {
			angular.forEach(editWages, function(value, key) {
				deleteIds.push(value.wageRateId);
			});
			var req = {
				"empWageIds" : deleteIds,
				"status" : 1
			};
			WageService.deleteEmpWages(req).then(function(data) {
			});
			GenericAlertService.alertMessage('Employee Wage Factor(s) activated successfully', 'Info');
			angular.forEach(editWages, function(value, key) {
				$scope.wages.splice($scope.wages.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Employee Wage Factor(s) is/are failed to activate', "Error");
			});
			editWages = [];
			$scope.deleteIds = [];

		}
	}
	$scope.deleteEmpWages = function() {
		if (editWages.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to deactivate', "Warning");
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.wages = [];
		} else {
			angular.forEach(editWages, function(value, key) {
				deleteIds.push(value.wageRateId);
			});
			var req = {
				"empWageIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				WageService.deleteEmpWages(req).then(function(data) {
				$scope.wagesSearch();
				});
				GenericAlertService.alertMessage('Employee Wage Factor(s) deactivated successfully', 'Info');
				angular.forEach(editWages, function(value, key) {
					$scope.wages.splice($scope.wages.indexOf(value), 1);
					editWages = [];
					$scope.deleteIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Employee Wage Factor(s) is/are failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(editWages, function(value) {
					value.selected = false;
				})
			})

		}
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'code', displayName: "Wage Factor Code", headerTooltip: "Wage Factor Code"},
						{ field: 'name', displayName: "Wage Factor Name", headerTooltip: "Wage Factor Name", },
						{ field: 'wageFactor', displayName: "Wage Factor", headerTooltip: "Wage Factor", },
						{ field: 'status',displayName: "Status", cellFilter: 'potstatusfilter: wage.status', headerTooltip: "Status",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_Wages");
					$scope.wagesSearch();
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
			template: 'views/help&tutorials/empwagefactorhelp.html',
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
