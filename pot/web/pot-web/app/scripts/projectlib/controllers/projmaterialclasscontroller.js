'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state('projmaterial', {
		url : '/projmaterial',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/materialclass/projmaterialclass.html',
				controller : 'ProjMaterialClassController'
			}
		}
	})
}]).controller("ProjMaterialClassController", ["$rootScope", "$scope","$q", "$state", "blockUI", "ngDialog", "ProjMaterialClassService", "GenericAlertService", "EpsProjectSelectFactory", "TreeService", function($rootScope, $scope,$q, $state,blockUI, ngDialog, ProjMaterialClassService, 
	GenericAlertService, EpsProjectSelectFactory, TreeService) {
	$scope.deletecodes = [];
	$scope.projId = null;
	$scope.projMaterialClassTOs = [];
	var selectedMaterialClassMap = [];
	$scope.sortType = 'materialSubGroupTO.code', $scope.sortReverse = false;
	var editprojMaterialClassTOs = [];
	$scope.projects = [];
	$scope.materialReq = {
		"code" : '',
		"name" : '',
		"measurementTO.name" : '',
		"status" : "1"
	}, $scope.searchProject = {};
	$scope.inputPorject = {};
	$scope.dataExist = false;
	$scope.activeFlag = 0;

	
	$scope.projMaterialClassItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'projMaterialClassTOs');
	};
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.projMaterialClassTOs = [];
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getProjMaterialClasses = function(projId) {
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : $scope.materialReq.status,
		};
		if (req.projId == null || req.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjMaterialClassService.getProjMaterialClasses(req).then(function(data) {
			$scope.activeFlag = 0;
			$scope.projMaterialClassTOs = populateTreeData(data.projMaterialClassTOs);

			if ($scope.projMaterialClassTOs != null && $scope.projMaterialClassTOs.length > 0) {
				if ($scope.projMaterialClassTOs[0].status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.projMaterialClassTOs[0].status == 2) {
					$scope.activeFlag = 2;
				}
			}
			if ($scope.projMaterialClassTOs <= 0) {
				if ($scope.materialReq.status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.materialReq.status == 2) {
					$scope.activeFlag = 2;
				}
				GenericAlertService.alertMessage('Materials   are not available for given search criteria', "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}, $scope.clickMaterialTransfer = function(materialTO) {
		if (selectedMaterialClassMap[materialTO.groupId] == null) {
			selectedMaterialClassMap[materialTO.groupId] = materialTO;
		}
	}
	$scope.resetProjMaterialClasses = function() {
		$scope.epsProjId = null;
		$scope.projMaterialClassTOs = [];
		$scope.searchProject = {};
		$scope.dataExist = false;
		$scope.activeFlag = 0;
		$scope.materialReq = {
			"status" : "1"
		};
	}, $scope.rowSelect = function(tab) {
		if (tab.select) {
			editprojMaterialClassTOs.push(tab);
		} else {
			editprojMaterialClassTOs.splice(editprojMaterialClassTOs.indexOf(tab), 1);
		}
	}, $scope.saveProjMaterialClasses = function() {
		var selectedMaterials = [];
		angular.forEach(selectedMaterialClassMap, function(value, key) {
			value.projId = $scope.searchProject.projId;
			selectedMaterials.push(value);
		});
		var req = {
			"projMaterialClassTOs" : selectedMaterials,
			"projId" : $scope.searchProject.projId
		};
		blockUI.start();
		ProjMaterialClassService.saveProjMaterialClasses(req).then(function(data) {
			blockUI.stop();
			if (data.status != null && data.status !== undefined && data.status === 'Info') {
				var projectClassTOs = data.projMaterialClassTOs;
                selectedMaterialClassMap = [];
				// var succMsg = GenericAlertService.alertMessageModal('Material Classification(s) is/are ' + data.message, data.status);
				var succMsg = GenericAlertService.alertMessageModal('Material Classification(s) saved successfully',"Info" );
				succMsg.then(function(popData) {
					ngDialog.close();
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
				});
			}
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Material Classification(s)  is/are failed to Save', 'Error');
		});
	}, $scope.checkAll = function() {
		angular.forEach($scope.projMaterialClassTOs, function(tab) {
			if ($scope.selectedAll) {
				tab.select = false;
			} else {
				tab.select = true;
			}
		});
	}, $scope.deleteProjMaterialClasses = function() {
		if (editprojMaterialClassTOs.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.projMaterialClassTOs = [];
		} else {
			angular.forEach(editprojMaterialClassTOs, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projMaterialClassIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Info', 'YES', 'NO').then(function() {
				ProjMaterialClassService.deleteProjMaterialClasses(req).then(function(data) {
					angular.forEach(editprojMaterialClassTOs, function(value, key) {
						$scope.projMaterialClassTOs.splice($scope.projMaterialClassTOs.indexOf(value), 1);
						$scope.projMaterialClassTOs = populateTreeData($scope.projMaterialClassTOs);
						GenericAlertService.alertMessage('Material(s) is/are  Deactivated successfully', 'Info');
						editprojMaterialClassTOs = [];
						$scope.deleteIds = [];
					}, function(error) {
						GenericAlertService.alertMessage(' Material(s) is/are  failed to Deactivate', "Error");
						editprojMaterialClassTOs = [];
						$scope.deleteIds = [];
					});
				}, function(error) {
					angular.forEach(editprojMaterialClassTOs, function(value) {
						value.select = false;
					})
					GenericAlertService.alertMessage('Material(s) is/are    failed to Deactivate', "Error");
					editprojMaterialClassTOs = [];
					$scope.deleteIds = [];
				});
			}, function(data) {
				angular.forEach(editprojMaterialClassTOs, function(value) {
					value.select = false;
				})
				GenericAlertService.alertMessage(' Material(s) is/are not Deactivated', "Info");
				editprojMaterialClassTOs = [];
				$scope.deleteIds = [];
			})

		}
	}

	$scope.activeProjMaterialClasses = function() {
		if (editprojMaterialClassTOs.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.projMaterialClassTOs = [];
		} else {
			angular.forEach(editprojMaterialClassTOs, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projMaterialClassIds" : deleteIds,
				"status" : 1
			};
			ProjMaterialClassService.deleteProjMaterialClasses(req).then(function(data) {
				GenericAlertService.alertMessage('Material(s) is/are  Activated successfully', 'Info');
				angular.forEach(editprojMaterialClassTOs, function(value, key) {
					$scope.projMaterialClassTOs.splice($scope.projMaterialClassTOs.indexOf(value), 1);
					$scope.projMaterialClassTOs = populateTreeData($scope.projMaterialClassTOs);
				}, function(error) {
					GenericAlertService.alertMessage('Material(s) is/are  failed to Activate', "Error");
				});
				editprojMaterialClassTOs = [];
				$scope.deleteIds = [];

			});

		}
	}

	function populateTreeData(data) {
		return TreeService.populateTreeData(data, 0, [], 'code', 'projMaterialClassTOs');
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
			template: 'views/help&tutorials/projectshelp/projmatclasshelp.html',
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