'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empregisterlist", {
		url : '/empregisterlist/',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/leaverequestform/emplistleaverequestform.html',
				controller : 'EmpRegisterListController'
			}
		}
	})
}]).controller("EmpRegisterListController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "EmpDetailsFactory", "ProjEmpClassService", "GenericAlertService", "EpsProjectSelectFactory", "$location", "EmpRegisterService", "EmpRegisterDataShareFactory", "$stateParams", function($rootScope, $scope, $q, $state, ngDialog, EmpDetailsFactory, ProjEmpClassService, GenericAlertService, EpsProjectSelectFactory, $location, EmpRegisterService, EmpRegisterDataShareFactory,$stateParams) {

	$scope.searchProject = {};
	$scope.empDatamoreFlag = 0;
	$scope.empDetails = [];
	$scope.empCode = '';
	var editEmpDetails = [];
	$rootScope.selectedEmployeeData = null;
	$scope.stateParamsRequestPage = ($stateParams.request === 'request');
	$scope.empDropDown = {
		"empCompanyMap" : {},
		"emppocureMentCatgMap" : {},
		"empClassMap" : {},
		"userProjMap" : {},
		"genderList" : [],
		"empLocalityList" : [],
		"employeeTypeList" : []
	};

	$scope.selectedEmployee = [];
	
	$scope.getUserProjects = function() {
	
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, 
	$scope.employeeSearch = function() {
		var deferred = $q.defer();
		$scope.selectedRow = null;
		editEmpDetails = [];
		var empReq = {
			"status" : 1,
			"projId" : $scope.searchProject.projId

		};
		EmpRegisterService.empRegisterOnLoad(empReq).then(function(data) {
			$scope.empDetails = data.empRegisterDtlTOs;
			$scope.empDropDown.userProjMap = data.userProjMap;
			$scope.empDropDown.empCompanyMap = data.registerOnLoadTO.companyMap;
			$scope.empDropDown.emppocureMentCatgMap = data.registerOnLoadTO.procureCatgMap;
			$scope.empDropDown.empClassMap = data.registerOnLoadTO.classificationMap;
			$scope.empDropDown.empProjClassMap = data.empProjClassMap;
			$scope.empDropDown.genderList = data.empRegisterDropDownTO.gender;
			$scope.empDropDown.empLocalityList = data.empRegisterDropDownTO.locality;
			$scope.empDropDown.employeeTypeList = data.empRegisterDropDownTO.employeeType;
			EmpRegisterDataShareFactory.getShareObjData($scope.empDropDown);
			$rootScope.selectedEmployeeData = null;
			$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
			deferred.resolve();
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employees", 'Error');
		});
		if ($scope.searchProject.projId != null && $scope.searchProject.projId != undefined) {
			$scope.disableFlag = 1;
		} else {
			$scope.disableFlag = 2;
		}
		return deferred.promise;
	}

	$scope.employeeSearch();

	$scope.empDeltailsRowSelect = function(position,empDetails) {
	      angular.forEach(empDetails, function(employee, index) {
	        if (position != index) {
	        	employee.selected = false;
	        	return;
	        }
	        editEmpDetails.splice(editEmpDetails.indexOf(employee), 1);
	        if (employee.selected) {
	        	if($scope.selectedRow){
		        	$scope.selectedRow = null;
		        	$rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		        }
	        	$scope.selectedRow = null;
	        	editEmpDetails.push(employee);
	        	$rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
				
			} else {
				editEmpDetails.splice(editEmpDetails.indexOf(employee), 1);
				$rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
			}
	      });
	},

	$scope.clickForwardempData = function(empDatamoreFlag1) {

		if ($scope.empDatamoreFlag < 1) {
			$scope.empDatamoreFlag = empDatamoreFlag1 + 1;
		}
	}, $scope.clickBackwardempData = function(empDatamoreFlag1) {
		if ($scope.empDatamoreFlag > 0) {
			$scope.empDatamoreFlag = empDatamoreFlag1 - 1;
		}
	}, $scope.addEmp = function(actionType) {
		if (actionType == 'Edit' && editEmpDetails <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var empDetailsPopup = EmpDetailsFactory.empDetailsOnLoad($scope.empDropDown, editEmpDetails, actionType);
		empDetailsPopup.then(function(data) {
			$scope.empDetails = data.empRegisterTOs;
			editEmpDetails = [];
			$scope.employeeSearch();
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching employee details", 'Error');
		});
	}, 
	 $scope.deactivateEmpRegisters = function() {
		if (editEmpDetails.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editEmpDetails, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"empRegIds" : deleteIds,
				"status" : 2
			};
			
			GenericAlertService.confirmMessageModal('Do you really want to deactivate the record', 'Warning', 'YES', 'NO').then(function() {
				EmpRegisterService.deactivateEmpRegisters(req).then(function(data) {
				GenericAlertService.alertMessage('Employee Registration Details are  Deactivated Successfully', 'Info');
				editEmpDetails = [];
				$scope.deleteIds = [];
			});

			angular.forEach(editEmpDetails, function(value, key) {
				$scope.tableData.splice($scope.tableData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Employee Registration Details are failed to Deactivate', "Error");
			});
		

		},function(data){
			angular.forEach(editEmpDetails, function(value) {
				value.selected = false;
		})
		})
	}
	},
	$scope.tabs = [  {
		title : 'Leave Request',
		resetMethod : 'resetLeaveRequest',
		url : 'views/projresources/projempreg/leaverequestform/empleaverequestform.html'
	} ];

	$scope.go = function(empDetails, indexValue, emp,value) {
		angular.forEach(empDetails, function(employee, index) {
	        if (indexValue != index) {
	        	employee.selected = false;
	        }
	        if (employee.selected) {
	        	employee.selected = false;
	        }
		  });
		  
		$rootScope.selectedEmployeeData = emp;
		$scope.setSelected(indexValue);
		$scope.moreFlag = 0;
		if(value){
			$scope.onClickTab($scope.tabs[value.tabIndex]);
		}else{
			$scope.onClickTab($scope.tabs[0]);
		}
	};

	
	$scope.currentTab = $scope.tabs[0].url;
	$scope.onClickTab = function(tab) {
		$scope.currentTotalTabs = tab;
		$scope.currentTab = tab.url;
	}, $scope.isActiveTab = function(tabUrl) {
		return tabUrl == $scope.currentTab;
	},

	$scope.moreFlag = 0;
	 $scope.clickMore1 = function(moreFlag1) {
		if ($scope.moreFlag < 1) {
			$scope.moreFlag = moreFlag1 + 1;
		} else {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickTab($scope.tabs[7]);
	}, $scope.clickMore2 = function(moreFlag2) {
		if ($scope.moreFlag < 2) {
			$scope.moreFlag = moreFlag2 + 1;
		} else {
			$scope.moreFlag = moreFlag2 - 1;
		}
		$scope.onClickTab($scope.tabs[13]);
	}
	$scope.setSelected = function(row) {
		$scope.selectedRow = row;
	}
	
	, $scope.resetSearchData = function() {
		$scope.selectedRow = null;
		$scope.disableFlag = 2;
		editEmpDetails = [];
		$scope.searchProject = {};
		$scope.empDetails = [];
		$rootScope.selectedEmployeeData = null;
		//$scope.onClickTab($scope.tabs[0]);
		$scope.employeeSearch($scope.searchProject.projId);
		$rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		
	};

	$rootScope.$on('employeeRefresh', function (event,value) {
		var selectedIndex = $scope.selectedRow;

		$scope.employeeSearch().then( function(){
			$scope.go($scope.empDetails,selectedIndex,$scope.empDetails[selectedIndex],value);
		});
		
	});
}])