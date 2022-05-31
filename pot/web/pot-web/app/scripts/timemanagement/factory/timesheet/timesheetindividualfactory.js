'use strict';
app.factory('TimeSheetIndividualFactory', ["ngDialog", "$q", "blockUI", "$filter", "TimeSheetService", "CrewPopupTimeSheetFactory", "EmpAttendanceService", "$timeout", "$rootScope", "GenericAlertService", "EmpRegisterPopUpService", function(ngDialog, $q, blockUI,$filter, TimeSheetService, CrewPopupTimeSheetFactory, EmpAttendanceService, $timeout, $rootScope, GenericAlertService,
		EmpRegisterPopUpService) {
	var deferred = $q.defer();
	
	var service = {};
	service.getEmpRegDetails = function(maxHrs, additional, timeSheetSearchReq, crewTypeId, existingEmpMap) {
		console.log(timeSheetSearchReq)
		console.log(crewTypeId)
	//	$scope.crewTypeId = crewTypeId;
	//	$scope.existingEmpMap = existingEmpMap;
		console.log(existingEmpMap)
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
		console.log(req)		
		var empPopup = TimeSheetService.getOtherCrewEmpAttendance(req);
		empPopup.then(function(data) {
			console.log(data)
			var popupData = service.openEmpRegPopup(maxHrs, additional,timeSheetSearchReq, crewTypeId, existingEmpMap, data.empRegLabelKeyTOs);
			popupData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee details", 'Error');
		});
		return deferred.promise;
	}, service.openEmpRegPopup = function(maxHrs, additional, timeSheetSearchReq, crewTypeId, existingEmpMap, empRegLabelKeyTOs) {
		var deferred = $q.defer();
		var servicePopup = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/individualemployeelist.html',
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
					var date = new Date(timeSheetSearchReq.weekStartDate);
					console.log(date)
					console.log(req)
					var empPopup = TimeSheetService.getOtherCrewEmpAttendance(req);
					empPopup.then(function(data) {
						$scope.empRegLabelKeyTOs = data.empRegLabelKeyTOs;
						console.log($scope.empRegLabelKeyTOs)
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
						console.log(man)
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
					console.log(empRegTos)
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
					console.log(req)
					blockUI.start();
					TimeSheetService.addEmpRegToTimeSheet(req).then(function(data) {
						console.log(data);
						blockUI.stop();
						for (const empReg of selectedEmpRegTOs) {
							for(const empDtlTo of data.timeSheetEmpDtlTOs){
								if(empReg.id === empDtlTo.empRegId){
									empDtlTo.empDetailLabelKeyTO = empReg;
								}

							}
						}
						var saveEmpReq = {
						"timeSheetEmpDtlTOs" : data.timeSheetEmpDtlTOs,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"empId" :  data.timeSheetEmpDtlTOs[0].empRegId,
							"weekCommenceDay" : $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
							//"weekCommenceDay" : $scope.timeSheetSearchReq.weekCommenceDay,
							"apprStatus" : null,
							"additional" : additional,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"maxHrs" : maxHrs
						},
						"status" : 1
					  };
					  console.log(saveEmpReq);
					  TimeSheetService.saveIndividualTimeSheetDetails(saveEmpReq).then(function(data) {
						deferred.resolve(data);
						$scope.closeThisDialog();
						GenericAlertService.alertMessage("Employee Details are added", "Info");
			         },	function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Employee Details are Failed to add ', "Warning");
					   })
					  });	
					}
				$scope.saveIndividualTimeSheetDetails = function(data) {
					console.log(data)
					console.log(timeSheetSearchReq)
			//		console.log($scope.timeSheetDetails)
			//		console.log(empRegTos)
			        $scope.timeSheetEmpDtlTOs=data.timeSheetEmpDtlTOs;
			        console.log($scope.timeSheetEmpDtlTOs)
			        console.log(timeSheetSearchReq.timeSheetLabelKeyTo.id, timeSheetSearchReq.timeSheetLabelKeyTo.code, timeSheetSearchReq.projectLabelKeyTO.projId, $scope.timeSheetEmpDtlTOs[0].empRegId, $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"), timeSheetSearchReq.weekStartDate, timeSheetSearchReq.weekEndDate)
					var saveEmpReq = {
						"timeSheetEmpDtlTOs" : data.timeSheetEmpDtlTOs,
						"timeSheetTO" : {
							"status" : 1,
							"id" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
							"code" : timeSheetSearchReq.timeSheetLabelKeyTo.code,
							"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
							"empId" :  $scope.timeSheetEmpDtlTOs[0].empRegId,
							"weekCommenceDay" : $filter('date')(new Date(timeSheetSearchReq.weekCommenceDay), "dd-MMM-yyyy"),
							//"weekCommenceDay" : $scope.timeSheetSearchReq.weekCommenceDay,
							"apprStatus" : null,
							"additional" : additional,
							"weekStartDate" : timeSheetSearchReq.weekStartDate,
							"weekEndDate" : timeSheetSearchReq.weekEndDate,
							"maxHrs" : maxHrs
						},
						"status" : 1
					};
					console.log(saveEmpReq);
					blockUI.start();
					TimeSheetService.saveIndividualTimeSheetDetails(saveEmpReq).then(function(data) {
						console.log()
						blockUI.stop();
						if (data.status == "Warning") {
							GenericAlertService.alertMessage("Time Sheet hours cannot be booked more than max hours ", "Warning");
							return;
						} else {
							var resultData = GenericAlertService.alertMessageModal('Employee(s) saved successfully', "Info");
							$scope.closeThisDialog();
							resultData.then(function() {
								deferred.resolve(data);
							});
						}
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Emp(s) popup are failed to save', "Error");
					});
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
