'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empregister", {
		url : '/empregister',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/empregtabs/empreg.html',
				controller : 'EmpRegisterController'
			}
		}
	})
}]).controller("EmpRegisterController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "EmpDetailsFactory", "ProjEmpClassService", "GenericAlertService", "EpsProjectMultiSelectFactory", "$location", "EmpRegisterService", "EmpRegisterDataShareFactory","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, EmpDetailsFactory, ProjEmpClassService, GenericAlertService, EpsProjectMultiSelectFactory, $location, EmpRegisterService, EmpRegisterDataShareFactory, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.searchProject = {};
	$scope.empDatamoreFlag = 0;
	$scope.empDetails = [];
	$scope.empCode = '';
	var editEmpDetails = [];
	$rootScope.selectedEmployeeData = null;
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
	
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {			
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, 
	$scope.employeeSearch = function(click) {
		var deferred = $q.defer();
		$scope.selectedRow = null;
		editEmpDetails = [];
		var projIds = null;
		if($scope.searchProject.projIds != null && $scope.searchProject.projIds.length>0){
			projIds = [];
			projIds= $scope.searchProject.projIds;
		}
		var empReq = {
			"status" : 1,
			"projIds" : projIds
		};
		if(click=='click'){
			if ($scope.searchProject.projIds == null || $scope.searchProject.projIds == undefined) {
				GenericAlertService.alertMessage("Please select Project", "Warning");
				return;
			}
		}
		EmpRegisterService.empRegisterOnLoad(empReq).then(function(data) {
			$scope.empDetails = data.empRegisterDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.empDetails);			
			$scope.empDropDown.emppocureMentCatgMap = data.registerOnLoadTO.procureCatgMap;
			$scope.empDropDown.genderList = data.empRegisterDropDownTO.gender;
			$scope.empDropDown.empLocalityList = data.empRegisterDropDownTO.locality;
			$scope.empDropDown.employeeTypeList = data.empRegisterDropDownTO.employeeType;
			EmpRegisterDataShareFactory.getShareObjData($scope.empDropDown);
			$rootScope.selectedEmployeeData = null;
			$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
			deferred.resolve();
			if(click=='click'){
				if ($scope.empDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", "Warning");
					return;
				}
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employees", 'Error');
		});
		if ($scope.searchProject.projIds != null && $scope.searchProject.projIds != undefined) {
			$scope.disableFlag = 1;
		} else {
			$scope.disableFlag = 2;
		}
		return deferred.promise;
	}
	$scope.employeeSearch();
	$scope.empDeltailsRowSelect = function(position,empDetails) {
	      /*angular.forEach(empDetails, function(employee, index) {
	        if (position != index) {
	        	employee.selected = false;
	        	return;
	        }
	        editEmpDetails.splice(editEmpDetails.indexOf(employee), 1);*/
	        if (empDetails.selected) {
		console.log(empDetails.selected);
	        	if($scope.selectedRow){
		        	$scope.selectedRow = null;
		        	$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		        }
	        	$scope.selectedRow = null;
	        	editEmpDetails.push(empDetails);
	        	$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
				
			} else {
				editEmpDetails.splice(editEmpDetails.indexOf(empDetails), 1);
				$scope.currentTotalTabs &&  $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
			}
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
		var empDetailsPopup = EmpDetailsFactory.empDetailsOnLoad(  $scope.empDropDown,editEmpDetails, actionType);
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
			$scope.empDetails = [];
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
				$scope.employeeSearch();
				$scope.deleteIds = [];
			});
			angular.forEach(editEmpDetails, function(value, key) {
				$scope.empDetails.splice($scope.empDetails.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Employee Registration Details are failed to Deactivate', "Error");
			});
		
		},function(data){
			angular.forEach(editEmpDetails, function(value) {
				value.selected = false;
		})
		})
	}
	},$scope.tabs = [ {
		title : 'Enrolment/Promotion',
		url : 'views/projresources/projempreg/emppromotion/enrollmentpromotion.html',
		value : 'Enrollment/Promotion',
		empseleniumlocator: 'Resources_Employee_EnrollmentTab',
		empappCodeTemplateKey:'RESOURCE_EMPENROLLMENTPROMOTION_VIEW',
		resetMethod : 'resetEnroll'
	}, {
		title : 'Employee Service History',
		resetMethod : 'resetEmpService',
		url : 'views/projresources/projempreg/empservicehistory/empservicehistory.html',
		empappCodeTemplateKey:'RESOURCE_EMPSERVICEHHISTORY_VIEW',
		empseleniumlocator: 'Resources_Employee_ServiceHistoryTab'
	}, {
		title : 'Qualification Records',
		resetMethod : 'resetEmpQualification',
		url : 'views/projresources/projempreg/empqualification/empqualificationrecords.html',
		empappCodeTemplateKey:'RESOURCE_QUALIFICATIONRECORDS_VIEW',
		empseleniumlocator: 'Resources_Employee_QualificationRecordsTab'
	}, {
		title : 'Charge Out Rates',
		resetMethod : 'resetChargeoutrate',
		url : 'views/projresources/projempreg/chargerate/empchargeoutrates.html',
		empappCodeTemplateKey:'RESOURCE_EMPCHARGEOUTRATES_VIEW',
		empseleniumlocator: 'Resources_Employee_ChargeOutRatesTab'
	}, {
		title : 'Regular Payable Rates',
		resetMethod : 'resetRegularPayable',
		url : 'views/projresources/projempreg/empregularpay/empregularpay.html',
		empappCodeTemplateKey:'RESOURCE_EMPREGULARPAYBLERATES_VIEW',
		empseleniumlocator: 'Resources_Employee_RegularPayableRates'
	}, {
		title : 'Non Regular Payable Rates',
		resetMethod : 'resetNonRegularPay',
		url : 'views/projresources/projempreg/empnonregularpay/empnonregularpay.html',
		empappCodeTemplateKey:'RESOURCE_EMPNONREGULARPAYBLERATES_VIEW',
		empseleniumlocator: 'Resources_Employee_NonRegularPayableRates'
	}, {
		title : 'Pay Deductions',
		resetMethod : 'resetPaydeductions',
		url : 'views/projresources/projempreg/paydeduction/emppaydeductions.html',
		empappCodeTemplateKey:'RESOURCE_EMPPAYDEDUCTION_VIEW',
		empseleniumlocator: 'Resources_Employee_PayDeductions'
	}, {
		title : 'Bank Account Details',
		resetMethod : 'resetBankAccount',
		url : 'views/projresources/projempreg/bankaccountdetails/empbankaccountdetails.html',
		empappCodeTemplateKey:'RESOURCE_EMPBANKACCTDETAILS_VIEW',
		empseleniumlocator: 'Resources_Employee_BankAccountDetails'
	}, {
		title : 'Super annuation / Provident fund',
		resetMethod : 'resetProvidentFund',
		url : 'views/projresources/projempreg/empprovident/empprovident.html',
		empappCodeTemplateKey:'RESOURCE_EMPSUPERANNPROVIDENTFUND_VIEW',
		empseleniumlocator: 'Resources_Employee_SuperAnnuationTab'
	}, {
		title : 'Medical History',
		resetMethod : 'resetMedicalHistory',
		url : 'views/projresources/projempreg/medicalhistory/empmedicalrecords.html',
		empappCodeTemplateKey:'RESOURCE_EMPMEDICALHISTROEY_VIEW',
		empseleniumlocator: 'Resources_Employee_MedicalHistory'
	}, {
		title : 'Leave and Attendance',
		resetMethod : 'resetLeaveandattendance',
		url : 'views/projresources/projempreg/leave&attendence/empleaveattendance.html',
		empappCodeTemplateKey:'RESOURCE_EMPLEAVEATTENDANCE_VIEW',
		empseleniumlocator: 'Resources_Employee_LeaveandAttendance'
	}, {
		title : 'Employee Contact Details',
		resetMethod : 'resetEmpContact',
		url : 'views/projresources/projempreg/contactdetails/empcontactdetails.html',
		empappCodeTemplateKey:'RESOURCE_EMPCONTRACTDETAILS_VIEW',
		empseleniumlocator: 'Resources_Employee_EmployeeContactDetails'
	}, {
		title : 'NOK (Next of Kin)  Details',
		resetMethod : 'resetNOK',
		url : 'views/projresources/projempreg/nok/empnok.html',
		empappCodeTemplateKey:'RESOURCE_EMPNOKDETAILS_VIEW',
		empseleniumlocator: 'Resources_Employee_NextOfKin'
	}, {
		title : 'Request for Transfer',
		resetMethod : 'resetRequestForTransfer',
		url : 'views/projresources/projempreg/emptransfer/emptransfer.html',
		empappCodeTemplateKey:'RESOURCE_EMPREQFORTRANSFER_VIEW',
		empseleniumlocator: 'Resources_Employee_RequestForTransfer'
	}, {
		title : 'Approval for Transfer',
		resetMethod : 'resetApprovalForTransfer',
		url : 'views/projresources/projempreg/approvaltransfer/emptransferapproval.html',
		empappCodeTemplateKey:'RESOURCE_EMPAPPROVALTRANSFER_VIEW',
		empseleniumlocator: 'Resources_Employee_ApprovalForTransfer'
	}, {
		title : 'Leave Request',
		resetMethod : 'resetLeaveRequest',
		url : 'views/projresources/projempreg/leaverequestform/empleaverequestform.html',
		empappCodeTemplateKey:'RESOURCE_EMPLEAVEREQUEST_VIEW',
		empseleniumlocator: 'Resources_Employee_LeaveRequest'
	}, {
		title : 'Leave Approval',
		resetMethod : 'resetLeaveApproval',
		url : 'views/projresources/projempreg/leaveapprovalform/empleaveapprovalform.html',
		empappCodeTemplateKey:'RESOURCE_EMPLEAVEAPPROVAL_VIEW',
		empseleniumlocator: 'Resources_Employee_LeaveApproval'
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
		$rootScope.$broadcast('defaultEmployeeEnrollmentTab');
		
		if(value && value.tabIndex==3){
			$rootScope.$broadcast('chargeoutrate');
		}
		if(value && value.tabIndex==1){
			$rootScope.$broadcast('empService');
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
	$scope.clickMore = function(moreFlag1) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickTab($scope.tabs[0]);
	}, $scope.clickMore1 = function(moreFlag1) {
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
	};
	
	$scope.resetSearchData = function() {
		$scope.selectedRow = null;
		$scope.disableFlag = 2;
		editEmpDetails = [];
		$scope.searchProject = {};
		$scope.empDetails = [];
		$scope.gridOptions.data=[];
		$rootScope.selectedEmployeeData = null;
		//$scope.onClickTab($scope.tabs[0]);
		$scope.employeeSearch($scope.searchProject.projId);
		$scope.go();
		$scope.currentTotalTabs && $rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		
	};
	$rootScope.$on('employeeRefresh', function (event,value) {
		var selectedIndex = $scope.selectedRow;
		$scope.employeeSearch().then( function(){
			$scope.go($scope.empDetails,selectedIndex,$scope.empDetails[selectedIndex],value);
		});
		
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.empDeltailsRowSelect($index,row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', displayName: "Select", headerTooltip : "Select", cellTemplate:linkCellTemplate },
						{ field: 'empCode', displayName: "Employee ID", headerTooltip: "Employee ID",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.empCode}}</div>'},
						{ field: 'firstName', displayName: "First Name", headerTooltip: "First Name", 
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.firstName}}</div>'},
						{ field: 'lastName', displayName: "Last Name", headerTooltip : "Last Name", 
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.lastName}}</div>'},
						
						{ field: 'cmpName', displayName: "Parent Company", headerTooltip : "Parent Company", 
						cellTemplate: '<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.cmpName}}</div>'},
						{ field: 'gender', displayName: "Gender", headerTooltip: "Gender",
						cellTemplate:'<div ng-click="go(empDetails,$index,employee)" ng-class="{selected: $index ==selectedRow}">{{row.entity.gender}}</div>'},
						{ field: 'empClassName', displayName: "Designation", headerTooltip: "Designation",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.empClassName}}</div>'},
						{ field: 'location', displayName: "Local or Non Local", headerTooltip : "Local or Non Local",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.location}}</div>' },
						
						{ field: 'empStatus', displayName: "Exng or New Employee", headerTooltip : "Exng or New Employee",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.empStatus}}</div>' },
						{ field: 'procurecatgName', displayName: "Employement Type", headerTooltip: "Employement Type",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.procurecatgName}}</div>'},
						{ field: 'projEmpRegisterTO.enrollmentDate', displayName: "Date of Enrollment", headerTooltip: "Date of Enrollment", 
						cellTemplate: '<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.enrollmentDate}}</div>'},
						{ field: 'projEmpRegisterTO.parentName', displayName: "Current / last EPS", headerTooltip : "Current / Last EPS", 
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.parentName}}</div>'},
						
						{ field: 'projEmpRegisterTO.name', displayName: "Current/Last Project", headerTooltip : "Current/Last Project", 
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.name}}</div>'},
						{ field: 'projEmpRegisterTO.empStatus', displayName: "Current Status", headerTooltip: "Current Status",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.empStatus}}</div>'},
						{ field: 'projEmpRegisterTO.mobilaizationDate', displayName: "Mobilisation Date", headerTooltip: "Mobilisation Date",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.mobilaizationDate}}</div>'},
						{ field: 'projEmpRegisterTO.expecteddeMobilaizationDate', displayName: "Expected De-Mob Date", headerTooltip : "Expected De-Mob Date",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.expecteddeMobilaizationDate}}</div>' },
						
						{ field: 'projEmpRegisterTO.deMobilaizationDate', displayName: "Act De-Mob Date", headerTooltip: "Act De-Mob Date",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.projEmpRegisterTO.deMobilaizationDate}}</div>'},
						{ field: 'Records', displayName: "Records", headerTooltip : "Records",cellClass:"justify-center",headerCellClass:"justify-center",
						cellTemplate:'<div ng-click="grid.appScope.go(empDetails,$index,row.entity)" ng-class="{selected: $index ==selectedRow}" ng-if="row.entity.projEmpRegisterTO.taxFileNum" class="fa fa-download"></div>' },
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList");
					$scope.employeeSearch();
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
			template: 'views/help&tutorials/resourceshelp/employeehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}])