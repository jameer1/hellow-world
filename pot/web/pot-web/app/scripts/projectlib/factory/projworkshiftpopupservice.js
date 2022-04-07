'use strict';
app.factory('ProjWorkShiftPopupService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjWorkShiftService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, ProjWorkShiftService, GenericAlertService) {
	var projWorkShiftPopUp;
	var service = {};
	service.projWorkShiftPopUp = function(actionType, selectedProject, editTableData) {
		var deferred = $q.defer();
		projWorkShiftPopUp = ngDialog.open({
			template : 'views/projectlib/workshift/workingshiftspopup.html',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			className:'ngdialog ngdialog-theme-plain ng-dialogueCustom4',
			controller : [ '$scope', function($scope) {
				var copyEditArray = angular.copy(editTableData);
				$scope.addTableData = [];
				var selectedWork = [];
				var emptyProjWorkShiftClassifyObj = [];
				var localprojWorkShiftTO = [];
				$scope.action = actionType;
				if (actionType === 'Edit') {
					$scope.addTableData = angular.copy(editTableData);
				}
				var req = {
					"status" : 1,
					"projId" : selectedProject.projId
				};
				ProjWorkShiftService.projWorkShiftOnLoad(req).then(function(data) {
					if (actionType === 'Add') {
						emptyProjWorkShiftClassifyObj = data.projWorkShiftTO;
						var localprojWorkShiftTO = angular.copy(emptyProjWorkShiftClassifyObj);
						$scope.addTableData.push(localprojWorkShiftTO);
					}
				});
				$scope.addRows = function() {
					var localprojWorkShiftTO = angular.copy(emptyProjWorkShiftClassifyObj);
					$scope.addTableData.push(localprojWorkShiftTO);
				}, 
				$scope.getWorkingShiftMap = function() {
					var req = {
						"status" : 1,
						"projId" : selectedProject.projId
					}
					ProjWorkShiftService.getWorkingShiftMap(req).then(function(data) {
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
				}, 
				$scope.saveProjWorkShifts = function() {
					var flag = false;
					var workShiftMap = [];
					angular.forEach($scope.addTableData, function(value, key) {
						if (workShiftMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							workShiftMap[value.code.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add'){	
						angular.forEach($scope.addTableData, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
						}else{	
							angular.forEach($scope.addTableData, function(value, key) {
								angular.forEach(copyEditArray, function(value1, key) {
									if(value1.code == value.code ){
										value.duplicateFlag = false;
										flag = false;
									}else{
										if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
											value.duplicateFlag = true;
											flag = true;
										} else {
											value.duplicateFlag = false;
											workShiftMap[value.code.toUpperCase()] = true;
										}
									}
								});
							});
						}
						if (flag) {
							GenericAlertService.alertMessage('Duplicate ShiftIds are not allowed', "Warning");
							return;
						}
					var req = {
						"projWorkShiftTOs" : $scope.addTableData,
						"projId" : selectedProject.projId
					};
					blockUI.start();
					ProjWorkShiftService.saveProjWorkShifts(req).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var projectClassTOs = data.projWorkShiftTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Working Shift(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Working Shift(s) saved successfully',"Info");
							succMsg.then(function(popData) {
								$scope.closeThisDialog();
								var returnPopObj = {
									"projWorkShiftTOs" : projectClassTOs
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Working Shift(s) is/are failed to Save', 'Error');
					});
				}, $scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedWork.push(tab);
					} else {
						selectedWork.splice(selectedWork.indexOf(tab), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedWork.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedWork.length < $scope.addTableData.length) {
						angular.forEach(selectedWork, function(value, key) {
							$scope.addTableData.splice($scope.addTableData.indexOf(value), 1);

						});
						selectedWork = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if(selectedWork.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
					}
					else if(selectedWork.length == 1){
					$scope.addTableData[0] = {
						'code' : '',
						'startHours' : '',
						'startMinutes' : '',
						'finishHours' : '',
						'finishMinutes' : '',
						'status' : '1',
						'selected' : false
					};
					selectedWork = [];
					GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
				}
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);
