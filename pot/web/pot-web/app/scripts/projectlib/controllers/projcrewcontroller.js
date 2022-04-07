'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("projcrewlist", {
		url : '/projcrewlist',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/crewlist/projcrewlist.html',
				controller : 'ProjCrewController'
			}
		}
	})
}]).controller("ProjCrewController", ["$scope", "$state","$q", "ngDialog", "ProjCrewListService", "ProjCrewListPopupService", "GenericAlertService", "EpsProjectSelectFactory","stylesService", "ngGridService", function($scope, $state, $q, ngDialog, ProjCrewListService, ProjCrewListPopupService, GenericAlertService, EpsProjectSelectFactory,stylesService, ngGridService) {
	$scope.tab = [];
	$scope.tableData = [];
	$scope.epsProjId = null;
	$scope.stylesSvc = stylesService;
	$scope.sortType='code',
	$scope.sortReverse=false;
	$scope.projId = null;
	$scope.epsProjects = [];
	$scope.projects = [];
	var editTableData = [];
	$scope.searchProject = {};
	$scope.crewReq = {
		"code" : '',
		"desc" : '',
		"projWorkShiftTO.code" : '',
		"status" : "1"
	}, $scope.activeFlag = 0;
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection	.then(function(data) {
			$scope.tableData = [];
							$scope.searchProject = data.searchProject;
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS/Project name",'Error');
						});
	},
	$scope.getProjCrewLists = function(projId) {
		editTableData = [];
		var workGetReq = {
			"projId" : $scope.searchProject.projId,
			"status" : $scope.crewReq.status
		};
		if (workGetReq.projId == null || workGetReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjCrewListService.getProjCrewLists(workGetReq).then(function(data) {
			$scope.activeFlag = 0;
			$scope.tableData = data.projCrewTOs;
			$scope.gridOptions.data = angular.copy($scope.tableData);
			if ($scope.tableData != null && $scope.tableData.length > 0) {
				if ($scope.tableData[0].status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.tableData[0].status == 2) {
					$scope.activeFlag = 2;
				}
			}
			if ($scope.tableData <= 0) {
				if ($scope.crewReq.status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.crewReq.status == 2) {
					$scope.activeFlag = 2;
				}
				GenericAlertService.alertMessage('Crew Lists  are not available for given search criteria', "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	},

	$scope.resetProjCrewList = function() {
		$scope.tableData = [];
		$scope.activeFlag = 0;
		$scope.searchProject = {};
		$scope.crewReq = {
			"status" : "1"
		}
	}, $scope.rowSelect = function(tab) {
		if (tab.select) {
			editTableData.push(tab);
		} else {
			editTableData.splice(editTableData.indexOf(tab), 1);
		}
	}
	$scope.addTableData = function(actionType, selectedProject) {
		angular.forEach(editTableData,function(value,key){
			value.select=false;
				});

		if (editTableData.length > 0 && actionType == 'Add') {
			editTableData=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		if (actionType == 'Edit' && editTableData <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
			var popupDetails = ProjCrewListPopupService.projCrewListPopUp(actionType, selectedProject, editTableData);
			editTableData = [];
			popupDetails.then(function(data) {
				$scope.tableData = data.projCrewTOs;
				$scope.shiftData = data.projWorkShiftTOs;
				editTableData = [];
				$scope.getProjCrewLists();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Project Employee Classification details", 'Info');
			})
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	}, $scope.checkAll = function() {
		angular.forEach($scope.tableData, function(tab) {
			if ($scope.selectedAll) {
				tab.select = false;
			} else {
				tab.select = true;
			}
		});
	}, $scope.deleteCrewList = function() {
		if (editTableData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", "Warning");
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editTableData, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projCrewIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			ProjCrewListService.deleteProjCrewLists(req).then(function(data) {
				angular.forEach(editTableData, function(value, key) {
					$scope.tableData.splice($scope.tableData.indexOf(value), 1);
				})
				GenericAlertService.alertMessage('CrewList(s)  Deactivated successfully', 'Info');
				editTableData = [];
				$scope.deleteIds = [];
				$scope.getProjCrewLists();
			}, function(error) {
				GenericAlertService.alertMessage(' CrewList(s) is/are  failed to Deactivate', "Error");
			});
			}, function(data) {
				angular.forEach(editTableData, function(value) {
					value.select = false;
			})
			GenericAlertService.alertMessage(' CrewList(s) is/are not Deactivated', "Info");
			editTableData = [];
			})
			
			
		}
		
	}
	

	
	
	
	
	
	
	
	
	
	$scope.activeCrewList = function() {
		if (editTableData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Activate", "Warning");
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editTableData, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projCrewIds" : deleteIds,
				"status" : 1
			};
			ProjCrewListService.deleteProjCrewLists(req).then(function(data) {
				GenericAlertService.alertMessage('CrewList(s) Activated successfully', 'Info');
			});

			angular.forEach(editTableData, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage(' CrewList(s) is/are failed to Activate', "Error");
			});
			editTableData = [];
			$scope.deleteIds = [];

		}
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.rowSelect(row.entity)" >';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'code', displayName: "Crew ID", headerTooltip: "Crew ID", },
						{ field: 'desc', displayName: "Crew Name", headerTooltip: "Crew Name"},
						{ field: 'projWorkShiftTO.code', displayName: "Shift Type", headerTooltip: "Shift Type"},
						{ field: 'status', cellFilter: 'potstatusfilter:tab.status', displayName: "Status", headerTooltip: "Status", },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Project_ProjectLibrary_CrewList");
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
			template: 'views/help&tutorials/projectshelp/crewlisthelp.html',
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
