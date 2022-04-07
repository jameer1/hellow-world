'use strict';
app.factory('TimeSheetEmpRegFactory', ["ngDialog", "$q", "blockUI", "$filter", "TimeSheetService", "CrewPopupTimeSheetFactory", "EmpAttendanceService", "$timeout", "$rootScope", "GenericAlertService", "EmpRegisterPopUpService", function(ngDialog, $q, blockUI,$filter, TimeSheetService, CrewPopupTimeSheetFactory, EmpAttendanceService, $timeout, $rootScope, GenericAlertService,
		EmpRegisterPopUpService) {
	var deferred = $q.defer();
	
	var service = {};
	service.getEmpRegDetails = function(timeSheetSearchReq, crewTypeId, existingEmpMap) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
			"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
			"fromCrewId" : timeSheetSearchReq.crewLabelKeyTO.id,
			"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
			"weekStartDate" : timeSheetSearchReq.weekStartDate,
			"weekEndDate" : timeSheetSearchReq.weekEndDate
		};		
		var empPopup = TimeSheetService.getOtherCrewEmpAttendance(req);
		empPopup.then(function(data) {
			var popupData = service.openEmpRegPopup(timeSheetSearchReq, crewTypeId, existingEmpMap, data.empRegLabelKeyTOs);
			popupData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee details", 'Error');
		});
		return deferred.promise;
	}, service.openEmpRegPopup = function(timeSheetSearchReq, crewTypeId, existingEmpMap, empRegLabelKeyTOs) {

		var deferred = $q.defer();
		var servicePopup = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/employeelist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.crewLabelKeyTO = [];

				$scope.crewLabelKeyTO.code = timeSheetSearchReq.crewLabelKeyTO.code;
				$scope.existingEmpMap = existingEmpMap;
				$scope.empRegLabelKeyTOs = empRegLabelKeyTOs;
				$scope.searchFlag = false;
				var selectedEmpRegTOs = [];
				$scope.getEmpRegDetails = function(crewId) {
					var req = {
						"status" : 1,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
						"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
						"fromCrewId" : crewId,
						"weekCommenceDay" :$filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
						"weekStartDate" : timeSheetSearchReq.weekStartDate,
						"weekEndDate" : timeSheetSearchReq.weekEndDate
					};
					var empPopup = TimeSheetService.getOtherCrewEmpAttendance(req);
					empPopup.then(function(data) {
						$scope.empRegLabelKeyTOs = data.empRegLabelKeyTOs;
						if ($scope.empRegLabelKeyTOs.length <= 0) {
							$scope.searchFlag = true;
						}
						$scope.empRegLabelKeyTOs = data.empRegLabelKeyTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting employee details", 'Error');
					});
				}, $scope.getCrewList = function(crewLabelKeyTO) {

					var crewReq = {
						"status" : 1,
						"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
					};
				
					var crewSerivcePopup = CrewPopupTimeSheetFactory.getCrewList(timeSheetSearchReq.crewLabelKeyTO.id, crewReq);
					crewSerivcePopup.then(function(data) {
					
						crewLabelKeyTO.id = data.projCrewTO.id;
						crewLabelKeyTO.code = data.projCrewTO.code;
						$scope.getEmpRegDetails(crewLabelKeyTO.id);
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
					});
				}, $scope.empregpopup = function(man) {
					if (man.select) {
						selectedEmpRegTOs.push(man);
					} else {
						selectedEmpRegTOs.pop(man);
					}
				}, $scope.saveManpowerDetails = function() {
					if (crewTypeId == 2 && selectedEmpRegTOs.length > 1) {
						GenericAlertService.alertMessage("Please select only one empployee,Individual timesheet applciable for single employee", 'Warning');
						return;
					}
					if (crewTypeId == 1 && selectedEmpRegTOs.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one Employee to create Time Sheet", 'Warning');
						return;
					}
					const empRegTos = new Array();
					for (const empReg of selectedEmpRegTOs) {
						empRegTos.push({ 'id': empReg.id });
					}
					var req = {
						"timeSheetEmpRegTOs" : empRegTos,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
							"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"apprStatus" : null
						}
					};
					blockUI.start();
					TimeSheetService.addEmpRegToTimeSheet(req).then(function(data) {
						blockUI.stop();
						for (const empReg of selectedEmpRegTOs) {
							for(const empDtlTo of data.timeSheetEmpDtlTOs){
								if(empReg.id === empDtlTo.empRegId){
									empDtlTo.empDetailLabelKeyTO = empReg;
								}

							}
						}
						deferred.resolve(data);
						$scope.closeThisDialog();
						GenericAlertService.alertMessage("Employee Details are added", "Info");
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Employee Details are Failed to add ', "Warning");
					});

				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
