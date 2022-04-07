'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantprocuredelivery", {
		url : '/plantprocuredelivery',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projplantreg/plantprocrdelivery/plantregprocrdeliverydtl.html',
				controller : 'PlantProcureDeliveryController'
			}
		}
	})
}]).controller("PlantProcureDeliveryController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenericAlertService", "PlantRegisterService", "ProjEmpClassService", "PlantProcureDeliveryFactory", "RegisterPurchaseOrderFactory","stylesService", "ngGridService","EmpRegisterService", function($rootScope, $scope, $q, $state, ngDialog, UserEPSProjectService, GenericAlertService, PlantRegisterService, ProjEmpClassService, PlantProcureDeliveryFactory, RegisterPurchaseOrderFactory, stylesService, ngGridService, EmpRegisterService) {
	var purchaseOrderMap = [];
	var editPoData = [];
	var plantProjPurchaseType = [];
	$scope.userProjectMap = [];
	$scope.plantProjPODtlTOs = null;
	$scope.showCreateButton = false;
	$scope.showEditButton = false;
	$scope.stylesSvc = stylesService;
	$scope.getPlantProjDeliveryDetails = function() {
		if ($rootScope.selectedPlantData == null || $rootScope.selectedPlantData == undefined) {
			return;
		}
		if ($rootScope.selectedPlantData.projId == null || $rootScope.selectedPlantData.projId == undefined) {
			if ($rootScope.selectedPlantData.assetType === 'New Plant') {
				$scope.showCreateButton = true;
			}
			if ($rootScope.selectedPlantData.assetType === 'Existing Plant ') {
				$scope.showEditButton = false;
			}
		}
		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedPlantData.projId,
			"plantId" : $rootScope.selectedPlantData.id
		};
		PlantRegisterService.getRegPlantProcureDeliveryDetails(req).then(function(data) {
			console.log("getPlantProjDeliveryDetails function");
			console.log(data);
			$scope.plantProjPODtlTO=data.plantProjPODtlTO;
			$scope.userProjMap = data.projectLibTO.userProjMap;
			if (data.plantProjPODtlTO != null && data.plantProjPODtlTO.id > 0) {
				$scope.plantProjPODtlTOs = [ data.plantProjPODtlTO ]
				$scope.gridOptions.data = angular.copy($scope.plantProjPODtlTOs);
				if ($scope.plantProjPODtlTOs != undefined && $scope.plantProjPODtlTOs != null && $scope.plantProjPODtlTOs.length > 0 && $scope.plantProjPODtlTOs[0].poStatus != 'C') {
					$scope.showCreateButton = false;
					$scope.showEditButton = true;
				}
			}

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting the plant procuremet details", "Error");
		});

	},
	$scope.getPlantProjDeliveryDetails();
	$scope.resetPurchaseOrderData = function() {
		$scope.plantProjPurchaseType = [];
		$scope.code = [];
	},
	$scope.addplantpoList = function(actionType) {
		//console.log("action type:"+actionType);
		//console.log($scope.userProjMap);
		$scope.flag1 = false;
		$scope.typeFlag = false;
		$scope.cumulative = 0;
		var popupDetails = PlantProcureDeliveryFactory.poDetailsPopUpOnLoad(actionType, $scope.plantProjPODtlTOs, plantProjPurchaseType, $scope.userProjMap);
		popupDetails.then(function(data) {
			$scope.plantProjPODtlTOs = [ data.plantProjPODtlTO ]
			console.log("addplantpoList function");
			console.log($scope.plantProjPODtlTOs);
			if ($scope.plantProjPODtlTOs != undefined && $scope.plantProjPODtlTOs != null && $scope.plantProjPODtlTOs.length > 0 && $scope.plantProjPODtlTOs[0].poStatus != 'C') {
				$scope.showCreateButton = false;
				$scope.showEditButton = true;
			}
			$rootScope.$broadcast('plantRefresh', {tabIndex:2});
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching procurement delivery details", 'Info');
		})		
	},
	
	$scope.deletePlantPo = function() {
		if (editPoData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.addPoData = [];
		} else {
			angular.forEach(editPoData, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"storeIds" : deleteIds,
				"status" : 2
			};
			PlantRegisterService.deleteStoreDocket(req).then(function(data) {
			});
			angular.forEach(editPoData, function(value, key) {
				GenericAlertService.alertMessage('Plant  details are deactivated successfully', 'Info');
				$scope.addPoData.splice($scope.addPoData.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Plant  details are failed to deactivate', "Error");
			});
			editPoData = [];
			$scope.deleteIds = [];
		}
	};

	/*$scope.downloadDocketDoc = function(plantPOId) {
		console.log(plantPOId);
		PlantRegisterService.downloadDocketDoc(plantPOId);
	};*/
	
	$scope.downloadDocketDoc = function(plantPOId,file_name){
		let req = {
			"recordId" : plantPOId,
			"category" : "Procurement Delivery Details",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
	}

	$scope.$on("defaultPlantProjDeliveryTab", function() {
		$scope.userProjectMap = [];
		$scope.plantProjPODtlTOs = null;
		$scope.getPlantProjDeliveryDetails();
	});
	
	$scope.$on("resetProcurDelDet", function() {
		$scope.plantProjPODtlTOs = [];
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'plantRegProjTO.parentName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'plantRegProjTO.name', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'purchaseLabelKeyTO.code', displayName: "Purchase Order Number", headerTooltip: "Purchase Order Number"},
						{ field: 'purchaseSchLabelKeyTO.code', displayName: "PO Schedule Item ID", headerTooltip: "PO Schedule Item ID",},
						{ field: 'purchaseSchLabelKeyTO.name', displayName: "PO Schedule Item Description", headerTooltip: "PO Schedule Item Description",},
						{ name: 'Docket', displayName: "Docket Details", headerTooltip: "Docket Details",cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate:'<div ng-click="grid.appScope.addplantpoList(row.entity.Edit)" class="btn btn-primary btn-sm">View/Edit</div>'},
						{ field: 'plantRegProjTO.commissionDate', displayName: "Commissioning Date", headerTooltip: "Commissioning Date"},
						{ field: 'odoMeter', displayName: "First Odo Meter Reading", headerTooltip: "First Odo Meter Reading"},
						{ name: 'Download', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Records of Deliver Dockets & Other Plant Records", headerTooltip : "Records of Deliver Dockets & Other Plant Records",
						cellTemplate: '<div ng-click="grid.appScope.downloadDocketDoc(row.entity.docId, row.entity.docName)" ng-show="row.entity.docName != null" class="fa fa-download" class="btn btn-primary btn-sm"></div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Plant&Equipments_ProcurementDelivery");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
}]);
