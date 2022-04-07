'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empleaveapproval", {
		url : '/empleaveapproval',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/leaveapprovalform/empleaveapprovalform.html',
				controller : 'EmpLeaveApprovalController'
			}
		}
	}).state("empleaveapproval1", {
		url: '/empleaveapproval1',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projresources/projempreg/leaveapprovalform/empallleaveapprovalform.html',
				controller: 'EmpLeaveApprovalController'
			}
		}
	})
}]).controller("EmpLeaveApprovalController", ["$scope", "$q", "$state", "$filter", "ngDialog", "$rootScope", "EmpRegisterDataShareFactory", "GenericAlertService", "EmpRegisterService", "EmpLeaveApprovalFactory", "LeaveApprovalViewFactory","stylesService", "ngGridService","EpsProjectMultiSelectFactory", function($scope, $q, $state, $filter, ngDialog, $rootScope, EmpRegisterDataShareFactory, GenericAlertService, EmpRegisterService, EmpLeaveApprovalFactory, LeaveApprovalViewFactory, stylesService, ngGridService, EpsProjectMultiSelectFactory) {
	$scope.empLeaveReqApprTOs = [];
	$scope.stylesSvc = stylesService;
	var editPendingApproval=[];
	$scope.approvalType = "Pending For Approval";
	$scope.approvalTypeList = [ "Pending For Approval", "Approved", "Not Approved", "All" ];
	$scope.selectedValue = [];

	// added value
	$scope.searchProject = {};
	$scope.empDetails = []; 
	$scope.empDatamoreFlag = 0;
	$scope.projTabs = {};
//	$rootScope.selectedEmployeeData = null; 
	$scope.selectedEmployee = [];
	
	$scope.projTabs = {
		title : 'Leave Approval',
		resetMethod : 'resetLeaveApproval',
		url : 'views/projresources/projempreg/leaveapprovalform/empleaveapprovalform1.html',
		empappCodeTemplateKey:'RESOURCE_EMPLEAVEAPPROVAL_VIEW',
		empseleniumlocator: 'Resources_Employee_LeaveApproval'
	}
	$scope.currentTab = $scope.projTabs.url;
	
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);


	$scope.leaveApproval = {
		"fromDate" : $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
		"toDate" : $filter('date')((defaultToDate), "dd-MMM-yyyy")
	}

	var editleavereq = {};
	$scope.editLeaveapproval = function(editleavereq) {
		
		var leaverequest = EmpLeaveApprovalFactory.getLeaveRequestDetails(editleavereq, $scope.empDropDown, $scope.leaveCodeMap);
		leaverequest.then(function(data) {
			if (data) {
				let empLeaves = angular.copy($scope.empLeaveReqApprTOs);
				empLeaves.splice(empLeaves.findIndex(item => item.id == data.id), 1);
				$scope.empLeaveReqApprTOs = empLeaves;
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting  details", 'Info');
		})
	},
	$scope.getUserProjects = function(){
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data){
			$scope.searchProject = data.searchProject;
			console.log($scope.searchProject)
		}, function(error){
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.rowSelect = function(position, employee){
		angular.forEach(employee, function(key, index){
			if(position != index){
				employee.selected = false;
				return;
			}
		
		if(employee.selected){
			if($scope.selectedRow){
				$scope.selectedRow = null;
				$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
				console.log($scope.selectedRow)
			}
			$scope.selectedRow = null;
			editEmpDetails.push(employee);
			$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		}else{
			editEmpDetails.splice(editEmpDetails.indexOf(employee), 1);
			$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		}
	});
	},
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
		return tabUrl == $scope.currentTab;
	},
	$scope.setSelected = function(row){
		$scope.selectedRow = row;
		console.log($scope.selectedRow)
	}
		$scope.getEmpLeaveReqApprovals = function() {
		var startDate = new Date($scope.leaveApproval.fromDate);
		var finishDate = new Date($scope.leaveApproval.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('From Date should be Less than To Date', 'Warning');
			return;
		}
		if($rootScope.selectedEmployeeData){
			var req = {
				"status" : 1,
				"projId" : $rootScope.selectedEmployeeData.projId,
				"id" : $rootScope.selectedEmployeeData.id,
				"apprStatus" : $scope.approvalType,
				"fromDate" : $filter('date')(($scope.leaveApproval.fromDate), "dd-MMM-yyyy"),
				"toDate" : $filter('date')(($scope.leaveApproval.toDate), "dd-MMM-yyyy"),
				"onload" : true,
				"fromApproval":true
			};
				EmpRegisterService.getEmpLeaveReqApprovals(req).then(function(data) {
					$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;
					console.log("$scope.empLeaveReqApprTOs")
					console.log($scope.empLeaveReqApprTOs)
					$scope.gridOptions.data = angular.copy($scope.empLeaveReqApprTOs);
					if (onload) {
						$scope.leaveCodeMap = data.leaveCodeMap;
					}
				}, function(error) {
					if(onload){
					GenericAlertService.alertMessage("Error occured while getting  employee leave approvals", 'Error');
					}

				});
			}else{
				GenericAlertService.alertMessage("Please select employee to get leave details", 'Warning');
			}
	}
	
	$scope.employeeSearch = function(){
		var empReq = {
			"status":1,
			"projIds": $scope.searchProject.projIds
		};
		EmpRegisterService.empRegisterOnLoad(empReq).then(function (data) {
			$scope.empDetails = data.empRegisterDtlTOs;
		}, function(error){
			GenericAlertService.alertMessage("Error occured while getting employee", 'Error');	
		})
	},
	$scope.getAllApproveddetails = function(){
		 var req = {
			"status": 1,
			"projId": $rootScope.selectedEmployeeData.projId,
			"id": $rootScope.selectedEmployeeData.id,
			"apprStatus" : $scope.approvalType,
			"fromDate" : $filter('date')(($scope.leaveApproval.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.leaveApproval.toDate), "dd-MMM-yyyy"),
			"fromApproval":true
		};
		EmpRegisterService.getEmpLeaveReqApprovals(req).then(function(data) {
					$scope.empLeaveReqApprTOs = data.empLeaveReqApprTOs;
					console.log($scope.empLeaveReqApprTOs)
				},function(error){
					GenericAlertService.alertMessage("Error occured while getting employee leave approvals", 'Error');
			});
		
	},
	$scope.getEmployeeDetails = function(empDetails, indexValue, emp, value){
		angular.forEach(empDetails, function(employee, index){
			if(indexValue != index){
				employee.selected = false;
			}
			if(employee.selected){
				employee.selected = false;
			}
		});
		$rootScope.selectedEmployeeData = emp;
		console.log($rootScope.selectedEmployeeData)
		$scope.setSelected(indexValue);
		$scope.onClickTab($scope.projTabs);
		$rootScope.$broadcast('defaultLeaveReq');
		//$scope.getAllApproveddetails();
	},
	$scope.$on("defaultLeaveReq", function(evt, empDropDown) {
		$scope.getAllApproveddetails();
	});
	$scope.$on("value", function() {
		$scope.value=1;
	});
	$scope.newRequestRowSelect = function(approval) {
		if (approval.select) {
			editPendingApproval.push(approval);
			$scope.selectedValue = editPendingApproval;
		} else {
			editPendingApproval.pop(approval);
		}
	};
	$scope.$on("resetLeaveApproval", function(){
		$scope.empLeaveReqApprTOs = [];
	});
	$scope.reset = function(onload){
		$scope.leaveApproval = {
			"fromDate" : $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')((defaultToDate), "dd-MMM-yyyy")
		};
		$scope.empLeaveReqApprTOs = [];
		$scope.empDetails = [];
		if(onload){
			$scope.empLeaveReqApprTOs = [];
	//		$scope.empDetails = [];
				
		}
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'reqDate', displayName: "Date of Request", headerTooltip: "Date of Request"},
						{ field: 'reqCode', displayName: "Leave Application ID", headerTooltip: "Leave Application ID", },
						{ field: 'startDate', displayName: "Start Date", headerTooltip: "Start Date"},
						{ field: 'endDate', displayName: "Finish Date", headerTooltip: "Finish Date",},
						{ field: 'totalDays', displayName: "Number of work Days", headerTooltip: "Number of work Days",},
						{ name: 'Name', displayName: "Approver Name", headerTooltip: "Approver Name",
						cellTemplate:'<div>{{row.entity.apprUserTO.firstName}}&nbsp;{{row.entity.apprUserTO.lastName}}</div>'},
						{ field: 'notifyCode', displayName: "Notification ID", headerTooltip: "Notification ID"},
						{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status"},
						{ field: 'leaveAvailed', displayName: "Leave Availed or Not availed", headerTooltip: "Leave Availed or Not availed"},
						{ name: 'Download', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "More Details", headerTooltip : "More Details",
						cellTemplate: 'template.html'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_LeaveApproval");
				//	$scope.getEmpLeaveReqApprovals();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);
