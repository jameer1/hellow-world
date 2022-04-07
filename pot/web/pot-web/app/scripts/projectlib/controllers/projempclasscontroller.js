'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state('projempclass', {
		url : '/projempclass',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/empclass/projempclass.html',
				controller : 'ProjEmpClassController'
			}
		}
	})
}]).controller("ProjEmpClassController", ["$rootScope", "$scope","$q", "$state", "ngDialog", "blockUI", "ProjEmpClassService", "GenericAlertService", "EpsProjectSelectFactory", "ProjectEmpClassificationService", "generalservice", function($rootScope, $scope, $q, $state, ngDialog,	blockUI, ProjEmpClassService, GenericAlertService, EpsProjectSelectFactory,
	 ProjectEmpClassificationService, generalservice) {
	$scope.epsProjId = null;
	$scope.sortType='empClassTO.code',
	$scope.sortReverse=false;
	$scope.projId = null;
	$scope.tableData = [];
	$scope.UiDelete = [];
	var editEmpClass = [];
	$scope.epsProjects = [];
	$scope.projects = [];
	$scope.searchProject = {};
	$scope.inputPorject = {};
	var projEmpClassifiPopUp = null;
	$scope.projEmpReq = {
		"empClassTO.code" : null,
		"empClassTO.name" : null,
		"projEmpCatgTO.code" : null,
		"tradeContrName" : null,
		"unionWorkerName" : null,
		"status" : "1"
	}
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection	.then(function(data) {
			$scope.tableData = [];
							$scope.searchProject = data.searchProject;
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
						});
	}
	
	
	var onLoadReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId
		};
		ProjEmpClassService.addProjEmpClassifyOnLoad(onLoadReq).then(function(data) {
			$scope.catgData = generalservice.employeeCategory;
			$scope.resourceData = data.empClassTOs;
			$scope.unitOfMeasures = data.measureUnitTOs;
		});
	$scope.activeFlag = 0;
	$scope.getProjEmpClasses = function() {
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : $scope.projEmpReq.status,
		};
		if (req.projId == null || req.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjEmpClassService.getProjEmpClasses(req).then(function(data) {
			$scope.activeFlag = 0;
			$scope.tableData = data.projEmpClassTOs;
			if ($scope.tableData != null && $scope.tableData.length > 0) {
				if ($scope.tableData[0].status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.tableData[0].status == 2) {
					$scope.activeFlag = 2;
				}
			}
			if ($scope.tableData <= 0) {
				if ($scope.projEmpReq.status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.projEmpReq.status == 2) {
					$scope.activeFlag = 2;
				}
				GenericAlertService.alertMessage('Employees  are not available for given search criteria', "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	},

	$scope.reset = function() {
		$scope.tableData = [];
		$scope.projEmpReq = {
			"status" : "1"
		}
		$scope.activeFlag = 0;
		$scope.projects = [];
		$scope.searchProject = {};
	}, $scope.rowSelect = function(tab) {
		if (tab.select) {
			editEmpClass.push(tab);
		} else {
			editEmpClass.splice(editEmpClass.indexOf(tab), 1);
		}
	}, $scope.addpopup = function(actionType, selectedProject, projId) {
		if (actionType == 'Edit' && editEmpClass <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
			var popupDetails = ProjectEmpClassificationService.projEmpClassifyPopup(actionType, editEmpClass, projId);
			popupDetails.then(function(data) {
				$scope.tableData = data.projEmpClassTOs;
				$scope.catgData = data.measureUnitTOs;
				editEmpClass = [];
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Project Employee Classification details", 'Info');
			})
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	}, 
	
	
	
		$scope.saveEmpClass = function () {
			let empProjSaveReq = {
				"projEmpClassTOs": $scope.tableData,
				"projId": $scope.searchProject.projId
			}
			let isEmpty = false, msg = "Please enter ";
			for (const tab of $scope.tableData) {
				if (!tab.projEmpCategory && !tab.tradeContrName && !tab.unionWorkerName) {
					continue;
				}
				else if(!tab.projEmpCategory || !tab.tradeContrName || !tab.unionWorkerName) {
					if (!tab.projEmpCategory) {
						msg += " Emp Category, ";
					}
					if (!tab.tradeContrName) {
						msg += " project contract name, ";
					}
					if (!tab.unionWorkerName) {
						msg += " worker union name ";
					}
					msg += "for " + tab.empClassTO.code ;
					isEmpty = true;
					break;
				}
			}
			

			if (isEmpty) {
				GenericAlertService.alertMessage(msg, 'Warning');
				return;
			}
			
			blockUI.start();
			ProjEmpClassService.saveProjEmpClasses(empProjSaveReq).then(function (data) {
				blockUI.stop();
				// var succMsg = GenericAlertService.alertMessageModal('Employee(s) is/are ' + data.message, data.status);
				var succMsg = GenericAlertService.alertMessageModal('Employee(s) details saved successfully',"Info");
				$scope.getProjEmpClasses();
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
			});
		},
	$scope.deleteEmpClass = function() {
		if (editEmpClass.length <= 0) {
			GenericAlertService.alertMessage( "Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editEmpClass, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projEmpClassIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Info', 'YES', 'NO').then(function() {
			ProjEmpClassService.deleteProjEmpClasses(req).then(function(data) {
				angular.forEach(editEmpClass, function(value, key) {
					$scope.tableData.splice($scope.tableData.indexOf(value), 1);
				GenericAlertService.alertMessage('Employee(s) is/are  Deactivated successfully', 'Info');
				editEmpClass = [];
				$scope.deleteIds = [];
			}, function(error) {
				GenericAlertService.alertMessage(' Employee(s) is/are  failed to Deactivate', "Error");
				editEmpClass = [];
				$scope.deleteIds = [];
			});
			}, function(error) {
				angular.forEach(editEmpClass, function(value) {
					value.select = false;
			})
				GenericAlertService.alertMessage('Employee(s) is/are    failed to Deactivate', "Error");
				editEmpClass = [];
				$scope.deleteIds = [];
			});
			}, function(data) {
				angular.forEach(editEmpClass, function(value) {
					value.select = false;
			})
			GenericAlertService.alertMessage(' Employee(s) is/are not Deactivated', "Info");
				editEmpClass = [];
				$scope.deleteIds = [];
			})

		}
	}
	$scope.activeEmpClass = function() {
		if (editEmpClass.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editEmpClass, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projEmpClassIds" : deleteIds,
				"status" : 1
			};
			ProjEmpClassService.deleteProjEmpClasses(req).then(function(data) {
				GenericAlertService.alertMessage('Employee(s) is/are Activated successfully', 'Info');
			});

			angular.forEach(editEmpClass, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Employee(s) is/are failed to Activate', "Error");
			});
			editEmpClass = [];
			$scope.deleteIds = [];

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
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/projectshelp/empclasshelp.html',
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