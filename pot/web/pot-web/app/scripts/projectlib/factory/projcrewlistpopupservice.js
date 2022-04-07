'use strict';
app.factory('ProjCrewListPopupService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjCrewListService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, ProjCrewListService, GenericAlertService) {
	var projCrewListPopUp;
	var service = {};
	service.projCrewListPopUp = function(actionType, selectedProject, editTableData) {
		var deferred = $q.defer();
		projCrewListPopUp = ngDialog.open({
			template : 'views/projectlib/crewlist/projcrewlistpopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			className:'ngdialog ngdialog-theme-plain ng-dialogueCustom4',
			controller : [ '$scope', function($scope) {
				var copyEditArray = angular.copy(editTableData);
				$scope.addTableData = [];
				var emptyProjCrewListObj = [];
				var selectedCrew = [];
				$scope.action = actionType;
				if (actionType === 'Edit') {
					$scope.addTableData = angular.copy(editTableData);
				}
				var onLoadReq = {
					"status" : 1,
					"projId" : selectedProject.projId
				};
				ProjCrewListService.projCrewListifOnLoad(onLoadReq).then(function(data) {
					$scope.shiftData = data.projWorkShiftResp.projWorkShiftTOs;
					$scope.updateResCode = function(data, tab) {
						tab.shiftId = data.id;
					}
					if (actionType === 'Add') {
						emptyProjCrewListObj = data.projCrewTO;
						var localprojCrewListTO = angular.copy(emptyProjCrewListObj);
						$scope.addTableData.push(localprojCrewListTO);
					}
				});
				$scope.addRows = function() {
					var localprojCrewListTO = angular.copy(emptyProjCrewListObj);
					$scope.addTableData.push(localprojCrewListTO);
				}, $scope.getCrewMap = function() {
					var req = {
						"status" : 1,
						"projId" : selectedProject.projId
					}
					ProjCrewListService.getCrewMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.projUniqueCodeMap;
					})
				}, $scope.checkDuplicate = function(tab) {
					tab.duplicateFlag = false;
					tab.code = tab.code.toUpperCase();
					if ($scope.uniqueCodeMap[tab.code] != null) {
						tab.duplicateFlag = true;
						return;
					}
					tab.duplicateFlag = false;
				}, $scope.saveCrewList = function() {
					var flag = false;
					var shiftTypeFlag = false;
					var crewListMap = [];
					
					angular.forEach($scope.addTableData, function(value, key) {
						
						if(value.projWorkShiftTO.code == null){
							  shiftTypeFlag = true;
							}
						if (crewListMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							crewListMap[value.code.toUpperCase()] = true;
							}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.addTableData, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					} else {
						angular.forEach($scope.addTableData, function(value, key) {
							angular.forEach(copyEditArray, function(value1, key) {
								if (value1.code == value.code) {
									value.duplicateFlag = false;
									flag = false;
								} else {
									if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
										value.duplicateFlag = true;
										flag = true;
									} else {
										value.duplicateFlag = false;
										crewListMap[value.code.toUpperCase()] = true;
									}
								}
							});
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Crew codes are not allowed', "Warning");
						return;
					}
					if(shiftTypeFlag){
						GenericAlertService.alertMessage('please select shift type', "Warning");
						return;
					}
	
					var req = {
						"projCrewTOs" : $scope.addTableData,
						"projId" : selectedProject.projId
					};
					blockUI.start();
					ProjCrewListService.saveProjCrewLists(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var projectClassTOs = data.projCrewTOs;
							// var succMsg = GenericAlertService.alertMessageModal('CrewList(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Crew List(s) saved successfully',"Info");
							succMsg.then(function(data1) {
								var returnPopObj = {
									"projCrewTOs" : projectClassTOs
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					}, function(error) {
						GenericAlertService.alertMessage('Crew List(s) failed to Save', 'Error');
					});
					ngDialog.close();
				}, $scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedCrew.push(tab);
					} else {
						selectedCrew.splice(selectedCrew.indexOf(tab), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedCrew.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedCrew.length < $scope.addTableData.length) {
						angular.forEach(selectedCrew, function(value, key) {
							$scope.addTableData.splice($scope.addTableData.indexOf(value), 1);

						});
						selectedCrew = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedCrew.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedCrew.length == 1) {
						$scope.addTableData[0] = {
							'code' : '',
							'desc' : '',
							'projWorkShiftTO.code' : '',
							'status' : '1',
							'selected' : false
						};
						selectedCrew = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
