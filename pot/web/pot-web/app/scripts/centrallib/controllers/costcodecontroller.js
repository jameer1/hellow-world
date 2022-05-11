'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costcode", {
		url: '/costcode',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/costcode/centlibcostcode.html',
				controller: 'CostCodeController'
			}
		}
	})
}]).controller("CostCodeController", ["$scope", "$state", "$q", "ngDialog", "blockUI", "CostCodeService", "GenericAlertService", "utilservice","stylesService", "ngGridService", function ($scope, $state, $q, ngDialog, blockUI, CostCodeService, GenericAlertService, utilservice, stylesService, ngGridService) {
	var service = {}
	$scope.stylesSvc = stylesService;
	$scope.costCodes = {};
	$scope.costCodesClass = [];
	$scope.deletecodes = [];
	$scope.sortType = "code"
	var deferred = $q.defer();
	var editcostCodesClass = [];
	$scope.uniqueCodeMap = [];
	$scope.costCodeReq = {
		"costCodeCode": null,
		"costCodeName": null,
		"status": "1"
	},
		// $scope.activeFlag = 0;
	$scope.costcodeSearch = function (click) {
		CostCodeService.getCostCodes($scope.costCodeReq).then(function (data) {
			editcostCodesClass = [];
			// $scope.activeFlag = 0;
			$scope.costCodesClass = data.costCodeTOs;
			// var copyCostCodeClass =  angular.copy($scope.costCodesClass).map((item) => {
			// 						if(item.status == 1 ) {
			// 						item.status = 'Active'}
			// 						else {
			// 						item.status = 'Inactive';
			// 						}
			// 						return item;
			// 						});
			// console.log('entire data is ', copyCostCodeClass);
			$scope.gridOptions.data = $scope.costCodesClass;
			if(click=='click'){
				if ($scope.costCodesClass.length <= 0) {
					GenericAlertService.alertMessage('Cost Code Details are not available for given search criteria', "Warning");
					return;
				}
			}
			// removed code

			// if ($scope.costCodesClass != null
			// 	&& $scope.costCodesClass.length > 0) {
			// 	if ($scope.costCodesClass[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.costCodesClass[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.costCodeReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.costCodeReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.costCodeReq = {
			// 		"costCodeCode" : null,
			// 		"costCodeName" : null,
			// 		"status" : "1"
			// 	};
			// }
		});
	},

		$scope.reset = function () {
			$scope.costCodeReq = {
				"costCodeCode": null,
				"costCodeName": null,
				"status": "1"
			}
			// $scope.activeFlag = 0;
			$scope.costcodeSearch();
		},
		$scope.rowSelect = function (costCodes) {
			if (costCodes.selected) {
				utilservice.addItemToArray(editcostCodesClass, "id", costCodes);
			} else {
				editcostCodesClass.splice(editcostCodesClass.indexOf(costCodes), 1);
			}
		}
	var service = {};
	var costCodePopUp;

	$scope.costUnits = function (actionType) {
		angular.forEach(editcostCodesClass, function (value, key) {
			value.selected = false;
		});

		if (editcostCodesClass.length > 0 && actionType == "Add") {
			editcostCodesClass = [];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		costCodePopUp = service.addCostUnits(actionType);
		costCodePopUp.then(function (data) {
			$scope.costCodesClass = data.costCodeTOs;
			$scope.reset();
		},
			function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Cost Code details", 'Error');
			});
	}
	service.addCostUnits = function (actionType) {
		var deferred = $q.defer();
		if (actionType === 'Edit' && editcostCodesClass <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		costCodePopUp = ngDialog.open({
			template: 'views/centrallib/costcode/costcodepopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $scope,
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.costUnits = [];
				var selectedCostCodes = [];
				$scope.action = actionType;
				$scope.costUnits = [];
				var copyEditArray = angular.copy(editcostCodesClass);
				if (actionType === 'Add') {
					$scope.costUnits.push({
						"code": '',
						"name": '',
						"status": 1,
						"selected": false
					});
				} else {
					$scope.costUnits = angular.copy(editcostCodesClass)
					editcostCodesClass = [];
				}
				$scope.addRows = function () {
					$scope.costUnits.push({
						"code": '',
						"name": '',
						"status": 1,
						"selected": false
					});
				},
					$scope.getCentLibCostCodeMap = function () {
						var req = {

						}
						CostCodeService.getCostCodeClassMap(req).then(function (data) {
							$scope.uniqueCodeMap = data.uniqueCodeMap;
						})
					}, $scope.checkDuplicate = function (costCode) {
						costCode.duplicateFlag = false;
						costCode.code = costCode.code.toUpperCase();
						if ($scope.uniqueCodeMap[costCode.code] != null) {
							costCode.duplicateFlag = true;
							GenericAlertService.alertMessage('Duplicate Cost codes are not allowed', "Warning");
							return;
						}
						costCode.duplicateFlag = false;
					},
					$scope.saveCostCodes = function (codeForm) {
						var flag = false;
						var costCodeMap = [];
						angular.forEach($scope.costUnits, function (value, key) {
							if (costCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							} else {
								value.duplicateFlag = false;
								costCodeMap[value.code.toUpperCase()] = true;
							}
						});
						if (actionType === 'Add') {
							angular.forEach($scope.costUnits, function (value, key) {
								if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
									value.duplicateFlag = true;
									flag = true;
								}
							});
						} else {
							angular.forEach($scope.costUnits, function (value, key) {
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
											costCodeMap[value.code.toUpperCase()] = true;
										}
									}
								});
							});
						}
						if (flag) {
							GenericAlertService.alertMessage('Duplicate Cost codes are not allowed', "Warning");
							return;
						}

						var req = {
							"costCodeTOs": $scope.costUnits
						}
						var results = [];
						blockUI.start();
						CostCodeService.saveCostCodes(req).then(function (data) {
							blockUI.stop();
							results = data.costCodeTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Cost code(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Cost code(s) saved successfully ',"Info");
							succMsg.then(function (data1) {
								$scope.closeThisDialog(costCodePopUp);
								var returnPopObj = {
									"costCodeTOs": results
								}
								deferred.resolve(returnPopObj);
							})
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Cost code(s) is/are failed to save', "Error");
						});
					},
					$scope.popUpRowSelect = function (costCodes) {
						if (costCodes.selected) {
							selectedCostCodes.push(costCodes);
						} else {
							selectedCostCodes.splice(selectedCostCodes.indexOf(costCodes), 1);
						}
					}, $scope.deleteRows = function () {
						if (selectedCostCodes.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						} else if (selectedCostCodes.length < $scope.costUnits.length) {
							angular.forEach(selectedCostCodes, function (value, key) {
								$scope.costUnits.splice($scope.costUnits.indexOf(value), 1);

							});
							selectedCostCodes = [];
							GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
						} else if (selectedCostCodes.length > 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						} else if (selectedCostCodes.length == 1) {
							$scope.costUnits[0] = {
								"code": '',
								"name": '',
								"status": 1,
								"selected": false
							};
							selectedCostCodes = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					}
			}]
		});
		return deferred.promise;
	},
		$scope.activeCostCodes = function () {
			if (editcostCodesClass.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
				return;
			}
			var deleteIds = [];
			$scope.nondeleteIds = [];
			if ($scope.selectedAll) {
				$scope.costCodesClass = [];
			} else {
				angular.forEach(editcostCodesClass, function (value, key) {
					deleteIds.push(value.id);
				});
				var req = {
					"costCodeIds": deleteIds,
					"status": 1
				};
				GenericAlertService.confirmMessageModal('Do you really want to Activate the record', 'Warning', 'YES', 'NO').then(function() {
				CostCodeService.deleteCostCodes(req).then(function (data) { });
				angular.forEach(editcostCodesClass, function (value, key) {
					GenericAlertService.alertMessage('Cost code(s) activated successfully', 'Info');
					$scope.costCodesClass.splice($scope.costCodesClass.indexOf(value), 1);
				},
					function (error) {
						GenericAlertService.alertMessage('Cost code(s) is/are failed to activate', "Error");
					});
					
				editcostCodesClass = [];
				$scope.deleteIds = [];
				});
			}
			
		}
	$scope.deleteCostCodes = function () {
		if (editcostCodesClass.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.costCodesClass = [];
		} else {
			angular.forEach(editcostCodesClass, function (value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"costCodeIds": deleteIds,
				"status": 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				CostCodeService.deleteCostCodes(req).then(function (data) {
				$scope.costcodeSearch();
				 });
				GenericAlertService.alertMessage('Cost code(s) deactivated successfully', 'Info');
				angular.forEach(editcostCodesClass, function (value, key) {
					$scope.costCodesClass.splice($scope.costCodesClass.indexOf(value), 1);
					editcostCodesClass = [];
					$scope.deleteIds = [];
				},
					function (error) {
						GenericAlertService.alertMessage('Cost code(s) is/are failed to deactivate', "Error");
					});
			}, function (data) {
				angular.forEach(editcostCodesClass, function (value) {
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
						{ field: 'code', displayName: "Cost Code ID", headerTooltip: "Cost Code ID"},
						{ field: 'name', displayName: "Cost Code Name", headerTooltip: "Cost Code Name", },
						{ field: 'status',displayName: "Status",cellFilter: 'potstatusfilter:costCodes.status', headerTooltip: "Status"}
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_CostCode");
					$scope.costcodeSearch();
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
			template: 'views/help&tutorials/costcodeclassficationhelp.html',
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