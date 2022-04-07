'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empcalculation", {

		url : '/empcalculation',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/employeewisecalc/employeepaycalcsheet.html',
				controller : 'EmpPayCalculationController'
			}
		}
	})
}]).controller("EmpPayCalculationController", ["$scope", "$q", "$state", "ngDialog", "GeneratePayRollFactory", "CompanyListPopUpFactory", "RegularPayservice", "NonRegularPayService", "EmpRegisterService", "CompanyService", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", function($scope, $q, $state, ngDialog, GeneratePayRollFactory, CompanyListPopUpFactory, RegularPayservice, NonRegularPayService, EmpRegisterService, CompanyService, UserEPSProjectService, GenerateInvoiceFactory, GenericAlertService) {

	$scope.date = new Date();
	$scope.getUserProjects = function() {
		$scope.activeFlag = 1;
		var userProjectSelection = UserEPSProjectService.epsProjectPopup();
		userProjectSelection.then(function(userEPSProjData) {
			$scope.searchProject = userEPSProjData.selectedProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.generateInvoice = function() {
		var popupDetails = GenerateInvoiceFactory.generatePopUpDetails();
		popupDetails.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');

		})
	}, $scope.generatePayRoll = function() {

		var popupDetails = GeneratePayRollFactory.generatePopUpDetails();
		popupDetails.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		})
	}, $scope.getCompanies = function(company) {
		var companyListService = {};
		var onLoadReq = {
			"status" : 1
		};
		var companyListService = CompanyListPopUpFactory.getCompanies(onLoadReq);
		companyListService.then(function(data) {
			$scope.name = data.selectedRecord.cmpName;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');

		})

	}, $scope.employeeSearch = function(getRequired) {
		var empReq = {
			"status" : 1,
			"getRequired" : getRequired
		};
		EmpRegisterService.empRegisterOnLoad(empReq).then(function(data) {
			$scope.empDetails = data.empRegisterDtlTOs;
			$scope.empCompanyMap = data.empCompanyMap;
			$scope.emppocureMentCatgMap = data.emppocureMentCatgMap;
			/*$scope.empClassMap = data.empempClassMap;
			$scope.genderList = data.genders;
			$scope.empLocalityList = data.localites;
			$scope.employeeTypeList = data.empTypes;*/
		}, function(error) {
			GenericAlertService.alertMessage("Error Occured While Getting Employess", 'Error');
		});
	}, $scope.getRegularPayRates = function() {
		var getRegularPayRatesReq = {
			"taxId" : 1,
			"status" : 1
		};
		RegularPayservice.getRegularPay(getRegularPayRatesReq).then(function(data) {
			$scope.regularPayCodes = data.regularPayAllowanceTOs;
		})
	}, $scope.getEmpRegularPayDetails = function() {
		var getEmpRegularPayReq = {
			"status" : 1,
			"empId" : $scope.empId,
		};
		EmpRegisterService.getEmpRegPayAllowances(getEmpRegularPayReq).then(function(data) {
			$scope.empRegularPayDetails = data.projEmpRegisterTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error Occured While Getting Employee emp pay Detail(s)", 'Error');
		});
	}, $scope.getNonRegularPayRates = function() {
		var getNonRegularPayRatesReq = {
			"taxId" : 1,
			"status" : 1
		};
		NonRegularPayService.getNonRegularPay(getNonRegularPayRatesReq).then(function(data) {
			$scope.nonRegularPayCodes = data.nonRegularPayAllowanceTOs;
		})
	}, $scope.getEmpNonRegularPayDetails = function() {
		var getEmpNonRegularPayReq = {
			"status" : 1,
			"empId" : $scope.empId,
		};
		EmpRegisterService.getEmpNonRegPayments(getEmpNonRegularPayReq).then(function(data) {
			$scope.empNonRegularPayDetails = data.projEmpRegisterTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error Occured While Getting Employee pay Detail(s)", 'Error');
		});
	}, $scope.tabs = [ {
		title : 'Employee Details',
		url : 'views/finance/financemain/employeewisecalc/employeedetails.html'
	}, {
		title : 'Pay Period Days Count',
		url : 'views/finance/financemain/employeewisecalc/payperioddayscount.html'
	}, {
		title : 'Pay Period Hours Count',
		url : 'views/finance/financemain/employeewisecalc/payperiodhourscount.html'
	}, {
		title : 'Regular Pay Allowance',
		url : 'views/finance/financemain/employeewisecalc/regularpayallowanceamount.html'
	}, {
		title : 'Non Regular Pay Allowance',
		url : 'views/finance/financemain/employeewisecalc/nonregularpayallowanceamount.html'
	}, {
		title : 'Code Wise Applicable Deductions',
		url : 'views/finance/financemain/employeewisecalc/applicabledeductions.html'
	}, {
		title : 'Summary',
		url : 'views/finance/financemain/employeewisecalc/summary.html'
	} ];
	$scope.currentTab = 'views/finance/financemain/employeewisecalc/employeedetails.html';
	$scope.onClickTab = function(tab) {
		$scope.currentTab = tab.url;
	}
	$scope.isActiveTab = function(tabUrl) {
		return tabUrl == $scope.currentTab;
	}, $scope.moreFlag = 0;
	$scope.clickMore = function(moreFlag1) {

		$scope.onClickTab($scope.tabs[0]);
	}, $scope.clickMore1 = function(moreFlag1) {
		if ($scope.moreFlag < 1) {
			$scope.moreFlag = moreFlag1 + 1;
			$scope.onClickTab1($scope.projTabs[0].childTabs[6]);
		}
	}, $scope.clickMore = function(moreFlag1) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag1 - 1;
			$scope.onClickTab1($scope.projTabs[0].childTabs[0]);
		}
	}

}]);