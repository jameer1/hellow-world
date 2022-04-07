'use strict';

/**
 * @ngdoc function
 * @name potApp.controller : Procurement Category Controller
 * @description # Procurement Category Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("procurecatg", {
		url : '/procurecatg',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/procure/procure.html',
				controller : 'ProCategoryController'
			}
		}
	})
}]).controller('ProCategoryController', ["$scope", "utilservice", "$q", "ngDialog", "blockUI", "ProcureService", "GenericAlertService", "generalservice","stylesService", "ngGridService", function($scope, utilservice, $q, ngDialog, blockUI, ProcureService, GenericAlertService,generalservice, stylesService, ngGridService) {
	var editCategories = [];
	$scope.categories = [];
	$scope.stylesSvc = stylesService;
	var selectedCategory = [];
	$scope.sortType = "procurement.name"
	var deferred = $q.defer();
	$scope.deleteIds = [];
	$scope.proCategory = [];
	$scope.uniqueCodeMap = [];
	$scope.procReq = {
		"procureId" : 'Services',
		"subProcureName" : null,
		"status" : "1"
	};
	var req = {
		"status" : 1
	};
	$scope.getprocures = function() {
		// console.log('*** get Procurment sub categories *********');
		$scope.proCategory = generalservice.getprocures;
		// console.log('*** $scope.proCategory *********  ', $scope.proCategory);
	}, 
	// $scope.activeFlag = 0;
	$scope.procureSearch = function(click) {
		// console.log('*** get Procurment sub categories click  ********* ', click);
		// console.log('*** get Procurment sub categories click  ********* ', $scope.procReq);
		ProcureService.getProcureCatgs($scope.procReq).then(function(data) {
			// console.log('*** get Procurment sub categories click  ********* ', data);
			editCategories = [];
			// $scope.activeFlag = 0;
			$scope.categories = data.procureMentCatgTOs;
			// var dummyProcure = angular.copy($scope.categories).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{
			// item.status = 'Inactive'}
			// return item;
			// });
			$scope.gridOptions.data = $scope.categories;
			if(click=='click'){
				if ($scope.categories.length <= 0) {
					GenericAlertService.alertMessage('Procurement Category Details are not available for given search criteria', "Warning");
					return;
				}
			}
			// if ($scope.categories != null && $scope.categories.length > 0) {
			// 	if ($scope.categories[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.categories[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.procReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.procReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.procReq = {
			// 		"procureId" : null,
			// 		"subProcureName" : null,
			// 		"status" : "1"
			// 	};
			// }
		});
	},

	$scope.reset = function() {
		$scope.procReq = {
			"procureId" : null,
			"subProcureName" : null,
			"status" : "1"
		}
		// $scope.activeFlag = 0;
		$scope.procureSearch();
	}, $scope.updateCatgCode = function(data) {
		$scope.procReq.procureId = data.name;
	}, $scope.rowSelect = function(proCat) {
		if (proCat.selected) {
			utilservice.addItemToArray(editCategories, "proCatgId", proCat);
		} else {
			editCategories.splice(editCategories.indexOf(proCat), 1);
		
		
		}
	}
	var service = {};
	var categoryPopUp;
	$scope.addCategories = function(actionType) {
		
		angular.forEach(editCategories,function(value,key){
			value.selected=false;
				});
		if(editCategories.length > 0 && actionType=="Add"){
			editCategories=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
					}
		categoryPopUp = service.addCategory(actionType);
		categoryPopUp.then(function(data) {
			$scope.categories = data.procureMentCatgTOs;
			$scope.procureSearch('click');
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting Procurement Details", 'Error');
		});
	}

	service.addCategory = function(actionType) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editCategories.length === 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		categoryPopUp = ngDialog.open({
			template : 'views/centrallib/procure/procurepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.categoryList = [];
				$scope.action = actionType;
				$scope.proCategory = [];
				$scope.proCategorys=[];
				var copyEditArray = angular.copy(editCategories);
				if (actionType === 'Add') {
					$scope.categoryList.push({
						'code' : '',
						'desc' : '',
						'status' : '1',
						'selected' : false
					});
				} else {
					$scope.categoryList = angular.copy(editCategories);
					editCategories = [];
				}
				var req = {
					"status" : 1
				};
			
					$scope.proCategory = generalservice.getprocures;
					
					//proCategorys assignment  
					angular.forEach($scope.proCategory,function(value){	
						if(value.name=='Manpower'||value.name=='Plants'||value.name=='Man Power'){
							
						}	
						else{		
							$scope.proCategorys.push(value);
							}
	
					})
			
				$scope.addRows = function() {
					$scope.categoryList.push({
						'code' : '',
						'desc' : '',
						'status' : '1',
						'selected' : false
					});
				}, $scope.updateCatgCode = function(data, category) {
					category.procureId = data.name;
				}, $scope.getProcureCatgClassMap = function() {
					var req = {

					}
					ProcureService.getProcureCatgClassMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				}, $scope.checkDuplicate = function(procurement) {
					procurement.duplicateFlag = false;
					procurement.code = procurement.code.toUpperCase();
					if ($scope.uniqueCodeMap[procurement.procurement.name + " " + procurement.code] != null) {
						GenericAlertService.alertMessage('Duplicate Procurement codes are not allowed', "Warning");
						procurement.duplicateFlag = true;
						return;
					}
					procurement.duplicateFlag = false;
				}, $scope.saveProcureCatgs = function(categoryForm) {
					if(categoryForm.$valid){
					var flag = false;
					var procurecatgMap = [];
					angular.forEach($scope.categoryList, function(value, key) {
						if (procurecatgMap[value.procurement.name + " " + value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							procurecatgMap[value.procurement.name + " " + value.code.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.categoryList, function(value, key) {
							if ($scope.uniqueCodeMap[value.procurement.name + " " + value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Procurement codes are not allowed', "Warning");
						return;
					}
					var req = {
						"procureMentCatgTOs" : $scope.categoryList
					}
					var results = [];
					blockUI.start();
					ProcureService.saveProcureCatgs(req).then(function(data) {
						blockUI.stop();
						results = data.procureMentCatgTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Procurement Category(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Procurement Category(s) saved successfully ',"Info");
						succMsg.then(function(data1) {
							$scope.closeThisDialog(categoryPopUp);
							var returnPopObj = {
								"procureMentCatgTOs" : results
							}
							deferred.resolve(returnPopObj);
							$scope.procureSearch('click');
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Procurement(s)  is/are failed to save', "Error");
					});
					}
				else{
					blockUI.stop();
					GenericAlertService.alertMessage('Please fill all mandatory fields( <font color="red"> * </font> )', "Warning");
				}
					
				}
				$scope.popUpRowSelect = function(category) {
					if (category.selected) {
						selectedCategory.push(category);
					} else {
						selectedCategory.splice(selectedCategory.indexOf(category), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedCategory.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedCategory.length < $scope.categoryList.length) {
						angular.forEach(selectedCategory, function(value, key) {
							$scope.categoryList.splice($scope.categoryList.indexOf(value), 1);

						});
						selectedCategory = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedCategory.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedCategory.length == 1) {
						$scope.categoryList[0] = {
							'code' : '',
							'desc' : '',
							'status' : '1',
							'selected' : false
						};
						selectedCategory = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			} ]
		});
		return deferred.promise;
	}
	$scope.activeProcureCatgs = function() {
		if (editCategories.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to activate', 'Warning');
			return;
		}
		var deleteIds = [];
		var nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.categories = [];
		} else {
			angular.forEach($scope.categories, function(value, key) {
				if (!value.selected) {
					nondeleteIds.push(value);
				} else {
					if (value.proCatgId != null && value.proCatgId > 0) {
						deleteIds.push(value.proCatgId);
					}
				}
			});
			var req = {
				"procureCatgIds" : deleteIds,
				"status" : 1
			};
			ProcureService.deleteProcureCatgs(req).then(function(data) {
			});
			GenericAlertService.alertMessage('Procurement(s) activated successfully', 'Info');
			angular.forEach(editCategories, function(value, key) {
				$scope.categories.splice($scope.categories.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Procurement(s) is/are failed to activate', "Error");
			});
			editCategories = [];
			$scope.deleteIds = [];
		}
	}
	$scope.deleteProcureCatgs = function() {
		if (editCategories.length <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to deactivate', 'Warning');
			return;
		}
		var deleteIds = [];
		var nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.categories = [];
		} else {
			angular.forEach($scope.categories, function(value, key) {
				if (!value.selected) {
					nondeleteIds.push(value);
				} else {
					if (value.proCatgId != null && value.proCatgId > 0) {
						deleteIds.push(value.proCatgId);
					}
				}
			});
			var req = {
				"procureCatgIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				ProcureService.deleteProcureCatgs(req).then(function(data) {
					$scope.procureSearch('click');
				});
				GenericAlertService.alertMessage('Procurement(s) deactivated successfully', 'Info');
				angular.forEach(editCategories, function(value, key) {
					$scope.categories.splice($scope.categories.indexOf(value), 1);
					editCategories = [];
					$scope.deleteIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Procurement(s) is/are failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(editCategories, function(value) {
					value.selected = false;
				})
			})
		}
	}
	var linkCellTemplate ='<input type="checkbox" ng-model="row.entity.selected" ng-disabled=(row.entity.procureId=="Plants"||row.entity.procureId=="Manpower")?true:false ng-change="grid.appScope.rowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'procureId', displayName: "Procurement Category", headerTooltip: "Procurement Category"},
						{ field: 'code', displayName: "Sub Category Code", headerTooltip: "Sub Category Code", },
						{ field: 'desc', displayName: "Sub Category Name", headerTooltip: "Sub Category Name", },
						{ field: 'status',displayName: "Status", cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_Wages");
					$scope.procureSearch();
					
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
			template: 'views/help&tutorials/procurementcathelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			reloadOnSearch: false,
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
