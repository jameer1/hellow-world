'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empchargeoutrates", {
		url : '/empchargeoutrates',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/chargerate/empchargeoutrates.html',
				controller : 'EmpChargeOutRatesController'
			}
		}
	})
}]).controller("EmpChargeOutRatesController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "EmpRegisterDataShareFactory", "EmpChargeOutRatesFactory", "GenericAlertService", "EmpRegisterService", "stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, EmpRegisterDataShareFactory, EmpChargeOutRatesFactory, GenericAlertService, EmpRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.empChargeOutRateTOs = [];
	var selectedEmpChareOutRate = null;
	$scope.projCostItemMap = [];
	$scope.projGeneralLabelTO = {};
	$scope.projEmployeeClassMap = [];
	$scope.empDropDown = {};
	$scope.measureId = 'Hours';
	$scope.getEmpChargeOutRates = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the Employee", "Warning");
			return;
		}

		if ($rootScope.selectedEmployeeData.projId == null && $rootScope.selectedEmployeeData.projId == undefined) {
			return;
		}
		var req = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id,
			"projId" : $rootScope.selectedEmployeeData.projId
		};
		var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
		resultMap.then(function(data) {
			$scope.empDropDown = data.empMapsData;
			EmpRegisterService.getEmpChargeOutRates(req).then(function(data) {
				$scope.empChargeOutRateTOs = data.empChargeOutRateTOs;
				for (let empCharge of $scope.empChargeOutRateTOs) {
					empCharge.cmpName1=$rootScope.selectedEmployeeData.cmpName;
					empCharge.measureId1=$scope.measureId;
					}
				for(var i=0;i<$scope.empChargeOutRateTOs.length;i++){
				if($scope.empChargeOutRateTOs[i].projEmpRegisterTO.empStatus == 'Current Employee'){
					$scope.empChargeOutRateTOs[i].projEmpRegisterTO.empStatus = 'On Transfer';
				}
				if($scope.empChargeOutRateTOs[i].projEmpRegisterTO.empStatus == 'Former Employee'){
					$scope.empChargeOutRateTOs[i].projEmpRegisterTO.empStatus = 'Relieved';
				}
			}
			
				$scope.gridOptions.data = angular.copy($scope.empChargeOutRateTOs);
				$scope.projGeneralLabelTO = data.projGeneralLabelTO;
			}, function(error) {
				if (error.message != null && error.message != undefined) {
					GenericAlertService.alertMessage(error.message, error.status);
				} else {
					GenericAlertService.alertMessage("Error occured while getting employee charge-out rate details", 'Error');
				}
			});
		});

	}, $scope.getEmpChargeOutRates();
	$scope.addChargeOutRate = function() {
		if (selectedEmpChareOutRate == null) {
			GenericAlertService.alertMessage("Please select one row to modify", 'Warning');
			return;
		}
		var itemData = {
			"projCostItemMap" : $scope.projCostItemMap,
			"projGeneralLabelTO" : $scope.projGeneralLabelTO,
			"projEmployeeClassMap" : $scope.projEmployeeClassMap,
			"empDropDown" : $scope.empDropDown,
			"chargeOutRate" : selectedEmpChareOutRate
		}
		
		var employeechargedetailspopup = EmpChargeOutRatesFactory.getEmpChargeOutRateDetails(itemData);
		employeechargedetailspopup.then(function(data) {
			$scope.empChargeOutRateTOs = data.empChargeOutRateTOs;
			selectedEmpChareOutRate = null;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching emp chargeout rate details", 'Error');
		});
	}
	$scope.empChargeOutRowSelect = function(chargeOutRatesTO) {
		selectedEmpChareOutRate = chargeOutRatesTO;
		
	}
	$scope.showChargeOutRates = function(remarks) {
		ngDialog.open({
			template : 'views/projresources/projempreg/chargerate/chargeoutcommentspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			controller : [ '$scope', function($scope) {
				$scope.notes = remarks;
			} ]
		});
	},
	$scope.$on("resetChargeoutrate", function() {
		$scope.empChargeOutRateTOs = [];
	});
	$rootScope.$on('chargeoutrate', function (event) {
		
		if($rootScope.selectedEmployeeData){
			$scope.getEmpChargeOutRates();	
		}
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.empChargeOutRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'projEmpRegisterTO.deploymentId', displayName: "Posting#", headerTooltip: "Posting#"},
						{ field: 'fromDate', displayName: "From", headerTooltip: "From", },
						{ field: 'projEmpRegisterTO.parentName', displayName: "EPS", headerTooltip: "EPS"},
						{ field: 'projEmpRegisterTO.name', displayName: "Project", headerTooltip: "Project",},
						{ field: 'cmpName1', displayName: "Company", headerTooltip: "Company"},
						{ field: 'measureId1', displayName: "Unit", headerTooltip: "Unit"},
						{ field: 'projCurrency', displayName: "Curr", headerTooltip: "Curr"},
						{ field: 'normalRate', displayName: "Nrml Rate", headerTooltip: "Nrml Rate"},
						{ field: 'idleRate', displayName: "Idle Rate", headerTooltip: "Idle Rate"},
						{ field: 'paidLeaveRate', displayName: "PL.Rate/Day", headerTooltip: "PL.Rate/Day"},
						{ field: 'leaveCostItemCode', displayName: "PL CostCode", headerTooltip: "PL CostCode"},
						{ field: 'mobCostItemCode', displayName: "Mob CostCode", headerTooltip: "Mob CostCode"},
						{ field: 'mobRate', displayName: "Mob Rate", headerTooltip: "Mob Rate"},
						{ field: 'deMobCostItemCode', displayName: "DeMob CostCode", headerTooltip: "DeMob CostCode", },
						{ field: 'deMobRate', displayName: "DeMob Rate", headerTooltip: "DeMob Rate"},
						{ field: 'projEmpRegisterTO.empStatus', displayName: "Demob Status", headerTooltip: "Demob Status",},
						{ name: 'Comment Documents', displayName: "Notes", headerTooltip : "Notes", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<div ng-click="grid.appScope.showChargeOutRates(row.entity.comments)" class="fa fa-comment fa-flip-horizontal"  class="btn btn-primary btn-sm" ></div>',},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_Qualification");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);