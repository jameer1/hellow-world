'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("tangibleclass", {
		url: '/tangibleclass',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/tangibleclass/tangibleclass.html',
				controller: 'TangibleClassController'
			}
		}
	})
}]).controller("TangibleClassController", ["$rootScope", "$scope", "$q", "utilservice", "ngDialog", "blockUI", "TangibleClassService", "MeasureService", "GenericAlertService", "generalservice", "TreeService", function ($rootScope, $scope, $q, utilservice, ngDialog, blockUI,
		TangibleClassService, MeasureService, GenericAlertService, generalservice, TreeService) {

	var deleteTangibleData = [];
	$scope.TangibleData = [];
	$scope.sortType = 'code',
	$scope.sortReverse = false;
	$scope.searchProject = {};
	$scope.searchForm = {
		'name' : '',
		'code' : ''
	};
	var deferred = $q.defer();

	$scope.getTangibleGroups = function () {
		var tangibleName, tangibleCode;
		if ($scope.searchForm.name) {
			tangibleName = $scope.searchForm.name;
		}
		if ($scope.searchForm.code) {
			tangibleCode = $scope.searchForm.code;
		}
		var req = {
			"status": 1,
			"tangibleName": tangibleName,
			"tangibleCode": tangibleCode
		};
		TangibleClassService.getCentralTangible(req).then(function (data) {
			deleteTangibleData = [];
			$scope.TangibleData = populateTangibleClassData(data.tangibleClassTOs, 0, []);
		}, function (error) {
			console.log(error)
			GenericAlertService.alertMessage("Error occured while getting Tangible Groups", 'Error');
		});
	};

	function populateTangibleClassData(data, level, tangibleClassTOs, isChild, parent) {
		return TreeService.populateTreeData(data, level, tangibleClassTOs, 'code', 'childTangibleItemTOs',
			isChild, parent)
	}

	$scope.tangibleSelect = function (tab) {
		if (tab.select) {
			utilservice.addItemKeyValueToArray(deleteTangibleData, "id", tab);
		} else {
			deleteTangibleData.splice(deleteTangibleData.indexOf(tab.id), 1);
		}
	};
	$scope.resetTangibleData = function () {
		$scope.TangibleData = [];
		$scope.searchForm.name = "";
		$scope.searchForm.code = "";
		$scope.getTangibleGroups();
	}



	var tangiblepopup;
	var addTangibleservice = {};
	$scope.editTangibleDetails = function (actionType, itemData) {
		if (deleteTangibleData > 0 && actionType === "Add") {
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}


		tangiblepopup = addTangibleservice.addTangibleDetails(actionType, itemData);
		tangiblepopup.then(function (data) {
			$scope.TangibleData = populateTangibleClassData(data.tangibleClassTOs, 0, []);
		}, function (error) {
			GenericAlertService.alertMessage("Error Occured While Getting  Tangible Groups", "Error");
		});
	}

	addTangibleservice.addTangibleDetails = function (actionType, itemData) {
		var deferred = $q.defer();
		tangiblepopup = ngDialog.open({
			template: 'views/centrallib/tangibleclass/tangibleclasspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.pcode = null;
				$scope.action = actionType;
				$scope.editTangibleData = [];
				$scope.proCategory = [];
				$scope.measurements = [];
				$scope.projTangibleId = null;
				$scope.popupTangibleClassClick = function (item, expand) {
					TreeService.dynamicTreeItemClick($scope.editTangibleData, item, expand);
				};
				if (itemData) {
					$scope.pcode = itemData.name;
					$scope.projTangibleId = itemData.id;
				}
				if (actionType === 'Add') {
					$scope.editTangibleData.push({
						"select": false,
						"clientId": $scope.clientId,
						"parentId": null,
						"item": false,
						"deleteFlag": false,
						"status": 1,
						"code": '',
						"name": '',
						"childTangibleItemTOs": [],
						"level": 0,
						"collapse": false,
						"expand": true,
					});
					$scope.editTangibleData = TreeService.populateDynamicTreeData($scope.editTangibleData, 0, [],
						'code', 'childTangibleItemTOs');
				} else {
					$scope.editTangibleData = TreeService.populateDynamicTreeData(angular.copy([itemData]), 0, [],
						'code', 'childTangibleItemTOs');
					itemData = [];
				}

				$scope.addTangibleSubGroup = function (tabData, index) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData, index)) {
						const itemToAdd = angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"item": false,
							"deleteFlag": false,
							"status": 1,
							"code": null,
							"name": null,
							"childTangibleItemTOs": [],
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editTangibleData = TreeService.addItemToTree($scope.editTangibleData, tabData, itemToAdd, index);
					}

				}
				$scope.addTangibleItem = function (tabData, index) {
					// check for input fileds validations
					if ($scope.checkDataValidationRecursively(tabData, index)) {
						const itemToAdd = angular.copy({
							"select": false,
							"clientId": $scope.clientId,
							"parentId": tabData.id,
							"status": 1,
							"code": null,
							"name": null,
							"item": true,
							"deleteFlag": false,
							"measureUnitTO": '',
							"measureId": null,
							"level": (tabData.level + 1),
							"collapse": false,
							"expand": true,
						});
						$scope.editTangibleData = TreeService.addItemToTree($scope.editTangibleData, tabData, itemToAdd, index);
					}
				}
				$scope.deleteTangibleClass = function (index) {
					TreeService.deleteDynamicTreeItem($scope.editTangibleData, index);
				}
				$scope.getMeasuresByProcureType = function () {
					var req = {
						"status": 1,
//						"procureClassName": $scope.procCategory[1].name
					};
					MeasureService.getMeasuresByProcureType(req).then(function (data) {
						$scope.proCategory = data.measureUnitTOs;

					});
				}, $scope.updatePlantCode = function (data, tab) {
					tab.measureId = data.id;
				};

				$scope.saveTangibleGroups = function () {
					if ($scope.checkDataValidationRecursively($scope.editTangibleData[0], 0)) {
						const data = TreeService.extractTreeDataForSaving($scope.editTangibleData, 'childTangibleItemTOs');
						var tangiblesaveReq = {
							"tangibleClassTOs": data,
						};
						if(actionType === 'Add' && checkForDuplicateGroupId()){
							GenericAlertService.alertMessage("Duplicate Tangible Group ID", "Warning");
							return;
						}
						blockUI.start();
						TangibleClassService.saveTangibleGroups(tangiblesaveReq).then(function (data) {
							blockUI.stop();
							if (data.status != null && data.status !== undefined && data.status === 'Info') {
								var results = data.tangibleClassTOs;
								// var succMsg = GenericAlertService.alertMessageModal('Tangible Group  Details   is/are ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal('Tangible Group Detail(s) saved successfully','Info');
								succMsg.then(function (data) {
									$scope.closeThisDialog(tangiblepopup);
									var returnPopObj = {
										"tangibleClassTOs": results
									};
									deferred.resolve(returnPopObj);
								}, function (error) {
									blockUI.stop();
									GenericAlertService.alertMessage("Tangible Group  Details is/are failed to Save", "Error");
								});
							}
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage("Tangible Group  Details is/are failed to Save", "Error");
						});
					}
			
				};

				$scope.checkDataValidationRecursively = function (tabData, index) {
					if (!tabData.code || !tabData.name) {
						tabData.invalidField = true;
						return false;
					}

					if (tabData.item && !tabData.measureId) {
						tabData.invalidField = true;
						return false;
					}

					for (++index; index < $scope.editTangibleData.length; index++) {
						return $scope.checkDataValidationRecursively($scope.editTangibleData[index], index);
					}
					return true;

				};

				function checkForDuplicateGroupId(){
					for (const group of $scope.TangibleData) {
						if (!group.parentId && group.code && group.code.toUpperCase() === $scope.editTangibleData[0].code.toUpperCase()) {
							return true;
						}
					}
					return false;
				}
			}]

		});
		return deferred.promise;
	}

	$scope.deleteTangibleGroups = function () {
		if (deleteTangibleData == undefined || deleteTangibleData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Tangible to deactivate", 'Warning');
			return;
		}
		var tangibleDeactivateReq = {
			"tangibleIds": deleteTangibleData,
			"status": 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
			TangibleClassService.deleteTangibleGroups(tangibleDeactivateReq).then(function (data) {
				GenericAlertService.alertMessage("Tangible Group(s) Deactivated successfully", "Info");
				deleteTangibleData = [];
				$scope.getTangibleGroups();
			}, function (error) {
				GenericAlertService.alertMessage('Tangible Group(s) is/are failed to deactivate', "Error");
			});
		}, function (data) {
			$scope.getTangibleGroups();
		})
	}

	
	$scope.tangibleClassItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childTangibleItemTOs');
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
			template: 'views/help&tutorials/Enterprisehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	var commonService = {};
	$scope.groupPage = function () {
		var group = commonService.grouping();
		group.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	$scope.sortPage = function () {
		var sort = commonService.sorting();
		sort.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var grouppagepopup;
	var sortpagepopup;
	commonService.grouping = function () {
		var deferred = $q.defer();
		grouppagepopup = ngDialog.open({
			template: 'views/groupingsorting/centrallibrary/tangroup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	commonService.sorting = function () {
		var deferred = $q.defer();
		sortpagepopup = ngDialog.open({
			template: 'views/groupingsorting/centrallibrary/tansort.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);
