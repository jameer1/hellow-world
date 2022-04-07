'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantattendence", {
		url : '/plantattendence',
		parent : 'site',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/timemanagement/attendance/plantattendance.html',
				controller : 'PlantAttendenceController'
			}
		}
	})
}]).controller('PlantAttendenceController', ["$scope", "uiGridGroupingConstants", "stylesService", "ngGridService", "uiGridConstants", "$q", "$state", "blockUI", "ngDialog", "AttendancePlantCreateFactory", "PlantNotificationFactory", "PlantAttendanceService", "PlantAttendanceCopyFactory", "AttendancePlantRegFactory", "EpsProjectSelectFactory", "ProjEmpClassService", "ProjectCrewPopupService", "PlantAttendSheetsFactory", "generalservice", "PotCommonService", "GenericAlertService", function($scope, uiGridGroupingConstants, stylesService, ngGridService, uiGridConstants, $q, $state, blockUI,ngDialog, AttendancePlantCreateFactory, PlantNotificationFactory,
		PlantAttendanceService, PlantAttendanceCopyFactory, AttendancePlantRegFactory, EpsProjectSelectFactory, ProjEmpClassService, 
		ProjectCrewPopupService, PlantAttendSheetsFactory, generalservice, PotCommonService,GenericAlertService) {
	$scope.stylesSvc = stylesService;
	$scope.plantFlag = false;
	$scope.searchProject = {};
	$scope.plantAttendenceDetails = [];
	$scope.plantAttendanceMap = [];
	$scope.plantAttendanceDays = [];
	$scope.plantAttendanceDaysDD = generalservice.plantAttendanceTypes;
	$scope.moreFlag = 0;
	$scope.currentMonth = false;
	$scope.plantDemobilizationDateMap=[];
	$scope.currentDate = null;
	$scope.checkAttendance = function(attendanceTypeTO) {
		$scope.plantFlag = true;
		var inputvar = attendanceTypeTO.code.toUpperCase();
		var keyId = 0;
		var mapCode = null;
		angular.forEach($scope.plantAttendanceMap, function(value, key) {
			mapCode = value.code.toUpperCase();
			if (mapCode === inputvar) {
				keyId = value.id;
			}
		});
		if (keyId > 0) {
			attendanceTypeTO.id = keyId;
		} else {
			attendanceTypeTO.id = null;
		}
		attendanceTypeTO.code = inputvar;
	}

	$scope.plantAttendReq = {
		"status" : 1,
		"crewLabelKeyTO" : {
			"id" : null,
			"code" : null,
			"name" : null
		},
		"attendenceId" : null,
		"attendenceCode":null,
		"attendenceMonth" : null
	};
	$scope.$watch('plantAttendReq.attendenceMonth', function (newValue, oldValue) {

		if (newValue != oldValue && oldValue != null) {

			$scope.plantAttendReq.crewLabelKeyTO = {};
			$scope.plantAttendReq.attendenceCode = null;
			$scope.plantAttendanceDays = [];
			return;
		}

	});
	$scope.getUserProjects = function() {
		$scope.plantAttendenceDetails = [];
		$scope.plantAttendReq.crewLabelKeyTO = {
			"id" : null,
			"code" : null,
			"name" : null
		};
		$scope.plantAttendReq.attendenceCode = null;
		$scope.plantAttendReq.attendenceMonth = null;
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
		});
	},
	$scope.resetPlantSearch = function() {
		//Confirmation Flag
		if ($scope.plantFlag == true) {
			GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function() {
				$scope.savePlantAttendanceRecords();
			}, function(error) {
				$scope.plantFlag = false;
				$scope.resetPlantSearch();
			})
			return;
		}
		$scope.searchProject = {};
		$scope.plantAttendReq = {
			"status" : 1,
			"crewLabelKeyTO" : {
				"id" : null,
				"code" : null,
				"name" : null
			},
			"attendenceId" : null,
			"attendenceCode":null,
			"attendenceMonth" : null

		};
		$scope.plantAttendenceDetails = [];
		$scope.plantAttendanceDays = [];
		$scope.plantAttendanceMap = [];
		$scope.currentMonth = false;
		$scope.plantDemobilizationDateMap=[];
	}, $scope.getPlantNotifications = function(projId) {
		if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0 || $scope.plantAttendReq.crewLabelKeyTO == undefined || $scope.plantAttendReq.crewLabelKeyTO.id <= 0) {
			GenericAlertService.alertMessage("Please select Project and Crew to get Notification details", 'Warning');
			return;
		}
		var popup = PlantNotificationFactory.getPlantNotificationDetails($scope.searchProject, $scope.plantAttendReq);
		popup.then(function(data) {
			// $scope.empAttendenceDetails = data.empAttendenceTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
		})
	},$scope.getSystemDate= function()
	{
		var deferred = $q.defer();
		$scope.currentMonth = true;
		var req = {
			"status" : 1
		};
		PotCommonService.getSystemDate(req).then(function(data) {
			var currentDate =		{
				"currentDate": data.labelKeyTO.displayNamesMap['mmm-yyyy']
			} ;
			deferred.resolve(currentDate);
		});
		return deferred.promise;
	}

	$scope.getAttendanceOnLoad = function(projId, crewId) {
	/*	$scope.currentMonth = true;
		var  sysdateObj = $scope.getSystemDate();
		sysdateObj.then(function(data) {
		$scope.plantAttendReq.attendenceMonth = data.currentDate;*/
		var getPlantAttendReq = {
			"status" : 1,
			"projId" : projId,
			"crewId" : crewId,
			"attendenceMonth" : $scope.plantAttendReq.attendenceMonth

		};
		PlantAttendanceService.getAttendanceOnLoad(getPlantAttendReq).then(function(data) {
			$scope.plantAttendReq.attendenceCode = data.plantAttendanceMstrTO.attendenceName;
			$scope.plantAttendReq.attendenceId = data.plantAttendanceMstrTO.id;
			$scope.plantAttendReq.month = data.plantAttendanceMstrTO.attendenceMonth;
			$scope.plantAttendanceDays = data.attendenceDays;
			$scope.attendenceDayMap = data.attendenceDayMap;
			$scope.plantAttendanceMap = data.plantAttendanceMap;
		},
		function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Attendance ID", 'Error');
		});
		/*},function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Attendance ID", 'Error');
		});
		*/
	}, $scope.getPlantAttendenceSheets = function(projId, crewId) {
		$scope.currentMonth = false;
		if (projId == null || crewId == null) {
			GenericAlertService.alertMessage("Please select project and crew to get Attendance ID(s)", 'Warning');
		} else {
			var popUpData = PlantAttendSheetsFactory.getPlantAttendenceSheets(projId, crewId);
			popUpData.then(function(data) {
				$scope.plantAttendReq.attendenceCode = data.attendanceObj.code;
				$scope.plantAttendReq.attendenceId = data.attendanceObj.id;
				$scope.plantAttendReq.attendenceMonth = data.attendanceObj.name;
				$scope.plantAttendanceDays = data.attendenceDays;
				$scope.gridOptions.data = angular.copy($scope.plantAttendanceDays);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Plant ", 'Error');
			});
		}
	}, $scope.getPlantAttendenceDetails = function() {
		if (!$scope.plantAttendReq.attendenceId) {
			GenericAlertService.alertMessage("No data is available, Click on Create to add Plant Attendance ", 'Warning');
			return;
		}
		if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0 || $scope.plantAttendReq.attendenceId == undefined || $scope.plantAttendReq.attendenceId <= 0 || $scope.plantAttendReq.crewLabelKeyTO == undefined || $scope.plantAttendReq.crewLabelKeyTO.id <= 0) {
			GenericAlertService.alertMessage("Please select all the search criteria to get attendence details", 'Warning');
			return;
		}
		//Confirmation Flag
		if ($scope.plantFlag == true) {
			GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function() {
				$scope.savePlantAttendanceRecords();
			}, function(error) {
				$scope.plantFlag = false;
				$scope.getPlantAttendenceDetails();
			})
			return;
		}

		var attendReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId,
			"crewId" : $scope.plantAttendReq.crewLabelKeyTO.id,
			"attendenceId" : $scope.plantAttendReq.attendenceId,
			"attendenceMonth" : $scope.plantAttendReq.attendenceMonth
		};

		PlantAttendanceService.getPlantAttendanceDetails(attendReq).then(function(data) {
			$scope.plantAttendenceDetails = data.plantAttendenceTOs;
			$scope.plantDemobilizationDateMap=data.plantDemobilizationDateMap;
			angular.forEach($scope.plantAttendenceDetails, function(value, key) {
				angular.forEach(value.plantAttendenceDtlMap, function(value1, key1) {
					if ($scope.plantAttendanceMap[value1.attendanceTypeTO.id] != null) {
						value1.attendanceTypeTO.code = $scope.plantAttendanceMap[value1.attendanceTypeTO.id].code;
					}
				});
			});
			$scope.gridOptions.data = angular.copy($scope.plantAttendenceDetails);
			if ($scope.plantAttendenceDetails.length <= 0) {
				GenericAlertService.alertMessage("Plants are not avaialble for the selected project", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
		});
	}, $scope.savePlantAttendanceRecords = function() {
		$scope.plantFlag = false;
		var attendReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId,
			"crewId" : $scope.plantAttendReq.crewLabelKeyTO.id,
			"attendenceId" : $scope.plantAttendReq.attendenceId,
			"attendenceMonth" : $scope.plantAttendReq.attendenceMonth,
			"plantAttendenceTOs" : $scope.plantAttendenceDetails
		};
		blockUI.start();
		PlantAttendanceService.savePlantAttendanceRecords(attendReq).then(function(data) {
			blockUI.stop();
			$scope.plantAttendenceDetails = data.plantAttendenceTOs;
			GenericAlertService.alertMessage("Attendance saved successfully", 'Info');
			$state.go("listofplantattendence");
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
		});

	},

	$scope.getCrewList = function() {
		if (!$scope.searchProject.projId) {
			GenericAlertService.alertMessage("Please select project to get crews", 'Warning');
			return;
		}
		if (!$scope.plantAttendReq.attendenceMonth) {
			GenericAlertService.alertMessage("Please choose Attendence Month", 'Warning');
			return;
		}
		var crewReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId
		};
		var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
		crewSerivcePopup.then(function(data) {
			$scope.plantAttendReq.crewLabelKeyTO.id = data.projCrewTO.id;
			$scope.plantAttendReq.crewLabelKeyTO.code = data.projCrewTO.code;
			$scope.plantAttendReq.crewLabelKeyTO.name = data.projCrewTO.desc;
			$scope.plantAttendenceDetails = [];
			$scope.getAttendanceOnLoad($scope.searchProject.projId, $scope.plantAttendReq.crewLabelKeyTO.id);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
		});

	}, 
	$scope.clickForward = function(moreFlag) {
		if ($scope.moreFlag <= 32) {
			$scope.moreFlag = moreFlag + 5;
		}
	}, 
	$scope.clickBackward = function(moreFlag) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag - 5;
		}
	}, 
	$scope.addPlantRegDetails = function(projId, plantAttendReq) {
	if (projId == undefined || projId == null) {
			
 GenericAlertService.alertMessage("Please select project to add Plants", 'Warning');
	
		return;
		
}
	//Confirmation Flag
	if ($scope.plantFlag == true) {
		GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function() {
			$scope.savePlantAttendanceRecords();
		}, function(error) {
			$scope.plantFlag = false;
			$scope.addPlantRegDetails(projId, plantAttendReq);
		})
		return;
	}

		var plantPopUp = [];
		var plantExistingMap = [];
		angular.forEach($scope.plantAttendenceDetails,function(value,key)
			{
			plantExistingMap[value.plantId]=true;
			});
		plantPopUp = AttendancePlantRegFactory.getPlantRegDetails(plantExistingMap,projId, plantAttendReq);
		plantPopUp.then(function(data) {
			angular.forEach(data.plantAttendenceTOs,function(value,key)
				{
				$scope.plantAttendenceDetails.push(value);
				});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Plants", 'Error');
		});
	}, $scope.getPlantCopyTemplate = function(projId, plantAttendReq) {
		if (projId !== undefined && projId != null && plantAttendReq.crewLabelKeyTO !== undefined && plantAttendReq.crewLabelKeyTO.id <= 0) {
			GenericAlertService.alertMessage("Please select project and crew ", 'Warning');
			return;
		}
		var popup = PlantAttendanceCopyFactory.copyAttendancePlantDetails(projId, plantAttendReq);
		popup.then(function(data) {
			$scope.plantAttendenceDetails = data.plantAttendenceTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
		})
	},
	$scope.createPlantAttendanceRecord = function( plantAttendReq,projId) {
		if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0 || $scope.plantAttendReq.crewLabelKeyTO.id == undefined || $scope.plantAttendReq.crewLabelKeyTO.id <= 0) {
			GenericAlertService.alertMessage("Please select Project and Crew to create Emp Details", 'Warning');
			return;
		}
		if ( plantAttendReq.attendenceId != undefined ||  plantAttendReq.attendenceId > 0 ) {
			GenericAlertService.alertMessage("Attendance Record Already existed for selected Crew", 'Warning');
			return;
		}
		var empPopup = [];
		empPopup = AttendancePlantCreateFactory.getPlantCreateDetails($scope.searchProject, $scope.plantAttendReq, $scope.plantAttendanceDays, $scope.plantAttendanceMap, $scope.plantDetailsMap);
		empPopup.then(function(data) {
			$scope.plantAttendenceDetails = data.plantAttendenceTOs;
			plantAttendReq.attendenceId = data.labelKeyTO.id;
			plantAttendReq.attendenceCode=data.labelKeyTO.code;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while opening Create  popup ", 'Error');
		});
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'plantCode', displayName: "EPS Name", headerTooltip: "EPS Id", groupingShowAggregationMenu: false },
						{ field: 'plantName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
						{ field: 'plntRegNo', displayName: "Employee Id", headerTooltip: "Employee ID", groupingShowAggregationMenu: false },
						{ field: 'classType', displayName: "Company Name", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
						{ field: 'plntManfature', displayName: "First Name", headerTooltip: "First Name", groupingShowAggregationMenu: false },
						{ field: 'plntModel', displayName: "Last Name", headerTooltip: "Last Name", groupingShowAggregationMenu: false },
						{ field: 'cmpCatgName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false },
						
						{ name: 'curve', cellTemplate: 
						"<div class='ui-grid-cell-contents' class='input-width' ng-show='$index >= moreFlag && $index < moreFlag + 7' ng-repeat='attendenceDay in plantAttendanceDays'><select 'ng-model='plantAttendance.plantAttendenceDtlMap[attendenceDay].attendanceTypeTO.code'" 
						+ "ng-show='plantAttendance.plantAttendenceDtlMap[attendenceDay]'" 
						+ "ng-if='plantAttendance.plantAttendenceDtlMap[attendenceDay].attendenceFlag || plantAttendance.plantAttendenceDtlMap[attendenceDay].attendanceTypeTO.code !=null'"
						+ "ng-disabled='!plantAttendance.plantAttendenceDtlMap[attendenceDay].attendenceFlag'>"
						+ "<option ng-repeat='plantLeaveType in plantAttendanceDaysDD' ng-value='plantLeaveType.code'>{{ plantLeaveType.code }}</option></select>"
						+ "<span ng-if='!plantAttendance.plantAttendenceDtlMap[attendenceDay].attendenceFlag && plantAttendance.plantAttendenceDtlMap[attendenceDay].attendanceTypeTO.code == null'>N/A</span></div>", 
						displayName: "Curve", headerTooltip : "Resource Curve", groupingShowAggregationMenu: false,enableFiltering: false },

						{ field: 'empCategory', displayName: "Category", headerTooltip: "Category", groupingShowAggregationMenu: false, 
						cellTemplate:"ng-show='moreFlag >=0 && moreFlag < plantAttendanceDays.length-7" },
						
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_AttRec_DailyEmp");
					$scope.gridOptions.showColumnFooter = false;
				}
			});
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
			template: 'views/help&tutorials/asbuiltrecordshelp/plantattendancehelp.html',
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