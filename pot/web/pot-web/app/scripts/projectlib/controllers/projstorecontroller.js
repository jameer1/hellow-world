'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state('projstore', {
		url : '/projstore',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/storeandstock/storeyard.html',
				controller : 'ProjStoreController'
			}
		}
	})
}]).controller("ProjStoreController", ["$rootScope", "$scope","$q", "$state", "ngDialog", "ProjStoreStockService", "GenericAlertService", "EpsProjectSelectFactory", "ProjStocksPopupFactory","stylesService", "ngGridService", function($rootScope, $scope,$q, $state, ngDialog, ProjStoreStockService, GenericAlertService, EpsProjectSelectFactory, ProjStocksPopupFactory, stylesService, ngGridService) {
	var editStoreData = [];
	$scope.tableData = [];
	$scope.sortType='code',
	$scope.sortReverse=false;
	$scope.epsProjId = null;
	$scope.projId = null;
	$scope.stylesSvc = stylesService;
	$scope.epsProjects = [];
	var projStoreClassifyPopUp = null
	$scope.inputPorject = {};
	var emptyProjStoreClassifyObj = [];
	$scope.projects = [];
	$scope.storeReq = {
		"code" : '',
		"desc" : '',
		"status" : "1"
	}, $scope.searchProject = {};
	$scope.emptyProjStoreClassifyObj = [];
	$scope.inputPorject = {};
	$scope.dataExist = false;
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection	.then(function(data) {
			$scope.tableData = [];
							$scope.searchProject = data.searchProject;
							
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
						});
	},$scope.activeFlag = 0;
	$scope.getProjStoreStocks = function() {
		var workGetReq = {
			"projId" : $scope.searchProject.projId,
			"status" : $scope.storeReq.status
		};
		if (workGetReq.projId == null || workGetReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjStoreStockService.getProjStoreStocks(workGetReq).then(function(data) {
			$scope.activeFlag = 0;
			$scope.tableData = data.projStoreStockTOs;
			$scope.gridOptions.data = angular.copy($scope.tableData);
			if ($scope.tableData != null && $scope.tableData.length > 0) {
				if ($scope.tableData[0].status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.tableData[0].status == 2) {
					$scope.activeFlag = 2;
				}
			}
			if ($scope.tableData <= 0) {
				if ($scope.storeReq.status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.storeReq.status == 2) {
					$scope.activeFlag = 2;
				}
				GenericAlertService.alertMessage('Store and Stocks   are not available for given search criteria', "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}, $scope.resetProjStoreStock = function() {
		$scope.epsProjId = null;
		$scope.projId = null;
		$scope.tableData = [];
		$scope.searchProject = [];
		$scope.projects.proj = null;
		$scope.epsProjects.proj = null;
		$scope.storeReq = {
			"status" : "1"
		}
		$scope.activeFlag = 0;
	}, $scope.rowSelect = function(tab) {
		if (tab.select) {
			editStoreData.push(tab);
		} else {
			editStoreData.splice(editStoreData.indexOf(tab), 1);
		}
	}, $scope.addpopup = function(actionType, selectedProject) {

		angular.forEach(editStoreData,function(value,key){
			value.select=false;
				});

		
		if(editStoreData.length > 0 && actionType=="Add"){
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
			
			
		}
		if (actionType == 'Edit' && editStoreData <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
			var popupDetails = ProjStocksPopupFactory.projStoreClassifyPopUp(actionType, selectedProject, editStoreData);
			editStoreData=[];
			popupDetails.then(function(data) {
				$scope.tableData = data.projStoreStockTOs;
				editStoreData = [];
				$scope.getProjStoreStocks();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Project Employee Classification details", 'Info');
			})
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	}, $scope.deleteStoreClass = function() {
		if (editStoreData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editStoreData, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projStoreStockIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			ProjStoreStockService.deleteProjStoreStocks(req).then(function(data) {
				GenericAlertService.alertMessage('Store(s) Deactivated successfully', 'Info');
				editStoreData = [];
				$scope.deleteIds = [];
				$scope.getProjStoreStocks();
			});
			angular.forEach(editStoreData, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage(' Store(s) is/are failed to Deactivate', "Error");
			});
			},function(data){
				angular.forEach(editStoreData, function(value) {
					value.select = false;
			})
			})
			
		}
	}
	$scope.activeStoreClass = function() {
		if (editStoreData.length <= 0) {
			GenericAlertService.alertMessage( "Please select atleast one row to Activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editStoreData, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projStoreStockIds" : deleteIds,
				"status" : 1
			};
			ProjStoreStockService.deleteProjStoreStocks(req).then(function(data) {
				GenericAlertService.alertMessage('Store(s) Activated successfully', 'Info');
			});
			angular.forEach(editStoreData, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage(' Store(s) is/are  failed to Activate', "Error");
			});
			editStoreData = [];
			$scope.deleteIds = [];
		}
	}
	
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.rowSelect(row.entity)" >';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'code', displayName: "Store/Location ID", headerTooltip: "Store/Location ID", },
						{ field: 'desc', displayName: "Store/Location Description", headerTooltip: "Store/Location Description",},
						{ field: 'category', displayName: "Category", headerTooltip: "Category",},
						{ field: 'status', cellFilter: 'potstatusfilter:tab.status', displayName: "Status", headerTooltip: "Status", },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Project_ProjectLibrary_Project_Plant_StockYard");
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
			template: 'views/help&tutorials/projectshelp/warehousehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);