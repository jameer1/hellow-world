'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("serviceclass", {
		url: '/serviceclass',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/serviceclass/serviceclass.html',
				controller: 'ServiceClassController'
			}
		}
	})
}]).controller("ServiceClassController", ["$scope", "$q", "utilservice", "ngDialog", "blockUI", "ClassificationService", "GenericAlertService","ProcureService","stylesService", "ngGridService", function ($scope, $q, utilservice, ngDialog, blockUI, ClassificationService, GenericAlertService,ProcureService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.classification = {};
	$scope.serviceclassifications = [];
	var deferred = $q.defer();
	$scope.sortType = "code"
	var selectedServiceClass = [];
	$scope.deletecodes = $scope.uniqueCodeMap = [];
	$scope.serviceReq = {
		"serviceCode": null,
		"serviceName": null,
		"status": "1"
	};
	
	$scope.procSubCatReq = {
			"procureId" : 'Services',
			"procureClassName": "Services",
			"subProcureName" : null,
			"status" : "1"
		};
	var editClassifications = [];
	// $scope.activeFlag = 0;
	$scope.serviceClsSearch = function (click) {
		ClassificationService.getServiceClasses($scope.serviceReq).then(function (data) {
			editClassifications = [];
			// $scope.activeFlag = 0;
			$scope.serviceclassifications = data.serviceClassTOs;
			// var dummyData = angular.copy($scope.serviceclassifications).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{item.status = 'Inactive'}
			// return item;
			// });
			$scope.gridOptions.data = $scope.serviceclassifications;
			if(click=='click'){
				if ($scope.serviceclassifications.length <= 0) {
					GenericAlertService.alertMessage('Service Classification Details are not available for given search criteria', "Warning");
					return;
				}
			}
			// if ($scope.serviceclassifications != null && $scope.serviceclassifications.length > 0) {
			// 	if ($scope.serviceclassifications[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.serviceclassifications[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.serviceReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.serviceReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.serviceReq = {
			// 		"serviceCode" : null,
			// 		"serviceName" : null,
			// 		"status" : "1"
			// 	}
			// }
		});
	},

		$scope.reset = function () {
			$scope.serviceReq = {
				"serviceCode": null,
				"serviceName": null,
				"status": "1",
				"procSubCatCode": "1"
			}
			// $scope.activeFlag = 0;
			$scope.serviceClsSearch();
		}, $scope.rowSelect = function (classification) {
			if (classification.selected) {
				utilservice.addItemToArray(editClassifications, "id", classification);
			} else {
				editClassifications.splice(editClassifications.indexOf(classification), 1);

			}
		}
	var service = {};
	var servicePopUp;
	$scope.addClassService = function (actionType) {
		ProcureService.getProcureCatgs($scope.procSubCatReq).then(function(data) {
			$scope.categories = data.procureMentCatgTOs;
		});
		
		
		angular.forEach(editClassifications, function (value, key) {
			value.selected = false;
		});
		if (editClassifications.length > 0 && actionType == "Add") {
			editClassifications = [];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		servicePopUp = service.serviceClass(actionType);
		servicePopUp.then(function (data) {
			$scope.serviceclassifications = data.serviceClassTOs;
			$scope.reset ();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting service details", 'Error');
		});
	}
	service.serviceClass = function (actionType) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editClassifications <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		servicePopUp = ngDialog.open({
			template: 'views/centrallib/serviceclass/servicePopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			scope: $scope,
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.action = actionType;
				$scope.classService = [];
				var copyEditArray = angular.copy(editClassifications);
				if (actionType === 'Add') {
					$scope.classService.push({
						"procurmentSubCategory":'',
						"code": '',
						"name": '',
						"status": 1,
						"selected": false
					});
				} else {
					$scope.classService = angular.copy(editClassifications);
					editClassifications = [];
				}
				$scope.addRows = function () {
					$scope.classService.push({
						"code": '',
						"name": '',
						"status": 1,
						"selected": false,
						"procurmentSubCategory":'',
					});
				}, $scope.getServiceClassMap = function () {
					var req = {
					
					}
					ClassificationService.getServiceClassMap(req).then(function (data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				}, $scope.checkDuplicate = function (service) {
					service.duplicateFlag = false;
					service.code = service.code.toUpperCase();
					if ($scope.uniqueCodeMap[service.code] != null) {
						//GenericAlertService.alertMessage('Duplicate Service Classfication codes are not allowed', "Warning");
						service.duplicateFlag = true;
						return;
					}
					service.duplicateFlag = false;
				}, $scope.saveServiceClasses = function (classificationForm) {
					var flag = false;
					var serviceClassMap = [];
					angular.forEach($scope.classService, function (value, key) {
						if (serviceClassMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							serviceClassMap[value.code.toUpperCase()] = true;
							if(value.procurmentSubCategory) {
								value.procSubCatCode = value.procurmentSubCategory.code;
								value.procSubCatName = value.procurmentSubCategory.desc;
								value.proCatgId = value.procurmentSubCategory.proCatgId;
							}
						}
						
					});
					if (actionType === 'Add') {
						angular.forEach($scope.classService, function (value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					}

					if (flag) {
						GenericAlertService.alertMessage('Duplicate Service Classfication codes are not allowed', "Warning");
						return;
					}
					console.log('saveclassification form ===>  ',  $scope.classService);
					
					var req = {
						"serviceClassTOs": $scope.classService
					}
					var results = [];
					blockUI.start();
					
					ClassificationService.saveServiceClasses(req).then(function (data) {
						console.log('req  ', req);
						blockUI.stop();
						results = data.serviceClassTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Service Classification(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Service Classification(s) saved successfully ',"Info");
						succMsg.then(function (data1) {
							$scope.closeThisDialog(servicePopUp);
							var returnPopObj = {
								"serviceClassTOs": results
							}
							deferred.resolve(returnPopObj);
						})
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Service Classification(s)  is/are failed to save', "Error");
					});
				}, $scope.popUpRowSelect = function (classification) {
					if (classification.selected) {
						selectedServiceClass.push(classification);
					} else {
						selectedServiceClass.splice(selectedServiceClass.indexOf(classification), 1);
					}
				}, $scope.deleteRows = function () {
					if (selectedServiceClass.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedServiceClass.length < $scope.classService.length) {
						angular.forEach(selectedServiceClass, function (value, key) {
							$scope.classService.splice($scope.classService.indexOf(value), 1);

						});
						selectedServiceClass = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedServiceClass.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedServiceClass.length == 1) {
						$scope.classService[0] = {
							"code": '',
							"name": '',
							"status": 1,
							"selected": false
						};
						selectedServiceClass = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			}]
		});
		return deferred.promise;
	}, $scope.activeServiceClasses = function () {
		if (editClassifications.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.serviceclassifications = [];
		} else {
			angular.forEach(editClassifications, function (value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"serviceClassIds": deleteIds,
				"status": 1
			};
			ClassificationService.deleteServiceClasses(req).then(function (data) {
			});
			angular.forEach(editClassifications, function (value, key) {
				$scope.serviceclassifications.splice($scope.serviceclassifications.indexOf(value), 1);
				GenericAlertService.alertMessage('Service Classification(s) activated successfully', 'Info');
			}, function (error) {
				GenericAlertService.alertMessage('Service Classification(s) is/are failed to activate', "Error");
			});
			editClassifications = [];
			$scope.deleteIds = [];

		}
	}
	$scope.deleteServiceClasses = function () {
		if (editClassifications.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.serviceclassifications = [];
		} else {
			angular.forEach(editClassifications, function (value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"serviceClassIds": deleteIds,
				"status": 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				ClassificationService.deleteServiceClasses(req).then(function (data) {
					$scope.serviceClsSearch();
				});
				angular.forEach(editClassifications, function (value, key) {

					GenericAlertService.alertMessage('Service Classification(s) deactivated successfully', 'Info');
					$scope.serviceclassifications.splice($scope.serviceclassifications.indexOf(value), 1);
					editClassifications = [];
					$scope.deleteIds = [];
				}, function (error) {
					GenericAlertService.alertMessage('Service Classification(s) is/are failed to deactivate', "Error");
				});
			}, function (data) {
				angular.forEach(editClassifications, function (value) {
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
						{ field: 'procSubCatCode', displayName: "Procurement Sub Category Id", headerTooltip: "Procurement Sub Category Id"},
						{ field: 'procSubCatName', displayName: "Procurement Sub Category Name", headerTooltip: "Procurement Sub Category Name", },
						{ field: 'code', displayName: "Service Classification ID", headerTooltip: "Service Classification ID"},
						{ field: 'name', displayName: "Service Classification Name", headerTooltip: "Service Classification Name",},
						{ field: 'status', displayName: "Status", cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status",},
						];
					let data = [];
					$scope.serviceClsSearch();
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_ServiceClassification");
					
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
			template: 'views/help&tutorials/serviceclasshelp.html',
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