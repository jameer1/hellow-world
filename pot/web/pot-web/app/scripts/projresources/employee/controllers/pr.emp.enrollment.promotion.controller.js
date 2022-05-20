'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empenrollement", {
		url : '/empenrollement',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/emppromotion/enrollmentpromotion.html',
				controller : 'EmpPromotionsController'
			}
		}
	})
}]).controller("EmpPromotionsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "EmpRegisterDataShareFactory", "EmpDetailsFactory", "EmpStatusFactory", "ProjEmpClassService", "EmpEnrollmentFactory", "GenericAlertService", "UserEPSProjectService", "$location", "EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, EmpRegisterDataShareFactory, EmpDetailsFactory, EmpStatusFactory, ProjEmpClassService, EmpEnrollmentFactory, GenericAlertService, UserEPSProjectService, $location, EmpRegisterService, stylesService, ngGridService) {

	var editPoData = [];
	$scope.employeeProjDtlTOs = [];
	$scope.showEditButton = false;
	$scope.empRegisterDtlTO = {};
	$scope.empRegisterDropDownTO = {};
	$scope.emppocureMentCatgMap = [];
	$scope.empDropDown = [];
	$scope.procureCatgMap = [];
	$scope.stylesSvc = stylesService;
	$scope.poRowSelect = function(employeePo) {
		if (employeePo.selected) {
			editPoData.push(employeePo);
		} else {
			editPoData.pop(employeePo);
		}

	}

	$scope.employeePo = [];
	$scope.getRegEmployeeProcureDeliveryDetails = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			return;
		}
		$scope.showEditButton = true;
		var getEmployeePoReq = {
			"status" : 1,
			"projId" : $rootScope.selectedEmployeeData.projId,
			"empId" : $rootScope.selectedEmployeeData.id
		};
		var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
		resultMap.then(function(data) {
			
			$scope.empDropDown = data.empMapsData;
			EmpRegisterService.getEmpEnrollments(getEmployeePoReq).then(function(data) {
				console.log(data.empEnrollmentDtlTOs);
				$scope.employeeProjDtlTOs = data.empEnrollmentDtlTOs;
				$scope.gridOptions.data = angular.copy($scope.employeeProjDtlTOs);
				$scope.empRegisterDtlTO = data.empRegisterDtlTO;
				$scope.empRegisterDropDownTO = data.empRegisterDropDownTO;

			}, function(error) {
				if (error.message != null && error.message != undefined) {
					GenericAlertService.alertMessage(error.message, error.status);
				} else {
					GenericAlertService.alertMessage("Error occured while getting the Employee Procuremet Details", "Error");
				}
			});
		});

	},

	$scope.$on("defaultEmployeeEnrollmentTab", function(evt, empDropDown) {
		var editPoData = [];
		$scope.employeeProjDtlTOs = [];
		$scope.showEditButton = false;
		$scope.empRegisterDtlTO = {};
		$scope.empRegisterDropDownTO = {};
		$scope.emppocureMentCatgMap = [];
		$scope.empMapsData = empDropDown;
		$scope.procureCatgMap = [];
		$scope.getRegEmployeeProcureDeliveryDetails();
	});

	$scope.getRegEmployeeProcureDeliveryDetails();

	$scope.downloadContract = function(clientId,fileName) {
		let req={
			"recordId" : clientId,
			"category" : "Enrollment/Promotions",
			"fileName" : fileName
		}
		EmpRegisterService.downloadRegisterDocs(req);
	};

	$scope.addemployeepoList = function(actionType) {
		if ($rootScope.selectedEmployeeData != undefined && $rootScope.selectedEmployeeData != null) {

			if (actionType == 'Edit' && editPoData <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
				return;
			}

			var mapData = {
				"editPoData" : editPoData,
				"empRegisterDtlTO" : $scope.empRegisterDtlTO,
				"empRegisterDropDownTO" : $scope.empRegisterDropDownTO,
				"employeeProjDtlTOs" : $scope.employeeProjDtlTOs,
				"procureCatgMap" : $scope.empDropDown.emppocureMentCatgMap,
				"activeEmpEnrollment" : editPoData[0],
				"empDropDown":$scope.empDropDown
				
			};
			
			
			var popupDetails = EmpEnrollmentFactory.poDetailsPopUpOnLoad(actionType, mapData);
			popupDetails.then(function(data) {
				$scope.employeeProjDtlTOs = data.employeeProjDtlTOs;
				$scope.empRegisterDtlTO = data.empRegisterDtlTO;
				$scope.empRegisterDropDownTO = data.empRegisterDropDownTO;
				$scope.userProjectMap=data.userProjectMap;
				$scope.procureCatgMap = data.procureCatgMap;
				
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Purchase Order Details", 'Info');
			})

		} else {
			GenericAlertService.alertMessage("Please select the Employee for Procurement & Delivery Details", 'Info');
		}
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.poRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'empLabelKeyTO.code', displayName: "Emp ID", headerTooltip: "Emp ID"},
						{ field: 'empLabelKeyTO.name', displayName: "Emp Name", headerTooltip: "Emp Name", },
						{ field: 'parentName', displayName: "EPS", headerTooltip: "EPS"},
						{ field: 'name', displayName: "Project", headerTooltip: "Project",},
						{ field: 'projectPOTO.purchaseLabelKeyTO.code', displayName: "PO#", headerTooltip: "PO#",},
						{ field: 'projectPOTO.purchaseSchLabelKeyTO.code', displayName: "PO Sch ID", headerTooltip: "PO Sch ID"},
						{ field: 'empClassName', displayName: "Res Class Name", headerTooltip: "Res Class Name"},
						{ field: 'effectiveFrom', displayName: "Enroll Date", headerTooltip: "Enroll Date"},
						{ field: 'empLocation', displayName: "Enroll Loc", headerTooltip: "Enroll Loc"},
						{ field: 'reportingManagerLabelKeyTO.name', name:'reportingManagerLabelKeyTO.name', displayName: "Reporting to", headerTooltip: "Reporting to",
						cellTemplate:'<div>{{row.entity.reportingManagerLabelKeyTO.code}}{{row.entity.reportingManagerLabelKeyTO.name}}</div>' },
						{ field: 'contractDate', displayName: "Emp Con.Date", headerTooltip: "Emp Con.Date"},
						{ field: 'contractNumber', displayName: "Emp Con#", headerTooltip: "Emp Con#"},
						{ field: 'contractDocumentFileName', displayName: "More Details", headerTooltip : "More Details", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<div ng-click="grid.appScope.downloadContract(row.entity.id,row.entity.contractDocumentFileName)" class="fa fa-download" class="btn btn-primary btn-sm" >{{row.entity.contractDocumentFileName}}</div>',},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_EnrollmentPromotion");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
	$scope.$on("resetEnroll", function() { 
		$scope.employeeProjDtlTOs = [];
		$scope.gridOptions.data=[];
	});

}]);