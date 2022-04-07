'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("empleaverequest", {
		url: '/empleaverequest',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projresources/projempreg/leaverequestform/empallleaverequestform.html',
				controller: 'EmpLeaveRequestController'
			}
		}
	}).state("empleaverequest1", {
		url: '/empleaverequest1',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projresources/projempreg/leaverequestform/empleaverequestform.html',
				controller: 'EmpLeaveRequestController'
			}
		}
	})
}]).controller("EmpLeaveRequestController", ["$scope", "$q", "$state", "$filter", "ngDialog", "$rootScope", "EmpRegisterDataShareFactory", "GenericAlertService", "EmpLeaveApprovalFactory", "EmpRegisterService", "EmpLeaveRequestFactory", "LeaveApprovalViewFactory", "EpsProjectMultiSelectFactory", "stylesService", "ngGridService", function ($scope, $q, $state, $filter, ngDialog, $rootScope, EmpRegisterDataShareFactory, GenericAlertService, EmpLeaveApprovalFactory, EmpRegisterService, EmpLeaveRequestFactory, LeaveApprovalViewFactory, EpsProjectMultiSelectFactory, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.empLeaveReqApprTOs = [];
	$scope.searchProject = {};
	$scope.empDetails = [];
	var editEmpDetails = [];
	var editProgress = [];
	
	$scope.empDatamoreFlag = 0;
	$scope.projTabs = {};
	$scope.currentTab = null; 
		
	$scope.empDropDown = {
		"empCompanyMap": {},
		"emppocureMentCatgMap": {},
		"empClassMap": {},
		"userProjMap": {},
		"genderList": [],
		"empLocalityList": [],
		"employeeTypeList": []
	};
	
	$scope.selectedEmployee = [];
	
	$scope.projTabs = {
		title : 'Leave Request',
		resetMethod : 'resetLeaveRequest',
		url : 'views/projresources/projempreg/leaverequestform/empleaverequestform1.html',
		empappCodeTemplateKey:'RESOURCE_EMPLEAVEREQUEST_VIEW',
		empseleniumlocator: 'Resources_Employee_LeaveRequest'
	},
	$scope.currentTab = $scope.projTabs.url;
	
	$scope.getUserProjects = function() {
	
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {			
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	} 

$scope.clickForwardempData = function(empDatamoreFlag1){
	if($scope.empDatamoreFlag < 1){
		$scope.empDatamoreFlag = empDatamoreFlag1 + 1;
	}
}, $scope.clickBackwardempData = function(empDatamoreFlag1){
	if($scope.empDatamoreFlag > 0 ){
		$scope.empDatamoreFlag = empDatamoreFlag1 -1;
	}
},

	$scope.onClickTab = function(tab){
		$scope.currentTab = tab.url;
	},$scope.isActiveTab = function(tabUrl){
		return tabUrl == $scope.currentTab
		
	}
	
	let todayDate = new Date();
	let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
	$scope.fromDate = $filter('date')((lastMonthDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((todayDate), "dd-MMM-yyyy");

	$scope.projectsAssignedToEmployee = $rootScope.selectedEmployeeData ? true : false;

	$scope.leaveReq = {
		"fromDate": $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
		"toDate": $filter('date')(($scope.toDate), "dd-MMM-yyyy")
	}

	var editleavereq = {};
	$scope.getEmpLeaveReqApprovals = function () {
		var startDate = new Date($scope.leaveReq.fromDate);
		var finishDate = new Date($scope.leaveReq.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('From Date should be Less than To Date', 'Warning');
			return;
		}
		if ($rootScope.selectedEmployeeData) {
			var req = {
				"status": 1,
				"projId": $rootScope.selectedEmployeeData.projId,
				"id": $rootScope.selectedEmployeeData.id,
				"fromDate": $filter('date')(($scope.leaveReq.fromDate), "dd-MMM-yyyy"),
				"toDate": $filter('date')(($scope.leaveReq.toDate), "dd-MMM-yyyy"),
				"onload": onload
			};
			var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
			resultMap.then(function (data) {
				$scope.empDropDown = data.empMapsData;
				EmpRegisterService.getEmpLeaveReqApprovals(req).then(function (data) {

					$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;
					for (let manpower of $scope.empLeaveReqApprTOs) {
				manpower.ApproverName = manpower.apprUserTO.firstName + manpower.apprUserTO.lastName;
			}
					$scope.gridOptions.data = angular.copy($scope.empLeaveReqApprTOs);
					if (onload) {
						$scope.leaveCodeMap = data.leaveCodeMap;
					}
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting  employee transfer requests", 'Error');

				});
			});
		}else {
			GenericAlertService.alertMessage("Please select employee to apply leave request", 'Warning');
		}
	},

		$scope.getUserProjects = function () {

			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = data.searchProject;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		}

	$scope.employeeSearch = function () {
	//	$scope.selectedRow = null;
	//	editEmpDetails = [];
		var empReq = {
			"status": 1,
			"projIds": $scope.searchProject.projIds

		};
		EmpRegisterService.empRegisterOnLoad(empReq).then(function (data) {
			$scope.empDetails = data.empRegisterDtlTOs;
			console.log($scope.empDetails)
	//		$scope.empDropDown.emppocureMentCatgMap = data.registerOnLoadTO.procureCatgMap;
	//		$scope.empDropDown.genderList = data.empRegisterDropDownTO.gender;
	//		$scope.empDropDown.empLocalityList = data.empRegisterDropDownTO.locality;
	//		$scope.empDropDown.employeeTypeList = data.empRegisterDropDownTO.employeeType;
	//		EmpRegisterDataShareFactory.getShareObjData($scope.empDropDown);
//			$rootScope.selectedEmployeeData = null;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting employees", 'Error');
		});
		/*if ($scope.searchProject.projId != null && $scope.searchProject.projId != undefined) {
			$scope.disableFlag = 1;
		} else {
			$scope.disableFlag = 2;
		}*/
	//	return deferred.promise;
	}
	
	$scope.empDetailsRowSelect = function(position,empDetails) {
		angular.forEach(empDetails, function(employee, index) {
		  if (position != index) {
			  employee.selected = false;
			  return;
		  }
		  editEmpDetails.splice(employee.indexOf(employee), 1);
		  if (employee.selected) {
			  if($scope.selectedRow){
				  $scope.selectedRow = null;
				  $scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
			  }
			  $scope.selectedRow = null;
			  editEmpDetails.push(employee);
			  $scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
			  
		  } else {
			  editEmpDetails.splice(editEmpDetails.indexOf(employee), 1);
			  $scope.currentTotalTabs &&  $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		  }
		});
  },

$scope.LeaveRequestRecords = function(empDetails, indexValue, emp, values){
	angular.forEach(empDetails, function(employee, index){
		if(indexValue != index){
			employee.selected = false;
		}
		if(employee.selected){
			employee.selected = false
		}
	});
	$rootScope.selectedEmployeeData = emp;
	console.log($rootScope.selectedEmployeeData)
	$scope.setSelected(indexValue);
	$scope.onClickTab($scope.projTabs);
	$rootScope.$broadcast('defaultLeaveReq');
	//$scope.getAllEmpLeaveReqApprovals($rootScope.selectedEmployeeData);
};
$scope.$on("defaultLeaveReq", function(evt, empDropDown) {
		$scope.getAllEmpLeaveReqApprovals();
	});
$scope.setSelected = function(row){
	$scope.selectedRow = row;
}
	$scope.getAllEmpLeaveReqApprovals = function () {
		var startDate = new Date($scope.leaveReq.fromDate);
		var finishDate = new Date($scope.leaveReq.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('From Date should be Less than To Date', 'Warning');
			return;
		}
		var req = {
			"status": 1,
			"projId": $rootScope.selectedEmployeeData.projId,
			"id": $rootScope.selectedEmployeeData.id,
			"fromDate": $filter('date')(($scope.leaveReq.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.leaveReq.toDate), "dd-MMM-yyyy"),
		};

		EmpRegisterService.getEmpLeaveReqApprovals(req).then(function (data) {
			$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;
		}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting  employee leave approvals", 'Error');

		});
	}

	$scope.rowSelect = function (request) {
		if (request.selected) {
			editleavereq.push(request);
		} else {
			editleavereq.pop(request);
		}

	}, $scope.createOrEditLeaveRequest = function (actionType, editleavereq) {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the employee", "Warning");
			return;
		}
		if (actionType == 'Add' && !$rootScope.selectedEmployeeData.projId) {
			GenericAlertService.alertMessage("To request leave, Please assign employee to some Project", 'Warning');
			return;
		}
		if (actionType == 'Edit' && editleavereq <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else {
			var leaverequest = EmpLeaveRequestFactory.getLeaveRequestDetails(actionType, editleavereq, $scope.empDropDown, $scope.leaveCodeMap);
			leaverequest.then(function (data) {
				$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;

				editleavereq = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
			})
		}
	},
		$scope.createOrEditEmployeeLeaveRequest = function (actionType, editleavereq) {
			$scope.employeeLeaveRequest = "leave";
			if (editProgress.length >= 0) {
				var leaverequest = EmpLeaveRequestFactory.getLeaveRequestDetails(actionType, editEmpDetails, $scope.empDropDown, $scope.employeeLeaveRequest);
				leaverequest.then(function (data) {
					$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;

					editleavereq = [];
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
				})
			} else {
				GenericAlertService.alertMessage('Please Select Atleast One Row', "Warning");
				return;
			}
		},
		$scope.viewApproveLeaveRequest = function (actionType, editleavereq) {
			if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
				GenericAlertService.alertMessage("Please select the employee", "Warning");
				return;
			}

			var leaverequest = EmpLeaveApprovalFactory.getLeaveRequestDetails(editleavereq, $scope.empDropDown, $scope.leaveCodeMap);
			leaverequest.then(function (data) {
				$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;
				
				editleavereq = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while selecting  Details", 'Info');
			})

		},
		
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'reqDate', displayName: "Date of Request", headerTooltip: "Date of Request"},
						{ field: 'reqCode', displayName: "Leave Application ID", headerTooltip: "Leave Application ID", },
						{ field: 'startDate', displayName: "Start Date", headerTooltip: "Start Date"},
						{ field: 'endDate', displayName: "Finish Date", headerTooltip: "Finish Date",},
						{ field: 'totalDays', displayName: "Number of work Days", headerTooltip: "Number of work Days",},
						{ name: 'ApproverName', displayName: "Approver Name", headerTooltip: "Approver Name"},
						{ field: 'notifyCode', displayName: "Notification ID", headerTooltip: "Notification ID"},
						{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status"},
						{ field: 'leaveAvailed', displayName: "Leave Availed or Not availed", headerTooltip: "Leave Availed or Not availed"},
						{ name: 'Download', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "More Details", headerTooltip : "More Details",
						cellTemplate: 'template.html'
			      		 },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_LeaveRequest");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
	$scope.reset = function (loadAll) {
		if (loadAll) {
			$scope.leaveReq = {
				"fromDate": $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
				"toDate": $filter('date')(($scope.toDate), "dd-MMM-yyyy")
			}
			$scope.empLeaveReqApprTOs = [];
			$scope.empDetails = [];
		}
		else {
			$scope.leaveReq = {
				"fromDate": $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
				"toDate": $filter('date')(($scope.toDate), "dd-MMM-yyyy")
			}
		}
		$scope.empLeaveReqApprTOs = [];
		$scope.empDetails = [];
	}
}]);