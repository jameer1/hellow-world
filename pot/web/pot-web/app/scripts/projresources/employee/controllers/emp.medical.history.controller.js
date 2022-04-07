'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empmedicalhistory", {
		url : '/empmedicalhistory',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/medicalhistory/empmedicalrecords.html',
				controller : 'EmpMedicalHistoryController'
			}
		}
	})
}]).controller("EmpMedicalHistoryController",
		["$rootScope", "$scope", "utilservice", "EmpRegisterDataShareFactory", "EmpMedicalHistoryFactory", "GenericAlertService", "$location", "EmpRegisterService", "stylesService", "ngGridService", function($rootScope, $scope, utilservice, EmpRegisterDataShareFactory, EmpMedicalHistoryFactory, GenericAlertService, $location, EmpRegisterService, stylesService, ngGridService) {
			$scope.stylesSvc = stylesService;
			$scope.empMedicalHistoryTOs = [];
			var editEmpMedicalHistory = [];
			$scope.empDropDown = [];
			$scope.getEmpMedicalHistory = function() {
				console.log("getEmpMedicalHistory function");
				if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
					GenericAlertService.alertMessage("Please select the employee", "Warning");
					return;
				}
				var req = {
					"status" : 1,
					"empId" : $rootScope.selectedEmployeeData.id,
				};
				var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
				resultMap.then(function(data) {
					editEmpMedicalHistory = [];
					$scope.empDropDown = data.empMapsData;
					EmpRegisterService.getEmpMedicalHistory(req).then(function(data) {
						$scope.empMedicalHistoryTOs = data.empMedicalHistoryTOs;
						$scope.gridOptions.data = angular.copy($scope.empMedicalHistoryTOs);
						console.log($scope.empMedicalHistoryTOs);
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting medical records details", 'Error');
					});
				});

			}, $scope.getEmpMedicalHistory();
			$scope.medicalrecordRowSelect = function(medicalrecord) {
				if (medicalrecord.selected) {
					utilservice.addItemToArray(editEmpMedicalHistory, "id", medicalrecord);					
				} else {
					editEmpMedicalHistory.pop(medicalrecord);
				}
			}, 
			
		$scope.downloadMedicalDoc = function(record_id,file_name) {
			let req = {
				"recordId" : record_id,
				"category" : "Medical History",
				"fileName" : file_name
			}
			EmpRegisterService.downloadRegisterDocs(req);
			console.log("downloadMedicalDoc");
		},						
		$scope.addMedicalRecord = function(actionType) {
				if ($rootScope.selectedEmployeeData != undefined && $rootScope.selectedEmployeeData != null) {
				if (actionType == 'Edit' && editEmpMedicalHistory <= 0) {
					GenericAlertService.alertMessage("please select atleast one row to modify", 'Warning');
					return;
				}
				var itemData = {
					"editEmpMedicalHistory" : editEmpMedicalHistory,
					"projGeneralLabelTO" : $scope.projGeneralLabelTO,
					"projEmployeeClassMap" : $scope.projEmployeeClassMap,
					"empDropDown" : $scope.empDropDown
				}
				var medicalrecordspopup = EmpMedicalHistoryFactory.getEmpLatestServiceHistory(actionType, itemData);
				medicalrecordspopup.then(function(data) {
					editEmpMedicalHistory = [];
					$scope.getEmpMedicalHistory();
					$scope.empMedicalHistoryTOs = data.empMedicalHistoryTOs;
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while fetching emp medical history details", 'Error');
				});
				}
				else {
					GenericAlertService.alertMessage("Please select the Employee for Procurement & Delivery Details", 'Info');
				}
			},
			$scope.$on("resetMedicalHistory", function() {
				$scope.empMedicalHistoryTOs = [];
			});
			var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.medicalrecordRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'fromDate', displayName: "Date", headerTooltip: "Date"},
						{ field: 'projEmpRegisterTO.deploymentId', displayName: "Project Posting#", headerTooltip: "Project Posting#", },
						{ field: 'projEmpRegisterTO.parentName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projEmpRegisterTO.name', displayName: "Project Name", headerTooltip: "Project Name",},
						{ field: 'recordId', displayName: "Record#", headerTooltip: "Record#",},
						{ field: 'item', displayName: "Item / Record", headerTooltip: "Item / Record"},
						{ field: 'particular', displayName: "Particulars", headerTooltip: "Particulars"},
						{ field: 'comments', displayName: "Notes", headerTooltip: "Notes"},
						{ name: 'Download Documents', displayName: "Download", headerTooltip : "Download", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<div ng-click="grid.appScope.downloadMedicalDoc(row.entity.id,row.entity.fileName)" class="fa fa-download" class="btn btn-primary btn-sm" >{{medicalrecord.fileName}}</div>',},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_MedicalHistory");
					$scope.getEmpMedicalHistory();
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
		}]);