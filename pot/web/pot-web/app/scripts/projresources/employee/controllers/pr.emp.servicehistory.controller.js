'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empservicehistory", {
		url : '/empservicehistory',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/empservicehistory/empservicehistory.html',
				controller : 'EmpServiceHistoryController'
			}
		}
	})
}]).controller("EmpServiceHistoryController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "EmpRegisterDataShareFactory", "EmpServiceHistoryFactory", "GenericAlertService", "$location", "EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, EmpRegisterDataShareFactory,EmpServiceHistoryFactory, GenericAlertService, $location, EmpRegisterService, stylesService, ngGridService) {
	$scope.projEmpRegisterTOs = [];
	var editEmpServiceHistory = [];
	$scope.empRegisterDtlTO = {};
	$scope.empServiceType = {};
	$scope.empDropDown = {};
	$scope.stylesSvc = stylesService;
	
	$scope.getprojEmpRegisterTOs = function() {
		
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the Employee", "Warning");
			return;
		}
		var getEmpServiceHistoryReq = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id,
		};
		var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
		resultMap.then(function(data) {
			$scope.empDropDown = data.empMapsData;
			EmpRegisterService.getEmpServiceHistory(getEmpServiceHistoryReq).then(function(data) {
				$scope.projClassificationMap = data.projectLibTO.projClassMap;
				$scope.projEmpRegisterTOs = data.projEmpRegisterTOs;
				for(var i=0;i<$scope.projEmpRegisterTOs.length;i++){
				if($scope.projEmpRegisterTOs[i].empStatus == 'Current Employee'){
					$scope.projEmpRegisterTOs[i].empStatus = ' On Transfer';
				}
				if($scope.projEmpRegisterTOs[i].empStatus == 'Former Employee'){
					$scope.projEmpRegisterTOs[i].empStatus = 'Relieved';
				}
			}
				$scope.gridOptions.data = angular.copy($scope.projEmpRegisterTOs);
				$scope.empRegisterDtlTO = data.empRegisterDtlTO;
				$scope.empServiceType = data.empServiceType;
			}, function(error) {
				GenericAlertService.alertMessage("Error Occured While Getting Employee Servoice History Details", 'Error');
			});
		});

	}, $scope.getprojEmpRegisterTOs();
	$scope.empServiceHistoryRowSelect = function(servicehistory) {
		if (servicehistory.selected) {
			editEmpServiceHistory.push(servicehistory);
		} else {
			editEmpServiceHistory.splice(editEmpServiceHistory.indexOf(servicehistory), 1);
		}
	}, $scope.addEmpServiceHistory = function(actionType) {
		if (actionType == 'Edit' && editEmpServiceHistory <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}

		var itemData = {
			"empServiceType" : $scope.empServiceType,
			"projClassificationMap" : $scope.projClassificationMap,
			"editEmpServiceHistory" : editEmpServiceHistory,
			"empDropDown":$scope.empDropDown
		};

		var serviceHistoryPopup = EmpServiceHistoryFactory.empServiceHistoryOnLoad(itemData);
		serviceHistoryPopup.then(function(data) {
			$scope.projEmpRegisterTOs = data.empServiceHistoryDtlTOs;
			editEmpServiceHistory = [];

		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching emp service history  details", 'Error');
		});
	},
	$scope.$on("resetEmpService", function() {
		$scope.projEmpRegisterTOs = [];
	});
	$rootScope.$on('empService', function (event) {
		
		if($rootScope.selectedEmployeeData){
			$scope.getprojEmpRegisterTOs();	
		}
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.empServiceHistoryRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'deploymentId', displayName: "Posting#", headerTooltip: "Posting#"},
						{ field: 'parentName', displayName: "EPS", headerTooltip: "EPS", },
						{ field: 'name', displayName: "Project", headerTooltip: "Project"},
						{ field: 'dummy1', displayName: "Profit Center", headerTooltip: "Profit Center",},
						{ field: 'dummy', displayName: "Finance Center", headerTooltip: "Finance Center",},
						{ field: 'empClassifyName', displayName: "Desg /PayRoll", headerTooltip: "Desg /PayRoll"},
						{ field: 'empContractName', displayName: "Desg /ProjContract", headerTooltip: "Desg /ProjContract"},
						{ field: 'empWorkUnionName', displayName: "Desg /TradeUnion", headerTooltip: "Desg /TradeUnion"},
						{ field: 'empCatgName', displayName: "Category", headerTooltip: "Category"},
						{ field: 'mobilaizationDate', displayName: "Mob Date", headerTooltip: "Mob Date"},
						{ field: 'expecteddeMobilaizationDate', displayName: "Exp DeMob Date", headerTooltip: "Exp DeMob Date"},
						{ field: 'deMobilaizationDate', displayName: "Act DeMob date", headerTooltip: "Act DeMob date"},
						{ field: 'taxFileNum', displayName: "Emp.Tax File#", headerTooltip: "Emp.Tax File#"},
						{ field: 'workType', displayName: "Responsibilities", headerTooltip: "Responsibilities", },
						{ field: 'empStatus', displayName: "DeMob Status", headerTooltip: "DeMob Status"},
						{ field: 'notes', displayName: "Notes", headerTooltip: "Notes",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_ServiceHistory");
					$scope.getprojEmpRegisterTOs();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);
