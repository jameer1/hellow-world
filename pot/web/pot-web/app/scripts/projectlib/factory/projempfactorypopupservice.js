'use strict';
app.factory('ProjectEmpClassificationService', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "ProjEmpClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, ProjEmpClassService, GenericAlertService) {
	var projEmpClassifyPopUp;
	var service = {};
	service.projEmpClassifyPopup = function(actionType, editEmpClass, projId) {
		var deferred = $q.defer();
		projEmpClassifyPopUp = ngDialog.open({
			template : 'views/projectlib/empclass/projemppopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var copyEditArray = angular.copy(editEmpClass);
				var emptyProjEmpClassifyObj = {};
				var selectedEmp = [];
				$scope.resourceData = [];
				$scope.catgData = [];
				$scope.addprojetemp = [];
				$scope.action = actionType;
				if (actionType == 'Edit') {
					$scope.addprojetemp = angular.copy(editEmpClass);
				}
				var onLoadReq = {
					"status" : 1,
					"projId" : projId
				};
				ProjEmpClassService.addProjEmpClassifyOnLoad(onLoadReq).then(function(data) {
					$scope.catgData = data.projEmpCatgTOs;
					$scope.resourceData = data.empClassTOs;
					$scope.unitOfMeasures = data.measureUnitTOs;
					if (actionType == 'Add') {
						emptyProjEmpClassifyObj = data.projEmpClassTO;
						var localprojEmpClassTO = angular.copy(emptyProjEmpClassifyObj);
						$scope.addprojetemp.push(localprojEmpClassTO);
					}
				});
				$scope.addEmpClass = function() {
					var localprojEmpClassTO = angular.copy(emptyProjEmpClassifyObj);
					$scope.addprojetemp.push(localprojEmpClassTO);
				}, $scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedEmp.push(tab);
					} else {
						selectedEmp.splice(selectedEmp.indexOf(tab), 1);
					}
				}

				$scope.uiDelete = function() {
					if (selectedEmp.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedEmp.length < $scope.addprojetemp.length) {
						angular.forEach(selectedEmp, function(value, key) {
							$scope.addprojetemp.splice($scope.addprojetemp.indexOf(value), 1);
						});
						selectedEmp = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if(selectedEmp.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
					}
					else if(selectedEmp.length == 1){
					$scope.addprojetemp[0] = {
						'empClassTO.code' : '',
						'empClassTO.name' : '',
						'projEmpCatgTO.code' : '',
						'tradeContrName' : '',
						'unionWorkerName' : '',
						'measureUnitTO.name' : '',
						'status' : '1',
						'selected' : false
					};
					selectedEmp = [];
					GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
				}
				}
				
				$scope.getProjEmpClassMap = function() {
					var req = {
							"projId" : projId
					}
					ProjEmpClassService.getProjEmpClassMap(req).then(function(data) {
					$scope.uniqueCodeMap = data.projUniqueCodeMap;
					})
					}, 
				$scope.saveEmpClass = function() {
					var flag = false;
					var empMap = [];
					angular.forEach($scope.addprojetemp, function(value, key) {
						if ((empMap[value.projEmpCatgTO.id] != null) &&  (empMap[ value.empClassTO.id] != null)) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							empMap[value.projEmpCatgTO.id ] = true;
							empMap[value.empClassTO.id ] = true;
						}
					});
						 if (actionType === 'Add'){				
								angular.forEach($scope.addprojetemp, function(value, key) {
									 if ($scope.uniqueCodeMap[value.empClassTO.id + "-" + value.projEmpCatgTO.id ] != null) {
										value.duplicateFlag = true;
										flag = true;
									}
								});
								}else{							
									angular.forEach($scope.addprojetemp, function(value, key) {
										angular.forEach(copyEditArray, function(value1, key) {
											if(value1.empClassTO.id + "-" + value.projEmpCatgTO.id  == value.empClassTO.id + "-" + value.projEmpCatgTO.id  ){
												value.duplicateFlag = false;
												flag = false;
											}else{
												if ($scope.uniqueCodeMap[value.empClassTO.id + "-" + value.projEmpCatgTO.id ] != null) {
													value.duplicateFlag = true;
													flag = true;
												} else {
													value.duplicateFlag = false;
												}
											}
										});
									});
								}
						 if (flag) {
						 GenericAlertService.alertMessage('Duplicate emp codes are not allowed', "Warning");
						 return;
						 }
					var empProjSaveReq = {
						"projEmpClassTOs" : $scope.addprojetemp,
						"projId" : projId
					};
					blockUI.start();
					ProjEmpClassService.saveProjEmpClasses(empProjSaveReq).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var projectClassTOs = data.projEmpClassTOs;
							var succMsg = GenericAlertService.alertMessageModal('Employee(s) is/are ' + data.message, data.status);
							succMsg.then(function(data) {
								var returnPopObj = {
									"projEmpClassTOs" : projectClassTOs
								};
								deferred.resolve(returnPopObj);
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					}, function(error) {
						GenericAlertService.alertMessage("Record already exist in active(OR)deactive status", 'Error');
					});
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
