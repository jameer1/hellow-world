'use strict';
app.factory('CompanyCurrentProjectsFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "CompanyService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, CompanyService, GenericAlertService) {
	var projectPopUp;
	var service = {};
	service.projectPopUp = function(actionType, editProjs, companyId, data) {
		var deferred = $q.defer();
		projectPopUp = ngDialog.open({
			template : 'views/centrallib/companylist/currentprojpopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				var selectedProject = [];
				$scope.projectList = [];
				$scope.currentProjs = [];
				$scope.action = actionType;
				var proj = {
					'cmpId' : companyId,
					'projCode' : '',
					'projName' : '',
					'contractValue' : '',
					'startDate' : '',
					'finishDate' : '',
					'performance' : '',
					'status' : '1',
					'selected' : false
				};
				if (actionType === 'Add') {
					$scope.projectList.push(angular.copy(proj));
				} else {
					$scope.projectList = angular.copy(editProjs);
					editProjs = [];
				}
				$scope.addRows = function() {
					$scope.projectList.push(angular.copy(proj));
				}
				
				$scope.datePickerChange=function(startDate){
					$scope.timeFlag=true;
				}
				$scope.projdetails = function projdetails(index, selectId) {
					ngDialog.open({
						template : 'views/centrallib/companylist/projdetails.html',
						closeByDocument : false,
						showClose : false,
						className : 'ngdialog-theme-plain ng-dialogueCustom2',
						controller : [ '$scope', function($scope) {
							var selectedProject = [];
							$scope.projCode = '';
							$scope.projName = '';
							$scope.projId = '';
							$scope.indexVal = null;
							$scope.currentProjsDetails = [];
							$scope.startDate = new Date();
							$scope.finishDate = new Date();
							$scope.getCurrentProjs = function() {
								var req = {
									"status" : 1
								}
								CompanyService.getProjectsByClient(req).then(function(data) {
									$scope.currentProjsDetails = data.epsProjs;
								});
							}
							$scope.projDetailsPopUp = function(projData) {
								$scope.projCode = projData.projCode;
								$scope.projName = projData.projName;
								$scope.projId = projData.projId;
								$scope.indexVal = index;
								$scope.closeThisDialog();
							}
						} ]
					}).closePromise.then(function(value) {
						$scope.projectList[value.$dialog.scope().indexVal].projCode = value.$dialog.scope().projCode;
						$scope.projectList[value.$dialog.scope().indexVal].projName = value.$dialog.scope().projName;
						$scope.projectList[value.$dialog.scope().indexVal].projId = value.$dialog.scope().projId;
					});
				}
				$scope.savePojects = function() {
					 var flag = false;
					angular.forEach($scope.projectList, function(value) {
						if(value.startDate=="" || value.startDate==null){
							GenericAlertService.alertMessage('Please enter start date', 'Warning');
							forEach.break();
							return;
						}
						var startDate = new Date(value.startDate);
						var finishDate = new Date(value.finishDate);
						if (startDate > finishDate) {
							GenericAlertService.alertMessage('Start date must be < Finish date', 'Warning');
							forEach.break();
							return;
						}
					   
						for (var i = 0; i < data.length; i++) {
							if (data[i].projCode == value.projCode && data[i].contractValue == value.contractValue && data[i].startDate == value.startDate && data[i].finishDate == value.finishDate && data[i].performance == value.performance) {
								flag = true;
							}
						}	
					})
					if (flag) {
							GenericAlertService.alertMessage('Duplicate Current Projects are not allowed', 'Warning');
							return;
						}
					angular.forEach($scope.projectList, function(value) {
						if(value.projCode==null || value.projName==null ||value.projCode==undefined || value.projName==undefined || value.projCode==""|| value.projName==""){
							GenericAlertService.alertMessage('Please select Project Id', 'Warning');
							
							forEach.break();
							return;
						}
						
						
					})
							var req = {
								"companyProjectsTOs" : $scope.projectList,
								'companyId' : companyId,
							}
					blockUI.start();
							CompanyService.saveCompanyCurrentProjs(req).then(function(data) {
								blockUI.stop();
								var results = data.companyProjectsTO;
								// var succMsg = GenericAlertService.alertMessageModal(' Company Current Project(s) is/are ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal(' Company Current Project(s) saved successfully',"Info");
								succMsg.then(function(popData) {
									ngDialog.close(projectPopUp);
									var returnPopObj = {
										"companyProjectsTO" : results
									};
									deferred.resolve(returnPopObj);
								});
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage('Project(s) is/are failed to save', 'Error');

							});
				}, $scope.projectPopUpRowSelect = function(proj) {
					if (proj.selected) {
						selectedProject.push(proj);
					} else {
						selectedProject.splice(selectedProject.indexOf(proj), 1);
					}
				}, $scope.deleteProjRows = function() {
					if (selectedProject.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedProject.length < $scope.projectList.length) {
						angular.forEach(selectedProject, function(value, key) {
							$scope.projectList.splice($scope.projectList.indexOf(value), 1);
						});
						selectedProject = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if(selectedProject.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
					} else if(selectedProject.length == 1){
						$scope.projectList[0] ={
								'cmpId' : companyId,
								'projCode' : '',
								'projName' : '',
								'contractValue' : '',
								'startDate' : '',
								'finishDate' : '',
								'performance' : '',
								'status' : '1',
								'selected' : false
							};
							selectedProject = [];
							GenericAlertService.alertMessage(	'Please leave atleast one row',"Warning");
						}
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
