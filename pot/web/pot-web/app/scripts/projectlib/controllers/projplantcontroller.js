'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state('projplantclass', {
		url : '/projplantclass',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectlib/plantclass/projplant.html',
				controller : 'ProjPlantController'
			}
		}
	})
}]).controller("ProjPlantController", ["$rootScope", "$scope","$q", "$state", "ngDialog", "blockUI", "ProjPlantClassService", "ProjEmpClassService", "GenericAlertService", "EpsProjectSelectFactory", "ProjPlantClassificationPopupService", function($rootScope, $scope,$q, $state, ngDialog,blockUI, ProjPlantClassService, ProjEmpClassService, GenericAlertService, EpsProjectSelectFactory, ProjPlantClassificationPopupService) {
	$scope.epsProjId = null;
	$scope.projId = null;
	$scope.projPlantData = [];
	$scope.sortType = 'plantClassTO.code', $scope.sortReverse = false;
	$scope.epsProjects = [];
	$scope.projects = [];
	$scope.plantReq = {
		"plantClassTO.code" : '',
		"plantContrName" : '',
		"measureUnitTO.name" : '',
		"status" : "1"
	}
	var editPlantClass = [];
	$scope.searchProject = {};
	$scope.inputPorject = {};
	$scope.dataExist = false;
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.projPlantData = [];
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.activeFlag = 0;
	$scope.getProjPlantClasses = function(projId) {
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : $scope.plantReq.status
		};
		if (req.projId == null || req.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}
		ProjPlantClassService.getProjPlantClasses(req).then(function(data) {
			$scope.activeFlag = 0;
			$scope.projPlantData = data.projPlantClassTOs;
			if ($scope.projPlantData != null && $scope.projPlantData.length > 0) {
				if ($scope.projPlantData[0].status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.projPlantData[0].status == 2) {
					$scope.activeFlag = 2;
				}
			}
			if ($scope.projPlantData <= 0) {
				if ($scope.plantReq.status == 1) {
					$scope.activeFlag = 1;
				} else if ($scope.plantReq.status == 2) {
					$scope.activeFlag = 2;
				}

				GenericAlertService.alertMessage('Plant Classifiations   are not available for given search criteria', "Warning");
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});
	}, $scope.resetProjPlantClasses = function() {
		$scope.searchProject = {};
		$scope.projPlantData = [];
		$scope.activeFlag = 0;
		$scope.projects.proj = null;
		$scope.plantReq = {
			"status" : "1"
		};
	}, $scope.rowSelect = function(tab) {
		if (tab.select) {
			editPlantClass.push(tab);
		} else {
			editPlantClass.splice(editPlantClass.indexOf(tab), 1);
		}
	}

	$scope.saveProjPlantClasses = function() {
		var req = {
			"projPlantClassTOs" : $scope.projPlantData,
			"projId" : $scope.searchProject.projId
		};
		blockUI.start();
		ProjPlantClassService.saveProjPlantClasses(req).then(function(data) {
			blockUI.stop();
			// var succMsg = GenericAlertService.alertMessageModal('Plant Classification(s) is/are ' + data.message, data.status);
			var succMsg = GenericAlertService.alertMessageModal('Plant Classification(s) saved successfully',"Info");
			$scope.getProjPlantClasses();
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage('Plant Classification(s) is/are failed to Save', 'Error');
		});
	}, $scope.addprojetplant = function(actionType, selectedProject) {
		if (actionType == 'Edit' && editPlantClass <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
			var popupDetails = ProjPlantClassificationPopupService.projPlantClassifiPopUp(actionType, selectedProject, editPlantClass);
			popupDetails.then(function(data) {
				$scope.projPlantData = data.projPlantClassTOs;
				editPlantClass = [];
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Project Employee Classification details", 'Info');
			})
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	}, $scope.deletePlantClass = function() {
		if (editPlantClass.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.projPlantData = [];
		} else {
			angular.forEach(editPlantClass, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
		}
		var req = {
			"projPlantClassIds" : deleteIds,
			"status" : 2
		};

		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			ProjPlantClassService.deleteProjPlantClasses(req).then(function(data) {
				GenericAlertService.alertMessage('Plant(s) are  Deactivated successfully', 'Info');
			});
			angular.forEach(editPlantClass, function(value, key) {
				$scope.projPlantData.splice($scope.projPlantData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Plant(s) is/are  failed to Deactivate', "Error");
			});
			editPlantClass = [];
			$scope.deleteIds = []
		}, function(data) {
			angular.forEach(editPlantClass, function(value) {
				value.select = false;
			})
			editPlantClass = [];
		})
	}
	$scope.activePlantClass = function() {
		if (editPlantClass.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.projPlantData = [];
		} else {
			angular.forEach(editPlantClass, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"projPlantClassIds" : deleteIds,
				"status" : 1
			};
			ProjPlantClassService.deleteProjPlantClasses(req).then(function(data) {
				GenericAlertService.alertMessage('Plant(s) is/are   Activated successfully', 'Info');
			});

			angular.forEach(editPlantClass, function(value, key) {
				$scope.projPlantData.splice($scope.projPlantData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Plant(s) is/are  failed to Activate', "Error");
			});
			editPlantClass = [];
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
			template: 'views/help&tutorials/projectshelp/plantclasshelp.html',
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