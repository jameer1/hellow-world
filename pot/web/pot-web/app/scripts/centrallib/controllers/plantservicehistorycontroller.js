'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantservicehistory", {
		url : '/plantservicehistory',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/plantservicehistory/plantservicehistory.html',
				controller : 'PlantServiceHistoryController'
			}
		}
	})
}]).controller("PlantServiceHistoryController", ["$rootScope", "$q", "$scope","ngDialog", "utilservice", "PlantServiceHistoryFactory", "PlantServiceHistoryService", "GenericAlertService", "TreeService", function($rootScope, $q, $scope,ngDialog, utilservice, 
	PlantServiceHistoryFactory, PlantServiceHistoryService, GenericAlertService, TreeService) {
	var deactivateData = [];
	$scope.tableData = [];
	$scope.plantServiceSearchReq = { 'name': "", 'code': "" };
	let palntServiceHistoryDataCopy = null;
	$scope.getPlantServiceHistoryDetails = function() {
		var plantServiceHistoryReq = {
			"status" : 1
		};
		PlantServiceHistoryService.getPlantServiceHistroyDetails(plantServiceHistoryReq).then(function(data) {
			deactivateData = [];
			palntServiceHistoryDataCopy = angular.copy(data.plantServiceClassificationMstrTOs);
			$scope.tableData = populatePlantServiceHistoryData(data.plantServiceClassificationMstrTOs,0,[]);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting plant service history Details", 'Error');
		});
	}, $scope.rowSelect = function(rowData) {
		if (rowData.select) {
			utilservice.addItemKeyValueToArray(deactivateData, "id", rowData);
		} else {
			deactivateData.splice(deactivateData.indexOf(rowData.id), 1);
		}
	}

	function populatePlantServiceHistoryData(data, level, plantServiceClassificationMstrTOs, isChild, parent) {
		return TreeService.populateTreeData(data, level, plantServiceClassificationMstrTOs, 'code',
			'plantServiceClassificationMstrTOs', isChild, parent);
	}

	$scope.plantServiceSearch = function () {
		if ($scope.plantServiceSearchReq.code || $scope.plantServiceSearchReq.name) {
			const plantServiceTOs = new Array();
			for (const plantService of palntServiceHistoryDataCopy) {
				if (plantService.code.toUpperCase() === $scope.plantServiceSearchReq.code.toUpperCase()
					|| plantService.name.toUpperCase() === $scope.plantServiceSearchReq.name.toUpperCase()) {
					plantServiceTOs.push(plantService);
				}
			}
			$scope.tableData = populatePlantServiceHistoryData(plantServiceTOs, 0, []);
			if ($scope.tableData.length <= 0) {
				GenericAlertService.alertMessage('Plant Service History Details are not available for given search criteria', "Warning");
				return;
			}
		}
	};

	$scope.reset = function () {
		if ($scope.plantServiceSearchReq.code || $scope.plantServiceSearchReq.name) {
			$scope.tableData = [];
			$scope.plantServiceSearchReq = { 'name': "", 'code': "" };
			$scope.tableData = populatePlantServiceHistoryData(angular.copy(palntServiceHistoryDataCopy), 0, []);
		}
	};

	$scope.addPlantServiceHistoryRecord = function(actionType, tab) {
		if(deactivateData.length > 0 && actionType=="Add"){
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		var plantServiceHistoryPopUp = PlantServiceHistoryFactory.addPlantServiceHistoryDetails(actionType, tab);
		plantServiceHistoryPopUp.then(function(data) {
			$scope.tableData = populatePlantServiceHistoryData(data.plantServiceClassificationMstrTOs, 0, []);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while saving Plant Service History Details", "Error");
		});
	}, $scope.deactivatePlantServiceHistoryRecord = function() {
		if (deactivateData == undefined || deactivateData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Plant Service Record to Deactivate", "Warning");
			return;
		}
		var plantDeactivateReq = {
			"plantServiceIds" : deactivateData,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			PlantServiceHistoryService.deactivatePlantServiceDetails(plantDeactivateReq).then(function(data) {
				GenericAlertService.alertMessage("Plant Service History(s) Deactivated successfully", "Info");
				$scope.getPlantServiceHistoryDetails();
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while Deactivating Plant Service History Details", "Error");
			});
		}, function(data) {
			$scope.getPlantServiceHistoryDetails();
			deactivateData = [];
		})
	};

	$scope.plantServiceClassItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'plantServiceClassificationMstrTOs');
	};

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
			template: 'views/help&tutorials/plantservicehistoryhelp.html',
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