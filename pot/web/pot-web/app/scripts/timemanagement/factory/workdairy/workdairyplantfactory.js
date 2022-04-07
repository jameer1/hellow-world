'use strict';
app.factory('WorkDairyPlantFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "WorkDiaryService", "ProjectCrewPopupService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, WorkDiaryService, ProjectCrewPopupService) {
	var manpowerFactoryPopup;
	var service = {};
	service.getPlantRegDetails = function(workDairySearchReq, workDairyCostCodes,plantMap) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : workDairySearchReq.projectLabelKeyTO.projId,
			"crewId" : workDairySearchReq.crewLabelKeyTO.id,
			"workDairyDate" : workDairySearchReq.workDairyDate
		};
		var popup = WorkDiaryService.getPlantRegDetails(req);
		popup.then(function(data) {
			var openPopup = service.openPlantRegPopup(data.labelKeyTOs, workDairySearchReq, workDairyCostCodes,plantMap);
			openPopup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting  Plants", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Plants", 'Error');
		});
		return deferred.promise;

	}, service.openPlantRegPopup = function(plantRegLabelKeyTOs, workDairySearchReq, workDairyCostCodes,plantMap) {
		var deferred = $q.defer();
		manpowerFactoryPopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairyplantreg.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.plantMap=plantMap;
			
				$scope.crewLabelKeyTO = [];
				$scope.plantRegLabelKeyTOs = [];
				$scope.crewLabelKeyTO.code=workDairySearchReq.crewLabelKeyTO.code;
				plantRegLabelKeyTOs.map((plant) => {
					if (!plantMap[plant.id]) {
						$scope.plantRegLabelKeyTOs.push(plant);
					}
				});
				
				var selectedPlantRegTOs = [];
				$scope.getPlantRegDetails = function(crewLabelKeyTO) {
					var req = {
						"status" : 1,
						"projId" : workDairySearchReq.projectLabelKeyTO.projId,
						"crewId" : crewLabelKeyTO.id,
						"workDairyDate" : workDairySearchReq.workDairyDate
					};
					var popup = WorkDiaryService.getPlantRegDetails(req);
					popup.then(function(data) {
						$scope.plantRegLabelKeyTOs = data.labelKeyTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting plants", 'Error');
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
						$scope.getPlantRegDetails(crewLabelKeyTO);
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Crews", 'Error');
					});
				}, $scope.selectPlant = function(plantObj) {
					if (plantObj.select) {
						selectedPlantRegTOs.push(plantObj);
					} else {
						selectedPlantRegTOs.pop(plantObj);
					}
				}, $scope.addPlantsToWorkDairy = function() {
					var workDairyPlantCostDtlTOs = [];
					angular.forEach(workDairyCostCodes, function(value, key) {
						workDairyPlantCostDtlTOs.push(angular.copy({
							"costId" : value.costId,
							"code" : value.code,
							"usedTime" : null,
							"idleTime" : null,
							"workDairyId" : workDairySearchReq.workDairyId,
							"status" : 1
						}))
					});
					var workDairyPlantStatusTOs = []
					workDairyPlantStatusTOs
							.push({
								"plantStatusId" : null,
								"status" : 1,
								"workDairyPlantCostDtlTOs" : workDairyPlantCostDtlTOs

							});
					var workDairyPlantDtlTOs = [];
					angular.forEach(selectedPlantRegTOs, function(value, key) {
						workDairyPlantDtlTOs.push(angular.copy({
							"workDairyId" : workDairySearchReq.workDairyId,
							"plantRegId" : value.id,
							"code" : value.code,
							"name" : value.name,
							"shiftName" : null,
							"status" : 1,
							"plantRegNum" : value.displayNamesMap['plntRegNo'],
							"classType" : value.displayNamesMap['classType'],
							"manufacture" : value.displayNamesMap['plntManfature'],
							"model" : value.displayNamesMap['plntModel'],
							"cmpCategoryName" : value.displayNamesMap['cmpCatgName'],
							"procureCatg" : value.displayNamesMap['procureCatg'],
							"workDairyPlantStatusTOs" : workDairyPlantStatusTOs
						}));
					});
					var req = {
						"workDairyPlantDtlTOs" : workDairyPlantDtlTOs
					};
					GenericAlertService.alertMessageModal("Plants added to Work Diary", "Info");
					$scope.closeThisDialog();
					deferred.resolve(req);
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
