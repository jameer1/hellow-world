'use strict';
app.factory('WorkDairyEmpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "WorkDiaryService", "ProjectCrewPopupService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, WorkDiaryService, ProjectCrewPopupService) {
	var manpowerFactoryPopup;
	var service = {};
	service.getEmpRegDetails = function(workDairySearchReq, workDairyCostCodes,existingMap) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : workDairySearchReq.projectLabelKeyTO.projId,
			"crewId" : workDairySearchReq.crewLabelKeyTO.id,
			"workDairyDate" : workDairySearchReq.workDairyDate
		};
		var empPopup = WorkDiaryService.getEmpRegDetails(req);
		empPopup.then(function(data) {
			var openPopup = service.openEmpRegPopup(data.labelKeyTOs, workDairySearchReq, workDairyCostCodes,existingMap);
			openPopup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting  Employees", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Employees", 'Error');
		});
		return deferred.promise;

	}, service.openEmpRegPopup = function(empRegLabelKeyTOs, workDairySearchReq, workDairyCostCodes,existingMap) {
		var deferred = $q.defer();
		manpowerFactoryPopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairyempreg.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.existingMap = existingMap;
				$scope.crewLabelKeyTO = [];
		
				$scope.crewLabelKeyTO.code=workDairySearchReq.crewLabelKeyTO.code;
				
			
				
				$scope.empRegLabelKeyTOs = empRegLabelKeyTOs;
				var selectedEmpRegTOs = [];
				$scope.getEmpRegDetails = function(crewLabelKeyTO) {
					
					var req = {
						"status" : 1,
						"projId" : workDairySearchReq.projectLabelKeyTO.projId,
						"crewId" : crewLabelKeyTO.id,
						"workDairyDate" : workDairySearchReq.workDairyDate
					};
					var empPopup = WorkDiaryService.getEmpRegDetails(req);
					empPopup.then(function(data) {
						$scope.empRegLabelKeyTOs = data.labelKeyTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting empployees", 'Error');
					});
				}, $scope.getCrewList = function(crewLabelKeyTO) {
					var crewReq = {
						"status" : 1,
						"projId" : workDairySearchReq.projectLabelKeyTO.projId
					};

					var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
					crewSerivcePopup.then(function(data) {
					
						crewLabelKeyTO.id = data.projCrewTO.id;
						crewLabelKeyTO.code = data.projCrewTO.code;
						$scope.getEmpRegDetails(crewLabelKeyTO);
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Crews", 'Error');
					});
				}, $scope.selectEmpReg = function(empObj) {
					if (empObj.select) {
						selectedEmpRegTOs.push(empObj);
					} else {
						selectedEmpRegTOs.pop(empObj);
					}
				}, $scope.addEmpToWorkDairy = function() {
					if (selectedEmpRegTOs.length === 0) {
			               GenericAlertService.alertMessage('Please select atleast one employee to add', "Warning");
			                return;
		             }
					var workDairyEmpTOs = [];
					var workDairyEmpCostDtlTOs = [];
					angular.forEach(workDairyCostCodes, function(value, key) {
						workDairyEmpCostDtlTOs.push(angular.copy({
							"costId" : value.costId,
							"code" : value.code,
							"usedTime" : null,
							"idleTime" : null,
							"workDairyId" : workDairySearchReq.workDairyId,
							"status" : 1
						}))
					});
					var workDairyEmpWageTOs = []
					workDairyEmpWageTOs.push({
						"wageId" : null,
						"status" : 1,
						"workDairyEmpCostDtlTOs" : workDairyEmpCostDtlTOs
					});
					angular.forEach(selectedEmpRegTOs, function(value, key) {
						workDairyEmpTOs.push({
							"workDairyId" : workDairySearchReq.workDairyId,
							"empRegId" : value.id,
							"code" : value.code,
							"classType" : value.displayNamesMap.classType,
							"cmpCatgName" : value.displayNamesMap.cmpCatgName, 
							"firstName" : value.displayNamesMap.firstName,
							"gender" : value.displayNamesMap.gender,
							"lastName" : value.displayNamesMap.lastName,
							"procureCatg" : value.displayNamesMap.procureCatg,
							"status" : 1,
							"workDairyEmpWageTOs" : workDairyEmpWageTOs,
							"unitOfMeasure" : value.unitOfMeasure
						});
					});

					var req = {
						"workDairyEmpDtlTOs" : workDairyEmpTOs
					}
					
					var resultData = GenericAlertService.alertMessageModal('Employee(s) added successfully to Work Diary ', "Info");
					resultData.then(function() {
						$scope.closeThisDialog();
						deferred.resolve(req);
					});
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
