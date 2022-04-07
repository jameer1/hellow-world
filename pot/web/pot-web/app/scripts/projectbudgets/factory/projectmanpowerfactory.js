'use strict';

app.factory('ProjManPowerFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjectBudgetService", "MeasureService", "ProjEmpClassService", "GenericAlertService", "generalservice", "ProjectStatusService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI,
	ProjectBudgetService, MeasureService, ProjEmpClassService, GenericAlertService, generalservice, ProjectStatusService) {
	var projManPowerPopup;
	var service = {};
	service.manPowerPopupDetails = function (actionType, projId, editManpower) {
		var deferred = $q.defer();
		projManPowerPopup = ngDialog.open({
			/*
			 * preCloseCallback: function() { editManpower = []; },
			 */
			template: 'views/projectbudgets/manpowerpopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			controller: ['$scope', function ($scope) {
				$scope.action = actionType;
				var selectedManpower = [];
				$scope.manpowerList = [];
				var copyEditArray = angular.copy(editManpower);
				$scope.isPrimaveraIntegrationEnabled = 'Yes';
				ProjectStatusService.getProjGenerals({"projId": $scope.projId, "status": 1}).then(function(data){
					$scope.isPrimaveraIntegrationEnabled = data.projGeneralMstrTO.primaveraIntegration;
				}, function (error) {
					cosole.log(error)
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
				
				if (actionType === 'Add') {
					$scope.manpowerList.push({
						'selected': false,
						'projId': $scope.projId,
						'originalQty': null,
						'revisedQty': null,
						'actualQty': null,
						'remainingQty': null,
						"empClassTO": {
							id: null,
							code: null,
							name: null
						},
						"projEmpCatgTO": {
							id: null,
							code: null,
							name: null
						},
						"measureUnitTO": null,
						'estimateComplete': null,
						'estimateCompletion': null,
						'startDate': null,
						'finishDate': null,
						'status': 1
					});
				} else {
					$scope.manpowerList = angular.copy(editManpower);
					editManpower = [];
				}

				$scope.calcRemainingUnits = function (budgetObj) {
					if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0) {

						budgetObj.remainingQty = budgetObj.revisedQty - budgetObj.actualQty;
						return budgetObj.remainingQty;
					} else {
						budgetObj.remainingQty = budgetObj.originalQty - budgetObj.actualQty;
						return budgetObj.remainingQty;
					}
				}
				var manPowerService = {};
				$scope.manPowerDetails = function (tab) {
					var projPowerPopup = manPowerService.getManPowerDetails($scope.projId);
					projPowerPopup.then(function (data) {
						tab.empClassTO = data.empClassTO;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting project employee class Details", 'Error');
					});
				}

				manPowerService.getManPowerDetails = function (projId) {
					var deferred = $q.defer();
					ngDialog.open({
						template: 'views/projectbudgets/tradelist.html',
						closeByDocument: false,
						showClose: false,
						className: 'ngdialog-theme-plain ng-dialogueCustom4',
						controller: ['$scope', function ($scope) {
							$scope.employeesClassification = [];
							$scope.getEmpClasses = function () {
								var req = {
									"projId": projId
								}
								ProjectBudgetService.getEmpClasses(req).then(function (data) {
									$scope.employeesClassification = data.empClassTOs;
								});
							},
								$scope.projEmpClassPopUp = function (empClassTO) {
									var returnProjEmpClassTO = {
										"empClassTO": empClassTO
									};
									deferred.resolve(returnProjEmpClassTO);
									$scope.closeThisDialog();

								}
						}]
					});
					return deferred.promise;
				}
				$scope.addRows = function () {

					$scope.manpowerList.push({
						'selected': false,
						'projId': $scope.projId,
						'originalQty': null,
						'revisedQty': null,
						'actualQty': null,
						'remainingQty': null,
						"empClassTO": {
							id: null,
							code: null,
							name: null
						},
						"projEmpCatgTO": {
							id: null,
							code: null,
							name: null
						},
						"measureUnitTO": {
							id: null,
							code: null,
							name: null
						},
						'estimateComplete': null,
						'estimateCompletion': null,
						'startDate': null,
						'finishDate': null,
						'status': 1
					});
				},
					$scope.proCategory = [];
				$scope.getProjEmpTyFpes = function () {
					$scope.empCategoryList = generalservice.employeeCategory;
				},
					$scope.updateCategory = function (data, manpower) {
						manpower.empCatgId = data.id;
					},

					$scope.getMeasuresByProcureType = function () {
						var req = {
							"status": 1
							//"procureClassName": "Man Power"
						};
						MeasureService.getMeasuresByProcureType(req).then(function (data) {
							$scope.proCategory = data.measureUnitTOs;

						});
					}, $scope.updatePlantCode = function (data, manpower) {
						manpower.measureId = data.id;
					},
					$scope.getProjManpowerMap = function (manpower) {
						var req = {
							"projId": $rootScope.projId,
							"status": 1
						};
						ProjectBudgetService.getProjManpowerMap(req).then(function (data) {
							$scope.manpowerMap = data.projSettingsUniqueMap;
						}, function (error) {
							GenericAlertService.alertMessage("Error occured while selecting Manpower ActualHours", 'Error');
						});
					}

				$scope.checkDuplicate = function (manpower) {

					manpower.duplicateFlag = false;
					if ($scope.manpowerMap[manpower.empClassTO.id + " " + manpower.projEmpCatgTO.id] != null) {
						manpower.duplicateFlag = true;
						GenericAlertService.alertMessage('Duplicate Resources  are not allowed', "Warning");
						return;
					}
					manpower.duplicateFlag = false;
				},

					$scope.saveManpower = function () {
						var flag = false;
						if (actionType === 'Add') {
							angular.forEach($scope.manpowerList, function (value, key) {
								if ($scope.manpowerMap[value.empClassTO.id + " " + value.projEmpCatgTO.id] != null) {
									value.duplicateFlag = true;
									flag = true;
								}
							});
						} else {
							angular.forEach($scope.manpowerList, function (value, key) {
								angular.forEach(copyEditArray, function (value1, key) {
									if (value1.empClassTO.id == value.empClassTO.id) {
										value.duplicateFlag = false;
										flag = false;
									} else {
										if ($scope.manpowerMap[value.empClassTO.id + " " + value.projEmpCategory] != null) {
											value.duplicateFlag = true;
											flag = true;
										} else {
											value.duplicateFlag = false;
											$scope.manpowerMap[value.empClassTO.id] = true;
											$scope.manpowerMap[value.projEmpCatgTO.id] = true;
										}
									}
								});
							});
						}
						if (flag) {
							GenericAlertService.alertMessage('Duplicate Resources codes are not allowed', "Warning");
							return;
						}
						angular.forEach($scope.manpowerList, function (value) {
							if (value.empClassTO.code == null || value.empClassTO.code == undefined || value.empClassTO.name == null || value.empClassTO.name == undefined) {
								GenericAlertService.alertMessage('Please select ResourceId  ', 'Warning');
								forEach.break();
								return;
							}
						})

						angular.forEach($scope.manpowerList, function (value) {
							if ($scope.isPrimaveraIntegrationEnabled=='No') {
								if (value.startDate == null || value.startDate == undefined || value.finishDate == null || value.finishDate == undefined) {
									GenericAlertService.alertMessage('Date fields can not be empty  ', 'Warning');
									forEach.break();
									return;
								}
								var startDate = new Date(value.startDate);
								var finishDate = new Date(value.finishDate);
								if (startDate > finishDate) {
									GenericAlertService.alertMessage('Start Date must be less than Finish Date', 'Warning');
									forEach.break();
									return;
								}
							}
						})
						var req = {
							"projManpowerTOs": $scope.manpowerList,
							"projId": $scope.projId
						}
						var results = [];
						blockUI.start();
						ProjectBudgetService.saveProjManpowers(req).then(function (data) {
							blockUI.stop();
							results = data.projManpowerTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Manpower(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Manpower saved successfully ',"Info");
							succMsg.then(function (data) {
								$scope.closeThisDialog();
								var returnPopObj = {
									"projManpowerTOs": results
								}
								deferred.resolve(returnPopObj);
							})
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage('Manpower(s) is/are failed to save', "Error");
						});

					},

					$scope.manpowerPopUpRowSelect = function (manpower) {
						if (manpower.selected) {
							selectedManpower.push(manpower);
						} else {
							selectedManpower.splice(selectedManpower.indexOf(manpower), 1);
						}
					}, $scope.deleteProjRows = function () {
						if (selectedManpower.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						}
						if (selectedManpower.length < $scope.manpowerList.length) {
							angular.forEach(selectedManpower, function (value, key) {
								$scope.manpowerList.splice($scope.manpowerList.indexOf(value), 1);
								GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
							});
							selectedManpower = [];
						} else if (selectedManpower.length > 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
						else if (selectedManpower.length == 1) {
							$scope.manpowerList[0] = {
								'projId': $scope.projId,
								'originalQty': '',
								'revisedQty': '',
								'actualQty': '',
								'remainingQty': '',
								'estimateComplete': '',
								'estimateCompletion': '',
								'startDate': '',
								'finishDate': '',
								'status': 1
							};
							selectedManpower = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					}
			}]

		});
		return deferred.promise;
	}
	return service;
}]);
