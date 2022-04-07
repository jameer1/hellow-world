'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("projworkshift", {
		url : '/projworkshift',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/workshift/workingshifts.html',
				controller : 'ProjWorkShiftController'
			}
		}
	})
}]).controller("ProjWorkShiftController", ["$rootScope", "$scope","$q", "$state", "ngDialog", "ProjWorkShiftService", "ProjWorkShiftPopupService", "GenericAlertService", "EpsProjectSelectFactory","stylesService", "ngGridService", function($rootScope, $scope,$q, $state, ngDialog, ProjWorkShiftService, ProjWorkShiftPopupService, GenericAlertService, EpsProjectSelectFactory, stylesService, ngGridService) {
	$scope.epsProjId = null;
	$scope.projId = null;
	$scope.sortType='code',
	$scope.stylesSvc = stylesService;
	$scope.sortReverse=false;
	$scope.addtableData = [];
	$scope.tableData = [];
	$scope.epsProjects = [];
	$scope.projects = [];
	$scope.searchProject = {};
	var editTableData = [];
	$scope.workShiftReq = {
		"code" : '',
		"status" : "1"
	}, $scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection	.then(function(data) {
			$scope.tableData = [];
							$scope.searchProject = data.searchProject;
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
						});
	}, $scope.activeFlag = 0;
	$scope.getProjWorkShifts = function() {
		var workGetReq = {
			"projId" : $scope.searchProject.projId,
			"status" : $scope.workShiftReq.status
		};
		if (workGetReq.projId == null || workGetReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjWorkShiftService.getProjWorkShifts(workGetReq).then(function(data) {
			$scope.activeFlag = 0;
			$scope.tableData = data.projWorkShiftTOs
			
			for (let manpower of $scope.tableData) {
				manpower.starthrs= manpower.startHours+":"+  manpower.startMinutes;
				manpower.endhrs=manpower.finishHours+":"+manpower.finishMinutes;
			}
			$scope.gridOptions.data = angular.copy($scope.tableData);
			if ($scope.tableData != null && $scope.tableData.length > 0) {
				if ($scope.tableData[0].status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.tableData[0].status == 2) {
					$scope.activeFlag = 2;
				}
			}
			if ($scope.tableData <= 0) {
				if ($scope.workShiftReq.status == 1) {
					$scope.activeFlag = 1;
					GenericAlertService.alertMessage('Active Shifts are not available for given search criteria', "Warning");
				} else if ($scope.workShiftReq.status == 2) {
					$scope.activeFlag = 2;
					GenericAlertService.alertMessage('Inactive Shifts are not available for given search criteria', "Warning");
				}
				//GenericAlertService.alertMessage('Working Shifts are not available for given search criteria', "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}, $scope.resetProjWorkShift = function() {
		$scope.tableData = [];
		$scope.searchProject = {};
		$scope.workShiftReq = {
			"status" : "1"
		}
		$scope.activeFlag = 0;
	},

	$scope.rowSelect = function(tab) {
		if (tab.select) {
			editTableData.push(tab);
		} else {
			editTableData.splice(editTableData.indexOf(tab), 1);
		}
	}, $scope.addTableData = function(actionType, selectedProject) {
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
			var popupDetails = ProjWorkShiftPopupService.projWorkShiftPopUp(actionType, selectedProject, editTableData);
			editTableData = [];
			popupDetails.then(function(data) {
				$scope.tableData = data.projWorkShiftTOs;
				editTableData = [];
				$scope.getProjWorkShifts();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Project Employee Classification details", 'Info');
			});
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	}, $scope.deleteWorkShifts = function() {
		if (editTableData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
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
				"projWorkShiftIds" : deleteIds,
				"status" : 2
			};
			
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			ProjWorkShiftService.deleteProjWorkShifts(req).then(function(data) {
				GenericAlertService.alertMessage('Working Shift(s) Deactivated Successfully', 'Info');
				editTableData = [];
				$scope.deleteIds = [];
				$scope.getProjWorkShifts();
			});

			angular.forEach(editTableData, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Working Shift(s) is/are failed to Deactivate', "Error");
			});
		

		},function(data){
			angular.forEach(editTableData, function(value) {
				value.select = false;
		})
		})
	}
	}
	$scope.activeWorkShifts = function() {
		if (editTableData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Activate", 'Warning');
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
				"projWorkShiftIds" : deleteIds,
				"status" : 1
			};
			ProjWorkShiftService.deleteProjWorkShifts(req).then(function(data) {
				GenericAlertService.alertMessage('Working Shift(s) Activated Successfully', 'Info');
			});

			angular.forEach(editTableData, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Working Shift(s) is/are failed to Activate', "Error");
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
						{ field: 'code', displayName: "Shift", headerTooltip: "Shift", },
						{ name: 'starthrs', displayName: "Start Hour", headerTooltip: "Start Hour"},
						{ name: 'endhrs', displayName: "Duration in Hours", headerTooltip: "Duration in Hours"},
						{ field: 'status', cellFilter: 'potstatusfilter:tab.status', displayName: "Status", headerTooltip: "Status", },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Project_ProjectLibrary_Project_Plant_WorkingShift");
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
			template: 'views/help&tutorials/projectshelp/projworkshifthelp.html',
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
